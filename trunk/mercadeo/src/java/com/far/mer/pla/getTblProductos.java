/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
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
public class getTblProductos extends HttpServlet {
    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String easy_ip = null;
    private int easy_puerto = 1433;
    private String easy_db = null;
    private String easy_usuario = null;
    private String easy_clave = null;
    
    private BaseDatos objEasy = null;
    private String codigos = "";

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
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

        String cod_nivel = request.getParameter("ax_cod_nivel");
        String clase = request.getParameter("ax_clase");
        String linea = request.getParameter("ax_linea");
        String txtProd = request.getParameter("txtPro");
        int  p = request.getParameter("pag") != null ? Integer.parseInt( request.getParameter("pag") ) : 0;
        long numPags = 0;
        int fxp = 50;
            
            
        String msg = "";
        String [][] rsProductos = null;
        
        PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        this.objEasy = new BaseDatos(this.easy_ip, this.easy_puerto, this.easy_db, this.easy_usuario, this.easy_clave);
        
        try{
            String tabla = "tbl_articulos";
            String campos = "codigo, descripcion";    
            String where = "where status in ('a', 'p', 'g', 'v', 'd')";
            if(txtProd.compareTo("")!=0){
                where += " and lower(descripcion) like '"+txtProd.toLowerCase()+"'";
            }else{
                if(cod_nivel.compareTo("0")!=0 && cod_nivel.compareTo("-1")!=0){
                    this.codigos = "";
                    String cod_niveles = this.getNiveles(cod_nivel);
                    where += " and cod_nivel in ("+ cod_niveles.substring(0, cod_niveles.length()-2) +")";
                }
                if(clase.compareTo("0")!=0){
                    where += " and clase='"+clase+"'";
                }
                if(linea.compareTo("0")!=0){
                    where += " and linea='"+linea+"'";
                }
            }
            
            rsProductos = objEasy.paginar(tabla, campos, where, p, fxp);
            numPags = objEasy.getNumPaginas();
        }catch(Exception e){
            msg = e.getMessage();
        }
        

        try {
            StringBuilder html = new StringBuilder();
            
            html.append("<table>");
            html.append("<tr>");
            html.append("<th width=\"410\">PRODUCTO</th>");
            html.append("</tr>");
            html.append("</table>");
            String codigo;
            String descripcion;
            html.append("<div id=\"axProv\" class=\"tabla borde\" style=\"width:725px\">");
            if(rsProductos != null){
                for(int i=0; i<rsProductos.length; i++){
                    codigo = rsProductos[i][0];
                    descripcion = rsProductos[i][1];
                    html.append("<div class=\"jm_filaImp\" id=\"axrP"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">");
                    html.append("<input type=\"hidden\" id=\"axCodigo"+i+"\" value=\""+codigo+"\" />");
                    html.append("<input type=\"hidden\" id=\"axDescripcion"+i+"\" value=\""+descripcion+"\" />");
                    html.append("<label><input type=\"checkbox\" id=\"ch"+i+"\" /> "+descripcion+"</label>");
                    html.append("</div>");
                }
            }
            html.append("</div>");

            out.print("obj»axProductosFil^msg»"+msg+"^fun»_('axd_nav_pags').innerHTML=axNpg="+numPags+";^frm»"+html.toString());
            
        }catch(Exception e){
            msg = e.getMessage();
        } finally {
            objPlanMerca.cerrar();
            this.objEasy.cerrar();
            out.close();
        }
    }

    public String getNiveles(String cod_nivel)
    {
        try{
            ResultSet rs = this.objEasy.consulta("select cod_nivel "
                    + "from tbl_nivel with (nolock) where cod_Padre="+cod_nivel+" order by cod_nivel");
            int filas = this.objEasy.getFilas(rs);
            if(filas<=0){
                this.codigos += cod_nivel + ", ";
            }else{
                String nivel;
                //int hoja;
                while(rs.next()){
                    nivel = rs.getString("cod_nivel")!=null ? rs.getString("cod_nivel") : "-1";
                    //hoja = rs.getString("hoja")!=null ? rs.getInt("hoja") : 1;
                    //if(hoja==1){
                    //    this.codigos += nivel + ", ";
                    //}else{
                        this.getNiveles(nivel);
                    //}
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return this.codigos;
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
