package be.mousty.pojo;

public class Eleve extends Personne{
	// VARIABLES
	private String categorie;
	private int numEleve;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Eleve(){}
	
	// PROPRIETE
	public String 	getCategorie		() 							{ return categorie; }
	public int 		getNumEleve			() 							{ return numEleve; }
	public void 	setCategorie		(String categorie) 			{ this.categorie = categorie; }
	public void 	setNumEleve			(int numEleve) 				{ this.numEleve = numEleve; }
}
