package com.faesa.app.principal;

import com.faesa.app.controller.EventoController;
import com.faesa.app.model.Evento;
import com.faesa.app.util.ScannerUtil;
import com.faesa.app.util.StringUtil;
import java.util.Scanner;
import java.util.Date;
import java.util.List;

public class App
{
    private static final Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        ScannerUtil.init(scan);
        
        exibirSplashScreen();
        
        boolean running = true;
        while(running)
        {
            System.out.println(
                '\n' +
                "+------------------------+\n" +
                "|     MENÚ PRINCIPAL     |\n" +
                "+------------------------+\n" +
                "| 1. Relatórios          |\n" +
                "| 2. Inserir Registros   |\n" +
                "| 3. Remover Registros   |\n" +
                "| 4. Atualizar Registros |\n" +
                "| 5. Sair                |\n" +
                "+------------------------+"
            );
            
            switch(ScannerUtil.scanIntInRange("Opção: ", 1, 5))
            {
                case 1:
                    relatorios();
                    break;
                case 2:
                    inserirRegistros();
                    break;
                case 3:
                    removerRegistros();
                    break;
                case 4:
                    atualizarRegistros();
                    break;
                case 5:
                    running = false;
            }
        }
    }
    
    private static void exibirSplashScreen()
    {        
        
        System.out.println(
            "+---------------------------------+\n" +
            "|      REDE SOCIAL ESPORTIVA      |\n" +
            "|                                 |\n" +
            "|  TOTAL DE REGISTROS EXISTENTES  |\n" +
            "|    1 - ESPORTES: " + getQtdRegStr(0) +
            "|    2 - EVENTOS:  " + getQtdRegStr(EventoController.getTotalRegistros()) +
            "|                                 |\n" +
            "|  CRIADO POR:                    |\n" +
            "|    - Bruno Coutinho Remeikis    |\n" +
            "|    - Isabelly Lopes dos Santos  |\n" +
            "|    - João Henrique Schultz      |\n" +
            "|    - Nicolas Lima               |\n" +
            "|    - Pedro Cassemiro            |\n" + 
            "|  DISCIPLINA: Banco de Dados     |\n" +
            "|              2023/2             |\n" +
            "|  PROFESSOR: Howard Roatti       |\n" +
            "+---------------------------------+"
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
        "|\n";
    }
    
    private static boolean deveContinuar()
    {
        return ScannerUtil.scanString(
            "Deseja inserir um novo registro? (s/N): ",
            true, true, "S", "N"
        ).equalsIgnoreCase("S");
    }
    
    private static void relatorios()
    {
        
    }
    
    private static void inserirRegistros()
    {
        do {
            System.out.println(
                '\n' +
                "+-------------------+\n" +
                "| Criar novo Evento |\n" +
                "+-------------------+"
            );

            String nome = ScannerUtil.scanString("- Nome do Evento: ");
            Date dt = ScannerUtil.scanDate("- Data do Evento (dd/MM/yyyy): ");
            String local = ScannerUtil.scanString("- Local: ");
            String desc = ScannerUtil.scanString("- Descrição: ");

            Evento e = new Evento(null, nome, desc, dt, local, null);
            if(EventoController.save(e))
                System.out.println("\nEvento criado com sucesso!");
        }
        while(deveContinuar());
    }
    
    private static void removerRegistros()
    {
        do {
            System.out.println(
                '\n' +
                "+--------------------+\n" +
                "|   Remover Evento   |\n" +
                "+--------------------+"
            );

            System.out.println("\nEventos:");
            List<Evento> eventos = EventoController.findAll();
            for(Evento e: eventos)
                System.out.println(e.getId() + " - " + e.getNome());

            System.out.println("(Deixe em branco caso queira desistir de remover)");
            Integer id = ScannerUtil.scanIntInRange("\n- ID do Evento a ser removido: ", 1, null, true);
            
            if(id == null)
                return;

            Evento evento = null;
            for(Evento e: eventos)
                if(e.getId() == id) {
                    evento = e;
                    break;
                }

            if(ScannerUtil.scanString(
                "Deseja mesmo excluir \"" + evento.getNome() + "\"? (s/N): ",
                false, true, "S", "N"
            ).equalsIgnoreCase("S"))
            {
                if(EventoController.delete(id))
                    System.out.println("Evento removido com sucesso.");
                else
                    System.out.println("Não existe nenhum evento com este ID.");
            }
        }
        while(deveContinuar());
    }
    
    private static void atualizarRegistros()
    {
        do {
            System.out.println(
                '\n' +
                "+--------------------+\n" +
                "|  Atualizar Evento  |\n" +
                "+--------------------+"
            );

            int id = ScannerUtil.scanIntInRange("- ID do Evento: ", 1, null);

            Evento e = EventoController.selectById(id);

            if(e == null) {
                System.out.println("Não foi encontrado nenhum evento com este ID.");
                return;
            }

            System.out.println("(Deixe o campo branco caso queira manter o mesmo valor)");

            String nome = ScannerUtil.scanString(
                "- Nome do Evento (" + e.getNome() + ")\n: ", true
            );
            if(nome != null)
                e.setNome(nome);

            Date dt = ScannerUtil.scanDate(
                "- Data do Evento (" + ScannerUtil.formatDate(e.getData()) + ")\n: ", true
            );
            if(dt != null)
                e.setData(dt);

            String local = ScannerUtil.scanString(
                "- Local (" + e.getLocal() + ")\n: ", true
            );
            if(local != null)
                e.setLocal(local);

            String desc = ScannerUtil.scanString(
                "- Descrição: (" + e.getDescricao() + ")\n: ", true
            );
            if(desc != null)
                e.setDescricao(desc);

            if(EventoController.save(e))
            {
                System.out.println("\nEvento atualizado com sucesso!");
                System.out.println("");
            }
        }
        while(deveContinuar());
    }
}
