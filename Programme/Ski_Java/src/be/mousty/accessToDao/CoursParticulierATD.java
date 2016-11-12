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
	
	public CoursParticulierATD(CoursParticulier C){
		super(C.getNomSport(), C.getPrix(), C.getMinEl(), C.getMaxEl(), C.getPeriodeCours());
		this.nombreHeures = C.getNombreHeures();
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
	public ArrayList<CoursParticulier> getListCoursParticulierSelonId(int idMoniteur, String periode, long numSemaine)
	{ return CoursParticulierDAO.getMyListSelonID(idMoniteur, numSemaine, -1, periode); 	}
	
	
	public ArrayList<CoursParticulierATD> getListCoursParticulierSelonIdATD(int numMoniteur, String periode, long numSemaine){
		ArrayList<CoursParticulier> listCP  = getListCoursParticulierSelonId(numMoniteur, periode, numSemaine);
		ArrayList<CoursParticulierATD> listCPATP = new ArrayList<CoursParticulierATD>();
		for(int i = 0; i < listCP.size(); i++){
			CoursParticulierATD CPATD = new CoursParticulierATD();
			//DMATD.setNom(A.get(i).getNomAccreditation());
			CPATD.setMaxEl(listCP.get(i).getMaxEl());
			CPATD.setMinEl(listCP.get(i).getMinEl());
			CPATD.setNombreHeures(listCP.get(i).getNombreHeures());
			CPATD.setNomSport(listCP.get(i).getNomSport());
			CPATD.setPeriodeCours(listCP.get(i).getPeriodeCours());
			CPATD.setPrix(listCP.get(i).getPrix());
			listCPATP.add(CPATD);
		}
		return listCPATP;
	}
	
	public int getIdATD(CoursParticulierATD CPATD){
		CoursParticulier CP = new CoursParticulier();
		CP.setMaxEl(CPATD.getMaxEl());
		CP.setMinEl(CPATD.getMinEl());
		CP.setNombreHeures(CPATD.getNombreHeures());
		CP.setNomSport(CPATD.getNomSport());
		CP.setPeriodeCours(CPATD.getPeriodeCours());
		CP.setPrix(CPATD.getPrix());
		CP = getId(CP);
		return CP.getNumCoursParticulier();
	}
	
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
