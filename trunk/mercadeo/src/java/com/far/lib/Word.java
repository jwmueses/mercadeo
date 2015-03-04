/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.lib;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVerticalJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;


/**
 *
 * @author Jorge
 */
public class Word {
    private XWPFDocument doc = null;
    private String error = "";
    String fuente = "Arial";
    
    public Word()
    {
        try{
            this.doc = new XWPFDocument();
        }catch(Exception e){
            this.error = e.getMessage();
        }
    }
    
    public void setFuente(String fuente)
    {
        this.fuente = fuente;
    }

    public void setCabeceraPagina(String texto, String alineacion)
    {
        
    }
    
    public void setPiePagina(String texto, String alineacion)
    {
        
    }
    
    public void setTexto(String texto, ParagraphAlignment alineacion, TextAlignment alineacionVertical, 
            int tamanio, boolean negrita, boolean cursiva, UnderlinePatterns subrayado, Borders borde, int posicion)
    {
        XWPFParagraph p1 = this.doc.createParagraph();
        
        p1.setAlignment(alineacion);
        
        p1.setBorderTop(borde);
        p1.setBorderRight(borde);
        p1.setBorderBottom(borde);
        p1.setBorderLeft(borde);
        p1.setBorderBetween(borde);

        p1.setVerticalAlignment(alineacionVertical);
        
        XWPFRun r1 = p1.createRun();
        r1.setBold(negrita);
        r1.setItalic(cursiva);
        r1.setFontSize(tamanio);
        r1.setText(texto);
        r1.setFontFamily(this.fuente);
        r1.setUnderline(subrayado);
        if(posicion>=0){
            r1.setTextPosition(100);
        }
    }
    
    public void setTexto(String texto, ParagraphAlignment alineacion, int tamanio, boolean negrita, boolean cursiva, UnderlinePatterns subrayado)
    {
        this.setTexto(texto, alineacion, TextAlignment.TOP, tamanio, negrita, cursiva, subrayado, Borders.NONE, -1);
    }
    
    public void setTexto(String texto)
    {
        this.setTexto(texto, ParagraphAlignment.LEFT, TextAlignment.TOP, 12, false, false, UnderlinePatterns.NONE, Borders.NONE, -1);
    }
    
    public void setEstiloTabla(XWPFTable tabla, int columnas, int tamanio, boolean cabeceraDiferente)
    {
        //  estilo de la tabla
        CTTblPr tblPr = tabla.getCTTbl().getTblPr();
        CTString styleStr = tblPr.addNewTblStyle();
        styleStr.setVal("StyledTable");

        List<XWPFTableRow> rows = tabla.getRows();
        int rowCt = 0;
        int colCt = 0;
        for (XWPFTableRow row : rows) {
            // get table row properties (trPr)
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            // set row height; units = twentieth of a point, 360 = 0.25"
            CTHeight ht = trPr.addNewTrHeight();
            ht.setVal(BigInteger.valueOf(360));

            // get the cells in this row
            List<XWPFTableCell> cells = row.getTableCells();
            // add content to each cell
            for (XWPFTableCell cell : cells) {
                // get a table cell properties element (tcPr)
                CTTcPr tcpr = cell.getCTTc().addNewTcPr();
                // set vertical alignment to "center"
                CTVerticalJc va = tcpr.addNewVAlign();
                va.setVal(STVerticalJc.CENTER);

                // create cell color element
                CTShd ctshd = tcpr.addNewShd();
                ctshd.setColor("auto");
                ctshd.setVal(STShd.CLEAR);
                if (rowCt == 0 && cabeceraDiferente) {
                    // header row
                    ctshd.setFill("A7BFDE");
                }
                /*else if (rowCt % 2 == 0) {
                    // even row
                    ctshd.setFill("D3DFEE");
                }else {
                    // odd row
                    ctshd.setFill("EDF2F8");
                }*/

                // get 1st paragraph in cell's paragraph list
                XWPFParagraph para = cell.getParagraphs().get(0);
        
                // create a run to contain the content
                XWPFRun rh = para.createRun();
                // style cell as desired
                /*if (colCt == columnas - 1) {
                    // last column is 10pt Courier
                    rh.setFontSize(tamanio);
                    rh.setFontFamily(this.fuente);
                }*/
                rh.setFontSize(tamanio);
                if (rowCt == 0 && cabeceraDiferente) {
                    // header row
                    //rh.setText("header row, col " + colCt);
                    rh.setBold(true);
                    para.setAlignment(ParagraphAlignment.CENTER);
                }else{
                    String dato = para.getParagraphText();
                    if(Cadena.esNumero(dato)){
                        para.setAlignment(ParagraphAlignment.RIGHT);
                    }else{
                        para.setAlignment(ParagraphAlignment.LEFT);
                    }
                }
                    
                /*}else{if (rowCt % 2 == 0) {
                    // even row
                    //rh.setText("row " + rowCt + ", col " + colCt);
                    para.setAlignment(ParagraphAlignment.LEFT);
                } else {
                    // odd row
                    //rh.setText("row " + rowCt + ", col " + colCt);
                    para.setAlignment(ParagraphAlignment.LEFT);
                }*/
                colCt++;
            } // for cell
            colCt = 0;
            rowCt++;
        }
    }
    
