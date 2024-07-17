import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PanneauPrincipal extends JPanel {

    private JTextPane leText;
    private JTextField barRecherche;
    private JButton btnRecherche;
    private SimpleAttributeSet rouge;
    private SimpleAttributeSet vert;
    private SimpleAttributeSet jaune;
    private SimpleAttributeSet defaut;
    private StyledDocument doc;
    private String texte;
    private String chaineCaratereRecherchee;

    JMenu nomMenu;
    JMenuBar menuBar;


    public PanneauPrincipal() {

        initComposants();
    }

    /**
     * initialisation des composants
     */
    private void initComposants() {

        // initialisation du espace de texte pour rechercher un mot
        barRecherche = new JTextField();
        barRecherche.setPreferredSize(new Dimension(200, 30));

        leText = new JTextPane();
        leText.setPreferredSize(new Dimension(400, 300));

        // le style pour surligner le JTextPane en rouge
        rouge = new SimpleAttributeSet();
        StyleConstants.setBackground(rouge, Color.RED);

        // le style pour surligner le JTextPane en vert
        vert = new SimpleAttributeSet();
        StyleConstants.setBackground(vert, Color.GREEN);

        // le style pour surligner le JTextPane en jaune
        jaune = new SimpleAttributeSet();
        StyleConstants.setBackground(jaune, Color.YELLOW);

        defaut = new SimpleAttributeSet();

        doc = leText.getStyledDocument();

        // initie un bouton pour rechercher la chaine de caractère
        btnRecherche = new JButton("Rechercher");


        /**
         * bouton qui fait la recherche selon ce qui est écris sur le JTextField
         */
        btnRecherche.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // on prend Le texte qu'on a ecris dans le JTextPane en String
                texte = leText.getText();

                // on prend ce qu'on a ecris dans la barre de recherche en
                // String
                chaineCaratereRecherchee = barRecherche.getText();

                // on obtient la position du curseur actuel
                int positionCurseur = leText.getCaretPosition();

                // apres une nouvelle recherche on remment le texte à son style par defaut
                doc.setCharacterAttributes(0, doc.getLength(), defaut, true);

                if(!chaineCaratereRecherchee.isEmpty()) {

                    // on surligne en jaune tous les occurences
                    surlignerEnJaune(chaineCaratereRecherchee);

                    // on surligne en rouge à partir du curseur
                    doc.setCharacterAttributes(positionCurseur, 1, rouge, true);

                    // on surliggne en vert pour la chaine de caractere rechercher après le curseur
                    surlignerEnVert(chaineCaratereRecherchee);
                }
            }
        });

        // ajoute les composants au panneau principal
        this.add(barRecherche);
        this.add(btnRecherche);
        this.add(leText);

    }

    /**<
     * Recherche la chaine de caractère
     *
     * @param motRechercher
     */
    private void surlignerEnJaune(String motRechercher){

        // change les String en minuscule pour la recherche insensible
        texte = texte.toLowerCase();
        motRechercher = motRechercher.toLowerCase();

        // on transforme le texte ecris en String et remplace les \n par du
        // vide (car doc.setCharacterAttributes ne les detecte pas)
        texte = texte.replace("\n","");

        int index = 0;

        // La boucle continue tant qu'une occurrence du mot recherché est
        // trouvee
        while ((index = texte.indexOf(motRechercher, index)) >= 0) {

                // on surligne le caractère recherchee
                doc.setCharacterAttributes(index, motRechercher.length(), jaune, true);

                // on passe à prochaine occurence
                index += motRechercher.length();

        }

    }

    /**
     * surligne dans le JTextPane en vert l'occurence après le curseur
     *
     * @param motRechercher
     */
    private void surlignerEnVert(String motRechercher){

        // case insensible
        texte = texte.toLowerCase();
        motRechercher = motRechercher.toLowerCase();

        // obitent position curseur
        int positionCurseur = leText.getCaretPosition();
        // cherche la position de l'occurence après le curseur
        int index = texte.indexOf(motRechercher, positionCurseur);
        doc.setCharacterAttributes(index, motRechercher.length(), vert, true);


    }



}
