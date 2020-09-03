package monix.vue;

import java.util.LinkedHashMap;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import monix.Monix;
import monix.controleur.ControleurVente;
import monix.modele.stock.Produit;
import monix.modele.stock.StockBouchon;
import monix.modele.vente.Achat;
import monix.modele.vente.AchatImpossibleException;
import monix.modele.vente.Vente;
import monix.modele.vente.VenteChangeEvenement;

/**
 * Classe de tests unitaires JUnit 4 de la classe VueClient.
 *
 * <p>Utilisation d'un bouchon de stock de type StockBouchon.</p>
 * <p>Utilisation d'un mock (simulacre) de contrôleur (construit avec EasyMock) pour le MVC.</p>
 * <p>Utilisation de Jemmy 2 pour la manipulation de l'IHM.</p>
 *
 * @version 5.3
 * @author Matthias Brun
 *
 * @see monix.vue.VueClient
 * @see monix.modele.stock.StockBouchon
 * @see org.easymock.EasyMock
 * @see org.netbeans.jemmy.JemmyProperties
 *
 */
public class VueClientTest
{
	/**
	 * Caractère de fin de ligne utilisé dans le ticket de la vue client. 
	 */
	private static final String FINDELIGNE = System.getProperty("line.separator");
	
	/**
	 * Vue Client nécessaire aux tests.
	 */
	private VueClient vueClient;
	
	/**
	 * Mock d'un contrôleur nécessaire aux tests.
	 */
	private ControleurVente controleurMock;
	
	/**
	 * Bouchon de stock nécessaire aux tests.
	 */
	private StockBouchon stockBouchon;

	/**
	 * Opérateurs de JFrame pour accéder à la fenêtre de la vue.
	 */
	private JFrameOperator fenetre;
	
	/**
	 * Opérateurs de JTextField pour accéder aux champs texte de la vue.
	 */
	private JTextFieldOperator texteTotal;

	/**
	 * Opérateurs de JTextPan pour accéder au panneau texte de la vue (ticket).
	 */
	private JTextPaneOperator texteTicket;

	/**
	 * Fixe les propriétés de Jemmy pour les tests.
	 * Crée un mock de contrôleur et un mock de stock.
	 * Crée et affiche la vue nécessaire aux tests.
	 *
	 * <p>Code exécuté avant chaque test.</p>
	 *
	 * @throws Exception toute exception.
	 * 
	 * @see org.netbeans.jemmy.JemmyProperties
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
		
		// Création d'un mock de contrôleur.
		this.controleurMock = EasyMock.createMock(ControleurVente.class);
		Assert.assertNotNull(this.controleurMock);
		
		// Création d'un bouchon de stock.
		this.stockBouchon = new StockBouchon();
		Assert.assertNotNull(this.stockBouchon);
		
		// Création de la vue nécessaire aux tests.
		// La vue s'appuie sur le mock de contrôleur.
		this.vueClient = new VueClient(this.controleurMock);
		Assert.assertNotNull(this.vueClient);
		
		// Affichage de la vue et accès à cette vue.
		this.vueClient.affiche();
		this.accesVue();
	}

	/**
	 * Fermeture de la vue client.
	 *
	 * <p>Code exécuté après chaque test.</p>
	 *
	 * @throws Exception toute exception.
	 *
	 */
	@After
	public void tearDown() throws Exception 
	{
		// 2 secondes d'observation par suspension du thread (objectif pédagogique).
		final Long timeout = Long.valueOf(2000); 
		Thread.sleep(timeout);
		
		if (this.vueClient != null) {
			this.vueClient.ferme();
		}
	}
	
