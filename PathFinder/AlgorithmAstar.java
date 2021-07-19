///*****************************************************************************
// Class: AlgorithmAstar
// Author: Walter Hernandez
//
// Purpose: The algorirthm of the PathFinder. More specific the A star algorithm 
//
// Attributes: cols,rows, openSet/closedSets/neighbors <Lists> , [][]grid, startPos, endPos

// Methods: constructor, algorithm, finalPath ,distance, getNodes, makeneighborsSet.
// Other methods are just getters to communicate with the GUI class


//*******************************************************************************


import java.util.*;
//import java.util.Deque;

public class AlgorithmAstar{


	private int cols;
	private int rows;

	//private Node[][] grid;

	private List<Node> openSet = new ArrayList();
	private List<Node> closedSet = new ArrayList();
	
	private Node[][] grid;
	private Node startPos;//START AND END POS
	private Node endPos;

	

	private List<Node>neighborsSet = new ArrayList();
	private List<Node>cameFrom = new ArrayList();


	private int timeOfCompletion;

	private boolean noPathFound;




	public AlgorithmAstar(int col, int row){
		cols = col;
		rows = row;

		grid = new Node[cols][rows];
		makeGrid();

	
	}

	
	public void adjustParameter(int col, int row){
		this.cols = col;
		this.rows = row;
	}

	public void makeGrid(){
		for (int i =0 ;i<cols ;i++ ) {
			for (int j = 0;j < rows ;j++ ) {
				grid[i][j] = new Node(i,j);

			}
		
		}

	}



	public void algorithm(Node start, Node target){ //THE MAGIC HAPPENS HERE, ALL THE ALGORITHM 


		Node current = new Node(0,0);

	for (int i = 0 ;i< cols ;i++ ) {
			for (int j = 0;j< rows ;j++ ) {
				grid[i][j].setGValue(Double.POSITIVE_INFINITY);
			

			}
		}

		

		for (int i = 0 ;i< cols ;i++ ) {
			for (int j = 0;j< rows ;j++ ) {
				grid[i][j].setFValue(Double.POSITIVE_INFINITY);
			
			}
		}
	

		

		openSet.add(grid[start.getXValue()][start.getYValue()]);



		grid[start.getXValue()][start.getYValue()].setGValue(0);
		grid[start.getXValue()][start.getYValue()].setFValue(0);



	

		while(!openSet.isEmpty()){
			

			
			

			
			int winner = 0;

			//GETS THE LOWEST FVALUE ON THE OPEN LIST
			for (int i = 0;i<openSet.size() ;i++ ) {
				if (openSet.get(i).getFValue() < openSet.get(winner).getFValue()) {
					winner = i;
				
					
				}else{
					winner = winner;
				}
			}


			
			current = grid[openSet.get(winner).getXValue()][openSet.get(winner).getYValue()];

			grid[current.getXValue()][current.getYValue()].makeNeighbors(cols,rows,grid);

			

			if (grid[current.getXValue()][current.getYValue()] == grid[target.getXValue()][target.getYValue()]) {
				System.out.println("Win");
				System.out.println(closedSet.size());
				noPathFound = false;

				finalPath(grid[current.getXValue()][current.getYValue()]);//RECONSTRUCT THE PATH FROM START TO END
				break;  
			}


			//makeneighborsSet(current);
			openSet.remove(grid[current.getXValue()][current.getYValue()]);
			closedSet.add(grid[current.getXValue()][current.getYValue()]);





			

			for (int i  = 0;i< grid[current.getXValue()][current.getYValue()].neighborsSize() ;i++ ) {
			 
				double tempG= 0.0;

				Node neighbor = current.getNeighbor(i);
				
				

				

				

				if (!closedSet.contains(grid[neighbor.getXValue()][neighbor.getYValue()])) {
					tempG  =  grid[current.getXValue()][current.getYValue()].getGValue() + 1 ;

					if (openSet.contains(neighbor)) {
						if (tempG < grid[neighbor.getXValue()][neighbor.getYValue()].getGValue()) {
							grid[neighbor.getXValue()][neighbor.getYValue()].setGValue(tempG);
						}
					}else{
						grid[neighbor.getXValue()][neighbor.getYValue()].setGValue(tempG);
						openSet.add(grid[neighbor.getXValue()][neighbor.getYValue()]);
					}
				
					grid[neighbor.getXValue()][neighbor.getYValue()].setHValue(distance(neighbor,target));
			
					grid[neighbor.getXValue()][neighbor.getYValue()].setFValue((grid[neighbor.getXValue()][neighbor.getYValue()].getGValue() + grid[neighbor.getXValue()][neighbor.getYValue()].getHValue()));

					grid[neighbor.getXValue()][neighbor.getYValue()].setParent(grid[current.getXValue()][current.getYValue()]);
				}
			
				

		
			}


		}
	if (grid[current.getXValue()][current.getYValue()] != grid[target.getXValue()][target.getYValue()]) {
		noPathFound = true;
		System.out.println("No path Found");
	}
	


	}
	public void finalPath(Node current){ //RECONSTRUCT THE PATH
		Node temp = grid[current.getXValue()][current.getYValue()];

		cameFrom.add(grid[temp.getXValue()][temp.getYValue()]);

		while(temp.parentExist()){
			cameFrom.add(temp.getParent());
			temp = temp.getParent();
		}
	}

