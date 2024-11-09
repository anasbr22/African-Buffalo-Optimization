

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;



public class It_sad extends JPanel {

    private static final long serialVersionUID = 1L;
    private DefaultTableModel model;
    private JTable table;
    private List<float[]> tabData = new ArrayList<>(); // Tableau pour stocker les données sous forme de float
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JLabel lblNewLabel_2;
    private JLabel lblNewLabel_3;
    private JTextField textField_4;
    private JLabel lblNewLabel_3_1;
    private JLabel lblNewLabel_3_2;

  

    /**
     * Create the panel.
     */
    public It_sad() {
    	 	
        setBackground(new Color(65, 201, 226));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(247, 238, 223));
        add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("     Problème du sac à dos");
        lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 25));
        lblNewLabel.setBounds(23, 26, 539, 64);
        panel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Liste d'objets");
        lblNewLabel_1.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1.setBounds(54, 133, 189, 39);
        panel.add(lblNewLabel_1);

        // Créer un tableau avec trois colonnes : numéro d'objet, poids, valeur
        String[] columnNames = {"Numéro objet", "Poids", "Valeur"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0; // Rendre les cellules modifiables sauf la première colonne
            }
        };
        table = new JTable(model);
        table.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Sélection unique
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        scrollPane.setBounds(54, 182, 457, 157);
        panel.add(scrollPane);

        // Bouton pour ajouter une ligne dans le tableau
        JButton btnAjouter = new JButton("Ajouter ligne ");
        btnAjouter.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnAjouter.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        btnAjouter.setBackground(new Color(232, 232, 232));
        btnAjouter.setBounds(54, 350, 143, 30);
        panel.add(btnAjouter);

        // Action du bouton pour ajouter une ligne dans le tableau
        btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int rowCount = model.getRowCount() + 1;
                model.addRow(new Object[]{rowCount, 0.0f, 0.0f}); // Ajouter une ligne avec des valeurs par défaut
            }
        });

        // Bouton pour supprimer la ligne sélectionnée
        JButton btnSupprimerSelection = new JButton("Supprimer ligne");
        btnSupprimerSelection.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnSupprimerSelection.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        btnSupprimerSelection.setBackground(new Color(232, 232, 232));
        btnSupprimerSelection.setBounds(215, 350, 143, 30);
        panel.add(btnSupprimerSelection);

        // Action du bouton pour supprimer la ligne sélectionnée
        btnSupprimerSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                    // Mettre à jour les numéros d'objet après la suppression
                    updateObjectNumbers();
                }
            }
        });

        // Bouton pour supprimer toutes les lignes
        JButton btnSupprimerTout = new JButton("Supprimer tout");
        btnSupprimerTout.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnSupprimerTout.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        btnSupprimerTout.setBackground(new Color(232, 232, 232));
        btnSupprimerTout.setBounds(368, 350, 143, 30);
        panel.add(btnSupprimerTout);

        // Action du bouton pour supprimer toutes les lignes
        btnSupprimerTout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.setRowCount(0);
            }
        });

        // Bouton pour lire à partir d'un fichier CSV
        JButton btnLireCSV = new JButton("Lire CSV");
        btnLireCSV.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnLireCSV.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        btnLireCSV.setBackground(new Color(232, 232, 232));
        btnLireCSV.setBounds(54, 483, 143, 30);
        panel.add(btnLireCSV);

        // Action du bouton pour lire à partir d'un fichier CSV
        btnLireCSV.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importerDonneesCSV();
            }
        });

        // Bouton pour lire à partir d'un fichier texte
        JButton btnLireTexte = new JButton("Lire Texte");
        btnLireTexte.setFont(new Font("Segoe UI Black", Font.BOLD, 11));
        btnLireTexte.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        btnLireTexte.setBackground(new Color(232, 232, 232));
        btnLireTexte.setBounds(207, 483, 143, 30);
        panel.add(btnLireTexte);

        // Action du bouton pour lire à partir d'un fichier texte
        btnLireTexte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importerDonneesTexte();
            }
        });

        // Bouton pour valider et afficher les données
        JButton btnValider = new JButton("Valider");
        btnValider.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        btnValider.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        btnValider.setBackground(new Color(232, 232, 232));
        btnValider.setBounds(54, 538, 100, 30);
        panel.add(btnValider);
        
        JLabel lblNewLabel_1_1 = new JLabel("Lire .CSV ou .TXT");
        lblNewLabel_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1_1.setBounds(54, 433, 189, 39);
        panel.add(lblNewLabel_1_1);
        
        JLabel lblNewLabel_1_2 = new JLabel(" Liste des objets sélectionnés");
        lblNewLabel_1_2.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1_2.setBounds(729, 133, 230, 39);
        panel.add(lblNewLabel_1_2);
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        scrollPane_1.setBackground(new Color(230, 202, 162));
        scrollPane_1.setBounds(652, 182, 377, 194);
        panel.add(scrollPane_1);
        
        lblNewLabel_2 = new JLabel("  ");
        lblNewLabel_2.setFont(new Font("Segoe UI Black", Font.BOLD, 16));
        scrollPane_1.setViewportView(lblNewLabel_2);
        lblNewLabel_2.setBackground(new Color(230, 202, 162));
        
        JLabel lblNewLabel_1_2_1 = new JLabel("Valeur totale ");
        lblNewLabel_1_2_1.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1_2_1.setBounds(683, 387, 108, 39);
        panel.add(lblNewLabel_1_2_1);
        
        lblNewLabel_3 = new JLabel("  ");
        lblNewLabel_3.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        lblNewLabel_3.setBackground(new Color(232, 232, 232));
        lblNewLabel_3.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
        lblNewLabel_3.setBounds(652, 426, 172, 30);
        panel.add(lblNewLabel_3);
        
        JLabel lblNewLabel_1_2_1_1 = new JLabel("Nombre de buffles");
        lblNewLabel_1_2_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1.setBounds(215, 536, 120, 35);
        panel.add(lblNewLabel_1_2_1_1);
        
        textField = new JTextField();
        textField.setText("10");
        textField.setColumns(10);
        textField.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField.setBackground(new Color(232, 232, 232));
        textField.setBounds(340, 542, 120, 25);
        panel.add(textField);
        
        JLabel lblNewLabel_1_2_1_1_1 = new JLabel("LP1");
        lblNewLabel_1_2_1_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1_1.setBounds(477, 541, 34, 25);
        panel.add(lblNewLabel_1_2_1_1_1);
        
        textField_1 = new JTextField();
        textField_1.setText("0.5");
        textField_1.setColumns(10);
        textField_1.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField_1.setBackground(new Color(232, 232, 232));
        textField_1.setBounds(508, 542, 120, 25);
        panel.add(textField_1);
        
        JLabel lblNewLabel_1_2_1_1_1_1 = new JLabel("LP2");
        lblNewLabel_1_2_1_1_1_1.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1_1_1.setBounds(652, 541, 34, 25);
        panel.add(lblNewLabel_1_2_1_1_1_1);
        
        textField_2 = new JTextField();
        textField_2.setText("0.6");
        textField_2.setColumns(10);
        textField_2.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField_2.setBackground(new Color(232, 232, 232));
        textField_2.setBounds(683, 542, 120, 25);
        panel.add(textField_2);
        
        JLabel lblNewLabel_1_2_1_1_3 = new JLabel("N. Itérations");
        lblNewLabel_1_2_1_1_3.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1_3.setBounds(825, 538, 90, 30);
        panel.add(lblNewLabel_1_2_1_1_3);
        
        textField_3 = new JTextField();
        textField_3.setText("1000");
        textField_3.setColumns(10);
        textField_3.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField_3.setBackground(new Color(232, 232, 232));
        textField_3.setBounds(913, 542, 120, 25);
        panel.add(textField_3);
        
        JButton btnNewButton = new JButton("Retour");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Supprimer le frame actuel
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(It_sad.this);
                frame.dispose();
                
                // Afficher le frame de la classe Home
                Home homeFrame = new Home();
                homeFrame.setVisible(true);
            }
        });

        btnNewButton.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
        btnNewButton.setBackground(new Color(232, 232, 232));
        btnNewButton.setFont(new Font("Segoe UI Black", Font.BOLD, 18));
        btnNewButton.setBounds(913, 26, 137, 39);
        panel.add(btnNewButton);
        
        JLabel lblNewLabel_1_3 = new JLabel("Capacité maximale");
        lblNewLabel_1_3.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1_3.setBounds(54, 383, 189, 39);
        panel.add(lblNewLabel_1_3);
        
        textField_4 = new JTextField();
        textField_4.setText("1000");
        textField_4.setColumns(10);
        textField_4.setBorder(new LineBorder(new Color(65, 201, 255)));
        textField_4.setBackground(new Color(232, 232, 232));
        textField_4.setBounds(215, 392, 120, 25);
        panel.add(textField_4);
        
        JLabel lblNewLabel_1_2_1_2 = new JLabel("Poid total");
        lblNewLabel_1_2_1_2.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
        lblNewLabel_1_2_1_2.setBounds(901, 387, 90, 39);
        panel.add(lblNewLabel_1_2_1_2);
        
        lblNewLabel_3_1 = new JLabel(" ");
        lblNewLabel_3_1.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
        lblNewLabel_3_1.setBorder(new LineBorder(new Color(65, 201, 255), 2, true));
        lblNewLabel_3_1.setBackground(new Color(232, 232, 232));
        lblNewLabel_3_1.setBounds(857, 426, 172, 30);
        panel.add(lblNewLabel_3_1);
        
        JLabel lblNewLabel_1_2_1_1_2 = new JLabel("Temps d'exécution (ms) :  ");
        lblNewLabel_1_2_1_1_2.setFont(new Font("Segoe UI Black", Font.BOLD, 12));
        lblNewLabel_1_2_1_1_2.setBounds(648, 467, 176, 30);
        panel.add(lblNewLabel_1_2_1_1_2);
        
        lblNewLabel_3_2 = new JLabel("");
        lblNewLabel_3_2.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
        lblNewLabel_3_2.setBorder(null);
        lblNewLabel_3_2.setBackground(new Color(232, 232, 232));
        lblNewLabel_3_2.setBounds(837, 467, 170, 30);
        panel.add(lblNewLabel_3_2);

        // Action du bouton pour valider et afficher les données
        btnValider.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validerEtAfficherDonnees();
            }
        });
    }

    // Mettre à jour les numéros d'objet après une suppression
    private void updateObjectNumbers() {
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(i + 1, i, 0);
        }
    }

    // Importer les données à partir d'un fichier CSV
    private void importerDonneesCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                model.setRowCount(0); // Supprimer toutes les lignes existantes
                int rowNum = 0;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";"); // Utiliser la virgule comme séparateur pour le CSV
                    if (parts.length >= 2) {
                        String poids = parts[0];
                        String valeur = parts[1];
                        model.addRow(new Object[]{++rowNum, poids, valeur});
                    }
                }
                reader.close();
                lblNewLabel_2.setText("");
                lblNewLabel_3.setText("");
                lblNewLabel_3_1.setText("");
                lblNewLabel_3_2.setText("");
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Importer les données à partir d'un fichier texte
    private void importerDonneesTexte() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getPath();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                model.setRowCount(0); // Supprimer toutes les lignes existantes
                int rowNum = 0;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length >= 2) {
                        String poids = parts[0];
                        String valeur = parts[1];
                        model.addRow(new Object[]{++rowNum, poids, valeur});
                    }
                }
                reader.close();
                lblNewLabel_2.setText("");
                lblNewLabel_3.setText("");
                lblNewLabel_3_1.setText("");
                lblNewLabel_3_2.setText("");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Méthode pour valider 
    private void validerEtAfficherDonnees() {
        tabData.clear(); // vider le tableau 

        for (int i = 0; i < model.getRowCount(); i++) {
            float[] rowData = new float[3]; 
            for (int j = 0; j < 3; j++) {
                String cellValue = model.getValueAt(i, j).toString(); 
                rowData[j] = Float.parseFloat(cellValue); 
            }
            tabData.add(rowData); // Ajouter les données de la ligne au tableau "tabData"
        }
        float l1 = Float.parseFloat(textField_1.getText());
        float l2 = Float.parseFloat(textField_2.getText());
        float max_W = Float.parseFloat(textField_4.getText());
        int nbr_iter = Integer.parseInt(textField_3.getText());
        int population = Integer.parseInt(textField.getText());
        
        
        
        // Obtenir le temps avant l'exécution
        long startTime = System.currentTimeMillis();
        
        ABOKnapsack abo1 = new ABOKnapsack(l1, l2, population, nbr_iter, tabData, max_W);
        
        // Obtenir le temps après l'exécution
        long endTime = System.currentTimeMillis();

        // Calculer le temps d'exécution en millisecondes
        long executionTime = endTime - startTime;
        
        
        
        
        System.out.println(abo1.getBestSolution());
        System.out.println("getBestFitness  : "+abo1.getBestFitness());
        System.out.println("Weight : "+abo1.getWeight());
        
        
       
        
        
       

        
        lblNewLabel_3.setText(Float.toString(abo1.getBestFitness()));
        lblNewLabel_3_1.setText(Float.toString(abo1.getWeight()));
        lblNewLabel_3_2.setText(Long.toString(executionTime));
        
        
        
        StringBuilder item_selected = new StringBuilder("<html>");
       
        for (int i = 0; i < abo1.getBestSolution().size(); i++) {
            if (abo1.getBestSolution().get(i)) {
                item_selected.append("     Objet -->  ").append(i + 1).append("<br>");
            }
        }
        item_selected.append("</html>");
        
 
        lblNewLabel_2.setText(item_selected.toString());
        lblNewLabel_2.revalidate();
        
        
    }
}




// data -> tabData (3 col "numObjet,poid,valeur")  ==  (List<float[]> tabData = new ArrayList<>())
//nombre buffalos -> textField
//LP1 -> textField_1
//LP2 -> textField_2
//d'itérations  -> textField_3
// max poid  -> textField_4

// affichage Liste des objets sélectionnés   -> lblNewLabel_2
// affichage Liste des objets sélectionnés   -> lblNewLabel_3
// poid total -> lblNewLabel_3_1
// temp exec -> lblNewLabel_3_2




