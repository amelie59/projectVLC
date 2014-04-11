/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.projectvlc.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.io.File;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author isen
 */
public class JTableModel extends AbstractTableModel {
    
    final Object donnees [][];
      Object temp [][];
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
