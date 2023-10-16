package com.faesa.app.principal;

import com.faesa.app.controller.EsporteController;
import com.faesa.app.controller.EventoController;
import com.faesa.app.model.Evento;
import com.faesa.app.model.dto.EventosEsporteDTO;
import com.faesa.app.util.ScannerUtil;
import com.faesa.app.util.StringUtil;
import com.faesa.app.util.tableBuilder.Column;
import com.faesa.app.util.tableBuilder.TableBuilder;
import com.faesa.app.util.tableBuilder.TextAlign;
import java.util.List;
import java.util.Scanner;

public abstract class App
{
    // Atributos estáticos (atributos de classe)
    private static final Scanner scan = new Scanner(System.in);
    private static final App[] apps = {
        new AppEsporte(),
        new AppEvento(),
    };
    
    // Atributos (atributos de entidade)
    private final String menuName;
    
    // Construtor
    protected App(String menuName) {
        this.menuName = menuName;
    }
    
    // Main
    public static void main(String[] args)
    {
        // Inicializar variáveis
        ScannerUtil.init(scan);
        
        // Splash Screen
        exibirSplashScreen();
        
        App app = null;
        while(true)
        {
            // Menú Principal
            int menuOpt = exibirMenu();
            
            if(menuOpt == 5)
                System.exit(0);
            
            // Menú de Entidades
            if(menuOpt != 1)
                app = exibirMenuEntidades();
            
            String loopMsg = null;
            do {
                switch(menuOpt)
                {
                    case 1:
                        loopMsg = relatorios();
                        break;
                    case 2:
                        printTitulo("Criar novo " + app.menuName);
                        loopMsg = app.inserirRegistros();
                        break;
                    case 3:
                        printTitulo("Remover " + app.menuName);
                        loopMsg = app.removerRegistros();
                        break;
                    case 4:
                        printTitulo("Atualizar " + app.menuName);
                        loopMsg = app.atualizarRegistros();
                        break;
                }
            }
            while(
                loopMsg != null &&
                ScannerUtil.scanConfirmation(loopMsg + " (s/N): ")
            );
        }
    }
    
    private static void exibirSplashScreen()
    {
        int qtdEsportes = EsporteController.getTotalRegistros();
        int qtdEventos = EventoController.getTotalRegistros();
        
        System.out.println(
            "###################################\n" +
            "#      REDE SOCIAL ESPORTIVA      #\n" +
            "#                                 #\n" +
            "#  TOTAL DE REGISTROS EXISTENTES  #\n" +
            "#    1 - ESPORTES: " + getQtdRegStr(qtdEsportes) +
            "#    2 - EVENTOS:  " + getQtdRegStr(qtdEventos) +
            "#                                 #\n" +
            "#  CRIADO POR:                    #\n" +
            "#    - Bruno Coutinho Remeikis    #\n" +
            "#    - Isabelly Lopes dos Santos  #\n" +
            "#    - João Henrique Schultz      #\n" +
            "#    - Nicolas Lima               #\n" +
            "#    - Pedro Cassemiro            #\n" + 
            "#  DISCIPLINA: Banco de Dados     #\n" +
            "#              2023/2             #\n" +
            "#  PROFESSOR: Howard Roatti       #\n" +
            "###################################"
        );
    }
    
    private static String getQtdRegStr(int qtdRegistros) {
        return StringUtil.fillLeft(
            (qtdRegistros >= 0
                ? (qtdRegistros + "")
                : "erro"
            ), 4
        ) +
        StringUtil.repeat(' ', 11) +
        "#\n";
    }
    
    private static int exibirMenu()
    {
        System.out.print(
            '\n' +
            "+--------------------------------+\n" +
            "|         MENÚ PRINCIPAL         |\n" +
            "+--------------------------------+\n" +
            "| 1. Relatórios                  |\n" +
            "| 2. Inserir Registros           |\n" +
            "| 3. Remover Registros           |\n" +
            "| 4. Atualizar Registros         |\n" +
            "| 5. Sair                        |\n" +
            "+--------------------------------+"
        );
        
        return ScannerUtil.scanIntInRange(" > Opção: ", 1, 5);
    }
    
    private static App exibirMenuEntidades()
    {
        for(int i = 0; i < apps.length; i++)
            System.out.println(
                "| " + (i + 1) + ". " +
                StringUtil.fillRight(apps[i].menuName, 27) +
                " |"
            );
        System.out.print("+--------------------------------+");
        
        return apps[
            ScannerUtil.scanIntInRange(" > Opção: ", 1, apps.length) - 1
        ];
    }
    
    private static void printTitulo(String titulo)
    {
        String titleLine = "|  " + titulo + "  |";
        String divLine = '+' + StringUtil.repeat('~', titleLine.length() - 2) + '+';
        
        System.out.println();
        System.out.println(divLine);
        System.out.println(titleLine);
        System.out.println(divLine);
    }
    
    //  Métodos de entidade
    
    private static String relatorios()
    {
        System.out.print(
            "| 1. Núm de eventos por esporte  |\n" +
            "| 2. Listar todos os eventos     |\n" +
            "| 3. Voltar                      |\n" +
            "+--------------------------------+"
        );
        
        switch(ScannerUtil.scanIntInRange(" > Opção: ", 1, 3))
        {
            case 1:
                relatorioQtdEventosEsporte();
                break;
            case 2:
                relatorioAllEventos();
                break;
        }
        
        return null;
    }
    
    private static void relatorioQtdEventosEsporte()
    {
        System.out.println();
        TableBuilder tb = new TableBuilder(
            "Quantidade de Eventos por Esporte",
            new Column("ID"),
            new Column("Esporte", 20),
            new Column("Eventos", 7, null, TextAlign.RIGHT)
        );

        List<EventosEsporteDTO> eventosEsporte = EsporteController.listQtdEventosEsporte();
        if(eventosEsporte == null || eventosEsporte.isEmpty())
            tb.newLine()
                .addCell("Ainda não existem esportes registrados.", 3)
                .endLine();
        else
            for(EventosEsporteDTO ee: eventosEsporte)
                tb.newLine()
                    //.addCell(ee.getIdEsporte()+ "")
                    .addCell(StringUtil.fillLeft(ee.getIdEsporte() + "", 2) + "   " + ee.getNomeEsporte(), 2)
                    .addCell(ee.getQtdEventos() + "")
                    .endLine();

        tb.printTable();
    }
    
    private static void relatorioAllEventos()
    {
        System.out.println();
        TableBuilder tb = new TableBuilder(
            "Listar Todos os Eventos",
            new Column("ID"),
            new Column("Nome", 20),
            new Column("Esporte", 20),
            new Column("Data", 16),
            new Column("Local", 10),
            new Column("Descrição", 15)
        );

        List<Evento> eventos = EventoController.listAll();
        if(eventos == null || eventos.isEmpty())
            tb.newLine()
                .addCell("Ainda não existem eventos registrados.", 6)
                .endLine();
        else
            for(Evento e: eventos)
                tb.newLine()
                    .addCell(e.getId() + "")
                    .addCell(e.getNome())
                    .addCell(e.getEsporte().getNome())
                    .addCell(ScannerUtil.formatDate(e.getData()))
                    .addCell(e.getLocal())
                    .addCell(e.getDescricao())
                    .endLine();

        tb.printTable();
    }
    
    // Métodos abstratos
    protected abstract String inserirRegistros();
    protected abstract String removerRegistros();
    protected abstract String atualizarRegistros();
}
