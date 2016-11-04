package POJO;

import DAO.AbstractDAOFactory;
import DAO.DAO;

public class Reservation {
	// VARIABLES
	Semaine S;
	Cours 	C;
	Eleve	E;
	Client	Cli;
	Moniteur M;
	private int heureDebut;
	private int heureFin;
	private int numReservation;
	private boolean aUneAssurance;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Reservation> ReservationDAO = adf.getReservationDAO();
	
	// CONSTRUCTEURS
	public Reservation(){}
	public Reservation(int heureDebut, int heureFin, int numReservation, boolean aUneAssurance, Semaine S, Cours C, Eleve E, Client Cli, Moniteur M){
		this.heureDebut 		= heureDebut;
		this.heureFin 			= heureFin;
		this.numReservation 	= numReservation;
		this.aUneAssurance 		= aUneAssurance;
		this.C 					= C;
		this.S 					= S;
		this.E 					= E;
		this.Cli 				= Cli;
		this.M 					= M;
	}
	
	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return 
			"Heure de d�but de s�ance : " + heureDebut + System.getProperty("line.separator") 
			+ "Heure de fin de s�ance : " + heureFin  + System.getProperty("line.separator") 
			+ S.toString() + System.getProperty("line.separator") 
			+ C.toString() + System.getProperty("line.separator")
			+ E.toString() + System.getProperty("line.separator")
			+ Cli.toString() + System.getProperty("line.separator")
			+ M.toString() + System.getProperty("line.separator"); }
	
	// METHODE
	public int createReservation	() { return ReservationDAO.create(this); }
	public void deleteReservation	() { ReservationDAO.delete(null); }
	
	// PROPRIETE
	public int 		getHeureDebut		() { return heureDebut; }
	public int 		getHeureFin			() { return heureFin; }
	public int 		getNumReservation	() { return numReservation; }
	public Semaine 	getSemaine			() { return S;}
	public Cours 	getCours			() { return C;}
	public Eleve 	getEleve			() { return E;}
	public Client 	getClient			() { return Cli;}
	public Moniteur getMoniteur			() { return M;}
	public boolean 	getAUneAssurance() { return aUneAssurance; }
	public void setHeureDebut		(int heureDebut) 			{ this.heureDebut = heureDebut; }
	public void setHeureFin			(int heureFin) 			{ this.heureFin = heureFin; }
	public void setNumReservation	(int numReservation) 	{ this.numReservation = numReservation; }
	//public void serMoniteur(Moniteur M)  { this.M = M;}
	
	public void setAUneAssurance(boolean aUneAssurance) { this.aUneAssurance = aUneAssurance; }
}