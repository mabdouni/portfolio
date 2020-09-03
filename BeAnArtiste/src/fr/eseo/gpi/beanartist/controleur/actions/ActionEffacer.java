package fr.eseo.gpi.beanartist.controleur.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import fr.eseo.gpi.beanartist.vue.formes.VueForme;
import fr.eseo.gpi.beanartist.vue.ui.FenetreBeAnArtist;

public class ActionEffacer extends AbstractAction {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NOM_ACTION = "Effacer";
	
	public ActionEffacer() {
		super(NOM_ACTION);
	}

	public void actionPerformed(ActionEvent event) {
		int reponse = JOptionPane.showConfirmDialog(FenetreBeAnArtist.getInstance(), " vous êtes sur ?", NOM_ACTION,
				JOptionPane.YES_NO_OPTION);
		if (reponse == JOptionPane.YES_OPTION) {
			FenetreBeAnArtist.getInstance().getPanneauDessin().setVueFormes(new ArrayList<VueForme>());
			FenetreBeAnArtist.getInstance().getPanneauDessin().repaint();
		}
	}
}
