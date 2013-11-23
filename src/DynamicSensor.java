import java.util.ArrayList;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DynamicSensor
{
	static ArrayList<Target> targetList = new ArrayList<Target>();
	static ArrayList<Sensor> sensorList = new ArrayList<Sensor>();
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
//Begin Corey's guess		
		int sList = 3;//sensorList.size();
		int tList = 3;//targetList.size();
//		//create matrix with two additional columns and one additional row
//		m = new double[tList+1][sList+2];
//		//fill top row with 0
//		for(int col=1; col<=sList;col++)
//		{
//			m[0][col]=0.0;
//		}
//		//fill first and last row with big numbers
//		for(int row=1; row<=tList;row++)
//		{
//			m[row][0]=1500.0;
//			m[row][sList+1]=1500.0;
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
		
		
		for(int row = 1; row<=tList;row++)
		{
			for(int col = 1; col<=sList;col++)
			{
				if(m[row-1][col] != m[row][col])
				{
					m[row][col] = m[row][col] + min(row,col);//recurrence
				}
				
			}
		}
		
		double cost = 1500.0;
		
		for (int col = 1; col<=sList;col++)
		{
			cost = m[tList][col]<cost ? m[tList][col]:cost;
		}
		System.out.println(cost);
////End Corey's guess		
		
		
		
		
		
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
	}
	
	//increments through the above row to find the min
	public static double min(int row, int col)
	{
		double min = 16000.0;
		for(int i = 1; i<=3;i++)//col-1
		{
			min= m[row-1][i]<min ? m[row-1][i]:min;
			if(min==0.0){return 0.0;}
		}
		return min;
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

