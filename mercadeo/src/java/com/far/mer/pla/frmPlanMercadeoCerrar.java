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
public class frmPlanMercadeoCerrar extends HttpServlet {

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

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        
        String id_plan_mercadeo = request.getParameter("id");
        String desde = request.getParameter("d");
        
        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        /*try{
            ResultSet rsAct = objActividad.getActividadPlanMercadeo(id_actividad);
            if(rsAct.next()){
                actividad = rsAct.getString("actividad")!=null?rsAct.getString("actividad"):"";
                plan_mercadeo = rsAct.getString("plan_mercadeo")!=null?rsAct.getString("plan_mercadeo"):"";
                usuario_creacion = rsAct.getString("usuario_creacion")!=null?rsAct.getString("usuario_creacion"):"";
                rsAct.close();
            }
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }
        
        
        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String remitente = objConfiguracion.getValor("mail_remitente");
        
        String para_ususario_creador = "";
        BaseDatos objDB = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        try{
            ResultSet rs = objDB.consulta("select Email from usuarios with (nolock) where lower(NombreCorto) = '"+usuario_creacion+"'");
            if(rs.next()){
                para_ususario_creador = rs.getString("Email")!=null?rs.getString("Email"):"";
                rs.close();
            }
        }catch(Exception e){
            res = "msg»" + e.getMessage();
        }finally{
            objDB.cerrar();
        }
        
        String asunto = "AUTORIZACION GERENTE DE OPERACIONES " + (aprobada.compareTo("1")==0 ? "ACEPTADA" : "RECHAZADA");
        
        String mensaje = "Sr(a).\n" +
        "ADMINISTRADOR(A) DE PLANES DE MERCADEO\n" +
        "Por favor revisar la actividad: "+actividad+" del plan de mercadeo con nombre: "+plan_mercadeo+" el mismo que ya fue aprobado por la GERENCIA DE OPERACIONES.\n" +
        "\n" +
        "\n" +
        "Att.\n" +
        empleado + "\n" +
        "GERENTE DE OPERACIONES";
        if(aprobada.compareTo("0")==0){
            mensaje = "Sr(a).\n" +
            "ADMINISTRADOR(A) DE PLANES DE MERCADEO\n" +
            "Por favor revisar la actividad: "+actividad+" del plan de mercadeo con nombre: "+plan_mercadeo+" el mismo que fue rechazado por la GERENCIA DE OPERACIONES.\n" +
            "El motivo del rechazo es: "+motivo+"\n" +
            "\n" +
            "Att.\n" +
            empleado + "\n" +
            "GERENTE DE OPERACIONES";
        }*/
        
        try {
            //if(!objPlanMercadeo.hayAuspiciosSinConfirmar(id_plan_mercadeo)){
                if(objPlanMercadeo.cerrar(id_plan_mercadeo)){
                    //if(Correo.enviar(this._svr_mail, remitente, para_ususario_creador, "", "", asunto, mensaje, false)){
                        if(desde.compareTo("l")==0){
                            String WHERE = request.getParameter("WHERE");
                            String p = request.getParameter("p") != null ? request.getParameter("p") : "0";
                            res = "tbl»"+objPlanMercadeo.paginarJSON("tbl_plan_mercadeo", "id_plan_mercadeo,sec_tipo_plan,plan_mercadeo,usuario_creacion", WHERE, Integer.parseInt(p), 100)+
                                    "^id»-1^msg»Plan de Mercadeo cerrado satisfactoriamente.";
                        }
                        if(desde.compareTo("s")==0){
                            res = "fun»_R('bloq_vta1');_R('vta1');^id»-1^msg»Plan de Mercadeo cerrado satisfactoriamente.";
                        }
                    /*}else{
                        res = "msg»" + Correo.getError();
                        objPlanMercadeo.habilitar(id_plan_mercadeo);
                    }*/
                }else{
                    res = "msg»" + objPlanMercadeo.getError();
                }
            /*}else{
                res = "msg»No se puede cerrar el Plan de Mercadeo debido a que existen auspicios sin confirmar.";
            }*/
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        }finally{
            //objConfiguracion.cerrar();
            objPlanMercadeo.cerrar();
        }
        
        try{
            out.print(res);
        }finally {
            out.close();
        }
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
