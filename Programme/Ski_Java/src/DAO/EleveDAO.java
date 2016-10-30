package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import POJO.Client;
import POJO.Eleve;
import POJO.Personne;
import POJO.Utilisateur;

public class EleveDAO extends DAO<Eleve> {
	public EleveDAO(Connection conn) {
		super(conn);
	}

	public int create(Eleve obj) {
		try {
			System.out.println("EleveDao -> " + obj.getNumPersonne());
			int numPersonne = obj.getNumPersonne();
			//Vérification que la personne n'est pas encore inscrite en tant qu'élève.
			Eleve e = find(numPersonne);
			// La personne n'existe pas
			if (e == null || numPersonne == -1){
				Personne P = new Personne(-1,obj.getNom(), obj.getPre(), obj.getAdresse(), obj.getSexe(), obj.getDateNaissance());
				numPersonne = P.createPersonne();
				if (numPersonne == -1){
					P.deletePersonne();
					return -1;
				}
			}

			String requete5 = "INSERT INTO Eleve (aPrisUneAssurance, categorie, numEleve) VALUES (?,?,?)";
			PreparedStatement pst5 = connect.prepareStatement(requete5);

			pst5.setBoolean(1, obj.getAUneAssurance());
			pst5.setString(2, obj.getCategorie());
			pst5.setInt(3, numPersonne);
			pst5.executeUpdate();
			pst5.close();
			System.out.println("Ajout d'un eleve effectue");
			return numPersonne;

		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	public boolean delete(Eleve obj) {
		return false;
	}

	public boolean update(Eleve obj) {
		return false;
	}

	// On cherche une Eleve grâce à son id
	public Eleve find(int id) {
		Eleve eleve = new Eleve();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Eleve INNER JOIN Personne ON Eleve.numEleve = Personne.numPersonne WHERE numEleve = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			// int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
			while (result.next()) {
				eleve = new Eleve(result.getInt("numClient"), result.getString("nom"), result.getString("prenom"), result.getString("adresse"), 
						result.getString("sexe"), result.getDate("dateNaissance"));
			}
			return eleve;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}



	public ArrayList<Eleve> getList() {

		ArrayList<Eleve> liste = new ArrayList<Eleve>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Eleve INNER JOIN Personne On Personne.numPersonne = Eleve.numEleve";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
				Eleve eleve = new Eleve(rs.getInt("numEleve"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("sexe"), rs.getDate("dateNaissance"));
				//				eleve.setNumEleve(rs.getInt("numEleve"));
				//				eleve.setNom(rs.getString("nom"));
				//				eleve.setPre(rs.getString("prenom"));
				//				eleve.setDateNaissance(rs.getDate("dateNaissance"));
				//				eleve.setSexe(rs.getString("sexe"));
				//				eleve.setAdresse(rs.getString("adresse"));
				liste.add(eleve);
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

	/*@Override
	public  int verifPseudoMdp(Utilisateur obj){
		// TODO Auto-generated method stub
		return -1;
	}*/
}