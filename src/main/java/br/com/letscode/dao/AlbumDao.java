package br.com.letscode.dao;

import br.com.letscode.dominio.Album;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AlbumDao {
    Album inserirNoArquivo(Album album) throws IOException;

    List<Album> getAll() throws IOException;

    Optional<Album> findByNome(String nome) throws IOException;

    Optional<Album> findByArtista(String nome) throws IOException;

    Optional<Album> findByFaixa(String nome) throws IOException;

    Optional<Album> findByTipoMusica(String nome) throws IOException;

    Album alterarArquivo(Album album, String identificador) throws IOException;

    void removerItemArquivo(String identificador) throws IOException;
}