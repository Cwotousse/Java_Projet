package be.mousty.pojo;

import be.mousty.accessToDao.CoursParticulierATD;

/**
	Classe POJO relatif à la table CoursParticulier dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/
public class CoursParticulier extends Cours{
	// VARIABLES
	private int 	numCoursParticulier;
	private int 	nombreHeures;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public CoursParticulier (){}
	
	public CoursParticulier(String nomSport, double prix, int minEleve, int maxEleve, String periodeCoursParticulier, int nombreHeures){
		super(nomSport, prix, minEleve, maxEleve, periodeCoursParticulier);
		this.nombreHeures = nombreHeures;
	}

	public CoursParticulier(CoursParticulierATD C){
		super(C.getNomSport(), C.getPrix(), C.getMinEl(), C.getMaxEl(), C.getPeriodeCours());
		this.nombreHeures = C.getNombreHeures();
	}
	
	// PROPRIETES
	public int getNumCoursParticulier	() 							{ return numCoursParticulier; }
	public int getNombreHeures			() 							{ return nombreHeures; }
	public void setNumCoursParticulier	(int numCoursParticulier) 	{ this.numCoursParticulier = numCoursParticulier; }
	public void setNombreHeures			(int el) 					{ nombreHeures = el; }
}