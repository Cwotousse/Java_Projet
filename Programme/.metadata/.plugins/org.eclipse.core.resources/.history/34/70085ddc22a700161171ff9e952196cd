package be.mousty.accessToDao;

import java.sql.Date;
import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Eleve;

public class EleveATD extends PersonneATD{
	// VARIABLES
	private String categorie;

	// CONSTRUCTEURS
	public EleveATD(){}

	public EleveATD(String categorie, String nom, String pre, String adresse, String sexe, Date dateNaissance){
		super(nom, pre, adresse, sexe, dateNaissance);
		this.categorie = categorie;
	}

	public EleveATD(String nom, String pre, String adresse, String sexe, Date dateNaissance){
		super(nom, pre, adresse, sexe, dateNaissance);
		attributerCategorie();
	}

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Eleve> EleveDAO = adf.getEleveDAO();
	public int					create				(Eleve e) 	{ return EleveDAO.create(e); 	}
	public boolean 				delete				()	 		{ return EleveDAO.delete(null); }
	public Eleve 				getId				(Eleve e) 	{ return EleveDAO.getId(e); 	}
	public boolean 				update				(Eleve e) 	{ return EleveDAO.update(e); 	}
	public Eleve 				find				(int id) 	{ return EleveDAO.find(id); 	} 
	public ArrayList<Eleve> 	getListEl			() 			{ return EleveDAO.getList(); 	} 
	public ArrayList<Eleve> 	getListSelonCriteres(Eleve e) 	{ return EleveDAO.getListSelonCriteres(e); 	}
	public ArrayList<Eleve> getListEleveSelonAccredProfEtCours(int numMoniteur, int numSemaine, String periode)
	{ return EleveDAO.getMyListSelonID(numMoniteur, numSemaine, -1,  periode); }

	// METHODES
	private void attributerCategorie(){
		if(this.calculerAge() <= 12 && this.calculerAge() > 4){ this.categorie = "Enfant"; }
		else if (this.calculerAge() > 12){ this.categorie = "Adulte"; }
		else { this.categorie = "erreur"; }
	}

	// METHODEs SURCHARGEEs
	@Override
	public String toString() { 
		return 
				super.toString()+ System.getProperty("line.separator")
				+ "ELEVE, catégorie " + categorie + System.getProperty("line.separator");
	}

	// PROPRIETE
	public String 	getCategorie () 				{ return categorie; }
	public void 	setCategorie (String categorie) { this.categorie = categorie; }
}

