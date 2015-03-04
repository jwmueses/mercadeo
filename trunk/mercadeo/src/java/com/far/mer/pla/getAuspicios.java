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
public class getAuspicios extends HttpServlet {
    private String AUS_ip = null;
    private int AUS_puerto = 1433;
    private String AUS_db = null;
    private String AUS_usuario = null;
    private String AUS_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this.AUS_ip = config.getServletContext().getInitParameter("AUS_IP");
        this.AUS_puerto = Integer.parseInt(config.getServletContext().getInitParameter("AUS_PUERTO"));
        this.AUS_db = config.getServletContext().getInitParameter("AUS_DB");
        this.AUS_usuario = config.getServletContext().getInitParameter("AUS_USUARIO");
        this.AUS_clave = config.getServletContext().getInitParameter("AUS_CLAVE");
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

        String idTP = request.getParameter("idTP");
        String msg = "";
        String html = "";
        BaseDatos objDB = new BaseDatos(this.AUS_ip, this.AUS_puerto, this.AUS_db, this.AUS_usuario, this.AUS_clave);
        try{
            
            ResultSet rs = objDB.consulta("select num_auspicio, ruc, proveedor, usuario_creacion, subtotal from vta_auspicio with (nolock) "
                    + "where estado=1 and id_tipo_plan='"+idTP+"'");
            msg = objDB.getError();
            String num_auspicio = "";
            String ruc = "";
            String proveedor = "";
            String usuario_creacion = "";
            String subtotal = "";
            int i=0;
            while(rs.next()){
                num_auspicio = rs.getString("num_auspicio")!=null?rs.getString("num_auspicio"):"";
                ruc = rs.getString("ruc")!=null?rs.getString("ruc"):"";
                proveedor = rs.getString("proveedor")!=null?rs.getString("proveedor"):"";
                usuario_creacion = rs.getString("usuario_creacion")!=null?rs.getString("usuario_creacion"):"";
                subtotal = rs.getString("subtotal")!=null?rs.getString("subtotal"):"0";
                html += "<tr class=\"tr1\" onmouseover=\"this.className='tr1Sobre'\" onmouseout=\"this.className='tr1'\">";
                html += "<td id=\"numAus"+i+"\">" + num_auspicio+"</td>";
                html += "<td><input type=\"hidden\" id=\"rucAus"+i+"\" value=\"" + ruc+"\" />";
                html += "<input type=\"hidden\" id=\"provAus"+i+"\" value=\"" + proveedor+"\" />" + proveedor+"</td>";
                html += "<td>" + usuario_creacion+"</td>";
                html += "<td align=\"right\" id=\"monAus"+i+"\">" + subtotal+"</td>";
                html += "<td align=\"center\"><input type=\"checkbox\" id=\"chAus"+i+"\" /></td>";
                html += "</tr>";
                i++;
            }
            rs.close();
        }catch(Exception e){
            msg = e.getMessage();
        }finally{
            objDB.cerrar();
        }

        try {
            out.print("obj»vta2_html^msg»"+msg+"^frm»");
            out.print("<table class=\"table1\" width=\"98%\" cellspacing=\"1\"><tr>"
                    + "<th class=\"th1\">Nro. de Auspicio</th>"
                    + "<th class=\"th1\">Proveedor</th>"
                    + "<th class=\"th1\">Usuario</th>"
                    + "<th class=\"th1\">Monto del Auspicio</th>"
                    + "<th class=\"th1\">Seleccionar</th></tr>"
                    +html+"</table>"
                    + "<p style=\"text-align:right\"><input type=\"button\" value=\"Aceptar\" onclick=\"mer_setAuspicios()\" /> &nbsp;&nbsp;&nbsp; </p>");
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
