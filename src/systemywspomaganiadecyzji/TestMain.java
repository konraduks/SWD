/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemywspomaganiadecyzji;

import Data.DecisionTree;

/**
 *
 * @author KonradD
 */
public class TestMain {

    public static void main(String[] args) {
        //String[] colName = {"Outlook", "Temperature", "Humidity", "Windy"};
        //String[] decClass = {"N", "N", "P", "P", "P", "N", "P", "N", "P", "P", "P", "P", "P", "N"};
        /*String[][] data = {
            {"sunny", "hot", "high", "false"},
            {"sunny", "hot", "high", "true"},
            {"overcast", "hot", "high", "false"},
            {"rain", "mild", "high", "false"},
            {"rain", "cool", "normal", "false"},
            {"rain", "cool", "normal", "true"},
            {"overcast", "cool", "normal", "true"},
            {"sunny", "mild", "high", "false"},
            {"sunny", "cool", "normal", "false"},
            {"rain", "mild", "normal", "false"},
            {"sunny", "mild", "normal", "true"},
            {"overcast", "mild", "high", "true"},
            {"overcast", "hot", "normal", "false"},
            {"rain", "mild", "high", "true"}};*/
        String[] colName = {"LISDLG", "LISSZE", "PLADLG", "PLASZE"};
        String[] decClass ={ "SETOSA", "VIRGINIC", "VERSICOL", "VIRGINIC", "VIRGINIC", "SETOSA", "VIRGINIC", "VERSICOL", "VERSICOL", "SETOSA", "VERSICOL", "VERSICOL", "VIRGINIC", "VERSICOL", "VIRGINIC", "VIRGINIC", "VIRGINIC", "SETOSA", "VERSICOL", "VIRGINIC", "VIRGINIC", "VERSICOL", "VIRGINIC", "VIRGINIC", "VIRGINIC", "SETOSA", "VIRGINIC", "VERSICOL", "VERSICOL", "VERSICOL", "SETOSA", "VIRGINIC", "VERSICOL", "VIRGINIC", "VIRGINIC", "SETOSA", "SETOSA", "VERSICOL", "VIRGINIC", "SETOSA", "VIRGINIC", "SETOSA", "VERSICOL", "SETOSA", "VIRGINIC", "VIRGINIC", "SETOSA", "VERSICOL", "VIRGINIC", "SETOSA", "SETOSA", "VIRGINIC", "SETOSA", "SETOSA", "SETOSA", "VIRGINIC", "VIRGINIC", "SETOSA", "SETOSA", "SETOSA", "VERSICOL", "VIRGINIC", "SETOSA", "SETOSA", "VERSICOL", "VERSICOL", "SETOSA", "SETOSA", "VERSICOL", "VERSICOL", "SETOSA", "SETOSA", "VIRGINIC", "VIRGINIC", "VIRGINIC", "VERSICOL", "VIRGINIC", "SETOSA", "SETOSA", "VIRGINIC", "VIRGINIC", "VIRGINIC", "VIRGINIC", "VERSICOL", "VERSICOL", "VERSICOL", "SETOSA", "SETOSA", "VIRGINIC", "VIRGINIC", "SETOSA", "VERSICOL", "VERSICOL", "VERSICOL", "SETOSA", "SETOSA", "VERSICOL", "VERSICOL", "VERSICOL", "SETOSA", "SETOSA", "VIRGINIC", "VERSICOL", "VIRGINIC", "VERSICOL", "SETOSA", "SETOSA", "VIRGINIC", "VERSICOL", "VIRGINIC", "VIRGINIC", "SETOSA", "VERSICOL", "VERSICOL", "SETOSA", "VERSICOL", "VERSICOL", "VERSICOL", "VERSICOL", "VERSICOL", "VERSICOL", "VIRGINIC", "VIRGINIC", "SETOSA", "SETOSA", "VIRGINIC", "VIRGINIC", "VERSICOL", "VERSICOL", "VERSICOL", "VIRGINIC", "VIRGINIC", "VERSICOL", "SETOSA", "SETOSA", "SETOSA", "VIRGINIC", "SETOSA", "SETOSA", "VERSICOL", "VERSICOL", "VERSICOL", "SETOSA", "SETOSA", "SETOSA", "VIRGINIC", "VERSICOL", "VIRGINIC", "SETOSA", "VERSICOL"};
        String[][] data = {
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "1.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "2.0"},
            {"1.0", "2.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "1.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "1.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "2.0", "2.0"},
            {"2.0", "1.0", "2.0", "2.0"},
            {"2.0", "2.0", "2.0", "2.0"},
            {"1.0", "2.0", "1.0", "1.0"},
            {"1.0", "1.0", "1.0", "1.0"},};
        DecisionTree dt = new DecisionTree();
        //dt.colNames = colName;
        //dt.entropia(decClass);
        //dt.generateTree(data, decClass);
        //dt.generateRoot(data, decClass, colName);
        //dt.print();
        dt.leaveOneOutMethod(data, decClass, colName);
    }
}
