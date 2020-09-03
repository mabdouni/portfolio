package fr.eseo.gpi.beanartist.controleur.outils;

import fr.eseo.gpi.beanartist.modele.formes.Ligne;
import fr.eseo.gpi.beanartist.vue.formes.VueForme;
import fr.eseo.gpi.beanartist.vue.formes.VueLigne;
import fr.eseo.gpi.beanartist.vue.ui.PanneauDessin;

public class OutilLigne extends OutilForme{

	public OutilLigne(PanneauDessin panneauDessin) {
		super(panneauDessin);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected VueForme creerVueForme() {
		if(getDebut().getX()==getFin().getX() && getDebut().getY()== getFin().getY()) {
			return new VueLigne(new Ligne(getDebut()));
		}
		return new VueLigne(new Ligne(getDebut(),
				getFin().getX()-getDebut().getX(),getFin().getY()-getDebut().getY()));
		}
}
