package POJO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import DAO.AbstractDAOFactory;
import DAO.DAO;

public class Eleve extends Personne{
	// VARIABLES
	private boolean aUneAssurance;
	private String categorie;
	private int numEleve;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Eleve> EleveDao = adf.getEleveDAO();
	
	// CONSTRUCTEURS
	public Eleve(){}
	
	public Eleve(String categorie, boolean aUneAssurance){
		//this.numEleve 		= numEleve;
		this.aUneAssurance 	= aUneAssurance;
		this.categorie 		= categorie;
	}
	
	public Eleve(int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance){
		super(numPersonne, nom, pre, adresse, sexe, dateNaissance);
		//this.aUneAssurance 			= aUneAssurance;
		attributerCategorie();
	}
	
	// METHODEs
	private void attributerCategorie(){
		if(this.calculerAge() <= 12 && this.calculerAge() > 4){ this.categorie = "Enfant"; }
		else if (this.calculerAge() > 12){ this.categorie = "Adulte"; }
		else { this.categorie = "erreur"; }
		//
	}
	
	public int 				createEleve	() { return EleveDao.create(this); }
	public Eleve 			findEleve	(int id){ return EleveDao.find(id); }
	public ArrayList<Eleve> getListEleve(){ return EleveDao.getList(); }
	
	public HashSet<Eleve> getListEleveSelonAccredProf(int numMoniteur){
		HashSet<Eleve> listeFiltree = new HashSet<Eleve>();
		ArrayList<Eleve> listeFull =  EleveDao.getList(); 
		Moniteur M = new Moniteur();
		M = M.findMoniteur(numMoniteur);
		ArrayList<Accreditation> listeAccredMoniteur = M.getAccrediList();
		//System.out.println(M.getNom());
		for(Accreditation A : listeAccredMoniteur)
			for(Eleve eFull : listeFull){
				//System.out.println("Nom accred : " + A.getNom());
				if(A.getNom().equals(eFull.getCategorie()))
					listeFiltree.add(eFull);
			}
			
		return listeFiltree;
	}
	
	// METHODEs SURCHARGEEs
	@Override
	public String toString() { 
		return 
			super.toString()+ System.getProperty("line.separator")
			+ "ELEVE, cat�gorie " + categorie + System.getProperty("line.separator")
			+ "Poss�de une assurance : " + aUneAssurance + System.getProperty("line.separator");
	}
	
	// PROPRIETE
	public boolean getAUneAssurance		() { return aUneAssurance; }
	public String getCategorie			() { return categorie; }
	public int getNumEleve				() { return numEleve; }
	public void setAUneAssurance		(boolean aUneAssurance) 	{ this.aUneAssurance = aUneAssurance; }
	public void setCategorie			(String categorie) 			{ this.categorie = categorie; }
	public void setNumEleve				(int numEleve) 				{ this.numEleve = numEleve; }
	

	
}
