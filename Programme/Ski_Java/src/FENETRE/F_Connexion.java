package FENETRE;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.AbstractDAOFactory;
import DAO.DAO;
import POJO.Utilisateur;

import java.awt.Label;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class F_Connexion extends JFrame {
	private JTextField txtNomDutilisateur;
	private JPasswordField pwdPassword;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Connexion frame = new F_Connexion();
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
	public F_Connexion() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 167, 203);
		getContentPane().setLayout(null);
		
		JLabel lblStationSki = new JLabel("PROJET JAVA");
		lblStationSki.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblStationSki.setBounds(77, 11, 74, 15);
		getContentPane().add(lblStationSki);
		
		JLabel errorBox = new JLabel("");
		errorBox.setForeground(Color.RED);
		errorBox.setBounds(10, 90, 141, 14);
		getContentPane().add(errorBox);
		
		txtNomDutilisateur = new JTextField();
		txtNomDutilisateur.setToolTipText("Nom d'utilisateur");
		txtNomDutilisateur.setText("adri");
		txtNomDutilisateur.setBounds(10, 39, 139, 20);
		getContentPane().add(txtNomDutilisateur);
		txtNomDutilisateur.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setToolTipText("Mot de passe");
		pwdPassword.setText("test");
		pwdPassword.setBounds(10, 70, 139, 20);
		getContentPane().add(pwdPassword);
		
		JButton btnSeConnecter = new JButton("Se connecter");
		btnSeConnecter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
				DAO<Utilisateur> UtilisateurDao = adf.getUtilisateurDAO();
				Utilisateur u = UtilisateurDao.verifPseudoMdp(txtNomDutilisateur.getText(), pwdPassword.getText());
				switch(u.getTypeUtilisateur()){
				case 2 : // moniteur
					setVisible(false); //you can't see me!
					//dispose(); //Destroy the JFrame object
					F_Moniteur frame = new F_Moniteur();
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setVisible(true);
					//F_Moniteur.
					break;
				case 1 : 
					setVisible(false); //you can't see me!
					//dispose(); //Destroy the JFrame object

					break;
				default:
					errorBox.setText("Donnees incorrectes");
				}
			}
		});
		btnSeConnecter.setBounds(10, 113, 139, 23);
		getContentPane().add(btnSeConnecter);
		
		JButton btnJeNePossde = new JButton("S'inscrire");
		btnJeNePossde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnJeNePossde.setBounds(10, 138, 139, 23);
		getContentPane().add(btnJeNePossde);
		
		JLabel label = new JLabel("STATION SKI");
		label.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		label.setBounds(10, 0, 74, 15);
		getContentPane().add(label);
		
		
	}
}
