package be.mousty.fenetre;
/**
	Classe JFrame permettant d'afficher les fen�tres qui permettent d'utiliser le programme.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category Fen�tre
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.ArrayList;

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

import be.mousty.accessToDao.DisponibiliteMoniteurATD;
import be.mousty.accessToDao.MoniteurATD;
import be.mousty.accessToDao.SemaineATD;
import be.mousty.utilitaire.ButtonColumn;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class F_Moniteur extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int numMoniteur = -1;
	DisponibiliteMoniteurATD DMATD = new DisponibiliteMoniteurATD();
	SemaineATD SATD = new SemaineATD();

	private JPanel contentPane;
	// New
	JSeparator separator 				= new JSeparator();
	JButton btnDeco 					= new JButton("Se d\u00E9connecter");
	JButton btn_cours 					= new JButton("Mes cours");
	private final JLabel lblMoniteur 	= new JLabel("Moniteur");
	JCheckBox chckB_coursPart 			= new JCheckBox("Disponible");
	JLabel lblNewLabel		 			= new JLabel("Cours particulier");


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Moniteur frame = new F_Moniteur(120);
					frame.setVisible(true);
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public F_Moniteur(int idMoniteur) {
		setTitle("Domaine Ch�telet");
		setIconImage(Toolkit.getDefaultToolkit().getImage("..\\logo.png"));
		numMoniteur = idMoniteur;
		boolean premierPassage = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		chckB_coursPart.setVerticalAlignment(SwingConstants.TOP);

		// Font
		lblMoniteur.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));

		// Bounds
		lblMoniteur.setBounds(10, 11, 117, 36);
		separator.setBounds(10, 45, 73, 2);
		btnDeco.setBounds(10, 92, 117, 23);
		btn_cours.setBounds(10, 58, 117, 23);
		chckB_coursPart.setBounds(14, 134, 113, 25);
		lblNewLabel.setBounds(24, 156, 103, 16);

		// Add		
		contentPane.add(lblMoniteur);
		contentPane.add(separator);
		contentPane.add(btnDeco);
		contentPane.add(btn_cours);
		contentPane.add(lblNewLabel);
		contentPane.add(chckB_coursPart);



		// Affiche le nom du moniteur
		MoniteurATD MATD = new MoniteurATD();
		MATD = MATD.findM(numMoniteur);
		lblMoniteur.setText(MATD.getNom().toUpperCase() + " " + MATD.getPre());

		// Action sur les boutons
		// Se d�connecter
		btnDeco.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				F_Connexion frame = new F_Connexion();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});

		//Modifier sa disponibilit�
		chckB_coursPart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// update les disponibilit�s
				MoniteurATD MATD = new MoniteurATD();
				DisponibiliteMoniteurATD dm = new DisponibiliteMoniteurATD();
				if (dm.possedeCoursParticulier(idMoniteur)){
					if(MATD.updateDispo(numMoniteur)) JOptionPane.showMessageDialog(contentPane, "Disponibilit� mise � jour.");
					else JOptionPane.showMessageDialog(contentPane, "Une erreur est intervenue lors de la modification.");
				}
				else { JOptionPane.showMessageDialog(contentPane, "Vous ne pouvez pas mettre � jour votre statut, des cours sont en attente d'�tre prest�s."); chckB_coursPart.setSelected(true); } 
			}
		});

		// Affichier les cours du moniteur
		btn_cours.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Affiche F_AfficherRDV
				setVisible(false);
				F_AfficherCoursaPresterMoniteur frame = new F_AfficherCoursaPresterMoniteur(idMoniteur);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});

		if (premierPassage){
			afficherTable(idMoniteur);
			chargerCheckoxCoursParticulier(idMoniteur);
		}
		premierPassage = false;
	}

	public void afficherTable(int idMoniteur) {
		ArrayList<DisponibiliteMoniteurATD> listDispo = DMATD.getListDispo(numMoniteur);
		ArrayList<SemaineATD> listSemaine = SATD.getListSem();

		String[] columns = new String[] { "Dispo", "Debut", "Fin", "Modifier" }; // headers for the table
		Object[][] data = new Object[listSemaine.size()][4]; // actual data for the table in a 2d array

		for (int i = 0; i < listSemaine.size(); i++) {
			data[i][0] = listDispo.get(i).getDisponible();
			data[i][1] = listSemaine.get(i).getDateDebut();
			data[i][2] = listSemaine.get(i).getDateFin();
			data[i][3] = "Changer"; // ajout du bouton
		}
		DefaultTableModel model = new DefaultTableModel(data, columns);
		JTable table = new JTable(model);

		// Action de modification
		final Action changerValeur = new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable mytableClicked = (JTable) e.getSource();
				Date dateDebutSem = (Date) mytableClicked.getModel().getValueAt(mytableClicked.getSelectedRow(), 1);
				// Update de la disponibilit�
				if(DMATD.updateDispo(dateDebutSem, F_Moniteur.numMoniteur)){
					JOptionPane.showMessageDialog(contentPane, "Disponibilit� modifi�e!");
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "Vous ne pouvez pas mettre � jour votre statut, des cours sont en attente d'�tre prest�s.");
				}


				//Allouer un nouveau moniteur si la dispo devient fausse.


				setVisible(false);
				F_Moniteur frame = new F_Moniteur(idMoniteur);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		};

		new ButtonColumn(table, changerValeur, 3);
		JScrollPane pane = new JScrollPane(table);
		// Changer la couleur selon la dispo
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int col) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

				boolean status = (boolean) table.getModel().getValueAt(row, 0);
				if (status) { setBackground(new Color(102, 255, 51)); } 
				else { setBackground(new Color(255, 77, 0)); }
				return this;
			}
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);

		pane.setBounds(156, 18, 388, 232);
		contentPane.add(pane);
	}

	public void chargerCheckoxCoursParticulier(int idMoniteur){
		MoniteurATD MATD = new MoniteurATD();
		chckB_coursPart.setSelected(MATD.find(idMoniteur).getDisponiblecoursParticulier());
	}
}