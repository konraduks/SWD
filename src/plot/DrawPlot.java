/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plot;

import Plotter.plot;
import com.mathworks.toolbox.javabuilder.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import systemywspomaganiadecyzji.Window;
import systemywspomaganiadecyzji.Window;

/**
 *
 * @author KonradD
 */
public class DrawPlot {

    private static DrawPlot drawPlot;
    private plot thePlot;

    private DrawPlot() {
    }

    public static DrawPlot getInstance() {
        if (drawPlot == null) {
            drawPlot = new DrawPlot();
        }
        return drawPlot;
    }

    /*public void draw2D(double[] x, double[] y){
        
        Plot2DPanel plot = new Plot2DPanel();
        
        plot.addScatterPlot("Error plot", Color.BLUE, x, y);
        
        JFrame frame = new JFrame("a plot panel");
        frame.setContentPane(plot);
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public void draw3D(double[] x, double[] y, double[] z){
        Plot3DPanel plot = new Plot3DPanel();
        
        plot.addScatterPlot("Error plot", Color.BLUE, x, y, z);
        
        JFrame frame = new JFrame("a plot panel");
        frame.setContentPane(plot);
        frame.setSize(800, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }*/
    public void draw2D(double[] x, double[] y) {
        try {
            thePlot = new plot();
            thePlot.plot2D(x, y);
            thePlot.waitForFigures();

        } catch (Exception ex) {
            Logger.getLogger(DrawPlot.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Exception: " + ex.toString());
        } finally {
            if (thePlot != null) {
                thePlot.dispose();
            }
        }
    }

    public void draw2D_desc(double[] x, double[] y, String[] desc) {
        try {
            thePlot = new plot();
            thePlot.plot2D_desc(x, y, desc);
            thePlot.waitForFigures();

        } catch (Exception ex) {
            Logger.getLogger(DrawPlot.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Exception: " + ex.toString());
        } finally {
            if (thePlot != null) {
                thePlot.dispose();
            }
        }
    }
    
    public void draw2D_desc_lines(double[] x, double[] y, String[] desc, double[] xL, double[] yL) {
        try {
            thePlot = new plot();
            thePlot.plot2D_desc_lines(x, y, desc, xL, yL);//(x, y, desc);
            thePlot.waitForFigures();

        } catch (Exception ex) {
            Logger.getLogger(DrawPlot.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Exception: " + ex.toString());
        } finally {
            if (thePlot != null) {
                thePlot.dispose();
            }
        }
    }

    public static void main(String[] args) {
        //new DrawPlot().draw3D();
        //DrawPlot.getInstance().draw3D();
        new Window().setVisible(true);
    }

    public void draw3D(double[] x1, double[] y1, double[] z1) {
        //plot thePlot = null;
//        MWNumericArray x = null;
//        MWNumericArray y = null;
//        MWNumericArray z = null;
        //int n = 10;
        try {

            /*double[] ox = {1, 5, 6};
            double[] oy = {1, 5, 6};
            double[] oz = {1, 5, 6};*/
 /*int[] dims = {1, n};
            x = MWNumericArray.newInstance(dims,
                    MWClassID.DOUBLE, MWComplexity.REAL);
            y = MWNumericArray.newInstance(dims,
                    MWClassID.DOUBLE, MWComplexity.REAL);
            z = MWNumericArray.newInstance(dims,
                    MWClassID.DOUBLE, MWComplexity.REAL);
            for (int i = 1; i <= n; i++) {
                x.set(i, i);
                y.set(i, i * i);
                z.set(i, i * 2);
            }*/
            thePlot = new plot();
            thePlot.plot3D(x1, y1, z1);
            thePlot.waitForFigures();

        } catch (Exception ex) {
            Logger.getLogger(DrawPlot.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Exception: " + ex.toString());
        } finally {
            /* Free native resources */
//            MWArray.disposeArray(x);
//            MWArray.disposeArray(y);
//            MWArray.disposeArray(z);
            //MWArray.disposeArray(y);
            if (thePlot != null) {
                thePlot.dispose();
            }
        }
    }

    public void draw3D_desc(double[] x, double[] y, double[] z, String[] desc) {
        MWNumericArray desc1 = null;
        try {
            /*int n = desc.length;
            
            int[] dims = {1, n};
            desc1 = MWNumericArray.newInstance(dims,
                    MWClassID.CELL, MWComplexity.COMPLEX);
            for (int i = 1; i <= n; i++) {
                desc1.set(i, desc[i-1]);                
            }*/
            thePlot = new plot();
            thePlot.plot3D_desc(x, y, z, desc);
            thePlot.waitForFigures();

        } catch (Exception ex) {
            Logger.getLogger(DrawPlot.class.getName()).log(Level.SEVERE, null, ex);
            //System.out.println("Exception: " + ex.toString());
        } finally {
            /* Free native resources */
            MWArray.disposeArray(desc1);
            /*MWArray.disposeArray(y);
            MWArray.disposeArray(z);*/
            //MWArray.disposeArray(y);
            if (thePlot != null) {
                thePlot.dispose();
            }
        }
    }
}
