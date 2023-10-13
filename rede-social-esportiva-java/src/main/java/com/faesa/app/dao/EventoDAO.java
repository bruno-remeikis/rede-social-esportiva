package com.faesa.app.dao;

import com.faesa.app.model.Evento;

public interface EventoDAO
{
    Evento selectById(int id) throws Exception;
    
    boolean insert(Evento e) throws Exception;
    
    boolean update(Evento e) throws Exception;
    
    boolean delete(int id) throws Exception;
}
