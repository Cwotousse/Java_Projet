package be.mousty.pojo;
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
	
	// PROPRIETES
	public int getNumCoursParticulier	() 							{ return numCoursParticulier; }
	public int getNombreHeures			() 							{ return nombreHeures; }
	public void setNumCoursParticulier	(int numCoursParticulier) 	{ this.numCoursParticulier = numCoursParticulier; }
	public void setNombreHeures			(int el) 					{ nombreHeures = el; }
}