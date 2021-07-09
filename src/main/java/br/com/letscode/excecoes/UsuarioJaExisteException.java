package br.com.letscode.excecoes;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String message){
        super(message);
    }
}
