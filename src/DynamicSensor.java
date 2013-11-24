import java.util.ArrayList;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DynamicSensor
{
	static ArrayList<Target> targetList = new ArrayList<Target>();
	static ArrayList<Sensor> sensorList = new ArrayList<Sensor>();
	static ArrayList<Sensor> sensorSubList = new ArrayList<Sensor>();
	double finalMinCost;
	static double[][] m =new double[][]{
		{1500.0, 0.0,    0.0, 0.0,    1500.0},
		{1500.0, 1.0,    2.5, 1500.0, 1500.0},
		{1500.0, 1.0,    2.5, 2.0,    1500.0},
		{1500.0, 1500.0, 1500.0, 2.0,    1500.0}
	};
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		File myFile = new File("Sensor.txt");
//		Scanner scan;
//		String line;
//		try {
//			scan = new Scanner(myFile);
//			while(scan.hasNext())
//			{
//				line=scan.nextLine();
//				parseLine(line);
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		for(Sensor s:sensorList)
		{
			for(Target t:targetList)
			{
				if(distance(t,s))
				{
					s.targets.add(t);
				}
			}
		}
//Begin Corey's guess		
		int sList = 3;//sensorList.size();
		int tList = 3;//targetList.size();
//		//create matrix with two additional columns and one additional row to account for edge cases
//		m = new double[tList+1][sList+2];
//		//fill top row with 0 as to not affect sum
//		for(int col=1; col<=sList;col++)
//		{
//			m[0][col]=0.0;
//		}
//		//fill first and last row with big numbers
//		for(int row=1; row<=tList;row++)
//		{
//			m[row][0]=15000.0;
//			m[row][sList+1]=15000.0;
//		}
//		
//		// Fill matrix with weights  
//		for(int row = 1; row<=tList;row++)
//		{
//			for(int col = 1; col<=sList;col++)
//			{
//				if(distance(targetList.get(row-1),sensorList.get(col-1)))
//				{
//					m[row][col] = sensorList.get(col-1).cost;
//				}else
//				{
//					m[row][col] = 1500.0;// huge number that would never be chosen
//				}
//			}
//		}
		
		
		for(int row = 1; row <= tList ;row++)
		{
			for(int col = 1; col<=sList;col++)
			{
				if(m[row-1][col] != m[row][col]) // same as if (S1 covers only T1 or T2)
				{
					//Adds minimum of previous row to this cell
					m[row][col] = m[row][col] + min(row,col);//recurrence
				}
				
			}
		}
		//Iterates through last row to find minimum cost
		double cost = 1500.0;
		int finalCol = 1;
		for (int col = 1; col <= sList; col++)
		{
			//cost = m[tList][col]<cost ? m[tList][col]:cost;
			if (m[tList][col] < cost) {
				cost = m[tList][col];
				finalCol = col; //keeps track of the column as well in order to use in printSensorList
			}
		}
////End Corey's guess		
		
		System.out.println("The minimum cost is: " + cost);
		printSensorSet(targetList.size(), finalCol); 
		for(Sensor s:sensorSubList) {
			System.out.print("S" + s);
		}
		
	}
	
	//increments through the above row to find the minimum value
	public static double min(int row, int col)
	{
		double min = 16000.0;
		for(int i = 1; i<=3;i++)//col-1 iterates through columns here
		{
			min= m[row-1][i] < min ? m[row-1][i]: min;
			if(min == 0.0) {
				return 0.0;
			}
		}
		return min;
	}
	public static void printSensorSet (int row, int col) { //we call this function using the last row, first column 
		if (row == 0) { //in order to ignore first row

			return;// sensorSubList;
		}
		//will recurse until matching index is found in previous row. Could also be done with a for loop
		if (m[row][col] == m[row - 1][col] + sensorList.get(col-1).getCost() ) { //this means the proper one was found
			//check to see if this sensor is already in the sublist
			if (!sensorSubList.contains(sensorList.get(col-1))) {
				sensorSubList.add(sensorList.get(col));
			}
			printSensorSet (row -1, col); //this row's (target's) proper sensor was found. 
		}
		else if (col == sensorList.size()) {//means we are looking at last column, and correct index wasn't already found
			System.out.println("YOU MESSED UP BRO"); //just an error catcher, don't really need these two lines of code
			//return sensorSubList;
		}
		else printSensorSet(row, col + 1); //repeat on the next index over in the previous row
		//return null;
	}
	
	
	public boolean isThereCover (ArrayList<Sensor> partialSensorList) {
		ArrayList<Target> partialTargetList = new ArrayList<Target>();
		for (Sensor s: partialSensorList) {
			for (Target t: s.targets) {
				if (!partialTargetList.contains(t)) {
					partialTargetList.add(t);
				}
			}
		}
		return (partialTargetList.size() == targetList.size());
		
		//Adds the first sensor
		//look at first sensor (has a list of targets, say, T1 T2 and T5)
		//Add T1 T2 and T5 to an ArrayList (insert if unique)
		//insert T1 into a partialTargetList if there does not already exist a T1
		//do same for all targets of the first sensor
		//git test
		//Go to the second sensor and repeat
		//After all targets are added to list, check to see if there is full cover
		//Do this by comparing partialTargetList.size and targetList.size
		//return boolean answer
	}
	//Parses file and creates sensor object and target object
	private static void parseLine(String line)
	{
		
		String delims = "[ ]+";
		String[] tokens = line.split(delims);
		if(tokens[0].equals("1"))
		{
			Sensor sensor = new Sensor();
			sensor.x = Double.parseDouble(tokens[1]);
			sensor.y = Double.parseDouble(tokens[2]);
			sensor.cost = Double.parseDouble(tokens[3]);
			sensorList.add(sensor);
		}
		else if(tokens[0].equals("2"))
		{
			Target target = new Target();
			target.x = Double.parseDouble(tokens[1]);
			target.y = Double.parseDouble(tokens[2]);
			targetList.add(target);
		}
	}
	
	
	//Calculates distance between target and Sensor and returns true if <= 1
	private static boolean distance(Target t, Sensor s)
	{
		double tx = t.x;
		double ty = t.y;
		double sx = s.x;
		double sy = s.y;
		double answer;
		answer=Math.pow(Math.pow(tx-sx,2)+Math.pow(ty-sy,2),0.5);
		if(answer<=1)
		{
			return true;
		}
		return false;
	}


}

