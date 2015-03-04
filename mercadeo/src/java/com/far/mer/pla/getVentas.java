/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.mer.pla.clas.CalcularVentas;
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
public class getVentas extends HttpServlet {

    private String estd_ip = null;
    private int estd_puerto = 1433;
    private String estd_db = null;
    private String estd_usuario = null;
    private String estd_clave = null;
    
    private String easy_ip = null;
    private int easy_puerto = 1433;
    private String easy_db = null;
    private String easy_usuario = null;
    private String easy_clave = null;
    
    //private BaseDatos objEasy = null;
    //private String codigos = "";

    public void init(ServletConfig config) throws ServletException
    {
        this.estd_ip = config.getServletContext().getInitParameter("ESTD_IP");
        this.estd_puerto = Integer.parseInt(config.getServletContext().getInitParameter("ESTD_PUERTO"));
        this.estd_db = config.getServletContext().getInitParameter("ESTD_DB");
        this.estd_usuario = config.getServletContext().getInitParameter("ESTD_USUARIO");
        this.estd_clave = config.getServletContext().getInitParameter("ESTD_CLAVE");
        
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

        String msg = "";
        String fecha_ini = request.getParameter("fi");
        String fecha_fin = request.getParameter("ff");
        
        String cod_niveles = request.getParameter("prCNiv");
        String clases = request.getParameter("prCCla");
        String lineas = request.getParameter("prCLin");
        
        String articulos = request.getParameter("arts");
        String oficinas = request.getParameter("ofs");
        
        BaseDatos objDB = new BaseDatos(this.estd_ip, this.estd_puerto, this.estd_db, this.estd_usuario, this.estd_clave);
        BaseDatos objEasy = new BaseDatos(this.easy_ip, this.easy_puerto, this.easy_db, this.easy_usuario, this.easy_clave);

        /*String where = "where convert(datetime, (CAST(DIA AS varchar) + '/' + CAST(MES AS varchar) + '/' + CAST(ANIO AS varchar))) between '"+fecha_ini+"' and '"+fecha_fin+"'";
        if(cod_niveles.compareTo("") != 0){
            String vecNivles[] = cod_niveles.replace("'", "").split(",");
            String vecClases[] = clases.replace("'", "").split(",");
            String vecLineas[] = lineas.replace("'", "").split(",");
            String ax_cod_niveles = "";
            String ax_where = "";
            for(int i=0; i<vecNivles.length; i++){
                ax_where += "(";
                if(vecNivles[i].compareTo("")!=0 && vecNivles[i].compareTo("-1")!=0){
                    this.codigos = "";
                    ax_cod_niveles = this.getNiveles(vecNivles[i]);
                    ax_where += "COD_SEGMENTO in ("+ax_cod_niveles.substring(0, ax_cod_niveles.length()-2)+")";
                }
                if(vecClases[i].compareTo("") != 0 && vecClases[i].compareTo("0") != 0){
                    ax_where += (this.codigos.compareTo("")!=0 ? " or " : "") + "(COD_CLASE in ("+clases+")";
                    if(vecLineas[i].compareTo("") != 0 && vecLineas[i].compareTo("0") != 0){
                        ax_where += " and COD_LINEA in ("+lineas+")";
                    }
                    ax_where += ")";
                }
                if(ax_where.compareTo("(") == 0){
                    ax_where = "";
                }else{
                    ax_where += ") or ";
                }
            }
            if(ax_where.compareTo("")!=0){
                where += " and (" + ax_where.substring(0, ax_where.length()-4) + ")";
            }
        }
        
        if(articulos.compareTo("") != 0 && articulos.compareTo("'0'") != 0 && cod_niveles.compareTo("") == 0){
            where += " and COD_ARTICULO in ("+articulos+")";
        }
        if(oficinas.compareTo("") != 0 && oficinas.compareTo("'0'") != 0){
            where += " and BODEGA in ("+oficinas+")";
        }
        ResultSet rs = objDB.consulta("select BODEGA, cast(sum(TOTAL - VALOR_IVA - DESCUENTO) as numeric(19, 2)) "
                + "from TBL_VENTAS_DIARIAS_ARTICULO with (nolock) "
                + where
                + " group by BODEGA order by BODEGA");
        
        msg = objDB.getError();*/
        
        try {
            CalcularVentas ventas = new CalcularVentas(objDB, objEasy);
            ResultSet rs = ventas.calcular(fecha_ini, fecha_fin, cod_niveles, clases, lineas, articulos, oficinas);
            out.print("obj»axVentas^msg»"+msg+"^fun»mer_setVentas()^frm»" + objDB.getJSON(rs));
        } finally {
            objEasy.cerrar();
            objDB.cerrar();
            out.close();
        }
    }
    
    
    /*public String getNiveles(String cod_nivel)
    {
        try{
            ResultSet rs = this.objEasy.consulta("select cod_nivel from tbl_nivel with (nolock) where cod_Padre="+cod_nivel+" order by cod_nivel");
            int filas = this.objEasy.getFilas(rs);
            if(filas<=0){
                this.codigos += cod_nivel + ", ";
            }else{
                String nivel;
                while(rs.next()){
                    nivel = rs.getString("cod_nivel")!=null ? rs.getString("cod_nivel") : "-1";
                    this.getNiveles(nivel);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return this.codigos;
    }*/

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
