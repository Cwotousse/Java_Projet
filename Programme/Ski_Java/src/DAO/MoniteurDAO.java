package DAO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Accreditation;
import POJO.Moniteur;
import POJO.Personne;
import POJO.Utilisateur;

public class MoniteurDAO extends DAO<Moniteur>{

	public MoniteurDAO(Connection conn) { super(conn); }

	public int create(Moniteur obj) {
		try {
			Personne P = new Personne(-1,obj.getNom(), obj.getPre(), obj.getAdresse(), obj.getSexe(), obj.getDateNaissance());
			int numPersonne = P.createPersonne();
			if (numPersonne != -1){
				Utilisateur U = new Utilisateur(numPersonne, obj.getPseudo(), obj.getMdp(), obj.getTypeUtilisateur());
				if(U.createUtilisateur() != -1){
					//on l'utilise pour ajouter les donn�es dans la table Moniteur
					String requete3 = "INSERT INTO Moniteur (anneeDexp, numMoniteur) VALUES (?, ?)";
					PreparedStatement pst = connect.prepareStatement(requete3);

					pst.setInt(1, 0); // obj.getAnneeExp()
					pst.setInt(2, numPersonne);
					pst.executeUpdate();
					pst.close();

					// On lui ajoute les accr�ditations
					java.util.Date ud = new Date();
					java.sql.Date now = new java.sql.Date(ud.getTime());

					for(int i = 0; i < obj.getAccrediList().size(); i++){
						String sqlAccred = "SELECT numAccreditation from Accreditation WHERE nomAccreditation = ? ";
						pst = this.connect.prepareStatement(sqlAccred);
						pst.setString(1, obj.getAccrediList().get(i).toString()); // Nom de l'accr�ditation
						ResultSet rsAccred = pst.executeQuery();
						int numAccred = -1 ;
						while (rsAccred.next()) numAccred = rsAccred.getInt(1); // On a l'id du moniteur

						String requete4 = "INSERT INTO LigneAccreditation (numMoniteur, numAccreditation, dateAccreditation) VALUES (?,?,?)";
						PreparedStatement pst4 = connect.prepareStatement(requete4);

						pst4.setInt(1, numPersonne);
						pst4.setInt(2, numAccred);
						pst4.setDate(3, now);

						pst4.executeUpdate();
						pst4.close();
					}
					System.out.println("Ajout d'un moniteur effectue");
					return numPersonne;
				} else {
					P.deletePersonne();
					return -1;
				} // utilisateur
			} else { return -1; } // personne
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}

	public boolean delete(Moniteur obj) {
		return false;
	}

	public boolean update(Moniteur obj) {
		return false;
	}

	// On cherche une Moniteur gr�ce � son id
	public Moniteur find(int id) {
		/*Moniteur moniteur = new Moniteur();
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
		return moniteur;*/
		//Moniteur liste = new Moniteur>();
		Moniteur moniteur = null;
		PreparedStatement pst = null;
		PreparedStatement pstAccred = null;
		try {
			String sql = "SELECT * FROM Moniteur "
					+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
					+ "INNER JOIN Personne ON Utilisateur.numUtilisateur = Personne.numPersonne "
					+ "WHERE numPersonne = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();
				String sqlAccred = "Select * from Accreditation "
						+ "INNER JOIN LigneAccreditation ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation "
						+ "WHERE numMoniteur = ?;";
				pstAccred = this.connect.prepareStatement(sqlAccred);
				pstAccred.setInt(1, id);
				ResultSet rsAccred = pstAccred.executeQuery();
				while(rsAccred.next()){
					Accreditation a = new Accreditation(rsAccred.getString("nomAccreditation"));
					listeAccred.add(a);
				}
				moniteur = new Moniteur(rs.getInt("numMoniteur"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("adresse"), rs.getString("sexe"), rs.getDate("dateNaissance"), rs.getString("pseudo"), rs.getString("mdp"),
						rs.getInt("typeUtilisateur"), listeAccred);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (pst != null) {
				try {
					pst.close();
					pstAccred.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return moniteur;
	}



	public ArrayList<Moniteur> getList() {
		ArrayList<Moniteur> liste = new ArrayList<Moniteur>();
		PreparedStatement pst = null;
		PreparedStatement pstAccred = null;
		try {
			String sql = "SELECT * FROM Moniteur "
					+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
					+ "INNER JOIN Personne ON Utilisateur.numUtilisateur = Personne.numPersonne";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();
				String sqlAccred = "Select * from Accreditation "
						+ "INNER JOIN LigneAccreditation ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation "
						+ "WHERE numMoniteur = ?;";
				pstAccred = this.connect.prepareStatement(sqlAccred);
				pstAccred.setInt(1, rs.getInt("numMoniteur"));
				ResultSet rsAccred = pstAccred.executeQuery();
				while(rsAccred.next()){
					Accreditation a = new Accreditation(rsAccred.getString("nomAccreditation"));
					listeAccred.add(a);
				}
				Moniteur moniteur = new Moniteur(rs.getInt("numMoniteur"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("adresse"), rs.getString("sexe"), rs.getDate("dateNaissance"), rs.getString("pseudo"), rs.getString("mdp"),
						rs.getInt("typeUtilisateur"), listeAccred);
				liste.add(moniteur);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (pst != null) {
				try {
					pst.close();
					pstAccred.close();
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
