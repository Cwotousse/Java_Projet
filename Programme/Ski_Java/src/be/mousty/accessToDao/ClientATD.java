package be.mousty.accessToDao;
/**
	Classe m�tier relatif li�e � la classe Client et ClientDAO.
	@author Adrien MOUSTY
	@version Finale 1.4.0
	@category M�tier
 */
import java.sql.Date;
import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Client;
import be.mousty.pojo.Eleve;
import be.mousty.pojo.Reservation;

public class ClientATD extends UtilisateurATD{
	// VARIABLES
	private String 	adresseFacturation;
	private ArrayList<ReservationATD> listReservCli = new ArrayList<ReservationATD>();
	private ArrayList<EleveATD> listEleveCli = new ArrayList<EleveATD>();

	// CONSTRUCTEURS
	public ClientATD(){}
	public ClientATD(Client C){
		super(C.getNom(), C.getPre(), C.getAdresse(), C.getSexe(), C.getDateNaissance(), C.getPseudo(),
				C.getMdp(), C.getTypeUtilisateur());
		this.adresseFacturation = C.getAdresseFacturation();
		ReservationATD RATD = new ReservationATD();
		EleveATD EATD = new EleveATD();
		this.listReservCli = RATD.getMyListATD(C.getNumClient());
		this.listEleveCli = EATD.getListEleveSelonidClient(C.getNumClient());
	}

	public ClientATD(String nom, String pre, String adresse, String sexe, Date dateNaissance,
			String pseudo, String mdp, int typeUtilisateur, String adresseFacturation, ArrayList<ReservationATD> listReservCli, ArrayList<EleveATD> listEleveCli){
		super(nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeUtilisateur);
		this.adresseFacturation = adresseFacturation;
		this.listReservCli = listReservCli;
		this.listEleveCli = listEleveCli;
	}

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf 	= AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Client> ClientDAO 	= adf.getClientDAO();
	public int					create				(Client c) 		{ return ClientDAO.create(c); 	 	}
	public boolean 				delete				()	 			{ return ClientDAO.delete(null); 	}
	public Client 				getId				(Client c) 		{ return ClientDAO.getId(c); 		}
	public boolean 				update				(Client c) 		{ return ClientDAO.update(c); 		}
	public Client 				find				(int id) 		{ return ClientDAO.find(id); 		} 
	public ArrayList<Client> 	getListCli			() 				{ return ClientDAO.getList(); 		} 
	public ArrayList<Client> 	getListSelonCriteres(Client c) 		{ return ClientDAO.getListSelonCriteres(c); 			}

	public boolean changeClientToEleve(int idClient){
		Client C = find(idClient);
		if(C != null){
			System.out.println("F_Client -> ajout eleve");
			//= new Eleve(, , , , ,);
			Eleve E = new Eleve();
			E.setNumEleve(C.getNumPersonne());
			this.setDateNaissance(C.getDateNaissance());
			E.setCategorie(this.attributerCategorie());
			E.setNumClient(idClient);
			E.setNumPersonne(C.getNumPersonne());
			E.setNom(C.getNom());
			E.setPre(C.getPre());
			E.setDateNaissance( C.getDateNaissance());
			E.setAdresse(C.getAdresse());
			E.setSexe(C.getSexe());
			EleveATD EATD = new EleveATD(E);
			if(EATD.create(E) != -1) return true;
		}
		return false;
	}

	public int inscriptionClient(){
		Client C = new Client();
		C.setAdresseFacturation(this.getAdresseFacturation());
		C.setPseudo(this.getPseudo());
		C.setMdp(this.getMdp());
		C.setTypeUtilisateur(2);
		C.setNom(this.getNom());
		C.setPre(this.getPre());
		C.setDateNaissance(this.getDateNaissance());
		C.setAdresse(this.getAdresse());
		C.setSexe(this.getSexe());
		return create(C);
	}

