/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.ActividadTipo;
import com.far.mer.pla.clas.Concepto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
public class frmConcepto extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String pago_ip = null;
    private int pago_puerto = 1433;
    private String pago_db = null;
    private String pago_usuario = null;
    private String pago_clave = null;
    

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.pago_ip = config.getServletContext().getInitParameter("PAGO_IP");
        this.pago_puerto = Integer.parseInt(config.getServletContext().getInitParameter("PAGO_PUERTO"));
        this.pago_db = config.getServletContext().getInitParameter("PAGO_DB");
        this.pago_usuario = config.getServletContext().getInitParameter("PAGO_USUARIO");
        this.pago_clave = config.getServletContext().getInitParameter("PAGO_CLAVE");
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
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        Concepto objConcepto = new Concepto(this.pago_ip, this.pago_puerto, this.pago_db, this.pago_usuario, this.pago_clave);
        //ResultSet rsConceptos = objConcepto.getConceptos();
        
        ActividadTipo objActividadTipo = new ActividadTipo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        //objActividadTipo.setConceptos(rsConceptos);
        
        String id = request.getParameter("id");
        
        String monto = "";
        String concepto = "";
                
        if(id.compareTo("-1")!=0){
            try{
                ResultSet rsTipo = objActividadTipo.getTipoPlan(id);
                if(rsTipo.next()){
                    monto = rsTipo.getString("monto")!=null ? rsTipo.getString("monto") : "";
                    concepto = rsTipo.getString("concepto")!=null ? rsTipo.getString("concepto") : "";
                    rsTipo.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        try {
            out.print("obj»d_3^frm»");
            out.print("<form action=\"frmConceptoGuardar\" onsubmit=\"return mer_conceptoGuardar(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id\" name=\"id\" value=\""+id+"\" />");
            out.print("<h3>ADMINISTRACION DE MONTOS POR CONCEPTOS</h3>");
            out.print("<table cellpadding=\"2\" width=\"100%\">");
            out.print("<tr>");
            out.print("<td>Concepto:</td>");
            out.print("<td>"+concepto+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Monto: <span class=\"marca\">*</span></td>");
            out.print("<td><input type=\"text\" id=\"monto\" name=\"monto\" size=\"10\" value=\""+monto+"\"  onkeypress=\"_evaluar(event, '0123456789.')\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Guardar\" /></td>");
            out.print("</tr>");
            
            out.print("</table>");
            out.print("</form>");
        } finally {
            objActividadTipo.cerrar();
            objConcepto.cerrar();
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
