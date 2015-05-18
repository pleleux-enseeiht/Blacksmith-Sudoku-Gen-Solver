import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/** Classe de test de vidage de sudoku par Recuit Simulé et backtracking
 * Pour lancer l'application : java MainGen ([File])
 * Paramètres : température initiale du recuit simulé
 *              température finale du recuit simulé
 *              le pas de décrémentation de l'algorithme
 * @author      Philippe Leleux
 * @version     1.0
 */ 

public class MainGen {

	static int verbose = 0;
	static String save = null;
	static double Ti = 1;
	static double Tf = 0.15;
	static double pas = 0.0002;

	public static void main(String[] args) {
		try {
			Sudoku temp = new Sudoku();
			RecuitSimule recuit = new RecuitSimule();
			recuit.sudoku.genererSudoku();
			boolean in_option = false;
			for (int index = 0; index < args.length; ++index) {
				if (!in_option) {
					in_option = true;
					if (args[index].equals("-g") || args[index].equals("--grid")) {
						recuit = new RecuitSimule(new Sudoku(args[index + 1]));
						recuit.sudoku = recuit.solver(recuit.sudoku);
					} else if (args[index].equals("-c") || args[index].equals("--config")) {
						MainGen.save_config(args[index + 1]);
					} else if (args[index].equals("-h") || args[index].equals("--help")) {
						throw new Exception();
					} else if (args[index].equals("-v") || args[index].equals("--verbose")) {
						MainGen.verbose = 1;
					} else if (args[index].equals("-s") || args[index].equals("--save")) {
						MainGen.save = args[index + 1];
					} else {
						throw new Exception();
					}
				} else {
					in_option = false;
				}
			}

			System.out.println("La grille de départ est : \n" + recuit.sudoku);
			long t0=System.currentTimeMillis();
			
			System.out.println("Vidage de la grille par recuit simulé en cours...");
			recuit.recuitSimule(verbose, Ti, Tf, pas);
			long t1=System.currentTimeMillis();
			System.out.println("Durée d'exécution : "+(t1-t0)+"ms");
			
			long nbOperations = (long) recuit.sudoku.nombreCasesPleines();
			nbOperations = ((long) Math.pow(2, nbOperations)) - 1;

			if (save != null) {
				MainGen.toFile(recuit.getSudoku(), save);
			}
		} catch (GrillePasValide e) {
			System.out.println("La grille n'est pas valide");
		} catch (Exception e) {
			System.out.println("Argument or file error:");
			MainGen.print_help();
		}
	}
	
	private static void save_config(String config_file) throws Exception {
		String[] lines;
		String line;             //parcours par ligne du fichier
		String name;
		String input;
		BufferedReader in  = new BufferedReader(new FileReader(config_file));
		while ((line = in.readLine()) != null) {
			lines = line.split("\t");
			name = lines[0];
			input = lines[1];
			if (name.toUpperCase().equals("TI")) {
				MainGen.Ti = Double.parseDouble(input);
			} else if (name.toUpperCase().equals("TF")) {
				MainGen.Tf = Double.parseDouble(input);
			} else if (name.toUpperCase().equals("PAS")) {
				MainGen.pas = Double.parseDouble(input);
			}
		}
		in.close();
	}

	private static void print_help() {
		System.out.println("Sudoku grid generator");
		System.out.println("java MainGen [options]");
		System.out.println("options description:");
		System.out.println(" -h/--help: display this help");
		System.out.println(" -c/--config filename: take the parameters in the tab-delimited config file to compute the grid. The parameters (default value) are: Ti (1), Tf(0.15), pas(0.0002)");
		System.out.println(" -g/--grid filename: take the grid contained in the file as basis to generate the grid. Default: empty grid");
		System.out.println(" -s/--save filename: save the generated grid to the file (replaces the content if the file already exists). Default: don't save");
		System.out.println(" -v/--verbose: display supplementary information. Default: quiet");
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
