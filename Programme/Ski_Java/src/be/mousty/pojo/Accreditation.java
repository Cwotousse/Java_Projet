package be.mousty.pojo;

public class Accreditation {
	// VARIABLES
	private String 	nomAccreditation;
	private int 	numAccreditation;
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Accreditation(){}
	
	// PROPRIETE
	public String 	getNomAccreditation	() { return nomAccreditation; }
	public int 		getNumAccreditation	() { return numAccreditation; }
	public void 	setNomAccreditation		(String nomAccreditation) 			{ this.nomAccreditation = nomAccreditation; }
	public void 	setNumAccreditation		(int numAccreditation) 	{ this.numAccreditation = numAccreditation; }
}
