package com.client.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.backend.model.Message;
import com.backend.model.MessageThread;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminChatbox 
{
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		DataInputStream in = null;
		DataOutputStream out = null;
		ObjectInputStream objectIn = null;
		Socket socket = new Socket("127.0.0.1",5000);
		String sender="ADMIN";
		Message message ;
		MessageThread thread;
		String text;
		
		ObjectMapper mapper = new ObjectMapper();
		
		in = new DataInputStream(System.in);
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("All THREADS");
		
		out.writeUTF(sender);
		
		objectIn = new ObjectInputStream(socket.getInputStream());
		
		thread = (MessageThread)objectIn.readObject();
		
		thread.print();
	
		
		
		
		while(true)
		{
			System.out.print("Me : ");
			text = in.readLine();
			
			out.writeUTF(mapper.writeValueAsString(new Message(sender,text)));
			if(text.equals("$$$"))
				break;
		}
		socket.close();
		in.close();
		out.close();
		objectIn.close();
	}
}
