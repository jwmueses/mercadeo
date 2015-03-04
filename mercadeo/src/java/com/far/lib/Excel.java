/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.lib;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
public class Excel {
    String xls = "";
    String subtitulo = "";
    int columnas = 1;
    int filas = 1;
    
    public Excel(String subt){
        this.subtitulo = subt;
    }
            
    public void setEncabezado()
    {
        this.xls+="<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>\n";
        this.xls+="<?mso-application progid=\"Excel.Sheet\"?>\n";
        this.xls+="<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\n";
        this.xls+=" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n";
        this.xls+=" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n";
        this.xls+=" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\n";
        this.xls+=" xmlns:html=\"http://www.w3.org/TR/REC-html40\">\n";
        this.xls+=" <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">\n";
        this.xls+="  <Author>Jorge Mueses</Author>\n";
        this.xls+="  <LastAuthor>Jorge Mueses</LastAuthor>\n";
        this.xls+="  <Created>2010-03-05T18:37:44Z</Created>\n";
        this.xls+="  <LastSaved>2010-03-05T18:40:43Z</LastSaved>\n";
        this.xls+="  <Company>YAKUSOFT</Company>\n";
        this.xls+="  <Version>15.00</Version>\n";
        this.xls+=" </DocumentProperties>\n";
        this.xls+=" <OfficeDocumentSettings xmlns=\"urn:schemas-microsoft-com:office:office\">\n";
        this.xls+=" <AllowPNG/>\n";
        this.xls+=" </OfficeDocumentSettings>\n";
        this.xls+=" <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\n";
        this.xls+="  <WindowHeight>10200</WindowHeight>\n";
        this.xls+="  <WindowWidth>16455</WindowWidth>\n";
        this.xls+="  <WindowTopX>240</WindowTopX>\n";
        this.xls+="  <WindowTopY>30</WindowTopY>\n";
        this.xls+="  <ProtectStructure>False</ProtectStructure>\n";
        this.xls+="  <ProtectWindows>False</ProtectWindows>\n";
        this.xls+=" </ExcelWorkbook>\n";
        this.xls+=" <Styles>\n";
        this.xls+="  <Style ss:ID=\"Default\" ss:Name=\"Normal\">\n";
        this.xls+="   <Alignment ss:Vertical=\"Bottom\"/>\n";
        this.xls+="   <Borders/>\n";
        this.xls+="   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#000000\"/>\n";
        this.xls+="   <Interior/>\n";
        this.xls+="   <NumberFormat/>\n";
        this.xls+="   <Protection/>\n";
        this.xls+="  </Style>\n";
        this.xls+="  <Style ss:ID=\"s62\">\n";
        this.xls+="   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Bottom\"/>\n";
        this.xls+="   <Font ss:FontName=\"Calibri\" x:Family=\"Swiss\" ss:Size=\"11\" ss:Color=\"#000000\"\n";
        this.xls+="    ss:Bold=\"1\"/>\n";
        this.xls+="  </Style>\n";
        this.xls+=" </Styles>\n";
        this.xls+=" <Worksheet ss:Name=\"Hoja1\">\n";
        this.xls+="  <Table ss:ExpandedColumnCount=\"<<COLUMNAS>>\" ss:ExpandedRowCount=\"<<FILAS>>\" x:FullColumns=\"1\"\n";
        this.xls+="   x:FullRows=\"1\" ss:DefaultColumnWidth=\"60\" ss:DefaultRowHeight=\"15\">\n";
        this.xls+="   <Column ss:AutoFitWidth=\"0\" ss:Width=\"49.5\"/>\n";
    }
    
    public void setCabeceraTabla(String [] cabTabla)
    {
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        for(int i=0; i<this.columnas; i++){
            this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+cabTabla[i]+"</Data></Cell>\n";
        }
        this.xls+="   </Row>\n";
    }
    
    public void setCabeceraTabla(List tr)
    {
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        Iterator it = tr.iterator();
        while(it.hasNext()){
            this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+(String)it.next()+"</Data></Cell>\n";
        }
        this.xls+="   </Row>\n";
    }
    
