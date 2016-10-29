package POJO;

import java.sql.Date;
import java.util.Scanner;

public class Eleve extends Personne{
	// VARIABLES
	private boolean aUneAssurance;
	private String categorie;
	private int numEleve;
	
	// CONSTRUCTEURS
	public Eleve(){}
	
	public Eleve(String categorie, boolean aUneAssurance){
		//this.numEleve 		= numEleve;
		this.aUneAssurance 	= aUneAssurance;
		this.categorie 		= categorie;
	}
	
	public Eleve(int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance){
		super(numPersonne, nom, pre, adresse, sexe, dateNaissance);
		//this.aUneAssurance 			= aUneAssurance;
		attributerCategorie();
	}
	
	// METHODEs
	private void attributerCategorie(){
		if(this.calculerAge() <= 12 && this.calculerAge() > 4){ this.categorie = "enfant"; }
		else if (this.calculerAge() > 12){ this.categorie = "adulte"; }
		else { this.categorie = "erreur"; }
		//
	}
	
	/*public void ajouterEleve() throws Exception{
		Scanner aUneAssuranceEleve = new Scanner(System.in);
		Scanner categoriee = new Scanner(System.in);
		System.out.println("Ajout d'un �l�ve");
		super.ajouterPersonne();
		
		System.out.print("L'el�ve poss�de t-il une assurance ? [true/false] "); setNom(aUneAssuranceEleve.next());
		attributerCategorie();
	}*/
	
	// METHODEs SURCHARGEEs
	@Override
	public String toString() { 
		return 
			super.toString()+ System.getProperty("line.separator")
			+ "ELEVE, cat�gorie " + categorie + System.getProperty("line.separator")
			+ "Poss�de une assurance : " + aUneAssurance + System.getProperty("line.separator");
	}
	
	// PROPRIETE
	public boolean getAUneAssurance		() { return aUneAssurance; }
	public String getCategorie			() { return categorie; }
	public int getNumEleve				() { return numEleve; }
	public void setAUneAssurance		(boolean aUneAssurance) 	{ this.aUneAssurance = aUneAssurance; }
	public void setCategorie			(String categorie) 			{ this.categorie = categorie; }
	public void setNumEleve				(int numEleve) 				{ this.numEleve = numEleve; }
	

	
}
