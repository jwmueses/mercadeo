/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.Estrategia;
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
public class frmEstrategiaEliminar extends HttpServlet {

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
        Estrategia objEstrategia = new Estrategia(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try {
            String id = request.getParameter("id");
            String id_plan_mercadeo = request.getParameter("id_plan_mercadeo");

            if(objEstrategia.eliminar(id)){
                res = "obj»axEstrategias^fun»_('pm_d_3').innerHTML=_('pm_d_5').innerHTML='';^frm»"+this.getEstrategias(objEstrategia, id_plan_mercadeo);
            }else{
                res = "msg»" + objEstrategia.getError();
            }
           
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        }finally{
            objEstrategia.cerrar();
        }
        
        try{
            out.print(res);
        }finally {
            out.close();
        }
    } 
    
    public String getEstrategias(Estrategia objEstrategia, String id_plan_mercadeo)
    {
        String html = "";
        try{
            ResultSet rsEstrategias = objEstrategia.getEstrategias(id_plan_mercadeo);
            String id_estrategia = "";
            String estrategia = "";
            int i=0;
            String num_actividades = "";
            float pre_totales = 0;
            float sumatoria = 0;
            html = "<table>";
            html += "<tr><th width=\"300\">ESTRATEGIA</th><th width=\"50\"># ACT.</th><th width=\"70\">TOTALES</th><th width=\"25\">&nbsp;</th></tr>";
            while(rsEstrategias.next()){
                id_estrategia = rsEstrategias.getString("id_estrategia")!=null ? rsEstrategias.getString("id_estrategia") : "";
                estrategia = rsEstrategias.getString("estrategia")!=null ? rsEstrategias.getString("estrategia") : "";
                num_actividades = rsEstrategias.getString("num_actividades")!=null ? rsEstrategias.getString("num_actividades") : "";
                pre_totales = rsEstrategias.getString("pre_totales")!=null ? rsEstrategias.getFloat("pre_totales") : 0;
                html += "<tr style=\"cursor:pointer\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">";
                html += "<td onclick=\"mer_estrategiaEditar("+id_estrategia+")\"><input type=\"hidden\" id=\"id_estrategia"+i+"\" name=\"id_estrategia"+i+"\" value=\""+id_estrategia+"\" />"+estrategia+"</td>";
                html += "<td align=\"center\" onclick=\"mer_estrategiaEditar("+id_estrategia+")\">"+num_actividades+"</td>";
                html += "<td align=\"right\" onclick=\"mer_estrategiaEditar("+id_estrategia+")\">"+pre_totales+"</td>";
                html += "<td align=\"right\"> <div class=\"borrar\" title=\"Eliminar\" onclick=\"mer_estrategiaEliminar("+id_estrategia+");\">&nbsp;</div></td>";
                html += "</tr>";
                sumatoria += pre_totales;
                i++;
            }
            html += "<tr><td colspan=\"3\" align=\"right\">"+sumatoria+"</th><th>&nbsp;</th></tr>";
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
