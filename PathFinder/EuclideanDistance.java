///*****************************************************************************
// Class: EuclideanDistance
// Author: Walter Hernandez
//
// Purpose: Give a different distance method to the AlgorithmAstart(parent)
//
// Attributes: 
//
// Methods: distance
//
//*******************************************************************************
import java.util.*;
public class EuclideanDistance extends AlgorithmAstar{


	public EuclideanDistance(int col, int row){
		super(col,row);
	}

	@Override
	public int distance(Node current, Node target){
		return (int)Math.sqrt(Math.abs(current.getXValue()-target.getXValue()) + Math.abs(current.getYValue()-target.getYValue()));
	}
}