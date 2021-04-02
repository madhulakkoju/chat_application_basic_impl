package com.client.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.backend.model.Message;
import com.backend.model.MessageThread;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientChatBox 
{
	public static void main(String args[]) throws UnknownHostException, IOException, ClassNotFoundException 
	{
		DataInputStream in = null;
		DataOutputStream out = null;
		ObjectInputStream objectIn = null;
		Socket socket = new Socket("127.0.0.1",5000);
		String sender=null;
		Message message ;
		MessageThread thread;
		String text;
		
		ObjectMapper mapper = new ObjectMapper();
		
		in = new DataInputStream(System.in);
		out = new DataOutputStream(socket.getOutputStream());
		System.out.println("Enter your email address");
		sender=in.readLine();
		out.writeUTF(sender);
		
		
		objectIn = new ObjectInputStream(socket.getInputStream());
		
		thread = (MessageThread)objectIn.readObject();
		
		thread.print();
	
		
		
		
		System.out.println("Send all queries. Admin will revert Back to you shortly");
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
