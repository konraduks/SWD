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
import java.util.Locale;

/**
 *
 * @author KonradD
 */
public class k_nn_method {

    private ArrayList<String> possibleResults = new ArrayList<>();
    private double[] pointPos;

    private Macierz inverseCovarianceMatrix;

    public k_nn_method() {

    }

    //tryb klasyfikacji
    public void classificationMode(int metric, ArrayList<ArrayList<String>> data, int decisionCol, double[] pointPos, int neighbourCount) {
        this.pointPos = pointPos;
        System.out.println("metryka: " + metric);
        ArrayList<Structure> tempRes = distanceList(metric, data, decisionCol);
        tempRes = sort(tempRes);
        findResolution(tempRes.subList(0, neighbourCount)/*, decisionCol, possibleResults*/);
    }

    private ArrayList<Structure> distanceList(int metric, ArrayList<ArrayList<String>> data, int decisionCol) {
        ArrayList<Structure> tempRes = new ArrayList<>(); //przechowywane obliczone odleglosci
        ArrayList<String> row;
        //System.out.println("metryka: " + metric);
        if (metric == 3) { //czyli mahalanobis
            double[][] dataCov = new double[data.size()][data.get(0).size() - 1];
            for (int i = 0; i < dataCov.length; i++) {
                for (int j = 0, y = 0; j < data.get(0).size(); j++) {
                    if (j == decisionCol) {
                        continue;
                    }
                    dataCov[i][y++] = Double.parseDouble(data.get(i).get(j).replace(",", "."));
                }
            }
            /*for (int i = 0; i < dataCov.length; i++) {
                System.err.println(Arrays.toString(dataCov[i]));
            }*/
            System.out.println("Kowariancja");
            inverseArray(covarianceMatrix(dataCov));

            /*double[][] tempInv = inverseCovarianceMatrix.getTablice();
            System.out.println("---------------------");
            System.out.println("Rev cov:");
            for (int i = 0; i < tempInv.length; i++) {
                for (int j = 0; j < tempInv[0].length; j++) {
                    System.out.print(String.format("%.5f", tempInv[i][j]) + " ");
                }
                System.out.println("");
            }
            System.out.println("---------------------");*/
        }
        //double[] tempPoint = new double[data.size() - 1];
        double[] tempPoint = new double[data.get(0).size() - 1];
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
            //tempRes.add(new Structure(j, euclideanDistance(pointPos, tempPoint), row.get(decisionCol)));
            if (metric == 0) {
                tempRes.add(new Structure(j, euclideanDistance(pointPos, tempPoint), row.get(decisionCol)));
            } else if (metric == 1) {
                tempRes.add(new Structure(j, ManhattanDistance(pointPos, tempPoint), row.get(decisionCol)));
            } else if (metric == 2) {
                tempRes.add(new Structure(j, maxDistance(pointPos, tempPoint), row.get(decisionCol)));
            } else if (metric == 3) {
                tempRes.add(new Structure(j, mahalanobisDistance(pointPos, tempPoint), row.get(decisionCol)));
            }
            Arrays.fill(tempPoint, 0.0);
        }
        return tempRes;
    }

    private int findResolution(List<Structure> data/*, int decisionCol, ArrayList<String> possibleResults*/) {
        int[] results = new int[possibleResults.size()];
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
            /*if (!maxDuplicate) {
                break;
            } else if (data.size() == 2) {
                resultPos = -1;
                break;
            } else if (maxDuplicate) {
                System.out.println("przed: " + data.size());
                data = data.subList(0, data.size() - 1);
                Arrays.fill(results, 0);
                System.out.println("po: " + data.size());
            }*/
            if (!maxDuplicate) {
                break;
           /* } else if (data.size() == 2) {
                resultPos = -1;
                break;*/
            } else if (maxDuplicate) {
                //System.out.println("przed: " + data.size());
                data = data.subList(0, data.size() - 1);
                Arrays.fill(results, 0);
                //System.out.println("po: " + data.size());
            }
        }
