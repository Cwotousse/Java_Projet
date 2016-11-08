package be.mousty.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.mousty.pojo.Utilisateur;
public class UtilisateurDAO extends DAO<Utilisateur> {
	public UtilisateurDAO(Connection conn) {
		super(conn);
	}

	public int create(Utilisateur obj) { 
		PreparedStatement pst2 = null;
		try
		{
			// V�rifier si la personne existe d�j� (username/mdp)
			if(find(obj.getNumUtilisateur()) != null){ return -1; }
			else {
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
				//pst2.close();
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
		Utilisateur U = new Utilisateur();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Utilisateur INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur";
			pst = this.connect.prepareStatement(sql);
			ResultSet res_Rec_Util = pst.executeQuery();
			while (res_Rec_Util.next()) {
				U.setNumUtilisateur(res_Rec_Util.getInt("numUtilisateur"));
				U.setPseudo(res_Rec_Util.getString("pseudo"));
				U.setMdp(res_Rec_Util.getString("mdp"));
				U.setTypeUtilisateur(res_Rec_Util.getInt("typeUtilisateur"));
				U.setNumPersonne(res_Rec_Util.getInt("numPersonne"));
				U.setNom(res_Rec_Util.getString("nom"));
				U.setPre(res_Rec_Util.getString("prenom"));
				U.setDateNaissance(res_Rec_Util.getDate("dateNaissance"));
				U.setAdresse(res_Rec_Util.getString("adresse"));
				U.setSexe(res_Rec_Util.getString("sexe"));
				liste.add(U);
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
	
	public Utilisateur returnUser(String mdp, String pseudo){
			Utilisateur U = new Utilisateur();
			PreparedStatement pst = null;
			try {
				String sql = "SELECT * FROM Utilisateur INNER JOIN PERSONNE ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE mdp = ? AND pseudo = ? ;";
				pst = this.connect.prepareStatement(sql);
				pst.setString(1, mdp);
				pst.setString(2, pseudo);
				ResultSet res_Rec_Util = pst.executeQuery();
				while (res_Rec_Util.next()) {
					U.setNumUtilisateur(res_Rec_Util.getInt("numUtilisateur"));
					U.setPseudo(res_Rec_Util.getString("pseudo"));
					U.setMdp(res_Rec_Util.getString("mdp"));
					U.setTypeUtilisateur(res_Rec_Util.getInt("typeUtilisateur"));
					U.setNumPersonne(res_Rec_Util.getInt("numPersonne"));
					U.setNom(res_Rec_Util.getString("nom"));
					U.setPre(res_Rec_Util.getString("prenom"));
					U.setDateNaissance(res_Rec_Util.getDate("dateNaissance"));
					U.setAdresse(res_Rec_Util.getString("adresse"));
					U.setSexe(res_Rec_Util.getString("sexe"));
				}
			}
			catch (SQLException e) { e.printStackTrace(); }
			finally {
				if (pst != null) {
					try { pst.close(); }
					catch (SQLException e) { e.printStackTrace(); }
				}
			}
			return U;
	}
	
	@Override public String calculerPlaceCours(int numCours, int numSemaine, int numMoniteur) { return -1 + ""; }
	@Override public ArrayList<Utilisateur> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Utilisateur> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Utilisateur> getListCoursParticulierSelonId(int numMoniteur, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Utilisateur> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Utilisateur> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Utilisateur> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<Utilisateur> getListDispo(int numSemaine, String periode) { return null; }
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
	public ArrayList<Utilisateur> getListSemaineSelonDateDuJour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumSemaine(Date dateDebut) {
		// TODO Auto-generated method stub
		return 0;
	}
}
