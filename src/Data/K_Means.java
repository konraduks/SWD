/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

//import cern.colt.Arrays;
import java.util.Arrays;
import cern.colt.list.MinMaxNumberList;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import plot.DrawPlot;

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
    public int[] clusterPointsAmount; //ile punktow zostalo przydzielonych do poszczegolnych skupien;
    String[] clusterCentreTranslated; //przyporzadkowane nazwy klas decyzyjnych do srodkow
    int[] decisonClassClusterCentre; //1 kolumna to klasy decyzyjne zamienione na int wg clusterCentreTranslated//, a druga to otrzymane wyniki(clusterCentreList)
    int[][] mistakesMatrix; //macierz pomylek
    boolean isChanged = true;

    /**
     * Funkcja grupowanie. Pierwszy parametr zbior, drugi liczba skupien.
     *
     * @param data Zbior dla którego wyznaczamy analize skupien
     * @param decisionClass klasa decyzyjna
     * @param clusterAmount Liczba skupien
     * @param metric metryka(0 - euklidesowa, 1 - Manhattan, 2 - Max, 3 -
     * Mahalanobisa)
     */
    public void group(double[][] data, String[] decisionClass, int clusterAmount, int metric) {
        k_nn_method knm = new k_nn_method();
        data = deleteEqualCol(data);
        //int len = 8;
        //metric = 12 - len;
        /*clusterCentre = new double[clusterAmount][4];
        clusterCentre[0] = new double[]{5.0, 3.3, 1.4, 0.2};
        clusterCentre[1] = new double[]{6.4, 2.8, 5.6, 2.2};
        clusterCentre[2] = new double[]{6.5, 2.8, 4.6, 1.5};*/
        
        //clusterCentre = new double[clusterAmount][12-len];
        //clusterCentre[0] = new double[]{55000554.0, 4545450.0, 45454545175.0, 94.0, 100.0, 202.0, 380.0, 179.0, 143.0, 28.0, 11.0, -5.0, 20.0, 71.0, 0.0, 72.0, 20.0, 0.0, 0.0, 48.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 64.0, 36.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 52.0, 48.0, 0.0, 0.0, 56.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 64.0, 32.0, 0.0, 0.0, 0.0, 72.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 12.0, 0.0, 0.0, 44.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 44.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 56.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 44.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 40.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 56.0, 48.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 48.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 64.0, 40.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.9, 0.0, 7.8, -0.7, 0.0, 0.0, 1.1, 1.9, 27.3, 45.1, 0.1, 0.0, 9.1, -2.6, 0.0, 0.0, 0.4, 1.5, 24.5, 36.8, -0.4, -0.4, 1.6, -2.2, 0.0, 0.0, -1.0, -0.9, -1.5, -9.2, -0.4, -8.2, 1.8, 0.0, 0.0, 0.0, -0.7, -1.7, -23.4, -35.6, 0.9, 0.0, 3.2, -0.4, 0.0, 0.0, 0.7, 1.2, 9.4, 18.0, -0.1, 0.0, 5.1, -2.5, 0.0, 0.0, 0.3, 0.6, 9.8, 12.6, 1.6, -6.5, 0.0, 0.0, 0.0, 0.0, -0.4, -0.4, -18.2, -22.4, 2.1, 0.0, 1.2, -6.9, 0.0, 0.0, -0.5, 2.9, -12.7, 18.0, 0.7, 0.0, 9.0, -7.9, 0.0, 0.0, 0.1, 4.1, 7.6, 51.0, 0.4, 0.0, 15.0, -5.5, 0.0, 0.0, 0.1, 3.3, 28.8, 63.1, 0.1, 0.0, 15.2, -3.7, 0.0, 0.0, 0.6, 3.0, 36.8, 68.0, 0.1, 0.0, 12.2, -2.2, 0.0, 0.0, 0.4, 2.6, 34.6, 61.6};
//        clusterCentre[1-len] = new double[]{56.0, 1.0, 164.0, 65.0, 90.0, 164.0, 420.0, 381.0, 99.0, -8.0, 153.0, 41.0, 0.0, 79.0, 0.0, 72.0, 0.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 36.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 16.0, 64.0, 0.0, 0.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 72.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 68.0, 0.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 60.0, 0.0, 0.0, 12.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 76.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 72.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 52.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 56.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 48.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 64.0, 0.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.9, 0.0, 10.4, 0.0, 0.0, 0.0, 0.9, -1.4, 37.4, 21.2, -0.2, 0.0, 4.3, -0.7, 0.0, 0.0, 1.1, 0.1, 11.7, 13.3, 0.7, 0.0, 0.4, -6.3, 0.0, 0.0, 0.3, 0.4, -19.8, -13.9, 0.7, -7.5, 0.0, 0.0, 0.0, 0.0, -0.9, -0.1, -27.0, -29.0, -0.9, 0.0, 9.1, 0.0, 0.0, 0.0, 0.5, -1.2, 30.9, 7.7, 0.1, 0.0, 0.7, -2.5, 0.0, 0.0, 0.6, -0.6, -6.7, -18.9, 0.9, -7.3, 0.0, 0.0, 0.0, 0.0, -0.6, 2.0, -27.7, -4.9, 0.5, -4.7, 0.0, 0.0, 0.0, 0.0, -0.2, 2.2, -16.9, 8.1, 0.0, 0.0, 6.0, -7.2, 0.0, 0.0, -0.4, 0.3, -4.3, -0.9, -0.4, 0.0, 9.3, -6.4, 0.0, 0.0, 0.2, -1.7, 2.5, -14.8, -0.3, 0.0, 6.5, -2.4, 0.0, 0.0, 0.5, -1.7, 11.2, -3.7, -0.4, 0.0, 5.4, 0.0, 0.0, 0.0, 0.4, -1.4, 17.2, 3.0};
//        clusterCentre[2-len] = new double[]{62.0, 0.0, 170.0, 72.0, 102.0, 135.0, 401.0, 156.0, 83.0, 72.0, 71.0, 68.0, 72.0, 70.0, 20.0, 36.0, 48.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 104.0, 0.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 36.0, 0.0, 0.0, 0.0, 48.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 24.0, 40.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 84.0, 0.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 64.0, 0.0, 0.0, 12.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 16.0, 56.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 28.0, 52.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 32.0, 56.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 36.0, 56.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 40.0, 52.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.4, -0.5, 5.8, -1.9, 0.0, 0.0, 0.8, 0.4, 5.4, 8.4, -0.8, 0.0, 7.4, 0.0, 0.0, 0.0, 1.7, 1.4, 19.2, 29.2, 0.0, 0.0, 1.6, 0.0, 0.0, 0.0, 1.0, 1.1, 8.3, 16.6, 0.1, -5.7, 1.3, 0.0, 0.0, 0.0, -1.0, -1.0, -9.1, -15.5, -0.5, -0.6, 1.5, -1.9, 0.0, 0.0, -0.4, -0.2, -2.8, -4.1, 0.4, 0.0, 4.4, 0.0, 0.0, 0.0, 1.3, 1.0, 18.4, 25.2, 0.7, 0.0, 0.6, -8.5, 0.0, 0.0, -0.1, -1.4, -26.5, -39.9, 0.1, -0.8, 2.0, -12.9, 0.0, 0.0, -0.2, -2.7, -35.3, -54.7, -0.9, -2.5, 11.9, -18.6, 0.0, 0.0, -0.2, -5.2, -34.7, -76.3, -1.4, -1.7, 16.7, -13.3, 0.0, 0.0, 0.9, -3.1, -12.5, -37.3, -0.9, -0.8, 12.7, -5.7, 0.0, 0.0, 0.8, -0.3, 6.1, 3.7, -0.4, -0.5, 9.0, -2.0, 0.0, 0.0, 0.8, 0.9, 12.3, 19.3};
//        clusterCentre[3-len] = new double[]{69.0, 0.0, 176.0, 75.0, 82.0, 145.0, 357.0, 129.0, 101.0, 49.0, 46.0, 71.0, 47.0, 80.0, 0.0, 44.0, 32.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 60.0, 0.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 52.0, 0.0, 0.0, 0.0, 48.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 16.0, 56.0, 0.0, 0.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 36.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 48.0, 0.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 56.0, 0.0, 0.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 48.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 40.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 44.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 16.0, 36.0, 48.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 40.0, 32.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.8, -0.4, 0.0, 0.0, 0.7, 0.7, 5.5, 9.0, 0.3, -1.4, 4.5, 0.0, 0.0, 0.0, 1.9, 0.9, 11.6, 16.1, 0.2, -1.8, 2.3, 0.0, 0.0, 0.0, 1.3, 0.3, 2.7, 3.8, -0.1, 0.0, 0.4, -3.7, 0.0, 0.0, -1.3, -0.9, -10.0, -15.2, -0.6, 0.0, 1.1, -1.1, 0.0, 0.0, -0.5, 0.3, 0.3, 1.9, 0.8, -1.4, 3.4, 0.0, 0.0, 0.0, 1.6, 0.7, 6.2, 9.9, 0.8, 0.0, 1.2, -9.1, 0.0, 0.0, 0.2, 0.9, -23.8, -14.3, 1.2, 0.0, 2.1, -11.4, 0.0, 0.0, 0.3, 2.6, -23.6, 3.9, 0.7, 0.0, 6.4, -10.1, 0.0, 0.0, 0.8, 2.6, -7.4, 20.1, 0.1, 0.0, 9.4, -6.3, 0.0, 0.0, 1.0, 1.2, 3.1, 11.2, 0.0, -0.4, 9.8, -3.9, 0.0, 0.0, 0.9, 1.0, 8.0, 14.6, 0.1, -0.6, 6.8, -0.8, 0.0, 0.0, 0.9, 0.8, 11.8, 16.2};
//        clusterCentre[4-len] = new double[]{69.0, 1.0, 160.0, 71.0, 75.0, 156.0, 322.0, 172.0, 105.0, 18.0, 8.0, 70.0, 14.0, 98.0, 0.0, 36.0, 36.0, 0.0, 0.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 68.0, 0.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 36.0, 0.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 52.0, 0.0, 0.0, 12.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 12.0, 44.0, 20.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 52.0, 0.0, 0.0, 0.0, 32.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 52.0, 0.0, 0.0, 12.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 48.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 44.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 44.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 40.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 36.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.2, 0.0, 8.0, -1.2, 0.0, 0.0, 0.7, 1.6, 12.3, 21.9, 0.2, -0.8, 4.7, 0.0, 0.0, 0.0, 1.3, 1.4, 15.1, 26.3, 0.4, -3.2, 1.7, 0.0, 0.0, 0.0, 1.1, -0.6, -4.0, -8.8, 0.1, 0.0, 1.0, -7.4, 0.0, 0.0, -1.2, -1.2, -18.2, -25.8, -0.4, -0.4, 5.7, -0.9, 0.0, 0.0, -0.6, 1.1, 11.4, 19.9, 0.1, -0.8, 1.7, 0.0, 0.0, 0.0, 1.3, 0.1, 3.5, 4.3, 0.2, 0.0, 1.3, -6.6, 0.0, 0.0, -0.6, 0.6, -15.6, -9.5, 0.0, 0.0, 3.4, -7.8, 0.0, 0.0, -0.3, 3.4, -12.6, 22.0, -0.4, 0.0, 9.5, -9.1, 0.0, 0.0, 0.2, 5.2, -2.9, 44.9, -0.6, 0.0, 10.5, -7.1, 0.0, 0.0, 0.3, 3.5, 3.3, 28.5, -0.3, 0.0, 7.5, -2.5, 0.0, 0.0, 0.3, 1.7, 8.5, 19.7, -0.3, 0.0, 6.6, -0.8, 0.0, 0.0, 0.2, 1.3, 11.8, 23.5};
//        clusterCentre[5-len] = new double[]{56.0, 1.0, 165.0, 64.0, 81.0, 174.0, 401.0, 149.0, 39.0, 25.0, 37.0, -17.0, 31.0, 53.0, 0.0, 48.0, 0.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 64.0, 0.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 32.0, 24.0, 0.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 20.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 0.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 52.0, 0.0, 0.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 32.0, 52.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 48.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 44.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 40.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 0.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.6, 0.0, 7.2, 0.0, 0.0, 0.0, 0.4, 1.5, 17.2, 26.5, 0.0, 0.0, 5.5, 0.0, 0.0, 0.0, 0.1, 1.7, 17.6, 29.5, 0.3, -1.6, 0.9, 0.0, 0.0, 0.0, -0.3, 0.4, -1.5, 1.3, 0.1, -6.4, 0.0, 0.0, 0.0, 0.0, -0.3, -1.6, -15.3, -25.5, -0.3, 0.0, 4.2, -0.9, 0.0, 0.0, 0.4, 0.7, 8.3, 12.3, 0.2, 0.0, 2.2, 0.0, 0.0, 0.0, -0.2, 0.8, 6.6, 11.7, 0.4, 0.0, 1.0, -8.8, 0.0, 0.0, 0.5, -0.6, -21.6, -26.8, 0.4, 0.0, 2.6, -7.9, 0.0, 0.0, 0.8, 2.0, -16.4, 1.2, 0.0, 0.0, 5.8, -7.7, 0.0, 0.0, 0.9, 3.8, -5.7, 27.7, -0.2, 0.0, 9.5, -5.0, 0.0, 0.0, 0.5, 2.6, 11.8, 34.6, -0.4, 0.0, 11.0, -2.4, 0.0, 0.0, 0.4, 2.6, 21.6, 43.4, -0.5, 0.0, 8.5, 0.0, 0.0, 0.0, 0.2, 2.1, 20.4, 38.8};
//        clusterCentre[6-len] = new double[]{40.0, 0.0, 176.0, 74.0, 92.0, 216.0, 362.0, 161.0, 86.0, 83.0, 63.0, 77.0, 72.0, 86.0, 0.0, 36.0, 32.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 64.0, 0.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 20.0, 0.0, 0.0, 0.0, 48.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 44.0, 0.0, 0.0, 8.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 68.0, 0.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 64.0, 0.0, 0.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 32.0, 56.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 48.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 44.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 36.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.8, -1.3, 0.0, 0.0, 0.4, 1.3, 3.0, 13.9, 0.4, 0.0, 6.2, 0.0, 0.0, 0.0, 1.5, 2.9, 16.1, 40.4, 0.5, 0.0, 5.1, 0.0, 0.0, 0.0, 1.2, 1.6, 16.3, 29.7, -0.2, -4.3, 0.5, 0.0, 0.0, 0.0, -0.9, -2.0, -8.9, -24.5, -0.2, 0.0, 0.4, -2.6, 0.0, 0.0, -0.4, -0.2, -5.3, -6.6, 0.5, 0.0, 5.4, 0.0, 0.0, 0.0, 1.2, 2.2, 18.3, 35.4, 0.8, 0.0, 1.3, -8.3, 0.0, 0.0, -0.9, -0.1, -24.7, -25.8, 1.3, 0.0, 1.8, -12.0, 0.0, 0.0, -0.2, 3.2, -30.8, 5.0, 1.7, 0.0, 3.8, -14.0, 0.0, 0.0, 0.5, 5.8, -26.8, 38.1, 0.4, 0.0, 8.7, -7.8, 0.0, 0.0, 0.0, 4.2, -1.5, 33.7, 0.1, 0.0, 7.4, -2.0, 0.0, 0.0, 0.0, 2.5, 12.6, 33.6, 0.1, 0.0, 5.4, 0.0, 0.0, 0.0, 0.0, 1.4, 14.0, 25.7};
//        clusterCentre[7-len] = new double[]{75.0, 0.0, 190.0, 80.0, 91.0, 193.0, 371.0, 174.0, 121.0, -16.0, 13.0, 64.0, -2.0, 63.0, 0.0, 52.0, 44.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 20.0, 36.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 40.0, 0.0, 0.0, 0.0, 60.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 56.0, 36.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 32.0, 0.0, 0.0, 0.0, 56.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 80.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 52.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 48.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 52.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 48.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 56.0, 44.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.2, 0.0, 6.1, -1.0, 0.0, 0.0, 0.6, 2.1, 13.6, 30.8, 0.0, 0.0, 1.7, -1.0, 0.6, 0.0, 1.3, 1.5, 3.7, 14.5, 0.1, -5.2, 1.4, 0.0, 0.0, 0.0, 0.8, -0.6, -10.7, -15.6, 0.4, -3.9, 0.0, 0.0, 0.0, 0.0, -0.8, -1.7, -10.1, -22.0, 0.0, 0.0, 5.7, -1.0, 0.0, 0.0, -0.1, 1.2, 14.1, 22.5, 0.0, -2.5, 0.8, 0.0, 0.0, 0.0, 1.0, 0.4, -4.8, -2.7, 0.1, -6.0, 0.0, 0.0, 0.0, 0.0, -0.8, -0.6, -24.0, -29.7, 0.0, 0.0, 2.0, -6.4, 0.0, 0.0, 0.2, 2.9, -12.6, 15.2, -0.1, 0.0, 8.4, -10.0, 0.0, 0.0, 0.6, 5.9, -3.9, 52.7, -0.3, 0.0, 15.2, -8.4, 0.0, 0.0, 0.9, 5.1, 17.7, 70.7, -0.4, 0.0, 13.5, -4.0, 0.0, 0.0, 0.9, 3.9, 25.5, 62.9, -0.3, 0.0, 9.0, -0.9, 0.0, 0.0, 0.9, 2.9, 23.3, 49.4};
        //clusterCentre[8-len] = new double[]{75.0, 1.0, 159.0, 59.0, 163.0, 147.0, 431.0, 242.0, 97.0, 56.0, -136.0, 75.0, -144.0, 72.0, 0.0, 140.0, 0.0, 0.0, 0.0, 100.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 116.0, 0.0, 0.0, 0.0, 60.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 92.0, 72.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 128.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 64.0, 64.0, 0.0, 0.0, 0.0, 84.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 96.0, 64.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 140.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 132.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 20.0, 104.0, 0.0, 0.0, 12.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 56.0, 92.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 92.0, 20.0, 60.0, 0.0, 132.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 148.0, 0.0, 0.0, 0.0, 104.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -1.5, 0.0, 3.6, 0.0, 0.0, 0.0, 0.3, -1.1, 25.2, 15.3, -1.7, 0.0, 6.3, 0.0, 0.0, 0.0, 1.0, -1.5, 36.5, 23.0, -0.2, 0.0, 6.4, -3.9, 0.0, 0.0, 0.7, -0.4, 15.4, 11.8, 1.9, -2.9, 0.0, 0.0, 0.0, 0.0, -0.6, 1.2, -18.5, -8.0, -0.7, -3.3, 3.5, 0.0, 0.0, 0.0, -0.3, -0.3, 0.7, -1.9, -1.1, 0.0, 6.1, -2.7, 0.0, 0.0, 0.8, -0.8, 20.6, 13.6, 4.0, -10.5, 0.0, 0.0, 0.0, 0.0, -1.9, 4.5, -73.5, -17.7, 8.4, -29.6, 0.0, 0.0, 0.0, 0.0, -2.3, 9.5, -195.3, -77.5, 15.1, 0.0, 2.5, -47.1, 0.0, 0.0, -3.1, 18.8, -242.4, -9.3, 9.7, 0.0, 4.4, -28.4, 0.0, 0.0, 0.4, 15.6, -118.3, 72.0, -0.5, 0.0, 4.2, -1.8, 5.8, 0.0, 0.1, 3.8, 34.9, 80.5, -4.5, 0.0, 12.0, 0.0, 0.0, 0.0, 0.1, -3.7, 88.8, 43.7};
        //clusterCentre[9-len] = new double[]{54.0, 0.0, 172.0, 95.0, 138.0, 163.0, 386.0, 185.0, 102.0, 96.0, 34.0, 70.0, 66.0, 75.0, 0.0, 40.0, 80.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 56.0, 52.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 116.0, 0.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 64.0, 0.0, 0.0, 0.0, 88.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 92.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 128.0, 0.0, 0.0, 0.0, 24.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 36.0, 76.0, 0.0, 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 40.0, 28.0, 60.0, 0.0, 96.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 48.0, 20.0, 56.0, 24.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 88.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 76.0, 0.0, 0.0, 28.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 72.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 4.5, -2.8, 0.0, 0.0, 0.3, 2.5, -2.2, 19.8, 0.8, -0.4, 6.4, -1.3, 0.0, 0.0, 0.7, 2.7, 14.2, 37.9, -0.2, -0.6, 4.4, 0.0, 0.0, 0.0, 0.5, 0.2, 24.7, 26.2, -1.0, -5.3, 1.8, 0.0, 0.0, 0.0, -0.5, -2.5, -8.0, -28.5, 0.5, 0.0, 1.7, -2.7, 0.0, 0.0, -0.2, 1.0, -9.4, -1.2, 0.4, 0.0, 4.9, 0.0, 0.0, 0.0, 0.6, 1.4, 31.3, 42.7, -0.8, 0.0, 0.7, -3.8, 6.5, 0.0, 0.3, -3.3, 18.7, -13.6, -0.9, 0.0, 2.2, -4.1, 7.4, 0.0, 0.5, -2.4, 20.9, -2.6, 0.0, 0.0, 5.8, -4.1, 4.0, -0.5, 0.4, 0.3, 20.4, 23.3, 0.7, 0.0, 10.0, -5.7, 0.0, 0.0, 0.5, 2.2, -3.0, 20.7, 1.3, 0.0, 11.1, -3.4, 0.0, 0.0, 0.4, 3.4, 11.5, 48.2, 0.9, 0.0, 9.5, -2.4, 0.0, 0.0, 0.3, 3.4, 12.3, 49.0};
        //clusterCentre[10-len] = new double[]{13.0, 0.0, 169.0, 51.0, 100.0, 167.0, 321.0, 174.0, 91.0, 107.0, 66.0, 52.0, 88.0, 84.0, 0.0, 36.0, 48.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 44.0, 36.0, 0.0, 0.0, 44.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 64.0, 0.0, 0.0, 0.0, 48.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 36.0, 0.0, 0.0, 0.0, 52.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 64.0, 0.0, 0.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 44.0, 40.0, 0.0, 0.0, 44.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 60.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 32.0, 60.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 32.0, 60.0, 0.0, 0.0, 44.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 40.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 40.0, 0.0, 0.0, 32.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 36.0, 56.0, 0.0, 0.0, 40.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.5, 0.0, 2.7, -6.4, 0.0, 0.0, 0.9, 1.7, -10.5, 7.1, 0.1, -1.2, 19.1, -2.3, 0.0, 0.0, 1.4, 4.3, 36.7, 84.8, -0.4, -2.3, 21.7, 0.0, 0.0, 0.0, 0.7, 2.6, 66.7, 95.8, -0.2, -9.0, 3.2, 0.0, 0.0, 0.0, -1.1, -2.9, -14.1, -39.0, 0.5, 0.0, 1.8, -12.9, 0.0, 0.0, 0.4, -0.4, -38.7, -42.1, -0.1, -1.6, 19.9, -0.7, 0.0, 0.0, 1.0, 3.3, 40.4, 65.4, 0.4, 0.0, 6.7, -24.4, 0.0, 0.0, -1.2, 0.4, -61.2, -59.9, 0.9, -0.5, 11.9, -43.3, 0.0, 0.0, 0.8, 3.4, -111.4, -95.1, 2.0, -0.8, 19.8, -48.4, 0.0, 0.0, 1.6, 8.7, -114.5, -72.8, 2.0, 0.0, 31.0, -25.7, 0.0, 0.0, 0.8, 5.9, 29.2, 85.8, 0.6, 0.0, 19.5, -11.4, 0.0, 0.0, 0.8, 3.3, 20.1, 49.1, 0.0, -0.6, 12.2, -2.8, 0.0, 0.0, 0.9, 2.2, 13.5, 31.1};
        //clusterCentre[11-len] = new double[]{44.0, 0.0, 169.0, 80.0, 109.0, 128.0, 382.0, 195.0, 60.0, -34.0, 112.0, 154.0, 7.0, 63.0, 20.0, 80.0, 0.0, 0.0, 0.0, 44.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 52.0, 68.0, 0.0, 0.0, 36.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 28.0, 76.0, 0.0, 0.0, 16.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 76.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 24.0, 76.0, 0.0, 0.0, 0.0, 56.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 60.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 76.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 64.0, 0.0, 0.0, 12.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 32.0, 56.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 36.0, 68.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 44.0, 68.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 0.0, 0.0, 0.0, 24.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -0.9, -0.6, 8.0, 0.0, 0.0, 0.0, -0.3, -1.5, 31.4, 16.1, 0.0, 0.0, 4.5, -4.7, 0.0, 0.0, -0.1, 1.8, -4.2, 13.4, 0.9, 0.0, 1.8, -10.9, 0.0, 0.0, 0.4, 2.4, -38.9, -14.5, 0.5, -5.6, 0.0, 0.0, 0.0, 0.0, 0.2, -1.0, -21.2, -31.4, -0.9, -1.1, 8.6, 0.0, 0.0, 0.0, -0.2, -1.8, 31.3, 13.0, 0.5, 0.0, 1.7, -7.8, 0.0, 0.0, 0.4, 2.0, -19.7, 0.7, 2.9, -18.3, 0.0, 0.0, 0.0, 0.0, 0.8, 5.4, -69.5, -8.0, 1.9, 0.0, 3.4, -16.7, 0.0, 0.0, 1.1, 4.1, -50.0, -3.3, 1.6, 0.0, 9.2, -22.7, 0.0, 0.0, 1.4, 6.3, -48.8, 23.0, 0.1, 0.0, 12.8, -15.6, 0.0, 0.0, 0.6, 3.4, -30.0, -1.5, -0.3, 0.0, 9.1, -4.3, 0.0, 0.0, 0.5, 2.0, 5.4, 22.2, -0.7, 0.0, 6.9, 0.0, 0.0, 0.0, 0.4, 1.3, 20.7, 29.2};

        //losowanie poczatkowych srodkow
        randClusterCentre(transpose(data), clusterAmount);
        clusterCentreTranslated = new String[clusterAmount];
        //ustawienie dla wszystkich punktów najblizszego srodka
        clusterCentreList = new int[data.length];
        
        System.out.println("Srodki:");
        for (double[] center : clusterCentre) {
            System.out.println(Arrays.toString(center));
        }

        
        int counter = 0;
        while (isChanged) {
            counter++;
            isChanged = false;
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
                double distance = 0, tempDistance = 0;
                int nearestCentre = 0; //pozycja w tabeli cluserCentre najblizszego srodka
                if (metric == 3) {
                    knm.doInverseCovarianceMatrix(data);
                }
                for (int j = 0; j < clusterCentre.length; j++) {
                    //knm.euclideanDistance(p1, p2);
                    /*if (j == 0) {
                        distance = knm.euclideanDistance(row, clusterCentre[j]);
                        nearestCentre = j;
                        continue;
                    }
                    if ((tempDistance = knm.euclideanDistance(row, clusterCentre[j])) < distance) {
                        distance = tempDistance;
                        nearestCentre = j;
                    }*/
                    if (metric == 0) {
                        tempDistance = knm.euclideanDistance(row, clusterCentre[j]);
                    } else if (metric == 1) {
                        tempDistance = knm.ManhattanDistance(row, clusterCentre[j]);
                    } else if (metric == 2) {
                        tempDistance = knm.maxDistance(row, clusterCentre[j]);
                    } else if (metric == 3) {
                        //System.out.println("Dane: " + Arrays.toString(row) + ", " + Arrays.toString(clusterCentre[j]));
                        tempDistance = knm.mahalanobisDistance(row, clusterCentre[j]);
                    }
                    if (j == 0 || tempDistance < distance) {
                        distance = tempDistance;
                        nearestCentre = j;
                    }
                }

                //sprawdzenie, czy wartosc w poprzedniej iteracji byla taka sama, jesli nie ustawienie nowej i ustawienie flagi isChanged na true
                if (clusterCentreList[i] != nearestCentre) {
                    clusterCentreList[i] = nearestCentre;
                    isChanged = true;
                }
                //System.out.println(nearestCentre);
            }
            //zmiana srodkow skupien
            changeClusterCentre(data, clusterAmount);
        }

        System.out.println("Srodki:");
        for (double[] center : clusterCentre) {
            System.out.println(Arrays.toString(center));
        }
        System.out.println("ilosc iteracji: " + counter);
        System.out.println("-----------------------------");
        System.out.println(Arrays.toString(clusterCentreList));

        translateClusterCentreToDecisionClass(data, decisionClass, metric);
        translateDecisionClassToIntArray(decisionClass);
        generateMistakesMatrix();

        String[] temp = new String[clusterCentre.length];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = "srodek " + clusterCentreTranslated[i];
        }

        /*System.out.println("Wartosci decision class i clustercentrelist: ");
        for (int i = 0; i < decisonClassClusterCentre.length; i++) {
            System.out.println(decisonClassClusterCentre[i] + " " + clusterCentreList[i]);
        }*/
        System.out.println("");
        System.out.println("\n macierz pomylek:");
        for (int[] row : mistakesMatrix) {
            System.out.println(Arrays.toString(row));
        }

        /*double[][] allXY = concatenate(data, clusterCentre);
        double[] x = new double[allXY.length];
        double[] y = new double[allXY.length];
        double[] z = new double[allXY.length];
        for (int i = 0; i < allXY.length; i++) {
            x[i] = allXY[i][0];
            y[i] = allXY[i][1];
            z[i] = allXY[i][2];
        }
        //DrawPlot.getInstance().draw2D_desc(x, y, concatenate(decisionClass, temp));//concatenate(data, clusterCentre);
        DrawPlot.getInstance().draw3D_desc(x, y, z, concatenate(decisionClass, temp));//concatenate(data, clusterCentre);*/
    }

    /*
    Wylosowanie poczatkowych srodkow skupien
     */
    public void randClusterCentre(double[][] data, int amount) {
        System.out.println("Rozmiar: " + data.length + ", " + data[0].length);
        double[][] minMax = getMinMax(data);
        clusterCentre = new double[amount][minMax[0].length];
        System.out.println(minMax[0].length + " ");
        for (int i = 0; i < amount; i++) {
            for (int j = 0; j < minMax[0].length; j++) {
                /*if (minMax[0][j] < minMax[1][j]) {
                    clusterCentre[i][j] = ThreadLocalRandom.current().nextDouble(minMax[0][j], minMax[1][j]);
                }else{
                    clusterCentre[i][j] = ThreadLocalRandom.current().nextDouble(minMax[1][j], minMax[0][j]);
                }*/
                try {
                    clusterCentre[i][j] = ThreadLocalRandom.current().nextDouble(minMax[0][j], minMax[1][j]);
                } catch (Exception e) {
                    System.err.println("Wartosci: " + minMax[0][j] + " " + minMax[1][j]);
                }
            }
        }

        /*for (double[] dob : clusterCentre) {
            System.out.println(Arrays.toString(dob));
        }*/
    }

    /**
     * Zmiana srodkow skupien za pomoca sredniej arytmetycznej dla
     * poszczegolnych kolumn dla danego srodka
     */
    private void changeClusterCentre(double[][] data, int clusterAmount) {
        //double[][][] colSum = new double[clusterCentre.length][data.length][data[0].length]; //suma poszczegolnych kolumn dla poszczegolnych srodkow skupien
        double[][] colSum = new double[clusterCentre.length][data[0].length]; //suma poszczegolnych kolumn dla poszczegolnych srodkow skupien        
        clusterPointsAmount = new int[clusterCentre.length];
        for (int i = 0; i < clusterCentreList.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                colSum[clusterCentreList[i]][j] += data[i][j];
            }
            clusterPointsAmount[clusterCentreList[i]]++;
        }

        /*System.out.println("Wartosci divide val: " + Arrays.toString(clusterPointsAmount));
        for (double[] col : colSum) {
            System.out.println(Arrays.toString(col));
        }*/
        for (int i = 0; i < clusterCentre.length; i++) {
            for (int j = 0; j < clusterCentre[i].length; j++) {
                //colSum[i][j] = colSum[i][j] / divideVal[i]; 
                clusterCentre[i][j] = colSum[i][j] / clusterPointsAmount[i];
            }
        }
        
        //jesli ktorys cluster nie ma punktow, losuj ponownie wszystkie dane
        for (int i = 0; i < clusterCentre.length; i++) {
            if(clusterPointsAmount[i]==0){
                randClusterCentre(transpose(data), clusterAmount);
                isChanged = true;
                return;
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

    /*private void changeClusterCentre(double[][] data) {
        //float[][] colSum = new float[clusterCentre.length][data[0].length]; //suma poszczegolnych kolumn dla poszczegolnych srodkow skupien
        BigDecimal[][] colSum = new BigDecimal[clusterCentre.length][data[0].length];
        for (BigDecimal[] row : colSum) {
            Arrays.fill(row, BigDecimal.ZERO);
        }        
        int[] divideVal = new int[clusterCentre.length]; //ile punktow zostalo przydzielonych do poszczegolnych skupien;
        System.err.println("Wielkosc colSum: " + colSum.length + ", " + colSum[0].length);
        System.err.println("Wielkosc tablicy dzielenia: " + divideVal.length);
        for (int i = 0; i < clusterCentreList.length; i++) {
            //colSum[clusterCentreList[i]] += data[i];
            //Arrays.fill(colSum[i], BigDecimal.ZERO);
            for (int j = 0; j < data[i].length; j++) {
                //colSum[clusterCentreList[i]][j] += (float) data[i][j];
                //System.out.println("colSum: " + colSum[clusterCentreList[i]][j]);
                //double length = data[i][j].length;
                colSum[clusterCentreList[i]][j] = colSum[clusterCentreList[i]][j].add(new BigDecimal(data[i][j]).setScale(1, BigDecimal.ROUND_UP));
            }
            divideVal[clusterCentreList[i]]++;
        }
        System.out.println("Wartosci divide val: " + Arrays.toString(divideVal));
        
        for (BigDecimal[] col : colSum) {
            System.out.println(Arrays.toString(col));
        }
        for (int i = 0; i < clusterCentre.length; i++) {
            for (int j = 0; j < clusterCentre[i].length; j++) {
                //colSum[i][j] = colSum[i][j] / divideVal[i]; 
                //clusterCentre[i][j] = colSum[i][j] / divideVal[i];
                clusterCentre[i][j] = colSum[i][j].divide(new BigDecimal(divideVal[i])).setScale(1, BigDecimal.ROUND_UP).doubleValue();
                //System.out.println(" " + clusterCentre[i][j]);
            }
        }
    }*/

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

    /**
     * Dobór odpowiednich srodkow skupien do klas decyzyjnych
     *
     * @param data
     * @param decisionClass
     * @param metric
     */
    public void translateClusterCentreToDecisionClass(double[][] data, String[] decisionClass, int metric) {
        k_nn_method knm = new k_nn_method();
        double[] row;
        ArrayList<Structure> tempVal;
        //wyliczenie tablicy najblizszych sasiadow dla wszystkich srodkow
        if (metric == 3) {
            knm.doInverseCovarianceMatrix(data);
        }
        for (int i = 0; i < clusterCentre.length; i++) {
            tempVal = new ArrayList<>();
            for (int y = 0; y < data.length; y++) {
                row = data[y];//new double[data[0].length];                
                if (metric == 0) {
                    tempVal.add(new Structure(y, knm.euclideanDistance(row, clusterCentre[i]), decisionClass[y]));//knm.euclideanDistance(row, clusterCentre[j]);
                } else if (metric == 1) {
                    tempVal.add(new Structure(y, knm.ManhattanDistance(row, clusterCentre[i]), decisionClass[y]));
                } else if (metric == 2) {
                    tempVal.add(new Structure(y, knm.maxDistance(row, clusterCentre[i]), decisionClass[y]));
                } else if (metric == 3) {
                    //try{
                    tempVal.add(new Structure(y, knm.mahalanobisDistance(row, clusterCentre[i]), decisionClass[y]));
                    //}catch(Exception ex){
                    //System.err.println("Dane: " + Arrays.toString(row) + ", " + Arrays.toString(clusterCentre[i]) + ", " + decisionClass[y]);
                    //System.err.println(ex);                        
                    //}                    
                }
            }
            //sortowanie
            tempVal = knm.sort(tempVal);
            //pobranie najblizszych sasiadow i pokazanie ktora to klasa decyzyjna z glownego programu
            //ilosc najblizszych sasiadow = , ale jesli wartosc w clusterPointsAmount jest mniejsza od 5 to pobranie tej wartosci z tablicy
            int neighbourCount;
            if (clusterPointsAmount[i] < 5) {
                neighbourCount = clusterPointsAmount[i];
            } else {
                neighbourCount = 20;
            }
            String decisClass = findResolution(tempVal.subList(0, neighbourCount), decisionClass);
            clusterCentreTranslated[i] = decisClass;
            System.out.println("Dla " + i + ", klasa decyzyjna to: " + decisClass);
        }
    }

    /**
     * Wskazanie powiazania srodka z klasa decyzyjna
     *
     * @param data
     * @return
     */
    private String findResolution(List<Structure> data, String[] decisionClass) {
        //int[] results = new int[possibleResults.size()];
        ArrayList<String> possibleRes = getPossibleRes(decisionClass);
        int[] clusterPoints;// = new int[possibleRes.size()];

        while (true) {
            clusterPoints = new int[possibleRes.size()];
            boolean maxDuplicate = false;
            //resultPos = -1;
            int result = 0;
            for (Structure str : data) {
                //results[possibleResults.indexOf(str.getDecClass())]++;
                clusterPoints[possibleRes.indexOf(str.getDecClass())]++;
            }
            if (duplicatesTheHighestValue(clusterPoints)) {
                maxDuplicate = true;
            }

            if (!maxDuplicate) {
                break;
                /* } else if (data.size() == 2) {
                resultPos = -1;
                break;*/
            } else if (maxDuplicate) {
                //System.out.println("przed: " + data.size());
                data = data.subList(0, data.size() - 1);
                Arrays.fill(clusterPoints, 0);
                //System.out.println("po: " + data.size());
            }
        }

        int resultPos = 0;
        //znalezienie najwiekszej wartosci
        for (int i = 1; i < clusterPoints.length; i++) {
            if (clusterPoints[i] > clusterPoints[resultPos]) {
                resultPos = i;
            }
        }

        return possibleRes.get(resultPos);
    }

    /**
     * Zwraca liste mozliwych wartosci z klasy decyzyjnej
     *
     * @param decisionClass
     * @return
     */
    private ArrayList<String> getPossibleRes(String[] decisionClass) {
        ArrayList<String> possibleRes = new ArrayList<>();
        for (int i = 0; i < decisionClass.length; i++) {
            if (!possibleRes.contains(decisionClass[i])) {
                possibleRes.add(decisionClass[i]);
            }
        }
        return possibleRes;
    }

    /**
     * Sprawdza, czy tablica zawiera duplikaty wartosci najwiekszej
     *
     * @param x
     * @return
     */
    public boolean duplicatesTheHighestValue(int[] x) {
        int theHighestValue = x[0];
        int theHighestCounter = 1;
        //znalezienie najwiekszej wartosci
        for (int i = 1; i < x.length; i++) {
            if (x[i] > theHighestValue) {
                theHighestValue = x[i];
                theHighestCounter = 1;
            } else if (x[i] == theHighestValue) {
                theHighestCounter++;
            }
        }
        //jesli theHighestCounter == 1, to nie ma duplikatow, jesli wiekszy to sa
        if (theHighestCounter == 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Zamiana klasy decyzyjnej na tablice intow
     */
    private void translateDecisionClassToIntArray(String[] decisionClass) {
        decisonClassClusterCentre = new int[decisionClass.length];
        for (int i = 0; i < decisionClass.length; i++) {
            //decisonClassClusterCentre[i] = clusterCentreTranslated.//decisionClass[i]
            for (int x = 0; x < clusterCentreTranslated.length; x++) {
                if (clusterCentreTranslated[x].equals(decisionClass[i])) {
                    decisonClassClusterCentre[i] = x;
                    break;
                }
            }
        }
    }

    /**
     * Generowanie macierzy pomylek pion to wartosci z klasy decyzyjnej, poziomo
     * wartosci otrzymane
     */
    private void generateMistakesMatrix() {
        mistakesMatrix = new int[clusterCentreTranslated.length][clusterCentreTranslated.length];
        for (int i = 0; i < decisonClassClusterCentre.length; i++) {
            mistakesMatrix[decisonClassClusterCentre[i]][clusterCentreList[i]]++;
        }
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

    /**
     * laczenie dwoch tabel
     */
    public <T> T[] concatenate(T[] a, T[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    private double[][] deleteEqualCol(double[][] data) {
        double val = 0;
        ArrayList<Integer> colToRemove = new ArrayList<>();
        for (int i = 0, y = 0; y < data[0].length; i++) {
            if (i == data[0].length - 1) {
                colToRemove.add(0, y);
                y++;
                i = -1;
                //wartosci w kolumnie sa rowne, czyli do usuniecia                
                continue;
            }
            if (i == 0) {
                val = data[i][y];
            } else if (val != data[i][y]) {
                //wartosci w kolumnie sa rozne mozna przejsc do nastepnej                
                y++;
                i = -1;
            }
        }
        //System.out.println("Do usuniecia: " + colToRemove.toString());
        double[][] temp = new double[data.length][data[0].length - colToRemove.size()];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0, y = 0; j < data[i].length; j++) {
                if (colToRemove.contains(j)) {
                    continue;
                }
                temp[i][y++] = data[i][j];
            }
        }
        //System.out.println("PO CZYSZCZENIU");        
        /*for (double[] stemp : temp) {
            System.out.println(Arrays.toString(stemp));
        }*/
        return temp;
    }
}
