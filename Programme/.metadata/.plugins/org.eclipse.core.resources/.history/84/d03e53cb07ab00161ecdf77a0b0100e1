package be.mousty.accessToDao;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import be.mousty.dao.AbstractDAOFactory;
import be.mousty.dao.DAO;
import be.mousty.pojo.Semaine;

public class SemaineATD {
	// VARIABLES
	private Date dateDebut;
	private Date dateFin;
	//private String descriptif;
	private boolean congeScolaire;
	private int numSemaineDansAnnee;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	// CONSTRUCTEURS
	public SemaineATD(){}
	public SemaineATD( boolean congeScolaire, Date dateDebut, Date dateFin, int numSemaineDansAnnee){
		this.dateDebut 				= dateDebut;
		this.dateFin 				= dateFin;
		this.congeScolaire 			= congeScolaire;
		this.numSemaineDansAnnee 	= numSemaineDansAnnee;
	}

	public SemaineATD(Semaine S){
		this.dateDebut 				= S.getDateDebut();
		this.dateFin 				= S.getDateFin();
		this.congeScolaire 			= S.getCongeScolaire();
		this.numSemaineDansAnnee 	= S.getNumSemaineDansAnnee();
	}

	// APPEL AUX METHODES DAO DANS LES CLASSES METIER
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Semaine> SemaineDAO = adf.getSemaineDAO();
	public int		create	(Semaine s) 	{ return SemaineDAO.create(s); 	}
	public boolean 	delete	(Semaine s)	 	{ return SemaineDAO.delete(s); 	}
	public Semaine 	getId	(Semaine s) 	{ return SemaineDAO.getId(s); 	}
	public boolean 	update	(Semaine s) 	{ return SemaineDAO.update(s); 	}
	public Semaine 	find	(int id) 		{ return SemaineDAO.find(id); 	} 
	public ArrayList<Semaine> getList () 	{ return SemaineDAO.getList(); 	} 
	public void AjouterSemainesDansDB	(String start, String end)	{ SemaineDAO.AjouterSemainesDansDB(start, end);	}
	public ArrayList<Semaine> getListSelonCriteres(Semaine obj) 	{ return SemaineDAO.getListSelonCriteres(obj);	}

	// METHODES
	public static boolean EstEnPeriodeDeConge(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return ((date.after(formatter.parse("2016-12-24")) && date.before(formatter.parse("2017-01-07"))) 
					|| (date.after(formatter.parse("2017-02-25")) && date.before(formatter.parse("2017-03-04")))
					|| date.after(formatter.parse("2017-04-01")) && date.before(formatter.parse("2017-04-15")));
		} 
		catch (ParseException e) { e.printStackTrace(); return false; }
	}

	public ArrayList<SemaineATD> getListSemaineSelonDateDuJour(){
		ArrayList<Semaine> listS = getListSelonCriteres(null);
		ArrayList<SemaineATD> listSATD = new ArrayList<SemaineATD>();
		for (int i = 0; i < listS.size(); i++) {
			SemaineATD SATD = new SemaineATD();
			SATD.setCongeScolaire(listS.get(i).getCongeScolaire());
			SATD.setDateDebut(listS.get(i).getDateDebut());
			SATD.setDateFin(listS.get(i).getDateFin());
			SATD.setNumSemaineDansAnnee(listS.get(i).getNumSemaineDansAnnee());
			listSATD.add(SATD);
		}
		return listSATD;
	}

	public ArrayList<SemaineATD> getListSem(){
		ArrayList<Semaine> listSemaine  = getList();
		ArrayList<SemaineATD> listSemaineoATD = new ArrayList<SemaineATD>();
		for(int i = 0; i < listSemaine.size(); i++){
			SemaineATD SATD = new SemaineATD();
			//DMATD.setNom(A.get(i).getNomAccreditation());
			SATD.setCongeScolaire(listSemaine.get(i).getCongeScolaire());
			SATD.setDateDebut(listSemaine.get(i).getDateDebut());
			SATD.setDateFin(listSemaine.get(i).getDateFin());
			SATD.setNumSemaineDansAnnee(listSemaine.get(i).getNumSemaineDansAnnee());
			listSemaineoATD.add(SATD);
		}
		return listSemaineoATD;
	}

	public int getIdATD(SemaineATD SATD){
		Semaine S = new Semaine();
		S.setCongeScolaire(SATD.getCongeScolaire());
		S.setDateDebut(SATD.getDateDebut());
		S.setDateFin(SATD.getDateFin());
		S.setNumSemaineDansAnnee(SATD.getNumSemaineDansAnnee());
		return getId(S).getNumSemaine();
	}
	
	public SemaineATD findATD (int numSem){
		Semaine S = find(numSem);
		SemaineATD SATD = new SemaineATD();
		SATD.setCongeScolaire(S.getCongeScolaire());
		SATD.setDateDebut(S.getDateDebut());
		SATD.setDateFin(S.getDateFin());
		SATD.setNumSemaineDansAnnee(S.getNumSemaineDansAnnee());
		return SATD;
	}
	
	public Semaine transformATDintoPojo(int numSemaine){
		Semaine S = new Semaine();
		S.setCongeScolaire(this.getCongeScolaire());
		S.setDateDebut(this.getDateDebut());
		S.setDateFin(this.getDateFin());
		S.setNumSemaineDansAnnee(this.getNumSemaineDansAnnee());
		S.setNumSemaine(numSemaine);
		return S;
	}
	
	// FONCTION SURCHARGEE
	@Override
	public String toString() { 
		return  //descriptif + System.getProperty("line.separator") 
				"Période de congé scolaire : " + congeScolaire + System.getProperty("line.separator")
				+ "Période comprise entre le " + dateDebut.toString() + " et le " + dateFin.toString() + System.getProperty("line.separator"); 
	}


	// PROPRIETE
	public Date getDateDebut			() { return dateDebut; }
	public Date getDateFin				() { return dateFin; }
	//public String getDescriptif			() { return descriptif; }
	public boolean getCongeScolaire		() { return congeScolaire; }
	public int getNumSemaineDansAnnee	() { return numSemaineDansAnnee; }
	//public int getNumSemaine			() { return numSemaine; }
	public void setDateDebut			(Date dateDebut) 			{ this.dateDebut = dateDebut; }
	public void setDateFin				(Date dateFin) 				{ this.dateFin = dateFin; }
	//public void setDescriptif			(String descriptif) 		{ this.descriptif = descriptif; }
	public void setCongeScolaire		(boolean congeScolaire) 	{ this.congeScolaire = congeScolaire; }
	public void setNumSemaineDansAnnee	(int numSemaineDansAnnee) 	{ this.numSemaineDansAnnee = numSemaineDansAnnee; }
	//public void setNumSemaine			(int numSemaine) 			{ this.numSemaine = numSemaine; }
}

