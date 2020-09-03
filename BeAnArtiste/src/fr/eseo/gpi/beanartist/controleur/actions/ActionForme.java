package fr.eseo.gpi.beanartist.controleur.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.eseo.gpi.beanartist.controleur.outils.OutilCarre;
import fr.eseo.gpi.beanartist.controleur.outils.OutilCercle;
import fr.eseo.gpi.beanartist.controleur.outils.OutilEllipse;
import fr.eseo.gpi.beanartist.controleur.outils.OutilLigne;
import fr.eseo.gpi.beanartist.controleur.outils.OutilRectangle;
import fr.eseo.gpi.beanartist.vue.ui.FenetreBeAnArtist;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class ActionForme extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NOM_ACTION_RECTANGLE = "Rectangle";
	public static final String NOM_ACTION_CARRE = "Carré";
	public static final String NOM_ACTION_CERCLE = "Cercle";
	public static final String NOM_ACTION_LIGNE = "Ligne";
	public static final String NOM_ACTION_ELLIPSE = "Ellipse";

	
	public ActionForme(String nomAction) {
		super(nomAction);
	}
	public void actionPerformed(ActionEvent event) {
			PanneauDessin panneau = FenetreBeAnArtist.getInstance().getPanneauDessin();
			if(event.getActionCommand().equals(NOM_ACTION_RECTANGLE))
				new OutilRectangle(panneau).associer(panneau);
			else if(event.getActionCommand().equals(NOM_ACTION_CARRE)) 
				new OutilCarre(panneau).associer(panneau);
			else if(event.getActionCommand().equals(NOM_ACTION_CERCLE))
				new OutilCercle(panneau).associer(panneau);
			else if(event.getActionCommand().equals(NOM_ACTION_LIGNE)) 
				new OutilLigne(panneau).associer(panneau);
			else if(event.getActionCommand().equals(NOM_ACTION_ELLIPSE)) 
				new OutilEllipse(panneau).associer(panneau);
	}
}