	// Modification 1.4.0
	// Transformation pour les listes r�servation
	/*public ArrayList<ReservationATD> changeTypeReservationList(ArrayList<Reservation> listR){
		try {
			ArrayList<ReservationATD> LE = new ArrayList<ReservationATD>();
			for(int i = 0; i < listR.size(); i++){
				ReservationATD RATD = new ReservationATD();
				RATD.setHeureDebut(listR.get(i).getHeureDebut());
				RATD.setHeureFin(listR.get(i).getHeureDebut());
				RATD.setAUneAssurance(listR.get(i).getAUneAssurance());
				RATD.setSemaine(listR.get(i).getSemaine());
				RATD.setCours(listR.get(i).getCours());
				RATD.setEleve(listR.get(i).getEleve());
				RATD.setClient(listR.get(i).getClient());
				RATD.setMoniteur(listR.get(i).getMoniteur());
				LE.add(RATD);
			}
			return LE;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}


	public ArrayList<Reservation> changeTypeReservationlistEnATD(ArrayList<ReservationATD> listReservationATD){
		try {
			ArrayList<Reservation> LE = new ArrayList<Reservation>();
			for(int i = 0; i < listReservationATD.size(); i++){
				Reservation R = new Reservation();
				R.setHeureDebut(listReservationATD.get(i).getHeureDebut());
				R.setHeureFin(listReservationATD.get(i).getHeureDebut());
				R.setAUneAssurance(listReservationATD.get(i).getAUneAssurance());
				R.setSemaine(listReservationATD.get(i).getSemaine());
				R.setCours(listReservationATD.get(i).getCours());
				R.setEleve(listReservationATD.get(i).getEleve());
				R.setClient(listReservationATD.get(i).getClient());
				R.setMoniteur(listReservationATD.get(i).getMoniteur());
				LE.add(R);
			}
			return LE;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}*/

	// Transformation pour les listes Eleve
	public ArrayList<EleveATD> EATD(ArrayList<Eleve> listE){
		try {
			ArrayList<EleveATD> LE = new ArrayList<EleveATD>();
			for(int i = 0; i < listE.size(); i++){
				EleveATD EATD = new EleveATD(listE.get(i));
				/*EATD.setAdresse(listE.get(i).getAdresse());
				EATD.setCategorie(listE.get(i).getCategorie());
				EATD.setDateNaissance(listE.get(i).getDateNaissance());
				EATD.setNom(listE.get(i).getNom());
				EATD.setPre(listE.get(i).getPre());
				EATD.setSexe(listE.get(i).getSexe());
				//System.out.println(EATD.getSexe());*/
				LE.add(EATD);
			}
			return LE;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}


	public ArrayList<Eleve> changeTypeElevelistEnATD(ArrayList<EleveATD> listEleveATD, int idClient){
		try {
			Client C = find(idClient);
			ArrayList<Eleve> LE = new ArrayList<Eleve>();
			for(int i = 0; i < listEleveATD.size(); i++){
				Eleve E = new Eleve();
				E.setNumClient(C.getNumClient());
				E.setDateNaissance(listEleveATD.get(i).getDateNaissance());
				E.setCategorie(listEleveATD.get(i).getCategorie());
				E.setNom(listEleveATD.get(i).getNom());
				E.setPre(listEleveATD.get(i).getPre());
				E.setAdresse(listEleveATD.get(i).getAdresse());
				E.setSexe(listEleveATD.get(i).getSexe());
				LE.add(E);
			}
			return LE;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	// PROPRIETES
	public String getAdresseFacturation	() 				{ return adresseFacturation; }
	public void setAdresseFacturation 	(String el) 	{ this.adresseFacturation  = el; }

	public ArrayList<ReservationATD> getListReservCli() { return listReservCli; }
	public void setListReservCli(ArrayList<ReservationATD> listReservCli) { this.listReservCli = listReservCli; }

	public ArrayList<EleveATD> getListEleveCli() { return listEleveCli; }
	public void setListEleveCli(ArrayList<EleveATD> listEleveCli) { this.listEleveCli = listEleveCli; }
}

