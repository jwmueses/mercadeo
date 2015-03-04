/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Cadena;
import com.far.lib.Fecha;
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
public class getPlanesMercadeo extends HttpServlet {
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

        String txt = request.getParameter("txt").toLowerCase();
        int estado = Integer.parseInt( request.getParameter("estado") );
        
        String msg = "";
        StringBuilder html = new StringBuilder();
        String id_plan_mercadeo_ini = "-1";
        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try{
            StringBuilder where = new StringBuilder();
            where.append("where P.fecha_creacion>='"+(Fecha.getAnio() + "-01-01 00:00:00.000" )+"'");
            switch(estado){
                case 3:
                    where.append(" and P.estado in(1,2,7,8) and P.mecanica_tipo='o' and aprobada_operaciones=0");
                break;
                case 4:
                    where.append(" and P.estado in(1,2,3) and P.tipo_alcance='f' and P.plan_completo=1");
                break;
                case 5:
                    where.append(" and P.estado in(1,2,3) and P.tipo_alcance='d' and P.plan_completo=1");
                break;
                case 6:
                    where.append(" and P.estado in(4,5)");
                break;
            }
            if(txt.compareTo("")!=0){
                if(Cadena.esNumero(txt)){
                    where.append(" and P.sec_tipo_plan="+txt);
                }else{
                    where.append(" and lower(P.plan_mercadeo) like '"+txt+"'");
                }
            }
            /*ResultSet rs = objDB.consulta("select distinct top(20) P.id_plan_mercadeo, P.plan_mercadeo, P.estado "
                    + "from (tbl_plan_mercadeo as P with (nolock) inner join tbl_estrategia as E with (nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) "
                    + "inner join tbl_actividad as A with (nolock) on E.id_estrategia=A.id_estrategia "
                    + where + " order by P.plan_mercadeo"); */
            ResultSet rs = objDB.consulta("select distinct P.id_plan_mercadeo, P.sec_tipo_plan, P.plan_mercadeo, P.estado "
                    + "from tbl_plan_mercadeo as P with (nolock) "
                    + where.toString() + " order by P.plan_mercadeo"); 
            
            
            msg = objDB.getError();
            String id_plan_mercadeo = "";
            String sec_tipo_plan = "";
            String plan_mercadeo = "";
            String estado1 = "";
            html.append("<table cellspacing=\"2\">");
            html.append("<tr>");
            html.append("<td colspan=\"2\" class=\"jm_filaSobre\" style=\"cursor:pointer\" onclick=\"mer_getActividades(-1, 0, "+estado+")\"> TODOS </td>");
            html.append("<td valign=\"middle\">&nbsp;</td>");
            html.append("</tr>");
            int i=0;
            while(rs.next()){
                id_plan_mercadeo = rs.getString("id_plan_mercadeo")!=null?rs.getString("id_plan_mercadeo"):"";
                sec_tipo_plan = rs.getString("sec_tipo_plan")!=null?rs.getString("sec_tipo_plan"):"";
                plan_mercadeo = rs.getString("plan_mercadeo")!=null?rs.getString("plan_mercadeo"):"";
                estado1 = rs.getString("estado")!=null?rs.getString("estado"):"0";
                if(id_plan_mercadeo_ini.compareTo("-1")==0){
                    id_plan_mercadeo_ini = id_plan_mercadeo;
                }
                html.append("<tr onmouseover=\"_('del" + i + "').style.visibility='visible';\" onmouseout=\"_('del" + i + "').style.visibility='hidden';\">");
                html.append("<td class=\"estado"+estado1+"\" style=\"width:35px\" style=\"cursor:pointer\" onclick=\"mer_getActividades(" + id_plan_mercadeo + ", 0, "+estado+")\">" + sec_tipo_plan + "</td>");
                html.append("<td class=\"estado"+estado1+"\" style=\"width:125px\" style=\"cursor:pointer\" onclick=\"mer_getActividades(" + id_plan_mercadeo + ", 0, "+estado+")\">" + plan_mercadeo + "</td>");
                html.append("<td valign=\"middle\" style=\"width:20px\"><div id=\"del" + i + "\" class=\"mostrar\" onclick=\"mer_mercadeoMostrar(" + id_plan_mercadeo + ", "+estado+")\" title=\"mostrar\">&nbsp;</div></td>");
                html.append("</tr>");
                i++;
            }
            html.append("</table>");
            rs.close();
        }catch(Exception e){
            msg = e.getMessage();
        }finally{
            objDB.cerrar();
        }

        try {
            out.print("obj»d_1^msg»"+msg+"^frm»"+html.toString());
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
