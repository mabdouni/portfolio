package monix.communication;

import monix.modele.stock.Produit;

/**
 * Classe du protocole de communication avec Morix. 
 * 
 * @version 3.0
 * @author Matthias Brun
 * 
 */
public final class ProtocoleMorix
{
	/**
	 * Taille d'une commande.
	 */
	private static final int TAILLE_COMMANDE = 2;
	
	/**
	 * Caractère préfixant une commande.
	 */
	private static final char CARACTERE_COMMANDE = '/';

	/**
	 * Commande de récupération de l'ensemble des produits.
	 */
	public static final char DONNE_PRODUITS = 'P';

	/**
	 * Commande d'inscription au stock de Morix'.
	 */
	public static final char INSCRIPTION_CLIENT = 'I';

	/**
	 * Commande de changement de la quantité d'un produit dans le stock de Morix.
	 */
	public static final char CHANGE_QUANTITE_PRODUIT = 'c';

	/**
	 * Commande de modification de la quantité d'un produit dans le stock local de Monix.
	 */
	public static final char MODIFIE_QUANTITE_PRODUIT = 'q';

	/**
	 * Séparateur de champs d'information pour un produit.
	 */
	private static final String SEPARATEUR_CHAMPS_PRODUIT = ":::";

	/**
	 * Séparateur de produits dans un ensemble de produits.
	 */
	private static final String SEPARATEUR_PRODUITS = "///";

	/**
	 * Message de confirmation d'inscription du client au canal de diffusion.
	 */
	public static final String MESSAGE_INSCRIPTION = "Inscription OK";

	/**
	 * Message erreur.
	 */
	public static final String MESSAGE_ERREUR = "Erreur Morix : ";

	/**
	 * Constructeur privé d'un protocole de communication avec Morix.
	 * 
	 * Ce constructeur privé assure la non-instanciation d'un
	 * protocole de communication dans un programme
	 * (le protocole n'offre que des attributs et des méthodes statiques).
	 */
	private ProtocoleMorix() 
	{
		// Constructeur privé pour assurer la non-instanciation d'un protocole de communication.
	}

	/**
	 * Formate une commande sans paramètre à partir de son nom.
	 * 
	 * <p>La construction d'une commande se fait en préfixant son nom 
	 * par le caractère CARACTERE_COMMANDE et en le suffixant par un espace.</p>
	 *
	 * @param commande la commande à formater.
	 * @return la commande formatée.
	 *
	 */
	static public final String formateCommande(char commande)
	{
		return Character.toString(ProtocoleMorix.CARACTERE_COMMANDE) + commande + " "; 
	}

	/**
	 * Retourne les paramètres d'une commande dans un tableau de chaînes de caractères.
	 * 
	 * @param commande la commande d'où extraire les paramètres.
	 * @param separateur le séparateur permettant de distinguer les paramètres dans la commande.
	 * @return le tableau des paramètres de la commande.
	 */
	private static final String[] parametresCommande(String commande, String separateur)
	{
		String chaineParametres = "";
		String[] parametres = null;

		if (commande.length() > ProtocoleMorix.TAILLE_COMMANDE + 1) {
			chaineParametres = commande.substring(ProtocoleMorix.TAILLE_COMMANDE + 1, commande.length());
			parametres = chaineParametres.split(separateur);
		}
		return parametres;		
	}
	
	/**
	 * Valide qu'un message respecte bien le format d'une commande.
	 *
	 * @param message le message à analyser.
	 * 
	 * @return vrai (<i>true</i>) si le message est une commande, faux (<i>false</i>) sinon.
	 */
	public static final Boolean estUneCommande(String message)
	{
		return (message.length() > 1) && (message.charAt(0) == ProtocoleMorix.CARACTERE_COMMANDE);
	}

	/**
	 * Donne la commande d'un message.
	 * 
	 * <p>
	 * La commande est identifiée par le second caractère d'un message
	 * (le premier caractère étant ProtocoleMorix.CARACTERE_COMMANDE).
	 * </p>
	 * 
	 * @param message le message d'où extraire la commande.
	 * 
	 * @return la commande du message.
	 */
	public static final char commandeDuMessage(String message)
	{
		return message.charAt(1);
	}
	
