package monix.communication;

import java.io.EOFException;
import java.io.IOException;

import monix.modele.stock.Produit;
import monix.modele.stock.StockMorix;

/**
 * Classe de communication avec Morix. 
 * 
 * @version 5.3
 * @author Matthias Brun
 * 
 */
public class CommunicationMorix
{
	/**
	 * Connexion réseau avec Morix.
	 */
	private ConnexionMorix connexion;

	/**
	 * Constructeur d'une communication avec Morix.
	 *
	 * @param adresse l'adresse du serveur de Morix.
	 * @param port le port de connexion à Morix.
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 *
	 */
	public CommunicationMorix(String adresse, Integer port) throws CommunicationMorixException
	{
		try {
			this.connexion = new ConnexionMorix(adresse, port);
		}
		catch (IOException ex) {
			System.err.println("Impossible d'établir la communication avec Morix.");
			throw new CommunicationMorixException(ex.getMessage());
		}
	}


	/**
	 * Envoie la commande de récupération de l'ensemble des produits du stock de Morix.
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 *
	 */
	public void demandeProduitsStock() throws CommunicationMorixException
	{
		try {
			this.connexion.ecrire(ProtocoleMorix.formateCommande(ProtocoleMorix.DONNE_PRODUITS));
		}
		catch (IOException ex) {
			System.err.println("Demande de l'ensemble des produits de Morix impossible.");
			throw new CommunicationMorixException(ex.getMessage());
		}
	}

	/**
	 * Envoie la commande de demande d'inscription au canal de diffusion du stock de Morix.
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 *
	 */
	public void demandeInscriptionCanalDiffusion() throws CommunicationMorixException
	{
		try {
			this.connexion.ecrire(ProtocoleMorix.formateCommande(ProtocoleMorix.INSCRIPTION_CLIENT));
		}
		catch (IOException ex) {
			System.err.println("Demande d'inscription comme client de Morix impossible.");
			throw new CommunicationMorixException(ex.getMessage());
		}
	}

	/**
	 * Envoie la commande de changement d'une certaine quantité d'un produit dans le stock de Morix.
	 *
	 * <p>
	 * Si la quantité est positive, cette quantité sera ajoutée dans le stock. 
	 * Si la quantité est négative, la quantité de produit sera enlevée.
	 * </p>
	 * 
	 * @param produit le produit concerné.
	 * @param quantite la quantité de produit à changer.
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 *
	 */
	public void demandeChangerProduitStock(Produit produit, Integer quantite) throws CommunicationMorixException
	{
		try {
			this.connexion.ecrire(ProtocoleMorix.formatteMessageChangementQuantiteProduit(produit.donneId(), quantite));
		}
		catch (IOException ex) {
			System.err.println("Demande de changement d'une quantité de produit du stock de Morix impossible.");
			throw new CommunicationMorixException(ex.getMessage());
		}
	}

	/**
	 * Réception de l'ensemble des produits du stock de Morix.
	 *
	 * <p>L'ensemble des produits est fourni sous la forme d'une chaîne de caractères.
	 * Les produits sont séparés par le séparateur <tt>SEPARATEUR_PRODUITS</tt> et
	 * les champs d'un produit sont respectivement l'id, le libellé, le prix et la quantité 
	 * séparés par le séparateur <tt>SEPARATEUR_CHAMPS_PRODUIT</tt>.</p>
	 *
	 * @return un tableau des produits présents dans le stock de Morix.
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 */
	public Produit[] receptionProduitsStock() throws CommunicationMorixException
	{
		Produit[] ensembleProduits = null;

		try {
			final String message = this.receptionneMessage();

			ensembleProduits = ProtocoleMorix.donneEnsembleProduits(message);
		}
		catch (IOException | CommunicationMorixException ex) {
			System.err.println("Récupération de l'ensemble des produits auprès de Morix impossible.");
			throw new CommunicationMorixException(ex.getMessage());
		}

		return ensembleProduits;
	}

