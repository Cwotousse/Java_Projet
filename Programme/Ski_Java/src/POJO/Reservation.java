package POJO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
	// VARIABLES
	Semaine s;
	Cours 	c;
	private Date 	heureDebut;
	private Date 	heureFin;
	private boolean reservationPayee;
	
	SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm");
	// CONSTRUCTEURS
	public Reservation(){}
	public Reservation(Date heureDebut, Date heureFin, boolean reservationPayee, Semaine s, Cours c){
		this.heureDebut 		= heureDebut;
		this.heureFin 			= heureFin;
		this.reservationPayee 	= reservationPayee;
		this.c 					= c;
		this.s 					= s;
	}
	
	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return 
			"Heure de début de séance : "   + hourFormat.format(heureDebut) + System.getProperty("line.separator") 
			+ "Heure de fin de sécuance : " + hourFormat.format(heureFin)  + System.getProperty("line.separator") 
			+ "Réservation payée : "+ reservationPayee + System.getProperty("line.separator") 
			+ s.toString() + System.getProperty("line.separator") 
			+ c.toString() + System.getProperty("line.separator"); }
	
	// PROPRIETE
	public Date getHeureDebut			() { return heureDebut; }
	public Date getHeureFin				() { return heureFin; }
	public boolean getReservationPayee	() { return reservationPayee; }
	public void setHeureDebut		(Date heureDebut) 			{ this.heureDebut = heureDebut; }
	public void setHeureFin			(Date heureFin) 			{ this.heureFin = heureFin; }
	public void setReservationPayee	(boolean reservationPayee) 	{ this.reservationPayee = reservationPayee; }
}