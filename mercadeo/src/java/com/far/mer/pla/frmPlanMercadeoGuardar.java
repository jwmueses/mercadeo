/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla;

import com.far.lib.BaseDatos;
import com.far.lib.Correo;
import com.far.mer.pla.clas.Configuracion;
import com.far.mer.pla.clas.PlanMercadeo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
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
public class frmPlanMercadeoGuardar extends HttpServlet {

    private String _ip = null;
    private int _puerto = 1433;
    private String _db = null;
    private String _usuario = null;
    private String _clave = null;
    
    private String _svr_mail = null;
    
    private String gene_ip = null;
    private int gene_puerto = 1433;
    private String gene_db = null;
    private String gene_usuario = null;
    private String gene_clave = null;
    
    BaseDatos objGene = null; 
    
    private String AUS_ip = null;
    private int AUS_puerto = 1433;
    private String AUS_db = null;
    private String AUS_usuario = null;
    private String AUS_clave = null;

    public void init(ServletConfig config) throws ServletException
    {
        this._ip = config.getServletContext().getInitParameter("_IP");
        this._puerto = Integer.parseInt(config.getServletContext().getInitParameter("_PUERTO"));
        this._db = config.getServletContext().getInitParameter("_DB");
        this._usuario = config.getServletContext().getInitParameter("_USUARIO");
        this._clave = config.getServletContext().getInitParameter("_CLAVE");
        
        this._svr_mail = config.getServletContext().getInitParameter("_SVR_MAIL");
        
        this.gene_ip = config.getServletContext().getInitParameter("GENE_IP");
        this.gene_puerto = Integer.parseInt(config.getServletContext().getInitParameter("GENE_PUERTO"));
        this.gene_db = config.getServletContext().getInitParameter("GENE_DB");
        this.gene_usuario = config.getServletContext().getInitParameter("GENE_USUARIO");
        this.gene_clave = config.getServletContext().getInitParameter("GENE_CLAVE");
        
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
        HttpSession sesion = request.getSession(true);
        String usuario = (String)sesion.getAttribute("usuario");
        String empleado = (String)sesion.getAttribute("empleado");
        String cargo = (String)sesion.getAttribute("cargo");
        String centro_costos = (String)sesion.getAttribute("centro_costos");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "Mon, 01 Jan 2001 00:00:01 GMT");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        String res = "msg»Ha ocurrido un error inesperado. Por favor inténtelo más tarde";
        
        Configuracion objConfiguracion = new Configuracion(this._ip, this._puerto, this._db, this._usuario, this._clave);
        String admin_tiempos_conf = objConfiguracion.getValor("admin_tiempos_conf");
        
        BaseDatos objAuspicio = new BaseDatos(this.AUS_ip, this.AUS_puerto, this.AUS_db, this.AUS_usuario, this.AUS_clave);
        
