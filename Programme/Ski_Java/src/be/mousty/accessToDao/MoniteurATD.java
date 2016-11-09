package be.mousty.accessToDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Moniteur;

public class MoniteurATD extends UtilisateurATD{
	// VARIABLES 
	private int anneeDexp;
	private ArrayList<AccreditationATD> listAccreditation = new ArrayList<AccreditationATD>();



	// CONSTRUCTEURS
	public MoniteurATD(){}
	public MoniteurATD(String nom, String pre, String adresse, String sexe, Date dateNaissance, 
			String pseudo, String mdp, int typeMoniteur, ArrayList<AccreditationATD> listAccreditation){
		super(nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeMoniteur);
		Random r = new Random();
		this.anneeDexp 	= r.nextInt(10 - 0 + 1) + 0; // entre 10 et 0
		this.listAccreditation = listAccreditation;
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
	public ArrayList<Moniteur> 	getListDispo		(int numSemaine, String periode) { return MoniteurDAO.getMyListSelonID(-1, numSemaine, -1,  periode); }

	// METHODES 

	// Pour ne pas additionner 2 fois le m�me moniteur
	public void addAccreditation(AccreditationATD ac){
		if(!listAccreditation.contains(ac))
			listAccreditation.add(ac);
	}

	// METHODE SURCHARGEE
	@Override
	public String toString() { 
		return 
				super.toString()+ System.getProperty("line.separator")
				+ "MONITEUR, a accumule" + anneeDexp + " ann�e(s) d'ex�rience." + System.getProperty("line.separator");
	}

	// PROPRIETES
	public int 	getAnneeExp	()			{ return anneeDexp; 	}
	public void setAnneeExp (int el) 	{ this.anneeDexp = el; 	}
	public 		ArrayList<AccreditationATD> getAccrediList	() 				{ return listAccreditation; }
	public void setAccrediList	(ArrayList<AccreditationATD> accrediList) 	{ this.listAccreditation = accrediList; 	}
}

