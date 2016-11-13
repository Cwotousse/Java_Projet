package be.mousty.fenetre;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import be.mousty.accessToDao.CoursCollectifATD;
import be.mousty.accessToDao.CoursParticulierATD;
import be.mousty.accessToDao.MoniteurATD;
import be.mousty.accessToDao.ReservationATD;
import be.mousty.accessToDao.SemaineATD;
import be.mousty.utilitaire.ButtonColumn;

public class F_AfficherRDV extends JFrame {
	ReservationATD RATD = new ReservationATD();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_AfficherRDV frame = new F_AfficherRDV(118, 1);
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
	@SuppressWarnings("unchecked")
	public F_AfficherRDV(int idPersonne, int typePersonne) {
		
		try{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 1200, 385);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			// ADF
			//AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
			//DAO<Reservation> ReservationDAO = adf.getReservationDAO();

			// New
			JLabel lblVosCours = new JLabel("Vos cours");
			JSeparator separator = new JSeparator();
			JLabel lbl_error = new JLabel("Error label");
			JButton btn_fr = new JButton("Retour");
			JLabel lbl_somme = new JLabel("");


			// Font
			lblVosCours.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));

			// SetBound
			lblVosCours.setBounds(10, 11, 76, 20);
			separator.setBounds(10, 37, 76, 20);
			lbl_error.setBounds(166, 16, 203, 16);
			btn_fr.setBounds(10, 308, 125, 25);
			lbl_somme.setBounds(166, 313, 505, 14);


			// Add
			contentPane.add(lblVosCours);
			contentPane.add(separator);
			contentPane.add(lbl_error);
			contentPane.add(btn_fr);
			contentPane.add(lbl_somme);

			// Deconnexion
			btn_fr.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					setVisible(false);
					switch(typePersonne){
					case 1 : 
						F_Client frameC = new F_Client(idPersonne);
						frameC.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frameC.setVisible(true);
						break;
					case 2 : 
						F_Moniteur frameM = new F_Moniteur(idPersonne);
						frameM.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frameM.setVisible(true);
						break;
					}
				}
			});




			// Liste RDV
			
			ArrayList<ReservationATD> listReserv = RATD.getMyListATD(idPersonne);
			int somme = 0;
			int sommeAssurance = 0;
			int sommeReduction = 0;
			// TABLEAU -> https://tips4java.wordpress.com/2010/01/24/table-row-rendering/
			//headers for the table
			String[] columns = new String[] { "N�","Jour d�but", "jour fin", "heure debut", "heure fin", "Libell�", "El�ves min", "Eleves actuellement" ,"Moniteur", "Eleve", "Titutlaire", "type", "Prix", "Annuler" };
			@SuppressWarnings("rawtypes")
			HashSet hs_numSem = new HashSet();
			//actual data for the table in a 2d array
			Object[][] data  = new Object[listReserv.size()][14];

			for (int i = 0; i < listReserv.size(); i++) {
				MoniteurATD MATD = new MoniteurATD(listReserv.get(i).getMoniteur());
				SemaineATD SATD = new SemaineATD(listReserv.get(i).getSemaine());
				String strPlaceCours;
				RATD = listReserv.get(i);
				int numReservation = RATD.getIdReserv();
				int numCours = RATD.find(numReservation).getCours().getNumCours();
				if (listReserv.get(i).getCours().getPrix() < 90) { 
					CoursParticulierATD CPATD = new CoursParticulierATD(); 
					strPlaceCours = CPATD.calculerPlaceCours(
							numCours,
							RATD.getDateDebutReserv(numReservation),
							MATD.getId(listReserv.get(i).getMoniteur()).getNumMoniteur());
				}
				else { 
					CoursCollectifATD CCATD = new CoursCollectifATD(); 
					strPlaceCours = CCATD.calculerPlaceCours(
							numCours,
							(long)SATD.getId(listReserv.get(i).getSemaine()).getNumSemaine(),
							MATD.getId(listReserv.get(i).getMoniteur()).getNumMoniteur());
					}
				String[] parts = strPlaceCours.split("-");
				String[] partPeriode = listReserv.get(i).getCours().getPeriodeCours().split("-");
				
				
				
				RATD = listReserv.get(i);
				data[i][0] = RATD.getIdReserv();
				data[i][1] = listReserv.get(i).getSemaine().getDateDebut() ;
				data[i][2] = listReserv.get(i).getCours().getPrix() > 90 ? listReserv.get(i).getSemaine().getDateFin()  : listReserv.get(i).getSemaine().getDateDebut() ;
				data[i][3] = partPeriode[0] + "h";
				data[i][4] = partPeriode[1] + "h";
				data[i][5] = listReserv.get(i).getCours().getNomSport();
				data[i][6] = listReserv.get(i).getCours().getMinEl();
				data[i][7] = listReserv.get(i).getCours().getMaxEl() - Integer.parseInt(parts[0]);
				data[i][8] = listReserv.get(i).getMoniteur().getNom().toUpperCase() + " " + listReserv.get(i).getMoniteur().getPre();
				data[i][9] = listReserv.get(i).getEleve().getNom().toUpperCase() + " " + listReserv.get(i).getEleve().getPre();
				data[i][10] = listReserv.get(i).getClient().getNom().toUpperCase() + " " + listReserv.get(i).getClient().getPre();
				data[i][11] = listReserv.get(i).getCours().getPrix() > 90 ? "Collectif" : "Particulier";
				data[i][12] = listReserv.get(i).getCours().getPrix() + "�";
				data[i][13] = "Annuler"; //listReserv.get(i).getCours().getPrix() + "�";

				somme += listReserv.get(i).getCours().getPrix();

				if(listReserv.get(i).getAUneAssurance()){ sommeAssurance += 15; }
				hs_numSem.add(listReserv.get(i).getSemaine().getNumSemaine());
			}
			for(Object hs : hs_numSem) {
				// R�duction par semaine
				sommeReduction = RATD.valeurReduction((int)hs, -1, -1);
			}
			lbl_somme.setText("La somme totale est de : " + (somme + sommeAssurance - sommeReduction) + " � (" + sommeAssurance + "� d'assurance, " + sommeReduction + " de r�duction).");

			DefaultTableModel model = new DefaultTableModel(data, columns);
			JTable table = new JTable( model );

			// Action de modification
			final Action changerValeur = new AbstractAction() 
			{
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					JTable mytableClicked = (JTable)e.getSource();
					Object numRes = mytableClicked.getModel().getValueAt(mytableClicked.getSelectedRow(), 0);
					//if(ReservationDAO.delete(ReservationDAO.find(Integer.parseInt(numRes.toString()))))
					if(RATD.delete(RATD.find(Integer.parseInt(numRes.toString()))))
						JOptionPane.showMessageDialog(null, "Cours supprim�.");
					else
						JOptionPane.showMessageDialog(null, "Une erreur est intervenue, le cours n'est pas supprim�.");
					
					setVisible(false);
					switch(typePersonne){
					case 1 : 
						F_Client frameC = new F_Client(idPersonne);
						frameC.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frameC.setVisible(true);
						break;
					case 2 : 
						F_Moniteur frameM = new F_Moniteur(idPersonne);
						frameM.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
						frameM.setVisible(true);
						break;
					}
				}
			};

			new ButtonColumn(table, changerValeur, 13);

			JScrollPane pane = new JScrollPane(table);

			//Changer la couleur
			table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int col) {
					super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

					boolean estValide = false;
					// On va calculer la place des cours, ensuite on va voir si la palce correspond au maximum possible.
					// Si oui, on colorie en vert, si non on colorie en rouge.
					MoniteurATD MATD = new MoniteurATD(listReserv.get(row).getMoniteur());
					SemaineATD SATD = new SemaineATD(listReserv.get(row).getSemaine());
					ReservationATD RATD = listReserv.get(row);
					int numReservation = RATD.getIdReserv();
					int numCours = RATD.find(numReservation).getCours().getNumCours();
					String strPlaceCours;
					// ATTENTION il faut savoir si c'est un cours particulier ou collectif, cela se choisi via le prix.
					if (listReserv.get(row).getCours().getPrix() < 90) { 
						CoursParticulierATD CPATD = new CoursParticulierATD(); 
						strPlaceCours = CPATD.calculerPlaceCours(
								numCours,
								// modifier ici
								RATD.getDateDebutReserv(numReservation),
								MATD.getId(listReserv.get(row).getMoniteur()).getNumMoniteur());
					}
					else { 
						CoursCollectifATD CCATD = new CoursCollectifATD(); 
						strPlaceCours = CCATD.calculerPlaceCours(
								numCours,
								(long)SATD.getId(listReserv.get(row).getSemaine()).getNumSemaine(),
								MATD.getId(listReserv.get(row).getMoniteur()).getNumMoniteur());}

					//String string =  CATD.calculerPlaceCours(CATD.getId(listReserv.get(row).getCours()), dateJour.getTime() , MATD.getId(listReserv.get(row).getMoniteur()));

					
					//System.out.println(strPlaceCours +" ~ " + row );
					
					//System.out.println(RATD.find(numReservation).getSemaine().getDateFin().getTime());
					
					String[] parts = strPlaceCours.split("-");
					 // S'il y a assez de places mini, on colorie en vert.
					if(Integer.parseInt(parts[1]) == 0)
						estValide = true;
					if (estValide) { setBackground(new Color(102, 255, 51)); } 
					else { setBackground(new Color(255,255,153)); }
					return this;
				}
			});

			pane.setBounds(10, 42, 1168, 255);
			contentPane.add(pane);

		}
		catch(Exception E){ E.getStackTrace(); }
	}
}

