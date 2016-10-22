package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import POJO.Utilisateur;
public class UtilisateurDAO extends DAO<Utilisateur> {
	public UtilisateurDAO(Connection conn) {
		super(conn);
	}

	public boolean create(Utilisateur obj) {
		try {
			String requete = "SELECT numUtilisateur, typeUtilisateur FROM  Utilisateur WHERE pseudo = '" + obj.getPseudo() + "' AND mdp = '" + obj.getMdp()  +"';";
			 
			 Statement stmt = connect.createStatement();

			// 5.2 Execution de l'insert into 
			 ResultSet find = stmt.executeQuery(requete);
			if (!find.next()){
				String sql = "INSERT INTO utilisateur " + "(pseudo, mdp, typeUtilisateur) " + " VALUES(?,?,?)";
				PreparedStatement pst = this.connect.prepareStatement(sql);
				pst.setString(1, obj.getPseudo());
				pst.setString(2, obj.getMdp());
				pst.setInt(3, obj.getTypeUtilisateur());
				pst.executeUpdate();
			pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean delete(Utilisateur obj) {
		return false;
	}

	public boolean update(Utilisateur obj) {
		return false;
	}

	// On cherche un élève grâce à son id
	public Utilisateur find(int id) {
		Utilisateur utilisateur = new Utilisateur();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM utilisateur WHERE numUtilisateur = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setMdp(rs.getString("mdp"));
				utilisateur.setTypeUtilisateur(rs.getInt("typeUtilisateur"));
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
		return utilisateur;
	}

	public ArrayList<Utilisateur> getList() {
		Utilisateur utilisateur;
		ArrayList<Utilisateur> liste = new ArrayList<Utilisateur>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM utilisateur";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				utilisateur = new Utilisateur();
				utilisateur.setPseudo(rs.getString("pseudo"));
				utilisateur.setMdp(rs.getString("mdp"));
				utilisateur.setTypeUtilisateur(rs.getInt("typeUtilisateur"));
				liste.add(utilisateur);
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
}
