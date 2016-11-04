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
					F_Moniteur frame = new F_Moniteur(-1);
					frame.setVisible(true);
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public F_Moniteur(int idMoniteur) {
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
		
		JButton btnDeco = new JButton("Se d\u00E9connecter");
		btnDeco.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				F_Connexion frame = new F_Connexion();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		btnDeco.setBounds(10, 227, 117, 23);
		contentPane.add(btnDeco);
		
		JButton btn_cours = new JButton("Mes cours");
		btn_cours.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Affiche F_AfficherRDV
				setVisible(false);
				F_AfficherRDV frame = new F_AfficherRDV(idMoniteur);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		btn_cours.setBounds(10, 58, 117, 23);
		contentPane.add(btn_cours);
		
		
	}
}
