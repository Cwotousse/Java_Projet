package be.mousty.dao;
/**
	Classe DAO permettant � effectuer des requ�tes et les transformer en objet POJO.
	@author Adrien MOUSTY
	@version Finale 1.3.3
	@category DAO
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import be.mousty.accessToDao.CoursCollectifATD;
import be.mousty.accessToDao.CoursParticulierATD;
import be.mousty.accessToDao.ReservationATD;
import be.mousty.pojo.Accreditation;
import be.mousty.pojo.Client;
import be.mousty.pojo.Cours;
import be.mousty.pojo.Eleve;
import be.mousty.pojo.Moniteur;
import be.mousty.pojo.Reservation;
import be.mousty.pojo.Semaine;


public class ReservationDAO extends DAO<Reservation> {

	public ReservationDAO(Connection conn) { super(conn); }

	/**
		Objectif : M�thode permettant de cr�er un �l�ment dans la DB.
		@version Finale 1.3.3
		@param Une instance de l'objet n�c�ssaire � la cr�ation.
		@return L'ID de l'enregistrement cr�� dans la DB.
	 */
	@Override public int create(Reservation obj) { 
		PreparedStatement pst_Res = null;
		PreparedStatement pst_Res_Cli = null;
		PreparedStatement pst_Res_Ele = null;
		PreparedStatement pst_Cou_Mon = null;
		PreparedStatement pst_Cou_Sem = null;
		PreparedStatement pst_Res_Cou = null; 
		PreparedStatement pst_numReserv = null;
		try
		{
			java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			String insertReservation = "INSERT INTO Reservation (heureDebut, heurefin, aPrisAssurance, aPaye) VALUES (?,?,?,?)";
			pst_Res = connect.prepareStatement(insertReservation);
			pst_Res.setInt(1, obj.getHeureDebut());
			pst_Res.setInt(2, obj.getHeureFin());
			pst_Res.setBoolean(3, obj.getAUneAssurance());
			pst_Res.setBoolean(4, obj.getaPaye());
			pst_Res.executeUpdate();

			String selectNumReserv = "SELECT MAX(numReservation) FROM Reservation";
			pst_numReserv = this.connect.prepareStatement(selectNumReserv);
			ResultSet rs = pst_numReserv.executeQuery();
			while (rs.next()) { obj.setNumReservation(rs.getInt(1)); }
			//System.out.println("ReservationDao -> " + obj.getNumReservation());

			String insertReservCli 		= "INSERT INTO ReservationClient 	(numReservation, numClient) VALUES (?,?)";
			pst_Res_Cli = connect.prepareStatement(insertReservCli);
			pst_Res_Cli.setInt(1, obj.getNumReservation());
			pst_Res_Cli.setInt(2, obj.getClient().getNumPersonne());
			pst_Res_Cli.executeUpdate();
			//System.out.println("ReservationClient insert");
			String insertReservEleve 	= "INSERT INTO ReservationEleve 	(numReservation, numEleve) 	VALUES (?,?)";
			pst_Res_Ele = connect.prepareStatement(insertReservEleve);
			pst_Res_Ele.setInt(1, obj.getNumReservation());
			pst_Res_Ele.setInt(2, obj.getEleve().getNumPersonne());
			pst_Res_Ele.executeUpdate();
			//System.out.println("ReservationEleve insert");

			String insertCoursMoniteur 	= "INSERT INTO CoursMoniteur 		(numCours, numMoniteur, dateAjout) 	VALUES (?,?,?)";
			pst_Cou_Mon = connect.prepareStatement(insertCoursMoniteur);
			pst_Cou_Mon.setInt(1, obj.getCours().getNumCours());
			pst_Cou_Mon.setInt(2, obj.getMoniteur().getNumPersonne());
			pst_Cou_Mon.setDate(3, now);
			pst_Cou_Mon.executeUpdate();

			//System.out.println("CoursMoniteur insert");
			String insertCoursSemaine 	= "INSERT INTO CoursSemaine 		(numCours, numSemaine, dateDebutReserv, dateFinReserv, dateAjout)	 	VALUES (?,?,?,?,?)";
			pst_Cou_Sem = connect.prepareStatement(insertCoursSemaine);
			pst_Cou_Sem.setInt(1, obj.getCours().getNumCours());
			pst_Cou_Sem.setInt(2, obj.getSemaine().getNumSemaine());
			pst_Cou_Sem.setDate(3, obj.getSemaine().getDateDebut());
			pst_Cou_Sem.setDate(4, obj.getSemaine().getDateFin());
			pst_Cou_Sem.setDate(5, now);
			pst_Cou_Sem.executeUpdate();
			//System.out.println("CoursSemaine insert");
			String insertReservCours 	= "INSERT INTO ReservationCours 	(numReservation, numCours) 	VALUES (?,?)";
			pst_Res_Cou = connect.prepareStatement(insertReservCours);
			pst_Res_Cou.setInt(1, obj.getNumReservation());
			pst_Res_Cou.setInt(2, obj.getCours().getNumCours());
			pst_Res_Cou.executeUpdate();
			//System.out.println("ReservationCours insert");

			pst_Res_Ele.close();
			pst_Cou_Mon.close();
			pst_Cou_Sem.close();
			pst_Res_Cou.close();
			pst_Res.close(); 
			pst_numReserv.close();
			return obj.getNumReservation();
			//}
		}

		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_Res != null ) {
				try {
					pst_Res_Cli.close();

				}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return -1;
	}

	/**
		Objectif : Supprimer une r�servation.
		@version Finale 1.3.3
		@param Une instance de l'objet n�c�ssaire � la suppression.
		@return Un bool�en pour savoir si la suppression s'est effectu�e correctemment
	 */
	@Override public boolean delete(Reservation obj) {
		PreparedStatement pst_Res 		= null;

		try
		{
			// Il se peut qu'il y ait plusieurs cours les m�mes avec les m�mes nombres de semaine, on s�lectionne donc le dernier.
			String deleteCoursMoniteur 	= "DELETE FROM CoursMoniteur WHERE dateAjout = "
					+ "(SELECT max(dateAjout) FROM CoursSemaine WHERE numCours in (SELECT numCours FROM ReservationCours WHERE numReservation = ?)); ";
			PreparedStatement pst_Cou_Mon 	= connect.prepareStatement(deleteCoursMoniteur);
			pst_Cou_Mon.setInt(1, obj.getNumReservation());
			pst_Cou_Mon.executeUpdate();
			pst_Cou_Mon.close();

			// Il se peut qu'il y ait plusieurs cours les m�mes avec les m�mes nombres de semaine, on s�lectionne donc le dernier.
			String deleteCoursSemaine 	= "DELETE FROM CoursSemaine WHERE dateAjout = "
					+ "(SELECT max(dateAjout) FROM CoursSemaine WHERE numCours in (SELECT numCours FROM ReservationCours WHERE numReservation = ?));";
			PreparedStatement pst_Cou_Sem 	= connect.prepareStatement(deleteCoursSemaine);
			pst_Cou_Sem.setInt(1, obj.getNumReservation());
			pst_Cou_Sem.executeUpdate();
			pst_Cou_Sem.close();


			String deleteReservCli 		= "DELETE FROM ReservationClient 	WHERE numReservation = ?";
			PreparedStatement pst_Res_Cli 	= connect.prepareStatement(deleteReservCli);
			pst_Res_Cli.setInt(1, obj.getNumReservation());
			pst_Res_Cli.executeUpdate();
			pst_Res_Cli.close();



			String deleteReservEleve 	= "DELETE FROM ReservationEleve 	WHERE numReservation = ?";
			PreparedStatement pst_Res_Ele 	= connect.prepareStatement(deleteReservEleve);
			pst_Res_Ele.setInt(1, obj.getNumReservation());
			pst_Res_Ele.executeUpdate();
			pst_Res_Ele.close();


			String deleteReservCours 	= "DELETE FROM ReservationCours 	WHERE numReservation = ?";
			PreparedStatement pst_Res_Cou 	= connect.prepareStatement(deleteReservCours);
			pst_Res_Cou.setInt(1, obj.getNumReservation());
			pst_Res_Cou.executeUpdate();
			pst_Res_Cou.close();

			String deleteReservation 	= "DELETE FROM Reservation WHERE numReservation = ?";
			pst_Res = connect.prepareStatement(deleteReservation);
			pst_Res.setInt(1, obj.getNumReservation());
			pst_Res.executeUpdate();

			return true;
		}

		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_Res != null) {
				try {
					/*pst_Res_Cli.close();
					pst_Res_Ele.close();
					pst_Cou_Mon.close();
					pst_Cou_Sem.close();
					pst_Res_Cou.close();*/
					pst_Res.close(); 
				}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return false;
	}

	/**
		Objectif : Mettre � jour le payement de la r�servation.
		@version Finale 1.3.3
		@param Des valeurs ins�r�es dans un objet permettant d'identifier un seul enregistrement dans la DB.
		@return boolean pour conna�tre l'�tat de la requ�te.
	 */
	public boolean update(Reservation obj) { 
		PreparedStatement pst_update = null;
		boolean estUpdate = false;
		try {
			// Si un r�sultat est retourn�, une update va �tre faite, et donc l'assurance ne doit PLUS �tre pay�e.
			String rech_ass = "UPDATE Reservation Set aPaye = ? WHERE numReservation = ?;";
			pst_update = this.connect.prepareStatement(rech_ass);
			pst_update.setBoolean(1, !obj.getaPaye());
			pst_update.setInt(2, obj.getNumReservation());
			pst_update.executeUpdate();
			estUpdate = true;
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_update != null) {
				try { pst_update.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return estUpdate;
	}

	/**
		Objectif : R�cup�rer un instance d'un objet compl�tement initialis�e correspondant aux valeurs entr�es en param�tre.
		@version Finale 1.3.3
		@param Des valeurs ins�r�es dans un objet permettant d'identifier une seule personne dans la DB.
		@return instance d'un objet compl�tement initialis�e correspondant aux valeurs entr�es en param�tre.
	 */
	@Override public Reservation getId(Reservation obj) {
		PreparedStatement pst = null;
		try {
			Semaine S 	= new Semaine();
			Cours C 	= new Cours();
			Eleve E 	= new Eleve();
			Client Cli 	= new Client();
			Moniteur M 	= new Moniteur();

			String sql = "SELECT * FROM Cours "
					+ "INNER JOIN CoursMoniteur ON CoursMoniteur.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					+ "INNER JOIN ReservationCours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN Reservation ON Reservation.numReservation = ReservationCours.numReservation "
					+ "INNER JOIN ReservationClient ON ReservationClient.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
					+ "WHERE numClient = ? AND Cours.numCours = ? AND numSemaine = ? AND numEleve = ? AND periodeCours = ? "
					+ "GROUP BY Reservation.numReservation;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, obj.getClient().getNumClient());
			pst.setInt(2, obj.getCours().getNumCours());
			pst.setInt(3, obj.getSemaine().getNumSemaine());
			pst.setInt(4, obj.getEleve().getNumEleve());
			pst.setString(5, obj.getCours().getPeriodeCours());
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();

				String selectSemaine 	= "SELECT * FROM Semaine	WHERE numSemaine 	= ? ";
				String selectCours 		= "SELECT * FROM Cours 		WHERE numCours 		= ? ";
				String selectEleve 		= "SELECT * FROM Eleve "
						+ "INNER JOIN Personne ON Personne.numPersonne = Eleve.numEleve "
						+ "WHERE numPersonne =  ? ";
				String selectClient 	= "SELECT * FROM Client 	INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Client.numClient "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ?  ";
				String selectMoniteur 	= "SELECT * FROM Moniteur	"
						+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ? ";
				String selectAccredMoni = "Select * from Accreditation INNER JOIN LigneAccreditation "
						+ "ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation WHERE numMoniteur = ?;";

				PreparedStatement pst_Sem = connect.prepareStatement(selectSemaine);
				PreparedStatement pst_Cou = connect.prepareStatement(selectCours);
				PreparedStatement pst_Ele = connect.prepareStatement(selectEleve);
				PreparedStatement pst_Cli = connect.prepareStatement(selectClient);
				PreparedStatement pst_Mon = connect.prepareStatement(selectMoniteur);
				PreparedStatement pst_Acc = connect.prepareStatement(selectAccredMoni);

				pst_Sem.setInt(1, rs.getInt("numSemaine"));
				pst_Cou.setInt(1, rs.getInt("numCours"));
				pst_Ele.setInt(1, rs.getInt("numEleve"));
				pst_Cli.setInt(1, rs.getInt("numClient"));
				pst_Mon.setInt(1, rs.getInt("numMoniteur"));
				pst_Acc.setInt(1, rs.getInt("numMoniteur"));

				ResultSet rs_Sem = pst_Sem.executeQuery();
				ResultSet rs_Cou = pst_Cou.executeQuery();
				ResultSet rs_Ele = pst_Ele.executeQuery();
				ResultSet rs_Cli = pst_Cli.executeQuery();
				ResultSet rs_Acc = pst_Acc.executeQuery();
				ResultSet rs_Mon = pst_Mon.executeQuery();

				while (rs_Sem.next()) {
					S = new Semaine();
					S.setNumSemaine(rs_Sem.getInt("numSemaine"));
					S.setCongeScolaire(rs_Sem.getBoolean("CongeScolaireOuNon"));
					S.setDateDebut(rs_Sem.getDate("dateDebut"));
					S.setDateFin(rs_Sem.getDate("dateFin"));
					S.setNumSemaineDansAnnee(rs_Sem.getInt("numSemaineDansAnnee"));
				}
				while (rs_Cou.next()) {
					C = new Cours();
					C.setNumCours(rs_Cou.getInt("numCours"));
					C.setNomSport(rs_Cou.getString("nomSport"));
					C.setPrix(rs_Cou.getInt("prix"));
					C.setMinEl(rs_Cou.getInt("minEleve"));
					C.setMaxEl(rs_Cou.getInt("maxEleve"));
					C.setPeriodeCours(rs_Cou.getString("periodeCours"));
				}
				while (rs_Ele.next()) {
					E = new Eleve();
					E.setNumEleve(rs_Ele.getInt("numEleve"));
					E.setCategorie(rs_Ele.getString("categorie"));
					E.setNumPersonne(rs_Ele.getInt("numEleve"));
					E.setNom(rs_Ele.getString("nom"));
					E.setPre(rs_Ele.getString("prenom"));
					E.setDateNaissance(rs_Ele.getDate("dateNaissance"));
					E.setAdresse(rs_Ele.getString("adresse"));
					E.setSexe(rs_Ele.getString("sexe"));
				}
				while (rs_Cli.next()) {
					Cli = new Client();
					Cli.setAdresseFacturation(rs_Cli.getString("adresseFacturation"));
					Cli.setNumClient(rs_Cli.getInt("numClient"));
					Cli.setPseudo(rs_Cli.getString("pseudo"));
					Cli.setMdp(rs_Cli.getString("mdp"));
					Cli.setTypeUtilisateur(rs_Cli.getInt("typeUtilisateur"));
					Cli.setNumPersonne(rs_Cli.getInt("numPersonne"));
					Cli.setNom(rs_Cli.getString("nom"));
					Cli.setPre(rs_Cli.getString("prenom"));
					Cli.setDateNaissance(rs_Cli.getDate("dateNaissance"));
					Cli.setAdresse(rs_Cli.getString("adresse"));
					Cli.setSexe(rs_Cli.getString("sexe"));
				}
				while(rs_Acc.next()){
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rs_Acc.getString("nomAccreditation"));
					A.setNumAccreditation(rs_Acc.getInt("numAccreditation"));
				}
				while (rs_Mon.next()) {
					M = new Moniteur();
					M.setNumMoniteur(rs_Mon.getInt("numMoniteur"));
					M.setAnneeExp(0);
					M.setAccrediList(listeAccred);
					M.setNumUtilisateur(rs_Mon.getInt("numUtilisateur"));
					M.setPseudo(rs_Mon.getString("pseudo"));
					M.setMdp(rs_Mon.getString("mdp"));
					M.setTypeUtilisateur(rs_Mon.getInt("typeUtilisateur"));
					M.setNumPersonne(rs_Mon.getInt("numPersonne"));
					M.setNom(rs_Mon.getString("nom"));
					M.setPre(rs_Mon.getString("prenom"));
					M.setDateNaissance(rs_Mon.getDate("dateNaissance"));
					M.setAdresse(rs_Mon.getString("adresse"));
					M.setSexe(rs_Mon.getString("sexe")); 
				}

				Reservation R = new Reservation();
				R.setHeureDebut(rs.getInt("heureDebut"));
				R.setHeureFin(rs.getInt("heureFin"));
				R.setNumReservation(rs.getInt("numReservation"));
				R.setAUneAssurance(rs.getBoolean("aPrisAssurance"));
				R.setSemaine(S);
				R.setCours(C);
				R.setEleve(E);
				R.setClient(Cli);
				R.setMoniteur(M);
				return R;
			}
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

	/**
		Objectif : Retourner un enregistrement de la DB par rapport � sa cl� primaire.
		@version Finale 1.3.3
		@param la valeur de la cl� primaire.
		@return Une instance de l'objet initialis�e avec les valeurs issue de la DB.
	 */
	@Override public Reservation find(int id) {
		PreparedStatement pst = null;
		try {
			Semaine S 	= new Semaine();
			Cours C 	= new Cours();
			Eleve E 	= new Eleve();
			Client Cli 	= new Client();
			Moniteur M 	= new Moniteur();

			String sql = "SELECT * FROM Cours "
					+ "INNER JOIN CoursMoniteur ON CoursMoniteur.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					//+ "INNER JOIN Personne On Personne.numPersonne = CoursMoniteur.numMoniteur "
					+ "INNER JOIN ReservationCours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN Reservation ON Reservation.numReservation = ReservationCours.numReservation "
					+ "INNER JOIN ReservationClient ON ReservationClient.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
					+ "WHERE ReservationClient.numReservation = ? ;";
			// Modifier ReservationClient.numClient si �a ne va pas

			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();

				String selectSemaine 	= "SELECT * FROM Semaine	WHERE numSemaine 	= ? ";
				String selectCours 		= "SELECT * FROM Cours 		WHERE numCours 		= ? ";
				String selectEleve 		= "SELECT * FROM Eleve "
						+ "INNER JOIN Personne ON Personne.numPersonne = Eleve.numEleve "
						+ "WHERE numPersonne =  ? ";
				String selectClient 	= "SELECT * FROM Client 	INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Client.numClient "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ?  ";
				String selectMoniteur 	= "SELECT * FROM Moniteur	"
						+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ? ";
				String selectAccredMoni = "Select * from Accreditation INNER JOIN LigneAccreditation "
						+ "ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation WHERE numMoniteur = ?;";

				PreparedStatement pst_Sem = connect.prepareStatement(selectSemaine);
				PreparedStatement pst_Cou = connect.prepareStatement(selectCours);
				PreparedStatement pst_Ele = connect.prepareStatement(selectEleve);
				PreparedStatement pst_Cli = connect.prepareStatement(selectClient);
				PreparedStatement pst_Mon = connect.prepareStatement(selectMoniteur);
				PreparedStatement pst_Acc = connect.prepareStatement(selectAccredMoni);

				pst_Sem.setInt(1, rs.getInt("numSemaine"));
				pst_Cou.setInt(1, rs.getInt("numCours"));
				pst_Ele.setInt(1, rs.getInt("numEleve"));
				pst_Cli.setInt(1, rs.getInt("numClient"));
				pst_Mon.setInt(1, rs.getInt("numMoniteur"));
				pst_Acc.setInt(1, rs.getInt("numMoniteur"));

				ResultSet rs_Sem = pst_Sem.executeQuery();
				ResultSet rs_Cou = pst_Cou.executeQuery();
				ResultSet rs_Ele = pst_Ele.executeQuery();
				ResultSet rs_Cli = pst_Cli.executeQuery();
				ResultSet rs_Acc = pst_Acc.executeQuery();
				ResultSet rs_Mon = pst_Mon.executeQuery();

				while (rs_Sem.next()) {
					S = new Semaine();
					S.setNumSemaine(rs_Sem.getInt("numSemaine"));
					S.setCongeScolaire(rs_Sem.getBoolean("CongeScolaireOuNon"));
					S.setDateDebut(rs_Sem.getDate("dateDebut"));
					S.setDateFin(rs_Sem.getDate("dateFin"));
					S.setNumSemaineDansAnnee(rs_Sem.getInt("numSemaineDansAnnee"));
				}
				while (rs_Cou.next()) {
					C = new Cours();
					C.setNumCours(rs_Cou.getInt("numCours"));
					C.setNomSport(rs_Cou.getString("nomSport"));
					C.setPrix(rs_Cou.getInt("prix"));
					C.setMinEl(rs_Cou.getInt("minEleve"));
					C.setMaxEl(rs_Cou.getInt("maxEleve"));
					C.setPeriodeCours(rs_Cou.getString("periodeCours"));
				}
				while (rs_Ele.next()) {
					E = new Eleve();
					E.setNumEleve(rs_Ele.getInt("numEleve"));
					E.setCategorie(rs_Ele.getString("categorie"));
					E.setNumPersonne(rs_Ele.getInt("numEleve"));
					E.setNom(rs_Ele.getString("nom"));
					E.setPre(rs_Ele.getString("prenom"));
					E.setDateNaissance(rs_Ele.getDate("dateNaissance"));
					E.setAdresse(rs_Ele.getString("adresse"));
					E.setSexe(rs_Ele.getString("sexe"));
				}
				while (rs_Cli.next()) {
					Cli = new Client();
					Cli.setAdresseFacturation(rs_Cli.getString("adresseFacturation"));
					Cli.setNumClient(rs_Cli.getInt("numClient"));
					Cli.setPseudo(rs_Cli.getString("pseudo"));
					Cli.setMdp(rs_Cli.getString("mdp"));
					Cli.setTypeUtilisateur(rs_Cli.getInt("typeUtilisateur"));
					Cli.setNumPersonne(rs_Cli.getInt("numPersonne"));
					Cli.setNom(rs_Cli.getString("nom"));
					Cli.setPre(rs_Cli.getString("prenom"));
					Cli.setDateNaissance(rs_Cli.getDate("dateNaissance"));
					Cli.setAdresse(rs_Cli.getString("adresse"));
					Cli.setSexe(rs_Cli.getString("sexe"));
				}
				while(rs_Acc.next()){
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rs_Acc.getString("nomAccreditation"));
					A.setNumAccreditation(rs_Acc.getInt("numAccreditation"));
				}
				while (rs_Mon.next()) {
					M = new Moniteur();
					M.setNumMoniteur(rs_Mon.getInt("numMoniteur"));
					M.setAnneeExp(0);
					M.setAccrediList(listeAccred);
					M.setNumUtilisateur(rs_Mon.getInt("numUtilisateur"));
					M.setPseudo(rs_Mon.getString("pseudo"));
					M.setMdp(rs_Mon.getString("mdp"));
					M.setTypeUtilisateur(rs_Mon.getInt("typeUtilisateur"));
					M.setNumPersonne(rs_Mon.getInt("numPersonne"));
					M.setNom(rs_Mon.getString("nom"));
					M.setPre(rs_Mon.getString("prenom"));
					M.setDateNaissance(rs_Mon.getDate("dateNaissance"));
					M.setAdresse(rs_Mon.getString("adresse"));
					M.setSexe(rs_Mon.getString("sexe")); 
				}

				Reservation R = new Reservation();
				R.setHeureDebut(rs.getInt("heureDebut"));
				R.setHeureFin(rs.getInt("heureFin"));
				R.setNumReservation(rs.getInt("numReservation"));
				R.setAUneAssurance(rs.getBoolean("aPrisAssurance"));
				R.setaPaye(rs.getBoolean("aPaye"));
				R.setSemaine(S);
				R.setCours(C);
				R.setEleve(E);
				R.setClient(Cli);
				R.setMoniteur(M);
				return R;
			}
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

	/**
		Objectif : Retourner la liste compl�te des enregistrements contenu dans une table
		@version Finale 1.3.3
		@return La liste compl�te des utilisateurs.
	 */
	public  ArrayList<Reservation> getList() {
		ArrayList<Reservation> liste = new ArrayList<Reservation>();

		PreparedStatement pst = null;
		try {
			Semaine S 	= new Semaine();
			Cours C 	= new Cours();
			Eleve E 	= new Eleve();
			Client Cli 	= new Client();
			Moniteur M 	= new Moniteur();

			String sql = "SELECT * FROM Cours "
					+ "INNER JOIN CoursMoniteur ON CoursMoniteur.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					//+ "INNER JOIN Personne On Personne.numPersonne = CoursMoniteur.numMoniteur "
					+ "INNER JOIN ReservationCours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN Reservation ON Reservation.numReservation = ReservationCours.numReservation "
					+ "INNER JOIN ReservationClient ON ReservationClient.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation"
					+ "GROUP BY Reservation.numReservation;";

			pst = this.connect.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//System.out.println("Reservation DAO -> " + rs.getInt("numReservation"));
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();

				String selectSemaine 	= "SELECT * FROM Semaine	WHERE numSemaine 	= ? ";
				String selectCours 		= "SELECT * FROM Cours 		WHERE numCours 		= ? ";
				String selectEleve 		= "SELECT * FROM Eleve "
						+ "INNER JOIN Personne ON Personne.numPersonne = Eleve.numEleve "
						+ "WHERE numPersonne =  ? ";
				String selectClient 	= "SELECT * FROM Client 	INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Client.numClient "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ?  ";
				String selectMoniteur 	= "SELECT * FROM Moniteur	"
						+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ? ";
				String selectAccredMoni = "Select * from Accreditation INNER JOIN LigneAccreditation "
						+ "ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation WHERE numMoniteur = ?;";

				PreparedStatement pst_Sem = connect.prepareStatement(selectSemaine);
				PreparedStatement pst_Cou = connect.prepareStatement(selectCours);
				PreparedStatement pst_Ele = connect.prepareStatement(selectEleve);
				PreparedStatement pst_Cli = connect.prepareStatement(selectClient);
				PreparedStatement pst_Mon = connect.prepareStatement(selectMoniteur);
				PreparedStatement pst_Acc = connect.prepareStatement(selectAccredMoni);

				pst_Sem.setInt(1, rs.getInt("numSemaine"));
				pst_Cou.setInt(1, rs.getInt("numCours"));
				pst_Ele.setInt(1, rs.getInt("numEleve"));
				pst_Cli.setInt(1, rs.getInt("numClient"));
				pst_Mon.setInt(1, rs.getInt("numMoniteur"));
				pst_Acc.setInt(1, rs.getInt("numMoniteur"));

				ResultSet rs_Sem = pst_Sem.executeQuery();
				ResultSet rs_Cou = pst_Cou.executeQuery();
				ResultSet rs_Ele = pst_Ele.executeQuery();
				ResultSet rs_Cli = pst_Cli.executeQuery();
				ResultSet rs_Acc = pst_Acc.executeQuery();
				ResultSet rs_Mon = pst_Mon.executeQuery();

				while (rs_Sem.next()) {
					S = new Semaine();
					S.setNumSemaine(rs_Sem.getInt("numSemaine"));
					S.setCongeScolaire(rs_Sem.getBoolean("CongeScolaireOuNon"));
					S.setDateDebut(rs_Sem.getDate("dateDebut"));
					S.setDateFin(rs_Sem.getDate("dateFin"));
					S.setNumSemaineDansAnnee(rs_Sem.getInt("numSemaineDansAnnee"));
				}
				while (rs_Cou.next()) {
					C = new Cours();
					C.setNumCours(rs_Cou.getInt("numCours"));
					C.setNomSport(rs_Cou.getString("nomSport"));
					C.setPrix(rs_Cou.getInt("prix"));
					C.setMinEl(rs_Cou.getInt("minEleve"));
					C.setMaxEl(rs_Cou.getInt("maxEleve"));
					C.setPeriodeCours(rs_Cou.getString("periodeCours"));
				}
				while (rs_Ele.next()) {
					E = new Eleve();
					E.setNumEleve(rs_Ele.getInt("numEleve"));
					E.setCategorie(rs_Ele.getString("categorie"));
					E.setNumPersonne(rs_Ele.getInt("numEleve"));
					E.setNom(rs_Ele.getString("nom"));
					E.setPre(rs_Ele.getString("prenom"));
					E.setDateNaissance(rs_Ele.getDate("dateNaissance"));
					E.setAdresse(rs_Ele.getString("adresse"));
					E.setSexe(rs_Ele.getString("sexe"));
				}
				while (rs_Cli.next()) {
					Cli = new Client();
					Cli.setAdresseFacturation(rs_Cli.getString("adresseFacturation"));
					Cli.setNumClient(rs_Cli.getInt("numClient"));
					Cli.setPseudo(rs_Cli.getString("pseudo"));
					Cli.setMdp(rs_Cli.getString("mdp"));
					Cli.setTypeUtilisateur(rs_Cli.getInt("typeUtilisateur"));
					Cli.setNumPersonne(rs_Cli.getInt("numPersonne"));
					Cli.setNom(rs_Cli.getString("nom"));
					Cli.setPre(rs_Cli.getString("prenom"));
					Cli.setDateNaissance(rs_Cli.getDate("dateNaissance"));
					Cli.setAdresse(rs_Cli.getString("adresse"));
					Cli.setSexe(rs_Cli.getString("sexe"));
				}
				while(rs_Acc.next()){
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rs_Acc.getString("nomAccreditation"));
					A.setNumAccreditation(rs_Acc.getInt("numAccreditation"));
				}
				while (rs_Mon.next()) {
					M = new Moniteur();
					M.setNumMoniteur(rs_Mon.getInt("numMoniteur"));
					M.setAnneeExp(0);
					M.setAccrediList(listeAccred);
					M.setNumUtilisateur(rs_Mon.getInt("numUtilisateur"));
					M.setPseudo(rs_Mon.getString("pseudo"));
					M.setMdp(rs_Mon.getString("mdp"));
					M.setTypeUtilisateur(rs_Mon.getInt("typeUtilisateur"));
					M.setNumPersonne(rs_Mon.getInt("numPersonne"));
					M.setNom(rs_Mon.getString("nom"));
					M.setPre(rs_Mon.getString("prenom"));
					M.setDateNaissance(rs_Mon.getDate("dateNaissance"));
					M.setAdresse(rs_Mon.getString("adresse"));
					M.setSexe(rs_Mon.getString("sexe")); 
				}

				Reservation R = new Reservation();
				R.setHeureDebut(rs.getInt("heureDebut"));
				R.setHeureFin(rs.getInt("heureFin"));
				R.setNumReservation(rs.getInt("numReservation"));
				R.setAUneAssurance(rs.getBoolean("aPrisAssurance"));
				R.setaPaye(rs.getBoolean("aPaye"));
				R.setSemaine(S);
				R.setCours(C);
				R.setEleve(E);
				R.setClient(Cli);
				R.setMoniteur(M);;
				liste.add(R);
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

	/**
		@version Finale 1.3.3
		@param Le num�ro de l'utilisateur qui souhaite voir ses cours
		@return La liste des r�servations de la personne qui fait cette requ�te.
	 */
	@Override public ArrayList<Reservation> getMyListSelonID(int idPersonne, long nonUsed, int nonUsed2, String nonUsed3) {
		ArrayList<Reservation> liste = new ArrayList<Reservation>();
		//Reservation R = new Reservation();

		PreparedStatement pst = null;
		try {
			Semaine S 	= new Semaine();
			Cours C 	= new Cours();
			Eleve E 	= new Eleve();
			Client Cli 	= new Client();
			Moniteur M 	= new Moniteur();

			String innerTypePersonne = "INNER JOIN Personne On Personne.numPersonne = ReservationClient.numClient ";

			// IL FAUT SAVOIR SI LA PERSONNE ENTREE EN PARAM EST UN CLIENT OU UN MONITEUR
			String rechercheMon = "SELECT * FROM Moniteur WHERE numMoniteur = ?";
			//String rechercheCli = "SELECT * FROM Client WHERE numClient = ?";

			PreparedStatement pst_Rec_Mon = this.connect.prepareStatement(rechercheMon);
			//PreparedStatement pst_Rec_Cli = this.connect.prepareStatement(rechercheCli);

			pst_Rec_Mon.setInt(1, idPersonne);
			//pst_Rec_Cli.setInt(1, idPersonne);

			ResultSet res_Rec_Mon = pst_Rec_Mon.executeQuery();
			//ResultSet res_Rec_Cli = pst_Rec_Cli.executeQuery();

			if (res_Rec_Mon.isBeforeFirst() ) { innerTypePersonne = "INNER JOIN Personne On Personne.numPersonne = CoursMoniteur.numMoniteur "; } 


			java.sql.Date dateNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			//System.out.println(dateNow.getTime());

			String sql = "SELECT distinct * FROM Cours "
					+ "INNER JOIN CoursMoniteur ON CoursMoniteur.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					+ "INNER JOIN ReservationCours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN Reservation ON Reservation.numReservation = ReservationCours.numReservation "
					+ "INNER JOIN ReservationClient ON ReservationClient.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
					+ innerTypePersonne
					+ "WHERE Personne.numPersonne = ? AND dateDebutReserv > " + dateNow.getTime()
					+ " GROUP BY Reservation.numReservation;";

			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, idPersonne);
			ResultSet rs = pst.executeQuery();


			while (rs.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();

				String selectSemaine 	= "SELECT * FROM Semaine	WHERE numSemaine 	= ? ";
				String selectCours 		= "SELECT * FROM Cours 		WHERE numCours 		= ? ";
				String selectEleve 		= "SELECT * FROM Eleve "
						+ "INNER JOIN Personne ON Personne.numPersonne = Eleve.numEleve "
						+ "WHERE numPersonne =  ? ";
				String selectClient 	= "SELECT * FROM Client 	INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Client.numClient "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ?  ";
				String selectMoniteur 	= "SELECT * FROM Moniteur	"
						+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ? ";
				String selectAccredMoni = "Select * from Accreditation INNER JOIN LigneAccreditation "
						+ "ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation WHERE numMoniteur = ?;";

				PreparedStatement pst_Sem = connect.prepareStatement(selectSemaine);
				PreparedStatement pst_Cou = connect.prepareStatement(selectCours);
				PreparedStatement pst_Ele = connect.prepareStatement(selectEleve);
				PreparedStatement pst_Cli = connect.prepareStatement(selectClient);
				PreparedStatement pst_Mon = connect.prepareStatement(selectMoniteur);
				PreparedStatement pst_Acc = connect.prepareStatement(selectAccredMoni);

				pst_Sem.setInt(1, rs.getInt("numSemaine"));
				pst_Cou.setInt(1, rs.getInt("numCours"));
				pst_Ele.setInt(1, rs.getInt("numEleve"));
				pst_Cli.setInt(1, rs.getInt("numClient"));
				pst_Mon.setInt(1, rs.getInt("numMoniteur"));
				pst_Acc.setInt(1, rs.getInt("numMoniteur"));

				ResultSet rs_Sem = pst_Sem.executeQuery();
				ResultSet rs_Cou = pst_Cou.executeQuery();
				ResultSet rs_Ele = pst_Ele.executeQuery();
				ResultSet rs_Cli = pst_Cli.executeQuery();
				ResultSet rs_Acc = pst_Acc.executeQuery();
				ResultSet rs_Mon = pst_Mon.executeQuery();

				while (rs_Sem.next()) {
					S = new Semaine();
					S.setNumSemaine(rs_Sem.getInt("numSemaine"));
					S.setCongeScolaire(rs_Sem.getBoolean("CongeScolaireOuNon"));
					S.setDateDebut(rs_Sem.getDate("dateDebut"));
					S.setDateFin(rs_Sem.getDate("dateFin"));
					S.setNumSemaineDansAnnee(rs_Sem.getInt("numSemaineDansAnnee"));
				}
				while (rs_Cou.next()) {
					C = new Cours();
					C.setNumCours(rs_Cou.getInt("numCours"));
					C.setNomSport(rs_Cou.getString("nomSport"));
					C.setPrix(rs_Cou.getInt("prix"));
					C.setMinEl(rs_Cou.getInt("minEleve"));
					C.setMaxEl(rs_Cou.getInt("maxEleve"));
					C.setPeriodeCours(rs_Cou.getString("periodeCours"));
				}
				while (rs_Ele.next()) {
					E = new Eleve();
					E.setNumEleve(rs_Ele.getInt("numEleve"));
					E.setCategorie(rs_Ele.getString("categorie"));
					E.setNumPersonne(rs_Ele.getInt("numEleve"));
					E.setNom(rs_Ele.getString("nom"));
					E.setPre(rs_Ele.getString("prenom"));
					E.setDateNaissance(rs_Ele.getDate("dateNaissance"));
					E.setAdresse(rs_Ele.getString("adresse"));
					E.setSexe(rs_Ele.getString("sexe"));
				}
				while (rs_Cli.next()) {
					Cli = new Client();
					Cli.setAdresseFacturation(rs_Cli.getString("adresseFacturation"));
					Cli.setNumClient(rs_Cli.getInt("numClient"));
					Cli.setPseudo(rs_Cli.getString("pseudo"));
					Cli.setMdp(rs_Cli.getString("mdp"));
					Cli.setTypeUtilisateur(rs_Cli.getInt("typeUtilisateur"));
					Cli.setNumPersonne(rs_Cli.getInt("numPersonne"));
					Cli.setNom(rs_Cli.getString("nom"));
					Cli.setPre(rs_Cli.getString("prenom"));
					Cli.setDateNaissance(rs_Cli.getDate("dateNaissance"));
					Cli.setAdresse(rs_Cli.getString("adresse"));
					Cli.setSexe(rs_Cli.getString("sexe"));
				}
				while(rs_Acc.next()){
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rs_Acc.getString("nomAccreditation"));
					A.setNumAccreditation(rs_Acc.getInt("numAccreditation"));
				}
				while (rs_Mon.next()) {
					M = new Moniteur();
					M.setNumMoniteur(rs_Mon.getInt("numMoniteur"));
					M.setAnneeExp(0);
					M.setAccrediList(listeAccred);
					M.setNumUtilisateur(rs_Mon.getInt("numUtilisateur"));
					M.setPseudo(rs_Mon.getString("pseudo"));
					M.setMdp(rs_Mon.getString("mdp"));
					M.setTypeUtilisateur(rs_Mon.getInt("typeUtilisateur"));
					M.setNumPersonne(rs_Mon.getInt("numPersonne"));
					M.setNom(rs_Mon.getString("nom"));
					M.setPre(rs_Mon.getString("prenom"));
					M.setDateNaissance(rs_Mon.getDate("dateNaissance"));
					M.setAdresse(rs_Mon.getString("adresse"));
					M.setSexe(rs_Mon.getString("sexe")); 
				}

				Reservation R = new Reservation();
				R.setHeureDebut(rs.getInt("heureDebut"));
				R.setHeureFin(rs.getInt("heureFin"));
				R.setNumReservation(rs.getInt("numReservation"));
				R.setAUneAssurance(rs.getBoolean("aPrisAssurance"));
				R.setaPaye(rs.getBoolean("aPaye"));
				R.setSemaine(S);
				R.setCours(C);
				R.setEleve(E);
				R.setClient(Cli);
				R.setMoniteur(M);
				liste.add(R);
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

	/**
		@version Finale 1.3.3
		@param Un objet de type cours permettant d'avoir les informations n�c�ssaires � l'ex�cution de la query
		@return Retourne la liste  des r�servations selon le num�ro de la semaine et de la p�riode.
	 */
	@Override public ArrayList<Reservation> getListSelonCriteres(Reservation obj) {
		ArrayList<Reservation> liste = new ArrayList<Reservation>();

		PreparedStatement pst = null;
		try {
			Semaine S 	= new Semaine();
			Cours C 	= new Cours();
			Eleve E 	= new Eleve();
			Client Cli 	= new Client();
			Moniteur M 	= new Moniteur();

			String sql = "SELECT * FROM Cours "
					+ "INNER JOIN CoursMoniteur ON CoursMoniteur.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					+ "INNER JOIN ReservationCours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN Reservation ON Reservation.numReservation = ReservationCours.numReservation "
					+ "INNER JOIN ReservationClient ON ReservationClient.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
					+ "WHERE CoursSemaine.numSemaine = ? AND Cours.periodeCours = ? ;";

			pst = this.connect.prepareStatement(sql);
			//pst.setInt(1, numMoniteur);
			pst.setInt(1, obj.getSemaine().getNumSemaine());
			pst.setString(2, obj.getCours().getPeriodeCours());
			System.out.println("ReservationDao -> numSemaine : " + obj.getSemaine().getNumSemaine());
			System.out.println("ReservationDao -> periode : " + obj.getCours().getPeriodeCours());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();

				String selectSemaine 	= "SELECT * FROM Semaine	WHERE numSemaine 	= ? ";
				String selectCours 		= "SELECT * FROM Cours 		WHERE numCours 		= ? ";
				String selectEleve 		= "SELECT * FROM Eleve "
						+ "INNER JOIN Personne ON Personne.numPersonne = Eleve.numEleve "
						+ "WHERE numPersonne =  ? ";
				String selectClient 	= "SELECT * FROM Client 	INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Client.numClient "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ?  ";
				String selectMoniteur 	= "SELECT * FROM Moniteur	"
						+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ? ";
				String selectAccredMoni = "Select * from Accreditation INNER JOIN LigneAccreditation "
						+ "ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation WHERE numMoniteur = ?;";

				PreparedStatement pst_Sem = connect.prepareStatement(selectSemaine);
				PreparedStatement pst_Cou = connect.prepareStatement(selectCours);
				PreparedStatement pst_Ele = connect.prepareStatement(selectEleve);
				PreparedStatement pst_Cli = connect.prepareStatement(selectClient);
				PreparedStatement pst_Mon = connect.prepareStatement(selectMoniteur);
				PreparedStatement pst_Acc = connect.prepareStatement(selectAccredMoni);

				pst_Sem.setInt(1, rs.getInt("numSemaine"));
				pst_Cou.setInt(1, rs.getInt("numCours"));
				pst_Ele.setInt(1, rs.getInt("numEleve"));
				pst_Cli.setInt(1, rs.getInt("numClient"));
				pst_Mon.setInt(1, rs.getInt("numMoniteur"));
				pst_Acc.setInt(1, rs.getInt("numMoniteur"));

				ResultSet rs_Sem = pst_Sem.executeQuery();
				ResultSet rs_Cou = pst_Cou.executeQuery();
				ResultSet rs_Ele = pst_Ele.executeQuery();
				ResultSet rs_Cli = pst_Cli.executeQuery();
				ResultSet rs_Acc = pst_Acc.executeQuery();
				ResultSet rs_Mon = pst_Mon.executeQuery();

				while (rs_Sem.next()) {
					S = new Semaine();
					S.setNumSemaine(rs_Sem.getInt("numSemaine"));
					S.setCongeScolaire(rs_Sem.getBoolean("CongeScolaireOuNon"));
					S.setDateDebut(rs_Sem.getDate("dateDebut"));
					S.setDateFin(rs_Sem.getDate("dateFin"));
					S.setNumSemaineDansAnnee(rs_Sem.getInt("numSemaineDansAnnee"));
				}
				while (rs_Cou.next()) {
					C = new Cours();
					C.setNumCours(rs_Cou.getInt("numCours"));
					C.setNomSport(rs_Cou.getString("nomSport"));
					C.setPrix(rs_Cou.getInt("prix"));
					C.setMinEl(rs_Cou.getInt("minEleve"));
					C.setMaxEl(rs_Cou.getInt("maxEleve"));
					C.setPeriodeCours(rs_Cou.getString("periodeCours"));
				}
				while (rs_Ele.next()) {
					E = new Eleve();
					E.setNumEleve(rs_Ele.getInt("numEleve"));
					E.setCategorie(rs_Ele.getString("categorie"));
					E.setNumPersonne(rs_Ele.getInt("numEleve"));
					E.setNom(rs_Ele.getString("nom"));
					E.setPre(rs_Ele.getString("prenom"));
					E.setDateNaissance(rs_Ele.getDate("dateNaissance"));
					E.setAdresse(rs_Ele.getString("adresse"));
					E.setSexe(rs_Ele.getString("sexe"));
				}
				while (rs_Cli.next()) {
					Cli = new Client();
					Cli.setAdresseFacturation(rs_Cli.getString("adresseFacturation"));
					Cli.setNumClient(rs_Cli.getInt("numClient"));
					Cli.setPseudo(rs_Cli.getString("pseudo"));
					Cli.setMdp(rs_Cli.getString("mdp"));
					Cli.setTypeUtilisateur(rs_Cli.getInt("typeUtilisateur"));
					Cli.setNumPersonne(rs_Cli.getInt("numPersonne"));
					Cli.setNom(rs_Cli.getString("nom"));
					Cli.setPre(rs_Cli.getString("prenom"));
					Cli.setDateNaissance(rs_Cli.getDate("dateNaissance"));
					Cli.setAdresse(rs_Cli.getString("adresse"));
					Cli.setSexe(rs_Cli.getString("sexe"));
				}
				while(rs_Acc.next()){
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rs_Acc.getString("nomAccreditation"));
					A.setNumAccreditation(rs_Acc.getInt("numAccreditation"));
				}
				while (rs_Mon.next()) {
					M = new Moniteur();
					M.setNumMoniteur(rs_Mon.getInt("numMoniteur"));
					M.setAnneeExp(0);
					M.setAccrediList(listeAccred);
					M.setNumUtilisateur(rs_Mon.getInt("numUtilisateur"));
					M.setPseudo(rs_Mon.getString("pseudo"));
					M.setMdp(rs_Mon.getString("mdp"));
					M.setTypeUtilisateur(rs_Mon.getInt("typeUtilisateur"));
					M.setNumPersonne(rs_Mon.getInt("numPersonne"));
					M.setNom(rs_Mon.getString("nom"));
					M.setPre(rs_Mon.getString("prenom"));
					M.setDateNaissance(rs_Mon.getDate("dateNaissance"));
					M.setAdresse(rs_Mon.getString("adresse"));
					M.setSexe(rs_Mon.getString("sexe")); 
				}

				Reservation R = new Reservation();
				R.setHeureDebut(rs.getInt("heureDebut"));
				R.setHeureFin(rs.getInt("heureFin"));
				R.setNumReservation(rs.getInt("numReservation"));
				R.setAUneAssurance(rs.getBoolean("aPrisAssurance"));
				R.setaPaye(rs.getBoolean("aPaye"));
				R.setSemaine(S);
				R.setCours(C);
				R.setEleve(E);
				R.setClient(Cli);
				R.setMoniteur(M);
				liste.add(R);
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

	/**
	 * Objectif : Si la personne a pris une r�servation la m�me semaine pour un cours au matin/apr�m, s'il reprend un autre cours 
	 * alors la nouvelle assurance est inscrite et l'ancienne est effac�e.
	 * 	@version Finale 1.3.3
	 * 	@param Le num�ro de l'�l�ve
	 * 	@param Le num�ro de la semaine
	 *	@param La p�riode
	 *	@return Un bool�en pour savoir si l'update s'est bien effectu�.
	 */
	public boolean updateAssurance(int numEleve, int numSemaine, String periode){
		PreparedStatement upd_ass = null;
		boolean estUpdate = false;
		try {
			String verifPeriode;
			switch(periode){
			case "09-12": verifPeriode = " = '14-17' ";
			break;
			case "14-17": verifPeriode = " = '09-12' ";
			break;
			case "12-13": verifPeriode = " IN('12-14', '13-14') ";
			break;
			case "13-14": verifPeriode = " IN('12-13', '12-14') ";
			break;
			case "12-14": verifPeriode = " IN('12-13', '13-14') ";
			break;
			default : verifPeriode = " = ? ";
			break;
			}

			String str_upd_ass = "UPDATE Reservation SET aPrisAssurance = 0 WHERE Reservation.numReservation in ( "
					+ "SELECT ReservationClient.numReservation FROM ReservationClient "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationCours ON ReservationCours.numReservation = Reservation.numReservation "
					+ "INNER JOIN Cours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					+ "WHERE aPrisAssurance = 1 AND numEleve = ? AND numSemaine = ? AND Cours.periodeCours "+ verifPeriode +");";

			upd_ass = this.connect.prepareStatement(str_upd_ass);
			upd_ass.setInt(1, numEleve);
			upd_ass.setInt(2, numSemaine);
			upd_ass.executeUpdate();
			estUpdate = true;
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (upd_ass != null) {
				try { upd_ass.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return estUpdate;
	}

	/**
	 * Objectif : Li�e � la query au dessus, il faut savoir s'il faut update l'assurance avant de le faire, car il faut afficher les infos au pr�alable
	 * 	@version Finale 1.3.3
	 * 	@param Le num�ro de l'�l�ve
	 * 	@param Le num�ro de la semaine
	 *	@param La p�riode
	 *	@return Un bool�en pour savoir s'il faut update.
	 */
	public boolean besoinDupdateOuNonAssurance(int numEleve, int numSemaine, String periode){
		PreparedStatement pst_rech = null;
		boolean estUpdate = false;
		try {
			String verifPeriode;
			switch(periode){
			case "09-12": verifPeriode = " = '14-17' ";
			break;
			case "14-17": verifPeriode = " = '09-12' ";
			break;
			case "12-13": verifPeriode = " IN('12-14', '13-14') ";
			break;
			case "13-14": verifPeriode = " IN('12-13', '12-14') ";
			break;
			case "12-14": verifPeriode = " IN('12-13', '13-14') ";
			break;
			default : verifPeriode = " = ? ";
			break;
			}
			// Si un r�sultat est retourn�, une update va �tre faite, et donc l'assurance ne doit PLUS �tre pay�e.
			String rech_ass = "SELECT aPrisAssurance FROM  Reservation WHERE Reservation.numReservation in ( "
					+ "SELECT ReservationClient.numReservation FROM ReservationClient "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationCours ON ReservationCours.numReservation = Reservation.numReservation "
					+ "INNER JOIN Cours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					+ "WHERE aPrisAssurance = 1 AND numEleve = ? AND numSemaine = ? AND Cours.periodeCours "+ verifPeriode +");";
			pst_rech = this.connect.prepareStatement(rech_ass);
			pst_rech.setInt(1, numEleve);
			pst_rech.setInt(2, numSemaine);
			ResultSet res_rech = pst_rech.executeQuery();

			while (res_rech.next()) {
				estUpdate = true;
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_rech != null) {
				try { pst_rech.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return estUpdate;
	}

	/**
	 * Objectif :Retourner 15% du prix des deux cours choisi si la personne a bien choisi deux cours pour une seule personne la m�me semaine.
	 * 	@version Finale 1.3.3
	 *  @param Le numro de la semaine
	 * 	@param Le num�ro de l'�l�ve
	 *	@param Le prix du cours
	 *	@return La valeur de la r�duction.
	 */
	public int valeurReduction(int numSemaine, int numEleve, double prixCours){
		PreparedStatement pst_rech_elev = null;
		int sommeReduc = 0;
		String ajoutParamEleve = "";
		if (numEleve != -1)
			ajoutParamEleve = " ReservationEleve.numEleve = " + numEleve + " AND ";
		try {
			String sql_rech_elev = "select distinct numEleve from ReservationEleve WHERE " + ajoutParamEleve + "numReservation IN "
					+ "(SELECT numReservation FROM ReservationCours WHERE numCours IN "
					+ "(SELECT numCours FROM CoursSemaine WHERE numSemaine = ?));";
			pst_rech_elev = this.connect.prepareStatement(sql_rech_elev);
			pst_rech_elev.setInt(1, numSemaine);

			ResultSet res_rech_Elev = pst_rech_elev.executeQuery();
			while (res_rech_Elev.next()) {
				int numElv = res_rech_Elev.getInt("numEleve");

				String sql_rech_reduc = "select count(*) AS cptCours, (sum(prix)*15 /100) AS somme FROM Cours WHERE numCours IN "
						+ "( SELECT distinct Cours.numCours FROM Cours "
						+ "INNER JOIN ReservationCours ON ReservationCours.numCours = Cours.numCours "
						+ "INNER JOIN ReservationEleve ON ReservationCours.numReservation = ReservationEleve.numReservation "
						+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
						+ "WHERE numSemaine = ? AND periodeCours IN('09-12', '14-17') AND numEleve = " + numElv + ");";

				PreparedStatement pst_rech_reduc = this.connect.prepareStatement(sql_rech_reduc);
				pst_rech_reduc.setInt(1, numSemaine);
				ResultSet res_rech_reduc = pst_rech_reduc.executeQuery();
				while (res_rech_reduc.next()) {
					if(numEleve != -1 && res_rech_reduc.getInt("cptCours") >= 1){ sommeReduc+= res_rech_reduc.getInt("somme") + (prixCours*15/100); }
					else if (res_rech_reduc.getInt("cptCours") > 1){ sommeReduc+= res_rech_reduc.getInt("somme"); }
				}
				pst_rech_reduc.close();
			}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst_rech_elev != null) {
				try { 
					pst_rech_elev.close();
				}
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return sommeReduc;
	}

	/**
	 * Objectif : UTilis� pour calculer les places restantes pour un cours particulier
	 * 	@version Finale 1.3.3
	 * 	@param Le num�ro d'utilisateur pour r�cup�rer ses r�servations
	 *	@return La date de d�but d'une reservation en miliseconde
	 */
	public long getDateDebutReserv(int id) {
		PreparedStatement pst = null;
		long dateDebut = -1;
		try {
			String sql = "SELECT * from CoursSemaine "
					+ "INNER JOIN ReservationCours ON ReservationCours.numCours = CoursSemaine.numCours "
					+ "WHERE dateDebutReserv = dateFinReserv AND numReservation = ? "
					+ "GROUP BY ReservationCours.numReservation;";

			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) { dateDebut= rs.getLong("dateDebutReserv"); }

		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return dateDebut;
	}

	/**
	 * Objectif : Regarder si des cours qui arrivent > 24heures sont suceptibles d'�tre annul�s
	 * 	@version Finale 1.3.3
	 * 	@param Le num�ro d'utilisateur pour r�cup�rer ses cours
	 *	@param Le type d'utilisateur pour ajuster le texte � display
	 *	@return La liste des r�servations potentiellement annul�es
	 */
	public ArrayList<Reservation> getReservationAnnulee(int numUtilisateur, int typeUtilisateur){
		ArrayList<Reservation> liste = new ArrayList<Reservation>();
		PreparedStatement pst = null;
		try {
			Semaine S 	= new Semaine();
			Cours C 	= new Cours();
			Eleve E 	= new Eleve();
			Client Cli 	= new Client();
			Moniteur M 	= new Moniteur();
			java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			String typePers = " numMoniteur = ?";
			if (typeUtilisateur == 2)
				typePers = " numClient = ?";

			String sql = "SELECT * FROM Cours "
					+ "INNER JOIN CoursMoniteur ON CoursMoniteur.numCours = Cours.numCours "
					+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
					+ "INNER JOIN ReservationCours ON ReservationCours.numCours = Cours.numCours "
					+ "INNER JOIN Reservation ON Reservation.numReservation = ReservationCours.numReservation "
					+ "INNER JOIN ReservationClient ON ReservationClient.numReservation = Reservation.numReservation "
					+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
					+ "WHERE " + typePers + " AND dateDebutReserv - " + now.getTime() + " < 86400000 AND dateDebutReserv - " + now.getTime() + " > 0 "
					// 86400000 -> Un jour en milisecondes
					+ "GROUP BY Reservation.numReservation ;";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, numUtilisateur);
			ResultSet res_resv_annul = pst.executeQuery();
			while (res_resv_annul.next()) {
				ArrayList<Accreditation> listeAccred = new ArrayList<Accreditation>();

				String selectSemaine 	= "SELECT * FROM Semaine	WHERE numSemaine 	= ? ";
				String selectCours 		= "SELECT * FROM Cours 		WHERE numCours 		= ? ";
				String selectEleve 		= "SELECT * FROM Eleve "
						+ "INNER JOIN Personne ON Personne.numPersonne = Eleve.numEleve "
						+ "WHERE numPersonne =  ? ";
				String selectClient 	= "SELECT * FROM Client 	INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Client.numClient "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ?  ";
				String selectMoniteur 	= "SELECT * FROM Moniteur	"
						+ "INNER JOIN Utilisateur ON Utilisateur.numUtilisateur = Moniteur.numMoniteur "
						+ "INNER JOIN Personne ON Personne.numPersonne = Utilisateur.numUtilisateur "
						+ "WHERE numPersonne =  ? ";
				String selectAccredMoni = "Select * from Accreditation INNER JOIN LigneAccreditation "
						+ "ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation WHERE numMoniteur = ?;";

				PreparedStatement pst_Sem = connect.prepareStatement(selectSemaine);
				PreparedStatement pst_Cou = connect.prepareStatement(selectCours);
				PreparedStatement pst_Ele = connect.prepareStatement(selectEleve);
				PreparedStatement pst_Cli = connect.prepareStatement(selectClient);
				PreparedStatement pst_Mon = connect.prepareStatement(selectMoniteur);
				PreparedStatement pst_Acc = connect.prepareStatement(selectAccredMoni);

				pst_Sem.setInt(1, res_resv_annul.getInt("numSemaine"));
				pst_Cou.setInt(1, res_resv_annul.getInt("numCours"));
				pst_Ele.setInt(1, res_resv_annul.getInt("numEleve"));
				pst_Cli.setInt(1, res_resv_annul.getInt("numClient"));
				pst_Mon.setInt(1, res_resv_annul.getInt("numMoniteur"));
				pst_Acc.setInt(1, res_resv_annul.getInt("numMoniteur"));

				ResultSet rs_Sem = pst_Sem.executeQuery();
				ResultSet rs_Cou = pst_Cou.executeQuery();
				ResultSet rs_Ele = pst_Ele.executeQuery();
				ResultSet rs_Cli = pst_Cli.executeQuery();
				ResultSet rs_Acc = pst_Acc.executeQuery();
				ResultSet rs_Mon = pst_Mon.executeQuery();

				while (rs_Sem.next()) {
					S = new Semaine();
					S.setNumSemaine(rs_Sem.getInt("numSemaine"));
					S.setCongeScolaire(rs_Sem.getBoolean("CongeScolaireOuNon"));
					S.setDateDebut(rs_Sem.getDate("dateDebut"));
					S.setDateFin(rs_Sem.getDate("dateFin"));
					S.setNumSemaineDansAnnee(rs_Sem.getInt("numSemaineDansAnnee"));
				}
				while (rs_Cou.next()) {
					C = new Cours();
					C.setNumCours(rs_Cou.getInt("numCours"));
					C.setNomSport(rs_Cou.getString("nomSport"));
					C.setPrix(rs_Cou.getInt("prix"));
					C.setMinEl(rs_Cou.getInt("minEleve"));
					C.setMaxEl(rs_Cou.getInt("maxEleve"));
					C.setPeriodeCours(rs_Cou.getString("periodeCours"));
				}
				while (rs_Ele.next()) {
					E = new Eleve();
					E.setNumEleve(rs_Ele.getInt("numEleve"));
					E.setCategorie(rs_Ele.getString("categorie"));
					E.setNumPersonne(rs_Ele.getInt("numEleve"));
					E.setNom(rs_Ele.getString("nom"));
					E.setPre(rs_Ele.getString("prenom"));
					E.setDateNaissance(rs_Ele.getDate("dateNaissance"));
					E.setAdresse(rs_Ele.getString("adresse"));
					E.setSexe(rs_Ele.getString("sexe"));
				}
				while (rs_Cli.next()) {
					Cli = new Client();
					Cli.setAdresseFacturation(rs_Cli.getString("adresseFacturation"));
					Cli.setNumClient(rs_Cli.getInt("numClient"));
					Cli.setPseudo(rs_Cli.getString("pseudo"));
					Cli.setMdp(rs_Cli.getString("mdp"));
					Cli.setTypeUtilisateur(rs_Cli.getInt("typeUtilisateur"));
					Cli.setNumPersonne(rs_Cli.getInt("numPersonne"));
					Cli.setNom(rs_Cli.getString("nom"));
					Cli.setPre(rs_Cli.getString("prenom"));
					Cli.setDateNaissance(rs_Cli.getDate("dateNaissance"));
					Cli.setAdresse(rs_Cli.getString("adresse"));
					Cli.setSexe(rs_Cli.getString("sexe"));
				}
				while(rs_Acc.next()){
					Accreditation A = new Accreditation();
					A.setNomAccreditation(rs_Acc.getString("nomAccreditation"));
					A.setNumAccreditation(rs_Acc.getInt("numAccreditation"));
				}
				while (rs_Mon.next()) {
					M = new Moniteur();
					M.setNumMoniteur(rs_Mon.getInt("numMoniteur"));
					M.setAnneeExp(0);
					M.setAccrediList(listeAccred);
					M.setNumUtilisateur(rs_Mon.getInt("numUtilisateur"));
					M.setPseudo(rs_Mon.getString("pseudo"));
					M.setMdp(rs_Mon.getString("mdp"));
					M.setTypeUtilisateur(rs_Mon.getInt("typeUtilisateur"));
					M.setNumPersonne(rs_Mon.getInt("numPersonne"));
					M.setNom(rs_Mon.getString("nom"));
					M.setPre(rs_Mon.getString("prenom"));
					M.setDateNaissance(rs_Mon.getDate("dateNaissance"));
					M.setAdresse(rs_Mon.getString("adresse"));
					M.setSexe(rs_Mon.getString("sexe")); 
				}

				// ATTENTION : Maintenant il faut savoir s'il y a assez de personnes pour ce cours ou non.
				String strPlaceCours = "";
				if (C.getPrix() > 90){
					CoursCollectifATD CCATD = new CoursCollectifATD();
					strPlaceCours = CCATD.calculerPlaceCours(
							C.getNumCours(),
							S.getNumSemaine(),
							M.getNumMoniteur());
				}
				else {
					CoursParticulierATD CPATD = new CoursParticulierATD(); 
					ReservationATD RATD = new ReservationATD();
					strPlaceCours = CPATD.calculerPlaceCours(
							C.getNumCours(),
							RATD.getDateDebutReserv(res_resv_annul.getInt("numReservation")),
							M.getNumMoniteur());
				}
				String[] parts = strPlaceCours.split("-");
				// Si le nombre max d'�l�ves - le nombre actuel est plus petit que le nbr minimum d'�l�ve, alors le cours risque d'�tre annul�.
				//System.out.println(C.getMaxEl() + " - " + Integer.parseInt(parts[0]) + " < " +C.getMinEl());
				if (C.getMaxEl() - Integer.parseInt(parts[0]) < C.getMinEl()){
					Reservation R = new Reservation();
					R.setHeureDebut(res_resv_annul.getInt("heureDebut"));
					R.setHeureFin(res_resv_annul.getInt("heureFin"));
					R.setNumReservation(res_resv_annul.getInt("numReservation"));
					R.setAUneAssurance(res_resv_annul.getBoolean("aPrisAssurance"));
					R.setaPaye(res_resv_annul.getBoolean("aPaye"));
					R.setSemaine(S);
					R.setCours(C);
					R.setEleve(E);
					R.setClient(Cli);
					R.setMoniteur(M);
					liste.add(R);
				}
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

	// Uniquement pour cours collectif.
	/**
	 * 	@version Finale 1.3.3
	 * 	@param Le num�ro de moniteur pour r�cup�rer ses accr�ditation (enfant, adulte)
	 *	@param Le nu�mro de la semaine
	 *  @param La p�riode
	 *	@return La cat�gorie
	 */
	public String getCategorieReservation(int numMoniteur, int numSemaine, String periode){
		String categorie = "";
		PreparedStatement pst = null;
		try {
			/*
			 * Il faut v�rifier que l'�l�ves propos� soit de la m�me cat�gorie qu'un autre si un cours est d�j� existant vis � vis du moniteur s�elctionn�
			 * -> Si un cours pour enfant est choisi pour la p�riode donn�e, alors la requ�te n'affiche que les enfants.
			 * */
			if (periode.equals("09-12") || periode.equals("14-17")){
				String sql_categ = "SELECT categorie FROM  Reservation "
						+ "INNER JOIN ReservationClient ON ReservationClient.numReservation = Reservation.numReservation  "
						+ "INNER JOIN ReservationEleve ON ReservationEleve.numReservation = Reservation.numReservation "
						+ "INNER JOIN Eleve ON Eleve.numEleve = ReservationEleve.numEleve "
						+ "INNER JOIN ReservationCours ON ReservationCours.numReservation = Reservation.numReservation "
						+ "INNER JOIN CoursMoniteur ON CoursMoniteur.numCours = ReservationCours.numCours "
						+ "INNER JOIN Moniteur ON Moniteur.numMoniteur = CoursMoniteur.numMoniteur "
						+ "INNER JOIN LigneAccreditation ON LigneAccreditation.numMoniteur = Moniteur.numMoniteur "
						+ "INNER JOIN Accreditation ON Accreditation.numAccreditation = LigneAccreditation.numAccreditation "
						+ "INNER JOIN Cours ON ReservationCours.numCours = Cours.numCours "
						+ "INNER JOIN CoursSemaine ON CoursSemaine.numCours = Cours.numCours "
						+ "where Eleve.categorie = Accreditation.nomAccreditation "
						+ "AND CoursSemaine.numSemaine = ? "
						+ "AND Moniteur.numMoniteur = ? "
						+ "AND periodeCours  = ? "
						+ "GROUP BY categorie;";
				pst = this.connect.prepareStatement(sql_categ);

				pst.setInt(1, numSemaine);
				pst.setInt(2, numMoniteur);
				pst.setString(3, periode);
				ResultSet res = pst.executeQuery();
				// int numPersonne, String nom, String pre, String adresse, String
				// sexe, Date dateNaissance, boolean aUneAssurance

				while (res.next()) { 
					// Retourne la cat�gorie du seul cours possible afin d'afficher les �l�ves qui entrent dans cette cat�gorie
					categorie = res.getString("categorie");
				}
			}
			return categorie;
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
		return categorie;
	}

	@Override
	public String calculerPlaceCours(int numCours, long numSemaine, int idMoniteur) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void creerTouteDisponibilites() {
		// TODO Auto-generated method stub

	}

	@Override
	public void creerTouteDisponibilitesSelonMoniteur(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public void AjouterSemainesDansDB(String start, String end) {
		// TODO Auto-generated method stub

	}


}