	/**
	 * Accès à la vue client.
	 * 
	 * <p>Cette méthode s'appuie sur les noms des widget pour y accéder.
	 * Cette approche nécessite que des noms soient associés aux widgets.
	 * Une bonne pratique veut que ces noms soient issus des spécifications de l'IHM.</p>
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue client, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 */
	private void accesVue()
	{
		// Accès à la fenêtre de la vue client (par son titre).
		try {
			this.fenetre = new JFrameOperator(Monix.MESSAGES.getString("CLIENT_TITRE_FENETRE"));
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("La fenêtre de la vue client n'est pas accessible : " + e.getMessage());
		}
		
		try {
			// Accès au champ d'information du prix total de la vente (par son nom).
			this.texteTotal = new JTextFieldOperator(this.fenetre, 
					new NameComponentChooser(Monix.IHM.getString("CLIENT_NOM_PRIX")));

			// Accès au panneau texte (ticket) de la vue (par son nom).
			this.texteTicket = new JTextPaneOperator(this.fenetre, 
					new NameComponentChooser(Monix.IHM.getString("CLIENT_NOM_TICKET")));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Problème d'accès à un composant de la vue client : " + e.getMessage());
		}
	}
	
	/**
	 * Test l'initialisation des différents champs de la vue.
	 * 
	 * <p>Méthodes concernées : 
	 * <ul>
	 * <li> private void construirePanneaux()
	 * <li> private void construireControles()
	 * <li> private void afficheDocumentTicketVierge()
	 * </ul>
	 * </p>
	 */
	@Test
	public void testInitialiseVue()
	{
		/*
		 * Données de test.
		 */
		final String prixTotalAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_PRIX_TOTAL");
		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET");
		
		/*
		 * Exécution du test.
		 */
		try {
			// Récupération des valeurs des champs de la vue.
			final String prixTotalActuel = this.texteTotal.getText();
			final String ticketActuel = this.texteTicket.getText();
			
			// Assertions.
			Assert.assertEquals("Prix total par défaut invalide.", prixTotalAttendu, prixTotalActuel);
			Assert.assertEquals("Ticket par défaut invalide.", ticketAttendu, ticketActuel);
						
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue client invalide." + e.getMessage());
		}
	}
	
	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode basée sur une Vente et sur le StockBouchon,
	 * avec utilisation de la méthode ajouteAchatProduit de la classe Vente
	 * pour effectuer les achats (injection des DT). 
	 * </p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	public void testVenteChange_1()
	{
		// Vente utile pour le test.
		final Vente venteTest = new Vente(this.stockBouchon);
		
		/*
		 * Données de test.
		 */
		final String idProduitTest = "11A";
		final Integer quantiteProduitTest = 2;
		final Double prixProduitTest = 10.0;
		
		final String prixTotalAttendu = 
				String.format("%1$.2f €", 
						this.stockBouchon.donneProduit(idProduitTest).donnePrix() * quantiteProduitTest);
		
		// Construction du ticketAttendu.
		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") + FINDELIGNE
				+ String.format("produit un A       x 2      x    %1$.2f €", prixProduitTest);
		
		/*
		 * Injection des données de test.
		 */
		try {
			// Acheter un produit.
			venteTest.ajouteAchatProduit(idProduitTest, quantiteProduitTest);
		} 
		catch (AchatImpossibleException e) {
			Assert.fail("Ajout d'un achat de produit dans une vente impossible.");
		}

		// Création de l'évènement de notification de l'achat du produit.
		final VenteChangeEvenement ev = new VenteChangeEvenement(venteTest.donnePrix(), venteTest.donneAchats());
		
		/*
		 * Exécution du test.
		 */
		
		// Appel de la méthode à tester.
		this.vueClient.venteChange(ev);
		
		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();

		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
	}
	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode identique à la précédente (testVenteChange_1)
	 * avec construction explicite (et autonome) de l'évènement VenteChangeEvenement
	 * passé en paramètre de la méthode à tester (injection des DT).</p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	@Ignore
	public void testVenteChange_2()
	{
		/*
		 * Données de test en entrée.
		 */
		final String libelleProduit = "Libellé P1";
		final Double prixProduit = 10.00;
		final Integer quantiteProduitAchete = 2;
		final Double prixTotal = prixProduit * quantiteProduitAchete;
		
		/*
		 * Résultats attendus.
		 */
		// Prix total attendu.
		final String prixTotalAttendu = String.format("%1$.2f €", prixTotal);
		
		// Ticket attendu.
		final String libelleProduitComplete = "         ";	// cf. configuration CLIENT_FENETRE_TICKET_LIBELLE_LARGEUR.
		final String quantiteProduitComplete = "      ";	// cf. configuration CLIENT_FENETRE_TICKET_QUANTITE_LARGEUR.
		final String prixProduitComplete = "   ";        	// cf. configuration CLIENT_FENETRE_TICKET_PRIX_LARGEUR.

		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") + FINDELIGNE
				+ libelleProduit + libelleProduitComplete
				+ "x " + quantiteProduitAchete + quantiteProduitComplete
				+ "x " + prixProduitComplete + String.format("%1$.2f €", prixProduit);
		
		/*
		 * Injection des données de test.
		 */
		
		// Construction du produit et de l'achat du produit.
		final Produit produit = new Produit("P1", libelleProduit, prixProduit, 1);
		final Achat achat = new Achat(produit, quantiteProduitAchete);

		//  Construction de l'ensemble des achats nécessaires aux tests.
		final LinkedHashMap<String, Achat> achats = new LinkedHashMap<String, Achat>();
		achats.put(produit.donneId(), achat);
				
		// Création de l'évènement de notification de l'achat du produit.
		final VenteChangeEvenement ev = new VenteChangeEvenement(prixTotal, achats);
				
		/*
		 * Exécution du test.
		 */
	
		// Appel de la méthode à tester.
		this.vueClient.venteChange(ev);

		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();
						
		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
	}
	

	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode identique à la précédente (testVenteChange_2)
	 * avec mock de l'évènement VenteChangeEvenement
	 * passé en paramètre de la méthode à tester (injection des DT).</p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	@Ignore
	public void testVenteChange_3()
	{
		/*
		 * Données de test en entrée.
		 */
		final String libelleProduit = "Libellé P1";
		final Double prixProduit = 10.00;
		final Integer quantiteProduitAchete = 2;
		final Double prixTotal = prixProduit * quantiteProduitAchete;
		
		/*
		 * Résultats attendus.
		 */
		// Prix total attendu.
		final String prixTotalAttendu = String.format("%1$.2f €", prixTotal);
		
		// Ticket attendu.
		final String libelleProduitComplete = "         ";	// cf. configuration CLIENT_FENETRE_TICKET_LIBELLE_LARGEUR.
		final String quantiteProduitComplete = "      ";	// cf. configuration CLIENT_FENETRE_TICKET_QUANTITE_LARGEUR.
		final String prixProduitComplete = "   ";        	// cf. configuration CLIENT_FENETRE_TICKET_PRIX_LARGEUR.

		final String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET") + FINDELIGNE
				+ libelleProduit + libelleProduitComplete
				+ "x " + quantiteProduitAchete + quantiteProduitComplete
				+ "x " + prixProduitComplete + String.format("%1$.2f €", prixProduit);
	
		/*
		 * Injection des données de test.
		 */
		
		// Construction du produit et de l'achat du produit.
		final Produit produit = new Produit("P1", libelleProduit, prixProduit, 1);
		final Achat achat = new Achat(produit, quantiteProduitAchete);

		//  Construction de l'ensemble des achats nécessaires aux tests.
		final LinkedHashMap<String, Achat> achats = new LinkedHashMap<String, Achat>();
		achats.put(produit.donneId(), achat);
				
		/* Création en configuration du mock de VenteChangeEvenement. */
		final VenteChangeEvenement evMock = EasyMock.createMock(VenteChangeEvenement.class);
		
		EasyMock.expect(evMock.donneAchats()).andReturn(achats);
		EasyMock.expect(evMock.donnePrix()).andReturn(prixTotal);
		EasyMock.replay(evMock);
		
		/*
		 * Exécution du test.
		 */

		// Appel de la méthode à tester.
		this.vueClient.venteChange(evMock);

		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();
						
		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
		
		/* 
		 * Vérification des sollicitations faites au mock.
		 */
		EasyMock.verify(evMock);
	}
	
