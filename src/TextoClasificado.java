import java.util.HashMap;

public class TextoClasificado {
	private String nombre;
	/**
	 * String: nombre del glosario (tem�tica)
	 * Integer: puntuaci�n
	 */
	private HashMap<String, Integer> ranking;
	
	public TextoClasificado(String nombre, HashMap<String, Integer> ranking){
		this.nombre = nombre;
		this.ranking = ranking;
	}

	public String getNombre() {
		return nombre;
	}

	public HashMap<String, Integer> getRanking() {
		return ranking;
	}
}
