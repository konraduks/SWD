/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author KonradD
 */
public class DecisionTree {

    //public String[] colNames;
    private Node node;
    private NodeDouble nodeD;
    private int goodEvaluate; //ilosc dobrze zaklasyfikowanych obiektow

    public DecisionTree() {

    }

    public void print() {
        node.print();
    }

    public void leaveOneOutMethod(String[][] data, String[] decClass, String[] colNames) {
        /*for(String[] row : data){
            System.out.println(Arrays.toString(row));
        }*/
        String[][] tempData;
        String[] tempDecClass;
        String[] rowToEvaluate;
        String rowTEdecClass = null;
        goodEvaluate = 0;
        for (int i = 0; i < data.length; i++) {
            tempData = new String[data.length - 1][data[0].length];
            tempDecClass = new String[decClass.length - 1];
            rowToEvaluate = new String[data[0].length];
            //rowTEdecClass = new String[1];
            for (int j = 0, pos = 0; j < data.length; j++) {
                if (j == i) {
                    rowToEvaluate = data[j];
                    rowTEdecClass = decClass[j];
                    continue;
                }
                tempData[pos] = data[j];
                tempDecClass[pos++] = decClass[j];
                //System.out.println(Arrays.toString(data[j]));
            }
            /*for (int p = 0; p < tempData.length; p++) {
                System.out.println(Arrays.toString(tempData[p]));
            }
            System.out.println("\n");*/
            generateRoot(tempData, tempDecClass, colNames);
            if (changeEVrow(rowToEvaluate, rowTEdecClass, colNames)) {
                goodEvaluate++;
            }
            //print();
        }
        System.out.println("Ilosc dobrze zaklasyfikowanych: " + goodEvaluate + "/" + data.length + "(" + (goodEvaluate * 1.0 / data.length) * 100 + "%)");
    }

    public void leaveOneOutMethod(double[][] data, String[] decClass, String[] colNames) {
        /*for(double[] row : data){
            System.out.println(Arrays.toString(row));
        }*/
        /*System.out.println("{");
        for (int i = 0; i < data.length; i++) {
            System.out.print("{");
            for (int j = 0; j < data[i].length; j++) {
                if (j != data[i].length - 1) {
                    System.out.print(" \"" + data[i][j] + "\",");
                }else{
                    System.out.print(" \"" + data[i][j] + "\"");
                }
            }
            System.out.println("},");
        }
        System.out.println("};");*/
        System.out.println("[");
        for (int i = 0; i < data.length; i++) {
            //System.out.print("{");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println(";");
        }
        System.out.println("]");
        System.out.println("\n");
        /*System.out.print("{");
        for (int i = 0; i < decClass.length; i++) {
            if (i != decClass.length - 1) {
                    System.out.print(" \"" + decClass[i] + "\",");
                }else{
                    System.out.print(" \"" + decClass[i] + "\"");
                }
        }
        System.out.println("};");*/
        System.out.print("{");
        for (int i = 0; i < decClass.length; i++) {
            if (i != decClass.length - 1) {
                    System.out.print(" \'" + decClass[i] + "\',");
                }else{
                    System.out.print(" \'" + decClass[i] + "\'");
                }
        }
        System.out.println("}");
        double[][] tempData;
        String[] tempDecClass;
        double[] rowToEvaluate;
        String rowTEdecClass = null;
        goodEvaluate = 0;
        for (int i = 0; i < data.length; i++) {
            tempData = new double[data.length - 1][data[0].length];
            tempDecClass = new String[decClass.length - 1];
            rowToEvaluate = new double[data[0].length];
            //rowTEdecClass = new String[1];
            for (int j = 0, pos = 0; j < data.length; j++) {
                if (j == i) {
                    rowToEvaluate = data[j];
                    rowTEdecClass = decClass[j];
                    continue;
                }
                tempData[pos] = data[j];
                tempDecClass[pos++] = decClass[j];
                //System.out.println(Arrays.toString(data[j]));
            }
            /*for (int p = 0; p < tempData.length; p++) {
                System.out.println(Arrays.toString(tempData[p]));
            }
            System.out.println("\n");*/
            generateRoot(tempData, tempDecClass, colNames);
            if (changeEVrow(rowToEvaluate, rowTEdecClass, colNames)) {
                goodEvaluate++;
            }
            //print();
        }
        System.out.println("Ilosc dobrze zaklasyfikowanych: " + goodEvaluate + "/" + data.length + "(" + (goodEvaluate * 1.0 / data.length) * 100 + "%)");
    }

