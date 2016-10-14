/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author KonradD
 */
public class DataSort1 {

    public DataSort1() {

    }

    //alfabetycznie
    ArrayList<TempData> tdata = new ArrayList();
    
    public void sortAphabetically(String[] tab){
        for(int i = 1; i<=tab.length; i++){
            tdata.add(new TempData(i, tab[i-1]));
        }
        
        Collections.sort(tdata, new Comparator<TempData>(){
            @Override
            public int compare(TempData td1, TempData td2) {
                return td1.value.compareTo(td2.value);
            }            
        });
        
        for(TempData td : tdata){
            System.out.println(" " + td.value);
        }
        
        /*Set<String> hs = new HashSet<>();
        
        
        for(TempData td : tdata){
            hs.add(td.value);
        }
        
        System.out.println(hs.toString());*/
        
        ArrayList<String> tempD = new ArrayList<>();
        for(TempData td : tdata){
            if(!tempD.contains(td.value)){
                tempD.add(td.value);
            }
        }
        
        for(String td : tempD){
            System.out.println(" " + td);
        }
    }
    
    //tworzenie tablicy zamiany srting na int, potrzebna aby powtorzenia mialy te sama wartosc
    private void changeStringToInt(){
        //tworzenie tablicy
        ArrayList<String> temp = new ArrayList<>();
        /*for(String s : tdata){
            
        }*/
    }
}

class TempData implements Comparable<String> {

    public int position;
    public String value;

    public TempData(int position, String value) {
        this.position = position;
        this.value = value;
    }

    @Override
    public int compareTo(String o) {
        return value.compareTo(o);
    }
}
