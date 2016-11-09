package be.mousty.accessToDao;

import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Accreditation;

public class AccreditationATD {
	// VARIABLES
	private String nom;

	// CONSTRUCTEURS
	public AccreditationATD(){}
	public AccreditationATD(String nom){
		this.nom = nom;
	}

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Accreditation> AccreditationDAO = adf.getAccreditationDAO();
	public int					 	create		(Accreditation a) 	{ return AccreditationDAO.create(a); }
	public boolean 					delete		(Accreditation a)	{ return AccreditationDAO.delete(a); }
	public Accreditation 			getId		(Accreditation a) 	{ return AccreditationDAO.getId(a);  }
	public boolean 					update		(Accreditation a) 	{ return AccreditationDAO.update(a); }
	public Accreditation 			find		(int id) 			{ return AccreditationDAO.find(id);  } 
	public ArrayList<Accreditation> getListAccr	() 					{ return AccreditationDAO.getList(); } 

	// FONCTION SURCHARGEE
	@Override
	public String toString() { return nom; }

	// PROPRIETE
	public String 	getNom  () { return nom; }
	public void setNom		(String nom) { this.nom = nom; }
}