    public void setTabla(ResultSet rsDatos, String cab[], int tamanio)
    {
        try {
            rsDatos.last();
            int filas = rsDatos.getRow();
            if(cab!=null){
                filas++;
            }
            rsDatos.beforeFirst();
            ResultSetMetaData mdata = rsDatos.getMetaData();
            int columnas = mdata.getColumnCount();
            
            XWPFTable tabla = this.doc.createTable(filas, columnas);
            tabla.setCellMargins(5, 50, 0, 50);
            //tabla.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
            //tabla.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
            
            int i=0;
            if(cab!=null){
                for(int j=0; j<columnas; j++){
                    tabla.getRow(i).getCell(j).setText(cab[j]);
                }
                i++;
            }
            while(rsDatos.next()){
                for(int j=1; j<=columnas; j++){
                    String dato = rsDatos.getString(j)!=null ? rsDatos.getString(j) : "";
                    tabla.getRow(i).getCell(j-1).setText(dato);
                }
                i++;
            }
            
            this.setEstiloTabla(tabla, columnas, tamanio, (cab!=null?true:false) );
            
    	} catch(Exception e) {
            this.error = e.getMessage();
    	}
    }
    
    public void setTabla(String rsDatos[][], String cab[], int tamanio)
    {
        try {
            int filas = rsDatos.length;
            if(cab!=null){
                filas++;
            }
            int columnas = rsDatos[0].length;
            
            XWPFTable tabla = this.doc.createTable(filas, columnas);
            tabla.setCellMargins(5, 50, 0, 50);
            //tabla.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
            //tabla.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
            
            int i=0;
            if(cab!=null){
                for(int j=0; j<columnas; j++){
                    tabla.getRow(i).getCell(j).setText(cab[j]);
                }
                i++;
            }
            for(int x=0; x<filas-1; x++){
                for(int j=0; j<columnas; j++){
                    tabla.getRow(i).getCell(j).setText(rsDatos[x][j]);
                }
                i++;
            }
            
            this.setEstiloTabla(tabla, columnas, tamanio, (cab!=null?true:false) );
            
    	} catch(Exception e) {
            this.error = e.getMessage();
    	}
    }
    
    public void setTabla(String rsDatos[], String cab[], int tamanio)
    {
        try {
            int filas = rsDatos.length;
            if(cab!=null){
                filas++;
            }
            int columnas = 1;
            
            XWPFTable tabla = this.doc.createTable(filas, columnas);
            tabla.setCellMargins(5, 50, 0, 50);
            //tabla.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
            //tabla.setInsideVBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, "");
            
            int i=0;
            if(cab!=null){
                for(int j=0; j<columnas; j++){
                    tabla.getRow(i).getCell(j).setText(cab[j]);
                }
                i++;
            }
            for(int x=0; x<filas-1; x++){
                tabla.getRow(i).getCell(0).setText(rsDatos[x]);
                i++;
            }
            
            this.setEstiloTabla(tabla, columnas, tamanio, (cab!=null?true:false) );
            
    	} catch(Exception e) {
            this.error = e.getMessage();
    	}
    }
    
    public void setImagen(String src, String escala, String margTop, String margDer, String margPie, String margIzq)
    {
       
    }
    
    public void setLista(ResultSet rsDatos)
    {
        
    }
    
    public void setSalto(String tipo)
    {
        
    }
    
    public void grabar(String rutaArchivo)
    {
        try{
            FileOutputStream out = new FileOutputStream(rutaArchivo);
            this.doc.write(out);
            out.close();
        }catch(Exception e){
            this.error = e.getMessage();
        }
    }
    
    public void enviarRespuesta(HttpServletResponse response, String nombre)
    {
        try{
            FileOutputStream out = new FileOutputStream(System.getProperty("java.io.tmpdir") + "/" + nombre);
            this.doc.write(out);
            out.close();
            
            FileInputStream a = new FileInputStream(System.getProperty("java.io.tmpdir") + "/" + nombre);
            while(a.available() > 0){
                response.getWriter().append((char)a.read());
            }
        }catch(Exception e){
            this.error = e.getMessage();
        }
    }
    
    public String getError()
    {
        return this.error;
    }
    
}
