package apps;

import structures.*;
import java.util.ArrayList;


public class MST {

	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {

		PartialTreeList l = new PartialTreeList();
		int counter = 0;
		for(Vertex v: graph.vertices){ //for each vertex v in graph.verticies
			//mark v belonging to t?
			PartialTree T = new PartialTree(v);
			MinHeap<PartialTree.Arc> P = T.getArcs(); 

			Vertex.Neighbor x = v.neighbors; //head of linked list of neighbors

			while(x!=null){
				PartialTree.Arc temp = new PartialTree.Arc(v, x.vertex, x.weight);
				P.insert(temp);
				P.siftDown(counter); //what does shift down do
				x=x.next;
			}
			l.append(T);
			counter++;
		}
	}

		return l;

	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {

		ArrayList<PartialTree.Arc> l = new ArrayList();  

		while(ptlist.size()>1){
			//removes first one
			PartialTree PTX = ptlist.remove();
			PartialTree.Arc PQX=null;
			PartialTree.Arc a = PTX.getArcs().getMin();
			Vertex v1 = a.v1;
			Vertex v2 = a.v2;

			lookV2(PTX,v1,v2,PQX,a);
			PQX= PTX.getArcs().deleteMin(); 

			System.out.println(PQX +" "+"is a component of the MST");

			PartialTree PTY = ptlist.removeTreeContaining(PQX.v2);    
			PTX.merge(PTY);
			l.add(PQX);
			ptlist.append(PTX);
		}

		return l;
	}

	//re-initializes the new v1 and v2 for the new ptx
	private static void lookV2(PartialTree PTX, Vertex v1,Vertex v2, PartialTree.Arc PQX, PartialTree.Arc newerA){
		while(look(v2, PTX)){
			PQX = PTX.getArcs().deleteMin();    
			newerA = PTX.getArcs().getMin();
			v1 = newerA.v1;
			v2 = newerA.v2;
		}

	}
	//looks for the next priority 
	private static boolean look(Vertex v2, PartialTree PTX) {
		while(v2 != null) {
			if(PTX.getRoot() == v2){ //root is there
				return true;
			}if(v2.equals(v2.parent)){ 
				return false;
			}
			v2 = v2.parent; //keep moving up
		}
		return false;
	}
}

