package be.mousty.accessToDao;

import java.sql.Date;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.dao.UtilisateurDAO;
import be.mousty.pojo.Utilisateur;

public class UtilisateurATD extends PersonneATD{
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Utilisateur> utilisateurDao = adf.getUtilisateurDAO();
	
	// VARIABLES
	private String pseudo;
	private String mdp;
	private int typeUtilisateur;
	
	// CONSTRUCTEURs
	public UtilisateurATD(){}
	public UtilisateurATD(String mdp, String pseudo){
		this.pseudo 			= pseudo;
		this.mdp 				= mdp;
		this.typeUtilisateur 	= utilisateurDao.returnUser(mdp,pseudo).getTypeUtilisateur();
	}
	
	public UtilisateurATD(String nom, String pre, String adresse, String sexe, Date dateNaissance,
			String pseudo, String mdp, int typeUtilisateur){
		super(nom, pre, adresse, sexe, dateNaissance);
		this.pseudo 			= pseudo;
		this.mdp 				= mdp;
		this.typeUtilisateur 	= typeUtilisateur;
	}

	// METHODES SURCHARGEES
	@Override
	public String toString() { 
		return 
				"Utilisateur." + System.getProperty("line.separator")
				+ "User name    : " + pseudo +  System.getProperty("line.separator")
				+ "Mot de passe : " + mdp + System.getProperty("line.separator");
	}
	
	public Utilisateur connexionMoniteur() { 
		UtilisateurDAO.returnUser
	}

	// PROPRIETES
	public String getPseudo			() { return pseudo; }
	public String getMdp			() { return mdp; }
	public int getTypeUtilisateur	() { return typeUtilisateur; }
	public void setPseudo			(String pseudo) 		{ this.pseudo = pseudo; }
	public void setMdp				(String mdp)			{ this.mdp = mdp; }
	public void setTypeUtilisateur	(int typeUtilisateur) 	{ this.typeUtilisateur = typeUtilisateur; }
}

