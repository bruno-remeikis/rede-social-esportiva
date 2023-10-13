package com.faesa.app.controller;

import com.faesa.app.dao.EventoDAOOracle;
import com.faesa.app.model.Evento;

public class EventoController
{
    public static int getTotalRegistros()
    {
        return new EventoDAOOracle().getTotalRegistros();
    }
    
    public static boolean insert(Evento evento)
    {
        try {
            new EventoDAOOracle().insert(evento);
            return true;
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar criar o evento: " + e.getMessage());
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
