package graph;

/**
 * 带权有向图的单源最短路径算法Dijkstra和任意两个顶点最短路径算法Floyd
 * 
 * @author yinger
 */
public class DijkstraAndFloydAlgorithm {

	public static final int INFINITE = 1000;

	public static void main(String[] args) {
		int[][] distance = new int[][] { { 0, 10, INFINITE, 30, 100 }, { INFINITE, 0, 50, INFINITE, INFINITE },
				{ INFINITE, INFINITE, 0, INFINITE, 10 }, { INFINITE, INFINITE, 20, 0, 60 },
				{ INFINITE, INFINITE, INFINITE, INFINITE, 0 } };
		System.out.println("Dijkstra Algorithm:");
		dijkstra(distance, 0);
		System.out.println("Floyd Algorithm:");
		floyd(distance, 1);
	}

	// dijkstra algorithm: the source node is giving
	private static void dijkstra(int[][] distance, int source) {
		int nodeSum = distance.length;
		int[] visit = new int[nodeSum];// identify whether the node has been visited
		int[] lowDist = new int[nodeSum];// lowDist[i] = j means min distance of node i to the tree is j
		int[] lowNode = new int[nodeSum];// lowNode[i] = j means node i into the tree via node j
		for (int i = 0; i < nodeSum; i++) {
			lowDist[i] = distance[source][i];
			lowNode[i] = source;
		}
		lowNode[source] = -1;// do this in order to get the path! give it an end
		int minDist, minNode, visitNodeSum = 0, currentNode;//
		visit[source] = 1;
		visitNodeSum++;
		while (visitNodeSum < nodeSum) {// util all the nodes
			minDist = INFINITE;
			minNode = source;
			for (int i = 0; i < nodeSum; i++) {// get the min Node and Distance
				if (visit[i] == 0 && lowDist[i] < minDist) {// not visit and dist is less
					minDist = lowDist[i];
					minNode = i;
				}
			}
			if (minNode == source) {// for other nodes,no way can be accessed!
				break;// node that not print means no way can reach it from the giving source node
			}
			visit[minNode] = 1;
			visitNodeSum++;
			System.out.print("Node= " + minNode + "  Distance= " + minDist + "  Path= " + minNode + " <- ");
			currentNode = minNode;
			while (true) {// print path:in order to get more helpful result
				if (lowNode[lowNode[currentNode]] != -1) {
					System.out.print(lowNode[currentNode] + " <- ");
				} else {
					System.out.print(lowNode[currentNode]);//
					break;
				}
				currentNode = lowNode[currentNode];
			}
			System.out.println();
			for (int i = 0; i < nodeSum; i++) {// modify the distance
				if (lowDist[i] > lowDist[minNode] + distance[minNode][i]) {
					lowDist[i] = lowDist[minNode] + distance[minNode][i];
					lowNode[i] = minNode;
				}
			}
		}
	}

	// floyd algorithm: get the min distance from the giving source node
	private static void floyd(int[][] distance, int source) {
		int nodeSum = distance.length;
		String[][] path = new String[nodeSum][nodeSum];// get the path
		for (int i = 0; i < nodeSum; i++) {
			for (int j = 0; j < nodeSum; j++) {
				path[i][j] = distance[i][j] == INFINITE ? "" : " " + i + " ->" + j + " ";
			}
		}
		for (int i = 0; i < nodeSum; i++) {// get min distance between any two nodes
			for (int j = 0; j < nodeSum; j++) {
				for (int k = 0; k < nodeSum; k++) {
					if (distance[i][j] > distance[i][k] + distance[k][j]) {
						distance[i][j] = distance[i][k] + distance[k][j];
						path[i][j] = path[i][k] + path[k][j];
					}
				}
			}
		}
		for (int i = 0; i < nodeSum; i++) {
			if (i == source) {
				continue;
			}
			System.out.println("Node= " + i + "  Distance= " + distance[source][i] + "  Path= " + path[source][i]);
		}
	}
}

// result
// Dijkstra Algorithm:
// Node= 1 Distance= 10 Path= 1 <- 0
// Node= 3 Distance= 30 Path= 3 <- 0
// Node= 2 Distance= 50 Path= 2 <- 3 <- 0
// Node= 4 Distance= 60 Path= 4 <- 2 <- 3 <- 0
// Floyd Algorithm:
// Node= 1 Distance= 10 Path= 0 ->1
// Node= 2 Distance= 50 Path= 0 ->3 3 ->2
// Node= 3 Distance= 30 Path= 0 ->3
// Node= 4 Distance= 60 Path= 0 ->3 3 ->2 2 ->4
