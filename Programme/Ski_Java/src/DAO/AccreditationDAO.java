package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import POJO.Accreditation;
import POJO.Client;
import POJO.Accreditation;
import POJO.Personne;
import POJO.Utilisateur;

public class AccreditationDAO extends DAO<Accreditation> {
	public AccreditationDAO(Connection conn) {
		super(conn);
	}

	public int create(Accreditation obj) {
		/*try {
			System.out.println("AccreditationDao -> " + obj.getNumAccreditation());
			int numPersonne = obj.getNumPersonne();
			//V�rification que la personne n'est pas encore inscrite en tant qu'�l�ve.
			Accreditation e = find(numPersonne);
			// La personne n'existe pas
			if (e == null || numPersonne == -1){
				AbstractDAOFactory adf = AbstractDAOFactory.getFactory(AbstractDAOFactory.DAO_FACTORY);
				DAO<Personne> PersonneDao = adf.getPersonneDAO();
				numPersonne = PersonneDao.create(new Personne(-1, obj.getNom(), obj.getPre(), obj.getAdresse(), obj.getSexe(), obj.getDateNaissance()));
				//				String sql1 = "SELECT numPersonne from Personne where nom = '" + obj.getNom() + "' and prenom = '" + obj.getPre() + 
				//						"' AND adresse = '"+ obj.getAdresse() + "' AND SEXE ='"+ obj.getSexe() + "' AND dateNaissance = '" + obj.getDateNaissance() + "';";
				//				PreparedStatement pst1 = this.connect.prepareStatement(sql1);
				//				ResultSet rs1 = pst1.executeQuery();
				//				while (rs1.next()) numPersonne = rs1.getInt(1); // On a l'id de l'utilisateur

				if (numPersonne == -1){
					PersonneDao.delete(null);
					return -1;
				}
			}

			String requete5 = "INSERT INTO Accreditation (aPrisUneAssurance, categorie, numAccreditation) VALUES (?,?,?)";
			PreparedStatement pst5 = connect.prepareStatement(requete5);

			pst5.setBoolean(1, obj.getAUneAssurance());
			pst5.setString(2, obj.getCategorie());
			pst5.setInt(3, numPersonne);
			pst5.executeUpdate();
			pst5.close();
			System.out.println("Ajout d'un accred effectue");
			return numPersonne;

		} 
		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}*/
		return -1;
	}

	public boolean delete(Accreditation obj) { return false; }

	public boolean update(Accreditation obj) {
		return false;
	}

	// On cherche une Accreditation gr�ce � son id
	public Accreditation find(int id) {
		Accreditation accred = new Accreditation();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Accreditation WHERE numAccreditation = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet result = pst.executeQuery();
			// int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
			while (result.next()) {
				accred = new Accreditation(result.getString("nom"));
			}
			return accred;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}



	public ArrayList<Accreditation> getList() {

		ArrayList<Accreditation> liste = new ArrayList<Accreditation>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Accreditation INNER JOIN Personne On Personne.numPersonne = Accreditation.numAccreditation";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//int numPersonne, String nom, String pre, String adresse, String sexe, Date dateNaissance, boolean aUneAssurance
				Accreditation accred = accred = new Accreditation(rs.getString("nom"));
				liste.add(accred);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (pst != null) {
				try {
					pst.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return liste;
	}

	/*@Override
	public  int verifPseudoMdp(Utilisateur obj){
		// TODO Auto-generated method stub
		return -1;
	}*/
}