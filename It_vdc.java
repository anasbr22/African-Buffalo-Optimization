

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;



public class It_vdc extends JPanel {

    private static final long serialVersionUID = 1L;
    private ArrayList<String> villes = new ArrayList<String>();
    private DefaultTableModel tableModelVilles;
    private DefaultTableModel tableModelMatrice;
    private JTable tableVilles;
    private JTable tableMatrice;
    private JScrollPane scrollPaneMatrice;
    private double[][] dataVilles; // Tableau global pour stocker les données
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_2_1;
    private JScrollPane scrollPaneLabel; //****

    /**
     * Create the panel.
     */
    public It_vdc() {
    	 
        setBackground(new Color(65, 201, 226));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(247, 238, 223));
        add(panel);
        panel.setLayout(null);

        JLabel lblProblmeDuVoyageur = new JLabel("Problème du voyageur de commerce");
        lblProblmeDuVoyageur.setFont(new Font("Segoe UI Black", Font.BOLD, 25));
        lblProblmeDuVoyageur.setBounds(23, 33, 539, 64);
        panel.add(lblProblmeDuVoyageur);

        // Tableau des noms de villes
        tableModelVilles = new DefaultTableModel(new Object[]{"Villes"}, 0);
        tableVilles = new JTable(tableModelVilles);
        tableVilles.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        tableVilles.setBackground(new Color(232, 232, 232));
        JScrollPane scrollPaneVilles = new JScrollPane(tableVilles);
        scrollPaneVilles.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        scrollPaneVilles.setBackground(new Color(232, 232, 232));
        scrollPaneVilles.setBounds(23, 108, 150, 200);
        panel.add(scrollPaneVilles);

        // Tableau de la matrice carrée de float
        tableModelMatrice = new DefaultTableModel(0, 0);
        tableMatrice = new JTable(tableModelMatrice);
        tableMatrice.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        scrollPaneMatrice = new JScrollPane(tableMatrice);
        scrollPaneMatrice.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        scrollPaneMatrice.setBackground(new Color(232, 232, 232));
        scrollPaneMatrice.setBounds(210, 108, 699, 276);
        panel.add(scrollPaneMatrice);

