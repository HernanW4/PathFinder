///*****************************************************************************
// Class: MakeGrid
// Author: Walter Hernandez
//
// Purpose: Makes the grid pane of the GUI part
//
// Attributes: grid, gap, startx/starty, endx/endy, start/end , manager, astar 
// row,col,widthGap, heightGap,manhattanMoveTotal, euclideanMoveTotal, timeofCompletion, astar[], gameRunning, start_exist, end_exist
//
// Methods: constructor, makemap, startGame, startPoint, erase, makeWall, endPoint, reconstructPath, deleteRandomMap, deleteWorkDone, changeRandomMap, makeRandomMap
//	displayRandomWall, saveToFile, loadMap
//
//*******************************************************************************


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import java.util.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.*;
import javafx.scene.control.Button;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class MakeGrid extends Pane{
	private int row,col;
	private Rectangle[][] grid;
//	private Rectangle[][] relocate;
	private int widthGap,heightGap;
	private int startx,starty, endx, endy;
	private int manhattanMoveTotal, euclideanMoveTotal;

	private int timeOfCompletion;

	private Node start, end;

//	private PathFinder manager = new PathFinder();
	private AlgorithmAstar[] astar = new AlgorithmAstar[2];

	private boolean gameRunning,start_exist, end_exist = false;

	private TaskPane taskPane;
	

	//MouseHandler mHandler = new MouseHandler();
	public MakeGrid(int row, int col){
		taskPane = new TaskPane();
		getChildren().add(taskPane);
		this.col = col;
		this.row = row;

		astar[0] = new ManhattanDistance(col,row);
		astar[1] = new EuclideanDistance(col,row);




		
	}

	// public void removeMap(int row, int col){
	// 	for (int k  = 0;k< astar.length ;k++ ) {
	// 		for (int i = 0;i< col ;i++ ) {
	// 			for (int j = 0;j < row ;j++ ) {
		
	// 				grid[i][j].setFill(Color.WHITE);

	// 				if (i == startx && j == starty) {
	// 					start_exist = false;
	// 					grid[i][j].setFill(Color.WHITE);
	// 					startx = 0;
	// 					starty = 0;
	// 				}
	// 				if (i == endx && j == endy) {
	// 					endx = 0;
	// 					endy = 0;
	// 					end_exist = false;
	// 					grid[i][j].setFill(Color.WHITE);

	// 				}
	// 				astar[k].getNodes(i,j).deleteWall();
	// 				astar[k].removeCloseList();
	// 				astar[k].removeCameFrom();
	// 			}
			
		
	// 		}
	// 	}
		

	
	// }

	// public void updateMap(){

	// 	for (int i = 0;i < grid.length ;i++ ) {
	// 		for (int j = 0;j<grid[i].length ;j++ ) {
	// 			if (i == startx && j == starty) {
	// 				relocate[i][j] = this.grid[i][j];




	// 			}
	// 		}
	// 	}
	// }

	// public void reScale(int row, int col, int method){
		

	// 	this.col = col;
	// 	this.row = row;

	// 	relocate = new Rectangle[col][row];
	// 	for (int i = 0;i< col ;i++ ) {
	// 		for (int j = 0;j < row ;j++ ) {
	// 			this.relocate[i][j] = new Rectangle(i*widthGap,j*heightGap,widthGap-1,widthGap-1);
	// 			this.relocate[i][j].setFill(Color.WHITE);
	// 			this.relocate[i][j].setStroke(Color.BLACK);

				
	// 			getChildren().add(this.relocate[i][j]);

	// 		//	grid[i][j].setFill(Color.WHITE);
	// 				//r.setStroke(Color.BLACK);
				
	// 		}
			
		
	// 	}



	// 	astar[method].adjustParameter(col,row);
	
	// }

	public void makemap(int width,int height){
				

		this.grid = new Rectangle[this.col][this.row];
		
		//grid = new Rectangle[col][row];
		this.widthGap = (int)width/col;
		this.heightGap = (int)height/col;
		

		for (int i = 0;i< col ;i++ ) {
			for (int j = 0;j < row ;j++ ) {
				this.grid[i][j] = new Rectangle((i*widthGap),heightGap*j,widthGap,heightGap);
				this.grid[i][j].setFill(Color.WHITE);
				this.grid[i][j].setStroke(Color.BLACK);

			//	grid[i][j].setFill(Color.WHITE);
					//r.setStroke(Color.BLACK);
				getChildren().add(this.grid[i][j]);
			}
			
		
		}
	}




	public void reconstructPath(int method){
		for (int i = 0;i<astar[method].getPathSize() ;i++ ) {
			if (grid[astar[method].getPathNode(i).getXValue()][astar[method].getPathNode(i).getYValue()] == grid[start.getXValue()][start.getYValue()]) {
				
			}
			else if (grid[astar[method].getPathNode(i).getXValue()][astar[method].getPathNode(i).getYValue()] == grid[end.getXValue()][end.getYValue()]) {
				
			}
			else {
				grid[astar[method].getPathNode(i).getXValue()][astar[method].getPathNode(i).getYValue()].setFill(Color.INDIGO);
				if (method == 0) {
					manhattanMoveTotal = astar[0].getPathSize();
				}
				if (method ==1) {
					euclideanMoveTotal = astar[1].getPathSize();
				}
			}
			if (i == astar[method].getPathSize()) {
				gameRunning = false;
			}
		}
	}

	
	public void startGame(int method){

		//System.out.println(astar.sizeOfNeighbors());
		//System.out.println(astar.getNeighbors(method).getXValue());

		if (start_exist && end_exist) {
			astar[method].algorithm(start,end);
			gameRunning =true;
			
			

			
		}
	}

	public void startPoint(int i,int j){
		//grid = new Rectangle[col][row];
		//gap = width / col;
		try{

		
			if (!gameRunning) {
				
				if (!start_exist) {
					startx = i;
					starty = j;
					grid[i][j].setFill(Color.GREEN);


					start = new Node(i,j);

					start_exist = true;
				}
			}
		}catch(IndexOutOfBoundsException e){
			//DO NOTHING, IT CAN MEAN THE USER CLICKED OUTSIDE THE GRID
			System.out.println("Start point cant be created");
		}






	//	grid[i][j].setStroke(Color.BLACK);
	//	grid[i][j].setFill(Color.WHITE);
					//r.setStroke(Color.BLACK);
		
			
	}

	public void deleteRandomMap(){
		if (!gameRunning) {
			for (int k = 0; k< astar.length;k++) {
			astar[k].setTimeOfCompletion(0);

			astar[k].deleteAllWalls();
			astar[k].removeCameFrom();
		
			astar[k].removeOpenList();
			astar[k].removeCloseList();

		
			for (int i = 0;i< col ;i++ ) {
				for (int j= 0;j < row ;j++ ) {
				
					if (astar[k].getNodes(i,j).getXValue()== start.getXValue() && astar[k].getNodes(i,j).getYValue() == start.getYValue()) {
					start_exist = false;
					grid[start.getXValue()][start.getYValue()].setFill(Color.WHITE);
					start.setXValue(0);
					start.setYValue(0);
					
				}
				if (astar[k].getNodes(i,j).getXValue()== end.getXValue() && astar[k].getNodes(i,j).getYValue() == end.getYValue()) {
					end_exist = false;
					grid[end.getXValue()][end.getYValue()].setFill(Color.WHITE);
					end.setXValue(0);
					end.setYValue(0);
					//grid[end.getXValue()][end.getYValue()].setFill(Color.WHITE);

					}
				else {

					grid[i][j].setFill(Color.WHITE);
				}


				}

			
			}
			for (int i = 0;i< astar[k].closedListSize() ;i++ ) {
				astar[k].getClosedList(i).deleteNeighbor();
			}
			astar[k].removeCloseList();
		}

		

		
			
		}
		




		
	}

	public void deleteWorkDone(int method){
		if (!gameRunning) {
			astar[method].setTimeOfCompletion(0);

			astar[method].removeCameFrom();
		
			astar[method].removeOpenList();
			astar[method].removeCloseList();

		
		for (int i = 0;i< col ;i++ ) {
			for (int j= 0;j < row ;j++ ) {
			
				if (astar[method].getNodes(i,j).getWall()){

					//DO NOTHING
				}
				else if(astar[method].getNodes(i,j).getXValue()== start.getXValue() && astar[method].getNodes(i,j).getYValue() == start.getYValue()){
					grid[i][j].setFill(Color.GREEN);
				}
				else if(astar[method].getNodes(i,j).getXValue()== end.getXValue() && astar[method].getNodes(i,j).getYValue() == end.getYValue()){
					grid[i][j].setFill(Color.RED);
				}
				else{
					
					grid[i][j].setFill(Color.WHITE);
				}

				

			}

			
		}
			for (int i = 0;i< astar[method].closedListSize() ;i++ ) {
				astar[method].getClosedList(i).deleteNeighbor();
			}
				astar[method].removeCloseList();
		}
		


	}
	public void changeRandomMap(int method){
		if (!gameRunning) {
			astar[Math.abs(method-1)].deleteAllWalls();


			for (int i = 0;i<col ;i++ ) {
				for (int j = 0;j<row ;j++ ) {
					if (astar[method].getNodes(i,j).getWall()) {
						grid[i][j].setFill(Color.BLACK);
					}
					if (start_exist) {
						if (astar[method].getNodes(start.getXValue(),start.getYValue()).getWall()) {
						astar[method].getNodes(start.getXValue(),start.getYValue()).deleteWall();
					}
					if (end_exist) {
						if (astar[method].getNodes(end.getXValue(),end.getYValue()).getWall()) {
						astar[method].getNodes(end.getXValue(),end.getYValue()).deleteWall();
					}
				}
			}

				}
			}
		}
		
	}

	public void makeRandomMap(){
		if (!gameRunning) {
			astar[0].randomObstacles();

		for (int i = 0;i < col ;i++ ) {
			for (int j = 0;j<row ;j++ ) {
				if (astar[0].getNodes(i,j).getWall()) {
					astar[1].getNodes(i,j).makeWall();
				}
			}
		}
		}




	}


	public void displayRandomWall(int method){
		
	//	astar[method].randomObstacles();
		if (!gameRunning) {
		for (int i = 0;i<col ;i++ ) {
			for (int j = 0;j<row ;j++ ) {
				if (astar[method].getNodes(i,j).getWall()) {
					grid[i][j].setFill(Color.BLACK);
				}
				if (start_exist) {
					if (astar[method].getNodes(start.getXValue(),start.getYValue()).getWall()) {
					astar[method].getNodes(start.getXValue(),start.getYValue()).deleteWall();
				}
				if (end_exist) {
					if (astar[method].getNodes(end.getXValue(),end.getYValue()).getWall()) {
					astar[method].getNodes(end.getXValue(),end.getYValue()).deleteWall();
				}
				}
			}

			}
		}
		}
		
	}



	public void erase(int i, int j, int method){
		try{

		
			if (!gameRunning) {
				grid[i][j].setFill(Color.WHITE);

				if (i == startx && j == starty) {
					start_exist = false;
					grid[i][j].setFill(Color.WHITE);
				}
				if (i == endx && j == endy) {
					end_exist = false;
					grid[i][j].setFill(Color.WHITE);

				}
		
				astar[method].deleteWall(i,j);
		}

	}catch(IndexOutOfBoundsException e){
		//DO NOTHING, IT CAN MEAN THE USER CLICKED OUTSIDE THE GRID
			System.out.println("can't erase");
	}
		

	}

	public void makeWall(int i,int j){
		try{

		
			if (!gameRunning) {
				for (int k = 0;k<astar.length ;k++ ) {
					if ((i == startx && j == starty)  || ( i == endx && j == endy)) {
				
				}

				else{
					grid[i][j].setFill(Color.BLACK);

					astar[k].makeWall(i,j);
					}
			}
		}
		}catch (IndexOutOfBoundsException e) {
			//DO NOTHING, IT CAN MEAN THE USER CLICKED OUTSIDE THE GRID
			System.out.println("Map point cant be created");
		}	
	
		
		
		
	}

	public void endPoint(int i, int j){
		try{
			if (!gameRunning) {
				if (!end_exist && start_exist) {
					endx = i;
					endy = j;
					grid[i][j].setFill(Color.RED);

		
					end = new Node(i,j);

					end_exist = true;
				}	
			}
		}catch(IndexOutOfBoundsException e){
			//DO NOTHING, IT CAN MEAN THE USER CLICKED OUTSIDE THE GRID
			System.out.println("End point cant be created");
		}
		

		
	}

	public void saveToFile(int method){
		File infoFile = new File("InfoFile.txt");
		File infoMap = new File("InfoMap.txt");

		try{
			FileWriter fileW = new FileWriter(infoFile);
			BufferedWriter buffW = new BufferedWriter(fileW);
			buffW.write("The grid was a "+col+" x "+row+":");
			buffW.newLine();

			buffW.write("It took "+manhattanMoveTotal+" squares to get from ("+startx+","+starty+") to ("+endx+","+endy+") in "+astar[0].getTimeOfCompletion() *.001+" seconds");
			buffW.newLine();
			
				
			buffW.write("The distance Formula used in this example was 'Manhattan Distance Formula' ");
			buffW.newLine();
			buffW.newLine();
			
				
			buffW.write("The grid was a "+col+" x "+row+":");
			buffW.newLine();

			buffW.write("It took "+euclideanMoveTotal+" squares to get from ("+startx+","+starty+") to ("+endx+","+endy+") in "+astar[1].getTimeOfCompletion() *.001+" seconds");
			buffW.newLine();
			

			buffW.write("The distance Formula used in this example was 'Euclidean Distance Formula' ");
			buffW.newLine();
			buffW.newLine();
				
			buffW.close();

			FileWriter infoFW = new FileWriter(infoMap);
			BufferedWriter infoBW = new BufferedWriter(infoFW);

			
			for (int i = 0;i<col ;i++ ) {
				for (int j = 0;j<row ;j++ ) {
					if (astar[method].getNodes(i,j).getWall()) {
						infoBW.write(i+","+j+"");
						infoBW.newLine();


				}

					
					
			

				}
			
			}

			infoBW.close();

			
		}catch(Exception e){
			System.out.println("ERROR");
		}

	}

	public void loadMap(int method){
		File infoMap = new File("InfoMap.txt");

		try{
			FileReader infoFR = new FileReader(infoMap);
			BufferedReader infoBR = new BufferedReader(infoFR);

			String line;

			while (  ( line = infoBR.readLine() ) != null     ) {

				String[] lineColumns;
				// break the line up to columns. break on the comma and delete the comma
				
				lineColumns = line.split(",");

				try{
					int i = Integer.parseInt(lineColumns[0]);
					int j = Integer.parseInt(lineColumns[1]);

					for (int k = 0;k<astar.length ;k++ ) {
						astar[k].getNodes(i,j).makeWall();
					}
					

					displayRandomWall(method);
				}
				catch(Exception e){
					System.out.println("Error");
				}


			}

			infoBR.close();

		}catch(Exception e){
			System.out.println("Something is up");
		}
	}




	public AlgorithmAstar[] getAstar() {
		return astar;
	}

	

	public Rectangle[][] getGrid() {
		return grid;
	}

	public int getStartX(){
		return startx;
	}

	public int getStartY(){
		return starty;
	}

	public int getEndX(){
		return endx;
	}

	public int getEndY(){
		return endy;
	}

	public void didStart(boolean cond){
		start_exist = cond;
	}

	public void didEnd(boolean cond){
		end_exist = cond;
	}
	public void setGameRunning(boolean condition){
		gameRunning = condition;
	}
	public boolean getGameRunning(){
		return gameRunning;
	}

	public boolean getStartexist(){
		return start_exist;
	}

	public boolean getEndexist(){
		return end_exist;
	}

	
	public Button manhattanBtn(){
		return taskPane.manhattanBtn();
	}

	public Button euclideanBtn(){
		return taskPane.euclideanBtn();
	}

	public Button randomBtn(){
		return taskPane.randomBtn();
	}

	public Button clearBtn(){
		return taskPane.clearBtn();
	}

	public Button saveBtn(){
		return taskPane.saveBtn();
	}

	public Button deleteBtn(){
		return taskPane.deleteBtn();
	}

	public Button loadBtn(){
		return taskPane.loadBtn();
	}

	public Button deleteCurrentBtn(){
		return taskPane.deleteCurrentBtn();
	}



}
