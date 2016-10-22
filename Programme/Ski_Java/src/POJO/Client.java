package POJO;

import java.util.Date;
import java.util.Scanner;

public class Client extends Utilisateur{
	// VARIABLES
	private String numCarteBanq;
	
	// CONSTRUCTEURs
	public Client(){}
	public Client(String nom, String pre, String ville, String rue, String sexe, Date dateNaissance,
			String pseudo, String mdp, int typeUtilisateur, String numCarteBanq){
		super(nom, pre, ville, rue, sexe, dateNaissance, pseudo, mdp, typeUtilisateur);
		this.numCarteBanq = numCarteBanq;
	}
	
	// METHODES
	public void ajouterClient() throws Exception{
		Scanner numCarteBanqClient = new Scanner(System.in);
		System.out.println("Ajout d'un client");
		super.ajouterPersonne();
		
		System.out.print("Numéro de carte bancaire : "); setNom(numCarteBanqClient.next());
	}
	
	
	// METHODES SURCHARGEES
	@Override
	public String toString() { 
		return 
			super.toString()+ System.getProperty("line.separator")
			+ "CLIENT." + System.getProperty("line.separator")
			+ "N° de carte  : " + numCarteBanq + System.getProperty("line.separator");
	}
	
	// PROPRIETES
	public String getNumCarteBanq() { return numCarteBanq; }
	public void setNumCarteBanq(String numCarteBanq) { this.numCarteBanq = numCarteBanq; }
}
