package br.com.letscode.service;

import br.com.letscode.dao.AlbumDao;
import br.com.letscode.dominio.*;
import br.com.letscode.excecoes.UsuarioJaExisteException;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AlbumServiceImpl implements AlbumService{
    @Inject
    private AlbumDao albumDao;

    @Override
    public Album inserir(Album album) throws IOException {
        if(albumDao.findByNome(album.getNome()).isPresent()){

            throw new UsuarioJaExisteException("Esse disco já foi registrado");
        }
        //album.setIdentificador(UUID.randomUUID().toString());
        return albumDao.inserirNoArquivo(album);
    }

    @Override
    public List<Album> listAll() throws IOException {
        return albumDao.getAll();
    }

    @Override
    public Optional<Album> findByNome(String nome) throws IOException {
        return albumDao.findByNome(nome);
    }

    @Override
    public Optional<Album> findByArtista(String nome) throws IOException {
        return albumDao.findByArtista(nome);
    }

    @Override
    public Optional<Album> findByFaixa(String nome) throws IOException {
        return albumDao.findByFaixa(nome);
    }

    @Override
    public Optional<Album> findByTipoMusica(String nome) throws IOException {
        return albumDao.findByTipoMusica(nome);
    }

    @Override
    public Album alterar(Album album, String identificador) throws IOException {

        return albumDao.alterarArquivo(album, identificador);
    }

    @Override
    public void remove(String identificador) throws IOException{
        albumDao.removerItemArquivo(identificador);
    }
}
