/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Correo;
import com.far.mer.pla.clas.Actividad;
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
public class tblActividadesEjecucionConfirmar extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String gene_ip = null;
    private int gene_puerto = 1433;
    private String gene_db = null;
    private String gene_usuario = null;
    private String gene_clave = null;
    
    private BaseDatos objDB = null;
    
    private String _svr_mail = null;
    private String remitente;
    
    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.gene_ip = config.getServletContext().getInitParameter("GENE_IP");
        this.gene_puerto = Integer.parseInt(config.getServletContext().getInitParameter("GENE_PUERTO"));
        this.gene_db = config.getServletContext().getInitParameter("GENE_DB");
        this.gene_usuario = config.getServletContext().getInitParameter("GENE_USUARIO");
        this.gene_clave = config.getServletContext().getInitParameter("GENE_CLAVE");
        
        this._svr_mail = config.getServletContext().getInitParameter("_SVR_MAIL");
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

        String id_plan_mercadeo = request.getParameter("planMercadeo");
        
        String id_actividad = "";
        String actividad = "";
        String comentario = "";
        String usuario_seg = "";
        String usuario_eje = "";
        String msg = "";
        
        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
        this.remitente = objConfiguracion.getValor("mail_remitente");
        
        this.objDB = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        try{
            int i=0;
            while(request.getParameter("chIdAct"+i)!=null){
                String ch = request.getParameter("chIdAct"+i);
                if(ch.compareTo("true")==0){
                    id_actividad = request.getParameter("idAct"+i);
                    actividad = request.getParameter("actividad"+i);
                    comentario = request.getParameter("comentario"+i);
                    usuario_seg = request.getParameter("usSeg"+i);
                    usuario_eje = request.getParameter("usEje"+i);
                    if(objActividad.setEjecucion(id_actividad, 1, "getDate()", comentario)){
                        String envio = this.enviarNotificacion(objPlanMercadeo, id_plan_mercadeo, actividad, usuario_seg, usuario_eje, empleado, cargo);
                        if(envio.compareTo("ok")!=0){
                            objActividad.setEjecucion(id_actividad, 0, "null", "");
                            msg = "^msg»La actividad " + actividad + " ha reportado un error en el envío del email abortando la operación. " + envio;
                            break;
                        }
                    }else{
                         msg = "^msg»La actividad " + actividad + " ha reportado un error abortando la operación. " + objActividad.getError();
                         break;
                    }
                }
                i++;
            }
        }catch(Exception e){
            msg = "^msg»" + e.getMessage();
        }

        
        try {
            out.print("^fun»mer_getActivicadesEjecucion()^msg»"+msg);
        } finally {
            this.objDB.cerrar();
            objConfiguracion.cerrar();
            objPlanMercadeo.cerrar();
            objActividad.cerrar();
            out.close();
        }
    }
    
    public String enviarNotificacion(PlanMercadeo objPlanMercadeo, String id_plan_mercadeo, String actividad, 
            String usuario_seg, String usuario_eje, String empleado, String cargo)
    {
        String plan_mercadeo = "";
        String usuario_creacion = "";
        try{
            ResultSet rsAct = objPlanMercadeo.getVerPlanMercadeo(id_plan_mercadeo);
            if(rsAct.next()){
                plan_mercadeo = rsAct.getString("plan_mercadeo")!=null?rsAct.getString("plan_mercadeo"):"";
                usuario_creacion = rsAct.getString("usuario_creacion")!=null ? rsAct.getString("usuario_creacion") : "";
                rsAct.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        String para_creador = this.getEMail(usuario_creacion);
        String para_ejecucion = this.getEMail(usuario_eje);
        String para_seguimiento[] = this.getEMailNombre(usuario_seg);
        String msg = "ok";
        
        String asunto = "NOTIFICACION DE CONFIRMACION DE EJECUCION DE ACTIVIDAD";
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Estimado(a) Sr(a).<br />");
        mensaje.append(para_seguimiento[1]);
        mensaje.append("<br /><br />Por medio de la presente le informo que la actividad: <strong>");
        mensaje.append(actividad);
        mensaje.append("</strong> perteneciente al Plan de Mercadeo <strong>");
        mensaje.append(plan_mercadeo);
        mensaje.append("</strong> ha sido confirmada su ejecuci&oacute;n.<br /><br /><br />");
        mensaje.append("Atentamente,<br /><strong>");
        mensaje.append(empleado);
        mensaje.append("<br />");
        mensaje.append(cargo);
        mensaje.append("</strong>");
        if(!Correo.enviar(this._svr_mail, this.remitente, para_creador, para_seguimiento[0], para_ejecucion, asunto, mensaje.toString(), true)){
            msg = Correo.getError();
        }
        return msg;
    }
    
    public String getEMail(String usuario)
    {
        try{
            ResultSet rs = this.objDB.consulta("select Email, Nombre from usuarios with (nolock) where lower(NombreCorto) = '"+usuario+"'");
            if(rs.next()){
                return rs.getString("Email")!=null?rs.getString("Email"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    
    public String[] getEMailNombre(String usuario)
    {
        String res[] = new String[]{"",""};
        try{
            ResultSet rs = this.objDB.consulta("select Email, Nombre from usuarios with (nolock) where lower(NombreCorto) = '"+usuario+"'");
            if(rs.next()){
                res[0] = rs.getString("Email")!=null?rs.getString("Email"):"";
                res[1] = rs.getString("Nombre")!=null?rs.getString("Nombre"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
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
