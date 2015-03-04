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
public class Laboratorio extends BaseDatos {
    public Laboratorio(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }

    public boolean rucDuplicado(String id_laboratorio, String ruc)
    {
        try{
            ResultSet rs = this.consulta("SELECT * FROM tbl_laboratorio with (nolock) where numero_idproveedor='"+ruc+"' and id_laboratorio<>"+id_laboratorio+";");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getLaboratorio(String id)
    {
        return this.consulta("select * from tbl_laboratorio with (nolock) where id_laboratorio="+id+";");
    }
    
    public ResultSet getTiposPlanes(String id)
    {
        return this.consulta("select * from vta_labortorio_tipo_plan_presupuesto where id_laboratorio="+id+" order by nombre;");
    }

    public ResultSet getLaboratoriosAsignables(String id_plan_mercadeo, String id_tipo_plan)
    {
        if(id_plan_mercadeo.compareTo("-1")!=0){
            return this.consulta("select L.id_laboratorio, nombre_comercial, monto, LP.saldo, presupuesto, id_tipo_plan "
                + "from (tbl_laboratorio as L with (nolock) inner join tbl_labortorio_tipo_plan_presupuesto as LP with (nolock) on L.id_laboratorio=LP.id_laboratorio) "
                + "where (L.id_laboratorio in (select id_laboratorio from tbl_plan_mercadeo_laboratorio where id_plan_mercadeo="+id_plan_mercadeo+") "
                + "or (monto>0 and estado=1) ) and id_tipo_plan='"+id_tipo_plan+"' order by nombre_comercial;");
        }
        return this.consulta("select L.id_laboratorio, nombre_comercial, monto, LP.saldo, presupuesto, id_tipo_plan "
                + "from (tbl_laboratorio as L with (nolock) inner join tbl_labortorio_tipo_plan_presupuesto as LP with (nolock) on L.id_laboratorio=LP.id_laboratorio) "
                + "where monto>0 and estado=1 and id_tipo_plan='"+id_tipo_plan+"' order by nombre_comercial;");
    }

    public String insertar(String numero_idproveedor, String nombre_comercial, String usuario, String anio_vigencia, String estado)
    {
        /*String pk = this.insertar("insert into tbl_laboratorio(numero_idproveedor, nombre_comercial, fecha_creacion, usuario_creacion, anio_vigencia, monto, saldo, estado) "
                + "values('"+numero_idproveedor+"', '"+nombre_comercial+"', getDate(), '"+usuario+"', "+anio_vigencia+", "+monto+", "+monto+", "+estado+");");
        if(pk.compareTo("-1")!=0){
            List sql = new ArrayList();
            String vec_id_tipo_planes[] = id_tipo_planes.split(",");
            String vec_presupuestos[] = presupuestos.split(",");
            for(int i=0; i<vec_id_tipo_planes.length; i++){
                sql.add("insert into tbl_labortorio_tipo_plan_presupuesto(id_laboratorio, id_tipo_plan, presupuesto) "
                        + "values("+pk+", '"+vec_id_tipo_planes[i]+"', "+vec_presupuestos[i]+");");
            }
            if( this.transacciones(sql) ){
                return true;
            }else{
                this.ejecutar("delete from tbl_laboratorio id_laboratorio="+pk);
            }
        }
        return false;*/
        return this.insertar("insert into tbl_laboratorio(numero_idproveedor, nombre_comercial, fecha_creacion, usuario_creacion, anio_vigencia, estado) "
                + "values('"+numero_idproveedor+"', '"+nombre_comercial+"', getDate(), '"+usuario+"', "+anio_vigencia+", "+estado+");");
    }

    public boolean actualizar(String id_laboratorio, String numero_idproveedor, String nombre_comercial, String anio_vigencia, String estado)
    {
        /*List sql = new ArrayList();
        sql.add("update tbl_laboratorio set numero_idproveedor='"+numero_idproveedor+"', nombre_comercial='"+nombre_comercial+"', anio_vigencia="+
                anio_vigencia+", monto="+monto+", estado="+estado+" where id_laboratorio="+id_laboratorio+";");

        String vec_id_tipo_planes[] = id_tipo_planes.split(",");
        String vec_presupuestos[] = presupuestos.split(",");
        sql.add("delete from tbl_labortorio_tipo_plan_presupuesto where id_laboratorio="+id_laboratorio+";");
        for(int i=0; i<vec_id_tipo_planes.length; i++){
            sql.add("insert into tbl_labortorio_tipo_plan_presupuesto(id_laboratorio, id_tipo_plan, presupuesto) "
                    + "values("+id_laboratorio+", '"+vec_id_tipo_planes[i]+"', "+vec_presupuestos[i]+");");
        }
        return this.transacciones(sql);*/
        return this.ejecutar("update tbl_laboratorio set numero_idproveedor='"+numero_idproveedor+"', nombre_comercial='"+nombre_comercial+"', anio_vigencia="+
                anio_vigencia+", estado="+estado+" where id_laboratorio="+id_laboratorio+";");
    }
    
    public boolean setPresupuestos(String id_laboratorio, String usuario, String id_tipo_planes, String presupuestos)
    {
        ResultSet rs = this.consulta("select distinct id_laboratorio, id_tipo_plan from tbl_labortorio_tipo_plan_presupuesto with (nolock) "
                + "where id_tipo_plan in ("+id_tipo_planes+") and id_laboratorio="+id_laboratorio);
        String mat[][] = Matriz.ResultSetAMatriz(rs);
        
        List sql = new ArrayList();
        String vec_id_tipo_planes[] = id_tipo_planes.split(",");
        String vec_presupuestos[] = presupuestos.split(",");
        String descripcion = "Registro de nueva asignacion de presupuesto";
        for(int i=0; i<vec_id_tipo_planes.length; i++){
            if(this.enMatriz(mat, id_laboratorio, vec_id_tipo_planes[i])==-1){ 
                sql.add("insert into tbl_labortorio_tipo_plan_presupuesto(id_laboratorio, id_tipo_plan, presupuesto) "
                    + "values("+id_laboratorio+", "+vec_id_tipo_planes[i]+", 0);");
            }
            sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor, es_entrada) "
                    + "values("+id_laboratorio+", "+vec_id_tipo_planes[i]+", '"+usuario+"', getdate(), '"+descripcion+"', "+vec_presupuestos[i]+", 1);");
        }
        return this.transacciones(sql);
    }
    
