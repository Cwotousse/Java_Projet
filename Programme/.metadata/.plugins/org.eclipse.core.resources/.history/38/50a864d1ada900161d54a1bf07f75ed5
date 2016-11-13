package be.mousty.fenetre;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
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

public class F_Moniteur extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int numMoniteur = -1;
	DisponibiliteMoniteurATD DMATD = new DisponibiliteMoniteurATD();
	SemaineATD SATD = new SemaineATD();

	private JPanel contentPane;
	private final JLabel lblMoniteur = new JLabel("Moniteur");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Moniteur frame = new F_Moniteur(120);
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
	public F_Moniteur(int idMoniteur) {
		numMoniteur = idMoniteur;
		boolean premierPassage = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblMoniteur.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));
		lblMoniteur.setBounds(10, 11, 117, 36);
		contentPane.add(lblMoniteur);

		JLabel errMsg = new JLabel("");
		errMsg.setForeground(Color.RED);
		errMsg.setBounds(10, 11, 46, 14);
		contentPane.add(errMsg);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 45, 73, 2);
		contentPane.add(separator);

		JButton btnDeco = new JButton("Se d\u00E9connecter");
		btnDeco.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				F_Connexion frame = new F_Connexion();
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		btnDeco.setBounds(10, 92, 117, 23);
		contentPane.add(btnDeco);

		JButton btn_cours = new JButton("Mes cours");
		btn_cours.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Affiche F_AfficherRDV
				setVisible(false);
				F_AfficherRDV frame = new F_AfficherRDV(idMoniteur);
				frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		btn_cours.setBounds(10, 58, 117, 23);
		contentPane.add(btn_cours);
		
		// Affiche le nom du moniteur
		MoniteurATD MATD = new MoniteurATD();
		MATD = MATD.findM(numMoniteur);
		lblMoniteur.setText(MATD.getNom().toUpperCase() + " " + MATD.getPre());
		
		if (premierPassage)
			afficherTable();
		premierPassage = false;
	}

	public void afficherTable() {

		// ArrayList<DisponibiliteMoniteur> listDispo =
		// DisponibiliteMoniteurDAO.getMyList(numMoniteur);
		ArrayList<DisponibiliteMoniteurATD> listDispo = DMATD.getListDispo(numMoniteur);
		// ArrayList<Semaine> listSemaine = SemaineDAO.getList();
		ArrayList<SemaineATD> listSemaine = SATD.getListSem();
		// headers for the table
		String[] columns = new String[] { "Dispo", "Debut", "Fin", "Modifier" };

		// actual data for the table in a 2d array
		Object[][] data = new Object[listSemaine.size()][4];

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
				DMATD.updateDispo(dateDebutSem, F_Moniteur.numMoniteur);
				JOptionPane.showMessageDialog(null, "Disponibilité modifiée!");
				table.removeAll();
				afficherTable();
			}
		};

		// ButtonColumn buttonColumn = new ButtonColumn(table, changerValeur,
		// 2);
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
		contentPane.add(pane);// getContentPane().add(pane);
	}
}