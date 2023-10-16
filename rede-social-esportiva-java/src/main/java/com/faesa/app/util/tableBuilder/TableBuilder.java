package com.faesa.app.util.tableBuilder;

import java.util.ArrayList;
import java.util.List;
import com.faesa.app.util.StringUtil;

public class TableBuilder
{
    public static char
        defaultBorderHorizontalChar = '-',
        defaultBorderVerticalChar = '|',
        defaultBorderCornerChar = '+';
    public static Character
        defaultDivDecoration = defaultBorderCornerChar;
    
    public static TextAlign defaultTitleAlign = TextAlign.CENTER;
    
    private char
        borderHorizontalChar = defaultBorderHorizontalChar,
        borderVerticalChar = defaultBorderVerticalChar,
        borderCornerChar = defaultBorderCornerChar;
    public static Character
        divDecoration = defaultDivDecoration;
    
    //private List<String[]> lines;
    private String title = null;
    private TextAlign titleAlign = defaultTitleAlign;
    private final Column[] columns;
    private final List<Line> lines;
    
    public TableBuilder(String... colTitles)
    {
        columns = new Column[colTitles.length];
        for(int i = 0; i < colTitles.length; i++)
            columns[i] = new Column(colTitles[i]);
        
        this.lines = new ArrayList();
    }
    
    public TableBuilder(Column... columns)
    {
        this.columns = columns;
        this.lines = new ArrayList();
    }
    
    public TableBuilder(String title, Column... columns)
    {
        this(columns);
        this.title = title;
    }
    
    public TableBuilder(String title, TextAlign titleAlign, Column... columns)
    {
        this(title, columns);
        this.titleAlign = titleAlign;
    }
    
    // GETTERS E SETTERS

    public char getBorderHorizontalChar() {
        return borderHorizontalChar;
    }

    public TableBuilder setBorderHorizontalChar(char borderHorizontalChar) {
        this.borderHorizontalChar = borderHorizontalChar;
        return this;
    }

    public char getBorderVerticalChar() {
        return borderVerticalChar;
    }

    public TableBuilder setBorderVerticalChar(char borderVerticalChar) {
        this.borderVerticalChar = borderVerticalChar;
        return this;
    }

    public char getBorderCornerChar() {
        return borderCornerChar;
    }

    public TableBuilder setBorderCornerChar(char borderCornerChar) {
        this.borderCornerChar = borderCornerChar;
        return this;
    }

    public static Character getDivDecoration() {
        return divDecoration;
    }

    /**
     * Caractere a ser colocado nas linhas divisórias, no enontro entre cada divisão horizontal com as divisórias.
     * @param divDecoration char - Caractere a ser exibido; null - Não exibe nada em especial.
     */
    public TableBuilder setDivDecoration(Character divDecoration) {
        this.divDecoration = divDecoration;
        return this;
    }

    public String getTitle() {
        return title;
    }
    
    /**
     * @param title Titulo da tabela. null para não exibir titulo.
     * @return TableBuilder - builder
     */
    public TableBuilder setTitle(String title) {
        this.title = title;
        return this;
    }
    
    /**
     * @param title Titulo da tabela. null para não exibir titulo.
     * @param titleAlign Alinhamento do titulo
     * @return TableBuilder
     */
    public TableBuilder setTitle(String title, TextAlign titleAlign) {
        this.title = title;
        this.titleAlign = titleAlign;
        return this;
    }
    
    public TextAlign getTitleAlign() {
        return titleAlign;
    }
    
    /**
     * @param titleAlign Alinhamento do titulo
     * @return TableBuilder
     */
    public TableBuilder setTitleAlign(TextAlign titleAlign) {
        this.titleAlign = titleAlign;
        return this;
    }
    
    // MÉTODOS DE CONSTRUÇÃO
    
    private void addLine(LineBuilder line)
    {
        lines.add(line);
    }
    
    public LineBuilder newLine()
    {
        return new LineBuilder(this);
    }
    
