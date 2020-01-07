package present;

import model.Dokument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class DocumentWindow extends JFrame {
    private Dokument dokumnet;
    private Action actionSave, actionClose;

    public DocumentWindow(Dokument dokument) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.dokumnet = dokument;
        this.setTitle("Dokument " + dokument.getCj());

        this.setVisible(true);

        initActions();

        JMenuBar menuBar = new JMenuBar();

        JMenu menuDokument = new JMenu("Dokument");
        menuDokument.add(actionSave);
        menuDokument.addSeparator();
        menuDokument.add(actionClose);
        menuBar.add(menuDokument);

        this.setJMenuBar(menuBar);

        JToolBar toolBar = new JToolBar("Nástroje", JToolBar.HORIZONTAL);
        toolBar.setFloatable(false);

        toolBar.add(actionSave);
        toolBar.add(actionClose);

        this.add(toolBar, "North");

        setSize(300,400);

        this.pack();
    }

    private void initActions() {
        actionSave = new AbstractAction("Ulož", new ImageIcon(getClass().getResource("/icons/saveBinder.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        };
        actionSave.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke('S', InputEvent.CTRL_MASK));
        actionSave.putValue(Action.SHORT_DESCRIPTION, "Uložení souboru");

        actionClose = new AbstractAction("Konec", new ImageIcon(getClass().getResource("/icons/exit.png"))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        actionClose.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        actionClose.putValue(Action.SHORT_DESCRIPTION, "Ukončení aplikace");

    }
}
