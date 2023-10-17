package com.faesa.app.dao;

import com.faesa.app.connection.OracleConnector;
import com.faesa.app.model.Esporte;
import com.faesa.app.model.dto.EventosEsporteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EsporteDAOOracle extends DAO implements EsporteDAO
{
    private static Esporte fillObject(ResultSet rs) throws Exception
    {
        Esporte e = new Esporte();
        e.setId(rs.getInt("ID"));
        e.setNome(rs.getString("NOME"));
        e.setDtInsert(rs.getDate("DT_INSERT"));
        return e;
    }
    
    @Override
    public int getTotalRegistros()
    {
        String query =
            "SELECT COUNT(*) QTD_REGISTROS FROM ESPORTE";

        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ){
            if(rs.next())
                return rs.getInt("QTD_REGISTROS");
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar consultar total de esportes registrados: " + e.getMessage());
        }
        
        return -1;
    }

    @Override
    public Esporte selectById(int id) throws Exception
    {
        String query =
            "SELECT ID, NOME, DT_INSERT " +
            "FROM ESPORTE " +
            "WHERE ID = ?";
        
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
    public Esporte selectByNome(String nome) throws Exception
    {
        String query =
            "SELECT ID, NOME, DT_INSERT " +
            "FROM ESPORTE " +
            "WHERE UPPER(NOME) = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            ps.setString(1, nome.toUpperCase());
            
            try(ResultSet rs = ps.executeQuery())
            {
                if(rs.next())
                    return fillObject(rs);
                return null;
            }
        }
    }

    @Override
    public List<Esporte> selectAll() throws Exception
    {
        String query =
            "SELECT ID, NOME, DT_INSERT " +
            "FROM ESPORTE";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ){
            List<Esporte> esportes = new ArrayList();

            while(rs.next())
                esportes.add(fillObject(rs));

            return esportes;
        }
    }
    
    @Override
    public List<EventosEsporteDTO> selectQtdEventosEsporte() throws Exception
    {
        /*String query =
            "SELECT " +
            "    ESPORTE.ID AS ID_ESPORTE " +
            "   ,ESPORTE.NOME AS NOME_ESPORTE " +
            "   ,(SELECT COUNT(*) FROM EVENTO WHERE EVENTO.ID_ESPORTE = ESPORTE.ID) AS QTD_EVENTOS " +
            "FROM ESPORTE";*/
        
        String query =
            "SELECT " +
            "    ESPORTE.ID AS ID_ESPORTE " +
            "   ,ESPORTE.NOME AS NOME_ESPORTE " +
            "   ,COUNT(EVENTO.ID) AS QTD_EVENTOS  " +
            "FROM ESPORTE " +
            "LEFT JOIN EVENTO ON " +
            "   EVENTO.ID_ESPORTE = ESPORTE.ID " +
            "GROUP BY " +
            "   ESPORTE.ID, " +
            "   ESPORTE.NOME " +
            "ORDER BY " +
            "   ESPORTE.NOME, " +
            "   ESPORTE.ID ";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ){
            List<EventosEsporteDTO> eventosEsportes = new ArrayList();

            while(rs.next())
            {
                EventosEsporteDTO ee = new EventosEsporteDTO();
                ee.setIdEsporte(rs.getInt("ID_ESPORTE"));
                ee.setNomeEsporte(rs.getString("NOME_ESPORTE"));
                ee.setQtdEventos(rs.getInt("QTD_EVENTOS"));
                eventosEsportes.add(ee);
            }

            return eventosEsportes;
        }
    }

    @Override
    public boolean insert(Esporte e) throws Exception
    {
        String query =
            "INSERT INTO ESPORTE(ID, NOME) " +
            "VALUES(ID_ESPORTE_SEQ.NEXTVAL, ?)";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            int i = 1;
            ps.setString(i++, e.getNome());
            
            ps.execute();
            return true;
        }
    }

    @Override
    public boolean update(Esporte e) throws Exception
    {
        String query =
            "UPDATE ESPORTE SET " +
            "   NOME = ? " +
            "WHERE ID = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            int i = 1;
            ps.setString(i++, e.getNome());
            ps.setInt(i++, e.getId());
            
            return ps.executeUpdate() == 1;
        }
    }

    @Override
    public boolean delete(int id) throws Exception
    {
        String query =
            "DELETE FROM ESPORTE WHERE ID = ?";
        
        try(
            Connection con = OracleConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(query);
        ){
            ps.setInt(1, id);
            
            return ps.executeUpdate() == 1;
        }
    }
}
