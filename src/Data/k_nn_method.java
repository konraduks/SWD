/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
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
    private Macierz[] inversedCovarianceMatrix;

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
            //System.out.println("Kowariancja");
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

    ArrayList<Integer> nC = new ArrayList<>(); //liczba sasaidow wzietych do klasyfikacji
    //ArrayList<Integer> fC = new ArrayList<>(); //liczba zle zaklasyfikowanych
    ArrayList<Double> qualityClassification = new ArrayList<>();

    //automatyczny(od 1 sasiada do n-1) tryb oceny
    public void automaticEvaluation1(int metric, ArrayList<ArrayList<String>> data, int decisionCol, boolean deleteEqualValCol) {        
        long startTime = System.currentTimeMillis();
        if (deleteEqualValCol) {
            data = deleteEqualCol(data);
        }
        if (metric == 3) { //czyli mahalanobis
            System.out.println(data.get(0).size());
            inversedCovarianceMatrix = new Macierz[data.size()];
            for (int x = 0; x < data.size(); x++) {
                double[][] dataCov = new double[data.size() - 1][data.get(0).size() - 1];
                ArrayList<ArrayList<String>> dataToCovArr = classificationSubArray(data, x);//lista bez rzedu do oceny
                for (int i = 4; i < dataCov.length; i++) {
                    for (int j = 0, y = 0; j < data.get(0).size(); j++) {
                        if (j == decisionCol) {
                            continue;
                        }
                        //dataCov[i][y++] = Double.parseDouble(data.get(i).get(j).replace(",", "."));
                        dataCov[i][y++] = Double.parseDouble(dataToCovArr.get(i).get(j).replace(",", "."));
                    }
                }
                long start = System.currentTimeMillis();
                inverseArray(covarianceMatrix(dataCov), x);
                long stop = System.currentTimeMillis();
                System.out.println("Czas odwracania: " + (stop - start));
            }
        }
        int falseCount;
        System.out.println(data.size());
        for (int neighbourCount = 1; neighbourCount < data.size(); neighbourCount++) {
            //ArrayList<Boolean> result = new ArrayList<>();
            falseCount = 0;
            ArrayList<Structure> tempRes;
            int calculatedValue;
            for (int i = 0; i < data.size(); i++) {
                setPoint(data.get(i), decisionCol);
                tempRes = automaticDistanceList(metric, classificationSubArray(data, i), decisionCol, i); //i to ktora tablice z inversedCovarianceMatrix wziac
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
            nC.add(neighbourCount);
            //fC.add(falseCount);
            double quality = ((data.size() - falseCount) / (data.size() * 1.0));
            qualityClassification.add(quality);
        }
        System.out.println(nC.toString());
        //System.out.println(fC.toString());
        System.out.println(qualityClassification.toString());
        long stopTime = System.currentTimeMillis();
        System.out.println("Laczny czas wykonania: " + ((stopTime - startTime) / 1000));
    }

    //automatyczny(od 1 sasiada do n-1) tryb oceny
    public void automaticEvaluation(int metric, ArrayList<ArrayList<String>> data, int decisionCol, boolean deleteEqualValCol) {
        long startTime = System.currentTimeMillis();
        if (deleteEqualValCol) {
            data = deleteEqualCol(data);
        }
        /*if (metric == 3) { //czyli mahalanobis
            System.out.println(data.get(0).size());
            inversedCovarianceMatrix = new Macierz[data.size()];
            for (int x = 0; x < data.size(); x++) {
                double[][] dataCov = new double[data.size() - 1][data.get(0).size() - 1];
                ArrayList<ArrayList<String>> dataToCovArr = classificationSubArray(data, x);//lista bez rzedu do oceny
                for (int i = 0; i < dataCov.length; i++) {
                    for (int j = 0, y = 0; j < data.get(0).size(); j++) {
                        if (j == decisionCol) {
                            continue;
                        }
                        //dataCov[i][y++] = Double.parseDouble(data.get(i).get(j).replace(",", "."));
                        dataCov[i][y++] = Double.parseDouble(dataToCovArr.get(i).get(j).replace(",", "."));
                    }
                }
                /*System.out.println("------------------");
                System.out.print("[");
                for (int i = 0; i < dataCov.length; i++) {
                    for (int j = 0; j < dataCov[i].length; j++) {
                        //System.out.print(String.format("%.5f", res[i][j]) + ", ");
                        System.out.print(dataCov[i][j]);
                        if (j != (dataCov.length - 1)) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println(";");
                }
                System.out.print("]");
                System.out.println("------------------");*-/
                long start = System.currentTimeMillis();
                inverseArray(covarianceMatrix(dataCov), x);
                long stop = System.currentTimeMillis();
                System.out.println("Czas odwracania: " + (stop - start));
            }
        }*/
        int falseCount;
        System.out.println(data.size());

        int[] result = new int[data.size() - 1];
        //ArrayList<Boolean> result = new ArrayList<>();
        falseCount = 0;
        ArrayList<Structure> tempRes;
        int calculatedValue;
        for (int i = 0; i < data.size(); i++) {
            if(metric == 3){
                double[][] dataCov = new double[data.size() - 1][data.get(0).size() - 1];
                ArrayList<ArrayList<String>> dataToCovArr = classificationSubArray(data, i);//lista bez rzedu do oceny
                
                for (int x = 0; x < dataCov.length; x++) {
                    for (int j = 0, y = 0; j < data.get(0).size(); j++) {
                        if (j == decisionCol) {
                            continue;
                        }
                        //dataCov[i][y++] = Double.parseDouble(data.get(i).get(j).replace(",", "."));
                        dataCov[x][y++] = Double.parseDouble(dataToCovArr.get(x).get(j).replace(",", "."));
                    }
                }
                
                //long start = System.currentTimeMillis();
                inverseArray(covarianceMatrix(dataCov));
                //long stop = System.currentTimeMillis();
                //System.out.println("Czas odwracania: " + (stop - start));
            }
            setPoint(data.get(i), decisionCol);
            tempRes = automaticDistanceList(metric, classificationSubArray(data, i), decisionCol, i); //i to ktora tablice z inversedCovarianceMatrix wziac
            tempRes = sort(tempRes);
            for (int neighbourCount = 1; neighbourCount < data.size(); neighbourCount++) {
                calculatedValue = findResolution(tempRes.subList(0, neighbourCount)/*, decisionCol, possibleResults*/);
                if (possibleResults.get(calculatedValue).equals(data.get(i).get(decisionCol))) {
                    //result.add(Boolean.TRUE);
                } else {
                    //result.add(Boolean.FALSE);
                    //falseCount++;
                    result[neighbourCount - 1]++;
                }
            }
            //System.out.println("Ilosc zle zaklasyfikowanych: " + falseCount + ", dla neighbourCount = " + neighbourCount);
            //nC.add(neighbourCount);
            //fC.add(falseCount);
            /*double quality = ((data.size() - falseCount) / (data.size() * 1.0));
            qualityClassification.add(quality);*/
        }
        System.out.print("[");
        for (int i = 0; i < result.length; i++) {
            qualityClassification.add(((data.size() - result[i]) / (data.size() * 1.0)));
            if (i != result.length - 1) {
                System.out.print((i + 1) + ", ");
            } else {
                System.out.print((i + 1));
            }
        }
        System.out.println("]");
        System.out.println(qualityClassification.toString());

        //System.out.println(nC.toString());
        //System.out.println(fC.toString());
        //System.out.println(qualityClassification.toString());
        System.out.println("Wynik automatic: " + Arrays.toString(result)); //zerowy indeks trzyma wynik dla k=1
        long stopTime = System.currentTimeMillis();
        System.out.println("Laczny czas wykonania(w s): " + ((stopTime - startTime) / 1000));
        System.out.println("Laczny czas wykonania(w ms): " + ((stopTime - startTime)));
    }

    private ArrayList<Structure> automaticDistanceList(int metric, ArrayList<ArrayList<String>> data, int decisionCol, int inverseMatrixPos) {
        ArrayList<Structure> tempRes = new ArrayList<>(); //przechowywane obliczone odleglosci
        ArrayList<String> row;
        //System.out.println("metryka: " + metric);        
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
                tempRes.add(new Structure(j, automaticMahalanobisDistance(pointPos, tempPoint, inverseMatrixPos), row.get(decisionCol)));
            }
            Arrays.fill(tempPoint, 0.0);
        }
        return tempRes;
    }

    private ArrayList<ArrayList<String>> deleteEqualCol(ArrayList<ArrayList<String>> data) {
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        String val = null;
        ArrayList<Integer> colToRemove = new ArrayList<>();
        //System.out.println("deleteEqual");
        for (int i = 0, y = 0; /*i < data.size()*/ y < data.get(0).size(); i++) {
            //System.out.print(data.get(i).get(y) + " ");
            if (i == data.get(0).size() - 1) {
                //System.out.println("kolumna z wartosciami do usuniecia " + y);
                colToRemove.add(0, y);
                y++;
                i = -1;
                //wartosci w kolumnie sa rowne, czyli do usuniecia                
                continue;
            }
            if (i == 0) {
                val = data.get(i).get(y);
            } else if (!val.equals(data.get(i).get(y))) {
                //wartosci w kolumnie sa rozne mozna przejsc do nastepnej
                //System.out.println("");
                y++;
                i = -1;
                //System.out.println("");
            }
            //for(int y = 0; y < data.size(); y++){
            //data.get(0).get(0);
            //data.get(1).get(0);
            //}
        }
        //System.out.println("Do usuniecia: " + colToRemove.toString());
        for (int i = 0; i < data.size(); i++) {
            temp.add(new ArrayList<String>());
            for (int j = 0; j < data.get(i).size(); j++) {
                if (colToRemove.contains(j)) {
                    continue;
                }
                temp.get(i).add(data.get(i).get(j));
            }
        }
        /*System.out.println("PO CZYSZCZENIU");
        for(ArrayList<String> stemp : temp){
            System.out.println(stemp.toString());
        }*/
        return temp;
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
        //Macierz mac = new Macierz(array);
        //inverseCovarianceMatrix = mac.wyznaczMacierzOdwrotna();
        
        DoubleMatrix2D matrix = new DenseDoubleMatrix2D(array);
        Algebra alg = new Algebra();
        matrix = alg.inverse(matrix);
        Macierz mac = new Macierz(matrix.toArray());
        inverseCovarianceMatrix = mac;
        
        //mac = mac.wyznaczMacierzOdwrotna();        
        /*System.out.println("---------------------");
        System.out.println("macierz odwrotna:");
        System.out.println(mac.toString());
        System.out.println("---------------------");*/
        //return mac.getTablice();
        
        
        
    }

    public /*double[][]*/ void inverseArray(double[][] array, int pos) {
        //Macierz mac = new Macierz(array);
        //mac = mac.wyznaczMacierzOdwrotna();
        //inverseCovarianceMatrix = mac.wyznaczMacierzOdwrotna();
        //inversedCovarianceMatrix[pos] = mac.wyznaczMacierzOdwrotna();        
        DoubleMatrix2D matrix = new DenseDoubleMatrix2D(array);
        Algebra alg = new Algebra();
        matrix = alg.inverse(matrix);
        Macierz mac = new Macierz(matrix.toArray());
        inversedCovarianceMatrix[pos] = mac;
        /*System.out.println("---------------------");
        System.out.println("macierz odwrotna:");
        System.out.println(mac.toString());
        System.out.println("---------------------");*/
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

    //point, data array
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

    //point, data array
    public double automaticMahalanobisDistance(double[] p1, double[] p2, int inverseMatrixPos) {
        double[][] t1 = {p1};
        double[][] t2 = {p2};
        Macierz mc1 = new Macierz(t1);
        Macierz mc2 = new Macierz(t2);
        /*System.err.println(mc1.toString());
        System.err.println(mc2.toString());*/
 /*Macierz temp =*/ mc2.odejmijMacierz(mc1);
        Macierz temp = new Macierz(mc2.getTablice());
        //temp.pomnoz(inverseCovarianceMatrix);
        //temp.pomnoz(inversedCovarianceMatrix[inverseMatrixPos]);
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
        /*System.out.println("---------------------");
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
        System.out.println("---------------------");*/
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
