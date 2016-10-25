package DAO;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Moniteur;
import POJO.Personne;
import POJO.Utilisateur;

public class MoniteurDAO extends DAO<Moniteur>{

	public MoniteurDAO(Connection conn) { super(conn); }

	public boolean create(Moniteur obj) {
		try {
			/*String requete = "INSERT INTO Personne (nom, prenom, adresse, dateNaissance, sexe) VALUES (?,?,?,?,?)";
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
			pst2.close();*/
			AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
			DAO<Personne> PersonneDao = adf.getPersonneDAO();
			DAO<Utilisateur> UtilisateurDao = adf.getUtilisateurDAO();
			
			if (PersonneDao.create(new Personne(obj.getNom(), obj.getPre(), obj.getAdresse(), obj.getSexe(), obj.getDateNaissance()))){

				
				if(UtilisateurDao.create(new Utilisateur(obj.getPseudo(), obj.getMdp(), obj.getTypeUtilisateur()))){

					String sql0 = "SELECT MAX(numUtilisateur) from Utilisateur";
					PreparedStatement pst0 = this.connect.prepareStatement(sql0);
					ResultSet rs0 = pst0.executeQuery();
					int numUtilisateur = -1 ;
					while (rs0.next()) numUtilisateur = rs0.getInt(1); // On a l'id de l'utilisateur

					//on l'utilise pour ajouter les donn�es dans la table Moniteur
					String requete3 = "INSERT INTO Moniteur (anneeDexp, numUtilisateur) VALUES (?, ?)";
					PreparedStatement pst = connect.prepareStatement(requete3);

					//pst.setInt(1, numMoniteur);     //L'id qui lie la table moniteur a la table utilisateur
					pst.setInt(1, 0); // obj.getAnneeExp()
					pst.setInt(2, numUtilisateur);
					pst.executeUpdate();
					pst.close();

					// On lui ajoute les accr�ditations
					String sql = "SELECT numMoniteur from Moniteur ORDER BY numMoniteur DESC LIMIT 1";
					pst = this.connect.prepareStatement(sql);
					ResultSet rs = pst.executeQuery();
					int numMoniteur = -1 ;
					while (rs.next()) numMoniteur = rs.getInt(1); // On a l'id du moniteur

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

						//pst2.setInt(1, numUtilisateur);     //L'id qui lie la table moniteur a la table personne
						pst4.setInt(1, numMoniteur);
						pst4.setInt(2, numAccred);
						pst4.setDate(3, now);

						pst4.executeUpdate();
						pst4.close();
					}
					System.out.println("Ajout d'un moniteur effectue");
					return true;
				} else {
					PersonneDao.delete(null);
					return false;
					} // utilisateur
			} else { return false; } // personne
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
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
	public  int verifPseudoMdp(Utilisateur obj){
		// TODO Auto-generated method stub
		return -1;
	}
}
