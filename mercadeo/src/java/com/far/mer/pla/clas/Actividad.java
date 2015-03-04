/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.mer.pla.clas;

import com.far.lib.BaseDatos;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class Actividad extends BaseDatos {
    private float total_gasto = 0;
    
    public Actividad(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }
    public boolean hayActividad(String id_actividad, String id_estrategia, String actividad)
    {
        try{
            ResultSet rs = this.consulta("SELECT * FROM tbl_actividad with (nolock) "
                    + "where actividad='"+actividad+"' and id_estrategia="+id_estrategia+" and id_actividad<>"+id_actividad+";");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getActividad(String id_actividad)
    {
        return this.consulta("select *, CONVERT(VARCHAR, fecha_ini, 103) as sql_fecha_ini, CONVERT(VARCHAR, fecha_fin, 103) as sql_fecha_fin "
                + "from tbl_actividad with (nolock) where id_actividad="+id_actividad);
    }
    public ResultSet getActividadPlanMercadeo(String id_actividad)
    {
        return this.consulta("select P.plan_mercadeo, P.usuario_creacion, E.estrategia, A.* "
                + "from (tbl_plan_mercadeo as P with (nolock) inner join tbl_estrategia as E with (nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) "
                + "inner join tbl_actividad  as A with (nolock) on E.id_estrategia=A.id_estrategia where A.id_actividad="+id_actividad);
    }
    public String getIdTipoPlan(String id_actividad)
    {
        String id_tipo_plan = "";
        try{
            ResultSet rs = this.consulta("select P.id_tipo_plan "
                + "from (tbl_plan_mercadeo as P with (nolock) inner join tbl_estrategia as E with (nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) "
                + "inner join tbl_actividad  as A with (nolock) on E.id_estrategia=A.id_estrategia where A.id_actividad="+id_actividad);
            if(rs.next()){
                id_tipo_plan = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
                rs.close();
            }
        }catch(Exception e){
            this.setError(e.getMessage());
        }
        return id_tipo_plan;
    }
    public ResultSet getPlanMercadeo(String id_actividad)
    {
        return this.consulta("select P.* "
            + "from (tbl_plan_mercadeo as P with (nolock) inner join tbl_estrategia as E with (nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) "
            + "inner join tbl_actividad  as A with (nolock) on E.id_estrategia=A.id_estrategia where A.id_actividad="+id_actividad);
    }
    public ResultSet getActividades(String id_estrategia)
    {
        return this.consulta("select * from tbl_actividad with (nolock) where id_estrategia="+id_estrategia+" order by id_actividad");
    }
    public ResultSet getActividadesCalendario(String id_plan_mercadeo, String usuario, String fecha_inicio, String txt, String estados)
    {
        String where = "";
        if(id_plan_mercadeo.compareTo("-1")!=0){
            where = " and PM.id_plan_mercadeo=" + id_plan_mercadeo;
        }else{
            if(estados.compareTo("0")!=0){
                where += " and PM.estado in ("+estados+")";
            }
            if(usuario.compareTo("")!=0 && usuario.compareTo("0")!=0){
                where += " and PM.usuario_creacion='"+usuario+"'";
            }
            if(txt.compareTo("")!=0){
                where += " and lower(PM.plan_mercadeo) like '%"+txt+"%'";
            }
        }
        return this.consulta("select A.id_actividad, a.actividad, CONVERT(VARCHAR, A.fecha_ini, 103) as sql_fecha_ini, PM.estado "
                + "from (tbl_plan_mercadeo as PM with (nolock) inner join tbl_estrategia as E with (nolock) on PM.id_plan_mercadeo=E.id_plan_mercadeo) "
                + "inner join tbl_actividad as A with (nolock) on E.id_estrategia=A.id_estrategia "
                + "where A.fecha_ini between '"+fecha_inicio+" 00:00:00.000' and DATEADD(day, 7, '"+fecha_inicio+"') "
                + where+" order by A.fecha_ini, PM.estado;");
    }
    /*public ResultSet getActividadesAdjuntos(String id_actividad)
    {
        return this.consulta("select * from tbl_actividad_adjunto with (nolock) where id_actividad="+id_actividad+" order by id_actividad_adjunto");
    }*/
    public ResultSet getFacturas(String id_actividades)
    {
        return this.consulta("select *, CONVERT(VARCHAR, fecha, 103) as fecha1 from tbl_actividad_compra with (nolock) where id_actividad in ("+id_actividades+") order by id_actividad, id_actividad_compra");
    }
    public ResultSet getActividadesCronograma(String id_actividad)
    {
        return this.consulta("select CONVERT(VARCHAR, fecha, 103) as fecha_sql, descripcion from tbl_actividad_cronograma with (nolock) where id_actividad="+id_actividad+" order by fecha");
    }
    public String ingresar(String id_estrategia, String tipo_pago, String actividad, String fecha_ini, String fecha_fin, String usuario_seg, String responsable_seg, String usuario_eje,
            String responsable_eje, String pre_tipo, String pre_proveedor, String pre_cantidad, String pre_p_u, String pre_total, String monto, String pre_id_tipo_plan_cuenta, 
            String pre_crono_adqui, String crono_descripciones)
    {
        String id_actividad = this.insertar("insert into tbl_actividad(id_estrategia, tipo_pago, actividad, fecha_ini, fecha_fin, usuario_seg, responsable_seg, "
                + "usuario_eje, responsable_eje, pre_tipo, pre_proveedor, pre_cantidad, pre_p_u, pre_total, monto, pre_id_tipo_plan_cuenta) "
                + "values("+id_estrategia+", '"+tipo_pago+"', '"+actividad+"', '"+fecha_ini+"', '"+fecha_fin+"', '"+usuario_seg+"', '"+responsable_seg+
                "', '"+usuario_eje+"', '"+responsable_eje+"', '"+pre_tipo+"', '"+pre_proveedor+"', "+pre_cantidad+", "+pre_p_u+", "+pre_total+
                ", "+monto+", "+pre_id_tipo_plan_cuenta+");");
        
        if(id_actividad.compareTo("-1") != 0){
            List sql = new ArrayList();
            //  cronograma de activiades   ----------------------------------------------------------------------------------------------
            if(pre_crono_adqui.compareTo("")!=0){
                String fecha[] = pre_crono_adqui.split("&;");
                String descripcion[] = crono_descripciones.split("&;");
                for(int i=0; i<fecha.length; i++){
                    sql.add("insert into tbl_actividad_cronograma(id_actividad, fecha, descripcion) "
                        + "values( "+id_actividad+", '"+fecha[i]+"', '"+descripcion[i]+"');");
                }
            }
            
            //  formulario y archivos adjuntos   ----------------------------------------------------------------------------------------
            /*if(archivos.compareTo("")!=0){
                String archivo[] = archivos.split("&;");
                String descripcion[] = descripciones.split("&;");
                for(int i=0; i<archivo.length; i++){
                    sql.add("insert into tbl_actividad_adjunto(id_actividad, archivo, descripcion) "
                        + "values( "+id_actividad+", '"+archivo[i]+"', '"+descripcion[i]+"');");
                }
            }*/
        
            if( this.transacciones(sql) ){
                return id_actividad;
            }else{
                this.ejecutar("delete from tbl_actividad where id_actividad="+id_actividad);
            }
            
        }
        return "-1";
    } 
    public boolean actualizar(String id_actividad, String id_estrategia, String tipo_pago, String actividad, String fecha_ini, String fecha_fin, 
            String usuario_seg, String responsable_seg, String usuario_eje, String responsable_eje,  String pre_tipo, String pre_proveedor, String pre_cantidad, 
            String pre_p_u, String pre_total, String monto, String pre_id_tipo_plan_cuenta, String pre_crono_adqui, String crono_descripciones)
    {
        List sql = new ArrayList();
        sql.add("update tbl_actividad set id_estrategia="+id_estrategia+", tipo_pago='"+tipo_pago+"', actividad='"+actividad+"', fecha_ini='"+fecha_ini+
                "', fecha_fin='"+fecha_fin+"', usuario_seg='"+usuario_seg+"', responsable_seg='"+responsable_seg+
                "', usuario_eje='"+usuario_eje+"', responsable_eje='"+responsable_eje+"', pre_tipo='"+pre_tipo+"', pre_proveedor='"+pre_proveedor+"', pre_cantidad="+pre_cantidad+
                ", pre_p_u="+pre_p_u+", pre_total="+pre_total+", monto="+monto+", pre_id_tipo_plan_cuenta="+pre_id_tipo_plan_cuenta+
                " where id_actividad="+id_actividad+";");
        
        //  cronograma de activiades   ----------------------------------------------------------------------------------------------
        sql.add("delete from tbl_actividad_cronograma where id_actividad="+id_actividad+";");
        if(pre_crono_adqui.compareTo("")!=0){
            String fecha[] = pre_crono_adqui.split("&;");
            String descripcion[] = crono_descripciones.split("&;");
            for(int i=0; i<fecha.length; i++){
                sql.add("insert into tbl_actividad_cronograma(id_actividad, fecha, descripcion) "
                    + "values( "+id_actividad+", '"+fecha[i]+"', '"+descripcion[i]+"');");
            }
        }
        
        //  formulario y archivos adjuntos   ----------------------------------------------------------------------------------------------
        /*sql.add("delete from tbl_actividad_adjunto where id_actividad="+id_actividad+";");
        if(archivos.compareTo("")!=0){
            String archivo[] = archivos.split("&;");
            String descripcion[] = descripciones.split("&;");
            for(int i=0; i<archivo.length; i++){
                sql.add("insert into tbl_actividad_adjunto(id_actividad, archivo, descripcion) "
                    + "values( "+id_actividad+", '"+archivo[i]+"', '"+descripcion[i]+"');");
            }
        }*/

        return this.transacciones(sql);
            
    } 

    /*public boolean aprobar(String id_actividad, String aprobada, String motivo)
    {
        return this.ejecutar("update tbl_actividad set registro_operaciones=1, aprobada_operaciones="+aprobada+", motivo_rechazo='"+motivo+"' where id_actividad="+id_actividad+";");
    }*/
    
    public boolean setMostrada(String id_actividad, String estado)
    {
        String campo = "";
        switch(Integer.parseInt(estado)){
            case 3:
                campo = "visto_operaciones";
            break;
            case 4:
                campo = "visto_marketing";
            break;
            case 5:
                campo = "visto_ventas";
            break;
            case 6:
                campo = "visto_comercial";
            break;
        }
        return this.ejecutar("update tbl_actividad set "+campo+"=1 where id_actividad="+id_actividad+";");
    }
    
    public boolean setEjecucion(String id_actividad, int eje_finalizada, String fecha, String comentario)
    {
        return this.ejecutar("update tbl_actividad set eje_finalizada="+eje_finalizada+", eje_finalizada_fecha="+fecha
                + ", eje_comentario='"+comentario+"' where id_actividad="+id_actividad+";");
    }
    
    public boolean setIdPlanCuenta(String id_actividad, String id_plan_cuenta)
    {
        return this.ejecutar("update tbl_actividad set pre_id_tipo_plan_cuenta="+id_plan_cuenta+" where id_actividad="+id_actividad+";");
    }
    
    public boolean eliminar(String id_actividad)
    {
        List sql = new ArrayList();
        sql.add("delete from tbl_actividad_cronograma where id_actividad="+id_actividad+";");
        //sql.add("delete from tbl_actividad_adjunto where id_actividad="+id_actividad+";");
        sql.add("delete from tbl_actividad where id_actividad="+id_actividad);
        return this.transacciones(sql);
    }
 
    
    /*  facturas de actividades     */
    
    public String tblFacturas(ResultSet rsAct, int alto)
    {
        String html = "";
        try{
            this.total_gasto = 0;
            int i=0;
            html += "<div id=\"axTblComp\" style=\"overflow:auto;width:982px;min-height:25px; max-height:"+(alto-220)+"px;\">"
                    + "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tbody id=\"tblGastos\">";
            while(rsAct.next()){
                String id_actividad_compra = rsAct.getString("id_actividad_compra")!=null ? rsAct.getString("id_actividad_compra") : "-1";
                String fecha = rsAct.getString("fecha1")!=null ? rsAct.getString("fecha1") : "";
                String ruc = rsAct.getString("ruc")!=null ? rsAct.getString("ruc") : "";
                String proveedor = rsAct.getString("proveedor")!=null ? rsAct.getString("proveedor") : "";
                String detalle = rsAct.getString("detalle")!=null ? rsAct.getString("detalle") : "";
                String num_documento = rsAct.getString("num_documento")!=null ? rsAct.getString("num_documento") : "";
                String cantidad = rsAct.getString("cantidad")!=null ? rsAct.getString("cantidad") : "";
                String base_12 = rsAct.getString("base_12")!=null ? rsAct.getString("base_12") : "";
                String base_0 = rsAct.getString("base_0")!=null ? rsAct.getString("base_0") : "";
                String iva = rsAct.getString("iva")!=null ? rsAct.getString("iva") : "";
                float total = rsAct.getString("total")!=null ? rsAct.getFloat("total") : 0;
                this.total_gasto += total;
                html += "<tr id=\"fGasto"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">";
                html += "<td style=\"width:99px\"><input type=\"hidden\" id=\"idActCom"+i+"\" value=\""+id_actividad_compra+"\" />"
                        + "<input id=\"fac_fecha"+i+"\" type=\"text\" class=\"texto\" style=\"width:100px\" maxlength=\"10\" value=\""+fecha+"\" onkeypress=\"_evaluar(event, '0123456789/')\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+")\" onkeyup=\"if(this.value.length==2 || this.value.length==5)this.value+='/';\" /></td>";
                html += "<td style=\"width:114px\"><input id=\"fac_ruc"+i+"\" type=\"text\" class=\"texto\" style=\"width:110px\" maxlength=\"13\" value=\""+ruc+"\" onkeypress=\"_numero(event)\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+")\" /></td>";
                html += "<td style=\"width:154px\"><input id=\"fac_proveedor"+i+"\" type=\"text\" maxlength=\"160\" class=\"texto\" style=\"width:150px\" value=\""+proveedor+"\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+")\" /></td>";
                html += "<td style=\"width:104px\"><input id=\"fac_detalle"+i+"\" type=\"text\" class=\"texto\" style=\"width:100px\" value=\""+detalle+"\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+")\" /></td>";
                html += "<td style=\"width:74px\"><input id=\"fac_cantidad"+i+"\" type=\"text\" class=\"caja\" style=\"width:70px\" value=\""+cantidad+"\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+")\" /></td>";
                html += "<td style=\"width:124px\"><input id=\"fac_num_documento"+i+"\" type=\"text\" class=\"texto\" maxlength=\"20\" style=\"width:120px\" value=\""+num_documento+"\" onkeypress=\"_evaluar(event, '0123456789-')\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+")\" onkeyup=\"if(this.value.length==3 || this.value.length==7)this.value+='-';\" /></td>";
                html += "<td style=\"width:74px\"><input id=\"fac_base_12_"+i+"\" type=\"text\" class=\"caja\" style=\"width:70px\" value=\""+base_12+"\" onkeypress=\"_evaluar(event, '0123456789.');\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+", '1')\" /></td>";
                html += "<td style=\"width:74px\"><input id=\"fac_base_0_"+i+"\" type=\"text\" class=\"caja\" style=\"width:70px\" value=\""+base_0+"\" onkeypress=\"_evaluar(event, '0123456789.');\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+", '1')\" /></td>";
                html += "<td style=\"width:64px\"><input id=\"fac_iva"+i+"\" type=\"text\" class=\"caja\" style=\"width:60px\" value=\""+iva+"\" onkeypress=\"_evaluar(event, '0123456789.')\" onkeyup=\"mer_seguimientoCompraActualizar(event, "+i+", '0')\" /></td>";
                html += "<td style=\"width:74px\"><input id=\"fac_total"+i+"\" type=\"text\" class=\"caja\" style=\"width:70px\" value=\""+total+"\" /></td>";
                html += "</tr>";
                i++;
            }
            html += "</tbody>";
            html += "</table></div>";
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return html;
    }
    public String tblLiquidacionFacturas(ResultSet rsAct, int alto)
    {
        String html = "";
        try{
            this.total_gasto = 0;
            int i=0;
            html += "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">";
            html += "<tr class=\"jm_fila_cab\">"
                    + "<th class=\"jm_TH\" style=\"width:104px\">FECHA</th>"
                    + "<th class=\"jm_TH\" style=\"width:114px\">RUC</th>"
                    + "<th class=\"jm_TH\" style=\"width:154px\">PROVEEDOR</th>"
                    + "<th class=\"jm_TH\" style=\"width:104px\">DETALLE</th>"
                    + "<th class=\"jm_TH\" style=\"width:70px\">CANTIDAD</th>"
                    + "<th class=\"jm_TH\" style=\"width:124px\">No. DOC.</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">BASE 12%</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">BASE 0%</th>"
                    + "<th class=\"jm_TH\" style=\"width:64px\">IVA</th>"
                    + "<th class=\"jm_TH\" style=\"width:74px\">TOTAL</th>"
                    + "</tr>"
                    + "</table>";
            
            html += "<div style=\"width:883px;height:"+alto+"px;overflow:auto;\">";
            html += "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">";
            while(rsAct.next()){
                String id_actividad_compra = rsAct.getString("id_actividad_compra")!=null ? rsAct.getString("id_actividad_compra") : "-1";
                String fecha = rsAct.getString("fecha1")!=null ? rsAct.getString("fecha1") : "";
                String ruc = rsAct.getString("ruc")!=null ? rsAct.getString("ruc") : "";
                String proveedor = rsAct.getString("proveedor")!=null ? rsAct.getString("proveedor") : "";
                String detalle = rsAct.getString("detalle")!=null ? rsAct.getString("detalle") : "";
                String num_documento = rsAct.getString("num_documento")!=null ? rsAct.getString("num_documento") : "";
                String cantidad = rsAct.getString("cantidad")!=null ? rsAct.getString("cantidad") : "";
                String base_12 = rsAct.getString("base_12")!=null ? rsAct.getString("base_12") : "";
                String base_0 = rsAct.getString("base_0")!=null ? rsAct.getString("base_0") : "";
                String iva = rsAct.getString("iva")!=null ? rsAct.getString("iva") : "";
                float total = rsAct.getString("total")!=null ? rsAct.getFloat("total") : 0;
                this.total_gasto += total;
                html += "<tr id=\"fGasto"+i+"\" onmouseover=\"this.className='jm_filaSobre'\" onmouseout=\"this.className='jm_filaImp'\">";
                html += "<td style=\"width:104px\"><input type=\"hidden\" id=\"idActCom"+i+"\" value=\""+id_actividad_compra+"\" />"+fecha+"</td>";
                html += "<td style=\"width:114px\">"+ruc+"</td>";
                html += "<td style=\"width:154px\">"+proveedor+"</td>";
                html += "<td style=\"width:104px\">"+detalle+"</td>";
                html += "<td style=\"width:70px\" align=\"right\">"+cantidad+"</td>";
                html += "<td style=\"width:124px\">"+num_documento+"</td>";
                html += "<td style=\"width:74px\" align=\"right\">"+base_12+"</td>";
                html += "<td style=\"width:74px\" align=\"right\">"+base_0+"</td>";
                html += "<td style=\"width:64px\" align=\"right\">"+iva+"</td>";
                html += "<td style=\"width:74px\" align=\"right\">"+total+"</td>";
                html += "</tr>";
                i++;
            }
            html += "</table>";
            html += "</div>";
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return html;
    }
    public float total_gasto()
    {
        return this.total_gasto;
    }
    
    public boolean ingresarFactura(String id_actividad, String fecha, String ruc, String proveedor, String num_documento, 
            String cantidad, String base_12, String base_0, String iva, String total, String detalle)
    {
        return this.ejecutar("insert into tbl_actividad_compra(id_actividad, fecha_ingreso, fecha, ruc, proveedor, num_documento, cantidad, base_12, base_0, iva, total, detalle) "
                + "values("+id_actividad+", getdate(), '"+fecha+"', '"+ruc+"', '"+proveedor+"', '"+num_documento+"', "+cantidad+", "+base_12+", "+base_0+", "+iva+", "+total+", '"+detalle+"')");
    }
    public boolean actualizarFactura(String id_actividad_compra, String fecha, String ruc, String proveedor, String num_documento, 
            String cantidad, String base_12, String base_0, String iva, String total, String detalle)
    {
        return this.ejecutar("update tbl_actividad_compra set fecha='"+fecha+"', ruc='"+ruc+"', proveedor='"+proveedor+"', num_documento='"+num_documento+
                "', cantidad="+cantidad+", base_12="+base_12+", base_0="+base_0+", iva="+iva+", total="+total+", detalle='"+detalle+"' where id_actividad_compra="+id_actividad_compra);
    }
    
    public boolean actualizaDistribucionGastoFarmacias(String id_actividad)
    {
        List sql = new ArrayList();
        try{
            ResultSet rs = this.consulta("select PMF.id_plan_mercadeo, oficina, p_gasto " +
            "from (tbl_actividad as A inner join tbl_estrategia as E on A.id_estrategia=E.id_estrategia) " +
            "inner join tbl_plan_mercadeo_farmacia as PMF on PMF.id_plan_mercadeo=E.id_plan_mercadeo " +
            "where A.id_actividad="+id_actividad);
            
            String id_plan_mercadeo = "-1";
            String total = "0";
            if(rs.next()){
                id_plan_mercadeo = rs.getString("id_plan_mercadeo")!=null ? rs.getString("id_plan_mercadeo") : "-1";
                ResultSet rsTotal = this.consulta("select case when sum(total)>0 then sum(total) else 0 end " +
                            "from (tbl_estrategia as E with (nolock) inner join tbl_actividad as A on E.id_estrategia=A.id_estrategia) " +
                            "inner join tbl_actividad_compra as C with(nolock) on A.id_actividad=C.id_actividad " +
                            "where E.id_plan_mercadeo="+id_plan_mercadeo);
                if(rsTotal.next()){
                    total = rsTotal.getString(1)!=null ? rsTotal.getString(1) : "0";
                    rsTotal.close();
                }
                rs.beforeFirst();
            }
            
            while(rs.next()){
                id_plan_mercadeo = rs.getString("id_plan_mercadeo")!=null ? rs.getString("id_plan_mercadeo") : "-1";
                String oficina = rs.getString("oficina")!=null ? rs.getString("oficina") : "";
                String p_gasto = rs.getString("p_gasto")!=null ? rs.getString("p_gasto") : "0";
                sql.add("update tbl_plan_mercadeo_farmacia set gasto = "+p_gasto+" * "+total+" / 100 "
                        + "where id_plan_mercadeo="+id_plan_mercadeo+" and oficina='"+oficina+"';");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return this.transacciones(sql);
    }
    
}
