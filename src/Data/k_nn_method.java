/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author KonradD
 */
public class k_nn_method {

    private ArrayList<String> possibleResults = new ArrayList<>();
    private double[] pointPos;

    public k_nn_method() {

    }

    public void classificationMode(ArrayList<ArrayList<String>> data, int decisionCol, double[] pointPos, int neighbourCount) {
        this.pointPos = pointPos;
        /*for (int i = 0; i < data.size(); i++) {
            System.out.println(i);
        }*/
 /*for (int i = 0; i < 100; i++) {
            if ((i % 2) == 0) {
                continue;
            }
            System.out.println(i);
        }*/
        ArrayList<Structure> tempRes = distanceList(0, data, decisionCol);
        /* ArrayList<String> row;
        //ArrayList<String> possibleResults = new ArrayList<>();
        double[] tempPoint = new double[data.size() - 1];
        //ArrayList<Double> tempPoint = new ArrayList<>();
        //for(ArrayList<String> row : data){
        for (int j = 0; j < data.size(); j++) {
            row = data.get(j);
            for (int i = 0, iterator = 0; i < row.size(); i++) {
                if (i == decisionCol) {
                    if (!possibleResults.contains(row.get(i))) {
                        possibleResults.add(row.get(i));
                    }
                    continue;
                }
                tempPoint[iterator++] = Double.parseDouble(row.get(i).replace(",", "."));
            }
            //tempRes.add(new Structure(j, euclideanDistance(pointPos, tempPoint)));
            tempRes.add(new Structure(j, euclideanDistance(pointPos, tempPoint), row.get(decisionCol)));
            Arrays.fill(tempPoint, 0.0);
        }*/
 /*for (Structure str : tempRes) {
            System.out.println(str.getValue() + " ");
        }*/

        tempRes = sort(tempRes);
        /*System.out.println("");
        System.out.println("po sortowaniu");
        for (Structure str : tempRes) {
            System.out.println(str.getPosition() + " " +str.getValue() + " ");
        }
        
        System.out.println("possible");
        for (String str : possibleResults) {
            System.out.println(str);
        }*/
        //String[][] closestNeighbours = new String[neighbourCount][data.size()];
        //String[]
        /*for(int i = 0; i < neighbourCount; i++){
            //closestNeighbours[i] = Array.
            System.out.println(tempRes.get(i).toString());
        }*/

        findResolution(tempRes.subList(0, neighbourCount)/*, decisionCol, possibleResults*/);
    }

    private ArrayList<Structure> distanceList(int Distance, ArrayList<ArrayList<String>> data, int decisionCol) {
        ArrayList<Structure> tempRes = new ArrayList<>(); //przechowywane obliczone odleglosci
        ArrayList<String> row;
        double[] tempPoint = new double[data.size() - 1];
        for (int j = 0; j < data.size(); j++) {
            row = data.get(j);
            for (int i = 0, iterator = 0; i < row.size(); i++) {
                if (i == decisionCol) {
                    if (!possibleResults.contains(row.get(i))) {
                        possibleResults.add(row.get(i));
                    }
                    continue;
                }
                tempPoint[iterator++] = Double.parseDouble(row.get(i).replace(",", "."));
            }
            tempRes.add(new Structure(j, euclideanDistance(pointPos, tempPoint), row.get(decisionCol)));
            Arrays.fill(tempPoint, 0.0);
        }
        return tempRes;
    }

