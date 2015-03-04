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
public class frmPlanMercadeoAprobar extends HttpServlet {

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
        String usuario = (String)sesion.getAttribute("usuario");
        String empleado = (String)sesion.getAttribute("empleado");
        String cargo = (String)sesion.getAttribute("cargo");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        
        String id_plan_mercadeo = request.getParameter("id_plan_mercadeo");
        String aprobado = request.getParameter("aprobado");
        String motivo = request.getParameter("motivo");
        String estado = request.getParameter("estado");
        String axEstado = estado;
        if(estado.compareTo("4")==0 && aprobado.compareTo("0")==0){
            axEstado = "2";
        }
        if(estado.compareTo("5")==0 && aprobado.compareTo("0")==0){
            axEstado = "7";
        }
        if(estado.compareTo("6")==0 && aprobado.compareTo("0")==0){
            axEstado = "8";
        }
        
        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        
        String plan_mercadeo = "";
        String estado_actual = "3";
        String usuario_creacion = "";
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try{
            ResultSet rsPM = objPlanMercadeo.getPlanMercadeo(id_plan_mercadeo);
            if(rsPM.next()){
                plan_mercadeo = rsPM.getString("plan_mercadeo")!=null ? rsPM.getString("plan_mercadeo") : "";
                estado_actual = rsPM.getString("estado")!=null ? rsPM.getString("estado") : "3";
                usuario_creacion = rsPM.getString("usuario_creacion")!=null ? rsPM.getString("usuario_creacion") : "";
                rsPM.close();
            }
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }
        
        
        String para_creador = this.getEMail(usuario_creacion);
        String cedula_creador = this.getCedula(usuario_creacion);
        String empleado_creador[] = this.getEmpleado(cedula_creador);
        
        
        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String remitente = objConfiguracion.getValor("mail_remitente");
        //String para = objConfiguracion.getValor("mail_marketing");
        //String para_ventas = objConfiguracion.getValor("mail_ventas");
        String para_comercial = objConfiguracion.getValor("mail_comercial");
        String para_compras_internas = "";
        