    public void generateTree(double[][] data, String[] decClass) {
        data = transpose(data);
        //zamiana klasy decyzyjnej na I(P);
        entropia(decClass);
        for (int i = 0; i < data.length; i++) {
            //System.out.println(Arrays.toString(data[i]));
            entropia(data[i]);
        }
    }

    /**
     * Znalezienie i ustanowienie korzenia typ danych: String
     */
    public void generateRoot(String[][] data, String[] decClass, String[] colNames) {
        double allEntropia = entropia(decClass);
        int pos = findCol(data, decClass, allEntropia);
        //node = new Node(pos + " ");
        node = new Node(colNames[pos]);
//        System.out.println("Wybor korzenia: " + colNames[pos] + ", wartosc: " + largestDifference);
        //wybor mozliwych rozgalezien        
        ArrayList<String> branch = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (!branch.contains(data[i][pos])) {
                branch.add(data[i][pos]);
            }
        }
//        System.out.println("Odgalezienia: " + branch.toString());
        for (int j = 0; j < branch.size(); j++) {
            ArraysPair pair = cutTables(data, decClass, colNames, pos, branch.get(j));
            Node child = new Node();
            node.addLeaf(branch.get(j), child);
            generateLeaves(pair.getData(), pair.getDecClass(), pair.getColNames(), node, child);
        }
    }

    /**
     * Znalezienie i ustanowienie korzenia typ danych: double
     */
    public void generateRoot(double[][] data, String[] decClass, String[] colNames) {
        double allEntropia = entropia(decClass);
        int pos = findCol(data, decClass, allEntropia);
        //node = new Node(pos + " ");
        nodeD = new NodeDouble(colNames[pos]);
        System.out.println("Wybor korzenia: " + colNames[pos] + ", wartosc: " + largestDifference);
        //wybor mozliwych rozgalezien        
        ArrayList<Double> branch = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (!branch.contains(data[i][pos])) {
                branch.add(data[i][pos]);
            }
        }
