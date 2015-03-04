/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Cadena;
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
public class getPlanesMercadeoEditar extends HttpServlet {
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
        //HttpSession sesion = request.getSession(true);
        //String usuario = (String)sesion.getAttribute("usuario");
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String txt = request.getParameter("txt").toLowerCase();
        String estado = request.getParameter("estado");
        String usuario = request.getParameter("usr");
        //String fecha_ini = request.getParameter("fi");
        //String fecha_fin = request.getParameter("ff");
        
        String msg = "";
        StringBuilder html = new StringBuilder();
        String id_plan_mercadeo_ini = "-1";
        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        try{
            String where = "where";
            if(estado.compareTo("0")!=0){
                where += " estado in("+estado+")";
            }
            if(usuario.compareTo("")!=0 && usuario.compareTo("0")!=0){
                where += (where.compareTo("where")!=0?" and":"") + " usuario_creacion='"+usuario+"'";
            }
            /*if(fecha_ini.compareTo("")!=0 && fecha_fin.compareTo("")==0){
                where += (where.compareTo("where")!=0?" and":"") + " fecha_creacion>='"+fecha_ini+" 00:00:00.000'";
            }
            if(fecha_ini.compareTo("")!=0 && fecha_fin.compareTo("")!=0){
                where += (where.compareTo("where")!=0?" and":"") + " fecha_creacion between '"+fecha_ini+" 00:00:00.000' and '"+fecha_fin+" 23:59:59.999'";
            }*/
            if(txt.compareTo("")!=0){
                if(Cadena.esNumero(txt)){
                    where += (where.compareTo("where")!=0?" and":"") + " sec_tipo_plan="+txt;
                }else{
                    where += (where.compareTo("where")!=0?" and":"") + " lower(plan_mercadeo) like '"+txt+"'";
                }
            }
            if(where.compareTo("where")==0){
                where = "";
            }
            
            ResultSet rs = objDB.consulta("select id_plan_mercadeo, sec_tipo_plan, plan_mercadeo, estado from tbl_plan_mercadeo with (nolock) "
                    + where + " order by plan_mercadeo");
            msg = objDB.getError();
            String id_plan_mercadeo = "";
            String sec_tipo_plan = "";
            String plan_mercadeo = "";
            String estado1 = "";
            html.append("<table cellspacing=\"1\">");
            html.append("<tr>");
            html.append("<td colspan=\"2\" class=\"jm_filaSobre\" style=\"cursor:pointer\" onclick=\"mer_getActividades(-1, 0, 0)\"> TODOS </td>");
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
                html.append("<td class=\"estado"+estado1+"\" style=\"width:35px\" style=\"cursor:pointer\" onclick=\"mer_getActividades(" + id_plan_mercadeo + ", 0, 0)\">" + sec_tipo_plan + "</td>");
                html.append("<td class=\"estado"+estado1+"\" style=\"width:125px\" style=\"cursor:pointer\" onclick=\"mer_getActividades(" + id_plan_mercadeo + ", 0, 0)\">" + plan_mercadeo + "</td>");
                html.append("<td valign=\"middle\" style=\"width:20px\"><div id=\"del" + i + "\" class=\"editar\" onclick=\"mer_mercadeoEditar(" + id_plan_mercadeo + ")\" title=\"editar\">&nbsp;</div></td>");
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
