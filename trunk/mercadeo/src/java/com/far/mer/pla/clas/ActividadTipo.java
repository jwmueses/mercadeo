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
public class ActividadTipo extends BaseDatos{
    public ActividadTipo(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }
    
    public ResultSet getTipoPlan(String id)
    {
        return this.consulta("select * from tbl_actividad_tipo with (nolock) where id_actividad_tipo="+id);
    }
    
    public ResultSet getTiposPlanes()
    {
        return this.consulta("select id_actividad_tipo, concepto, monto from tbl_actividad_tipo with (nolock) order by concepto");
    }
    
    public boolean actualizar(String id, String monto)
    {
        return this.ejecutar("update tbl_actividad_tipo set monto="+monto+" where id_actividad_tipo="+id);
    }
    
    public boolean setConceptos(ResultSet rsConcepto)
    {
        try{
            if(rsConcepto!=null){
                ResultSet rsTipos = this.consulta("select id_db_externa from tbl_actividad_tipo with (nolock) order by id_db_externa");
                String matTipos[][] = Matriz.ResultSetAMatriz(rsTipos);
                List sql = new ArrayList();
                String id_db_externa = "";
                String concepto = "";
                while(rsConcepto.next()){
                    id_db_externa = rsConcepto.getString(1)!=null ? rsConcepto.getString(1) : "";
                    concepto = rsConcepto.getString(2)!=null ? rsConcepto.getString(2) : "";
                    if(Matriz.enMatriz(matTipos, concepto, 0)>=0){
                        sql.add("update tbl_actividad_tipo set concepto="+concepto+", revisada=1 where id_db_externa='"+id_db_externa+";");
                    }else{
                        sql.add("insert into tbl_actividad_tipo(id_db_externa, concepto, revisada) values('"+id_db_externa+"', '"+concepto+"', 1);");
                    }
                }
                sql.add("delete from tbl_actividad_tipo where revisada=0;");
                sql.add("update tbl_actividad_tipo set revisada=0;");
                return this.transacciones(sql);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    
}