	/** 
	 * Réception de la confirmation d'inscription au canal de diffusion de Morix.
	 *
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 *
	 */
	public void receptionInscriptionCanalDiffusion() throws CommunicationMorixException
	{
		try {
			final String message = this.receptionneMessage();

			if (message.matches(ProtocoleMorix.MESSAGE_INSCRIPTION)) {
				// Inscription OK.
				System.out.println("Monix travaille avec Morix.");
			} else {
				// Le serveur Morix a retourné un message d'erreur.
				System.err.println("Inscription auprès de Morix non confirmée.");
				throw new IOException("Message de confirmation de Morix : " + message);
			}
		}
		catch (CommunicationMorixException ex) {
			System.err.println("Inscription auprès de Morix impossible.");
			throw ex;
		}
		catch (EOFException ex) {
			System.err.println("Communication avec Morix impossible (pour confirmation inscription).");
			throw new CommunicationMorixException(ex.getMessage());
		}
		catch (IOException ex) {
			System.err.println("Réception de la confirmation d'inscription auprès de Morix impossible.");
			throw new CommunicationMorixException(ex.getMessage());
		}
	}

	/**
	 * Réception et traitement d'une commande de Morix.
	 *
	 * @param destinataire le stock (StockMorix) à qui se destine la commande.
	 *
	 * @throws IOException exception s'entrée/sortie.
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 */
	public void receptionCommandeMorix(StockMorix destinataire) throws CommunicationMorixException, IOException
	{
		try {
			final String message = this.receptionneMessage();

			if (ProtocoleMorix.estUneCommande(message)) {
				// Si le message est une commande.
				this.traiteCommandeMorix(destinataire, message);
			} else {
				// Si le message n'est pas une commande.
				System.err.println("Réception du message de Morix non géré : " + message);
			}
		}
		catch (IOException | CommunicationMorixException ex) {
			System.err.println("Réception d'une commande auprès de Morix impossible.");
			throw ex;
		}
	}

	/**
	 * Traite une commande provenant de Morix.
	 *
	 * Commande prise en charge :
	 * <ul>
	 * <li> Commande de modification de la quantité d'un produit dans le stock.
	 *      Les paramètres qui accompagnent cette commande sont l'identifiant du
	 *      produit et la nouvelle quantité de produit dans le stock.</li>
	 * </ul>
	 *
	 * @param destinataire le stock (StockMorix) à qui se destine la commande.
	 * @param message le message à traiter (commande + paramètres).
	 */
	private void traiteCommandeMorix(StockMorix destinataire, String message)
	{
		if (ProtocoleMorix.commandeDuMessage(message) == ProtocoleMorix.MODIFIE_QUANTITE_PRODUIT) {
			// Commande de modification de la quantité d'un produit dans le stock.		
			destinataire.modifieQuantiteProduit(
					ProtocoleMorix.parametreModifieQuantiteProduitId(message),
					ProtocoleMorix.parametreModifieQuantiteProduitQuantite(message)
			);
		} else {
			System.err.println("Provenance de Morix : Commande inconnue.");
		}
	}
	
	/**
	 * Réception d'un message de Morix.
	 *
	 * @return le message issu de Morix.
	 *
	 * @throws IOException exception d'entrée/sortie.
	 * @throws CommunicationMorixException exception de communication avec Morix.
	 *
	 */
	private String receptionneMessage() throws CommunicationMorixException, IOException
	{
		String message = null;

		try {
			message = this.connexion.lire();
	
			if (message.matches(ProtocoleMorix.MESSAGE_ERREUR + ".*")) {
				// Le serveur Morix a retourné un message d'erreur.
				throw new CommunicationMorixException(message);
			}			
		}
		catch (EOFException ex) {
			System.err.println("Connexion interrompue avec Morix.");
			throw ex;
		}
		catch (IOException ex) {
			System.err.println("Reception d'un message de Morix impossible.");
			throw ex;
		}

		return message;
	}	
}
