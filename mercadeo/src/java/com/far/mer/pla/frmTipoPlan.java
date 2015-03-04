/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Matriz;
import com.far.mer.pla.clas.TipoPlan;
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
public class frmTipoPlan extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;

    private String easy_ip = null;
    private int easy_puerto = 1433;
    private String easy_db = null;
    private String easy_usuario = null;
    private String easy_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");

        this.easy_ip = config.getServletContext().getInitParameter("EASY_IP");
        this.easy_puerto = Integer.parseInt(config.getServletContext().getInitParameter("EASY_PUERTO"));
        this.easy_db = config.getServletContext().getInitParameter("EASY_DB");
        this.easy_usuario = config.getServletContext().getInitParameter("EASY_USUARIO");
        this.easy_clave = config.getServletContext().getInitParameter("EASY_CLAVE");
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
        
        String id_tipo_plan = "";
        String nombre = "";
        String cedula = "";
        String centro_costos_coordinador = "";
        String coordinador = "";
        String mail_responsable = "";
        String p_incremento = "0";
        String dias_prolonga = "0";
        int auspicio_manual = 0;
        int estado = 1;
        ResultSet rsCuentas = null;
        String codigos_cuentas[][] = null;
        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        if(id.compareTo("-1")!=0){
            rsCuentas = objTipoPlan.getTipoPlanCuentas(id);
            ResultSet rsSucursales = objTipoPlan.getTipoPlanSucursales(id);
            codigos_cuentas = Matriz.ResultSetAMatriz(rsSucursales);
            try{
                ResultSet rs = objTipoPlan.getTipoPlan(id);
                if(rs.next()){
                    id_tipo_plan = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
                    nombre = rs.getString("nombre")!=null ? rs.getString("nombre") : "";
                    cedula = rs.getString("cedula")!=null ? rs.getString("cedula") : "";
                    centro_costos_coordinador = rs.getString("centro_costos_coordinador")!=null ? rs.getString("centro_costos_coordinador") : "";
                    coordinador = rs.getString("coordinador")!=null ? rs.getString("coordinador") : "";
                    mail_responsable = rs.getString("mail_responsable")!=null ? rs.getString("mail_responsable") : "";
                    p_incremento = rs.getString("p_incremento")!=null ? rs.getString("p_incremento") : "0";
                    dias_prolonga = rs.getString("dias_prolonga")!=null ? rs.getString("dias_prolonga") : "0";
                    auspicio_manual = (rs.getString("auspicio_manual")!=null) ? rs.getInt("auspicio_manual") : -1;
                    estado = (rs.getString("estado")!=null) ? rs.getInt("estado") : 1;
                    rs.close();
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
        }

        BaseDatos obj = new BaseDatos(this.easy_ip, this.easy_puerto, this.easy_db, this.easy_usuario, this.easy_clave);
        ResultSet rs  = obj.consulta("select Sucursal, Nombre from Sucursal with (nolock) where Sucursal not in ('001', 'TOT') order by Sucursal");

        try {
            out.print("obj»d_3^foc»id_tipo_plan^msg»"+msg+"^frm»");
            out.print("<form action=\"frmTipoPlanGuardar\" onsubmit=\"return mer_tipo_planesGuardar(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_tipo_plan_ant\" name=\"id_tipo_plan_ant\" value=\""+(id_tipo_plan.compareTo("")!=0?id_tipo_plan:"-1")+"\" />");
            out.print("<input type=\"hidden\" id=\"tope\" name=\"tope\" value=\"0\" />");
            out.print("<h3>FORMULARIO DE TIPO DE PLAN</h3>");
            out.print("<table>");
            out.print("<tr>");
            out.print("<td>Acr&oacute;nimo (abreviatura): <span class=\"marca\">*</span></td>");
            out.print("<td><input type=\"text\" id=\"id_tipo_plan\" name=\"id_tipo_plan\" size=\"50\" maxlength=\"10\" value=\""+id_tipo_plan+"\" class=\"may\" "+(id.compareTo("-1")!=0 ? "readonly" :"")+"  /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Nombre: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"nombre\" name=\"nombre\" size=\"50\" maxlength=\"80\" value=\""+nombre+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Coordinador: <span class=\"marca\">*</span></td>");
            out.print("<td><div id=\"axEmp\"></div>"
                    + "<input type=\"text\" id=\"coordinador\" name=\"coordinador\" size=\"50\" maxlength=\"110\" value=\""+coordinador+"\" onkeyup=\"mer_getEmpledos();\" />"
                    + "<input type=\"hidden\" id=\"cedula\" name=\"cedula\" value=\""+cedula+"\" />"
                    + "<input type=\"hidden\" id=\"centro_costos_coordinador\" name=\"centro_costos_coordinador\" value=\""+centro_costos_coordinador+"\" /></td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>E-mail del responsable: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"mail_responsable\" name=\"mail_responsable\" size=\"50\" maxlength=\"50\" value=\""+mail_responsable+"\" /></td>");
            out.print("</tr>");
            
            out.print("<tr valign=\"top\">");
            out.print("<td>% de exceso en presupuesto para el ingreso de facturas: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"p_incremento\" name=\"p_incremento\" size=\"10\" maxlength=\"5\" value=\""+p_incremento+"\" onkeypress=\"_evaluar(event, '0123456789.')\" /></td>");
            out.print("</tr>");
            out.print("<tr valign=\"top\">");
            out.print("<td>D&iacute;as de prolonga a partir de fecha de finalizaci&oacute;n de plan de mercadeo: <span class=\"marca\">*</span> </td>");
            out.print("<td><input type=\"text\" id=\"dias_prolonga\" name=\"dias_prolonga\" size=\"10\" maxlength=\"2\" value=\""+dias_prolonga+"\" onkeypress=\"_numero(event)\" /></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td>Permitir confirmar auspicios manuales: <span class=\"marca\">*</span> </td>");
            out.print("<td><label><input type='radio' id='aus0' name='auspicio_manual' value='1' "+((auspicio_manual==1) ? "checked" : "")+" /> Si</label> &nbsp;&nbsp;&nbsp; " +
                    "<label><input type='radio' id='aus1' name='auspicio_manual' value='0' "+((auspicio_manual==0) ? "checked" : "")+" /> No</label></td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td>Estado: <span class=\"marca\">*</span> </td>");
            out.print("<td><label><input type='radio' id='e0' name='estado' value='1' "+((estado==1) ? "checked" : "")+" /> Activo</label> &nbsp;&nbsp;&nbsp; " +
                    "<label><input type='radio' id='e1' name='estado' value='0' "+((estado==0) ? "checked" : "")+" /> Inactivo</label></td>");
            out.print("</tr>");

            //  cuentas contables
            out.print("<tr><td colspan=\"2\">");
            out.print("<div id=\"axTxt\" class=\"h31\" style=\"width:380px;float:left;\">CUENTAS CONTABLES: &nbsp; ");
                out.print("<input type=\"text\" id=\"txtCts\" size=\"30\" class=\"buscar\" onfocus=\"this.value='';\" onkeyup=\"mer_getCuentas();\" onkeydown=\"_NoE(event);\" title=\"Busqueda por c&oacute;digo o descripci&oacute;n\" />");
            out.print("</div>");
            out.print("<input type=\"button\" style=\"margin-top:6px;\" value=\"Plan de cuentas\" onclick=\"mer_getPlanCuentas('vta1_html1')\" />");
            out.print("</td></tr>");
            
            out.print("<tr><td colspan=\"2\">");
            out.print("<div id=\"axCts\" class=\"tabla borde\">");
            try{
                int i=0;
                String cuenta = "";
                String descripcion = "";
                while(rsCuentas.next()){
                    cuenta = rsCuentas.getString("cuenta")!=null ? rsCuentas.getString("cuenta") : "";
                    descripcion = rsCuentas.getString("descripcion")!=null ? rsCuentas.getString("descripcion") : "";
                    out.print("<div class=\"jm_filaImp\" id=\"f"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                    "<div class=\"columna\" style=\"width:120px\"><input type=\"hidden\" id=\"c"+i+"\" name=\"c"+i+"\" value=\""+cuenta+"\" />"+cuenta+"</div>"+
                    "<div class=\"columna\" style=\"width:360px\"><input type=\"hidden\" id=\"d"+i+"\" name=\"d"+i+"\" value=\""+descripcion+"\" />"+descripcion+"</div>"+
                    "<div class=\"columna\" style=\"width:25px\"><div class=\"borrar\" title=\"Eliminar\" onclick=\"_R('f"+i+"');\">&nbsp;</div></div>"+
                    "</div>");
                    i++;
                }
                rsCuentas.close();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            out.print("</div>");
            out.print("</td></tr>");

            //  sucursales
            out.print("<tr><td colspan=\"2\"><br />");
            out.print("<div class=\"h31\">SUCURSALES</div>");
            try{
                out.print("<div class=\"tabla borde\">");
                out.print("<div class=\"jm_filaImp\">");
                out.print("<div class=\"columnaTH\" style=\"width:25px\">&nbsp;</div>");
                out.print("<div class=\"columnaTH\" style=\"width:110px\">C&Oacute;DIGO</div>");
                out.print("<div class=\"columnaTH\" style=\"width:370px\">NOMBRE</div>");
                out.print("</div>");
                int i =0;
                String codigo = "";
                String nombre_sucursal = "";
                while(rs.next()){
                    codigo = rs.getString("Sucursal")!=null ? rs.getString("Sucursal") : "";
                    nombre_sucursal = rs.getString("Nombre")!=null ? rs.getString("Nombre") : "";
                    out.print("<div class=\"jm_filaImp\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                    out.print("<div class=\"columna\" style=\"width:25px\"><input type=\"checkbox\" id=\"ch"+i+"\" name=\"ch"+i+"\" "+(Matriz.enMatriz(codigos_cuentas, codigo, 0)!=-1 ? "checked":"")+" /></div>");
                    out.print("<div class=\"columna\" style=\"width:110px\"><input type=\"hidden\" id=\"suc"+i+"\" name=\"suc"+i+"\" value=\""+codigo+"\" />"+codigo+"</div>");
                    out.print("<div class=\"columna\" style=\"width:370px\"><input type=\"hidden\" id=\"nomSuc"+i+"\" name=\"nomSuc"+i+"\" value=\""+nombre_sucursal+"\" />"+nombre_sucursal+"</div>");
                    out.print("</div>");
                    i++;
                }
                out.print("</div>");
                rs.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            out.print("</td></tr>");

            out.print("<tr>");
            out.print("<td colspan=\"2\" align=\"right\"><input type=\"submit\" value=\"Guardar\" /></td>");
            out.print("</tr>");
            out.print("</table>");
            out.print("</form>");
        } finally {
            objTipoPlan.cerrar();
            obj.cerrar();
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
