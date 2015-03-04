/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.lib;

/**
 *
 * @author Jorge
 */
public class Robot extends BaseDatos{
    public Robot(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }

    public void ejecutar()
    {
        this.consulta("update tbl_plan_mercadeo set estado=9 where (select DATEADD(day, dias_prolonga, tbl_plan_mercadeo.fecha_terminacion) "
        + "from tbl_tipo_plan where id_tipo_plan=tbl_plan_mercadeo.id_tipo_plan) < getdate() and plan_completo=1 and estado=6 and abierto=1");
        /*this.consulta("update tbl_plan_mercadeo set estado=9 where (select tbl_plan_mercadeo.fecha_terminacion "
        + "from tbl_tipo_plan where id_tipo_plan=tbl_plan_mercadeo.id_tipo_plan) < getdate() and plan_completo=1 and estado=6 and abierto=1");*/
    }
}
