package com.faesa.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public abstract class ScannerUtil
{
    private static Scanner scan;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    public static final void init(Scanner scan) {
        ScannerUtil.scan = scan;
    }
    
    public static final void setDateFormat(String dateFormat) {
        sdf = new SimpleDateFormat(dateFormat);
    }
    
    public static final String formatDate(Date date) {
        return sdf.format(date);
    }
    
    public static SimpleDateFormat getSimpleDateFormat() {
        return sdf;
    }

    public static final int scanIntInRange(String msg, Integer min, Integer max)
    {
        return scanIntInRange(msg, min, max, false);
    }
            
    /**
     * Pede uma op��o ao usu�rio at� que o valor inserido seja v�lido (1 a 3)
     */
    public static final Integer scanIntInRange(String msg, Integer min, Integer max, boolean canBeEmpty)
    {
        while(true)
        {
            if(msg != null)
                System.out.print(msg);
            
            try {
                String opcao = scan.nextLine().trim();
                
                if(canBeEmpty && opcao.isEmpty())
                    return null;
                
                int opcaoInt = Integer.parseInt(opcao);
                if((min == null || opcaoInt >= min)
                && (max == null || opcaoInt <= max))
                    return opcaoInt;
            }
            catch(NumberFormatException ex) {}

            System.out.println("Opção inválida.");
        }
    }
    
    public static final String scanString(String msg) {
        return scanString(msg, false);
    }   
    
    public static final String scanString(String msg, boolean canBeEmpty)
    {
        if(canBeEmpty)
        {
            if(msg != null)
                System.out.print(msg);
            
            return scan.nextLine().trim();
        }
        else
            while(true)
            {
                if(msg != null)
                    System.out.print(msg);

                String input = scan.nextLine().trim();

                if(!input.isEmpty())
                    return input;

                System.out.println("Campo não pode estar vazio");
            }
    }
    
    public static final String scanString(String msg, Integer min, Integer max)
    {
        while(true)
        {
            if(msg != null)
                System.out.print(msg);
            
            String input = scan.nextLine().trim();
            
            if((min == null || input.length() >= min)
            && (max == null || input.length() <= max))
                return input;
            
            System.out.println(
                "Campo inválido. Tamanho" +
                (min != null ? (" mínimo: " + min + ".") : "") +
                (max != null ? (" máximo: " + max + ".") : "")
            );
        }
    }
    
    public static final String scanString(String msg, boolean canBeEmpty, boolean ignoreCase, String... options)
    {
        while(true)
        {
            if(msg != null)
                System.out.print(msg);
            
            String input = scan.nextLine().trim();
            
            if(!input.isEmpty() || canBeEmpty)
                return input;
            
            if(ignoreCase) {
                for(String opt: options)
                    if(input.equalsIgnoreCase(opt))
                        return input;
            }
            else {
                for(String opt: options)
                    if(input.equals(opt))
                        return input;
            }
            
            System.out.println("Opção inválida.");
        }
    }
    
    public static String scanStringOrDefault(String msg, String defaultValue)
    {
        if(msg != null)
            System.out.print(msg);

        String input = scan.nextLine().trim();

        return input.isEmpty()
            ? defaultValue
            : input;
    }
    
    public static boolean scanConfirmation(String msg)
    {
        return scanConfirmation(msg, "S", "N", true, true);
    }
    
    public static boolean scanConfirmation(String msg, String yesStr, String noStr, boolean canBeEmpty, boolean ignoreCase)
    {
        return scanString(
            msg, canBeEmpty, ignoreCase, yesStr, noStr
        ).equalsIgnoreCase(yesStr);
    }
    
    public static final Date scanDate(String msg) {
        return scanDate(msg, false, null);
    }
    
    public static final Date scanDate(String msg, boolean canBeEmpty) {
        return scanDate(msg, canBeEmpty, null);
    }
    
    /**
     * Transforma texto inserido em data.
     * <br>Formato: "dd/MM/yyyy".
     * <br>Exemplo:  21/06/2023.
     */
    public static final Date scanDate(String msg, boolean canBeEmpty, String dateFormat)
    {
        while(true)
        {
            if(msg != null)
                System.out.print(msg);
            
            String input = scan.nextLine().trim();
            
            if(canBeEmpty && input.isEmpty())
                return null;
            
            SimpleDateFormat formato = dateFormat == null
                ? ScannerUtil.sdf
                : new SimpleDateFormat(dateFormat);
            
            try {
                return formato.parse(input);
            }
            catch(ParseException ex) {
                System.out.println("Data com formato inválido.");
            }
        }
    }
    
    public static Date scanDateOrDefault(String msg, Date defaultValue)
    {
        return scanDateOrDefault(msg, defaultValue, null);
    }
    
    public static Date scanDateOrDefault(String msg, Date defaultValue, String dateFormat)
    {
        while(true)
        {
            if(msg != null)
                System.out.print(msg);
            
            String input = scan.nextLine().trim();
            
            if(input.isEmpty())
                return defaultValue;
            
            SimpleDateFormat formato = dateFormat == null
                ? ScannerUtil.sdf
                : new SimpleDateFormat(dateFormat);
            
            try {
                return formato.parse(input);
            }
            catch(ParseException ex) {
                System.out.println("Data com formato inválido.");
            }
        }
    }
}
