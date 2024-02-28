package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		int trouverEtalLibre() {
			int etalLibre = -1;
			int i = 0;
			while (this.etals[i].isEtalOccupe() && (i < this.etals.length)) {
				i++;
			}
			if (!this.etals[i].isEtalOccupe()) {
				etalLibre = i;
			}
			
			return etalLibre;
		}
		
		Etal[] trouverEtals(String produit) {
			Etal[] etalsContenantProduit = new Etal[this.etals.length];
			int indiceEtalsInter = 0;
			for (int i = 0; i < this.etals.length; i++) {
				if (this.etals[i].contientProduit(produit)) {
					etalsContenantProduit[indiceEtalsInter++] = this.etals[i];
				}
			}
			return etalsContenantProduit;
		}
		
		Etal trouverVendeur(Gaulois gaulois) {
			int i = 0;
			Etal vendeurAEtal = null;
			while ((i < this.etals.length) && (!this.etals[i].getVendeur().equals(gaulois))) {
				i++;
			}
			if (this.etals[i].getVendeur().equals(gaulois)) {
				vendeurAEtal = this.etals[i];
			}
			return vendeurAEtal;
		}
		
		String afficherMarche() {
			StringBuilder etatMarche = new StringBuilder();
			int nbEtalVide = 0;
			for (int i = 0; i < this.etals.length; i++) {
				if (this.etals[i].isEtalOccupe()) {
					etatMarche.append(this.etals[i].afficherEtal());
				} else {
					++nbEtalVide;
				}
			}
			etatMarche.append("Il reste ");
			etatMarche.append(nbEtalVide);
			etatMarche.append(" étals non utilisés dans le marché.%n");
			
			return etatMarche.toString();
		}
	}

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}