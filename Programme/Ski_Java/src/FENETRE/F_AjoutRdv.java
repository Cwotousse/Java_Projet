package FENETRE;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import POJO.Cours;
import POJO.Eleve;
import POJO.Moniteur;
import POJO.Semaine;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class F_AjoutRdv extends JFrame {

	private JPanel contentPane;
	private ArrayList<Eleve> listEleve;// = new ArrayList<Eleve>();
	private ArrayList<Moniteur> listMoniteur;
	private ArrayList<Semaine> listSemaine;
	/*AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Eleve> EleveDao = adf.getEleveDAO();
	DAO<Moniteur> MoniteurDao = adf.getMoniteurDAO();*/
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
		JLabel lbl_moniteur = new JLabel("Moniteur");
		JLabel lbl_eleve = new JLabel("El\u00E8ve");
		JLabel lbl_horaire = new JLabel("Horaire");
		JLabel lbl_cours = new JLabel("Cours");
		JLabel lbl_infoCours = new JLabel("Il reste x places pour ce cours");
		JComboBox cb_nomEleve = new JComboBox();
		JComboBox cb_nomMoniteur = new JComboBox();
		JComboBox cb_semaine = new JComboBox();
		JComboBox cb_cours = new JComboBox();
		JRadioButton rdbtnCoursCollectif = new JRadioButton("Cours collectif");
		rdbtnCoursCollectif.setSelected(true);
		JRadioButton rdbtnCoursParticuler = new JRadioButton("Cours particulier");
		JCheckBox rdbtnCoursMatin = new JCheckBox("Cours matin");
		JCheckBox rdbtnCoursAprem = new JCheckBox("Cours apr\u00E8s-midi");
		
		// Alignement
		lbl_moniteur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_eleve.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_horaire.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_cours.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_infoCours.setHorizontalAlignment(SwingConstants.LEFT);
		
		// Font
		lblNewLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));

		// Foreground color
		lbl_error.setForeground(Color.RED);
		
		// Bouds
		lblNewLabel.setBounds			(10, 11, 193, 22);
		separator.setBounds				(10, 44, 160, 15);
		cb_nomEleve.setBounds			(214, 134, 250, 20);
		cb_nomMoniteur.setBounds		(214, 77, 250, 20);
		cb_semaine.setBounds			(214, 190, 250, 20);
		rdbtnCoursCollectif.setBounds	(6, 62, 109, 23);
		rdbtnCoursParticuler.setBounds	(6, 88, 109, 23);
		lbl_error.setBounds				(201, 18, 46, 14);
		lbl_moniteur.setBounds			(214, 45, 250, 14);
		lbl_eleve.setBounds				(214, 109, 250, 14);
		lbl_horaire.setBounds			(224, 165, 250, 14);
		lbl_cours.setBounds				(214, 221, 250, 14);
		cb_cours.setBounds				(214, 246, 250, 20);
		lbl_infoCours.setBounds			(214, 277, 250, 14);
		rdbtnCoursAprem.setBounds		(6, 161, 109, 23);
		rdbtnCoursMatin.setBounds		(6, 133, 109, 23);

		// Add
		contentPane.add(lblNewLabel);
		contentPane.add(separator);
		contentPane.add(cb_nomEleve);
		contentPane.add(cb_nomMoniteur);
		contentPane.add(rdbtnCoursCollectif);
		contentPane.add(lbl_error);
		contentPane.add(cb_semaine);
		contentPane.add(rdbtnCoursParticuler);
		contentPane.add(cb_cours);
		contentPane.add(lbl_cours);
		contentPane.add(lbl_eleve);
		contentPane.add(lbl_moniteur);
		contentPane.add(lbl_horaire);
		contentPane.add(lbl_infoCours);
		contentPane.add(rdbtnCoursMatin);
		contentPane.add(rdbtnCoursAprem);
		
		rdbtnCoursMatin.setSelected(true);
				
		// EVENEMENT CLICK SUR RADIOBUTTON
		// COURS COLLECTIF SELECTIONNE
		rdbtnCoursCollectif.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rdbtnCoursParticuler.setSelected(false);
				rdbtnCoursCollectif.setSelected	(true);
				
				// Visible
				lbl_infoCours.setVisible		(true);
				//lbl_cours.setVisible			(true);
				//cb_cours.setVisible				(true);
				rdbtnCoursMatin.setVisible		(true);
				rdbtnCoursAprem.setVisible		(true);
			}
		});
		
		// COURS PARTICULIER SELECTIONNE
		rdbtnCoursParticuler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnCoursCollectif.setSelected	(false);
				rdbtnCoursParticuler.setSelected(true);
				
				// Visible
				lbl_infoCours.setVisible		(false);
				//lbl_cours.setVisible			(false);
				//cb_cours.setVisible				(false);
				rdbtnCoursMatin.setVisible		(false);
				rdbtnCoursAprem.setVisible		(false);
			}
		});
		
		// COURS DU MATIN
		rdbtnCoursMatin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!rdbtnCoursAprem.isSelected())
				//rdbtnCoursMatin.setSelected(true);
					rdbtnCoursMatin.setSelected(true);
			}
		});
		
		// COURS APREM
		rdbtnCoursAprem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!rdbtnCoursMatin.isSelected())
					//rdbtnCoursMatin.setSelected(true);
					rdbtnCoursAprem.setSelected(true);
				//rdbtnCoursMatin.setSelected(false);
				//rdbtnCoursAprem.setSelected(true);
			}
		});
		// CHARGEMENT DES COMBOBOX
		// ON CHOISI IN NOUVEAU MONITEUR
		// -> Il faut charger les cours correspondants
		//Cours C = new Cours();
		
		cb_nomMoniteur.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				
			}
		});
		// Remplissage de la cb_Eleve avec les données issue de la DB
		Eleve E = new Eleve();
		listEleve = E.getListEleve();
		if (listEleve != null)
			for(Eleve e : listEleve){
				cb_nomEleve.addItem(e.getNom().toUpperCase() + " " + e.getPre());
			}
		else
			lbl_error.setText("Erreur ajout rdv");

		// Remplissage de la cb_Moniteur avec les données issue de la DB
		Moniteur M = new Moniteur();
		listMoniteur = M.getListMoniteur();
		if (listMoniteur != null)
			for(Moniteur m : listMoniteur){
				cb_nomMoniteur.addItem(m.getNom().toUpperCase() + " " + m.getPre());
			}
		else
			lbl_error.setText("Erreur ajout rdv");
		
		// Remplissage de la cb_Semaine avec les données issue de la DB
		Semaine S = new Semaine();
				listSemaine = S.getListSemaineSelonDateDuJour();
				if (listSemaine != null)
					for(Semaine s : listSemaine){
						if (!s.getCongeScolaire()) // N'affiche que les semaines ou il n'y a pas de congés
							cb_semaine.addItem(s.getDateDebut().toString());
					}
				else
					lbl_error.setText("Erreur disponibilités");
	}
}
