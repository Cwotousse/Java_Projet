package POJO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class Semaine {
	// VARIABLES
	private Date dateDebut;
	private Date dateFin;
	//private String descriptif;
	private boolean congeScolaire;
	private int numSemaineDansAnnee;
	private int numSemaine;
	//AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	//DAO<Semaine> SemaineDao = adf.getSemaineDAO();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	// CONSTRUCTEURS
	public Semaine(){}
	public Semaine(int numSemaine,  boolean congeScolaire, Date dateDebut, Date dateFin, int numSemaineDansAnnee){
		this.numSemaine 			= numSemaine;
		this.dateDebut 				= dateDebut;
		this.dateFin 				= dateFin;
		//this.descriptif 			= descriptif;
		this.congeScolaire 			= congeScolaire;
		this.numSemaineDansAnnee 	= numSemaineDansAnnee;
	}

	// METHODES
	
	
	public static boolean EstEnPeriodeDeConge(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return ((date.after(formatter.parse("2016-12-24")) && date.before(formatter.parse("2017-01-07"))) 
					|| (date.after(formatter.parse("2017-02-25")) && date.before(formatter.parse("2017-03-04")))
					|| date.after(formatter.parse("2017-04-01")) && date.before(formatter.parse("2017-04-15")));
		} 
		catch (ParseException e) { e.printStackTrace(); return false; }
	}

	
	
	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return  //descriptif + System.getProperty("line.separator") 
				"Période de congé scolaire : " + congeScolaire + System.getProperty("line.separator")
				+ "Période comprise entre le " + dateDebut.toString() + " et le " + dateFin.toString() + System.getProperty("line.separator"); 
	}


	// PROPRIETE
	public Date getDateDebut			() { return dateDebut; }
	public Date getDateFin				() { return dateFin; }
	//public String getDescriptif			() { return descriptif; }
	public boolean getCongeScolaire		() { return congeScolaire; }
	public int getNumSemaineDansAnnee	() { return numSemaineDansAnnee; }
	public int getNumSemaine			() { return numSemaine; }
	public void setDateDebut			(Date dateDebut) 			{ this.dateDebut = dateDebut; }
	public void setDateFin				(Date dateFin) 				{ this.dateFin = dateFin; }
	//public void setDescriptif			(String descriptif) 		{ this.descriptif = descriptif; }
	public void setCongeScolaire		(boolean congeScolaire) 	{ this.congeScolaire = congeScolaire; }
	public void setNumSemaineDansAnnee	(int numSemaineDansAnnee) 	{ this.numSemaineDansAnnee = numSemaineDansAnnee; }
	public void setNumSemaine			(int numSemaine) 			{ this.numSemaine = numSemaine; }
}
