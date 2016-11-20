package be.mousty.accessToDao;
/**
	Classe m�tier relatif li�e � la classe Personne et PersonneDAO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category M�tier
*/
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Personne;


public class PersonneATD {
	// VARIABLES 
	private String 	nom;
	private String 	pre;
	private String 	adresse;
	private String 	sexe;
	private Date 	dateNaissance;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	// CONSTRUCTEURS
	public PersonneATD(){}
	public PersonneATD(String nom, String pre, String adresse, String sexe, Date dateNaissance){
		this.nom 			= nom;
		this.pre 			= pre;
		this.adresse 		= adresse;
		this.sexe 			= sexe;
		this.dateNaissance 	= dateNaissance;
	}

	public PersonneATD(Personne P){
		this.nom 			= P.getNom();
		this.pre 			= P.getPre();
		this.adresse 		= P.getAdresse();
		this.sexe 			= P.getSexe();
		this.dateNaissance 	= P.getDateNaissance();
	}

	// METHODES NON LIEES AU DAO
	public double calculerAge(){
		try {
			Date now = new Date(Calendar.getInstance().getTime().getTime());
			long diffInMillies = now.getTime() - this.getDateNaissance().getTime();
			long tu =  TimeUnit.DAYS.convert(diffInMillies,TimeUnit.MILLISECONDS);
			return (double)tu/365;

		}
		catch (Exception e) { e.getStackTrace(); }
		return -1;
	}

	public String attributerCategorie(){
		String categorie = "Erreur";
		if(this.calculerAge() <= 12 && this.calculerAge() > 4){ categorie = "Enfant"; }
		if(this.calculerAge() > 12){ categorie = "Adulte"; }
		return categorie;
	}

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Personne> PersonneDAO = adf.getPersonneDAO();
	public int					create				(Personne p) 	{ return PersonneDAO.create(p); 	}
	public boolean 				delete				()	 			{ return PersonneDAO.delete(null); 	}
	public Personne 			getId				(Personne p) 	{ return PersonneDAO.getId(p); 		}
	public Personne 			find				(int id) 		{ return PersonneDAO.find(id); 		} 
	
	
	// PROPRIETES
	public String getNom		() 						{ return nom; 							}
	public String getPre		() 						{ return pre; 							}
	public String getAdresse	() 						{return adresse; 						}
	public String getSexe		() 						{ return sexe; 							}
	public Date getDateNaissance() 						{ return dateNaissance; 				}
	public void setNom			(String nom) 			{ this.nom = nom; 						}
	public void setPre			(String pre) 			{ this.pre = pre; 						}
	public void setAdresse		(String adresse) 		{ this.adresse = adresse;				}
	public void setSexe			(String sexe) 			{ this.sexe = sexe; 					}
	public void setDateNaissance(Date dateNaissance) 	{ this.dateNaissance = dateNaissance; 	}

}

