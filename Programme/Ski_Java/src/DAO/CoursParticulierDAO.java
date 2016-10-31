package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.CoursParticulier;

public class CoursParticulierDAO extends DAO<CoursParticulier> {
	public CoursParticulierDAO(Connection conn) {
		super(conn);
	}

	@Override
	public int create		(CoursParticulier obj) { return -1; }
	public boolean delete	(CoursParticulier obj) { return false; }
	public boolean update	(CoursParticulier obj) { return false; }

	// On cherche une CoursParticulier gr�ce � son id
	public CoursParticulier find(int id) {
		CoursParticulier coursParticulier = new CoursParticulier();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursParticulier INNER JOIN Cours ON CoursParticulier.numCoursParticulier = Cours.numCours WHERE numCoursParticulier = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			while (result.next()) {
				// int numCours, String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, int numCoursParticulier, int nombreHeures
				coursParticulier = new CoursParticulier(result.getInt("numCours"), result.getString("nomSport"), result.getInt("prix"),
						result.getInt("minEleve"), result.getInt("maxEleve"), result.getString("periodeCours"), result.getInt("nombreHeures"));
			}
			return coursParticulier;
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

	public ArrayList<CoursParticulier> getList() {
		ArrayList<CoursParticulier> liste = new ArrayList<CoursParticulier>();
		
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursParticulier INNER JOIN Cours ON Cours.numCours = CoursParticulier.numCoursParticulier";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CoursParticulier coursParticulier = new CoursParticulier(rs.getInt("numCours"), rs.getString("nomSport"), rs.getInt("prix"),
						rs.getInt("minEleve"), rs.getInt("maxEleve"), rs.getString("periodeCours"), rs.getInt("nombreHeures"));
				liste.add(coursParticulier);
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
}