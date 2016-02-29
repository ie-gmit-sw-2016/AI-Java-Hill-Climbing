package ie.gmit.sw;

import java.util.*;

public class HillClimbing {
	LinkedList<Node> queue = new LinkedList<Node>();
	List<Node> closed = new ArrayList<Node>();
	HeuristicNodeComparator sorter = new HeuristicNodeComparator();
	public void search(Node node){
		queue.addFirst(node);
		while(!queue.isEmpty()){
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
				Collections.sort(Arrays.asList(children), sorter);
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