        String asunto = "";
        String mensaje_creador = "";
        String mensaje_comercial = "";
        String mensaje_compras_internas = "";
        switch(Integer.parseInt(estado)){
            case 4:
                asunto = "AUTORIZACION DIRECTOR(A) DE MARKETING " + (aprobado.compareTo("1")==0 ? "ACEPTADA" : "RECHAZADA");
        
                if(aprobado.compareTo("0")==0){
                    para_comercial = "";
                    
                    mensaje_creador = "Estimado(a) Sr(a).<br />" +
                    "<strong>"+empleado_creador[0] + "<br />" +
                    empleado_creador[1] + "</strong><br /><br />" +      
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; ha sido <strong>RECHAZADO</strong> por el/la Director(a) de Marketing.<br /><br />" +
                    "El motivo del rechazo es: "+motivo+"<br />" +
                    "Por favor ingresar al sistema y realizar las modificaciones solicitadas.<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                }else{
                    mensaje_creador = "Estimado(a) Sr(a).<br />" +
                    "<strong>"+empleado_creador[0] + "<br />" +
                    empleado_creador[1] + "</strong><br /><br />" +        
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; ha sido <strong>APROBADO</strong> por el/la Director(a) de Marketing.<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                    
                    mensaje_comercial = "Estimado(a) Sr(a).<br />" +
                    "<strong>COORDINADOR(A) COMERCIAL</strong><br /><br />" +
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; est&aacute; listo para su revisi&oacute;n<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                }
            break;
                
            case 5:
                asunto = "AUTORIZACION DIRECTOR(A) DE VENTAS " + (aprobado.compareTo("1")==0 ? "ACEPTADA" : "RECHAZADA");
        
                if(aprobado.compareTo("0")==0){
                    para_comercial = "";
                    
                    mensaje_creador = "Estimado(a) Sr(a).<br />" +
                    "<strong>"+empleado_creador[0] + "<br />" +
                    empleado_creador[1] + "</strong><br /><br />" +       
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; ha sido <strong>RECHAZADO</strong> por el/la Director(a) de Ventas.<br /><br />" +
                    "El motivo del rechazo es: "+motivo+"<br />" +
                    "Por favor ingresar al sistema y realizar las modificaciones solicitadas.<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                }else{
                    mensaje_creador = "Estimado(a) Sr(a).<br />" +
                    "<strong>"+empleado_creador[0] + "<br />" +
                    empleado_creador[1] + "</strong><br /><br />" +         
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; ha sido <strong>APROBADO</strong> por el/la Director(a) de Ventas.<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                    
                    mensaje_comercial = "Estimado(a) Sr(a).<br />" +
                    "<strong>COORDINADOR(A) COMERCIAL</strong><br /><br />" +
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; est&aacute; listo para su revisi&oacute;n<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                }
            case 6:
                para_comercial = "";
                
                asunto = "AUTORIZACION COORDINADOR(A) COMERCIAL " + (aprobado.compareTo("1")==0 ? "ACEPTADA" : "RECHAZADA");
                
                mensaje_creador = "Estimado(a) Sr(a).<br />" +
                "<strong>"+empleado_creador[0] + "<br />" +
                empleado_creador[1] + "</strong><br /><br />" +        
                "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; ha sido <strong>APROBADO</strong> por el/la Coordinador(a) Comercial.<br /><br /><br />" +
                "Atentamente,<br />" +
                "<strong>"+empleado + "<br />" +
                cargo+"</strong>";
                if(aprobado.compareTo("0")==0){
                    mensaje_creador = "Estimado(a) Sr(a).<br />" +
                    "<strong>"+empleado_creador[0] + "<br />" +
                    empleado_creador[1] + "</strong><br /><br />" +        
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; ha sido <strong>RECHAZADO</strong> por el/la Coordinador(a) Comercial.<br /><br />" +
                    "El motivo del rechazo es: "+motivo+"<br />" +
                    "Por favor ingresar al sistema y realizar las modificaciones solicitadas.<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                }else{
                    para_compras_internas = objConfiguracion.getValor("mail_compras_internas");
                    mensaje_compras_internas = "Estimado(a) Sr(a).<br />" +
                    "<strong>JEFE DE ADQUISICIONES</strong><br /><br />" +      
                    "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; ha sido <strong>APROBADO</strong> por el/la Coordinador(a) Comercial.<br /><br /><br />" +
                    "Atentamente,<br />" +
                    "<strong>"+empleado + "<br />" +
                    cargo+"</strong>";
                }
            break;    
            
        }
        
        try {
            if(objPlanMercadeo.aprobar(id_plan_mercadeo, usuario, axEstado, motivo)){
                if(Correo.enviar(this._svr_mail, remitente, para_creador, "", "", asunto, mensaje_creador, true)){
                    res = "fun»_R('bloq_vta1');_R('vta1');calendario("+estado+");^id»-1^msg»Información ingresada satisfactoriamente.";
                    if(mensaje_comercial.compareTo("")!=0 && para_comercial.compareTo("")!=0){
                        if(!Correo.enviar(this._svr_mail, remitente, para_comercial, "", "", asunto, mensaje_comercial, true)){
                            res += " " + objPlanMercadeo.getError();
                        }
                    }
                    if(mensaje_compras_internas.compareTo("")!=0 && para_compras_internas.compareTo("")!=0){
                        if(!Correo.enviar(this._svr_mail, remitente, para_compras_internas, "", "", asunto, mensaje_compras_internas, true)){
                            res += " " + objPlanMercadeo.getError();
                        }
                    }
                }else{
                    res = "msg»" + Correo.getError();
                    objPlanMercadeo.aprobar(id_plan_mercadeo, "", estado_actual, "");
                }
            }else{
                res = "msg»" + objPlanMercadeo.getError();
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
