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
        String[] colName = {"Outlook", "Temperature", "Humidity", "Windy"};
        String[] decClass = {"N", "N", "P", "P", "P", "N", "P", "N", "P", "P", "P", "P", "P", "N"};
        String[][] data = {
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
            {"rain", "mild", "high", "true"}};
        DecisionTree dt = new DecisionTree();
        //dt.colNames = colName;
        //dt.entropia(decClass);
        //dt.generateTree(data, decClass);
        dt.generateRoot(data, decClass, colName);
        dt.print();
    }
}
