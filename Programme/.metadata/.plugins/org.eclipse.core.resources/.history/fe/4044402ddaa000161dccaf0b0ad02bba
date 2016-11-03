package POJO;

public class Reservation {
	// VARIABLES
	Semaine s;
	Cours 	c;
	private int heureDebut;
	private int heureFin;
	private int numReservation;
	
	// CONSTRUCTEURS
	public Reservation(){}
	public Reservation(int heureDebut, int heureFin, int numReservation, Semaine s, Cours c){
		this.heureDebut 		= heureDebut;
		this.heureFin 			= heureFin;
		this.numReservation 	= numReservation;
		this.c 					= c;
		this.s 					= s;
	}
	
	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return 
			"Heure de début de séance : "   + heureDebut + System.getProperty("line.separator") 
			+ "Heure de fin de sécuance : " + heureFin  + System.getProperty("line.separator") 
			+ s.toString() + System.getProperty("line.separator") 
			+ c.toString() + System.getProperty("line.separator"); }
	
	// PROPRIETE
	public int getHeureDebut			() { return heureDebut; }
	public int getHeureFin				() { return heureFin; }
	public int getNumReservation	() { return numReservation; }
	public void setHeureDebut		(int heureDebut) 			{ this.heureDebut = heureDebut; }
	public void setHeureFin			(int heureFin) 			{ this.heureFin = heureFin; }
	public void setReservationPayee	(int numReservation) 	{ this.numReservation = numReservation; }
}