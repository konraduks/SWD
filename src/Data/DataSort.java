/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author KonradD
 */
public class DataSort {
    
    public DataSort() {

    }
    
    // 0 - alfabetycznie, 1 -kolejnosc wystapien
    public String[] sort(String[] tab, int flag){
        String[] temp = new HashSet<String>(Arrays.asList(tab)).toArray(new String[0]);
        if(flag == 0){
            Arrays.sort(temp);
        }        
        //System.out.println(Arrays.toString(temp));
        String[] res = new String[tab.length];
        for(int i = 0, war = 0; i<tab.length; i++){            
            for(int j=0; j<temp.length; j++){
                if(tab[i].equals(temp[j])){
                    war = j + 1;
                    break;
                }
            }
            res[i] = war + "";
        }
        System.out.println(Arrays.toString(tab));
        System.out.println(Arrays.toString(res));
        return res;
    }   
}
