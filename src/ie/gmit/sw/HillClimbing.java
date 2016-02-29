package ie.gmit.sw;

import java.util.*;

public class HillClimbing {
	private LinkedList<Node> queue = new LinkedList<Node>();
	private List<Node> closed = new ArrayList<Node>();
	
	public void search(Node node){
		queue.addFirst(node);
		while(!queue.isEmpty()){
			// this is deepest ascent hill climbing, the algorithm will 
			// not terminate until hit the goal node
			// *** nb ***
			queue.removeFirst();
			closed.add(node);
			System.out.print(node.getNodeName());
			if (node.isGoalNode()){
				System.out.println("Reached goal node " + node.getNodeName() + " after " + calcTotalDistanceTravelled() + " miles.");
				System.out.println("Path: " + closed);
				System.out.println(queue);
				System.exit(0);
			}else{
				Node[] children = node.children();
				
				Arrays.sort(children, (Node current, Node next) -> 
					- (current.getApproximateDistanceFromGoal() - next.getApproximateDistanceFromGoal()));
				
				for (int i = 0; i < children.length; i++) {
					if (!children[i].isVisited() && !queue.contains(children[i])){
						children[i].setParent(node);
						queue.addFirst(children[i]);
					}
				}
				System.out.println(queue);
				node = queue.getFirst();
				node.setVisited(true);
			}
		}
	}
	
	private int calcTotalDistanceTravelled(){
		int totalDistance = 0;
		for (int j = 0; j < closed.size(); j++) {
			Node n = closed.get(j);
			Node parent = n.getParent();
			if (parent != null){
				totalDistance = totalDistance + parent.getDistance(n);
			}
		}
		return totalDistance;
	}
	
	public static void main(String[] args) {
		IrelandMap ire = new IrelandMap();
		Node start = ire.getStartNode();
		start.setVisited(true);
		HillClimbing hc = new HillClimbing();
		hc.search(start);
	}
}
