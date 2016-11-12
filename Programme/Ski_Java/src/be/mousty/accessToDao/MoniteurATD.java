package be.mousty.accessToDao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Accreditation;
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
	
	public MoniteurATD(Moniteur M){
		super(M.getNom(), M.getPre(), M.getAdresse(), M.getSexe(), M.getDateNaissance(), M.getPseudo(), M.getMdp(), M.getTypeUtilisateur());
		this.anneeDexp 	= M.getAnneeExp(); // entre 10 et 0
		this.listAccreditation = changeTypeAccredilist(M.getAccrediList());
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
	public ArrayList<Moniteur> 	getListDispo		(long numSemaine, String periode) { return MoniteurDAO.getMyListSelonID(-1, numSemaine, -1,  periode); }

	// METHODES 

	// Pour ne pas additionner 2 fois le m�me moniteur
	public void addAccreditation(AccreditationATD ac){
		if(!listAccreditation.contains(ac))
			listAccreditation.add(ac);
	}
	
	public MoniteurATD findM (int id){
		MoniteurATD MATD = new MoniteurATD();
		Moniteur M = find(id);
		MATD.setAdresse(M.getAdresse());
		MATD.setAnneeExp(M.getAnneeExp());
		MATD.setAccrediList(changeTypeAccredilist(M.getAccrediList()));
		MATD.setDateNaissance(M.getDateNaissance());
		MATD.setNom(M.getNom());
		MATD.setPre(M.getPre());
		MATD.setSexe(M.getSexe());
		return MATD;
	}
	
	public int inscriptionMoniteur(){
		Moniteur M = new Moniteur();
		M.setAnneeExp(0);
		M.setAccrediList(this.changeTypeAccredilistEnATD(getAccrediList()));
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

	public ArrayList<AccreditationATD> changeTypeAccredilist(ArrayList<Accreditation> A){
		ArrayList<AccreditationATD> LA = new ArrayList<AccreditationATD>();
		for(int i = 0; i < A.size(); i++){
			AccreditationATD AATD = new AccreditationATD();
			AATD.setNom(A.get(i).getNomAccreditation());
			LA.add(AATD);
		}
		return LA;
	}
	

	public ArrayList<Accreditation> changeTypeAccredilistEnATD(ArrayList<AccreditationATD> A){
		ArrayList<Accreditation> LA = new ArrayList<Accreditation>();
		for(int i = 0; i < A.size(); i++){
			Accreditation AATD = new Accreditation();
			AATD.setNomAccreditation((A.get(i).getNom()));
			switch(AATD.getNomAccreditation()){
			case "Snowboard" : AATD.setNumAccreditation(1);
				break;
			case "Ski" : AATD.setNumAccreditation(2);
				break;
			case "Ski de fond" : AATD.setNumAccreditation(3);
				break;
			case "Telemark" : AATD.setNumAccreditation(4);
				break;
			case "Enfant" :AATD.setNumAccreditation(5);
				break;
			case "Adulte" :AATD.setNumAccreditation(6);
				break;
			}
			LA.add(AATD);
		}
		return LA;
	}
	
	public ArrayList<MoniteurATD> getListDispoATD(int numSemaine, String periode){
		ArrayList<Moniteur> listM = getListDispo(numSemaine, periode);
		ArrayList<MoniteurATD> listMATD = new ArrayList<MoniteurATD>();
		for (int i = 0; i < listM.size(); i++) {
			MoniteurATD MATD = new MoniteurATD();
			MATD.setAdresse(listM.get(i).getAdresse());
			MATD.setAnneeExp(listM.get(i).getAnneeExp());
			MATD.setAccrediList(changeTypeAccredilist(listM.get(i).getAccrediList()));
			MATD.setDateNaissance(listM.get(i).getDateNaissance());
			MATD.setNom(listM.get(i).getNom());
			MATD.setPre(listM.get(i).getPre());
			MATD.setSexe(listM.get(i).getSexe());
			listMATD.add(MATD);
		}
		return listMATD;
	}
	
	public int getIdATD(MoniteurATD EATD){
		Moniteur M = new Moniteur();
		M.setAdresse(EATD.getAdresse());
		M.setAnneeExp(EATD.getAnneeExp());
		M.setAccrediList(changeTypeAccredilistEnATD(EATD.getAccrediList()));
		M.setDateNaissance(EATD.getDateNaissance());
		M.setNom(EATD.getNom());
		M.setPre(EATD.getPre());
		M.setSexe(EATD.getSexe());
		M.setNumMoniteur(-1);
		M.setNumPersonne(-1);
		M.setNumUtilisateur(-1);
		return  getId(M).getNumPersonne();
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

