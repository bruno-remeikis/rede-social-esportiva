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
    // Colunas da tabela
    private Integer id;
    private Integer idEsporte;
    private String nome;
    private String descricao;
    private Date data;
    private String local;
    private Date dtInsert;
    
    // Atributos auxiliares
    private Esporte esporte;

    public Evento(Integer id, Integer idEsporte, String nome, String descricao, Date data, String local, Date dtInsert) {
        this.id = id;
        this.idEsporte = idEsporte;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.dtInsert = dtInsert;
    }
}
