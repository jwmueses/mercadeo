/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

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

/**
 *
 * @author Jorge
 */
public class tblLiquidacionCompras extends HttpServlet {

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

        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        String id = request.getParameter("id");
        String idActividad = request.getParameter("idAct");
        String _alto =  request.getParameter("alto");
        int alto = (Integer.parseInt(_alto) / 2)-120;
        
        float total_incremento1 = 0;
        //String id_tipo_plan = "";
        //String plan_mercadeo = "";
        String p_incremento = "0"; 
        String presupuesto_total = "0"; 
        String gasto = "0"; 
        //int estado = 0; 
        if(id.compareTo("-1")!=0){
            try{
                ResultSet rsTipo = objPlanMercadeo.getPlanMercadeoTipo(id);
                if(rsTipo.next()){
                    //id_tipo_plan = rsTipo.getString("id_tipo_plan")!=null ? rsTipo.getString("id_tipo_plan") : "";
                    //plan_mercadeo = rsTipo.getString("plan_mercadeo")!=null ? rsTipo.getString("plan_mercadeo") : "";
                    p_incremento = rsTipo.getString("p_incremento")!=null ? rsTipo.getString("p_incremento") : "0";
                    presupuesto_total = rsTipo.getString("pre_total")!=null ? rsTipo.getString("pre_total") : "0";
                    gasto = rsTipo.getString("gasto")!=null ? rsTipo.getString("gasto") : "0";
                    //estado = rsTipo.getString("estado")!=null ? rsTipo.getInt("estado") : 0;
                    rsTipo.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        try {
            out.print("obj»vta1_html^frm»");
            out.print("<input type=\"hidden\" id=\"id\" name=\"id\" value=\""+id+"\" />");
            out.print("<table cellpadding=\"2\" width=\"100%\" class=\"psPanelGris\">");
            /*out.print("<tr>");
            out.print("<td>C&oacute;digo:</td>");
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
            out.print("</tr>");*/
            out.print("<tr>");
            out.print("<td>Presupuesto total: "+presupuesto_total+"</td>");
            out.print("<td>Total gasto: "+gasto+"</td>");
            out.print("</tr>");
            out.print("</table>");
            
            
            try{
                ResultSet rsAct = objPlanMercadeo.getActividades(id, p_incremento);
                out.print("<table cellspacing=\"0\" cellpadding=\"2\" border=\"0\">");
                out.print("<tr class=\"jm_fila_cab\">"
                        + "<th class=\"jm_TH\" width=\"160\">MEDIO</th>"
                        + "<th class=\"jm_TH\" width=\"230\">CONCEPTO</th>"
                        + "<th class=\"jm_TH\" width=\"70\">UNIDADES</th>"
                        + "<th class=\"jm_TH\" width=\"70\">COSTO / UNITARIO ($)</th>"
                        + "<th class=\"jm_TH\" width=\"70\">TOTAL PRESUPUESTO ($)</th>"
                        + "<th class=\"jm_TH\" width=\"70\">TOTAL INC. AUTORIZADO ($)</th>"
                        + "<th class=\"jm_TH\" width=\"70\">TOTAL GASTO ($)</th>"
                        + "<th class=\"jm_TH\" width=\"70\">DIFERENCIA ($)</th>"
                        + "</tr>"
                        + "</table>");
                
                out.print("<div style=\"width:883px;height:"+alto+"px;overflow:auto;\">");
                out.print("<table cellspacing=\"0\" cellpadding=\"2\" border=\"0\">");
                while(rsAct.next()){
                    String id_actividad = rsAct.getString("id_actividad")!=null ? rsAct.getString("id_actividad") : "";
                    //String tipo_pago = rsAct.getString("tipo_pago")!=null ? rsAct.getString("tipo_pago") : "i";
                    String pre_total = rsAct.getString("pre_total")!=null ? rsAct.getString("pre_total") : "0";
                    float total_incremento = rsAct.getString("total_incremento")!=null ? rsAct.getFloat("total_incremento") : 0;
                    if(idActividad.compareTo(id_actividad)==0){
                        total_incremento1 = total_incremento;
                    }
                    out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\" style=\"cursor:pointer\" onclick=\"mer_liquidacionGetFacturas("+id_actividad+", "+pre_total+", "+total_incremento+")\" >");
                    out.print("<td width=\"160\">"+(rsAct.getString("medio")!=null ? rsAct.getString("medio") : "")+"</td>");
                    out.print("<td width=\"230\">"+(rsAct.getString("actividad")!=null ? rsAct.getString("actividad") : "")+"</td>");
                    out.print("<td width=\"70\" align=\"center\">"+(rsAct.getString("pre_cantidad")!=null ? rsAct.getString("pre_cantidad") : "")+"</td>");
                    out.print("<td width=\"70\" align=\"right\">"+(rsAct.getString("pre_p_u")!=null ? rsAct.getString("pre_p_u") : "")+"</td>");
                    out.print("<td width=\"70\" align=\"right\">"+pre_total+"</td>");
                    out.print("<td width=\"70\" align=\"right\">"+total_incremento+"</td>");
                    out.print("<td width=\"70\" align=\"right\">"+(rsAct.getString("monto")!=null ? rsAct.getString("monto") : "")+"</td>");
                    out.print("<td width=\"70\" align=\"right\">"+(rsAct.getString("diferencia")!=null ? rsAct.getString("diferencia") : "")+"</td>");
                    out.print("</tr>");
                }
                out.print("</table>");
                out.print("</div>");
                
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
            out.print("<br />");
            
            out.print("<div id=\"tblFacturas\">");
            out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
            out.print("<tr class=\"jm_fila_cab\">"
                    + "<th class=\"jm_TH\" style=\"width:84px\">FECHA</th>"
                    + "<th class=\"jm_TH\" style=\"width:114px\">RUC</th>"
                    + "<th class=\"jm_TH\" style=\"width:154px\">PROVEEDOR</th>"
                    + "<th class=\"jm_TH\" style=\"width:104px\">DETALLE</th>"
                    + "<th class=\"jm_TH\" style=\"width:124px\">No. DOC.</th>"
                    + "<th class=\"jm_TH\" style=\"width:70px\">CANTIDAD</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">SUBTOTAL</th>"
                    + "<th class=\"jm_TH\" style=\"width:64px\">IVA</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">TOTAL</th>"
                    + "</tr>"
                    + "</table>");
            out.print("<div style=\"width:883px;height:"+alto+"px;overflow:auto;\"></div>");
            
            if(idActividad.compareTo("-1")!=0){
                ResultSet rsAct = objActividad.getFacturas(idActividad);
                out.print(objActividad.tblLiquidacionFacturas(rsAct, alto-10));
                //float total_gasto = objActividad.total_gasto();
                out.print("</div>");

                out.print("<form id=\"frmFact\" onsubmit=\"return mer_seguimientoCompraGuardar(this, 'tblLiquidacionComprasFacturasGuardar')\" autocomplete=\"off\">");
                out.print("<input type=\"hidden\" id=\"idPM\" name=\"idPM\" value=\""+id+"\" />");
                out.print("<input type=\"hidden\" id=\"idAct\" name=\"idAct\" value=\""+idActividad+"\" />");
                out.print("<input type=\"hidden\" id=\"totInc\" name=\"totInc\" value=\""+total_incremento1+"\" />");
                out.print("<input type=\"hidden\" id=\"idActCom\" name=\"idActCom\" value=\"-1\" />");

                out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
                out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                out.print("<td><input id=\"fac_fecha\" name=\"fac_fecha\" type=\"text\" class=\"texto\" maxlength=\"10\" style=\"width:80px\" onkeypress=\"_evaluar(event, '0123456789/')\" onkeyup=\"if(_gKC(event)!=8 && _gKC(event)!=46 && (this.value.length==2 || this.value.length==5))this.value+='/';\" /></td>");
                out.print("<td><input id=\"fac_ruc\" name=\"fac_ruc\" type=\"text\" class=\"texto\" maxlength=\"13\" style=\"width:110px\" onkeypress=\"_numero(event)\" /></td>");
                out.print("<td><input id=\"fac_proveedor\" name=\"fac_proveedor\" type=\"text\" maxlength=\"160\" class=\"texto\" style=\"width:150px\" /></td>");
                out.print("<td><input id=\"fac_detalle\" name=\"fac_detalle\" type=\"text\" class=\"texto\" style=\"width:100px\" /></td>");
                out.print("<td><input id=\"fac_num_documento\" name=\"fac_num_documento\" type=\"text\" maxlength=\"20\" class=\"texto\" style=\"width:120px\" onkeypress=\"_evaluar(event, '0123456789-')\" onkeyup=\"if(_gKC(event)!=8 && _gKC(event)!=46 && (this.value.length==3 || this.value.length==7))this.value+='-';\" /></td>");
                out.print("<td><input id=\"fac_cantidad\" name=\"fac_cantidad\" type=\"text\" class=\"caja\" style=\"width:66px\" onkeypress=\"_evaluar(event, '0123456789.')\" /></td>");
                out.print("<td><input id=\"fac_subtotal\" name=\"fac_subtotal\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.')\" /></td>");
                out.print("<td><input id=\"fac_iva\" name=\"fac_iva\" type=\"text\" class=\"caja\" style=\"width:60px\" onkeypress=\"_evaluar(event, '0123456789.')\" /></td>");
                out.print("<td><input id=\"fac_total\" name=\"fac_total\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_seguimientoValGastoTotal(this.value)\" /></td>");
                out.print("</tr>");
                out.print("</table>");

                out.print("<div style=\"text-align:right\"><input type=\"submit\" value=\"Registrar Factura\" />"
                        + " &nbsp;&nbsp;&nbsp; <input type=\"button\" value=\"Cerrar\" onclick=\"_R('bloq_vta1');_R('vta1');\" /></div>");
                out.print("</form>");
            }
        
            out.print("</div>");
            
        } finally {
            objPlanMercadeo.cerrar();
            objActividad.cerrar();
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
