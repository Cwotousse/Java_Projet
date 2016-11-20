package be.mousty.pojo;

import be.mousty.accessToDao.ReservationATD;

/**
	Classe POJO relatif � la table Reservation dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/
public class Reservation {
	// VARIABLES
	private int 	numReservation;
	private int 	heureDebut;
	private int 	heureFin;
	private boolean aPrisAssurance;
	private boolean aPaye;
	private Semaine S;
	private Cours 	C;
	private Eleve	E;
	private Client	Cli;
	private Moniteur M;
	//private ArrayList<Cours> listCours = new ArrayList<Cours>();
	
	// CONSTRUCTEURS
	public Reservation(){}

	public Reservation(int heureDebut, int heureFin, int numReservation, boolean aUneAssurance, Semaine S, Cours C,
			Eleve E, Client Cli, Moniteur M, boolean aPaye) {
		this.heureDebut 	= heureDebut;
		this.heureFin 		= heureFin;
		this.aPrisAssurance 	= aUneAssurance;
		this.aPaye 			= aPaye;
		this.C 				= C;
		this.S 				= S;
		this.E 				= E;
		this.Cli 			= Cli;
		this.M 				= M;
	}

	public Reservation(ReservationATD R) {
		this.heureDebut 	= R.getHeureDebut();
		this.heureFin 		= R.getHeureFin();
		this.aPrisAssurance = R.getAUneAssurance();
		this.aPaye 			= R.getaPaye();
		Cours C 			= R.getCours();
		Semaine S			= R.getSemaine();
		Eleve E				= R.getEleve();
		Client Cli 			= R.getClient();
		Moniteur M 			= R.getMoniteur();
		this.C 				= C;
		this.S 				= S;
		this.E 				= E;
		this.Cli 			= Cli;
		this.M 				= M;
	}
	
	// PROPRIETE
	public int 		getHeureDebut		() { return heureDebut; }
	public int 		getHeureFin			() { return heureFin; }
	public int 		getNumReservation	() { return numReservation; }
	public Semaine 	getSemaine			() { return S;}
	public Cours 	getCours			() { return C;}
	public Eleve 	getEleve			() { return E;}
	public Client 	getClient			() { return Cli;}
	public Moniteur getMoniteur			() { return M;}
	public boolean 	getAUneAssurance	() { return aPrisAssurance; }
	public void setHeureDebut			(int heureDebut) 		{ this.heureDebut = heureDebut; }
	public void setHeureFin				(int heureFin) 			{ this.heureFin = heureFin; }
	public void setNumReservation		(int numReservation) 	{ this.numReservation = numReservation; }
	public void setAUneAssurance		(boolean aUneAssurance) { this.aPrisAssurance = aUneAssurance; }
	public void setSemaine 				(Semaine S)  			{ this.S = S;}
	public void setCours 				(Cours C)  				{ this.C = C;}
	public void setEleve 				(Eleve E)  				{ this.E = E;}
	public void setClient 				(Client Cli)  			{ this.Cli = Cli;}
	public void setMoniteur 			(Moniteur M)  			{ this.M = M;}

	//public ArrayList<Cours> 	getListCours() 							{ return listCours; 			}
	//public void 				setListCours(ArrayList<Cours> listReserv) 	{ this.listCours = listReserv; }

	public boolean 	getaPaye() 				{ return aPaye; }
	public void 	setaPaye(boolean aPaye) { this.aPaye = aPaye; }
}