        this.objGene = new BaseDatos(this.gene_ip, this.gene_puerto, this.gene_db, this.gene_usuario, this.gene_clave);
        
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._ip, this._puerto, this._db, this._usuario, this._clave);
        //String para_creador = this.getEMail(usuario);
        try {
            //String WHERE = request.getParameter("WHERE");
            //String p = request.getParameter("p") != null ? request.getParameter("p") : "0";

            String id_plan_mercadeo = request.getParameter("id_plan_mercadeo");
            String id_tipo_plan = request.getParameter("id_tipo_plan");
            int notOpera = Integer.parseInt( request.getParameter("notOpera") );
            String plan_mercadeo = request.getParameter("plan_mercadeo");
            String tipo_alcance = request.getParameter("tipo_alcance");
            String evalua_ventas = request.getParameter("evalua_ventas");
            String tipo_alcance_de = request.getParameter("tipo_alcance_de");
            String fecha_ini = request.getParameter("fecha_ini");
            String fecha_fin = request.getParameter("fecha_fin");
            String promedio_ventas = request.getParameter("promedio_ventas");
            String fecha_ini_averificar = request.getParameter("fecha_ini_averificar");
            String fecha_fin_averificar = request.getParameter("fecha_fin_averificar");
            String tipo_dist_gasto = request.getParameter("tipo_dist_gasto");
            String proyeccion_ventas = request.getParameter("proyeccion_ventas");
            String total_auspicio = request.getParameter("total_auspicio");
            //String valor_venta = request.getParameter("valor_venta");
            //valor_venta = valor_venta.compareTo("")!=0 ? valor_venta : "0";
            
            String mecanica_tipo = request.getParameter("mecanica_tipo");
            String mecanica = request.getParameter("mecanica");
            String aplica_prom_p_v = request.getParameter("aplica_prom_p_v");
            String ope_fecha_ini = request.getParameter("ope_fecha_ini");
            String ope_fecha_fin = request.getParameter("ope_fecha_fin");
            
            String premio = request.getParameter("premio");
            //String oficinai = request.getParameter("oficinai");
            //String nombrei = request.getParameter("nombrei");
            String aprobada_operaciones = request.getParameter("aprobada_operaciones");
            String abierto = request.getParameter("abierto");
            
            int limFar = Integer.parseInt( request.getParameter("limFar") );
            int limProdFil = Integer.parseInt( request.getParameter("limProdFil") );
            int limProd = Integer.parseInt( request.getParameter("limProd") );
            int limVen = Integer.parseInt( request.getParameter("limVen") );
            int limCli = Integer.parseInt( request.getParameter("limCli") );
            int limAdj = Integer.parseInt( request.getParameter("limAdj") );
            int limProv = Integer.parseInt( request.getParameter("limProv") );
            int limAdjAct = Integer.parseInt( request.getParameter("limAdjAct") );
            
            if(tipo_alcance.compareTo("d")==0){
                fecha_ini = request.getParameter("dfecha_ini");
                fecha_fin = request.getParameter("dfecha_fin");
                promedio_ventas = request.getParameter("dpromedio_ventas");
            }
            
            mecanica_tipo = mecanica_tipo!=null ? mecanica_tipo : "t";
            aplica_prom_p_v = aplica_prom_p_v!=null ? aplica_prom_p_v : "";
            
            
            // verifico si permite auspicios con confirmaciones manuales---------------------------------------------------
            
            String auspicio_manual = "a";
            try{
                ResultSet rs = objPlanMercadeo.consulta("select case when auspicio_manual=1 then 'm' else 'a' end as auspicio_manual from tbl_tipo_plan where id_tipo_plan='"+id_tipo_plan+"'");
                if(rs.next()){
                    auspicio_manual = rs.getString("auspicio_manual")!=null ? rs.getString("auspicio_manual") : "a";
                    rs.close();
                }
            }catch(Exception e){
               e.printStackTrace();
            }
            
            
            
            
            // farmacias    --------------------------------------------------------------------------------------------
            String oficina;
            String oficinas = "";
            String nombres = "";
            String centros_costos = "";
            String p_ventas = "";
            String ps_crecimiento = "";
            String proy_ventas = "";
            String ps_gasto = "";
            for(int i=0; i<limFar; i++){
                if(request.getParameter("oficina"+i)!=null){
                    oficina = request.getParameter("oficina"+i);
                    oficinas += oficina + ",";
                    nombres += request.getParameter("nombre"+i) + ",";
                    centros_costos += this.getCentroCostos(oficina) + ",";
                    proy_ventas += request.getParameter("proy_ventas"+i) + ",";
                    if(tipo_alcance.compareTo("i")==0){
                        p_ventas += "null,";
                        ps_crecimiento += "null,";
                        ps_gasto += "null,";
                    }else{
                        p_ventas += request.getParameter("p_ventas"+i) + ",";
                        ps_crecimiento += request.getParameter("p_crecimiento"+i) + ",";
                        ps_gasto += request.getParameter("p_gasto"+i) + ",";
                    }
                }
            }
            if(oficinas.compareTo("")!=0){
                oficinas = oficinas.substring(0, oficinas.length()-1);
                nombres = nombres.substring(0, nombres.length()-1);
                centros_costos = centros_costos.substring(0, centros_costos.length()-1);
                p_ventas = p_ventas.substring(0, p_ventas.length()-1);
                ps_crecimiento = ps_crecimiento.substring(0, ps_crecimiento.length()-1);
                proy_ventas = proy_ventas.substring(0, proy_ventas.length()-1);
                ps_gasto = ps_gasto.substring(0, ps_gasto.length()-1);
            }
            
            // filtro de productos    ----------------------------------------------------------------------------------------------
            String cod_niveles = "";
            String desc_niveles = "";
            String clases = "";
            String desc_clases = "";
            String lineas = "";
            String desc_lineas = "";
            for(int i=0; i<limProdFil; i++){
                if(request.getParameter("cod_nivel"+i)!=null){
                    cod_niveles += request.getParameter("cod_nivel"+i) + ",";
                    desc_niveles += request.getParameter("desc_nivel"+i) + ",";
                    clases += request.getParameter("clase"+i) + ",";
                    desc_clases += request.getParameter("desc_clase"+i) + ",";
                    lineas += request.getParameter("linea"+i) + ",";
                    desc_lineas += request.getParameter("desc_linea"+i) + ",";
                }
            }
            if(cod_niveles.compareTo("")!=0){
                cod_niveles = cod_niveles.substring(0, cod_niveles.length()-1);
                desc_niveles = desc_niveles.substring(0, desc_niveles.length()-1);
                clases = clases.substring(0, clases.length()-1);
                desc_clases = desc_clases.substring(0, desc_clases.length()-1);
                lineas = lineas.substring(0, lineas.length()-1);
                desc_lineas = desc_lineas.substring(0, desc_lineas.length()-1);
            }
            
            // productos    ----------------------------------------------------------------------------------------------
            String codigos = "";
            String descripciones = "";
            for(int i=0; i<limProd; i++){
                if(request.getParameter("codigo"+i)!=null){
                    codigos += request.getParameter("codigo"+i) + ",";
                    descripciones += request.getParameter("descripcion"+i) + ",";
                }
            }
            if(codigos.compareTo("")!=0){
                codigos = codigos.substring(0, codigos.length()-1);
                descripciones = descripciones.substring(0, descripciones.length()-1);
            }
            
            // vendedores   ----------------------------------------------------------------------------------------------
            String codigo_vendedores = "";
            String nombre_vendedores = "";
            for(int i=0; i<limVen; i++){
                if(request.getParameter("codigo_vendedor"+i)!=null){
                    codigo_vendedores += request.getParameter("codigo_vendedor"+i) + ",";
                    nombre_vendedores += request.getParameter("nombre_vendedor"+i) + ",";
                }
            }
            if(codigo_vendedores.compareTo("")!=0){
                codigo_vendedores = codigo_vendedores.substring(0, codigo_vendedores.length()-1);
                nombre_vendedores = nombre_vendedores.substring(0, nombre_vendedores.length()-1);
            }
            
            // clientes ----------------------------------------------------------------------------------------------------
            String numero_idclientes = "";
            String nombres_comerciales = "";
            for(int i=0; i<limCli; i++){
                if(request.getParameter("numero_idcliente"+i)!=null){
                    numero_idclientes += request.getParameter("numero_idcliente"+i) + ",";
                    nombres_comerciales += request.getParameter("nombre_comercial"+i) + ",";
                }
            }
            if(numero_idclientes.compareTo("")!=0){
                numero_idclientes = numero_idclientes.substring(0, numero_idclientes.length()-1);
                nombres_comerciales = nombres_comerciales.substring(0, nombres_comerciales.length()-1);
            }
            
            // adjuntos -----------------------------------------------------------------------------------------------------
            String adjuntos = "";
            for(int i=0; i<limAdj; i++){
                if(request.getParameter("adjunto"+i)!=null){
                    adjuntos += request.getParameter("adjunto"+i) + "|";
                }
            }
            if(adjuntos.compareTo("")!=0){
                adjuntos = adjuntos.substring(0, adjuntos.length()-1);
            }
            
            //  auspicios laboratorios  --------------------------------------------------------------------------------------
            int j=0;
            String idsLab = "";
            String montos = "";
            String monto = "0";
            while(request.getParameter("idLab"+j)!=null){
                monto = request.getParameter("monto"+j)!=null ? request.getParameter("monto"+j) : "0";
                if(Float.parseFloat(monto) > 0){
                    idsLab += request.getParameter("idLab"+j) + ",";
                    montos += monto + ",";
                }
                j++;
            }
            if(idsLab.compareTo("")!=0){
                idsLab = idsLab.substring(0, idsLab.length()-1);
                montos = montos.substring(0, montos.length()-1);
            }

            //  auspicios no estrategicos (proveedores) --------------------------------------------------------------------
            String rucs = "";
            String fechRegs = "";
            String montops = "";
            String nomsCom = "";
            String numForms = "";
            for(int i=0; i<limProv; i++){
                if(request.getParameter("nomCom"+i)!=null){
                    rucs += request.getParameter("ruc"+i) + ",";
                    nomsCom += request.getParameter("nomCom"+i) + ",";
                    fechRegs += request.getParameter("fechReg"+i) + ",";
                    montops += request.getParameter("montop"+i) + ",";
                    numForms += request.getParameter("numForm"+i) + ",";
                }
            }
            if(rucs.compareTo("")!=0){
                rucs = rucs.substring(0, rucs.length()-1);
                nomsCom = nomsCom.substring(0, nomsCom.length()-1);
                fechRegs = fechRegs.substring(0, fechRegs.length()-1);
                montops = montops.substring(0, montops.length()-1);
                numForms = numForms.substring(0, numForms.length()-1);
            }

            // formulario y archivos adjuntos    -----------------------------------------------------------------------------------------------------------------
            String archivos = "";
            String descripciones_mecanica = "";
            
            if(mecanica_tipo.compareTo("t")==0){
                aplica_prom_p_v = "";
                ope_fecha_ini = ope_fecha_fin = "NULL";
            }else{
                mecanica = "";
                ope_fecha_ini = "'" + ope_fecha_ini + "'";
                ope_fecha_fin = "'" + ope_fecha_fin + "'";
                
                for(int i=0; i<=limAdjAct; i++){
                    if(request.getParameter("archivo"+i)!=null){
                        archivos += request.getParameter("archivo"+i) + "&;";
                        descripciones_mecanica += request.getParameter("descripcion_arch"+i) + "&;";
                    }
                }
                if(archivos.compareTo("")!=0){
                    archivos = archivos.substring(0, archivos.length()-2);
                    descripciones_mecanica = descripciones_mecanica.substring(0, descripciones_mecanica.length()-2);
                }
            }
            
            //  usuarios con limite de tiempo para confirmacion  --------------------------------------------------------------------------------------
            /*j=0;
            String id_tipo_plan_usuarios = "";
            String nums_dias = "";
            while(request.getParameter("id_tipo_plan_usuario"+j)!=null){
                id_tipo_plan_usuarios += request.getParameter("id_tipo_plan_usuario"+j) + ",";
                nums_dias += (request.getParameter("num_dias"+j).compareTo("")!=0 ? request.getParameter("num_dias"+j) : "0" ) + ",";
                j++;
            }
            if(id_tipo_plan_usuarios.compareTo("")!=0){
                id_tipo_plan_usuarios = id_tipo_plan_usuarios.substring(0, id_tipo_plan_usuarios.length()-1);
                nums_dias = nums_dias.substring(0, nums_dias.length()-1);
            }*/

            
            String remitente = "";
            String para = "";
            String asunto = "";
            String mensaje = "";
            if(notOpera==1 && mecanica_tipo.compareTo("o")==0){
                remitente = objConfiguracion.getValor("mail_remitente");
                para = objConfiguracion.getValor("mail_operaciones");

                asunto = "NOTIFICACION DE PLAN DE MERCADEO";
                mensaje = "Estimado(a) Sr(a).<br />" +
                "<strong>GERENTE DE OPERACIONES</strong><br /><br />" +
                "Por medio de la presente le informo que el plan de mercadeo con nombre: <strong>"+plan_mercadeo+"</strong>; est&aacute; listo para revisi&oacute;n de Mec&aacute;nica de Operaciones.<br /><br /><br />" +
                "Atentamente,<br />" +
                "<strong>"+empleado + "<br />" +
                cargo+"</strong>";
            }
            
            
            
            
            if(id_plan_mercadeo.compareTo("-1")==0){
                
                res = "msg»Nombre del plan ya existen.";
                if(!objPlanMercadeo.planDuplicado(id_plan_mercadeo, plan_mercadeo)){
                    String sec_tipo_plan = String.valueOf( objPlanMercadeo.getSecuencial() );
                    String pk = objPlanMercadeo.ingresar(usuario, empleado, centro_costos, id_tipo_plan, sec_tipo_plan, plan_mercadeo, tipo_alcance, evalua_ventas, tipo_alcance_de, 
                    fecha_ini, fecha_fin, promedio_ventas, fecha_ini_averificar, fecha_fin_averificar, tipo_dist_gasto, proyeccion_ventas, 
                    ps_gasto, premio, adjuntos, total_auspicio, mecanica_tipo, mecanica, aplica_prom_p_v, ope_fecha_ini, ope_fecha_fin, abierto, oficinas, 
                    nombres, centros_costos, p_ventas, ps_crecimiento, proy_ventas, cod_niveles, desc_niveles, clases, desc_clases, lineas, desc_lineas, 
                    codigos, descripciones, codigo_vendedores, nombre_vendedores, numero_idclientes, nombres_comerciales, idsLab, montos, 
                    rucs, fechRegs, montops, nomsCom, numForms, admin_tiempos_conf, archivos, descripciones_mecanica, objAuspicio, auspicio_manual);
                    if(pk.compareTo("-1")!=0){
                        
                        
                        
                        String msg = "Información ingresada satisfactoriamente.";
                        if(notOpera==1){
                            if(!Correo.enviar(this._svr_mail, remitente, para, "", "", asunto, mensaje, true)){
                                msg = Correo.getError();
                            }
                        }
                        res = "fun»_R('bloq_vta1');_R('vta1');mer_getPlanesMercadeo(0);mer_mercadeoEditar("+pk+");^id»-1^msg»"+msg;
                    }else{
                        res = "msg»" + objPlanMercadeo.getError();
                    }
                }
                
            }else{
                
                if(objPlanMercadeo.actualizar(id_plan_mercadeo, usuario, id_tipo_plan, plan_mercadeo, tipo_alcance, evalua_ventas, tipo_alcance_de, 
                    fecha_ini, fecha_fin, promedio_ventas, tipo_dist_gasto, fecha_ini_averificar, fecha_fin_averificar, proyeccion_ventas, 
                    ps_gasto, premio, adjuntos, total_auspicio, mecanica_tipo, mecanica, aplica_prom_p_v, ope_fecha_ini, ope_fecha_fin, abierto, oficinas, 
                    nombres, centros_costos, p_ventas, ps_crecimiento, proy_ventas, cod_niveles, desc_niveles, clases, desc_clases, lineas, desc_lineas, 
                    codigos, descripciones, codigo_vendedores, nombre_vendedores, numero_idclientes, nombres_comerciales, idsLab, montos, 
                    rucs, fechRegs, montops, nomsCom, numForms, admin_tiempos_conf, archivos, descripciones_mecanica, aprobada_operaciones, objAuspicio, auspicio_manual)){
                    String msg = "Información guardada satisfactoriamente.";
                    /*if(notOpera==1){
                        if(!Correo.enviar(this._svr_mail, remitente, para, "", "", asunto, mensaje, true)){
                            msg = Correo.getError();
                        }
                    }*/
                    int plan_completo = 0;
                    try{
                        ResultSet rsPlan = objPlanMercadeo.getPlanMercadeo(id_plan_mercadeo);
                        if(rsPlan.next()){
                            plan_completo = (rsPlan.getString("plan_completo")!=null) ? rsPlan.getInt("plan_completo") : 0;
                            
                            rsPlan.close();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    String verBtn = (id_plan_mercadeo.compareTo("-1")!=0 && objPlanMercadeo.existenActividades(id_plan_mercadeo)) ? "visible" : "hidden";
                    if(mecanica_tipo.compareTo("o")==0){
                        verBtn = (aprobada_operaciones.compareTo("1")==0 && plan_completo==0) ? "visible" : "hidden";
                    }
                
                    res = "fun»_('btnAut').style.visivility='"+verBtn+"';^msg»" + msg;
                }else{
                    res = "msg»" + objPlanMercadeo.getError();
                }
                
            }
        } catch(Exception e) {
            res = "msg»" + e.getMessage();
        } finally {
            this.objGene.cerrar();
            objAuspicio.cerrar();
            objPlanMercadeo.cerrar();
            objConfiguracion.cerrar();
        }

        try{
            out.print(res);
        }finally {
            out.close();
        }
    }
    
    public String getCentroCostos(String CODIGO_OFICINA)
    {
        try{
            ResultSet rs = this.objGene.consulta("select DESCRIPCION from CENTROS_COSTOS where CODIGO_OFICINA='"+CODIGO_OFICINA+"'");
            if(rs.next()){
                return rs.getString("DESCRIPCION")!=null?rs.getString("DESCRIPCION"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
    
    public String getEMail(String usuario)
    {
        try{
            ResultSet rs = this.objGene.consulta("select Email from usuarios with (nolock) where lower(NombreCorto) = '"+usuario+"'");
            if(rs.next()){
                return rs.getString("Email")!=null?rs.getString("Email"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
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