//pozycje
        /*System.out.println("pozycja najwiekszej wartosci: " + resultPos);
        System.out.println(Arrays.toString(results));
        System.out.println(possibleResults.toString());*/
        //System.out.println(possibleResults.get(resultPos));
        return resultPos;
    }

    //tryb oceny
    public void evaluationMode(int metric, ArrayList<ArrayList<String>> data, int decisionCol, int neighbourCount) {

        ArrayList<Boolean> result = new ArrayList<>();
        ArrayList<Structure> tempRes;
        int calculatedValue;
        for (int i = 0; i < data.size(); i++) {
            setPoint(data.get(i), decisionCol);
            tempRes = distanceList(metric, classificationSubArray(data, i), decisionCol);
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

    //automatyczny(od 1 sasiada do n-1) tryb oceny
    public void automaticEvaluation(int metric, ArrayList<ArrayList<String>> data, int decisionCol) {
        int falseCount;
        System.out.println(data.size());
        for (int neighbourCount = 1; neighbourCount < data.size(); neighbourCount++) {
            //ArrayList<Boolean> result = new ArrayList<>();
            falseCount = 0;
            ArrayList<Structure> tempRes;
            int calculatedValue;
            for (int i = 0; i < data.size(); i++) {
                setPoint(data.get(i), decisionCol);
                tempRes = distanceList(metric, classificationSubArray(data, i), decisionCol);
                tempRes = sort(tempRes);
                calculatedValue = findResolution(tempRes.subList(0, neighbourCount)/*, decisionCol, possibleResults*/);
                /*if (calculatedValue == -1) {
                    result.add(Boolean.FALSE);
                } else if (possibleResults.get(calculatedValue).equals(data.get(i).get(decisionCol))) {
                    result.add(Boolean.TRUE);
                } else {
                    result.add(Boolean.FALSE);
                }*/
                if (possibleResults.get(calculatedValue).equals(data.get(i).get(decisionCol))) {
                    //result.add(Boolean.TRUE);
                } else {
                    //result.add(Boolean.FALSE);
                    falseCount++;
                }
            }
            System.out.println("Ilosc zle zaklasyfikowanych: " + falseCount + ", dla neighbourCount = " + neighbourCount);
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

    public double[][] reverseArray(double[][] array) {
        for (int j = 0; j < array.length; j++) {
            for (int i = 0; i < array[j].length / 2; i++) {
                double temp = array[j][i];
                array[j][i] = array[j][array[j].length - i - 1];
                array[j][array[j].length - i - 1] = temp;
            }
        }
        return array;
    }

    public /*double[][]*/ void inverseArray(double[][] array) {
        Macierz mac = new Macierz(array);
        //mac = mac.wyznaczMacierzOdwrotna();
        inverseCovarianceMatrix = mac.wyznaczMacierzOdwrotna();
        System.out.println("---------------------");
        System.out.println("macierz odwrotna:");
        System.out.println(mac.toString());
        System.out.println("---------------------");
        //return mac.getTablice();
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

    public double mahalanobisDistance(double[] p1, double[] p2) {
        double[][] t1 = {p1};
        double[][] t2 = {p2};
        Macierz mc1 = new Macierz(t1);
        Macierz mc2 = new Macierz(t2);
        /*System.err.println(mc1.toString());
        System.err.println(mc2.toString());*/
 /*Macierz temp =*/ mc2.odejmijMacierz(mc1);
        Macierz temp = new Macierz(mc2.getTablice());
        temp.pomnoz(inverseCovarianceMatrix);
        mc2.transponuj();
        temp.pomnoz(mc2);
        //System.out.println("Wynik: " + temp.toString());
        return Math.sqrt(temp.getTablice()[0][0]);
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

    public double[][] covarianceMatrix(double[][] tab) {
        double[][] covarianceMatrix;
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
                res[i][j] = (1.0 / (tab.length - 1)) * convarianceMatrixEngine(tab1[i], tab1[j], average[i], average[j]);
                //System.out.println("P: " + (1.0/(tab.length - 1)) * convarianceMatrixEngine(tab1[i], tab1[j], average[i], average[j]));
            }
        }
        System.out.println("---------------------");
        System.out.println("cov:");
        System.out.print("[");
        for (int i = 0; i < tab[0].length; i++) {
            for (int j = 0; j < tab[0].length; j++) {
                //System.out.print(String.format("%.5f", res[i][j]) + ", ");
                System.out.print(String.format(Locale.US, "%.5f", res[i][j]));
                if (j != (tab[0].length - 1)) {
                    System.out.print(", ");
                }
            }
            System.out.println(";");
        }
        System.out.print("]");
        System.out.println("---------------------");
        return res;
    }

    private double convarianceMatrixEngine(double[] tabX, double[] tabY, double averageX, double averageY) {
        BigDecimal X, Y;
        BigDecimal avgX = new BigDecimal(averageX);
        BigDecimal avgY = new BigDecimal(averageY);
        BigDecimal res = new BigDecimal("0");
        for (int i = 0; i < tabX.length; i++) {
            int lengthX = tabX.length;
            int lengthY = tabY.length;
            X = new BigDecimal(tabX[i]);
            Y = new BigDecimal(tabY[i]);
            X = X.subtract(avgX);
            Y = Y.subtract(avgY);
            res = res.add(X.multiply(Y).setScale(lengthX, BigDecimal.ROUND_HALF_UP));
        }
        //System.out.println(res.toString());
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
