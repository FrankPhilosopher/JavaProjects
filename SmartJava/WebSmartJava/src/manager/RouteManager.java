package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import tool.DataBaseTool;
import entity.Distribution;
import entity.Orders;
import entity.Route;

public class RouteManager {

	public static final int MAXLENGTH = 100000000;

	// 添加路线,每次添加了某个起点配送点到相邻配送点的路线，则自动添加从相邻配送点到起点的路线
	public int add(Route route) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "insert into route(sDistribution,tDistribution,name,length) values('"
				+ route.getsDistribution()
				+ "', '"
				+ route.gettDistribution()
				+ "','" + route.getName() + "','" + route.getLength() + "')";
		/*
		 * String sql1 =
		 * "insert into route(sDistribution,tDistribution,name,length) values('"
		 * + route.gettDistribution() + "', '" + route.getsDistribution() +
		 * "','" + route.getName() + "','" + route.getLength() + "')";
		 */
		int count = DataBaseTool.executeSQL(dbConn, sql);
		// int count1 = DataBaseTool.executeSQL(dbConn, sql1);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	/*
	 * 修改路线 根据两个hashMap中输入，进行部分更新 hashMap:要修改的数据项及修改值 hashMap2：查询待修改项的范围
	 */
	public int update(HashMap<String, String> hashMap,
			HashMap<String, String> hashMap2) {

		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("update route set ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append(entry.getKey() + "='" + entry.getValue() + "',");
		}
		sql.delete(sql.length() - 1, sql.length());
		sql.append(" where ");
		for (Entry<String, String> entry : hashMap2.entrySet()) {
			sql.append(entry.getKey() + "='" + entry.getValue() + "' and");
		}
		sql.delete(sql.length() - 4, sql.length());
		int result = DataBaseTool.executeSQL(dbConn, sql.toString());
		return result;
	}

	// 根据Route进行update
	public int update(Route route) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = " update route set sDistribution="
				+ route.getsDistribution() + ",tDistribution="
				+ route.gettDistribution() + ",name='" + route.getName()
				+ "',length=" + route.getLength() + " where id="
				+ route.getId();
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据id进行delete
	public int delete(int carid) {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "delete from route where id=" + carid;
		int count = DataBaseTool.executeSQL(dbConn, sql);
		DataBaseTool.close(null, null, dbConn);
		return count;
	}

