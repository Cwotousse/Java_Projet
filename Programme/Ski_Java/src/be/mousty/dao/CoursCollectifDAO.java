package be.mousty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.mousty.pojo.Cours;
import be.mousty.pojo.CoursCollectif;
import be.mousty.pojo.Eleve;

public class CoursCollectifDAO extends DAO<CoursCollectif> {
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Eleve> EleveDAO = adf.getEleveDAO();
	DAO<Cours> CoursDAO = adf.getCoursDAO();
	
	public CoursCollectifDAO(Connection conn) { super(conn); }

	@Override
	public int create		(CoursCollectif obj) { return -1; }
	public boolean delete	(CoursCollectif obj) { return false; }
	public boolean update	(CoursCollectif obj) { return false; }

	// On cherche une CoursCollectif grâce à son id
	public CoursCollectif find(int id) {
		CoursCollectif CC = new CoursCollectif();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursCollectif INNER JOIN Cours ON CoursCollectif.numCoursCollectif = Cours.numCours WHERE numCoursCollectif = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet res_cou_col = pst.executeQuery();
			while (res_cou_col.next()) {
				// int numCours, String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, String categorieAge, String niveauCours
				CC.setNumCoursCollectif(res_cou_col.getInt("numCoursCollectif"));
				CC.setCategorieAge(res_cou_col.getString("categorieAge"));
				CC.setNiveauCours(res_cou_col.getString("niveauCours"));
				CC.setNumCours(res_cou_col.getInt("numCours"));
				CC.setNomSport(res_cou_col.getString("nomSport"));
				CC.setPrix(res_cou_col.getInt("prix"));
				CC.setMinEl(res_cou_col.getInt("minEleve"));
				CC.setMaxEl(res_cou_col.getInt("maxEleve"));
				CC.setPeriodeCours(res_cou_col.getString("periodeCours"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return CC;
	}

	public ArrayList<CoursCollectif> getList() {
		ArrayList<CoursCollectif> liste = new ArrayList<CoursCollectif>();
		
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM CoursCollectif INNER JOIN Cours ON Cours.numCours = CoursCollectif.numCoursCollectif";
			pst = this.connect.prepareStatement(sql);
			ResultSet res_cou_col = pst.executeQuery();
			while (res_cou_col.next()) {
				CoursCollectif CC = new CoursCollectif();
				CC.setNumCoursCollectif(res_cou_col.getInt("numCoursCollectif"));
				CC.setCategorieAge(res_cou_col.getString("categorieAge"));
				CC.setNiveauCours(res_cou_col.getString("niveauCours"));
				CC.setNumCours(res_cou_col.getInt("numCours"));
				CC.setNomSport(res_cou_col.getString("nomSport"));
				CC.setPrix(res_cou_col.getInt("prix"));
				CC.setMinEl(res_cou_col.getInt("minEleve"));
				CC.setMaxEl(res_cou_col.getInt("maxEleve"));
				CC.setPeriodeCours(res_cou_col.getString("periodeCours"));
				liste.add(CC);
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
	
	public ArrayList<CoursCollectif> getMyListSelonID(int idMoniteur, int idEleve, int numSemaine, String periode){
		//System.out.println("Entree fonc");
		/*ArrayList<Cours> listCours = CoursDAO.getListCoursSelonId(idMoniteur);
		ArrayList<CoursCollectif> listFull = getList();
		ArrayList<CoursCollectif> listSelonId = new ArrayList<CoursCollectif>();
		Eleve E = EleveDAO.find(idEleve);
		for (CoursCollectif CC : listFull){
			for (Cours C : listCours){
				if (CC.getNumCours() == C.getNumCours() && E.getCategorie().equals(CC.getCategorieAge())){
					//System.out.println("For String de taille " + periode.size());
					//for(String S : periode){
						//System.out.println(S + " / " + CC.getPeriodeCours());
						if(CC.getPeriodeCours().equals(periode)){
							//System.out.println("Ajout Cours Collectif");
							listSelonId.add(CC);
						}
					//}
				}
			}
		}
		return listSelonId;*/
		/* Sélection des cours par rapport à la catégorie de l'élève et de la période */
		/*Sélection des étudiants par rapport aux accreds du moniteur */
		PreparedStatement pst_lst_cou1 = null;
		PreparedStatement pst_lst_cou2 = null;
		ArrayList<CoursCollectif> listSelonId = new ArrayList<CoursCollectif>();
		try {
			String sql1 = "SELECT * from Cours "
			+ "INNER JOIN CoursCollectif ON CoursCollectif.numCoursCollectif = Cours.numCours "
			+ "WHERE PeriodeCours = ? AND CoursCollectif.categorieAge in "
			+ "(SELECT Categorie from ELEVE Where numEleve = ?) "
			+ "AND nomSport in "
			+ "(SELECT nomAccreditation from accreditation where numAccreditation in "
			+ "(SELECT numAccreditation from ligneAccreditation where numMoniteur = ?)) "
			
			+ "AND   Cours.numCours IN "
            + "(SELECT CoursMoniteur.numCours FROM CoursMoniteur "
           	+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = CoursMoniteur.numCours "         
           	+ "WHERE  CoursSemaine.numCours IN "
            + "(SELECT CoursSemaine.numCours FROM Cours WHERE CoursSemaine.numSemaine = ? AND periodeCours = ? AND numMoniteur = ?));";
			pst_lst_cou1 = this.connect.prepareStatement(sql1);
			pst_lst_cou1.setString(1, periode);
			pst_lst_cou1.setInt(2, idEleve);
			pst_lst_cou1.setInt(3, idMoniteur);
			pst_lst_cou1.setInt(4, numSemaine);
			pst_lst_cou1.setString(5, periode);
			pst_lst_cou1.setInt(6, idMoniteur);
			ResultSet res_cou_col = pst_lst_cou1.executeQuery();
			while (res_cou_col.next()) {
				CoursCollectif CC = new CoursCollectif();
				CC.setNumCoursCollectif(res_cou_col.getInt("numCoursCollectif"));
				CC.setCategorieAge(res_cou_col.getString("categorieAge"));
				CC.setNiveauCours(res_cou_col.getString("niveauCours"));
				CC.setNumCours(res_cou_col.getInt("numCours"));
				CC.setNomSport(res_cou_col.getString("nomSport"));
				CC.setPrix(res_cou_col.getInt("prix"));
				CC.setMinEl(res_cou_col.getInt("minEleve"));
				CC.setMaxEl(res_cou_col.getInt("maxEleve"));
				CC.setPeriodeCours(res_cou_col.getString("periodeCours"));
				listSelonId.add(CC);
			}
			if (listSelonId.isEmpty()){
				String sql2 = "SELECT * from Cours "
						+ "INNER JOIN CoursCollectif ON CoursCollectif.numCoursCollectif = Cours.numCours "
						+ "WHERE PeriodeCours = ? AND CoursCollectif.categorieAge in "
						+ "(SELECT Categorie from ELEVE Where numEleve = ?) "
						+ "AND nomSport in "
						+ "(SELECT nomAccreditation from accreditation where numAccreditation in "
						+ "(SELECT numAccreditation from ligneAccreditation where numMoniteur = ?));";
				pst_lst_cou2 = this.connect.prepareStatement(sql2);
				pst_lst_cou2.setString(1, periode);
				pst_lst_cou2.setInt(2, idEleve);
				pst_lst_cou2.setInt(3, idMoniteur);
						ResultSet res_cou_col2 = pst_lst_cou2.executeQuery();
						while (res_cou_col2.next()) {
							CoursCollectif CC = new CoursCollectif();
							CC.setNumCoursCollectif(res_cou_col2.getInt("numCoursCollectif"));
							CC.setCategorieAge(res_cou_col2.getString("categorieAge"));
							CC.setNiveauCours(res_cou_col2.getString("niveauCours"));
							CC.setNumCours(res_cou_col2.getInt("numCours"));
							CC.setNomSport(res_cou_col2.getString("nomSport"));
							CC.setPrix(res_cou_col2.getInt("prix"));
							CC.setMinEl(res_cou_col2.getInt("minEleve"));
							CC.setMaxEl(res_cou_col2.getInt("maxEleve"));
							CC.setPeriodeCours(res_cou_col2.getString("periodeCours"));
							listSelonId.add(CC);
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
	
	@Override
	public CoursCollectif getId(CoursCollectif obj) {
		PreparedStatement pst = null;
		CoursCollectif CC = new CoursCollectif();
		try {
			String sql = "SELECT numCours FROM cours "
					+ "INNER JOIN CoursCollectif ON numCoursCollectif = numCours "
					+ "WHERE nomSport = ? AND periodeCours = ? AND categorieAge = ? AND niveauCours = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setString(1, obj.getNomSport());
			pst.setString(2, obj.getPeriodeCours());
			pst.setString(3, obj.getCategorieAge());
			pst.setString(4, obj.getNiveauCours());
			ResultSet res_Rec_CC = pst.executeQuery();
			while (res_Rec_CC.next()) {
				CC.setNumCoursCollectif(res_Rec_CC.getInt("numCoursCollectif"));
				CC.setCategorieAge(res_Rec_CC.getString("categorieAge"));
				CC.setNiveauCours(res_Rec_CC.getString("niveauCours"));
				CC.setNumCours(res_Rec_CC.getInt("numCours"));
				CC.setNomSport(res_Rec_CC.getString("nomSport"));
				CC.setPrix(res_Rec_CC.getInt("prix"));
				CC.setMinEl(res_Rec_CC.getInt("minEleve"));
				CC.setMaxEl(res_Rec_CC.getInt("maxEleve"));
				CC.setPeriodeCours(res_Rec_CC.getString("periodeCours"));
				}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return CC;
	}

	@Override
	public ArrayList<CoursCollectif> getListSelonCriteres(CoursCollectif obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAssurance(int numEleve, int numSemaine, String periode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int valeurReduction(int numSem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String calculerPlaceCours(int numCours, int numSemaine, int idMoniteur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void creerTouteDisponibilites() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void creerTouteDisponibilitesSelonMoniteur(int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void AjouterSemainesDansDB(String start, String end) {
		// TODO Auto-generated method stub
		
	}	
}
