package xyz.urffer.urfquest.shared;

import java.util.ArrayList;

import xyz.urffer.urfquest.shared.message.Message;

public class MessageQueue {
	
	private ArrayList<Message> messages = new ArrayList<Message>();
	
	public synchronized Message poll() {
		if (messages.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		Message m = messages.remove(0);
		return m;
	}
	
	public synchronized void add(Message m) {
		messages.add(m);
		notify();
	}
	
	public synchronized boolean isEmpty() {
		return messages.isEmpty();
	}
	
	public synchronized int size() {
		return messages.size();
	}

}
