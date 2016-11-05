package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Personne;

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
			pst.close();

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
			System.out.println("Personne supprimée");
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

	public boolean update(Personne obj) { return false; }
	public Personne find(int id) { return null; } 
	public ArrayList<Personne> getList() { return null; } 

	@Override public String calculerPlaceCours(int numCours, int numSemaine) { return -1 + ""; }
	@Override public ArrayList<Personne> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Personne> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode) { return null; }
	@Override public ArrayList<Personne> getListCoursParticulierSelonId(int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Personne> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode, int cours) { return null; }
	@Override public ArrayList<Personne> getMyList(int idPersonne) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public ArrayList<Personne> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<Personne> getListDispo(int numSemaine) { return null; }
}
