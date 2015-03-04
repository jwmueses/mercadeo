/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.TipoPlan;
import com.far.mer.seg.clas.Usuario;
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
public class frmTipoPlanAsignacion extends HttpServlet {

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

        Usuario objUsuario = new Usuario(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);

        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);

        String nombre = "";
        try{
            ResultSet rs = objTipoPlan.getTipoPlan(id);
            if(rs.next()){
                nombre = rs.getString("nombre")!=null ? rs.getString("nombre") : "";
                rs.close();
            }
        }catch(Exception e){
            msg = e.getMessage();
        }

        try {
            out.print("obj»d_3^msg»"+msg+"^foc»txtUs^frm»");
            out.print("<form action=\"frmTipoPlanAsignacionGuardar\" onsubmit=\"return mer_asignacionGuardar(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_tipo_plan\" name=\"id_tipo_plan\" value=\""+id+"\" />");
            out.print("<input type=\"hidden\" id=\"tope\" name=\"tope\" value=\"0\" />");
            out.print("<h3>ASIGNACION DE TIPO DE PLAN A USUARIOS</h3>");
            out.print("<table width=\"470\">");
            out.print("<tr>");
            out.print("<td width=\"80\">Tipo de plan: </td>");
            out.print("<td width=\"390\">"+nombre+"</td>");
            out.print("</tr>");
            
            out.print("<tr><td colspan=\"2\"><br />");
            out.print("<div>");
            out.print("<div id=\"axTxt\" class=\"h31\">USUARIOS: &nbsp; ");
            out.print("<input type=\"text\" id=\"txtUs\" size=\"46\" class=\"buscar\" onfocus=\"this.value='';\" onkeyup=\"mer_getUsuarios();\" onkeydown=\"_NoE(event);\" title=\"Busqueda por nombre corto o nombres\" />");
            out.print("</div></div>");
            out.print("</td></tr>");
            
            out.print("<tr><td colspan=\"2\">");
            out.print("<table>");
            out.print("<tr>");
            out.print("<th width=\"120\">USUARIO</th><th width=\"300\">NOMBRES</th><th width=\"25\">&nbsp;</th>");
            out.print("</tr>");
            out.print("</table>");
            out.print("<div id=\"axUsr\" class=\"tabla borde\">");
            try{
                int i=0;
                String alias = "";
                String usuario = "";
                ResultSet rs = objTipoPlan.getUsuarios(id);
                while(rs.next()){
                    alias = rs.getString("alias")!=null ? rs.getString("alias") : "";
                    usuario = rs.getString("usuario")!=null ? rs.getString("usuario") : "";
                    out.print("<div class=\"jm_filaImp\" id=\"f"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                    "<div class=\"columna\" style=\"width:120px\"><input type=\"hidden\" id=\"a"+i+"\" name=\"a"+i+"\" value=\""+alias+"\" />"+alias+"</div>"+
                    "<div class=\"columna\" style=\"width:300px\"><input type=\"hidden\" id=\"u"+i+"\" name=\"u"+i+"\" value=\""+usuario+"\" />"+usuario+"</div>"+
                    "<div class=\"columna\" style=\"width:25px\"><div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('f"+i+"');\">&nbsp;</div></div>"+
                    "</div>");
                    i++;
                }
                rs.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            out.print("</div>");
            /*try{
                out.print("<div class=\"tabla borde\">");
                out.print("<div class=\"jm_filaImp\">");
                out.print("<div class=\"columnaTH\" style=\"width:25px\">&nbsp;</div>");
                out.print("<div class=\"columnaTH\" style=\"width:370px\">USUARIO</div>");
                out.print("</div>");
                int i =0;
                String alias = "";
                String empleado = "";
                ResultSet rsUsuarios = objUsuario.getUsuarios();
                ResultSet rsAsignaciones = objTipoPlan.getTipoPlanUsuarios(id);
                String matUsuarios[][] = Matriz.ResultSetAMatriz(rsAsignaciones);
                while(rsUsuarios.next()){
                    alias = rsUsuarios.getString(1)!=null ? rsUsuarios.getString(1) : "";
                    empleado = rsUsuarios.getString(2)!=null ? rsUsuarios.getString(1) : "";
                    out.print("<div class=\"jm_filaImp\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                    out.print("<div class=\"columna\" style=\"width:25px\"><input type=\"checkbox\" id=\"ch"+i+"\" name=\"ch"+i+"\" "+(Matriz.enMatriz(matUsuarios, alias, 1)!=-1 ? "checked":"")+" onclick=\"mer_setAsignacionUsuario("+i+")\" /></div>");
                    out.print("<div class=\"columna\" style=\"width:370px\"><input type=\"hidden\" id=\"us"+i+"\" name=\"us"+i+"\" value=\""+alias+"\" />"+empleado+"</div>");
                    out.print("</div>");
                    i++;
                }
                out.print("</div>");
                rsUsuarios.close();
            }catch(Exception e){
                e.printStackTrace();
            }*/
            out.print("</td></tr>");

            out.print("<tr>");
            out.print("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Guardar\" /></td>");
            out.print("</tr>");
            
            out.print("</table>");
            out.print("</form>");
        } finally {
            objTipoPlan.cerrar();
            objUsuario.cerrar();
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
