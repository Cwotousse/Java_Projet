package be.mousty.accessToDao;

public class DisponibiliteMoniteurATD {
	// VARIABLES
	private boolean disponible;

	// CONSTRUCTEURS
	public DisponibiliteMoniteurATD(){}
	public DisponibiliteMoniteurATD(boolean disponible){ this.disponible 	= disponible; }

	// FONCTION SURCHARGEE
	@Override public String toString() { return  "Le moniteur est disponible : " + disponible + "."; }

	// PROPRIETE
	public boolean 	getDisponible	() { return disponible; }
	public void 	setDisponible	(boolean disponible)	{ this.disponible = disponible; }
}