    public TableBuilder addDivider()
    {
        lines.add(new LineDivider(this));
        return this;
    }
    
    /**
     * Constrói e printa tabela completa
     * @return String - Tabela completa
     */
    public String printTable()
    {
        String table = generateTable();
        System.out.println(table);
        return table;
    }
    
    /**
     * Constrói a tabela completa
     * @return String - Tabela completa
     */
    public String generateTable()
    {
        int tableWidth = 1;
        for(Column c: columns)
            tableWidth += c.getWidth() + 3;
        
        String horizontalBorder = borderCornerChar + StringUtil.repeat(borderHorizontalChar, tableWidth - 2) + borderCornerChar;
        String horizontalDecoratedBorder = horizontalBorder;
        String blankLine = borderVerticalChar + StringUtil.repeat(' ', tableWidth - 2) + borderVerticalChar;
        String emptyLine = blankLine;
        
        if(divDecoration != null)
        {
            int a = 2;
            for(int i = 0; i < columns.length - 1; i++)
            {
                a += columns[i].getWidth() + 1;
                horizontalDecoratedBorder = StringUtil.replaceFrom(horizontalDecoratedBorder, divDecoration + "", a);
                a += 2;
            }
        }
        
        int textIndexes[] = new int[columns.length];
        
        // Constrói linha vazia
        int x = 2;
        for(int i = 0; i < columns.length; i++)
        {
            textIndexes[i] = x;
            
            if(i < columns.length - 1)
            {
                x += columns[i].getWidth() + 1;
                emptyLine = StringUtil.replaceFrom(emptyLine, borderVerticalChar + "", x);
                x += 2;
            }
        }
        
        // Constrói titulo
        String titleLine = null;
        if(title != null)
        {
            int blankLineSize = blankLine.length() - 4;
            
            titleLine = StringUtil.replaceFrom(
                blankLine,
                title,
                2 + getAlignedPosition(
                    blankLineSize,
                    title.length(),
                    titleAlign
                ),
                blankLineSize
            );
        }
        
        // Constrói cabeçalho (titulo das colunas)
        String headerLine = emptyLine;
        for(int i = 0; i < columns.length; i++)
        {
            Column col = columns[i];
            int xAligned = textIndexes[i] + getAlignedPosition(
                col.getWidth(),
                col.getText().length(),
                col.getHeaderAlign()
            );
            
            headerLine = StringUtil.replaceFrom(headerLine, col.getText(), xAligned, col.getWidth());
        }
        
        String table =
            (titleLine != null ? (
                horizontalBorder + '\n' +
                titleLine + '\n'
            ) : "") +
            horizontalDecoratedBorder + '\n' +
            headerLine + '\n' +
            horizontalDecoratedBorder + '\n';
        
        // Constrói linhas
        for(Line line: lines) 
        {
            String row = null;
            
            if(line instanceof LineBuilder)
            {
                row = emptyLine;
                LineBuilder lb = (LineBuilder) line;
                
                int j = 0;
                for(int i = 0; j < lb.cells.length; i++)
                {
                    Column col = columns[i];
                    Cell cell = lb.cells[i];

                    // Para caso a célula ocupe mais que uma coluna (colspan > 1)
                    int colWidth = col.getWidth();
                    for(int k = 1; k < cell.colspan; k++)
                    {
                        // Atualiza largura da célula unificada
                        colWidth += columns[i + k].getWidth() + 3;

                        // Remove divisor da célula usada na unificação
                        int indexDivisor = textIndexes[j + k] - 2;
                        row = StringUtil.replaceFrom(row, " ", indexDivisor);
                    }

                    int xAligned = textIndexes[j] + getAlignedPosition(
                        colWidth,
                        cell.text.length(),
                        col.getContentAlign()
                    );

                    row = StringUtil.replaceFrom(row, cell.text, xAligned, colWidth);
                    j += cell.colspan;
                }
            }
            else if(line instanceof LineDivider)
            {
                row = horizontalDecoratedBorder;
            }
            
            table += row + '\n';
        }
        table += horizontalDecoratedBorder;
        
        return table;
    }
    
