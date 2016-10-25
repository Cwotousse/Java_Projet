package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import POJO.Client;
import POJO.Personne;
import POJO.Utilisateur;

public class ClientDAO extends DAO<Client> {
	public ClientDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Client obj) {
		try {
			AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
			DAO<Personne> PersonneDao = adf.getPersonneDAO();
			if (PersonneDao.create(new Personne(obj.getNom(), obj.getPre(), obj.getAdresse(), obj.getSexe(), obj.getDateNaissance()))){

				DAO<Utilisateur> UtilisateurDao = adf.getUtilisateurDAO();
				if(UtilisateurDao.create(new Utilisateur(obj.getPseudo(), obj.getMdp(), obj.getTypeUtilisateur()))){
					String sql0 = "SELECT MAX(numUtilisateur) from Utilisateur";
					PreparedStatement pst0 = this.connect.prepareStatement(sql0);
					ResultSet rs0 = pst0.executeQuery();
					int numUtilisateur = -1 ;
					while (rs0.next()) numUtilisateur = rs0.getInt(1); // On a l'id de l'utilisateur
					
					String requete5 = "INSERT INTO Client (adresseFacturation, numUtilisateur) VALUES (?,?)";
					PreparedStatement pst5 = connect.prepareStatement(requete5);

					pst5.setString(1, obj.getAdresseFacturation());
					pst5.setInt(2, numUtilisateur);
					pst5.executeUpdate();
					pst5.close();
					System.out.println("Ajout d'un client effectue");
					return true;
				} else { 
					PersonneDao.delete(null);
					return false;
					} // utilisateur
			} else { return false; } // personne
		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
 
	}

	public boolean delete(Client obj) {
		return false;
	}

	public boolean update(Client obj) {
		return false;
	}

	// On cherche une Client gr�ce � son id
	public Client find(int id) {
		Client client = new Client();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Client WHERE numClient = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				client.setNumUtilisateur(rs.getInt("numUtilisateur"));
				client.setNumClient(rs.getInt("numClient"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return client;
	}



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
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return liste;
	}

	@Override
	public  int verifPseudoMdp(Utilisateur obj){
		// TODO Auto-generated method stub
		return -1;
	}
}