package be.mousty.dao;

import java.sql.Connection;
import java.util.Date;

import be.mousty.pojo.Accreditation;
import be.mousty.pojo.Moniteur;
import be.mousty.pojo.Personne;
import be.mousty.pojo.Utilisateur;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MoniteurDAO extends DAO<Moniteur>{
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Utilisateur> UtilisateurDAO = adf.getUtilisateurDAO();
	DAO<Personne> PersonneDao = adf.getPersonneDAO();
	public MoniteurDAO(Connection conn) { super(conn); }

	public int create(Moniteur obj) {
		PreparedStatement pst = null;
		PreparedStatement pst_accred = null;
		try {
			Personne P = new Personne();
			//P.setNumPersonne(res_Rec_Util.getInt("numPersonne"));
			P.setNom(obj.getNom());
			P.setPre( obj.getPre());
			P.setDateNaissance(obj.getDateNaissance());
			P.setAdresse(obj.getAdresse());
			P.setSexe(obj.getSexe());
			int numPersonne = PersonneDao.create(P);
			if (numPersonne != -1){
				Utilisateur U = new Utilisateur();
				U.setNumUtilisateur(numPersonne);
				U.setPseudo(obj.getPseudo());
				U.setMdp(obj.getMdp());
				U.setTypeUtilisateur( obj.getTypeUtilisateur());
				/*U.setNumPersonne(numPersonne);
				U.setNom(res_Rec_Util.getString("nom"));
				U.setPre(res_Rec_Util.getString("prenom"));
				U.setDateNaissance(res_Rec_Util.getDate("dateNaissance"));
				U.setAdresse(res_Rec_Util.getString("adresse"));
				U.setSexe(res_Rec_Util.getString("sexe"));*/
				if(UtilisateurDAO.create(U) != -1){
					//on l'utilise pour ajouter les donn�es dans la table Moniteur
					String requete3 = "INSERT INTO Moniteur (anneeDexp, numMoniteur) VALUES (?, ?)";
					pst = connect.prepareStatement(requete3);

					pst.setInt(1, 0); // obj.getAnneeExp()
					pst.setInt(2, numPersonne);
					pst.executeUpdate();

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
						pst_accred = connect.prepareStatement(requete4);

						pst_accred.setInt(1, numPersonne);
						pst_accred.setInt(2, numAccred);
						pst_accred.setDate(3, now);

						pst_accred.executeUpdate();
					}
					System.out.println("Ajout d'un moniteur effectue");
					return numPersonne;
				}
				else {
					PersonneDao.delete(P);
					return -1;
				} // utilisateur
			} else { return -1; } // personne
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null || pst_accred != null) {
				try { pst.close(); pst_accred.close();}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return -1;
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
			ResultSet res_mon = pst.executeQuery();
			while (res_mon.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();
				String sqlAccred = "Select * from Accreditation "
						+ "INNER JOIN LigneAccreditation ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation "
						+ "WHERE numMoniteur = ?;";
				pstAccred = this.connect.prepareStatement(sqlAccred);
				pstAccred.setInt(1, id);
				ResultSet rsAccred = pstAccred.executeQuery();
				while(rsAccred.next()){
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rsAccred.getString("nomAccreditation"));
					A.setNumAccreditation(rsAccred.getInt("numAccreditation"));
					listeAccred.add(A);
				}
				Moniteur M = new Moniteur();
				M.setNumMoniteur(res_mon.getInt("numMoniteur"));
				M.setAnneeExp(0);
				M.setAccrediList(listeAccred);
				M.setNumUtilisateur(res_mon.getInt("numUtilisateur"));
				M.setPseudo(res_mon.getString("pseudo"));
				M.setMdp(res_mon.getString("mdp"));
				M.setTypeUtilisateur(res_mon.getInt("typeUtilisateur"));
				M.setNumPersonne(res_mon.getInt("numPersonne"));
				M.setNom(res_mon.getString("nom"));
				M.setPre(res_mon.getString("prenom"));
				M.setDateNaissance(res_mon.getDate("dateNaissance"));
				M.setAdresse(res_mon.getString("adresse"));
				M.setSexe(res_mon.getString("sexe"));
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
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rsAccred.getString("nomAccreditation"));
					A.setNumAccreditation(rsAccred.getInt("numAccreditation"));
					listeAccred.add(A);
				}
				Moniteur M = new Moniteur();
				M.setNumMoniteur(res_mon.getInt("numMoniteur"));
				M.setAnneeExp(0);
				M.setAccrediList(listeAccred);
				M.setNumUtilisateur(res_mon.getInt("numUtilisateur"));
				M.setPseudo(res_mon.getString("pseudo"));
				M.setMdp(res_mon.getString("mdp"));
				M.setTypeUtilisateur(res_mon.getInt("typeUtilisateur"));
				M.setNumPersonne(res_mon.getInt("numPersonne"));
				M.setNom(res_mon.getString("nom"));
				M.setPre(res_mon.getString("prenom"));
				M.setDateNaissance(res_mon.getDate("dateNaissance"));
				M.setAdresse(res_mon.getString("adresse"));
				M.setSexe(res_mon.getString("sexe"));
				liste.add(M);
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
	
	public ArrayList<Moniteur> getListDispo(int numSemaine, String periode) {
		ArrayList<Moniteur> liste = new ArrayList<Moniteur>();
		PreparedStatement pst_mon = null;
		PreparedStatement pstAccred = null;
		try {
			String verifPeriode;
			switch(periode){
				case "09-12" : verifPeriode = " = '09-12'";
					break;
				case "14-17" : verifPeriode = " = '14-17'";
					break;
				case "12-13": verifPeriode = " IN('12-14','12-13') ";
					break;
				case "13-14": verifPeriode = " IN('12-14','13-14') ";
					break;
				case "12-14": verifPeriode = " IN('12-13', '13-14', '12-14') ";
					break;
				default : verifPeriode = " = ? ";
					break;
			}
			String sql_mon =
					"SELECT distinct * FROM Moniteur "
					+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
					+ "INNER JOIN DisponibiliteMoniteur ON DisponibiliteMoniteur.numMoniteur = Utilisateur.numUtilisateur "
					+ "INNER JOIN Personne ON Personne.numPersonne = Moniteur.numMoniteur "
							+ "WHERE disponible = 1 "
							+ "AND numSemaine = ? "
							+ "AND Moniteur.numMoniteur NOT IN "
							+ "( SELECT numMoniteur FROM CoursMoniteur WHERE numCours IN "
							+ "( SELECT Cours.numCours FROM COURS "
							+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
							+ "WHERE periodeCours " + verifPeriode + "))"
									+ "OR  DisponibiliteMoniteur.numMoniteur IN "
           + "(SELECT CoursMoniteur.numMoniteur FROM CoursMoniteur WHERE  numCours IN "
             + "(SELECT numCours FROM Cours WHERE numSemaine = ? AND disponible = 1 AND periodeCours "+ verifPeriode +" ));";
			pst_mon = this.connect.prepareStatement(sql_mon);
			pst_mon.setInt(1, numSemaine);
			//pst_mon.setString(2, periode);
			pst_mon.setInt(2, numSemaine);
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
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rsAccred.getString("nomAccreditation"));
					A.setNumAccreditation(rsAccred.getInt("numAccreditation"));
					listeAccred.add(A);
				}
				Moniteur M = new Moniteur();
				M.setNumMoniteur(res_mon.getInt("numMoniteur"));
				M.setAnneeExp(0);
				M.setAccrediList(listeAccred);
				M.setNumUtilisateur(res_mon.getInt("numUtilisateur"));
				M.setPseudo(res_mon.getString("pseudo"));
				M.setMdp(res_mon.getString("mdp"));
				M.setTypeUtilisateur(res_mon.getInt("typeUtilisateur"));
				M.setNumPersonne(res_mon.getInt("numPersonne"));
				M.setNom(res_mon.getString("nom"));
				M.setPre(res_mon.getString("prenom"));
				M.setDateNaissance(res_mon.getDate("dateNaissance"));
				M.setAdresse(res_mon.getString("adresse"));
				M.setSexe(res_mon.getString("sexe"));
				liste.add(M);
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
	
	@Override
	public int getNumPersonne(String nom, String pre, String adr) {
		PreparedStatement pst = null;
		int id = -1;
		try {
			String sql = "SELECT numPersonne FROM Personne WHERE nom = ? AND prenom = ? AND adresse = ? ;";
			pst = this.connect.prepareStatement(sql);
			pst.setString(1, nom);
			pst.setString(2, pre);
			pst.setString(3, adr);
			ResultSet res_Rec_Accr = pst.executeQuery();
			while (res_Rec_Accr.next()) { id = res_Rec_Accr.getInt("numPersonne"); }
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return id;
	}

	@Override public String calculerPlaceCours(int numCours, int numSemaine, int numMoniteur) { return -1 + ""; }
	@Override public ArrayList<Moniteur> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Moniteur> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Moniteur> getListCoursParticulierSelonId(int numMoniteur, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Moniteur> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Moniteur> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Moniteur> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public Moniteur returnUser(String mdp, String pseudo) { return null; }
	@Override public int valeurReduction(int numSem) { return 0; }

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
	public ArrayList<Moniteur> getListSemaineSelonDateDuJour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumSemaine(java.sql.Date dateDebut) {
		// TODO Auto-generated method stub
		return 0;
	}
}
