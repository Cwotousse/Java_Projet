package be.mousty.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.mousty.pojo.Accreditation;

public class AccreditationDAO extends DAO<Accreditation> {
	public AccreditationDAO(Connection conn) { super(conn); }
	public int create(Accreditation obj) { return -1; }
	public boolean delete(Accreditation obj) { return false; }
	public boolean update(Accreditation obj) { return false; }

	// On cherche une Accreditation gr�ce � son id
	public Accreditation find(int id) {
		Accreditation accred = new Accreditation();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Accreditation WHERE numAccreditation = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet res_Rec_Accr = pst.executeQuery();
			// int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
			while (res_Rec_Accr.next()) {
				//accred = new Accreditation(result.getString("nom")); 
				accred  = new Accreditation();
				accred.setNomAccreditation(res_Rec_Accr.getString("nomAccreditation"));
				accred.setNumAccreditation(res_Rec_Accr.getInt("numAccreditation"));
			}
			return accred;
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return null;
	}



	public ArrayList<Accreditation> getList() {
		ArrayList<Accreditation> liste = new ArrayList<Accreditation>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Accreditation INNER JOIN Personne On Personne.numPersonne = Accreditation.numAccreditation";
			pst = this.connect.prepareStatement(sql);
			ResultSet res_Rec_Accr = pst.executeQuery();
			while (res_Rec_Accr.next()) {
				//int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
				Accreditation accred  = new Accreditation();
				accred.setNomAccreditation(res_Rec_Accr.getString("nom"));
				accred.setNumAccreditation(res_Rec_Accr.getInt("numAccreditation"));
				liste.add(accred);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return liste;
	}
	
	public int getNumAccred(String nomAccreditation) {
		PreparedStatement pst = null;
		int id = -1;
		try {
			String sql = "SELECT numAccreditation FROM Accreditation WHERE nomAccreditation = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setString(1, nomAccreditation);
			ResultSet res_Rec_Accr = pst.executeQuery();
			while (res_Rec_Accr.next()) { id = res_Rec_Accr.getInt("numAccreditation"); }
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return id;
	}

	@Override public String calculerPlaceCours(int numCours, int numSemaine, int numMoniteur) { return -1 + ""; }
	@Override public ArrayList<Accreditation> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Accreditation> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Accreditation> getListCoursParticulierSelonId(int numMoniteur, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Accreditation> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Accreditation> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Accreditation> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<Accreditation> getListDispo(int numSemaine, String periode) { return null; }
	@Override public Accreditation returnUser(String mdp, String pseudo) { return null; }
	@Override public int valeurReduction(int numSem) { return 0; }
	@Override
	public int getNumPersonne(String string, String pre, String adresse) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getNumCoursCollectif(String nomSport, String periode, String categorie, String niveauCours) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getNumCoursParticulier(String nomSport, String periode) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ArrayList<Accreditation> getListSemaineSelonDateDuJour() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getNumSemaine(Date dateDebut) {
		// TODO Auto-generated method stub
		return 0;
	}
}