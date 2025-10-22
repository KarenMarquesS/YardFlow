package org.example.yardflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.yardflow.model.EnumFuncao;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private long id;
    private String nome;
    private String email;
    private EnumFuncao funcao;

}
