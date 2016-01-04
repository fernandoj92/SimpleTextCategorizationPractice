import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Pattern;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

public class Glosario {
	
	private String nombre;
	/**
	 * String: término
	 * Integer: número de repeticiones (valor para puntuación)
	 */
	private HashMap<String, Integer> contenido;
	
	public String getNombre() {
		return nombre;
	}

	public HashMap<String, Integer> getContenido() {
		return contenido;
	}
	
	public Glosario(Path path){
		crearGlosario(path);
	}

	private void crearGlosario(Path path){
	
		String completeFileName = path.toString().substring(path.toString().lastIndexOf("\\")+1);
		HashMap<String, Integer> contenido = new HashMap<String, Integer>();
		//Analizamos el archivo de glosario y rellenamos el hashmap
		try{
			Files.lines(path).forEach(line ->
			{
				String[] fila = line.split(";");
				contenido.put(fila[0], Integer.parseInt(fila[1]));
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		String[] fileName = completeFileName.split(Pattern.quote("."));
		this.nombre = fileName[0];
		this.contenido = contenido;
	}
	//Tokenizo las palabras de la fila o del archivo en general y prueba cada una de ellas con el glosario, 
	//si la palabra coincide le asigno la puntuación asociada
	public int puntuarTexto(Path path){
		
		int puntuacion = 0;
		PTBTokenizer<CoreLabel> ptbt;
		
		try {
			ptbt = new PTBTokenizer<>(new FileReader(path.toString()), new CoreLabelTokenFactory(), "");
			while (ptbt.hasNext()) {
				CoreLabel label = ptbt.next();
				puntuacion += contenido.getOrDefault(label.toString().toLowerCase(), 0);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return puntuacion;
	}
}
