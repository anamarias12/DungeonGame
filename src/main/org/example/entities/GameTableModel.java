package main.org.example.entities;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

class GameTableModel extends AbstractTableModel {
    private final int rows;
    private final int cols;
    private Cell[][] gridData;

    public GameTableModel(Grid grid) {
        this.rows = grid.getLength();
        this.cols = grid.getWidth();
        this.gridData = grid.getGridStructure();
    }

    public void updateGrid(Grid grid) {
        this.gridData = grid.getGridStructure();
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return cols;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return gridData[rowIndex][columnIndex];
    }
}