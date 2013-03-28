package graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * 有向图的表示和遍历 <br>
 * 邻接矩阵和邻接表表示法和BFS/DFS
 * 
 * @author yinger
 */
public class DirectedGraphTraverse {

	public static void main(String[] args) {
		// matrix for graph --- condition:graph is directed graph
		int[][] adjustMatrix = new int[][] { { 0, 1, 1, 1, 1, 0 }, { 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 1, 0 },
				{ 1, 0, 0, 0, 1, 0 }, { 0, 0, 0, 1, 0, 0 }, { 0, 0, 0, 0, 0, 0 } };
		System.out.println("Adjust link table:");
		linkTable(adjustMatrix);
		System.out.println("BFS for adjust matrix:");
		bfs(adjustMatrix);//0  1  2  3  4  5
		System.out.println();
		System.out.println("DFS for adjust matrix:");
		dfs(adjustMatrix);//0  1  4  3  2  5 
	}

	// adjust link table
	private static void linkTable(int[][] adjustMatrix) {
		int nodeSum = adjustMatrix.length;
		GraphNode[] linkTable = new GraphNode[nodeSum];
		for (int i = 0; i < nodeSum; i++) {// init table
			linkTable[i] = new GraphNode(i);
		}
		for (int j = 0; j < nodeSum; j++) {// modify table
			for (int k = 0; k < nodeSum; k++) {
				if (adjustMatrix[j][k] == 1) {// edge exist
					linkTable[j].linkNodes.add(linkTable[k]);
				}
			}
		}
		for (int i = 0; i < nodeSum; i++) {// iterator
			System.out.print("Node: " + i + "\tLinkTable: ");
			for (GraphNode graphNode : linkTable[i].linkNodes) {
				System.out.print(graphNode.id + "  ");
			}
			System.out.println();
		}
	}

	// graph matrix dfs:not recursive
	private static void dfs(int[][] adjustMatrix) {
		int nodeSum = adjustMatrix.length;
		int[] visit = new int[nodeSum];// visit[i] = 1 means the node i has been visited
		ArrayDeque<GraphNode> deque = new ArrayDeque<GraphNode>();
		for (int i = 0; i < nodeSum; i++) {// make sure all the node has been visited,include the lonely node
			if (visit[i] == 0) {
				GraphNode current = new GraphNode(i);
				deque.addLast(current);
				while ((current = deque.pollLast()) != null) {// remove the last one
					visit(current);
					visit[current.id] = 1;
					for (int j = 0; j < nodeSum; j++) {
						if (adjustMatrix[current.id][j] == 1 && visit[j] == 0) {// adjust to current node and not visit
							deque.addLast(new GraphNode(j));// when in stack,same node will not insert twice!
							break;// insert only one node!
						}
					}
				}
			}
		}
	}

	// graph matrix bfs:not recursive
	private static void bfs(int[][] adjustMatrix) {
		int nodeSum = adjustMatrix.length;
		int[] visit = new int[nodeSum];// visit[i] = 1 means the node i has been visited
		ArrayDeque<GraphNode> deque = new ArrayDeque<GraphNode>();
		for (int i = 0; i < nodeSum; i++) {// make sure all the node has been visited,include the lonely node
			if (visit[i] == 0) {
				GraphNode current = new GraphNode(i);
				deque.addLast(current);
				while ((current = deque.pollFirst()) != null) {
					if (visit[current.id] == 0) {// insure the node has not visit
						visit(current);
						visit[current.id] = 1;
						for (int j = 0; j < nodeSum; j++) {// insert the nodes adjust current node into the deque
							if (adjustMatrix[current.id][j] == 1 && visit[j] == 0) {// if node in the deque,but not
								deque.addLast(new GraphNode(j));// visit,so it maybe insert many times!
							}
						}
					}
				}
			}
		}
	}

	private static void visit(GraphNode node) {
		System.out.print(node.id + "  ");
	}

}

class GraphNode {
	int id;// node id
	List<GraphNode> linkNodes = new ArrayList<GraphNode>();

	public GraphNode(int id) {
		this.id = id;
	}

}

// result
// Adjust link table:
// Node: 0 LinkTable: 1 2 3 4
// Node: 1 LinkTable: 4
// Node: 2 LinkTable: 4
// Node: 3 LinkTable: 0 4
// Node: 4 LinkTable: 3
// Node: 5 LinkTable:
// BFS for adjust matrix:
// 0 1 2 3 4 5
// DFS for adjust matrix:
// 0 1 4 3 2 5
