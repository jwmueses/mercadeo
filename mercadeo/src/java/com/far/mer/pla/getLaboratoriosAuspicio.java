/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.Matriz;
import com.far.mer.pla.clas.Laboratorio;
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
public class getLaboratoriosAuspicio extends HttpServlet {
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

        String id_plan_mercadeo = request.getParameter("id");
        String id_tipo_plan = request.getParameter("idTiPl");
        String msg = "";
        
        PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        Laboratorio objLaboratorio = new Laboratorio(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        String html = "<table cellpadding=\"0\" cellspacing=\"0\">";
        try{
            ResultSet rsLabs = objLaboratorio.getLaboratoriosAsignables(id_plan_mercadeo, id_tipo_plan);

            ResultSet rsAus = objPlanMerca.getAuspicios(id_plan_mercadeo);
            String laboratorios[][] = Matriz.ResultSetAMatriz(rsAus);
            String id_laboratorio = "";
            String nombre_comercial = "";
            float presupuesto = 0;
            float monto = 0;
            float saldo = 0;
            int i=0;
            int pos=0;
            html += "<tr><th width=\"300\">LABORATORIO</th><th>PRE. DIS.</th><th>MONTO</th><th>SALDO</th></tr>";
            while(rsLabs.next()){
                id_laboratorio = rsLabs.getString("id_laboratorio")!=null ? rsLabs.getString("id_laboratorio") : "";
                nombre_comercial = rsLabs.getString("nombre_comercial")!=null ? rsLabs.getString("nombre_comercial") : "";
                presupuesto = rsLabs.getString("presupuesto")!=null ? rsLabs.getFloat("presupuesto") : 0;
                saldo = rsLabs.getString("saldo")!=null ? rsLabs.getFloat("saldo") : 0;
                pos = Matriz.enMatriz(laboratorios, id_laboratorio, 0);
                monto = pos>=0 ? Float.parseFloat(laboratorios[pos][1]) : 0;
                presupuesto += monto; 
                html += "<tr onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">";
                html += "<td width=\"300\"><input type=\"hidden\" id=\"idLab"+i+"\" name=\"idLab"+i+"\" value=\""+id_laboratorio+"\" />"+nombre_comercial+"</td>"
                        + "<td><input type=\"hidden\" id=\"presupuesto"+i+"\" name=\"presupuesto"+i+"\" value=\""+presupuesto+"\" />"+presupuesto+"</td>";
                html += "<td><input type=\"text\" class=\"caja\" id=\"monto"+i+"\" name=\"monto"+i+"\" size=\"6\" value=\""+monto+"\" onkeypress=\"_numero(event)\" "
                        + "onkeyup=\"_('saldo"+i+"').value=parseFloat(_('presupuesto"+i+"').value)-parseFloat(this.value);mer_verBtnAutorizacion(0);mer_sumAuspicios();\" /></td>";
                html += "<td><input type=\"text\" class=\"caja\" id=\"saldo"+i+"\" size=\"6\" value=\""+saldo+"\" readonly /></td>";
                html += "</tr>";
                i++;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            objLaboratorio.cerrar();
        }
        html += "</table>";
        
        try {
            out.print("obj»axLabsAus^msg»"+msg+"^frm»" + html );
        } finally {
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