	// 根据map进行search -- 组合查询
	public ArrayList<Route> searchByMap(HashMap<String, String> hashMap)
			throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		StringBuffer sql = new StringBuffer("select * from route where 1=1 ");
		for (Entry<String, String> entry : hashMap.entrySet()) {
			sql.append("and " + entry.getKey() + "=" + entry.getValue() + " ");
		}
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql.toString());
		Route route = null;
		ArrayList<Route> list = new ArrayList<Route>();
		while (rs.next()) {
			route = new Route();
			route.setId(rs.getInt("id"));
			route.setsDistribution(rs.getInt("sDistribution"));
			route.settDistribution(rs.getInt("tDistribution"));
			route.setName(rs.getString("name"));
			route.setLength(rs.getInt("length"));
			list.add(route);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 根据id进行search
	public Route searchById(int id) throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from route where id=" + id;
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		Route route = null;
		while (rs.next()) {
			route = new Route();
			route.setId(rs.getInt("id"));
			route.setsDistribution(rs.getInt("sDistribution"));
			route.settDistribution(rs.getInt("tDistribution"));
			route.setName(rs.getString("name"));
			route.setLength(rs.getInt("length"));
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return route;
	}

	// 全部search
	public ArrayList<Route> search() throws SQLException {
		Connection dbConn = DataBaseTool.getConnection();
		String sql = "select * from route";
		ResultSet rs = DataBaseTool.executeQuery(dbConn, sql);
		ArrayList<Route> list = new ArrayList<Route>();
		Route route = null;
		while (rs.next()) {
			route = new Route();
			route.setId(rs.getInt("id"));
			route.setsDistribution(rs.getInt("sDistribution"));
			route.settDistribution(rs.getInt("tDistribution"));
			route.setName(rs.getString("name"));
			route.setLength(rs.getInt("length"));
			list.add(route);
		}
		DataBaseTool.close(rs, rs.getStatement(), dbConn);
		return list;
	}

	// 为订单生成最短路径 -- 使用dijska算法
	public String buildShortestPath(Orders order) {
		int sid = order.getsDistribution();
		int tid = order.gettDistribution();
		// 路径path
		StringBuffer path = new StringBuffer();//path保存路径中节点的名称
		StringBuffer pathId = new StringBuffer();//pathid保存路径中节点的id
		// 得到所有的路线
		ArrayList<Route> routes = new ArrayList<Route>();
		try {
			routes = search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 得到所有的配送点
		DistributionManager distributionManager = new DistributionManager();
		ArrayList<Distribution> distributions = new ArrayList<Distribution>();
		try {
			distributions = distributionManager.search();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 生成图的节点
		// ArrayList<GraphNode> nodeList = new ArrayList<GraphNode>();
		HashMap<Integer, GraphNode> nodeMap = new HashMap<Integer, GraphNode>();
		for (int i = 0; i < distributions.size(); i++) {
			GraphNode graphNode = new GraphNode(i,
					distributions.get(i).getId(), distributions.get(i)
							.getName());
			// nodeList.add(graphNode);
			nodeMap.put(distributions.get(i).getId(), graphNode);
		}
		// 构造有向图的邻接矩阵
		int nodenum = distributions.size();// 图中节点的总数
		int[][] distance = new int[nodenum][nodenum];// 距离权值数组
		int[] visit = new int[nodenum];// 标识节点是否已经加入了
		for (int i = 0; i < nodenum; i++) {
			for (int j = 0; j < nodenum; j++) {
				distance[i][j] = MAXLENGTH;// 初始化为很大，无穷，表示不可达！
			}
			visit[i] = 0;// 初始化为0，表示没有
		}
		Route route = null;
		for (int i = 0; i < routes.size(); i++) {
			route = routes.get(i);
			GraphNode node1 = nodeMap.get(route.getsDistribution());
			GraphNode node2 = nodeMap.get(route.gettDistribution());
			distance[node1.id][node2.id] = route.getLength();
		}
		// printdistance(distance);//测试生成的邻接矩阵
		// 初始化算法所需要的信息，从sid寻找路径
		GraphNode sNode = nodeMap.get(sid);
		GraphNode tNode = nodeMap.get(tid);
		visit[sNode.id] = 1;// 加入了起始点
		int currentNodeId = sNode.id;// 当前到达的节点
		int minlen, minid = currentNodeId;// minlen是当前的最短距离，minid是当前的距离最短的节点id
		int[] mindistance = new int[nodenum];// 各个节点到起点的最短距离
		for (int i = 0; i < nodenum; i++) {
			mindistance[i] = distance[currentNodeId][i];// 初始化为各个节点到起点之间的权值
		}
		ArrayList<GraphNode> pathList = new ArrayList<GraphNode>();// 路径的节点集合
		pathList.add(sNode);
		path.append(sNode.name+"->");
		pathId.append(sNode.distributionId+"-");
		boolean flag = true;//表示是否存在最短路径
		// 算法开始
		while (currentNodeId != tNode.id) {// 还没有到达终点
			minlen = MAXLENGTH;// 初始化为最大的值
			for (int i = 0; i < nodenum; i++) {// 求出距离最近的还没有访问过的节点
				if (visit[i] == 0 && mindistance[i] < minlen) {
					minid = i;
					minlen = distance[currentNodeId][i];
				}
			}
			// 可能没有minid，表示没有最短路径
			if (minid == currentNodeId) {
				flag = false;//false表示没有路径
				break;
			}
			// 找到minid，那么将这个minid加入进来
			visit[minid] = 1;// 标识这个节点已经被访问了
			currentNodeId = minid;// 重置当前节点
			for (int i = 0; i < visit.length; i++) {// 更新其他节点的最近距离
				if (mindistance[i] > (mindistance[currentNodeId] + distance[currentNodeId][i])) {
					mindistance[i] = mindistance[currentNodeId]
							+ distance[currentNodeId][i];
				}
			}
			for (Entry<Integer, GraphNode> entry : nodeMap.entrySet()) {
				if (entry.getValue().id == currentNodeId) {
					if(currentNodeId != tNode.id){
						path.append(entry.getValue().name+"->");
						pathId.append(entry.getValue().distributionId+"-");
					}else{
						path.append(entry.getValue().name);
						pathId.append(entry.getValue().distributionId);
					}
				}
			}
		}
		if (!flag) {
			order.setPath("");
			return "根据当前系统中的路线，"+sNode.name+"不能到达"+tNode.name;
		}else{
			order.setPath(pathId.toString());//后台数据库中path
			return path.toString();//前台要显示的string
		}
	}

	private void printdistance(int[][] distance) {
		for (int i = 0; i < distance.length; i++) {
			for (int j = 0; j < distance.length; j++) {
				System.out.print(distance[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		RouteManager routeManager = new RouteManager();
		Orders orders = new Orders();
		orders.setsDistribution(27);
		orders.settDistribution(36);
		String pahtString = routeManager.buildShortestPath(orders);
		System.out.println(pahtString);
		System.out.println(orders.getPath());
	}

}

// 图的节点
class GraphNode {

	int id;// 节点的编号
	int distributionId;// 配送点的id
	String name;// 配送点的名称

	// 构造方法
	public GraphNode(int id, int distributionId, String name) {
		this.id = id;
		this.distributionId = distributionId;
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GraphNode) {
			return ((GraphNode) obj).distributionId == this.distributionId;// 如果distributionId相同即相同
		}
		return false;
	}

}
