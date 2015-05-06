/** Cette classe implante l'exception correspondante à une grille invalide :
 * une case vide mais sans aucune possibilité.
 * @author      Philippe Leleux
 * @version     1.0
 */

public class GrillePasValide extends Throwable {

	private static final long serialVersionUID = 1L;
	String message;

	/** Construit une exception ChoixEntreeInvalide avec le constructeur
	 * de la super classe et en affichant la grille, la position invalide
	 * et son vecteur de possibilités
	 * @param i numéro de ligne de la case
	 * @param j numéro de colonne de la case
	 * @param tableauij vecteur de possibilités de la case
	 * @param sudoku le Sudoku invalide
	 */
	public GrillePasValide(int i, int j, int[] tableauij, Sudoku sudoku) {
		super();
		message = sudoku.toString();
		message += "(" + i + ", " + j + ")\n";
		for(int k=0; k<9; ++k) {
			message += tableauij[k] + "  ";
		}
	}	
	
}
