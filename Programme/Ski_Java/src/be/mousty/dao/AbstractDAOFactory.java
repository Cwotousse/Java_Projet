package be.mousty.dao;
/**
	Classe DAO permettant à effectuer des requêtes et les transformer en objet POJO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category DAO
*/
import be.mousty.pojo.Accreditation;
import be.mousty.pojo.Client;
import be.mousty.pojo.Cours;
import be.mousty.pojo.CoursCollectif;
import be.mousty.pojo.CoursParticulier;
import be.mousty.pojo.DisponibiliteMoniteur;
import be.mousty.pojo.Eleve;
import be.mousty.pojo.Moniteur;
import be.mousty.pojo.Personne;
import be.mousty.pojo.Reservation;
import be.mousty.pojo.Semaine;
import be.mousty.pojo.Utilisateur;

public abstract class AbstractDAOFactory {
	public static final int DAO_FACTORY 	= 0;
	public static final int XML_DAO_FACTORY = 1;

	public abstract DAO<Utilisateur> 			getUtilisateurDAO();
	public abstract DAO<Personne> 				getPersonneDAO();
	public abstract DAO<Client> 				getClientDAO();
	public abstract DAO<Moniteur> 				getMoniteurDAO();
	public abstract DAO<Eleve> 					getEleveDAO();
	public abstract DAO<Accreditation> 			getAccreditationDAO();
	public abstract DAO<Semaine> 				getSemaineDAO();
	public abstract DAO<Cours> 					getCoursDAO();
	public abstract DAO<CoursParticulier> 		getCoursParticulierDAO();
	public abstract DAO<CoursCollectif> 		getCoursCollectifDAO();
	public abstract DAO<Reservation> 			getReservationDAO();
	public abstract DAO<DisponibiliteMoniteur> 	getDisponibiliteMoniteurDAO();

	public static AbstractDAOFactory getFactory(int type){
		switch(type){
			case DAO_FACTORY: return new DAOFactory();
			default: return null;
		}
	}
	
}
