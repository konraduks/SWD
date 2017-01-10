/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eksploracjaDanych;

import cern.colt.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author KonradD
 */
public class Separate {

    private int possiblePointsToCut; //mozliwa ilosc punktow do odciecia w tej iteracji
    private int possibleCutDirection; //0 - dol lub lewo, 1 - gora lub prawy
    private int possibleColumnIndex; //kolumna z najwieksza iloscia wartosci do odciecia
    private List<Data> possibleList; //mozliwa kolumna z najwieksza iloscia danych do odciecia

    private List<Line> lines = new ArrayList<>(); // linie odseparowujace jak najwiecej elementow danej klasy

    private String partialRes = ""; //czesciowy wynik, jesli wiemy, ze odcinana czesc ma 0, to reszta musi miec 1 i to tutaj doklejamy
    private List<String> results = new ArrayList<>(); //kolumny z doklejona zmieniona na 0 1 klasa decyzyjna
    private List<String> deletedPoints = new ArrayList<>(); //punkty usuniete
    

    private double[][] data;
    private String[] decisionClass;

    private boolean flag = true;

    //public double[] xL = {1.9, 1.9, 2, 6};
    //public double[] yL = {0, 3, 2.7, 2.7};
    private List<Double> xL = new ArrayList<>(); //x linii
    private List<Double> yL = new ArrayList<>(); //y linii
    private List<Double> xP = new ArrayList<>(); //x punktow
    private List<Double> yP = new ArrayList<>(); //y punktow

    private boolean isNeedDraw;
    private double[][] minMax;

    public Separate() {

    }

    void startSeparate(double[][] data, String[] decisionClass, boolean draw) {
        /*for(int i = 0; i < data.length; i++){
            System.out.println(Arrays.toString(data[i]) + " " + decisionClass[i]);
        }*/
//        possiblePointsToCut = 0;
//        for (int i = 0; i < data[0].length; i++) {
//            List<Data> dataTemp = new ArrayList<>();
//            for (int y = 0; y < data.length; y++) {
//                dataTemp.add(new Data(y, data[y][i], decisionClass[y]));
//            }
//            Collections.sort(dataTemp);
//            System.out.println(dataTemp.toString());
//            checkLengthTheSameDec(dataTemp, i);
//        }
//        
//        System.out.println("Kolumna z najwieksza iloscia do odciecia: " + possibleColumnIndex + " ilosc do odciecia " + possiblePointsToCut + " kierunek: " + possibleCutDirection);
//        //tutaj trzeba dodac wyznaczanie linii
        this.data = data;
        this.decisionClass = decisionClass;
        isNeedDraw = draw;
        if (isNeedDraw) {
            minMax = getMinMax(data);
        }
        System.out.println(Arrays.toString(minMax[0]));
        System.out.println(Arrays.toString(minMax[1]));
        separateFun(/*data, decisionClass*/);
        System.out.println(lines.toString());
        System.out.println(results.toString());
        System.out.println("Usuniete punkty: " + deletedPoints.size());
        System.out.println(deletedPoints.toString());
    }

    private void separateFun(/*double[][] data, String[] decisionClass*/) {
        while (flag) {
            possiblePointsToCut = 0;
            System.out.println("-------------------------------------");
            for (int i = 0; i < data[0].length; i++) {
                List<Data> dataTemp = new ArrayList<>();
                for (int y = 0; y < data.length; y++) {
                    dataTemp.add(new Data(y, data[y][i], decisionClass[y]));
                }
                Collections.sort(dataTemp);
                System.out.println(dataTemp.toString());
                checkLengthTheSameDec(dataTemp, i);
            }

            System.out.println("Kolumna z najwieksza iloscia do odciecia: " + possibleColumnIndex + " ilosc do odciecia " + possiblePointsToCut + " kierunek: " + possibleCutDirection);
            //tutaj trzeba dodac wyznaczanie linii

            //jesli jest 0 to znaczy, ze jest gdzies XOR, usuwamy jedna krote z danych
            if (possiblePointsToCut == 0) {
                System.out.println("Houston we have XOR!");
                deleteOneRow();
                continue;
            }

            if (data.length - possiblePointsToCut != 0) {
                separateLines();
            }
            cutTables(/*data, decisionClass*/);
            /*} else {
                return;
            }*/
        }
        //System.out.println("eksploracjaDanych.Separate.separateFun() END");
    }

