package be.mousty.pojo;

import be.mousty.accessToDao.CoursCollectifATD;

/**
	Classe POJO relatif à la table CoursCollectif dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/

public class CoursCollectif extends Cours{
	// VARIABLES
	private int 	numCoursCollectif;
	private String 	categorieAge;
	private String 	niveauCours;

	// CONSTRUCTEUR SANS ARGUMENTS
	public CoursCollectif (){}
	
	public CoursCollectif(String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, 
			String categorieAge, String niveauCours){
		super(nomSport, prix, minEleve, maxEleve, periodeCours);
		this.categorieAge 	= categorieAge;
		this.niveauCours 	= niveauCours;
	}
	
	public CoursCollectif(CoursCollectifATD C){
		super(C.getNomSport(), C.getPrix(), C.getMinEl(), C.getMaxEl(), C.getPeriodeCours());
		this.categorieAge 	= C.getCategorieAge();
		this.niveauCours 	= C.getNiveauCours();
	}

	// PROPRIETES
	public int getNumCoursCollectif		() 							{ return numCoursCollectif; }
	public String getCategorieAge		() 							{ return categorieAge; }
	public String getNiveauCours		() 							{ return niveauCours; }
	public void setNumCoursCollectif	(int numCoursCollectif) 	{ this.numCoursCollectif = numCoursCollectif; }
	public void setCategorieAge			(String el) 				{ categorieAge = el; }
	public void setNiveauCours			(String niveauCours) 		{ this.niveauCours = niveauCours; }
}