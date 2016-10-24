package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import POJO.Client;

public class ClientDAO extends DAO<Client> {
	public ClientDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Client obj) {
		//		try {
		//			String requete = 
		//					"SELECT numClient FROM  Client " + 
		//					"INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Client.numUtilisateur " + 
		//					"WHERE Utilisateur.pseudo = '" + obj.getPseudo() + "' AND Utilisateur.mdp = '" + obj.getMdp()  +"';";
		//			 
		//			 Statement stmt = connect.createStatement();
		//
		//			// 5.2 Execution de l'insert into 
		//			 ResultSet find = stmt.executeQuery(requete);
		//			if (!find.next()){
		//				String sql = "INSERT INTO Client " + "(numUtilisateur) " + " VALUES(?)";
		//				PreparedStatement pst = this.connect.prepareStatement(sql);
		//				pst.setInt(1, obj.getNumUtilisateur());
		//				
		//				pst.executeUpdate();
		//			pst.close();
		//			}
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		}
		//
		//		return false;
		//	}
		//On ajoute les donn�es n�cessaires dans la table personne
		try {
			String requete = "INSERT INTO Personne (nom, prenom, adresse, dateNaissance, sexe) VALUES (?,?,?,?,?)";
			PreparedStatement pst = connect.prepareStatement(requete);

			pst.setString(1, obj.getNom());
			pst.setString(2, obj.getPre());
			pst.setString(3, obj.getAdresse());
			pst.setDate(4, obj.getDateNaissance());
			pst.setString(5, obj.getSexe());

			pst.executeUpdate();
			pst.close();

			//on r�cup�re l'id cr�e en dernier dans la table
			//String requete2 = "SELECT MAX(numPersonne) FROM Personne";
//			PreparedStatement pst2 = connect.prepareStatement(requete2);
//			ResultSet resultat = pst2.executeQuery();
//			pst2.close();
//			int numUtilisateur = -1;
//			if(resultat.next()){
//				numUtilisateur = resultat.getInt(1);
				//on l'utilise pour ajouter les donn�es dans la table Utilisateur
				String requete3 = "INSERT INTO Utilisateur (pseudo, mdp, typeUtilisateur) VALUES (?,?,?)";
				PreparedStatement pst3 = connect.prepareStatement(requete3);

				//pst3.setInt(1, numUtilisateur);     //L'id qui lie la table client a la table personne
				pst3.setString(1, obj.getPseudo());
				pst3.setString(2, obj.getMdp());
				pst3.setInt(3, obj.getTypeUtilisateur());

				pst3.executeUpdate();
				pst3.close();

				//on r�cup�re l'id cr�e en dernier dans la table
//				String requete4 = "SELECT MAX(numUtilisateur) FROM Utilisateur";
//				PreparedStatement pst4 = connect.prepareStatement(requete4);
//				ResultSet resultat4 = pst4.executeQuery();
//				pst4.close();
//				int numClient = -1;
//				if(resultat4.next()){
//					numUtilisateur = resultat.getInt(1);
					//on l'utilise pour ajouter les donn�es dans la table Utilisateur
					String requete5 = "INSERT INTO Client (adresseFacturation) VALUES (?)";
					PreparedStatement pst5 = connect.prepareStatement(requete5);

					//pst5.setInt(1, numClient);     //L'id qui lie la table client a la table utilisateur
					pst5.setString(1, obj.getAdresseFacturation());
					pst5.executeUpdate();
					pst5.close();
					System.out.println("Ajout d'un client effectue");
				//}
				//else { System.out.println("Quitte ajout 2"); return false; } 
				
			//}
			// { System.out.println("Quitte ajout 1"); return false; }
		} 
		catch (SQLException e) {
			System.out.println(obj.getDateNaissance());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Client obj) {
		return false;
	}

	public boolean update(Client obj) {
		return false;
	}

	// On cherche une Client gr�ce � son id
	public Client find(int id) {
		Client client = new Client();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Client WHERE numClient = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				client.setNumUtilisateur(rs.getInt("numUtilisateur"));
				client.setNumClient(rs.getInt("numClient"));
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
		return client;
	}



	public ArrayList<Client> getList() {
		Client client = null;
		ArrayList<Client> liste = new ArrayList<Client>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Client";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				client.setNumUtilisateur(rs.getInt("numUtilisateur"));
				client.setNumClient(rs.getInt("numClient"));
				liste.add(client);
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
		return liste;
	}

	@Override
	public Client verifPseudoMdp(String text, String text2) {
		// TODO Auto-generated method stub
		return null;
	}
}