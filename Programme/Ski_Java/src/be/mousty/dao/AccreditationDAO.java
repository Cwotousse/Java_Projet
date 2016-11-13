package be.mousty.dao;

import java.sql.Connection;
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
		Accreditation A = new Accreditation();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Accreditation WHERE numAccreditation = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet res_Rec_Accr = pst.executeQuery();
			// int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
			while (res_Rec_Accr.next()) {
				//accred = new Accreditation(result.getString("nom")); 
				A  = new Accreditation();
				A.setNomAccreditation(res_Rec_Accr.getString("nomAccreditation"));
				A.setNumAccreditation(res_Rec_Accr.getInt("numAccreditation"));
			}
			return A;
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
	
	@Override
	public Accreditation getId(Accreditation obj){
		PreparedStatement pst = null;
		Accreditation A = new Accreditation();
		try {
			String sql = "SELECT numAccreditation FROM Accreditation WHERE nomAccreditation = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setString(1, obj.getNomAccreditation());
			ResultSet res_Rec_Accr = pst.executeQuery();
			while (res_Rec_Accr.next()) {
				A.setNomAccreditation(res_Rec_Accr.getString("nom"));
				A.setNumAccreditation(res_Rec_Accr.getInt("numAccreditation"));
				}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return A;
	}
	@Override
	public ArrayList<Accreditation> getListSelonCriteres(Accreditation obj) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Accreditation> getMyListSelonID(int id1, long id2, int id3, String str1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean updateAssurance(int numEleve, int numSemaine, String periode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int valeurReduction(int numSem, int numEleve, double prixCours) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String calculerPlaceCours(int numCours, long numSemaine, int idMoniteur) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void creerTouteDisponibilites() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void creerTouteDisponibilitesSelonMoniteur(int i) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void AjouterSemainesDansDB(String start, String end) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public long getDateDebutReserv(int numReserv) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ArrayList<Accreditation> getReservationAnnulee(int numUtilisateur, int typeUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean besoinDupdateOuNonAssurance(int numEleve, int numSemaine, String periode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getCategorieReservation(int numMoniteur, int numSemaine, String periode) {
		// TODO Auto-generated method stub
		return null;
	}


}