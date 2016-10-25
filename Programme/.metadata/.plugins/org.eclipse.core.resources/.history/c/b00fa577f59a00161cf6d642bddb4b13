package FENETRE;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class F_Moniteur extends JFrame {

	private JPanel contentPane;
	private final JLabel lblMoniteur = new JLabel("Moniteur");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Moniteur frame = new F_Moniteur();
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
	public F_Moniteur() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblMoniteur.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));
		lblMoniteur.setBounds(10, 11, 73, 36);
		contentPane.add(lblMoniteur);
		
		JLabel errMsg = new JLabel("");
		errMsg.setForeground(Color.RED);
		errMsg.setBounds(10, 11, 46, 14);
		contentPane.add(errMsg);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 73, 2);
		contentPane.add(separator);
		
		JRadioButton rdbtnConsulterHoraire = new JRadioButton("Consulter horaire");
		rdbtnConsulterHoraire.setBounds(6, 54, 121, 23);
		contentPane.add(rdbtnConsulterHoraire);
		
		JRadioButton rdbtnSeDconnecter = new JRadioButton("Se d\u00E9connecter");
		rdbtnSeDconnecter.setBounds(6, 80, 121, 23);
		contentPane.add(rdbtnSeDconnecter);
		
		JButton btnSuivant = new JButton("Suivant");
		btnSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(rdbtnConsulterHoraire.isSelected()){
					
				}
				else if (rdbtnSeDconnecter.isSelected()){
					setVisible(false);
					F_Connexion frame = new F_Connexion();
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setVisible(true);
				}
				else {
					errMsg.setText("Choissez une opération.");
				}
			}
		});
		btnSuivant.setBounds(10, 191, 117, 23);
		contentPane.add(btnSuivant);
		
		
	}
}
