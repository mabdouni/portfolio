package monix.vue;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import monix.Monix;
import monix.controleur.ControleurVente;
import monix.modele.stock.Produit;
import monix.modele.stock.Stock;

/**
 * Classe de tests unitaires JUnit 4 de la classe VueCaisse.
 *
 * <p>Utilisation d'un mock de stock (construit avec EasyMock).</p>
 * <p>Utilisation d'un mock de contrôleur (construit avec EasyMock) pour le MVC.</p>
 * <p>Utilisation de Jemmy 2 pour la manipulation de l'IHM.</p>
 *
 * @version 5.3
 * @author Matthias Brun
 *
 * @see monix.vue.VueCaisse
 * @see org.easymock.EasyMock
 * @see org.netbeans.jemmy.JemmyProperties
 *
 */
public class VueCaisseTest
{
	/**
	 * Vue Caisse nécessaire aux tests.
	 */
	private VueCaisse vueCaisse;
	
	/**
	 * Mock d'un contrôleur nécessaire aux tests.
	 */
	private ControleurVente controleurMock;
	
	/**
	 * Mock d'un stock nécessaire aux tests.
	 */
	private Stock stockMock;

	/**
	 * Opérateurs de JFrame pour accéder à la fenêtre de la vue.
	 */
	private JFrameOperator fenetre;
	
	/**
	 * Opérateurs de JButton pour accéder aux boutons de la vue.
	 */
	private JButtonOperator boutonAnnuler, boutonAjouter, boutonFinVente;

	/**
	 * Opérateurs de JTextField pour accéder aux champs texte de la vue.
	 */
	private JTextFieldOperator texteId, texteQuantite, texteLibelle, textePrix, texteInfo, texteTotal;


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
		
		// Création d'un mock de stock.
		this.stockMock = EasyMock.createMock(Stock.class);
		Assert.assertNotNull(this.stockMock);
		
		// Création de la vue nécessaire aux tests.
		// La vue s'appuie sur le mock de contrôleur.
		this.vueCaisse = new VueCaisse(this.controleurMock);
		Assert.assertNotNull(this.vueCaisse);
		
