package com.faesa.app.principal;

import util.ScannerUtil;
import util.StringUtil;

public class App
{
    public static void main(String[] args)
    {
        int qtdEsportes = 0;
        int qtdEventos = 0;
        
        System.out.println(
            "+---------------------------------+\n" +
            "|      REDE SOCIAL ESPORTIVA      |\n" +
            "|                                 |\n" +
            "|  TOTAL DE REGISTROS EXISTENTES  |\n" +
            "|    1 - ESPORTES: " +
            StringUtil.fillLeft(qtdEsportes + "", 4) + "           |\n" +
            "|    2 - EVENTOS:  " +
            StringUtil.fillLeft(qtdEventos + "", 4) + "           |\n" +
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
            "+---------------------------------+\n"
        );
        
        boolean running = true;
        while(running)
        {
            System.out.println(
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
                    break;
                case 4:
                    break;
                case 5:
                    running = false;
            }
        }
    }
    
    private static void inserirRegistros()
    {
        
    }
}
