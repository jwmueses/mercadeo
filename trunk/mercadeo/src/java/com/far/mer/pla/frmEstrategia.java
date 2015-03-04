/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.Actividad;
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
public class frmEstrategia extends HttpServlet {

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

        String msg = "";
        String id = request.getParameter("id");
        String id_plan_mercadeo = request.getParameter("id_plan_mercadeo");
        int estado = Integer.parseInt( request.getParameter("estado") );
        int plan_completo = Integer.parseInt( request.getParameter("plan_completo") );
        
        boolean modificar = (estado==1 || estado==2) && plan_completo==0 ? true : false;
        String lectura = (estado==1 || estado==2) && plan_completo==0 ? "" : "readonly";

        String concepto = "";
        String estrategia = "";
        String tactica = "";
        Estrategia objEstrategia = new Estrategia(this._ip, this._puerto, this._db, this._usuario, this._clave);

        if(id.compareTo("-1")!=0){
            try{
                ResultSet rs = objEstrategia.getEstrategia(id);
                if(rs.next()){
                    concepto = rs.getString("concepto")!=null ? rs.getString("concepto") : "";
                    estrategia = rs.getString("estrategia")!=null ? rs.getString("estrategia") : "";
                    tactica = rs.getString("tactica")!=null ? rs.getString("tactica") : "";
                    rs.close();
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
        }

        try {
            out.print("obj»pm_d_3^foc»estrategia^msg»"+msg+"^frm»");
            out.print("<form action=\"frmEstrategiaGuardar\" onsubmit=\"return mer_estrategiaGuardar(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_estrategia\" name=\"id_estrategia\" value=\""+id+"\" />");
            out.print("<input type=\"hidden\" id=\"id_plan_mercadeo1\" name=\"id_plan_mercadeo1\" value=\""+id_plan_mercadeo+"\" />");
            out.print("<input type=\"hidden\" id=\"estado1\" name=\"estado1\" value=\""+estado+"\" />");
            out.print("<input type=\"hidden\" id=\"plan_completo1\" name=\"plan_completo1\" value=\""+plan_completo+"\" />");
            out.print("<h3>FORMULARIO DE ESTRATEGIA</h3>");
            out.print("<table>");
            out.print("<tr>");
            out.print("<td nowrap>Estrategia: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"estrategia\" name=\"estrategia\" size=\"45\" maxlength=\"60\" value=\""+estrategia+"\" "+lectura+" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan=\"2\">Concepto: <span class=\"marca\">*</span></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan=\"2\"><textarea id=\"concepto\" style=\"width:380px;height:100px\" name=\"concepto\" "+lectura+">"+concepto+"</textarea></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td colspan=\"2\">T&aacute;cticas: <span class=\"marca\">*</span></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan=\"2\"><textarea id=\"tactica\" name=\"tactica\" style=\"width:380px;height:100px\" "+lectura+">"+tactica+"</textarea></td>");
            out.print("</tr>");
            
            if(id.compareTo("-1")!=0){

                out.print("<tr><td colspan=\"2\">");

                    if(modificar){
                        out.print("<div><input type=\"button\" value=\"Nueva Actividad\" onclick=\"mer_actividadEditar(-1)\" /></div>");
                    }
                    out.print("<div id=\"axActividades\" class=\"psPanelGris\" >");
                    Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
                    try{
                        ResultSet rsActividades = objActividad.getActividades(id);
                        int i=0;
                        String id_actividad = "";
                        String actividad = "";
                        float pre_total = 0;
                        float sumatoria = 0;
                        out.print("<table width=\"100%\">");
                        out.print("<tr><th width=\"300\">ACTIVIDAD</th><th>TOTAL</th><th>&nbsp;</th></tr>");
                        while(rsActividades.next()){
                            id_actividad = rsActividades.getString("id_actividad")!=null ? rsActividades.getString("id_actividad") : "";
                            actividad = rsActividades.getString("actividad")!=null ? rsActividades.getString("actividad") : "";
                            pre_total = rsActividades.getString("pre_total")!=null ? rsActividades.getFloat("pre_total") : 0;
                            out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                            out.print("<td style=\"cursor:pointer\" onclick=\"mer_actividadEditar("+id_actividad+")\"><input type=\"hidden\" id=\"id_actividad"+i+"\" name=\"id_actividad"+i+"\" value=\""+id_actividad+"\" />"+actividad+"</td>");
                            out.print("<td style=\"cursor:pointer\" align=\"right\" onclick=\"mer_actividadEditar("+id_actividad+")\">"+pre_total+"</td>");
                            out.print("<td align=\"right\">");
                            if(modificar){
                                out.print("<div class=\"borrar\" title=\"Eliminar\" onclick=\"mer_actividadEliminar("+id_actividad+");\">&nbsp;</div>");
                            }
                            out.print("</td></tr>");
                            sumatoria += pre_total;
                            i++;
                        }
                        out.print("<tr><td colspan=\"2\" align=\"right\">"+sumatoria+"</th><th>&nbsp;</th></tr>");
                        out.print("</table>");
                    }catch(Exception e){
                        msg = e.getMessage();
                    }finally{
                        objActividad.cerrar();
                    }
                    out.print("</div>");
                    
                out.print("</td></tr>");
            }
            
            if(modificar){
                out.print("<tr>");
                out.print("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Guardar\" /></td>");
                out.print("</tr>");
            }
            
            out.print("</table>");
            out.print("</form>");
        } finally {
            objEstrategia.cerrar();
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
