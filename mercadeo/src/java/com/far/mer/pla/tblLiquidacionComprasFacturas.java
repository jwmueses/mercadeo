/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.mer.pla.clas.Actividad;
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
public class tblLiquidacionComprasFacturas extends HttpServlet {

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

        String idPM = request.getParameter("idPM");
        String idAct = request.getParameter("idAct");
        String totPre = request.getParameter("totPre");
        String totInc = request.getParameter("totInc");
        int alto = Integer.parseInt( request.getParameter("alto") );
        alto = (alto / 2)-130;
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet rsAct = objActividad.getFacturas(idAct);
        float total_gasto = objActividad.total_gasto();
        try {
            out.print("obj»tblFacturas^frm»");
            out.print("<div id=\"axGasto\">");
            out.print(objActividad.tblLiquidacionFacturas(rsAct, alto));
            //float total_gasto = objActividad.total_gasto();
            out.print("</div>");
            
            out.print("<form id=\"frmFact\" onsubmit=\"return mer_seguimientoCompraGuardar(this, 'tblLiquidacionComprasFacturasGuardar')\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"idPM\" name=\"idPM\" value=\""+idPM+"\" />");
            out.print("<input type=\"hidden\" id=\"idAct\" name=\"idAct\" value=\""+idAct+"\" />");
            out.print("<input type=\"hidden\" id=\"totInc\" name=\"totInc\" value=\""+totInc+"\" />");
            out.print("<input type=\"hidden\" id=\"totGasto\" value=\""+total_gasto+"\" />");
            out.print("<input type=\"hidden\" id=\"totPre\" value=\""+totPre+"\" />");
            out.print("<input type=\"hidden\" id=\"difer\" value=\""+(Float.parseFloat(totPre) - total_gasto)+"\" />");
            out.print("<input type=\"hidden\" id=\"idActCom\" name=\"idActCom\" value=\"-1\" />");
            
            out.print("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
            out.print("<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
            out.print("<td><input id=\"fac_fecha\" name=\"fac_fecha\" type=\"text\" class=\"texto\" maxlength=\"10\" style=\"width:80px\" onkeypress=\"_evaluar(event, '0123456789/')\" onkeyup=\"if(_gKC(event)!=8 && _gKC(event)!=46 && (this.value.length==2 || this.value.length==5))this.value+='/';\" />"
                    + "<input type=\"button\" value=\".\" style=\"width:20px;height:19px\" onClick=\"SelectorFecha.crear('fac_fecha', 'SQL');\" /></td>");
            out.print("<td><input id=\"fac_ruc\" name=\"fac_ruc\" type=\"text\" class=\"texto\" maxlength=\"13\" style=\"width:110px\" onkeypress=\"_numero(event)\" /></td>");
            out.print("<td><input id=\"fac_proveedor\" name=\"fac_proveedor\" type=\"text\" maxlength=\"160\" class=\"texto\" style=\"width:150px\" /></td>");
            out.print("<td><input id=\"fac_detalle\" name=\"fac_detalle\" type=\"text\" class=\"texto\" style=\"width:100px\" /></td>");
            out.print("<td><input id=\"fac_cantidad\" name=\"fac_cantidad\" type=\"text\" class=\"caja\" style=\"width:66px\" onkeypress=\"_evaluar(event, '0123456789.')\" /></td>");
            out.print("<td><input id=\"fac_num_documento\" name=\"fac_num_documento\" type=\"text\" maxlength=\"20\" class=\"texto\" style=\"width:120px\" onkeypress=\"_evaluar(event, '0123456789-')\" onkeyup=\"if(_gKC(event)!=8 && _gKC(event)!=46 && (this.value.length==3 || this.value.length==7))this.value+='-';\" /></td>");
            out.print("<td><input id=\"base_12\" name=\"base_12\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.');_('fac_base_0').value=0;\" onkeyup=\"mer_seguimientoSumar(event, true)\" /></td>");
            out.print("<td><input id=\"fac_base_0\" name=\"fac_base_0\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.');_('fac_base_12').value=0;\" onkeyup=\"mer_seguimientoSumar(event, true)\" /></td>");
            out.print("<td><input id=\"fac_iva\" name=\"fac_iva\" type=\"text\" class=\"caja\" style=\"width:60px\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_seguimientoSumar(event, false)\" /></td>");
            out.print("<td><input id=\"fac_total\" name=\"fac_total\" type=\"text\" class=\"caja\" style=\"width:70px\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_seguimientoValGastoTotal(this.value)\" /></td>");
            out.print("</tr>");
            out.print("</table>");
            
            out.print("<div style=\"text-align:right\"><input type=\"submit\" value=\"Registrar Factura\" />"
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