//        System.out.println("Odgalezienia: " + branch.toString());
        for (int j = 0; j < branch.size(); j++) {
            ArraysPair pair = cutTables(data, decClass, colNames, pos, branch.get(j));
            NodeDouble child = new NodeDouble();
            nodeD.addLeaf(branch.get(j), child);
            generateLeaves(pair.getDataDouble(), pair.getDecClass(), pair.getColNames(), nodeD, child);
        }
    }

    double largestDifference;

    /**
     * Znajduje najlepsza kolumne do klasyfikacji
     *
     * @return
     */
    public int findCol(String[][] data, String[] decClass, double allEntropia) {
        largestDifference = 0;
        double temp = 0;
        int pos = 0;
        data = transpose(data);
        for (int i = 0; i < data.length; i++) {
            if ((temp = (allEntropia - entropiaCol(data[i], decClass))) > largestDifference) {
                largestDifference = temp;
                pos = i;
            }
        }
        data = transpose(data);
        return pos;
    }

    /**
     * Znajduje najlepsza kolumne do klasyfikacji
     *
     * @return
     */
    public int findCol(double[][] data, String[] decClass, double allEntropia) {
        largestDifference = 0;
        double temp = 0;
        int pos = 0;
        data = transpose(data);
        for (int i = 0; i < data.length; i++) {
            if ((temp = (allEntropia - entropiaCol(data[i], decClass))) > largestDifference) {
                largestDifference = temp;
                pos = i;
            }
        }
        data = transpose(data);
        return pos;
    }

    Node child;

    /**
     * Generuje liscie, jesli entropia = 0, to bierze te kolumne i konczy w
     * danej czesci
     */
    public void generateLeaves(String[][] data, String[] decClass, String[] colNames, Node parent, Node thisChild) {
        double entropia = entropia(decClass);
        //jesli entropia = 0, to koniec algorytmu dla tej sciezki
        if (entropia == 0 || colNames.length == 0) {
            //System.out.println("Koniec dla potomka: " + parent.getName() + ", czyli: " + thisChild.getName());
            //System.out.println("Koniec dla potomka: " + parent.getName() + ", czyli: " + child.getName());
            //thisChild.setName(null);
            thisChild.setEndVal(decClass[0]);
            //ustawienie odpowiednich wartosci w drzewie
            return;
        }
        int pos = findCol(data, decClass, entropia);
        //System.out.println("Wybor liscia: " + colNames[pos] + ", wartosc: " + largestDifference);
        try {
            thisChild.setName(colNames[pos]);
        } catch (Exception e) {
            System.err.println("blad");
        }
        
        ArrayList<String> branch = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (!branch.contains(data[i][pos])) {
                branch.add(data[i][pos]);
            }
        }
        for (int j = 0; j < branch.size(); j++) {
            ArraysPair pair = cutTables(data, decClass, colNames, pos, branch.get(j));
            /*Node*/ child = new Node();
            thisChild.addLeaf(branch.get(j), child);
            generateLeaves(pair.getData(), pair.getDecClass(), pair.getColNames(), thisChild, child);
        }
    }

    NodeDouble childD;

    /**
     * Generuje liscie, jesli entropia = 0, to bierze te kolumne i konczy w
     * danej czesci
     */
    public void generateLeaves(double[][] data, String[] decClass, String[] colNames, NodeDouble parent, NodeDouble thisChild) {
        double entropia = entropia(decClass);
        //jesli entropia = 0, to koniec algorytmu dla tej sciezki
        if (entropia == 0 || colNames.length == 0) {
            //System.out.println("Koniec dla potomka: " + parent.getName() + ", czyli: " + thisChild.getName());
            //System.out.println("Koniec dla potomka: " + parent.getName() + ", czyli: " + child.getName());
            //thisChild.setName(null);
            if(entropia != 0){
                thisChild.setEndVal(mostAppear(decClass));
            }else{
                thisChild.setEndVal(decClass[0]);
            }            
            //ustawienie odpowiednich wartosci w drzewie
            return;
        }
        int pos = findCol(data, decClass, entropia);
        //System.out.println("Wybor liscia: " + colNames[pos] + ", wartosc: " + largestDifference);
        thisChild.setName(colNames[pos]);
        ArrayList<Double> branch = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            try {
                if (!branch.contains(data[i][pos])) {
                    branch.add(data[i][pos]);
                }
            } catch (Exception e) {
                System.err.println("Wyjatek: " + data.length + ", " + data[i].length + " " + entropia + " " + childD.getName() + " " + parent.getName());
            }

        }
        for (int j = 0; j < branch.size(); j++) {
            ArraysPair pair = cutTables(data, decClass, colNames, pos, branch.get(j));
            /*Node*/ childD = new NodeDouble();
            thisChild.addLeaf(branch.get(j), childD);
            generateLeaves(pair.getDataDouble(), pair.getDecClass(), pair.getColNames(), thisChild, childD);
        }
    }

    /**
     * Przycinanie zbioru danych po kolumnie i odpowiedniej wartosci z niej
     */
    private ArraysPair cutTables(String[][] data, String[] decClass, String[] colNames, int col, String val) {
        int size = 0;
        String[][] res;// = new String[data.length][data[0].length-1];
        String[] dec; //klasa decyzyjna
        String[] columns = new String[colNames.length - 1]; //nazwy kolumn
        for (String[] str : data) {
            if (str[col].equals(val)) {
                size++;
            }
        }
        res = new String[size][data[0].length - 1];
        dec = new String[size];
        for (int i = 0, pos = 0; i < data.length || pos != size; i++) {
            if (data[i][col].equals(val)) {
                //res[pos++] = getRow(data[i], col);
                try {
                    dec[pos] = decClass[i];
                    res[pos++] = getRow(data[i], col);
                } catch (Exception e) {
                    System.err.println(Arrays.toString(data[i]) + " " + col);
                }
            }
        }
        for (int i = 0, pos = 0; i < colNames.length; i++) {
            if (i != col) {
                columns[pos++] = colNames[i];
            }
        }
        return new ArraysPair(res, dec, columns);
    }

    /**
     * Przycinanie zbioru danych po kolumnie i odpowiedniej wartosci z niej
     */
    private ArraysPair cutTables(double[][] data, String[] decClass, String[] colNames, int col, double val) {
        int size = 0;
        double[][] res;// = new String[data.length][data[0].length-1];
        String[] dec; //klasa decyzyjna
        String[] columns = new String[colNames.length - 1]; //nazwy kolumn
        for (double[] str : data) {
            if (str[col] == val) {
                size++;
            }
        }
        res = new double[size][data[0].length - 1];
        dec = new String[size];
        for (int i = 0, pos = 0; i < data.length || pos != size; i++) {
            if (data[i][col] == val) {
                //res[pos++] = getRow(data[i], col);
                try {
                    dec[pos] = decClass[i];
                    res[pos++] = getRow(data[i], col);
                } catch (Exception e) {
                    System.err.println(Arrays.toString(data[i]) + " " + col);
                }
            }
        }
        for (int i = 0, pos = 0; i < colNames.length; i++) {
            if (i != col) {
                columns[pos++] = colNames[i];
            }
        }
        return new ArraysPair(res, dec, columns);
    }

    /**
     * Zwraca krotke bez wartosci uzytej w budoaniu drzewa, np: uzylismy kolumny
     * outlook to do dalszej czesci nie potrzebujemy tej kolumny
     */
    private String[] getRow(String[] row, int pos) {
        String res[] = new String[row.length - 1];
        for (int i = 0, x = 0; i < row.length; i++) {
            if (i != pos) {
                res[x++] = row[i];
            }
        }
        return res;
    }

    /**
     * Zwraca krotke bez wartosci uzytej w budoaniu drzewa, np: uzylismy kolumny
     * outlook to do dalszej czesci nie potrzebujemy tej kolumny
     */
    private double[] getRow(double[] row, int pos) {
        double res[] = new double[row.length - 1];
        for (int i = 0, x = 0; i < row.length; i++) {
            if (i != pos) {
                res[x++] = row[i];
            }
        }
        return res;
    }

    public void generateTree(String[][] data, String[] decClass) {
        data = transpose(data);
        //zamiana klasy decyzyjnej na I(P);
        entropia(decClass);
        for (int i = 0; i < data.length; i++) {
            //System.out.println(Arrays.toString(data[i]));
            entropiaCol(data[i], decClass);
        }
    }

    /**
     * Wyliczanie entropii poczatkowej
     *
     * @param decClass
     */
    public double entropia(String[] decClass) {
        ArrayList<String> poss = new ArrayList<>();
        ArrayList<Integer> value = new ArrayList<>();
        for (String str : decClass) {
            if (!poss.contains(str)) {
                poss.add(str);
                value.add(1);
            } else {
                value.set(poss.indexOf(str), value.get(poss.indexOf(str)) + 1);
            }
        }
        int size = decClass.length;
        double res = 0;
        for (int val : value) {
            //System.out.println(val + " " + size + " " + (val * 1.0 /size) + " " +(val/size)*log(val/size));
            //https://en.wikipedia.org/wiki/Logarithm#Change_of_base
            res += -(val * 1.0 / size) * (log10(val * 1.0 / size) / log10(2));
            //System.out.println(" " + (val * 1.0 /size)*(log10(val * 1.0 /size)/log10(2)));
        }
        //System.out.println("I(P): " + res);
        return res;
    }

    /**
     * Wyliczanie entropii dla poszczegolnych wartosci w poszczegolnych klasach
     *
     * @param decClass
     */
    public double entropiaCol(String[] col, String[] decClass) {
        //System.out.println(Arrays.toString(col));
        ArrayList<String> posCol = new ArrayList<>();
        ArrayList<Integer> valueCol = new ArrayList<>();
        ArrayList<String> posDecClass = new ArrayList<>();
        for (String str : col) {
            if (!posCol.contains(str)) {
                posCol.add(str);
                valueCol.add(1);
            } else {
                valueCol.set(posCol.indexOf(str), valueCol.get(posCol.indexOf(str)) + 1);
            }
        }
        //mozliwe rozwiazania klasy decyzyjnej
        for (String str : decClass) {
            if (!posDecClass.contains(str)) {
                posDecClass.add(str);
            }
        }
        //System.out.println(poss.toString());        
        //wyliczanie entropii dla poszczegolnych wartosci z powiazaniem decClass
        ArrayList<Double> resP = new ArrayList<>();
        for (String strCol : posCol) {
            ArrayList<String> posCol2 = new ArrayList<>();
            ArrayList<Integer> valueCol2 = new ArrayList<>();
            int length = 0;
            double res = 0;
            for (int i = 0; i < col.length; i++) {
                if (strCol.equals(col[i])) {
                    length++;
                    if (!posCol2.contains(strCol + decClass[i])) {
                        posCol2.add(strCol + decClass[i]);
                        valueCol2.add(1);
                    } else {
                        valueCol2.set(posCol2.indexOf(strCol + decClass[i]), valueCol2.get(posCol2.indexOf(strCol + decClass[i])) + 1);
                    }
                }
            }
            for (int val : valueCol2) {
                res += -(val * 1.0 / length) * (log10(val * 1.0 / length) / log10(2));
                //System.out.println(val + " " + length);
            }
            resP.add(res);
            //System.out.println(strCol + ": " + res);
            //System.out.println(" " + posCol2.toString() + " " + valueCol2.toString());            
        }
        double result = 0;
        for (int i = 0; i < valueCol.size(); i++) {
            result += (valueCol.get(i) / (col.length * 1.0)) * resP.get(i);
        }
        //System.out.println(" " + result);
        return result;
    }

    /**
     * Wyliczanie entropii dla poszczegolnych wartosci w poszczegolnych klasach
     *
     * @param decClass
     */
    public double entropiaCol(double[] col, String[] decClass) {
        //System.out.println(Arrays.toString(col));
        ArrayList<Double> posCol = new ArrayList<>();
        ArrayList<Integer> valueCol = new ArrayList<>();
        ArrayList<String> posDecClass = new ArrayList<>();
        for (double str : col) {
            if (!posCol.contains(str)) {
                posCol.add(str);
                valueCol.add(1);
            } else {
                valueCol.set(posCol.indexOf(str), valueCol.get(posCol.indexOf(str)) + 1);
            }
        }
        //mozliwe rozwiazania klasy decyzyjnej
        for (String str : decClass) {
            if (!posDecClass.contains(str)) {
                posDecClass.add(str);
            }
        }
        //System.out.println(poss.toString());        
        //wyliczanie entropii dla poszczegolnych wartosci z powiazaniem decClass
        ArrayList<Double> resP = new ArrayList<>();
        for (double strCol : posCol) {
            ArrayList<String> posCol2 = new ArrayList<>();
            ArrayList<Integer> valueCol2 = new ArrayList<>();
            int length = 0;
            double res = 0;
            for (int i = 0; i < col.length; i++) {
                //if (strCol.equals(col[i])) {
                if (strCol == col[i]) {
                    length++;
                    if (!posCol2.contains(strCol + decClass[i])) {
                        posCol2.add(strCol + decClass[i]);
                        valueCol2.add(1);
                    } else {
                        valueCol2.set(posCol2.indexOf(strCol + decClass[i]), valueCol2.get(posCol2.indexOf(strCol + decClass[i])) + 1);
                    }
                }
            }
            for (int val : valueCol2) {
                res += -(val * 1.0 / length) * (log10(val * 1.0 / length) / log10(2));
                //System.out.println(val + " " + length);
            }
            resP.add(res);
            //System.out.println(strCol + ": " + res);
            //System.out.println(" " + posCol2.toString() + " " + valueCol2.toString());            
        }
        double result = 0;
        for (int i = 0; i < valueCol.size(); i++) {
            result += (valueCol.get(i) / (col.length * 1.0)) * resP.get(i);
        }
        //System.out.println(" " + result);
        return result;
    }

    private void entropia(double[] decClass) {
        ArrayList<Double> poss = new ArrayList<>();
        ArrayList<Integer> value = new ArrayList<>();
        for (double str : decClass) {
            if (!poss.contains(str)) {
                poss.add(str);
                value.add(1);
            } else {
                value.set(poss.indexOf(str), value.get(poss.indexOf(str)) + 1);
            }
        }
        int size = decClass.length;
        double res = 0;
        for (int val : value) {
            //System.out.println(val + " " + size + " " + (val * 1.0 /size) + " " +(val/size)*log(val/size));
            //https://en.wikipedia.org/wiki/Logarithm#Change_of_base
            res += (val * 1.0 / size) * (log10(val * 1.0 / size) / log10(2));
        }
        System.out.println("I(P): " + (-res));
    }

    private boolean changeEVrow(String[] rowToEvaluate, String rowTEdecClass, String[] colNames) {
        /*
        1. pobranie nazwy z node -> czuli korzenia
        2. znalezienie indeksu wartosc w colnames
        3. pobranie wartosci z rowtoevaluate
        4. sprawdzenie ktory to potomek za pomoca node->values i pobranie tego indeksu i przejscie do leafs.get(indeks)
        4a. powtarzanie krokow 1 - 4
        5. przechodzenie w dol dopoki name != null
        6. porownanie endVal z rowTedecClass
         */
        Node temp = node;
        String tempstr = null;
        int index = 0;
//        System.out.println(Arrays.toString(rowToEvaluate));
//        System.out.println(rowTEdecClass.toString());
//        System.out.println(Arrays.toString(colNames));
        while (true) {
            if (temp.getName() == null) { //nie ma nazwy, czyli lisc
                if (temp.getEndVal().equals(rowTEdecClass)) {
                    //System.out.println("Zaklasyfikowano dobrze");
                    return true;
                } else {
//                    System.out.println("zle");
                    return false;
                }
            }
            index = 0;
            //System.out.println(temp.getName());
            while (!temp.getName().equals(colNames[index])) {
                index++;
            }
            tempstr = rowToEvaluate[index];
            index = temp.getValues().indexOf(tempstr);
            temp = temp.getLeafs().get(index);
        }

    }

    private boolean changeEVrow(double[] rowToEvaluate, String rowTEdecClass, String[] colNames) {
        NodeDouble temp = nodeD;
        double tempstr = 0;
        int index = 0;
        while (true) {
            if (temp.getName() == null) { //nie ma nazwy, czyli lisc
                if (temp.getEndVal().equals(rowTEdecClass)) {
                    //System.out.println("Zaklasyfikowano dobrze");
                    return true;
                } else {
//                    System.out.println("zle");
                    return false;
                }
            }
            index = 0;
            //System.out.println(temp.getName());
            while (!temp.getName().equals(colNames[index])) {
                index++;
            }
            tempstr = rowToEvaluate[index];
            index = temp.getValues().indexOf(tempstr);
            try {
                temp = temp.getLeafs().get(index);
            } catch (Exception e) {
                //System.err.println("");
                return false;
                //return true;
            }
            
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

    public String[][] transpose(String[][] array) {
        if (array == null || array.length == 0)//empty or unset array, nothing do to here
        {
            return array;
        }
        int width = array.length;
        int height = array[0].length;

        String[][] array_new = new String[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array_new[y][x] = array[x][y];
            }
        }
        return array_new;
    }

    private String mostAppear(String[] decClass) {
        String res;
        ArrayList<String> poss = new ArrayList<>();
        ArrayList<Integer> value = new ArrayList<>();
        for (String str : decClass) {
            if (!poss.contains(str)) {
                poss.add(str);
                value.add(1);
            } else {
                value.set(poss.indexOf(str), value.get(poss.indexOf(str)) + 1);
            }
        }
        int theBiggestVal = 0;
        int pos = 0;
        for(int i = 0; i < value.size(); i++){
            if(value.get(i) > theBiggestVal){
                theBiggestVal = value.get(i);
                pos = i;
            }
        }
        res = poss.get(pos);
        return res;
    }
}

