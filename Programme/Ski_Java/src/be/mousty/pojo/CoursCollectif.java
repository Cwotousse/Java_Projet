package be.mousty.pojo;


public class CoursCollectif extends Cours{
	// VARIABLES
	private int 	numCoursCollectif;
	private String 	categorieAge;
	private String 	niveauCours;

	// CONSTRUCTEUR SANS ARGUMENTS
	public CoursCollectif (){}

	// PROPRIETES
	public int getNumCoursCollectif		() 							{ return numCoursCollectif; }
	public String getCategorieAge		() 							{ return categorieAge; }
	public String getNiveauCours		() 							{ return niveauCours; }
	public void setNumCoursCollectif	(int numCoursCollectif) 	{ this.numCoursCollectif = numCoursCollectif; }
	public void setCategorieAge			(String el) 				{ categorieAge = el; }
	public void setNiveauCours			(String niveauCours) 		{ this.niveauCours = niveauCours; }
}