import java.util.ArrayList;
import java.lang.Math;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DynamicSensor
{
	static ArrayList<Target> targetList = new ArrayList<Target>();
	static ArrayList<Sensor> sensorList = new ArrayList<Sensor>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File myFile = new File("Sensor.txt");
		Scanner scan;
		String line;
		try {
			scan = new Scanner(myFile);
			while(scan.hasNext())
			{
				line=scan.nextLine();
				parseLine(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

