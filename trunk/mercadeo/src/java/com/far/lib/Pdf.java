/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.lib;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
public class Pdf extends PdfPageEventHelper {
    
    private DecimalFormat formatoNumero;
    private String ruc = "";
    private String titulo = "";
    private String subtitulo = "";
    private String direccion = "";
    private String rep_pie = "";
    private boolean vertical =true;
    
    public Pdf(boolean vertical, String titulo, String ruc, String subtitulo, String direccion, String rep_pie)
    {
        this.titulo = titulo;
        this.ruc = ruc;
        this.subtitulo = subtitulo;
        this.direccion = direccion;
        this.vertical = vertical;
        this.rep_pie = rep_pie;
        
        this.formatoNumero = new DecimalFormat("#############.##");
        DecimalFormatSymbols dfs = this.formatoNumero.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        this.formatoNumero.setDecimalFormatSymbols(dfs);
    }
    
    public void onStartPage(PdfWriter writer, Document document)
    {
        Pdf.setEncabezado(writer, document, this.subtitulo);
    }
    
    public void onEndPage(PdfWriter writer, Document document)
    {
        Pdf.setPie(writer, document, this.rep_pie);
    }
    
    public static PdfPCell setCeldaPDF(PdfPTable tabla, int alineacion, int borde, int colspan)
    {        
        PdfPCell celda = new PdfPCell(tabla);        
        celda.setHorizontalAlignment(alineacion);
        celda.setColspan(colspan);
        celda.setVerticalAlignment(Element.ALIGN_TOP);
        celda.setBorderColor(Color.LIGHT_GRAY);
        celda.setBorderWidth(borde);
        return celda;
    }
    public static PdfPCell setCeldaPDF(String texto, int tipo, int tamanio, int estilo, int alineacion, int borde)
    {        
        PdfPCell celda = new PdfPCell(new Paragraph(texto, new Font(tipo, tamanio, estilo)));        
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_TOP);
        celda.setBorderColor(Color.LIGHT_GRAY);
        celda.setBorderWidth(borde);
        return celda;
    }
    public static PdfPCell setCeldaPDF(String texto, int tipo, int tamanio, int estilo, int alineacion, int borde, Color color)
    {        
        PdfPCell celda = new PdfPCell(new Paragraph(texto, new Font(tipo, tamanio, estilo)));        
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_TOP);
        celda.setBorderColor(Color.LIGHT_GRAY);
        celda.setBorderWidth(borde);
        celda.setBackgroundColor(color);
        return celda;
    }
    public static PdfPCell setCeldaPDF(String texto, int tipo, int tamanio, int estilo, int alineacion, int borde, int padding, int colspan)
    {        
        PdfPCell celda = new PdfPCell(new Paragraph(texto, new Font(tipo, tamanio, estilo)));        
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(padding);
        celda.setColspan(colspan);
        celda.setVerticalAlignment(Element.ALIGN_TOP);
        celda.setBorderColor(Color.LIGHT_GRAY);
        celda.setBorderWidth(borde);
        return celda;
    }
    public static PdfPCell setCeldaPDF(String texto, int tipo, int tamanio, int estilo, int alineacion, int borde, Color color, int padding, int colspan)
    {        
        PdfPCell celda = new PdfPCell(new Paragraph(texto, new Font(tipo, tamanio, estilo)));        
        celda.setHorizontalAlignment(alineacion);
        celda.setPadding(padding);
        celda.setColspan(colspan);
        celda.setVerticalAlignment(Element.ALIGN_TOP);
        celda.setBorderColor(Color.LIGHT_GRAY);
        celda.setBorderWidth(borde);
        celda.setBackgroundColor(color);
        return celda;
    }
    public static PdfPCell setFilaBlanco(int colSpan, int alto)
    {
        PdfPCell celda = new PdfPCell(new Paragraph(" ", new Font(Font.HELVETICA, alto, Font.NORMAL)));
        celda.setBorderWidth(0);
        celda.setPadding(0);
        celda.setColspan(colSpan);
        return celda;
    }
    public static PdfPTable setCabecera(String titulo, String ruc, String subtitulo, String direccion)
    {
        PdfPTable tbl_encab = new PdfPTable(1);
        tbl_encab.addCell(Pdf.setCeldaPDF(titulo, Font.HELVETICA, 13, Font.BOLD, Element.ALIGN_CENTER, 0));
        tbl_encab.addCell(Pdf.setCeldaPDF(direccion, Font.HELVETICA, 11, Font.NORMAL, Element.ALIGN_CENTER, 0));
        if(ruc.compareTo("")!=0){
            tbl_encab.addCell(Pdf.setCeldaPDF("RUC: "+ruc, Font.HELVETICA, 11, Font.NORMAL, Element.ALIGN_CENTER, 0));
        }
        tbl_encab.addCell(Pdf.setCeldaPDF(subtitulo, Font.HELVETICA, 10, Font.NORMAL, Element.ALIGN_CENTER, 0));
        
        PdfPTable encabezado = new PdfPTable(1);
        encabezado.addCell(Pdf.setCeldaPDF(tbl_encab, Element.ALIGN_CENTER, 0, 1));
        
        encabezado.addCell(Pdf.setFilaBlanco(2, 6));

        encabezado.addCell(Pdf.setCeldaPDF("Fecha de impresión: "+Fecha.getFecha("SQL"), Font.HELVETICA, 10, Font.NORMAL, Element.ALIGN_LEFT, 0, 0, 2));

        encabezado.addCell(Pdf.setFilaBlanco(2, 4));
        
        return encabezado;
    }
    public static void setEncabezado(PdfWriter writer, Document document, String texto)
    {
        if(writer.getPageNumber() > 1){
            try{
                PdfPTable encabezado = new PdfPTable(1);
                encabezado.setTotalWidth(document.right() - document.left()-120);
                encabezado.addCell(Pdf.setCeldaPDF(texto, Font.HELVETICA, 9, Font.BOLD, Element.ALIGN_LEFT, 0));
                encabezado.writeSelectedRows(0, -1, 60, document.top()+25, writer.getDirectContent());
                
                PdfContentByte cb = writer.getDirectContent();
                cb.setLineWidth(2);
                cb.moveTo(60, document.top()+10);
                cb.lineTo(document.right() - document.left()-58, document.top()+10);
            }catch(Exception e) {
                throw new ExceptionConverter(e);
            }
        }
    }
    public static PdfPTable setCabeceraTabla(String [] titulo, float [] ancho)
    {
        PdfPTable encabezado = new PdfPTable(ancho);
        encabezado.setSpacingBefore(5f);
        for(int i=0; i<titulo.length; i++){
            encabezado.addCell(Pdf.setCeldaPDF(titulo[i], Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1, Color.cyan));  //  Color.CYAN
        }
        return encabezado;
    }
    
    /**
     alineacion (ALIGN_LEFT=0, ALIGN_CENTER=1, ALIGN_RIGHT=2, ALIGN_JUSTIFIED=3, ALIGN_TOP=4, ALIGN_MIDDLE=5, ALIGN_BOTTOM=6, ALIGN_BASELINE=7, ALIGN_JUSTIFIED_ALL=8
    */
    public void lista(HttpServletResponse response, ResultSet registros, String [] cabTabla, float [] anchoTabla, int [] alineacion)
    {
        /* inicio PDF */     
        Rectangle orientacion = (this.vertical) ? PageSize.A4 : PageSize.A4.rotate();
        Document document = new Document(orientacion);// paso 1
        document.setMargins(-40, -60, 50, 80); /*Izquierda, derecha, tope, pie */
        try{
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream()); // paso 2
            writer.setPageEvent(new Pdf(this.vertical, this.titulo, this.ruc, this.subtitulo, this.direccion, this.rep_pie));
            
            
            document.open(); // paso 3
            
            /* todo el cuerpo del doc es el paso 4 */
            
            document.add(Pdf.setCabecera(this.titulo, this.ruc, this.subtitulo, this.direccion));
            
            document.add(Pdf.setCabeceraTabla(cabTabla, anchoTabla));
            
            ResultSetMetaData mdata = registros.getMetaData();      
            int numCols = mdata.getColumnCount();
            PdfPTable tbl_det = new PdfPTable(anchoTabla);
            int num = 1;
            try{
                while(registros.next()){
                    tbl_det.addCell(Pdf.setCeldaPDF(String.valueOf(num), Font.HELVETICA, 6, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    for(int i=1; i<=numCols; i++){
                        tbl_det.addCell(Pdf.setCeldaPDF(((registros.getString(i)!=null) ? registros.getString(i) : ""), Font.HELVETICA, 6, Font.NORMAL, alineacion[i], 1));
                    }
                    num++;
                }
                registros.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            
            document.add(tbl_det);
            
            
        }catch(IllegalStateException ie){
            ie.printStackTrace();
        }catch(DocumentException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        document.close(); // paso 5
        /* fin PDF */        
    }
    
    /**
     alineacion (ALIGN_LEFT=0, ALIGN_CENTER=1, ALIGN_RIGHT=2, ALIGN_JUSTIFIED=3, ALIGN_TOP=4, ALIGN_MIDDLE=5, ALIGN_BOTTOM=6, ALIGN_BASELINE=7, ALIGN_JUSTIFIED_ALL=8
    */
    public void lista(HttpServletResponse response, String maestro, ResultSet registros, String [] cabTabla, float [] anchoTabla, int [] alineacion)
    {
        /* inicio PDF */     
        Rectangle orientacion = (this.vertical) ? PageSize.A4 : PageSize.A4.rotate();
        Document document = new Document(orientacion);// paso 1
        document.setMargins(0,-40,50,80); /*Izquierda, derecha, tope, pie */
        try{
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream()); // paso 2
            writer.setPageEvent(new Pdf(this.vertical, this.titulo, this.ruc, this.subtitulo, this.direccion, this.rep_pie));
            
            
            document.open(); // paso 3
            
            /* todo el cuerpo del doc es el paso 4 */
            
            document.add(Pdf.setCabecera(this.titulo, this.ruc, this.subtitulo, this.direccion));
            
            PdfPTable tbl_cab = new PdfPTable(1);
            tbl_cab.addCell(Pdf.setCeldaPDF(maestro, Font.HELVETICA, 10, Font.NORMAL, Element.ALIGN_LEFT, 0));
            document.add(tbl_cab);
            
            document.add(Pdf.setCabeceraTabla(cabTabla, anchoTabla));
            
            ResultSetMetaData mdata = registros.getMetaData();      
            int numCols = mdata.getColumnCount();
            PdfPTable tbl_det = new PdfPTable(anchoTabla);
            int num = 1;
            try{
                while(registros.next()){
                    tbl_det.addCell(Pdf.setCeldaPDF(String.valueOf(num), Font.HELVETICA, 6, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    for(int i=1; i<=numCols; i++){
                        tbl_det.addCell(Pdf.setCeldaPDF(((registros.getString(i)!=null) ? registros.getString(i) : ""), Font.HELVETICA, 6, Font.NORMAL, alineacion[i], 1));
                    }
                    num++;
                }
                registros.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            
            document.add(tbl_det);
            
            
        }catch(IllegalStateException ie){
            ie.printStackTrace();
        }catch(DocumentException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        document.close(); // paso 5
        /* fin PDF */        
    }
    
    public void PlanMercadeo(HttpServletResponse response, ResultSet rs, BaseDatos objDB)
    {
        /* inicio PDF */     
        Rectangle orientacion = (this.vertical) ? PageSize.A4 : PageSize.A4.rotate();
        Document document = new Document(orientacion);// paso 1
        document.setMargins(0,-20,50,80); /*Izquierda, derecha, tope, pie */
        try{
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream()); // paso 2
            writer.setPageEvent(new Pdf(this.vertical, this.titulo, this.ruc, this.subtitulo, this.direccion, this.rep_pie));
            
            
            document.open(); // paso 3
            
            /* todo el cuerpo del doc es el paso 4 */
            
            document.add(Pdf.setCabecera(this.titulo, this.ruc, this.subtitulo, this.direccion));
                
            document.add(new Paragraph(" "));
            
            String id = "-1";
            String sec_tipo_plan = "";
            String id_tipo_plan="";
            String tipo_plan="";
            String plan_mercadeo="";
            String fecha_creacion="";
            String fecha_aprobacion="";
            String fecha_ini="";
            String fecha_fin="";
            String promedio_ventas="";
            String fecha_ini_averificar="";
            String fecha_fin_averificar="";
            String premio="";
            String mecanica_tipo = "";
            String mecanica = "";
            String aplica_prom_p_v = "";
            String ope_fecha_ini = "";
            String ope_fecha_fin = "";
            String adjuntos="";
            String tipo_alcance="";
            String tipo_alcance_de="";
            try{
                if(rs.next()){
                    id = rs.getString("id_plan_mercadeo")!=null ? rs.getString("id_plan_mercadeo") : "-1";
                    sec_tipo_plan = rs.getString("sec_tipo_plan")!=null ? rs.getString("sec_tipo_plan") : "-1";
                    id_tipo_plan = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
                    tipo_plan = rs.getString("tipo_plan")!=null ? rs.getString("tipo_plan") : "";
                    plan_mercadeo = rs.getString("plan_mercadeo")!=null ? rs.getString("plan_mercadeo") : "";
                    fecha_creacion = rs.getString("fecha_creacion")!=null ? rs.getString("fecha_creacion") : "";
                    fecha_aprobacion = rs.getString("fecha_aprobacion")!=null ? rs.getString("fecha_aprobacion") : "";
                    tipo_alcance = rs.getString("tipo_alcance")!=null ? rs.getString("tipo_alcance") : "";
                    tipo_alcance_de = rs.getString("tipo_alcance_de")!=null ? rs.getString("tipo_alcance_de") : "";
                    fecha_ini = rs.getString("fecha_ini")!=null ? rs.getString("fecha_ini") : "";
                    fecha_fin = rs.getString("fecha_fin")!=null ? rs.getString("fecha_fin") : "";
                    promedio_ventas = rs.getString("promedio_ventas")!=null ? rs.getString("promedio_ventas") : "";
                    fecha_ini_averificar = rs.getString("fecha_ini_averificar")!=null ? rs.getString("fecha_ini_averificar") : "";
                    fecha_fin_averificar = rs.getString("fecha_fin_averificar")!=null ? rs.getString("fecha_fin_averificar") : "";
                    premio = rs.getString("premio")!=null ? rs.getString("premio") : "";
                    mecanica_tipo = rs.getString("mecanica_tipo")!=null ? rs.getString("mecanica_tipo") : "";
                    mecanica = rs.getString("mecanica")!=null ? rs.getString("mecanica") : "";
                    aplica_prom_p_v = rs.getString("aplica_prom_p_v")!=null ? rs.getString("aplica_prom_p_v") : "";
                    ope_fecha_ini = rs.getString("ope_fecha_ini")!=null ? rs.getString("ope_fecha_ini") : "";
                    ope_fecha_fin = rs.getString("ope_fecha_fin")!=null ? rs.getString("ope_fecha_fin") : "";
                    adjuntos = rs.getString("adjuntos")!=null ? rs.getString("adjuntos") : "";
                    rs.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            ResultSet rsFarmacias = objDB.consulta("select * from tbl_plan_mercadeo_farmacia with (nolock) where id_plan_mercadeo="+id+" order by nombre");
            ResultSet rsProductosFiltro = objDB.consulta("select * from tbl_plan_mercadeo_producto_filtro with (nolock) where id_plan_mercadeo="+id+" order by id_plan_mercadeo_producto_filtro");
            ResultSet rsProductos = objDB.consulta("select * from tbl_plan_mercadeo_producto with (nolock) where id_plan_mercadeo="+id+" order by descripcion");

            String alcance = "Farmacia";
            if(tipo_alcance.compareTo("d")==0){
                alcance = "Distribución";
            }
            
            String tipo_alcance_de1 = "Mercadeo";
            if(tipo_alcance_de.compareTo("c")==0){
                tipo_alcance_de1 = "Mercadeo";
            }else if(tipo_alcance_de.compareTo("i")==0){
                       tipo_alcance_de1 = "Inauguración";
            }
            
            PdfPTable tblPM = new PdfPTable(new float[]{20, 80});
            tblPM.addCell(Pdf.setCeldaPDF("DATOS DEL PLAN DE MERCADEO", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_CENTER, 0, 3, 2));
            
            tblPM.addCell(Pdf.setCeldaPDF("No.: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            tblPM.addCell(Pdf.setCeldaPDF(sec_tipo_plan, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            
            tblPM.addCell(Pdf.setCeldaPDF("TIPO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            tblPM.addCell(Pdf.setCeldaPDF(tipo_plan, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            
            tblPM.addCell(Pdf.setCeldaPDF("NOMBRE: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            tblPM.addCell(Pdf.setCeldaPDF(plan_mercadeo, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            
            tblPM.addCell(Pdf.setCeldaPDF("FECHA DE CREACIÓN: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            tblPM.addCell(Pdf.setCeldaPDF(fecha_creacion, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            
            tblPM.addCell(Pdf.setCeldaPDF("FECHA DE APROBACIÓN: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            tblPM.addCell(Pdf.setCeldaPDF(fecha_aprobacion, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            
            tblPM.addCell(Pdf.setCeldaPDF("TIPO DE MECÁNICA: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            tblPM.addCell(Pdf.setCeldaPDF((mecanica_tipo.compareTo("t")==0) ? "Texto" : "Operaciones", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            
            if(mecanica_tipo.compareTo("t")==0){
                tblPM.addCell(Pdf.setCeldaPDF("MECÁNICA: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(mecanica, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            }
            if(mecanica_tipo.compareTo("o")==0){
                tblPM.addCell(Pdf.setCeldaPDF("APLICA PARA: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF((aplica_prom_p_v.compareTo("p")==0) ? "Promociones" : "Convenios", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                try{
                    PdfPTable tblFAR1 = new PdfPTable(new float[]{40,60});
                    tblFAR1.addCell(Pdf.setCeldaPDF("ARCHIVO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    tblFAR1.addCell(Pdf.setCeldaPDF("DESCRIPCION", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    String archivo="";
                    String descripcion="";
                    ResultSet rsAdj = objDB.consulta("select * from tbl_plan_mercadeo_adjunto with (nolock) where id_plan_mercadeo="+id+" order by id_plan_mercadeo_adjunto");
                    while(rsAdj.next()){
                        archivo = rsAdj.getString("archivo")!=null ? rsAdj.getString("archivo") : "";
                        descripcion = rsAdj.getString("descripcion")!=null ? rsAdj.getString("descripcion") : "";
                        tblFAR1.addCell(Pdf.setCeldaPDF(archivo, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                        tblFAR1.addCell(Pdf.setCeldaPDF(descripcion, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                    }
                    tblPM.addCell(Pdf.setCeldaPDF(tblFAR1, Element.ALIGN_LEFT, 0, 2));
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                tblPM.addCell(Pdf.setCeldaPDF("FECHA DE INICIO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(ope_fecha_ini, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                tblPM.addCell(Pdf.setCeldaPDF("FECHA DE TÉRMINO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(ope_fecha_fin, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
            }
            
            
            tblPM.addCell(Pdf.setCeldaPDF("ALCANCE: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            tblPM.addCell(Pdf.setCeldaPDF(alcance, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            
            if(tipo_alcance.compareTo("f")==0){
                tblPM.addCell(Pdf.setCeldaPDF("TIPO DE ALCANCE: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(tipo_alcance_de1, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                tblPM.addCell(Pdf.setCeldaPDF("PROMEDIO DE VENTAS DESDE: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(fecha_ini, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                tblPM.addCell(Pdf.setCeldaPDF("PROMEDIO DE VENTAS HASTA: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(fecha_fin, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                if(tipo_alcance_de.compareTo("i")!=0){
                    tblPM.addCell(Pdf.setCeldaPDF("RANGO DE FECHAS PARA EVALUAR LA PROYECCION DE VENTAS", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0, 3, 2));
                    
                    tblPM.addCell(Pdf.setCeldaPDF( ((tipo_alcance_de.compareTo("i")==0) ? "PERÍODO A EVALUAR" : "")+" DESDE: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                    tblPM.addCell(Pdf.setCeldaPDF(fecha_ini_averificar, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                    
                    tblPM.addCell(Pdf.setCeldaPDF( ((tipo_alcance_de.compareTo("i")==0) ? "PERÍODO A EVALUAR" : "")+" HASTA: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                    tblPM.addCell(Pdf.setCeldaPDF(fecha_fin_averificar, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                    
                    try{
                        tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                        tblPM.addCell(Pdf.setCeldaPDF("PRODUCTOS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                        
                        PdfPTable tblPRDF = new PdfPTable(3);
                        tblPRDF.addCell(Pdf.setCeldaPDF("CATEGORIA", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        tblPRDF.addCell(Pdf.setCeldaPDF("LABORATORIO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        tblPRDF.addCell(Pdf.setCeldaPDF("LINEA", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        String desc_nivel="";
                        String desc_clase="";
                        String desc_linea="";
                        while(rsProductosFiltro.next()){
                            desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                            desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                            desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                            tblPRDF.addCell(Pdf.setCeldaPDF(desc_nivel, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                            tblPRDF.addCell(Pdf.setCeldaPDF(desc_clase, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                            tblPRDF.addCell(Pdf.setCeldaPDF(desc_linea, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                        }
                        tblPM.addCell(Pdf.setCeldaPDF(tblPRDF, Element.ALIGN_LEFT, 0, 2));
                        
                        PdfPTable tblPRD = new PdfPTable(1);
                        String descripcion="";
                        tblPRD.addCell(Pdf.setCeldaPDF("PRODUCTO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        while(rsProductos.next()){
                            descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                            tblPRD.addCell(Pdf.setCeldaPDF(descripcion, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                        }
                        tblPM.addCell(Pdf.setCeldaPDF(tblPRD, Element.ALIGN_LEFT, 0, 2));
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                    try{
                        tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                        tblPM.addCell(Pdf.setCeldaPDF("FARMACIAS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                        PdfPTable tblFAR = new PdfPTable(new float[]{100,20,20,20,20});
                        tblFAR.addCell(Pdf.setCeldaPDF("FARMACIA", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        tblFAR.addCell(Pdf.setCeldaPDF("PROM. VENTAS", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        tblFAR.addCell(Pdf.setCeldaPDF("%", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        tblFAR.addCell(Pdf.setCeldaPDF("PROY. VENTAS", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        tblFAR.addCell(Pdf.setCeldaPDF("% DISTRIBUCION GASTO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                        String nombre="";
                        String p_ventas="0";
                        String p_crecimiento="0";
                        String proy_ventas="0";
                        String p_gasto="0";
                        while(rsFarmacias.next()){
                            nombre = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                            p_ventas = rsFarmacias.getString("p_ventas")!=null ? rsFarmacias.getString("p_ventas") : "0";
                            p_crecimiento = rsFarmacias.getString("p_crecimiento")!=null ? rsFarmacias.getString("p_crecimiento") : "0";
                            proy_ventas = rsFarmacias.getString("proy_ventas")!=null ? rsFarmacias.getString("proy_ventas") : "0";
                            p_gasto = rsFarmacias.getString("p_gasto")!=null ? rsFarmacias.getString("p_gasto") : "0";
                            tblFAR.addCell(Pdf.setCeldaPDF(nombre, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                            tblFAR.addCell(Pdf.setCeldaPDF(p_ventas, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                            tblFAR.addCell(Pdf.setCeldaPDF(p_crecimiento, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                            tblFAR.addCell(Pdf.setCeldaPDF(proy_ventas, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                            tblFAR.addCell(Pdf.setCeldaPDF(p_gasto, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                        }
                        //tblFAR.addCell(Pdf.setCeldaPDF(promedio_ventas, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1, 3, 4));
                        tblPM.addCell(Pdf.setCeldaPDF(tblFAR, Element.ALIGN_LEFT, 0, 2));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                
                if(tipo_alcance_de.compareTo("i")==0){
                    tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                    tblPM.addCell(Pdf.setCeldaPDF("FARMACIAS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                    PdfPTable tblFAR1 = new PdfPTable(new float[]{80,20});
                    tblFAR1.addCell(Pdf.setCeldaPDF("NOMBRE", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    tblFAR1.addCell(Pdf.setCeldaPDF("PROY. VENTAS", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    tblFAR1.addCell(Pdf.setCeldaPDF("% DISTRIBUCION GASTO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    String nombre="";
                    String proy_ventas="0";
                    String p_gasto="0";
                    rsFarmacias.beforeFirst();
                    while(rsFarmacias.next()){
                        nombre = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                        proy_ventas = rsFarmacias.getString("proy_ventas")!=null ? rsFarmacias.getString("proy_ventas") : "0";
                        p_gasto = rsFarmacias.getString("p_gasto")!=null ? rsFarmacias.getString("p_gasto") : "0";
                        tblFAR1.addCell(Pdf.setCeldaPDF(nombre, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                        tblFAR1.addCell(Pdf.setCeldaPDF(proy_ventas, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                        tblFAR1.addCell(Pdf.setCeldaPDF(p_gasto, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                    }
                    tblPM.addCell(Pdf.setCeldaPDF(tblFAR1, Element.ALIGN_LEFT, 0, 2));
                }
                
            }
            
            
            if(tipo_alcance.compareTo("d")==0){
                tblPM.addCell(Pdf.setCeldaPDF("CONSULTA DE FACTURAS: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF("desde "+fecha_ini+"   hasta "+fecha_fin, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                tblPM.addCell(Pdf.setCeldaPDF("VALOR A COMPARAR LA SUMATORIA DE LAS FACTURAS: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(promedio_ventas, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                tblPM.addCell(Pdf.setCeldaPDF("PORCENTAJE O PREMIO A ENTREGARSE: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                tblPM.addCell(Pdf.setCeldaPDF(premio, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                
                try{
                    tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                    tblPM.addCell(Pdf.setCeldaPDF("VENDEDORES", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                    PdfPTable tblVEN = new PdfPTable(1);
                    String nombre_vendedor = "";
                    ResultSet rsVen = objDB.consulta("select * from tbl_plan_mercadeo_vendedor with (nolock) where id_tipo_plan='"+id+"' order by nombre_vendedor");
                    tblVEN.addCell(Pdf.setCeldaPDF("VENDEDOR", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    while(rsVen.next()){
                        nombre_vendedor = rsVen.getString("nombre_vendedor")!=null ? rsVen.getString("nombre_vendedor") : "";
                        tblVEN.addCell(Pdf.setCeldaPDF(nombre_vendedor, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                    }
                    tblPM.addCell(Pdf.setCeldaPDF(tblVEN, Element.ALIGN_LEFT, 0, 2));
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                
                
                try{
                    tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                    tblPM.addCell(Pdf.setCeldaPDF("PRODUCTOS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));

                    PdfPTable tblPRDF = new PdfPTable(3);
                    tblPRDF.addCell(Pdf.setCeldaPDF("CATEGORIA", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    tblPRDF.addCell(Pdf.setCeldaPDF("LABORATORIO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    tblPRDF.addCell(Pdf.setCeldaPDF("LINEA", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    String desc_nivel="";
                    String desc_clase="";
                    String desc_linea="";
                    while(rsProductosFiltro.next()){
                        desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                        desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                        desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                        tblPRDF.addCell(Pdf.setCeldaPDF(desc_nivel, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                        tblPRDF.addCell(Pdf.setCeldaPDF(desc_clase, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                        tblPRDF.addCell(Pdf.setCeldaPDF(desc_linea, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                    }
                    tblPM.addCell(Pdf.setCeldaPDF(tblPRDF, Element.ALIGN_LEFT, 0, 2));

                    PdfPTable tblPRD = new PdfPTable(1);
                    String descripcion="";
                    tblPRD.addCell(Pdf.setCeldaPDF("PRODUCTO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    while(rsProductos.next()){
                        descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                        tblPRD.addCell(Pdf.setCeldaPDF(descripcion, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                    }
                    tblPM.addCell(Pdf.setCeldaPDF(tblPRD, Element.ALIGN_LEFT, 0, 2));

                }catch(Exception e){
                    e.printStackTrace();
                }
                
                
                try{
                    tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                    tblPM.addCell(Pdf.setCeldaPDF("CLIENTES", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                    PdfPTable tblCLI = new PdfPTable(1);
                    String nombre_comercial = "";
                    ResultSet rsCli = objDB.consulta("select * from tbl_plan_mercadeo_cliente with (nolock) where id_tipo_plan='"+id+"' order by nombre_comercial");
                    tblCLI.addCell(Pdf.setCeldaPDF("CLIENTE", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                    while(rsCli.next()){
                        nombre_comercial = rsCli.getString("nombre_comercial")!=null ? rsCli.getString("nombre_comercial") : "";
                        tblCLI.addCell(Pdf.setCeldaPDF(nombre_comercial, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                    }
                    tblPM.addCell(Pdf.setCeldaPDF(tblCLI, Element.ALIGN_LEFT, 0, 2));
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                // adjuntos
                if(adjuntos.compareTo("")!=0){
                    tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                    tblPM.addCell(Pdf.setCeldaPDF("ARCHIVOS ADJUNTOS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                    String vecAdj[] = adjuntos.split("|");
                    for(int i=0; i<vecAdj.length; i++){
                        tblPM.addCell(Pdf.setCeldaPDF(vecAdj[i], Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 1, 3, 2));
                    }
                }
                
            }
            
            tblPM.addCell(Pdf.setFilaBlanco(2, 12));
            tblPM.addCell(Pdf.setCeldaPDF("AUSPICIOS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
            
            try{
                tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                tblPM.addCell(Pdf.setCeldaPDF("LABORATORIOS ESTRATÉGICOS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                PdfPTable tblLABS = new PdfPTable(new float[]{70,15,15});
                tblLABS.addCell(Pdf.setCeldaPDF("LABORATORIO ESTRATÉGICO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                tblLABS.addCell(Pdf.setCeldaPDF("PRES. DISP.", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                tblLABS.addCell(Pdf.setCeldaPDF("MONTO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));

                ResultSet rsLabs = objDB.consulta("select L.id_laboratorio, nombre_comercial, monto, LP.saldo, presupuesto, id_tipo_plan "
                        + "from (tbl_laboratorio as L with (nolock) inner join tbl_labortorio_tipo_plan_presupuesto as LP with (nolock) on L.id_laboratorio=LP.id_laboratorio) "
                        + "where (L.id_laboratorio in (select id_laboratorio from tbl_plan_mercadeo_laboratorio where id_plan_mercadeo="+id+") "
                        + "or (monto>0 and estado=1) ) and id_tipo_plan='"+id_tipo_plan+"' order by nombre_comercial;");
                ResultSet rsAus = objDB.consulta("select id_laboratorio, monto from tbl_plan_mercadeo_laboratorio with (nolock) where id_plan_mercadeo="+id+" order by id_laboratorio;");
                String laboratorios[][] = Matriz.ResultSetAMatriz(rsAus);
                String id_laboratorio = "";
                String nombre_comercial = "";
                float presupuesto = 0;
                float monto = 0;
                int pos=0;
                while(rsLabs.next()){
                    id_laboratorio = rsLabs.getString("id_laboratorio")!=null ? rsLabs.getString("id_laboratorio") : "";
                    nombre_comercial = rsLabs.getString("nombre_comercial")!=null ? rsLabs.getString("nombre_comercial") : "";
                    presupuesto = rsLabs.getString("presupuesto")!=null ? rsLabs.getFloat("presupuesto") : 0;
                    pos = Matriz.enMatriz(laboratorios, id_laboratorio, 0);
                    monto = pos>=0 ? Float.parseFloat(laboratorios[pos][1]) : 0;
                    presupuesto += monto; 
                    if(monto>0){
                        tblLABS.addCell(Pdf.setCeldaPDF(nombre_comercial, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                        tblLABS.addCell(Pdf.setCeldaPDF(String.valueOf(presupuesto), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                        tblLABS.addCell(Pdf.setCeldaPDF(String.valueOf(monto), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                    }
                }
                tblPM.addCell(Pdf.setCeldaPDF(tblLABS, Element.ALIGN_LEFT, 0, 2));
            }catch(Exception e){
                e.printStackTrace();
            }
            
            try{
                tblPM.addCell(Pdf.setFilaBlanco(2, 3));
                tblPM.addCell(Pdf.setCeldaPDF("LABORATORIOS NO ESTRATÉGICOS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                PdfPTable tblPRO1 = new PdfPTable(new float[]{70,15,15});
                tblPRO1.addCell(Pdf.setCeldaPDF("PROVEEDOR", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                tblPRO1.addCell(Pdf.setCeldaPDF("MONTO", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                tblPRO1.addCell(Pdf.setCeldaPDF("# FORM.", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                String nombre_comercial = "";
                String monto = "";
                String num_formulario = "";
                ResultSet rsProv = objDB.consulta("select numero_idproveedor, nombre_comercial, num_formulario, monto, CONVERT(VARCHAR,fecha_registro, 103) as fecha_registro, confirmado, eliminado "
                + "from tbl_plan_mercadeo_proveedor with (nolock) where id_plan_mercadeo="+id+" and eliminado=0 order by eliminado, nombre_comercial;");
                while(rsProv.next()){
                    nombre_comercial = rsProv.getString("nombre_comercial")!=null ? rsProv.getString("nombre_comercial") : "";
                    num_formulario = rsProv.getString("num_formulario")!=null ? rsProv.getString("num_formulario") : "";
                    monto = rsProv.getString("monto")!=null ? rsProv.getString("monto") : "";
                    tblPRO1.addCell(Pdf.setCeldaPDF(nombre_comercial, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                    tblPRO1.addCell(Pdf.setCeldaPDF(monto, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                    tblPRO1.addCell(Pdf.setCeldaPDF(num_formulario, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_RIGHT, 1));
                }
                tblPM.addCell(Pdf.setCeldaPDF(tblPRO1, Element.ALIGN_LEFT, 0, 2));
            }catch(Exception e){
                e.printStackTrace();
            }

            tblPM.addCell(Pdf.setFilaBlanco(2, 12));
            tblPM.addCell(Pdf.setCeldaPDF("ESTRATEGIAS", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
            tblPM.addCell(Pdf.setFilaBlanco(2, 3));
            
            document.add(tblPM);
            
            
            
            
            try{
                PdfPTable tblESTR = new PdfPTable(new float[]{15,30,55});
                String id_estrategia = "-1";
                String estrategia = "";
                String concepto = "";
                String tactica = "";
                ResultSet rsEstr = objDB.consulta("select * from tbl_estrategia with (nolock) where id_plan_mercadeo="+id+" order by estrategia");
                while(rsEstr.next()){
                    id_estrategia = rsEstr.getString("id_estrategia")!=null ? rsEstr.getString("id_estrategia") : "-1";
                    estrategia = rsEstr.getString("estrategia")!=null ? rsEstr.getString("estrategia") : "";
                    concepto = rsEstr.getString("concepto")!=null ? rsEstr.getString("concepto") : "";
                    tactica = rsEstr.getString("tactica")!=null ? rsEstr.getString("tactica") : "";
                    
                    tblESTR.addCell(Pdf.setFilaBlanco(3, 3));
                    
                    tblESTR.addCell(Pdf.setCeldaPDF("ESTRATEGIA: ", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0));
                    tblESTR.addCell(Pdf.setCeldaPDF(estrategia, Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 2));
                    
                    tblESTR.addCell(Pdf.setCeldaPDF("CONCEPTO: ", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0));
                    tblESTR.addCell(Pdf.setCeldaPDF(concepto, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0, 3, 2));
                    
                    tblESTR.addCell(Pdf.setCeldaPDF("TÁCTICA: ", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0));
                    tblESTR.addCell(Pdf.setCeldaPDF(tactica, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0, 3, 2));
                    
                    tblESTR.addCell(Pdf.setCeldaPDF("ACTIVIDADES ", Font.HELVETICA, 8, Font.BOLD, Element.ALIGN_LEFT, 0, 3, 3));
                    
                    ResultSet rsAct = objDB.consulta("select *, CONVERT(VARCHAR, fecha_ini, 103) as sql_fecha_ini, CONVERT(VARCHAR, fecha_fin, 103) as sql_fecha_fin "
                                                    + "from tbl_actividad with (nolock) where id_estrategia="+id_estrategia);
                    
                    while(rsAct.next()){
                        String id_actividad = rsAct.getString("id_actividad")!=null ? rsAct.getString("id_actividad") : "";
                        
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("ACTIVIDAD: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("actividad")!=null ? rsAct.getString("actividad") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("FECHA DE INICIO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("sql_fecha_ini")!=null ? rsAct.getString("sql_fecha_ini") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("FECHA DE TÉRMINO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("sql_fecha_fin")!=null ? rsAct.getString("sql_fecha_fin") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        String responsable_seg = rsAct.getString("responsable_seg")!=null ? rsAct.getString("responsable_seg") : "";
                        if(responsable_seg.compareTo("")!=0){
                            tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                            tblESTR.addCell(Pdf.setCeldaPDF("RESPONSABLE DE SEGUIMIENTO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                            tblESTR.addCell(Pdf.setCeldaPDF(responsable_seg, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        }
                        
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("RESPONSABLE DE EJECUCIÓN: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("responsable_eje")!=null ? rsAct.getString("responsable_eje") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        String tipo_presupuesto="Bienes";
                        String pre_tipo = rsAct.getString("pre_tipo")!=null ? rsAct.getString("pre_tipo") : "";
                        if(pre_tipo.compareTo("s")==0){
                            tipo_presupuesto = "Servicio";
                        }else if(pre_tipo.compareTo("a")==0){
                                  tipo_presupuesto = "Activo fijo";
                        }else if(pre_tipo.compareTo("p")==0){
                                  tipo_presupuesto = "Premio";
                        }else if(pre_tipo.compareTo("i")==0){
                                  tipo_presupuesto = "Inventario";
                        }
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("TIPO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF(tipo_presupuesto, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                            
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("PROVEEDOR SUGERIDO: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("pre_proveedor")!=null ? rsAct.getString("pre_proveedor") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("CANTIDAD: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("pre_cantidad")!=null ? rsAct.getString("pre_cantidad") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("PRECIO UNITARIO INCLUIDO IVA: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("pre_p_u")!=null ? rsAct.getString("pre_p_u") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF("TOTAL: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        tblESTR.addCell(Pdf.setCeldaPDF((rsAct.getString("pre_total")!=null ? rsAct.getString("pre_total") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                        
                        try{
                            String pre_id_tipo_plan_cuenta = rsAct.getString("pre_id_tipo_plan_cuenta")!=null ? rsAct.getString("pre_id_tipo_plan_cuenta") : "";
                            ResultSet rsCuenta = objDB.consulta("select descripcion from tbl_tipo_plan_cuenta with (nolock) where id_tipo_plan_cuenta="+pre_id_tipo_plan_cuenta);
                            if(rsCuenta.next()){
                                tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                                tblESTR.addCell(Pdf.setCeldaPDF("CUENTA CONTABLE: ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                                tblESTR.addCell(Pdf.setCeldaPDF(rsCuenta.getString("descripcion")!=null ? rsCuenta.getString("descripcion") : "", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                                rsCuenta.close();
                            }   
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                        
                        try{
                            ResultSet rsCrono = objDB.consulta("select CONVERT(VARCHAR, fecha, 103) as fecha_sql, descripcion from tbl_actividad_cronograma with (nolock) where id_actividad="+id_actividad+" order by fecha");
                            if(objDB.getFilas(rsCrono)>0){
                                tblESTR.addCell(Pdf.setFilaBlanco(3, 3));
                                tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                                tblESTR.addCell(Pdf.setCeldaPDF("CRONOGRAMA DE ACTISICIONES:", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0, 3, 2));

                                PdfPTable tblCLI = new PdfPTable(new float[]{15,75});
                                tblCLI.addCell(Pdf.setCeldaPDF("FECHA", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                                tblCLI.addCell(Pdf.setCeldaPDF("DESCRIPCIÓN", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_CENTER, 1));
                                while(rsCrono.next()){
                                    tblCLI.addCell(Pdf.setCeldaPDF((rsCrono.getString("fecha_sql")!=null ? rsCrono.getString("fecha_sql") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                                    tblCLI.addCell(Pdf.setCeldaPDF((rsCrono.getString("descripcion")!=null ? rsCrono.getString("descripcion") : ""), Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 1));
                                }
                                tblESTR.addCell(Pdf.setCeldaPDF(" ", Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
                                tblESTR.addCell(Pdf.setCeldaPDF(tblCLI, Element.ALIGN_LEFT, 0, 2));
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                        tblESTR.addCell(Pdf.setFilaBlanco(3, 12));
                    }
                    
                    tblESTR.addCell(Pdf.setFilaBlanco(3, 12));
                }
                
                document.add(tblESTR);
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }catch(IllegalStateException ie){
            ie.printStackTrace();
        }catch(DocumentException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        document.close(); // paso 5
        /* fin PDF */        
    }
    
    public void setMensaje(HttpServletResponse response, String mensaje)
    {
        /* inicio PDF */     
        Rectangle orientacion = (this.vertical) ? PageSize.A4 : PageSize.A4.rotate();
        Document document = new Document(orientacion);// paso 1
        document.setMargins(0,-20,50,80); /*Izquierda, derecha, tope, pie */
        try{
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream()); // paso 2
            writer.setPageEvent(new Pdf(this.vertical, this.titulo, this.ruc, this.subtitulo, this.direccion, this.rep_pie));
            
            
            document.open(); // paso 3
            
            /* todo el cuerpo del doc es el paso 4 */
            
            document.add(Pdf.setCabecera(this.titulo, this.ruc, this.subtitulo, this.direccion));
            
            document.add(new Paragraph(" "));
            
            PdfPTable tbl = new PdfPTable(1);
            tbl.addCell(Pdf.setCeldaPDF(mensaje, Font.HELVETICA, 8, Font.NORMAL, Element.ALIGN_LEFT, 0));
            document.add(tbl);
            
        }catch(IllegalStateException ie){
            ie.printStackTrace();
        }catch(DocumentException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        document.close(); // paso 5
        /* fin PDF */        
    }
    
    public static void setPie(PdfWriter writer, Document document, String rep_pie)
    {
        try{
            PdfContentByte cb = writer.getDirectContent();
            //cb.setLineWidth(2);
            //cb.moveTo(60, document.bottomMargin()-5);
            //cb.lineTo(document.right() - document.left()-70, document.bottomMargin()-5);
            
            PdfPTable pie = new PdfPTable(1);
            pie.setTotalWidth(document.right() - document.left()-120);
            pie.addCell(Pdf.setCeldaPDF(rep_pie, Font.HELVETICA, 9, Font.BOLD, Element.ALIGN_CENTER, 0));
            pie.addCell(Pdf.setCeldaPDF("Pág " + String.valueOf(writer.getPageNumber()), Font.HELVETICA, 9, Font.BOLD, Element.ALIGN_RIGHT, 0));
            pie.addCell(Pdf.setCeldaPDF("Reporte diseñado por: Jorge Mueses Cevallos.      Móvil: 0995204832     mail:jorge_mueses@yahoo.com", Font.HELVETICA, 5, Font.BOLD, Element.ALIGN_LEFT, 0));
            pie.writeSelectedRows(0, -1, 60, document.bottomMargin()-10, cb);
        }catch(Exception e) {
            throw new ExceptionConverter(e);
        }
    }
    
}
