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
public class Estrategia extends BaseDatos {
    public Estrategia(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }
    public boolean hayEstrategia(String id_estrategia, String id_plan_mercadeo, String estrategia)
    {
        try{
            ResultSet rs = this.consulta("SELECT * FROM tbl_estrategia with (nolock) "
                    + "where estrategia='"+estrategia+"' and id_plan_mercadeo="+id_plan_mercadeo+" and id_estrategia<>"+id_estrategia+";");
            if(this.getFilas(rs)>0){
                return true;
            }
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getEstrategia(String id_estrategia)
    {
        return this.consulta("select * from tbl_estrategia with (nolock) where id_estrategia="+id_estrategia+" order by estrategia");
    }
    public ResultSet getEstrategias(String id_plan_mercadeo)
    {
        return this.consulta("select E.id_estrategia, estrategia, count(id_actividad) as num_actividades, sum(pre_total) as pre_totales "
                + "from tbl_estrategia as E with (nolock) left outer join tbl_actividad as A with (nolock) on E.id_estrategia=A.id_estrategia "
                + "where id_plan_mercadeo="+id_plan_mercadeo+" group by E.id_estrategia, estrategia order by estrategia");
    }
    
    public String ingresar(String id_plan_mercadeo, String estrategia, String concepto, String tactica)
    {
        return this.insertar("insert into tbl_estrategia(id_plan_mercadeo, estrategia, concepto, tactica) "
                + "values("+id_plan_mercadeo+", '"+estrategia+"', '"+concepto+"', '"+tactica+"');");
    }
    public boolean actualizar(String id_estrategia, String estrategia, String concepto, String tactica)
    {
        return this.ejecutar("update tbl_estrategia set estrategia='"+estrategia+"', concepto='"+concepto+
                "', tactica='"+tactica+"' where id_estrategia="+id_estrategia+";");
    }
    public boolean eliminar(String id_estrategia)
    {
        List sql = new ArrayList();
        sql.add("delete from tbl_actividad_cronograma where id_actividad in (select id_actividad from tbl_actividad where id_estrategia="+id_estrategia+");");
        //sql.add("delete from tbl_actividad_adjunto where id_actividad in (select id_actividad from tbl_actividad where id_estrategia="+id_estrategia+");");
        sql.add("delete from tbl_actividad where id_estrategia="+id_estrategia);
        sql.add("delete from tbl_estrategia where id_estrategia="+id_estrategia);
        return this.transacciones(sql);
    }
    
}
