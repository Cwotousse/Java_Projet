package POJO;

import java.util.Date;

public class LigneReservationEleve{
private Date dateReservationEl;
	
	// CONSTRUCTEUR
	public LigneReservationEleve(){
		Date d1 = new Date();
		this.dateReservationEl = d1; // now
	}
	
	// PROPRIETE
	public Date getDateReservationEtud 	() 			{ return dateReservationEl;}
	public void setDateReservationEtud 	(Date el)	{ this.dateReservationEl = el;}
}
