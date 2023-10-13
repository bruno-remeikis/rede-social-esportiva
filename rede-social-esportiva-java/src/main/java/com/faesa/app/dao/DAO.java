package com.faesa.app.dao;

import java.sql.PreparedStatement;

public abstract class DAO
{
    abstract int getTotalRegistros();
    
    protected void setDate(PreparedStatement ps, int i, java.util.Date date) throws Exception
    {
        ps.setDate(i, new java.sql.Date(date.getTime()));
    }
}
