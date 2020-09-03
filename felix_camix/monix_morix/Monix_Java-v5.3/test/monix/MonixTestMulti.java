package monix;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.util.NameComponentChooser;


/**
 * Classe de tests de l'application Monix.
 * 
 * <p>
 * Ces tests s'appuient sur Jemmy 2 pour automatiser la manipulation de l'IHM.
 * Ils illustrent en particulier la manipulation de plusieurs instances du logiciel testé ainsi que 
 * l'enchainement de plusieurs tests sur ces instances.
 * </p>
 * 
 * <p>
 * Ces tests lancent deux instances de Monix sans paramètre. Cela nécessite qu'une instance de serveur Morix soit lancée.
 * Ces tests peuvent aussi être exécutés en lançant deux instances de Monix avec le paramètre "-b" (utilisation d'un bouchon de stock)
 * dans ce cas il n'est pas nécessaire de lancer une instance de Morix (mais les vues stock des instances de Monix ne seront pas 
 * cohérentes entre elle dans ce cas-là).</p>
 *
 * @version 5.3
 * @author Matthias Brun
 *
 */
public class MonixTestMulti
{
	// Remarque : les attributs suivants sont statiques pour pouvoir être instanciés dans une méthode statique "BeforeClass".
	
	/**
	 * Nombre d'instances d'application Monix impliquées dans le test.
	 */
	private static final int NBINSTANCES = 2;
	
	/**
	 * La référence de la classe de l'application à tester.
	 */
	private static ClassReference application;

	/**
	 * Le nom de l'application à tester. 
	 */
	private static final String APPLICATION = "monix.Monix";
	
	/**
	 * Les paramètres de lancement de l'application.
	 */
	private static final String[] PARAMETRES = {""}; // "-b" en mode bouchonné, "" en mode collaboration avec Morix;
	
	//
	// Manipulation des widgets des vues caisse de Monix.
	//
	
