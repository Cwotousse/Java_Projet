package be.mousty.fenetre;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import be.mousty.accessToDao.AccreditationATD;
import be.mousty.accessToDao.DisponibiliteMoniteurATD;
import be.mousty.accessToDao.UtilisateurATD;
import be.mousty.utilitaire.DateLabelFormatter;

public class F_Inscription extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5174575610200719333L;

	private JPanel contentPane;
	private JTextField txtF_userName;
	private JTextField txtF_mdp;
	private JTextField txtF_nom;
	private JTextField txtF_pre;
	private JTextField txtF_adresse;
	private String sexe = "H"; 
	//private JTable table;
	private JTextField txtF_adresseFact;
	private ArrayList<AccreditationATD> listAccreditation = new ArrayList<AccreditationATD>();
	private int numUtilisateur;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Inscription frame = new F_Inscription("", "");
					frame.setVisible(true);
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public F_Inscription(String login, String mdp) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		// News
		JLabel lblInscription 		= new JLabel("Inscription");
		JLabel lblNom 				= new JLabel("Nom");
		JLabel lblPre 				= new JLabel("Prenom");
		JLabel lblPseu 				= new JLabel("Pseudonyme");
		JLabel lblMdp				= new JLabel("Mot de passe");
		JLabel lblDateNaiss 		= new JLabel("Date de naissance");
		JLabel lblAdresse 			= new JLabel("Adresse");
		JLabel lblAdresseFact 		= new JLabel("Adresse de facturation");
		JLabel lblAccred 			= new JLabel("Accreditation");
		JRadioButton rdbtnH 		= new JRadioButton("Homme");
		JRadioButton rdbtnF 		= new JRadioButton("Femme");
		JRadioButton rdbtnMoniteur 	= new JRadioButton("Moniteur");
		JRadioButton rdbtnClient 	= new JRadioButton("Client");
		JCheckBox chkb_snow 		= new JCheckBox("Snowboard");
		JCheckBox chkb_skiFond 		= new JCheckBox("Ski fond");
		JCheckBox chkb_skiAlpin 	= new JCheckBox("Ski alpin");
		JCheckBox chkb_telemark 	= new JCheckBox("T\u00E9l\u00E9mark");
		JCheckBox chkb_jeune 		= new JCheckBox("Enfant");
		JCheckBox chkb_adulte 		= new JCheckBox("Adulte");
		txtF_userName 				= new JTextField("");
		txtF_mdp 					= new JTextField("");
		txtF_nom 					= new JTextField("");
		txtF_pre 					= new JTextField("");
		txtF_adresse 				= new JTextField("");
		txtF_adresseFact 			= new JTextField();
		JButton btn_retour 			= new JButton("Retour");
		JButton btn_inscrip 		= new JButton("S'enregistrer");

		// Visibility
		lblAccred.setVisible		(false);
		chkb_snow.setVisible		(false);
		chkb_skiAlpin.setVisible	(false);
		chkb_skiFond.setVisible		(false);
		chkb_telemark.setVisible	(false);
		chkb_jeune.setVisible		(false);
		chkb_adulte.setVisible		(false);
		lblAdresseFact.setVisible	(true);
		txtF_adresseFact.setVisible	(true);


		// Set ToolTip
		chkb_snow.setToolTipText		("Accreditation");
		chkb_skiFond.setToolTipText		("Accreditation");
		chkb_skiAlpin.setToolTipText	("Accreditation");
		chkb_telemark.setToolTipText	("Accreditation");
		txtF_userName.setToolTipText	("Pseudonyme");
		txtF_mdp.setToolTipText			("Mot de passe");
		txtF_nom.setToolTipText			("Nom");
		txtF_pre.setToolTipText			("Prenom");
		txtF_adresse.setToolTipText		("Adresse");

		// Set Text 
		txtF_userName.setText		(login);
		txtF_mdp.setText			(mdp);

		// Fonts
		lblInscription.setFont	(new Font("Yu Gothic UI", Font.PLAIN, 13));
		lblNom.setFont			(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblPre.setFont			(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblPseu.setFont			(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblMdp.setFont			(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblDateNaiss.setFont	(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblAdresse.setFont		(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblAdresseFact.setFont	(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblAccred.setFont		(new Font("Yu Gothic UI", Font.PLAIN, 11));

		// Bounds 
		lblInscription.setBounds	(10, 11, 67, 14);
		lblNom.setBounds			(10, 31, 46, 14);
		lblPre.setBounds			(10, 84, 130, 14);
		lblPseu.setBounds			(159, 31, 155, 14);
		lblMdp.setBounds			(159, 84, 155, 14);
		lblDateNaiss.setBounds		(10, 132, 130, 14);
		lblAdresse.setBounds		(10, 185, 130, 14);
		lblAdresseFact.setBounds	(159, 185, 155, 14);
		lblAccred.setBounds			(159, 185, 155, 14);
		rdbtnH.setBounds			(159, 128, 67, 23);
		rdbtnF.setBounds			(228, 128, 75, 23);
		chkb_snow.setBounds			(239, 202, 97, 23);
		chkb_skiFond.setBounds		(239, 221, 97, 23);
		chkb_skiAlpin.setBounds		(159, 221, 97, 23);
		chkb_telemark.setBounds		(159, 202, 97, 23);
		chkb_jeune.setBounds		(159, 247, 67, 23);
		chkb_adulte.setBounds		(239, 247, 75, 23);
		txtF_userName.setBounds		(159, 43, 155, 20);
		txtF_mdp.setBounds			(159, 101, 155, 20);
		txtF_nom.setBounds			(10, 43, 130, 20);
		txtF_pre.setBounds			(10, 101, 130, 20);
		txtF_adresse.setBounds		(10, 202, 130, 40);
		txtF_adresseFact.setBounds	(159, 203, 155, 39);
		rdbtnMoniteur.setBounds		(228, 146, 75, 23);
		rdbtnClient.setBounds		(159, 146, 67, 23);
		btn_inscrip.setBounds		(159, 302, 155, 23);
		btn_retour.setBounds		(10, 302, 130, 23);

		// Add
		contentPane.add(lblInscription);
		contentPane.add(lblNom);
		contentPane.add(lblPre);
		contentPane.add(lblPseu);
		contentPane.add(lblMdp);
		contentPane.add(lblDateNaiss);
		contentPane.add(lblAdresse);
		contentPane.add(lblAdresseFact);
		contentPane.add(lblAccred);
		contentPane.add(rdbtnH);
		contentPane.add(rdbtnF);
		contentPane.add(chkb_snow);
		contentPane.add(chkb_skiFond);
		contentPane.add(chkb_telemark);
		contentPane.add(chkb_jeune);
		contentPane.add(chkb_adulte);
		contentPane.add(chkb_skiAlpin);
		contentPane.add(txtF_userName);
		contentPane.add(txtF_mdp);
		contentPane.add(txtF_nom);
		contentPane.add(txtF_pre);
		contentPane.add(txtF_adresse);
		contentPane.add(txtF_adresseFact);
		contentPane.add(rdbtnMoniteur);
		contentPane.add(rdbtnClient);
		contentPane.add(btn_inscrip);
		contentPane.add(btn_retour);


		rdbtnMoniteur.setSelected(false);
		rdbtnClient.setSelected(true);
		rdbtnH.setSelected(true);

		// Set columns
		txtF_userName.setColumns(10);
		txtF_mdp.setColumns(10);
		txtF_nom.setColumns(10);
		txtF_pre.setColumns(10);
		txtF_adresse.setColumns(10);
		txtF_adresseFact.setColumns(10);


		// Datepicker
		SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.getJFormattedTextField().setToolTipText("Date de naissance");

		datePicker.setBounds(10, 146, 130, 23);
		contentPane.add(datePicker);


		// Action sur les boutons/
		rdbtnH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnH.setSelected(true);
				sexe = "H";
				rdbtnF.setSelected(false);
			}
		});

		rdbtnF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnH.setSelected(false);
				sexe = "F";
				rdbtnF.setSelected(true);
			}
		});

		rdbtnMoniteur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnMoniteur.setSelected	(true);
				rdbtnClient.setSelected		(false);
				lblAdresseFact.setVisible	(false);
				txtF_adresseFact.setVisible	(false);

				lblAccred.setVisible		(true);
				chkb_snow.setVisible		(true);
				chkb_skiAlpin.setVisible	(true);
				chkb_skiFond.setVisible		(true);
				chkb_telemark.setVisible	(true);
				chkb_jeune.setVisible		(true);
				chkb_adulte.setVisible		(true);

			}
		});

		rdbtnClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnClient.setSelected		(true);
				lblAdresseFact.setVisible	(true);
				txtF_adresseFact.setVisible	(true);

				rdbtnMoniteur.setSelected	(false);
				lblAccred.setVisible		(false);
				chkb_snow.setVisible		(false);
				chkb_skiAlpin.setVisible	(false);
				chkb_skiFond.setVisible		(false);
				chkb_telemark.setVisible	(false);
				chkb_jeune.setVisible		(false);
				chkb_adulte.setVisible		(false);
			}
		});

		// Retour
		btn_retour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				F_Connexion frame = new F_Connexion();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});

		// Appui sur le bouton inscription
		btn_inscrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					// Il faut vérifier que les champs sont tous remplis.
					if(!txtF_userName.getText().equals("") && !txtF_mdp.getText().equals("") && !txtF_nom.getText().equals("") && !txtF_pre.getText().equals("") && !txtF_adresse.getText().equals("")){
						String dateNaissance = datePicker.getJFormattedTextField().getText();
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
						java.util.Date ud = df.parse(dateNaissance);
						java.sql.Date sd = new java.sql.Date(ud.getTime());
						UtilisateurATD UATD = new UtilisateurATD();
						if (rdbtnClient.isSelected()){
							if(!txtF_adresseFact.getText().equals("")){
								numUtilisateur = UATD.inscriptionClient(txtF_adresseFact.getText(), txtF_userName.getText(), txtF_mdp.getText(), txtF_nom.getText(), txtF_pre.getText(),
										sd, txtF_adresse.getText(), sexe);
								if (numUtilisateur!= -1){
									setVisible(false);
									F_Client frame = new F_Client(numUtilisateur);
									frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
									frame.setVisible(true);
								}
								else { JOptionPane.showMessageDialog(contentPane, "Verifiez vos donnees"); }
							}
							else{ JOptionPane.showMessageDialog(contentPane, "Entrez une adresse de facturation"); }
						}

						// Moniteur est sélectionné
						else {
							//MoniteurATD MATD = new MoniteurATD();
							// Créer la liste des accréditations
							if(chkb_snow.isSelected()){
								AccreditationATD A = new AccreditationATD();
								A.setNom("Snowboard");
								//A.setNumAccreditation(1);
								listAccreditation.add(A);
							}
							if(chkb_skiAlpin.isSelected()){	
								AccreditationATD A = new AccreditationATD();
								A.setNom("Ski");
								//A.setNumAccreditation(2);
								listAccreditation.add(A);
							}
							if(chkb_skiFond.isSelected()){
								AccreditationATD A = new AccreditationATD();
								A.setNom("Ski de fond");
								//A.setNumAccreditation(3);
								listAccreditation.add(A);
							}
							if(chkb_telemark.isSelected()){
								AccreditationATD A = new AccreditationATD();
								A.setNom("Telemark");
								//A.setNumAccreditation(4);
								listAccreditation.add(A);
							}
							if(chkb_jeune.isSelected()){
								AccreditationATD A = new AccreditationATD();
								A.setNom("Enfant");
								//A.setNumAccreditation(5);
								listAccreditation.add(A);
							}
							if(chkb_adulte.isSelected()){
								AccreditationATD A = new AccreditationATD();
								A.setNom("Adulte");
								//A.setNumAccreditation(6);
								listAccreditation.add(A);
							}
							numUtilisateur = UATD.inscriptionMoniteur(listAccreditation, txtF_userName.getText(), txtF_mdp.getText(), txtF_nom.getText(), txtF_pre.getText(),
									sd, txtF_adresse.getText(), sexe);
							if(numUtilisateur != -1){

								DisponibiliteMoniteurATD DATD = new DisponibiliteMoniteurATD();
								DATD.creerTouteDisponibilitesSelonMoniteur(numUtilisateur);

								setVisible(false);
								F_Moniteur frame = new F_Moniteur(numUtilisateur);
								frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
								frame.setVisible(true);
							}
							else { JOptionPane.showMessageDialog(contentPane, "Verifiez vos donnees"); }
						}
					}
					else { JOptionPane.showMessageDialog(contentPane, "Certaines champs sont vides."); }

				}
				catch (ParseException e) {
					JOptionPane.showMessageDialog(contentPane, "Verifiez vos données.");
					e.printStackTrace();
				}
			}
		});

	}
}