    /**
     * Funkcja wyznaczajaca linie podzialu
     */
    private void separateLines() {
        //possibleColumnIndex
        int pos = possiblePointsToCut;
        double p1 = 0, p2 = 0;

        if (possibleCutDirection == 0) {
            p1 = possibleList.get(possibleList.size() - 1 - pos).getValue();
            p2 = possibleList.get(possibleList.size() - pos).getValue();
        } else if (possibleCutDirection == 1) {
            p1 = possibleList.get(pos - 1).getValue();
            p2 = possibleList.get(pos).getValue();
        }
        if (isNeedDraw) {
            if (possibleColumnIndex == 0) {
                xL.add((p1 + p2) / 2);
                xL.add((p1 + p2) / 2);
                yL.add(minMax[1][0]);
                yL.add(minMax[1][1]);
                //yL.add(10.0);
                //yL.add(-10.0);  
            } else if (possibleColumnIndex == 1) {
                xL.add(minMax[0][0]);
                xL.add(minMax[0][1]);
                //xL.add(10.0);
                //xL.add(-10.0);
                yL.add((p1 + p2) / 2);
                yL.add((p1 + p2) / 2);
            }
        }
        //new Line(possibleColumnIndex, double wyznaczone przez ostatni punkt i najblizszy temu punkt /2);
        lines.add(new Line(possibleColumnIndex, (p1 + p2) / 2));
    }

    /**
     * Funkcja zmniejszajaca zbior danych o krotki do wyrzucenia
     */
    private void cutTables(/*double[][] data, String[] decisionClass*/) {
        //int[] forbidden = new int[possibleList.size()];
        double[][] dataTemp = new double[data.length - possiblePointsToCut][data[0].length];
        //System.out.println(dataTemp.length + " " + dataTemp[0].length);
        String[] decTemp = new String[decisionClass.length - possiblePointsToCut];
        List<Integer> forbidden = new ArrayList<>();
        //int length = 
        //possiblePointsToCut
        int length = possiblePointsToCut;
        if (possibleCutDirection == 1) {
            for (int i = 0; i < length; i++) {
                //forbidden[i] = possibleList.get(i).getLp();
                forbidden.add(possibleList.get(i).getLp());
            }
        } else if (possibleCutDirection == 0) {
            for (int i = 0, iterator = possibleList.size() - 1; i < length; i++) {
                //forbidden[i] = possibleList.get(i).getLp();
                forbidden.add(possibleList.get(iterator--).getLp());
            }
        }

        //System.out.println(possibleList.toString());
        for (int i = 0, iterator = 0; i < data.length; i++) {
            if (!forbidden.contains(i)) {
                System.out.println(Arrays.toString(data[i]));
                dataTemp[iterator] = data[i];
                decTemp[iterator++] = decisionClass[i];
            } //dodanie wyrzucanych wartosci do results
            else if (data.length - possiblePointsToCut == 0) {
                xP.add(data[i][0]);
                yP.add(data[i][1]);
                results.add(Arrays.toString(data[i]) + " " + partialRes);
            } else {
                xP.add(data[i][0]);
                yP.add(data[i][1]);
                results.add(Arrays.toString(data[i]) + " " + partialRes + possibleCutDirection);
            }
        }
        int direction = possibleCutDirection == 0 ? 1 : 0;
        partialRes += direction + " ";
        if (data.length - possiblePointsToCut == 0) {
            flag = false;
            return;
        }
        System.out.println("eksploracjaDanych.Separate.cutTables()1");
        data = dataTemp;
        decisionClass = decTemp;
        System.out.println("eksploracjaDanych.Separate.cutTables()2");
        for (int i = 0; i < data.length; i++) {
            System.out.println(Arrays.toString(data[i]) + " " + decisionClass[i]);
        }
        System.out.println("eksploracjaDanych.Separate.cutTables()3");
    }

