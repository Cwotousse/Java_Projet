package be.mousty.dao;
/**
	Classe DAO permettant à effectuer des requêtes et les transformer en objet POJO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category DAO
*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.mousty.pojo.Utilisateur;
public class UtilisateurDAO extends DAO<Utilisateur> {
	public UtilisateurDAO(Connection conn) { super(conn); }

	/**
		Objectif : Méthode permettant de créer un élément dans la DB.
		@version Finale 1.3.3
		@param Une instance de l'objet nécéssaire à la création.
		@return L'ID de l'enregistrement créé dans la DB.
	 */
	public int create(Utilisateur obj) { 
		PreparedStatement pst2 = null;
		try
		{
			// Vérifier si la personne existe déjà (username/mdp)
			Utilisateur U = getId(obj);
			System.out.println(U.getNumPersonne());
			if(U.getNumPersonne() != -1 || U.getNumPersonne() != 0) { return -1; }
			else {
				//on l'utilise pour ajouter les données dans la table Utilisateur
				String requete2 = "INSERT INTO Utilisateur (pseudo, mdp, typeUtilisateur, numUtilisateur) VALUES (?,?,?,?)";
				pst2 = connect.prepareStatement(requete2);

				//pst2.setInt(1, numUtilisateur);     //L'id qui lie la table moniteur a la table personne
				pst2.setString(1, obj.getPseudo());
				pst2.setString(2, obj.getMdp());
				pst2.setInt(3, obj.getTypeUtilisateur());
				pst2.setInt(4, obj.getNumUtilisateur());

				pst2.executeUpdate();
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

	/**
		Objectif : Retourner un enregistrement de la DB par rapport à sa clé primaire.
		@version Finale 1.3.3
		@param la valeur de la clé primaire.
		@return Une instance de l'objet initialisée avec les valeurs issue de la DB.
	 */
	@Override public Utilisateur find(int id) {
		// On cherche un élève grâce à son id
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

	/**
		Objectif : Retourner la liste complète des enregistrements contenu dans une table
		@version Finale 1.3.3
		@return La liste complète des utilisateurs.
	 */
	public ArrayList<Utilisateur> getList() {
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
	
	/**
		Objectif : Récupérer un instance d'un objet complètement initialisée correspondant aux valeurs entrées en paramètre.
		@version Finale 1.3.3
		@param Des valeurs insérées dans un objet permettant d'identifier une seule personne dans la DB.
		@return instance d'un objet complètement initialisée correspondant aux valeurs entrées en paramètre.
	 */
	@Override public Utilisateur getId(Utilisateur obj) {
			Utilisateur U = new Utilisateur();
			PreparedStatement pst = null;
			try {
				String sql = "SELECT * FROM Utilisateur INNER JOIN PERSONNE ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE mdp = ? AND pseudo = ? ;";
				pst = this.connect.prepareStatement(sql);
				pst.setString(1, obj.getMdp());
				pst.setString(2, obj.getPseudo());
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

	@Override 
	public ArrayList<Utilisateur> getListSelonCriteres(Utilisateur obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Utilisateur> getMyListSelonID(int id1, long id2, int id3, String str1) {
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
	public ArrayList<Utilisateur> getReservationAnnulee(int numUtilisateur, int typeUtilisateur) {
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
