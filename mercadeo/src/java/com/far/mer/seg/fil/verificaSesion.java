/**
** @version 1.0
** @package FACTURAPYMES.
* @author Jorge Washington Mueses Cevallos.
* @copyright Copyright (C) 2011 por Jorge Mueses. Todos los derechos reservados.
* @license http://www.gnu.org/copyleft/gpl.html GNU/GPL.
** FACTURAPYMES es un software de libre distribución, que puede ser
* copiado y distribuido bajo los términos de la Licencia
* Attribution-NonCommercial-NoDerivs 3.0 Unported,
* de acuerdo con la publicada por la CREATIVE COMMONS CORPORATION.
*/

package com.far.mer.seg.fil;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
//import com.far.mer.seg.clas.Usuario;

public class verificaSesion implements Filter{
    //private FilterConfig config;
    /*private String urlAutenticar;

    private String _ip = null;
    private int _puerto = 5432;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;*/

    public void init(FilterConfig config) throws ServletException
    {
        /*this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");*/

        //Tambien se pueden cargar los parametros que configura la url de salida
        /*this.urlAutenticar = config.getInitParameter("Salir");
        if(urlAutenticar == null || urlAutenticar.trim().length() == 0) {
            //Error al cargar la página
            throw new ServletException("El tiempo de la sesión ha caducado, por favor, cierre el sistema y vuelva a ingresar.");
        }*/
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException
    {
        HttpServletRequest miReq = (HttpServletRequest)req;
        HttpSession sesion = miReq.getSession(true);
        String usuario = (String)sesion.getAttribute("usuario");
        usuario = (usuario!=null) ? usuario : "";
        if(usuario.compareTo("")==0){
            ((HttpServletResponse)res).sendRedirect(((HttpServletRequest)req).getContextPath() + "/urlAutenticar");
        }else{
            chain.doFilter(req, res);
            /*Usuario us = new Usuario(this._ip, this._puerto, this._db, this._usuario, this._clave);
            if(sesion.getId().compareTo(us.getSesion(usuario))==0){
                chain.doFilter(req, res);
            }else{
                ((HttpServletResponse)res).sendRedirect(((HttpServletRequest)req).getContextPath() + "/" + urlAutenticar);

            }
            us.cerrar();*/
        }
    }

    public void destroy() {
        //config = null;
    }
}
