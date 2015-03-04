/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Correo;
import com.far.lib.Fecha;
import com.far.mer.pla.clas.Configuracion;
import com.far.mer.pla.clas.Laboratorio;
import com.far.mer.pla.clas.PlanMercadeo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jorge
 */
public class confirmarGasto extends HttpServlet {
    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String depr_ip = null;
    private int depr_puerto = 1433;
    private String depr_db = null;
    private String depr_usuario = null;
    private String depr_clave = null;
    
    private String AUS_ip = null;
    private int AUS_puerto = 1433;
    private String AUS_db = null;
    private String AUS_usuario = null;
    private String AUS_clave = null;
    
    private String _svr_mail = null;
    BaseDatos objDB = null;
    

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this.depr_ip = config.getServletContext().getInitParameter("DEPR_IP");
        this.depr_puerto = Integer.parseInt(config.getServletContext().getInitParameter("DEPR_PUERTO"));
        this.depr_db = config.getServletContext().getInitParameter("DEPR_DB");
        this.depr_usuario = config.getServletContext().getInitParameter("DEPR_USUARIO");
        this.depr_clave = config.getServletContext().getInitParameter("DEPR_CLAVE");
        
        this.AUS_ip = config.getServletContext().getInitParameter("AUS_IP");
        this.AUS_puerto = Integer.parseInt(config.getServletContext().getInitParameter("AUS_PUERTO"));
        this.AUS_db = config.getServletContext().getInitParameter("AUS_DB");
        this.AUS_usuario = config.getServletContext().getInitParameter("AUS_USUARIO");
        this.AUS_clave = config.getServletContext().getInitParameter("AUS_CLAVE");
        
        this._svr_mail = config.getServletContext().getInitParameter("_SVR_MAIL");
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
        HttpSession sesion = request.getSession(true);
        String empleado = (String)sesion.getAttribute("empleado");
        String cargo = (String)sesion.getAttribute("cargo");
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String id_plan_mercadeo = request.getParameter("idPM");
        String id_prov = request.getParameter("id_prov");
        String obj = request.getParameter("obj");
        String numAus = request.getParameter("numAus");
        String monto = request.getParameter("monto");
        String pos = request.getParameter("i");
        
