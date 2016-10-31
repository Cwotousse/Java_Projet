package FENETRE;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import POJO.Cours;
import POJO.CoursCollectif;
import POJO.CoursParticulier;
import POJO.Eleve;
import POJO.Moniteur;
import POJO.Semaine;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class F_AjoutRdv extends JFrame {

	private JPanel contentPane;
	private int numMoniteur;
	private int numCours;
	private int numEleve;
	private HashSet <String> periode = new HashSet <String>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_AjoutRdv frame = new F_AjoutRdv(-1);
					frame.setVisible(true);
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public F_AjoutRdv(int idClient) {
		periode.add("09-12");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// New
		JSeparator separator = new JSeparator();
		JSeparator separator_1 = new JSeparator();
		JLabel lbl_Reserv = new JLabel("R\u00E9servation");
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
		JRadioButton rdbtnCoursParticulier = new JRadioButton("Cours particulier");
		JCheckBox rdbtnCoursMatin = new JCheckBox("Cours matin");
		JCheckBox rdbtnCoursAprem = new JCheckBox("Cours apr\u00E8s-midi");
		JButton btn_ret = new JButton("Retour");
		JButton btn_inscrip = new JButton("Inscrire");

		// Alignement
		lbl_moniteur.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_eleve.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_horaire.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_cours.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_infoCours.setHorizontalAlignment(SwingConstants.LEFT);

		// Font
		lbl_Reserv.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));

		// Foreground color
		lbl_error.setForeground(Color.RED);

		// Bouds
		lbl_Reserv.setBounds			(10, 11, 193, 22);
		separator.setBounds				(10, 44, 105, 15);
		cb_nomEleve.setBounds			(214, 134, 250, 20);
		cb_nomMoniteur.setBounds		(214, 77, 250, 20);
		cb_semaine.setBounds			(214, 190, 250, 20);
		rdbtnCoursCollectif.setBounds	(6, 62, 109, 23);
		rdbtnCoursParticulier.setBounds	(6, 88, 109, 23);
		lbl_error.setBounds				(213, 18, 251, 14);
		lbl_moniteur.setBounds			(214, 45, 250, 14);
		lbl_eleve.setBounds				(214, 109, 250, 14);
		lbl_horaire.setBounds			(224, 165, 250, 14);
		lbl_cours.setBounds				(214, 221, 250, 14);
		cb_cours.setBounds				(214, 246, 250, 20);
		lbl_infoCours.setBounds			(214, 277, 250, 14);
		rdbtnCoursAprem.setBounds		(6, 161, 109, 23);
		rdbtnCoursMatin.setBounds		(6, 133, 109, 23);
		btn_inscrip.setBounds			(10, 310, 105, 23);
		btn_ret.setBounds				(10, 344, 105, 23);
		separator_1.setBounds			(10, 299, 105, 15);

		// Add
		contentPane.add(lbl_Reserv);
		contentPane.add(separator);
		contentPane.add(cb_nomEleve);
		contentPane.add(cb_nomMoniteur);
		contentPane.add(rdbtnCoursCollectif);
		contentPane.add(lbl_error);
		contentPane.add(cb_semaine);
		contentPane.add(rdbtnCoursParticulier);
		contentPane.add(cb_cours);
		contentPane.add(lbl_cours);
		contentPane.add(lbl_eleve);
		contentPane.add(lbl_moniteur);
		contentPane.add(lbl_horaire);
		contentPane.add(lbl_infoCours);
		contentPane.add(rdbtnCoursMatin);
		contentPane.add(rdbtnCoursAprem);
		contentPane.add(btn_inscrip);
		contentPane.add(btn_ret);
		contentPane.add(separator_1);

		rdbtnCoursMatin.setSelected(true);





		// EVENEMENT CLICK SUR RADIOBUTTON
		// Valider l'inscritpion
		btn_inscrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});

		// Retour menu client
		btn_ret.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Affiche F_ajoutEleve
				setVisible(false);
				F_Client frame = new F_Client(idClient);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		// COURS COLLECTIF SELECTIONNE
		rdbtnCoursCollectif.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				rdbtnCoursParticulier.setSelected(false);
				rdbtnCoursCollectif.setSelected	(true);
				
				// Visible
				lbl_infoCours.setVisible		(true);
				//lbl_cours.setVisible			(true);
				//cb_cours.setVisible				(true);
				rdbtnCoursMatin.setVisible		(true);
				rdbtnCoursAprem.setVisible		(true);
				
				// Clear le hashset
				periode.clear();
				periode.add("09-12");
			}
		});

		// COURS PARTICULIER SELECTIONNE
		rdbtnCoursParticulier.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnCoursCollectif.setSelected	(false);
				rdbtnCoursParticulier.setSelected(true);

				// Visible
				lbl_infoCours.setVisible		(false);
				//lbl_cours.setVisible			(false);
				//cb_cours.setVisible			(false);
				rdbtnCoursMatin.setVisible		(false);
				rdbtnCoursAprem.setVisible		(false);
				
				// Clear le hashset
				periode.clear();
				periode.add("12-13");
			}
		});

		// COURS DU MATIN
		rdbtnCoursMatin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!rdbtnCoursAprem.isSelected()){
					//rdbtnCoursMatin.setSelected(true);
					rdbtnCoursMatin.setSelected(true);
				
					periode.add("09-12");
				}
			}
		});

		// COURS APREM
		rdbtnCoursAprem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!rdbtnCoursMatin.isSelected()){
					//rdbtnCoursMatin.setSelected(true);
					rdbtnCoursAprem.setSelected(true);
				//rdbtnCoursMatin.setSelected(false);
				//rdbtnCoursAprem.setSelected(true);
					
					periode.add("14-17");
				}
			}
		});
		// lISTES UTILES AUX COMBOBOXS
		Eleve E 							= new Eleve();
		Moniteur M 						= new Moniteur();
		Semaine S 							= new Semaine();
		Cours C 							= new Cours();
		CoursParticulier CP 				= new CoursParticulier();
		CoursCollectif CC 					= new CoursCollectif();
		ArrayList<Eleve> listEleve			= E.getListEleve();
		ArrayList<Moniteur> listMoniteur	= M.getListMoniteur();
		ArrayList<Semaine> listSemaine		= S.getListSemaineSelonDateDuJour();	
		ArrayList<Cours> listCours = null;//						= C.getListCoursSelonId(numMoniteur, numEleve);//C.getListCours();	
		//ArrayList<CoursParticulier> listCoursPartic		= CP.getListCours();	
		ArrayList<CoursCollectif> listCoursColl			= CC.getListCoursCollectif();	
		// ON CHOISI IN NOUVEAU MONITEUR
		// -> Il faut charger les cours correspondants
		//Cours C = new Cours();
		cb_nomEleve.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					Object item = cb_nomEleve.getSelectedItem();
					int value = ((ComboItem)item).getValue();
					numEleve = value;
					System.out.println("Num élève : " + value);
				}
			}
		});
		
		cb_nomMoniteur.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					Object item = cb_nomMoniteur.getSelectedItem();
					int value = ((ComboItem)item).getValue();
					numMoniteur = value;
					System.out.println("Num moniteur : " + value);
					//listCours = C.getListCoursSelonId(value, 0);
				}
			}
		});
		
		cb_semaine.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					Object item = cb_semaine.getSelectedItem();
					int value = ((ComboItem)item).getValue();
					//numSemaine = value;
					System.out.println("Num semaine : " + value);
				}
			}
		});
		
		cb_cours.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.SELECTED) {
					Object item = cb_cours.getSelectedItem();
					int value = ((ComboItem)item).getValue();
					numCours = value;
					System.out.println("Num cours : " + value);
				}
			}
		});

		// REMPLISSAGE DES COMBOBOX
		
		// ELEVES
		if (listEleve != null)
			for(Eleve e : listEleve){
				//cb_nomEleve.addItem(e.getNom().toUpperCase() + " " + e.getPre());
				cb_nomEleve.addItem (new ComboItem(e.getNom().toUpperCase() + " " + e.getPre(), e.getNumPersonne()));
				//cb_nomEleve.add
			}
		else
			lbl_error.setText("Erreur ajout rdv");

		// MONITEUR
		if (listMoniteur != null)
			for(Moniteur m : listMoniteur){
				//String [] arr = new String[] { m.getNumPersonne() + "", m.getNom().toUpperCase(), m.getNom()};
				//cb_nomMoniteur.addItem(arr);
				cb_nomMoniteur.addItem (new ComboItem(m.getNom().toUpperCase() + " " + m.getPre(), m.getNumPersonne()));
			}
		else
			lbl_error.setText("Erreur ajout rdv");

		// SEMAINE		
		if (listSemaine != null)
			for(Semaine s : listSemaine){
				if (!s.getCongeScolaire()) // N'affiche que les semaines ou il n'y a pas de congés
					//cb_semaine.addItem();
				cb_semaine.addItem (new ComboItem(s.getDateDebut().toString(), s.getNumSemaine()));

			}
		else
			lbl_error.setText("Erreur disponibilités");

		//rdbtnCoursCollectif
		// COURS
		//listCours = C.getListCoursSelonId(numMoniteur, numEleve);
		listCoursColl = CC.getListCoursCollectifSelonId(numMoniteur, numEleve, periode);
		if (listCoursColl != null){
			cb_cours.removeAllItems();
			if(rdbtnCoursCollectif.isSelected()){ // Cours collectif
				//for(Cours cc : listCours){
				for(CoursCollectif cc : listCoursColl){
					cb_cours.addItem (new ComboItem("Niveau " + cc.getNiveauCours() + ", " + cc.getCategorieAge() + " (" + cc.getNomSport() + ") ", cc.getNumCoursCollectif()));
					//cb_cours.addItem (new ComboItem(cc.getNomSport(), cc.getNumCours()));
					//cb_cours.addItem(cc.getNomSport() + " " + cc.getCategorieAge() + " " + cc.getNiveauCours());
					lbl_infoCours.setText("Il reste " + (cc.getMaxEl()-cc.getMinEl()) + " places disponibles.");
				}
			}
			else if(rdbtnCoursParticulier.isSelected()){ // Cours particulier
				lbl_infoCours.setText("Pas de cours dispo.");
			}
		}
		else
			lbl_error.setText("Erreur disponibilités");
	}

	// CLASSE UTILISEE POUR RECUPERER LID DE LA PERSONNE
	class ComboItem
	{
	    private String key;
	    private int value;

	    public ComboItem(String key, int value)
	    {
	        this.key 	= key;
	        this.value 	= value;
	    }

	    @Override
	    public String toString	() { return key; } 
	    public String getKey	() { return key; }
	    public int getValue		() { return value; }
	}
	
}