    public String lista(ResultSet registros, String [] cabTabla, String [] tipo)
    {
        this.setEncabezado();
        this.columnas = cabTabla.length;
        this.xls = this.xls.replace("<<COLUMNAS>>", String.valueOf(this.columnas));
        
        this.xls+="   <Column ss:AutoFitWidth=\"0\" ss:Width=\"140.25\" ss:Span=\""+(this.columnas-2)+"\"/>\n";
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+this.subtitulo+"</Data></Cell>\n";
        this.xls+="   </Row>\n";

        this.setCabeceraTabla(cabTabla);
        try{
            while(registros.next()){
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                for(int j=1; j<=this.columnas; j++){
                    String campo = (registros.getString(j)!=null)?registros.getString(j):"";
                    String tipo_dato = tipo[j-1];
                    this.xls+="    <Cell><Data ss:Type=\""+(campo.compareTo("")!=0 ? tipo_dato : "String")+"\">" + campo + "</Data></Cell>\n";
                }
                this.xls+="   </Row>\n";
                this.filas++;
            }
            registros.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        this.xls = this.xls.replace("<<FILAS>>", String.valueOf(this.filas+1));
            
        this.setPie();

        return this.xls;
    }

    public String lista(String maestro, String maestro1, ResultSet registros, String [] cabTabla, String [] tipo)
    {
        this.setEncabezado();
        this.columnas = cabTabla.length;
        this.xls = this.xls.replace("<<COLUMNAS>>", String.valueOf(this.columnas));
        
        this.xls+="   <Column ss:AutoFitWidth=\"0\" ss:Width=\"140.25\" ss:Span=\""+(this.columnas-2)+"\"/>\n";
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+this.subtitulo+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">"+maestro+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">"+maestro1+"</Data></Cell>\n";
        this.xls+="   </Row>\n";

        this.setCabeceraTabla(cabTabla);
        try{
            while(registros.next()){
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                for(int j=1; j<=this.columnas; j++){
                    this.xls+="    <Cell><Data ss:Type=\""+tipo[j-1]+"\">" + ((registros.getString(j)!=null)?registros.getString(j):"") + "</Data></Cell>\n";
                }
                this.xls+="   </Row>\n";
                this.filas++;
            }
            registros.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        this.xls = this.xls.replace("<<FILAS>>", String.valueOf(this.filas+3));
            
        this.setPie();

        return this.xls;
    }
    
    public String PlanMercadeo(HttpServletResponse response, ResultSet rs, BaseDatos objDB)
    {
        String id = "-1";
        String sec_tipo_plan = "";
        String id_tipo_plan="";
        String tipo_plan="";
        String plan_mercadeo="";
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
        
        
        
        
        this.setEncabezado();
        this.columnas = 5;
        this.xls = this.xls.replace("<<COLUMNAS>>", String.valueOf(this.columnas));
        
        this.xls+="   <Column ss:AutoFitWidth=\"0\" ss:Width=\"140.25\" ss:Span=\""+(this.columnas-2)+"\"/>\n";
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">DATOS DEL PLAN DE MERCADEO</Data></Cell>\n";
        this.xls+="   </Row>\n";
        this.filas++;
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">No.: "+sec_tipo_plan+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        this.filas++;
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">TIPO: "+tipo_plan+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        this.filas++;
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">NOMBRE: "+plan_mercadeo+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        this.filas++;
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">TIPO DE MECÁNICA: "+((mecanica_tipo.compareTo("t")==0) ? "Texto" : "Operaciones")+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        this.filas++;
        
        if(mecanica_tipo.compareTo("t")==0){
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">MECÁNICA: "+mecanica+"</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
        }
        
        if(mecanica_tipo.compareTo("o")==0){
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">APLICA PARA: "+((aplica_prom_p_v.compareTo("p")==0) ? "Promociones" : "Convenios")+"</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
            
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">ARCHIVO</Data></Cell>\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">DESCRIPCION</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
            
            try{
                ResultSet rsAdj = objDB.consulta("select * from tbl_plan_mercadeo_adjunto with (nolock) where id_plan_mercadeo="+id+" order by id_plan_mercadeo_adjunto");
                String archivo="";
                String descripcion="";
                while(rsAdj.next()){
                    archivo = rsAdj.getString("archivo")!=null ? rsAdj.getString("archivo") : "";
                    descripcion = rsAdj.getString("descripcion")!=null ? rsAdj.getString("descripcion") : "";
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">"+archivo+"</Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\"><Data ss:Type=\"String\">"+descripcion+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">FECHA DE INICIO: "+ope_fecha_ini+"</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
            
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">FECHA DE TÉRMINO: "+ope_fecha_fin+"</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
        }
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">ALCANCE: "+alcance+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        this.filas++;
            
        if(tipo_alcance.compareTo("f")==0){
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">TIPO DE ALCANCE: "+tipo_alcance_de1+"</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
            
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">PROMEDIO DE VENTAS DESDE: "+fecha_ini+"</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
            
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">PROMEDIO DE VENTAS HASTA: "+fecha_fin+"</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
            
            if(tipo_alcance_de.compareTo("i")!=0){
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">RANGO DE FECHAS PARA EVALUAR LA PROYECCION DE VENTAS</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">"+( ((tipo_alcance_de.compareTo("i")==0) ? "PERÍODO A EVALUAR" : "")+" DESDE: " + fecha_ini_averificar)+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">"+( ((tipo_alcance_de.compareTo("i")==0) ? "PERÍODO A EVALUAR" : "")+" HASTA: " + fecha_fin_averificar)+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                if(objDB.getFilas(rsProductosFiltro)>0 || objDB.getFilas(rsProductos)>0){
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">PRODUCTOS</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                }
                
                if(objDB.getFilas(rsProductosFiltro)>0){
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">CATEGORIA</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">LABORATORIO</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">LINEA</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;

                    try{
                        String desc_nivel="";
                        String desc_clase="";
                        String desc_linea="";
                        while(rsProductosFiltro.next()){
                            desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                            desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                            desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+desc_nivel+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+desc_clase+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+desc_linea+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            
                if(objDB.getFilas(rsProductos)>0){
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">PRODUCTO</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    try{
                        String descripcion="";
                        while(rsProductos.next()){
                            descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\"><Data ss:Type=\"String\">"+descripcion+"</Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                
                if(objDB.getFilas(rsFarmacias)>0){
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">FARMACIAS</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">FARMACIA</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">PROM. VENTAS</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">%</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">PROY. VENTAS</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">% DISTRIBUCION GASTO</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    try{
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
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+nombre+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+p_ventas+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+p_crecimiento+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+proy_ventas+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+p_gasto+"</Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                
            }
            
            if(tipo_alcance_de.compareTo("i")==0){
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">FARMACIAS</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">NOMBRE</Data></Cell>\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">PROY. VENTAS</Data></Cell>\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">% DISTRIBUCION GASTO</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                    
                try{
                    String nombre="";
                    String proy_ventas="0";
                    String p_gasto="0";
                    rsFarmacias.beforeFirst();
                    while(rsFarmacias.next()){
                        nombre = rsFarmacias.getString("nombre")!=null ? rsFarmacias.getString("nombre") : "";
                        proy_ventas = rsFarmacias.getString("proy_ventas")!=null ? rsFarmacias.getString("proy_ventas") : "0";
                        p_gasto = rsFarmacias.getString("p_gasto")!=null ? rsFarmacias.getString("p_gasto") : "0";
                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+nombre+"</Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+proy_ventas+"</Data></Cell>\n";
                        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\"><Data ss:Type=\"String\">"+p_gasto+"</Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            
            if(tipo_alcance.compareTo("d")==0){
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">CONSULTA DE FACTURAS: </Data></Cell>\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">desde "+fecha_ini+"   hasta "+fecha_fin+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">VALOR A COMPARAR LA SUMATORIA DE LAS FACTURAS: </Data></Cell>\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+promedio_ventas+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">PORCENTAJE O PREMIO A ENTREGARSE: </Data></Cell>\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+premio+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
               
                try{
                    String nombre_vendedor = "";
                    ResultSet rsVen = objDB.consulta("select * from tbl_plan_mercadeo_vendedor with (nolock) where id_tipo_plan='"+id+"' order by nombre_vendedor");
                    if(objDB.getFilas(rsVen)>0){
                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">VENDEDORES</Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;

                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">VENDEDOR</Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;
                        while(rsVen.next()){
                            nombre_vendedor = rsVen.getString("nombre_vendedor")!=null ? rsVen.getString("nombre_vendedor") : "";
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+nombre_vendedor+"</Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">CATEGORIA</Data></Cell>\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">LABORATORIO</Data></Cell>\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">LINEA</Data></Cell>\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;

                try{
                    String desc_nivel="";
                    String desc_clase="";
                    String desc_linea="";
                    while(rsProductosFiltro.next()){
                        desc_nivel = rsProductosFiltro.getString("desc_nivel")!=null ? rsProductosFiltro.getString("desc_nivel") : "";
                        desc_clase = rsProductosFiltro.getString("desc_clase")!=null ? rsProductosFiltro.getString("desc_clase") : "";
                        desc_linea = rsProductosFiltro.getString("desc_linea")!=null ? rsProductosFiltro.getString("desc_linea") : "";
                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+desc_nivel+"</Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+desc_clase+"</Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+desc_linea+"</Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">PRODUCTO</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;

                try{
                    String descripcion="";
                    while(rsProductos.next()){
                        descripcion = rsProductos.getString("descripcion")!=null ? rsProductos.getString("descripcion") : "";
                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\"><Data ss:Type=\"String\">"+descripcion+"</Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                try{
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">CLIENTES</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">CLIENTE</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    String nombre_comercial = "";
                    ResultSet rsCli = objDB.consulta("select * from tbl_plan_mercadeo_cliente with (nolock) where id_tipo_plan='"+id+"' order by nombre_comercial");
                    if(objDB.getFilas(rsCli)>0){
                        while(rsCli.next()){
                            nombre_comercial = rsCli.getString("nombre_comercial")!=null ? rsCli.getString("nombre_comercial") : "";
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+nombre_comercial+"</Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                if(adjuntos.compareTo("")!=0){
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">ARCHIVOS ADJUNTOS</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    String vecAdj[] = adjuntos.split("|");
                    for(String adjunto : vecAdj){
                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">"+adjunto+"</Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;
                    }
                }
            }
            
            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">AUSPICIOS</Data></Cell>\n";
            this.xls+="   </Row>\n";
            this.filas++;
            
            try{
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
                if(objDB.getFilas(rsLabs)>0){
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">LABORATORIOS ESTRATÉGICOS</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;

                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">LABORATORIO ESTRATÉGICO</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">PRES. DISP.</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">MONTO</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;

                    while(rsLabs.next()){
                        id_laboratorio = rsLabs.getString("id_laboratorio")!=null ? rsLabs.getString("id_laboratorio") : "";
                        nombre_comercial = rsLabs.getString("nombre_comercial")!=null ? rsLabs.getString("nombre_comercial") : "";
                        presupuesto = rsLabs.getString("presupuesto")!=null ? rsLabs.getFloat("presupuesto") : 0;
                        pos = Matriz.enMatriz(laboratorios, id_laboratorio, 0);
                        monto = pos>=0 ? Float.parseFloat(laboratorios[pos][1]) : 0;
                        presupuesto += monto; 
                        if(monto>0){
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+nombre_comercial+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+presupuesto+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">"+monto+"</Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            try{
                ResultSet rsProv = objDB.consulta("select numero_idproveedor, nombre_comercial, num_formulario, monto, CONVERT(VARCHAR,fecha_registro, 103) as fecha_registro, confirmado, eliminado "
                + "from tbl_plan_mercadeo_proveedor with (nolock) where id_plan_mercadeo="+id+" and eliminado=0 order by eliminado, nombre_comercial;");
                String nombre_comercial = "";
                String monto = "";
                String num_formulario = "";
                if(objDB.getFilas(rsProv)>0){
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">LABORATORIOS NO ESTRATÉGICOS</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;

                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">PROVEEDOR</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\">MONTO</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"># FORM.</Data></Cell>\n";
                    this.xls+="    <Cell ss:StyleID=\"s62\"><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    while(rsProv.next()){
                        nombre_comercial = rsProv.getString("nombre_comercial")!=null ? rsProv.getString("nombre_comercial") : "";
                        num_formulario = rsProv.getString("num_formulario")!=null ? rsProv.getString("num_formulario") : "";
                        monto = rsProv.getString("monto")!=null ? rsProv.getString("monto") : "";
                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+nombre_comercial+"</Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+monto+"</Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">"+num_formulario+"</Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            
        }    
        
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">ESTRATEGIAS</Data></Cell>\n";
        this.xls+="   </Row>\n";
        this.filas++;
        try{
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
        
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">ESTRATEGIA: "+estrategia+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">CONCEPTO: "+concepto+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">TÁCTICA: "+tactica+"</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-1)+"\"><Data ss:Type=\"String\">ACTIVIDADES</Data></Cell>\n";
                this.xls+="   </Row>\n";
                this.filas++;
                
                ResultSet rsAct = objDB.consulta("select *, CONVERT(VARCHAR, fecha_ini, 103) as sql_fecha_ini, CONVERT(VARCHAR, fecha_fin, 103) as sql_fecha_fin "
                                                    + "from tbl_actividad with (nolock) where id_estrategia="+id_estrategia);
                while(rsAct.next()){
                    String id_actividad = rsAct.getString("id_actividad")!=null ? rsAct.getString("id_actividad") : "";
                    String actividad = (rsAct.getString("actividad")!=null ? rsAct.getString("actividad") : "");
                    String sql_fecha_ini = (rsAct.getString("sql_fecha_ini")!=null ? rsAct.getString("sql_fecha_ini") : "");
                    String sql_fecha_fin = (rsAct.getString("sql_fecha_fin")!=null ? rsAct.getString("sql_fecha_fin") : "");
                    String responsable_seg = (rsAct.getString("responsable_seg")!=null ? rsAct.getString("responsable_seg") : "");
                    String responsable_eje = (rsAct.getString("responsable_eje")!=null ? rsAct.getString("responsable_eje") : "");
                    String pre_tipo = (rsAct.getString("pre_tipo")!=null ? rsAct.getString("pre_tipo") : "");
                    String pre_proveedor = (rsAct.getString("pre_proveedor")!=null ? rsAct.getString("pre_proveedor") : "");
                    String pre_cantidad = (rsAct.getString("pre_cantidad")!=null ? rsAct.getString("pre_cantidad") : "");
                    String pre_p_u = (rsAct.getString("pre_p_u")!=null ? rsAct.getString("pre_p_u") : "");
                    String pre_total = (rsAct.getString("pre_total")!=null ? rsAct.getString("pre_total") : "");
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">ACTIVIDAD: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+actividad+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">FECHA DE INICIO: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+sql_fecha_ini+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">FECHA DE TÉRMINO: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+sql_fecha_fin+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    if(responsable_seg.compareTo("")!=0){
                        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                        this.xls+="    <Cell><Data ss:Type=\"String\">RESPONSABLE DE SEGUIMIENTO: </Data></Cell>\n";
                        this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+responsable_seg+"</Data></Cell>\n";
                        this.xls+="   </Row>\n";
                        this.filas++;
                    }
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">RESPONSABLE DE EJECUCIÓN: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+responsable_eje+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    String tipo_presupuesto="Bienes";
                    if(pre_tipo.compareTo("s")==0){
                        tipo_presupuesto = "Servicio";
                    }else if(pre_tipo.compareTo("a")==0){
                              tipo_presupuesto = "Activo fijo";
                    }else if(pre_tipo.compareTo("p")==0){
                              tipo_presupuesto = "Premio";
                    }else if(pre_tipo.compareTo("i")==0){
                              tipo_presupuesto = "Inventario";
                    }
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">TIPO: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+tipo_presupuesto+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">PROVEEDOR SUGERIDO: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+pre_proveedor+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">CANTIDAD: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+pre_cantidad+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">PRECIO UNITARIO INCLUIDO IVA: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+pre_p_u+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                    this.xls+="    <Cell><Data ss:Type=\"String\">TOTAL: </Data></Cell>\n";
                    this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+pre_total+"</Data></Cell>\n";
                    this.xls+="   </Row>\n";
                    this.filas++;
                    
                    try{
                        String pre_id_tipo_plan_cuenta = rsAct.getString("pre_id_tipo_plan_cuenta")!=null ? rsAct.getString("pre_id_tipo_plan_cuenta") : "";
                        ResultSet rsCuenta = objDB.consulta("select descripcion from tbl_tipo_plan_cuenta with (nolock) where id_tipo_plan_cuenta="+pre_id_tipo_plan_cuenta);
                        if(rsCuenta.next()){
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">CUENTA CONTABLE: </Data></Cell>\n";
                            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+(rsCuenta.getString("descripcion")!=null ? rsCuenta.getString("descripcion") : "")+"</Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                            rsCuenta.close();
                        }   
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                    
                    try{
                        ResultSet rsCrono = objDB.consulta("select CONVERT(VARCHAR, fecha, 103) as fecha_sql, descripcion from tbl_actividad_cronograma with (nolock) where id_actividad="+id_actividad+" order by fecha");
                        if(objDB.getFilas(rsCrono)>0){
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-2)+"\"><Data ss:Type=\"String\">CRONOGRAMA DE ACTISICIONES</Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                
                            this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                            this.xls+="    <Cell><Data ss:Type=\"String\">FECHA: </Data></Cell>\n";
                            this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">DESCRIPCIÓN</Data></Cell>\n";
                            this.xls+="   </Row>\n";
                            this.filas++;
                            while(rsCrono.next()){
                                this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
                                this.xls+="    <Cell><Data ss:Type=\"String\"> </Data></Cell>\n";
                                this.xls+="    <Cell><Data ss:Type=\"String\">"+(rsCrono.getString("fecha_sql")!=null ? rsCrono.getString("fecha_sql") : "")+"</Data></Cell>\n";
                                this.xls+="    <Cell ss:MergeAcross=\""+(this.columnas-3)+"\"><Data ss:Type=\"String\">"+(rsCrono.getString("descripcion")!=null ? rsCrono.getString("descripcion") : "")+"</Data></Cell>\n";
                                this.xls+="   </Row>\n";
                                this.filas++;
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        this.xls = this.xls.replace("<<FILAS>>", String.valueOf(this.filas));
            
        this.setPie();

        return this.xls;
    }

    public String setMensaje(String mensaje)
    {
        this.setEncabezado();
        this.xls = this.xls.replace("<<COLUMNAS>>", String.valueOf(1));
        
        this.xls+="   <Column ss:AutoFitWidth=\"0\" ss:Width=\"340.25\" ss:Span=\"1\"/>\n";
        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\"1\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+this.subtitulo+"</Data></Cell>\n";
        this.xls+="   </Row>\n";

        this.xls+="   <Row ss:AutoFitHeight=\"0\">\n";
        this.xls+="    <Cell ss:MergeAcross=\"1\" ss:StyleID=\"s62\"><Data ss:Type=\"String\">"+mensaje+"</Data></Cell>\n";
        this.xls+="   </Row>\n";
        
        this.xls = this.xls.replace("<<FILAS>>", String.valueOf(2));
            
        this.setPie();

        return this.xls;
    }
    
    public String truncar(float num)
    {
        if(num>0){
            num = num + 0.0009f;
        }
        String cad2 = String.valueOf(num).replace(".", ":");
        String cad[] = cad2.split(":");
        String res = "";
        if(cad.length>1){
            cad[1] += "000";
            res = cad[1].substring(0,2);
        }
        return cad[0]+"."+res;
    }
    
    public void setPie()
    {
        this.xls+="  </Table>\n";
        this.xls+="  <WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">\n";
        this.xls+="   <PageSetup>\n";
        this.xls+="    <Header x:Margin=\"0.3\"/>\n";
        this.xls+="    <Footer x:Margin=\"0.3\"/>\n";
        this.xls+="    <PageMargins x:Bottom=\"0.75\" x:Left=\"0.7\" x:Right=\"0.7\" x:Top=\"0.75\"/>\n";
        this.xls+="   </PageSetup>\n";
        this.xls+="   <Unsynced/>\n";
        this.xls+="   <Print>\n";
        this.xls+="    <ValidPrinterInfo/>\n";
        this.xls+="    <PaperSizeIndex>9</PaperSizeIndex>\n";
        this.xls+="    <HorizontalResolution>600</HorizontalResolution>\n";
        this.xls+="    <VerticalResolution>600</VerticalResolution>\n";
        this.xls+="   </Print>\n";
        this.xls+="   <Selected/>\n";
        this.xls+="   <Panes>\n";
        this.xls+="    <Pane>\n";
        this.xls+="     <Number>1</Number>\n";
        this.xls+="    </Pane>\n";
        this.xls+="   </Panes>\n";
        this.xls+="   <ProtectObjects>False</ProtectObjects>\n";
        this.xls+="   <ProtectScenarios>False</ProtectScenarios>\n";
        this.xls+="  </WorksheetOptions>\n";
        this.xls+=" </Worksheet>\n";
        this.xls+="</Workbook>\n";
    }
}
