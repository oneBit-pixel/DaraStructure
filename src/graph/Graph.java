package graph;

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {

    private ArrayList<String> vertexList;//存储顶点集合
    private int[][] edges;//存储图对应的邻结矩阵
    private int numOfEdges;
    //定义给数组boolean[],记录某个结点是否被访问
    private boolean[] isVisited;


    //构造器
    public static void main(String[] args) {
        //测试一把图是否创建
        int n = 5;//结点的个数
        String Vertexs[] = {"A", "B", "C", "D", "E"};
        //创建一个对象
        Graph graph = new Graph(n);
        //循环的添加结点
        for (String vertex : Vertexs) {
            graph.insertVertex(vertex);
        }
        //添加边
        //A-B A-C B-C B-D B-E
        graph.insertEdge(0, 1, 1);//A-B
        graph.insertEdge(0, 2, 1);//A-B
        graph.insertEdge(1, 2, 1);//A-B
        graph.insertEdge(1, 3, 1);//A-B
        graph.insertEdge(1, 4, 1);//A-B

        //显示一把邻结举证
        graph.showGraph();

        //测试一把，我们的dfs遍历是否ok
        System.out.println("深度遍历");
        graph.dfs();
    }

    //构造器
    public Graph(int n) {
        //初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<String>(n);
        numOfEdges = 0;
        isVisited=new boolean[5];
    }

    //得到第一个领接结点的下标
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    //根据前一个邻接结点下标来获取下一个邻接结点
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    //深度优先遍历算法
    //i 第一次就是 0
    public void dfs(boolean[] isVisited, int i) {
        //首先访问该结点，输出
        System.out.print(getValueByIndex(i) + "->");
        //将结点设置为已经访问
        isVisited[i] = true;
        //查找结点i的第一个捆绑结点i
        int w = getFirstNeighbor(i);
        while (w != -1) {
            //说明有
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            //如果这个w结点已经被访问过
            w = getNextNeighbor(i, w);
        }
    }

    //对dfs 进行一个重载,遍历我们所有的结点并进行dfs
    public void dfs() {
        //遍历所有的结点，进行dfs[回溯]
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
    }

    //图中 常用的方法
    //返回的结点个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //显示图对应的矩阵
    public void showGraph() {
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    //得到的边的数目
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //返回结点i（下标） 对应值的数据 0->"A" 1->"B" 2->"C"
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    //返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }
    //插入结点


    //插入结点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    //添加边

    /**
     * @param v1     表示点的下标,即是第几个顶点 "A"-"B" "A“->0 "B"->1
     * @param v2     第二个顶点对应的下标
     * @param weight 表示
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

}
