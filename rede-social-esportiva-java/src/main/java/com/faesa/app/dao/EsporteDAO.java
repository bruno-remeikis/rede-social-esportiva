package com.faesa.app.dao;

import com.faesa.app.model.Esporte;
import com.faesa.app.model.dto.EventosEsporteDTO;
import java.util.List;

public interface EsporteDAO
{
    int getTotalRegistros();
    
    Esporte selectById(int id) throws Exception;
    
    Esporte selectByNome(String nome) throws Exception;
    
    List<Esporte> selectAll() throws Exception;
    
    List<EventosEsporteDTO> selectQtdEventosEsporte() throws Exception; 
    
    boolean insert(Esporte e) throws Exception;
    
    boolean update(Esporte e) throws Exception;
    
    boolean delete(int id) throws Exception;
}
