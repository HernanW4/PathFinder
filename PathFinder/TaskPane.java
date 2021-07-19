///*****************************************************************************
// Class: TaskPane
// Author: Walter Hernandez
//
// Purpose: Makes the side bar on the right of the screen with buttons
//
// Attributes: Button: manhattanDistance, euclideanDistance, randomMap, clearMap, saveMap, deleleMap, deleteCurrent, loadMapBtn, 
// Attributes: Text: distances, chooseRandomMap, clearText, saveText, deleteRandom, deleteText, startText, loadText
//
// Methods: constructor, bunch of getters so the MakeGrid can use the buttons
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
import javafx.scene.text.Text;


public class TaskPane extends Pane{


	private Button manhattanDistance, euclideanDistance;

	private Button randomMap, clearMap, saveMap, deleleMap, deleteCurrent, loadMapBtn;


	private Text distances, chooseRandomMap, clearText, saveText, deleteRandom, deleteText, startText, loadText ;
	public TaskPane(){


		distances = new Text(700,25,"Choose a Distance method");
		manhattanDistance = new Button("Manhattan");
		euclideanDistance = new Button("Euclidean");


		manhattanDistance.setFocusTraversable(false);
		euclideanDistance.setFocusTraversable(false);

		manhattanDistance.relocate(700,50);
		euclideanDistance.relocate(700,100);

		getChildren().addAll(manhattanDistance,euclideanDistance,distances);


		randomMap = new Button("RandomMap");
		randomMap.relocate(700,200);
		randomMap.setFocusTraversable(false);

		chooseRandomMap = new Text(700, 180,"Make Random Map");

		getChildren().addAll(randomMap,chooseRandomMap);


		// clearMap = new Button("Clear Map");
		// clearText = new Text(700,300,"Clear The Map");

		// clearMap.relocate(700,250);
		// clearMap.setFocusTraversable(false);

		// getChildren().addAll(clearMap,clearText);


		saveMap = new Button("Save Info");
		saveText = new Text(700,280,"Save MapInfo");
		saveMap.relocate(700,290);
		saveMap.setFocusTraversable(false);

		getChildren().addAll(saveMap,saveText);

		loadMapBtn = new Button("Load Map");
		loadText = new Text(700,350,"Load Last saved map");
		loadMapBtn.relocate(700,360);

		loadMapBtn.setFocusTraversable(false);


		getChildren().addAll(loadMapBtn,loadText);



		deleleMap = new Button("Clear");
		deleteRandom = new Text(700,410,"Clear the Whole Map");
		deleleMap.relocate(700,420);

		deleleMap.setFocusTraversable(false);

		getChildren().addAll(deleleMap,deleteRandom);

		deleteCurrent = new Button("Delete Work");
		deleteText = new Text(700,470,"Start again with same Map");
		deleteCurrent.relocate(700,480);

		deleteCurrent.setFocusTraversable(false);


		startText = new Text(700,600,"Press Space To Start");

		getChildren().addAll(deleteCurrent,deleteText,startText);

		








	}

	public Button clearBtn(){
		return clearMap;
	}
	public Button randomBtn(){
		return randomMap;
	}

	public Button manhattanBtn(){
		return manhattanDistance; 
	}

	public Button euclideanBtn(){
		return euclideanDistance;
	}
	public Button saveBtn(){
		return saveMap;
	}
	public Button deleteBtn(){
		return deleleMap;
	}

	public Button deleteCurrentBtn(){
		return deleteCurrent;
	}

	public Button loadBtn(){
		return loadMapBtn;
	}






}