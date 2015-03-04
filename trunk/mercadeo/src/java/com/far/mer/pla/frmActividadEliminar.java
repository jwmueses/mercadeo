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
public class frmActividadEliminar extends HttpServlet {

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
        //HttpSession sesion = request.getSession(true);
        //String usuario = (String)sesion.getAttribute("usuario");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try {
            String id = request.getParameter("id");
            String id_estrategia = request.getParameter("id_estrategia");

            if(objActividad.eliminar(id)){
                res = "obj»axActividades^fun»mer_getEstrategias();_('pm_d_5').innerHTML='';^frm»"+this.getActividads(objActividad, id_estrategia);
            }else{
                res = "msg»" + objActividad.getError();
            }
           
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        }finally{
            objActividad.cerrar();
        }
        
        try{
            out.print(res);
        }finally {
            out.close();
        }
    } 
    
    public String getActividads(Actividad objActividad, String id_estrategia)
    {
        String html = "";
        try{
            ResultSet rsActividades = objActividad.getActividades(id_estrategia);
            int i=0;
            String id_actividad = "";
            String actividad = "";
            float pre_total = 0;
            float sumatoria = 0;
            html += "<table width=\"100%\">";
            html += "<tr><th width=\"300\">ACTIVIDAD</th><th>TOTAL</th><th>&nbsp;</th></tr>";
            while(rsActividades.next()){
                id_actividad = rsActividades.getString("id_actividad")!=null ? rsActividades.getString("id_actividad") : "";
                actividad = rsActividades.getString("actividad")!=null ? rsActividades.getString("actividad") : "";
                pre_total = rsActividades.getString("pre_total")!=null ? rsActividades.getFloat("pre_total") : 0;
                html += "<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">";
                html += "<td onclick=\"mer_actividadEditar("+id_actividad+")\"><input type=\"hidden\" id=\"id_actividad"+i+"\" name=\"id_actividad"+i+"\" value=\""+id_actividad+"\" />"+actividad+"</td>";
                html += "<td align=\"right\" onclick=\"mer_actividadEditar("+id_actividad+")\">"+pre_total+"</td>";
                html += "<td align=\"right\"> <div class=\"borrar\" title=\"Eliminar\" onclick=\"mer_actividadEliminar("+id_actividad+");\">&nbsp;</div></td>";
                html += "</tr>";
                sumatoria += pre_total;
                i++;
            }
            html += "<tr><td colspan=\"2\" align=\"right\">"+sumatoria+"</th><th>&nbsp;</th></tr>";
            html += "</table>";
        }catch(Exception e){
            e.printStackTrace();
        }
        return html;
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
