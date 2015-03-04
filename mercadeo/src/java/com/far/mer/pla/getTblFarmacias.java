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
public class getTblFarmacias extends HttpServlet {
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

        String sucursal = request.getParameter("idSuc");
        String zona = request.getParameter("idProv");
        String ciudad = request.getParameter("idCiud");
        String txtFar = request.getParameter("txtFar");
        String msg = "";
        ResultSet rsFarmacias = null;
        
        PlanMercadeo objPlanMerca = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        BaseDatos objEasy = new BaseDatos(this.easy_ip, this.easy_puerto, this.easy_db, this.easy_usuario, this.easy_clave);
        
        try{
            String where = "where estado='A' and Oficina not in('000', 'TOT')";
            if(sucursal.compareTo("0")!=0){
                where += " and Sucursal='"+sucursal+"'";
            }
            if(zona.compareTo("0")!=0){
                where += (where.compareTo("where")!=0 ? " and" : "") + " provincia='"+zona+"'";
            }
            if(ciudad.compareTo("0")!=0){
                where += (where.compareTo("where")!=0 ? " and" : "") + " canton='"+ciudad+"'";
            }
            if(txtFar.compareTo("")!=0){
                where += (where.compareTo("where")!=0 ? " and" : "") + " lower(Nombre) like '%"+txtFar+"%'";
            }
            
            rsFarmacias = objEasy.consulta("select Oficina, Nombre from Oficina with (nolock) "+where+" order by Nombre");
        }catch(Exception e){
            msg = e.getMessage();
        }
        

        try {
            out.print("obj»axFarmaciasFil^msg»"+msg+"^frm»");
                
            int i=0;
            String Oficina="";
            String Nombre="";
            while(rsFarmacias.next()){
                Oficina = rsFarmacias.getString("Oficina")!=null ? rsFarmacias.getString("Oficina") : "";
                Nombre = rsFarmacias.getString("Nombre")!=null ? rsFarmacias.getString("Nombre") : "";
                out.print("<div class=\"jm_filaImp\" id=\"axrF"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">"
                        + "<input type=\"hidden\" id=\"axOficina"+i+"\" value=\""+Oficina+"\" />"
                        + "<input type=\"hidden\" id=\"axNombre"+i+"\" value=\""+Nombre+"\" />"
                        + "<label><input type=\"checkbox\" id=\"ch"+i+"\" /> "+Nombre+"</label>"+
                "</div>");
                i++;
            }
        }catch(Exception e){
            msg = e.getMessage();
        } finally {
            objPlanMerca.cerrar();
            objEasy.cerrar();
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
