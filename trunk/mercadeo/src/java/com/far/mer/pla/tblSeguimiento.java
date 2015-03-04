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
public class tblSeguimiento extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String segu_ip = null;
    private int segu_puerto = 1433;
    private String segu_db = null;
    private String segu_usuario = null;
    private String segu_clave = null;
    
    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.segu_ip = config.getServletContext().getInitParameter("SEGU_IP");
        this.segu_puerto = Integer.parseInt(config.getServletContext().getInitParameter("SEGU_PUERTO"));
        this.segu_db = config.getServletContext().getInitParameter("SEGU_DB");
        this.segu_usuario = config.getServletContext().getInitParameter("SEGU_USUARIO");
        this.segu_clave = config.getServletContext().getInitParameter("SEGU_CLAVE");
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

        boolean aniadir_car = false;
        BaseDatos objDBSegu = new BaseDatos(this.segu_ip, this.segu_puerto, this.segu_db, this.segu_usuario, this.segu_clave);
        try{
            ResultSet rsPrivilegios = objDBSegu.consulta("select * from Atribuciones with (nolock) where NombreCorto='"+usuario+"' and Transaccion='mer_seguimiento' and Habilitado=1");
            if(rsPrivilegios.next()){
                if(objDBSegu.getFilas(rsPrivilegios)>0){
                    aniadir_car = true;
                }
                rsPrivilegios.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        String id = request.getParameter("id");
        
        String id_tipo_plan = "";
        String sec_tipo_plan = "";
        String plan_mercadeo = "";
        String p_incremento = "0"; 
        String presupuesto_total = "0"; 
        String gasto = "0"; 
        String fecha_creacion = "";
        String fecha_terminacion = "";
        String fecha_comercial = "";
        String fecha_mark_vent = "";
        String fecha_operaciones = "";
        int prolonga = 0; 
        int estado = 0; 
        if(id.compareTo("-1")!=0){
            try{
                ResultSet rsTipo = objPlanMercadeo.getPlanMercadeoTipo(id);
                if(rsTipo.next()){
                    id_tipo_plan = rsTipo.getString("id_tipo_plan")!=null ? rsTipo.getString("id_tipo_plan") : "";
                    sec_tipo_plan = rsTipo.getString("sec_tipo_plan")!=null ? rsTipo.getString("sec_tipo_plan") : "";
                    plan_mercadeo = rsTipo.getString("plan_mercadeo")!=null ? rsTipo.getString("plan_mercadeo") : "";
                    p_incremento = rsTipo.getString("p_incremento")!=null ? rsTipo.getString("p_incremento") : "0";
                    presupuesto_total = rsTipo.getString("pre_total")!=null ? rsTipo.getString("pre_total") : "0";
                    gasto = rsTipo.getString("gasto")!=null ? rsTipo.getString("gasto") : "0";
                    prolonga = rsTipo.getString("prolonga")!=null ? rsTipo.getInt("prolonga") : 0;
                    estado = rsTipo.getString("estado")!=null ? rsTipo.getInt("estado") : 0;
                    fecha_creacion = rsTipo.getString("fecha_creacion")!=null ? rsTipo.getString("fecha_creacion") : "";
                    fecha_terminacion = rsTipo.getString("fecha_terminacion")!=null ? rsTipo.getString("fecha_terminacion") : "";
                    fecha_comercial = rsTipo.getString("fecha_comercial")!=null ? rsTipo.getString("fecha_comercial") : "";
                    fecha_mark_vent = rsTipo.getString("fecha_mark_vent")!=null ? rsTipo.getString("fecha_mark_vent") : "";
                    fecha_operaciones = rsTipo.getString("fecha_operaciones")!=null ? rsTipo.getString("fecha_operaciones") : "";
                    rsTipo.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        try {
            out.print("obj»d_3^frm»");
            out.print("<input type=\"hidden\" id=\"id\" name=\"id\" value=\""+id+"\" />");
            out.print("<table cellpadding=\"2\" width=\"100%\" class=\"psPanelGris\">");
            out.print("<tr>");
            out.print("<td >C&oacute;digo:</td>");
            out.print("<td colspan=\"3\">"+id_tipo_plan+" &nbsp;&nbsp; "+sec_tipo_plan+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td width=\"200\">Nombre del plan de mercadeo:</td>");
            out.print("<td width=\"350\">"+plan_mercadeo+"</td>");
            out.print("<td width=\"120\">Presupuesto total:</td>");
            out.print("<td>"+presupuesto_total+"</td>");
            out.print("</tr>");
            out.print("<tr>");
            out.print("<td>% de incremento aprobador:</td>");
            out.print("<td>"+p_incremento+" %</td>");
            out.print("<td>Total gasto:</td>");
            out.print("<td>"+gasto+"</td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td>Fecha de creaci&oacute;n:</td>");
            out.print("<td>"+fecha_creacion+"</td>");
            out.print("<td>Fecha de t&eacute;rmino:</td>");
            out.print("<td>"+fecha_terminacion+"</td>");
            out.print("</tr>");
            
            out.print("<tr>");
            out.print("<td>Fecha de &uacute;ltima aprobaci&oacute;n:</td>");
            out.print("<td colspan=\"3\">"+(fecha_comercial.compareTo("")!=0 ? fecha_comercial : (fecha_mark_vent.compareTo("")!=0 ? fecha_mark_vent : fecha_operaciones) )+"</td>");
            out.print("</tr>");
            out.print("</table>");
            
            out.print("<br />");
            
            out.print("<table cellpadding=\"2\" width=\"100%\" height=\"30\" class=\"psPanelGris\">");
            out.print("<tr>");
            out.print("<td>PRESUPUESTO APROBADO</td>");
            out.print("<td align=\"right\"><input type=\"button\" value=\"Exportar a Excel\" onclick=\"abrir('ExcelPlanMercadeoGastos?id="+id+"&plan="+plan_mercadeo+"&p="+p_incremento+"')\" />");
            if(objPlanMercadeo.activarCierre(id)){
                out.print("&nbsp;&nbsp;&nbsp; <input type=\"button\" value=\"Finalizar Plan de Mercadeo\" onclick=\"mer_seguimientoCerrarPlan("+id+", "+sec_tipo_plan+")\" />");
            }
            out.print("</td></tr>");
            out.print("</table>");
            
            try{
                int i=0;
                ResultSet rsAct = objPlanMercadeo.getActividades(id, p_incremento);
                out.print("<table cellspacing=\"0\" cellpadding=\"2\" border=\"0\">");
                out.print("<tr class=\"jm_fila_cab\">"
                        + "<th class=\"jm_TH\">MEDIO</th>"
                        + "<th class=\"jm_TH\">CONCEPTO</th>"
                        + "<th class=\"jm_TH\">UNIDADES</th>"
                        + "<th class=\"jm_TH\">COSTO / UNITARIO ($)</th>"
                        + "<th class=\"jm_TH\">TOTAL PRESUPUESTO ($)</th>"
                        + "<th class=\"jm_TH\">TOTAL INCREMENTO AUTORIZADO ($)</th>"
                        + "<th class=\"jm_TH\">TOTAL GASTO ($)</th>"
                        + "<th class=\"jm_TH\">DIFERENCIA ($)</th>"
                        + "<th class=\"jm_TH\">VER</th>"
                        + "<th class=\"jm_TH\">A&Ntilde;ADIR</th>"
                        + "</tr>");
                while(rsAct.next()){
                    String id_actividad = rsAct.getString("id_actividad")!=null ? rsAct.getString("id_actividad") : "";
                    String tipo_pago = rsAct.getString("tipo_pago")!=null ? rsAct.getString("tipo_pago") : "i";
                    String pre_total = rsAct.getString("pre_total")!=null ? rsAct.getString("pre_total") : "0";
                    String total_incremento = rsAct.getString("total_incremento")!=null ? rsAct.getString("total_incremento") : "0";
                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                    out.print("<td>"+(rsAct.getString("medio")!=null ? rsAct.getString("medio") : "")+"</td>");
                    out.print("<td>"+(rsAct.getString("actividad")!=null ? rsAct.getString("actividad") : "")+"</td>");
                    out.print("<td align=\"center\">"+(rsAct.getString("pre_cantidad")!=null ? rsAct.getString("pre_cantidad") : "")+"</td>");
                    out.print("<td align=\"right\">"+(rsAct.getString("pre_p_u")!=null ? rsAct.getString("pre_p_u") : "")+"</td>");
                    out.print("<td align=\"right\">"+pre_total+"</td>");
                    out.print("<td align=\"right\">"+total_incremento+"</td>");
                    out.print("<td align=\"right\">"+(rsAct.getString("monto")!=null ? rsAct.getString("monto") : "")+"</td>");
                    out.print("<td align=\"right\">"+(rsAct.getString("diferencia")!=null ? rsAct.getString("diferencia") : "")+"</td>");
                    out.print("<td align=\"center\"><div id=\"ver" + i + "\" class=\"ver\" onclick=\"mer_seguimientoVer(" + id_actividad + ", '"+pre_total+"')\" title=\"visualizar\">&nbsp;</div></td>");
                    if(tipo_pago.compareTo("i")==0 && estado!=9 && estado!=10 && aniadir_car && prolonga==1){
                        out.print("<td align=\"center\"><div id=\"car" + i + "\" class=\"car\" onclick=\"mer_seguimientoAnadir("+id_actividad+", '"+pre_total+"', '"+total_incremento+"')\" title=\"a&ntilde;adir\">&nbsp;</div></td>");
                    }else{
                        out.print("<td align=\"center\">&nbsp;</td>");
                    }
                    out.print("</tr>");
                    i++;
                }
                out.print("</table>");
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
        } finally {
            objPlanMercadeo.cerrar();
            objDBSegu.cerrar();
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
