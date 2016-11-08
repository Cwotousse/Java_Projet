package be.mousty.pojo;

import java.sql.Date;

public class Semaine {
	// VARIABLES
	private int numSemaine;
	private boolean congeScolaire;
	private Date dateDebut;
	private Date dateFin;
	private int numSemaineDansAnnee;

	// CONSTRUCTEUR SANS ARGUMENTS
	public Semaine(){}

	// PROPRIETE
	public Date getDateDebut			() { return dateDebut; }
	public Date getDateFin				() { return dateFin; }
	public boolean getCongeScolaire		() { return congeScolaire; }
	public int getNumSemaineDansAnnee	() { return numSemaineDansAnnee; }
	public int getNumSemaine			() { return numSemaine; }
	public void setDateDebut			(Date dateDebut) 			{ this.dateDebut = dateDebut; }
	public void setDateFin				(Date dateFin) 				{ this.dateFin = dateFin; }
	public void setCongeScolaire		(boolean congeScolaire) 	{ this.congeScolaire = congeScolaire; }
	public void setNumSemaineDansAnnee	(int numSemaineDansAnnee) 	{ this.numSemaineDansAnnee = numSemaineDansAnnee; }
	public void setNumSemaine			(int numSemaine) 			{ this.numSemaine = numSemaine; }
}
