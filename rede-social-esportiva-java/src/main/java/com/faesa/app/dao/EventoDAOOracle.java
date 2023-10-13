package com.faesa.app.dao;

import com.faesa.app.connection.OracleConnector;
import com.faesa.app.model.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventoDAOOracle extends DAO implements EventoDAO
{
    @Override
    public int getTotalRegistros()
    {
        String query =
            "SELECT COUNT(*) QTD_REGISTROS FROM EVENTO";

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

    @Override
    public void insert(Evento e) throws Exception
    {
        String query =
            "INSERT INTO EVENTO(ID_EVENTO, NOME, DESCRICAO, DT_EVENTO, LOCAL_EVENTO)" +
            "VALUES(ID_EVENTO_SEQ.NEXTVAL, ?, ?, ?, ?)";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            ps.setString(1, e.getNome());
            ps.setString(2, e.getDescricao());
            setDate(ps, 3, e.getData());
            ps.setString(4, e.getLocal());
            
            ps.execute();
        }
    }

    @Override
    public boolean delete(int id) throws Exception
    {
        String query =
            "DELETE FROM EVENTO WHERE ID_EVENTO = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            ps.setInt(1, id);
            
            return ps.executeUpdate() == 1;
        }
    }
}
