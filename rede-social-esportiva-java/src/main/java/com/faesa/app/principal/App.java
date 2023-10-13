package com.faesa.app.principal;

import com.faesa.app.controller.EventoController;
import com.faesa.app.model.Evento;
import com.faesa.app.util.ScannerUtil;
import com.faesa.app.util.StringUtil;
import java.util.Scanner;
import java.util.Date;

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
                    break;
                case 2:
                    inserirRegistros();
                    break;
                case 3:
                    removerRegistros();
                    break;
                case 4:
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
            ),
            4
        ) + "           |\n";
    }
    
    private static void inserirRegistros()
    {
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

        Evento e = new Evento(null, nome, desc, dt, local);
        if(EventoController.insert(e))
            System.out.println("\nEvento criado com sucesso!");
    }
    
    private static void removerRegistros()
    {
        System.out.println(
            '\n' +
            "+--------------------+\n" +
            "|   Remover Evento   |\n" +
            "+--------------------+"
        );
        
        int id = ScannerUtil.scanIntInRange("- ID do Evento: ", 1, null);
        
        if(EventoController.delete(id))
            System.out.println("Evento removido com sucesso.");
        else
            System.out.println("Não existe nenhum evento com este ID.");
    }
}
