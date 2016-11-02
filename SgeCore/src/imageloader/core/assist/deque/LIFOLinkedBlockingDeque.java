package imageloader.core.assist.deque;

import java.util.NoSuchElementException;

/**
 * {@link LinkedBlockingDeque} using LIFO algorithm
 * 
 */
public class LIFOLinkedBlockingDeque<T> extends LinkedBlockingDeque<T> {

	private static final long serialVersionUID = -4114786347960826192L;
	@Override
	public boolean offer(T e) {
		return super.offerFirst(e);
	}

	/**
	 * Retrieves and removes the first element of this deque. This method differs from {@link #pollFirst pollFirst} only
	 * in that it throws an exception if this deque is empty.
	 * 
	 * @return the head of this deque
	 * @throws NoSuchElementException
	 *             if this deque is empty
	 */
	@Override
	public T remove() {
		return super.removeFirst();
	}
}