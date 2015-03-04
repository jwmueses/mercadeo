/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
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
public class frmNivel extends HttpServlet {
    private String easy_ip = null;
    private int easy_puerto = 1433;
    private String easy_db = null;
    private String easy_usuario = null;
    private String easy_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this.easy_ip = config.getServletContext().getInitParameter("EASY_IP");
        this.easy_puerto = Integer.parseInt(config.getServletContext().getInitParameter("EASY_PUERTO"));
        this.easy_db = config.getServletContext().getInitParameter("EASY_DB");
        this.easy_usuario = config.getServletContext().getInitParameter("EASY_USUARIO");
        this.easy_clave = config.getServletContext().getInitParameter("EASY_CLAVE");
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

        String ojP = request.getParameter("ojP");
        //String cod_nivelUp = request.getParameter("ctaUp");
        
        String msg = "";
        String html = "";
        BaseDatos objDB = new BaseDatos(this.easy_ip, this.easy_puerto, this.easy_db, this.easy_usuario, this.easy_clave);
        try{
            ResultSet rs = objDB.consulta("select cod_nivel, descripcion, case when cod_nivel in (select distinct cod_Padre from tbl_nivel with (nolock)) then 1 else 0 end as hoja "
                    + "from tbl_nivel with (nolock) where cod_Padre "+(ojP.compareTo("cmbC")==0?"=0":"="+ojP.replace("s", ""))+" order by descripcion");
            msg = objDB.getError();
            String cod_nivel = "";
            String descripcion = "";
            int hoja=1;
            while(rs.next()){
                cod_nivel = rs.getString("cod_nivel")!=null?rs.getString("cod_nivel"):"";
                descripcion = rs.getString("descripcion")!=null?rs.getString("descripcion"):"";
                hoja = rs.getString("hoja")!=null?rs.getInt("hoja"):1;
                if(hoja==1){
                    if(ojP.compareTo("cmbC")==0){
                        html += "<div id=\"r_0\" class=\"blockCarpeta\" onclick=\"mer_setNivel('-1', 'TODAS')\">"
                            + "<div id=\"t_0\">"
                                + "<img src=\"img/carpeta.png\" height=\"16\" />"
                                + "<a href=\"javascript:void(0)\" class=\"seleccion\" style=\"background-color:#F5F5F5\"> TODOS </a>"
                            + "</div>"
                            + "<div id=\"s_0\"></div>"
                        + "</div>";
                    }
                    html += "<div id=\"r" + cod_nivel + "\" class=\"blockCarpeta\">"
                            + "<div>"
                                + "<img id=\"t" + cod_nivel + "\" src=\"img/carpeta.png\" height=\"16\" style=\"cursor:pointer\" onclick=\"mer_getNiveles('s"+cod_nivel+"')\" />"
                                + "<a href=\"javascript:void(0)\" class=\"seleccion\" onclick=\"mer_setNivel('"+cod_nivel+"', '"+descripcion+"')\" style=\"background-color:#F5F5F5\"> " + descripcion+"</a>"
                            + "</div>"
                            + "<div id=\"s" + cod_nivel + "\"></div>"
                        + "</div>";
                }else{
                    html += "<div id=\"r" + cod_nivel + "\" class=\"blockCarpeta\" >"
                            + "<div>"
                                + "<img id=\"t" + cod_nivel + "\" src=\"img/carpeta.png\" height=\"16\" />"
                                + "<a href=\"javascript:void(0)\" class=\"seleccion\" onclick=\"mer_setNivel('"+cod_nivel+"', '"+descripcion+"')\" style=\"background-color:#F5F5F5\"> " + descripcion+"</a>"
                            + "</div>"
                            + "<div id=\"s" + cod_nivel + "\"></div>"
                        + "</div>";
                }
            }
            rs.close();
        }catch(Exception e){
            msg += e.getMessage();
        }finally{
            objDB.cerrar();
        }

        try {
            out.print("obj»"+ojP+"^msg»"+msg+"^frm»"+html);
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
