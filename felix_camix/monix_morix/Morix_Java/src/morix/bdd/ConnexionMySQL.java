package morix.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import morix.stock.Produit;

/**
 * Classe d'accès à la base de donnée MySQL. 
 * 
 * @version 5.3
 * @author Matthias Brun
 * 
 * @see morix.bdd.ConnexionBDD
 */
public class ConnexionMySQL implements ConnexionBDD
{
	/**
	 * Connexion à la base de données.
	 */
	private Connection connexion;

	/**
	 * Requête d'ajout de produit.
	 */
	private PreparedStatement requeteAjoutProduit;

	/**
	 * Requête de suppression d'une quantité de produit.
	 */
	private PreparedStatement requeteChangeQuantiteProduit;

	/**
	 * Requête de récupération d'information sur un produit.
	 */
	private PreparedStatement requeteDonneProduit;

	/**
	 * Requête de récupération des informations de tous les produits.
	 */
	private PreparedStatement requeteDonneTousProduits;

	/**
	 * Préparation des requêtes (pour optimiser les accès à la base de données).
	 *
	 * @throws SQLException exception d'accès à la base de données.
	 *
	 */
	private void prepareRequetes() throws SQLException
	{
		String requeteSQL; 

		requeteSQL = "INSERT INTO Produit VALUE(?, ?, ?, ?)";
		this.requeteAjoutProduit = this.connexion.prepareStatement(requeteSQL);

		requeteSQL = "UPDATE Produit SET quantite = quantite + ? WHERE id = ?";
		this.requeteChangeQuantiteProduit = this.connexion.prepareStatement(requeteSQL);

		requeteSQL = "SELECT * FROM Produit WHERE id = ?";
		this.requeteDonneProduit = this.connexion.prepareStatement(requeteSQL);

		requeteSQL = "SELECT * FROM Produit ORDER BY id";
		this.requeteDonneTousProduits = this.connexion.prepareStatement(requeteSQL);
	}


	/**
	 * Constructeur d'une connexion MySQL.
	 *	
	 * @param url l'URL de la base de données.
	 * @param user l'utilisateur de la base de données.
	 * @param password le mot de passe de l'utilisateur de la base de données.
	 *
	 * @throws BDDException exception d'accès à la base de donnée.
	 *
	 */ 
	public ConnexionMySQL(String url, String user, String password) throws BDDException
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException ex) {
			System.err.println("Problème d'accès au driver MySQL JDBC.");
			throw new BDDException(ex.getMessage());
		}
		try {
			this.connexion = DriverManager.getConnection(url, user, password);
		} 
		catch (SQLException ex) {
			System.err.println("Problème de connexion à MySQL.");
			this.ferme();
			throw new BDDException(ex.getMessage());
		}
		try {
			this.prepareRequetes();
		}
		catch (SQLException ex) {
			System.err.println("Problème de préparation des requêtes SQL.");
			this.ferme();
			throw new BDDException(ex.getMessage());
		}
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.bdd.ConnexionBDD
	 */
	@Override
	public Connection donneConnexion()
	{
		return this.connexion;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.bdd.ConnexionBDD
	 */
	@Override
	final public void ferme() throws BDDException
	{
		try {
			if (this.connexion != null) {
				this.connexion.close();
			}
		}
		catch (SQLException ex) {
			System.err.println("Problème de déconnexion de MySQL.");
			throw new BDDException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.bdd.ConnexionBDD
	 */
	@Override
	synchronized public void ajouteProduit(String id, String libelle, Double prix, Integer quantite) throws BDDException
	{
		try {
			Integer index = 1;
			
			this.requeteAjoutProduit.setString(index++, id);
			this.requeteAjoutProduit.setString(index++, libelle);
			this.requeteAjoutProduit.setDouble(index++, prix);
			this.requeteAjoutProduit.setInt(index++, quantite);
			this.requeteAjoutProduit.executeUpdate();
		}
		catch (SQLException ex) {
			System.err.println("Problème d'ajout d'un produit dans MySQL.");
			throw new BDDException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.bdd.ConnexionBDD
	 */
	@Override
	synchronized public void changeQuantiteProduit(String id, Integer quantite) throws BDDException
	{
		try {
			this.requeteChangeQuantiteProduit.setInt(1, quantite);
			this.requeteChangeQuantiteProduit.setString(2, id);
			this.requeteChangeQuantiteProduit.executeUpdate();
		}
		catch (SQLException ex)	{
			System.err.println("Problème de modification de quantité d'un produit dans MySQL.");
			throw new BDDException(ex.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.bdd.ConnexionBDD
	 */
	@Override
	synchronized public Produit donneProduit(String id) throws BDDException
	{
		ResultSet resultat = null;
		Produit produit = null;

		try {
			this.requeteDonneProduit.setString(1, id);
			resultat = this.requeteDonneProduit.executeQuery();
		
			if (resultat.next()) {
				Integer index = 1;
				
				produit = new Produit(
					resultat.getString(index++),	// identifiant
					resultat.getString(index++),	// libellé
					resultat.getDouble(index++),	// prix
					resultat.getInt(index++)		// quantité
				);
			}
		}
		catch (SQLException ex) {
			System.err.println("Problème d'accès à un produit dans MySQL.");
			throw new BDDException(ex.getMessage());
		}
		finally {
			if (resultat != null) {
				try {
					resultat.close();
				} 
				catch (SQLException e) {
					System.err.println("Problème de fermeture de résultats issus de MySQL.");
					throw new BDDException(e.getMessage());
				}
			}
		}

		return produit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see morix.bdd.ConnexionBDD
	 */
	@Override
	synchronized public ArrayList<Produit> donneTousProduits() throws BDDException
	{
		ResultSet resultat = null;
		final ArrayList<Produit> produits = new ArrayList<Produit>();

		try {
			resultat = this.requeteDonneTousProduits.executeQuery();

			while (resultat.next())	{
				Integer index = 1;
				
				produits.add(
					new Produit(
						resultat.getString(index++),	// identifiant
						resultat.getString(index++),	// libellé
						resultat.getDouble(index++),	// prix
						resultat.getInt(index++)		// quantité
					)
				);
			}
		}
		catch (SQLException ex) {
			System.err.println("Problème d'accès aux produits dans MySQL.");
			throw new BDDException(ex.getMessage());
		}
		finally {
			if (resultat != null) {
				try {
					resultat.close();
				} 
				catch (SQLException e) {
					System.err.println("Problème de fermeture de résultats issus de MySQL.");
					throw new BDDException(e.getMessage());
				}
			}
		}

		return produits;
	}
}

