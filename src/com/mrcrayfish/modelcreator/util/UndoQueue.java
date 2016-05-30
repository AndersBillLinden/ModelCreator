package com.mrcrayfish.modelcreator.util;

public class UndoQueue
{
	public static Task undoHead = null;
	public static Task redoHead = null;
	
	public static void push(Task task)
	{
		if (undoHead != null)
			undoHead.next = task;
		
		task.prev = undoHead;
		undoHead = task;
	}
	
	public static Task next() throws RedoQueueEmptyException
	{
		if (redoHead == null)
			throw new RedoQueueEmptyException();
		
		undoHead = redoHead;

		redoHead = redoHead.next;
		
		return undoHead;
	}
	
	public static Task pop() throws UndoQueueEmptyException
	{
		if (undoHead == null)
			throw new UndoQueueEmptyException();
		
		redoHead = undoHead;
		undoHead = undoHead.prev;
		
		return redoHead;
	}
		
	public abstract static class Task
	{
		Task prev = null;
		Task next = null;
		
		public abstract void undo();
		public abstract void redo();
	}

	public static class UndoQueueEmptyException extends Exception
	{
	}

	public static class RedoQueueEmptyException extends Exception
	{
	}
}
