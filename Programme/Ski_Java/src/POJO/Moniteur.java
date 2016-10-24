package POJO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Moniteur extends Utilisateur{
	// VARIABLES 
	private int anneeDexp;
	private int numMoniteur;
	private ArrayList<Accreditation> listAccreditation = new ArrayList<Accreditation>();
	
	// CONSTRUCTEURS
	public Moniteur(){}
	public Moniteur(String nom, String pre, String adresse, String sexe, Date dateNaissance, 
			String pseudo, String mdp, int typeUtilisateur){
		super(nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeUtilisateur);
		this.anneeDexp 	= 0 ;// A CHANGER via une methode calculerAnneeExp
		//this.numMoniteur = numMoniteur;
	}
	
	// METHODES 
	public void ajouterMoniteur() throws Exception{
		Scanner dateDiplomeMoniteur = new Scanner(System.in);
		Scanner sportPratiqueMoniteur = new Scanner(System.in);
		Scanner evaluationMoniteur = new Scanner(System.in);
		System.out.println("Ajout d'un moniteur");
		super.ajouterPersonne();
		
		System.out.print("Date de dipl�me du moniteur [jj-mm-yyyy] : "); setNom(dateDiplomeMoniteur.next());
		System.out.print("Sport enseign� par le moniteur : "); setNom(sportPratiqueMoniteur.next());
		System.out.print("Evaluation du moniteur (/10) : "); setNom(evaluationMoniteur.next());
	}
	
	// Pour ne pas additionner 2 fois le m�me moniteur
	public void addAccreditation(Accreditation ac){
		if(!listAccreditation.contains(ac))
			listAccreditation.add(ac);
	}
	public void removeAccreditation(Accreditation ac){ this.listAccreditation.remove(ac); }
	public boolean equals(Moniteur mo){ return this.getNumMoniteur() == mo.getNumMoniteur(); }

	
	// METHODE SURCHARGEE
	@Override
	public String toString() { 
		return 
			super.toString()+ System.getProperty("line.separator")
			+ "MONITEUR, a accumule" + anneeDexp + " ann�e(s) d'ex�rience." + System.getProperty("line.separator");
	}
	
	// PROPRIETES
	public int getAnneeExp		() 			{ return anneeDexp; }
	public int getNumMoniteur	() 			{ return numMoniteur; }
	public ArrayList<Accreditation> getAccrediList() { return listAccreditation; }
	public void setNumMoniteur	(int el) { this.numMoniteur = el;}
	public void setAnneeExp 	(int el) 	{ this.anneeDexp = el; }
	public void setAccrediList(ArrayList<Accreditation> accrediList) { this.listAccreditation = accrediList; 	}
}