    private int findResolution(List<Structure> data/*, int decisionCol, ArrayList<String> possibleResults*/) {
        /*boolean maxDuplicate = false;
        int resultPos = -1;
        int result = 0;*/
        int[] results = new int[possibleResults.size()];

        /*for (int tempRes : results) {
            if (tempRes == result) {
                maxDuplicate = true;
            } else if (tempRes > result) {
                result = tempRes;
                maxDuplicate = false;
            }
        }*/
 /*for (int i = 0; i < possibleResults.size(); i++) {
            if (results[i] == result) {
                maxDuplicate = true;
                System.out.println(i);
            } else if (results[i] > result) {
                result = results[i];
                maxDuplicate = false;
            }
        }*/
        int resultPos;
        while (true) {
            boolean maxDuplicate = false;
            resultPos = -1;
            int result = 0;
            for (Structure str : data) {
                results[possibleResults.indexOf(str.getDecClass())]++;
            }
            for (int i = 0; i < possibleResults.size(); i++) {
                if (results[i] == result) {
                    maxDuplicate = true;
                    //System.out.println(i);
                } else if (results[i] > result) {
                    result = results[i];
                    resultPos = i;
                    maxDuplicate = false;
                }
            }
            if (!maxDuplicate) {
                break;
            } else if (data.size() == 2) {
                resultPos = -1;
                break;
            } else if (maxDuplicate) {
                System.out.println("przed: " + data.size());
                data = data.subList(0, data.size() - 1);
                Arrays.fill(results, 0);
                System.out.println("po: " + data.size());
            }
        }

        System.out.println("pozycja najwiekszej wartosci: " + resultPos);
        System.out.println(Arrays.toString(results));
        System.out.println(possibleResults.toString());
        //System.out.println(possibleResults.get(resultPos));
        return resultPos;
    }

    public void classificationEval(ArrayList<ArrayList<String>> data, int decisionCol, int neighbourCount) {

        ArrayList<Boolean> result = new ArrayList<>();
        ArrayList<Structure> tempRes;
        int calculatedValue;
        for (int i = 0; i < data.size(); i++) {
            setPoint(data.get(i), decisionCol);
            tempRes = distanceList(0, classificationSubArray(data, i), decisionCol);
            tempRes = sort(tempRes);
            calculatedValue = findResolution(tempRes.subList(0, neighbourCount)/*, decisionCol, possibleResults*/);
            if (calculatedValue == -1) {
                result.add(Boolean.FALSE);
            } else if (possibleResults.get(calculatedValue).equals(data.get(i).get(decisionCol))) {
                result.add(Boolean.TRUE);
            } else {
                result.add(Boolean.FALSE);
            }
        }

        //System.out.println(result);
        System.out.println("");
        for (boolean b : result) {
            System.out.println(b);
        }
    }

    private void setPoint(ArrayList<String> data, int decisionCol) {
        pointPos = new double[data.size() - 1];
        for (int i = 0, iterator = 0; i < data.size(); i++) {
            if (i != decisionCol) {
                pointPos[iterator++] = Double.parseDouble(data.get(i).replace(",", "."));
            }
        }
    }

