/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Correo;
import com.far.mer.pla.clas.Configuracion;
import com.far.mer.pla.clas.PlanMercadeo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class frmPlanMercadeoEnviarAAut extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String _svr_mail = null;
    
    private String gene_ip = null;
    private int gene_puerto = 1433;
    private String gene_db = null;
    private String gene_usuario = null;
    private String gene_clave = null;
    
    private String segu_ip = null;
    private int segu_puerto = 1433;
    private String segu_db = null;
    private String segu_usuario = null;
    private String segu_clave = null;
    
    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this._svr_mail = config.getServletContext().getInitParameter("_SVR_MAIL");
        
        this.gene_ip = config.getServletContext().getInitParameter("GENE_IP");
        this.gene_puerto = Integer.parseInt(config.getServletContext().getInitParameter("GENE_PUERTO"));
        this.gene_db = config.getServletContext().getInitParameter("GENE_DB");
        this.gene_usuario = config.getServletContext().getInitParameter("GENE_USUARIO");
        this.gene_clave = config.getServletContext().getInitParameter("GENE_CLAVE");
        
        this.segu_ip = config.getServletContext().getInitParameter("SEGU_IP");
        this.segu_puerto = Integer.parseInt(config.getServletContext().getInitParameter("SEGU_PUERTO"));
        this.segu_db = config.getServletContext().getInitParameter("SEGU_DB");
        this.segu_usuario = config.getServletContext().getInitParameter("SEGU_USUARIO");
        this.segu_clave = config.getServletContext().getInitParameter("SEGU_CLAVE");
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sesion = request.getSession(true);
        String empleado = (String)sesion.getAttribute("empleado");
        String cargo = (String)sesion.getAttribute("cargo");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        
        String id_plan_mercadeo = request.getParameter("id");

        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        String plan_mercadeo = "";
        String tipo_alcance = "";
        String usuario_creacion = "";
        try{
            ResultSet rsAct = objPlanMercadeo.getVerPlanMercadeo(id_plan_mercadeo);
            if(rsAct.next()){
                plan_mercadeo = rsAct.getString("plan_mercadeo")!=null?rsAct.getString("plan_mercadeo"):"";
                tipo_alcance = rsAct.getString("tipo_alcance")!=null?rsAct.getString("tipo_alcance"):"";
                usuario_creacion = rsAct.getString("usuario_creacion")!=null ? rsAct.getString("usuario_creacion") : "";
                rsAct.close();
            }
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }
        
        String para_creador = this.getEMail(usuario_creacion);
        String cedula_creador = this.getCedula(usuario_creacion);
        String empleado_creador[] = this.getEmpleado(cedula_creador);
        
        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String remitente = objConfiguracion.getValor("mail_remitente");
        String para = objConfiguracion.getValor("mail_marketing");
        
        String asunto = "NOTIFICACION DE PLAN DE MERCADEO";
        String mensaje = "Estimado(a) Sr(a).<br />" +
        "<strong>DIRECTOR(A) DE MARKETING</strong><br /><br />" +
        "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; est&aacute; listo para su revisi&oacute;n.<br /><br /><br />" +
        "Atentamente,<br />" +
        "<strong>"+empleado + "<br />" +
        cargo+"</strong>";
        
        String mensaje_creador = "Estimado(a) Sr(a).<br />" +
        "<strong>"+empleado_creador[0] + "<br />" +
        empleado_creador[1] + "</strong><br /><br />" +  
        "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; est&aacute; listo para la revisi&oacute;n por el/la <strong>DIRECTOR(A) DE MARKETING</strong>.<br /><br /><br />" +
        "Atentamente,<br />" +
        "<strong>"+empleado + "<br />" +
        cargo+"</strong>";
        if(tipo_alcance.compareTo("d")==0){
            para = objConfiguracion.getValor("mail_ventas");
            mensaje = "Estimado(a) Sr(a).<br />" +
            "<strong>DIRECTOR(A) DE VENTAS</strong><br /><br />" +
            "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; est&aacute; listo para su revisi&oacute;n.<br /><br /><br />" +
            "Atentamente,<br />" +
            "<strong>"+empleado + "<br />" +
            cargo+"</strong>";
            
            mensaje_creador = "Estimado(a) Sr(a).<br />" +
            "<strong>"+empleado_creador[0] + "<br />" +
            empleado_creador[1] + "</strong><br /><br />" +  
            "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; est&aacute; listo para la revisi&oacute;n por el/la <strong>DIRECTOR(A) DE VENTAS</strong>.<br /><br /><br />" +
            "Atentamente,<br />" +
            "<strong>"+empleado + "<br />" +
            cargo+"</strong>";
        }
        
       
        try {
            if(objPlanMercadeo.existenActividades(id_plan_mercadeo)){
                if(objPlanMercadeo.enviarAAutorizaciones(id_plan_mercadeo, 1)){
                    if(Correo.enviar(this._svr_mail, remitente, para, "", "", asunto, mensaje, true)){
                        Correo.enviar(this._svr_mail, remitente, para_creador, "", "", asunto, mensaje_creador, true);
                        res = "fun»_R('bloq_vta1');_R('vta1');mer_getPlanesMercadeo(0);^msg»Información guardada satisfactoriamente.";
                    }else{
                        res = "msg»" + Correo.getError();
                        objPlanMercadeo.enviarAAutorizaciones(id_plan_mercadeo, 0);
                    }
                }else{
                    res = "msg»" + objPlanMercadeo.getError();
                }
            }else{
                res = "msg»Debe registrar por lo menos una estrategia y una actividad, para poder enviar a autorizaciones.";
            }
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        } finally {
            objPlanMercadeo.cerrar();
            objConfiguracion.cerrar();
        }

        try{
            out.print(res);
        }finally {
            out.close();
        }
    }

    public String getCedula(String usuario)
    {
        BaseDatos objDB = new BaseDatos(this.segu_ip, this.segu_puerto, this.segu_db, this.segu_usuario, this.segu_clave);
        try{
            ResultSet rs = objDB.consulta("select Cedula from Usuarios with (nolock) where NombreCorto='"+usuario+"'");
            if(rs.next()){
                return rs.getString("Cedula")!=null?rs.getString("Cedula"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objDB.cerrar();
        }
        return "";
    }
    
    public String[] getEmpleado(String cedula)
    {
        String vec[] = new String[]{"",""};
        BaseDatos objDB = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        try{
            ResultSet rs = objDB.consulta("select E.NOMBRES, E.APELLIDOS, C.DESCRIPCION from EMPLEADOS as E with (nolock) inner join CARGOS as C with (nolock) "
                    + "on E.CODIGO_CARGO=C.CODIGO_CARGO where E.CEDULA='"+cedula+"'");
            if(rs.next()){
                String empleado = rs.getString("NOMBRES")!=null?rs.getString("NOMBRES"):"";
                empleado += " " + (rs.getString("APELLIDOS")!=null?rs.getString("APELLIDOS"):"");
                vec[0] = empleado;
                vec[1] = rs.getString("DESCRIPCION")!=null?rs.getString("DESCRIPCION"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objDB.cerrar();
        }
        return vec;
    }
    
    public String getEMail(String usuario)
    {
        BaseDatos objDB = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        try{
            ResultSet rs = objDB.consulta("select Email from usuarios with (nolock) where lower(NombreCorto) = '"+usuario+"'");
            if(rs.next()){
                return rs.getString("Email")!=null?rs.getString("Email"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objDB.cerrar();
        }
        return "";
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
