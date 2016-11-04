package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import POJO.Accreditation;
import POJO.Cours;
import POJO.CoursCollectif;
import POJO.CoursParticulier;
import POJO.Eleve;
import POJO.Moniteur;
import POJO.Reservation;

public class CoursDAO extends DAO<Cours> {
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Moniteur> MoniteurDAO = adf.getMoniteurDAO();
	
	public CoursDAO(Connection conn) {
		super(conn);
	}

	@Override
	public int create		(Cours obj) { return -1; }
	public boolean delete	(Cours obj) { return false; }
	public boolean update	(Cours obj) { return false; }

	// On cherche une Cours gr�ce � son id
	public Cours find(int id) {
		Cours cours = new Cours();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Cours WHERE numCours = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			while (result.next()) {
				cours = new Cours(result.getInt("numCours"), result.getString("nomSport"), result.getInt("prix"),
						result.getInt("minEleve"), result.getInt("maxEleve"),  result.getString("periodeCours"));
			}
			return cours;
		}
		catch (SQLException e) { e.printStackTrace(); return null; }
		finally { 
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
	}

	public ArrayList<Cours> getList() {
		ArrayList<Cours> liste = new ArrayList<Cours>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Cours";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				liste.add(new Cours(rs.getInt("numCours"), rs.getString("nomSport"), rs.getInt("prix"), rs.getInt("minEleve"), 
						rs.getInt("maxEleve"), rs.getString("periodeCours")));
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
	
	public ArrayList<Cours> getListCoursSelonId(int idMoniteur){
		/*ArrayList<Cours> listFull = getList(); 
		ArrayList<Cours> listSelonId = new ArrayList<Cours>();
		// new ArrayList<Accreditation>();
		Moniteur M = MoniteurDAO.find(idMoniteur);
		ArrayList<Accreditation> listAccred = M.getAccrediList(); // Liste des accreditations du moniteur
		for(Cours C : listFull){
			for(Accreditation A : listAccred){
				if(A.getNom().equals(C.getNomSport())){
					// Si l'accreditation du moniteur correspond � celle du cours propos�, on l'ajoute dans la liste tri�e
					listSelonId.add(C);
				}
			}
		}
		return listSelonId;*/
		PreparedStatement pst_rec_cou = null;
		ArrayList<Cours> listSelonId = new ArrayList<Cours>();
		try {

			String sql_rec_cou = "SELECT * FROM Cours WHERE nomSport IN "
					+ "( SELECT nomAccreditation FROM Accreditation WHERE numAccreditation IN "
					+ "( SELECT numAccreditation FROM LigneAccreditation WHERE numMoniteur =  ?));";
			
			pst_rec_cou = this.connect.prepareStatement(sql_rec_cou);
			
			pst_rec_cou.setInt(1, idMoniteur);
			
			ResultSet res_rec_cou = pst_rec_cou.executeQuery();
			
			while (res_rec_cou.next()) {
				listSelonId.add(new Cours(res_rec_cou.getInt("numCours"), res_rec_cou.getString("nomSport"), res_rec_cou.getInt("prix"),
						res_rec_cou.getInt("minEleve"), res_rec_cou.getInt("maxEleve"), res_rec_cou.getString("periodeCours")));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_rec_cou != null) {
				try { pst_rec_cou.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return listSelonId;
	}
	
	public String calculerPlaceCours(int idCours, int idSemaine){
		PreparedStatement pst_rec_cou = null;
		PreparedStatement pst_cpt_cou = null;
		try {
			int min = 0;
			int max = 0;
			int placeActuelle = 0;
			String sql_rec_cou = "SELECT * FROM Cours WHERE numCours = ?";
			String sql_cpt_res = "SELECT Count(*) AS total FROM ReservationCours "
					+ "INNER JOIN Cours ON Cours.numCours = ReservationCours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					+ "WHERE ReservationCours.numCours = ? AND CoursSemaine.numSemaine = ?;";
			
			pst_rec_cou = this.connect.prepareStatement(sql_rec_cou);
			pst_cpt_cou = this.connect.prepareStatement(sql_cpt_res);
			
			pst_rec_cou.setInt(1, idCours);
			pst_cpt_cou.setInt(1, idCours);
			pst_cpt_cou.setInt(2, idSemaine);
			
			ResultSet res_rec_cou = pst_rec_cou.executeQuery();
			ResultSet res_cpt_cou = pst_cpt_cou.executeQuery();
			
			while (res_rec_cou.next()) {
				min = res_rec_cou.getInt("minEleve");
				max = res_rec_cou.getInt("maxEleve");
			}
			
			while (res_cpt_cou.next()) {
				placeActuelle = res_cpt_cou.getInt("total");
			}
			int seuilMini = min - placeActuelle;
			if (seuilMini < 0){
				seuilMini = 0;
			}
			return (max - placeActuelle) + "-" + seuilMini;
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_rec_cou != null || pst_cpt_cou != null) {
				try { 
					pst_rec_cou.close();
					pst_cpt_cou.close();
					}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return -1 + "";
	}
	
	@Override public ArrayList<Cours> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode) { return null; }
	@Override public ArrayList<Cours> getListCoursParticulierSelonId(int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Cours> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode, int cours) { return null; }
	@Override public ArrayList<Cours> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Cours> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
}
