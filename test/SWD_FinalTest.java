/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Data.k_nn_method;
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
public class SWD_FinalTest {
    
    public SWD_FinalTest() {
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
    public void EuclideanDistance2Points(){
        k_nn_method knn = new k_nn_method();
        double[] p1 = {2,-2};
        double[] p2 = {3,0};
        assertEquals(Math.sqrt(5), knn.euclideanDistance(p1, p2), 0.1);        
    }
    
    @Test
    public void EuclideanDistance3Points(){
        k_nn_method knn = new k_nn_method();
        double[] p1 = {2,-2,4};
        double[] p2 = {3,0,6};
        assertEquals(3, knn.euclideanDistance(p1, p2), 0);    
        
        /*double[] a = new double[10];
        for(int i = 0; i < 10; i++){
            System.out.println(a[i]);
        }*/
    }
}
