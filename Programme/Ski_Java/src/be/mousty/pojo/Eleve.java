package be.mousty.pojo;
/**
	Classe POJO relatif à la table Eleve dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/
public class Eleve extends Personne{
	// VARIABLES
	private String categorie;
	private int numEleve;
	private int numClient;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Eleve(){}
	
	// PROPRIETE
	public String 	getCategorie	() 					{ return categorie; }
	public int 		getNumEleve		() 					{ return numEleve; }
	public int 		getNumClient	() 					{ return numClient; }
	public void 	setCategorie	(String categorie) 	{ this.categorie = categorie; }
	public void 	setNumEleve		(int numEleve) 		{ this.numEleve = numEleve; }
	public void 	setNumClient	(int numClient) 	{ this.numClient = numClient; }
}
