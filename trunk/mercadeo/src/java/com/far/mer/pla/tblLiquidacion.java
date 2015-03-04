/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.mer.pla.clas.Actividad;
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
public class tblLiquidacion extends HttpServlet {

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
            ResultSet rsPrivilegios = objDBSegu.consulta("select * from Atribuciones with (nolock) where NombreCorto='"+usuario+"' and Transaccion='mer_liquidacion_add_facts' and Habilitado=1");
            if(rsPrivilegios.next()){
                if(objDBSegu.getFilas(rsPrivilegios)>0){
                    aniadir_car = true;
                }
                rsPrivilegios.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        String id = request.getParameter("id");
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsAct = objActividad.getFacturas(objPlanMercadeo.getIdActividades(id));
        
        String id_tipo_plan = "";
        String plan_mercadeo = "";
        String p_incremento = "0"; 
        float presupuesto_total = 0; 
        float gasto = 0; 
        int estado = 0; 
        if(id.compareTo("-1")!=0){
            try{
                ResultSet rsTipo = objPlanMercadeo.getPlanMercadeoTipo(id);
                if(rsTipo.next()){
                    id_tipo_plan = rsTipo.getString("id_tipo_plan")!=null ? rsTipo.getString("id_tipo_plan") : "";
                    plan_mercadeo = rsTipo.getString("plan_mercadeo")!=null ? rsTipo.getString("plan_mercadeo") : "";
                    p_incremento = rsTipo.getString("p_incremento")!=null ? rsTipo.getString("p_incremento") : "0";
                    presupuesto_total = rsTipo.getString("pre_total")!=null ? rsTipo.getFloat("pre_total") : 0;
                    gasto = rsTipo.getString("gasto")!=null ? rsTipo.getFloat("gasto") : 0;
                    estado = rsTipo.getString("estado")!=null ? rsTipo.getInt("estado") : 0;
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
            out.print("<td colspan=\"3\">"+id_tipo_plan+"</td>");
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
            out.print("</table>");
            
            out.print("<br />");
            
            out.print("<table cellpadding=\"2\" width=\"100%\" height=\"30\" class=\"psPanelGris\">");
            out.print("<tr>");
            out.print("<td>PRESUPUESTO APROBADO</td>");
            out.print("</tr>");
            out.print("</table>");
            
            try{
            //float total_gasto = 0;
            int i=0;
            out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
            out.print("<tr class=\"jm_fila_cab\">"
                    + "<th class=\"jm_TH\" style=\"width:84px\">FECHA</th>"
                    + "<th class=\"jm_TH\" style=\"width:114px\">RUC</th>"
                    + "<th class=\"jm_TH\" style=\"width:154px\">PROVEEDOR</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">CANTIDAD</th>"
                    + "<th class=\"jm_TH\" style=\"width:104px\">DETALLE</th>"
                    + "<th class=\"jm_TH\" style=\"width:124px\">No. DOC.</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">BASE 12%</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">BASE 0%</th>"
                    + "<th class=\"jm_TH\" style=\"width:64px\">IVA</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">TOTAL</th>"
                    + "</tr>");
            out.print("<tbody id=\"tblGastos\">");
            while(rsAct.next()){
                String id_actividad_compra = rsAct.getString("id_actividad_compra")!=null ? rsAct.getString("id_actividad_compra") : "-1";
                String fecha = rsAct.getString("fecha1")!=null ? rsAct.getString("fecha1") : "";
                String ruc = rsAct.getString("ruc")!=null ? rsAct.getString("ruc") : "";
                String proveedor = rsAct.getString("proveedor")!=null ? rsAct.getString("proveedor") : "";
                String detalle = rsAct.getString("detalle")!=null ? rsAct.getString("detalle") : "";
                String num_documento = rsAct.getString("num_documento")!=null ? rsAct.getString("num_documento") : "";
                String cantidad = rsAct.getString("cantidad")!=null ? rsAct.getString("cantidad") : "";
                String base_12 = rsAct.getString("base_12")!=null ? rsAct.getString("base_12") : "";
                String base_0 = rsAct.getString("base_0")!=null ? rsAct.getString("base_0") : "";
                String iva = rsAct.getString("iva")!=null ? rsAct.getString("iva") : "";
                float total = rsAct.getString("total")!=null ? rsAct.getFloat("total") : 0;
                //total_gasto += total;
                out.print("<tr id=\"fGasto"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                out.print("<td><input type=\"hidden\" id=\"idActCom"+i+"\" value=\""+id_actividad_compra+"\" />"+fecha+"</td>");
                out.print("<td>"+ruc+"</td>");
                out.print("<td>"+proveedor+"</td>");
                out.print("<td>"+detalle+"</td>");
                out.print("<td align=\"right\">"+cantidad+"</td>");
                out.print("<td>"+num_documento+"</td>");
                out.print("<td align=\"right\">"+base_12+"</td>");
                out.print("<td align=\"right\">"+base_0+"</td>");
                out.print("<td align=\"right\">"+iva+"</td>");
                out.print("<td align=\"right\">"+total+"</td>");
                out.print("</tr>");
                i++;
            }
            out.print("</tbody>");
            out.print("</table>");
            
            if(estado!=9 && estado!=10){
                out.print("<table cellpadding=\"2\" width=\"100%\">");
                out.print("<tr>");
                if(aniadir_car && estado!=9 && estado!=10){
                    out.print("<td><input type=\"button\" value=\"A&ntilde;adir Factura\" onclick=\"mer_liquidacionAnadir("+id+", -1)\" /></td>");
                }
                out.print("<td align=\"right\"><input type=\"button\" value=\"Cerrar Plan\" onclick=\"mer_liquidacionCerrarPlan("+id+", "+(presupuesto_total-gasto)+");\" /></td>");
                out.print("</tr>");
                out.print("</table>");
            }
            
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
