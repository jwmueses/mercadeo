/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.far.mer.pla.clas;

import com.far.lib.BaseDatos;
import java.sql.ResultSet;

/**
 *
 * @author Jorge
 */
public class Concepto extends BaseDatos{
    public Concepto(String m, int p, String db, String u, String c){
        super(m, p, db, u, c);
    }
    
    public ResultSet getConceptos()
    {
        return this.consulta("select id_concepto, concepto from tbl_conceptos with (nolock)");
    }
 
}
