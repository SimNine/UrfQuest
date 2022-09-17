package xyz.urffer.urfquest.shared;

import java.util.ArrayList;

import xyz.urffer.urfquest.shared.protocol.Packet;

public class MessageQueue {
	
	private ArrayList<Packet> messages = new ArrayList<Packet>();
	
	public synchronized Packet poll() {
		if (messages.isEmpty())
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
		Packet m = messages.remove(0);
		return m;
	}
	
	public synchronized void add(Packet m) {
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
