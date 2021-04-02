package com.server.application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.backend.model.Message;
import com.backend.model.MessageThread;
import com.backend.util.MessageThreadImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServerImplementation 
{
	public static void main(String args[]) throws IOException
	{
		DataInputStream in = null;
		DataOutputStream out = null;
		ServerSocket server = null;
		Socket socket = null;
		
		ObjectOutputStream objectOut = null;
		
		server = new ServerSocket(5000);
		
		String sender;
		Message message ;
		MessageThread thread;
		String text;
		
		ObjectMapper mapper = new ObjectMapper();
		
		MessageThreadImpl threads = new MessageThreadImpl();
		
		while(true)
		{
			socket = server.accept();
			
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(System.out);
			
			System.out.println("Accepted");
			
			sender = in.readUTF();
			System.out.println("User : "+sender);
			thread = threads.getMessageThread(sender);
			
			
			objectOut = new ObjectOutputStream(socket.getOutputStream());
			
			objectOut.writeObject(thread);
			
			
			
			//Accept all messages from Client
			while(true)
			{
				text = in.readUTF();
				message = mapper.readValue(text,Message.class);
				thread.addMessage(message);
				//out.writeUTF(sender +" : "+text+"\n");
				if(message.getText().equals("$$$"))
					break;
			}
			
			socket.close();
			socket = null;
			System.out.println("Closed socket");
			in.close();
			in=null;
			System.out.println("Closed IN");
			//out.close();
			//out =null;
			objectOut.close();
			System.out.println("Closed OUT");
			System.out.println("Closed All");
			System.out.println(threads);
			threads.print();
		}
		
		
		
	}
}
