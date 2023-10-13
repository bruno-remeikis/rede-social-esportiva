package com.faesa.app.util;

public class StringUtil
{
    public static String repeat(char c, int size)
    {
        String str = "";
        for(int i = 0; i < size; i++)
            str += c;

        return str;
    }

    public static String replaceFrom(String originalStr, String snippet, int fromIndex)
    {
        char[] str = originalStr.toCharArray();

        int jFrom = fromIndex;

        for(int i = 0; i < snippet.length() && jFrom < originalStr.length(); i++)
        {
            str[jFrom] = snippet.charAt(i);
            jFrom++;
        }

        return new String(str);
    }
    
    public static String replaceFrom(String originalStr, String snippet, int fromIndex, int limit)
    {
        char[] str = originalStr.toCharArray();

        int jFrom = fromIndex;

        for(int i = 0; i < snippet.length() /*&& jFrom < originalStr.length()*/ && i < limit; i++)
        {
            str[jFrom] = snippet.charAt(i);
            jFrom++;
        }

        return new String(str);
    }
    
    public static String fillLeft(String str, int returnSize)
    {
        return fillLeft(str, returnSize, ' ');
    }
    
    public static String fillLeft(String str, int returnSize, char filler)
    {
        int initialLength = str.length();
        for(int i = 0; i < returnSize - initialLength; i++)
            str = filler + str;
        return str;
    }
    
    public static String fillRight(String str, int returnSize)
    {
        return fillRight(str, returnSize, ' ');
    }
    
    public static String fillRight(String str, int returnSize, char filler)
    {
        int initialLength = str.length();
        for(int i = 0; i < returnSize - initialLength; i++)
            str += filler;
        return str;
    }
}