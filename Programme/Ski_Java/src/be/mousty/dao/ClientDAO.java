package be.mousty.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import be.mousty.pojo.Client;
import be.mousty.pojo.Personne;
import be.mousty.pojo.Utilisateur;

public class ClientDAO extends DAO<Client> {
	AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
	DAO<Utilisateur> UtilisateurDAO = adf.getUtilisateurDAO();
	DAO<Personne> PersonneDao = adf.getPersonneDAO();
	public ClientDAO(Connection conn) { super(conn); }

	@Override
	public int create(Client obj) {
		PreparedStatement pst_cre_cli = null;
		try {
			Personne P = new Personne();
			//P.setNumPersonne(res_Rec_Util.getInt("numPersonne"));
			P.setNom(obj.getNom());
			P.setPre( obj.getPre());
			P.setDateNaissance(obj.getDateNaissance());
			P.setAdresse(obj.getAdresse());
			P.setSexe(obj.getSexe());
			int numPersonne = PersonneDao.create(P);
			if (numPersonne != -1){
				Utilisateur U = new Utilisateur();
				U.setNumUtilisateur(numPersonne);
				U.setPseudo(obj.getPseudo());
				U.setMdp(obj.getMdp());
				U.setTypeUtilisateur( obj.getTypeUtilisateur());
				if(UtilisateurDAO.create(U) != -1){
					/*String sql0 = "SELECT numPersonne FROM Personne WHERE numPersonne";
					PreparedStatement pst0 = this.connect.prepareStatement(sql0);
					ResultSet rs0 = pst0.executeQuery();
					int numUtilisateur = -1 ;
					while (rs0.next()) numUtilisateur = rs0.getInt(1); // On a l'id de l'utilisateur*/

					String requete5 = "INSERT INTO Client (adresseFacturation, numClient) VALUES (?,?)";
					pst_cre_cli = connect.prepareStatement(requete5);

					pst_cre_cli.setString(1, obj.getAdresseFacturation());
					pst_cre_cli.setInt(2, numPersonne);
					pst_cre_cli.executeUpdate();
					//pst_cre_cli.close();
					System.out.println("Ajout d'un client effectue");
					return numPersonne;
				}
				else { 
					PersonneDao.delete(P);
					return -1;
				} // utilisateur
			}
			else { return -1; } // personne
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_cre_cli != null) {
				try { pst_cre_cli.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return -1;

	}

	public boolean delete(Client obj) { return false; }
	public boolean update(Client obj) { return false; }

	// On cherche une Client gr�ce � son id
	public Client find(int id) {
		Client C = new Client();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Client INNER JOIN Utilisateur ON Client.numClient = Utilisateur.numUtilisateur "
					+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur WHERE numClient = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet res_cli = pst.executeQuery();
			while (res_cli.next()) {
				C.setAdresseFacturation(res_cli.getString("adresseFacturation"));
				C.setNumClient(res_cli.getInt("numClient"));
				C.setPseudo(res_cli.getString("pseudo"));
				C.setMdp(res_cli.getString("mdp"));
				C.setTypeUtilisateur(res_cli.getInt("typeUtilisateur"));
				C.setNumPersonne(res_cli.getInt("numPersonne"));
				C.setNom(res_cli.getString("nom"));
				C.setPre(res_cli.getString("prenom"));
				C.setDateNaissance(res_cli.getDate("dateNaissance"));
				C.setAdresse(res_cli.getString("adresse"));
				C.setSexe(res_cli.getString("sexe"));
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return C;
	}

	@SuppressWarnings("null")
	public ArrayList<Client> getList() {
		Client client = null;
		ArrayList<Client> liste = new ArrayList<Client>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Client";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				client.setNumUtilisateur(rs.getInt("numUtilisateur"));
				client.setNumClient(rs.getInt("numClient"));
				liste.add(client);
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return liste;
	}
	
	@Override
	public int getNumPersonne(String nom, String pre, String adr) {
		PreparedStatement pst = null;
		int id = -1;
		try {
			String sql = "SELECT numPersonne FROM Personne WHERE nom = ? AND prenom = ? AND adresse = ? ;";
			pst = this.connect.prepareStatement(sql);
			pst.setString(1, nom);
			pst.setString(2, pre);
			pst.setString(3, adr);
			ResultSet res_Rec_Accr = pst.executeQuery();
			while (res_Rec_Accr.next()) { id = res_Rec_Accr.getInt("numPersonne"); }
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return id;
	}

	@Override public String calculerPlaceCours(int numCours, int numSemaine, int numMoniteur) { return -1 + ""; }
	@Override public ArrayList<Client> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Client> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Client> getListCoursParticulierSelonId(int numMoniteur, String periode, int numSemaine) { return null; }
	@Override public ArrayList<Client> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Client> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Client> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<Client> getListDispo(int numSemaine, String periode) { return null; }
	@Override public Client returnUser(String mdp, String pseudo) { return null; }
	@Override public int valeurReduction(int numSem) { return 0; }

	@Override
	public int getNumCoursCollectif(String nomSport, String periode, String categorie, String niveauCours) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumCoursParticulier(String nomSport, String periode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ArrayList<Client> getListSemaineSelonDateDuJour() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNumSemaine(Date dateDebut) {
		// TODO Auto-generated method stub
		return 0;
	}
}