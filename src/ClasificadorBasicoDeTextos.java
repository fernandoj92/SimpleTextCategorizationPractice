import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class ClasificadorBasicoDeTextos {
	
	private Path testFilesPath;
	private Path glosariosPath;
	private List<Glosario> listaGlosarios = new ArrayList<Glosario>();
	private List<TextoClasificado> textosClasificados = new ArrayList<TextoClasificado>();
	
	
	public ClasificadorBasicoDeTextos(Path glosariosPath, Path textFilesPath){
		this.testFilesPath = textFilesPath;
		this.glosariosPath = glosariosPath;
	}

	public List<TextoClasificado> clasificar() throws IOException{

		//Cargamos los glosarios
		cargarGlosarios();
		// Iteramos por cada uno de los archivos del directorio "Test files"
		// y clasificamos dichos archivos según la temática de los glosarios 
		clasificarTextos();
		
		return textosClasificados;
	}
	
	private void cargarGlosarios() throws IOException{

		Stream<Path> paths = Files.walk(glosariosPath);
		paths.filter(path -> 
			path.toString().toLowerCase().endsWith(".txt"))
				.forEach(path -> listaGlosarios.add(new Glosario(path)));
		paths.close();
	}
	
	private void clasificarTextos() throws IOException{
		
		Stream<Path> paths = Files.walk(testFilesPath);
		paths.filter(path -> 
			path.toString().toLowerCase().endsWith(".txt"))
				.forEach(path -> clasificarTexto(path));
		paths.close();
	}
	
	private void clasificarTexto(Path path){
		HashMap<String, Integer> ranking = new HashMap<String, Integer>();
		String fileName = path.toString().substring(path.toString().lastIndexOf("\\")+1);
		
		listaGlosarios.forEach(glosario ->
			ranking.put(glosario.getNombre(), glosario.puntuarTexto(path)));
		
		textosClasificados.add(new TextoClasificado(fileName,ranking));
	}
}