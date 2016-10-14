/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author KonradD
 */
public class k_nn_method {

    public k_nn_method() {

    }

    public void classificationMode(ArrayList<ArrayList<String>> data, int decisionCol, double[] pointPos, int neighbourCount) {

        /*for (int i = 0; i < data.size(); i++) {
            System.out.println(i);
        }*/
 /*for (int i = 0; i < 100; i++) {
            if ((i % 2) == 0) {
                continue;
            }
            System.out.println(i);
        }*/
        ArrayList<Structure> tempRes = new ArrayList<>(); //przechowywane obliczone odleglosci
        ArrayList<String> row;
        double[] tempPoint = new double[data.size() - 1];
        //ArrayList<Double> tempPoint = new ArrayList<>();
        //for(ArrayList<String> row : data){
        for (int j = 0; j < data.size(); j++) {
            row = data.get(j);
            for (int i = 0, iterator = 0; i < row.size(); i++) {
                if (i == decisionCol) {
                    continue;
                }
                //tempPoint.add(Double.parseDouble(row.get(i)));
                tempPoint[iterator++] = Double.parseDouble(row.get(i).replace(",", "."));
            }
            //tempPoint.toArray(new double[10]);
            //tempRes.add(new Structure(j, euclideanDistance(pointPos, tempPoint.toArray(new double[tempPoint.size()]))));
            tempRes.add(new Structure(j, euclideanDistance(pointPos, tempPoint)));
            Arrays.fill(tempPoint, 0.0);
            //tempPoint.clear();
        }
        /*for (Structure str : tempRes) {
            System.out.println(str.getValue() + " ");
        }*/

        
        tempRes = sort(tempRes);
        System.out.println("");
        System.out.println("po sortowaniu");
        for (Structure str : tempRes) {
            System.out.println(str.getPosition() + " " +str.getValue() + " ");
        }
    }
    
    
    public void classificationEval(ArrayList<ArrayList<String>> data, int decisionCol, int neighbourCount){
        
    }

    public double euclideanDistance(double[] p1, double[] p2) {
        double res = 0;
        for (int i = 0; i < p1.length; i++) {
            res += Math.pow(p1[i] - p2[i], 2);
        }
        res = Math.sqrt(res);
        return res;
    }
    
    public ArrayList<Structure> sort(ArrayList<Structure> tempRes){
        Collections.sort(tempRes, new Comparator<Structure>() {
            @Override
            public int compare(Structure s1, Structure s2) {
                if (s1.getValue() < s2.getValue()) {
                    return -1;
                }
                if (s1.getValue() > s2.getValue()) {
                    return 1;
                }
                return 0;
            }
        });
        return tempRes;
    }
}

class Structure {

    private int position;
    private double value;

    public Structure(int position, double value) {
        this.position = position;
        this.value = value;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
