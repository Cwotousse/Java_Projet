package be.mousty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.mousty.pojo.Personne;

public class PersonneDAO  extends DAO<Personne> {
	public PersonneDAO(Connection conn) { super(conn); }

	public int create(Personne obj) {
		PreparedStatement pst = null;
		try {
			String requete = "INSERT INTO Personne (nom, prenom, adresse, dateNaissance, sexe) VALUES (?,?,?,?,?)";
			pst = connect.prepareStatement(requete);

			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getPre());
			pst.setString(3, obj.getAdresse());
			pst.setDate(4, obj.getDateNaissance());
			pst.setString(5, obj.getSexe());

			pst.executeUpdate();

			PreparedStatement rechNumPersPst;
			String sql = "SELECT MAX(numPersonne) FROM Personne";
			rechNumPersPst = this.connect.prepareStatement(sql);
			ResultSet rs = rechNumPersPst.executeQuery();
			while (rs.next()) {
				obj.setNumPersonne(rs.getInt(1));
			}
			System.out.println("personneDao -> " + obj.getNumPersonne());
			return obj.getNumPersonne();
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return -1;
	}

	public boolean delete(Personne obj) {
		PreparedStatement pst = null;
		try {
			String requete = "DELETE FROM Personne WHERE numPersonne = (SELECT MAX(numPersonne) FROM Personne);";
			pst = connect.prepareStatement(requete);
			pst.executeUpdate();
			pst.close();
			System.out.println("Personne supprim�e");
			return true;
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return false;
	}
	
	
	public Personne getId (Personne obj) {
		PreparedStatement pst = null;
		Personne P = new Personne ();
		P.setNumPersonne(-1);
		//int id = -1;
		try {
			String sql = "SELECT * FROM Personne WHERE nom = ? AND prenom = ? AND adresse = ? ;";
			pst = this.connect.prepareStatement(sql);
			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getPre());
			pst.setString(3, obj.getAdresse());
			ResultSet res_Rec_Sem = pst.executeQuery();
			while (res_Rec_Sem.next()) {
				P.setNumPersonne(res_Rec_Sem.getInt("numPersonne"));
				P.setNom(res_Rec_Sem.getString("nom"));
				P.setPre(res_Rec_Sem.getString("prenom"));
				P.setDateNaissance(res_Rec_Sem.getDate("dateNaissance"));
				P.setAdresse(res_Rec_Sem.getString("adresse"));
				P.setSexe(res_Rec_Sem.getString("sexe"));
				}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return P;
	}

	public boolean update(Personne obj) { return false; }
	public Personne find(int id) { return null; } 
	public ArrayList<Personne> getList() { return null; }

	@Override
	public ArrayList<Personne> getListSelonCriteres(Personne obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Personne> getMyListSelonID(int id1, long id2, int id3, String str1) {
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
	public ArrayList<Personne> getReservationAnnulee(int numUtilisateur, int typeUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	} 

}
