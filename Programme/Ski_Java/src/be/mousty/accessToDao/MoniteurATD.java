package be.mousty.accessToDao;
/**
	Classe m�tier relatif li�e � la classe Moniteur et MoniteurDAO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category M�tier
 */
import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Moniteur;

public class MoniteurATD extends UtilisateurATD{
	// VARIABLES 
	private int anneeDexp;
	private boolean disponiblecoursParticulier;
	private ArrayList<AccreditationATD> listAccreditation = new ArrayList<AccreditationATD>();

	// CONSTRUCTEURS
	public MoniteurATD(){}
	public MoniteurATD(String nom, String pre, String adresse, String sexe, Date dateNaissance, 
			String pseudo, String mdp, int typeMoniteur, ArrayList<AccreditationATD> listAccreditation, boolean disponiblecoursParticulier){
		super(nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeMoniteur);
		Random r = new Random();
		this.anneeDexp 	= r.nextInt(10 - 0 + 1) + 0; // entre 10 et 0
		this.listAccreditation = listAccreditation;
		this.disponiblecoursParticulier = disponiblecoursParticulier;
	}

	public MoniteurATD(Moniteur M){
		super(M.getNom(), M.getPre(), M.getAdresse(), M.getSexe(), M.getDateNaissance(), M.getPseudo(), M.getMdp(), M.getTypeUtilisateur());
		this.anneeDexp 	= M.getAnneeExp(); // entre 10 et 0
		this.listAccreditation = AccreditationATD.changeTypeAccredilist(M.getAccrediList());
		this.disponiblecoursParticulier = M.getDisponiblecoursParticulier();
	}



	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Moniteur> MoniteurDAO = adf.getMoniteurDAO();
	public int					create				(Moniteur m) 	{ return MoniteurDAO.create(m); 				}
	public boolean 				delete				()	 			{ return MoniteurDAO.delete(null); 				}
	public Moniteur 			getId				(Moniteur m) 	{ return MoniteurDAO.getId(m); 					}
	public boolean 				update				(Moniteur m) 	{ return MoniteurDAO.update(m); 				}
	public Moniteur 			find				(int id) 		{ return MoniteurDAO.find(id); 					} 
	public ArrayList<Moniteur> 	getListMon			() 				{ return MoniteurDAO.getList(); 				} 
	public ArrayList<Moniteur> 	getListSelonCriteres(Moniteur m) 	{ return MoniteurDAO.getListSelonCriteres(m); 	}
	public ArrayList<Moniteur> 	getListDispo		(int typeCours, long numSemaine, String periode) { return MoniteurDAO.getMyListSelonID(typeCours, numSemaine, -1,  periode); }

	// METHODES 	
	public MoniteurATD findM (int id){

		Moniteur M = find(id);
		MoniteurATD MATD = new MoniteurATD(M);
		return MATD;
	}

	public int inscriptionMoniteur(){
		Moniteur M = new Moniteur();
		M.setAnneeExp(0);
		M.setAccrediList(AccreditationATD.changeTypeAccredilistEnATD(getAccrediList()));
		M.setDisponiblecoursParticulier(getDisponiblecoursParticulier());
		M.setPseudo(getPseudo());
		M.setMdp(getMdp());
		M.setTypeUtilisateur(1);
		M.setNom(getNom());
		M.setPre(getPre());
		M.setDateNaissance(getDateNaissance());
		M.setAdresse(getAdresse());
		M.setSexe(getSexe());
		return create(M);
	}

	public ArrayList<MoniteurATD> getListDispoATD(int typeCours, long numSemaine, String nomAccred, String periode){
		ArrayList<Moniteur> listM = getListDispo(typeCours, numSemaine, periode);
		ArrayList<MoniteurATD> listMATD = new ArrayList<MoniteurATD>();
		ArrayList<MoniteurATD> listFiltree = new ArrayList<MoniteurATD>();
		for (int i = 0; i < listM.size(); i++) {
			MoniteurATD MATD = new MoniteurATD(listM.get(i));
			listMATD.add(MATD);
		}
		for (int k = 0; k < listMATD.size() ; k++ ){
			for (int j = 0; j < listMATD.get(k).getAccrediList().size(); j++) {
				// On compare la liste des moniteurs avec le cours choisi, s'il correspond alors on l'ajoute � la liste.
				if (nomAccred.equals(listMATD.get(k).getAccrediList().get(j).getNom()))
					listFiltree.add(listMATD.get(k));
			}
		}
		return listFiltree;
	}

	public int getIdATD(MoniteurATD MATD){
		Moniteur M = new Moniteur();
		M.setAdresse(MATD.getAdresse());
		M.setAnneeExp(MATD.getAnneeExp());
		M.setDisponiblecoursParticulier(MATD.getDisponiblecoursParticulier());
		M.setAccrediList(AccreditationATD.changeTypeAccredilistEnATD(MATD.getAccrediList()));
		M.setDateNaissance(MATD.getDateNaissance());
		M.setNom(MATD.getNom());
		M.setPre(MATD.getPre());
		M.setSexe(MATD.getSexe());
		M.setNumMoniteur(-1);
		M.setNumPersonne(-1);
		M.setNumUtilisateur(-1);
		return  getId(M).getNumPersonne();
	}

	// IL FAUT LA DEPLACER DANS DISPONIBILITEMONITEUR
	// Mise � jour de la disponibilite pour les cours particulier du moniteur
	public boolean updateDispo	(int numMoniteur) {
		MoniteurATD ATD = new MoniteurATD();
		return MoniteurDAO.update(ATD.find(numMoniteur));
	}
	
	
	public boolean ceMoniteurADesReservationsPourCetteSemaine(Date dateDebut, int numMoniteur){
		ReservationATD RATD = new ReservationATD();
		return RATD.ceMoniteurADesReservationsPourCetteSemaine(dateDebut, numMoniteur);
	}

	public boolean ceMoniteurDoitPresterCoursParticulier(int numMoniteur){
		ReservationATD RATD = new ReservationATD();
		return RATD.ceMoniteurDoitPresterCoursParticulier(numMoniteur);
	}

	// PROPRIETES
	public int 		getAnneeExp								()										{ return anneeDexp; 	}
	public boolean 	getDisponiblecoursParticulier			()	 									{ return disponiblecoursParticulier; }
	public void 	setAnneeExp 							(int el) 								{ this.anneeDexp = el; 	}
	public void 	setDisponiblecoursParticulier			(boolean disponiblecoursParticulier) 	{ this.disponiblecoursParticulier = disponiblecoursParticulier; }
	public ArrayList<AccreditationATD> getAccrediList		() 										{ return listAccreditation; }
	public void setAccrediList	(ArrayList<AccreditationATD> accrediList) 							{ this.listAccreditation = accrediList; 	}


}

