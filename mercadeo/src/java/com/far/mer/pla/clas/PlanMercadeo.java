/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla.clas;

import com.far.lib.BaseDatos;
import com.far.lib.Matriz;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jorge
 */
public class PlanMercadeo extends BaseDatos {
    public PlanMercadeo(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }

    public long getSecuencial()
    {
        long sec = -1;
        try{
            ResultSet rs = this.consulta("SELECT case when max(sec_tipo_plan) is not null then max(sec_tipo_plan)+1 else 1 end FROM tbl_plan_mercadeo with (nolock);");
            if(rs.next()){
                sec = rs.getString(1)!=null ? rs.getLong(1) : -1;
                rs.close();
            }
        }catch(Exception e){
            sec = -1;
            e.printStackTrace();
        }
        return sec;
    }

    public boolean planDuplicado(String id_plan_mercadeo, String plan_mercadeo)
    {
        try{
            ResultSet rs = this.consulta("SELECT * FROM tbl_plan_mercadeo with (nolock) where plan_mercadeo='"+plan_mercadeo+"' and id_plan_mercadeo<>"+id_plan_mercadeo+";");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getPlanMercadeo(String id)
    {
        return this.consulta("select id_plan_mercadeo, sec_tipo_plan, id_tipo_plan, plan_mercadeo, CONVERT(VARCHAR,fecha_creacion, 103) as fecha_creacion, "
                + "usuario_creacion, tipo_alcance, tipo_alcance_de, evalua_ventas, CONVERT(VARCHAR,fecha_ini, 103) as fecha_ini, CONVERT(VARCHAR,fecha_fin, 103) as fecha_fin, "
                + "promedio_ventas, CONVERT(VARCHAR,fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR,fecha_fin_averificar, 103) as fecha_fin_averificar, "
                + "tipo_dist_gasto, proyeccion_ventas, premio, adjuntos, total_auspicio, estado, plan_completo, mecanica_tipo, mecanica, aplica_prom_p_v, CONVERT(VARCHAR,ope_fecha_ini, 103) as ope_fecha_ini, "
                + "CONVERT(VARCHAR,ope_fecha_fin, 103) as ope_fecha_fin, registro_operaciones, aprobada_operaciones, abierto, "
                + "case estado when 1 then 'CREADO' when 2 then 'RECHAZADO' when 3 then 'APROBADO POR OPERACIONES' "
                + "when 4 then 'AUTORIZADO POR MARKETING' when 5 then 'AUTORIZADO POR VENTAS' "
                + "when 6 then 'AUTORIZADO POR COMERCIAL' when 9 then 'TERMINADO' when 10 then 'ANULADO' else '' end as estado_txt, "
                + "aprobado_mark_vent, aprobado_comercial "
                + "from tbl_plan_mercadeo with (nolock) where id_plan_mercadeo="+id+";");
    }
    
    public ResultSet getPlanMercadeoTipo(String id)
    {
        return this.consulta("select P.id_tipo_plan, T.p_incremento, P.sec_tipo_plan, P.plan_mercadeo, T.nombre, CONVERT(VARCHAR, P.fecha_aprobacion, 103) as fecha_aprobacion, " +
        "T.p_incremento, T.coordinador, sum(A.pre_total) as pre_total, sum(A.monto) as gasto, sum(A.pre_total) - sum(A.monto), " +
        "P.estado, CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, CONVERT(VARCHAR, P.fecha_terminacion, 103) as fecha_terminacion, " + 
        "case when CONVERT(date, DATEADD(day, T.dias_prolonga, fecha_terminacion)) >= CONVERT(date, GETDATE()) then 1 else 0 end as prolonga, "+ 
        "CONVERT(VARCHAR, P.fecha_operaciones, 103) as fecha_operaciones, "+ 
        "CONVERT(VARCHAR, P.fecha_mark_vent, 103) as fecha_mark_vent, "+ 
        "CONVERT(VARCHAR, P.fecha_aprobacion, 103) as fecha_comercial "+ 
        "from ((tbl_tipo_plan as T with (nolock) inner join tbl_plan_mercadeo as P with (nolock) on T.id_tipo_plan=P.id_tipo_plan) " +
        "inner join tbl_estrategia as E with (nolock) on P.id_plan_mercadeo=E.id_plan_mercadeo) " +
        "inner join tbl_actividad as A with (nolock) on E.id_estrategia=A.id_estrategia " +
        "where P.id_plan_mercadeo="+id +
        " group by P.id_tipo_plan, T.p_incremento, P.sec_tipo_plan, P.plan_mercadeo, T.nombre, fecha_aprobacion, T.p_incremento, T.dias_prolonga, " +
        "T.coordinador, P.estado, P.fecha_creacion, fecha_operaciones, fecha_mark_vent, fecha_terminacion " +
        "order by P.plan_mercadeo");
    }
    
    public ResultSet getActividadesEjecucion(String id, String usuario)
    {
        return this.consulta("select * from tbl_actividad with (nolock) "
                + "where eje_finalizada_fecha is null and usuario_eje='"+usuario+"' and "
                + "id_estrategia in (SELECT id_estrategia FROM tbl_estrategia with (nolock) where id_plan_mercadeo="+id+");");
    }
    
    public ResultSet getActividades(String id, String p_incremento)
    {
        return this.consulta("select *, case when tipo_pago='i' then 'COMPRAS INTERNAS' else 'PAGO EN FARMACIA' end as medio, "
                + "cast( Round(pre_total + (pre_total * "+p_incremento+" / 100), 2, 0) as numeric(13,2) ) as total_incremento, "
                + "cast( Round((pre_total + (pre_total * "+p_incremento+" / 100))-monto, 2, 0) as numeric(13,2) ) as diferencia from tbl_actividad with (nolock) "
                + "where id_estrategia in (SELECT id_estrategia FROM tbl_estrategia with (nolock) where id_plan_mercadeo="+id+");");
    }
    
    public ResultSet getVerPlanMercadeo(String id)
    {
        return this.consulta("select P.id_plan_mercadeo, P.sec_tipo_plan, P.id_tipo_plan, P.plan_mercadeo, CONVERT(VARCHAR, P.fecha_creacion, 103) as fecha_creacion, "
                + "P.usuario_creacion, P.tipo_alcance, P.tipo_alcance_de, CONVERT(VARCHAR, P.fecha_ini, 103) as fecha_ini, CONVERT(VARCHAR, P.fecha_fin, 103) as fecha_fin, "
                + "P.promedio_ventas, CONVERT(VARCHAR,fecha_ini_averificar, 103) as fecha_ini_averificar, CONVERT(VARCHAR,fecha_fin_averificar, 103) as fecha_fin_averificar, "
                + "P.tipo_dist_gasto, P.proyeccion_ventas, P.premio, P.adjuntos, P.plan_completo, mecanica_tipo, mecanica, aplica_prom_p_v, CONVERT(VARCHAR,ope_fecha_ini, 103) as ope_fecha_ini, "
                + "CONVERT(VARCHAR,ope_fecha_fin, 103) as ope_fecha_fin, registro_operaciones, aprobada_operaciones, P.abierto, TP.nombre as tipo_plan, P.motivo_rechazo, P.motivo_rechazo_operacion "
                + "from tbl_plan_mercadeo as P with (nolock) inner join tbl_tipo_plan as TP with (nolock) on TP.id_tipo_plan=P.id_tipo_plan where P.id_plan_mercadeo="+id+";");
    }

    public boolean existenActividades(String id_plan_mercadeo)
    {
        try{
            ResultSet rs = this.consulta("select * from tbl_actividad with (nolock) "
                    + "where id_estrategia in (SELECT id_estrategia FROM tbl_estrategia with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+");");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean activarCierre(String id_plan_mercadeo)
    {
        try{
            ResultSet rs = this.consulta("select case when CONVERT(date, fecha_fin_averificar) < CONVERT(date, GETDATE()) then 1 else 0 end as fin_ventas, "
                    + "case when CONVERT(date, fecha_terminacion) < CONVERT(date, GETDATE()) then 1 else 0 end as fin_actividades "
                    + "from tbl_plan_mercadeo with (nolock) "
                    + "where id_plan_mercadeo="+id_plan_mercadeo+";");
            if(rs.next()){
                int fin_ventas = rs.getString("fin_ventas")!=null ? rs.getInt("fin_ventas") : 0;
                int fin_actividades = rs.getString("fin_actividades")!=null ? rs.getInt("fin_actividades") : 0;
                if(fin_ventas==1 && fin_actividades==1){
                    return true;
                }
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    /*public boolean hayActividadesSinConfirmar(String id_plan_mercadeo)
    {
        try{
            ResultSet rs = this.consulta("select * from tbl_actividad with (nolock) where mecanica_tipo='o' and aprobada_operaciones=0 and "
                    + " id_estrategia in (SELECT id_estrategia FROM tbl_estrategia with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+");");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }*/
    
    public String getIdActividades(String id_plan_mercadeo)
    {
        String ids = "";
        try{
            ResultSet rs = this.consulta("select id_actividad from tbl_actividad with (nolock) "
                    + "where id_estrategia in (SELECT id_estrategia FROM tbl_estrategia with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+");");
            while(rs.next()){
                ids += rs.getString("id_actividad")!=null ? rs.getString("id_actividad") + "," : "";
            }
            if(ids.compareTo("")!=0){
                ids = ids.substring(0, ids.length()-1);
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ids;
    }
    
    public String ingresar(String usuario, String empleado, String centro_costos, String id_tipo_plan, String sec_tipo_plan, String plan_mercadeo, String tipo_alcance, String evalua_ventas, 
            String tipo_alcance_de, String fecha_ini, String fecha_fin, String promedio_ventas, String fecha_ini_averificar, 
            String fecha_fin_averificar, String tipo_dist_gasto, String proyeccion_ventas, String ps_gasto, String premio, String adjuntos, 
            String total_auspicio, String mecanica_tipo, String mecanica, String aplica_prom_p_v, String ope_fecha_ini, String ope_fecha_fin, String abierto, 
            String oficinas, String nombres, String centros_costos, String p_ventas, String ps_crecimiento, String proy_ventas, String cod_niveles, String desc_niveles, 
            String clases, String desc_clases, String lineas, String desc_lineas, String codigos, String descripciones, 
            String codigo_vendedores, String nombre_vendedores, String numero_idclientes, String nombres_comerciales, String idsLab, 
            String montos, String rucs, String fechRegs, String montops, String nomsCom, String numForms, String admin_tiempos_conf, 
            String archivos, String descripciones_mecanica, BaseDatos objAuspicio, String auspicio_manual)
    {
        String id_plan_mercadeo = this.insertar("insert into tbl_plan_mercadeo(id_tipo_plan, sec_tipo_plan, plan_mercadeo, fecha_creacion, "
                + "usuario_creacion, nombre_solicitante, centro_costos, tipo_alcance, evalua_ventas, tipo_alcance_de, fecha_ini, fecha_fin, promedio_ventas, fecha_ini_averificar, "
                + "fecha_fin_averificar, tipo_dist_gasto, proyeccion_ventas, premio, adjuntos, total_auspicio,  mecanica_tipo,"
                + " mecanica, aplica_prom_p_v, ope_fecha_ini, ope_fecha_fin, abierto) "
                + "values('"+id_tipo_plan+"', "+sec_tipo_plan+", '"+plan_mercadeo+"', getDate(), '"+usuario+"', '"+empleado+"', '"+centro_costos+"', '"+tipo_alcance+"', "+evalua_ventas+
                ", '"+tipo_alcance_de+"', '"+fecha_ini+"', '"+fecha_fin+"', "+promedio_ventas+", '"+fecha_ini_averificar+"', '"+fecha_fin_averificar+
                "', '"+tipo_dist_gasto+"', "+proyeccion_ventas+", '"+premio+"', '"+adjuntos+"', "+total_auspicio+", '"+mecanica_tipo+
                "', '"+mecanica+"', '"+aplica_prom_p_v+"', "+ope_fecha_ini+", "+ope_fecha_fin+", "+abierto+");");
        
        if(id_plan_mercadeo.compareTo("-1") != 0){
            List sql = new ArrayList();
            
            //  farmacias   ----------------------------------------------------------------------------------------------
            if(oficinas.compareTo("")!=0){
                String oficina[] = oficinas.split(",");
                String nombre[] = nombres.split(",");
                String centro_costo[] = centros_costos.split(",");
                String p_venta[] = p_ventas.split(",");
                String p_crecimiento[] = ps_crecimiento.split(",");
                String proy_venta[] = proy_ventas.split(",");
                String p_gasto[] = ps_gasto.split(",");
                for(int i=0; i<oficina.length; i++){
                    if(oficina[i].toLowerCase().compareTo("null")!=0 && nombre[i].toLowerCase().compareTo("null")!=0 && proy_venta[i].toLowerCase().compareTo("null")!=0){
                        sql.add("insert into tbl_plan_mercadeo_farmacia(id_plan_mercadeo, oficina, nombre, centro_costos, p_ventas, p_crecimiento, proy_ventas, p_gasto) "
                        + "values( "+id_plan_mercadeo+", '"+oficina[i]+"', '"+centro_costo[i]+"', '"+nombre[i]+"', "+p_venta[i]+", "+p_crecimiento[i]+", "+proy_venta[i]+", "+p_gasto[i]+");");
                    }
                }
            }
            
            //  filtro para productos   ----------------------------------------------------------------------------------------------
            if(cod_niveles.compareTo("")!=0){
                String cod_nivel[] = cod_niveles.split(",");
                String desc_nivel[] = desc_niveles.split(",");
                String clase[] = clases.split(",");
                String desc_clase[] = desc_clases.split(",");
                String linea[] = lineas.split(",");
                String desc_linea[] = desc_lineas.split(",");
                for(int i=0; i<cod_nivel.length; i++){
                    sql.add("insert into tbl_plan_mercadeo_producto_filtro(id_plan_mercadeo, cod_nivel, desc_nivel, clase, desc_clase, linea, desc_linea) "
                        + "values( "+id_plan_mercadeo+", "+cod_nivel[i]+", '"+desc_nivel[i]+"', '"+clase[i]+"', '"+desc_clase[i]+"', '"+linea[i]+"', '"+desc_linea[i]+"');");
                }
            }
            
            //  productos   ----------------------------------------------------------------------------------------------
            if(codigos.compareTo("")!=0){
                String codigo[] = codigos.split(",");
                String descripcion[] = descripciones.split(",");
                for(int i=0; i<codigo.length; i++){
                    sql.add("insert into tbl_plan_mercadeo_producto(id_plan_mercadeo, codigo, descripcion) "
                        + "values( "+id_plan_mercadeo+", '"+codigo[i]+"', '"+descripcion[i]+"');");
                }
            }
            
            //  vendedores   ----------------------------------------------------------------------------------------------
            if(codigo_vendedores.compareTo("")!=0){
                String codigo_vendedor[] = codigo_vendedores.split(",");
                String nombre_vendedor[] = nombre_vendedores.split(",");
                for(int i=0; i<codigo_vendedor.length; i++){
                    sql.add("insert into tbl_plan_mercadeo_vendedor(id_plan_mercadeo, codigo_vendedor, nombre_vendedor) "
                        + "values( "+id_plan_mercadeo+", '"+codigo_vendedor[i]+"', '"+nombre_vendedor[i]+"');");
                }
            }
            
            //  clientes   ----------------------------------------------------------------------------------------------
            if(numero_idclientes.compareTo("")!=0){
                String numero_idcliente[] = numero_idclientes.split(",");
                String nombre_comercial[] = nombres_comerciales.split(",");
                for(int i=0; i<numero_idcliente.length; i++){
                    sql.add("insert into tbl_plan_mercadeo_cliente(id_plan_mercadeo, numero_idcliente, nombre_comercial) "
                        + "values( "+id_plan_mercadeo+", '"+numero_idcliente[i]+"', '"+nombre_comercial[i]+"');");
                }
            }
            
            //  auspicios laboratorios  --------------------------------------------------------------------------------------
            if(idsLab.compareTo("")!=0){
                String idLab[] = idsLab.split(",");
                String monto[] = montos.split(",");
                for(int i=0; i<idLab.length; i++){
                    if(this.getSaldoLaboratorio(id_plan_mercadeo, idLab[i], Float.parseFloat(monto[i]) )){
                        sql.add("insert into tbl_plan_mercadeo_laboratorio(id_plan_mercadeo, id_tipo_plan, id_laboratorio, monto) "
                            + "values( "+id_plan_mercadeo+", '"+id_tipo_plan+"', "+idLab[i]+", "+monto[i]+");");
                        
                        sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor, es_entrada) "
                            + "values("+idLab[i]+", '"+id_tipo_plan+"', '"+usuario+"', getdate(), 'Asignación de presupuesto para el plan de mercadeo: "+plan_mercadeo+"', "+monto[i]+", 0);");
                    }else{
                        this.setError("El monto $" + monto[i] + " supera el saldo disponible del laboratorio.");
                    }
                }
            }

            //  auspicios no estrategicos (proveedores) --------------------------------------------------------------------
            List sql2 = new ArrayList();
            if(rucs.compareTo("")!=0){
                String ruc[] = rucs.split(",");
                String nomCom[] = nomsCom.split(",");
                String fechReg[] = fechRegs.split(",");
                String montop[] = montops.split(",");
                String numForm[] = numForms.split(",");
                for(int i=0; i<ruc.length; i++){
                    sql.add("insert into tbl_plan_mercadeo_proveedor(id_plan_mercadeo, numero_idproveedor, nombre_comercial, num_formulario, monto, fecha_registro, plazo_confirmacion) "
                            + "values( "+id_plan_mercadeo+", '"+ruc[i]+"', '"+nomCom[i]+"', '"+numForm[i]+"', "+montop[i]+", '"+fechReg[i]+"', dateadd(day, "+admin_tiempos_conf+", '"+fechReg[i]+"') );");
                    
                    // actualizo los auspicios la confirmacion y el estado
                    sql2.add("update tbl_auspicio set tipo_confirmacion='a', estado='5' where num_auspicio='"+numForm[i]+"';");
                }
            }
            
            //  formulario y archivos adjuntos   ----------------------------------------------------------------------------------------------
            if(archivos.compareTo("")!=0){
                String archivo[] = archivos.split("&;");
                String descripcion[] = descripciones_mecanica.split("&;");
                for(int i=0; i<archivo.length; i++){
                    sql.add("insert into tbl_plan_mercadeo_adjunto(id_plan_mercadeo, archivo, descripcion) "
                        + "values( "+id_plan_mercadeo+", '"+archivo[i]+"', '"+descripcion[i]+"');");
                }
            }

            //  usuarios con limite de tiempo para confirmacion  --------------------------------------------------------------------------------------
            /*if(id_tipo_plan_usuarios.compareTo("")!=0){
                String id_tipo_plan_usuario[] = id_tipo_plan_usuarios.split(",");
                String num_dias[] = nums_dias.split(",");
                for(int i=0; i<id_tipo_plan_usuario.length; i++){
                    sql.add("insert into tbl_tipo_plan_usuario_confirmar_auspicio(id_plan_mercadeo, id_tipo_plan_usuario, fecha_inicio, num_dias) "
                        + "values( "+id_plan_mercadeo+", '"+id_tipo_plan_usuario[i]+"', getDate(), '"+num_dias[i]+"');");
                }
            }*/
        
            if( this.transacciones(sql) ){
                objAuspicio.transacciones(sql2);
                return id_plan_mercadeo;
            }else{
                this.ejecutar("delete from tbl_plan_mercadeo where id_plan_mercadeo="+id_plan_mercadeo);
            }
            
        }
        return "-1";
    }

    public boolean actualizar(String id_plan_mercadeo, String usuario, String id_tipo_plan, String plan_mercadeo, String tipo_alcance, String evalua_ventas,  
            String tipo_alcance_de, String fecha_ini, String fecha_fin, String promedio_ventas, String tipo_dist_gasto, String fecha_ini_averificar, 
            String fecha_fin_averificar, String proyeccion_ventas, String ps_gasto, String premio, String adjuntos, String total_auspicio, 
            String mecanica_tipo, String mecanica, String aplica_prom_p_v, String ope_fecha_ini, String ope_fecha_fin, String abierto,
            String oficinas, String nombres, String centros_costos, String p_ventas, String ps_crecimiento, String proy_ventas, String cod_niveles, String desc_niveles, 
            String clases, String desc_clases, String lineas, String desc_lineas, String codigos, String descripciones, 
            String codigo_vendedores, String nombre_vendedores, String numero_idclientes, String nombres_comerciales, String idsLab, 
            String montos, String rucs, String fechRegs, String montops, String nomsCom, String numForms, String admin_tiempos_conf, 
            String archivos, String descripciones_mecanica, String aprobada_operaciones, BaseDatos objAuspicio, String auspicio_manual)
    {
        List sql = new ArrayList();
        
        sql.add("update tbl_plan_mercadeo set id_tipo_plan='"+id_tipo_plan+"', tipo_alcance='"+tipo_alcance+"', evalua_ventas="+evalua_ventas+", tipo_alcance_de='"+tipo_alcance_de+
                "', plan_mercadeo='"+plan_mercadeo+"', fecha_ini='"+fecha_ini+"', fecha_fin='"+fecha_fin+"', promedio_ventas="+promedio_ventas+
                ", fecha_ini_averificar='"+fecha_ini_averificar+"', fecha_fin_averificar='"+fecha_fin_averificar+"', tipo_dist_gasto='"+tipo_dist_gasto+
                "', proyeccion_ventas="+proyeccion_ventas+", premio='"+premio+"', adjuntos='"+adjuntos+"', total_auspicio="+total_auspicio+
                ", mecanica_tipo='"+mecanica_tipo+ "', mecanica='"+mecanica+"', aplica_prom_p_v='"+aplica_prom_p_v+"', ope_fecha_ini="+ope_fecha_ini+
                ", ope_fecha_fin="+ope_fecha_fin+", abierto="+abierto+ (aprobada_operaciones.compareTo("0")==0 ? ", registro_operaciones=0" : "") +
                " where id_plan_mercadeo="+id_plan_mercadeo+";");

        //  farmacias   ----------------------------------------------------------------------------------------------
        sql.add("delete from tbl_plan_mercadeo_farmacia where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(oficinas.compareTo("")!=0){
            String oficina[] = oficinas.split(",");
            String nombre[] = nombres.split(",");
            String centro_costo[] = centros_costos.split(",");
            String p_venta[] = p_ventas.split(",");
            String p_crecimiento[] = ps_crecimiento.split(",");
            String proy_venta[] = proy_ventas.split(",");
            String p_gasto[] = ps_gasto.split(",");
            for(int i=0; i<oficina.length; i++){
                if(oficina[i].toLowerCase().compareTo("null")!=0 && nombre[i].toLowerCase().compareTo("null")!=0 && proy_venta[i].toLowerCase().compareTo("null")!=0){
                    sql.add("insert into tbl_plan_mercadeo_farmacia(id_plan_mercadeo, oficina, nombre, centro_costos, p_ventas, p_crecimiento, proy_ventas, p_gasto) "
                    + "values( "+id_plan_mercadeo+", '"+oficina[i]+"', '"+centro_costo[i]+"', '"+nombre[i]+"', "+p_venta[i]+", "+p_crecimiento[i]+", "+proy_venta[i]+", "+p_gasto[i]+");");
                }
            }
        }
        
        //  filtro para productos   ----------------------------------------------------------------------------------------------
        sql.add("delete from tbl_plan_mercadeo_producto_filtro where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(cod_niveles.compareTo("")!=0){
            String cod_nivel[] = cod_niveles.split(",");
            String desc_nivel[] = desc_niveles.split(",");
            String clase[] = clases.split(",");
            String desc_clase[] = desc_clases.split(",");
            String linea[] = lineas.split(",");
            String desc_linea[] = desc_lineas.split(",");
            for(int i=0; i<cod_nivel.length; i++){
                sql.add("insert into tbl_plan_mercadeo_producto_filtro(id_plan_mercadeo, cod_nivel, desc_nivel, clase, desc_clase, linea, desc_linea) "
                    + "values( "+id_plan_mercadeo+", "+cod_nivel[i]+", '"+desc_nivel[i]+"', '"+clase[i]+"', '"+desc_clase[i]+"', '"+linea[i]+"', '"+desc_linea[i]+"');");
            }
        }

        //  productos   ----------------------------------------------------------------------------------------------
        sql.add("delete from tbl_plan_mercadeo_producto where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(codigos.compareTo("")!=0){
            String codigo[] = codigos.split(",");
            String descripcion[] = descripciones.split(",");
            for(int i=0; i<codigo.length; i++){
                sql.add("insert into tbl_plan_mercadeo_producto(id_plan_mercadeo, codigo, descripcion) "
                    + "values( "+id_plan_mercadeo+", '"+codigo[i]+"', '"+descripcion[i]+"');");
            }
        }

        //  vendedores   ----------------------------------------------------------------------------------------------
        sql.add("delete from tbl_plan_mercadeo_vendedor where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(codigo_vendedores.compareTo("")!=0){
            String codigo_vendedor[] = codigo_vendedores.split(",");
            String nombre_vendedor[] = nombre_vendedores.split(",");
            for(int i=0; i<codigo_vendedor.length; i++){
                sql.add("insert into tbl_plan_mercadeo_vendedor(id_plan_mercadeo, codigo_vendedor, nombre_vendedor) "
                    + "values( "+id_plan_mercadeo+", '"+codigo_vendedor[i]+"', '"+nombre_vendedor[i]+"');");
            }
        }

        //  clientes   ----------------------------------------------------------------------------------------------
        sql.add("delete from tbl_plan_mercadeo_cliente where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(numero_idclientes.compareTo("")!=0){
            String numero_idcliente[] = numero_idclientes.split(",");
            String nombre_comercial[] = nombres_comerciales.split(",");
            for(int i=0; i<numero_idcliente.length; i++){
                sql.add("insert into tbl_plan_mercadeo_cliente(id_plan_mercadeo, numero_idcliente, nombre_comercial) "
                    + "values( "+id_plan_mercadeo+", '"+numero_idcliente[i]+"', '"+nombre_comercial[i]+"');");
            }
        }

        //  auspicios laboratorios  --------------------------------------------------------------------------------------
        sql.add("delete from tbl_plan_mercadeo_laboratorio where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(idsLab.compareTo("")!=0){
            ResultSet rs = this.consulta("select id_laboratorio, id_tipo_plan, monto from tbl_plan_mercadeo_laboratorio with (nolock) "
                + "where id_plan_mercadeo="+id_plan_mercadeo);
            String mat[][] = Matriz.ResultSetAMatriz(rs);
        
            String idLab[] = idsLab.split(",");
            String monto[] = montos.split(",");
            for(int i=0; i<idLab.length; i++){
                if(this.getSaldoLaboratorio(id_plan_mercadeo, idLab[i], Float.parseFloat(monto[i]) )){
                    sql.add("insert into tbl_plan_mercadeo_laboratorio(id_plan_mercadeo, id_tipo_plan, id_laboratorio, monto) "
                        + "values( "+id_plan_mercadeo+", '"+id_tipo_plan+"', "+idLab[i]+", "+monto[i]+");");
                    
                    int pos = this.enMatriz(mat, idLab[i], id_tipo_plan);
                    if(pos!=-1){
                        if(Float.parseFloat(monto[i]) != Float.parseFloat(mat[pos][2])){
                            sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor, es_entrada) "
                                + "values("+idLab[i]+", '"+id_tipo_plan+"', '"+usuario+"', getdate(), 'Re-asignación de presupuesto para el plan de mercadeo: "+plan_mercadeo+"', "+mat[pos][2]+", 1);");
                            sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor, es_entrada) "
                                + "values("+idLab[i]+", '"+id_tipo_plan+"', '"+usuario+"', getdate(), 'Re-asignación de presupuesto para el plan de mercadeo: "+plan_mercadeo+"', "+monto[i]+", 0);");
                        }
                    }else{
                        sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor, es_entrada) "
                            + "values("+idLab[i]+", '"+id_tipo_plan+"', '"+usuario+"', getdate(), 'Re-asignación de presupuesto para el plan de mercadeo: "+plan_mercadeo+"', "+monto[i]+", 0);");
                    }
                }else{
                    this.setError("El monto $" + monto[i] + " supera el saldo disponible del laboratorio.");
                }
            }
        }

        //  auspicios no estrategicos (proveedores) --------------------------------------------------------------------
        List sql2 = new ArrayList();
        ResultSet rsProv = this.consulta("select id_plan_mercadeo, numero_idproveedor, eliminado, num_formulario, monto "
                + "from tbl_plan_mercadeo_proveedor "
                + "where id_plan_mercadeo="+id_plan_mercadeo);
        sql.add("update tbl_plan_mercadeo_proveedor set eliminado=1, monto=NULL where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(this.getFilas(rsProv)>0){
            String matProv[][] = Matriz.ResultSetAMatriz(rsProv);
            String ruc[] = rucs.split(",");
            String nomCom[] = nomsCom.split(",");
            String fechReg[] = fechRegs.split(",");
            String montop[] = montops.split(",");
            String numForm[] = numForms.split(",");
            if(montops.toLowerCase().compareTo("")!=0 && montops.toLowerCase().compareTo("null")!=0){
                int pos = 0;
                int pos1 = 0;
                for(int i=0; i<ruc.length; i++){
                    // todos los auspicios se habilitan
                    sql2.add("update tbl_auspicio set tipo_confirmacion='"+auspicio_manual+"', estado='1' where num_auspicio='"+matProv[i][3]+"';");
                    
                    pos = this.enMatriz(matProv, numForm[i], montop[i], 3, 4);
                    if(pos==-1){
                        pos1 = this.enMatriz(matProv, numForm[i], 3);
                        if(pos1==-1){
                            sql.add("insert into tbl_plan_mercadeo_proveedor(id_plan_mercadeo, numero_idproveedor, nombre_comercial, num_formulario, monto, fecha_registro, plazo_confirmacion) "
                                + "values( "+id_plan_mercadeo+", '"+ruc[i]+"', '"+nomCom[i]+"', '"+numForm[i]+"', "+montop[i]+", '"+fechReg[i]+"', dateadd(day, "+admin_tiempos_conf+", '"+fechReg[i]+"') );");

                            // actualizo en la base de auspicios
                            sql2.add("update tbl_auspicio set tipo_confirmacion='a', estado='5' where num_auspicio='"+numForm[i]+"';");
                        }else{
                            sql.add("update tbl_plan_mercadeo_proveedor set eliminado=0, nombre_comercial='"+nomCom[i]+"', monto="+montop[i]+
                                " where id_plan_mercadeo="+id_plan_mercadeo+" and numero_idproveedor='"+ruc[i]+"' and num_formulario='"+numForm[i]+"'");
                            // va a estado creado     1=creado; 2=anulado; 3=confirmado; 4=confirmado anulado; 5=abierto; 9=cerrado
                            sql2.add("update tbl_auspicio set tipo_confirmacion='a', estado='5' where num_auspicio='"+numForm[i]+"';");
                        }
                    }else{
                        if(matProv[pos][2].compareTo("0")==0 && montop[i].compareTo("")!=0 &&  montop[i].compareTo("null")!=0){
                            sql.add("update tbl_plan_mercadeo_proveedor set eliminado=0, nombre_comercial='"+nomCom[i]+"', monto="+montop[i]+
                                " where id_plan_mercadeo="+id_plan_mercadeo+" and numero_idproveedor='"+ruc[i]+"' and num_formulario='"+numForm[i]+"'");
                            // va a estado creado     1=creado; 2=anulado; 3=confirmado; 4=confirmado anulado; 5=abierto; 9=cerrado
                            sql2.add("update tbl_auspicio set tipo_confirmacion='a', estado='5' where num_auspicio='"+numForm[i]+"';");
                        }
                        
                        /*if(matProv[pos][2].compareTo("1")==0 && montop[i].compareTo("")==0){
                            //sql.add("update tbl_plan_mercadeo_proveedor set eliminado=0, nombre_comercial='"+nomCom[i]+"', monto="+montop[i]+
                            //    " where id_plan_mercadeo="+id_plan_mercadeo+" and numero_idproveedor='"+ruc[i]+"' and num_formulario='"+numForm[i]+"'");
                            
                            // va a estado creado     1=creado; 2=anulado; 3=confirmado; 4=confirmado anulado; 5=abierto; 9=cerrado
                            sql2.add("update tbl_auspicio set tipo_confirmacion='"+auspicio_manual+"', estado='1' where num_auspicio='"+numForm[i]+"';");
                        }*/
                        
                    }
                }
            }else{
                for(int i=0; i<ruc.length; i++){
                    sql2.add("update tbl_auspicio set tipo_confirmacion='"+auspicio_manual+"', estado='1' where num_auspicio='"+matProv[i][3]+"';");
                }
            }
        }
        
        //  formulario y archivos adjuntos   ----------------------------------------------------------------------------------------------
        sql.add("delete from tbl_plan_mercadeo_adjunto where id_plan_mercadeo="+id_plan_mercadeo+";");
        if(archivos.compareTo("")!=0){
            String archivo[] = archivos.split("&;");
            String descripcion[] = descripciones_mecanica.split("&;");
            for(int i=0; i<archivo.length; i++){
                sql.add("insert into tbl_plan_mercadeo_adjunto(id_plan_mercadeo, archivo, descripcion) "
                    + "values( "+id_plan_mercadeo+", '"+archivo[i]+"', '"+descripcion[i]+"');");
            }
        }

        //  usuarios con limite de tiempo para confirmacion  --------------------------------------------------------------------------------------
        /*if(id_tipo_plan_usuarios.compareTo("")!=0){
            sql.add("delete from tbl_tipo_plan_usuario_confirmar_auspicio where id_plan_mercadeo="+id_plan_mercadeo+";");
            String id_tipo_plan_usuario[] = id_tipo_plan_usuarios.split(",");
            String num_dias[] = nums_dias.split(",");
            for(int i=0; i<id_tipo_plan_usuario.length; i++){
                sql.add("insert into tbl_tipo_plan_usuario_confirmar_auspicio(id_plan_mercadeo, id_tipo_plan_usuario, num_dias) "
                    + "values( "+id_plan_mercadeo+", '"+id_tipo_plan_usuario[i]+"', '"+num_dias[i]+"');");
            }
        }*/
        boolean ok = this.transacciones(sql);
        if(ok){
            objAuspicio.transacciones(sql2);
        }
        return ok;
    }

    public boolean setConfAuspicios(String id_plan_mercadeo, String id_tipo_plan_usuarios, String nums_dias)
    {
        List sql = new ArrayList();
        if(id_tipo_plan_usuarios.compareTo("")!=0){
            sql.add("delete from tbl_tipo_plan_usuario_confirmar_auspicio where id_plan_mercadeo="+id_plan_mercadeo+";");
            String id_tipo_plan_usuario[] = id_tipo_plan_usuarios.split(",");
            String num_dias[] = nums_dias.split(",");
            for(int i=0; i<id_tipo_plan_usuario.length; i++){
                sql.add("insert into tbl_tipo_plan_usuario_confirmar_auspicio(id_plan_mercadeo, id_tipo_plan_usuario, num_dias) "
                    + "values( "+id_plan_mercadeo+", '"+id_tipo_plan_usuario[i]+"', '"+num_dias[i]+"');");
            }
        }
        return this.transacciones(sql);
    }
    
    public boolean enviarAAutorizaciones(String id_plan_mercadeo, int plan_completo)
    {
        return this.ejecutar("update tbl_plan_mercadeo set plan_completo="+plan_completo+" where id_plan_mercadeo="+id_plan_mercadeo+";");
    }
    
    public boolean aprobar(String id_plan_mercadeo, String usuario, String estado, String motivo)
    {
        String sql="";
        switch(Integer.parseInt(estado)){
            case 2:
                sql = "update tbl_plan_mercadeo set estado=2, plan_completo=0, fecha_mark_vent=null, usuario_mark_vent='"+usuario+"', aprobado_mark_vent=0, motivo_rechazo_mark_vent='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo+";";
            break;
                
            case 4:
                sql = "update tbl_plan_mercadeo set estado=4, fecha_mark_vent=getdate(), usuario_mark_vent='"+usuario+"', aprobado_mark_vent=1, motivo_rechazo_mark_vent='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo+";";
            break;
                
            case 5:
                sql = "update tbl_plan_mercadeo set estado=5, fecha_mark_vent=getdate(), usuario_mark_vent='"+usuario+"', aprobado_mark_vent=1, motivo_rechazo_mark_vent='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo+";";
            break;
                
            case 6:
                sql = "update tbl_plan_mercadeo set estado=6, fecha_aprobacion=getdate(), usuario_comercial='"+usuario+"', aprobado_comercial=1, motivo_rechazo='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo+";";
            break;
            
            case 7:
                sql = "update tbl_plan_mercadeo set estado=7, plan_completo=0, fecha_mark_vent=null, usuario_mark_vent='"+usuario+"', aprobado_mark_vent=0, motivo_rechazo_mark_vent='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo+";";
            break;
                
            case 8:
                sql = "update tbl_plan_mercadeo set estado=8, plan_completo=0, fecha_aprobacion=null, usuario_comercial='"+usuario+"', aprobado_comercial=0, motivo_rechazo='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo+";";
            break;
        }
        return this.ejecutar(sql);
    }
    
    public boolean aprobarOperaciones(String id_plan_mercadeo, String usuario, String registro_operaciones, String aprobada, String motivo)
    {
        return this.ejecutar("update tbl_plan_mercadeo set registro_operaciones="+registro_operaciones+", aprobada_operaciones="+aprobada+
                ", usuario_operaciones='"+usuario+"', fecha_operaciones="+(usuario.compareTo("")!=0?"getdate()":"null")+
                ", motivo_rechazo_operacion='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo+";");
    }
    
    public boolean cerrar(String id_plan_mercadeo)
    {
        return this.ejecutar("update tbl_plan_mercadeo set estado=9, fecha_cierre=getdate() where id_plan_mercadeo="+id_plan_mercadeo);
    }
    public boolean habilitar(String id_plan_mercadeo)
    {
        return this.ejecutar("update tbl_plan_mercadeo set estado=6, fecha_cierre=null where id_plan_mercadeo="+id_plan_mercadeo);
    }
    
    public boolean hayAuspiciosSinConfirmarUsuario(String usuario)
    {
        try{
            ResultSet rs = this.consulta("SELECT PP.* FROM tbl_plan_mercadeo as P with (nolock) inner join tbl_plan_mercadeo_proveedor as PP with (nolock) "
                    + "on P.id_plan_mercadeo=PP.id_plan_mercadeo where P.usuario_creacion='"+usuario+"' and PP.plazo_confirmacion<getdate() and PP.confirmado=0 and PP.eliminado=0;");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean hayAuspiciosSinConfirmar(String id_plan_mercadeo)
    {
        try{
            ResultSet rs = this.consulta("SELECT * FROM tbl_plan_mercadeo_proveedor with (nolock) where id_plan_mercadeo='"+id_plan_mercadeo+"' and confirmado=0 and eliminado=0;");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean getSaldoLaboratorio(String id_plan_mercadeo, String idLab, float monto)
    {
        try{
            float saldo =0;
            float monto_asignado = 0;
            ResultSet rs = this.consulta("select saldo from tbl_laboratorio with (nolock) where id_laboratorio="+idLab+";");
            if(rs.next()){
                saldo = rs.getString(1)!=null ? rs.getFloat(1) : 0;
                rs.close();
            }
            ResultSet rs1 = this.consulta("select monto from tbl_plan_mercadeo_laboratorio with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+" and  id_laboratorio="+idLab+";");
            if(rs1.next()){
                monto_asignado = rs1.getString(1)!=null ? rs1.getFloat(1) : 0;
                rs1.close();
            }
            if( (saldo+monto_asignado) >= monto ){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getAuspicios(String id_plan_mercadeo)
    {
        return this.consulta("select id_laboratorio, monto from tbl_plan_mercadeo_laboratorio with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+" order by id_laboratorio;");
    }
    public ResultSet getProveedores(String id_plan_mercadeo)
    {
        return this.consulta("select numero_idproveedor, nombre_comercial, num_formulario, monto, CONVERT(VARCHAR,fecha_registro, 103) as fecha_registro, confirmado, eliminado,"
                + "case when auspicio_manual=1 then 'm' else 'a' end as auspicio_manual "
                + "from (tbl_plan_mercadeo as P with(nolock) inner join tbl_plan_mercadeo_proveedor as PP with (nolock) on PP.id_plan_mercadeo=P.id_plan_mercadeo) "
                + "inner join tbl_tipo_plan as T with(nolock) on T.id_tipo_plan=P.id_tipo_plan "
                + " where id_plan_mercadeo="+id_plan_mercadeo+" order by eliminado, nombre_comercial;");
    }
    public ResultSet getAdjuntos(String id_plan_mercadeo)
    {
        return this.consulta("select * from tbl_plan_mercadeo_adjunto with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+" order by id_plan_mercadeo_adjunto");
    }
    public boolean setConfirmacion(String id_plan_mercadeo, String id_proveedor, String num_formulario, int confirmado)
    {
        String fecha_confirmacion = confirmado==1 ? "getDate()" : "null";
        return this.ejecutar("update tbl_plan_mercadeo_proveedor set confirmado="+confirmado+", fecha_confirmacion="+fecha_confirmacion+" "
            + "where id_plan_mercadeo="+id_plan_mercadeo+" and numero_idproveedor='"+id_proveedor+"' and num_formulario='"+num_formulario+"'");
    }
    public ResultSet getUsuariosTipoPlan(String id_tipo_plan)
    {
        return this.consulta("select * from tbl_tipo_plan_usuario with (nolock) where id_tipo_plan='"+id_tipo_plan+"';");
    }
    public ResultSet getUsuariosConfAusp(String id_plan_mercadeo)
    {
        return this.consulta("select id_tipo_plan_usuario, num_dias from tbl_tipo_plan_usuario_confirmar_auspicio with (nolock) where id_plan_mercadeo="+id_plan_mercadeo+";");
    }
    public ResultSet getVendedores(String id)
    {
        return this.consulta("select * from tbl_plan_mercadeo_vendedor with (nolock) where id_plan_mercadeo="+id+" order by nombre_vendedor");
    }
    public ResultSet getClientes(String id)
    {
        return this.consulta("select * from tbl_plan_mercadeo_cliente with (nolock) where id_plan_mercadeo="+id+" order by nombre_comercial");
    }
    
    public int enMatriz(String[][] matriz, String clave1, String clave2)
    {
        if(matriz!=null){
            for(int i=0; i<matriz.length; i++){
                if(matriz[i][0].compareTo(clave1)==0 && matriz[i][1].compareTo(clave2)==0){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int enMatriz(String[][] matriz, String num_form)
    {
        
        if(matriz!=null){
            for(int i=0; i<matriz.length; i++){
                if(matriz[i][5].compareTo(num_form)==0 && matriz[i][2].compareTo("1")==0){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int enMatriz(String[][] matriz, String num_form, int pos)
    {
        
        if(matriz!=null){
            for(int i=0; i<matriz.length; i++){
                if(matriz[i][pos].compareTo(num_form)==0){
                    return i;
                }
            }
        }
        return -1;
    }
    
    public int enMatriz(String[][] matriz, String num_form, String montop, int pos, int pos1)
    {
        
        if(matriz!=null){
            for(int i=0; i<matriz.length; i++){
                if(matriz[i][pos].compareTo(num_form)==0 && matriz[i][pos1].compareTo(montop)==0){
                    return i;
                }
            }
        }
        return -1;
    }
    
    
    /*  cierres */
    
    public boolean setVentasFarmacia(String idPlanMercadeo, String oficina, String ventas, double auspicio, double subtotal_12, double subtotal_0, double iva, double gasto, double porcentaje_cumplimiento_gasto)
    {
        return this.ejecutar("update tbl_plan_mercadeo_farmacia set p_ventas_reales="+ventas+", auspicio="+auspicio+", subtotal_12="+subtotal_12+
                ", subtotal_0="+subtotal_0+", iva="+iva+", gasto="+gasto+", porcentaje_cumplimiento_gasto="+porcentaje_cumplimiento_gasto+
                " where id_plan_mercadeo="+idPlanMercadeo+" and oficina='"+oficina+"'");
    }
    
    public boolean setVentasReales(String idPlanMercadeo, float ventas, double promedio_p_crecimiento, double porcentaje_cumplimiento, 
            float total_gasto, double porcentaje_cumplimiento_gasto)
    {
        return this.ejecutar("update tbl_plan_mercadeo set ventas_reales="+ventas+", porcentaje_crecimiento="+promedio_p_crecimiento+
                ", porcentaje_cumplimiento="+porcentaje_cumplimiento+", total_gasto="+total_gasto+
                ", porcentaje_cumplimiento_gasto="+porcentaje_cumplimiento_gasto+" where id_plan_mercadeo="+idPlanMercadeo);
    }
 
    
    /*  anulaciones */
    
    public boolean hayAuspiciosNoEstrategicos(String id_plan_mercadeo)
    {
        try{
            ResultSet rs = this.consulta("SELECT * FROM tbl_plan_mercadeo_proveedor with (nolock) where id_plan_mercadeo='"+id_plan_mercadeo+"' and confirmado=0 and eliminado=0;");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean anular(String id_plan_mercadeo, String motivo)
    {
        return this.ejecutar("update tbl_plan_mercadeo set estado=10, fecha_anulacion=getdate(), motivo_anulacion='"+motivo+"' where id_plan_mercadeo="+id_plan_mercadeo);
    }
}
