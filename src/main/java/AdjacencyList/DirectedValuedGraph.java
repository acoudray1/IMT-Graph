package AdjacencyList;

import java.util.ArrayList;

import GraphAlgorithms.GraphTools;
import Nodes.DirectedNode;

public class DirectedValuedGraph extends DirectedGraph<DirectedNode> {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public DirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.order = matrixVal.length;
        this.nodes = new ArrayList<>();
        for (int i = 0; i < this.order; i++) {
            this.nodes.add(i, this.makeNode(i));
        }
        for (DirectedNode n : this.getNodes()) {
            for (int j = 0; j < matrixVal[n.getLabel()].length; j++) {
            	DirectedNode nn = this.getNodes().get(j);
                if (matrixVal[n.getLabel()][j] != 0) {
                    n.getSuccs().put(nn,matrixVal[n.getLabel()][j]);
                    nn.getPreds().put(n,matrixVal[n.getLabel()][j]);
                    this.m++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost  if it is not already present in the graph
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
    	if (!this.getNodeOfList(from).getSuccs().containsKey(this.getNodeOfList(to))) {
    		this.getNodeOfList(from).addPred(getNodeOfList(from), cost);
    		this.getNodeOfList(from).addSucc(getNodeOfList(to), cost);
    	}
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(DirectedNode n : nodes){
            s.append("successors of ").append(n).append(" : ");
            for(DirectedNode sn : n.getSuccs().keySet()){
            	s.append("(").append(sn).append(",").append(n.getSuccs().get(sn)).append(")  ");
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
    
    // Calcule le chemin le plus court à partir du sommet initial du graphe
 	public int[][] getCheminPlusCourtDjikstra() {
 		int[] v = new int[this.getNbNodes()];
 		int[] p = new int[this.getNbNodes()];
 		boolean[] b = new boolean[this.getNbNodes()];
 		
 		int x = 0;
 		
 		int min = Integer.MAX_VALUE;
 		
 		for (int i = 0; i < this.getNbNodes(); i++) {
 			if (!b[i] && v[i]<min) {
 				x = i;
 				min = v[i];
 				
 			}
 			if (min < Integer.MAX_VALUE) {
 				b[x] = true;
 				for (int j = 0; j < this.getNbNodes(); j++) {
 					if (!b[j] && v[x]+this.getNodeOfList(new DirectedNode(i)).getSuccs().get(new DirectedNode(j))<v[j]) {
 						v[j] = v[x]+this.getNodeOfList(new DirectedNode(i)).getSuccs().get(new DirectedNode(j));
 						p[j] = x;
 					}
 				}
 			}
 			System.out.println("sommet : " + i + " - atteint? : " + b[i] + " - valeur : " + v[i] + " - pred : " + p[i]);
 		}
 		
 		return null;
 	}
    
    
    public static void main(String[] args) {
        int[][] matrix = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrix);
        GraphTools.afficherMatrix(matrixValued);
        DirectedValuedGraph al = new DirectedValuedGraph(matrixValued);
        System.out.println(al);

        System.out.println("\n************\nTests\n");
		System.out.println("isArc() [0][3] (expected : false) - result : " + al.isArc(new DirectedNode(0), new DirectedNode(3)));
		System.out.println("isArc() [0][2] (expected : true) - result : " + al.isArc(new DirectedNode(0), new DirectedNode(2)));
		
		al.addArc(new DirectedNode(0), new DirectedNode(3), 32);
		System.out.println("isArc() after added [0][3] (expected : true) - result : "
				+ al.isArc(new DirectedNode(0), new DirectedNode(3)));
        
        al.getCheminPlusCourtDjikstra();
    }
	
}
