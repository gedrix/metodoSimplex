/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodosimplex;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Gedo
 */
public class MetodoSimplex {

    /**
     * @param args the command line arguments
     */
    //array list para la funcion objetiva y restricciones
    public static ArrayList<FobjetivaRestriccion> valoresOR = new ArrayList<>();
    public static void main(String[] args) {

        Scanner leer = new Scanner(System.in);
        System.out.println("\nIntroduce el numero de variables para la funcion objetivo");
        int variableFO;
        variableFO = leer.nextInt();
        int X =1;
        //lleno el array list de la funcion objetiva
        FobjetivaRestriccion funcionObjetiva = new FobjetivaRestriccion(); // Funcion Objetivo
        for (int i = 0; i < variableFO; i++) 
        {
            System.out.println(" valor de X" + X);
            funcionObjetiva.Valorx.add(leer.nextDouble());
            X++;
        }
        valoresOR.add(funcionObjetiva);
        
        //lleno el array list de las restricciones
        System.out.println("\n ingrese el numero de restricciones:");
        int restricciones;
        int igualdad;
        double terminoI;
        restricciones = leer.nextInt();
        int auxRest = variableFO+2;
        for (int i = 1; i < restricciones+1; i++) 
        {
            FobjetivaRestriccion restri = new FobjetivaRestriccion();
            X =1;
            System.out.println("ingrese 1 para <= : 2 para >=  ; 3 para =");
            for (int j = 0; j < auxRest; j++) 
            {
                if (j <variableFO) 
                {
                    System.out.println(" valor de X" + X);
                    restri.Valorx.add(leer.nextDouble());
                    X++; 
                }
                if (j == variableFO) 
                {
                    System.out.println("igualdad");
                    igualdad =leer.nextInt();
                    restri.condicion = igualdad;
                }
                if (j == variableFO+1) 
                {
                    System.out.println("termino idependiente");
                    terminoI =leer.nextDouble();
                    restri.TerminoInde = terminoI;
                }
            }
            valoresOR.add(restri);
        }        
        
        //presentar la función objetivo y restricciones iniciales
       //FuncionOR();

       System.out.println("\nDesea MAXIMIZAR elija 0 ;o MINIMIZAR elija 1");
       int opcionMN;
       opcionMN = leer.nextInt();
       estandarizarFunciones(opcionMN);
    }
    public static void FuncionOR()
    {
        //presenta la funcion objetiva
        int X;
         String aux = "";
        X=1;
        System.out.println("\n la funcion objetiva es:");
        for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) 
        {
            aux += "("+valoresOR.get(0).Valorx.get(i)+")"+" X" +X +" +";
            X = X+1;
        }
        System.out.println(aux);
         
        System.out.println("\n las restricciones son:");
        
        //presentar las restricciones
        
        for (int i = 1; i < valoresOR.size(); i++) 
        {
            aux = "";
            X=1;
            for (int j = 0; j < valoresOR.get(i).Valorx.size(); j++) 
            {
               aux += "("+valoresOR.get(i).Valorx.get(j)+")"+" X" +X +" +";
                X++;
            }
            if (valoresOR.get(i).condicion == 1 ) 
            {
                aux += "<= " ;
            }
            if (valoresOR.get(i).condicion == 2) 
            {
                aux += ">= " ;
            }
            if(valoresOR.get(i).condicion == 3)
            {
                aux += "= " ;
            }
            aux+= valoresOR.get(i).TerminoInde;
            System.out.println(aux);
        }
       // System.out.println("\n");
    } 
   public static void estandarizarFunciones(int opcionMN)
   {
       if(opcionMN ==0)
       {
           //cambia la función objetiva toda por -1
           for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) 
            {
                valoresOR.get(0).Valorx.set(i, valoresOR.get(0).Valorx.get(i) * -1);
            }
           //las restricciones cambian cambia los signos <= >= a = y 
           //agregando las variables auxiliares
           //se inicia en 1 xq la función Objetiva tiene el index 0
           for (int i = 1; i < valoresOR.size(); i++) 
           {
               if (valoresOR.get(i).condicion == 1) 
               {
                 valoresOR.get(i).condicion = 3; //cambia el <= a =
                 valoresOR.get(i).Valorx.add(1d); //agrego la nueva variable aux en 1 ejm X4
                 valoresOR.get(i).pivote =valoresOR.get(i).Valorx.size()-1; //elijo como pivote la nueva variable agregada
                   //for para agregar las otras variables auxiliares en 0
                   for (int j = 0; j < valoresOR.size(); j++) 
                   {
                       if (j != i) 
                        { // que sea diferente al valor ya agregado
                            valoresOR.get(j).Valorx.add(0d); // se añade el 0 en las otras variables aux
                        }
                   }
        
               }else if (valoresOR.get(i).condicion == 2) 
                    {
                        valoresOR.get(i).condicion = 3; //cambia el >= a =
                        valoresOR.get(i).Valorx.add(-1d); //agrego la nueva variable aux en 1 ejm X4
                        valoresOR.get(i).pivote = valoresOR.get(i).Valorx.size() - 1; //elijo como pivote la nueva variable agregada
                        //for para agregar las otras variables auxiliares en 0
                        for (int j = 0; j < valoresOR.size(); j++) {
                            if (j != i) { // que sea diferente al valor ya agregado
                                valoresOR.get(j).Valorx.add(0d); // se añade el 0 en las otras variables aux
                            }
                        }    
                    }
           }
        System.out.println("la función maximida es:");
        FuncionOR();  
       }if(opcionMN ==1)
       {
           System.out.println("la función minimizada es:");
           FuncionOR();
       }
   }
         
}
