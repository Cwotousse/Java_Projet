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

	public boolean delete(Moniteur obj) { return false; }

	public boolean update(Moniteur obj) { return false; }

	// On cherche une Moniteur gr�ce � son id
	public Moniteur find(int id) {
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
					listeAccred.add(new Accreditation(rsAccred.getString("nomAccreditation")));
				}
				moniteur = new Moniteur(rs.getInt("numMoniteur"), rs.getString("nom"), rs.getString("prenom"),
						rs.getString("adresse"), rs.getString("sexe"), rs.getDate("dateNaissance"), rs.getString("pseudo"), rs.getString("mdp"),
						rs.getInt("typeUtilisateur"), listeAccred);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null || pstAccred != null) {
				try { pst.close(); pstAccred.close();}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return moniteur;
	}



	public ArrayList<Moniteur> getList() {
		ArrayList<Moniteur> liste = new ArrayList<Moniteur>();
		PreparedStatement pst_mon = null;
		PreparedStatement pstAccred = null;
		try {
			String sql_mon = "SELECT * FROM Moniteur "
					+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
					+ "INNER JOIN Personne ON Utilisateur.numUtilisateur = Personne.numPersonne";
			pst_mon = this.connect.prepareStatement(sql_mon);
			ResultSet res_mon = pst_mon.executeQuery();
			while (res_mon.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();
				String sqlAccred = "Select * from Accreditation "
						+ "INNER JOIN LigneAccreditation ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation "
						+ "WHERE numMoniteur = ?;";
				pstAccred = this.connect.prepareStatement(sqlAccred);
				pstAccred.setInt(1, res_mon.getInt("numMoniteur"));
				ResultSet rsAccred = pstAccred.executeQuery();
				while(rsAccred.next()){
					Accreditation a = new Accreditation(rsAccred.getString("nomAccreditation"));
					listeAccred.add(a);
				}
				Moniteur moniteur = new Moniteur(res_mon.getInt("numMoniteur"), res_mon.getString("nom"), res_mon.getString("prenom"),
						res_mon.getString("adresse"), res_mon.getString("sexe"), res_mon.getDate("dateNaissance"), res_mon.getString("pseudo"), res_mon.getString("mdp"),
						res_mon.getInt("typeUtilisateur"), listeAccred);
				liste.add(moniteur);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_mon != null || pstAccred != null) {
				try {
					pst_mon.close();
					pstAccred.close();
				}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return liste;
	}
	
	public ArrayList<Moniteur> getListDispo(int numSemaine) {
		ArrayList<Moniteur> liste = new ArrayList<Moniteur>();
		PreparedStatement pst_mon = null;
		PreparedStatement pstAccred = null;
		try {
			String sql_mon = "SELECT * FROM Moniteur "
					+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
					+ "INNER JOIN Personne ON Utilisateur.numUtilisateur = Personne.numPersonne "
					+ "INNER JOIN DisponibiliteMoniteur ON DisponibiliteMoniteur.numMoniteur = Personne.numPersonne "
					+ "WHERE numSemaine = ? AND disponible = 1 ";
			pst_mon = this.connect.prepareStatement(sql_mon);
			pst_mon.setInt(1, numSemaine);
			ResultSet res_mon = pst_mon.executeQuery();
			while (res_mon.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();
				String sqlAccred = "Select * from Accreditation "
						+ "INNER JOIN LigneAccreditation ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation "
						+ "WHERE numMoniteur = ?;";
				pstAccred = this.connect.prepareStatement(sqlAccred);
				pstAccred.setInt(1, res_mon.getInt("numMoniteur"));
				ResultSet rsAccred = pstAccred.executeQuery();
				while(rsAccred.next()){
					Accreditation a = new Accreditation(rsAccred.getString("nomAccreditation"));
					listeAccred.add(a);
				}
				Moniteur moniteur = new Moniteur(res_mon.getInt("numMoniteur"), res_mon.getString("nom"), res_mon.getString("prenom"),
						res_mon.getString("adresse"), res_mon.getString("sexe"), res_mon.getDate("dateNaissance"), res_mon.getString("pseudo"), res_mon.getString("mdp"),
						res_mon.getInt("typeUtilisateur"), listeAccred);
				liste.add(moniteur);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_mon != null || pstAccred != null) {
				try {
					pst_mon.close();
					pstAccred.close();
				}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return liste;
	}

	@Override public String calculerPlaceCours(int numCours, int numSemaine) { return -1 + ""; }
	@Override public ArrayList<Moniteur> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Moniteur> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode) { return null; }
	@Override public ArrayList<Moniteur> getListCoursParticulierSelonId(int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Moniteur> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode, int cours) { return null; }
	@Override public ArrayList<Moniteur> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Moniteur> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
}