    /**
     * Sprawdza dlugosc tej samej klasy decyzyjnej dla posortowanych danych - od
     * gory i od dolu dodac modul do sprawdzenia, czy nie ma XOR !!!
     */
    private void checkLengthTheSameDec(List<Data> dataTemp, int columnIndex) {
        int down;
        int up = down = 0;
        //od gory
        int index = 0;
        String decision = dataTemp.get(index).getDecision();
        while (index != dataTemp.size() && dataTemp.get(index).getDecision().equals(decision)) {
            index++;
        }
        //sprawdzenie, czy nie ma XOR
        int checkPos = index - 1;
        if (checkPos < dataTemp.size() - 1 && dataTemp.get(checkPos).getValue() == dataTemp.get(checkPos + 1).getValue()) {
            System.out.println("XOR XOR ALERT!!! CHECKLENGTHSAMEDEC UP");
            //index = removeXORDown(dataTemp, index);
            index = removeXORDown(dataTemp, checkPos);
        }
        /*try {
            if (index < dataTemp.size() - 1 && dataTemp.get(index).getValue() == dataTemp.get(index + 1).getValue()) {
            //removeXOR(index);            
            System.out.println("XOR XOR ALERT!!! CHECKLENGTHSAMEDEC UP");
        }
        } catch (Exception e) {
            System.err.println("wielkosc index: " + index + " wielkosc datatemp: " + dataTemp.size());
        }*/
 /*if(index < dataTemp.size() - 1){
            if()
        }*/

        up = index;
        System.out.println("Index od gory: " + up);
        //od dolu
        index = dataTemp.size() - 1;
        decision = dataTemp.get(index).getDecision();
        while (index != 0 && dataTemp.get(index).getDecision().equals(decision)) {
            index--;
        }
        /*if (index != 0 && dataTemp.get(index).getValue() == dataTemp.get(index - 1).getValue()) {
            //removeXOR(index);
            System.out.println("XOR XOR ALERT!!! CHECKLENGTHSAMEDEC DOWN");
        }*/
        //checkPos = dataTemp.size() - 1 - index;
        for (Data dT : dataTemp) {
            System.out.println(dT.getDecision());
        }
        //System.out.println("Index dolny: " + index);
        if (index != 0 && dataTemp.get(index).getValue() == dataTemp.get(index + 1).getValue()) {
            //removeXOR(index);
            index = removeXORUp(dataTemp, index);
            System.out.println("XOR XOR ALERT!!! CHECKLENGTHSAMEDEC DOWN");
        }
        down = dataTemp.size() - 1 - index - 1;
        System.out.println("Index dolny: " + index + " oraz " + down);
        //System.out.println("Od gory: " + up + " od dolu: " + down);
        if (down > possiblePointsToCut || up > possiblePointsToCut) {
            possibleList = dataTemp;
            possibleColumnIndex = columnIndex;
            if (up > down) {
                possiblePointsToCut = up;
                possibleCutDirection = 1;
            } else {
                possiblePointsToCut = down;
                possibleCutDirection = 0;
            }
        }
    }

