///*****************************************************************************
// Class: ManhattanDistance
// Author: Walter Hernandez
//
// Purpose: Give a different distance method to the AlgorithmAstart(parent)
//
// Attributes: 
//
// Methods: distance
//
//*******************************************************************************

public class ManhattanDistance extends AlgorithmAstar{

	public ManhattanDistance(int col, int row){
		super(col,row);
	}
	@Override
	public int distance(Node current, Node target){
		return Math.abs(current.getXValue()-target.getXValue()+Math.abs(current.getYValue()-target.getYValue()));
	}
}