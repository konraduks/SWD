/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemywspomaganiadecyzji;

import Data.k_nn_method;
import plot.DrawPlot;
import java.awt.Window;

/**
 *
 * @author KonradD
 */
public class mainik {
    
    public static void main(String[] args) {
        //new createplot().rysuj();
        
        //new k_nn_method().classificationMode(null, 0, null, 0);
        DrawPlot.getInstance();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new systemywspomaganiadecyzji.Window().setVisible(true);
            }
        });
    }
}
