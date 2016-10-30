package POJO;

import java.sql.Date;
import java.util.Scanner;

import DAO.AbstractDAOFactory;
import DAO.DAO;

public class Client extends Utilisateur{
	// VARIABLES
	private int numClient;
	private String adresseFacturation;
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Client> ClientDao = adf.getClientDAO();
	
	// CONSTRUCTEURs
	public Client(){}
	public Client(int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance,
			String pseudo, String mdp, int typeUtilisateur, String adresseFacturation){
		super(numPersonne, nom, pre, adresse, sexe, dateNaissance, pseudo, mdp, typeUtilisateur);
		//this.numClient = numClient;
		this.adresseFacturation = adresseFacturation;
	}
	
	// METHODES
	public Client rechercherClient(int id){
		return ClientDao.find(id);
	}
	
	// METHODES SURCHARGEES
	@Override
	public String toString() { 
		return 
			super.toString()+ System.getProperty("line.separator")
			+ "CLIENT." + System.getProperty("line.separator")
			+ "N° de client : " + numClient + System.getProperty("line.separator");
	}
	
	// PROPRIETES
	public String getAdresseFacturation() { return adresseFacturation;}
	public int getNumClient		() 				{ return numClient; }
	public void setAdresseFacturation (String el) { this.adresseFacturation  = el;}
	public void setNumClient 	(int numClient) { this.numClient = numClient; }
}
