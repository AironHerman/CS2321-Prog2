package cs2321;

import net.datastructures.*;

/*
 * You are tasked with designing a simple text editor that allows users to insert characters, 
 * move the cursor left or right, and retrieve the current state of the text. In this case,
 * the text/characters are all integers. Meaning, we are basically creating a number editor using
 * the DLL with a cursor that can go left or right (see examples below for help understanding).
 * 
 * 
 * Background: We are recreating commands from vi (visual editor) in Linux through the DLL.
 * Here are some commands of vi:
 *  1. w  - Press w to move the cursor to the right one word (integer in our case) at a time.
 *  2. b  - Press b to move the cursor to the left one word at a time.
 *  3. cw - To replace a word, position the cursor at the beginning of the word to be replaced.
 *           Type cw, followed by the new word. To finish, press Esc.
 *  4. i  - Insert text to the left of the cursor by typing i from command mode.
 *  5. dw -To delete a word, position the cursor at the beginning of the word and type dw. 
 *           The word and the space it occupied are removed.
 * 
 * 
 * - Example 0: Start with 1, 2, 3 and cursor at 2. 
 *              Left. cursor points at 1.
 * 
 * - Example 1: Start with 1, 2, 3 and cursor at 2. 
 *              Insert 4 before. We now have 1, 4, 2, 3, with cursor at 4.
 * 
 * - Example 2: Start with 1, 2, 3 and cursor at 2. 
 *              replace value with 4. We now have 1, 4, 3, with cursor at 4
 * 
 * - Example 3: Start with 1, 2, 3 and cursor at 2. 
 *              Delete. We now have 1, 3 with cursor at 3 (cursor shifts right if right is not null, otherwise left -- see Example 4 for other scenario).
 * 
 * - Example 4: Start with 1, 2, 3 and cursor at 3. 
 *              Delete. We now have 1, 2 with cursor at 2.
 * 
 */

public class NumberEditor {

	DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>(); 
	Position<Integer> cursor = null;


	/**
	 * Inserts the number at the position before or after the cursor. 
	 * When inserting, if the cursor is null, then the DLL is empty,
	 * so be sure to handle this case.
	 */
	public void insertBefore(int n) {
		if (cursor == null) {
			cursor = list.addFirst(n);
		}
		else {
			cursor = list.addBefore(cursor,  n);
		}
	}
	
	public void insertAfter(int n) {
		if (cursor == null) {
			cursor = list.addFirst(n);
		}
		else {
			cursor = list.addAfter(cursor,  n);
		}
	}


	/**
	 * Delete the number under the cursor. Cursor will point to the NEXT number.
	 * If there is no number after it, the cursor will point to previous. 
	 * If there is no previous, then cursor will not point to any number.
	 */
	public void delete() {
		if (cursor == null) {
			return;
		}
		
		Position<Integer> nextPos = list.after(cursor);
		Position<Integer> prevPos = list.before(cursor);
		
		list.remove(cursor);
		
		if (nextPos != null) {
			cursor = nextPos;
		}
		else if (prevPos != null) {
			cursor = prevPos;
		}
		else {
			cursor = null;
		}
	}


	/** 
	 * Replace the number under the cursor.
	 */
	public void replace(Integer n) {
		list.set(cursor,  n);
	}


	/**
	 * Moves the cursor one position to the left.
	 * Consider the case where the cursor is already at the leftmost position.
	 * Consider the case where DLL is empty.
	 */
	public void left() {
		if (cursor != null) {
			Position<Integer> prevPos = list.before(cursor);
			if (prevPos!= null)
			{
				cursor = prevPos;
			}
		}
	}


	/**
	 * Moves the cursor one position to the right.
	 * Consider the case where the cursor is already at the rightmost position.
	 * Consider the case where DLL is empty.
	 */
	public void right() {
		if (cursor != null) {
			Position<Integer> nextPos = list.after(cursor);
			if (nextPos!= null)
			{
				cursor = nextPos;
			}
		}
	}


	/**
	 * Retrieves the number under the cursor with return type int.
	 *
	 * @return the number under the cursor. 
	 *          Throw IllegalStateException exception if there is no number.
	 */
	public int get() throws IllegalStateException  {
		if (cursor == null) {
			throw new IllegalStateException("No number under cursor");
		}
		return cursor.getElement();
	}
	

	/**
	 * @return the array of numbers in the list in sequential order
	 */
	
	public int[] numbers() {
		int[] result = new int[list.size()];
		Position<Integer> current = list.first();
		int index = 0;
		
		while (current != null) {
			result[index++] = current.getElement();
			current = list.after(current);
		}
	
		return result;

	}


}
