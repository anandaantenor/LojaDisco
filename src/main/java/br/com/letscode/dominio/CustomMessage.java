package br.com.letscode.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CustomMessage {

    private Integer status;
    private String message;

}
