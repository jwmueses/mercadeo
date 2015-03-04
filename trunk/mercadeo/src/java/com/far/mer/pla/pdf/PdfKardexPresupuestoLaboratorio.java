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

package com.far.mer.pla.pdf;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.ResultSet;
import com.far.lib.Pdf;

import com.far.lib.BaseDatos;

/**
 *
 * @author Jorge
 */
public class PdfKardexPresupuestoLaboratorio extends HttpServlet {
    private String _ip = null;
    private int _puerto = 5432;
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
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("application/pdf");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");

        String idLab = request.getParameter("idLab");
        String laboratorio = request.getParameter("lab");

        BaseDatos objDB = new BaseDatos(this._ip, this._puerto, this._db, this._usuario, this._clave);
        ResultSet registros = objDB.consulta("select CONVERT(VARCHAR, fecha_registro, 103), CONVERT(VARCHAR, fecha_registro, 108), usuario, descripcion + ' para ' + nombre, "
                + "case when es_entrada=1 then cast(valor as varchar) else ' ' end, case when es_entrada=0 then cast(valor as varchar) else ' ' end, saldo "
                + "from tbl_tipo_plan  as P with (nolock) inner join  tbl_labortorio_tipo_plan_presupuesto_kardex as K with (nolock) on P.id_tipo_plan=K.id_tipo_plan where id_laboratorio="+idLab);
        
        String titulo = "FARMAENLACE";
        String ruc = "RUC: 1791984722001";
        String subtitulo = "Kardex de laboratorio estratégico " + laboratorio;
        String direccion = "AV. CAPITAN RAFAEL RAMOS E2-210 Y CASTELLI";
        String pie_pag = "Ecuador";
        Pdf objPdf = new Pdf(true, titulo, ruc, subtitulo, direccion, pie_pag);
        
        String [] cabTabla = new String [] {"Nro.", "FECHA", "HORA", "USUARIO", "DESCRIPCION", "ENTRADA", "SALIDA", "SALDOS"};
        float [] anchoTabla = new float[]{25f,45f,35f,60f,150f,45f,45f,45f};
        int [] alineaciones = new int[]{0,0,0,0,0,2,2,2};
        objPdf.lista(response, registros, cabTabla, anchoTabla, alineaciones);
        

        try{
            registros.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        objDB.cerrar();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
    * Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
    * Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
    * Returns a short description of the servlet.
    */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
