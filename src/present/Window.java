package present;

import model.Cj;
import model.Dokument;
import model.Spis;
import utils.CSVFileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Window extends JFrame {
    private Spis spis = new Spis();
    private Cj cisloJednaci = new Cj();
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);


    public Window() {
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("ESSL Light");

        JMenuBar menuBar = new JMenuBar();

        JMenu menuSoubor = new JMenu("Soubor");
        JMenuItem polozkaUloz = new JMenuItem("Ulož");
        polozkaUloz.addActionListener(e -> akceSouborUloz());
        JMenuItem polozkaNacti = new JMenuItem("Načti");
        polozkaNacti.addActionListener(e -> akceSouborNacti());
        JMenuItem polozkaKonec = new JMenuItem("Konec");
        polozkaKonec.addActionListener(e -> akceSouborKonec());
        menuSoubor.add(polozkaUloz);
        menuSoubor.addSeparator();
        menuSoubor.add(polozkaNacti);
        menuSoubor.addSeparator();
        menuSoubor.add(polozkaKonec);
        menuBar.add(menuSoubor);

        JMenu menuDokument = new JMenu("Dokument");
        JMenuItem polozkaVytvorit = new JMenuItem("Vytvořit");
        polozkaVytvorit.addActionListener(e -> akceDokumentVytvorit());
        JMenuItem polozkaUpravit = new JMenuItem("Upravit");
        polozkaUpravit.addActionListener(e -> akceDokumentUpravit());
        JMenuItem polozkaSmazat = new JMenuItem("Smazat");
        polozkaSmazat.addActionListener(e -> akceDokumentSmazat());
        menuDokument.add(polozkaVytvorit);
        menuDokument.addSeparator();
        menuDokument.add(polozkaUpravit);
        menuDokument.addSeparator();
        menuDokument.add(polozkaSmazat);
        menuBar.add(menuDokument);

        JMenu menuHledani = new JMenu("Hledání");
        JMenuItem polozkaVyhledatCj = new JMenuItem("Vyhledat v čísle jednacím");
        polozkaVyhledatCj.addActionListener(e -> akceDokumentVyhledatCj());
        JMenuItem polozkaVyhledatVec = new JMenuItem("Vyhledat ve věci");
        polozkaVyhledatVec.addActionListener(e -> akceDokumentVyhledatVec());
        JMenuItem polozkaVyhledatDetail = new JMenuItem("Vyhledat v detailu");
        polozkaVyhledatDetail.addActionListener(e -> akceDokumentVyhledatDetail());
        JMenuItem polozkaVse = new JMenuItem("Vypnout filtr");
        polozkaVse.addActionListener(e -> zobrazVse());
        menuHledani.add(polozkaVyhledatCj);
        menuHledani.addSeparator();
        menuHledani.add(polozkaVyhledatVec);
        menuHledani.addSeparator();
        menuHledani.add(polozkaVyhledatDetail);
        menuHledani.addSeparator();
        menuHledani.add(polozkaVse);
        menuBar.add(menuHledani);

        this.setJMenuBar(menuBar);


        model.addColumn("Číslo jednací");
        model.addColumn("Věc");
        model.addColumn("Detail");
        table.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        this.pack();
    }

    private void zobrazVse(){
        model.setRowCount(0);
        for(int i = 0; i < spis.velikostSpisu(); i++){
            Dokument dokument = spis.najdiDokument(i);
            if (dokument.getAktivni() == true) {
                model.addRow(new Object[] {dokument.getCj(), dokument.getVec(), dokument.getDetail()});
            }
        }
    }

    private void vyhledat(String hledany, int sloupec){
        model.setRowCount(0);
        for(int i = 0; i < spis.velikostSpisu(); i++){
            Dokument dokument = spis.najdiDokument(i);
            switch (sloupec){
                case 0:
                    if (dokument.getCj().contains(hledany) && dokument.getAktivni() == true) {
                        model.addRow(new Object[] {dokument.getCj(), dokument.getVec(), dokument.getDetail()});
                    }
                    break;
                case 1:
                    if (dokument.getVec().contains(hledany) && dokument.getAktivni() == true) {
                        model.addRow(new Object[] {dokument.getCj(), dokument.getVec(), dokument.getDetail()});
                    }
                    break;
                case 2:
                    if (dokument.getDetail().contains(hledany) && dokument.getAktivni() == true) {
                        model.addRow(new Object[] {dokument.getCj(), dokument.getVec(), dokument.getDetail()});
                    }
                    break;
            }
        }
    }

    private void akceSouborNacti(){
        final JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.dir").toString()));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        file.setFileFilter(filter);
        file.showOpenDialog(this);
        if (file.getSelectedFile() != null) {
            CSVFileHandler csv = new CSVFileHandler(file.getSelectedFile().toString());
            try {
                spis = csv.nacti();
                cisloJednaci.setPocitadlo(spis.velikostSpisu() + 1);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error File Not Found", "Varování", JOptionPane.WARNING_MESSAGE);
            }
            zobrazVse();
        }
    }

    private void akceSouborUloz(){
        final JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.dir").toString()));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        file.setFileFilter(filter);
        file.showSaveDialog(this);
        if (file.getSelectedFile() != null) {
            String fileName = file.getSelectedFile().toString();
            CSVFileHandler csv = new CSVFileHandler(fileName + ".csv");
            if (fileName.substring(fileName.length() - 3).contains("csv")) {
                csv = new CSVFileHandler(fileName);
            }
            try {
                csv.uloz(spis);
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error File Not Found", "Varování", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void akceSouborKonec() {
        System.exit(0);
    }

    private void akceDokumentVyhledatCj(){
        String hledany = JOptionPane.showInputDialog("Zadej hledané číslo jednací");
        vyhledat(hledany, 0);
    }

    private void akceDokumentVyhledatVec(){
        String hledany = JOptionPane.showInputDialog("Zadej hledanou věc");
        vyhledat(hledany, 1);
    }

    private void akceDokumentVyhledatDetail(){
        String hledany = JOptionPane.showInputDialog("Zadej hledaný detail");
        vyhledat(hledany, 2);
    }

    private void akceDokumentVytvorit(){
        String vec = JOptionPane.showInputDialog("Zadej věc dokumentu");
        String detail = JOptionPane.showInputDialog("Zadej detail dokumentu");
        Dokument d = new Dokument(cisloJednaci.getCj(), vec, detail);
        spis.zarad(d);
        zobrazVse();
    }

    private void akceDokumentUpravit(){
        if (table.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Není vybrán žádný řádek", "Varování", JOptionPane.WARNING_MESSAGE);
        } else {
            String vec = JOptionPane.showInputDialog("Zadej novou věc dokumentu");
            String detail = JOptionPane.showInputDialog("Zadej nový detail dokumentu");
            spis.najdiDokument(table.getValueAt(table.getSelectedRow(), 0).toString()).setVec(vec);
            spis.najdiDokument(table.getValueAt(table.getSelectedRow(), 0).toString()).setDetail(detail);
            zobrazVse();
        }
    }

    private void akceDokumentSmazat(){
        if (table.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Není vybrán žádný řádek", "Varování", JOptionPane.WARNING_MESSAGE);
        } else {
            spis.odeber(table.getValueAt(table.getSelectedRow(), 0).toString());
            zobrazVse();
        }
    }
}
