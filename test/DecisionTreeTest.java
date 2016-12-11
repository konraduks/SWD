/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Data.DecisionTree;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author KonradD
 */
public class DecisionTreeTest {

    public DecisionTreeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void entropia1() {
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

    //@Test
    public void entropia1OutlookSunny() {
        String[] colName = {"Temperature", "Humidity", "Windy"};
        String[] decClass = {"N", "N", "N", "P", "P"};
        String[][] data = {
            {"hot", "high", "false"},
            {"hot", "high", "true"},
            {"mild", "high", "false"},
            {"cool", "normal", "false"},
            {"mild", "normal", "true"}
        };
        DecisionTree dt = new DecisionTree();
        //dt.colNames = colName;
        //dt.generateTree(data, decClass);
        dt.generateRoot(data, decClass, colName);
    }

    //@Test
    public void entropia2() {
        String[] decClass = {"no", "no", "yes", "yes", "yes", "no", "yes", "no", "yes", "yes", "yes", "yes", "yes", "no"};
        String[][] data = {
            {"sunny", "85", "85", "FALSE"},
            {"sunny", "80", "90", "TRUE"},
            {"overcast", "83", "86", "FALSE"},
            {"rainy", "70", "96", "FALSE"},
            {"rainy", "68", "80", "FALSE"},
            {"rainy", "65", "70", "TRUE"},
            {"overcast", "64", "65", "TRUE"},
            {"sunny", "72", "95", "FALSE"},
            {"sunny", "69", "70", "FALSE"},
            {"rainy", "75", "80", "FALSE"},
            {"sunny", "75", "70", "TRUE"},
            {"overcast", "72", "90", "TRUE"},
            {"overcast", "81", "75", "FALSE"},
            {"rainy", "71", "91", "TRUE"}
        };

        DecisionTree dt = new DecisionTree();
        //dt.generateTree(data, decClass);
    }
}
