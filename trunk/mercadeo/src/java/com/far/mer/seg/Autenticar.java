/**
* @version 1.0
* @package FARMAENLACE.
* @author Jorge Washington Mueses Cevallos.
* @copyright Copyright (C) 2007 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
* PHPCoreMueses! es un software de libre distribución, que puede ser
* copiado y distribuido bajo los términos de la Licencia Pública
* General GNU, de acuerdo con la publicada por la Free Software
* Foundation, versión 2 de la licencia o cualquier versión posterior.
*/

package com.far.mer.seg;

import com.far.lib.BaseDatos;
import java.io.*;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.ws.WebServiceRef;
import org.tempuri.Wslogin;

public class Autenticar extends HttpServlet {
    //@WebServiceRef(wsdlLocation = "WEB-INF/wsdl/192.168.238.6/wsloginfarma/wslogin.asmx.wsdl")
    //private Wslogin service;

    private String segu_ip = null;
    private int segu_puerto = 1433;
    private String segu_db = null;
    private String segu_usuario = null;
    private String segu_clave = null;
    
    private String gene_ip = null;
    private int gene_puerto = 1433;
    private String gene_db = null;
    private String gene_usuario = null;
    private String gene_clave = null;
    
    public void init(ServletConfig config) throws ServletException
    {
        this.segu_ip = config.getServletContext().getInitParameter("SEGU_IP");
        this.segu_puerto = Integer.parseInt(config.getServletContext().getInitParameter("SEGU_PUERTO"));
        this.segu_db = config.getServletContext().getInitParameter("SEGU_DB");
        this.segu_usuario = config.getServletContext().getInitParameter("SEGU_USUARIO");
        this.segu_clave = config.getServletContext().getInitParameter("SEGU_CLAVE");
        
        this.gene_ip = config.getServletContext().getInitParameter("GENE_IP");
        this.gene_puerto = Integer.parseInt(config.getServletContext().getInitParameter("GENE_PUERTO"));
        this.gene_db = config.getServletContext().getInitParameter("GENE_DB");
        this.gene_usuario = config.getServletContext().getInitParameter("GENE_USUARIO");
        this.gene_clave = config.getServletContext().getInitParameter("GENE_CLAVE");
    }
    
    /**
    * Procesa peticiones HTTP del método <code>POST</code>.
    * @param request petición al servlet
    * @param response respuesta del servlet
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession sesion = request.getSession(true);
        sesion.setAttribute("usuario", "");

        String msg = "Usuario o Contraseña incorrectos.";
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");
        
        try{
            //String ok = this.isAuthenticated(usuario, clave);
            String ok = "OK";
            if(ok.toUpperCase().compareTo("OK")==0){
                msg = "0";
                String empleado = usuario;
                String cargo = "";
                String centro_costos = "";
                BaseDatos objDBSegu = new BaseDatos(this.segu_ip, this.segu_puerto, this.segu_db, this.segu_usuario, this.segu_clave);
                try{
                    ResultSet rsUsuario = objDBSegu.consulta("select Cedula from Usuarios with (nolock) where NombreCorto='"+usuario+"'");
                    if(rsUsuario.next()){
                        String cedula = rsUsuario.getString("Cedula")!=null ? rsUsuario.getString("Cedula") : "";
                        if(cedula.compareTo("")!=0){
                            BaseDatos objDBGene = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
                            try{
                                ResultSet rsEmpleado = objDBGene.consulta("select E.NOMBRES, E.APELLIDOS, C.DESCRIPCION, CC.DESCRIPCION as centro_costos "
                                        + "from (EMPLEADOS as E with (nolock) inner join CARGOS as C with (nolock) on E.CODIGO_CARGO=C.CODIGO_CARGO) "
                                        + "inner join CENTROS_COSTOS as CC with(nolock) on CC.CODIGO_CENTRO_COSTO=E.CODIGO_CENTRO_COSTO "
                                        + "where CEDULA='"+cedula+"'");
                                if(rsEmpleado.next()){
                                    empleado = rsEmpleado.getString("NOMBRES")!=null ? rsEmpleado.getString("NOMBRES") + " " : "";
                                    empleado += rsEmpleado.getString("APELLIDOS")!=null ? rsEmpleado.getString("APELLIDOS") : "";
                                    cargo = rsEmpleado.getString("DESCRIPCION")!=null ? rsEmpleado.getString("DESCRIPCION") + " " : "";
                                    centro_costos = rsEmpleado.getString("centro_costos")!=null ? rsEmpleado.getString("centro_costos"): "";
                                    rsEmpleado.close();
                                }
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }finally{
                                objDBGene.cerrar();
                            }
                            
                        }
                        rsUsuario.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    objDBSegu.cerrar();
                }
                
                
                sesion.setAttribute("usuario", usuario);
                sesion.setAttribute("empleado", empleado);
                sesion.setAttribute("cargo", cargo);
                sesion.setAttribute("centro_costos", centro_costos);
                
            }
        }catch(Exception e){
            e.printStackTrace();
            msg = "Usuario o Contraseña incorrectos.";
        }
       
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        try {
            if(msg.compareTo("0")==0){
                response.sendRedirect("index.php");
            }else{
                response.sendRedirect("index.jsp?msg="+msg);
            }
        } finally {
            out.close();
        }
    }

    /*private String isAuthenticated(java.lang.String username, java.lang.String pwd) {
        org.tempuri.WsloginSoap port = service.getWsloginSoap();
        return port.isAuthenticated(username, pwd);
    }*/

}