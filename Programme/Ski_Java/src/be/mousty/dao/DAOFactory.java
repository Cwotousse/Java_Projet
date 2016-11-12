package be.mousty.dao;

import java.sql.Connection;

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

public class DAOFactory extends AbstractDAOFactory{
	protected static final Connection conn = SingletonConnection.getInstance();
	
	public DAO<Utilisateur> 			getUtilisateurDAO			() { return new UtilisateurDAO(conn); 			}
	public DAO<Personne> 				getPersonneDAO				() { return new PersonneDAO(conn); 				}
	public DAO<Client> 					getClientDAO				() { return new ClientDAO(conn); 				}
	public DAO<Moniteur> 				getMoniteurDAO				() { return new MoniteurDAO(conn); 				}
	public DAO<Eleve> 					getEleveDAO					() { return new EleveDAO(conn); 				}
	public DAO<Accreditation> 			getAccreditationDAO			() { return new AccreditationDAO(conn);	 		}
	public DAO<Semaine> 				getSemaineDAO 				() { return new SemaineDAO(conn); 				}
	public DAO<Cours> 					getCoursDAO 				() { return new CoursDAO(conn); 				}
	public DAO<CoursParticulier>		getCoursParticulierDAO 		() { return new CoursParticulierDAO(conn); 		}
	public DAO<CoursCollectif> 			getCoursCollectifDAO 		() { return new CoursCollectifDAO(conn); 		}
	public DAO<Reservation> 			getReservationDAO 			() { return new ReservationDAO(conn); 			}
	public DAO<DisponibiliteMoniteur> 	getDisponibiliteMoniteurDAO	() { return new DisponibiliteMoniteurDAO(conn);	}
}