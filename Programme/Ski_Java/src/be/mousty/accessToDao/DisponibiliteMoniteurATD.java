package be.mousty.accessToDao;
/**
	Classe m�tier relatif li�e � la classe DisponibiliteMoniteur et DisponibiliteMoniteurDAO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category M�tier
 */
import java.sql.Date;
import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.DisponibiliteMoniteur;
import be.mousty.pojo.Semaine;

public class DisponibiliteMoniteurATD {
	// VARIABLES
	private boolean disponible;

	// CONSTRUCTEURS
	public DisponibiliteMoniteurATD(){}
	public DisponibiliteMoniteurATD(boolean disponible){ this.disponible 	= disponible; }
	public DisponibiliteMoniteurATD(DisponibiliteMoniteur DM){ this.disponible 	= DM.getDisponible(); }


	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<DisponibiliteMoniteur> DisponibiliteMoniteurDAO = adf.getDisponibiliteMoniteurDAO();
	public int						create	(DisponibiliteMoniteur d) 		{ return DisponibiliteMoniteurDAO.create(d); 	}
	public boolean 					delete	()	 							{ return DisponibiliteMoniteurDAO.delete(null); }
	public DisponibiliteMoniteur 	getId	(DisponibiliteMoniteur d) 		{ return DisponibiliteMoniteurDAO.getId(d); 	}
	public boolean 					update	(DisponibiliteMoniteur d) 		{ return DisponibiliteMoniteurDAO.update(d); 	}
	public DisponibiliteMoniteur 	find	(int id) 						{ return DisponibiliteMoniteurDAO.find(id); 	} 
	public ArrayList<DisponibiliteMoniteur> getListCP 			() 					{ return DisponibiliteMoniteurDAO.getList(); 	} 
	public ArrayList<DisponibiliteMoniteur> getMyListSelonID	(int idMoniteur)	{ return DisponibiliteMoniteurDAO.getMyListSelonID(idMoniteur, -1, -1, ""); 	}
	public void creerTouteDisponibilites						()					{ DisponibiliteMoniteurDAO.creerTouteDisponibilites();		}
	public void creerTouteDisponibilitesSelonMoniteur			(int numMoniteur) 	{ DisponibiliteMoniteurDAO.creerTouteDisponibilitesSelonMoniteur(numMoniteur); }
	public boolean getListSelonCriteres(DisponibiliteMoniteur obj) {
		ArrayList<DisponibiliteMoniteur> listBoolean = DisponibiliteMoniteurDAO.getListSelonCriteres(obj);
		boolean valRetour = listBoolean == null ? true : false; 
		return valRetour; // Si true, �a a fonctionn�.
	}

	// METHODES
	public ArrayList<DisponibiliteMoniteurATD> getListDispo(int numMoniteur){
		ArrayList<DisponibiliteMoniteur> listDispo  = getMyListSelonID(numMoniteur);
		ArrayList<DisponibiliteMoniteurATD> listDispoATD = new ArrayList<DisponibiliteMoniteurATD>();
		for(int i = 0; i < listDispo.size(); i++){
			DisponibiliteMoniteurATD DMATD = new DisponibiliteMoniteurATD();
			DMATD.setDisponible(listDispo.get(i).getDisponible());
			listDispoATD.add(DMATD);
		}
		return listDispoATD;
	}

	/**
	 * Objectif : Savoir si le moniteur doit prester des cours � une semaine donn�e, si oui, il ne peut pas modifier son statut
	 * @param dateDebut
	 * @param numMoniteur
	 * @return
	 */
	public boolean updateDispo(Date dateDebut, int numMoniteur){
		ReservationATD RATD = new ReservationATD();
		if(!RATD.ceMoniteurADesReservationsPourCetteSemaine(dateDebut, numMoniteur)) {
			// S'il n'a pas de cours on peut modifier la dispo.
			SemaineATD SATD = new SemaineATD();
			Semaine S = new Semaine();
			S.setDateDebut(dateDebut);
			S = SATD.getId(S); // num�ro de semaine
			DisponibiliteMoniteur DM = new DisponibiliteMoniteur();
			DM.setNumSemaine(S.getNumSemaine());
			DM.setNumMoniteur(numMoniteur);
			return getListSelonCriteres(DM);
		}
		return false;
	}

	/**
	 * Objectif : Savoir si le moniteur peut odate son statut vis � vis des cours particuliers.
	 * @param numMoniteur
	 * @return
	 */
	public boolean possedeCoursParticulier(int numMoniteur){
		ReservationATD RATD = new ReservationATD();
		return !RATD.ceMoniteurDoitPresterCoursParticulier(numMoniteur);
	}


