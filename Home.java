

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Home extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	 private void updateCurrentPanel(JPanel newPanel) {
	        // Met à jour le panneau courant
	        getContentPane().removeAll();
	        getContentPane().add(newPanel);
	        getContentPane().revalidate();
	        getContentPane().repaint();
	       
	    }
	public Home() {
		setResizable(false);
		setBounds(new Rectangle(100, 30, 1500, 640));
		setMinimumSize(new Dimension(600, 0));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 1100, 640);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(65, 201, 226));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(247, 238, 221));
		contentPane.add(panel);
		panel.setLayout(new GridLayout(2, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("<html><h1>Optimisation des problèmes \"sac à dos\" <br> et  \"voyageur de commerce\" avec \"ABO-(African Buffalo Optimization)\"<br>Performances maximales, solutions optimales</h1></html>");
		lblNewLabel.setBackground(new Color(247, 238, 221));
		lblNewLabel.setForeground(new Color(0, 121, 187));
		lblNewLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 11));
		panel.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel_2.setMinimumSize(new Dimension(0, 0));
		panel_2.setBackground(new Color(247, 238, 221));
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton = new JButton("Problème 1 (sac à dos)");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel p1 = new It_sad();
				 updateCurrentPanel(p1);
				
			}
		});
		btnNewButton.setBackground(new Color(232, 232, 232));
		btnNewButton.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
		btnNewButton.setForeground(new Color(0, 121, 187));
		btnNewButton.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
		btnNewButton.setBounds(124, 21, 346, 63);
		panel_2.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Problème 2 (voyageur de commerce)");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel p2 = new It_vdc();
				 updateCurrentPanel(p2);

			}
		});
		btnNewButton_1.setBackground(new Color(232, 232, 232));
		btnNewButton_1.setBorder(new LineBorder(new Color(65, 201, 255), 3, true));
		btnNewButton_1.setForeground(new Color(0, 121, 187));
		btnNewButton_1.setFont(new Font("Segoe UI Black", Font.BOLD, 15));
		btnNewButton_1.setBounds(124, 112, 346, 63);
		panel_2.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(247, 238, 221));
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));

		String currentDir = System.getProperty("user.dir");

        // Spécifier le nom du fichier image
        String imageFilename = "empty.png";

        // Construire le chemin complet vers l'image
        String imagePath = currentDir + File.separator + "img" + File.separator + imageFilename;
		
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBackground(new Color(247, 238, 221));
		lblNewLabel_1.setIcon(new ImageIcon(imagePath));
		panel_1.add(lblNewLabel_1);
	}
}
