package com.faesa.app.util.tableBuilder;

public class Column
{
    public static final int ADJUSTED_WIDTH = 0;
    
    public static TextAlign defaultHeaderAlign = TextAlign.LEFT;
    public static TextAlign defaultContentAlign = TextAlign.LEFT;
    
    private String text;
    private int width;
    private TextAlign headerAlign = defaultHeaderAlign;
    private TextAlign contentAlign = defaultContentAlign;

    public Column(String text)
    {
        this.text = text;
        this.width = text.length();
    }
    
    /**=
     * @param text Titulo da coluna
     * @param width Largura da columa. Se for <= 0, será igual ao tamanho do 'text'
     */
    public Column(String text, int width)
    {
        this(text);
        this.width = width > Column.ADJUSTED_WIDTH ? width : text.length();
    }
    
    public Column(String text, int width, TextAlign headerAlign)
    {
        this(text, width);
        this.headerAlign = headerAlign != null ? headerAlign : Column.defaultHeaderAlign;
    }
    
    public Column(String text, int width, TextAlign headerAlign, TextAlign contentAlign)
    {
        this(text, width, headerAlign);
        this.contentAlign = contentAlign != null ? contentAlign : Column.defaultContentAlign;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public TextAlign getHeaderAlign() {
        return headerAlign;
    }

    public void setHeaderAlign(TextAlign headerAlign) {
        this.headerAlign = headerAlign;
    }

    public TextAlign getContentAlign() {
        return contentAlign;
    }

    public void setContentAlign(TextAlign contentAlign) {
        this.contentAlign = contentAlign;
    }
}
