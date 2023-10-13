package com.faesa.app.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento
{
    private Integer id;
    private String nome;
    private String descricao;
    private Date data;
    private String local;
    private Date dtInsert;
}
