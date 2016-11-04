package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import POJO.Cours;
import POJO.CoursCollectif;
import POJO.CoursParticulier;
import POJO.Eleve;
import POJO.Reservation;
import POJO.Semaine;
public class SemaineDAO extends DAO<Semaine> {
	public SemaineDAO(Connection conn) {
		super(conn);
	}

	public int create(Semaine obj) { 
		try
		{
			System.out.println("SemaineDao -> " + obj.getNumSemaineDansAnnee());
			String requete2 = "INSERT INTO Semaine (congeScolaireOuNon,dateDebut,dateFin,numSemaineDansAnnee) VALUES (?,?,?,?)";
			PreparedStatement pst2 = connect.prepareStatement(requete2);

			//pst2.setInt(1, numSemaine);     //L'id qui lie la table moniteur a la table personne
			pst2.setBoolean(1, obj.getCongeScolaire());
			pst2.setDate(2, obj.getDateDebut());
			pst2.setDate(3, obj.getDateFin());
			pst2.setInt(4, obj.getNumSemaineDansAnnee());

			pst2.executeUpdate();
			pst2.close();


			PreparedStatement pst_numReserv;
			String selectNumReserv = "SELECT MAX(numSemaine) FROM Semaine";
			pst_numReserv = this.connect.prepareStatement(selectNumReserv);
			ResultSet rs = pst_numReserv.executeQuery();
			while (rs.next()) { obj.setNumSemaine(rs.getInt(1)); }
			System.out.println("ReservationDao -> " + obj.getNumSemaine());
			
			return obj.getNumSemaine();
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	public boolean delete(Semaine obj) {
		return false;
	}

	public boolean update(Semaine obj) {
		return false;
	}

	// On cherche un élève grâce à son id
	public Semaine find(int id) {
		Semaine semaine = new Semaine();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM semaine WHERE numSemaine = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				semaine.setCongeScolaire(rs.getBoolean("CongeScolaireOuNon"));
				semaine.setDateDebut(rs.getDate("dateDebut"));
				semaine.setDateFin(rs.getDate("dateFin"));
				semaine.setNumSemaineDansAnnee(rs.getInt("numSemaineDansAnnee"));
				semaine.setNumSemaine(rs.getInt("numSemaine"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return semaine;
	}

	public  ArrayList<Semaine> getList() {
		ArrayList<Semaine> liste = new ArrayList<Semaine>();

		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Semaine";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Semaine semaine = new Semaine(rs.getInt("numSemaine"), rs.getBoolean("CongeScolaireOuNon"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getInt("numSemaineDansAnnee"));
				liste.add(semaine);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return liste;
	}
	
	/*public ArrayList<Semaine> getListSemaineSelonDateDuJour(){
		//AjouterSemainesDansDB();
		// ATTENTION SEMAINE NUMANNEE 44 A 47 SONT A SUPPRIMER
		Semaine S = new Semaine();
		ArrayList<Semaine> listeRetour = new ArrayList<Semaine>();
		ArrayList<Semaine> listSemaine = S.getListSemaine();
		//  Aujourd'hui
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		int jourDelaisMax = EstEnPeriodeDeConge(today) ? 7 : 31; 
		Calendar c = Calendar.getInstance(); 
		c.setTime(today); 
		// 1 mois si période scolaire, sinon 7 jours.
		c.add(Calendar.DATE, jourDelaisMax); // Ajout d'un mois ou d'un jour si c'est un période de congé ou pas.
		java.util.Date maxDateToDisplay = c.getTime();
		
		if (listSemaine != null)
			for(Semaine s : listSemaine){
				// N'affiche que les semaines ou il n'y a pas de congés et qui ne sont pas passées.
				if (!s.getCongeScolaire() && s.dateDebut.after(today) && s.dateDebut.before(maxDateToDisplay)) 
					listeRetour.add(s);
			}
		return listeRetour;
	}*/
	
	@Override public String calculerPlaceCours(int numCours, int numSemaine) { return -1 + ""; }
	@Override public ArrayList<Semaine> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Semaine> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode) { return null; }
	@Override public ArrayList<Semaine> getListCoursParticulierSelonId(int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Semaine> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode, int cours) { return null; }
	@Override public ArrayList<Semaine> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Semaine> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
}

