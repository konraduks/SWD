/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import cern.colt.Arrays;

/**
 *
 * @author KonradD
 */
public class ParseDataToMatlab {
    
    public ParseDataToMatlab(){
        
    }
    
    public void parse(double[][] data){
        //System.out.println(Arrays.toString(data[0]));
        System.out.print("[ ");
        for(int i = 0; i < data.length; i++){
            int size = Arrays.toString(data[i]).length();
            System.out.println(Arrays.toString(data[i]).substring(1, size - 1) + ";");
        }
        System.out.println("]");
    }
    
    public void compareData(String[] decisionClass){        
        int[] data = new int[]{4, 11, 2, 1, 2, 2, 2, 2, 2, 2, 4, 4, 11, 11, 1, 2, 2, 9, 1, 11, 2, 11, 4, 11, 0, 11, 1, 0, 0, 4, 4, 11, 1, 9, 11, 2, 1, 4, 4, 4, 9, 11, 11, 4, 4, 9, 4, 2, 4, 4, 1, 1, 11, 11, 1, 4, 9, 2, 1, 2, 11, 11, 9, 11, 11, 4, 11, 11, 9, 4, 11, 11, 9, 2, 2, 11, 11, 9, 1, 11, 8, 2, 9, 8, 5, 4, 4, 4, 1, 1, 1, 4, 6, 1, 11, 2, 11, 11, 4, 11, 11, 4, 9, 2, 11, 2, 1, 1, 4, 2, 1, 4, 4, 4, 2, 11, 1, 1, 4, 1, 2, 11, 11, 11, 11, 11, 2, 2, 4, 11, 10, 1, 11, 1, 1, 2, 1, 2, 2, 2, 1, 4, 11, 11, 11, 11, 2, 11, 4, 11, 4, 11, 1, 11, 2, 4, 1, 2, 9, 4, 4, 1, 1, 4, 11, 1, 11, 1, 4, 9, 0, 1, 9, 2, 3, 5, 4, 11, 1, 1, 2, 11, 1, 9, 11, 4, 6, 11, 11, 4, 5, 11, 11, 2, 2, 9, 9, 1, 2, 0, 2, 1, 11, 11, 2, 0, 11, 4, 1, 11, 11, 11, 11, 11, 11, 0, 11, 2, 11, 2, 3, 1, 11, 11, 1, 0, 4, 4, 9, 0, 4, 11, 3, 0, 2, 11, 4, 1, 11, 2, 11, 2, 2, 4, 1, 1, 11, 2, 2, 4, 4, 1, 2, 11, 1, 1, 1, 4, 0, 11, 2, 9, 4, 4, 11, 11, 1, 11, 6, 2, 1, 4, 4, 0, 11, 9, 1, 2, 9, 2, 4, 2, 10, 11, 11, 2, 2, 2, 0, 3, 1, 9, 4, 0, 1, 2, 1, 11, 11, 11, 1, 1, 2, 2, 11, 9, 2, 2, 2, 4, 11, 11, 11, 4, 2, 4, 2, 1, 0, 2, 0, 11, 1, 3, 0, 4, 2, 4, 1, 0, 11, 2, 9, 5, 4, 1, 1, 4, 0, 4, 2, 2, 2, 9, 11, 1, 2, 11, 2, 9, 5, 4, 2, 11, 11, 7, 2, 8, 11, 2, 0, 9, 11, 2, 4, 0, 11, 11, 11, 11, 2, 4, 1, 1, 2, 11, 9, 9, 0, 9, 1, 4, 11, 4, 5, 11, 11, 2, 2, 2, 11, 1, 0, 1, 11, 1, 11, 1, 2, 11, 11, 4, 2, 1, 2, 11, 4, 11, 3, 11, 11};
        int length = 12;
        int[][] res = new int[length][length];
        
        for(int i = 0; i < data.length; i++){
            int value = Integer.parseInt(decisionClass[i]);
            value--;
            if(value == 13){
                value = 10;
            }else if(value == 15){
                value = 11;
            }
            res[value][data[i]]++;
        }
        for(int[] row : res){
            System.out.println(Arrays.toString(row));
        }
    }
}