	public int distance(Node current, Node target){
		//DO NOTHING
		return 0;
	}

	public Node getNodes(int i, int j){
		return grid[i][j];
	}

	public void makeWall(int i, int j){
		grid[i][j].makeWall();
	}

	public void deleteWall(int i, int j){
		grid[i][j].deleteWall();
	}
	public void setCols(int cols){
		this.cols = cols;
	}
	public void setRows(int rows){
		this.rows = rows;
	}



	
	public int sizeOfNeighbors(int i, int j){
		return grid[i][j].neighborsSize();
	}

	public Node getClosedList(int i){
		return closedSet.get(i);
	}

	public double closedListSize(){
		return closedSet.size();
	}

	public int getGridSize(){
		return grid.length;
	}

	public int getSizeOfColumns(int i){
		return grid[i].length;
	}


	public Node getPathNode(int i ){
		return cameFrom.get(i);
	}

	public int getPathSize(){
		return cameFrom.size();
	}



	// public Node getCurrent(){
	// 	return current;
	// }
	// public Node getTarget(){
	// 	return target;
	// }	

	public void randomObstacles(){
		for (int i = 0;i< cols ;i++ ) {
			for (int j = 0;j<rows ;j++ ) {
				if (grid[i][j].getWall()) {
					
				}
				else{
					grid[i][j].wallGenerator();
				}
				

			}
		}
	}

	public void deleteAllWalls(){
		for (int i = 0;i< cols ;i++ ) {
			for (int j = 0;j<rows ;j++ ) {
				if (grid[i][j].getWall()) {
					grid[i][j].deleteWall();

				}
				
				

			}
		}
	}

	public void removeCloseList(){
		closedSet.clear();
	}
	public void removeOpenList(){
		openSet.clear();
	}

	public void removeCameFrom(){
		cameFrom.clear();
		for (int i = 0;i< cols ;i++ ) {
			for (int j = 0;j<rows ;j++ ) {
				if (grid[i][j].parentExist()) {
					grid[i][j].deleteParent();

				}
				
				

			}
		}

	}

	public void deleteNeighbors(){
		for (int i = 0;i<cols ;i++ ) {
			for (int j = 0;j<rows ;j++ ) {
				grid[i][j].deleteNeighbor();
			}
		}
	}



	public void setTimeOfCompletion(int i ){
		timeOfCompletion = timeOfCompletion + i;
	}

	public int getTimeOfCompletion(){
		return timeOfCompletion;
	}

	public boolean getNoPath(){
		return noPathFound;
	}
	
	
}