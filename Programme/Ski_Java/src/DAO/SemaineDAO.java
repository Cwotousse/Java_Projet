package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import POJO.Semaine;
public class SemaineDAO extends DAO<Semaine> {
	public SemaineDAO(Connection conn) {
		super(conn);
	}

	public int create(Semaine obj) { 
		try
		{
			System.out.println("SemaineDao -> " + obj.getNumSemaineDansAnnee());
			String requete2 = "INSERT INTO Semaine (congeScolaireOuNon,dateDebut,dateFin,numSemaineDansAnnee) VALUES (?,?,?,?)";
			PreparedStatement pst2 = connect.prepareStatement(requete2);

			//pst2.setInt(1, numSemaine);     //L'id qui lie la table moniteur a la table personne
			pst2.setBoolean(1, obj.getCongeScolaire());
			pst2.setDate(2, obj.getDateDebut());
			pst2.setDate(3, obj.getDateFin());
			pst2.setInt(4, obj.getNumSemaineDansAnnee());

			pst2.executeUpdate();
			pst2.close();
			//System.out.println("Ajout d'un moniteur effectue");
			System.out.println("SemaineDao -> " + obj.getNumSemaineDansAnnee());
			return obj.getNumSemaine();
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	public boolean delete(Semaine obj) {
		return false;
	}

	public boolean update(Semaine obj) {
		return false;
	}

	// On cherche un �l�ve gr�ce � son id
	public Semaine find(int id) {
		Semaine semaine = new Semaine();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM semaine WHERE numSemaine = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				semaine.setCongeScolaire(rs.getBoolean("CongeScolaireOuNon"));
				semaine.setDateDebut(rs.getDate("dateDebut"));
				semaine.setDateFin(rs.getDate("dateFin"));
				semaine.setNumSemaineDansAnnee(rs.getInt("numSemaineDansAnnee"));
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
		return semaine;
	}

	public  ArrayList<Semaine> getList() {
		ArrayList<Semaine> liste = new ArrayList<Semaine>();

		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Semaine";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Semaine semaine = new Semaine(rs.getInt("numSemaine"), rs.getBoolean("CongeScolaireOuNon"), rs.getDate("dateDebut"), rs.getDate("dateFin"), rs.getInt("numSemaineDansAnnee"));
				liste.add(semaine);
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
}
