package br.com.letscode.service;

import br.com.letscode.dominio.Album;
import br.com.letscode.excecoes.AlbumJaExisteException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface AlbumService {

    Album inserir(Album album) throws IOException, AlbumJaExisteException;

    List<Album> listAll() throws IOException;

    Optional<Album> findByNome(String nome) throws IOException;

    Optional<Album> findByArtista(String nome) throws IOException;

    Optional<Album> findByFaixa(String nome) throws IOException;

    Optional<Album> findByTipoMusica(String nome) throws IOException;

    Album alterar(Album album, String identificador) throws IOException;

    Album remove(String identificador) throws IOException;
}