	/**
	 * Formate le message de demande de changement de quantité d'un produit du stock.
	 * 
	 * <p>Le message débute par le commande de changement de quantité d'un produit
	 * (ProtocoleMorix.CHANGE_QUANTITE_PRODUIT) suivie des champs :
	 * <ul>
	 * <li> id du produit et
	 * <li> quantité du produit à ajouter ou à enlever.
	 * </ul>
	 * Ces champs sont séparés par le séparateur <tt>ProtocoleMorix.SEPARATEUR_CHAMPS_PRODUIT</tt>.
	 * </p>
	 * 
	 * @param id l'identifiant du produit concerné.
	 * @param quantite la quantité à ajouter ou à enlever.
	 * 
	 * @return le message formatté.
	 */
	public static final String formatteMessageChangementQuantiteProduit(String id, Integer quantite)
	{
		return ProtocoleMorix.formateCommande(ProtocoleMorix.CHANGE_QUANTITE_PRODUIT)
				+ id + ProtocoleMorix.SEPARATEUR_CHAMPS_PRODUIT
				+ quantite;
	}
	
	/**
	 * Donne l'ensemble des produits du stock dans un tableau à partir d'un message de Morix.
	 * 
	 * <p>L'ensemble des produits est fourni sous la forme d'une chaîne de caractères.
	 * Les produits sont séparés par le séparateur <tt>ProtocoleMorix.SEPARATEUR_PRODUITS</tt>.
	 * </p>
	 * <p>
	 * Les champs d'un produit sont respectivement :
	 * <ul>
	 * <li> l'id,
	 * <li> le libellé et
	 * <li> le prix.
	 * </ul>
	 * Ces champs sont séparés par le séparateur <tt>ProtocoleMorix.SEPARATEUR_CHAMPS_PRODUIT</tt>.
	 * </p>
	 * 
	 * @param message l'ensemble des produits fournis dans un message issu de Morix.
	 * 
	 * @return le tableau de produit de cet ensemble.
	 */
	public static final Produit[] donneEnsembleProduits(String message)
	{
		final String[] produits = message.split(ProtocoleMorix.SEPARATEUR_PRODUITS);

		final Produit[] ensembleProduits = new Produit[produits.length];

		// Pour chaque produit.
		for (int i = 0; i < produits.length; i++) {
			final String[] produit = produits[i].split(ProtocoleMorix.SEPARATEUR_CHAMPS_PRODUIT);
			Integer index = 0;
			
			ensembleProduits[i] = 
				new Produit(
					produit[index++],					// id.
					produit[index++],					// libellé.
					Double.valueOf(produit[index++]),	// prix.
					Integer.valueOf(produit[index])		// quantité.
				);
		}
		
		return ensembleProduits;
	}
	
	/**
	 * Donne l'identifiant du produit concerné par une commande de modification de quantité
	 * d'un produit à partir du message de cette commande de modification.
	 * 
	 * @param message le message de commande de modification.
	 * 
	 * @return l'identifiant du produit concerné.
	 */
	public static final String parametreModifieQuantiteProduitId(String message)
	{
		final String[] parametres = ProtocoleMorix.parametresCommande(message, ProtocoleMorix.SEPARATEUR_CHAMPS_PRODUIT);
		
		return parametres[0];
	}
	
	/**
	 * Donne la quantité concernée par une commande de modification de quantité
	 * d'un produit à partir du message de cette commande de modification.
	 * 
	 * @param message le message de commande de modification.
	 * 
	 * @return la quantité à modifier.
	 */
	public static final Integer parametreModifieQuantiteProduitQuantite(String message)
	{
		final String[] parametres = ProtocoleMorix.parametresCommande(message, ProtocoleMorix.SEPARATEUR_CHAMPS_PRODUIT);
		
		return Integer.parseInt(parametres[1]);
	}
}