	/**
	 * Test l'affichage d'une vente dans la vue client suite à un changement dans la vente.
	 * 
	 * <p>
	 * Méthode identique à la précédente (testVenteChange_3)
	 * avec achat de plusieurs produits.</p>
	 * 
	 * Méthode concernée : public void venteChange(VenteChangeEvenement ev)
	 * 
	 */
	@Test
	public void testVenteChange_multi()
	{
		/*
		 * Données de test en entrée.
		 */
		final int nbDT = 4;
		
		final String[] libelleProduit = {"Libellé P1", "Libellé P2", "Libellé P3", "Libellé P4"};
		final Double[] prixProduit = {10.00, 20.50, 35.10, 47.65};
		final Integer[] quantiteProduitAchete = {2, 3, 7, 9};
		final Double prixTotal = 756.05;
		
		/*
		 * Résultats attendus.
		 */
		// Prix total attendu.
		final String prixTotalAttendu = String.format("%1$.2f €", prixTotal);
		
		// Ticket attendu.
		final String libelleProduitComplete = "         ";	// cf. configuration CLIENT_FENETRE_TICKET_LIBELLE_LARGEUR.
		final String quantiteProduitComplete = "      ";	// cf. configuration CLIENT_FENETRE_TICKET_QUANTITE_LARGEUR.
		final String prixProduitComplete = "   ";        	// cf. configuration CLIENT_FENETRE_TICKET_PRIX_LARGEUR.

		String ticketAttendu = Monix.MESSAGES.getString("CLIENT_TEXTE_TICKET");
		
		for (int i = 0; i < nbDT; i++) {
			ticketAttendu += FINDELIGNE + libelleProduit[i] + libelleProduitComplete
				+ "x " + quantiteProduitAchete[i] + quantiteProduitComplete
				+ "x " + prixProduitComplete + String.format("%1$.2f €", prixProduit[i]);
		}
		
		/*
		 * Injection des données de test.
		 */
		
		// Construction des produit et de l'achat du produit et de l'ensemble des achats nécessaires aux tests.
		final LinkedHashMap<String, Achat> achats = new LinkedHashMap<String, Achat>();
		for (int i = 0; i < nbDT; i++) {
			
			final String idProduit = "P" + i;
			final Produit produit = new Produit(idProduit, libelleProduit[i], prixProduit[i], 1);
			final Achat achat = new Achat(produit, quantiteProduitAchete[i]);
			achats.put(idProduit, achat);
		}
				
		/* Création en configuration du mock de VenteChangeEvenement. */
		final VenteChangeEvenement evMock = EasyMock.createMock(VenteChangeEvenement.class);
		
		EasyMock.expect(evMock.donneAchats()).andReturn(achats);
		EasyMock.expect(evMock.donnePrix()).andReturn(prixTotal);
		EasyMock.replay(evMock);
		
		/*
		 * Exécution du test.
		 */

		// Appel de la méthode à tester.
		this.vueClient.venteChange(evMock);

		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */
		
		// Récupération des valeurs des champs de la vue.
		final String prixTotalActuel = this.texteTotal.getText();
		final String ticketActuel = this.texteTicket.getText();
						
		// Assertions.
		Assert.assertEquals("Prix total invalide.", prixTotalAttendu, prixTotalActuel);
		Assert.assertEquals("Ticket invalide.", ticketAttendu, ticketActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du prix total.
			this.texteTotal.waitText(prixTotalAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix total invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.texteTicket.waitText(ticketAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Ticket invalide.");
		}
		*/
		
		/* 
		 * Vérification des sollicitations faites au mock.
		 */
		EasyMock.verify(evMock);
	}
	
}
