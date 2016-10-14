/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author KonradD
 */
public class DataOperation {
    
    public DataOperation(){
        
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
}