		// Affichage de la vue et accès à cette vue.
		this.vueCaisse.affiche();
		this.accesVue();
	}

	/**
	 * Fermeture de la vue caisse.
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
		
		if (this.vueCaisse != null) {
			this.vueCaisse.ferme();
		}
	}

	/**
	 * Accès à la vue caisse.
	 * 
	 * <p>Cette méthode s'appuie sur les index des widget pour y accéder.</p>
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue caisse, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 */
	private void accesVue()
	{
		// Accès à la fenêtre de la vue de la caisse (par son titre).
		try {
			this.fenetre = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_TITRE_FENETRE"));
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("La fenêtre de la vue caisse n'est pas accessible : " + e.getMessage());
		}
		
		try {
			// Index pour la récupération des widgets.
			Integer index = 0;
		
			// Accès au champ de saisie d'un identifiant produit (par son index).
			this.texteId = new JTextFieldOperator(this.fenetre, index++);
			
			// Accès au champ de saisie de la quantité de produit (par son index).
			this.texteQuantite = new JTextFieldOperator(this.fenetre, index++);
			
			// Accès au champ de libellé d'un produit (par son index).
			this.texteLibelle = new JTextFieldOperator(this.fenetre, index++);
			
			// Accès au champ de prix d'un produit (par son index).
			this.textePrix = new JTextFieldOperator(this.fenetre, index++);
			
			// Accès au champ d'information d'un achat (par son index).
			this.texteInfo = new JTextFieldOperator(this.fenetre, index++);
			
			// Accès au champ de prix total de la vente (par son index).
			this.texteTotal = new JTextFieldOperator(this.fenetre, index);
			
			// Ré-initialisation de l'index pour l'accès aux boutons.
			index = 0;
			
			// Accès au bouton d'ajout d'un produit à la vente (par son index).
			this.boutonAjouter = new JButtonOperator(this.fenetre, index++);
			
			// Accès au bouton d'annulation d'un produit à la vente (par son index).
			this.boutonAnnuler = new JButtonOperator(this.fenetre, index++);
			
			// Accès au bouton de clôture de la vente (par son index).
			this.boutonFinVente = new JButtonOperator(this.fenetre, index);
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Problème d'accès à un composant de la vue caisse : " + e.getMessage());
		}
	}

	/**
	 * Accès à la vue caisse.
	 * 
	 * <p>Cette méthode s'appuie sur les valeurs des widget pour y accéder.</p>
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue caisse, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 */
	@SuppressWarnings("unused")
	private void accesVueParValeur()
	{
		// Accès à la fenêtre de la vue de la caisse (par son titre).
		try {
			this.fenetre = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_TITRE_FENETRE"));
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("La fenêtre de la vue caisse n'est pas accessible : " + e.getMessage());
		}
		
		try {
			// Accès au champ de saisie d'un identifiant produit (par sa valeur).
			this.texteId = new JTextFieldOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_TEXTE_ID_PRODUIT"));
			
			// Accès au champ de saisie de la quantité de produit (par sa valeur).
			this.texteQuantite = new JTextFieldOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_TEXTE_QUANTITE"));
			
			// Accès au champ de libellé d'un produit (par sa valeur).
			this.texteLibelle = new JTextFieldOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_TEXTE_LIBELLE"));
			
			// Accès au champ de prix d'un produit (par sa valeur).
			this.textePrix = new JTextFieldOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX"));
			
			// Accès au champ d'information d'un achat (par sa valeur).
			this.texteInfo = new JTextFieldOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_TEXTE_INFO"));
			
			// Accès au champ de prix total de la vente (par sa valeur).
			this.texteTotal = new JTextFieldOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX_TOTAL"));
			
			// Accès au bouton d'ajout d'un produit à la vente (par sa valeur).
			this.boutonAjouter = new JButtonOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_BOUTON_AJOUTER"));
			
			// Accès au bouton d'annulation d'un produit à la vente (par sa valeur).
			this.boutonAnnuler = new JButtonOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_BOUTON_ANNULER"));
			
			// Accès au bouton de clôture de la vente (par sa valeur).
			this.boutonFinVente = new JButtonOperator(this.fenetre, Monix.MESSAGES.getString("CAISSE_BOUTON_FIN_VENTE"));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Problème d'accès à un composant de la vue caisse : " + e.getMessage());
		}
	}
	
	/**
	 * Accès à la vue caisse.
	 * 
	 * <p>Cette méthode s'appuie sur les noms des widget pour y accéder.
	 * Cette approche nécessite que des noms soient associés aux widgets.
	 * Une bonne pratique veut que ces noms soient issus des spécifications de l'IHM.</p>
	 * 
	 * <p>Cette méthode concerne l'accès à la fenêtre de la vue caisse, 
	 * avec titre adéquat et widgets attendus pour cette vue.</p>
	 */
	@SuppressWarnings("unused")
	private void accesVueParNom()
	{
		// Accès à la fenêtre de la vue de la caisse (par son titre).
		try {
			this.fenetre = new JFrameOperator(Monix.MESSAGES.getString("CAISSE_TITRE_FENETRE"));
		} 
		catch (TimeoutExpiredException e) {
			Assert.fail("La fenêtre de la vue caisse n'est pas accessible : " + e.getMessage());
		}

		// Accès aux composants (widgets) de la vue caisse
		try {
			// Accès au champ de saisie d'un identifiant produit (par son nom).
			this.texteId = new JTextFieldOperator(this.fenetre,  
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_SAISIE_ID_PRODUIT")));

			// Accès au champ de saisie de la quantité de produit (par son nom).
			this.texteQuantite = new JTextFieldOperator(this.fenetre, 
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_SAISIE_QUANTITE_PRODUIT")));

			// Accès au champ de libellé d'un produit (par son nom).
			this.texteLibelle = new JTextFieldOperator(this.fenetre,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_LIBELLE_PRODUIT")));

			// Accès au champ de prix d'un produit (par son nom).
			this.textePrix = new JTextFieldOperator(this.fenetre,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_PRIX_PRODUIT")));

			// Accès au champ d'information d'un achat (par son nom).
			this.texteInfo = new JTextFieldOperator(this.fenetre,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_INFO_PRODUIT")));

			// Accès au champ de prix total de la vente (par son nom).
			this.texteTotal = new JTextFieldOperator(this.fenetre,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_AFFICHAGE_PRIX_TOTAL")));

			// Accès au bouton d'ajout d'un produit à la vente (par son nom).
			this.boutonAjouter = new JButtonOperator(this.fenetre,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_AJOUTER")));

			// Accès au bouton d'annulation d'un produit à la vente (par son nom).
			this.boutonAnnuler = new JButtonOperator(this.fenetre,   
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_ANNULER")));

			// Accès au bouton de clôture de la vente (par son nom).
			this.boutonFinVente = new JButtonOperator(this.fenetre, 
					new NameComponentChooser(Monix.IHM.getString("CAISSE_NOM_BOUTON_FIN_VENTE")));
		}
		catch (TimeoutExpiredException e) {
			Assert.fail("Problème d'accès à un composant de la vue caisse : " + e.getMessage());
		}
	}
	
	/**
	 * Test l'initialisation des différents champs de la vue.
	 * 
	 * <p>Méthodes concernées : 
	 * <ul>
	 * <li> private void construireControles()
	 * <li> private void initialiseIdQuantiteLibellePrix()
	 * </ul>
	 * </p>
	 */
	@Test
	public void testInitialiseVue()
	{
		/*
		 * Données de test.
		 */
		final String idProduitAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_ID_PRODUIT");
		final String quantiteAttendue = Monix.MESSAGES.getString("CAISSE_TEXTE_QUANTITE");
		final String libelleAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_LIBELLE");
		final String prixAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX");
		final String infoAttendue = Monix.MESSAGES.getString("CAISSE_TEXTE_INFO");
		final String totalAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX_TOTAL");
		
		final String boutonAjouterAttendu = Monix.MESSAGES.getString("CAISSE_BOUTON_AJOUTER");
		final String boutonAnnulerAttendu = Monix.MESSAGES.getString("CAISSE_BOUTON_ANNULER");
		final String boutonFinVenteAttendu = Monix.MESSAGES.getString("CAISSE_BOUTON_FIN_VENTE");
		
		/*
		 * Configuration et chargement du mock du contrôleur.
		 */

		// Le mock du contrôleur sera sollicité lors de la perte du focus du champ
		// de saisie d'un identifiant lors de la fermeture de la fenêtre.
		// Le contrôleur retourne alors le mock du stock.
		EasyMock.expect(this.controleurMock.donneStock()).andReturn(this.stockMock);
		EasyMock.replay(this.controleurMock);
		
		/*
		 * Exécution du test.
		 */
		try {
			// Récupération des valeurs des champs de la vue.
			final String idProduitActuel = this.texteId.getText();
			final String quantiteActuelle = this.texteQuantite.getText();
			final String libelleActuel = this.texteLibelle.getText();
			final String prixActuel = this.textePrix.getText();
			final String infoActuelle = this.texteInfo.getText();
			final String totalActuel = this.texteTotal.getText();
			
			// Récupération les libellés des boutons de la vue.
			final String boutonAjouterActuel = this.boutonAjouter.getText();
			final String boutonAnnulerActuel = this.boutonAnnuler.getText();
			final String boutonFinVenteActuel = this.boutonFinVente.getText();

			// Assertions.
			Assert.assertEquals("Id produit par défaut invalide.", idProduitAttendu, idProduitActuel);
			Assert.assertEquals("Quantité de produit par défaut invalide.", quantiteAttendue, quantiteActuelle);
			Assert.assertEquals("Libellé par défaut invalide.", libelleAttendu, libelleActuel);
			Assert.assertEquals("Prix d'un produit par défaut invalide.", prixAttendu, prixActuel);
			Assert.assertEquals("Information d'un achat par défaut invalide.", infoAttendue, infoActuelle);
			Assert.assertEquals("Prix total de la vente par défaut invalide.", totalAttendu, totalActuel);
			Assert.assertEquals("Libellé du bouton ajouter invalide.", boutonAjouterAttendu, boutonAjouterActuel);
			Assert.assertEquals("Libellé du bouton annuler invalide.", boutonAnnulerAttendu, boutonAnnulerActuel);
			Assert.assertEquals(
					"Libellé du bouton de clôture de vente invalide.", 
					boutonFinVenteAttendu, boutonFinVenteActuel);
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue caisse invalide." + e.getMessage());
		}
		
		/* Attention : la vérification des sollicitations faite au mock n'a pas de sens ici, 
		 * puisque celui-ci ne sera sollicité qu'à la fermeture de la fenêtre.
		 */
	}
	
	
	/**
	 * Test de la réaction suite à la perte du focus du champ de saisie d'un identifiant de produit.
	 * 
	 * <p>Test après saisie d'un identifiant de produit <b>non valide</b>.</p>
	 * 
	 * <p>Méthode concernée : public void focusLost(FocusEvent ev)</p>
	 */
	@Test
	public void testFocusLost_produitNonValide()
	{
		/*
		 * Données de test.
		 */
		final String idProduit = "id de produit invalide (quelconque)";
		final String libelleAttendu = "Produit inconnu";
		final String prixAttendu = Monix.MESSAGES.getString("CAISSE_TEXTE_PRIX");

		/*
		 * Configuration et chargement des mocks du contrôleur et du stock.
		 */

		// Le mock du contrôleur est sollicité pour connaître le stock,
		// celui-ci retourne alors le mock du stock.
		EasyMock.expect(this.controleurMock.donneStock()).andReturn(this.stockMock);
		EasyMock.replay(this.controleurMock);
		
		// Le mock du stock est sollicité pour récupérer le produit correspondant à l'identifiant saisi,
		// celui-ci retourne null (car le produit n'est pas censé être dans le stock pour ce test).
		EasyMock.expect(this.stockMock.donneProduit(idProduit)).andReturn(null);
		EasyMock.replay(this.stockMock);
		
		/*
		 * Exécution du test.
		 */
		try {
			// Saisie de la référence du produit.
			this.texteId.clearText();
			this.texteId.typeText(idProduit);
			
			// Force la perte de focus du champ de saisie de l'identifiant d'un produit
			// en donnant le focus au champ de saisie de la quantité de produit.
			this.texteQuantite.clickMouse();
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue caisse invalide." + e.getMessage());
		}
		
		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */	
		// Récupération de la valeur du champ de libellé du produit et du champ du prix du produit.
		final String libelleActuel = this.texteLibelle.getText();
		final String prixActuel = this.textePrix.getText();

		// Assertions.
		Assert.assertEquals("Libellé pour un produit non-existant invalide.", libelleAttendu, libelleActuel);
		Assert.assertEquals("Prix d'un produit non-existant invalide.", prixAttendu, prixActuel);
			
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */
		/*
		try {
			// Attente du message du libellé.
			this.texteLibelle.waitText(libelleAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Libellé pour un produit non-existant invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.textePrix.waitText(prixAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix d'un produit non-existant invalide.");
		}
		*/
		
		/* 
		 * Vérification des sollicitations faites aux mocks.
		 */
		EasyMock.verify(this.controleurMock);
		EasyMock.verify(this.stockMock);
	}
	
	/**
	 * Test de la réaction suite à la perte du focus du champ de saisie d'un identifiant de produit.
	 * 
	 * <p>Test après saisie d'un identifiant de produit <b>valide</b>.</p>
	 * 
	 * <p>Méthode concernée : public void focusLost(FocusEvent ev)</p>
	 */
	@Test
	public void testFocusLost_produitValide()
	{
		/*
		 * Données de test.
		 */
		final String idProduit = "id de produit valide (quelconque)";
		final String libelleProduit = "produit de test";
		final Integer quantiteProduit = 20;
		final Double prixProduit = 10.0;
		
		final Produit produit = new Produit(idProduit, libelleProduit, prixProduit, quantiteProduit);
		
		final String libelleAttendu = libelleProduit;
		final String prixAttendu = String.format("%1$.2f €", prixProduit);
		
		/*
		 * Configuration et chargement des mocks du contrôleur et du stock.
		 */

		// Le mock du contrôleur est sollicité pour connaître le stock,
		// celui-ci retourne alors le mock du stock.
		EasyMock.expect(this.controleurMock.donneStock()).andReturn(this.stockMock);
		EasyMock.replay(this.controleurMock);
		
		// Le mock du stock est sollicité pour récupérer le produit correspondant à l'identifiant saisi,
		// celui-ci retourne null (car le produit n'est pas censé être dans le stock pour ce test).
		EasyMock.expect(this.stockMock.donneProduit(idProduit)).andReturn(produit);
		EasyMock.replay(this.stockMock);
		
		/*
		 * Exécution du test.
		 */
		try {
			// Saisie de la référence du produit.
			this.texteId.clearText();
			this.texteId.typeText(idProduit);
			
			// Force la perte de focus du champ de saisie de l'identifiant d'un produit
			// en donnant le focus au champ de saisie de la quantité de produit.
			this.texteQuantite.clickMouse();
		}
		catch (Exception e) {
			Assert.fail("Manipulation de la vue caisse invalide." + e.getMessage());
		}
		
		/*
		 * Deux approches de validation.
		 */
		
		/* Approche 1 */	
		
		// Récupération de la valeur du champ de libellé du produit et du champ du prix du produit.
		final String libelleActuel = this.texteLibelle.getText();
		final String prixActuel = this.textePrix.getText();

		// Assertions.
		Assert.assertEquals("Libellé pour un produit existant invalide.", libelleAttendu, libelleActuel);
		Assert.assertEquals("Prix d'un produit existant invalide.", prixAttendu, prixActuel);
		
		/* Approche 2 : 
		 * nécessite JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout); (cf. setUp). */	
		/*
		try {
			// Attente du message du libellé.
			this.texteLibelle.waitText(libelleAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Libellé pour un produit existant invalide.");
		}
		
		try {
			// Attente du message du prix.
			this.textePrix.waitText(prixAttendu);
		} catch (TimeoutExpiredException e) {
			Assert.fail("Prix d'un produit existant invalide.");
		}
		*/
		
		/*
		 * Vérification des sollicitations faites aux mocks. 
		 */
		EasyMock.verify(this.controleurMock);
		EasyMock.verify(this.stockMock);
	}

}
