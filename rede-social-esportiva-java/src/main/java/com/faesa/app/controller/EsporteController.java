package com.faesa.app.controller;

import com.faesa.app.dao.EsporteDAO;
import com.faesa.app.dao.EsporteDAOOracle;
import com.faesa.app.model.Esporte;
import com.faesa.app.model.dto.EventosEsporteDTO;
import com.faesa.app.util.StringUtil;
import java.sql.SQLException;
import java.util.List;

public class EsporteController
{
    private static final EsporteDAO esporteDao = new EsporteDAOOracle();
    
    public static int getTotalRegistros()
    {
        return esporteDao.getTotalRegistros();
    }
    
    public static Esporte findById(int id)
    {
        try {
            return esporteDao.selectById(id);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar buscar esporte por ID: " + e.getMessage());
            return null;
        }
    }
    
    public static Esporte findByNome(String nome)
    {
        try {
            return esporteDao.selectByNome(nome);
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar buscar esporte por nome: " + e.getMessage());
            return null;
        }
    }
    
    public static List<Esporte> listAll()
    {
        try {
            return esporteDao.selectAll();
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar buscar esportes." + e.getMessage());
            return null;
        }
    }
    
    public static List<EventosEsporteDTO> listQtdEventosEsporte()
    {
        try {
            return esporteDao.selectQtdEventosEsporte();
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar buscar esportes." + e.getMessage());
            return null;
        }
    }
    
    /**
     * Se {@code esporte} possuir ID, realiza-se um UPDATE sobre o registro.
     * Caso contrário, um novo registro será inserido.
     * @param esporte {@code Esporte} a ser inserido.
     * @return {@code boolean} se a operação for bem sucedida.
     */
    public static boolean save(Esporte esporte)
    {
        try {
            if(esporte.getId() == null)
            {
                if(esporteDao.selectByNome(esporte.getNome()) != null) {
                    System.out.println("O esporte " + esporte.getNome() + " já foi cadastrado anteriormente.");
                    return false;
                }
                
                return esporteDao.insert(esporte);
            }
            else
            {
                if(StringUtil.isEmpty(esporte.getNome()))
                    if(esporteDao.selectByNome(esporte.getNome()) != null) {
                        System.out.println("O esporte " + esporte.getNome() + " já foi cadastrado anteriormente.");
                        return false;
                    }
                
                return esporteDao.update(esporte);
            }
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar salvar o esporte: " + e.getMessage());
            return false;
        }
    }
    
    public static boolean delete(int id)
    {
        try {
            return esporteDao.delete(id);
        }
        catch(SQLException e) {
            switch(e.getErrorCode()) {
                case 2292:
                    System.out.println("Não é possível remover este esporte pois ele possui eventos vinculados.");
                    break;
            }
            return false;
        }
        catch(Exception e) {
            System.out.println("Erro ao tentar remover o esporte: " + e.getMessage());
            return false;
        }
    }
}
