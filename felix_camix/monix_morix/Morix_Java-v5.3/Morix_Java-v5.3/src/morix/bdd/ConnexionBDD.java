package morix.bdd;

import java.sql.Connection;
import java.util.ArrayList;

import morix.stock.Produit;

/**
 * Interface d'accès à une base de données. 
 * 
 * @version 5.3
 * @author Matthias Brun
 */
public interface ConnexionBDD
{
	/**
	 * Donne la connexion à la base de données.
	 *
	 * @return la connexion à la base de données.
	 *
	 */
	public Connection donneConnexion();

	/**
	 * Fermeture de la connexion à la base de données.
	 *
	 * @throws BDDException exception d'accès à la base de données.
	 *
	 */
	public void ferme() throws BDDException;

	/**
	 * Ajoute un produit dans la base de données.
	 * 
	 * @param id l'identifiant du produit.
	 * @param libelle le libellé du produit.
	 * @param prix le prix du produit.
	 * @param quantite la quantité de produit à ajouter.
	 * 
	 * @throws BDDException exception d'accès à la base de données.
	 *
	 */
	public void ajouteProduit(String id, String libelle, Double prix, Integer quantite)  throws BDDException;

	/**
	 * Change une certaine quantité d'un produit dans la base de données.
	 * 
	 * <p>
	 * Si la quantité est positive, cette quantité sera ajoutée. 
	 * Si la quantité est négative, elle sera enlevée.
	 * </p>
	 * 
	 * @param id l'identifiant du produit.
	 * @param quantite la quantité de produit à changer.
	 * 
	 * @throws BDDException exception d'accès à la base de données.
	 *
	 */
	public void changeQuantiteProduit(String id, Integer quantite) throws BDDException;

	/**
	 * Donne un produit à partir de son identifiant.
	 * 
	 * @param id l'identifiant du produit.
	 * @return le produit dont l'identifiant est id, <code>null</code> si le produit n'est pas dans la base.
	 *
	 * @throws BDDException exception d'accès à la base de données.
	 *
	 */
	public Produit donneProduit(String id) throws BDDException;

	/**
	 * Donne tous les produits de la base de données.
	 * 
	 * @return l'ensemble des produits présents dans le stock.
	 *
	 * @throws BDDException exception d'accès à la base de données.
	 *
	 */
	public ArrayList<Produit> donneTousProduits() throws BDDException;

}

