package com.faesa.app.controller;

import com.faesa.app.dao.EventoDAO;
import com.faesa.app.dao.EventoDAOOracle;
import com.faesa.app.model.Evento;
import java.util.List;

public class EventoController
{
    private static final EventoDAO eventoDao = new EventoDAOOracle();
    
    public static int getTotalRegistros()
    {
        return eventoDao.getTotalRegistros();
    }
    
    public static Evento findById(int id)
    {
        try {
            return eventoDao.selectById(id);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar buscar evento por ID: " + e.getMessage());
            return null;
        }
    }
    
    public static List<Evento> listAll()
    {
        try {
            return eventoDao.selectAll();
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar buscar eventos." + e.getMessage());
            return null;
        }
    }
    
    /**
     * Se {@code evento} possuir ID, realiza-se um UPDATE sobre o registro.
     * Caso contrário, um novo registro será inserido.
     * @param evento {@code Evento} a ser inserido.
     * @return {@code boolean} se a operação for bem sucedida.
     */
    public static boolean save(Evento evento)
    {
        try {
            return evento.getId() == null
                ? eventoDao.insert(evento)
                : eventoDao.update(evento);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar salvar o evento: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean delete(int id)
    {
        try {
            return eventoDao.delete(id);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar remover o evento: " + e.getMessage());
            return false;
        }
    }
}
