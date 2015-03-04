/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

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
public class getUsrConfAusp extends HttpServlet {
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
        String html = "";
        
        PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try{
            html += "<h3 class=\"psPanelAzul\" style=\"color:#000\">NUMERO DE DIAS PARA CONFIRMAR AUSPICIOS</h3>";
            html += "<table><tr>";
            html += "<th style=\"width:378px\">USUARIO</th>";
            html += "<th style=\"width:77px\"># DIAS</th>";
            html += "</tr></table>";
            ResultSet rsUsCoAu = objPlanMerca.getUsuariosTipoPlan(id_tipo_plan);
            int j=0;
            String id_tipo_plan_usuario = "";
            String usuarioConf = "";
            String num_dias = "0";
            while(rsUsCoAu.next()){
                id_tipo_plan_usuario = rsUsCoAu.getString("id_tipo_plan_usuario")!=null ? rsUsCoAu.getString("id_tipo_plan_usuario") : "";
                usuarioConf = rsUsCoAu.getString("usuario")!=null ? rsUsCoAu.getString("usuario") : "";
                //num_dias = rsUsCoAu.getString("num_dias")!=null ? rsUsCoAu.getString("num_dias") : "0";
                html += "<div class=\"fila jm_filaImp\" onmouseover=\"this.className='fila jm_filaSobre'\" onmouseout=\"this.className='fila jm_filaImp'\">"+
                "<div class=\"columna\" style=\"width:378px\"><input type=\"hidden\" id=\"id_tipo_plan_usuario"+j+"\" name=\"id_tipo_plan_usuario"+j+"\" value=\""+id_tipo_plan_usuario+"\" />"+usuarioConf+"</div>"+
                "<div class=\"columna\" style=\"width:77px\"><input type=\"text\" class=\"caja\" onkeypress=\"_numero(event)\" id=\"num_dias"+j+"\" name=\"num_dias"+j+"\" size=\"8\" value=\""+num_dias+"\" /></div>"+
                "</div>";
                j++;
            }
            rsUsCoAu.close();
        }catch(Exception e){
            msg = e.getMessage();
        }finally{
            objPlanMerca.cerrar();
        }
        
        
        try {
            out.print("obj»axConfAus^msg»"+msg+"^frm»" + html );
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
