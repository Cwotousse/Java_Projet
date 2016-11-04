package FENETRE;

import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import DAO.AbstractDAOFactory;
import DAO.DAO;
import POJO.ButtonColumn;
import POJO.DisponibiliteMoniteur;
import POJO.Semaine;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.table.*;

public class F_Moniteur extends JFrame {
	static int numMoniteur = -1;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<DisponibiliteMoniteur> DisponibiliteMoniteurDAO = adf.getDisponibiliteMoniteurDAO();
	DAO<Semaine> SemaineDAO = adf.getSemaineDAO();

	private JPanel contentPane;
	private final JLabel lblMoniteur = new JLabel("Moniteur");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_Moniteur frame = new F_Moniteur(109);
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
		numMoniteur = idMoniteur;
		boolean premierPassage = true;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblMoniteur.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));
		lblMoniteur.setBounds(10, 11, 73, 36);
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

		/*// Action de modification
		final Action changerValeur = new AbstractAction() 
		{
		    @Override
		    public void actionPerformed(ActionEvent e) 
		    {
		    	JTable mytableClicked = (JTable)e.getSource();
		        String[] parts = mytableClicked.getModel().getValueAt(mytableClicked.getSelectedRow(), mytableClicked.getSelectedColumn()).toString().split(",");
		        DisponibiliteMoniteurDAO.changeDispoSelonIdSemaine(Integer.parseInt(parts[0]), F_Moniteur.numMoniteur);
				JOptionPane.showMessageDialog(null, "Disponibilité modifiée!");
				//afficherTable();

		    }
		};

		// construction du JTable
		ArrayList<DisponibiliteMoniteur> listDispo = DisponibiliteMoniteurDAO.getMyList(numMoniteur);
		ArrayList<Semaine> listSemaine = SemaineDAO.getList();
		//headers for the table
		String[] columns = new String[] { "Dispo","Période", "Modifier" };

		//actual data for the table in a 2d array
		Object[][] data  = new Object[listDispo.size()][3];

		for (int i = 0; i < listDispo.size(); i++) {
			data[i][0] = listDispo.get(i).getDisponible();
			data[i][1] = listSemaine.get(i).getDateDebut() + " à " + listSemaine.get(i).getDateFin();
			data[i][2] =  listSemaine.get(i).getNumSemaine() + ", Changer "; // ajout du bouton

			/*if (listDispo.get(i).getDisponible() == true)
				data.ro.setBackground(Color.GREEN);
			else
				data[i].setBackground(Color.RED);
		}
		DefaultTableModel model = new DefaultTableModel(data, columns);
		JTable table = new JTable( model );

		ButtonColumn buttonColumn = new ButtonColumn(table, changerValeur, 2);

		JScrollPane pane = new JScrollPane(table);

		// Changer la couleur selon la dispo
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		        boolean status= (boolean)table.getModel().getValueAt(row, 0);
		        if (status) { setBackground(new Color(102,255,51)); } 
		        else { setBackground(new Color(255,77,0)); }       
		        return this;
		    }   
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);


		pane.setBounds(156, 18, 388, 232);
		getContentPane().add(pane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);*/
		if(premierPassage)
			afficherTable();
		premierPassage = false;
	}

	public  void afficherTable(){
		ArrayList<DisponibiliteMoniteur> listDispo = DisponibiliteMoniteurDAO.getMyList(numMoniteur);
		ArrayList<Semaine> listSemaine = SemaineDAO.getList();
		//headers for the table
		String[] columns = new String[] { "Dispo","Période", "Modifier" };

		//actual data for the table in a 2d array
		Object[][] data  = new Object[listDispo.size()][3];

		for (int i = 0; i < listDispo.size(); i++) {
			data[i][0] = listDispo.get(i).getDisponible();
			data[i][1] = listSemaine.get(i).getDateDebut() + " à " + listSemaine.get(i).getDateFin();
			data[i][2] =  listSemaine.get(i).getNumSemaine() + ", Changer "; // ajout du bouton
		}
		DefaultTableModel model = new DefaultTableModel(data, columns);
		JTable table = new JTable( model );

		// Action de modification
		final Action changerValeur = new AbstractAction() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				JTable mytableClicked = (JTable)e.getSource();
				String[] parts = mytableClicked.getModel().getValueAt(mytableClicked.getSelectedRow(), mytableClicked.getSelectedColumn()).toString().split(",");
				DisponibiliteMoniteurDAO.changeDispoSelonIdSemaine(Integer.parseInt(parts[0]), F_Moniteur.numMoniteur);
				JOptionPane.showMessageDialog(null, "Disponibilité modifiée!");
				table.removeAll();
				afficherTable();
				//afficherTable();
				//table.repaint();
				//F_Moniteur.this.removeAll(); 
				//F_Moniteur.this.updateWindow();
			}
		};

		ButtonColumn buttonColumn = new ButtonColumn(table, changerValeur, 2);

		JScrollPane pane = new JScrollPane(table);

		// Changer la couleur selon la dispo
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

				boolean status= (boolean)table.getModel().getValueAt(row, 0);
				if (status) { setBackground(new Color(102,255,51)); } 
				else { setBackground(new Color(255,77,0)); }       
				return this;
			}   
		});

		table.getColumnModel().getColumn(0).setPreferredWidth(15);
		table.getColumnModel().getColumn(2).setPreferredWidth(20);


		pane.setBounds(156, 18, 388, 232);
		contentPane.add(pane);//getContentPane().add(pane);
	}
}