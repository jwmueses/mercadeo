/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.Cadena;
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
public class tblSeguimientoCompras extends HttpServlet {

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

        String idAct = request.getParameter("idAct");
        String totPre = request.getParameter("totPre");
        String totInc = request.getParameter("totInc");
        int alto = (request.getParameter("alto")!=null ? Integer.parseInt(request.getParameter("alto")) : 400);
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String id_plan_mercadeo="";
        String sec_tipo_plan="";
        try{
            ResultSet rsPM = objActividad.getPlanMercadeo(idAct);
            if(rsPM.next()){
                id_plan_mercadeo = rsPM.getString("id_plan_mercadeo")!=null ? rsPM.getString("id_plan_mercadeo") : "";
                sec_tipo_plan = rsPM.getString("sec_tipo_plan")!=null ? rsPM.getString("sec_tipo_plan") : "";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        
        
        try {
            ResultSet rsAct = objActividad.getFacturas(idAct);
            out.print("obj»vta1_html^frm»");
            out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">"
                    + "<tr class=\"jm_fila_cab\">"
                    + "<th class=\"jm_TH\" style=\"width:99px\">FECHA</th>"
                    + "<th class=\"jm_TH\" style=\"width:114px\">RUC</th>"
                    + "<th class=\"jm_TH\" style=\"width:154px\">PROVEEDOR</th>"
                    + "<th class=\"jm_TH\" style=\"width:104px\">DETALLE</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">CANTIDAD</th>"
                    + "<th class=\"jm_TH\" style=\"width:124px\">No. DOC.</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">BASE 12%</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">BASE 0%</th>"
                    + "<th class=\"jm_TH\" style=\"width:64px\">IVA</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">TOTAL</th>"
                    + "</tr>"
                    + "</table>");
            out.print("<div id=\"axGasto\">");
            out.print(objActividad.tblFacturas(rsAct, alto));
            float total_gasto = objActividad.total_gasto();
            out.print("</div>");
            
            out.print("<form id=\"frmFact\" onsubmit=\"return mer_seguimientoCompraGuardar(this, 'tblSeguimientoComprasGuardar')\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"idAct\" name=\"idAct\" value=\""+idAct+"\" />");
            out.print("<input type=\"hidden\" id=\"totInc\" name=\"totInc\" value=\""+totInc+"\" />");
            out.print("<input type=\"hidden\" id=\"alto\" name=\"alto\" value=\""+alto+"\" />");
            out.print("<input type=\"hidden\" id=\"idActCom\" name=\"idActCom\" value=\"-1\" />");
            
            out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
            out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
            out.print("<td nowrap><input id=\"fac_fecha\" name=\"fac_fecha\" type=\"text\" class=\"texto\" maxlength=\"10\" style=\"width:80px\" onkeypress=\"_evaluar(event, '0123456789/')\" onkeyup=\"if(_gKC(event)!=8 && _gKC(event)!=46 && (this.value.length==2 || this.value.length==5))this.value+='/';\" />"
                    + "<input type=\"button\" value=\".\" style=\"width:20px;height:19px\" onClick=\"SelectorFecha.crear('fac_fecha', 'SQL');\" /></td>");
            out.print("<td><div id=\"axTxtruc\"></div><input id=\"fac_ruc\" name=\"fac_ruc\" type=\"text\" class=\"texto\" maxlength=\"13\" style=\"width:110px\" onkeypress=\"_numero(event)\" onkeyup=\"mer_getProveedoresCompras('ruc');\" /></td>");
            out.print("<td><div id=\"axTxtproveedor\"></div><input id=\"fac_proveedor\" name=\"fac_proveedor\" type=\"text\" maxlength=\"160\" class=\"texto\" style=\"width:150px\" onkeyup=\"mer_getProveedoresCompras('proveedor');\" /></td>");
            out.print("<td><input id=\"fac_detalle\" name=\"fac_detalle\" type=\"text\" class=\"texto\" style=\"width:100px\" /></td>");
            out.print("<td><input id=\"fac_cantidad\" name=\"fac_cantidad\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.')\" value=\"1\" /></td>");
            out.print("<td><input id=\"fac_num_documento\" name=\"fac_num_documento\" type=\"text\" maxlength=\"20\" class=\"texto\" style=\"width:120px\" onkeypress=\"_evaluar(event, '0123456789-')\" onkeyup=\"if(_gKC(event)!=8 && _gKC(event)!=46 && (this.value.length==3 || this.value.length==7))this.value+='-';\" /></td>");
            out.print("<td><input id=\"fac_base_12\" name=\"fac_base_12\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.');\" value=\"0\" onkeyup=\"mer_seguimientoSumar(event, true)\" /></td>");
            out.print("<td><input id=\"fac_base_0\" name=\"fac_base_0\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.');\" value=\"0\" onkeyup=\"mer_seguimientoSumar(event, true)\" /></td>");
            out.print("<td><input id=\"fac_iva\" name=\"fac_iva\" type=\"text\" class=\"caja\" style=\"width:60px\" onkeypress=\"_evaluar(event, '0123456789.');\" value=\"0\" onkeyup=\"mer_seguimientoSumar(event, false)\" /></td>");
            out.print("<td><input id=\"fac_total\" name=\"fac_total\" type=\"text\" class=\"caja\" style=\"width:70px\" readonly  /></td>");
            out.print("</tr>");
            out.print("</table>");
            
            out.print("<p class=\"psPanelGris\">Total presupuesto ($): <input id=\"totPre\" type=\"text\" class=\"caja\" style=\"width:100px\" value=\""+totPre+"\" readonly />"
                    + " &nbsp;&nbsp;&nbsp; Total gastos ($): <input id=\"totGasto\" type=\"text\" class=\"caja\" style=\"width:100px\" value=\""+Cadena.truncar(total_gasto)+"\"  />"
                    + " &nbsp;&nbsp;&nbsp; Diferencia ($): <input id=\"difer\" type=\"text\" class=\"caja\" style=\"width:100px\" value=\""+Cadena.truncar(Float.parseFloat(totPre) - total_gasto)+"\"  /></p>");
            
            out.print("<div style=\"text-align:right\">");
            if(objPlanMercadeo.activarCierre(id_plan_mercadeo)){
                out.print("<input type=\"button\" value=\"Finalizar Plan de Mercadeo Nro. "+sec_tipo_plan+"\" onclick=\"mer_seguimientoCerrarPlan("+id_plan_mercadeo+", "+sec_tipo_plan+")\" />");
            }
            out.print(" &nbsp;&nbsp;&nbsp; <input type=\"submit\" value=\"Registrar Factura\" />"
                    + " &nbsp;&nbsp;&nbsp; <input type=\"button\" value=\"Cerrar\" onclick=\"_R('bloq_vta1');_R('vta1');\" /></div>");
            out.print("</form>");
            
        } finally {
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
