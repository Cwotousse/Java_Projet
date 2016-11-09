package be.mousty.fenetre;

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
import javax.swing.table.DefaultTableModel;

import be.mousty.accessToDao.ReservationATD;
import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Reservation;
import be.mousty.utilitaire.ButtonColumn;

public class F_AfficherRDV extends JFrame {
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
					F_AfficherRDV frame = new F_AfficherRDV(118);
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
	public F_AfficherRDV(int idPersonne) {
		try{
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 791, 375);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);

			// ADF
			AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
			DAO<Reservation> ReservationDAO = adf.getReservationDAO();

			// New
			JLabel lblVosCours = new JLabel("Vos cours");
			JSeparator separator = new JSeparator();
			JLabel lbl_error = new JLabel("Error label");
			JButton btn_fr = new JButton("D\u00E9connexion");
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
					// Deconnexion
					setVisible(false);
					F_Connexion frame = new F_Connexion();
					frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frame.setVisible(true);
				}
			});




			// Liste RDV
			ReservationATD R = new ReservationATD();
			ArrayList<Reservation> listReserv = R.getMyList(idPersonne);
			int somme = 0;
			int sommeAssurance = 0;
			int sommeReduction = 0;
			// TABLEAU -> https://tips4java.wordpress.com/2010/01/24/table-row-rendering/
			//headers for the table
			String[] columns = new String[] { "N�","P�riode", "Horaire", "Libell�", "El�ves max", "Moniteur", "Eleve", "Titutlaire", "Prix", "Annuler" };
			@SuppressWarnings("rawtypes")
			HashSet hs_numSem = new HashSet();
			//actual data for the table in a 2d array
			Object[][] data  = new Object[listReserv.size()][10];

			for (int i = 0; i < listReserv.size(); i++) {
				data[i][0] = listReserv.get(i).getNumReservation();
				data[i][1] = listReserv.get(i).getSemaine().getDateDebut() + " � " + listReserv.get(i).getSemaine().getDateFin();
				data[i][2] = listReserv.get(i).getHeureDebut() + " � " + listReserv.get(i).getHeureFin() + "h";
				data[i][3] = listReserv.get(i).getCours().getNomSport();
				data[i][4] = listReserv.get(i).getCours().getMaxEl();
				data[i][5] = listReserv.get(i).getMoniteur().getNom().toUpperCase() + " " + listReserv.get(i).getMoniteur().getPre();
				data[i][6] = listReserv.get(i).getEleve().getNom().toUpperCase() + " " + listReserv.get(i).getEleve().getPre();
				data[i][7] = listReserv.get(i).getClient().getNom().toUpperCase() + " " + listReserv.get(i).getClient().getPre();
				data[i][8] = listReserv.get(i).getCours().getPrix() + "�";
				data[i][9] = "Del"; //listReserv.get(i).getCours().getPrix() + "�";
				somme += listReserv.get(i).getCours().getPrix();
				
				if(listReserv.get(i).getAUneAssurance()){ sommeAssurance += 15; }
				hs_numSem.add(listReserv.get(i).getSemaine().getNumSemaine());
			}
			for(Object hs : hs_numSem) {
				// R�duction par semaine
				sommeReduction = ReservationDAO.valeurReduction((int)hs);
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
					if(ReservationDAO.delete(ReservationDAO.find(Integer.parseInt(numRes.toString()))))
						JOptionPane.showMessageDialog(null, "Cours supprim�.\nLe tableau sera actualis� par la suite.");
					else
						JOptionPane.showMessageDialog(null, "Une erreur est intervenue, le cours n'est pas supprim�.");
				}
			};

			new ButtonColumn(table, changerValeur, 9);

			JScrollPane pane = new JScrollPane(table);
			
			pane.setBounds(10, 42, 751, 255);
			contentPane.add(pane);

		}
		catch(Exception E){ E.getStackTrace(); }
	}
}

