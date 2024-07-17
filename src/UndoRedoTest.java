import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.swing.undo.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class UndoRedoTest {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ctrl z ctrl y");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTextArea textArea = new JTextArea(20, 40);
            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.add(scrollPane);

            //creer undomanager
            UndoManager undoManager = new UndoManager();
            Document doc = textArea.getDocument();

            //ajouter un undoableEditListener (ecoute les edits au text area qui peuvent etre undo)
            doc.addUndoableEditListener(new UndoableEditListener() {
                @Override
                public void undoableEditHappened(UndoableEditEvent e) {
                    undoManager.addEdit(e.getEdit());
                }
            });

            //bind ctrl z au undo
            textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_DOWN_MASK), "Undo");
            textArea.getActionMap().put("Undo", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (undoManager.canUndo()) {
                        undoManager.undo();
                    }
                }
            });

            //bind ctrl y au redo
            textArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                    KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK), "Redo");
            textArea.getActionMap().put("Redo", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (undoManager.canRedo()) {
                        undoManager.redo();
                    }
                }
            });

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}