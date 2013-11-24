import java.util.ArrayList;
public class Sensor extends Target{
	double cost;
	ArrayList<Target> targets = new ArrayList<Target>();
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
}
