package br.com.letscode.dominio;

import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
public class Album {

    private String identificador;
    private String artista;
    private String nome;
    private BigDecimal preco;
    private int quantidadeEmEstoque;
    private int numeroDeFaixas;
    private String tipoDeMusica;
    private String[] faixas;
    private String[] duracaoDeFaixas;
}
