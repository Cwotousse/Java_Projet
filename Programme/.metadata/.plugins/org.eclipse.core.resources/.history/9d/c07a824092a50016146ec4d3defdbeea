package DAO;

import java.sql.Connection;

import POJO.Accreditation;
import POJO.Client;
import POJO.Cours;
import POJO.CoursCollectif;
import POJO.CoursParticulier;
import POJO.DisponibiliteMoniteur;
import POJO.Eleve;
import POJO.Moniteur;
import POJO.Personne;
import POJO.Reservation;
import POJO.Semaine;
import POJO.Utilisateur;

public class DAOFactory extends AbstractDAOFactory{
protected static final Connection conn = SingletonConnection.getInstance();
	
	public DAO<Utilisateur> 			getUtilisateurDAO			() { return new UtilisateurDAO(conn); }
	public DAO<Personne> 				getPersonneDAO				() { return new PersonneDAO(conn); }
	public DAO<Client> 					getClientDAO				() { return new ClientDAO(conn); }
	public DAO<Moniteur> 				getMoniteurDAO				() { return new MoniteurDAO(conn); }
	public DAO<Eleve> 					getEleveDAO					() { return new EleveDAO(conn); }
	public DAO<Accreditation> 			getAccreditationDAO			() { return new AccreditationDAO(conn); }
	public DAO<Semaine> 				getSemaineDAO 				() { return new SemaineDAO(conn); }
	public DAO<Cours> 					getCoursDAO 				() { return new CoursDAO(conn); }
	public DAO<CoursParticulier>		getCoursParticulierDAO 		() { return new CoursParticulierDAO(conn); }
	public DAO<CoursCollectif> 			getCoursCollectifDAO 		() { return new CoursCollectifDAO(conn); }
	public DAO<Reservation> 			getReservationDAO 			() { return new ReservationDAO(conn); }
	public DAO<DisponibiliteMoniteur> 	getDisponibiliteMoniteurDAO	() { return new DisponibiliteMoniteurDAO(conn);	}
}