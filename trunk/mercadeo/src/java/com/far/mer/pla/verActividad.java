/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.DatosDinamicos;
import com.far.mer.pla.clas.Actividad;
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
public class verActividad extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    private String _url_anexo = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        this._url_anexo = config.getServletContext().getInitParameter("_URL_ANEXO");
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
        String estado = request.getParameter("est");
        String panel = request.getParameter("pn");

        String actividad = "";
        String fecha_ini = "";
        String fecha_fin = "";
        
        String responsable_seg = "";
        String responsable_eje = "";
        String pre_tipo = "";
        String pre_proveedor = "";
        String pre_cantidad = "";
        String pre_p_u = "";
        String pre_total = "";
        String pre_id_tipo_plan_cuenta = "";
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);

        if(id.compareTo("-1")!=0){
            try{
                ResultSet rs = objActividad.getActividad(id);
                if(rs.next()){
                    actividad = rs.getString("actividad")!=null ? rs.getString("actividad") : "";
                    fecha_ini = rs.getString("sql_fecha_ini")!=null ? rs.getString("sql_fecha_ini") : "";
                    fecha_fin = rs.getString("sql_fecha_fin")!=null ? rs.getString("sql_fecha_fin") : "";
                    responsable_seg = rs.getString("responsable_seg")!=null ? rs.getString("responsable_seg") : "";
                    responsable_eje = rs.getString("responsable_eje")!=null ? rs.getString("responsable_eje") : "";
                    pre_tipo = rs.getString("pre_tipo")!=null ? rs.getString("pre_tipo") : "";
                    pre_proveedor = rs.getString("pre_proveedor")!=null ? rs.getString("pre_proveedor") : "";
                    pre_cantidad = rs.getString("pre_cantidad")!=null ? rs.getString("pre_cantidad") : "";
                    pre_p_u = rs.getString("pre_p_u")!=null ? rs.getString("pre_p_u") : "";
                    pre_total = rs.getString("pre_total")!=null ? rs.getString("pre_total") : "";
                    pre_id_tipo_plan_cuenta = rs.getString("pre_id_tipo_plan_cuenta")!=null ? rs.getString("pre_id_tipo_plan_cuenta") : "";
                    rs.close();
                }
                
                if(estado.compareTo("0")!=0){
                    objActividad.setMostrada(id, estado);
                }
            }catch(Exception e){
                msg = e.getMessage();
            }
        }
        
        TipoPlan objTipoPlan = new TipoPlan(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        ResultSet rsCrono = objActividad.getActividadesCronograma(id);
        
        

        try {
            out.print("obj»"+panel+"d_5^msg»"+msg+"^frm»");
            out.print("<h3>DATOS DE LA ACTIVIDAD</h3>");
            out.print("<table cellpadding=\"2\" width=\"100%\">");
            out.print("<tr>");
            out.print("<td>Actividad: </td>");
            out.print("<td>"+actividad+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Fecha de inicio </td>");
            out.print("<td>"+fecha_ini+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Fecha de t&eacute;rmino:</td>");
            out.print("<td>"+fecha_fin+"</td>");
            out.print("</tr>");
            
            if(responsable_seg.compareTo("")!=0){
                out.print("<tr valign=\"top\">");
                out.print("<td>Responsable de seguimiento:</td>");
                out.print("<td>"+responsable_seg+"</td>");
                out.print("</tr>");
            }
            
            out.print("<tr valign=\"top\">");
            out.print("<td nowrap>Responsable de ejecuci&oacute;n:</td>");
            out.print("<td>"+responsable_eje+"</td>");
            out.print("</tr>");
            
            String tipo_presupuesto="Bienes";
            if(pre_tipo.compareTo("s")==0){
                tipo_presupuesto = "Servicio";
            }else if(pre_tipo.compareTo("a")==0){
                      tipo_presupuesto = "Activo fijo";
            }else if(pre_tipo.compareTo("p")==0){
                      tipo_presupuesto = "Premio";
            }else if(pre_tipo.compareTo("i")==0){
                      tipo_presupuesto = "Inventario";
            }
            
            out.print("<tr>");
            out.print("<td>Tipo:</td>");
            out.print("<td>"+tipo_presupuesto+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Proveedor sugerido:</td>");
            out.print("<td>"+pre_proveedor+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Cantidad:</td>");
            out.print("<td>"+pre_cantidad+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td nowrap>Precio unitario incluido IVA: </td>");
            out.print("<td>"+pre_p_u+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>Total:</td>");
            out.print("<td>"+pre_total+"</td>");
            out.print("</tr>");
            out.print("<tr valign=\"top\">");
            out.print("<td>Cuenta contable:</td>");
            if(estado.compareTo("6")==0){
                String id_tipo_plan = objActividad.getIdTipoPlan(id);
                ResultSet rsCuentas = objTipoPlan.getTipoPlanCuentasActividad(id_tipo_plan);
                out.print("<td>"+DatosDinamicos.combo(rsCuentas, "pre_id_tipo_plan_cuenta", pre_id_tipo_plan_cuenta, "", "", 250)+
                        " <input type=\"button\" value=\"Actualizar\" onclick=\"mer_setPlanCuenta("+id+")\" /></td>");
            }else{
                String cuenta = objTipoPlan.getNombreTipoPlan(pre_id_tipo_plan_cuenta);
                out.print("<td>"+cuenta+"</td>");
            }
            out.print("</tr>");
            
            if(objActividad.getFilas(rsCrono) > 0){
                out.print("<tr>");
                out.print("<td colspan=\"2\">Cronograma de adquisiciones:</td>");
                out.print("</tr>");

                out.print("<tr><td colspan=\"2\" class=\"psPanelGris\">");
                out.print("<table width=\"100%\">");
                        out.print("<tr>");
                        out.print("<th width=\"85\"> FECHA </th><th> DESCRIPCION </th>");
                        out.print("</tr>");
                try{    
                    String fecha = "";
                    String descripcion = "";
                    while(rsCrono.next()){
                        fecha = rsCrono.getString("fecha_sql")!=null ? rsCrono.getString("fecha_sql") : "";
                        descripcion = rsCrono.getString("descripcion")!=null ? rsCrono.getString("descripcion") : "";
                        out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                        "<td> &nbsp; "+fecha+" &nbsp; </td>"+
                        "<td> &nbsp; "+descripcion+"</td>"+
                        "</tr>");
                    }
                }catch(Exception e){
                    msg = e.getMessage();
                }
                out.print("</table>");

                out.print("</td></tr>");
            }
            
            out.print("</table>");
            
        } finally {
            objTipoPlan.cerrar();
            objActividad.cerrar();
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
