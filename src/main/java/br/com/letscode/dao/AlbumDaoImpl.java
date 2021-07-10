package br.com.letscode.dao;

import br.com.letscode.dominio.Album;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AlbumDaoImpl implements AlbumDao{

    String caminho = "C:/Users/DELL/Documents/LojaDeDiscos/src/main/java/arquivo/discos.csv";

    private Path path;
    @PostConstruct
    public void init(){

        try {
            path = Paths.get(caminho);
            if (!path.toFile().exists()) {
                Files.createFile(path);
            }
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
    @Override
    public Album inserirNoArquivo(Album album) throws IOException{
        write(format(album), StandardOpenOption.APPEND);
        return album;
    }

    private void write(String albumStr, StandardOpenOption option) throws IOException {

        try(BufferedWriter bf = Files.newBufferedWriter(path, option)){
            bf.flush();
            bf.write(albumStr);
        }
    }

    @Override
    public List<Album> getAll() throws IOException{
        List<Album> album;
        try(BufferedReader br = Files.newBufferedReader(path)){
            album = br.lines().filter(Objects::nonNull)
                    .filter(Predicate.not(String::isEmpty))
                    .map(this::convert).collect(Collectors.toList());
        }
        return album;
    }

    @Override
    public Optional<Album> findByNome(String nome) throws IOException {
        List<Album> albums = getAll();
        return albums.stream().filter(album -> album.getNome().equals(nome)).findFirst();
    }

    @Override
    public Optional<Album> findByArtista(String nome) throws IOException {
        List<Album> albums = getAll();
        return albums.stream().filter(album -> album.getArtista().equals(nome)).findFirst();

    }
    @Override
    public Optional<Album> findByFaixa(String nome) throws IOException {
        List<Album> albums = getAll();
        return albums.stream().filter(album -> Arrays.asList(album.getFaixas()).contains(nome)).findFirst();
    }

    @Override
    public Optional<Album> findByTipoMusica(String nome) throws IOException {
        List<Album> albums = getAll();
        return albums.stream().filter(album -> album.getGenero().equals(nome)).findFirst();
    }

    @Override
    public Album alterarArquivo(Album album, String identificador) throws IOException {

        List<Album> albuns = getAll();
        Optional<Album> optionalAlbum = albuns.stream()
                .filter(albumSearch -> albumSearch.getNome().equals(identificador)).findFirst();
        if(optionalAlbum.isPresent()){
            optionalAlbum.get().setNome(album.getNome());

            reescreverArquivo(albuns);
            return optionalAlbum.get();
        }
        return album;
    }

    private void reescreverArquivo(List<Album> albums) throws IOException {

        StringBuilder builder = new StringBuilder();
        for (Album albumBuilder: albums) {
            builder.append(format(albumBuilder));
        }

        write(builder.toString(), StandardOpenOption.CREATE);
    }

    @Override
    public Album removerItemArquivo(String nomeAlbum) throws IOException {
        List<Album> albums = getAll();
        List<Album> albumResultante = new ArrayList<>();
        Album albumRemovido = null;

        for (Album album:albums){
            if(!album.getNome().equals(nomeAlbum)){
                albumResultante.add(album);
            }else{
                albumRemovido = album;
            }
        }
        eraseContent();
        reescreverArquivo(albumResultante);
        return albumRemovido;
    }


    private String format(Album album){
        String format = String.format("%s;%s;%s;%s;%s;%s;%s;%s \r\n",
                album.getIdentificador(),
                album.getArtista(),
                album.getNome(),
                album.getGenero(),
                album.getPreco(),
                album.getQuantidadeEmEstoque(),
                album.getNumeroDeFaixas(),
                album.getFaixas());
        return format;
    }

    private Album convert(String linha){
        StringTokenizer token = new StringTokenizer(linha,";");
        Album album = new Album();
        album.setIdentificador(token.nextToken());
        album.setArtista(token.nextToken());
        album.setNome(token.nextToken());
        album.setGenero(token.nextToken());
        album.setPreco(token.nextToken());
        album.setQuantidadeEmEstoque(token.nextToken());
        album.setNumeroDeFaixas(token.nextToken());

        var data = token.nextToken();

        int numeroDeFaixas = Integer.parseInt(album.getNumeroDeFaixas());
        String[] faixas = new String[numeroDeFaixas*2];


        int i = 0;
        while(token.hasMoreElements()){
            faixas[i] = data;
            data = token.nextToken();
            i++;
        }
        album.setFaixas(faixas);
        return album;
    }

    public void eraseContent() throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(path);
        writer.write("");
        writer.flush();
    }
}
