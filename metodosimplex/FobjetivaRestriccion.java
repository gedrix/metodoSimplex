/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodosimplex;

import java.util.ArrayList;

/**
 *
 * @author Gedo
 */
public class FobjetivaRestriccion {
    public ArrayList<Double> Valorx = new ArrayList(); 
    public int condicion = 0; //<= >= ==
    public Double TerminoInde = 0d; //valor despues del igual
    public int pivote = 0; 
}
