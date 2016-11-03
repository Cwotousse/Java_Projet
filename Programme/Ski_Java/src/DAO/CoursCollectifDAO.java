package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.CoursCollectif;
import POJO.Reservation;

public class CoursCollectifDAO extends DAO<CoursCollectif> {
	public CoursCollectifDAO(Connection conn) {
		super(conn);
	}

	@Override
	public int create		(CoursCollectif obj) { return -1; }
	public boolean delete	(CoursCollectif obj) { return false; }
	public boolean update	(CoursCollectif obj) { return false; }

	// On cherche une CoursCollectif grâce à son id
	public CoursCollectif find(int id) {
		CoursCollectif coursCollectif = new CoursCollectif();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursCollectif INNER JOIN Cours ON CoursCollectif.numCoursCollectif = Cours.numCours WHERE numCoursCollectif = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			while (result.next()) {
				// int numCours, String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, String categorieAge, String niveauCours
				coursCollectif = new CoursCollectif(result.getInt("numCours"), result.getString("nomSport"), result.getInt("prix"),
						result.getInt("minEleve"), result.getInt("maxEleve"), result.getString("periodeCours"), 
						result.getString("categorieAge"), result.getString("niveauCours"));
			}
			return coursCollectif;
		}
		catch (SQLException e) { e.printStackTrace(); return null; }
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
	}

	public ArrayList<CoursCollectif> getList() {
		ArrayList<CoursCollectif> liste = new ArrayList<CoursCollectif>();
		
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursCollectif INNER JOIN Cours ON Cours.numCours = CoursCollectif.numCoursCollectif";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CoursCollectif coursCollectif = new CoursCollectif(rs.getInt("numCours"), rs.getString("nomSport"), rs.getInt("prix"),
						rs.getInt("minEleve"), rs.getInt("maxEleve"), rs.getString("periodeCours"), rs.getString("categorieAge"), rs.getString("niveauCours"));
				liste.add(coursCollectif);
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
	
	@Override
	public ArrayList<Reservation> getMyList(int idPersonne) { return null; }
}

