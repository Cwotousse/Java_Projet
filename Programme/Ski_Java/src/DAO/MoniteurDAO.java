package DAO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Moniteur;

public class MoniteurDAO extends DAO<Moniteur>{

	public MoniteurDAO(Connection conn) { super(conn); }

	public boolean create(Moniteur obj) {
		//On ajoute les donn�es n�cessaires dans la table personne
		Date now = new Date();
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

			//on l'utilise pour ajouter les donn�es dans la table Utilisateur
			String requete2 = "INSERT INTO Utilisateur (pseudo, mdp, typeUtilisateur) VALUES (?,?,?)";
			PreparedStatement pst2 = connect.prepareStatement(requete2);

			//pst2.setInt(1, numUtilisateur);     //L'id qui lie la table moniteur a la table personne
			pst2.setString(1, obj.getPseudo());
			pst2.setString(2, obj.getMdp());
			pst2.setInt(3, obj.getTypeUtilisateur());

			pst2.executeUpdate();
			pst2.close();

			//on l'utilise pour ajouter les donn�es dans la table Moniteur
			String requete3 = "INSERT INTO Moniteur (anneeDexp) VALUES (?)";
			PreparedStatement pst3 = connect.prepareStatement(requete3);

			//pst3.setInt(1, numMoniteur);     //L'id qui lie la table moniteur a la table utilisateur
			pst3.setInt(1, 0); // obj.getAnneeExp()
			pst3.executeUpdate();
			pst3.close();
			
			// On lui ajoute les accr�ditations
			String sql = "SELECT numMoniteur from Moniteur ORDER BY numMoniteur DESC LIMIT 1";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			int numMoniteur = -1 ;
			while (rs.next()) numMoniteur = rs.getInt(1); // On a l'id du moniteur
			
			
			String requete4 = "INSERT INTO LigneAccreditation (numMoniteur, numAccreditation, dateAccreditation) VALUES (?,?,?)";
			PreparedStatement pst4 = connect.prepareStatement(requete4);

			//pst2.setInt(1, numUtilisateur);     //L'id qui lie la table moniteur a la table personne
			pst4.setInt(1, numMoniteur);
			pst4.setInt(2, 3);
			pst4.setDate(3, (java.sql.Date) now);

			pst4.executeUpdate();
			pst4.close();

			System.out.println("Ajout d'un moniteur effectue");
		} 
		catch (SQLException e) {
			System.out.println(obj.getDateNaissance());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean delete(Moniteur obj) {
		return false;
	}

	public boolean update(Moniteur obj) {
		return false;
	}

	// On cherche une Moniteur gr�ce � son id
	public Moniteur find(int id) {
		Moniteur moniteur = new Moniteur();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Moniteur WHERE numMoniteur = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				moniteur.setNumUtilisateur(rs.getInt("numUtilisateur"));
				moniteur.setNumMoniteur(rs.getInt("numMoniteur"));
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
		return moniteur;
	}



	public ArrayList<Moniteur> getList() {
		Moniteur moniteur = null;
		ArrayList<Moniteur> liste = new ArrayList<Moniteur>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Moniteur";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				moniteur.setNumUtilisateur(rs.getInt("numUtilisateur"));
				moniteur.setNumMoniteur(rs.getInt("numMoniteur"));
				liste.add(moniteur);
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
	public Moniteur verifPseudoMdp(String text, String text2) {
		// TODO Auto-generated method stub
		return null;
	}
}
