package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import POJO.Accreditation;

public class AccreditationDAO extends DAO<Accreditation> {
	public AccreditationDAO(Connection conn) { super(conn); }
	public int create(Accreditation obj) { return -1; }
	public boolean delete(Accreditation obj) { return false; }
	public boolean update(Accreditation obj) { return false; }

	// On cherche une Accreditation grâce à son id
	public Accreditation find(int id) {
		Accreditation accred = new Accreditation();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Accreditation WHERE numAccreditation = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			// int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
			while (result.next()) { accred = new Accreditation(result.getString("nom")); }
			return accred;
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return null;
	}



	public ArrayList<Accreditation> getList() {

		ArrayList<Accreditation> liste = new ArrayList<Accreditation>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Accreditation INNER JOIN Personne On Personne.numPersonne = Accreditation.numAccreditation";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
				Accreditation accred  = new Accreditation(rs.getString("nom"));
				liste.add(accred);
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

	@Override public String calculerPlaceCours(int numCours, int numSemaine) { return -1 + ""; }
	@Override public ArrayList<Accreditation> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Accreditation> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode) { return null; }
	@Override public ArrayList<Accreditation> getListCoursParticulierSelonId(int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Accreditation> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Accreditation> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Accreditation> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<Accreditation> getListDispo(int numSemaine, String periode) { return null; }
}