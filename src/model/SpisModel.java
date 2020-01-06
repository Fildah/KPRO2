package model;

import javax.swing.table.AbstractTableModel;

public class SpisModel extends AbstractTableModel {
    Spis spis;
    String[] names = {"Číslo jednací", "Věc", "Detail"};

    public SpisModel(Spis spis) {
        this.spis = spis;
    }

    @Override
    public int getRowCount() {
        return spis.velikostSpisu();
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Dokument dokument = spis.najdiDokument(rowIndex);

        switch (columnIndex) {
            case 0 :
                return dokument.getCj();
            case 1 :
                return dokument.getVec();
            case 2 :
                return dokument.getDetail();
        }

        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (spis.velikostSpisu() == 0) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Dokument dokument = spis.najdiDokument(rowIndex);

        switch (columnIndex) {
            case 1 :
                dokument.setVec(aValue.toString());
            case 2 :
                dokument.setDetail(aValue.toString());
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    public void refresh() {
        fireTableDataChanged();
    }
}
