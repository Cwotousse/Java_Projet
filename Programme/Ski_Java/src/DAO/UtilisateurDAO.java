package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Utilisateur;
public class UtilisateurDAO extends DAO<Utilisateur> {
	public UtilisateurDAO(Connection conn) {
		super(conn);
	}

	public int create(Utilisateur obj) { 
		PreparedStatement pst2 = null;
		try
		{
			// V�rifier si la personne existe d�j� (username/mdp)
			Utilisateur uTmp= find(obj.getTypeUtilisateur());
			//int numType = uTmp.getTypeUtilisateur();
			if(uTmp == null){ return -1; }
			else {
				/*String sql = "SELECT MAX(numPersonne) from Personne";
				PreparedStatement pst = this.connect.prepareStatement(sql);
				ResultSet rs = pst.executeQuery();
				int numPersonne = -1 ;
				while (rs.next()) numPersonne = rs.getInt(1); // On a l'id de la personne*/

				//on l'utilise pour ajouter les donn�es dans la table Utilisateur
				System.out.println("UtilisateurDao -> " + obj.getNumUtilisateur());
				String requete2 = "INSERT INTO Utilisateur (pseudo, mdp, typeUtilisateur, numUtilisateur) VALUES (?,?,?,?)";
				pst2 = connect.prepareStatement(requete2);

				//pst2.setInt(1, numUtilisateur);     //L'id qui lie la table moniteur a la table personne
				pst2.setString(1, obj.getPseudo());
				pst2.setString(2, obj.getMdp());
				pst2.setInt(3, obj.getTypeUtilisateur());
				pst2.setInt(4, obj.getNumUtilisateur());

				pst2.executeUpdate();
				pst2.close();
				//System.out.println("Ajout d'un moniteur effectue");
				System.out.println("UtilisateurDao -> " + obj.getNumUtilisateur());
				return obj.getNumUtilisateur();
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst2 != null) {
				try { pst2.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return -1;
	}
	public boolean delete(Utilisateur obj) { return false; }

	public boolean update(Utilisateur obj) { return false; }

	// On cherche un �l�ve gr�ce � son id
	public Utilisateur find(int id) {
		Utilisateur utilisateur = new Utilisateur();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM utilisateur WHERE numUtilisateur = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setMdp(rs.getString("mdp"));
				utilisateur.setTypeUtilisateur(rs.getInt("typeUtilisateur"));
			}
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

	public  ArrayList<Utilisateur> getList() {
		ArrayList<Utilisateur> liste = new ArrayList<Utilisateur>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Utilisateur INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Utilisateur utilisateur = new Utilisateur(rs.getInt("numUtilisateur"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"),
						rs.getString("sexe"), rs.getDate("dateNaissance"), rs.getString("pseudo"), rs.getString("mdp"), rs.getInt("typeUtilisateur"));
				liste.add(utilisateur);
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
	
	@Override public String calculerPlaceCours(int numCours, int numSemaine) { return -1 + ""; }
	@Override public ArrayList<Utilisateur> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Utilisateur> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode) { return null; }
	@Override public ArrayList<Utilisateur> getListCoursParticulierSelonId(int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Utilisateur> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode, int cours) { return null; }
	@Override public ArrayList<Utilisateur> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Utilisateur> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<Utilisateur> getListDispo(int numSemaine) { return null; }
}
