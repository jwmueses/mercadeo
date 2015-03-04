/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.mer.pla.clas;

import com.far.lib.BaseDatos;
import java.sql.ResultSet;

/**
 *
 * @author Jorge
 */
public class CalcularVentas {
    private BaseDatos objEstadistica = null;
    private BaseDatos objEasyGestion = null;
    private String codigos = "";
    
    public CalcularVentas()
    {}
    
    public CalcularVentas(BaseDatos objEsta, BaseDatos objEasy)
    {
        this.objEstadistica = objEsta;
        this.objEasyGestion = objEasy;
    }
    
    public ResultSet calcular(String fecha_ini, String fecha_fin, String cod_niveles, String clases, String lineas, String articulos, String oficinas)
    {
        String where = "where convert(datetime, (CAST(DIA AS varchar) + '/' + CAST(MES AS varchar) + '/' + CAST(ANIO AS varchar))) between '"+fecha_ini+"' and '"+fecha_fin+"'";
        if(cod_niveles.compareTo("") != 0){
            String vecNivles[] = cod_niveles.replace("'", "").split(",");
            String vecClases[] = clases.replace("'", "").split(",");
            String vecLineas[] = lineas.replace("'", "").split(",");
            String ax_cod_niveles = "";
            String ax_where = "";
            for(int i=0; i<vecNivles.length; i++){
                ax_where += "(";
                if(vecNivles[i].compareTo("")!=0 && vecNivles[i].compareTo("-1")!=0){
                    this.codigos = "";
                    ax_cod_niveles = this.getNiveles(vecNivles[i]);
                    ax_where += "COD_SEGMENTO in ("+ax_cod_niveles.substring(0, ax_cod_niveles.length()-2)+")";
                }
                if(vecClases[i].compareTo("") != 0 && vecClases[i].compareTo("0") != 0){
                    ax_where += (this.codigos.compareTo("")!=0 ? " or " : "") + "(COD_CLASE in ("+clases+")";
                    if(vecLineas[i].compareTo("") != 0 && vecLineas[i].compareTo("0") != 0){
                        ax_where += " and COD_LINEA in ("+lineas+")";
                    }
                    ax_where += ")";
                }
                if(ax_where.compareTo("(") == 0){
                    ax_where = "";
                }else{
                    ax_where += ") or ";
                }
            }
            if(ax_where.compareTo("")!=0){
                where += " and (" + ax_where.substring(0, ax_where.length()-4) + ")";
            }
        }
        
        if(articulos.compareTo("") != 0 && articulos.compareTo("'0'") != 0 && cod_niveles.compareTo("") == 0){
            where += " and COD_ARTICULO in ("+articulos+")";
        }
        if(oficinas.compareTo("") != 0 && oficinas.compareTo("'0'") != 0){
            where += " and BODEGA in ("+oficinas+")";
        }
        return this.objEstadistica.consulta("select BODEGA, cast(sum(TOTAL - VALOR_IVA - VALOR_DSC_FACTURA) as numeric(19, 2)) as subtotal "
                + "from TBL_VENTAS_DIARIAS_ARTICULO with (nolock) "
                + where
                + " group by BODEGA order by BODEGA");
        
    }
    
    public String getNiveles(String cod_nivel)
    {
        try{
            ResultSet rs = this.objEasyGestion.consulta("select cod_nivel from tbl_nivel with (nolock) where cod_Padre="+cod_nivel+" order by cod_nivel");
            int filas = this.objEasyGestion.getFilas(rs);
            if(filas<=0){
                this.codigos += cod_nivel + ", ";
            }else{
                String nivel;
                while(rs.next()){
                    nivel = rs.getString("cod_nivel")!=null ? rs.getString("cod_nivel") : "-1";
                    this.getNiveles(nivel);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return this.codigos;
    }
    
}