    private ArrayList<ArrayList<String>> classificationSubArray(ArrayList<ArrayList<String>> data, int posClassificatedPoint) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (i != posClassificatedPoint) {
                temp.add(data.get(i));
            }
        }
        return temp;
    }

    public double euclideanDistance(double[] p1, double[] p2) {
        double res = 0;
        for (int i = 0; i < p1.length; i++) {
            res += Math.pow(p1[i] - p2[i], 2);
        }
        res = Math.sqrt(res);
        return res;
    }

    public double ManhattanDistance(double[] p1, double[] p2) {
        double res = 0;
        for (int i = 0; i < p1.length; i++) {
            res += Math.abs(p1[i] - p2[i]);
        }
        return res;
    }

    //metryka nieskonczonosc
    public double maxDistance(double[] p1, double[] p2) {
        double[] res = new double[p1.length];
        for (int i = 0; i < p1.length; i++) {
            res[i] = Math.abs(p1[i] - p2[i]);
            //System.out.println(res[i]);
        }
        Arrays.sort(res);
        //System.out.println(Arrays.toString(res));
        return res[p1.length - 1];
    }
    
    public double mahalanobisDistance(){
        return 0;
    }

    public ArrayList<Structure> sort(ArrayList<Structure> tempRes) {
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

    public void covarianceMatrix() {
        double[][] tab = {
            {4.0000, 2, 0.6000},
            {4.2000, 2.1, 0.5900},
            {3.9000, 2, 0.5800},
            {4.3000, 2.1, 0.6200},
            {4.1000, 2.2, 0.6300}};
        double[][] res = new double[tab[0].length][tab[0].length];
        double[] average = new double[tab[0].length];
        double[][] tab1 = new double[tab[0].length][];
        for (int i = 0; i < tab[0].length; i++) {
            tab1[i] = new double[tab.length];
            tab1[i] = getCol(tab, i);
            //System.out.println(Arrays.toString(tab1[i]));
        }
        for (int i = 0; i < tab[0].length; i++) {
            double[] temp = new double[tab.length];
            for (int j = 0; j < tab.length; j++) {
                temp[j] = tab[j][i];
            }
            average[i] = srednia(temp);
        }
        //System.out.println(Arrays.toString(average));
        for (int i = 0; i < tab[0].length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                //res[i][j] = (tab.length - 1 ) *
                res[i][j] = (1.0 / (tab.length - 1)) * convarianceMatrixEngine(tab1[i], tab1[j], average[i], average[j]);
                //System.out.println("P: " + (1.0/(tab.length - 1)) * convarianceMatrixEngine(tab1[i], tab1[j], average[i], average[j]));
            }
        }
        /*for (int i = 0; i < tab[0].length; i++) {
            //System.out.println(String.format("%.0f", Arrays.toString(res[i])));
            System.out.println(Arrays.toString(res[i]));
        }
        System.out.println("");
        for (int i = 0; i < tab[0].length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                System.out.print(String.format("%.5f", res[i][j]) + " ");
            }
            System.out.println("");
        }*/

        double[] point = {2, 4, 2};
        
    }

    private double convarianceMatrixEngine(double[] tabX, double[] tabY, double averageX, double averageY) {
        //double[] temp = new double[tabX.length];
        BigDecimal X, Y;// - new BigDecimal();
        BigDecimal avgX = new BigDecimal(averageX);
        BigDecimal avgY = new BigDecimal(averageY);
        //double res = 0;
        BigDecimal res = new BigDecimal("0");
        for (int i = 0; i < tabX.length; i++) {
            int lengthX = tabX.length;
            int lengthY = tabY.length;
            X = new BigDecimal(tabX[i]);//.setScale(lengthX);
            Y = new BigDecimal(tabY[i]);//.setScale(lengthY);
            //System.out.println("XY: " + X.toString() + ", " + Y.toString());
            X = X.subtract(avgX);
            Y = Y.subtract(avgY);
            //System.out.println(X.multiply(Y).setScale(lengthX, BigDecimal.ROUND_HALF_UP));
            //res = res.add(X.multiply(Y).setScale(lengthX, BigDecimal.ROUND_HALF_UP));
            res = res.add(X.multiply(Y).setScale(lengthX, BigDecimal.ROUND_HALF_UP));
            //res += X.multiply(Y).doubleValue();
            //System.out.println(res);
            //temp[i] = (tabX[i] - averageX) * (tabY[i] - averageY);
            //System.out.println((tabX[i] - averageX) * (tabY[i] - averageY));

            //res += (tabX[i] - averageX) * (tabY[i] - averageY);
        }

        /*for (int i = 0; i < tabX.length; i++) {
            res += temp[i];
        } */
        //System.out.println(res);
        System.out.println(res.toString());
        return res.doubleValue();
    }

    private double[] getCol(double[][] tab, int column) {
        double[] temp = new double[tab.length];
        //for(int i = 0; i < tab[0].length; i++){            
        for (int j = 0; j < tab.length; j++) {
            temp[j] = tab[j][column];
        }
        //}
        return temp;
    }

    private double srednia(double[] tab) {
        int ilosc = 0;
        double suma = 0;
        for (double war : tab) {
            ilosc++;
            suma += war;
        }
        //System.out.println(suma);
        return suma / ilosc;
    }
}

class Structure {

    private int position;
    private double value;
    private String decClass;

    public Structure(int position, double value, String decClass) {
        this.position = position;
        this.value = value;
        this.decClass = decClass;
    }

    public Structure(int position, double value) {
        this.position = position;
        this.value = value;
    }

    public Structure(String decClass) {
        this.decClass = decClass;
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

    public String getDecClass() {
        return decClass;
    }

    public void setDecClass(String decClass) {
        this.decClass = decClass;
    }

    @Override
    public String toString() {
        return "Structure{" + "position=" + position + ", value=" + value + ", decClass=" + decClass + '}';
    }
}
