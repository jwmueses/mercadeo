/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.Matriz;
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
public class frmPlanMercdeoConfAuspicio extends HttpServlet {

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
       
        PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        String id_tipo_plan="";
        String plan_mercadeo="";
        try{
            ResultSet rs = objPlanMerca.getPlanMercadeo(id);
            if(rs.next()){
                id_tipo_plan = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
                plan_mercadeo = rs.getString("plan_mercadeo")!=null ? rs.getString("plan_mercadeo") : "";
                //plan_completo = (rs.getString("plan_completo")!=null) ? rs.getInt("plan_completo") : 0;
                //abierto = (rs.getString("abierto")!=null) ? rs.getInt("abierto") : 1;
                rs.close();
            }
        }catch(Exception e){
            msg = e.getMessage();
        }
        
        try {
            int i=0;
            out.print("obj»d_3^msg»"+msg+"^frm»");
            out.print("<form action=\"frmPlanMercdeoConfAuspicioGuardar\" onsubmit=\"return Ajax.enviarForm(this)\" autocomplete=\"off\">");
            out.print("<input type=\"hidden\" id=\"id_plan_mercadeo\" name=\"id_plan_mercadeo\" value=\""+id+"\" />");
            
            out.print("<div>");
            out.print("<h3>PLAN DE MERCADEO \""+plan_mercadeo+"\"</h3>");
            out.print("<h4 class=\"psPanelVerde\" style=\"color:#000\">NUMERO DE DIAS PARA CONFIRMAR AUSPICIOS</h4>");
            out.print("<table><tr>");
            out.print("<th style=\"width:200px\">USUARIO</th>");
            out.print("<th style=\"width:77px\"># DIAS</th>");
            out.print("</tr></table>");
            try{
                ResultSet rsUsCoAu = objPlanMerca.getUsuariosConfAusp(id);
                String tiempos[][] = Matriz.ResultSetAMatriz(rsUsCoAu);

                int j=0;
                String id_tipo_plan_usuario = "";
                String usuarioConf = "";
                String num_dias = "";
                int pos=-1;
                ResultSet rsUsTipoPlan = objPlanMerca.getUsuariosTipoPlan(id_tipo_plan);
                while(rsUsTipoPlan.next()){
                    id_tipo_plan_usuario = rsUsTipoPlan.getString("id_tipo_plan_usuario")!=null ? rsUsTipoPlan.getString("id_tipo_plan_usuario") : "";
                    usuarioConf = rsUsTipoPlan.getString("usuario")!=null ? rsUsTipoPlan.getString("usuario") : "";
                    pos = Matriz.enMatriz(tiempos, id_tipo_plan_usuario, 0);
                    num_dias = pos!=-1?tiempos[pos][1]:"";
                    //num_dias = rsUsCoAu.getString("num_dias")!=null ? rsUsCoAu.getString("num_dias") : "";
                    out.print("<div class=\"fila jm_filaImp\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"+
                    "<div class=\"columna\" style=\"width:198px\"><input type=\"hidden\" id=\"id_tipo_plan_usuario"+j+"\" name=\"id_tipo_plan_usuario"+j+"\" value=\""+id_tipo_plan_usuario+"\" />"+usuarioConf+"</div>"+
                    "<div class=\"columna\" style=\"width:77px\"><input type=\"text\" class=\"caja\" onkeypress=\"_numero(event)\" id=\"num_dias"+j+"\" name=\"num_dias"+j+"\" size=\"8\" value=\""+num_dias+"\" /></div>"+
                    "</div>");
                    j++;
                }
                rsUsCoAu.close();
            }catch(Exception ex){
                msg = ex.getMessage();
            }
            out.print("</div>");
            out.print("<div style=\"text-align:right\"><input type=\"submit\" value=\"Guardar\" /></div>");
            out.print("</form>");
            
        } finally {
            objPlanMerca.cerrar();
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
