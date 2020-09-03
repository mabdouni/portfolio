package morix.bdd;

/**
 * Classe d'exception d'accès à la base de données.
 *
 * @version 5.3
 * @author Matthias Brun
 *
 * @see java.lang.Exception
 */
public class BDDException extends Exception
{

	/**
	 * UID auto-généré.
	 */
	private static final long serialVersionUID = -2452508615653386904L;

	/**
	 * Constructeur d'exception d'accès à la base de donnée.
	 *
	 */
	public BDDException()
	{
		super();
	}
	
	/**
	 * Constructeur d'exception d'accès à la base de donnée.
	 *
	 * @param message le message de l'exception.
	 */
	public BDDException(String message)
	{
		super(message);
	}
}