    public boolean verificaReasignarPresupuestos(String id_laboratorio, String id_tipo_planes, String presupuestos)
    {
        boolean ok = false;
        String vec_id_tipo_planes[] = id_tipo_planes.split(",");
        String vec_presupuestos[] = presupuestos.split(",");
        for(int i=0; i<vec_id_tipo_planes.length; i++){
            ResultSet rs = this.consulta("select L.id_laboratorio, nombre_comercial, monto, saldo, presupuesto, id_tipo_plan "
                + "from (tbl_laboratorio as L with (nolock) inner join tbl_labortorio_tipo_plan_presupuesto as LP with (nolock) on L.id_laboratorio=LP.id_laboratorio) "
                + "where monto < presupuesto + "+vec_presupuestos[i]+";");
        }
        
        return ok;
    }
    public boolean reasignarPresupuestos(String id_laboratorio, String usuario, String id_tipo_planes, String presupuestos)
    {
        String descripcion = "Registro de re-asignacion de presupuesto";
        
        ResultSet rs = this.consulta("select distinct id_laboratorio, id_tipo_plan, presupuesto, saldo from tbl_labortorio_tipo_plan_presupuesto with (nolock) "
                + "where id_tipo_plan in ("+id_tipo_planes+") and id_laboratorio="+id_laboratorio);
        String mat[][] = Matriz.ResultSetAMatriz(rs);
        
        List sql = new ArrayList();
        String vec_id_tipo_planes[] = id_tipo_planes.split(",");
        String vec_presupuestos[] = presupuestos.split(",");
        for(int i=0; i<vec_id_tipo_planes.length; i++){
            int pos = this.enMatriz(mat, id_laboratorio, vec_id_tipo_planes[i]);
            if(pos==-1){ /* si es nueva asignacion al laboratorio */
                sql.add("insert into tbl_labortorio_tipo_plan_presupuesto(id_laboratorio, id_tipo_plan, presupuesto) "
                    + "values("+id_laboratorio+", "+vec_id_tipo_planes[i]+", "+vec_presupuestos[i]+");");
                sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor) "
                    + "values("+id_laboratorio+", "+vec_id_tipo_planes[i]+", '"+usuario+"', getdate(), '"+descripcion+"', "+vec_presupuestos[i]+");");
            }else{
                float asignaciones = 0;
                try{
                    ResultSet rsK = this.consulta("select sum(PML.monto) from tbl_plan_mercadeo_laboratorio as PML with (nolock) inner join tbl_laboratorio as L with (nolock) on PML.id_laboratorio=L.id_laboratorio "
                        + "where PML.id_tipo_plan = "+vec_id_tipo_planes[i]+" and PML.id_laboratorio="+id_laboratorio+" and L.anio_vigencia=datepart(year, getdate());");
                    if(rsK.next()){
                        asignaciones = rsK.getString(1)!=null ? rsK.getFloat(1) : 0;
                        rsK.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                if(asignaciones <= Float.parseFloat(vec_presupuestos[i])){
                    if(Float.parseFloat(mat[pos][2]) != Float.parseFloat(vec_presupuestos[i])){
                        sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor, es_entrada) "
                            + "values("+id_laboratorio+", "+vec_id_tipo_planes[i]+", '"+usuario+"', getdate(), '"+descripcion+"', "+mat[pos][2]+", 0);");
                        sql.add("insert into tbl_labortorio_tipo_plan_presupuesto_kardex(id_laboratorio, id_tipo_plan, usuario, fecha_registro, descripcion, valor) "
                            + "values("+id_laboratorio+", "+vec_id_tipo_planes[i]+", '"+usuario+"', getdate(), '"+descripcion+"', "+vec_presupuestos[i]+");");
                    }
                }else{
                    this.setError("La re-asignación en uno de los tipos de planes de mercadeo es menor a lo gastado por los planes de mercadeo vigentes");
                    return false;
                }
            } 
        }
        return this.transacciones(sql);
    }
    
    public int enMatriz(String[][] matriz, String clave1, String clave2)
    {
        if(matriz!=null){
            for(int i=0; i<matriz.length; i++){
                if(matriz[i][0].compareTo(clave1)==0 && matriz[i][1].compareTo(clave2.replace("'", ""))==0){
                    return i;
                }
            }
        }
        return -1;
    }

}
