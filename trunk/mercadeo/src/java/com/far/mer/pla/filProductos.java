/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.DatosDinamicos;
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
public class filProductos extends HttpServlet {
    private String easy_ip = null;
    private int easy_puerto = 1433;
    private String easy_db = null;
    private String easy_usuario = null;
    private String easy_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this.easy_ip = config.getServletContext().getInitParameter("EASY_IP");
        this.easy_puerto = Integer.parseInt(config.getServletContext().getInitParameter("EASY_PUERTO"));
        this.easy_db = config.getServletContext().getInitParameter("EASY_DB");
        this.easy_usuario = config.getServletContext().getInitParameter("EASY_USUARIO");
        this.easy_clave = config.getServletContext().getInitParameter("EASY_CLAVE");
    }
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
        
        int alto = Integer.parseInt( request.getParameter("al") );
        String grupo = request.getParameter("grupo");
        
        BaseDatos objEasy = new BaseDatos(this.easy_ip, this.easy_puerto, this.easy_db, this.easy_usuario, this.easy_clave);
        ResultSet rsClases = objEasy.consulta("select clase, descripcion from tbl_clases with (nolock) order by descripcion");
        
        try {
            out.print("obj»vta3_html^foc»txtPro^fun»mer_setNavegacion('axNavProd');^frm»");
            out.print("<form id=\"frm3\" action=\"getTblProductos\" onsubmit=\"return Ajax.enviarForm(this, true)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"pag\" name=\"pag\" value=\"0\" />");
            out.print("<table width=\"100%\" >");
            out.print("<tr>");
            out.print("<td colspan=\"4\"><div id=\"axNivel\"></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td nowrap><span>Categor&iacute;a: </span>"
                    + "<span id=\"axNivel1\">"
                    + "<input type=\"hidden\" id=\"ax_cod_nivel\" name=\"ax_cod_nivel\" value=\"-1\" />"
                    + "<input type=\"text\" id=\"ax_desc_nivel\" value=\"TODAS\" readonly onclick=\"mer_getNiveles('axNivel');_('txtPro').value='';\" />"
                    + "</span></td>");
            out.print("<td nowrap>Laboratorio: "+DatosDinamicos.combo(rsClases, "ax_clase", "", "mer_getLineas();_('txtPro').value='';", "TODAS", 200)+"</td>");
            out.print("<td nowrap colspan=\"2\">L&iacute;nea: <span id=\"axLineas\">TODAS<input type=\"hidden\" id=\"ax_linea\" name=\"ax_linea\" value=\"0\" /></span></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td colspan=\"2\"><input type=\"button\" value=\"Agregar Filtro\" onclick=\"mer_agregarProductosFiltro("+grupo+")\" /></td>");
            out.print("<td colspan=\"2\" align=\"right\"><input type=\"button\" value=\"Traer productos\" onclick=\"_('pag').value=_('axd_nav_pag').value=0;Ajax.enviarForm(_('frm3'));\" /></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td colspan=\"4\"><hr /></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td>Producto: ");
            out.print("<input type=\"text\" id=\"txtPro\" name=\"txtPro\" style=\"width:200px\" class=\"buscar\" onfocus=\"this.select();\" onkeyup=\"if(_gKC(event)==13){_('pag').value=_('axd_nav_pag').value=0;Ajax.enviarForm(_('frm3'));}\" onkeydown=\"_NoE(event);\" title=\"producto\" />");
            out.print("</td>");
            out.print("<td colspan=\"2\" id=\"axNavProd\"></td>");
            
            out.print("</td>");
            out.print("<td align=\"right\"><input type=\"button\" value=\"Agregar productos\" onclick=\"mer_agregarProductos("+grupo+")\" /></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td colspan=\"4\"><div style=\"width:750px;font-weight:bold;border:1px solid #CCC\"><input type=\"checkbox\" onclick=\"mer_marcar(this.checked)\" /> &nbsp;&nbsp;&nbsp; PRODUCTOS</div>"
                    + "<div id=\"axProductosFil\" style=\"overflow:auto;width:750px;height:"+(alto-245)+"px;border:1px solid #CCC\"></td>");
            out.print("</tr>");
            
            out.print("</table>");
            out.print("</form>");
        } finally {            
            objEasy.cerrar();
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
