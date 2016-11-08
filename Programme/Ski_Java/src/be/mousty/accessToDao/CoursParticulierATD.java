package be.mousty.accessToDao;

public class CoursParticulierATD extends CoursATD{
	// VARIABLES
	private int 	nombreHeures;
	//AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	//DAO<CoursParticulier> CoursParticulierDao = adf.getCoursParticulierDAO();
	
	// CONSTRUCTEURS
	public CoursParticulierATD (){}
	public CoursParticulierATD(int nombreHeures){
		this.nombreHeures = nombreHeures;
	}
	
	public CoursParticulierATD(String nomSport, double prix, int minEleve, int maxEleve, String periodeCours, int nombreHeures){
		super(nomSport, prix, minEleve, maxEleve, periodeCours);
		this.nombreHeures = nombreHeures;
	}
	
	// METHODES
	//public int createCoursParticulier					() 		 { return CoursParticulierDao.create(this); }
	//public void deleteCoursParticulier					()		 { CoursParticulierDao.delete(null); }
	//public CoursParticulier rechercherCoursParticulier	(int id) { return CoursParticulierDao.find(id); }
	//public ArrayList<CoursParticulier> getListCoursParticulier()		{ return CoursParticulierDao.getList(); }
	
	// FONCTION SURCHARGEE
		@Override
		public String toString() { 
			return super.toString()+ System.getProperty("line.separator") + 
			"Nombre d'heures ce cours : " + nombreHeures + System.getProperty("line.separator");
		}
	
	// PROPRIETES
	public int getNombreHeures	()			{ return nombreHeures; }
	public void setNombreHeures	(int el) 	{ nombreHeures = el; }
}
