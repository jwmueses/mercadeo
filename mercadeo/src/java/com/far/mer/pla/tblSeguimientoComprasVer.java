/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.Actividad;
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
public class tblSeguimientoComprasVer extends HttpServlet {

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

        String idAct = request.getParameter("idAct");
        String totPre = request.getParameter("totPre");
        //String totInc = request.getParameter("totInc");
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsAct = objActividad.getFacturas(idAct);
        try {
            float total_gasto = 0;
            out.print("obj»vta1_html^frm»");
            
            try{
                int i=0;
                out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
                out.print("<tr class=\"jm_fila_cab\">"
                        + "<th class=\"jm_TH\" style=\"width:84px\">FECHA</th>"
                        + "<th class=\"jm_TH\" style=\"width:154px\">PROVEEDOR</th>"
                        + "<th class=\"jm_TH\" style=\"width:204px\">DETALLE</th>"
                        + "<th class=\"jm_TH\" style=\"width:124px\">No. DOC.</th>"
                        //+ "<th class=\"jm_TH\" style=\"width:74px\">CANTIDAD</th>"
                        + "<th class=\"jm_TH\" style=\"width:74px\">TOTAL</th>"
                        + "</tr>");
                while(rsAct.next()){
                    String fecha = rsAct.getString("fecha1")!=null ? rsAct.getString("fecha1") : "";
                    String proveedor = rsAct.getString("proveedor")!=null ? rsAct.getString("proveedor") : "";
                    String detalle = rsAct.getString("detalle")!=null ? rsAct.getString("detalle") : "";
                    String num_documento = rsAct.getString("num_documento")!=null ? rsAct.getString("num_documento") : "";
                    //String cantidad = rsAct.getString("cantidad")!=null ? rsAct.getString("cantidad") : "";
                    float total = rsAct.getString("total")!=null ? rsAct.getFloat("total") : 0;
                    total_gasto += total;
                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                    out.print("<td><input id=\"fac_fecha"+i+"\" type=\"text\" class=\"texto\" style=\"width:80px\" value=\""+fecha+"\" readonly /></td>");
                    out.print("<td><input id=\"fac_proveedor"+i+"\" type=\"text\" maxlength=\"160\" class=\"texto\" style=\"width:150px\" value=\""+proveedor+"\" readonly /></td>");
                    out.print("<td><input id=\"fac_detalle"+i+"\" type=\"text\" class=\"texto\" style=\"width:200px\" value=\""+detalle+"\" readonly /></td>");
                    out.print("<td><input id=\"fac_num_documento"+i+"\" type=\"text\" class=\"texto\" maxlength=\"20\" style=\"width:120px\" value=\""+num_documento+"\" readonly /></td>");
                    //out.print("<td><input id=\"fac_cantidad"+i+"\" type=\"text\" class=\"caja\" style=\"width:70px\" value=\""+cantidad+"\" readonly /></td>");
                    out.print("<td><input id=\"fac_total"+i+"\" type=\"text\" class=\"caja\" style=\"width:70px\" value=\""+total+"\" readonly /></td>");
                    out.print("</tr>");
                    i++;
                }
                out.print("</table>");
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
            out.print("<p class=\"psPanelGris\">Total presupuesto ($): <input id=\"totPre\" type=\"text\" class=\"caja\" style=\"width:100px\" value=\""+totPre+"\" readonly />"
                    + " &nbsp;&nbsp;&nbsp; Total gastos ($): <input id=\"totGasto\" type=\"text\" class=\"caja\" style=\"width:100px\" value=\""+total_gasto+"\" readonly />"
                    + " &nbsp;&nbsp;&nbsp; Diferencia ($): <input id=\"difer\" type=\"text\" class=\"caja\" style=\"width:100px\" value=\""+(Float.parseFloat(totPre) - total_gasto)+"\" readonly /></p>");
            
        } finally {
            objActividad.cerrar();
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
