package be.mousty.pojo;
/**
	Classe POJO relatif � la table Personne dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/
import java.sql.Date;

public class Personne {
	// VARIABLES 
	private int 	numPersonne;
	private String 	nom;
	private String 	pre;
	private String 	adresse;
	private Date 	dateNaissance;
	private String 	sexe;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Personne(){}
	
	public Personne(String nom, String pre, String adresse, String sexe, Date dateNaissance){
		this.nom 			= nom;
		this.pre 			= pre;
		this.adresse 		= adresse;
		this.sexe 			= sexe;
		this.dateNaissance 	= dateNaissance;
		this.numPersonne 	= -1;
	}
	
	
	// PROPRIETE
	public String getNom		() { return nom; }
	public String getPre		() { return pre; }
	public String getAdresse	() { return adresse; }
	public String getSexe		() { return sexe; }
	public Date getDateNaissance() { return dateNaissance; }
	public int getNumPersonne 	() { return numPersonne; }
	public void setNom			(String nom) 			{ this.nom = nom; }
	public void setPre			(String pre) 			{ this.pre = pre; }
	public void setAdresse		(String adresse) 		{ this.adresse = adresse;	}
	public void setSexe			(String sexe) 			{ this.sexe = sexe; }
	public void setDateNaissance(Date dateNaissance) 	{ this.dateNaissance = dateNaissance; }
	public void setNumPersonne	(int el)				{ this.numPersonne = el; }
}
