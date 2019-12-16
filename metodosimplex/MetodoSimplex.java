/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metodosimplex;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        int X = 1;
        //lleno el array list de la funcion objetiva
        FobjetivaRestriccion funcionObjetiva = new FobjetivaRestriccion(); // Funcion Objetivo
        for (int i = 0; i < variableFO; i++) {
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
        int auxRest = variableFO + 2;
        for (int i = 1; i < restricciones + 1; i++) {
            FobjetivaRestriccion restri = new FobjetivaRestriccion();
            X = 1;
            System.out.println("ingrese 1 para <= : 2 para >=  ; 3 para =");
            for (int j = 0; j < auxRest; j++) {
                if (j < variableFO) {
                    System.out.println(" valor de X" + X);
                    restri.Valorx.add(leer.nextDouble());
                    X++;
                }
                if (j == variableFO) {
                    System.out.println("igualdad");
                    igualdad = leer.nextInt();
                    restri.condicion = igualdad;
                }
                if (j == variableFO + 1) {
                    System.out.println("termino idependiente");
                    terminoI = leer.nextDouble();
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

        //presntar la tabla
        presentarTabla();
        pivoteo();
        //resolverSimplex();

    }

    public static void FuncionOR() {
        //presenta la funcion objetiva
        int X;
        String aux = "";
        X = 1;
        System.out.println("\n la funcion objetiva es:");
        for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) {
            aux += "(" + valoresOR.get(0).Valorx.get(i) + ")" + " X" + X + " +";
            X = X + 1;
        }
        System.out.println(aux);

        System.out.println("\n las restricciones son:");

        //presentar las restricciones
        for (int i = 1; i < valoresOR.size(); i++) {
            aux = "";
            X = 1;
            for (int j = 0; j < valoresOR.get(i).Valorx.size(); j++) {
                aux += "(" + valoresOR.get(i).Valorx.get(j) + ")" + " X" + X + " +";
                X++;
            }
            if (valoresOR.get(i).condicion == 1) {
                aux += "<= ";
            }
            if (valoresOR.get(i).condicion == 2) {
                aux += ">= ";
            }
            if (valoresOR.get(i).condicion == 3) {
                aux += "= ";
            }
            aux += valoresOR.get(i).TerminoInde;
            System.out.println(aux);
        }
        // System.out.println("\n");
    }

    public static void estandarizarFunciones(int opcionMN) {
        if (opcionMN == 0) {
            //cambia la función objetiva toda por -1
            for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) {
                valoresOR.get(0).Valorx.set(i, valoresOR.get(0).Valorx.get(i) * -1);
            }
            //las restricciones cambian cambia los signos <= >= a = y 
            //agregando las variables auxiliares
            //se inicia en 1 xq la función Objetiva tiene el indice 0
            for (int i = 1; i < valoresOR.size(); i++) {
                if (valoresOR.get(i).condicion == 1) {
                    valoresOR.get(i).condicion = 3; //cambia el <= a =
                    valoresOR.get(i).Valorx.add(1d); //agrego la nueva variable aux en 1 ejm X4
                    valoresOR.get(i).pivote = valoresOR.get(i).Valorx.size() - 1; //elijo como pivote la nueva variable agregada
                    //for para agregar las otras variables auxiliares en 0
                    for (int j = 0; j < valoresOR.size(); j++) {
                        if (j != i) { // que sea diferente al valor ya agregado
                            valoresOR.get(j).Valorx.add(0d); // se añade el 0 en las otras variables aux
                        }
                    }

                } else if (valoresOR.get(i).condicion == 2) {
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
        }
        if (opcionMN == 1) 
        {
            //las restricciones cambian cambia los signos <= >= a = y 
            //agregando las variables auxiliares
            //se inicia en 1 xq la función Objetiva tiene el index 0
            for (int i = 1; i < valoresOR.size(); i++) {
                if (valoresOR.get(i).condicion == 1) {
                    valoresOR.get(i).condicion = 3; //cambia el <= a =
                    valoresOR.get(i).Valorx.add(1d); //agrego la nueva variable aux en 1 ejm X4
                    valoresOR.get(i).pivote = valoresOR.get(i).Valorx.size() - 1; //elijo como pivote la nueva variable agregada
                    //for para agregar las otras variables auxiliares en 0
                    for (int j = 0; j < valoresOR.size(); j++) {
                        if (j != i) { // que sea diferente al valor ya agregado
                            valoresOR.get(j).Valorx.add(0d); // se añade el 0 en las otras variables aux
                        }
                    }

                } else if (valoresOR.get(i).condicion == 2) {
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

            System.out.println("la función minimizada es:");
            FuncionOR();
        }
    }

    public static void presentarTabla() {
        System.out.println("\n tabla de iteraciones");
        String tablaP = "   ";
        int X = 1;
        //presento la primera columna de x1, x2..... xn
        for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) 
        {
            tablaP += "\tX" + X;
            X++;
        }
        tablaP += "\tIdepe";
        System.out.println(tablaP);
        //presento las restricciones
        for (int i = 1; i < valoresOR.size(); i++) 
        {
            tablaP = "X" + (valoresOR.get(i).pivote + 1);
            for (int j = 0; j < valoresOR.get(i).Valorx.size(); j++) 
            {
                tablaP += "\t" + valoresOR.get(i).Valorx.get(j);
            }
            tablaP += "\t" + valoresOR.get(i).TerminoInde;
            System.out.println(tablaP);
        }
        //presento la funcion objetiva
        tablaP = "Z";
        for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) 
        {
            tablaP += "\t" + valoresOR.get(0).Valorx.get(i);
        }
        tablaP += "\t" + valoresOR.get(0).TerminoInde;
        System.out.println(tablaP);
    }

    public static void resolverSimplex() {
        boolean valorVerificar = false;
        //verifico que la ecuacion objetiva no hayan valores -0
        for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) 
        {
            //si es menor a 0 el boolean es true para seguir resolviendo
            if (valoresOR.get(0).Valorx.get(i) < 0) 
            {
                valorVerificar = true;
            }
            //si es false el programa acaba
            if (valoresOR.get(0).Valorx.get(i) > 0) 
            {
                valorVerificar = false;
            }
        }
        while (valorVerificar) 
        {

            resolverSimplex();
        }

    }

    public static void pivoteo() 
    {
        double comparacion = 0d;
        int indice = 0;
        //obtener el numero negativo de la funcion objetiva
        for (int i = 0; i < valoresOR.get(0).Valorx.size(); i++) 
        {
            if (valoresOR.get(0).Valorx.get(i) < comparacion) 
            {
                comparacion = valoresOR.get(0).Valorx.get(i); //se guarda el valor es decir 3X3 se toma solo 3
                indice = i; //otenemos el indice para saber en que posicion
            }
        }

        double razon = 0d;
        int auxIndice = 0;
        double auxComparacion=100;
        //voy a recorrerar las restricciones con el fin de obtener el valor de la razon
        //y el indice donde se ubica
        for (int j = 1; j < valoresOR.size(); j++) 
        { //indice es el valor que se obtuvo en el for de arriba
            if (valoresOR.get(j).Valorx.get(indice) > 0) 
            { // solo si es positivo
                razon = valoresOR.get(j).TerminoInde / valoresOR.get(j).Valorx.get(indice);
                System.out.println("razón ecuación ("+"E" + j + ") es: " + razon);
                if (razon < auxComparacion) 
                {
                    auxComparacion = razon;
                    auxIndice = j;
                }
                //System.out.println("valor auxIndice  "+auxIndice);
            }
        }
        //si la razon es diferente de cero es xq se encontró al menos un positivo
        //en esa X 
        if (razon != 0d) 
        {
            valoresOR.get(auxIndice).pivote = indice;
            System.out.println("Pivote esta en: X" + (indice + 1)+ "  en la ecuación E:(" + auxIndice + ")");
        }
        
        //cuando existe dos -3 y en el primero que se encontro todos
        //son negativos entoncs va tomar el segundo -3
        if (razon == 0d) 
        {          
            double comparacion2 = 0d;
            int indice2 = 0;
            double razon2 = 0d;
            int auxIndice2 = 0;
            double auxComparacion2 = 100;
            int auxindiceF = indice +1;
            for (int i = auxindiceF; i < valoresOR.get(0).Valorx.size(); i++) 
            {
                //compara si existe otro valor igual al anterior
                if (Objects.equals(valoresOR.get(0).Valorx.get(i), valoresOR.get(0).Valorx.get(indice)) ) 
                {
                    indice2 = i;
            
                    comparacion2 = valoresOR.get(0).Valorx.get(i); //se guarda el valor es decir 3X3 se toma solo 3
                    indice2 = i; //otenemos el indice para saber en que posicion
                    //voy a recorrerar las restricciones con el fin de obtener el valor de la razon
                    //y el indice donde se ubica
                }
            }

                for (int j = 1; j < valoresOR.size(); j++) 
                { //indice es el valor que se obtuvo en el for de arriba
                    if (valoresOR.get(j).Valorx.get(indice2) > 0) { // solo si es positivo
                        razon2 = valoresOR.get(j).TerminoInde / valoresOR.get(j).Valorx.get(indice2);
                        System.out.println("razón ecuación (" + "E" + j + ") es: " + razon2);
                        if (razon2 < auxComparacion2) {
                            auxComparacion2 = razon2;
                            auxIndice2 = j;
                        }
                        //System.out.println("valor auxIndice  "+auxIndice);
                    }
                }
                valoresOR.get(auxIndice2).pivote = indice2;
                System.out.println("Pivote esta en: X" + (indice2 + 1) + "  en la ecuación E:(" + auxIndice2 + ")");
            
        }
    }
}
