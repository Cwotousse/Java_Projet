package be.mousty.pojo;

public class Utilisateur extends Personne{
	// VARIABLES
	private int numUtilisateur;
	private String mdp;
	private String pseudo;
	private int typeUtilisateur;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Utilisateur(){}

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
