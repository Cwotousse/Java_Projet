package be.mousty.accessToDao;

import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.CoursCollectif;

public class CoursCollectifATD extends CoursATD{
	// VARIABLES
	private int 	numCoursCollectif;
	private String 	categorieAge;
	private String 	niveauCours;

	// CONSTRUCTEURS
	public CoursCollectifATD (){}
	public CoursCollectifATD(String categorieAge, String niveauCours){
		this.categorieAge 	= categorieAge;
		this.niveauCours 	= niveauCours;
	}

	public CoursCollectifATD(String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, 
			String categorieAge, String niveauCours){
		super(nomSport, prix, minEleve, maxEleve, periodeCours);
		this.categorieAge 	= categorieAge;
		this.niveauCours 	= niveauCours;
	}

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<CoursCollectif> CoursCollectifDAO = adf.getCoursCollectifDAO();
	public int					create				(CoursCollectif c) 		{ return CoursCollectifDAO.create(c); 	}
	public boolean 				delete				()	 						{ return CoursCollectifDAO.delete(null); 	}
	public CoursCollectif 	getId				(CoursCollectif c) 		{ return CoursCollectifDAO.getId(c); 		}
	public boolean 				update				(CoursCollectif c) 		{ return CoursCollectifDAO.update(c); 	}
	public CoursCollectif 	find				(int id) 					{ return CoursCollectifDAO.find(id); 		} 
	public ArrayList<CoursCollectif> 	getListCP 	() 							{ return CoursCollectifDAO.getList(); 	} 
	public ArrayList<CoursCollectif> getListCoursCollectifSelonId(int idMoniteur, int idEleve, int numSemaine,  String periode)
	{ return CoursCollectifDAO.getMyListSelonID(idMoniteur, idEleve, numSemaine, periode); 	}

	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return  
				super.toString()+ System.getProperty("line.separator") + 
				"Catégorie d'age : " + categorieAge + System.getProperty("line.separator") + 
				"Niveau du cours : " + niveauCours + System.getProperty("line.separator");
	}

	// PROPRIETES
	public int getNumCoursCollectif		() 							{ return numCoursCollectif; }
	public String getCategorieAge		() 							{ return categorieAge; }
	public String getNiveauCours		() 							{ return niveauCours; }
	public void setNumCoursCollectif	(int numCoursCollectif) 	{ this.numCoursCollectif = numCoursCollectif; }
	public void setCategorieAge			(String el) 				{ categorieAge = el; }
	public void setNiveauCours			(String niveauCours) 		{ this.niveauCours = niveauCours; }
}
