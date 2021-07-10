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

    //escreve o arquivo ou adiciona um conteudo junto ao mesmo.
    private void write(String albumStr, StandardOpenOption option) throws IOException {

        try(BufferedWriter bf = Files.newBufferedWriter(path, option)){
            bf.flush();
            bf.write(albumStr);
        }
    }

    @Override
    //lista todos os elementos contidos no arquivo.
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
        return albums.stream().filter(album -> album.getFaixas().equals(nome)).findFirst();
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
    public void removerItemArquivo(String nomeAlbum) throws IOException {
        List<Album> albums = getAll();
        List<Album> albumResultante = new ArrayList<>();
        for (Album album:albums){
            if(!album.getNome().equals(nomeAlbum)){
                albumResultante.add(album);
            }
        }
        eraseContent();
        reescreverArquivo(albumResultante);

    }

    //FIXME precisa arrumar essa parte para conseguir inserir no json
    public ArrayList faixasArray(String nome) throws IOException {
        Optional<Album> albumResultante = findByNome(nome);
        ArrayList faixas = new ArrayList();
        int j = 6;
        int numeroDeFaixas = Integer.parseInt(albumResultante.get().getNumeroDeFaixas());
        ArrayList[] values = (ArrayList[]) albumResultante.stream().toArray();
        for(int i = 0; i < numeroDeFaixas; i++, j = j + 2){
            faixas =  values[j];
        }
        return faixas;
    }

    @SneakyThrows
    private String format(Album album){
        String format = String.format("%s;%s;%s;%s;%s;%s;%s \r\n", album.getIdentificador(),
                album.getNome(), album.getArtista(), album.getGenero(),
                album.getPreco(), album.getQuantidadeEmEstoque(),
                album.getNumeroDeFaixas());
        return format;
    }

    private Album convert(String linha){
        StringTokenizer token = new StringTokenizer(linha,",");
        Album album = new Album();
        album.setIdentificador(token.nextToken());
        album.setNome(token.nextToken());
        album.setArtista(token.nextToken());
        album.setGenero(token.nextToken());
        album.setPreco(token.nextToken());
        album.setQuantidadeEmEstoque(token.nextToken());
        album.setNumeroDeFaixas(token.nextToken());
        return album;
    }

    public void eraseContent() throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(path);
        writer.write("");
        writer.flush();
    }
}
