package be.mousty.accessToDao;

import java.sql.Date;

public class EleveATD extends PersonneATD{
	// VARIABLES
	private String categorie;
	
	// CONSTRUCTEURS
	public EleveATD(){}
	
	public EleveATD(String categorie, String nom, String pre, String adresse, String sexe, Date dateNaissance){
		super(nom, pre, adresse, sexe, dateNaissance);
		this.categorie = categorie;
	}
	
	public EleveATD(String nom, String pre, String adresse, String sexe, Date dateNaissance){
		super(nom, pre, adresse, sexe, dateNaissance);
		attributerCategorie();
	}
	
	// METHODEs
	private void attributerCategorie(){
		if(this.calculerAge() <= 12 && this.calculerAge() > 4){ this.categorie = "Enfant"; }
		else if (this.calculerAge() > 12){ this.categorie = "Adulte"; }
		else { this.categorie = "erreur"; }
	}
	
	// METHODEs SURCHARGEEs
	@Override
	public String toString() { 
		return 
			super.toString()+ System.getProperty("line.separator")
			+ "ELEVE, catégorie " + categorie + System.getProperty("line.separator");
	}
	
	// PROPRIETE
	public String 	getCategorie () 				{ return categorie; }
	public void 	setCategorie (String categorie) { this.categorie = categorie; }
}