	/*
	 * public boolean updateDispo(Date dateDebut, int numMoniteur){
		SemaineATD SATD = new SemaineATD();
		Semaine S = new Semaine();
		S.setDateDebut(dateDebut);
		//boolean EtatDispo = true;
		//long numSemaine;
		S = SATD.getId(S); // num�ro de semaine
		DisponibiliteMoniteur DM = new DisponibiliteMoniteur();
		DM.setNumSemaine(S.getNumSemaine());
		//numSemaine = S.getNumSemaine();
		DM.setNumMoniteur(numMoniteur);
		//if(){
			// Apr�s l'update, on r�cup�re la dispo pour la semaine
			//ArrayList<DisponibiliteMoniteur> fullDispo  = getMyListSelonID(numMoniteur);
			//for(DisponibiliteMoniteur dm : fullDispo){
				// On recherche le num�ro de semaine
				//if (dm.getNumSemaine() == DM.getNumSemaine()){
					//EtatDispo = dm.getDisponible();
				//}
			//}

			// Si il est sur false on rechercher un nouveau moniteur pour le suppl�er ses r�servation � la date indiqu�e
			//if (!EtatDispo){
				// Il faut r�cup�rer ses r�servations, s'il en a.
				ReservationATD RATD = new ReservationATD();
				ArrayList<ReservationATD> listReservationMoniteurSelonNumSemaine = new ArrayList<ReservationATD>();
				ArrayList<ReservationATD> listFullReservation = RATD.getMyListATD(numMoniteur);
				for(ReservationATD ratd : listFullReservation){
					if (ratd.getSemaine().getDateDebut().equals(dateDebut)){
						listReservationMoniteurSelonNumSemaine.add(ratd);
					}
				}
				// Il y a des reservations dispo
				if (!listReservationMoniteurSelonNumSemaine.isEmpty()){
					//System.out.println("Des r�servations ont �t�s trouv�es ("+ listReservationMoniteurSelonNumSemaine.size() +"). Nous proc�dons � l'attribution d'un nouveau moniteur pour vos cours");
					System.out.println("Vous ne pouvez pas mettre � jour votre statut, des cours sont en attente d'�tre prest�.");

				}
				else {
					//System.out.println("Update");
					return getListSelonCriteres(DM);
				}
					/*MoniteurATD ancienMoniteur = new MoniteurATD();
					ancienMoniteur = ancienMoniteur.findM(numMoniteur);


					ArrayList <MoniteurATD> listeMoniteurAyantLesM�mesAccreditations = new ArrayList <MoniteurATD>();
					int typeCours = 1; // 1 -> collectif, 2 -> particulier
					// Recherche d'un nouveau moniteur pour chaque cours
					for(ReservationATD ratd : listReservationMoniteurSelonNumSemaine){
						// Pour la r�servation il faut trouver le type de cours donn�, s'il correspond avec une accred alors on le prend (on exclu aussi le moniteur qui se d�siste)
						ArrayList <MoniteurATD> listeMoniteursDispo = new ArrayList <MoniteurATD>();
						ArrayList <MoniteurATD> listeMoniteursDispoAvecAccred = new ArrayList <MoniteurATD>();
						if (ratd.getMoniteur().getNumMoniteur() != ancienMoniteur.getNumId()){
							if (ratd.getCours().getPrix() < 90) { 
								// Cours particulier
								listeMoniteursDispo = ancienMoniteur.getListDispoATD(2, dateDebut.getTime(), ratd.getCours().getPeriodeCours());
							}
							else {
								// coursCollectif
								listeMoniteursDispo = ancienMoniteur.getListDispoATD(1, numSemaine, ratd.getCours().getPeriodeCours());
							}
						}

						// Il y au moins un moniteur disponible, on verifie ses accreditations
						if (!listeMoniteursDispo.isEmpty()){


							for(MoniteurATD mon : listeMoniteursDispo){
								ArrayList<AccreditationATD> listaccredMoniteur = mon.getAccrediList();
								for(AccreditationATD A : listaccredMoniteur){
									// On compare les accr�ditations avec les moniteurs disponibles
									boolean trouve = false;
									if (A.getNom().equals(ratd.getCours().getNomSport()) && !trouve){
										System.out.println("Un moniteur a �t� trouv�, nous v�rifions le nombre maximum d'�l�ves.");
										trouve = true;
									}
								}

							}

						}
						// On supprime le cours
						else {
							System.out.println("Aucun moniteur n'a �t� trouv�, un le cours de " + ratd.getCours().getNomSport() + " a �t� supprim�.");

						}
					}

					// S'il n'y a pas de moniteurs, dispo, on supprime les r�servations
					//ReservationATD RATD = new ReservationATD();
				//}

			//}
		//}

		return false;
	}
	 * */
	// PROPRIETE
	public boolean 	getDisponible	() 						{ return disponible; 			}
	public void 	setDisponible	(boolean disponible)	{ this.disponible = disponible; }
}

