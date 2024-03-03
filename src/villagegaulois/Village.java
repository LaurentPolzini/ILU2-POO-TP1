package villagegaulois;

import java.util.ArrayList;
import java.util.List;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}
	
	private static class Marche {
		private Etal[] etals;
		
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			
			// a ce stade j'ai un tableau avec nbEtal cases vides (null). J'y met maintenant mes etaux.
			for (int i = 0; i < this.etals.length; i++) {
				this.etals[i] = new Etal();
			}
		}
		
		public void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		/**
		 * Retourne l'index du premier etalage innocupé et -1 si tous occupés.
		 */
		public int trouverEtalLibre() {
			int iEtalLibre = -1;
			
			for (int i = 0; iEtalLibre < 0 && i < this.etals.length; i++) {
				if (!this.etals[i].isEtalOccupe()) {
					iEtalLibre = i;
				}
			}
			
			return iEtalLibre;
		}
		
		public List<Etal> trouverEtals(String produit) {
			List<Etal> etals = new ArrayList<Etal>();
			
			for (int i = 0; i < this.etals.length; i++) {
				if (this.etals[i].isEtalOccupe() && this.etals[i].contientProduit(produit)) {
					etals.add(this.etals[i]);
				}
			}
			return etals;
		}
		
		public Etal trouverVendeur(Gaulois gaulois) {
			int i = 0;
			Etal vendeurAEtal = null;
			for (;(i < this.etals.length) && (!this.etals[i].getVendeur().equals(gaulois)); i++);
			if (this.etals[i].getVendeur().equals(gaulois)) {
				vendeurAEtal = this.etals[i];
			}
			return vendeurAEtal;
		}
		
		public String afficherMarche() {
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
			etatMarche.append(" étals non utilisés dans le marché.\n");
			
			return etatMarche.toString();
		}
	}
	
	/**
	 * Positionne un vendeur dans le prmeier étalage inncuopé du marché.
	 * 
	 * @param vendeur
	 * @param produit
	 * @param nbProduit
	 * 
	 * @return "Pas de place au marché" ou "Le vendeur [name] s'est installé à l'étalage n°[num]"
	 */
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		String szMsg = "Pas de place au marché pour le vendeur " + vendeur.getNom();
		
		int indiceEtalLibre = this.marche.trouverEtalLibre();
		
		if (indiceEtalLibre != -1) {
			this.marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
			szMsg = "Le vendeur " + vendeur.getNom() + " s'est installé à l'étalage n°" + (indiceEtalLibre + 1);
		}
		
		return szMsg;
	}
	
	// 
	public String rechercherVendeursProduit(String produit) {
		String szMsg = "Il n'y a pas de vendeur qui propose des " + produit + " au marché.";
		
		List<Etal> etalsDontProd = this.marche.trouverEtals(produit);
		
		for (int i = 0; i < etalsDontProd.size(); i++) {
			if (i == 0) {
				szMsg = "Les vendeurs qui proposent des fleurs sont : \n";
			}
			szMsg += "- " + etalsDontProd.get(i).getVendeur().getNom() + "\n";
		}
		
		return szMsg;
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		return this.marche.afficherMarche();
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

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if (this.chef == null) {
			throw new VillageSansChefException();
		} else {
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
		}
		return chaine.toString();
	}
}