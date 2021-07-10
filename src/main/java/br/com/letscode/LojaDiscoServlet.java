package br.com.letscode;

import br.com.letscode.dominio.Album;
import br.com.letscode.dominio.CustomMessage;
import br.com.letscode.excecoes.AlbumJaExisteException;
import br.com.letscode.excecoes.AlbumNaoEncontradoException;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder conteudo = getBody(request);
        Album albumRequest = gson.fromJson(conteudo.toString(), Album.class);
        PrintWriter print = prepareResponse(response);
        String resposta;
        if(albumRequest.getNome() == null || albumRequest.getArtista() == null){
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

            }catch (AlbumJaExisteException albumJaExisteException){
                response.setStatus(400);
                resposta = gson.toJson(new CustomMessage(400, albumJaExisteException.getMessage()));
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
        String line;
        StringBuilder conteudo = new StringBuilder();

        while(null != (line= br.readLine())){
            conteudo.append(line);
        }
        return conteudo;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String nomeDisco =  request.getParameter("nomeDisco");
        final String nomeArtista = request.getParameter("nomeArtista");
        final String nomeDaMusica = request.getParameter("nomeMusica");
        final String tipoDeMusica = request.getParameter("tipoDaMusica");

        HttpSession sessao = request.getSession();
        List<Album> albums = new ArrayList<>();
        if(Objects.nonNull(sessao.getAttribute(ALBUMS_SESSION))){
            albums.addAll((List<Album>) sessao.getAttribute(ALBUMS_SESSION));
        }else{
            albums.addAll(albumService.listAll());
        }

        PrintWriter printWriter = prepareResponse(response);
        if(nomeDisco != null){
            try{
                Optional<Album> optionalAlbum = albumService.findByNome(nomeDisco);
                printWriter.write(gson.toJson(optionalAlbum));
            } catch(AlbumNaoEncontradoException albumNaoEncontradoException){
                response.setStatus(400);
                printWriter.write(gson.toJson(new CustomMessage(400, albumNaoEncontradoException.getMessage())));
            }
        }else if(nomeArtista != null){
            Optional<Album> optionalAlbum = albumService.findByArtista(nomeArtista);
            if(optionalAlbum != null){
                printWriter.write(gson.toJson(optionalAlbum));
            }else{
                messageNaoEncontrado(response, printWriter);
            }
        }else if(nomeDaMusica != null) {
            Optional<Album> optionalAlbum = albumService.findByFaixa(nomeDaMusica);
            if (optionalAlbum != null) {
                printWriter.write(gson.toJson(optionalAlbum));
            } else {
                messageNaoEncontrado(response, printWriter);
            }
        }else if(tipoDeMusica != null) {
            Optional<Album> optionalAlbum = albumService.findByTipoMusica(tipoDeMusica);
            if (optionalAlbum != null) {
                printWriter.write(gson.toJson(optionalAlbum));
            } else {
                messageNaoEncontrado(response, printWriter);
            }
        }else{
            printWriter.write(gson.toJson(albums));
        }
        printWriter.close();
    }

    private void messageNaoEncontrado(HttpServletResponse response, PrintWriter printWriter){
        CustomMessage message = new CustomMessage(404, "Album não encontrado");
        response.setStatus(404);
        printWriter.write(gson.toJson(message));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder conteudo = getBody(request);
        String identificador = request.getParameter("identificador");

        PrintWriter printWriter = prepareResponse(response);
        String resposta;
        if(Objects.isNull(identificador)){
            resposta = erroMessage(response);
        }else{
            Album album = gson.fromJson(conteudo.toString(), Album.class);
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
            Album albumRemovido = albumService.remove(identificador);
            albumService.remove(identificador);
            resposta = gson.toJson(new CustomMessage(204, "Album " + albumRemovido.getNome()+ " removido."));
            request.getSession().setAttribute(ALBUMS_SESSION, albumService.listAll());
        }
        printWriter.write(resposta);
        printWriter.close();
    }

    private String erroMessage(HttpServletResponse response) {
        response.setStatus(400);
        return gson.toJson(new CustomMessage(400,"Identificador não informado"));

    }
}
