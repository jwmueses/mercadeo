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
public class TipoPlan extends BaseDatos {
    public TipoPlan(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }

    public boolean idDuplicado(String id)
    {
        try{
            ResultSet rs = this.consulta("SELECT * FROM tbl_tipo_plan with (nolock) where id_tipo_plan='"+id+"';");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getTipoPlan(String id)
    {
        return this.consulta("select * from tbl_tipo_plan with (nolock) where id_tipo_plan='"+id+"'");
    }
    
    public String getIdTipoPlan(String nombre)
    {
        String res = "";
        try{
            ResultSet rs = this.consulta("select * from tbl_tipo_plan where lower(nombre) = '"+nombre.toLowerCase()+"'");
            if(rs.next()){
                res = rs.getString("id_tipo_plan")!=null ? rs.getString("id_tipo_plan") : "";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }
    
    public String getNombreTipoPlan(String id)
    {
        String res = "";
        try{
            ResultSet rs = this.consulta("select descripcion from tbl_tipo_plan_cuenta with (nolock) where id_tipo_plan_cuenta="+id);
            if(rs.next()){
                res = rs.getString("descripcion")!=null ? rs.getString("descripcion") : "";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public ResultSet getTipoPlanes()
    {
        return this.consulta("select id_tipo_plan, nombre from tbl_tipo_plan with (nolock) order by nombre");
    }
    
    public ResultSet getTipoPlanes(String usuario)
    {
        return this.consulta("select id_tipo_plan, nombre from tbl_tipo_plan with (nolock) where id_tipo_plan in (select id_tipo_plan from tbl_tipo_plan_usuario with (nolock) where alias='"+usuario+"') order by nombre");
    }
    
    public ResultSet getTiposPlanes(String id_tipo_plan, String usuario)
    {
        return this.consulta("select distinct TP.id_tipo_plan, TP.nombre from tbl_tipo_plan as TP with (nolock) inner join tbl_tipo_plan_usuario as TPU with (nolock) "
                + "on TPU.id_tipo_plan=TP.id_tipo_plan where (TP.id_tipo_plan='"+id_tipo_plan+"' or TP.estado=1) and TPU.alias='"+usuario+"' order by TP.nombre");
    }

    public ResultSet getTipoPlanCuentas(String id)
    {
        return this.consulta("select * from tbl_tipo_plan_cuenta with (nolock) where id_tipo_plan='"+id+"'");
    }
    
    public ResultSet getTipoPlanCuentasActividad(String id)
    {
        return this.consulta("select id_tipo_plan_cuenta, descripcion from tbl_tipo_plan_cuenta with (nolock) where id_tipo_plan='"+id+"'");
    }

    public ResultSet getUsuarios(String id)
    {
        return this.consulta("select * from tbl_tipo_plan_usuario with (nolock) where id_tipo_plan='"+id+"' order by usuario");
    }

    public ResultSet getTipoPlanSucursales(String id)
    {
        return this.consulta("select codigo, nombre from tbl_tipo_plan_sucursal with (nolock) where id_tipo_plan='"+id+"' order by codigo");
    }
    
    public ResultSet getTipoPlanUsuarios(String id)
    {
        return this.consulta("select * from tbl_tipo_plan_usuario with (nolock) where id_tipo_plan='"+id+"' order by alias");
    }

    public ResultSet getSucursales(String usuario)
    {
        return this.consulta("select distinct TPS.codigo, TPS.nombre from tbl_tipo_plan_sucursal as TPS with (nolock) inner join "
                + "tbl_tipo_plan_usuario as TPU with (nolock) on TPU.id_tipo_plan=TPS.id_tipo_plan "
                + "where TPU.alias='"+usuario+"' order by TPS.nombre");
    }
    
    public boolean setAsignacion(String id_tipo_plan, String alias, String usuarios)
    {
        List sql = new ArrayList();
        sql.add("delete from tbl_tipo_plan_usuario where id_tipo_plan='"+id_tipo_plan+"';");
        String vec_alias[] = alias.split(",");
        String vec_usuarios[] = usuarios.split(",");
        for(int i=0; i<vec_alias.length; i++){
            sql.add("insert into tbl_tipo_plan_usuario(id_tipo_plan, alias, usuario) values('"+id_tipo_plan+"', '"+vec_alias[i]+"', '"+vec_usuarios[i]+"');");
        }
        return this.transacciones(sql);
    }
    
    public boolean insertar(String id_tipo_plan, String usuario, String cedula, String nombre, String coordinador, String centro_costos_coordinador, String mail_responsable,
            String p_incremento, String dias_prolonga, String auspicio_manual, String estado, String cuentas, String descripciones, String codigos, String sucursales)
    {
        List sql = new ArrayList();

        sql.add("insert into tbl_tipo_plan(id_tipo_plan,fecha_creacion,usuario_creacion,nombre,cedula,coordinador,centro_costos_coordinador,mail_responsable, p_incremento, dias_prolonga, auspicio_manual, estado) "
                + "values('"+id_tipo_plan+"', getDate(), '"+usuario+"', '"+nombre+"', '"+cedula+"', '"+coordinador+"', '"+centro_costos_coordinador+"', '"+mail_responsable+"', "+p_incremento+", "+dias_prolonga+", "+auspicio_manual+", "+estado+");");

        String vec_cuentas[] = cuentas.split(",");
        String vec_descripciones[] = descripciones.split(",");
        for(int i=0; i<vec_cuentas.length; i++){
            sql.add("insert into tbl_tipo_plan_cuenta(id_tipo_plan,cuenta,descripcion) "
                    + "values('"+id_tipo_plan+"', '"+vec_cuentas[i]+"', '"+vec_descripciones[i]+"');");
        }
        String vec_codigos[] = codigos.split(",");
        String vec_sucursales[] = sucursales.split(",");
        for(int i=0; i<vec_codigos.length; i++){
            sql.add("insert into tbl_tipo_plan_sucursal(id_tipo_plan,codigo,nombre) "
                    + "values('"+id_tipo_plan+"', '"+vec_codigos[i]+"', '"+vec_sucursales[i]+"');");
        }
        return this.transacciones(sql);
    }

    public boolean actualizar(String id_tipo_plan, String usuario, String cedula, String nombre, String coordinador, String centro_costos_coordinador, String mail_responsable, 
            String p_incremento, String dias_prolonga, String auspicio_manual, String estado, String cuentas, String descripciones, String codigos, String sucursales)
    {
        List sql = new ArrayList();

        sql.add("update tbl_tipo_plan set id_tipo_plan='"+id_tipo_plan+"', nombre='"+nombre+"', cedula='"+cedula+"', coordinador='"+coordinador+
                "', centro_costos_coordinador='"+centro_costos_coordinador+"', mail_responsable='"+mail_responsable+"', p_incremento="+p_incremento+", dias_prolonga="+dias_prolonga+", auspicio_manual="+
                auspicio_manual+", estado="+estado+" where id_tipo_plan='"+id_tipo_plan+"';");
        
        String vec_cuentas[] = cuentas.split(",");
        String vec_descripciones[] = descripciones.split(",");
        
        ResultSet rs = this.consulta("select distinct id_tipo_plan, cuenta from tbl_tipo_plan_cuenta "
                + "where cuenta in ("+cuentas+") and id_tipo_plan_cuenta in(select distinct pre_id_tipo_plan_cuenta from tbl_actividad) order by id_tipo_plan");
        String mat[][] = Matriz.ResultSetAMatriz(rs);
        
        sql.add("delete from tbl_tipo_plan_cuenta where id_tipo_plan='"+id_tipo_plan+"' and id_tipo_plan_cuenta not in(select distinct pre_id_tipo_plan_cuenta from tbl_actividad);");
        for(int i=0; i<vec_cuentas.length; i++){
            if(this.enMatriz(mat, id_tipo_plan, vec_cuentas[i])==-1){
                sql.add("insert into tbl_tipo_plan_cuenta(id_tipo_plan,cuenta,descripcion) "
                    + "values('"+id_tipo_plan+"', '"+vec_cuentas[i]+"', '"+vec_descripciones[i]+"');");
            }
        }
        String vec_codigos[] = codigos.split(",");
        String vec_sucursales[] = sucursales.split(",");
        sql.add("delete from tbl_tipo_plan_sucursal where id_tipo_plan='"+id_tipo_plan+"';");
        for(int i=0; i<vec_codigos.length; i++){
            sql.add("insert into tbl_tipo_plan_sucursal(id_tipo_plan,codigo,nombre) "
                    + "values('"+id_tipo_plan+"', '"+vec_codigos[i]+"', '"+vec_sucursales[i]+"');");
        }
        return this.transacciones(sql);
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

}
