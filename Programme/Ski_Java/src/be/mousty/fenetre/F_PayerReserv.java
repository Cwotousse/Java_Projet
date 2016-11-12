package be.mousty.fenetre;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import be.mousty.accessToDao.ClientATD;
import be.mousty.accessToDao.CoursATD;
import be.mousty.accessToDao.CoursCollectifATD;
import be.mousty.accessToDao.CoursParticulierATD;
import be.mousty.accessToDao.EleveATD;
import be.mousty.accessToDao.MoniteurATD;
import be.mousty.accessToDao.ReservationATD;
import be.mousty.accessToDao.SemaineATD;
import net.miginfocom.swing.MigLayout;

public class F_PayerReserv extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9156134129701338741L;
	private JPanel contentPane;
	private JPasswordField pwdF_code;
	private JTextField txtF_numCompte;


	// Access To Dao
	SemaineATD SATD 			= new SemaineATD();
	CoursATD CATD 				= new CoursATD();
	ReservationATD RATD 		= new ReservationATD();
	EleveATD EATD 				= new EleveATD();
	ClientATD CLIATD 			= new ClientATD();
	MoniteurATD MATD 			= new MoniteurATD();
	CoursCollectifATD CCATD 	= new CoursCollectifATD();
	CoursParticulierATD CPATD 	= new CoursParticulierATD();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_PayerReserv frame = new F_PayerReserv(false, false, -1,-1,-1,-1, null, -1, "");
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
	public F_PayerReserv(boolean coursCollectif, boolean assurance, int numMoniteur, int idClient, int numEleve, int numSemaine, Date dateJour, int numCours, String periode) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][][][][grow][][][][]", "[][][][][][][][][][][][][][]"));

		JLabel lbl_reserv = new JLabel("R\u00E9servation");
		lbl_reserv.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));
		JLabel lbl_nom = new JLabel("Nom : ");
		JLabel lbl_affNom = new JLabel("");
		JLabel lbl_factur = new JLabel("Adresse facturation : ");
		JLabel lbl_nomElev = new JLabel("El\u00E8ve : ");
		JLabel lbl_affEleve = new JLabel("");
		JLabel lbl_affAdressFactur = new JLabel("");
		JLabel lbl_cours = new JLabel("Cours : ");
		JLabel lbl_affCours = new JLabel("");
		JLabel lbl_compte = new JLabel("N\u00B0 compte");
		JLabel lbl_periode = new JLabel("P\u00E9riode : ");
		JLabel lbl_affPeriode = new JLabel("");
		txtF_numCompte = new JTextField();
		JLabel lbl_prix = new JLabel("Prix : ");
		JLabel lbl_affPrix = new JLabel("");
		JLabel lbl_codeCompte = new JLabel("Code : ");
		JLabel lbl_assurance = new JLabel("Assurance : ");
		JLabel lbl_affAssurance = new JLabel("");
		pwdF_code = new JPasswordField();
		JLabel lbl_reduc = new JLabel("R\u00E9duction :");
		JLabel lbl_affReduction = new JLabel("");
		JLabel lbl_tot = new JLabel("Prix total :");
		JLabel lbl_affPrixTotal = new JLabel("");
		JButton btn_ret = new JButton("Annuler payement");
		JButton btn_payer = new JButton("Payer");


		txtF_numCompte.setColumns(10);

		contentPane.add(lbl_affEleve, "cell 2 2");
		contentPane.add(lbl_reserv, "cell 2 0");
		contentPane.add(lbl_nom, "cell 1 1");
		contentPane.add(lbl_affNom, "cell 2 1,alignx left,aligny bottom");
		contentPane.add(lbl_factur, "cell 5 1");
		contentPane.add(lbl_nomElev, "cell 1 2");
		contentPane.add(lbl_affAdressFactur, "cell 5 2");
		contentPane.add(lbl_cours, "cell 1 3");
		contentPane.add(lbl_affCours, "cell 2 3");
		contentPane.add(lbl_compte, "cell 5 3");
		contentPane.add(lbl_periode, "cell 1 4");
		contentPane.add(lbl_affPeriode, "cell 2 4");
		contentPane.add(txtF_numCompte, "cell 5 4,growx");
		contentPane.add(lbl_prix, "cell 1 5");
		contentPane.add(lbl_affPrix, "cell 2 5");
		contentPane.add(lbl_codeCompte, "cell 5 5");
		contentPane.add(lbl_assurance, "cell 1 6");
		contentPane.add(lbl_affAssurance, "cell 2 6");
		contentPane.add(pwdF_code, "cell 5 6,growx");
		contentPane.add(lbl_reduc, "cell 1 7");
		contentPane.add(lbl_affReduction, "cell 2 7");
		contentPane.add(lbl_tot, "cell 1 8");
		contentPane.add(lbl_affPrixTotal, "cell 2 8");
		contentPane.add(btn_ret, "cell 1 10");
		contentPane.add(btn_payer, "cell 5 10,growx");

		// Charger les infos
		lbl_affNom.setText(CLIATD.find(idClient).getNom().toUpperCase());
		lbl_affEleve.setText(EATD.find(numEleve).getPre().toUpperCase());
		lbl_affAdressFactur.setText(CLIATD.find(idClient).getAdresseFacturation());
		lbl_affCours.setText(CATD.find(numCours).getNomSport());
		lbl_affPeriode.setText(periode);
		lbl_affPrix.setText(CATD.find(numCours).getPrix() + "�");
		int prixAssurance = RATD.updateAssurance(numEleve, numSemaine, periode) ? 0 : 15;
		lbl_affAssurance.setText(prixAssurance + "�");
		lbl_affReduction.setText(RATD.valeurReduction(numSemaine, numEleve, CATD.find(numCours).getPrix()) + "�");
		lbl_affPrixTotal.setText(CATD.find(numCours).getPrix() + prixAssurance - RATD.valeurReduction(numSemaine, numEleve, CATD.find(numCours).getPrix()) + "�");



		// Effectuer la r�servation
		btn_payer.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(pwdF_code.getText() != "" && txtF_numCompte.getText() != ""){
					/*String string;
				if(!coursCollectif)
					string = CATD.calculerPlaceCours(numCours, dateJour.getTime(), numMoniteur);
				else 
					string = CATD.calculerPlaceCours(numCours, numSemaine, numMoniteur);
				//String string = CoursDAO.calculerPlaceCours(numCours, numSemaine, numMoniteur);
				String[] parts = string.split("-");
				String part1 = parts[0];
				if(Integer.parseInt(part1) > 0){
					//System.out.println("R�servation effectu�");
					String[] partsPer = periode.split("-");
					String heureDebut = partsPer[0];
					String heureFin = partsPer[1];
					//boolean assurance = chkb_assur.isSelected();
					RATD = new ReservationATD();
					RATD.setHeureDebut(Integer.parseInt(heureDebut));
					RATD.setHeureFin(Integer.parseInt(heureFin));
					RATD.setNumReservation(-1);
					RATD.setAUneAssurance(assurance);
					// utilis� pour r�servation coursParticulier.
					if(!coursCollectif){
						SATD = SATD.findATD(numSemaine);
						SATD.setDateDebut(dateJour);
						SATD.setDateFin(dateJour);
						RATD.setSemaine(SATD.transformATDintoPojo(numSemaine));
						System.out.println("F_AjoutRrdv -> cours particulier");
					}
					else { RATD.setSemaine(SATD.find(numSemaine)); System.out.println("F_AjoutRrdv -> cours collectif");}


					RATD.setCours(CATD.find(numCours));
					RATD.setEleve(EATD.find(numEleve));
					RATD.setClient(CLIATD.find(idClient));
					RATD.setMoniteur(MATD.find(numMoniteur));
					int numReservation = RATD.createReservation();


					if(numReservation != -1){
						// Update -> d�coche toutes les assurances pr�c�demment s�lectionn�es, pour faciliter le calcul d'assurance final.
						//ReservationDAO.updateAssurance(numEleve, numSemaine, periode);
						RATD.updateAssurance(numEleve, numSemaine, periode);
						System.out.println("Num reserv : " + numReservation);

						// Retour � l'ancien �cran
						setVisible(false);
						F_Client frame = new F_Client(idClient);
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frame.setVisible(true);
					}
					//else { lbl_infoCours.setText("Reservation annul�e. (6 ans mini pour faire du snow)"); }
					else JOptionPane.showMessageDialog(null, "Reservation annul�e. (6 ans mini pour faire du snow)");
				}
				//else { lbl_infoCours.setText("Vous ne pouvez plus r�server pour ce cours."); }
				else JOptionPane.showMessageDialog(null, "Vous ne pouvez plus r�server pour ce cours.");*/
					if(RATD.effectuerReservation(coursCollectif, assurance, numMoniteur, idClient, numEleve, numSemaine, dateJour, numCours, periode) != -1){
						// Retour � l'�cran client
						setVisible(false);
						F_Client frame = new F_Client(idClient);
						frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frame.setVisible(true);
					}
				}
				else{ JOptionPane.showMessageDialog(null, "Entre un n� de compte et/ou votre mot de passe.");}
			}

		});

		// annule le payement
		btn_ret.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setVisible(false);
				F_AjoutRdv frame = new F_AjoutRdv(idClient);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}
