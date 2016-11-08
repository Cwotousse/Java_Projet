package be.mousty.pojo;

public class ReservationClient {
	// VARIABLES
	private int numClient;
	private int numReservation;
	
	// CONSTRUCTEURS
	public ReservationClient(){}
	
	// PROPRIETE
	public int getNumClient			() { return numClient; }
	public int getNumReservation	() { return numReservation; }
	public void setNumClient		(int el) 	{ this.numClient = el; }
	public void setNumReservation	(int el) 	{ this.numReservation = el; }
}