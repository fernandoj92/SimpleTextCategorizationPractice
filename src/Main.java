import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class Main {
	
	private final static String testFilesPath = System.getProperty("user.dir") + "\\Test files";
	private final static String glosariosPath = System.getProperty("user.dir") + "\\Glosarios";
	private static List<TextoClasificado> textosClasificados = new ArrayList<TextoClasificado>();
	
	public static void main(String[] args) throws IOException {
		ClasificadorBasicoDeTextos clasificador = 
				new ClasificadorBasicoDeTextos(Paths.get(glosariosPath), Paths.get(testFilesPath));
		//Clasificamos los textos
		textosClasificados = clasificador.clasificar();
		//Mostramos los resultados
		textosClasificados.forEach(texto ->
		{
			String s ="";
			for (Entry<String, Integer> entry : texto.getRanking().entrySet())
				s += entry.getKey() + ":" + entry.getValue()+ " ";
			System.out.println(texto.getNombre() + " = "+s);
		});
	}
}
