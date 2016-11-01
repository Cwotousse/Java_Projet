package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import POJO.Reservation;


public class ReservationDAO extends DAO<Reservation> {
	public ReservationDAO(Connection conn) {
		super(conn);
	}

	public int create(Reservation obj) { 
		try
		{

				//on l'utilise pour ajouter les donn�es dans la table Reservation
				System.out.println("ReservationDao -> " + obj.getNumReservation());
				String requete2 = "INSERT INTO Reservation (pseudo, mdp, typeReservation, numReservation) VALUES (?,?,?,?)";
				PreparedStatement pst2 = connect.prepareStatement(requete2);

				//pst2.setInt(1, numReservation);     //L'id qui lie la table moniteur a la table personne
				pst2.setString(1, obj.getPseudo());
				pst2.setString(2, obj.getMdp());
				pst2.setInt(3, obj.getTypeReservation());
				pst2.setInt(4, obj.getNumReservation());

				pst2.executeUpdate();
				pst2.close();
				//System.out.println("Ajout d'un moniteur effectue");
				System.out.println("ReservationDao -> " + obj.getNumReservation());
				return obj.getNumReservation();
		}

		catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return -1;
		}
	}
	public boolean delete(Reservation obj) {
		return false;
	}

	public boolean update(Reservation obj) {
		return false;
	}

	// On cherche un �l�ve gr�ce � son id
	public Reservation find(int id) {
		Reservation reservation = new Reservation();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM reservation WHERE numReservation = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				reservation.setPseudo(rs.getString("pseudo"));
				reservation.setMdp(rs.getString("mdp"));
				reservation.setTypeReservation(rs.getInt("typeReservation"));
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
		return reservation;
	}

	public  ArrayList<Reservation> getList() {
		ArrayList<Reservation> liste = new ArrayList<Reservation>();
		
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Reservation INNER JOIN Personne ON Personne.numPersonne = Reservation.numReservation";
			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Reservation reservation = new Reservation(rs.getInt("numReservation"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"),
						rs.getString("sexe"), rs.getDate("dateNaissance"), rs.getString("pseudo"), rs.getString("mdp"), rs.getInt("typeReservation"));
				liste.add(reservation);
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
}

