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
import com.far.lib.Fecha;
import com.far.mer.pla.clas.Laboratorio;

/**
 *
 * @author Jorge
 */
public class frmLaboratorioEstrategico extends HttpServlet {

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

        String numero_idproveedor = "";
        String nombre_comercial = "";
        int anio_vigencia = Fecha.getAnio();
        String monto = "";
        int estado = 1;
        Laboratorio objLaboratorio = new Laboratorio(this._ip, this._puerto, this._db, this._usuario, this._clave);

        if(id.compareTo("-1")!=0){
            try{
                ResultSet rs = objLaboratorio.getLaboratorio(id);
                if(rs.next()){
                    numero_idproveedor = rs.getString("numero_idproveedor")!=null ? rs.getString("numero_idproveedor") : "";
                    nombre_comercial = rs.getString("nombre_comercial")!=null ? rs.getString("nombre_comercial") : "";
                    anio_vigencia = rs.getString("anio_vigencia")!=null ? rs.getInt("anio_vigencia") : 0;
                    monto = rs.getString("monto")!=null ? rs.getString("monto") : "";
                    estado = (rs.getString("estado")!=null) ? rs.getInt("estado") : 1;
                    rs.close();
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
        }

        try {
            out.print("obj»d_3^foc»nombre_comercial^msg»"+msg+"^frm»");
            out.print("<form action=\"frmLaboratorioEstrategicoGuardar\" onsubmit=\"return mer_laboratorioGuardar(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_laboratorio\" name=\"id_laboratorio\" value=\""+id+"\" />");
            out.print("<h3>FORMULARIO DE LABORATORIO ESTRAT&Eacute;GICO</h3>");
            out.print("<table>");
            out.print("<tr>");
            out.print("<td nowrap>Laboratorio: <span class=\"marca\">*</span> </td>");
            out.print("<td><div id=\"axTxt\"></div><input type=\"text\" id=\"nombre_comercial\" name=\"nombre_comercial\" size=\"50\" maxlength=\"70\" value=\""+nombre_comercial+"\" class=\"may\" onkeyup=\"mer_getProveedor();\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>RUC: <span class=\"marca\">*</span></td>");
            out.print("<td><input type=\"text\" id=\"numero_idproveedor\" name=\"numero_idproveedor\" size=\"50\" maxlength=\"13\" value=\""+numero_idproveedor+"\" onkeypress=\"_numero(event);\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Monto: </td>");
            out.print("<td><input type=\"text\" id=\"monto\" name=\"monto\" size=\"50\" maxlength=\"13\" value=\""+monto+"\" onkeypress=\"_evaluar(event, '0123456789.')\" readonly /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>A&ntilde;o de vigencia: <span class=\"marca\">*</span></td>");
            out.print("<td><input type=\"text\" id=\"anio_vigencia\" name=\"anio_vigencia\" size=\"50\" maxlength=\"4\" value=\""+anio_vigencia+"\" onkeypress=\"_numero(event)\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Estado: <span class=\"marca\">*</span> </td>");
            out.print("<td><label><input type='radio' id='e0' name='estado' value='1' "+((estado==1) ? "checked" : "")+" /> Activo</label> &nbsp;&nbsp;&nbsp; " +
                    "<label><input type='radio' id='e1' name='estado' value='0' "+((estado==0) ? "checked" : "")+" /> Inactivo</label></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Guardar\" /></td>");
            out.print("</tr>");
            out.print("</table>");
            out.print("</form>");
            
            //  asignacion de presupuestos a tipos de plan
            if(id.compareTo("-1")!=0){
                out.print("<hr />");
                out.print("<form action=\"frmLaboratorioEstrategicoReasignacionGuardar\" onsubmit=\"return mer_laboratorioReasignacionGuardar(this)\" autocomplete=\"off\">");
                out.print("<input type=\"hidden\" id=\"limite\" name=\"limite\" value=\"0\" />");
                out.print("<input type=\"hidden\" id=\"id_lab\" name=\"id_lab\" value=\""+id+"\" />");
                out.print("<div><input type=\"button\" value=\"Nueva asignaci&oacute;n de presupuestos\" onclick=\"mer_asignarPresupuesto("+id+")\" />"
                        + " &nbsp;&nbsp; <input type=\"submit\" value=\"Guardar re-asignaci&oacute;n de presupuestos\" /></div>");

                out.print("<div class=\"tabla borde\">");
                    out.print("<div class=\"jm_filaImp\">");
                    out.print("<div class=\"columnaTH\" style=\"width:110px\">ABR.</div>");
                    out.print("<div class=\"columnaTH\" style=\"width:340px\">NOMBRE</div>");
                    out.print("<div class=\"columnaTH\" style=\"width:80px\">PRES.</div>");
                    out.print("<div class=\"columnaTH\" style=\"width:25px\">&nbsp;</div>");
                    out.print("</div>");
                out.print("</div>");
                
                out.print("<div id=\"TiPl\" class=\"tabla\">");
                try{
                    int i=0;
                    ResultSet rsTipoPlanes = objLaboratorio.getTiposPlanes(id);
                    //String matPresupuesto[][] = Matriz.ResultSetAMatriz(rsTipoPlanesPresupuesto);
                    //ResultSet rsTipoPlanes = objLaboratorio.consulta("select id_tipo_plan, nombre from tbl_tipo_plan order by nombre");
                    String id_tipo_plan = "";
                    String nombre = "";
                    String presupuesto = "0";
                    //int pos = 0;
                    while(rsTipoPlanes.next()){
                        id_tipo_plan = rsTipoPlanes.getString("id_tipo_plan")!=null ? rsTipoPlanes.getString("id_tipo_plan") : "";
                        nombre = rsTipoPlanes.getString("nombre")!=null ? rsTipoPlanes.getString("nombre") : "";
                        presupuesto = rsTipoPlanes.getString("presupuesto")!=null ? rsTipoPlanes.getString("presupuesto") : "";
                        //pos = Matriz.enMatriz(matPresupuesto, id_tipo_plan, 0);
                        //presupuesto = pos!=-1 ? matPresupuesto[pos][1]:"0";
                        out.print("<div class=\"jm_filaImp\" id=\"f"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                        "<div class=\"columna\" style=\"width:110px\"><input type=\"hidden\" id=\"id_tipo_plan"+i+"\" name=\"id_tipo_plan"+i+"\" value=\""+id_tipo_plan+"\" />"+id_tipo_plan+"</div>"+
                        "<div class=\"columna\" style=\"width:340px\">"+nombre+"</div>"+
                        "<div class=\"columna\" style=\"width:80px;text-align:right\"><input class=\"caja\" type=\"text\" id=\"presupuesto"+i+"\" name=\"presupuesto"+i+"\" value=\""+presupuesto+"\" style=\"width:78px;\" onkeypress=\"_evaluar(event, '0123456789.')\" /></div>"+
                        //"<div class=\"columna\" style=\"width:25px\"><div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('f"+i+"');\">&nbsp;</div></div>"+
                        "</div>");
                        i++;
                    }
                    rsTipoPlanes.close();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                out.print("</div>");
                out.print("</form>");
            }
            
        } finally {
            objLaboratorio.cerrar();
            //obj.cerrar();
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
