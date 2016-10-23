package DAO;

import java.sql.Connection;

import POJO.Personne;
import POJO.Utilisateur;

public class DAOFactory extends AbstractDAOFactory{
protected static final Connection conn = SingletonConnection.getInstance();
	
	public DAO<Utilisateur> getUtilisateurDAO(){ return new UtilisateurDAO(conn); }
	public DAO<Personne> getPersonneDAO(){ return new PersonneDAO(conn); }
	//public DAO<Professeur> getProfesseurDAO() {	return new ProfesseurDAO(conn); }
	//public DAO<Eleve> getEleveDAO() { return new EleveDAO(conn); }
	//public DAO<Matiere> getMatiereDAO(){ return new MatiereDAO(conn); }
}