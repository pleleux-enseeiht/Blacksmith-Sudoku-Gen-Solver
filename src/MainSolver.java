import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/** Classe de test de génération de sudoku par Permutations Aleatoires et Recuit Simulé
 * Pour lancer l'application : java MainSolver [fichier]
 * Paramètres : fichier le chemin relatif du fichier à utiliser
 * @author      Philippe Leleux
 * @version     1.0
 */
import java.io.File;

public class MainSolver {

	static String save = null;

	public static void main(String[] args) {
		try {
			long t0;
			long t1;
			Sudoku sudoku = new Sudoku();
			boolean in_option = false;
			for (int index = 0; index < args.length; ++index) {
				if (!in_option) {
					in_option = true;
					if (args[index].equals("-g") || args[index].equals("--grid")) {
						sudoku = new Sudoku(args[index + 1]);
					} else if (args[index].equals("-h") || args[index].equals("--help")) {
							throw new Exception();
					} else if (args[index].equals("-s") || args[index].equals("--save")) {
						MainSolver.save = args[index + 1];
					} else {
						throw new Exception();
					}
				} else {
					in_option = false;
				}
			}

			System.out.println("La grille de départ est : \n" + sudoku);
			RecuitSimule recuit = new RecuitSimule(sudoku);
			t0=System.currentTimeMillis();
			System.out.println("Résolution en cours...");
			sudoku = recuit.solver(sudoku);
			t1=System.currentTimeMillis();
			System.out.println("Résultat : \n" + sudoku);
			System.out.println("Durée d'exécution : "+(t1-t0)+"ms");

			if (save != null) {
				MainSolver.toFile(sudoku, save);
			}
		} catch (Exception e) {
			System.out.println("Argument or file error:");
			MainSolver.print_help();
		}
	}

	private static void print_help() {
		System.out.println("Sudoku grid solver");
		System.out.println("java MainSolver [options]");
		System.out.println("options description:");
		System.out.println(" -h/--help: display this help");
		System.out.println(" -g/--grid filename: will solve the grid contained in the file. Default: empty grid");
		System.out.println(" -s/--save filename: save the generated grid to the file (replaces the content if the file already exists). Default: don't save");
	}

	//author mkyong
	//origin http://www.mkyong.com/java/how-to-write-to-file-in-java-bufferedwriter-example/
	public static void toFile(Sudoku sudoku, String filename) throws Exception {
		String content = sudoku.toString();

		File file = new File(filename);

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
	}
}
