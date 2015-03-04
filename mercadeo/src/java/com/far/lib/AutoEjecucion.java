/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.lib;

import com.far.mer.pla.clas.Actividad;
import com.far.mer.pla.clas.CalcularVentas;
import com.far.mer.pla.clas.Configuracion;
import com.far.mer.pla.clas.PlanMercadeo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;

/**
 *
 * @author Jorge
 */
@Stateless
@LocalBean
public class AutoEjecucion {
    private final String _IP = "127.0.0.1";
    private final int _PUERTO = 1433;
    private final String _DB = "PlanMercadeo";
    private final String _USUARIO = "sa";
    private final String _CLAVE = "sql";    //    sqlfarma      desarrollo = sql
    
    private final String GENE_IP = "127.0.0.1";    // 192.168.238.10      desarrollo = 127.0.0.1
    private final int GENE_PUERTO = 1433;
    private final String GENE_DB = "BDGENERAL";
    private final String GENE_USUARIO = "sa";
    private final String GENE_CLAVE = "sql";        
    
    private String ESTAD_IP = "127.0.0.1";  // 192.168.238.19      desarrollo = 127.0.0.1
    private int ESTAD_PUERTO = 1433;
    private String ESTAD_DB = "FARMAESTADISTICA_VENTAS_TMP";    // FARMAESTADISTICA_VENTAS      desarrollo = FARMAESTADISTICA_VENTAS_TMP
    private String ESTAD_USUARIO = "sa";
    private String ESTAD_CLAVE = "sql"; //    sqlfarma      desarrollo = sql
    
    private String EASY_IP = "127.0.0.1";   //  192.168.238.159      desarrollo = 127.0.0.1
    private int EASY_PUERTO = 1433;
    private String EASY_DB = "EasyGestion"; // EasyGestionEmpresarial      desarrollo = EasyGestion
    private String EASY_USUARIO = "sa";
    private String EASY_CLAVE = "sql";    //    sqlfarma      desarrollo = sql
    
    private String DEPR_IP = "127.0.0.1";   //  192.168.238.159      desarrollo = 127.0.0.1
    private int DEPR_PUERTO = 1433;
    private String DEPR_DB = "db_ctrlProveedores"; // db_ctrlProveedores      desarrollo = db_ctrlProveedores
    private String DEPR_USUARIO = "sa";
    private String DEPR_CLAVE = "sql";    //    sqlfarma      desarrollo = sql
    
    private String AUS_IP = "127.0.0.1";   //  192.168.238.159      desarrollo = 127.0.0.1
    private int AUS_PUERTO = 1433;
    private String AUS_DB = "auspicios"; // db_ctrlProveedores      desarrollo = db_ctrlProveedores
    private String AUS_USUARIO = "sa";
    private String AUS_CLAVE = "sql";    //    sqlfarma      desarrollo = sql
    
    
    private String SVR_MAIL = "mail.farmaenlace.com";
    
    
    
    /**
     * Cerrar auspicios automaticamente. Cuando en el lapso de 30 dias 
     * a partir de la primera confirmacion no se ha cerrado, 
     * el sistema lo realizara de manera automatica.
    */
    /*@Schedule(dayOfMonth="*", hour="00", minute="00", second="01")
    public void ejecutar()
    {
        int dia = Fecha.getDia();
        int mes = Fecha.getMes();
        int anio = Fecha.getAnio();
        
        this.confirmarAuspicios();
        this.notificacionesActividades();
        if(dia==5){
            this.evaluarVentas(anio, mes);
        }
    }*/
    
    private void confirmarAuspicios()
    {
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._IP, this._PUERTO, this._DB, this._USUARIO, this._CLAVE);
        BaseDatos objProveedores = new BaseDatos(this.DEPR_IP, this.DEPR_PUERTO, this.DEPR_DB, this.DEPR_USUARIO, this.DEPR_CLAVE);
        BaseDatos objAus = new BaseDatos(this.AUS_IP, this.AUS_PUERTO, this.AUS_DB, this.AUS_USUARIO, this.AUS_CLAVE);
        BaseDatos objGene = new BaseDatos(this.GENE_IP, this.GENE_PUERTO, this.GENE_DB, this.GENE_USUARIO, this.GENE_CLAVE);
        
