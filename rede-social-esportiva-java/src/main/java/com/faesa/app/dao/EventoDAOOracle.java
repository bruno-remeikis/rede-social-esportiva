package com.faesa.app.dao;

import com.faesa.app.connection.OracleConnector;
import com.faesa.app.model.Esporte;
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
        e.setId(rs.getInt("ID"));
        e.setNome(rs.getString("NOME"));
        e.setDescricao(rs.getString("DESCRICAO"));
        e.setData(rs.getDate("DT_EVENTO"));
        e.setLocal(rs.getString("LOCAL"));
        e.setDtInsert(rs.getDate("DT_INSERT"));
        
        Esporte esporte = new Esporte();
        esporte.setNome(rs.getString("NOME_ESPORTE"));
        e.setEsporte(esporte);
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
            "SELECT " +
            "    EVENTO.ID " +
            "   ,EVENTO.NOME " +
            "   ,EVENTO.DESCRICAO " +
            "   ,EVENTO.DT_EVENTO " +
            "   ,EVENTO.LOCAL " +
            "   ,EVENTO.DT_INSERT " +
            "   ,ESPORTE.NOME AS NOME_ESPORTE " +
            "FROM EVENTO " +
            "INNER JOIN ESPORTE ON " +
            "   ESPORTE.ID = EVENTO.ID_ESPORTE " +
            "WHERE EVENTO.ID = ?";
        
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
            "SELECT " +
            "    EVENTO.ID " +
            "   ,EVENTO.NOME " +
            "   ,EVENTO.DESCRICAO " +
            "   ,EVENTO.DT_EVENTO " +
            "   ,EVENTO.LOCAL " +
            "   ,EVENTO.DT_INSERT " +
            "   ,ESPORTE.NOME AS NOME_ESPORTE " +
            "FROM EVENTO " +
            "INNER JOIN ESPORTE ON " +
            "   ESPORTE.ID = EVENTO.ID_ESPORTE";
        
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
            "INSERT INTO EVENTO(ID, ID_ESPORTE, NOME, DESCRICAO, DT_EVENTO, LOCAL) " +
            "VALUES(ID_EVENTO_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            int i = 1;
            ps.setInt(i++, e.getIdEsporte());
            ps.setString(i++, e.getNome());
            ps.setString(i++, e.getDescricao());
            setDate(ps, i++, e.getData());
            ps.setString(i++, e.getLocal());
            
            ps.execute();
            return true;
        }
    }
    
    @Override
    public boolean update(Evento e) throws Exception
    {
        String query =
            "UPDATE EVENTO SET " +
            "   NOME = ?, " +
            "   DESCRICAO = ?, " +
            "   DT_EVENTO = ?, " +
            "   LOCAL = ? " +
            "WHERE ID = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            int i = 1;
            ps.setString(i++, e.getNome());
            ps.setString(i++, e.getDescricao());
            setDate(ps, i++, e.getData());
            ps.setString(i++, e.getLocal());
            ps.setInt(i++, e.getId());
            
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(int id) throws Exception
    {
        String query =
            "DELETE FROM EVENTO WHERE ID = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            ps.setInt(1, id);
            
            return ps.executeUpdate() == 1;
        }
    }
}
