import java.util.Arrays;

public class FindMST {
    private Edge[] MST; // Minimum Spanning Tree Array
    private int totalCostMST; // Total cost of the Minimum Spanning Tree
    private int numberOfVertices; // Number of vertices in the graph
    private Subset[] unionFindSubsets; // Subsets for union-find operations

    /** Constructor */
    public FindMST(Edge[] edges) {
        numberOfVertices = 0;
        for (Edge edge : edges) {
            numberOfVertices = Math.max(numberOfVertices, Math.max(edge.getVertex1(), edge.getVertex2()) + 1);
        }

        // Initialize MST and subsets array
        MST = new Edge[numberOfVertices - 1];
        unionFindSubsets = new Subset[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            unionFindSubsets[i] = new Subset();
            unionFindSubsets[i].parent = i;
            unionFindSubsets[i].rank = 0;
        }
    }

    /** Main calculate Minimum Spanning Tree method */
    public void calculateMST(Edge[] edges) {
        // Sort edges by weight
        Arrays.sort(edges);

        int edgeIndex = 0; // Index of the current edge being considered
        int mstIndex = 0; // Index of the current edge being added to MST

        while (mstIndex < numberOfVertices - 1 && edgeIndex < edges.length) {
            Edge nextEdge = edges[edgeIndex++];

            int x = find(unionFindSubsets, nextEdge.getVertex1());
            int y = find(unionFindSubsets, nextEdge.getVertex2());

            if (x != y) {
                MST[mstIndex++] = nextEdge;
                union(unionFindSubsets, x, y);
            }
        }

        // Calculate total cost of MST
        totalCostMST = 0;
        for (Edge edge : MST) {
            totalCostMST += edge.getWeight();
        }
    }

    /** Get cost of Minimum Spanning Tree */
    public int getMstCost() {
        return totalCostMST;
    }

    /** Get calculated Minimum Spanning Tree Array */
    public Edge[] getMST() {
        return MST;
    }

    /** Print Minimum Spanning Tree */
    public void printMST() {
        System.out.println("Minimum Spanning Tree");
        System.out.println("weight: " + totalCostMST);
        System.out.println("Edge List:");
        for (Edge edge : MST) {
            System.out.println(edge);
        }
    }

    /** Find operation in union-find */
    private int find(Subset[] subsets, int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    /** Union operation in union-find */
    private void union(Subset[] subsets, int x, int y) {
        int xRoot = find(subsets, x);
        int yRoot = find(subsets, y);

        if (subsets[xRoot].rank < subsets[yRoot].rank) {
            subsets[xRoot].parent = yRoot;
        } else if (subsets[xRoot].rank > subsets[yRoot].rank) {
            subsets[yRoot].parent = xRoot;
        } else {
            subsets[yRoot].parent = xRoot;
            subsets[xRoot].rank++;
        }
    }
}
