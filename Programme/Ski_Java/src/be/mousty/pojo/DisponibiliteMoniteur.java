package be.mousty.pojo;

import be.mousty.accessToDao.DisponibiliteMoniteurATD;

/**
	Classe POJO relatif à la table DisponibiliteMoniteur dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/
public class DisponibiliteMoniteur {
	// VARIABLES
	private int numMoniteur;
	private int numSemaine;
	private boolean disponible;
	private int numDispo;

	// CONSTRUCTEUR SANS ARGUMENTS
	public DisponibiliteMoniteur(){}
	public DisponibiliteMoniteur(boolean disponible){ this.disponible 	= disponible; }
	public DisponibiliteMoniteur(DisponibiliteMoniteurATD DM){ this.disponible 	= DM.getDisponible(); }

	// PROPRIETE
	public boolean 	getDisponible	() { return disponible; }
	public int 		getNumMoniteur	() { return numMoniteur; }
	public int 		getNumSemaine	() { return numSemaine; }
	public int 		getNumDispo		() { return numDispo; }
	public void 	setDisponible	(boolean disponible)	{ this.disponible = disponible; }
	public void 	setNumMoniteur	(int numMoniteur) 		{ this.numMoniteur = numMoniteur; }
	public void 	setNumSemaine	(int numSemaine) 		{ this.numSemaine = numSemaine; }
	public void 	setNumDispo		(int numDispo) 			{ this.numDispo = numDispo; }
}
