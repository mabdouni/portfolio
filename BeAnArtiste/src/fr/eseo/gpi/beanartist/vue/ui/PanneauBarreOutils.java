package fr.eseo.gpi.beanartist.vue.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import fr.eseo.gpi.beanartist.controleur.actions.ActionEffacer;
import fr.eseo.gpi.beanartist.controleur.actions.ActionForme;

public class PanneauBarreOutils extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton effacer;
	ButtonGroup lesButtons;
	public PanneauBarreOutils(){
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.gray);
		initComponents();
	}
	private void initComponents() {
		effacer = new JButton(new ActionEffacer());
		JToggleButton rectangle = new JToggleButton(new ActionForme(ActionForme.NOM_ACTION_RECTANGLE)),
		carre = new JToggleButton(new ActionForme(ActionForme.NOM_ACTION_CARRE)),
		ellipse = new JToggleButton(new ActionForme(ActionForme.NOM_ACTION_ELLIPSE)),
		cercle = new JToggleButton(new ActionForme(ActionForme.NOM_ACTION_CERCLE)),
		ligne = new JToggleButton(new ActionForme(ActionForme.NOM_ACTION_LIGNE));

		ButtonGroup lesButtons = new ButtonGroup();
		add(effacer,BorderLayout.NORTH);
		lesButtons.add(rectangle);
		lesButtons.add(carre);
		lesButtons.add(ellipse);
		lesButtons.add(cercle);
		lesButtons.add(ligne);
	
		add(rectangle);
		add(carre);
		add(ellipse);
		add(cercle);
		add(ligne);
	}
}
