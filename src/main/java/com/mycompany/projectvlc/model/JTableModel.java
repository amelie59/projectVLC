/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.projectvlc.model;

import java.io.File;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author isen
 */
public class JTableModel extends AbstractTableModel {
    
    final Object donnees [][];
     final String[] entetes = {"Nom du film", "Durée", "Genre"};
     private File repertoire = new File("/home/isen/Video");
        File[] files = repertoire.listFiles();
        
        public JTableModel (){
        super();
        donnees = new Object[files.length][3];
         
        
}
       
            
            public int getRowCount() {
              
            return files.length;
           
            }

            public int getColumnCount() {
               return entetes.length;
            }

            public Object getValueAt(int rowIndex, int columnIndex) {
               return donnees[rowIndex][columnIndex];
            }
           
     @Override
            public boolean isCellEditable(int row, int col){
                // true si l'on veut pouvoir modifier les celulles, false ds le cas contraire
                return false;
            }
           
     @Override
            public void setValueAt(Object val, int row, int col){
                donnees[row][col] = val;
                fireTableCellUpdated(row, col);
            }
      
}
