package com.server.application;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import com.backend.model.Message;
import com.backend.model.MessageThread;
import com.backend.util.MessageThreadImpl;

public class ServerApplication 
{
	public Socket socket = null;
	public DataInputStream in = null;
	public DataOutputStream out = null;
	public ServerSocket server = null;
	public ObjectInputStream objInStream = null;
	public ObjectOutputStream objOutStream = null;
	
	public MessageThreadImpl threads;
	
	
	public ServerApplication(int port)
	{
		try 
		{
			server = new ServerSocket(port);
			threads = new MessageThreadImpl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		ServerApplication serverApp = new ServerApplication(5000);
		Message message;
		MessageThread thread ;
		String sender = null;
	
		while(true)
		{
			// Accept Clients
			System.out.println("Accepted");
			serverApp.socket = serverApp.server.accept();
			System.out.println("Accepted");
			
			serverApp.objInStream = new ObjectInputStream(serverApp.socket.getInputStream());
			System.out.println("Accepted");
			serverApp.objOutStream = new ObjectOutputStream(serverApp.socket.getOutputStream());
			System.out.println("Accepted");
			message = (Message)serverApp.objInStream.readObject();
			System.out.println("Accepted");
			sender = message.getSender();
			
			if(sender.equalsIgnoreCase("admin"))
			{
				// Admin Implementation
			}
			else
			{
				// send Message Thread
				
				thread = serverApp.threads.getMessageThread(sender);
				
				serverApp.objOutStream.writeObject(thread);
				
				while(true)
				{
					System.out.print(sender +" : ");
					message = (Message)serverApp.objInStream.readObject();
					System.out.println(message.getText());
					if(message.getText().equalsIgnoreCase("OVER_$$$"))
						break;
				}
			}
			
			serverApp.objInStream.close();
			serverApp.objOutStream.close();
			serverApp.socket.close();
			
		}
		
		
		
		
		
	}
	
	
}
