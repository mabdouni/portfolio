package monix;

import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
 * Ces tests s'appuient sur Jemmy 2 pour automatiser la manipulation de l'IHM.</p>
 * 
 * <p>
 * Ces tests lancent une instance de Monix avec le paramètre "-b" (utilisation d'un bouchon de stock).
 * Ces tests peuvent être exécutés avec un stock Morix si le paramètre "-b" n'est pas passé au lancement
 * des instances de Monix. Dans ce cas, cela nécessite qu'une instance de serveur Morix soit lancée.</p>
 *
 * @version 5.3
 * @author Matthias Brun
 *
 */
public class MonixTestSimple
{
	//
	// Manipulation des widgets des vues caisse de Monix.
	//
	
	/**
	 * Opérateur de JFrame utile pour les tests 
	 * (pour la manipulation de la fenêtre (ou vue) de la caisse de l'instance de Monix).
	 */
	private JFrameOperator fenetreCaisse;
	
	/**
	 * Opérateur de JButton utile pour les tests
	 * (pour la manipulation du bouton "annuler" de la vue de la caisse de l'instance de Monix).
	 */
	@SuppressWarnings("unused")
	private JButtonOperator boutonAnnuler;
	
	/**
	 * Opérateurs de JButton utile pour les tests
	 * (pour la manipulation du bouton "ajouter" de la vue de la caisse de l'instance de Monix).
	 */
	private JButtonOperator boutonAjouter;
	
	/**
	 * Opérateurs de JButton utile pour les tests
	 * (pour la manipulation du bouton "fin de vente" de la vue de la caisse de l'instance de Monix).
	 */
	private JButtonOperator boutonFinVente;

	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "id" de l'instance de Monix).
	 */
	private JTextFieldOperator texteId;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "quantité" de l'instance de Monix).
	 */
	private JTextFieldOperator texteQuantite;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "libellé" de l'instance de Monix).
	 */
	private JTextFieldOperator texteLibelle;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "prix" de l'instance de Monix).
	 */
	private JTextFieldOperator textePrix;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "info" de l'instance de Monix).
	 */
	private JTextFieldOperator texteInfo;
	
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "total" de l'instance de Monix).
	 */
	private JTextFieldOperator texteTotal;

	//
	// Manipulation des widgets des vue client de Monix.
	//
	
	/**
	 * Opérateur de JFrame utile pour les tests 
	 * (pour la manipulation de la fenêtre (ou vue) client de l'instance de Monix).
	 */
	private JFrameOperator fenetreClient;
	
	/**
	 * Les champs texte de la vue.
	 */
	/**
	 * Opérateur de JTextField utile pour les tests
	 * (pour la manipulation du champ texte "prix total" de l'instance de Monix).
	 */
	private JTextFieldOperator texteTotalClient;

	/**
	 * Opérateur de TextPane utile pour les tests
	 * (pour la manipulation du panneau texte de la vue du ticket de l'instance de Monix).
	 */
	private JTextPaneOperator texteTicket;

	//
	// Manipulation des widgets des vue stock de Monix.
	//
	
	/**
	 * Opérateur de JFrame utile pour les tests 
	 * (pour la manipulation de la fenêtre (ou vue) stock de l'instance de Monix).
	 */
	private JFrameOperator fenetreStock;
	
	
	/**
	 * Configure Jemmy pour les tests et lancements de l'application à tester.
	 *
	 * <p>Code exécuté avant chaque test.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@Before
	public void setUp() throws Exception
	{
		// Fixe les timeouts de Jemmy (http://wiki.netbeans.org/Jemmy_Operators_Environment#Timeouts),
		// ici : 3s pour l'affichage d'une frame ou d'un composant (widget), 
		//       ou une attente de changement d'état (waitText par exemple).
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);
		
		// Démarrage de l'instance de Monix nécessaire aux tests.
		try {
			final String[] parametres = {"-b"}; // "-b" pour lancer Monix en mode bouchonné (p/r Morix).
			
			new ClassReference("monix.Monix").startApplication(parametres);
			
			// 10 secondes d'observation par suspension du thread
			// (objectif pédagogique, pour prendre le temps de déplacer les fenêtres à l'écran).
			final Long timeoutObs = Long.valueOf(10000); 
			Thread.sleep(timeoutObs);
		}
		catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException e) {
			Assert.fail("Probl�me de lancement de Monix : " + e.getMessage());
			throw e;
		}
		
		// Accès à l'interface de Monix.
		this.accesInterface();
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
	 */
	private void accesInterface() 
	{
		// Accès à l'interface de la vue caisse.
		this.accesVueCaisse();
		
		// Accès à l'interface de la vue client.
		this.accesVueClient();
		
		// Accès à l'interface de la vue stock.
		this.accesVueStock();
	}
	
	/**
	 * Accès à la vue caisse d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue caisse, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 *
	 */
	private void accesVueCaisse()
	{
		// Accès à la fenêtre de la vue de la caisse (par son titre).
		try {
			this.fenetreCaisse = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_TITRE_FENETRE"));
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("La fen�tre de la vue caisse n'est pas accessible : " + e.getMessage());
		}
	
		// Accès aux composants (widgets) de la vue caisse
		try {
			// Accès au champ de saisie d'un identifiant produit (par son nom).
			this.texteId = new JTextFieldOperator(this.fenetreCaisse,  
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_SAISIE_ID_PRODUIT")));
			
			// Accès au champ de saisie de la quantité de produit (par son nom).
			this.texteQuantite = new JTextFieldOperator(this.fenetreCaisse, 
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_SAISIE_QUANTITE_PRODUIT")));
			
			// Accès au champ de libellé d'un produit (par son nom).
			this.texteLibelle = new JTextFieldOperator(this.fenetreCaisse,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_LIBELLE_PRODUIT")));
			
			// Accès au champ de prix d'un produit (par son nom).
			this.textePrix = new JTextFieldOperator(this.fenetreCaisse,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_PRIX_PRODUIT")));
			
			// Accès au champ d'information d'un achat (par son nom).
			this.texteInfo = new JTextFieldOperator(this.fenetreCaisse,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_INFO_PRODUIT")));
			
			// Accès au champ de prix total de la vente (par son nom).
			this.texteTotal = new JTextFieldOperator(this.fenetreCaisse,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_PRIX_TOTAL")));
			
			// Accès au bouton d'ajout d'un produit à la vente (par son nom).
			this.boutonAjouter = new JButtonOperator(this.fenetreCaisse,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_AJOUTER")));
			
			// Accès au bouton d'annulation d'un produit à la vente (par son nom).
			this.boutonAnnuler = new JButtonOperator(this.fenetreCaisse,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_ANNULER")));
			
			// Accès au bouton de clôture de la vente (par son nom).
			this.boutonFinVente = new JButtonOperator(this.fenetreCaisse, 
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_FIN_VENTE")));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Probl�me d'acc�s � un composant de la vue caisse : " + e.getMessage());
		}
	}

	/**
	 * Accès à la vue client d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue client, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 *
	 */
	private void accesVueClient()
	{
		// Accès à la fenêtre de la vue client (par son titre).
		try {
			this.fenetreClient = new JFrameOperator(Monix.MESSAGES.getString("CLIENT_TITRE_FENETRE"));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("La fen�tre de la vue client n'est pas accessible : " + e.getMessage());
		}
		
		// Accès aux composants (widgets) de la vue client.
		try {
			// Accès au champ d'information du prix total de la vente (par son nom).
			this.texteTotalClient = new JTextFieldOperator(this.fenetreClient, 
					new NameComponentChooser(Monix.IHM.getString("CLIENT_NOM_PRIX")));
	
			// Accès au panneau texte de la vue (ticket) (par son nom).
			this.texteTicket = new JTextPaneOperator(this.fenetreClient, 
					new NameComponentChooser(Monix.IHM.getString("CLIENT_NOM_TICKET")));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Probl�me d'acc�s � un composant de la vue client : " + e.getMessage());
		}
	}
	
	/**
	 * Accès à la vue stock d'une instance de Monix.
	 * 
	 * <p>Cette méthode concerne la récupération de la fenêtre de la vue stock, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 *
	 */
	private void accesVueStock()
	{
		// Accès à la fenêtre de la vue stock (par son titre).
		try {
			this.fenetreStock = new JFrameOperator(Monix.MESSAGES.getString("STOCK_TITRE_FENETRE"));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("La fen�tre de la vue stock n'est pas accessible : " + e.getMessage());
		}
	}
	
	/**
	 * Implantation pédagogique de temporisation en fin de test pour laisser un temps d'observation,
	 * et fermeture des fenêtres affichées (pour enchaînement des tests si besoin).
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
		
		// Fermeture des fenêtres (pour enchaînement des tests si besoin).
		if (this.fenetreCaisse != null) {
			this.fenetreCaisse.dispose();
		}
		if (this.fenetreClient != null) {
			this.fenetreClient.dispose();
		}
		if (this.fenetreStock != null) {
			this.fenetreStock.dispose();
		}
	}
	
	/**
	 * Test l'achat d'un produit.
	 * 
	 * Remarque : pour simplifier la lisibilité du code pour l'apprentisssage de Jemmy2 (objectif pédagogique)
	 *            aucune assertion (assert) n'est présente dans le code de test. Dans une version plus réaliste
	 *            des assertions seraient bien sûr présentes. 
	 * 
	 * @throws InterruptedException  pour la temporisation par suspension du thread.
	 */
	@Test
	public void testAchatProduit() throws InterruptedException
	{
		// 1,5 seconde d'observation par suspension du thread 
		// entre chaque action (objectif pédagogique).
		final Long timeout = Long.valueOf(1500); 

		// Effacement du champ de saisie de l'ID du produit.
		this.texteId.clickMouse();
		this.texteId.clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
				
		// Saisie de l'ID du produit.
		this.texteId.typeText("11A");
		
		// Forcer la perte de focus du champ de saisie de l'identifiant d'un produit
		// en donnant le focus au champ de saisie de la quantité de produit.
		this.texteQuantite.clickMouse();
		
		// Validation des valeurs des champs libellé et prix du produit, normalement mis
		// à jour après la perte de focus du champ de saisie de l'identifiant du produit.
		final String libelleAttendu = "produit un A";
		final String prixAttendu = String.format("%1$.2f €", 10.0);
		
		try {
			// Attente du message du libellé.
			this.texteLibelle.waitText(libelleAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Libell� du produit (11A) invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.textePrix.waitText(prixAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Prix du produit (11A) invalide.");
		}
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
			
		// Effacement du champ de saisie de la quantité de produit.
		this.texteQuantite.clearText();
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Saisie de la quantité de produit 
		// Remarque : enterText est utilisé pour saisir et formatter la valeur saisie (JFormattedTextField).
		this.texteQuantite.enterText("2");	

		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Ajout du produit à la vente.
		this.boutonAjouter.doClick();
		
		// Validation des valeurs des champs libellé prix du produit ainsi que du ticket.
		final String infoAttendu = String.format("+ produit un A   x   2  x %1$.2f €", 10.0);
		final String totalAttendu = String.format("%1$.2f €", 20.0);
		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") 
				+ System.getProperty("line.separator")
				+ String.format("produit un A       x 2      x    %1$.2f €", 10.0);
		final String totalClientAttendu = String.format("%1$.2f €", 20.0);
		
		try {
			// Attente du message d'information de l'achat.
			this.texteInfo.waitText(infoAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Information pour l'achat du produit (11A) invalide.");
		}
		
		try {
			// Attente du message du prix de l'achat.
			this.texteTotal.waitText(totalAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Prix total pour l'achat du produit (11A) invalide.");
		}
				
		try {
			// Attente du ticket.
			this.texteTicket.waitText(ticketAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Ticket pour l'achat du produit (11A) invalide.");
		}
		
		try {
			// Attente du message du prix total de la vente.
			this.texteTotalClient.waitText(totalClientAttendu);
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("Prix total pour la vente du produit (11A) invalide.");
		}
		
		// Observation par suspension du thread (objectif pédagogique).
		Thread.sleep(timeout);
		
		// Fin de la vente.
		this.boutonFinVente.doClick();
	}
}

