/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Data.K_Means;
import cern.colt.Arrays;
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
public class K_MeansTest {

    public K_MeansTest() {
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
    public void minMax() {
        double[][] temp = {
            {1, 8, 9, 12, 49, 100},
            {3, 7, 10, 14, 55, 110},
            {2, 8, 11, 15, 50, 108}};
        double[][] res = {
            {1, 7, 9, 12, 49, 100},
            {3, 8, 11, 15, 55, 110}
        };
        /*for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[i].length; j++) {
                System.err.println(res[i][j]);
            }
        }*/
        K_Means km = new K_Means();
        temp = km.getMinMax(km.transpose(temp));
        for (double[] dob : temp) {
            System.out.println(Arrays.toString(dob));
        }
        assertEquals(res, temp);
    }

    @Test
    public void generateRand() {
        double[][] temp = {
            {1, 8, 9, 12, 49, 100},
            {3, 7, 10, 14, 55, 110},
            {2, 8, 11, 15, 50, 108}};
        K_Means km = new K_Means();
        km.randClusterCentre(km.transpose(temp), 3);
    }
}
