/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author KonradD
 */
public class DataOperation {

    public DataOperation() {

    }

    private int ilosc;

    public double[] normalizacjaZmiennych(double[] tab) {
        double srednia = srednia(tab);
        System.out.println("srednia: " + srednia);
        double odchylenieStandardowe = odchylenieStandardowe(tab, srednia);
        System.out.println("odchylenie standardowe: " + odchylenieStandardowe);
        double[] res = new double[ilosc];
        for (int i = 0; i < ilosc; i++) {
            res[i] = (tab[i] - srednia) / odchylenieStandardowe;
        }
        for (int i = 0; i < ilosc; i++) {
            System.out.println(tab[i] + " " + res[i]);
        }
        return res;
    }

    private double srednia(double[] tab) {
        double suma = 0;
        for (double war : tab) {
            ilosc++;
            suma += war;
        }
        return suma / ilosc;
    }

    private double odchylenieStandardowe(double[] tab, double srednia) {
        double sum = 0;
        for (double war : tab) {
            sum += Math.pow(war - srednia, 2);
        }
        sum /= ilosc;
        return Math.sqrt(sum);
    }
    
    
    //1. srednia
    public double Wariancja(double[] tab){
        double s = 0;
        double srednia = srednia(tab);
        for(int i = 0; i < tab.length; i++){
            s += Math.pow(tab[i] - srednia, 2);
        }
        return s/tab.length;
    }
    
    

    public String[] dyskretyzacja(double[] tab, int rangeNumbers) {
        double[] temp = Arrays.copyOf(tab, tab.length);
        String[] res = new String[tab.length];
        Arrays.sort(temp);
        System.out.println(temp[0] + " " + temp[temp.length - 1]);
        double val = temp[temp.length - 1] - temp[0];
        double rangeValue = val / rangeNumbers;
        System.out.println(val + " " + rangeValue);
        double min = temp[0];
        System.out.println(Arrays.toString(tab));
        for (int i = 0; i < tab.length; i++) {
            int tV = (int) ((tab[i] - min) / rangeValue);
            if(tV == rangeNumbers){
                tV --;
            }
            res[i] = tV + 1 + "";
        }
        //System.out.println(Arrays.toString(temp));
        wynikiPoDyskretyzacji(res, rangeNumbers);
        return res;
    }
    
    //ile danych w danym przedziale, tab - tablica po dyskretyzacji, rangenumbers - liczba przedzialow
    private void wynikiPoDyskretyzacji(String[] tab, int rangeNumbers){
        int[] rangeVal = new int[rangeNumbers];
        for(int i = 0, temp = 0; i < tab.length; i++){
            temp = Integer.parseInt(tab[i]);
            System.out.println(temp);
            rangeVal[temp - 1] ++;
        }
        System.out.println(Arrays.toString(rangeVal));
    }
}
