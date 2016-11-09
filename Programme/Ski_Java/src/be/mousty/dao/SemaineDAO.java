package be.mousty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import be.mousty.accessToDao.SemaineATD;
import be.mousty.pojo.Semaine;

public class SemaineDAO extends DAO<Semaine> {
	public SemaineDAO(Connection conn) { super(conn); }

	public int create(Semaine obj) { 
		PreparedStatement pst2 = null;
		try
		{
			System.out.println("SemaineDao -> " + obj.getNumSemaineDansAnnee());
			String requete2 = "INSERT INTO Semaine (congeScolaireOuNon,dateDebut,dateFin,numSemaineDansAnnee) VALUES (?,?,?,?)";
			pst2 = connect.prepareStatement(requete2);

			//pst2.setInt(1, numSemaine);     //L'id qui lie la table moniteur a la table personne
			pst2.setBoolean(1, obj.getCongeScolaire());
			pst2.setDate(2, obj.getDateDebut());
			pst2.setDate(3, obj.getDateFin());
			pst2.setInt(4, obj.getNumSemaineDansAnnee());

			pst2.executeUpdate();
			pst2.close();


			PreparedStatement pst_numReserv;
			String selectNumReserv = "SELECT MAX(numSemaine) FROM Semaine";
			pst_numReserv = this.connect.prepareStatement(selectNumReserv);
			ResultSet rs = pst_numReserv.executeQuery();
			while (rs.next()) { obj.setNumSemaine(rs.getInt(1)); }
			System.out.println("ReservationDao -> " + obj.getNumSemaine());

			return obj.getNumSemaine();
		}

		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst2 != null) {
				try { pst2.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return -1;
	}
	public boolean delete(Semaine obj) { return false; }

	public boolean update(Semaine obj) { return false; }

	// On cherche un �l�ve gr�ce � son id
	public Semaine find(int id) {
		PreparedStatement pst = null;
		Semaine S = new Semaine();
		try {
			String sql = "SELECT * FROM semaine WHERE numSemaine = ?";
			pst = this.connect.prepareStatement(sql);
			pst.setInt(1, id);
			ResultSet res_rec_sem = pst.executeQuery();
			while (res_rec_sem.next()) {
				//public Semaine(int numSemaine,  boolean congeScolaire, Date dateDebut, Date dateFin, int numSemaineDansAnnee){
				S.setNumSemaine(res_rec_sem.getInt("numSemaine"));
				S.setCongeScolaire(res_rec_sem.getBoolean("CongeScolaireOuNon"));
				S.setDateDebut(res_rec_sem.getDate("dateDebut"));
				S.setDateFin(res_rec_sem.getDate("dateFin"));
				S.setNumSemaineDansAnnee(res_rec_sem.getInt("numSemaineDansAnnee"));
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

	public  ArrayList<Semaine> getList() {
		ArrayList<Semaine> liste = new ArrayList<Semaine>();
		PreparedStatement pst = null;
		try {
			String sql = "SELECT * FROM Semaine";
			pst = this.connect.prepareStatement(sql);
			ResultSet res_rec_sem = pst.executeQuery();
			while (res_rec_sem.next()) {
				Semaine S = new Semaine();
				S.setNumSemaine(res_rec_sem.getInt("numSemaine"));
				S.setCongeScolaire(res_rec_sem.getBoolean("CongeScolaireOuNon"));
				S.setDateDebut(res_rec_sem.getDate("dateDebut"));
				S.setDateFin(res_rec_sem.getDate("dateFin"));
				S.setNumSemaineDansAnnee(res_rec_sem.getInt("numSemaineDansAnnee"));
				liste.add(S);
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

	public ArrayList<Semaine> getListSelonCriteres(Semaine S){
		//AjouterSemainesDansDB();
		// ATTENTION SEMAINE NUMANNEE 44 A 47 SONT A SUPPRIMER
		ArrayList<Semaine> listeRetour = new ArrayList<Semaine>();
		ArrayList<Semaine> listSemaine = getList();
		//  Aujourd'hui
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		int jourDelaisMax = SemaineATD.EstEnPeriodeDeConge(today) ? 7 : 31; 
		Calendar c = Calendar.getInstance(); 
		c.setTime(today); 
		// 1 mois si p�riode scolaire, sinon 7 jours.
		c.add(Calendar.DATE, jourDelaisMax); // Ajout d'un mois ou d'un jour si c'est un p�riode de cong� ou pas.
		java.util.Date maxDateToDisplay = c.getTime();

		if (listSemaine != null)
			for(Semaine s : listSemaine){
				// N'affiche que les semaines ou il n'y a pas de cong�s et qui ne sont pas pass�es.
				if (!s.getCongeScolaire() && s.getDateDebut().after(today) && s.getDateFin().before(maxDateToDisplay)) 
					listeRetour.add(s);
			}
		return listeRetour;
	}

	// Juste faire tourner une fois pour ajouter les semaines dans la DB 
	@Override
	public void AjouterSemainesDansDB(String startD, String endD){
		// Date de d�but d'ajout
		//int jour = 03;
		//int mois = 12;
		//int annee = 2016;
		try{
			// date du jour
			//java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			// test internet 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startDate = formatter.parse(startD); //"2016-10-30"
			java.util.Date endDate = formatter.parse(endD); //"2016-11-25"
			java.util.Date startDateWeek = null;
			java.util.Date endDateWeek = null;
			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);

			for (java.util.Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 7), date = start.getTime()) {
				/* 	�	Du 24/12/2016 => 07/01/2017 (No�l / Nouvel an)
						�	Du 25/02/2017 au 04/03/2017 (Carnaval)
						�	Du 01/04/2017 au 15/04/2017 (P�ques) */
				boolean estFerme = true;
				if((date.after(formatter.parse("2016-12-24")) && date.before(formatter.parse("2017-01-07"))) 
						|| (date.after(formatter.parse("2017-02-25")) && date.before(formatter.parse("2017-03-04")))
						|| date.after(formatter.parse("2017-04-01")) && date.before(formatter.parse("2017-04-15"))){
					// In between
					estFerme = false;
				}

				// Do your job here with `date`.
				startDateWeek = date;
				Calendar c = Calendar.getInstance(); 
				c.setTime(date); 
				c.add(Calendar.DATE, 6);
				endDateWeek = c.getTime();

				// Initialisation des propri�t�s

				// Ajout dans la DB
				//public Semaine(int numSemaine,  boolean congeScolaire, Date dateDebut, Date dateFin, int numSemaineDansAnnee){
				Semaine S = new Semaine();
				//S.setNumSemaine(res_rec_sem.getInt("numSemaine"));
				S.setCongeScolaire(estFerme);
				S.setDateDebut(new java.sql.Date(startDateWeek.getTime()));
				S.setDateFin(new java.sql.Date(endDateWeek.getTime()));
				S.setNumSemaineDansAnnee(c.get(Calendar.WEEK_OF_YEAR));
				create(S);
			}
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	@Override
	public Semaine getId (Semaine obj) {
		PreparedStatement pst = null;
		Semaine S = new Semaine ();
		//int id = -1;
		try {
			String sql = "SELECT * FROM Semaine WHERE dateDebut = ?;";
			pst = this.connect.prepareStatement(sql);
			pst.setDate(1, obj.getDateDebut());
			ResultSet res_Rec_Sem = pst.executeQuery();
			while (res_Rec_Sem.next()) {
				//id = res_Rec_Sem.getInt("numSemaine"); 
				S.setNumSemaine(res_Rec_Sem.getInt("numSemaine"));
				S.setCongeScolaire(res_Rec_Sem.getBoolean("CongeScolaireOuNon"));
				S.setDateDebut(res_Rec_Sem.getDate("dateDebut"));
				S.setDateFin(res_Rec_Sem.getDate("dateFin"));
				S.setNumSemaineDansAnnee(res_Rec_Sem.getInt("numSemaineDansAnnee"));
				}
		}
		catch (SQLException e) { e.printStackTrace(); }
		finally {
			if (pst != null) {
				try { pst.close(); }
				catch (SQLException e) { e.printStackTrace(); }
			}
		}
		return S;
	}

	@Override
	public ArrayList<Semaine> getMyListSelonID(int id1, int id2, int id3, String str1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateAssurance(int numEleve, int numSemaine, String periode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int valeurReduction(int numSem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String calculerPlaceCours(int numCours, int numSemaine, int idMoniteur) {
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
	
	/*public ArrayList<Semaine> getListSemaineSelonDateDuJour(){
		//AjouterSemainesDansDB();
		// ATTENTION SEMAINE NUMANNEE 44 A 47 SONT A SUPPRIMER
		Semaine S = new Semaine();
		ArrayList<Semaine> listeRetour = new ArrayList<Semaine>();
		ArrayList<Semaine> listSemaine = S.getListSemaine();
		//  Aujourd'hui
		java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		int jourDelaisMax = EstEnPeriodeDeConge(today) ? 7 : 31; 
		Calendar c = Calendar.getInstance(); 
		c.setTime(today); 
		// 1 mois si p�riode scolaire, sinon 7 jours.
		c.add(Calendar.DATE, jourDelaisMax); // Ajout d'un mois ou d'un jour si c'est un p�riode de cong� ou pas.
		java.util.Date maxDateToDisplay = c.getTime();

		if (listSemaine != null)
			for(Semaine s : listSemaine){
				// N'affiche que les semaines ou il n'y a pas de cong�s et qui ne sont pas pass�es.
				if (!s.getCongeScolaire() && s.dateDebut.after(today) && s.dateDebut.before(maxDateToDisplay)) 
					listeRetour.add(s);
			}
		return listeRetour;
	}*/


}

