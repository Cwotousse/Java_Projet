package FENETRE;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DAO.AbstractDAOFactory;
import DAO.DAO;
import POJO.Moniteur;
import POJO.Reservation;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class F_AfficherRDV extends JFrame {
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_AfficherRDV frame = new F_AfficherRDV(-1);
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
			lbl_somme.setBounds(166, 313, 203, 14);
			

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
			ArrayList<Reservation> listReserv = ReservationDAO.getMyList(idPersonne);
			int somme = 0;
			// TABLEAU -> https://tips4java.wordpress.com/2010/01/24/table-row-rendering/
			//headers for the table
			String[] columns = new String[] { "N�","P�riode", "Horaire", "Libell�", "Niveau", "Moniteur", "Eleve", "Titutlaire", "Prix" };

			//actual data for the table in a 2d array
			Object[][] data  = new Object[listReserv.size()][9];

			for (int i = 0; i < listReserv.size(); i++) {
				data[i][0] = listReserv.get(i).getNumReservation();
				data[i][1] = listReserv.get(i).getSemaine().getDateDebut() + " � " + listReserv.get(i).getSemaine().getDateFin();
				data[i][2] = listReserv.get(i).getHeureDebut() + " � " + listReserv.get(i).getHeureFin() + "h";
				data[i][3] = listReserv.get(i).getCours().getNomSport();
				data[i][4] = "niveau";
				data[i][5] = listReserv.get(i).getMoniteur().getNom().toUpperCase() + " " + listReserv.get(i).getMoniteur().getPre();
				data[i][6] = listReserv.get(i).getEleve().getNom().toUpperCase() + " " + listReserv.get(i).getEleve().getPre();
				data[i][7] = listReserv.get(i).getClient().getNom().toUpperCase() + " " + listReserv.get(i).getClient().getPre();
				data[i][8] = listReserv.get(i).getCours().getPrix() + "�";
				somme += listReserv.get(i).getCours().getPrix();
				if(listReserv.get(i).getAUneAssurance()){ somme += 15; }
			}
			lbl_somme.setText("La somme totale est de : " + somme + " �.");
			
			JScrollPane scrollPane = new JScrollPane(new JTable(new DefaultTableModel(data, columns)));
			scrollPane.setBounds(10, 42, 751, 255);
			contentPane.add(scrollPane);

		}
		catch(Exception E){ E.getStackTrace(); }
	}
}