        try{
            // planes de mercadeo con auspicios confirmados
            ResultSet rs = objPlanMercadeo.consulta("select distinct id_plan_mercadeo from tbl_plan_mercadeo_proveedor "
                    + "where CONVERT(date, DATEADD(day,30,fecha_confirmacion)) > CONVERT(date, GETDATE()) and confirmado=1");
            while(rs.next()){
                String id_plan_mercadeo = rs.getString("id_plan_mercadeo")!=null ? rs.getString("id_plan_mercadeo") : "";
                ResultSet rs1 = objPlanMercadeo.consulta("select PR.num_formulario, PR.numero_idproveedor, PR.nombre_comercial, PR.monto, PM.usuario_creacion, "
                    + "PM.sec_tipo_plan, PM.id_tipo_plan, PM.plan_mercadeo "
                    + "from tbl_plan_mercadeo as PM inner join tbl_plan_mercadeo_proveedor as PR on PM.id_plan_mercadeo=PR.id_plan_mercadeo "
                    + "where id_plan_mercadeo="+id_plan_mercadeo+" and confirmado=0");
                while(rs1.next()){
                    String num_formulario = rs1.getString("num_formulario")!=null ? rs1.getString("num_formulario") : "";
                    String numero_idproveedor = rs1.getString("numero_idproveedor")!=null ? rs1.getString("numero_idproveedor") : "";
                    String nombre_razon = rs1.getString("nombre_comercial")!=null ? rs1.getString("nombre_comercial") : "";
                    String monto = rs1.getString("monto")!=null ? rs1.getString("monto") : "0";
                    String usuario = rs1.getString("usuario_creacion")!=null ? rs1.getString("usuario_creacion") : "";
                    String sec_tipo_plan = rs1.getString("sec_tipo_plan")!=null ? rs1.getString("sec_tipo_plan") : "";
                    String id_tipo_plan = rs1.getString("id_tipo_plan")!=null ? rs1.getString("id_tipo_plan") : "";
                    String plan_mercadeo = rs1.getString("plan_mercadeo")!=null ? rs1.getString("plan_mercadeo") : "";
                    
                    ResultSet rsUs = objProveedores.consulta("select * from USUARIOS with (nolock) where LOGIN='"+usuario+"'");
                    if(objProveedores.getFilas(rsUs)>0){
                        String codigo_usuario = "";
                        if(rsUs.next()){
                            codigo_usuario = rsUs.getString("codigo_usuario")!=null ? rsUs.getString("codigo_usuario") : "0";
                        }
                        
                        if(objPlanMercadeo.ejecutar("update tbl_plan_mercadeo_proveedor set confirmado=1, fecha_confirmacion=getDate() where id_plan_mercadeo="+id_plan_mercadeo+" and numero_idproveedor='"+numero_idproveedor+"'")){
                            if(objAus.ejecutar("update tbl_auspicio set tipo_confirmacion='a', estado='9' where num_auspicio='"+num_formulario+"'")){
                                
                                String sec = this.getSecFactura(objProveedores);
                                List sql = new ArrayList();
                                sql.add("insert into FACTURAS(NUM_FACTURA, NUMERO_IDPROVEEDOR, FECHA, CODIGO_USUARIO, MOVIMIENTO, difTotal, mes, PENDIENTE, SALDO_KARDEX, FECHASIS, FECHASOLUCIONINC) "
                                        + "values('PM"+sec+"', '"+numero_idproveedor+"', getdate(), '"+codigo_usuario+"', -1, "+monto+", 'MES', 'S', "+monto+", getdate(), '1990/01/01');");

                                sql.add("insert into KARDEXLABORATORIOS(CODKARDEX, FECHA, ENTRADA, SALIDA, SALDOS, DESCRIPCION, NUM_DOCUMENTO, TIPO_DOCUMENTO, NUM_FACTURA, NUMERO_IDPROVEEDOR, CODIGO_USUARIO) "
                                        + "values('"+sec+"', getdate(), '"+monto+"', 0, "+monto+", 'PLAN MERCADEO "+Fecha.getAnio()+"_"+plan_mercadeo+"- PLAN MERCADEO -GENERADO POR AUSPICIOS DEL PLAN MERCADEO "+plan_mercadeo+" CON ABREVIATURA "+id_tipo_plan+" NUMERO: "+sec_tipo_plan+
                                        "', 'PM"+sec+"', 'PM', 'PM"+sec+"', '"+numero_idproveedor+"', '"+codigo_usuario+"');");
                                if(objProveedores.transacciones(sql)){
                                    Configuracion objConfiguracion = new Configuracion(this._IP, this._PUERTO, this._DB, this._USUARIO, this._CLAVE);
                                    String remitente = objConfiguracion.getValor("mail_remitente");
                                    String para_comercial = objConfiguracion.getValor("mail_comercial");
                                    objConfiguracion.cerrar();
                                    
                                    String empleado = "";
                                    String cargo = "";
                                    try{
                                //    FALTA EL CARGO DEL EMPELADO
                                        ResultSet rsEmpleado = objGene.consulta("select E.NOMBRES, E.APELLIDOS, C.DESCRIPCION "
                                                + "from (EMPLEADOS as E with (nolock) inner join CARGOS as C with (nolock) on E.CODIGO_CARGO=C.CODIGO_CARGO) "
                                                + "inner join CENTROS_COSTOS as CC with(nolock) on CC.CODIGO_CENTRO_COSTO=E.CODIGO_CENTRO_COSTO "
                                                + "where CEDULA='"+cedula+"'");
                                        if(rsEmpleado.next()){
                                            empleado = rsEmpleado.getString("NOMBRES")!=null ? rsEmpleado.getString("NOMBRES") + " " : "";
                                            empleado += rsEmpleado.getString("APELLIDOS")!=null ? rsEmpleado.getString("APELLIDOS") : "";
                                            cargo = rsEmpleado.getString("DESCRIPCION")!=null ? rsEmpleado.getString("DESCRIPCION") + " " : "";
                                            rsEmpleado.close();
                                        }
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                    }

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

                                    Correo.enviar(this.SVR_MAIL, remitente, para_comercial, "", "", asunto, mensaje, true);
                                }else{
                                    objPlanMercadeo.ejecutar("update tbl_plan_mercadeo_proveedor set confirmado=0, fecha_confirmacion=null where id_plan_mercadeo="+id_plan_mercadeo+" and numero_idproveedor='"+numero_idproveedor+"'");
                                    objAus.ejecutar("update tbl_auspicio set tipo_confirmacion='a', estado='1' where num_auspicio='"+num_formulario+"'");
                                }
                            }  
                        }
                    }
                }
                
                //objPlanMercadeo.ejecutar("update tbl_plan_mercadeo_proveedor set confirmado=1, fecha_confirmacion=getDate() "
                //        + "where confirmado=0 and id_plan_mercadeo="+id_plan_mercadeo);
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objGene.cerrar();
            objAus.cerrar();
            objProveedores.cerrar();
            objPlanMercadeo.cerrar();
        }
    }
    
