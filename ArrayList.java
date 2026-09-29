package cs2321;

import java.util.Iterator;

import net.datastructures.List;

public class ArrayList<E> implements List<E> {
	
	//TODO: add class members
	public static final int CAPACITY = 16;
	private E[] data;
	private int size = 0;
	
	private class ArrayListIterator implements Iterator<E> {
		//TODO: add class members
		private int j = 0;
		private boolean removable = false;
		
		@Override
		public boolean hasNext() {
			return j < size;
		}

		@Override
		public E next() {
			return data[j++];
		}
	}

	public ArrayList() {
		this(CAPACITY);
	}

	public ArrayList(int capacity) {
		data = (E[]) new Object[capacity];
	}
	
	@Override
	public int size() {
		return size;
	}

	// Return the current capacity of array, not the number of elements.
	// Notes: The initial capacity is 16. When the array is full, the array should be doubled. 
	public int capacity() {
		return data.length;
	}
	
	
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public E get(int i) throws IndexOutOfBoundsException {
		//check if index is within valid bounds
		if (i < 0 || i >= size) {
			throw new IndexOutOfBoundsException("Invalid index: " + i);
		}
		return data[i];
	}

	@Override
	public E set(int i, E e) throws IndexOutOfBoundsException {
		if (i < 0 || i >= size) {
			throw new IndexOutOfBoundsException("Invalid index: " + i);
		}
		E temp = data[i];
		data[i] = e;
		return temp;
	}

	@Override
	public void add(int i, E e) throws IndexOutOfBoundsException {
		if (i < 0 || i > size) {
			throw new IndexOutOfBoundsException("Invalid index: " + i);
		}
		
		// double array capacity if full
		if (size == data.length) {
			E[] temp = (E[]) new Object[data.length * 2];
			for (int k = 0; k < size; k++) {
				temp[k] = data[k];
			}
			data = temp;
		}
		
		// shift elements to the right starting from the end to make room
		for (int k = size - 1; k >= i; k--) {
			data[k+1] = data[k];
		}
		
		data[i] = e;
		size++;
	}
	
	@Override
	public E remove(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= size) {
	        throw new IndexOutOfBoundsException("Invalid index: " + i);
	    }
		
		E old = data[i];
		
		// Shift all elements starting from i+1 to the left
		for (int k = i; k <= size - 2; k++) {
			data[k] = data[k + 1];
		}
		
		// Clear duplicate reference
		data[size - 1] = null;
		
		size--;
		return old;
	}


	public void addFirst(E e)  {
		add(0, e);
	}
	
	public void addLast(E e)  {
		add(size, e);
	}
	
	public E removeFirst() throws IndexOutOfBoundsException {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("List is empty");
		}
		return remove(0);
	}
	
	public E removeLast() throws IndexOutOfBoundsException {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException("List is empty");
		}
		return remove(size - 1);
	}
	
	
	@Override
	public Iterator<E> iterator() {
		return new ArrayListIterator();
	}
}