    /**
     * Calcula o deslocamento de um texto de acordo com seu alinhamento.
     * @param cellWidth Espaço disponível para o texto
     * @param textLength Tamanho do texto
     * @param textAlign Alinhamento do texto
     * @return int >= 0 referente ao deslocamento necessário para que o texto
     * se adeque ao alinhamento especificado
     */
    private int getAlignedPosition(int cellWidth, int textLength, TextAlign textAlign)
    {
        int xAligned = 0;
        
        if(textAlign.equals(TextAlign.CENTER))
            xAligned = (int) ((cellWidth / 2) - Math.ceil(textLength / 2 /*2.0*/));
        else if(textAlign.equals(TextAlign.RIGHT))
            xAligned = cellWidth - textLength;
        
        return xAligned < 0
            ? 0
            : xAligned;
    }
    
    
    
    
    
    public class Line
    {
        protected final TableBuilder tableBuilder;

        private Line(TableBuilder tableBuilder) {
            this.tableBuilder = tableBuilder;
        }
    }
    
    
    
    
    
    public class LineDivider extends Line
    {
        private LineDivider(TableBuilder tableBuilder) {
            super(tableBuilder);
        }
    }
    
    
    
    
    
    public class LineBuilder extends Line
    {
        private final Cell[] cells;
        
        private int i = 0;

        private LineBuilder(TableBuilder tableBuilder)
        {
            super(tableBuilder);
            this.cells = new Cell[tableBuilder.columns.length];
        }

        /**
         * Adiciona célula de forma ordenada (i de 0 a n, sendo n o número de colunas da tabela)
         * @param text Conteúdo da célula
         * @return O próprio LineBuilder
         */
        public LineBuilder addCell(String text)
        {
            cells[i++] = new Cell(text);
            return this;
        }
        
        /**
         * Adiciona célula de forma ordenada (i de 0 a n, sendo n o número de colunas da tabela)
         * @param text Conteúdo da célula
         * @param colspan Número de colunas que a célula ocupará
         * @return O próprio LineBuilder
         */
        public LineBuilder addCell(String text, int colspan)
        {
            cells[i++] = new Cell(text, colspan);
            return this;
        }
        
        /**
         * Define o conteúdo da célula especificada em colIndex
         * @param colIndex index da célula (de 0 a n, sendo n o número de colunas da tabela)
         * @param text Conteúdo da célula
         * @return 
         */
        public LineBuilder setCell(int colIndex, String text)
        {
            cells[colIndex] = new Cell(text);
            return this;
        }
        
        /**
         * Define o conteúdo da célula especificada em colIndex
         * @param colIndex index da célula (de 0 a n, sendo n o número de colunas da tabela)
         * @param text Conteúdo da célula
         * @param colspan Número de colunas que a célula ocupará
         * @return 
         */
        public LineBuilder setCell(int colIndex, String text, int colspan)
        {
            cells[colIndex] = new Cell(text, colspan);
            return this;
        }

        /**
         * Fecha linha atual e inicia nova linha
         * @return LineBuilder - Nova linha.
         */
        public LineBuilder newLine()
        {
            endLine();
            return tableBuilder.newLine();
        }
        
        /**
         * Define o fim da linha
         * @return O TableBuilder que está sendo trabalhado
         */
        public TableBuilder endLine()
        {
            tableBuilder.addLine(this);
            return tableBuilder;
        }
    }
    
    
    
    
    
    public class Cell
    {
        private String text;
        private int colspan = 1;
        
        private Cell(String text)
        {
            this.text = text;
        }
        
        private Cell(String text, int colspan)
        {
            this.text = text;
            this.colspan = colspan > 0 ? colspan : 1;
        }
    }
}
