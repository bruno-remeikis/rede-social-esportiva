package com.faesa.app.controller;

import com.faesa.app.dao.EventoDAOOracle;
import com.faesa.app.model.Evento;

public class EventoController
{
    public static int getTotalRegistros()
    {
        return new EventoDAOOracle().getTotalRegistros();
    }
    
    public static Evento selectById(int id)
    {
        try {
            return new EventoDAOOracle().selectById(id);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar buscar evento por ID: " + e.getMessage());
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
                ? new EventoDAOOracle().insert(evento)
                : new EventoDAOOracle().update(evento);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar salvar o evento: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean delete(int id)
    {
        try {
            return new EventoDAOOracle().delete(id);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar remover o evento: " + e.getMessage());
            return false;
        }
    }
}
