package be.mousty.pojo;

import java.sql.Date;

import be.mousty.accessToDao.UtilisateurATD;

/**
	Classe POJO relatif à la table Utilisateur dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/
public class Utilisateur extends Personne{
	// VARIABLES
	private int numUtilisateur;
	private String mdp;
	private String pseudo;
	private int typeUtilisateur;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Utilisateur(){}
	
	public Utilisateur(String nom, String pre, String adresse, String sexe, Date dateNaissance,
			String pseudo, String mdp, int typeUtilisateur){
		super(nom, pre, adresse, sexe, dateNaissance);
		this.pseudo 			= pseudo;
		this.mdp 				= mdp;
		this.typeUtilisateur 	= typeUtilisateur;
	}
	
	public Utilisateur(UtilisateurATD U){
		super(U.getNom(), U.getPre(), U.getAdresse(), U.getSexe(), U.getDateNaissance());
		this.pseudo 			= U.getPseudo();
		this.mdp 				= U.getMdp();
		this.typeUtilisateur 	= U.getTypeUtilisateur();
		this.numUtilisateur 	= -1;
	}

	// PROPRIETES
	public String getPseudo			() { return pseudo; }
	public String getMdp			() { return mdp; }
	public int getTypeUtilisateur	() { return typeUtilisateur; }
	public int getNumUtilisateur	() { return numUtilisateur; }
	public void setPseudo			(String pseudo) 		{ this.pseudo = pseudo; }
	public void setMdp				(String mdp)			{ this.mdp = mdp; }
	public void setTypeUtilisateur	(int typeUtilisateur) 	{ this.typeUtilisateur = typeUtilisateur; }
	public void setNumUtilisateur	(int numUtilisateur) 	{ this.numUtilisateur = numUtilisateur; }
}
