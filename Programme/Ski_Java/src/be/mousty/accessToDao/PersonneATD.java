package be.mousty.accessToDao;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class PersonneATD {
	// VARIABLES 
	private String 	nom;
	private String 	pre;
	private String 	adresse;
	private String 	sexe;
	private Date 	dateNaissance;
		
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	// CONSTRUCTEURS
	public PersonneATD(){}
	public PersonneATD(String nom, String pre, String adresse, String sexe, Date dateNaissance){
		this.nom 			= nom;
		this.pre 			= pre;
		this.adresse 		= adresse;
		this.sexe 			= sexe;
		this.dateNaissance 	= dateNaissance;
	}
	
	
	// METHODES
	public double calculerAge(){
		try {
			Date now = new Date(Calendar.getInstance().getTime().getTime());
			long diffInMillies = now.getTime() - this.getDateNaissance().getTime();
			long tu =  TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
			return (double)tu/365;
			
		}
		catch (Exception e) { e.getStackTrace(); }
		return -1;
	}
	
	// METHODE SURCHARGEE
	@Override
	public String toString() { 
		return 
			nom.toUpperCase() + " " + pre + ", " + sexe + System.getProperty("line.separator") 
			+ "Date de naissance : " + dateFormat.format(dateNaissance) + " (" + (int)calculerAge() + ")" + System.getProperty("line.separator") 
			+ "Residence : " + adresse.toUpperCase() + System.getProperty("line.separator");
	}
	
	// PROPRIETE
	public String getNom		() { return nom; }
	public String getPre		() { return pre; }
	public String getAdresse	() { return adresse; }
	public String getSexe		() { return sexe; }
	public Date getDateNaissance() { return dateNaissance; }
	public void setNom			(String nom) 			{ this.nom = nom; }
	public void setPre			(String pre) 			{ this.pre = pre; }
	public void setAdresse		(String adresse) 		{ this.adresse = adresse;	}
	public void setSexe			(String sexe) 			{ this.sexe = sexe; }
	public void setDateNaissance(Date dateNaissance) 	{ this.dateNaissance = dateNaissance; }

}

