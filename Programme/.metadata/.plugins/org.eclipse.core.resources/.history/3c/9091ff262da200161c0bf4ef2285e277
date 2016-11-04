package POJO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Utilitaire {
  
	// DATES
    public static Date stringToDate(String dt) throws Exception{
    	 SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
    	 try { return dateformat.parse(dt); }
    	 catch (Exception e) { throw new Exception(); }
    }
    
    public static Date stringToHeure(String dt) throws Exception{
   	 SimpleDateFormat dateformat = new SimpleDateFormat("hh:mm");
   	 try { return dateformat.parse(dt); }
   	 catch (Exception e) { throw new Exception(); }
   }
    
    // MENUS
    // PREMIER MENU
    public static int afficherPremierMenu(){
    	int choix = -1 ;
    	String s;
    	Scanner sc = new Scanner(System.in);
    	do	{
    		Utilitaire.clr();
	    	System.out.println("MENU DE BASE");
	    	System.out.println("~~~~");
	    	System.out.println("Voulez vous : ");
	    	System.out.println("1. Se connecter");
	    	System.out.println("2. S'inscrire");
	    	System.out.println("3. Quitter");
	    	System.out.println("Votre choix : ");
	    	
    	    s = sc.next().trim(); // trim so that numbers with whitespace are valid
    	    if (s.matches("\\d+")) choix = Integer.parseInt(s); // if the string contains only numbers 0-9   
    	}while (choix < 1 || choix > 3 || !sc.hasNextLine());
    	return choix;
    }
    	// CONNEXION
	    public static String [] affichermenuConnexion(){
	    	String [] tabUser = new String[2];
	    	Scanner sc = new Scanner(System.in);
			Utilitaire.clr();
	    	System.out.println("MENU DE CONNEXION"); 
	    	System.out.println("~~~~");
	    	System.out.println("Votre nom d'utilisateur >");tabUser[0] = sc.next();
	    	System.out.println("Votre mot de passe > ");tabUser[1] = sc.next();
	    	return tabUser;
	    }
	    
	    // INSCRIPTION
	    public static int afficherTypeInscription(){
	    	int choix = -1 ;
	    	String s;
	    	Scanner sc = new Scanner(System.in);
	    	do	{
	    		Utilitaire.clr();
		    	System.out.println("Quel type de personne êtes-vous ? ");
		    	System.out.println("1. Un moniteur");
		    	System.out.println("2. Un client");
		    	System.out.println("3. Quitter");
		    	System.out.println("Votre choix : ");
		    	choix = sc.nextInt();
	    	    s = sc.next().trim(); // trim so that numbers with whitespace are valid
	    	    if (s.matches("\\d+")) choix = Integer.parseInt(s); // if the string contains only numbers 0-9   
	    	}while (choix < 1 || choix > 3 || !sc.hasNextLine());
	    	return choix;
	    }

	    public static String [] affichermenuInscription(){
	    	String [] tabUser = new String[3];
	    	ArrayList<Utilisateur> l = new ArrayList<Utilisateur>();
	    	Scanner sc = new Scanner(System.in);
			Utilitaire.clr();
	    	System.out.println("MENU D'INSCRIPTION"); 
	    	System.out.println("~~~~");
	    	System.out.println("Votre nom d'utilisateur > ");tabUser[0] = sc.next();
	    	System.out.println("Votre mot de passe > ");tabUser[1] = sc.next();
	    	System.out.println("[1] Moniteur ou [2] Client > "); tabUser[2] = sc.next();
	    	return tabUser;
	    }
	    
    // MONITEUR
    public static int affichermenuPrincipalMoniteur(){
    	int choix = -1 ;
    	String s;
    	Scanner sc = new Scanner(System.in);
    	do	{
    		Utilitaire.clr();
	    	System.out.println("MENU PRINCIPAL - Moniteur");
	    	System.out.println("~~~~");
	    	System.out.println("Voulez vous : ");
	    	System.out.println("1. Voir les cours prévus");
	    	System.out.println("2. Gérer les cours");
	    	System.out.println("3. Se déconnecter");
	    	System.out.println("Votre choix : ");
    	    s = sc.next().trim(); // trim so that numbers with whitespace are valid
    	    if (s.matches("\\d+")) choix = Integer.parseInt(s); // if the string contains only numbers 0-9   
    	}while (choix < 1 || choix > 3 || !sc.hasNextLine());
    	return choix;
    }
    
    // CLIENT
    public static int affichermenuPrincipalClient(){
    	int choix = -1 ;
    	String s;
    	Scanner sc = new Scanner(System.in);
    	do	{
    		Utilitaire.clr();
	    	System.out.println("MENU PRINCIPAL - Client");
	    	System.out.println("~~~~");
	    	System.out.println("Voulez vous : ");
	    	System.out.println("1. Effectuer une inscription");
	    	System.out.println("2. Voir les cours prévus");
	    	System.out.println("3. Gérer les cours");
	    	System.out.println("4. Payer les cours");
	    	System.out.println("5. Se déconnecter");
	    	System.out.println("Votre choix : ");
    	    s = sc.next().trim(); // trim so that numbers with whitespace are valid
    	    if (s.matches("\\d+")) choix = Integer.parseInt(s); // if the string contains only numbers 0-9   
    	}while (choix < 1 || choix > 5 || !sc.hasNextLine());
    	return choix;
    }
    
    public static int affichermenuInscriptionCours(){
    	int choix = -1 ;
    	String s;
    	Scanner sc = new Scanner(System.in);
    	do	{
    		Utilitaire.clr();
	    	System.out.println("INSCRIPTION COURS");
	    	System.out.println("~~~~");
	    	System.out.println("Voulez vous : ");
	    	System.out.println("1. Vous inscrire vous-même");
	    	System.out.println("2. Inscrire quelqu'un d'autre");
	    	System.out.println("Votre choix : ");
    	    s = sc.next().trim(); // trim so that numbers with whitespace are valid
    	    if (s.matches("\\d+")) choix = Integer.parseInt(s); // if the string contains only numbers 0-9   
    	}while (choix < 1 || choix > 2 || !sc.hasNextLine());
    	return choix;
    }
    
    // DECONNEXION
	public static int[] deconnexion(){
		int tab_usr [] = new int [2];
		tab_usr[0] = -1; // numUser
		tab_usr[1] = -1; // typeUser
		return tab_usr;
	}
    
    // AFFICHAGES
    public static void afficherLogo() throws IOException, InterruptedException {
		System.out.println("    _,               A                     /\\     ");    
		System.out.println("  O/                / \\                   /  \\         _"); 
		System.out.println("  /\\ __  _         /   `.   A            /  ` \\        |\\     "); 
		System.out.println("  \\ `. \\/ )       /      `./ \\          /   \\  \\      /  \\    ,'"); 
		System.out.println("   `  \\/ /       /        /   \\         / ,    \\     /    \\  /"); 
		System.out.println("      / /       /       ,',--. \\  /\\   /   /  \\ \\   /      \\/"); 
		System.out.println("`.   (_/       /      .' / ___) \\/  \\ /  /  `    \\.'        `."); 
		System.out.println("  `A          /    _,'  / / @/  /    /,       \\   \\           `."); 
		System.out.println("    `AA      /   ,'___  \\/  .|       /   ,'||`. \\ \\ "); 
		System.out.println(",-'  `.    /   ,-'   `-'`._/       /_.-'  ||  `-._\\"); 
		System.out.println("`._/ ,-'   _    `./              _.||._"); 
		System.out.println("           `/  _.-\"/    / \\         _,`-      `-."); 
		System.out.println("          /  /`-./    /    \\    _.-'             `-."); 
		System.out.println("         /  /   /    /   A  \\.-'                    `-."); 
		System.out.println("         /`./   /    /   / \\  \\     _                   :"); 
		System.out.println("        (()|    (      ,'   \\  `--+/ 3                 '`."); 
		System.out.println("         \\||    |   |  \\     \\____|__3                    `._.-."); 
		System.out.println("             _,-|   |   \\                                  /"); 
		System.out.println("         _,-'   |   |\\   \\                              "); 
		System.out.println("      ,-'       )   ) \\   )  _.-\"-.             "); 
		System.out.println("   ,-'          /  /  /`./.-\"     )    "); 
		System.out.println(" ,'            /  /  /  /        /   "); 
		System.out.println("'             /`./.-/  /       ,' "); 
		System.out.println("             /  /   \\  \\    ,-' "); 
		System.out.println("         _.-/  /     \\_/_.-\""); 
		System.out.println("     _.-\"   \\  \\    _.-\"        "); 
		System.out.println("__.-\"        \\_/_.-\""); 
		System.out.println("\\           _.-\""); 
		System.out.println(" \\      _.-\""); 
		System.out.println("  \\__.-\"  "); 
		Thread.sleep(3000);
	}
    
    public static void loadingScreen() throws InterruptedException{
    	String loadingItem = "";
    	for(int i = 0; i < 50; i++){
    		Utilitaire.clr();
    		System.out.print("Chargement : ");
    		loadingItem += "#";
    		System.out.println(loadingItem);
    		Thread.sleep(100);
    	}
    }
    
    public static void clr () { for (int i = 0; i < 50; ++i) System.out.println(); }
}
