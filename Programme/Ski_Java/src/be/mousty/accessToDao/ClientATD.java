package be.mousty.accessToDao;

import java.sql.Date;
import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Client;

public class ClientATD extends UtilisateurATD{
	// VARIABLES
	private String 	adresseFacturation;
	
	// CONSTRUCTEURS
	public ClientATD(){}
	public ClientATD(String nom, String pre, String adresse, String sexe, Date dateNaissance,
			String pseudo, String mdp, int typeUtilisateur, String adresseFacturation){
		super(nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeUtilisateur);
		this.adresseFacturation = adresseFacturation;
	}
	
	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf 	= AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Client> ClientDAO 	= adf.getClientDAO();
	public int					create				(Client c) 		{ return ClientDAO.create(c); 	 	}
	public boolean 				delete				()	 			{ return ClientDAO.delete(null); 	}
	public Client 				getId				(Client c) 		{ return ClientDAO.getId(c); 		}
	public boolean 				update				(Client c) 		{ return ClientDAO.update(c); 		}
	public Client 				find				(int id) 		{ return ClientDAO.find(id); 		} 
	public ArrayList<Client> 	getListCli			() 				{ return ClientDAO.getList(); 		} 
	public ArrayList<Client> 	getListSelonCriteres(Client c) 		{ return ClientDAO.getListSelonCriteres(c); 			}
	
	
	// PROPRIETES
	public String getAdresseFacturation	() 				{ return adresseFacturation; }
	public void setAdresseFacturation 	(String el) 	{ this.adresseFacturation  = el; }
}

