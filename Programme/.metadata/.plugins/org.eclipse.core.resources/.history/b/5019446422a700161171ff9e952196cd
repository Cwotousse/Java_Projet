package be.mousty.accessToDao;

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

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Cours> CoursDAO = adf.getCoursDAO();
	public int					create				(Cours c) 		{ return CoursDAO.create(c); 	}
	public boolean 				delete				()	 			{ return CoursDAO.delete(null); }
	public Cours 				getId				(Cours c) 		{ return CoursDAO.getId(c); 	}
	public boolean 				update				(Cours c) 		{ return CoursDAO.update(c); 	}
	public Cours 				find				(int id) 		{ return CoursDAO.find(id); 	} 
	public ArrayList<Cours> 	getListC			() 				{ return CoursDAO.getList(); 	} 
	public ArrayList<Cours> 	getListCoursSelonId	(int idMoniteur){ return CoursDAO.getMyListSelonID(idMoniteur, -1, -1, ""); 	}

	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return  "Cours : " + nomSport + System.getProperty("line.separator")
		+ "Période de cours : " + periodeCours + System.getProperty("line.separator")
		+ "Prix : " + prix + "€" + System.getProperty("line.separator") 
		+  "Nombre minimum d'élèves pour ce cours : " + minEleve + System.getProperty("line.separator") 
		+  "Nombre maximum d'élèves pour ce cours : " + maxEleve + System.getProperty("line.separator");
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
