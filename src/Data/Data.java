/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;

/**
 *
 * @author KonradD
 */
public class Data {

    private static Data data;

    private int maxWidth;
    //public ArrayList<Row> allData = new ArrayList<Row>();
    //public ArrayList<ArrayList<String>> allData = new ArrayList<Row>();
    private ArrayList<String> headers = new ArrayList<>();
    private ArrayList<ArrayList<String>> allData = new ArrayList<>();

    private ArrayList<String> Row = new ArrayList<>();

    private ArrayList<OldData> previousValue = new ArrayList<>();

    private Data() {
    }

    public static Data getInstance() {
        if (data == null) {
            data = new Data();
        }
        return data;
    }

    public void addHeader(String head) {
        headers.add(head);
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void newRow() {
        allData.add(Row);
        if (Row.size() > maxWidth) {
            maxWidth = Row.size();
        }
        Row = new ArrayList<>();
    }

    public void add(String value) {
        Row.add(value);
    }

    public ArrayList<ArrayList<String>> getAllData() {
        return allData;
    }

    public String getSpecifiedValue(int x, int y) {
        return allData.get(x).get(y);
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public ArrayList<String> getRow(int number) {
        return allData.get(number);
    }
    
    public void addColumn(String[] tab){
        int pos = 0;
        for(ArrayList<String> temp : allData){
            System.out.println(temp.toString());
            temp.add(tab[pos]);
            pos ++;
        }
        System.out.println("po dodaniu: ");
        for(ArrayList<String> temp : allData){
            System.out.println(temp.toString());            
        }
    }

    public double[] getColumn(int index){
        int pos = 0;
        double[] res = new double[allData.size()];
        for(ArrayList<String> temp : allData){
            
            res[pos] = Double.parseDouble(temp.get(index).replaceAll(",", "."));
            pos++;
        }
        return res;
    }
    
    public String[] getStringColumn(int index){
        int pos = 0;
        String[] res = new String[allData.size()];
        for(ArrayList<String> temp : allData){
            res[pos] = temp.get(index);
            pos++;
        }
        return res;
    }

    public void print() {
        for (ArrayList<String> s : allData) {
            System.out.println(s.toString());
        }
    }

    public void replace(int column, int row, String value) { //zmiana wartosci w tabeli i przeniesienie starej wartsci do prevValue
        /*String temp = allData.get(column).get(row);
        allData.get(column).set(row, value);
        previousValue.add(new OldData(column, row, temp));*/
        System.out.println(allData.size() + " " + allData.get(row).size());
        if ((allData.get(row).size() - 1) < column) {
            System.err.println("brak danych");
            for (int i = allData.get(row).size(); i < column; i++) {
                allData.get(row).add("");
            }
            allData.get(row).add(value);
            previousValue.add(new OldData(column, row, ""));
        } else {
            String temp = allData.get(row).get(column);
            previousValue.add(new OldData(column, row, temp));
            allData.get(row).set(column, value);
        }        
        print();
    }
    
    public String[] charToString(String[] temp){
        for(int i = 0; i < temp.length; i++){
            temp[i] += "a";
        }
        System.err.println(" test: a" + temp[5] + "a");
        return temp;
    }
}

class Row {

    ArrayList<String> dataRow = new ArrayList<>();
}

class OldData {

    public int column;
    public int row;
    public String value;

    public OldData(int column, int row, String value) {
        this.column = column;
        this.row = row;
        this.value = value;
    }
}
