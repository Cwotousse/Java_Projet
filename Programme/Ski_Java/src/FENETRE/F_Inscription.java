package FENETRE;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;
import org.jdatepicker.impl.UtilDateModel;

import DAO.AbstractDAOFactory;
import DAO.DAO;
import POJO.Accreditation;
import POJO.Client;
import POJO.Moniteur;
import POJO.Personne;
import POJO.Utilisateur;

import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
//import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButtonMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTable;

public class F_Inscription extends JFrame {

	private JPanel contentPane;
	private JTextField txtF_userName;
	private JTextField txtF_mdp;
	private JTextField txtF_nom;
	private JTextField txtF_pre;
	private JTextField txtF_adresse;
	private String sexe = "H"; 
	private JTable table;
	private JTextField txtF_adresseFact;
	private ArrayList<Accreditation> listAccreditation = new ArrayList<Accreditation>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Inscription frame = new F_Inscription("", "");
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
	public F_Inscription(String login, String mdp) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 350, 320);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblInscription = new JLabel("Inscription");
		lblInscription.setFont(new Font("Yu Gothic UI", Font.PLAIN, 13));
		lblInscription.setBounds(10, 11, 67, 14);
		contentPane.add(lblInscription);
		
		txtF_userName = new JTextField();
		txtF_userName.setToolTipText("Pseudonyme");
		txtF_userName.setBounds(159, 43, 155, 20);
		txtF_userName.setText(login);
		contentPane.add(txtF_userName);
		txtF_userName.setColumns(10);
		
		txtF_mdp = new JTextField();
		txtF_mdp.setToolTipText("Mot de passe");
		txtF_mdp.setBounds(159, 101, 155, 20);
		txtF_mdp.setText(mdp);
		contentPane.add(txtF_mdp);
		txtF_mdp.setColumns(10);
		
		txtF_nom = new JTextField();
		txtF_nom.setToolTipText("Nom");
		txtF_nom.setBounds(10, 43, 130, 20);
		contentPane.add(txtF_nom);
		txtF_nom.setColumns(10);
		
		txtF_pre = new JTextField();
		txtF_pre.setToolTipText("Prenom");
		txtF_pre.setBounds(10, 101, 130, 20);
		contentPane.add(txtF_pre);
		txtF_pre.setColumns(10);
		
		txtF_adresse = new JTextField();
		txtF_adresse.setToolTipText("Adresse");
		txtF_adresse.setBounds(10, 202, 130, 40);
		contentPane.add(txtF_adresse);
		txtF_adresse.setColumns(10);
		
		JLabel lblNom = new JLabel("Nom");
		lblNom.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblNom.setBounds(10, 31, 46, 14);
		contentPane.add(lblNom);
		
		JLabel lblPre = new JLabel("Prenom");
		lblPre.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblPre.setBounds(10, 84, 130, 14);
		contentPane.add(lblPre);
		
		JLabel lblPseu = new JLabel("Pseudonyme");
		lblPseu.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblPseu.setBounds(159, 31, 155, 14);
		contentPane.add(lblPseu);
		
		JLabel lblMdp = new JLabel("Mot de passe");
		lblMdp.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblMdp.setBounds(159, 84, 155, 14);
		contentPane.add(lblMdp);
		
		JLabel lblDateNaiss = new JLabel("Date de naissance");
		lblDateNaiss.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblDateNaiss.setBounds(10, 132, 130, 14);
		contentPane.add(lblDateNaiss);
		
		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblAdresse.setBounds(10, 185, 130, 14);
		contentPane.add(lblAdresse);
		
		JLabel lblAdresseFact = new JLabel("Adresse de facturation");
		lblAdresseFact.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblAdresseFact.setBounds(159, 185, 155, 14);
		contentPane.add(lblAdresseFact);
		
		JLabel lblAccred = new JLabel("Accreditation");
		lblAccred.setFont(new Font("Yu Gothic UI", Font.PLAIN, 11));
		lblAccred.setBounds(159, 185, 155, 14);
		contentPane.add(lblAccred);
		
		txtF_adresseFact = new JTextField();
		txtF_adresseFact.setBounds(159, 203, 155, 39);
		contentPane.add(txtF_adresseFact);
		txtF_adresseFact.setColumns(10);
		
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
		
		JRadioButton rdbtnH = new JRadioButton("Homme");
		JRadioButton rdbtnF = new JRadioButton("Femme");
		rdbtnH.setSelected(true);
		rdbtnH.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnH.setSelected(true);
				sexe = "H";
				rdbtnF.setSelected(false);
			}
		});
		
		rdbtnH.setBounds(159, 128, 67, 23);
		contentPane.add(rdbtnH);
		
		
		rdbtnF.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnH.setSelected(false);
				sexe = "F";
				rdbtnF.setSelected(true);
			}
		});
		rdbtnF.setBounds(228, 128, 75, 23);
		contentPane.add(rdbtnF);
		
		JCheckBox chkb_snow = new JCheckBox("Snowboard");
		chkb_snow.setToolTipText("Accreditation");
		chkb_snow.setBounds(239, 202, 97, 23);
		contentPane.add(chkb_snow);
		
		JCheckBox chkb_skiFond = new JCheckBox("Ski fond");
		chkb_skiFond.setToolTipText("Accreditation");
		chkb_skiFond.setBounds(239, 221, 97, 23);
		contentPane.add(chkb_skiFond);
		
		JCheckBox chkb_skiAlpin = new JCheckBox("Ski alpin");
		chkb_skiAlpin.setToolTipText("Accreditation");
		chkb_skiAlpin.setBounds(159, 221, 97, 23);
		contentPane.add(chkb_skiAlpin);
		
		JCheckBox chkb_telemark = new JCheckBox("T\u00E9l\u00E9mark");
		chkb_telemark.setToolTipText("Accreditation");
		chkb_telemark.setBounds(159, 202, 97, 23);
		contentPane.add(chkb_telemark);
		
		JLabel lbl_errLab = new JLabel("");
		lbl_errLab.setForeground(Color.RED);
		lbl_errLab.setBounds(128, 12, 186, 14);
		contentPane.add(lbl_errLab);
		lblAccred.setVisible(false);
		chkb_snow.setVisible(false);
		chkb_skiAlpin.setVisible(false);
		chkb_skiFond.setVisible(false);
		chkb_telemark.setVisible(false);
		
		JRadioButton rdbtnMoniteur = new JRadioButton("Moniteur");
		JRadioButton rdbtnClient = new JRadioButton("Client");
		rdbtnMoniteur.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnMoniteur.setSelected(true);
				rdbtnClient.setSelected(false);
				lblAdresseFact.setVisible(false);
				txtF_adresseFact.setVisible(false);
				
				lblAccred.setVisible(true);
				chkb_snow.setVisible(true);
				chkb_skiAlpin.setVisible(true);
				chkb_skiFond.setVisible(true);
				chkb_telemark.setVisible(true);
				
			}
		});
		rdbtnMoniteur.setBounds(228, 146, 75, 23);
		contentPane.add(rdbtnMoniteur);
		
		rdbtnClient.setSelected(true);
		rdbtnClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbtnClient.setSelected(true);
				lblAdresseFact.setVisible(true);
				txtF_adresseFact.setVisible(true);
				
				rdbtnMoniteur.setSelected(false);
				lblAccred.setVisible(false);
				chkb_snow.setVisible(false);
				chkb_skiAlpin.setVisible(false);
				chkb_skiFond.setVisible(false);
				chkb_telemark.setVisible(false);
			}
		});
		rdbtnClient.setBounds(159, 146, 67, 23);
		contentPane.add(rdbtnClient);
		
		JButton btn_inscrip = new JButton("S'enregistrer");
		
		
		btn_inscrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
				try {
				
				//Utilisateur u = UtilisateurDao.verifPseudoMdp(txtNomDutilisateur.getText(), pwdPassword.getText());
				//Date dt = new Date(10, 10, 2016);
				//java.sql.Date selectedDate = dt;//(java.sql.Date) datePicker.getModel().getValue();
				String dateNaissance = datePicker.getJFormattedTextField().getText();
				DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date ud = df.parse(dateNaissance);
				java.sql.Date sd = new java.sql.Date(ud.getTime());
				
				if(rdbtnClient.isSelected()){
					DAO<Client> ClientDao = adf.getClientDAO();
					if (ClientDao.create(new Client(txtF_nom.getText(), txtF_pre.getText(), txtF_adresse.getText(), sexe, sd,
							txtF_userName.getText(), txtF_mdp.getText(), 2, txtF_adresseFact.getText()))){
						// Afficher la fenetre client
						setVisible(false); //you can't see me!
						//dispose(); //Destroy the JFrame object
						F_Client frame = new F_Client();
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frame.setVisible(true);
					}
					else { lbl_errLab.setText("Verifiez vos donnees");}
				}
				else {
					DAO<Moniteur> MoniteurDao = adf.getMoniteurDAO();

					if(chkb_snow.isSelected()) 		listAccreditation.add(new Accreditation("Snowboard"));
					if(chkb_skiAlpin.isSelected())	listAccreditation.add(new Accreditation("Ski"));
					if(chkb_skiFond.isSelected())	listAccreditation.add(new Accreditation("Ski de fond"));
					if(chkb_telemark.isSelected())	listAccreditation.add(new Accreditation("Telemark"));
					
					if (MoniteurDao.create(new Moniteur(txtF_nom.getText(), txtF_pre.getText(), txtF_adresse.getText(), sexe,
							sd, txtF_userName.getText(), txtF_mdp.getText(), 1, listAccreditation))){
						setVisible(false); //you can't see me!
						//dispose(); //Destroy the JFrame object
						F_Moniteur frame = new F_Moniteur();
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frame.setVisible(true);
					}
					else { lbl_errLab.setText("Verifiez vos donnees");}
					
				}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					lbl_errLab.setText("Verifiez vos donnees");
					e.printStackTrace();
				}
			}
		});
		btn_inscrip.setBounds(159, 251, 155, 23);
		contentPane.add(btn_inscrip);
		
		JButton btn_retour = new JButton("Retour");
		btn_retour.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				F_Connexion frame = new F_Connexion();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		btn_retour.setBounds(10, 253, 130, 23);
		contentPane.add(btn_retour);
		
		lblAdresseFact.setVisible(true);
		txtF_adresseFact.setVisible(true);
		
		rdbtnMoniteur.setSelected(false);
		

	}
	
	public class DateLabelFormatter extends AbstractFormatter {

	    private String datePattern = "dd-MM-yyyy";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }
	}
}