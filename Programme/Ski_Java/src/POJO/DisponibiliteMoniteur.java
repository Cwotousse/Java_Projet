package POJO;

public class DisponibiliteMoniteur {
	// VARIABLES
	private boolean disponible;
	private int numMoniteur;
	private int numSemaine;
	private int numDispo;

	// CONSTRUCTEURS
	public DisponibiliteMoniteur(){}
	public DisponibiliteMoniteur(int numMoniteur, int numSemaine, boolean disponible){
		this.numSemaine 	= numSemaine;
		this.disponible 	= disponible;
		this.numMoniteur	= numMoniteur;
	}

	// FONCTION SURCHARGEE
	@Override public String toString() { return  "Le moniteur est disponible : " + disponible + "."; }


	// PROPRIETE
	public boolean 	getDisponible	() { return disponible; }
	public int 		getNumMoniteur	() { return numMoniteur; }
	public int 		getNumSemaine	() { return numSemaine; }
	public int 		getNumDispo		() { return numDispo; }
	public void setDisponible		(boolean disponible)	{ this.disponible = disponible; }
	public void setNumMoniteur		(int numMoniteur) 		{ this.numMoniteur = numMoniteur; }
	public void setNumSemaine		(int numSemaine) 		{ this.numSemaine = numSemaine; }
	public void setNumDispo			(int numDispo) 			{ this.numDispo = numDispo; }
}