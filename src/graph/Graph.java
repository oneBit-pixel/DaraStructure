package graph;

import java.util.ArrayList;
import java.util.Arrays;

public class Graph {

    private ArrayList<String> vertexList;//存储顶点集合
    private int[][] edges;//存储图对应的邻结矩阵
    private int numOfEdges;

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
    }

    public Graph(int n) {
        //初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<String>(n);
        numOfEdges = 0;
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
