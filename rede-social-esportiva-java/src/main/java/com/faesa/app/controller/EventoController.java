package com.faesa.app.controller;

import com.faesa.app.dao.EventoDAOOracle;

public class EventoController
{
    public static int getTotalRegistros()
    {
        return new EventoDAOOracle().getTotalRegistros();
    }
}
