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
public class getEmpleados extends HttpServlet {
    private String gene_ip = null;
    private int gene_puerto = 1433;
    private String gene_db = null;
    private String gene_usuario = null;
    private String gene_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this.gene_ip = config.getServletContext().getInitParameter("GENE_IP");
        this.gene_puerto = Integer.parseInt(config.getServletContext().getInitParameter("GENE_PUERTO"));
        this.gene_db = config.getServletContext().getInitParameter("GENE_DB");
        this.gene_usuario = config.getServletContext().getInitParameter("GENE_USUARIO");
        this.gene_clave = config.getServletContext().getInitParameter("GENE_CLAVE");
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
        String obj = request.getParameter("obj");
        String onClick = request.getParameter("onC");
        String msg = "";
        StringBuilder html = new StringBuilder();
        BaseDatos objDB = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        try{
            ResultSet rs = objDB.consulta("select top(20) E.CEDULA, E.APELLIDOS + ' ' + E.NOMBRES as empleado, E.EMAIL, U.NombreCorto, CC.DESCRIPCION "
                    + "from (EMPLEADOS as E with (nolock) inner join usuarios as U with (nolock) on U.Cedula=E.CEDULA COLLATE Traditional_Spanish_CI_AS) "
                    + "inner join CENTROS_COSTOS as CC with(nolock) on CC.CODIGO_CENTRO_COSTO=E.CODIGO_CENTRO_COSTO "
                    + "where lower(E.APELLIDOS + ' ' + E.NOMBRES) like '%"+txt+"%' order by empleado");
            msg = objDB.getError();
            String CEDULA = "";
            String empleado = "";
            String EMAIL = "";
            String NombreCorto = "";
            String CENTRO_COSTO = "";
            while(rs.next()){
                CEDULA = rs.getString("CEDULA")!=null?rs.getString("CEDULA"):"";
                empleado = rs.getString("empleado")!=null?rs.getString("empleado"):"";
                EMAIL = rs.getString("EMAIL")!=null?rs.getString("EMAIL"):"";
                NombreCorto = rs.getString("NombreCorto")!=null?rs.getString("NombreCorto"):"";
                CENTRO_COSTO = rs.getString("DESCRIPCION")!=null?rs.getString("DESCRIPCION"):"";
                html.append("<option value=\"" + CEDULA + "|" + EMAIL + "|" + NombreCorto + "|" + CENTRO_COSTO + "\">" + empleado +"</option>");
            }
            rs.close();
        }catch(Exception e){
            msg = e.getMessage();
        }finally{
            objDB.cerrar();
        }

        try {
            out.print("obj»"+obj+"^msg»"+msg+"^frm»");
            out.print("<select id=\"emp\" size=\"5\" onclick=\""+onClick+"\">"+html.toString()+"</select>");
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
