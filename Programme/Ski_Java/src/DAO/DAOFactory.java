package DAO;

import java.sql.Connection;

import POJO.Client;
import POJO.Moniteur;
import POJO.Personne;
import POJO.Utilisateur;

public class DAOFactory extends AbstractDAOFactory{
protected static final Connection conn = SingletonConnection.getInstance();
	
	public DAO<Utilisateur> getUtilisateurDAO	() { return new UtilisateurDAO(conn); }
	public DAO<Personne> 	getPersonneDAO		() { return new PersonneDAO(conn); }
	public DAO<Client> 		getClientDAO		() { return new ClientDAO(conn); }
	public DAO<Moniteur> 	getMoniteurDAO		() { return new MoniteurDAO(conn); }
}