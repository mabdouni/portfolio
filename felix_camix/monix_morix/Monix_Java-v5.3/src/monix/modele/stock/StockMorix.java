package monix.modele.stock;

import java.io.EOFException;
import java.io.IOException;

import monix.communication.CommunicationMorix;
import monix.communication.CommunicationMorixException;

/**
 * Classe de gestion d'un stock de Morix.
 *
 * @version 3.0
 * @author Matthias Brun
 *
 * @see monix.modele.stock.Stock
 * @see monix.modele.stock.StockAbstrait
 *
 */
public final class StockMorix extends StockAbstrait implements Runnable
{
	/**
	 * Communication avec Morix.
	 */
	private CommunicationMorix communication;

	/**
	 * Constructeur du stock.
	 * 
	 *  <p><ul>
	 *  <li> Récupération des produits de Morix.</li>
	 *  <li> Lancement du thread de réception des messages du canal de diffusion de Morix.</li>
	 *  </ul></p>
	 *
	 * @param adresse l'adresse du serveur de Morix.
	 * @param port le port de connexion à Morix.
	 * 
	 * @see monix.modele.stock.StockAbstrait
	 * 
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 */
	public StockMorix(String adresse, Integer port) throws CommunicationMorixException
	{
		super();

		this.communication = new CommunicationMorix(adresse, port);

		this.initialiseProduitsStock();
		this.inscriptionCanalDiffusion();

		// Lancement d'un thread de réception des messages du canal de diffusion de Morix.
		new Thread(this).start();
	}

	/**
	 * Initialise les produits disponibles à partir du stock de Morix.
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 */
	private void initialiseProduitsStock() throws CommunicationMorixException
	{
		try {
			// Envoi de la commande de récupération de l'ensemble des produits.
			this.communication.demandeProduitsStock();

			// Récupération de l'ensemble des produits.
			final Produit[] produits = this.communication.receptionProduitsStock();

			// Pour chaque produit.
			for (int i = 0; i < produits.length; i++) {
				// Ajout du produit dans le stock.
				ajouteProduit(produits[i]);
			}
		}  
		catch (CommunicationMorixException ex) {
			System.err.println("Initialisation des données du stock impossible.");
			throw ex;
		}
	}

	/**
	 * Inscription au canal de diffusion des informations sur le stock de Morix.
	 *
	 * <p>Cette inscription permet à Monix de recevoir les informations de modification
	 * de la base de données du stock de Morix.</p>
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 */
	private void inscriptionCanalDiffusion() throws CommunicationMorixException
	{
		try {
			// Envoi de la commande d'inscription au canal de diffusion.
			this.communication.demandeInscriptionCanalDiffusion();
		
			// Attente de la confirmation de l'inscription.
			this.communication.receptionInscriptionCanalDiffusion();
		}  
		catch (CommunicationMorixException ex) {
			System.err.println("Inscription au stock de Morix impossible.");
			throw ex;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see monix.modele.stock.Stock
	 * 
	 */
	@Override
	public void changeQuantiteProduit(Produit produit, Integer quantite)
	{
		try {
			this.communication.demandeChangerProduitStock(produit, quantite);

			// Remarque : la réponse à cette requête est reçue par le thread
			// en écoute du canal de diffusion de Morix.
		}
		catch (CommunicationMorixException ex) {
			System.err.println("Changement d'une quantité d'un produit du stock de Morix impossible.");
			System.err.println(ex.getMessage());
		}
	}

	/**
	 * Modifie la quantité d'un produit dans le stock.
	 *
	 * @param id l'identifiant du produit concerné.
	 * @param quantite la nouvelle quantité de produit.
	 */
	public void modifieQuantiteProduit(String id, Integer quantite)
	{
		donneProduit(id).modifieQuantite(quantite);
		signalStockChange();
	}

	/**
	 * Point d'entrée du thread de réception des messages du canal de diffusion de Morix.
	 * 
	 */
	@Override
	public void run() 
	{
		try {
			while (true) {
				// Réception et traitement des commandes de Morix.
				this.communication.receptionCommandeMorix(this);
			}
		}
		catch (EOFException ex) {
			System.err.println("Fin des échanges avec Morix.");
		}
		catch (IOException | CommunicationMorixException ex) {
			System.err.println("Communication avec Morix interrompue.");
			System.err.println(ex.getMessage());
		}
	}
}

