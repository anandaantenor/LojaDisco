package br.com.letscode;

import br.com.letscode.dominio.Album;
import br.com.letscode.dominio.CustomMessage;
import br.com.letscode.excecoes.UsuarioJaExisteException;
import br.com.letscode.service.AlbumService;
import com.google.gson.Gson;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet(name = "lojaDiscosServlet" , urlPatterns = "/lojaDiscos")
public class LojaDiscoServlet extends HttpServlet {

    public static final String ALBUMS_SESSION = "albums";
    @Inject
    private AlbumService albumService;

    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
    }


    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder conteudo = getBody(request);
        Album albumRequest = gson.fromJson(conteudo.toString(), Album.class);
        PrintWriter print = prepareResponse(response);
        String resposta = "";
        if(null==albumRequest.getNome() || null==albumRequest.getArtista()){
            CustomMessage message = new CustomMessage(HttpServletResponse.SC_BAD_REQUEST, "Invalid Parameters");
            response.setStatus(message.getStatus());
            resposta= gson.toJson(message);
        }else{

            try {
                HttpSession sessao = request.getSession(true);

                albumService.inserir(albumRequest);

                List<Album> albums = albumService.listAll();


                sessao.setAttribute(ALBUMS_SESSION, albums);

                resposta = gson.toJson(albums);
            }catch (UsuarioJaExisteException usuarioJaExisteException){
                response.setStatus(400);
                resposta = gson.toJson(new CustomMessage(400,usuarioJaExisteException.getMessage()));
            }
        }
        print.write(resposta);
        print.close();

    }


    private PrintWriter prepareResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter print = response.getWriter();
        return print;
    }

    private StringBuilder getBody(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String line="";
        StringBuilder conteudo = new StringBuilder();

        while(null!= (line= br.readLine())){
            conteudo.append(line);
        }
        return conteudo;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String nomeDisco =  request.getParameter("nome disco");
        HttpSession sessao = request.getSession();
        List<Album> albums = new ArrayList<>();
        if(Objects.nonNull(sessao.getAttribute(ALBUMS_SESSION))){
            albums.addAll((List<Album>) sessao.getAttribute(ALBUMS_SESSION));
        }else{
            albums.addAll(albumService.listAll());
        }

        PrintWriter printWriter =prepareResponse(response);
        if(null!=nomeDisco && Objects.nonNull(albums)){
            Optional<Album> optionalAlbum = albums.stream().filter(album -> album.getNome().equals(nomeDisco)).findFirst();
            if(optionalAlbum.isPresent()){

                printWriter.write(gson.toJson(optionalAlbum.get()));
            }else{

                CustomMessage message = new CustomMessage(404, "Album não encontrado");
                response.setStatus(404);
                printWriter.write(gson.toJson(message));
            }
        }else {

            printWriter.write(gson.toJson(albums));

        }

        printWriter.close();
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder conteudo = getBody(request);
        String identificador = request.getParameter("identificador");

        PrintWriter printWriter = prepareResponse(response);
        String resposta= "";
        if(Objects.isNull(identificador)){
            resposta = erroMessage(response);
        }else{
            Album album = gson.fromJson(conteudo.toString(),Album.class);
            resposta = gson.toJson(albumService.alterar(album, identificador));
            request.getSession().setAttribute(ALBUMS_SESSION,albumService.listAll());
        }

        printWriter.write(resposta);
        printWriter.close();
    }


    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String identificador = request.getParameter("identificador");
        PrintWriter printWriter = prepareResponse(response);
        String resposta;
        if(Objects.isNull(identificador)){
            resposta = erroMessage(response);
        }else {

            albumService.remove(identificador);
            resposta = gson.toJson(new CustomMessage(204, "album removido"));
            request.getSession().setAttribute(ALBUMS_SESSION, albumService.listAll());

        }
    }

    private String erroMessage(HttpServletResponse response) {

        response.setStatus(400);
        return gson.toJson(new CustomMessage(400,"identificador não informado"));

    }
}
