/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.mer.pla.Excel;

import com.far.lib.BaseDatos;
import com.far.lib.Matriz;
import com.far.lib.Word;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

/**
 *
 * @author Jorge
 */
public class WordPlanMercadeo extends HttpServlet {

    private String _ip = null;
    private int _puerto = 5432;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
    }

    /**
    * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sesion = request.getSession(true);
        String usuario = (String)sesion.getAttribute("usuario");
        
        if(request.getHeader("User-Agent").toLowerCase().contains("windows")){
            response.setContentType("application/vnd.ms-word;");
            response.setHeader("Content-disposition", "inline; filename=PlanMercadeo.docx;");
        }else{
            response.setContentType("text/xml;");
            response.setHeader("Content-disposition", "attachment; filename=PlanMercadeo.odt;");
        }
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String num_plan = request.getParameter("nro");
        String id_plan_mercadeo = request.getParameter("idPM1");
        
        
        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        ResultSet rsUsuarios = objDB.consulta("select distinct usuario_creacion, usuario_creacion as usuario_creacion1 "
                + "from tbl_plan_mercadeo where usuario_creacion is not null order by usuario_creacion");
        String matUsuarios[][] = Matriz.ResultSetAMatriz(rsUsuarios);
        
        
        ResultSet rs = null;
        ResultSet rsPlan = null;
        if(num_plan.compareTo("")!=0){        
            rs = objDB.consulta("select P.id_plan_mercadeo, P.sec_tipo_plan, P.id_tipo_plan, P.plan_mercadeo, CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, "
                + "CONVERT(VARCHAR, P.fecha_aprobacion, 103) as fecha_aprobacion, P.usuario_creacion, P.tipo_alcance, P.tipo_alcance_de, CONVERT(VARCHAR, P.fecha_ini, 103) as fecha_ini, CONVERT(VARCHAR, P.fecha_fin, 103) as fecha_fin, "
                + "P.promedio_ventas, CONVERT(VARCHAR,fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR,fecha_fin_averificar, 103) as fecha_fin_averificar, "
                + "P.tipo_dist_gasto, P.proyeccion_ventas, P.premio, P.adjuntos, P.plan_completo, mecanica_tipo, mecanica, aplica_prom_p_v, CONVERT(VARCHAR,ope_fecha_ini, 103) as ope_fecha_ini, "
                + "CONVERT(VARCHAR,ope_fecha_fin, 103) as ope_fecha_fin, registro_operaciones, aprobada_operaciones, P.abierto, TP.nombre as tipo_plan, P.motivo_rechazo, P.motivo_rechazo_operacion "
                + "from tbl_plan_mercadeo as P with (nolock) inner join tbl_tipo_plan as TP with (nolock) on TP.id_tipo_plan=P.id_tipo_plan "
                + "where P.estado in(6,9) and P.sec_tipo_plan="+num_plan + (Matriz.enMatriz(matUsuarios, usuario, 0)!=-1 ? " and P.usuario_creacion='"+usuario+"'" : "") );
            
            rsPlan = objDB.consulta("select * from tbl_plan_mercadeo with (nolock) where sec_tipo_plan="+num_plan);
        }else{
            rs = objDB.consulta("select P.id_plan_mercadeo, P.sec_tipo_plan, P.id_tipo_plan, P.plan_mercadeo, CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, "
                + "CONVERT(VARCHAR, P.fecha_aprobacion, 103) as fecha_aprobacion, P.usuario_creacion, P.tipo_alcance, P.tipo_alcance_de, CONVERT(VARCHAR, P.fecha_ini, 103) as fecha_ini, "
                + "CONVERT(VARCHAR, P.fecha_fin, 103) as fecha_fin, "
                + "P.promedio_ventas, CONVERT(VARCHAR,fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR,fecha_fin_averificar, 103) as fecha_fin_averificar, "
                + "P.tipo_dist_gasto, P.proyeccion_ventas, P.premio, P.adjuntos, P.plan_completo, mecanica_tipo, mecanica, aplica_prom_p_v, CONVERT(VARCHAR,ope_fecha_ini, 103) as ope_fecha_ini, "
                + "CONVERT(VARCHAR,ope_fecha_fin, 103) as ope_fecha_fin, registro_operaciones, aprobada_operaciones, P.abierto, TP.nombre as tipo_plan, P.motivo_rechazo, P.motivo_rechazo_operacion "
                + "from tbl_plan_mercadeo as P with (nolock) inner join tbl_tipo_plan as TP with (nolock) on TP.id_tipo_plan=P.id_tipo_plan "
                + "where P.estado in(6,9) and P.id_plan_mercadeo="+id_plan_mercadeo + (Matriz.enMatriz(matUsuarios, usuario, 0)!=-1 ? " and P.usuario_creacion='"+usuario+"'" : "") );
            
            rsPlan = objDB.consulta("select * from tbl_plan_mercadeo with (nolock) where id_plan_mercadeo="+id_plan_mercadeo);
        }
        
        String nombre_plan = "";
        try{
            if(rsPlan.next()){
                nombre_plan = rsPlan.getString("plan_mercadeo")!=null ? rsPlan.getString("plan_mercadeo") : "";
                rsPlan.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        Word docx = new Word();
        
        if(objDB.getFilas(rs)>0){
            
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

            ResultSet rsProductosFiltro = objDB.consulta("select desc_nivel, desc_clase, desc_linea from tbl_plan_mercadeo_producto_filtro with (nolock) where id_plan_mercadeo="+id+" order by id_plan_mercadeo_producto_filtro");
            ResultSet rsProductos = objDB.consulta("select descripcion from tbl_plan_mercadeo_producto with (nolock) where id_plan_mercadeo="+id+" order by descripcion");

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
            
            docx.setTexto("DATOS DEL PLAN DE MERCADEO", ParagraphAlignment.CENTER, 12, true, false, UnderlinePatterns.SINGLE);

            docx.setTexto("No.: " + sec_tipo_plan);
            docx.setTexto("TIPO: " + tipo_plan);
            docx.setTexto("NOMBRE: " + plan_mercadeo);
            docx.setTexto("FECHA DE CREACION: " + fecha_creacion);
            docx.setTexto("FECHA DE APROBACION: " + fecha_aprobacion);
            docx.setTexto("TIPO DE MECANICA: " + ((mecanica_tipo.compareTo("t")==0) ? "Texto" : "Operaciones") );
            
            if(mecanica_tipo.compareTo("t")==0){
                docx.setTexto("MECANICA: " + mecanica);
            }
            if(mecanica_tipo.compareTo("o")==0){
                docx.setTexto("APLICA PARA: " + ((aplica_prom_p_v.compareTo("p")==0) ? "Promociones" : "Convenios") );
                
                ResultSet rsAdj = objDB.consulta("select archivo, descripcion from tbl_plan_mercadeo_adjunto with (nolock) where id_plan_mercadeo="+id+" order by id_plan_mercadeo_adjunto");
                
                docx.setTabla(rsAdj, new String[]{"ARCHIVO","DESCRIPCION"}, 12);
                
                docx.setTexto("FECHA DE INICIO: " + ope_fecha_ini);
                docx.setTexto("FECHA DE TÉRMINO: " + ope_fecha_fin);
            }
            
            docx.setTexto("ALCANCE: " + alcance);
            
            if(tipo_alcance.compareTo("f")==0){
                docx.setTexto("TIPO DE ALCANCE: " + tipo_alcance_de1);
                docx.setTexto("PROMEDIO DE VENTAS DESDE: " + fecha_ini);
                docx.setTexto("FPROMEDIO DE VENTAS HASTA: " + fecha_fin);
                
                if(tipo_alcance_de.compareTo("i")!=0){
                    docx.setTexto("RANGO DE FECHAS PARA EVALUAR LA PROYECCION DE VENTAS", ParagraphAlignment.LEFT, 12, true, true, UnderlinePatterns.SINGLE);
                    docx.setTexto( ((tipo_alcance_de.compareTo("i")==0) ? "PERÍODO A EVALUAR" : "")+" DESDE: " + fecha_ini_averificar);
                    docx.setTexto( ((tipo_alcance_de.compareTo("i")==0) ? "PERÍODO A EVALUAR" : "")+" HASTA: " + fecha_fin_averificar);
                    docx.setTexto(" ");
                    
                    docx.setTexto("PRODUCTOS", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                    docx.setTabla(rsProductosFiltro, new String[]{"CATEGORIA","LABORATORIO","LINEA"}, 12);
                    
                    docx.setTexto(" ");
                    docx.setTabla(rsProductos, new String[]{"PRODUCTO"}, 12);
                    
                    docx.setTexto(" ");
                    docx.setTexto("FARMACIAS", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                    ResultSet rsFarmacias = objDB.consulta("select nombre, p_ventas, p_gasto, proy_ventas, p_gasto from tbl_plan_mercadeo_farmacia with (nolock) where id_plan_mercadeo="+id+" order by nombre");
                    docx.setTabla(rsFarmacias, new String[]{"FARMACIA","PROM. VENTAS","%","PROY. VENTAS","% DISTRIBUCION GASTO"}, 12);
                }
                
                if(tipo_alcance_de.compareTo("i")==0){
                    docx.setTexto(" ");
                    docx.setTexto("FARMACIAS", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                    ResultSet rsFarmacias = objDB.consulta("select nombre, proy_ventas, p_gasto from tbl_plan_mercadeo_farmacia with (nolock) where id_plan_mercadeo="+id+" order by nombre");
                    docx.setTabla(rsFarmacias, new String[]{"NOMBRE","PROY. VENTAS","% DISTRIBUCION GASTO"}, 12);
                }
            } // fin tipo_alcance == f
            
            if(tipo_alcance.compareTo("d")==0){
                docx.setTexto("CONSULTA DE FACTURAS desde "+fecha_ini+"   hasta "+fecha_fin);
                docx.setTexto("VALOR A COMPARAR LA SUMATORIA DE LAS FACTURAS: " + promedio_ventas);
                docx.setTexto("PORCENTAJE O PREMIO A ENTREGARSE: " + premio);
                
                docx.setTexto("VENDEDORES", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                ResultSet rsVen = objDB.consulta("select nombre_vendedor from tbl_plan_mercadeo_vendedor with (nolock) where id_tipo_plan='"+id+"' order by nombre_vendedor");
                docx.setTabla(rsVen, new String[]{"VENDEDOR"}, 12);
                
                docx.setTexto(" ");
                docx.setTexto("PRODUCTOS", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                docx.setTabla(rsProductosFiltro, new String[]{"CATEGORIA","LABORATORIO","LINEA"}, 12);
                
                docx.setTexto(" ");
                docx.setTabla(rsProductos, new String[]{"PRODUCTO"}, 12);
                
                docx.setTexto(" ");
                docx.setTexto("CLIENTES", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                ResultSet rsCli = objDB.consulta("select nombre_comercial from tbl_plan_mercadeo_cliente with (nolock) where id_tipo_plan='"+id+"' order by nombre_comercial");
                docx.setTabla(rsCli, new String[]{"CLIENTE"}, 12);
                
                if(adjuntos.compareTo("")!=0){
                    docx.setTexto(" ");
                    docx.setTexto("ARCHIVOS ADJUNTOS", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                    String vecAdj[] = adjuntos.split("|");
                    docx.setTabla(vecAdj, null, 12);
                }
            } // fin tipo_alcance == d
            
            docx.setTexto(" ");
            docx.setTexto("AUSPICIOS", ParagraphAlignment.LEFT, 12, true, true, UnderlinePatterns.SINGLE);
            
            try{
                docx.setTexto(" ");
                docx.setTexto("LABORATORIOS ESTRATÉGICOS", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                
                ResultSet rsLabs = objDB.consulta("select L.id_laboratorio, nombre_comercial, monto, LP.saldo, presupuesto, id_tipo_plan "
                        + "from (tbl_laboratorio as L with (nolock) inner join tbl_labortorio_tipo_plan_presupuesto as LP with (nolock) on L.id_laboratorio=LP.id_laboratorio) "
                        + "where (L.id_laboratorio in (select id_laboratorio from tbl_plan_mercadeo_laboratorio where id_plan_mercadeo="+id+") "
                        + "or (monto>0 and estado=1) ) and id_tipo_plan='"+id_tipo_plan+"' order by nombre_comercial;");
                ResultSet rsAus = objDB.consulta("select id_laboratorio, monto from tbl_plan_mercadeo_laboratorio with (nolock) where id_plan_mercadeo="+id+" order by id_laboratorio;");
                String laboratorios[][] = Matriz.ResultSetAMatriz(rsAus);
                String labsEstrategicos[][] = new String[objDB.getFilas(rsLabs)][3];
                String id_laboratorio = "";
                String nombre_comercial = "";
                float presupuesto = 0;
                float monto = 0;
                int pos=0;
                int i=0;
                while(rsLabs.next()){
                    id_laboratorio = rsLabs.getString("id_laboratorio")!=null ? rsLabs.getString("id_laboratorio") : "";
                    nombre_comercial = rsLabs.getString("nombre_comercial")!=null ? rsLabs.getString("nombre_comercial") : "";
                    presupuesto = rsLabs.getString("presupuesto")!=null ? rsLabs.getFloat("presupuesto") : 0;
                    pos = Matriz.enMatriz(laboratorios, id_laboratorio, 0);
                    monto = pos>=0 ? Float.parseFloat(laboratorios[pos][1]) : 0;
                    presupuesto += monto; 
                    if(monto>0){
                        labsEstrategicos[i][0] = nombre_comercial;
                        labsEstrategicos[i][1] = String.valueOf(presupuesto);
                        labsEstrategicos[i][2] = String.valueOf(monto);
                    }
                }
                docx.setTabla(labsEstrategicos, new String[]{"LABORATORIO ESTRATÉGICO","PRES. DISP.","MONTO"}, 12);
            }catch(Exception e){
                e.printStackTrace();
            }
            
            docx.setTexto(" ");
            docx.setTexto("LABORATORIOS NO ESTRATÉGICOS", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
            ResultSet rsProv = objDB.consulta("select nombre_comercial, monto, num_formulario "
                + "from tbl_plan_mercadeo_proveedor with (nolock) where id_plan_mercadeo="+id+" and eliminado=0 order by eliminado, nombre_comercial;");
            docx.setTabla(rsProv, new String[]{"PROVEEDOR","MONTO","# FORM."}, 12);
            
            
            docx.setTexto(" ");
            docx.setTexto("ESTRATEGIAS", ParagraphAlignment.LEFT, 12, true, true, UnderlinePatterns.SINGLE);
            
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
                    
                    docx.setTexto(" ");
                    docx.setTexto("ESTRATEGIA: " + estrategia);
                    docx.setTexto("CONCEPTO: " + concepto);
                    docx.setTexto("TÁCTICA: " + tactica);
                    
                    docx.setTexto(" ");
                    docx.setTexto("ACTIVIDADES", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                    ResultSet rsAct = objDB.consulta("select *, CONVERT(VARCHAR, fecha_ini, 103) as sql_fecha_ini, CONVERT(VARCHAR, fecha_fin, 103) as sql_fecha_fin "
                                                    + "from tbl_actividad with (nolock) where id_estrategia="+id_estrategia);
                    while(rsAct.next()){
                        String id_actividad = rsAct.getString("id_actividad")!=null ? rsAct.getString("id_actividad") : "";
                        
                        docx.setTexto("ACTIVIDAD: " + (rsAct.getString("actividad")!=null ? rsAct.getString("actividad") : "") );
                        docx.setTexto("FECHA DE INICIO: " + (rsAct.getString("sql_fecha_ini")!=null ? rsAct.getString("sql_fecha_ini") : "") );
                        docx.setTexto("FECHA DE TÉRMINO: " + (rsAct.getString("sql_fecha_fin")!=null ? rsAct.getString("sql_fecha_fin") : "") );
                        String responsable_seg = rsAct.getString("responsable_seg")!=null ? rsAct.getString("responsable_seg") : "";
                        if(responsable_seg.compareTo("")!=0){
                            docx.setTexto("RESPONSABLE DE SEGUIMIENTO: " + responsable_seg );
                        }
                        docx.setTexto("RESPONSABLE DE EJECUCIÓN: " + (rsAct.getString("responsable_eje")!=null ? rsAct.getString("responsable_eje") : "") );
                        
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
                        docx.setTexto("TIPO: " + tipo_presupuesto );
                        docx.setTexto("PROVEEDOR SUGERIDO: " + (rsAct.getString("pre_proveedor")!=null ? rsAct.getString("pre_proveedor") : "") );
                        docx.setTexto("CANTIDAD: " + (rsAct.getString("pre_cantidad")!=null ? rsAct.getString("pre_cantidad") : "") );
                        docx.setTexto("PRECIO UNITARIO INCLUIDO IVA: " + (rsAct.getString("pre_p_u")!=null ? rsAct.getString("pre_p_u") : "") );
                        docx.setTexto("TOTAL: " + (rsAct.getString("pre_total")!=null ? rsAct.getString("pre_total") : "") );
                        
                        String pre_id_tipo_plan_cuenta = rsAct.getString("pre_id_tipo_plan_cuenta")!=null ? rsAct.getString("pre_id_tipo_plan_cuenta") : "";
                        ResultSet rsCuenta = objDB.consulta("select descripcion from tbl_tipo_plan_cuenta with (nolock) where id_tipo_plan_cuenta="+pre_id_tipo_plan_cuenta);
                        docx.setTabla(rsCuenta, new String[]{"CUENTA CONTABLE"}, 12);
                        
                        ResultSet rsCrono = objDB.consulta("select CONVERT(VARCHAR, fecha, 103) as fecha_sql, descripcion from tbl_actividad_cronograma with (nolock) where id_actividad="+id_actividad+" order by fecha");
                        if(objDB.getFilas(rsCrono)>0){
                            docx.setTexto(" ");
                            docx.setTexto("CRONOGRAMA DE ACTISICIONES:", ParagraphAlignment.LEFT, 12, false, true, UnderlinePatterns.SINGLE);
                            docx.setTabla(rsCrono, new String[]{"FECHA","DESCRIPCIÓN"}, 12);
                        }
                    }
                    
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }else{    
            docx.setTexto("El plan de mercadeo " + nombre_plan.toUpperCase() + " aún no se encuentra autorizado por el/la COORDINADOR(A) COMERCIAL o no tiene autorización para ver el reporte");
        }
        
        docx.enviarRespuesta(response, "PlanMercadeo.docx");

        
        
        
        try{
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        objDB.cerrar();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
