/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import cern.colt.Arrays;
import cern.colt.list.MinMaxNumberList;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author KonradD
 */
/*
Metoda k-średnich
K-Means
analiza skupien
 */
public class K_Means {

    public double[][] clusterCentre; //srodki skupien;
    public int[] clusterCentreList; //lista srodkow dla kazdego punktu

    /**
     * Funkcja grupowanie. Pierwszy parametr zbior, drugi liczba skupien.
     *
     * @param data Zbior dla którego wyznaczamy analize skupien
     * @param decisionClass klasa decyzyjna
     * @param clusterAmount Liczba skupien
     */
    public void group(double[][] data, String[] decisionClass, int clusterAmount) {
        randClusterCentre(transpose(data), clusterAmount);

        //ustawienie dla wszystkich punktów najblizszego srodka
        clusterCentreList = new int[data.length];
        k_nn_method knm = new k_nn_method();
        for (int i = 0; i < clusterCentreList.length; i++) {
            //pobranie rzedu z data
            double[] row = data[i];
            //double[] row = new double[data[i].length];
            /*for (int j = 0; j < data[i].length; j++) {
                row[j] = data[i][j];
            }*/
 /*System.err.println("----------------------");
            System.err.println(Arrays.toString(row));
            System.err.println("----------------------");*/
            //wyliczenie odleglosci od punktu do wszystkich srodkow
            double distance = 0, tempDistance;
            int nearestCentre = 0; //pozycja w tabeli cluserCentre najblizszego srodka
            for (int j = 0; j < clusterCentre.length; j++) {
                //knm.euclideanDistance(p1, p2);
                if (j == 0) {
                    distance = knm.euclideanDistance(row, clusterCentre[j]);
                    nearestCentre = j;
                    continue;
                }
                if ((tempDistance = knm.euclideanDistance(row, clusterCentre[j])) < distance) {
                    distance = tempDistance;
                    nearestCentre = j;
                }
            }
            clusterCentreList[i] = nearestCentre;
            //System.out.println(nearestCentre);
        }
        //zmiana srodkow skupien
        changeClusterCentre(data);

        System.out.println("-----------------------------");
        System.out.println(Arrays.toString(clusterCentreList));

    }

    /*
    Wylosowanie poczatkowych srodkow skupien
     */
    public void randClusterCentre(double[][] data, int amount) {
        double[][] minMax = getMinMax(data);
        clusterCentre = new double[amount][minMax[0].length];
        System.out.println(minMax[0].length + " ");
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < minMax[0].length; j++) {
                clusterCentre[i][j] = ThreadLocalRandom.current().nextDouble(minMax[0][j], minMax[1][j]);
            }
        }

        for (double[] dob : clusterCentre) {
            System.out.println(Arrays.toString(dob));
        }
    }

    /**
     * Zmiana srodkow skupien za pomoca sredniej arytmetycznej dla
     * poszczegolnych kolumn dla danego srodka
     */
    private void changeClusterCentre(double[][] data) {
        //double[][][] colSum = new double[clusterCentre.length][data.length][data[0].length]; //suma poszczegolnych kolumn dla poszczegolnych srodkow skupien
        double[][] colSum = new double[clusterCentre.length][data[0].length]; //suma poszczegolnych kolumn dla poszczegolnych srodkow skupien
        int[] divideVal = new int[clusterCentre.length]; //ile punktow zostalo przydzielonych do poszczegolnych skupien;
        System.err.println("Wielkosc colSum: " + colSum.length + ", " + colSum[0].length);
        System.err.println("Wielkosc tablicy dzielenia: " + divideVal.length);
        for (int i = 0; i < clusterCentreList.length; i++) {
            //colSum[clusterCentreList[i]] += data[i];
            for(int j = 0; j < data[i].length; j++){
                colSum[clusterCentreList[i]][j] += data[i][j];
            }
            divideVal[clusterCentreList[i]]++;
        }
        System.out.println("Wartosci divide val: " + Arrays.toString(divideVal));
        for(double[] col : colSum ){
                System.out.println(Arrays.toString(col));
            }
        for(int i = 0; i < clusterCentre.length; i++){
            for(int j = 0; j < clusterCentre[j].length; j++){
                //colSum[i][j] = colSum[i][j] / divideVal[i]; 
                clusterCentre[i][j] = colSum[i][j] / divideVal[i]; 
            }
        }
        
        /*for(double[] col : colSum ){
                System.out.println(Arrays.toString(col));
            }*/
        /*for (double[][] allCol : colSum) {
            //System.out.println(Arrays.toString(col));
            System.out.println("--------------------");
            for(double[] col : allCol ){
                System.out.println(Arrays.toString(col));
            }
        }*/
    }

    /*
    znalezienie wartosci minimalnej i maksymalnej dla kazdej kolumny, aby mozna bylo wyznaczyc metoda random poczatkowe ustawienia srodkow skupien.
     */
    public double[][] getMinMax(double[][] data) {
        //throw new UnsupportedOperationException("Not supported yet.");   
        double[][] res = new double[2][data.length];
        System.out.println(data[0].length);
        for (int i = 0; i < data.length; i++) {
            //System.out.println(i + " " + data[i][0] + " " + data[i][5]);
            res[0][i] = data[i][0];
            for (int j = 0; j < data[0].length; j++) {
                if (data[i][j] < res[0][i]) {
                    res[0][i] = data[i][j];
                } else if (data[i][j] > res[1][i]) {
                    res[1][i] = data[i][j];
                }
            }
        }
        return res;
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

    /*public double[][] getMinMax(ArrayList<ArrayList<String>> data) {
        //throw new UnsupportedOperationException("Not supported yet.");   
        double[][] res = new double[2][data.size()];        
        for (int i = 0; i < data.get(0).size(); i++) {                   
            res[0][i] = Double.parseDouble(data.get(i).get(0));
            for (int j = 0; j < data.length; j++) {
                if (data[i][j] < res[0][i]) {
                    res[0][i] = data[i][j];
                } else if (data[i][j] > res[1][i]) {
                    res[1][i] = data[i][j];
                }
            }
        }
        return res;
    }*/
}