        String msg = "";
        String fun = "_('"+obj+"').style.visibility='visible';";
        PlanMercadeo objPM = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        BaseDatos objAus = new BaseDatos(this.AUS_ip, this.AUS_puerto, this.AUS_db, this.AUS_usuario, this.AUS_clave);
        //Laboratorio objLab = new Laboratorio(this._ip, this._puerto, this._db, this._usuario, this._clave);
        this.objDB = new BaseDatos(this.depr_ip, this.depr_puerto, this.depr_db, this.depr_usuario, this.depr_clave);
        try{
            String usuario="";
            String sec_tipo_plan="";
            String id_tipo_plan="";
            String plan_mercadeo="";
            ResultSet rs = objPM.getPlanMercadeo(id_plan_mercadeo);
            if(rs.next()){
                usuario = rs.getString("usuario_creacion")!=null ? rs.getString("usuario_creacion") : "";
                sec_tipo_plan = rs.getString("sec_tipo_plan")!=null ? rs.getString("sec_tipo_plan") : "";
                id_tipo_plan = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
                plan_mercadeo = rs.getString("plan_mercadeo")!=null ? rs.getString("plan_mercadeo") : "";
                rs.close();
            }
            ResultSet rsUs = this.objDB.consulta("select * from USUARIOS with (nolock) where LOGIN='"+usuario+"'");
            /*if(this.objDB.getFilas(rsUs)>0){
                String codigo_usuario = "";
                if(rsUs.next()){
                    codigo_usuario = rsUs.getString("codigo_usuario")!=null ? rsUs.getString("codigo_usuario") : "0";
                }
                //ResultSet rsPr = this.objDB.consulta("select * from PROVEEDORES with (nolock) where NUMERO_IDPROVEEDOR='"+id_prov+"'");
                //if(this.objDB.getFilas(rsPr)>0){*/
                    String nombre_razon = "";
                    /*if(rsPr.next()){
                        nombre_razon = rsPr.getString("NOMBRE_RAZON")!=null ? rsPr.getString("NOMBRE_RAZON") : "";
                    }*/
                    String sec = this.getSecFactura();
                    
                    if(objPM.setConfirmacion(id_plan_mercadeo, id_prov, numAus, 1)){
                        if(objAus.ejecutar("update tbl_auspicio set tipo_confirmacion='a', estado='9' where num_auspicio='"+numAus+"'")){
                            
                            // solo para pruebas
                            fun += "_('"+obj+"').className='aprobado';_('"+obj+"').title='Confirmado';_('"+obj+"').onclik='';_('btnAnular').style.visibility='hidden';_('elAusNoEst"+pos+"').style.visibility='hidden';";
                            
                            /*List sql = new ArrayList();
                            sql.add("insert into FACTURAS(NUM_FACTURA, NUMERO_IDPROVEEDOR, FECHA, CODIGO_USUARIO, MOVIMIENTO, difTotal, mes, PENDIENTE, SALDO_KARDEX, FECHASIS, FECHASOLUCIONINC) "
                                    + "values('PM"+sec+"', '"+id_prov+"', getdate(), '"+codigo_usuario+"', -1, "+monto+", 'MES', 'S', "+monto+", getdate(), '1990/01/01');");

                            sql.add("insert into KARDEXLABORATORIOS(CODKARDEX, FECHA, ENTRADA, SALIDA, SALDOS, DESCRIPCION, NUM_DOCUMENTO, TIPO_DOCUMENTO, NUM_FACTURA, NUMERO_IDPROVEEDOR, CODIGO_USUARIO) "
                                    + "values('"+sec+"', getdate(), '"+monto+"', 0, "+monto+", 'PLAN MERCADEO "+Fecha.getAnio()+"_"+plan_mercadeo+"- PLAN MERCADEO -GENERADO POR AUSPICIOS DEL PLAN MERCADEO "+plan_mercadeo+" CON ABREVIATURA "+id_tipo_plan+" NUMERO: "+sec_tipo_plan+
                                    "', 'PM"+sec+"', 'PM', 'PM"+sec+"', '"+id_prov+"', '"+codigo_usuario+"');");
                            if(this.objDB.transacciones(sql)){
                                Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
                                String remitente = objConfiguracion.getValor("mail_remitente");
                                String para_comercial = objConfiguracion.getValor("mail_comercial");
                                objConfiguracion.cerrar();

                                String asunto = "CONFIRMACION DE AUSPICIOS";
                                //String para = this.getDestino();
                                String mensaje = "<font face='Verdana' size='2'><p>Sr(a).</p><strong>COORDINADOR COMERCIAL</strong><br />"
                                        + "<p>Se ha confirmado un auspicio en el Plan de Mercadeo con Nombre: "
                                        + "<strong>" + plan_mercadeo + " (" + id_tipo_plan + "-" + sec_tipo_plan + ")</strong> "
                                        + "y se gener&oacute; el siguiente detalle:</p></font>"
                                        + "<br/>";
                                mensaje += "<p><font face='Verdana' size='2'><strong>CONFIRMACIÓN AUSPICIOS</strong></font></p>";
                                mensaje += "<table border='1' style='font-family:Tahoma;font-size:11px'>";
                                mensaje += "<tr>";
                                mensaje += "<th>Tip. LAboratorio</th><th>Lab. Auspiciante</th><th>Valor Auspicio</th>";
                                mensaje += "</tr>";
                                mensaje += "<tr>";
                                mensaje += "<td>NO ESTRATEGICO</td><td>"+nombre_razon+"</td><td align='right'>"+monto+"</td>";
                                mensaje += "</tr>";
                                mensaje += "</table><br /><br /><br />";
                                mensaje += "Atentamente,<br />" +
                                            "<strong>"+empleado + "<br />" +
                                            cargo+"</strong>";

                                Correo.enviar(this._svr_mail, remitente, para_comercial, "", "", asunto, mensaje, true);

                                fun += "_('"+obj+"').className='aprobado';_('"+obj+"').title='Confirmado';_('"+obj+"').onclik='';_('btnAnular').style.visibility='hidden';_('elAusNoEst"+pos+"').style.visibility='hidden';";

                            }else{
                                objPM.setConfirmacion(id_plan_mercadeo, id_prov, 0);
                                objAus.ejecutar("update tbl_auspicio set tipo_confirmacion='a', estado='1' where num_auspicio='"+numAus+"'");
                                msg = this.objDB.getError();
                            }*/ 
                        }else{
                            objPM.setConfirmacion(id_plan_mercadeo, id_prov, numAus, 0);
                            msg = this.objDB.getError();
                        }   
                    }else{
                        msg = objPM.getError();
                    }
                /*}else{
                    msg = "El proveedor no se encuantra registrado en el CDP, proceda a crearlo para poder confirmar el gasto";
                }
            }else{
                msg = "El usuario no se encuentra creado en el CDP, proceda a crearlo antes de confirmar el gasto";
            }*/
            
        }catch(Exception e){
            msg = objPM.getError();
        }
        
        try {
            out.print("msg»"+msg+"^fun»"+fun);
        } finally {
            this.objDB.cerrar();
            objAus.cerrar();
            //objLab.cerrar();
            objPM.cerrar();
            out.close();
        }
    }

    /*public String getDestino()
    {
        String para = "";
        try{
            ResultSet rs = this.objDB.consulta("select * from KARDEXLABORATORIOS");
            if(rs.next()){
                para = rs.getString(1)!=null ? rs.getString(1) : "0";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return para;
    }*/
    
    public String getSecFactura()
    {
        String sec = "0";
        try{
            ResultSet rs = this.objDB.consulta("select max( convert(numeric, CODKARDEX) ) + 1 from KARDEXLABORATORIOS");
            if(rs.next()){
                sec = rs.getString(1)!=null ? rs.getString(1) : "0";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sec;
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
