/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.Fecha;
import com.far.lib.Matriz;
import com.far.mer.pla.clas.Actividad;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jorge
 */
public class getActividadesEditar extends HttpServlet {
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

        String id_plan_mercadeo = request.getParameter("id");
        String fecha = request.getParameter("fecha");
        String usuario = request.getParameter("usr");
        String txt = request.getParameter("txt");
        String estados = request.getParameter("estados");
        String _altTbl = request.getParameter("_altTbl");
        int altoTabla = Integer.parseInt(_altTbl) + 16;

        String msg = "";
        String html = "";
        
        Actividad objActividad = new Actividad(this._ip, this._puerto, this._db, this._usuario, this._clave);
        
        try{
            int anio = Fecha.datePart("anio", fecha);
            int mes = Fecha.datePart("mes", fecha);
            
            int dm = Fecha.datePart("dia", fecha);
            int ds = Fecha.getDiaSemana(fecha)-2;
            int ini = dm-ds;
            int numDiasMes = Fecha.getUltimoDiaMes(anio, mes);
            int vec[] = {0, 1, 2, 3, 4, 5, 6};   // 0=lunes
            int p=0;

            if(ini<=0){
                String fecha_ant = Fecha.sumar(fecha, Calendar.MONTH, -1);
                anio = Fecha.datePart("anio", fecha_ant);
                mes = Fecha.datePart("mes", fecha_ant);
                numDiasMes = Fecha.getUltimoDiaMes(anio, mes);
                ini = numDiasMes + ini;
            }

            for(int j=ini; j<ini+7; j++){
                int dia = j;
                if(dia>numDiasMes){
                    dia = dia - numDiasMes;
                }
                vec[p++] = dia;
            }
            
            String axfecha_ini = (vec[0]<10?"0"+vec[0]:vec[0]) + "/" + (mes<10?"0"+mes:mes) + "/" + anio;
            ResultSet rsActividades = objActividad.getActividadesCalendario(id_plan_mercadeo, usuario, axfecha_ini, txt, estados);
            String matActividades[][] = Matriz.ResultSetAMatriz(rsActividades);
            
            html += "<table class=\"agTabla\" width=\"100%\" cellpadding=\"0\" cellspacing=\"1\">";
            html += "<tr><th class=\"agTH\" nowrap>Lunes "+vec[0]+"</th><th class=\"agTH\" nowrap>Martes "+vec[1]+"</th>"
                    + "<th class=\"agTH\" nowrap>Mi&eacute;rcoles "+vec[2]+"</th><th class=\"agTH\" nowrap>Jueves "+vec[3]+"</th>"
                    + "<th class=\"agTH\" nowrap>Viernes "+vec[4]+"</th><th class=\"agTH\" nowrap>S&aacute;bado "+vec[5]+"</th>"
                    + "<th class=\"agTH\" nowrap>Domingo "+vec[6]+"</th></tr>";
            
            html += "<tr>";
            for(int i=0; i<vec.length; i++){
                axfecha_ini = (vec[i]<10?"0"+vec[i]:vec[i]) + "/" + (mes<10?"0"+mes:mes) + "/" + anio;
                html += "<td class=\"agColDef\" valign=\"top\" height=\""+altoTabla+"\" >";
                html += this.getActividades(matActividades, axfecha_ini);
                html += "</td>";
            } 
            html += "</tr>";
            html += "</table>";
            
        }catch(Exception e){
            msg = e.getMessage();
            html="";
        }finally{
            objActividad.cerrar();
        }

        try {
            out.print("obj»d_3^msg»"+msg+"^frm»"+html);
        } finally {
            out.close();
        }
    }

    public String getActividades(String [][] matActividades, String fecha)
    {
        String actividades = "";
        for(int i=0; i<matActividades.length; i++){
            if(matActividades[i][2].compareTo(fecha)==0){
                actividades += "<input type=\"text\" class=\"psPanelAzul\" style=\"cursor:pointer;\" onclick=\"mer_actividadMostrar("+matActividades[i][0]+", 0)\" "
                        + "value=\""+matActividades[i][1]+"\" title=\""+matActividades[i][1]+"\" onkeydown=\"_NoE(event)\" readonly /><br />";
            }
        }
        return actividades;
        
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
