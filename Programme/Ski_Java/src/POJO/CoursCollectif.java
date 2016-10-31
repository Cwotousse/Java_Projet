package POJO;

import java.util.ArrayList;
import java.util.HashSet;

import DAO.AbstractDAOFactory;
import DAO.DAO;

public class CoursCollectif extends Cours{
	// VARIABLES
	private int 	numCoursCollectif;
	private String 	categorieAge;
	private String 	niveauCours;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<CoursCollectif> CoursCollectifDao = adf.getCoursCollectifDAO();

	// CONSTRUCTEURS
	public CoursCollectif (){}
	public CoursCollectif(String categorieAge, String niveauCours){
		this.categorieAge 	= categorieAge;
		this.niveauCours 	= niveauCours;
	}

	public CoursCollectif(int numCours, String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, 
			String categorieAge, String niveauCours){
		super(numCours,nomSport, prix, minEleve, maxEleve, periodeCours);
		this.categorieAge 	= categorieAge;
		this.niveauCours 	= niveauCours;
	}

	// METHODES
	public int createCoursCollectif					() 		{ return CoursCollectifDao.create(this); }
	public void deleteCoursCollectif				()		{ CoursCollectifDao.delete(null); }
	public CoursCollectif rechercherCoursCollectif	(int id){ return CoursCollectifDao.find(id); }
	public ArrayList<CoursCollectif> getListCoursCollectif()		{ return CoursCollectifDao.getList(); }

	public ArrayList<CoursCollectif> getListCoursCollectifSelonId(int idMoniteur, int idEleve, HashSet<String> periode){
		System.out.println("Entree fonc");
		ArrayList<Cours> listCours = super.getListCoursSelonId(idMoniteur, idEleve);
		ArrayList<CoursCollectif> listFull = getListCoursCollectif();
		ArrayList<CoursCollectif> listSelonId = new ArrayList<CoursCollectif>();
		Eleve E = new Eleve();
		E = E.findEleve(idEleve);
		for (CoursCollectif CC : listFull){
			for (Cours C : listCours){
				if (CC.getNumCours() == C.getNumCours() && E.getCategorie().equals(CC.getCategorieAge())){
					System.out.println("For String de taille " + periode.size());
					for(String S : periode){
						System.out.println(S + " / " + CC.getPeriodeCours());
						if(CC.getPeriodeCours().equals(S)){
							System.out.println("Ajout Cours Collectif");
							listSelonId.add(CC);
						}
					}
				}
			}
		}
		return listSelonId;
	}

	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return  
				super.toString()+ System.getProperty("line.separator") + 
				"Catégorie d'age : " + categorieAge + System.getProperty("line.separator") + 
				"Niveau du cours : " + niveauCours + System.getProperty("line.separator");
	}

	// PROPRIETES
	public int getNumCoursCollectif		() 							{ return numCoursCollectif; }
	public String getCategorieAge		() 							{ return categorieAge; }
	public String getNiveauCours		() 							{ return niveauCours; }
	public void setNumCoursCollectif	(int numCoursCollectif) 	{ this.numCoursCollectif = numCoursCollectif; }
	public void setCategorieAge			(String el) 				{ categorieAge = el; }
	public void setNiveauCours			(String niveauCours) 		{ this.niveauCours = niveauCours; }
}