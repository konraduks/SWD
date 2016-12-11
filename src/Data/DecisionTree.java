/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author KonradD
 */
public class DecisionTree {

    public DecisionTree() {

    }

    public void generateTree(double[][] data, String[] decClass) {
        data = transpose(data);
        //zamiana klasy decyzyjnej na I(P);
        entropia(decClass);
        for (int i = 0; i < data.length; i++) {
            //System.out.println(Arrays.toString(data[i]));
            entropia(data[i]);
        }
    }

    public void generateTree(String[][] data, String[] decClass) {
        data = transpose(data);
        //zamiana klasy decyzyjnej na I(P);
        entropia(decClass);
        for (int i = 0; i < data.length; i++) {
            //System.out.println(Arrays.toString(data[i]));
            entropiaCol(data[i], decClass);
        }
    }

    /**
     * Wyliczanie entropii poczatkowej
     *
     * @param decClass
     */
    public void entropia(String[] decClass) {
        ArrayList<String> poss = new ArrayList<>();
        ArrayList<Integer> value = new ArrayList<>();
        for (String str : decClass) {
            if (!poss.contains(str)) {
                poss.add(str);
                value.add(1);
            } else {
                value.set(poss.indexOf(str), value.get(poss.indexOf(str)) + 1);
            }
        }
        int size = decClass.length;
        double res = 0;
        for (int val : value) {
            //System.out.println(val + " " + size + " " + (val * 1.0 /size) + " " +(val/size)*log(val/size));
            //https://en.wikipedia.org/wiki/Logarithm#Change_of_base
            res += (val * 1.0 / size) * (log10(val * 1.0 / size) / log10(2));
            //System.out.println(" " + (val * 1.0 /size)*(log10(val * 1.0 /size)/log10(2)));
        }
        System.out.println("I(P): " + (-res));
    }

    /**
     * Wyliczanie entropii dla poszczegolnych wartosci w poszczegolnych klasach
     *
     * @param decClass
     */
    public void entropiaCol(String[] col, String[] decClass) {
        ArrayList<String> posCol = new ArrayList<>();
        ArrayList<Integer> valueCol = new ArrayList<>();
        ArrayList<String> posDecClass = new ArrayList<>();
        for (String str : col) {
            if (!posCol.contains(str)) {
                posCol.add(str);
                valueCol.add(1);
            } else {
                valueCol.set(posCol.indexOf(str), valueCol.get(posCol.indexOf(str)) + 1);
            }
        }
        //mozliwe rozwiazania klasy decyzyjnej
        for (String str : decClass) {
            if (!posDecClass.contains(str)) {
                posDecClass.add(str);
            }
        }
        //System.out.println(poss.toString());        
        //wyliczanie entropii dla poszczegolnych wartosci z powiazaniem decClass
        ArrayList<Double> resP = new ArrayList<>();
        for (String strCol : posCol) {
            ArrayList<String> posCol2 = new ArrayList<>();
            ArrayList<Integer> valueCol2 = new ArrayList<>();
            int length = 0;
            double res = 0;
            for (int i = 0; i < col.length; i++) {
                if (strCol.equals(col[i])) {
                    length++;
                    if (!posCol2.contains(strCol + decClass[i])) {
                        posCol2.add(strCol + decClass[i]);
                        valueCol2.add(1);
                    } else {
                        valueCol2.set(posCol2.indexOf(strCol + decClass[i]), valueCol2.get(posCol2.indexOf(strCol + decClass[i])) + 1);
                    }
                }
            }
            for(int val : valueCol2){
                res += -(val * 1.0 / length) * (log10(val * 1.0 / length) / log10(2));
                //System.out.println(val + " " + length);
            }
            resP.add(res);
            //System.out.println(strCol + ": " + res);
            //System.out.println(" " + posCol2.toString() + " " + valueCol2.toString());            
        }
        double result = 0;
        for (int i = 0; i < valueCol.size(); i++) {
            result += (valueCol.get(i) / (col.length * 1.0))*resP.get(i);
        }
        System.out.println(" " + result);
        //System.out.println(valueCol.toString());
        /*for (String str : poss) {
            ArrayList<String> possCol = new ArrayList<>();
            ArrayList<Integer> valueCol = new ArrayList<>();
            for (String strCol : decClass) {
                if (!possCol.contains(strCol)) {
                    possCol.add(strCol);
                    valueCol.add(1);
                } else {
                    valueCol.set(possCol.indexOf(strCol), valueCol.get(possCol.indexOf(strCol)) + 1);
                }
            }
            System.out.println(" " + possCol.toString() + " " + valueCol.toString());
        }*/
 /*for(int i = 0; i < col.length; i++){
            
        }*/
//        for (String str : poss) {
//            ArrayList<String> possCol = new ArrayList<>();
//            ArrayList<Integer> valueCol = new ArrayList<>();
//            for (String strCol : decClass) {
//                if (!possCol.contains(strCol)) {
//                    possCol.add(strCol);}
////                    /valueCol.add(1);
////                } else {
////                    valueCol.set(possCol.indexOf(strCol), valueCol.get(possCol.indexOf(strCol)) + 1);
////                }
//            }
//            System.out.println(" " + possCol.toString() + " " + valueCol.toString());
//        }
    }

    private void entropia(double[] decClass) {
        ArrayList<Double> poss = new ArrayList<>();
        ArrayList<Integer> value = new ArrayList<>();
        for (double str : decClass) {
            if (!poss.contains(str)) {
                poss.add(str);
                value.add(1);
            } else {
                value.set(poss.indexOf(str), value.get(poss.indexOf(str)) + 1);
            }
        }
        int size = decClass.length;
        double res = 0;
        for (int val : value) {
            //System.out.println(val + " " + size + " " + (val * 1.0 /size) + " " +(val/size)*log(val/size));
            //https://en.wikipedia.org/wiki/Logarithm#Change_of_base
            res += (val * 1.0 / size) * (log10(val * 1.0 / size) / log10(2));
        }
        System.out.println("I(P): " + (-res));
    }

    public double[][] transpose(double[][] array) {
        if (array == null || array.length == 0)//empty or unset array, nothing do to here
        {
            return array;
        }

        int width = array.length;
        int height = array[0].length;

        double[][] array_new = new double[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array_new[y][x] = array[x][y];
            }
        }
        return array_new;
    }

    public String[][] transpose(String[][] array) {
        if (array == null || array.length == 0)//empty or unset array, nothing do to here
        {
            return array;
        }

        int width = array.length;
        int height = array[0].length;

        String[][] array_new = new String[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array_new[y][x] = array[x][y];
            }
        }
        return array_new;
    }

}
