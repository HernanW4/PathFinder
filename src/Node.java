///*****************************************************************************
// Class: Node
// Author: Walter Hernandez
//
// Purpose: Main part for the algorithm to work, a node = every square on the grid
//
// Attributes: f_value, g_value, h_value, x,y
//
// Methods: constructor, setters/getters, makeNeighbors, wallGenerator, 
//
//*******************************************************************************

import java.util.*;

public class Node{
	private double f_value;
	private double g_value;
	private int h_value;

	private List<Node>neighbors;


	private int x;
	private int y; 

	private boolean wall = false;




	private Node parent;



	public Node(int x, int y){
		this.x = x;
		this.y = y;

		
	


	}


	public void makeNeighbors(int cols, int rows, Node grid[][]){
		this.neighbors = new ArrayList();

		int i = this.x;
		int j = this.y;

		if (i< cols-1 && !grid[i+1][j].getWall()) {

			neighbors.add(grid[i+1][j]);
		}
		if (i > 0 && !grid[i-1][j].getWall() ) {
			neighbors.add(grid[i-1][j]);
		}
		if (j< rows - 1 && !grid[i][j+1].getWall()) {
			neighbors.add(grid[i][j+1]);
		}
		if (j>0 && !grid[i][j-1].getWall()) {
			neighbors.add(grid[i][j-1]);
		}


	}


	public void wallGenerator(){
		int random = randomFrom(0,3);
		if (random ==2) {
			this.wall = true;
		}
	}

	public Node getNeighbor(int i){
		return this.neighbors.get(i);
	}
	public int neighborsSize(){
		return this.neighbors.size();
	}

	public void deleteNeighbor(){
		this.neighbors.clear();
	}

	public void makeWall(){
		this.wall = true;
	}
	public void deleteWall(){
		this.wall = false;
	}

	public boolean getWall(){
		return wall;
	}


	

	public void setParent(Node parent){
		this.parent = parent;
		}
	public void setFValue(double f){
		f_value = f;
	}
	public void setGValue(double g){
		g_value = g; 
	} 
	public void setHValue(int h){
		h_value = h;
	}
	public void setXValue(int x){
		this.x = x;
	}
	public void setYValue(int y){
		this.y = y;
	}


	public double getFValue(){
		return f_value;
	}
	public double getGValue(){
		return g_value;
	}
	public int getHValue(){
		return h_value;
	}
	public int getXValue(){
		return x;
	}
	public int getYValue(){
		return y;
	}
	

	public Node getParent(){
		return this.parent;
	}

	public boolean parentExist(){
		if (this.parent != null) {
			return true;
		}
		else{
			return false; 
		}
	}

	public void deleteParent(){
		this.parent = null;
	}

		
	public int randomFrom (int low, int high) {

		int randNum = 0;

		// (int) is casting since Math.random() return a double and randNum is an int
		randNum = (int) (Math.random()*(high-low) + low);

		return randNum;
	}



}