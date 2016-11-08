package be.mousty.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.mousty.pojo.Cours;
import be.mousty.pojo.CoursParticulier;

public class CoursParticulierDAO extends DAO<CoursParticulier> {
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Cours> CoursDAO = adf.getCoursDAO();

	public CoursParticulierDAO(Connection conn) { super(conn); }

	@Override
	public int create		(CoursParticulier obj) { return -1; }
	public boolean delete	(CoursParticulier obj) { return false; }
	public boolean update	(CoursParticulier obj) { return false; }

	// On cherche une CoursParticulier grâce à son id
	public CoursParticulier find(int id) {
		CoursParticulier CP = new CoursParticulier();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursParticulier INNER JOIN Cours ON CoursParticulier.numCoursParticulier = Cours.numCours WHERE numCoursParticulier = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet res_cou_CP = pst.executeQuery();
			while (res_cou_CP.next()) {
				// int numCours, String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, int numCoursParticulier, int nombreHeures
				CP.setNombreHeures(res_cou_CP.getInt("nombreHeures"));
				CP.setNumCours(res_cou_CP.getInt("numCours"));
				CP.setNomSport(res_cou_CP.getString("nomSport"));
				CP.setPrix(res_cou_CP.getInt("prix"));
				CP.setMinEl(res_cou_CP.getInt("minEleve"));
				CP.setMaxEl(res_cou_CP.getInt("maxEleve"));
				CP.setPeriodeCours(res_cou_CP.getString("periodeCours"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return CP;
	}

	public ArrayList<CoursParticulier> getList() {
		ArrayList<CoursParticulier> liste = new ArrayList<CoursParticulier>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursParticulier INNER JOIN Cours ON Cours.numCours = CoursParticulier.numCoursParticulier";
			pst = this.connect.prepareStatement(sql);
			ResultSet res_cou_CP = pst.executeQuery();
			while (res_cou_CP.next()) {
				CoursParticulier CP = new CoursParticulier();
				CP.setNombreHeures(res_cou_CP.getInt("nombreHeures"));
				CP.setNumCours(res_cou_CP.getInt("numCours"));
				CP.setNomSport(res_cou_CP.getString("nomSport"));
				CP.setPrix(res_cou_CP.getInt("prix"));
				CP.setMinEl(res_cou_CP.getInt("minEleve"));
				CP.setMaxEl(res_cou_CP.getInt("maxEleve"));
				CP.setPeriodeCours(res_cou_CP.getString("periodeCours"));
				liste.add(CP);
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

	/*public ArrayList<CoursParticulier> getListCoursParticulierSelonId(int idMoniteur, String periode){
		//System.out.println("Entree fonc");
		ArrayList<Cours> listCours = CoursDAO.getListCoursSelonId(idMoniteur);
		ArrayList<CoursParticulier> listFull = getList();
		ArrayList<CoursParticulier> listSelonId = new ArrayList<CoursParticulier>();
		for (CoursParticulier CP : listFull){
			for (Cours C : listCours){
				if (CP.getNumCours() == C.getNumCours()){
					if(CP.getPeriodeCours().equals(periode)){
						listSelonId.add(CP);
					}
				}
			}
		}
		return listSelonId;
	}*/
	
	public ArrayList<CoursParticulier> getListCoursParticulierSelonId(int idMoniteur, String periode, int numSemaine){
		PreparedStatement pst_lst_cou1 = null;
		PreparedStatement pst_lst_cou2 = null;
		ArrayList<CoursParticulier> listSelonId = new ArrayList<CoursParticulier>();
		try {
			String verifPeriode;
			switch(periode){
				case "12-13": verifPeriode = " IN('12-13') ";
					break;
				case "13-14": verifPeriode = " IN('13-14') ";
					break;
				case "12-14": verifPeriode = " IN('12-14') ";
					break;
				default : verifPeriode = " = ? ";
					break;
			}
			String sql1 = "SELECT * from Cours "
			+ "INNER JOIN CoursParticulier ON CoursParticulier.numCoursParticulier = Cours.numCours "
			+ "WHERE nomSport in "
			+ "(SELECT nomAccreditation from accreditation where numAccreditation in "
			+ "(SELECT numAccreditation from ligneAccreditation where numMoniteur = ?)) "
			
			+ "AND   Cours.numCours IN "
            + "(SELECT CoursMoniteur.numCours FROM CoursMoniteur "
           	+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = CoursMoniteur.numCours "         
           	+ "WHERE  CoursSemaine.numCours IN "
            + "(SELECT CoursSemaine.numCours FROM Cours WHERE CoursSemaine.numSemaine = ? AND periodeCours " + verifPeriode + " AND numMoniteur = ?));";
			pst_lst_cou1 = this.connect.prepareStatement(sql1);
			pst_lst_cou1.setInt(1, idMoniteur);
			pst_lst_cou1.setInt(2, numSemaine);
			//pst_lst_cou1.setString(4, periode);
			pst_lst_cou1.setInt(3, idMoniteur);
			ResultSet res_lst_cou1 = pst_lst_cou1.executeQuery();
			while (res_lst_cou1.next()) {
				CoursParticulier CP = new CoursParticulier();
				CP.setNombreHeures(res_lst_cou1.getInt("nombreHeures"));
				CP.setNumCours(res_lst_cou1.getInt("numCours"));
				CP.setNomSport(res_lst_cou1.getString("nomSport"));
				CP.setPrix(res_lst_cou1.getInt("prix"));
				CP.setMinEl(res_lst_cou1.getInt("minEleve"));
				CP.setMaxEl(res_lst_cou1.getInt("maxEleve"));
				CP.setPeriodeCours(res_lst_cou1.getString("periodeCours"));
				listSelonId.add(CP);
			}
			if (listSelonId.isEmpty()){
				String sql2 = "SELECT * from Cours "
						+ "INNER JOIN CoursParticulier ON CoursParticulier.numCoursParticulier = Cours.numCours "
						+ "WHERE PeriodeCours  " + verifPeriode + "  AND nomSport in "
						+ "(SELECT nomAccreditation from accreditation where numAccreditation in "
						+ "(SELECT numAccreditation from ligneAccreditation where numMoniteur = ?));";
				pst_lst_cou2 = this.connect.prepareStatement(sql2);
				//pst_lst_cou2.setString(1, periode);
				pst_lst_cou2.setInt(1, idMoniteur);
						ResultSet res_lst_cou2 = pst_lst_cou2.executeQuery();
						while (res_lst_cou2.next()) {
							CoursParticulier CP = new CoursParticulier();
							CP.setNombreHeures(res_lst_cou2.getInt("nombreHeures"));
							CP.setNumCours(res_lst_cou2.getInt("numCours"));
							CP.setNomSport(res_lst_cou2.getString("nomSport"));
							CP.setPrix(res_lst_cou2.getInt("prix"));
							CP.setMinEl(res_lst_cou2.getInt("minEleve"));
							CP.setMaxEl(res_lst_cou2.getInt("maxEleve"));
							CP.setPeriodeCours(res_lst_cou2.getString("periodeCours"));
							listSelonId.add(CP);
						}
						pst_lst_cou2.close();
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_lst_cou1 != null) {
				try { pst_lst_cou1.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return listSelonId;
	}
		
		
		public int getNumCoursParticulier(String nomSport, String periode){
			PreparedStatement pst = null;
			int id = -1;
			try {
				String sql = "SELECT numCoursParticulier FROM cours INNER JOIN CoursParticulier ON numCoursParticulier = numCours "
						+ " WHERE nomSport = ? AND periodeCours = ?;";
				pst = this.connect.prepareStatement(sql);
				pst.setString(1, nomSport);
				pst.setString(2, periode);
				ResultSet res_Rec_CP = pst.executeQuery();
				while (res_Rec_CP.next()) { id = res_Rec_CP.getInt("numCoursParticulier"); }
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
	@Override public ArrayList<CoursParticulier> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<CoursParticulier> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode, int numSemaine) { return null; }
	@Override public ArrayList<CoursParticulier> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode) { return null; }
	@Override public ArrayList<CoursParticulier> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<CoursParticulier> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<CoursParticulier> getListDispo(int numSemaine, String periode) { return null; }
	@Override public CoursParticulier returnUser(String mdp, String pseudo) { return null; }
	@Override public int valeurReduction(int numSem) { return 0; }

	@Override
	public int getNumPersonne(String string, String pre, String adresse) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumCoursCollectif(String nomSport, String periode, String categorie, String niveauCours) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<CoursParticulier> getListSemaineSelonDateDuJour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumSemaine(Date dateDebut) {
		// TODO Auto-generated method stub
		return 0;
	}
}
