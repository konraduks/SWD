/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Data.DataOperation;
import Data.k_nn_method;
import java.util.Arrays;
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
    public void EuclideanDistance2Points() {
        k_nn_method knn = new k_nn_method();
        double[] p1 = {2, -2};
        double[] p2 = {3, 0};
        assertEquals(Math.sqrt(5), knn.euclideanDistance(p1, p2), 0.1);
    }

    @Test
    public void EuclideanDistance3Points() {
        k_nn_method knn = new k_nn_method();
        double[] p1 = {2, -2, 4};
        double[] p2 = {3, 0, 6};
        assertEquals(3, knn.euclideanDistance(p1, p2), 0);

        /*double[] a = new double[10];
        for(int i = 0; i < 10; i++){
            System.out.println(a[i]);
        }*/
    }

    @Test
    public void MaxDistanceTest2Points() {
        k_nn_method knn = new k_nn_method();
        double[] p1 = {-4, 6};
        double[] p2 = {1, 9};
        assertEquals(5, knn.maxDistance(p1, p2), 0);
    }

    @Test
    public void MaxDistanceTest3Points() {
        k_nn_method knn = new k_nn_method();
        double[] p1 = {-4, 6, 5};
        double[] p2 = {1, 9, -7};
        assertEquals(12, knn.maxDistance(p1, p2), 0);
    }

    @Test
    public void Wariancja1() {
        DataOperation dop = new DataOperation();
        double[] tab = {1, 2, 4};
        //System.out.println("Wariancja: " + dop.Wariancja(tab));
        assertEquals(2.33, dop.Wariancja(tab), 0.1);
    }

    @Test
    public void Wariancja2() {
        DataOperation dop = new DataOperation();
        //double[] tab = {1, 2, 3, 4, 5};        
        //assertEquals(2, dop.Wariancja(tab), 0.1);
        double[] tab = {90, 90, 60, 30, 30};
        double[] tab2 = {4.0000,
            4.2000,
            3.9000,
            4.3000,
            4.1000};
        //assertEquals(2, dop.Wariancja(tab2), 0.1);
        k_nn_method knn = new k_nn_method();
        //knn.covarianceMatrix();
    }

    @Test
    public void inverseArray() {
        /*double[][] tab = {
            {4.0000, 2, 0.6000},
            {4.2000, 2.1, 0.5900},
            {3.9000, 2, 0.5800},
            {4.3000, 2.1, 0.6200},
            {4.1000, 2.2, 0.6300}};
        k_nn_method knn = new k_nn_method();
        double[][] tab1 = knn.reverseArray(tab);
        System.out.println("inwersja: ");
        for(int i = 0; i < tab1.length; i++){
            System.out.println(Arrays.toString(tab1[i]));
        }*/
        double[][] tab = {
            {0.0250, 0.0075, 0.0017},
            {0.0075, 0.0070, 0.0014},
            {0.0017, 0.0014, 0.0004}
        };
        k_nn_method knn = new k_nn_method();
        /*tab =*/ knn.inverseArray(tab);
        System.out.println("inwersja: ");
        for(int i = 0; i < tab.length; i++){
            System.out.println(Arrays.toString(tab[i]));
        }
    }
    
    @Test
    public void covMatrix(){
        double[][] tab = {
            {1,2,3},
            {3,4,7},
            {0,0,0}            
        };
        k_nn_method knn = new k_nn_method();
        tab = knn.covarianceMatrix(tab);
        System.out.println("covMatrix: ");
        for(int i = 0; i < tab.length; i++){
            System.out.println(Arrays.toString(tab[i]));
        }
    }
    @Test
    public void covMatrix2(){
        double[][] tab = {
            {1,3,0},
            {2,4,0},
            {3,7,0}            
        };
        k_nn_method knn = new k_nn_method();
        tab = knn.covarianceMatrix(tab);
        System.out.println("---------------------");
        System.out.println("covMatrix2: ");
        for(int i = 0; i < tab.length; i++){
            System.out.println(Arrays.toString(tab[i]));
        }
        System.out.println("---------------------");
    }
}
