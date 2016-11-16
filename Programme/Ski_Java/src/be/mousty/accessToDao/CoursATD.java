package be.mousty.accessToDao;
/**
	Classe m�tier relatif li�e � la classe Cours et CoursDAO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category M�tier
*/
import java.util.ArrayList;
import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Cours;

public class CoursATD {
	// VARIABLES
	private String 	nomSport;
	private double 	prix;
	private int 	minEleve;
	private int 	maxEleve;
	private String 	periodeCours;


	// CONSTRUCTEURS
	public CoursATD (){}
	public CoursATD(String nomSport, double prix, int minEleve, int maxEleve, String periodeCours){
		this.prix 			= prix;
		this.minEleve 		= minEleve;
		this.maxEleve 		= maxEleve;
		this.periodeCours 	= periodeCours;
		this.nomSport 		= nomSport;
	}
	
	public CoursATD(Cours C){
		this.prix 			= C.getPrix();
		this.minEleve 		= C.getMinEl();
		this.maxEleve 		= C.getMaxEl();
		this.periodeCours 	= C.getPeriodeCours();
		this.nomSport 		= C.getNomSport();
	}

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Cours> CoursDAO = adf.getCoursDAO();
	public int					create				(Cours c) 		{ return CoursDAO.create(c); 	}
	public boolean 				delete				()	 			{ return CoursDAO.delete(null); }
	public Cours 				getId				(Cours c) 		{ return CoursDAO.getId(c); 	}
	public boolean 				update				(Cours c) 		{ return CoursDAO.update(c); 	}
	public Cours 				find				(int id) 		{ return CoursDAO.find(id); 	} 
	public ArrayList<Cours> 	getListC			() 				{ return CoursDAO.getList(); 	} 
	public String 				calculerPlaceCours 	(int idCours, long idSemaine, int idMoniteur) 
	{ return CoursDAO.calculerPlaceCours(idCours, idSemaine, idMoniteur); }
	public ArrayList<Cours> 	getListCoursSelonId	(int idMoniteur){ return CoursDAO.getMyListSelonID(idMoniteur, -1, -1, ""); 	}

	// Fonction de transformation ATD en POJO
	public ArrayList<CoursATD> changeTypeCoursListPojoEnATD(ArrayList<Cours> listC){
		try {
			ArrayList<CoursATD> ListCoursATD = new ArrayList<CoursATD>();
			for(int i = 0; i < listC.size(); i++){
				CoursATD CATD = new CoursATD();
				CATD.setMaxEl(listC.get(i).getMaxEl());
				CATD.setMinEl(listC.get(i).getMinEl());
				CATD.setNomSport(listC.get(i).getNomSport());
				CATD.setPeriodeCours(listC.get(i).getPeriodeCours());
				CATD.setPrix(listC.get(i).getPrix());
				ListCoursATD.add(CATD);
			}
			return ListCoursATD;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}


	public ArrayList<Cours> changeTypeCourslistATDEnPojo(ArrayList<CoursATD> listCoursATD){
		try {
			ArrayList<Cours> listC = new ArrayList<Cours>();
			for(int i = 0; i < listCoursATD.size(); i++){
				Cours C = new Cours();
				C.setMaxEl(listCoursATD.get(i).getMaxEl());
				C.setMinEl(listCoursATD.get(i).getMinEl());
				C.setNomSport(listCoursATD.get(i).getNomSport());
				C.setPeriodeCours(listCoursATD.get(i).getPeriodeCours());
				C.setPrix(listCoursATD.get(i).getPrix());
				listC.add(C);
			}
			return listC;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}
	
	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return  "Cours : " + nomSport + System.getProperty("line.separator")
		+ "P�riode de cours : " + periodeCours + System.getProperty("line.separator")
		+ "Prix : " + prix + "�" + System.getProperty("line.separator") 
		+  "Nombre minimum d'�l�ves pour ce cours : " + minEleve + System.getProperty("line.separator") 
		+  "Nombre maximum d'�l�ves pour ce cours : " + maxEleve + System.getProperty("line.separator");
	}

	// PROPRIETES
	public double 	getPrix			() 				{ return prix; }
	public int 		getMinEl		() 				{ return minEleve; }
	public int 		getMaxEl		() 				{ return maxEleve; }
	public String 	getPeriodeCours	() 				{ return periodeCours; }
	public String 	getNomSport		() 				{ return nomSport; }
	public void 	setPrix			(double el) 	{ prix = el; }
	public void 	setMinEl		(int el) 		{ minEleve = el; }
	public void 	setMaxEl		(int el) 		{ maxEleve = el; }
	public void 	setPeriodeCours	(String el) 	{ periodeCours = el; }
	public void 	setNomSport		(String el) 	{ nomSport = el; }
}
