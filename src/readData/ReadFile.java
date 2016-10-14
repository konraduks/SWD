/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readData;

import Data.Data;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author KonradD
 */
public class ReadFile {

    /*public void readFromFileTXT() {
        Frame a = null;
        JCheckBox checkBox = new JCheckBox("wiersz nagłówkowy z nazwami kolumn");
        FileDialog fd = new FileDialog(a, "Wczytaj", FileDialog.LOAD);
        fd.setFile("*.txt;*.bin");
        fd.add(checkBox);
        //fd.  
        checkBox.setVisible(true);
        fd.setVisible(true);
        
        /*checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.err.println("dzialaj");
            }
        });*/

//        JFileChooser fc = new JFileChooser();
//        JPanel accessory = new JPanel();
//        JCheckBox isOpenBox = new JCheckBox("Open file after saving");
//        accessory.setLayout(new BorderLayout());
//        accessory.add(isOpenBox, BorderLayout.SOUTH);
//        fc.setAccessory(accessory);
//        fc.setVisible(true);

       /* String file = fd.getFile();
        String directory = fd.getDirectory();        
        try {
            readFile(directory + file);
        } catch (IOException ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    public void readFromFileTXT(boolean isHeaders) {
        Frame a = null;
        JCheckBox checkBox = new JCheckBox("wiersz nagłówkowy z nazwami kolumn");
        FileDialog fd = new FileDialog(a, "Wczytaj", FileDialog.LOAD);
        fd.setFile("*.txt;*.bin");
        fd.add(checkBox);
        //fd.  
        checkBox.setVisible(true);
        fd.setVisible(true);        

        String file = fd.getFile();
        String directory = fd.getDirectory();        
        try {
            readFile(directory + file, isHeaders);
        } catch (IOException ex) {
            Logger.getLogger(ReadFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void readFile(String pathRead, boolean isHeaders) throws FileNotFoundException, IOException {
        //System.err.println(pathRead);
        BufferedReader br = new BufferedReader(new FileReader(pathRead));
        /*size = Integer.parseInt(br.readLine());
        

        table = new int[size][size];
        possibilities = new int[size][size][];

        String[] temp;
        temp = br.readLine().split(" ");
        blockSize[0] = Integer.parseInt(temp[0]);
        blockSize[1] = Integer.parseInt(temp[1]);

        //wczytywanie wartosci
        for (int i = 0; i < size; i++) {
            temp = br.readLine().split(" ");
            for (int j = 0; j < size; j++) {
                int value = Integer.parseInt(temp[j]);
                table[i][j] = value;
            }
        }*/
        if(isHeaders){
            readHeader(br);
        }
        String line;
        Data data = Data.getInstance();
        while((line = br.readLine()) != null){
            if(line.startsWith("#") || line.isEmpty()){
                continue;
            }
            //System.out.println(line);
            String[] parts = line.split("\\s+");
            for(String part: parts){
                //System.out.println(part);
                data.add(part);
            }
            data.newRow();
        }
        
        //System.out.println("wypis z daty");
        //data.print();
    }
    
    private void readHeader(BufferedReader br) throws FileNotFoundException, IOException {
        Data data = Data.getInstance();
        String line;
        while(data.getHeaders().isEmpty() && (line = br.readLine()) != null){
            if(line.startsWith("#") || line.isEmpty()){
                continue;
            }
            //System.out.println("all: " + line);
            String[] parts = line.split("\\s+");
            for(String part: parts){
                //System.out.println(part);
                data.addHeader(part);
                //System.out.println("Nagłówek : " + part);
            }
        }
    }
}