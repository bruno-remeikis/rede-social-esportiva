package com.faesa.app.dao;

import com.faesa.app.connection.OracleConnector;
import com.faesa.app.model.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventoDAOOracle extends DAO implements EventoDAO
{
    private static Evento fillObject(ResultSet rs) throws Exception
    {
        Evento e = new Evento();
        e.setId(rs.getInt("ID_EVENTO"));
        e.setNome(rs.getString("NOME"));
        e.setDescricao(rs.getString("DESCRICAO"));
        e.setData(rs.getDate("DT_EVENTO"));
        e.setLocal(rs.getString("LOCAL_EVENTO"));
        e.setDtInsert(rs.getDate("DT_INSERT"));
        return e;
    }
    
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
    public Evento selectById(int id) throws Exception
    {
        String query =
            "SELECT ID_EVENTO, NOME, DESCRICAO, DT_EVENTO, LOCAL_EVENTO, DT_INSERT " +
            "FROM EVENTO " +
            "WHERE ID_EVENTO = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            ps.setInt(1, id);
            
            try(ResultSet rs = ps.executeQuery())
            {
                if(rs.next())
                    return fillObject(rs);
                return null;
            }
        }
    }
    
    @Override
    public List<Evento> selectAll() throws Exception
    {
        String query =
            "SELECT ID_EVENTO, NOME, DESCRICAO, DT_EVENTO, LOCAL_EVENTO, DT_INSERT " +
            "FROM EVENTO";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ){
            List<Evento> eventos = new ArrayList();

            while(rs.next())
                eventos.add(fillObject(rs));

            return eventos;
        }
    }

    @Override
    public boolean insert(Evento e) throws Exception
    {
        String query =
            "INSERT INTO EVENTO(ID_EVENTO, NOME, DESCRICAO, DT_EVENTO, LOCAL_EVENTO) " +
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
            return true;
        }
    }
    
    @Override
    public boolean update(Evento e) throws Exception
    {
        String query =
            "UPDATE EVENTO SET " +
            "NOME = ?, " +
            "DESCRICAO = ?, " +
            "DT_EVENTO = ?, " +
            "LOCAL_EVENTO = ? " +
            "WHERE ID_EVENTO = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            ps.setString(1, e.getNome());
            ps.setString(2, e.getDescricao());
            setDate(ps, 3, e.getData());
            ps.setString(4, e.getLocal());
            ps.setInt(5, e.getId());
            
            return ps.executeUpdate() == 1;
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
