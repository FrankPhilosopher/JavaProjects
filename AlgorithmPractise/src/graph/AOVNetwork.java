package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AOVÍøµÄÍØÆËÅÅÐò
 * 
 * @author yinger
 */
public class AOVNetwork {

	public static void main(String[] args) {
		int nodeSum = 6;
		AovEdge edge1 = new AovEdge(1, 0);
		AovEdge edge2 = new AovEdge(1, 3);
		AovEdge edge3 = new AovEdge(2, 0);
		AovEdge edge4 = new AovEdge(2, 3);
		AovEdge edge5 = new AovEdge(3, 0);
		AovEdge edge6 = new AovEdge(3, 5);
		AovEdge edge7 = new AovEdge(4, 2);
		AovEdge edge8 = new AovEdge(4, 3);
		AovEdge edge9 = new AovEdge(4, 5);
		List<AovEdge> edgeList = new ArrayList<AovEdge>();
		Collections.addAll(edgeList, edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8, edge9);

		AovNode[] linkTable = new AovNode[nodeSum];
		for (int i = 0; i < nodeSum; i++) {// init linkTable
			linkTable[i] = new AovNode(i);
		}
		buildLinkTable(linkTable, edgeList);
		topsort(linkTable);
	}

	// get graph adjust link table according the edge list
	private static void buildLinkTable(AovNode[] linkTable, List<AovEdge> edgeList) {
		AovEdge edge;
		for (int i = 0; i < edgeList.size(); i++) {
			edge = edgeList.get(i);
			linkTable[edge.start].linkNodes.add(linkTable[edge.end]);
			linkTable[edge.end].inDegree++;// in degree!
		}
		// for (int i = 0; i < linkTable.length; i++) {//test linktable
		// System.out.print("Node= " + i + "  LinkNodes= ");
		// for (int j = 0; j < linkTable[i].linkNodes.size(); j++) {
		// System.out.print(linkTable[i].linkNodes.get(j).id+"  ");
		// }
		// System.out.println();
		// }
	}

	private static void topsort(AovNode[] linkTable) {
		int nodeSum = linkTable.length;
		int[] visit = new int[nodeSum];// visit = 0:not in stack,not visit;=1: visited;=2:in stack,not visit
		ArrayDeque<AovNode> deque = new ArrayDeque<AovNode>();
		for (int i = 0; i < nodeSum; i++) {
			if (visit[i] == 0 && linkTable[i].inDegree == 0) {// node with in degree = 0 should be in deque
				deque.addLast(linkTable[i]);
				visit[i] = 2;// in stack,but not visit!
			}
		}
//		System.out.println();//test deque
//		System.out.print("Deque: ");
//		for (AovNode aovNode : deque) {// test deque
//			System.out.print(aovNode.id + "  ");
//		}
//		System.out.println();
		AovNode node, linkNode;
		while ((node = deque.pollLast()) != null) {
			visit[node.id] = 1;// visit the node
			System.out.print(node.id + " - ");
			for (int i = 0; i < node.linkNodes.size(); i++) {
				linkNode = node.linkNodes.get(i);
				if (visit[linkNode.id] != 1) {// visited node not concern
					linkTable[linkNode.id].inDegree--;
				}
			}
			for (int i = 0; i < nodeSum; i++) {
				if (visit[i] == 0 && linkTable[i].inDegree == 0) {// node with in degree = 0 should be in deque
					deque.addLast(linkTable[i]);
					visit[i] = 2;// in stack,but not visit!
				}
			}
		}
	}

}

class AovEdge {

	int start;
	int end;

	public AovEdge(int start, int end) {
		this.start = start;
		this.end = end;
	}

}

class AovNode {

	int id;
	int inDegree;// in degree of the node
	List<AovNode> linkNodes = new ArrayList<AovNode>();

	public AovNode(int id) {
		this.id = id;
	}

}