    /*
    znalezienie wartosci minimalnej i maksymalnej dla kazdej kolumny, aby mozna bylo wyznaczyc granice linii
     */
    public double[][] getMinMax(double[][] data) {
        double[][] res = new double[2][2];
        for (int i = 0; i < data[i].length; i++) {
            res[i][0] = data[0][i];
            res[i][1] = data[0][i];
            //System.out.println(data[i][0]);
            //System.out.println(data[i][1]);
            for (int y = 0; y < data.length; y++) {
                /* if (data[y][i] < res[0][i]) {
                    res[0][i] = data[y][i];
                } else if (data[y][i] > res[1][i]) {
                    res[1][i] = data[y][i];
                }*/
                //System.out.println(data[y][i]);
                if (data[y][i] < res[i][0]) {
                    res[i][0] = data[y][i];
                } else if (data[y][i] > res[i][1]) {
                    res[i][1] = data[y][i];
                }
            }
            //System.out.println("koniec kolumny");
        }
        System.out.println(Arrays.toString(res));
        return res;
    }

    public double[] getxL() {
        double[] temp = new double[xL.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = xL.get(i);
        }
        return temp;
    }

    public double[] getyL() {
        double[] temp = new double[yL.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = yL.get(i);
        }
        return temp;
    }

    public double[] getxP() {
        double[] temp = new double[xP.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = xP.get(i);
        }
        return temp;
    }

    public double[] getyP() {
        double[] temp = new double[yP.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = yP.get(i);
        }
        return temp;
    }

    public String[] getDec() {
        String[] temp = new String[yP.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = "point";
        }
        return temp;
    }

    private int removeXORDown(List<Data> dataTemp, int index) {
        int temp = index;
        while (temp != 0) {
            temp--;
            if (dataTemp.get(temp).getValue() == dataTemp.get(index + 1).getValue()) {
                return temp;
            }
        }
        return 0;
    }

    private int removeXORUp(List<Data> dataTemp, int index) {
        int temp = index;
        while (temp != dataTemp.size() - 1) {
            temp++;
            if (dataTemp.get(temp).getValue() == dataTemp.get(index + 1).getValue()) {
                return temp;
            }
        }
        return dataTemp.size() - 1;
    }

    private void deleteOneRow() {
        double[][] dataTemp = new double[data.length - 1][data[0].length];
        //System.out.println(dataTemp.length + " " + dataTemp[0].length);
        String[] decTemp = new String[decisionClass.length - 1];

        for (int i = 0; i < data.length; i++) {
            if (i == data.length - 1) {
                deletedPoints.add(java.util.Arrays.toString(data[i]));
            } else {
                dataTemp[i] = data[i];
                decTemp[i] = decisionClass[i];
            }
        }

        data = dataTemp;
        decisionClass = decTemp;
    }

    class Data implements Comparable<Data> {

        private int lp; //liczba porzadkowa (poczatkowy indeks)
        private double value;
        private String decision;

        public Data(int lp, double value, String decision) {
            this.lp = lp;
            this.value = value;
            this.decision = decision;
        }

        public int getLp() {
            return lp;
        }

        public void setLp(int lp) {
            this.lp = lp;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getDecision() {
            return decision;
        }

        public void setDecision(String decision) {
            this.decision = decision;
        }

        @Override
        public String toString() {
            return "Data{" + "lp=" + lp + ", value=" + value + ", decision=" + decision + '}';
        }

        @Override
        public int compareTo(Data o) {
            //a > b ? +1 : a < b ? -1 : 0;
            //return o.getValue() - getValue();
            return o.getValue() > getValue() ? 1 : o.getValue() < getValue() ? -1 : 0;
        }
    }

    class Line {

        private int columnIndex;
        private double coordinate;

        public Line(int columnIndex, double coordinate) {
            this.columnIndex = columnIndex;
            this.coordinate = coordinate;
        }

        public int getColumnIndex() {
            return columnIndex;
        }

        public void setColumnIndex(int columnIndex) {
            this.columnIndex = columnIndex;
        }

        public double getCoordinate() {
            return coordinate;
        }

        public void setCoordinate(double coordinate) {
            this.coordinate = coordinate;
        }

        @Override
        public String toString() {
            return "Line{" + "columnIndex=" + columnIndex + ", coordinate=" + coordinate + '}';
        }

    }
}
