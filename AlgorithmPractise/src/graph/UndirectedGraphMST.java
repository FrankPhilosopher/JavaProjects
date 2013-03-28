package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 无向连通图的最小生成树算法<br>
 * Prime算法和Kruskal算法
 * 
 * @author yinger
 */
public class UndirectedGraphMST {

	public static final int INFINITE = 1000;

	public static void main(String[] args) {
		int[][] costMatrix = new int[][] { { 0, 34, 46, INFINITE, INFINITE, 19 },
				{ 34, 0, INFINITE, INFINITE, 12, INFINITE }, { 46, INFINITE, 0, 17, INFINITE, 25 },
				{ INFINITE, INFINITE, 17, 0, 38, 25 }, { INFINITE, 12, INFINITE, 38, 0, 26 },
				{ 19, INFINITE, 25, 25, 26, 0 } };
		System.out.println("Prime Algorithm:");
		prime(costMatrix);
		System.out.println("Kruskal Algorithm:");
		kruskal(costMatrix);
	}

	// prime algorithm time: O(n^2)
	private static void prime(int[][] costMatrix) {
		int nodeSum = costMatrix.length;
		int[] lowCost = new int[nodeSum];// lowCost[i] = j means the min cost of node i to the tree is j
		int[] lowNode = new int[nodeSum];// lowNode[i] = j means node i into tree via node j
		int[] visit = new int[nodeSum];// is the node has been included,it uses to avoid circle!
		for (int i = 0; i < nodeSum; i++) {// init lowcost according to costMatrix
			lowCost[i] = costMatrix[0][i];
			lowNode[i] = 0;// init
		}
		visit[0] = 1;// visit node 0,the source
		int minNode, minCost;// current node which cost is min
		for (int k = 1; k < nodeSum; k++) {// total time:nodeSum -1
			minNode = 0;
			minCost = INFINITE;
			for (int i = 0; i < nodeSum; i++) {
				if (visit[i] == 0 && lowCost[i] < minCost) {// not visit and lowcost is lower
					minNode = i;
					minCost = lowCost[i];
				}
			}
			visit[minNode] = 1;// visit the minNode
			System.out.println(minCost + " (" + lowNode[minNode] + " -> " + minNode + ")");
			for (int i = 0; i < nodeSum; i++) {// modify lowcost because of the newly insert node minNode
				if (costMatrix[minNode][i] < lowCost[i]) {
					lowCost[i] = costMatrix[minNode][i];
					lowNode[i] = minNode;
				}
			}
		}
	}

	// kruskal algorithm: time: O(e*log(e))
	private static void kruskal(int[][] costMatrix) {
		int nodeSum = costMatrix.length;
		// KruskalEdge[] edges = new KruskalEdge[];//array is not useful,because the number of edge is not sure
		ArrayList<KruskalEdge> edgeList = new ArrayList<KruskalEdge>();
		for (int i = 0; i < nodeSum; i++) {// init and sort the edge list
			for (int j = i + 1; j < nodeSum; j++) {// j>i
				if (costMatrix[i][j] != INFINITE) {
					insertNewEdge(edgeList, new KruskalEdge(i, j, costMatrix[i][j]));
				}
			}
		}
		// for (KruskalEdge kruskalEdge : edgeList) {//print edge list in increse orders
		// System.out.println(kruskalEdge.start + " -> " + kruskalEdge.end + " " + kruskalEdge.weight);
		// }
		// int[] visit = new int[nodeSum];// is the node visit? to avoid the circle//wrong method!
		List<KruskalTree> treeList = new ArrayList<KruskalTree>();
		for (int i = 0; i < nodeSum; i++) {// init tree:only self node in the tree
			KruskalTree tree = new KruskalTree(i);
			tree.treeNodes.add(new KruskalNode(i));
			treeList.add(tree);
		}
		KruskalEdge edge;
		KruskalTree startTree, endTree;
		while (treeList.size() > 1) {
			edge = edgeList.remove(0);
			startTree = findTree(treeList, edge.start);
			endTree = findTree(treeList, edge.end);
			// System.out.println(edge.start + "  " + edge.end + "  " + startTree.root + "  " + endTree.root);//test
			if (startTree.root == endTree.root) {// the two nodes from the same tree,
				continue;// circle will appear if the edge is inserted!
			}// else,the two trees are not same,then move tree nodes from one to another
			if (startTree.treeNodes.size() < endTree.treeNodes.size()) {// from the less one to the more one
				for (int i = 0; i < startTree.treeNodes.size(); i++) {
					endTree.treeNodes.add(startTree.treeNodes.get(i));
				}
				treeList.remove(startTree);
			} else {
				for (int i = 0; i < endTree.treeNodes.size(); i++) {
					startTree.treeNodes.add(endTree.treeNodes.get(i));
				}
				treeList.remove(endTree);
			}
			System.out.println(edge.weight + " (" + edge.start + " -> " + edge.end + ")");
		}
	}

	private static KruskalTree findTree(List<KruskalTree> treeList, int key) {// find the giving node in which tree
		for (int i = 0; i < treeList.size(); i++) {
			List<KruskalNode> treeNodes = treeList.get(i).treeNodes;
			for (KruskalNode kruskalNode : treeNodes) {
				if (kruskalNode.id == key) {
					return treeList.get(i);
				}
			}
		}
		return treeList.get(0);// default!
	}

	private static void insertNewEdge(ArrayList<KruskalEdge> edgeList, KruskalEdge kruskalEdge) {// using insert sort
		edgeList.add(kruskalEdge);// first insert to make size++
		int low = 0;
		int high = edgeList.size() - 2;// attention here: the last one not in! otherwise,exception will appear
		int mid;
		while (low <= high) {// find position:low
			mid = (low + high) / 2;
			if (edgeList.get(mid).weight > kruskalEdge.weight) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		for (int i = edgeList.size() - 1; i > low; i--) {// move back
			edgeList.set(i, edgeList.get(i - 1));
		}
		edgeList.set(low, kruskalEdge);
	}
}

class KruskalEdge {// edge in kruskal algorithm
	int start;
	int end;
	int weight;

	public KruskalEdge(int start, int end, int weight) {
		this.start = start;
		this.end = end;
		this.weight = weight;
	}

}

class KruskalNode {// node in kruskal algorithm

	int id;

	public KruskalNode(int id) {
		this.id = id;
	}

}

class KruskalTree {// tree in kruskal algorithm

	public KruskalTree(int root) {
		this.root = root;
	}

	int root;// root is the first node in the tree,in order to identify a tree
	List<KruskalNode> treeNodes = new ArrayList<KruskalNode>();// kruskal graph linked part:tree,node id saved!

}

// result:
// Prime Algorithm:
// 19 (0 -> 5)
// 25 (5 -> 2)
// 17 (2 -> 3)
// 26 (5 -> 4)
// 12 (4 -> 1)
// Kruskal Algorithm:
// 12 (1 -> 4)
// 17 (2 -> 3)
// 19 (0 -> 5)
// 25 (2 -> 5)
// 26 (4 -> 5)
