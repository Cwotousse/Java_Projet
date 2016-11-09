package be.mousty.accessToDao;

import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.CoursParticulier;


public class CoursParticulierATD extends CoursATD{
	// VARIABLES
	private int 	nombreHeures;
	
	// CONSTRUCTEURS
	public CoursParticulierATD (){}
	public CoursParticulierATD(int nombreHeures){
		this.nombreHeures = nombreHeures;
	}
	
	public CoursParticulierATD(String nomSport, double prix, int minEleve, int maxEleve, String periodeCoursParticulier, int nombreHeures){
		super(nomSport, prix, minEleve, maxEleve, periodeCoursParticulier);
		this.nombreHeures = nombreHeures;
	}
	
	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<CoursParticulier> CoursParticulierDAO = adf.getCoursParticulierDAO();
	public int					create				(CoursParticulier c) 		{ return CoursParticulierDAO.create(c); 	}
	public boolean 				delete				()	 						{ return CoursParticulierDAO.delete(null); 	}
	public CoursParticulier 	getId				(CoursParticulier c) 		{ return CoursParticulierDAO.getId(c); 		}
	public boolean 				update				(CoursParticulier c) 		{ return CoursParticulierDAO.update(c); 	}
	public CoursParticulier 	find				(int id) 					{ return CoursParticulierDAO.find(id); 		} 
	public ArrayList<CoursParticulier> 	getListCP 	() 							{ return CoursParticulierDAO.getList(); 	} 
	public ArrayList<CoursParticulier> getListCoursParticulierSelonId(int idMoniteur, String periode, int numSemaine)
	{ return CoursParticulierDAO.getMyListSelonID(idMoniteur, numSemaine, -1, periode); 	}
	
	// FONCTION SURCHARGEE
		@Override
		public String toString() { 
			return super.toString()+ System.getProperty("line.separator") + 
			"Nombre d'heures ce cours : " + nombreHeures + System.getProperty("line.separator");
		}
	
	// PROPRIETES
	public int getNombreHeures	()			{ return nombreHeures; 	}
	public void setNombreHeures	(int el) 	{ nombreHeures = el; 	}
}
