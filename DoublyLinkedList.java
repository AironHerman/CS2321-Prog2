package cs2321;
import java.util.Iterator;

import net.datastructures.Position;
import net.datastructures.PositionalList;


public class DoublyLinkedList<E> implements PositionalList<E> {
	

	/* 
	 * Node class which should contain element and pointers to previous and next nodes
	 */
	private static class Node<E> implements Position<E> {
		//TODO: implement Node class with more fields, methods, etc. 
		private E element;
		private Node<E> prev;
		private Node<E> next;
		
		// Constructor to initialize node
		public Node(E e, Node<E> p, Node<E> n) {
			element = e;
			prev = p;
			next = n;
		}
		

		
		
		@Override
		public E getElement() {
			if (next == null) {
				throw new IllegalStateException("Position no longer valid");
			}
			return element;
		}
		
		// Getters and setters for list logic
		public Node<E> getPrev() {return prev;}
		public Node<E> getNext() {return next;}
		public void setElement(E e) {element = e;}
		public void setPrev(Node<E> p) {prev = p;}
		public void setNext(Node<E> n) {next = n;}
	}
	
	// Helper Method to sefly cast a position and check validity
	private Node<E> validate(Position<E> p) throws IllegalArgumentException {
		if (!(p instanceof Node)) {
			throw new IllegalArgumentException("Invalid position");
		}
		Node<E> node = (Node<E>) p; // Safe cast
		if (node.getNext() == null) {
			throw new IllegalArgumentException("Position is no longer in the list");
		}
		return node;
	}
		
	// Helper Method to add a new node between existing nodes
	private Position<E> addBetween(E e, Node<E> predecessor, Node<E> successor) {
		Node<E> newest = new Node<>(e, predecessor, successor);
		predecessor.setNext(newest);
		successor.setPrev(newest);
		size++;
		return newest;
	}
		
	// nodes to cap ends of the list
	private Node<E> header;
	private Node<E> trailer;
	//number of elements in the list
	private int size = 0;
	public DoublyLinkedList() {
		header = new Node<>(null, null, null);
		trailer = new Node<>(null, header, null);
		header.setNext(trailer);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Position<E> first() {
		if (isEmpty()) {
			return null;
		}
		return header.getNext();
	}

	@Override
	public Position<E> last() {
		if (isEmpty()) {
			return null;
		}
		return trailer.getPrev();
	}

	@Override
	public Position<E> before(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getPrev() == header ? null : node.getPrev();
	}

	@Override
	public Position<E> after(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return node.getNext() == trailer ? null : node.getNext();
	}

	@Override
	public Position<E> addFirst(E e) {
		return addBetween(e, header, header.getNext());
	}

	@Override
	public Position<E> addLast(E e) {
		return addBetween(e, trailer.getPrev(), trailer);
	}

	@Override
	public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e, node.getPrev(), node);
	}

	@Override
	public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		return addBetween(e, node, node.getNext());
	}

	@Override
	public E set(Position<E> p, E e) throws IllegalArgumentException {
		Node<E> node = validate(p);
		E answer = node.getElement();
		node.setElement(e);;
		return answer;
	}

	@Override
	public E remove(Position<E> p) throws IllegalArgumentException {
		Node<E> node = validate(p);
		Node<E> predecessor = node.getPrev();
		Node<E> successor = node.getNext();
		
		// Unlink the node
		predecessor.setNext(successor);
		successor.setPrev(predecessor);
		size--;
		
		E answer = node.getElement();
		// help garbage collection
		node.setElement(null);
		node.setNext(null);
		node.setPrev(null);
		
		return answer;
	}


	public E removeFirst() throws IllegalArgumentException {
		if (isEmpty()) {
			return null;
		}
		return remove(first());
	}
	
	public E removeLast() throws IllegalArgumentException {
		if (isEmpty()) {
			return null;
		}
		return remove(last());
	}
	


	/*
	 * Returns an array containing all of the elements in this list 
	 * in proper sequence (from first to last element).
	 * The returned array will be "safe" in that no references to it are maintained by this list. 
	 * (In other words, this method must allocate a new array). 
	 * The caller is thus free to modify the returned array.
	*/

	public Object [] toArray() {
		Object[] result = new Object[size];
		Position<E> current = first();
		int index = 0;
		
		while (current != null) {
			result[index++] = current.getElement();
			current = after(current);
		}
		
		return result;
	}
	

	@Override
	public Iterator<E> iterator() {
		return new ElementIterator();
	}
	
	/*
	 *Element iterator will return one element at a time  
	 */
	private class ElementIterator implements Iterator<E> {
		
		// Track current position starting at beginning
		private Position<E> cursor = first();

		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public E next() {
			if (cursor == null) {
				throw new IllegalStateException("No more elements");
			}
			
			E element = cursor.getElement();
			cursor = after(cursor);
			return element;
		}

	}
	


	
	/************************************************************************
	 * 
	 * The method positions(), PositionInterable class and PositionIterator class
	 * have been fully implemented.  
	 * It depends the methods first(), after() that you are implementing. 
	 *
	 ************************************************************************/

	@Override
	public Iterable<Position<E>> positions() {
		return new PositionIterable();
	}
	
	/*
	 * Position iterator will return one Position at a time  
	 */
	private class PositionIterable implements Iterable<Position<E>> {

		@Override
		public Iterator<Position<E>> iterator() {
			return new PositionIterator();
		}
		
	}
	
	private class PositionIterator implements Iterator<Position<E>> {
		Position<E> p=first();
		
		@Override
		public boolean hasNext() {
			return p!=null;
		}

		@Override
		public Position<E> next() {
			Position<E> cur = p;
			p = after(p);
			return cur;
		}
		
	}

}
