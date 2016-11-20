package be.mousty.pojo;
import java.sql.Date;
/**
	Classe POJO relatif � la table Moniteur dans la DB.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category POJO
*/
import java.util.ArrayList;
import java.util.Random;

import be.mousty.accessToDao.AccreditationATD;
import be.mousty.accessToDao.MoniteurATD;

public class Moniteur extends Utilisateur{
	// VARIABLES 
	private int numMoniteur;
	private int anneeDexp;
	private boolean disponiblecoursParticulier;
	private ArrayList<Accreditation> listAccreditation = new ArrayList<Accreditation>();
	
	// CONSTRUCTEUR SANS ARGUMENTS
	public Moniteur(){}
	
	public Moniteur(String nom, String pre, String adresse, String sexe, Date dateNaissance, 
			String pseudo, String mdp, int typeMoniteur, ArrayList<Accreditation> listAccreditation, boolean disponiblecoursParticulier){
		super(nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeMoniteur);
		Random r = new Random();
		this.anneeDexp 	= r.nextInt(10 - 0 + 1) + 0; // entre 10 et 0
		this.listAccreditation = listAccreditation;
		this.disponiblecoursParticulier = disponiblecoursParticulier;
	}
	
	public Moniteur(MoniteurATD M){
		super(M.getNom(), M.getPre(), M.getAdresse(), M.getSexe(), M.getDateNaissance(), M.getPseudo(), M.getMdp(), M.getTypeUtilisateur());
		this.anneeDexp 	= M.getAnneeExp(); // entre 10 et 0
		this.listAccreditation = AccreditationATD.changeTypeAccredilistEnATD(M.getAccrediList());
		this.disponiblecoursParticulier = M.getDisponiblecoursParticulier();
	}
	
	// PROPRIETES
	public int getAnneeExp		() 			{ return anneeDexp; }
	public int getNumMoniteur	() 			{ return numMoniteur; }
	public boolean getDisponiblecoursParticulier() { return disponiblecoursParticulier; }
	public void setNumMoniteur	(int el) 	{ this.numMoniteur = el;}
	public void setAnneeExp 	(int el) 	{ this.anneeDexp = el; }
	public void setDisponiblecoursParticulier(boolean disponiblecoursParticulier) { this.disponiblecoursParticulier = disponiblecoursParticulier; }
	public ArrayList<Accreditation> getAccrediList() { return listAccreditation; }
	public void setAccrediList (ArrayList<Accreditation> accrediList) { this.listAccreditation = accrediList; 	}	
}
