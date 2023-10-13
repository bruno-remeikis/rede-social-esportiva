package com.faesa.app.dao;

import com.faesa.app.model.Evento;

public interface EventoDAO
{
    void insert(Evento e) throws Exception;
    
    boolean delete(int id) throws Exception;
}
