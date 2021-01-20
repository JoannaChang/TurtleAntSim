# TurtleAntSim

## About The Project

This is an agent-based model to study arboreal ant nest choice under spatial constraints. It simulates the movement and cavity occupation of  an  ant  colony within a constrained  space. The space (or arena) may have different structural features based on the spatial arrangement of the cavities and the bridges between them. This model is designed to explore the effects of structural constraints on turtle ant cavity occupation when the ant agents act under simple rules of movement driven by diffusion and pheromone feedback loops on trails and at nests, excluding preference or memory. 

## Getting Started

### Prerequisites

* Java: download [here](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html). The model was tested with Java 8, but should be able to work with other versions.

### Installation

1. Clone the repo
```sh
git clone https://github.com/JoannaChang/TurtleAntSim.git
```

### Model setup
The parameters of the model can be designated using a properties file. Examples are given in the **simulations** folder. The parameters that can be changed are:

| Parameter         | Description                                                                           | Format                | Example |
| :-----:           | :-:                                                                                   | :-:                   | :-:        |
| arena             | arena structure, see below                                                            | see below             | see below |
| bridges           | locations & lengths of bridges                                                        | see below             | see below|
| startRow          | starting location of ants, row                                                        | row                   | 8 |
| startCol          | starting location of ants, column                                                     | column                | 8|
| boxSize           | size of arena boxes                                                                   | size                  | 17|
| nestFile          | file name for cavity occupation output                                                | file.csv              | additional_nest.csv|
| activityFile      | file name for ant activity output                                                     | file.csv              | additional_activity.csv|
| totalSteps        | number of steps each ant takes/simulation                                             | steps                 | 50000 |
| totalSimulations  | number of simulations                                                                 | simulations           | 100 |
| numAnts           | number of ants/simulation                                                             | ants                  | 100 |
| pherStrength      | amount of pheromone deposited by an ant                                               | start, end, increment | 4,8,1|
| maxPher           | maximum pheromone in a cell                                                           | start, end, increment | 550,750,50|
| stayingFactor     | amount of pheromone that decreases the probability an ant will leave a cavity by half | start, end, increment | 40,50,5|
| nestAttrac        | additional attractiveness of a cavity compared to an empty cell                       | value, (opt. values)  | 4,6,8|
| cellAttrac        | intrinsic attractiveness of a cell                                                    | value, (opt. values)  | 12|
| chanceTurn        | probability of turning                                                                | value, (opt. values)  | 0.2|

The structure of the arena can be changed by the user. An arena is made up of a 2D grid of square cells. Each cell can represent either a wall, a cavity, a bridge, or an empty space. The structure (the arrangement of the square cells) can be designated within the main method of the **src/ArenaArray.java** file. The arena can be wholly customized (i.e. specify the location of each cell and cell type in the arena) with the "custom" methods, or it can be made based on a box grid (e.g. in our laboratory experiments) with the "box" methods. 

After editting the **ArenaArray.java** file, run
```sh
javac -d bin -sourcepath src src/ants/ArenaArray.java
java -classpath bin ants.ArenaArray
```
from the cloned directory to print out three lines designating
1. the arena structure ("arena" parameter)
2. the locations and length of each bridge ("bridges" parameter)
3. starting location of the ants ("startRow" and "startCol" parameters)

Copy the output to the properies file. 

In the **src/ants/SimController.java** file, designate the properties file by editting the line
```sh
input = new FileInputStream("simulations/file_name.properties");
```

## Simulation runs
Run the simulations from the cloned directory using
```sh
javac -d bin -cp lib/commons-csv-1.8.jar -sourcepath src src/ants/SimController.java
java -cp bin:lib/commons-csv-1.8.jar ants.SimController  
```
A window should pop up showing the movement of the ants in each simulation. At the end of all simulations, two csv files will be outputted describing:
1. cavity occupation: number of ants in each cavity at the end of each simulation
2. ant activity: bridge crossings, entries into cavities, and exits from cavities during each simulation


## Past simulations
The parameters for the simulations run for our paper are included in the **simulations** folder. The file names take the format: (type of setup)_(varied parameters). 

The type of set-up can be 
* "limited": limited-cavity setup or 
* "additional": additional-cavity setup

The varied parameters can be
* blank: standard model parameters are used, as defined in the manuscript
* "vary_pher": pheromone parameters (pheromone strength, max pheromone, and staying factor) were varied
* "vary_attrac": attraction parameters (cell attractiveness and cavity attractiveness) were varied
* "vary_chanceTurn": probability of turning parameter was varied 
