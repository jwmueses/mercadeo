/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.far.lib;

/**
 *
 * @author Jorge
 */
public class Numero {
    public static double redondear(double valor)
    {
        return (Math.round(valor * Math.pow(10, 2)) / Math.pow(10, 2));
    }
    public static double redondear(double valor, int decimales)
    {
        return (Math.round(valor * Math.pow(10, decimales)) / Math.pow(10, decimales));
    }
}
