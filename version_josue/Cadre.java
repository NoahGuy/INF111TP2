import javax.swing.*;
import java.awt.*;

public class Cadre extends JFrame implements Runnable{

    private JPanel panneauPrincipal;

    @Override
    public void run() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setContentPane(new PanneauPrincipal());

        setVisible(true);
    }
}