class Node {

    String name;
    ArrayList<String> values; //wartosci rozrozniajace, np: mala, duza
    ArrayList<Node> leafs; //liscie
    String endVal; //jesli ostateczny lisc to koniec

    public Node() {
        values = new ArrayList<>();
        leafs = new ArrayList<Node>();
    }

    public Node(String name) {
        this();
        this.name = name;
    }

    public void addLeaf(String val, Node nod) {
        values.add(val);
        leafs.add(nod);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public ArrayList<Node> getLeafs() {
        return leafs;
    }

    public void setLeafs(ArrayList<Node> leafs) {
        this.leafs = leafs;
    }

    public String getEndVal() {
        return endVal;
    }

    public void setEndVal(String endVal) {
        this.endVal = endVal;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        String desc;
        if (name != null) {
            desc = name + " " + values.toString();
        } else {
            desc = endVal;
        }
        System.out.println(prefix + (isTail ? "└── " : "├──  ") + desc);
        for (int i = 0; i < leafs.size() - 1; i++) {
            leafs.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (leafs.size() > 0) {
            leafs.get(leafs.size() - 1)
                    .print(prefix + (isTail ? "    " : "│   "), true);
        }
    }
}

class NodeDouble {

    String name;
    ArrayList<Double> values; //wartosci rozrozniajace, np: mala, duza
    ArrayList<NodeDouble> leafs; //liscie
    String endVal; //jesli ostateczny lisc to koniec

    public NodeDouble() {
        values = new ArrayList<>();
        leafs = new ArrayList<NodeDouble>();
    }

    public NodeDouble(String name) {
        this();
        this.name = name;
    }

    public void addLeaf(double val, NodeDouble nod) {
        values.add(val);
        leafs.add(nod);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Double> getValues() {
        return values;
    }

    public void setValues(ArrayList<Double> values) {
        this.values = values;
    }

    public ArrayList<NodeDouble> getLeafs() {
        return leafs;
    }

    public void setLeafs(ArrayList<NodeDouble> leafs) {
        this.leafs = leafs;
    }

    public String getEndVal() {
        return endVal;
    }

    public void setEndVal(String endVal) {
        this.endVal = endVal;
    }

    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        String desc;
        if (name != null) {
            desc = name + " " + values.toString();
        } else {
            desc = endVal;
        }
        System.out.println(prefix + (isTail ? "└── " : "├──  ") + desc);
        for (int i = 0; i < leafs.size() - 1; i++) {
            leafs.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (leafs.size() > 0) {
            leafs.get(leafs.size() - 1)
                    .print(prefix + (isTail ? "    " : "│   "), true);
        }
    }
}

class ArraysPair {

    String[][] data;
    double[][] dataDouble;
    String[] decClass;
    String[] colNames;

    public ArraysPair(String[][] data, String[] decClass, String[] colNames) {
        this.decClass = decClass;
        this.data = data;
        this.colNames = colNames;
    }

    public ArraysPair(double[][] dataDouble, String[] decClass, String[] colNames) {
        this.dataDouble = dataDouble;
        this.decClass = decClass;
        this.colNames = colNames;
    }

    public double[][] getDataDouble() {
        return dataDouble;
    }

    public void setDataDouble(double[][] dataDouble) {
        this.dataDouble = dataDouble;
    }

    public String[] getDecClass() {
        return decClass;
    }

    public void setDecClass(String[] decClass) {
        this.decClass = decClass;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }

    public String[] getColNames() {
        return colNames;
    }

    public void setColNames(String[] colNames) {
        this.colNames = colNames;
    }

}
