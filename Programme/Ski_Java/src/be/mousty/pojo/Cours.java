package be.mousty.pojo;
/**
	Classe POJO relatif � la table Cours dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/

public class Cours {
	// VARIABLES
	private int 	numCours;
	private String 	nomSport;
	private double 	prix;
	private int 	minEleve;
	private int 	maxEleve;
	private String 	periodeCours;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Cours (){}
	
	// PROPRIETES
	public int getNumCours			() 				{ return numCours; }
	public double getPrix			() 				{ return prix; }
	public int getMinEl				() 				{ return minEleve; }
	public int getMaxEl				() 				{ return maxEleve; }
	public String getPeriodeCours	() 				{ return periodeCours; }
	public String getNomSport		() 				{ return nomSport; }
	public void setNumCours			(int numCours) 	{ this.numCours = numCours; }
	public void setPrix				(double el) 	{ prix = el; }
	public void setMinEl			(int el) 		{ minEleve = el; }
	public void setMaxEl			(int el) 		{ maxEleve = el; }
	public void setPeriodeCours		(String el) 	{ periodeCours = el; }
	public void setNomSport			(String el) 	{ nomSport = el; }
}