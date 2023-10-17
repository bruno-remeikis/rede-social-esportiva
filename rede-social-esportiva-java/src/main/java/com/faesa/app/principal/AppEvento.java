package com.faesa.app.principal;

import com.faesa.app.controller.EsporteController;
import com.faesa.app.controller.EventoController;
import com.faesa.app.model.Esporte;
import com.faesa.app.model.Evento;
import com.faesa.app.util.ScannerUtil;
import com.faesa.app.util.StringUtil;
import com.faesa.app.util.tableBuilder.Column;
import com.faesa.app.util.tableBuilder.TableBuilder;
import com.faesa.app.util.tableBuilder.TextAlign;
import java.util.Date;
import java.util.List;

public class AppEvento extends App
{
    protected AppEvento() {
        super("Evento");
    }
    
    private void printRegistros(List<Evento> eventos)
    {
        TableBuilder tb = new TableBuilder(
            "Eventos",
            new Column("ID", 2, null, TextAlign.RIGHT),
            new Column("Evento", 20)
        );
        
        if(eventos == null || eventos.isEmpty())
            tb.newLine()
                .addCell("Nenhum evento cadastrado", 2)
                .endLine();
        else
            for(Evento e: eventos)
                tb.newLine()
                    .addCell(e.getId() + "")
                    .addCell(e.getNome())
                    .endLine();
        
        tb.printTable();
    }

    @Override
    public String inserirRegistros()
    {
        String nome = ScannerUtil.scanString("> Nome do evento: ", 3, 20);
        int idEsporte = scanEsporte();
        Date dt = ScannerUtil.scanDate("> Data do evento (dd/MM/yyyy HH:mm): ");
        String local = ScannerUtil.scanString("> Local: ", 3, 40);
        String desc = ScannerUtil.scanString("> Descrição: ", 0, 255);

        Evento e = new Evento(null, idEsporte, nome, desc, dt, local, null);
        if(EventoController.save(e))
            System.out.println("\nEvento criado com sucesso!");
            
        return "Deseja inserir outro registro?";
    }
    
    private int scanEsporte()
    {
        System.out.println("(Deixe em branco caso queira consultar a lista de esportes)");
        while(true)
        {
            String esporte = ScannerUtil.scanString("> Esporte (nome ou ID): ", true);
            
            // Exibir lista de esportes caso usuário envie em beanco
            if(esporte == null || esporte.isEmpty())
            {
                TableBuilder tb = new TableBuilder(
                    "Esportes",
                    new Column("ID", 2, null, TextAlign.RIGHT),
                    new Column("Nome", 30)
                );
                
                for(Esporte e: EsporteController.listAll())
                    tb.newLine()
                        .addCell(e.getId() + "")
                        .addCell(e.getNome())
                        .endLine();
                
                tb.printTable();
                continue;
            }

            boolean pesquisarPorId = true;
            int id = 0;
            try {
                id = Integer.parseInt(esporte);
                if(id <= 0) {
                    System.out.println("ID inválido.");
                    continue;
                }
            }
            catch(NumberFormatException e) {
                pesquisarPorId = false;
            }

            if(pesquisarPorId) {
                if(EsporteController.findById(id) != null)
                    return id;
                System.out.println("Esporte não encontrado.");
            }
            else {
                Esporte e = EsporteController.findByNome(esporte);
                if(e != null)
                    return e.getId();
                System.out.println("Esporte não encontrado.");
            }
        }
    }

    @Override
    public String removerRegistros()
    {
        List<Evento> eventos = EventoController.listAll();
        if(eventos == null || eventos.isEmpty()) {
            System.out.println("Ainda não há eventos cadastrados.");
            return null;
        }
        
        printRegistros(eventos);

        System.out.println("\n(Deixe em branco caso queira desistir de remover)");
        Integer id = ScannerUtil.scanIntInRange("> ID do evento a ser removido: ", 1, null, true);

        if(id == null)
            return null;

        Evento evento = null;
        for(Evento e: eventos)
            if(e.getId().equals(id)) {
                evento = e;
                break;
            }

        if(evento == null)
            System.out.println("Não existe nenhum evento com este ID.");
        else
        {
            if(ScannerUtil.scanString(
                "Deseja mesmo excluir \"" + evento.getNome() + "\"? (s/N): ",
                false, true, "S", "N"
            ).equalsIgnoreCase("S"))
            {
                if(EventoController.delete(id))
                    System.out.println("Evento removido com sucesso.");
                /*else
                    System.out.println("Não existe nenhum evento com este ID.");*/
            }
        }
        
        return "Deseja remover outro registro?";
    }

    @Override
    public String atualizarRegistros()
    {
        List<Evento> eventos = EventoController.listAll();
        if(eventos == null || eventos.isEmpty()) {
            System.out.println("Ainda não há eventos cadastrados.");
            return null;
        }
        
        printRegistros(eventos);
        
        System.out.println("\n(Deixe em branco caso queira desistir de remover)");
        Integer id = ScannerUtil.scanIntInRange("> ID do evento: ", 1, null, true);
        
        if(id == null)
            return null;

        Evento e = EventoController.findById(id);

        if(e == null) {
            System.out.println("Não foi encontrado nenhum evento com este ID.");
            return "Deseja tentar novamente?";
        }

        System.out.println("(Deixe o campo branco caso queira manter o mesmo valor)");

        e.setNome(ScannerUtil.scanStringOrDefault(
            "> Nome do evento (" + e.getNome() + ")\n: ", e.getNome())
        );
        e.setData(ScannerUtil.scanDateOrDefault(
            "> Data do evento (" + ScannerUtil.formatDate(e.getData()) + ")\n: ", e.getData())
        );
        e.setLocal(ScannerUtil.scanStringOrDefault(
            "> Local (" + e.getLocal() + ")\n: ", e.getLocal()
        ));
        e.setDescricao(ScannerUtil.scanStringOrDefault(
            "> Descrição (" + e.getDescricao() + ")\n: ", e.getDescricao()
        ));

        if(EventoController.save(e))
        {
            System.out.println("\nEvento atualizado com sucesso!");
            System.out.println("- Nome:        " + e.getNome());
            System.out.println("- Data:        " + ScannerUtil.formatDate(e.getData()));
            System.out.println("- Local:       " + e.getLocal());
            System.out.println("- Descrição:   " + e.getDescricao());
            System.out.println("- Inserido em: " + ScannerUtil.formatDate(e.getDtInsert()));
        }
        
        return "Deseja atualizar outro registro?";
    }
}
