/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
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
public class tblActividadesEjecucion extends HttpServlet {

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
        HttpSession sesion = request.getSession(true);
        String usuario = (String)sesion.getAttribute("usuario");
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String idPlanMercadeos = request.getParameter("idPM");
        int altEdi = Integer.parseInt(request.getParameter("altEdi"));
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        try {
            String id_actividad = "";
            String actividad = ""; 
            String usuario_seg = ""; 
            String usuario_eje = ""; 
            out.print("obj»d_1^frm»");
            out.print("<form id=\"frmConfActs\" action=\"tblActividadesEjecucionConfirmar\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"idPlanMercadeo\" name=\"idPlanMercadeo\" />");
            try{
                int i=0;
                ResultSet rsAct = objPlanMercadeo.getActividadesEjecucion(idPlanMercadeos, usuario);
                out.print("<table cellspacing=\"0\" cellpadding=\"2\" border=\"0\">");
                out.print("<tr class=\"jm_fila_cab\">"
                        + "<th class=\"jm_TH\" width=\"250px\">ACTIVIDAD</th>"
                        + "<th class=\"jm_TH\" width=\"65px\">REALIZADA</th>"
                        + "<th class=\"jm_TH\" width=\"250px\">COMENTARIO</th>"
                        + "</tr>"
                        + "</table>");
                out.print("<div style=\"overflow:auto;width:610px;height:"+(altEdi-30)+"px;\">"
                        + "<table cellspacing=\"0\" cellpadding=\"2\" border=\"0\">");
                while(rsAct.next()){
                    id_actividad = rsAct.getString("id_actividad")!=null ? rsAct.getString("id_actividad") : "";
                    actividad = rsAct.getString("actividad")!=null ? rsAct.getString("actividad") : "";
                    usuario_seg = rsAct.getString("usuario_seg")!=null ? rsAct.getString("usuario_seg") : "";
                    usuario_eje = rsAct.getString("usuario_eje")!=null ? rsAct.getString("usuario_eje") : "";
                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                    out.print("<td width=\"250px\"><input type=\"hidden\" id=\"idAct"+i+"\" name=\"idAct"+i+"\" value=\""+id_actividad+"\" />"
                            + "<input type=\"hidden\" id=\"usSeg"+i+"\" name=\"usSeg"+i+"\" value=\""+usuario_seg+"\" />"
                            + "<input type=\"hidden\" id=\"usEje"+i+"\" name=\"usEje"+i+"\" value=\""+usuario_eje+"\" />"
                            + "<input type=\"hidden\" id=\"actividad"+i+"\" name=\"actividad"+i+"\" value=\""+actividad+"\" />"+actividad+"</td>");
                    out.print("<td width=\"65px\" align=\"center\"><input type=\"checkbox\" id=\"chIdAct"+i+"\" name=\"chIdAct"+i+"\" /></td>");
                    out.print("<td width=\"250px\"><textarea id=\"comentario"+i+"\" name=\"comentario"+i+"\" style=\"width:240px;height:25px\"></textarea></td>");
                    out.print("</tr>");
                    i++;
                }
                out.print("</table><div>");
            }catch(Exception ex){
                ex.printStackTrace();
            }
            out.print("</form>");
            
        } finally {
            objPlanMercadeo.cerrar();
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
