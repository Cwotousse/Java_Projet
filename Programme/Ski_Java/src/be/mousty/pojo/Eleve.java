package be.mousty.pojo;

import java.sql.Date;

import be.mousty.accessToDao.EleveATD;

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
	
	public Eleve(String categorie, String nom, String pre, String adresse, String sexe, Date dateNaissance){
		super(nom, pre, adresse, sexe, dateNaissance);
		this.categorie = categorie;
	}

	public Eleve(EleveATD E){
		super(E.getNom(), E.getPre(), E.getAdresse(), E.getSexe(), E.getDateNaissance());
		this.categorie = E.attributerCategorie();
	}
	
	// PROPRIETE
	public String 	getCategorie	() 					{ return categorie; }
	public int 		getNumEleve		() 					{ return numEleve; }
	public int 		getNumClient	() 					{ return numClient; }
	public void 	setCategorie	(String categorie) 	{ this.categorie = categorie; }
	public void 	setNumEleve		(int numEleve) 		{ this.numEleve = numEleve; }
	public void 	setNumClient	(int numClient) 	{ this.numClient = numClient; }
}
