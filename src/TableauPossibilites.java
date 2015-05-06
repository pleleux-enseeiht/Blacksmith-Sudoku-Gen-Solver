/** La classe TableauPossibilites correspond aux différentes possibilités du
 * sudoku : un tableau de 9 valeurs par cases où un 1 correspond à une possi-
 * bilité et un 0 une impossibilité
 * @author      Philippe Leleux
 * @version     1.0
 */

public class TableauPossibilites {

	int[][][] tableau;	//Le tableau des possibilités

	/** Constructeur de TableauPossibilites a partir de rien
	 */	
	public TableauPossibilites() {
		tableau = new int[9][9][9];
	}	
	
	/** Constructeur de TableauPossibilites a partir d'une grille de sudoku
	 * @param sudoku la grille de sudoku dont on veut initialiser le tableau
	 */
	public TableauPossibilites(Sudoku sudoku) {
		this.tableau = new int[9][9][9];
		this.initialiser(sudoku);
	}
	
	/** Initialise un tableau de possibilités à partir d'un sudoku : un 1 
	 * correspond à une possibilité et un 0 à une impossibilité
	 * @param sudoku la grille de sudoku dont on veut initialiser le tableau
	 */
	public void initialiser (Sudoku sudoku) {
		int k;      //parcours des nombres
		int i, j;   //parcours des lignes et colonnes
		for(k=0; k<9; ++k) {
			for(i=0; i<9; ++i) {
				for(j=0; j<9; ++j) {
					if(sudoku.grille[i][j]!=0) {
						//case pleine => impossibilite
						tableau[i][j][k] = 0;
					} else {
						if(sudoku.valeurPossible(i, j, k+1)) {
							//possibilites
							tableau[i][j][k] = 1;
						} else {
							//impossibilites
							tableau[i][j][k] = 0;						
						}
					}
				}
			}
		}
	}
	
}
