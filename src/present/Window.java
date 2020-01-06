package present;

import model.Cj;
import model.Dokument;
import model.Spis;
import model.SpisModel;
import utils.CSVFileHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Window extends JFrame {
    private Spis spis = new Spis();
    private Cj cisloJednaci = new Cj();
    private SpisModel model = new SpisModel(spis);
    private JTable table = new JTable(model);
    private TableRowSorter<SpisModel> sorter = new TableRowSorter<>(model);

    private Action actionNew, actionOpen, actionSave, actionClose, actionCreate, actionEdit, actionDelete, actionFind, actionClearFilter;


    public Window() {
        table.setAutoCreateRowSorter(true);
        table.setRowSorter(sorter);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("ESSL Light");

        initActions();

        JMenuBar menuBar = new JMenuBar();

        JMenu menuSoubor = new JMenu("Soubor");
        menuSoubor.add(actionNew);
        menuSoubor.addSeparator();
        menuSoubor.add(actionOpen);
        menuSoubor.addSeparator();
        menuSoubor.add(actionSave);
        menuSoubor.addSeparator();
        menuSoubor.add(actionClose);
        menuBar.add(menuSoubor);

        JMenu menuDokument = new JMenu("Dokument");
        menuDokument.add(actionCreate);
        menuDokument.addSeparator();
        menuDokument.add(actionEdit);
        menuDokument.addSeparator();
        menuDokument.add(actionDelete);
        menuBar.add(menuDokument);

        JMenu menuHledani = new JMenu("Hledání");
        JMenuItem polozkaVyhledatVec = new JMenuItem("Vyhledat ve věci");
        polozkaVyhledatVec.addActionListener(e -> akceDokumentVyhledatVec());
        JMenuItem polozkaVyhledatDetail = new JMenuItem("Vyhledat v detailu");
        polozkaVyhledatDetail.addActionListener(e -> akceDokumentVyhledatDetail());
        menuHledani.add(actionFind);
        menuHledani.addSeparator();
        menuHledani.add(polozkaVyhledatVec);
        menuHledani.addSeparator();
        menuHledani.add(polozkaVyhledatDetail);
        menuHledani.addSeparator();
        // menuHledani.add(polozkaVse);
        menuBar.add(menuHledani);

        this.setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar("Nástroje",JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);

        toolBar.add(actionNew);
        toolBar.addSeparator();
        toolBar.add(actionOpen);
        toolBar.add(actionSave);
        toolBar.addSeparator();
        toolBar.addSeparator();
        toolBar.add(actionFind);
        toolBar.addSeparator();
        this.add(toolBar, "North");

        table.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(table), BorderLayout.CENTER);

        this.pack();
    }

    private void initActions() {
        actionNew = new AbstractAction("Nový", new ImageIcon(getClass().getResource("/icons/newBinder.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                spis.clear();
                model.refresh();
            }
        };
        actionNew.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK ));
        actionNew.putValue(Action.SHORT_DESCRIPTION,"Vytvoř nový soubor");

        actionOpen = new AbstractAction("Načti", new ImageIcon(getClass().getResource("/icons/loadBinder.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                nactiSoubor();
            }
        };
        actionOpen.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('O', InputEvent.CTRL_MASK ));
        actionOpen.putValue(Action.SHORT_DESCRIPTION,"Otevreni souboru");

        actionSave = new AbstractAction("Ulož", new ImageIcon(getClass().getResource("/icons/saveBinder.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                ulozSoubor();
            }
        };
        actionSave.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK ));
        actionSave.putValue(Action.SHORT_DESCRIPTION,"Uložení souboru");

        actionClose = new AbstractAction("Konec", new ImageIcon(getClass().getResource("/icons/exit.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        actionClose.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_F4,InputEvent.ALT_MASK ));
        actionClose.putValue(Action.SHORT_DESCRIPTION,"Ukončení aplikace");

        actionCreate = new AbstractAction("Vytvořit", new ImageIcon(getClass().getResource("/icons/newDocument.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                vytvorDokument();
            }
        };
        actionCreate.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('D', InputEvent.CTRL_MASK ));
        actionCreate.putValue(Action.SHORT_DESCRIPTION,"Vytvoření nového dukumentu");

        actionEdit = new AbstractAction("Upravit", new ImageIcon(getClass().getResource("/icons/editDocument.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                upravDokument();
            }
        };
        actionEdit.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('E', InputEvent.CTRL_MASK ));
        actionEdit.putValue(Action.SHORT_DESCRIPTION,"Upravení vybraného dokument");

        actionDelete = new AbstractAction("Smazat", new ImageIcon(getClass().getResource("/icons/deleteDocument.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                smazDokument();
            }
        };
        actionDelete.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('R', InputEvent.CTRL_MASK ));
        actionDelete.putValue(Action.SHORT_DESCRIPTION,"Smazaní vybraného dokumentu");

        actionFind = new AbstractAction("Hledej", new ImageIcon(getClass().getResource("/icons/find.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                hledej();
            }
        };
        actionFind.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke('F', InputEvent.CTRL_MASK ));
        actionFind.putValue(Action.SHORT_DESCRIPTION,"Vyhledání vybraného/ných záznamu/ů");

    }

    private void nactiSoubor(){
        final JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV FILES", "csv");
        file.setFileFilter(filter);
        file.showOpenDialog(this);
        if (file.getSelectedFile() != null) {
            CSVFileHandler csv = new CSVFileHandler(file.getSelectedFile().toString());
            try {
                spis.clear();
                csv.nacti(spis);
                cisloJednaci.setPocitadlo(spis.velikostSpisu() + 1);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error File Not Found", "Varování", JOptionPane.WARNING_MESSAGE);
            }
            model.refresh();
        }
    }

    private void ulozSoubor(){
        final JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.dir")));
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
        model.refresh();
    }

    private void hledej(){
        String hledany = JOptionPane.showInputDialog("Zadej hledané číslo jednací");
        System.out.println(hledany);
        RowFilter<SpisModel, Object> rf;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(hledany, 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    private void akceDokumentVyhledatVec(){
        String hledany = JOptionPane.showInputDialog("Zadej hledanou věc");
        // vyhledat(hledany, 1);
    }

    private void akceDokumentVyhledatDetail(){
        String hledany = JOptionPane.showInputDialog("Zadej hledaný detail");
        //  vyhledat(hledany, 2);
    }

    private void vytvorDokument(){
        String vec = JOptionPane.showInputDialog("Zadej věc dokumentu");
        String detail = JOptionPane.showInputDialog("Zadej detail dokumentu");
        Dokument d = new Dokument(cisloJednaci.getCj(), vec, detail);
        spis.zarad(d);
        model.refresh();
    }

    private void upravDokument(){
        if (table.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Není vybrán žádný řádek", "Varování", JOptionPane.WARNING_MESSAGE);
        } else {
            String vec = JOptionPane.showInputDialog("Zadej novou věc dokumentu");
            String detail = JOptionPane.showInputDialog("Zadej nový detail dokumentu");
            spis.najdiDokument(table.getValueAt(table.getSelectedRow(), 0).toString()).setVec(vec);
            spis.najdiDokument(table.getValueAt(table.getSelectedRow(), 0).toString()).setDetail(detail);
        }
        model.refresh();
    }

    private void smazDokument(){
        if (table.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(this, "Není vybrán žádný řádek", "Varování", JOptionPane.WARNING_MESSAGE);
        } else {
            spis.odeber(table.getValueAt(table.getSelectedRow(), 0).toString());
        }
        model.refresh();
    }
}
