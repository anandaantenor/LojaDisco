package br.com.letscode.dominio;

import lombok.*;

@Data
@Getter
@Setter
public class AlbumParaTeste {

    String nome;
    String artista;
    String genero;
    int quantidadeEmEstoque;
    double preco;
    int numeroDeFaixas;
    String[] faixas;
    String[] duracaoDeFaixas;

}
