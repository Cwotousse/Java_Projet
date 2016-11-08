package be.mousty.accessToDao;

import java.sql.Date;

public class ClientATD extends UtilisateurATD{
	// VARIABLES
	private String 	adresseFacturation;
	
	// CONSTRUCTEURS
	public ClientATD(){}
	public ClientATD(String nom, String pre, String adresse, String sexe, Date dateNaissance,
			String pseudo, String mdp, int typeUtilisateur, String adresseFacturation){
		super(nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeUtilisateur);
		//this.numClient = numClient;
		this.adresseFacturation = adresseFacturation;
	}
	
	
	// PROPRIETES
	public String getAdresseFacturation	() 				{ return adresseFacturation; }
	public void setAdresseFacturation 	(String el) 	{ this.adresseFacturation  = el; }
}

