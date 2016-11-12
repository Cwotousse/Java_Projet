package be.mousty.fenetre;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import be.mousty.accessToDao.UtilisateurATD;
import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Utilisateur;

public class F_Connexion extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2614761702909947171L;
	private JTextField txtNomDutilisateur;
	private JPasswordField pwdPassword;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Utilisateur> UtilisateurDAO = adf.getUtilisateurDAO();
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
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int typeUtilisateur = -1;
				try {
					UtilisateurATD UATD = new UtilisateurATD(pwdPassword.getText(), txtNomDutilisateur.getText());
					UATD = UATD.connexion();
					//UtilisateurDAO.returnUser(pwdPassword.getText(),txtNomDutilisateur.getText());
					
					switch(UATD.getTypeUtilisateur()){
					// -1 car le type est inconnu
					case 1 : // moniteur
						setVisible(false); //you can't see me!
						F_Moniteur frameMoni = new F_Moniteur(UATD.getNumId());
						frameMoni.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frameMoni.setVisible(true);
						//F_Moniteur.
						break;
					case 2 : 
						setVisible(false); //you can't see me!
						F_Client frameCli = new F_Client(UATD.getNumId());
						frameCli.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frameCli.setVisible(true);
						break;
					default:
						errorBox.setText("Donnees incorrectes");
					}
				}
				catch (Exception e) {
					e.getStackTrace();
					System.out.println("Erreur : " + typeUtilisateur);
				}
			}
		});
		btnSeConnecter.setBounds(10, 113, 139, 23);
		getContentPane().add(btnSeConnecter);

		JButton btnJeNePossde = new JButton("S'inscrire");
		btnJeNePossde.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setVisible(false); //you can't see me!
				//dispose(); //Destroy the JFrame object
				@SuppressWarnings("deprecation")
				F_Inscription frame = new F_Inscription(txtNomDutilisateur.getText(), pwdPassword.getText());
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
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
