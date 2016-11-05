package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Client;
import POJO.Personne;
import POJO.Utilisateur;

public class ClientDAO extends DAO<Client> {
	public ClientDAO(Connection conn) { super(conn); }

	@Override
	public int create(Client obj) {
		PreparedStatement pst_cre_cli = null;
		try {
			int numPersonne = -1;
			Personne P = new Personne(-1,obj.getNom(), obj.getPre(), obj.getAdresse(), obj.getSexe(), obj.getDateNaissance());
			numPersonne = P.createPersonne();
			if (numPersonne != -1){
				
				System.out.println("Client Dao -> " + numPersonne);
				Utilisateur U = new Utilisateur(numPersonne, obj.getPseudo(), obj.getMdp(), obj.getTypeUtilisateur());
				if(U.createUtilisateur()!= -1){
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
					pst_cre_cli.close();
					System.out.println("Ajout d'un client effectue");
					return numPersonne;
				}
				else { 
					P.deletePersonne();
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
		Client client = new Client();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Client INNER JOIN Utilisateur ON Client.numClient = Utilisateur.numUtilisateur "
					+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur WHERE numClient = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			while (result.next()) {
				client = new Client(result.getInt("numPersonne"), result.getString("nom"), result.getString("prenom"), result.getString("adresse"), 
						result.getString("sexe"), result.getDate("dateNaissance"), result.getString("pseudo"),
						result.getString("mdp"), result.getInt("typeUtilisateur"), result.getString("adresseFacturation"));
			}
			return client;
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return null;
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

	@Override public String calculerPlaceCours(int numCours, int numSemaine) { return -1 + ""; }
	@Override public ArrayList<Client> getListCoursSelonId(int idMoniteur) { return null; }
	@Override public ArrayList<Client> getListCoursCollectifSelonId(int numMoniteur, int numEleve, String periode) { return null; }
	@Override public ArrayList<Client> getListCoursParticulierSelonId(int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Client> getListEleveSelonAccredProfEtCours(int numSemaine, int numMoniteur, String periode) { return null; }
	@Override public ArrayList<Client> getMyList(int idPersonne) { return null; }
	@Override public ArrayList<Client> getListSemainePerdiodeMoniteur(int numMoniteur, int numSemaine, String periode) { return null; }
	@Override public boolean updateAssurance(int numEleve, int numSemaine, String periode) { return false; }
	@Override public void creerTouteDisponibilites() { }
	@Override public void creerTouteDisponibilitesSelonMoniteur(int i) { }
	@Override public boolean changeDispoSelonIdSemaine(int numSemaine, int numMoniteur) { return false; }
	@Override public ArrayList<Client> getListDispo(int numSemaine, String periode) { return null; }
}