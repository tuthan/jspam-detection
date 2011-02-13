/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guijnetpcap;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author LVT
 */
public class TableModel extends DefaultTableModel {
   
    Object[] columnNames = null;
    Object[][] data = null;
    public TableModel(Object[][] data ,Object[]column)
    {
        super(data,column);
        this.data=data;
        this.columnNames=column;
    }

}