        // Bouton pour ajouter une ville
        JButton btnAjouterVille = new JButton("Ajouter Ville");
        btnAjouterVille.setBackground(new Color(232, 232, 232));
        btnAjouterVille.setBorder(new LineBorder(new Color(65, 201, 255), 1, true));
        btnAjouterVille.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnAjouterVille.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomVille = "Nouvelle Ville"; // Vous pouvez demander à l'utilisateur le nom de la ville
                villes.add(nomVille);
                tableModelVilles.addRow(new Object[]{nomVille});
                ajouterColonneEtLigne();
                ajusterScrollHorizontal();
            }
        });
        btnAjouterVille.setBounds(23, 358, 150, 25);
        panel.add(btnAjouterVille);

        // Bouton pour supprimer une ville
        JButton btnSupprimerVille = new JButton("Supprimer Ville");
        btnSupprimerVille.setBackground(new Color(232, 232, 232));
        btnSupprimerVille.setBorder(new LineBorder(new Color(65, 201, 255), 1, true));
        btnSupprimerVille.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnSupprimerVille.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tableVilles.getSelectedRow() != -1) {
                    int index = tableVilles.getSelectedRow();
                    villes.remove(index);
                    tableModelVilles.removeRow(index);
                    supprimerColonneEtLigne(index);
                    ajusterScrollHorizontal();
                }
            }
        });
        btnSupprimerVille.setBounds(23, 394, 150, 25);
        panel.add(btnSupprimerVille);

        // Bouton pour supprimer toutes les villes
        JButton btnSupprimerToutesVilles = new JButton("Supprimer Toutes");
        btnSupprimerToutesVilles.setBackground(new Color(232, 232, 232));
        btnSupprimerToutesVilles.setBorder(new LineBorder(new Color(65, 201, 255), 1, true));
        btnSupprimerToutesVilles.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnSupprimerToutesVilles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                villes.clear();
                tableModelVilles.setRowCount(0);
                tableModelMatrice.setRowCount(0);
                tableModelMatrice.setColumnCount(0);
                ajusterScrollHorizontal();
            }
        });
        btnSupprimerToutesVilles.setBounds(23, 436, 150, 25);
        panel.add(btnSupprimerToutesVilles);

        // Bouton pour valider et stocker les données
        JButton btnValider = new JButton("Valider");
        btnValider.setBackground(new Color(232, 232, 232));
        btnValider.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        btnValider.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        btnValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowCount = tableModelMatrice.getRowCount();
                int columnCount = tableModelMatrice.getColumnCount();
                dataVilles = new double[rowCount][columnCount];
                for (int i = 0; i < rowCount; i++) {
                    for (int j = 0; j < columnCount; j++) {
                        Object value = tableModelMatrice.getValueAt(i, j);
                        if (value instanceof Double) {
                            dataVilles[i][j] = (Double) value;
                        } else if (value instanceof Float) {
                            dataVilles[i][j] = ((Double) value).doubleValue();
                        } else if (value instanceof String) {
                            try {
                                dataVilles[i][j] = Double.parseDouble((String) value);
                            } catch (NumberFormatException ex) {
                                // Gérer les cas où la conversion échoue
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                
               
                
                int nb_buffalos= Integer.parseInt(textField.getText());
                double LP1= Double.parseDouble(textField_1.getText());    
                double LP2= Double.parseDouble(textField_2.getText());
                int n_itr= Integer.parseInt(textField_3.getText());
                
                
                // Obtenir le temps avant l'exécution
                long startTime = System.currentTimeMillis();

                // Créer l'objet ABOForTSP
                ABOForTSP ABO1 = new ABOForTSP(nb_buffalos, n_itr, LP1, LP2, dataVilles);

                // Obtenir le temps après l'exécution
                long endTime = System.currentTimeMillis();

                // Calculer le temps d'exécution en millisecondes
                long executionTime = endTime - startTime;
                
                /*
                ABO
                 //data -> dataVilles (double[rowCount][columnCount])
                 //nombre buffalos -> textField
                 // LP1 -> textField_1
                 //LP2 -> textField_2
                 //d'itérations  -> textField_3
                 //affichage Chemin optimal   -> lblNewLabel_2
                 //affichage Distance totale parcourue   -> lblNewLabel_2_1
              */
                
                
                List<Integer> best_r = ABO1.getBestRoute();
                System.out.println(""+best_r);
                System.out.println(ABO1.getBestDistance());
                
                String best_r_string = String.join("->", best_r.stream().map(Object::toString).toArray(String[]::new));
                updateLabelText(best_r_string);
                
                lblNewLabel_2_1.setText("<html> "+ABO1.getBestDistance()+" <br>	temps d'exécution	<br> "+executionTime+" ms"+ " </html>");
                
                
                
                
                
                
                
                
               
            }
        });
        btnValider.setBounds(23, 539, 150, 25);
        panel.add(btnValider);

        JButton btnChoisirFichier = new JButton("Choisir un fichier...");
        btnChoisirFichier.setBackground(new Color(232, 232, 232));
        btnChoisirFichier.setBorder(new LineBorder(new Color(65, 201, 255), 1, true));
        btnChoisirFichier.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnChoisirFichier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choisir un fichier");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);

                // Filtre pour les fichiers CSV
                FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("Fichiers CSV (*.csv)", "csv");
                fileChooser.addChoosableFileFilter(csvFilter);

                // Filtre pour les fichiers TXT
                FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Fichiers TXT (*.txt)", "txt");
                fileChooser.addChoosableFileFilter(txtFilter);

                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    chargerMatriceDepuisFichier(selectedFile.getAbsolutePath());
                }
            }
        });
        btnChoisirFichier.setBounds(23, 472, 150, 25);
        panel.add(btnChoisirFichier);
        

        scrollPaneLabel = new JScrollPane();
        scrollPaneLabel.setBounds(210, 456, 699, 108);
        panel.add(scrollPaneLabel);
        lblNewLabel_2 = new JLabel("\r\n");
        lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.ITALIC, 13));
        lblNewLabel_2.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        lblNewLabel_2.setBackground(new Color(230, 202, 162));
        scrollPaneLabel.setViewportView(lblNewLabel_2);
        
        lblNewLabel_2_1 = new JLabel("");
        lblNewLabel_2_1.setFont(new Font("Segoe UI Black", Font.BOLD, 13));
        lblNewLabel_2_1.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        lblNewLabel_2_1.setBackground(new Color(232, 232, 232));
        lblNewLabel_2_1.setBounds(919, 456, 120, 108);
        panel.add(lblNewLabel_2_1);
        
        JLabel lblNewLabel_1_2 = new JLabel("Chemin optimal");
        lblNewLabel_1_2.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1_2.setBounds(210, 411, 230, 39);
        panel.add(lblNewLabel_1_2);
        
        JLabel lblNewLabel_1_2_1 = new JLabel("Distance totale");
        lblNewLabel_1_2_1.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1_2_1.setBounds(919, 411, 120, 39);
        panel.add(lblNewLabel_1_2_1);
        
        textField = new JTextField("10");
        textField.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField.setBackground(new Color(232, 232, 232));
        textField.setBounds(919, 155, 120, 25);
        panel.add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField("0.5");
        textField_1.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField_1.setBackground(new Color(232, 232, 232));
        textField_1.setColumns(10);
        textField_1.setBounds(919, 218, 120, 25);
        panel.add(textField_1);
        
        textField_2 = new JTextField("0.6");
        textField_2.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField_2.setBackground(new Color(232, 232, 232));
        textField_2.setColumns(10);
        textField_2.setBounds(919, 283, 120, 25);
        panel.add(textField_2);
        
        textField_3 = new JTextField("1000");
        textField_3.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField_3.setBackground(new Color(232, 232, 232));
        textField_3.setColumns(10);
        textField_3.setBounds(919, 347, 120, 25);
        panel.add(textField_3);
        
        JLabel lblNewLabel_1_2_1_1 = new JLabel("Nombre de buffles");
        lblNewLabel_1_2_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1.setBounds(919, 108, 120, 39);
        panel.add(lblNewLabel_1_2_1_1);
        
        JLabel lblNewLabel_1_2_1_1_1 = new JLabel("LP1");
        lblNewLabel_1_2_1_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1_1.setBounds(919, 179, 120, 39);
        panel.add(lblNewLabel_1_2_1_1_1);
        
        JLabel lblNewLabel_1_2_1_1_2 = new JLabel("LP2");
        lblNewLabel_1_2_1_1_2.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1_2.setBounds(919, 243, 120, 39);
        panel.add(lblNewLabel_1_2_1_1_2);
        
        JLabel lblNewLabel_1_2_1_1_3 = new JLabel("N. Itérations");
        lblNewLabel_1_2_1_1_3.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1_3.setBounds(919, 307, 120, 39);
        panel.add(lblNewLabel_1_2_1_1_3);
        
        JButton btnNewButton = new JButton("Retour");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Supprimer le frame actuel
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(It_vdc.this);
                frame.dispose();
                
                // Afficher le frame de la classe Home
                Home homeFrame = new Home();
                homeFrame.setVisible(true);
            }
        });
        btnNewButton.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        btnNewButton.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        btnNewButton.setBackground(new Color(232, 232, 232));
        btnNewButton.setBounds(902, 23, 137, 39);
        panel.add(btnNewButton);

    }
    
    
    private void updateLabelText(String text) {
        String formattedText = formatTextToHTML(text, 90);
        lblNewLabel_2.setText(formattedText);
        lblNewLabel_2.revalidate(); // Force la mise à jour de l'affichage
        scrollPaneLabel.getVerticalScrollBar().setValue(0); // Réinitialise la position de la barre de défilement à la partie supérieure
    }
    
    
    
    public String formatTextToHTML(String text, int maxLength) {
        StringBuilder builder = new StringBuilder();
        int length = text.length();
        builder.append("<html>");
        for (int i = 0; i < length; i += maxLength) {
            if (i + maxLength < length) {
                builder.append(text.substring(i, i + maxLength)).append("<br>");
            } else {
                builder.append(text.substring(i));
            }
        }
        builder.append("</html>");
        return builder.toString();
    }


    private void mettreAJourNomsVilles() {
        // Efface les noms des villes actuels dans tableVilles
        tableModelVilles.setRowCount(0);

        // Ajoute les noms des villes de la même manière qu'ils sont dans tableMatrice
        for (int i = 0; i < tableModelMatrice.getRowCount(); i++) {
            tableModelVilles.addRow(new Object[]{tableModelMatrice.getColumnName(i)});
        }
    }

    private void ajouterColonneEtLigne() {
        int size = villes.size();
        tableModelMatrice.addColumn("Ville " + size);
        tableModelMatrice.addRow(new Object[size]);
        for (int i = 0; i < size; i++) {
            tableModelMatrice.setValueAt(0.0, i, size - 1);
            tableModelMatrice.setValueAt(0.0, size - 1, i);

        }
        mettreAJourNomsVilles();
        // Définir une largeur minimale de 50 pixels pour les cellules
        tableMatrice.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < tableMatrice.getColumnCount(); i++) {
            tableMatrice.getColumnModel().getColumn(i).setMinWidth(50);
        }
    }

    private void supprimerColonneEtLigne(int index) {
        for (int i = index; i < tableModelMatrice.getRowCount() - 1; i++) {
            for (int j = 0; j < tableModelMatrice.getColumnCount(); j++) {
                tableModelMatrice.setValueAt(tableModelMatrice.getValueAt(i + 1, j), i, j);
            }
        }
        for (int j = index; j < tableModelMatrice.getColumnCount() - 1; j++) {
            for (int i = 0; i < tableModelMatrice.getRowCount(); i++) {
                tableModelMatrice.setValueAt(tableModelMatrice.getValueAt(i, j + 1), i, j);
            }
        }
        tableModelMatrice.setColumnCount(tableModelMatrice.getColumnCount() - 1);
        tableModelMatrice.setRowCount(tableModelMatrice.getRowCount() - 1);
        mettreAJourNomsVilles();
    }

    private void ajusterScrollHorizontal() {
        int totalWidth = tableMatrice.getPreferredSize().width;
        int visibleWidth = scrollPaneMatrice.getViewport().getWidth();
        if (totalWidth > visibleWidth) {
            scrollPaneMatrice.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        } else {
            scrollPaneMatrice.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }
        scrollPaneMatrice.revalidate();
    }

    private void chargerMatriceDepuisFichier(String nomFichier) {
        File fichier = new File(nomFichier);
        if (!fichier.exists() || !fichier.isFile()) {
            System.out.println("Le fichier n'existe pas ou n'est pas un fichier.");
            return;
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fichier));
            ArrayList<String[]> lignes = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lignes.add(line.split(";"));
            }

            // Mettre à jour les noms de villes
            tableModelVilles.setRowCount(lignes.size());
            for (int i = 0; i < lignes.size(); i++) {
                tableModelVilles.setValueAt("Ville " + (i + 1), i, 0);
            }

            // Mettre à jour la matrice
            int rowCount = lignes.size();
            int columnCount = lignes.get(0).length;
            tableModelMatrice.setRowCount(rowCount);
            tableModelMatrice.setColumnCount(columnCount);
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    tableModelMatrice.setValueAt(Double.parseDouble(lignes.get(i)[j]), i, j);
                }
            }

            // Définir une largeur minimale de 50 pixels pour les cellules
            tableMatrice.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            for (int i = 0; i < tableMatrice.getColumnCount(); i++) {
                tableMatrice.getColumnModel().getColumn(i).setMinWidth(50);
            }
            ajusterScrollHorizontal();
            lblNewLabel_2.setText("");
            lblNewLabel_2_1.setText("");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

   
}






//data -> dataVilles (double[rowCount][columnCount])
//nombre buffalos -> textField
// LP1 -> textField_1
//LP2 -> textField_2
//d'itérations  -> textField_3
//affichage Chemin optimal   -> lblNewLabel_2
//affichage Distance totale parcourue   -> lblNewLabel_2_1







