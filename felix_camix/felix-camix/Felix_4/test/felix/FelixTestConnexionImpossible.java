package felix;

import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.netbeans.jemmy.ClassReference;
import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.JTextPaneOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

import felix.Felix;


@RunWith (Parameterized.class)
public class FelixTestConnexionImpossible
{	
	private static ClassReference application;
	private static JFrameOperator fenetreConnexion;	
	private static JButtonOperator buttonConnecter;
	private static JTextFieldOperator texteIP, textePort, texteInformation; 
	
	private String ip;
	private String port;
	private String message;
	
	
	public FelixTestConnexionImpossible(String ip, String port, String message )
	{
		this.ip = ip;
		this.port = port;
		this.message = message;
	}
	@Parameters
	 public static List<Object[]> getParametres() {
	        return Arrays.asList(
	        		new Object[][] {{"167.238.50.129"},{ } });
	 }
	
	 
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		
		final Integer timeout = 3000;
		JemmyProperties.setCurrentTimeout("FrameWaiter.WaitFrameTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitComponentTimeout", timeout);
		JemmyProperties.setCurrentTimeout("ComponentOperator.WaitStateTimeout", timeout);
		
	
		try {
			new ClassReference("felix.Felix").startApplication();
		}
		catch (ClassNotFoundException e) {
			Assert.fail("Problème d'accès à la classe invoquée : " + e.getMessage());
			throw e;
		}
		
		accesVueConnexion();
	}	
	
	private static void accesVueConnexion() {
		try {
			fenetreConnexion = new JFrameOperator(Felix.IHM.getString("FENETRE_CONNEXION_TITRE"));
		}
		catch (TimeoutExpiredException e){
			Assert.fail("la fenêtre de la communication n'est pas accessible: "+ e.getMessage());
		}
		try {
			texteIP = new JTextFieldOperator(fenetreConnexion,new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_IP")));
			
			textePort = new JTextFieldOperator(fenetreConnexion,new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_PORT")));
			
			texteInformation = new JTextFieldOperator(fenetreConnexion,new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_INFORMATION")));
			
			buttonConnecter = new JButtonOperator(fenetreConnexion, new NameComponentChooser(Felix.IHM.getString("FENETRE_CONNEXION_NOM_CONNEXION")));
		}
		catch (TimeoutExpiredException e){
			Assert.fail("problème accèe a un composant de la vue connexion "+ e.getMessage());
		}
	}
	
	@Test
	public void entrerIP() throws InterruptedException {
		final Long timeout = Long.valueOf(3000); 
		
		texteIP.clickMouse();
		texteIP.clearText();

		
		texteIP.typeText("192.168.1.1");
		
		Thread.sleep(timeout);
		
		textePort.clickMouse();
		textePort.clearText();
		
		textePort.typeText("2020");
		
		buttonConnecter.clickMouse();
		
		Thread.sleep(timeout);
		
		Assert.assertEquals("Connexion au chat @%s:%s impossible",Felix.IHM.getString("FENETRE_CONNEXION_MESSAGE_CONNEXION_IMPOSSIBLE"));
	}
	
}
	

