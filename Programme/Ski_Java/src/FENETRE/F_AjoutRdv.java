package FENETRE;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.AbstractDAOFactory;
import DAO.DAO;
import POJO.Eleve;
import POJO.Moniteur;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Color;

public class F_AjoutRdv extends JFrame {

	private JPanel contentPane;
	private ArrayList<Eleve> listEleve;// = new ArrayList<Eleve>();
	private ArrayList<Moniteur> listMoniteur;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Eleve> EleveDao = adf.getEleveDAO();
	DAO<Moniteur> MoniteurDao = adf.getMoniteurDAO();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_AjoutRdv frame = new F_AjoutRdv();
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
	public F_AjoutRdv() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// New
		JSeparator separator = new JSeparator();
		JLabel lblNewLabel = new JLabel("Ajout d'un rendez-vous");
		JLabel lbl_error = new JLabel("");
		JComboBox cb_nomEleve = new JComboBox();
		JComboBox cb_nomMoniteur = new JComboBox();
		JRadioButton rdbtnCoursCollectif = new JRadioButton("Cours collectif");

		// Font
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));

		// Foreground color
		lbl_error.setForeground(Color.RED);
		
		// Bouds
		lblNewLabel.setBounds(10, 11, 193, 22);
		separator.setBounds(10, 44, 160, 15);
		cb_nomEleve.setBounds(10, 147, 160, 20);
		cb_nomMoniteur.setBounds(10, 92, 160, 20);
		rdbtnCoursCollectif.setBounds(6, 62, 109, 23);
		lbl_error.setBounds(201, 18, 46, 14);

		// Add
		contentPane.add(lblNewLabel);
		contentPane.add(separator);
		contentPane.add(cb_nomEleve);
		contentPane.add(cb_nomMoniteur);
		contentPane.add(rdbtnCoursCollectif);
		contentPane.add(lbl_error);

		// Remplissage de la cb_Eleve avec les donn�es issue de la DB
		listEleve = EleveDao.getList();
		if (listEleve != null)
			for(Eleve e : listEleve){
				cb_nomEleve.addItem(e.getNom().toUpperCase() + " " + e.getPre());
			}
		else
			lbl_error.setText("Erreur ajout rdv");

		// Remplissage de la cb_Moniteur avec les donn�es issue de la DB
		listMoniteur = MoniteurDao.getList();
		if (listMoniteur != null)
			for(Moniteur m : listMoniteur){
				cb_nomMoniteur.addItem(m.getNom().toUpperCase() + " " + m.getPre());
			}
		else
			lbl_error.setText("Erreur ajout rdv");
	}
}