    private String getSecFactura(BaseDatos objDB)
    {
        String sec = "0";
        try{
            ResultSet rs = objDB.consulta("select max( convert(numeric, CODKARDEX) ) + 1 from KARDEXLABORATORIOS");
            if(rs.next()){
                sec = rs.getString(1)!=null ? rs.getString(1) : "0";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sec;
    }
    
    private void notificacionesActividades()
    {
        Configuracion objConfiguracion = new Configuracion(this._IP, this._PUERTO, this._DB, this._USUARIO, this._CLAVE);
        String remitente = objConfiguracion.getValor("mail_remitente");
        
        Actividad objActividad = new Actividad(this._IP, this._PUERTO, this._DB, this._USUARIO, this._CLAVE);
        try{
            int dias_notificacion = 5;
            int dia = Fecha.getDiaSemana();
            if(dia==1){
                dias_notificacion = 7;
            }else{
                if(dia==7){
                    dias_notificacion = 6;
                }
            }
            
            ResultSet rs = objActividad.consulta("select *, CONVERT(VARCHAR, fecha_ini, 103) as txt_fecha_ini "
                    + "from tbl_activdad "
                    + "where CONVERT(date, DATEADD(day,"+dias_notificacion+",fecha_ini)) > CONVERT(date, GETDATE()) and eje_finalizada_fecha=null");
            
            while(rs.next()){
                
                String actividad = rs.getString("actividad")!=null ? rs.getString("actividad") : "";
                String txt_fecha_ini = rs.getString("txt_fecha_ini")!=null ? rs.getString("txt_fecha_ini") : "";
                String usuario_eje = rs.getString("usuario_eje")!=null ? rs.getString("usuario_eje") : "";
                String usuario_seg = rs.getString("usuario_seg")!=null ? rs.getString("usuario_seg") : "";
                
                StringBuilder mensaje = new StringBuilder();
                mensaje.append("Estimado(a) Sr(a).<br />");
                mensaje.append("Por medio de la presente le informo que la actividad: <strong>");
                mensaje.append(actividad);
                mensaje.append("</strong> est&aacute; pr&oacute;xima a dar inicio en la fecha ");
                mensaje.append(txt_fecha_ini);
                mensaje.append(".<br /><br /><br />Atentamente,<br /><br />");
                mensaje.append("<strong>SISTEMA DE PLANES DE MERCADEO</strong>");
                
                String empleado_eje[] = this.getEMailNombre(usuario_eje);
                String empleado_seg[] = this.getEMailNombre(usuario_seg);
                Correo.enviar("mail.farmaenlace.com", remitente, empleado_eje[0], empleado_seg[1], "", "NOTIFICACION DE INICIO DE ACTIVIDAD", mensaje.toString(), true);
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objConfiguracion.cerrar();
            objActividad.cerrar();
        }
    }
    
    private void evaluarVentas(int anio, int mes)
    {
        BaseDatos objEsta = new BaseDatos(this.ESTAD_IP, this.ESTAD_PUERTO, this.ESTAD_DB, this.ESTAD_USUARIO, this.ESTAD_CLAVE);
        BaseDatos objEasy = new BaseDatos(this.EASY_IP, this.EASY_PUERTO, this.EASY_DB, this.EASY_USUARIO, this.EASY_CLAVE);
        PlanMercadeo objPlanMercadeo = new PlanMercadeo(this._IP, this._PUERTO, this._DB, this._USUARIO, this._CLAVE);
        try{
            ResultSet rs = objPlanMercadeo.consulta("select * " +
                "from tbl_tipo_plan as T with (nolock) " +
                "where CONVERT(date, DATEADD(day, T.dias_prolonga, fecha_terminacion)) < convert(date, '"+anio+"-"+mes+"-01') "+ 
                "and ventas_reales is null and P.estado=9 ");
            while(rs.next()){
                String id_plan_mercadeo = rs.getString("id_plan_mercadeo")!=null ? rs.getString("id_plan_mercadeo") : "";
                String fecha_ini_averificar = rs.getString("fecha_ini_averificar")!=null ? rs.getString("fecha_ini_averificar") : "";
                String fecha_fin_averificar = rs.getString("fecha_fin_averificar")!=null ? rs.getString("fecha_fin_averificar") : "";
                
                String cod_niveles = "";
                String clases = "";
                String lineas = "";
                try{
                    ResultSet rsProductosFiltro = objPlanMercadeo.consulta("select cod_nivel,clase,linea from tbl_plan_mercadeo_producto_filtro with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+" order by id_plan_mercadeo_producto_filtro");
                    while(rsProductosFiltro.next()){
                        cod_niveles = rsProductosFiltro.getString("cod_nivel")!=null ? rsProductosFiltro.getString("cod_nivel") + "," : "";
                        clases = rsProductosFiltro.getString("clase")!=null ? rsProductosFiltro.getString("clase") + "," : "";
                        lineas = rsProductosFiltro.getString("linea")!=null ? rsProductosFiltro.getString("linea") + "," : "";
                    }
                    if(cod_niveles.compareTo("")!=0){
                        cod_niveles = cod_niveles.substring(0, cod_niveles.length()-1);
                        clases = clases.substring(0, clases.length()-1);
                        lineas = lineas.substring(0, lineas.length()-1);
                    }
                }catch(Exception e){}
                
                
                String articulos = "";
                try{
                    ResultSet rsProductos = objPlanMercadeo.consulta("select codigo from tbl_plan_mercadeo_producto with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+" order by descripcion");
                    while(rsProductos.next()){
                        articulos = rsProductos.getString("codigo")!=null ? rsProductos.getString("codigo") + "," : "";
                    }
                    if(articulos.compareTo("")!=0){
                        articulos = articulos.substring(0, articulos.length()-1);
                    }
                }catch(Exception e){}
                
                
                String oficinas = "";
                int numOficinas = 0;
                float sum_p_crecimiento = 0;
                String[][] planMercadeoFarmacia = null;
                try{
                    ResultSet rsFarmacias = objPlanMercadeo.consulta("select oficina, p_crecimiento, p_gasto from tbl_plan_mercadeo_farmacia with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+" order by nombre");
                    while(rsFarmacias.next()){
                        oficinas = rsFarmacias.getString("oficina")!=null ? rsFarmacias.getString("oficina") + "," : "";
                        sum_p_crecimiento += rsFarmacias.getString("p_crecimiento")!=null ? rsFarmacias.getFloat("p_crecimiento") : 0;
                        numOficinas ++;
                    }
                    if(oficinas.compareTo("")!=0){
                        oficinas = oficinas.substring(0, oficinas.length()-1);
                    }
                    planMercadeoFarmacia = Matriz.ResultSetAMatriz(rsFarmacias);
                }catch(Exception e){}
                
                double promedio_p_crecimiento = sum_p_crecimiento / numOficinas;
                
                
                
                float sumAuspicios = 0;
                try{
                    ResultSet rsAuspicios = objPlanMercadeo.consulta("select case when sum(monto)>0 then sum(monto) else 0 end from tbl_plan_mercadeo_laboratorio with (nolock) where id_plan_mercadeo="+id_plan_mercadeo
                            + "union "
                            + "select case when sum(monto)>0 then sum(monto) else 0 end from tbl_plan_mercadeo_proveedor with (nolock) where id_plan_mercadeo="+id_plan_mercadeo);
                    while(rsAuspicios.next()){
                        sumAuspicios += rsAuspicios.getString(1)!=null ? rsAuspicios.getFloat(1) : 0;
                    }
                }catch(Exception e){}
                
                
                float sumGastos = 0;
                try{
                    ResultSet rsGastos = objPlanMercadeo.consulta("select case when sum(total)>0 then sum(total) else 0 end " +
                        "from (tbl_estrategia as E with (nolock) inner join tbl_actividad as A on E.id_estrategia=A.id_estrategia) " +
                        "inner join tbl_actividad_compra as C with(nolock) on A.id_actividad=C.id_actividad " +
                        "where E.id_plan_mercadeo="+id_plan_mercadeo);
                    if(rsGastos.next()){
                        sumGastos = rsGastos.getString(1)!=null ? rsGastos.getFloat(1) : 0;
                    }
                }catch(Exception e){}
                
                
                float sum_ventas = 0;
                try{
                    CalcularVentas ventas = new CalcularVentas(objEsta, objEasy);
                    ResultSet rsVentas = ventas.calcular(fecha_ini_averificar, fecha_fin_averificar, cod_niveles, clases, lineas, articulos, oficinas);
                    while(rsVentas.next()){
                        String idOficina = rsVentas.getString("BODEGA")!=null ? rsVentas.getString("BODEGA") : "";
                        String subtotal = rsVentas.getString("subtotal")!=null ? rsVentas.getString("subtotal") : "0";
                        
                        int pos = Matriz.enMatriz(planMercadeoFarmacia, idOficina, 0);
                        float pGasto = pos!=-1 ? ( planMercadeoFarmacia[pos][2].compareTo("")!=0 ? Float.parseFloat(planMercadeoFarmacia[pos][2]) : 0 ) : 0;
                        double auspicio = Numero.redondear(pGasto * sumAuspicios / 100);
                        double gasto = Numero.redondear(pGasto * sumGastos / 100);
                        double pCumplimientoGasto = Numero.redondear(gasto * 100 / auspicio);
                        
                        sum_ventas += Float.parseFloat(subtotal);
                        objPlanMercadeo.setVentasFarmacia(id_plan_mercadeo, idOficina, subtotal, auspicio, gasto, pCumplimientoGasto);
                    }
                }catch(Exception e){}
                
                
                
                double ventas_100 = Numero.redondear( sum_ventas + (sum_ventas * promedio_p_crecimiento / 100) );
                double porcentaje_cumplimiento = Numero.redondear(sum_ventas * 100 / ventas_100);
                double porcentaje_cumplimiento_gasto = Numero.redondear(sumGastos * 100 / sumAuspicios);
                        
                objPlanMercadeo.setVentasReales(id_plan_mercadeo, sum_ventas, promedio_p_crecimiento, porcentaje_cumplimiento, sumGastos, porcentaje_cumplimiento_gasto);
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objEsta.cerrar();
            objEasy.cerrar();
            objPlanMercadeo.cerrar();
        }
    }
    
    private String[] getEMailNombre(String NombreCorto)
    {
        String usuario[] = new String[]{"",""};
        BaseDatos objDB = new BaseDatos(this.GENE_IP, this.GENE_PUERTO, this.GENE_DB, this.GENE_USUARIO, this.GENE_CLAVE);
        try{
            ResultSet rs = objDB.consulta("select Email, Nombre from usuarios with (nolock) where lower(NombreCorto) = '"+NombreCorto+"'");
            if(rs.next()){
                usuario[0] = rs.getString("Email")!=null?rs.getString("Email"):"";
                usuario[1] = rs.getString("Nombre")!=null?rs.getString("Nombre"):"";
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            objDB.cerrar();
        }
        return usuario;
    }
    
}
