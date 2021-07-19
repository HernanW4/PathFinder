///*****************************************************************************
// Class: PathFinderGUI
// Author: Walter Hernandez
//
// Purpose: handles the GUI part of the program, mostly events
//
// Attributes: WIDTH, HEIGHT, rectangles, start,end, col, row,
//
// Methods: 
//
//*******************************************************************************


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.Cursor;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import java.util.*;
import javafx.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class PathFinderGUI extends Application{

	//private Node grid[][];

	private final int WIDTH = 900;
	private final int HEIGHT = 800;
	//private MakeGrid[][] rectangles;
	private boolean start;
	private boolean end ;
	private int col = 40;
	private int row = 40;
	private int widthGap = (WIDTH-200) / row;
	private int heightGap = HEIGHT / col;
	private int startx,starty, endx, endy;


	private boolean manhattanChecked, euclideanChecked = false;


	private int i = 0, j = 0, method;


	private MakeGrid pane;
	//private TaskPane taskpane;

	private Timeline timeline;

	//private TaskPane taskpane = new TaskPane();


	//private Node endPoint



	public PathFinderGUI(){
		//taskpane = new TaskPane();
		pane = new MakeGrid(col,row);
		pane.makemap(WIDTH-200,HEIGHT);


		timeline = new Timeline();
		// make timeline repeat infinite number of times
		timeline.setCycleCount(Timeline.INDEFINITE);
		// timeline uses keyframes so make a keyframe
		//make a keyframe that runs every half second
		// keyframe can use a lambda expression for its action

		

		AlgorithmAstar[] astar = pane.getAstar();
		Rectangle[][] rectangles = pane.getGrid();

		KeyFrame keyframe = new KeyFrame(Duration.millis(7),action -> {
			
			// pane.draw_Neighbors();
			
				
			try{
				if (j >= astar[method].getClosedList(i).neighborsSize()) {
					j = 0;
					i++;
				}

				if (i >= astar[method].closedListSize())
				{
					timeline.stop();
					pane.setGameRunning(false);
					pane.reconstructPath(method);
					i = 0;
					j = 0;
					if (astar[method].getNoPath()) {
						timeline.stop();
						i = 0;
						j = 0;
					}
				}

				else
				{
					if (rectangles[astar[method].getClosedList(i).getNeighbor(j).getXValue()][astar[method].getClosedList(i).getNeighbor(j).getYValue()] == rectangles[pane.getStartX()][pane.getStartY()]) {
				
					}
					else if(rectangles[astar[method].getClosedList(i).getNeighbor(j).getXValue()][astar[method].getClosedList(i).getNeighbor(j).getYValue()] == rectangles[pane.getEndX()][pane.getEndY()]){

					}
					else{
						rectangles[astar[method].getClosedList(i).getNeighbor(j).getXValue()][astar[method].getClosedList(i).getNeighbor(j).getYValue()].setFill(Color.HOTPINK);
					}

					j++;
				}
				astar[method].setTimeOfCompletion(7);
				//pane.setGameRunning(false);
			}catch(IndexOutOfBoundsException e){
				System.out.println("Exception handling for IndexOutOfBoundsException");
				timeline.stop(); //STOPS THE ALGORITHM IF THE GAME STOP
			}

		

			
		});
		timeline.getKeyFrames().add(keyframe);
	}


	@Override
	public void start(Stage primaryStage) {
		

		
		//MakeGrid pane = new MakeGrid(col,row);
		Scene scene = new Scene(pane,WIDTH,HEIGHT);

		
		

		start = false;
		end = false;

		
	

		pane.manhattanBtn().setOnAction(e->{
			System.out.println("Manhattan");
		
		
			manhattanChecked = true;
			euclideanChecked = false;
			method = 0;
		//	pane.makeRandomMap(0);
			// method = 0;
			
		});
		pane.euclideanBtn().setOnAction(e->{
			System.out.println("euclideanBtn");
		
			euclideanChecked = true;
			manhattanChecked = false;
			method = 1;
			//pane.makeRandomMap(1);
			
			//pane.makeRandomMap(method);
		});
		pane.randomBtn().setOnAction(e->{
			pane.makeRandomMap();
			
			pane.displayRandomWall(method);
			
		});
		// pane.clearBtn().setOnAction(e->{
		// 	pane.removeMap(col,row);
		// });

		pane.saveBtn().setOnAction(e->{
			pane.saveToFile(method);
		});

		pane.deleteBtn().setOnAction(e->{
			pane.deleteRandomMap();
			
		//	manhattanChecked = false;
		});

		pane.deleteCurrentBtn().setOnAction(e->{
			pane.deleteWorkDone(method);
			
		});

		pane.loadBtn().setOnAction(e->{
			pane.loadMap(method);
			
		});
	

	

		try{


			pane.setOnMouseClicked(e->{

			
		//	startPoint = new int[1][1];
					
					// System.out.println((int)e.getX()/gap);
					// System.out.println((int)e.getY()/gap);
					if (e.getButton() == MouseButton.PRIMARY && pane.getStartexist() == false) { //CREATES THE START AND END POINT

						pane.startPoint((int)e.getX()/widthGap,(int)e.getY()/heightGap);
					
						start = true;
					}
					else if(e.getButton() == MouseButton.PRIMARY && pane.getStartexist() == true && pane.getEndexist() == false){
						pane.endPoint((int)e.getX()/widthGap,(int)e.getY()/heightGap);

		
						end = true;

					//else if()
					} else if(e.getButton() == MouseButton.PRIMARY&&pane.getStartexist() == true && pane.getEndexist() == true){
						if (((int)e.getX()/widthGap != pane.getStartX() && (int)e.getY()/heightGap != pane.getStartY()) && (((int)e.getX()/widthGap != pane.getEndX() && (int)e.getY()/heightGap != pane.getEndY() ))) {
							pane.makeWall((int)e.getX()/widthGap,(int)e.getY()/heightGap);
						}
						

					}else if(e.getButton() == MouseButton.SECONDARY){ //THE RIGHT CLICK OF THE MOUSE WOULD ERASE POINTS MADE BEFORE

						pane.erase((int)e.getX()/widthGap,(int)e.getY()/heightGap,method);

						if ((int)e.getX()/widthGap == pane.getStartX() && (int)e.getY()/heightGap == pane.getStartY())  {
							start = false;
						}
						if ((int)e.getX()/widthGap == pane.getEndX() && (int)e.getY()/heightGap == pane.getEndY())  {
							end = false;
						}

					}
					//else if(e.getButton() == )

				});
		}catch(IndexOutOfBoundsException e){
			System.out.println("Outside of Grid has been clicked");
		}

		pane.setOnMouseDragged(e->{ //THIS FUNCTION ENABLES WALLS AND DRAGGING THE MOUSE
			int gap = WIDTH / row;
		//	System.out.println("Dragged");

			if (e.getButton() == MouseButton.PRIMARY&&pane.getStartexist() == true && pane.getEndexist() == true) {
				if (e.getX() != pane.getStartX() && e.getY() != pane.getStartY()) {
					pane.makeWall((int)e.getX()/widthGap,(int)e.getY()/heightGap);

				}
			}
			 if(e.getButton() == MouseButton.SECONDARY){

				pane.erase((int)e.getX()/widthGap,(int)e.getY()/heightGap,method);

				if ((int)e.getX()/widthGap == pane.getStartX() && (int)e.getY()/heightGap == pane.getStartY())  {
						start = false;
					}
				if ((int)e.getX()/widthGap == pane.getEndX() && (int)e.getY()/heightGap == pane.getEndY())  {
						end = false;
				}

			}
 
		});

		// scene.setOnScroll((ScrollEvent event)->{                         TO BE CONTINUE ON THE SUMMER >:)
		
		// 	int zoomFactor = 2;

		// 	double deltaY = event.getDeltaY();
		// 	System.out.println(deltaY);
		// 	//pane.removeMap(row,col);

		// 	if (deltaY > 0 && col > 25 && row > 25) {
		// 		col = col - zoomFactor;
		// 		row = row - zoomFactor;
		// 	}
		// 	if (deltaY < 0 && col < 50 && row < 50 ) {
		// 		col = col + zoomFactor;
			
		// 		row = row + zoomFactor;
		// 	}

		// 		//scene.setScale(.5);


		// 	pane.reScale(col,row,method);
		// 	pane.makemap(WIDTH,HEIGHT);



		// });
		

		//START GAME
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key)->{
			if (key.getCode()==KeyCode.SPACE) {
				Timer timer = new Timer();


				System.out.println("Start");
				
				//pane.randomWall();
				pane.startGame(method);
				timeline.play();

				

			}
		});

		// scene.addEventHandler(KeyEvent.KEY_PRESSED, (key)->{
		// 	if (key.getCode() == KeyCode.R) {
		// 		pane.randomWall(method);
		// 	}
		// });

	

		primaryStage.setTitle("PathFinder");
		primaryStage.setScene(scene);

		primaryStage.show();
		
		
	}

	public static void main(String[] args) { launch(args); }
	

}
 // end of class LayoutDemo
