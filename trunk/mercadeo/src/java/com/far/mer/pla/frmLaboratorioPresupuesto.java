/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.far.mer.pla.clas.TipoPlan;

/**
 *
 * @author Jorge
 */
public class frmLaboratorioPresupuesto extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
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

        String id = request.getParameter("id");

        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);

        try {
            out.print("obj»vta1_html^foc»monto^frm»");
            out.print("<form action=\"frmLaboratorioPresupuestoGuardar\" onsubmit=\"return mer_asignarPresupuestoGuardar(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_lab\" name=\"id_lab\" value=\""+id+"\" />");
            out.print("<input type=\"hidden\" id=\"tope\" name=\"tope\" value=\"0\" />");
            
            out.print("<p>Monto: <input type=\"text\" id=\"monto\" name=\"monto\" size=\"10\" maxlength=\"13\" value=\"\" onkeypress=\"_evaluar(event, '0123456789.')\" /></p>");
            
            
            //  asignacion de presupuestos a tipos de plan

                out.print("<div class=\"tabla\">");
                    out.print("<div class=\"jm_filaImp\">");
                    out.print("<div class=\"columnaTH\" style=\"width:110px\">ABR.</div>");
                    out.print("<div class=\"columnaTH\" style=\"width:340px\">NOMBRE</div>");
                    out.print("<div class=\"columnaTH\" style=\"width:80px\">PRES.</div>");
                    //out.print("<div class=\"columnaTH\" style=\"width:25px\">&nbsp;</div>");
                    out.print("</div>");
                out.print("</div>");

                out.print("<div id=\"asTiPl\" class=\"tabla borde\">");
                try{
                    int i=0;
                    ResultSet rsTipoPlanes = objTipoPlan.getTipoPlanes();
                    String id_tipo_plan = "";
                    String nombre = "";
                    String presupuesto = "0";
                    while(rsTipoPlanes.next()){
                        id_tipo_plan = rsTipoPlanes.getString("id_tipo_plan")!=null ? rsTipoPlanes.getString("id_tipo_plan") : "";
                        nombre = rsTipoPlanes.getString("nombre")!=null ? rsTipoPlanes.getString("nombre") : "";
                        out.print("<div class=\"jm_filaImp\" id=\"asF"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                        "<div class=\"columna\" style=\"width:110px\"><input type=\"hidden\" id=\"ax_id_tipo_plan"+i+"\" name=\"ax_id_tipo_plan"+i+"\" value=\""+id_tipo_plan+"\" />"+id_tipo_plan+"</div>"+
                        "<div class=\"columna\" style=\"width:340px\">"+nombre+"</div>"+
                        "<div class=\"columna\" style=\"width:80px;text-align:right\"><input class=\"caja\" type=\"text\" id=\"ax_presupuesto"+i+"\" name=\"ax_presupuesto"+i+"\" value=\""+presupuesto+"\" style=\"width:78px;\" onkeypress=\"_evaluar(event, '0123456789.')\" /></div>"+
                        "</div>");
                        i++;
                    }
                    rsTipoPlanes.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                out.print("</div>");
                
            out.print("<p><input type=\"submit\" value=\"Registrar asignaci&oacute;n de presupuestos\" /></p>");
            out.print("</form>");
            
        } finally {
            objTipoPlan.cerrar();
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
