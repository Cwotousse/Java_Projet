package be.mousty.accessToDao;
/**
	Classe métier relatif liée à la classe DisponibiliteMoniteur et DisponibiliteMoniteurDAO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category Métier
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
		return valRetour; // Si true, ça a fonctionné.
	}
	
	
	// FONCTION SURCHARGEE
	@Override public String toString() { return  "Le moniteur est disponible : " + disponible + "."; }
	
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
	
	public boolean updateDispo(Date dateDebut, int numMoniteur){
		SemaineATD SATD = new SemaineATD();
		Semaine S = new Semaine();
		S.setDateDebut(dateDebut);
		boolean EtatDispo = true;
		S = SATD.getId(S); // numéro de semaine
		DisponibiliteMoniteur DM = new DisponibiliteMoniteur();
		DM.setNumSemaine(S.getNumSemaine());
		DM.setNumMoniteur(numMoniteur);
		if(getListSelonCriteres(DM)){
			// Après l'update, on récupère la dispo pour la semaine
			 ArrayList<DisponibiliteMoniteur> fullDispo  = getMyListSelonID(numMoniteur);
			 for(DisponibiliteMoniteur dm : fullDispo){
				 // On recherche le numéro de semaine
				 if (dm.getNumSemaine() == DM.getNumSemaine()){
					 EtatDispo = dm.getDisponible();
				 }
			 }
			 
			 // Si il est sur false on supprime ses réservation à la date indiquée
			 if (!EtatDispo){
				 ReservationATD RATD = new ReservationATD();
				 
			 }
		}
		
		return EtatDispo;
	}

	// PROPRIETE
	public boolean 	getDisponible	() 						{ return disponible; 			}
	public void 	setDisponible	(boolean disponible)	{ this.disponible = disponible; }
}

