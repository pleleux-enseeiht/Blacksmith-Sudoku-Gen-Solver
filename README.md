# Blacksmith-Sudoku-Gen-Solver
Sudoku generator using Simulated-Annealing and solver in Java

## Presentation
Develoment of a little library for Sudoku games with two purposes:
* Sudoku solver using **Backtracking**,
* generator of Sudoku games using simulated annealing.

#### What's a Sudoku game ?
[...this:](http://www.conceptispuzzles.com/?uri=puzzle/sudoku/rules)

![Sudoku grid](http://www.conceptispuzzles.com/picture/11/1354.gif "Sudoku grid")

#### Launch
**Sudoku generator**
*java MainGen [options]*

options description:
* -h/--help: display this help
* -c/--config filename: take the parameters in the tab-delimited config file to compute the grid. The parameters (default value) are:
  * Ti (1),
  * Tf(0.15),
  * pas(0.0002).
* -g/--grid filename: take the grid contained in the file as basis to generate the grid. Default: empty grid
* -s/--save filename: save the generated grid to the file (replaces the content if the file already exists). Default: don't save
* -v/--verbose: display supplementary information. Default: quiet

**Sudoku Solver**
*java MainSolver [options]*

options description:
* -h/--help: display this help
* -g/--grid filename: will solve the grid contained in the file. Default: empty grid
* -s/--save filename: save the generated grid to the file (replaces the content if the file already exists). Default: don't save

**grid file** (examples in grids directory)
```
000000010
400000000
020000000
000050407
008000300
001090000
300400200
050100000
000806000
```

**config file** (example in contig_file)
```
ti      1
tf      0.15
pas     0.0002
```

#### Tests
**Sudoku generator**
*java TestGen [Ti] [Tf] [pas] [grid_file]*

**Sudoku Solver**
*java TestSolver [grid_file]*

For details on the library, consult javadoc.

## Details
#### Sudoku games generator with simulated annealing
The principle of **simulated annealing** is based on metallurgical techniques: to cool down a metal, you must bring a little heat from time to time to minimize its energy in order to make it as solid as possible. In practical, with an optimisation (minimisation) problem, this method allows not to converge to a **local minimum** too fast. By "crossing over a pass", i.e. going through worst case with respect to the goal, we widen the search space to look for the **global minimum**.

The initial step is chosen among the solutions space with an initial energy and temperature. The goal is to decrease the energy while decreasing the temperature. Until we get to a specified final temperature, at each step we choose randomly a case:
* if filled we try to empty the case:
  * if the grid still has a unique solution
  * else the case is let filled,
* if empty, we pick randomly a number between 0 and 1:
  * if it is inferior to exp(-1/T), we fill it with the number in the solution (initial step),
  * else it remains empty.

The better compromise between speed and performance was observed with the default config parameter.

####Sudoku solving with backtracking
The backtracking principle is to go through all solutions possible and stop when we get one.
