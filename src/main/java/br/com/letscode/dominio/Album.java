package br.com.letscode.dominio;

import lombok.*;

@Data
@Getter
@Setter
public class Album {

    private String identificador;
    private String nome;
    private String artista;
    private String genero;
    private String preco;
    private String quantidadeEmEstoque;
    private String numeroDeFaixas;
    private String[] faixas;
}
