package be.mousty.pojo;

public class Reservation {
	// VARIABLES
	private int numReservation;
	private int heureDebut;
	private int heureFin;
	private boolean aPrisAssurance;
	Semaine S;
	Cours 	C;
	Eleve	E;
	Client	Cli;
	Moniteur M;
	
	
	
	// CONSTRUCTEURS
	public Reservation(){}
	
	// PROPRIETE
	public int 		getHeureDebut		() { return heureDebut; }
	public int 		getHeureFin			() { return heureFin; }
	public int 		getNumReservation	() { return numReservation; }
	public Semaine 	getSemaine			() { return S;}
	public Cours 	getCours			() { return C;}
	public Eleve 	getEleve			() { return E;}
	public Client 	getClient			() { return Cli;}
	public Moniteur getMoniteur			() { return M;}
	public boolean 	getAUneAssurance() { return aPrisAssurance; }
	public void setHeureDebut		(int heureDebut) 			{ this.heureDebut = heureDebut; }
	public void setHeureFin			(int heureFin) 			{ this.heureFin = heureFin; }
	public void setNumReservation	(int numReservation) 	{ this.numReservation = numReservation; }
	//public void serMoniteur(Moniteur M)  { this.M = M;}
	
	public void setAUneAssurance(boolean aUneAssurance) { this.aPrisAssurance = aUneAssurance; }
}