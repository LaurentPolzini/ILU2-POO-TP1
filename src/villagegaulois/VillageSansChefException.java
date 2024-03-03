package villagegaulois;

public class VillageSansChefException extends Exception{
	
	VillageSansChefException() {
		System.out.println("Il faut un chef au village !\n");
	}
}
