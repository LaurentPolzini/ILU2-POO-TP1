package histoire;

import personnages.*;
import villagegaulois.*;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		
		Etal etal = new Etal();
		
		//etal.occuperEtal(bonemine, "fleurs", 5);
		
		try {
			etal.acheterProduit(0, bonemine);
		} catch (IllegalArgumentException | IllegalStateException e) {
			e.printStackTrace();
		}
		
		Village village = new Village("village des gaulios", 5, 3);
		
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			e.printStackTrace();
		}
		
		
		
		System.out.println("Fin du test");
	}
	
}
