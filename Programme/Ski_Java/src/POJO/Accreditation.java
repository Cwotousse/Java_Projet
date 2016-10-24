package POJO;

public class Accreditation {
	// VARIABLES
	private String nom;
	private int numAccreditation;
	
	// CONSTRUCTEURS
	public Accreditation(){}
	public Accreditation(String nom){
		this.nom = nom;
	}
	
	// FONCTION SURCHARGEE
	@Override
	public String toString() { return nom; }
	
	// PROPRIETE
	public String 	getNom				() { return nom; }
	public int 		getNumAccreditation	() { return numAccreditation; }
	public void setNom					(String nom) 			{ this.nom = nom; }
	public void setNumAccreditation		(int numAccreditation) 	{ this.numAccreditation = numAccreditation; }
}
