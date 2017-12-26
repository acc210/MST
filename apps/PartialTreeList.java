package apps;

import java.util.Iterator;
import java.util.NoSuchElementException;

import structures.Vertex;


public class PartialTreeList implements Iterable<PartialTree> {

	/**
	 * Inner class - to build the partial tree circular linked list 
	 * 
	 */
	public static class Node {
		/**
		 * Partial tree
		 */
		public PartialTree tree;

		/**
		 * Next node in linked list
		 */
		public Node next;

		/**
		 * Initializes this node by setting the tree part to the given tree,
		 * and setting next part to null
		 * 
		 * @param tree Partial tree
		 */
		public Node(PartialTree tree) {
			this.tree = tree;
			next = null;
		}
	}

	/**
	 * Pointer to last node of the circular linked list
	 */
	private Node rear;

	/**
	 * Number of nodes in the CLL
	 */
	private int size;

	/**
	 * Initializes this list to empty
	 */
	public PartialTreeList() {
		rear = null;
		size = 0;
	}

	/**
	 * Adds a new tree to the end of the list
	 * 
	 * @param tree Tree to be added to the end of the list
	 */
	public void append(PartialTree tree) {
		Node ptr = new Node(tree);
		if (rear == null) {
			ptr.next = ptr;
		} else {
			ptr.next = rear.next;
			rear.next = ptr;
		}
		rear = ptr;
		size++;
	}

	/**
	 * Removes the tree that is at the front of the list.
	 * 
	 * @return The tree that is removed from the front
	 * @throws NoSuchElementException If the list is empty
	 */
	public PartialTree remove() 
			throws NoSuchElementException {


		if(rear==null){  //nothing there 
			throw new NoSuchElementException();
		}else{
			size--; 
		}

		if(rear.next==rear){ //one thing left 
			Node temp = rear;
			this.rear = null;
			return temp.tree;
		}else{
			Node temp = this.rear.next; //first one
			Node ptrn = this.rear.next.next;
			this.rear.next = ptrn;
			return temp.tree;
		}

		/* COMPLETE THIS METHOD */



	}

	/**
	 * Removes the tree in this list that contains a given vertex.
	 * 
	 * @param vertex Vertex whose tree is to be removed
	 * @return The tree that is removed
	 * @throws NoSuchElementException If there is no matching tree
	 */
	public PartialTree removeTreeContaining(Vertex vertex) 
			throws NoSuchElementException {

		if(rear==null){
			throw new NoSuchElementException();
		}
		Node ptr= rear.next;
		Node prev= rear;
		int i=0;
		PartialTree finalee=null;
		boolean find= false;

		finalee= removeT(i,vertex,ptr,prev,find,finalee);


		/* COMPLETE THIS METHOD */
		return finalee;

	}

	private PartialTree removeT(int i, Vertex v, Node ptr,Node prev, boolean find, PartialTree finalee){

		do{
			if(recur(v,ptr.tree,find)){
				if(ptr==rear){
					finalee=ptr.tree;
					prev.next=rear.next;
					rear=prev;
					size--;
					return finalee;
				}else{
					finalee=ptr.tree;
					prev.next=ptr.next;
					size--;
					return finalee;
				}
			}
			prev=ptr;
			ptr=ptr.next;
			i++;
		}while(i<size);

		return finalee;
	}

	private boolean recur(Vertex v, PartialTree T, boolean found){
		while(v!=null){
			if(v==T.getRoot()){
				found=true;
				return true;
			}if(v.equals(v.parent)){
				return false;
			}
			v=v.parent;
		}
		return false;
	}

	/**
	 * Gives the number of trees in this list
	 * 
	 * @return Number of trees
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns an Iterator that can be used to step through the trees in this list.
	 * The iterator does NOT support remove.
	 * 
	 * @return Iterator for this list
	 */
	public Iterator<PartialTree> iterator() {
		return new PartialTreeListIterator(this);
	}

	private class PartialTreeListIterator implements Iterator<PartialTree> {

		private PartialTreeList.Node ptr;
		private int rest;

		public PartialTreeListIterator(PartialTreeList target) {
			rest = target.size;
			ptr = rest > 0 ? target.rear.next : null;
		}

		public PartialTree next() 
				throws NoSuchElementException {
			if (rest <= 0) {
				throw new NoSuchElementException();
			}
			PartialTree ret = ptr.tree;
			ptr = ptr.next;
			rest--;
			return ret;
		}

		public boolean hasNext() {
			return rest != 0;
		}

		public void remove() 
				throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}

	}
}