	/**
	 * Opérateurs de JFrame utiles pour les tests 
	 * (pour la manipulation des fenêtres (ou vues) de la caisse des différentes instances de Monix).
	 */
	private static JFrameOperator[] fenetreCaisse = new JFrameOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JButton utiles pour les tests
	 * (pour la manipulation des boutons "annuler" de la vue de la caisse des différentes instances de Monix).
	 */
	private static JButtonOperator[] boutonAnnuler = new JButtonOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JButton utiles pour les tests
	 * (pour la manipulation des boutons "ajouter" de la vue de la caisse des différentes instances de Monix).
	 */
	private static JButtonOperator[] boutonAjouter = new JButtonOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JButton utiles pour les tests
	 * (pour la manipulation des boutons "fin de vente" de la vue de la caisse des différentes instances de Monix).
	 */
	private static JButtonOperator[] boutonFinVente = new JButtonOperator[NBINSTANCES];

	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "id" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteId = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "quantité" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteQuantite = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "libellé" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteLibelle = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "prix" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] textePrix = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "info" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteInfo = new JTextFieldOperator[NBINSTANCES];
	
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "total" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteTotal = new JTextFieldOperator[NBINSTANCES];

	//
	// Manipulation des widgets des vue client de Monix.
	//
	
	/**
	 * Opérateurs de JFrame utiles pour les tests 
	 * (pour la manipulation des fenêtres (ou vues) client des différentes instances de Monix).
	 */
	private static JFrameOperator[] fenetreClient = new JFrameOperator[NBINSTANCES];
	
	/**
	 * Les champs texte de la vue.
	 */
	/**
	 * Opérateurs de JTextField utiles pour les tests
	 * (pour la manipulation des champs texte "prix total" des différentes instances de Monix).
	 */
	private static JTextFieldOperator[] texteTotalClient = new JTextFieldOperator[NBINSTANCES];

	/**
	 * Opérateur de TextPane utiles pour les tests
	 * (pour la manipulation des panneaux textes des vues des tickets des différentes instances de Monix).
	 */
	private static JTextPaneOperator[] texteTicket = new JTextPaneOperator[NBINSTANCES];

	//
	// Manipulation des widgets des vue stock de Monix.
	//
	
	/**
	 * Opérateurs de JFrame utiles pour les tests 
	 * (pour la manipulation des fenêtres (ou vues) stock des différentes instances de Monix).
	 */
	private static JFrameOperator[] fenetreStock = new JFrameOperator[NBINSTANCES];
	
	
	/**
	 * Configure Jemmy pour les tests, lancements des instances de l'application Monix 
	 * et lancement d'une instance de Morix à tester.
	 *
	 * <p>Code exécuté avant les tests.</p>
	 *
	 * @throws Exception toute exception.
	 */
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		// Fixe les timeouts de Jemmy (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
		// ici : 3s pour l'affichage d'une frame ou d'un composant (widget), 
		//       ou une attente de changement d'état (waitText par exemple).
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);
		
		// Démarrage des instances de Monix nécessaires aux tests.
		try {
			MonixTestMulti.application = new ClassReference(MonixTestMulti.APPLICATION);
			
			lanceToutesInstancesMonix();
		}
		catch (ClassNotFoundException e) {
			Assert.fail("Problème d'accès à la classe invoquée : " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Lancement de toutes les instances de Monix nécessaires aux test.
	 * 
	 * @throws Exception toute exception.
	 */
	private static void lanceToutesInstancesMonix() throws Exception
	{
		for (int index = 0; index < NBINSTANCES; index++) {
			
			// Lance une instance de Monix. 
			lanceInstanceMonix(index);
			
			// 10 secondes d'observation par suspension du thread (objectif pédagogique)
			// (pour prendre le temps de déplacer les fenêtres à l'écran).
			final Long timeout = Long.valueOf(10000); 
			Thread.sleep(timeout);
		}
	}
	
	/**
	 * Lancement d'une instance de Monix.
	 * 
	 * @param index l'index de l'instance de Monix concernée.
	 * 
	 * @throws Exception toute exception.
	 */
	private static void lanceInstanceMonix(int index) throws Exception
	{
		try {
			// Lancement d'une instance de l'application.
			MonixTestMulti.application.startApplication(MonixTestMulti.PARAMETRES);
		}
		catch (InvocationTargetException e) {
			Assert.fail("Problème d'invocation de l'application : " + e.getMessage());
			throw e;
		} 
		catch (NoSuchMethodException e) {
			Assert.fail("Problème d'accès à la méthode invoquée : " + e.getMessage());
			throw e;
		}
		
		// Accès à l'interface de l'instance de Monix.
		accesInterface(index);
	}
	
	/**
	 * Accès à l'interface d'une instance de Monix.
	 * 
	 * <p>
	 * Cette méthode initialise les attributs de la classe de test pour ce
	 * qui concerne les accès aux fenêtres et aux widgets de l'interface de Monix.</p>
	 * <p>
	 * Elle vérifie au passage la bonne construction de l'interface, 
	 * avec titres adéquats et widgets attendus.</p>
	 * 
	 * @param index l'index de l'instance de Monix dont on doit récupérer l'interface.
	 */
	private static void accesInterface(int index) 
	{
		// Accès à l'interface de la vue caisse.
		accesVueCaisse(index);
		
		// Accès à l'interface de la vue client.
		accesVueClient(index);
		
		// Accès à l'interface de la vue stock.
		accesVueStock(index);
	}
	
	/**
	 * Accès à la vue caisse d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue caisse, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 * 
	 * @param index l'index de l'instance de Monix à laquelle on doit accéder à la vue caisse.
	 */
	private static void accesVueCaisse(int index)
	{
		// Accès à la fenêtre de la vue de la caisse (par son titre et son index).
		try {
			fenetreCaisse[index] = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_TITRE_FENETRE"), index);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("La fenêtre de la vue caisse (instance " + index + ") n'est pas accessible : " + e.getMessage());
		}
	
		// Accès aux composants (widgets) de la vue caisse
		try {
			// Accès au champ de saisie d'un identifiant produit (par son nom).
			texteId[index] = new JTextFieldOperator(fenetreCaisse[index],  
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_SAISIE_ID_PRODUIT")));
			
			// Accès au champ de saisie de la quantité de produit (par son nom).
			texteQuantite[index] = new JTextFieldOperator(fenetreCaisse[index], 
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_SAISIE_QUANTITE_PRODUIT")));
			
			// Accès au champ de libellé d'un produit (par son nom).
			texteLibelle[index] = new JTextFieldOperator(fenetreCaisse[index],   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_LIBELLE_PRODUIT")));
			
			// Accès au champ de prix d'un produit (par son nom).
			textePrix[index] = new JTextFieldOperator(fenetreCaisse[index],   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_PRIX_PRODUIT")));
			
			// Accès au champ d'information d'un achat (par son nom).
			texteInfo[index] = new JTextFieldOperator(fenetreCaisse[index],   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_INFO_PRODUIT")));
			
			// Accès au champ de prix total de la vente (par son nom).
			texteTotal[index] = new JTextFieldOperator(fenetreCaisse[index],   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_PRIX_TOTAL")));
			
			// Accès au bouton d'ajout d'un produit à la vente (par son nom).
			boutonAjouter[index] = new JButtonOperator(fenetreCaisse[index],   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_AJOUTER")));
			
			// Accès au bouton d'annulation d'un produit à la vente (par son nom).
			boutonAnnuler[index] = new JButtonOperator(fenetreCaisse[index],   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_ANNULER")));
			
			// Accès au bouton de clôture de la vente (par son nom).
			boutonFinVente[index] = new JButtonOperator(fenetreCaisse[index], 
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_FIN_VENTE")));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Problème d'accès à un composant de la vue caisse (instance " + index + ") : " + e.getMessage());
		}
	}

	/**
	 * Accès à la vue client d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue client, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 * 
	 * @param index l'index de l'instance de Monix à laquelle on doit accéder à la vue client.
	 */
	private static void accesVueClient(int index)
	{
		// Accès à la fenêtre de la vue client (par son titre et son index).
		try {
			fenetreClient[index] = new JFrameOperator(Monix.MESSAGES.getString("CLIENT_TITRE_FENETRE"), index);
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("La fenêtre de la vue client (instance " + index + ") n'est pas accessible : " + e.getMessage());
		}
		
		// Accès aux composants (widgets) de la vue client.
		try {
			// Accès au champ d'information du prix total de la vente (par son nom).
			texteTotalClient[index] = new JTextFieldOperator(fenetreClient[index], 
					new NameComponentChooser(Monix.IHM.getString("CLIENT_NOM_PRIX")));
	
			// Accès au panneau texte de la vue (ticket) (par son nom).
			texteTicket[index] = new JTextPaneOperator(fenetreClient[index], 
					new NameComponentChooser(Monix.IHM.getString("CLIENT_NOM_TICKET")));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Problème d'accès à un composant de la vue client (instance " + index + ") : " + e.getMessage());
		}
	}
	
	/**
	 * Accès à la vue stock d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre de la vue stock, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 * 
	 * @param index l'index de l'instance de Monix à laquelle on doit accéder à la vue stock.
	 */
	private static void accesVueStock(int index)
	{
		// Accès à la fenêtre de la vue stock (par son titre et son index).
		try {
			fenetreStock[index] = new JFrameOperator(Monix.MESSAGES.getString("STOCK_TITRE_FENETRE"), index);
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("La fenêtre de la vue stock (instance " + index + ") n'est pas accessible : " + e.getMessage());
		}
	}

	/**
	 * Implantation pédagogique de temporisation en fin de test pour laisser un temps d'observation.
	 *
	 * <p>Code exécuté après chaque test.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception 
	{
		// 5 secondes d'observation par suspension du thread (objectif pédagogique).
		final Long timeout = Long.valueOf(5000); 
		Thread.sleep(timeout);
	}
	
	/**
	 * Fermeture des fenêtres (pour enchaînement d'autres tests si besoin).
	 *
	 * <p>Code exécuté après les tests.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@AfterClass
	public static void tearDownClass() throws Exception
	{
		for (int index = 0; index < NBINSTANCES; index++) {
			
			if (fenetreCaisse[index] != null) {
				fenetreCaisse[index].dispose();
			}
			if (fenetreClient[index] != null) {
				fenetreClient[index].dispose();
			}
			if (fenetreStock[index] != null) {
				fenetreStock[index].dispose();
			}
		}
	}
	
	/**
	 * Test d'achats de produit via la première instance de Monix.
	 * 
	 * Remarque : pour simplifier la lisibilité du code pour l'apprentisssage de Jemmy2 (objectif pédagogique)
	 *            aucune assertion (assert) n'est présente dans le code de test. Dans une version plus réaliste
	 *            des assertions seraient bien sûr présentes. 
	 *            
	 * @throws InterruptedException  pour la temporisation par suspension du thread.
	 */
	@Test
	public void testAchatProduits() throws InterruptedException
	{
		// 1,5 seconde d'observation par suspension du thread 
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500); 

		// Effacement du champ de saisie de l'ID du produit.
		texteId[0].clickMouse();
		texteId[0].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
			
		// Saisie de l'ID du premier produit.
		texteId[0].typeText("11A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
				
		// Effacement du champ de saisie de la quantité du premier produit.
		texteQuantite[0].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Saisie de la quantité de produit 
		// Remarque : enterText est utilisé pour saisir et formatter la valeur saisie (JFormattedTextField).
		texteQuantite[0].enterText("1");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Ajout du produit à la vente.
		boutonAjouter[0].doClick();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Effacement du champ de saisie de l'ID du produit.
		texteId[0].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
				
		// Saisie de l'ID du second produit.
		texteId[0].typeText("22A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Effacement du champ de saisie de la quantité du second produit.
		texteQuantite[0].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Saisie de la quantité du second produit 
		// Remarque : enterText est utilisé pour saisir et formatter la valeur saisie (JFormattedTextField).
		texteQuantite[0].enterText("2");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Ajout du second produit à la vente.
		boutonAjouter[0].doClick();
	
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Fin de la vente.
		boutonFinVente[0].doClick();
	}
	

	/**
	 * Test d'annulation d'achats de produits via la deuxième instance de Monix.
	 * 
	 * Remarque : pour simplifier la lisibilité du code pour l'apprentisssage de Jemmy2 (objectif pédagogique)
	 *            aucune assertion (assert) n'est présente dans le code de test. Dans une version plus réaliste
	 *            des assertions seraient bien sûr présentes. 
	 *            
	 * @throws InterruptedException  pour la temporisation par suspension du thread.
	 */
	@Test
	public void testAnnulationProduits() throws InterruptedException
	{
		// 1,5 seconde d'observation par suspension du thread 
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500); 

		// Effacement du champ de saisie de l'ID du produit.
		texteId[1].clickMouse();
		texteId[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Saisie de l'ID du premier produit.
		texteId[1].typeText("11A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Effacement du champ de saisie de la quantité de produit.
		texteQuantite[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Saisie de la quantité du premier produit 
		// Remarque : enterText est utilisé pour saisir et formatter la valeur saisie (JFormattedTextField).
		texteQuantite[1].enterText("1");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Annulation du produit de la vente.
		boutonAnnuler[1].doClick();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Effacement du champ de saisie de l'ID du second produit.
		texteId[1].clickMouse();
		texteId[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Saisie de l'ID du second produit.
		texteId[1].typeText("22A");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Effacement du champ de saisie de la quantité du second produit.
		texteQuantite[1].clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Saisie de la quantité du second produit 
		// Remarque : enterText est utilisé pour saisir et formatter la valeur saisie (JFormattedTextField).
		texteQuantite[1].enterText("2");
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Annulation du second produit de la vente.
		boutonAnnuler[1].doClick();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Fin de la vente.
		boutonFinVente[1].doClick();
	}
}

