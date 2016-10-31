package POJO;

public class Cours {
	// VARIABLES
	private double 	prix;
	private int 	minEleve;
	private int 	maxEleve;
	private String 	categAge;
	private String 	typeCours;
	
	// CONSTRUCTEURS
	public Cours (){}
	public Cours(double prix, int minEleve, int maxEleve, String categAge, String typeCours){
		this.prix 		= prix;
		this.minEleve 	= minEleve;
		this.maxEleve 	= maxEleve;
		this.categAge 	= categAge;
		this.typeCours 	= typeCours;
	}
	
	// FONCTION SURCHARGEE
		@Override
		public String toString() { 
			return  "Cours : " + typeCours + System.getProperty("line.separator")
					+ "Catégorie d'âge : " + categAge + System.getProperty("line.separator")
					+ "Prix : " + prix + "€" + System.getProperty("line.separator") 
					+  "Nombre minimum d'élèves pour ce cours : " + minEleve + System.getProperty("line.separator") 
					+  "Nombre maximum d'élèves pour ce cours : " + maxEleve + System.getProperty("line.separator");
		}
	
	// PROPRIETES
	public double getPrix		() 		{ return prix; }
	public int getMinEl			() 		{ return minEleve; }
	public int getMaxEl			()		{ return maxEleve; }
	public String getCategAge	() 		{ return categAge; }
	public String getTypeCours	() 		{ return typeCours; }
	public void setPrix			(double el) { prix = el; }
	public void setMinEl		(int el) 	{ minEleve = el; }
	public void setMaxEl		(int el) 	{ maxEleve = el; }
	public void setCategAge		(String el) { categAge = el; }
	public void setTypeCours	(String el) { typeCours = el; }
}
