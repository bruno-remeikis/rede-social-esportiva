package com.faesa.app.dao;

import com.faesa.app.connection.OracleConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventoDAOOracle implements EventoDAO
{
    @Override
    public int getTotalRegistros()
    {
        String query = "SELECT COUNT(*) QTD_REGISTROS FROM EVENTOS";

        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ){
            if(rs.next())
                return rs.getInt("QTD_REGISTROS");
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar consultar total de eventos registrados: " + e.getMessage());
        }
        
        return -1;
    }   
}
