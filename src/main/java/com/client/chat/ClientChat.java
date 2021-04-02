package com.client.chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import com.backend.model.Message;
import com.backend.model.MessageThread;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ClientChat {
	public Socket socket = null;
	public DataInputStream in = null;
	public DataOutputStream out = null;
	public String sender = null;
	public ObjectInputStream objInStream = null;
	public ObjectOutputStream objOutStream = null;
	
	public ClientChat(String address, int port)
	{
		try 
		{
			socket = new Socket(address,port);
			System.out.println("Connected");
			
			//in = new DataInputStream(System.in);			
			//out = new DataOutputStream(socket.getOutputStream());
			objInStream = new ObjectInputStream(socket.getInputStream());
			objOutStream = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Connected");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void sendMessage(Message message) throws IOException
	{
		objOutStream.writeObject(message);
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException
	{
		ClientChat ch = new ClientChat("127.0.0.1",5000);
		System.out.print("Enter Your EmailAddress : ");
		Scanner sc = new Scanner(System.in);
		ch.sender = sc.nextLine();
		
		ch.sendMessage(new Message(ch.sender));
		
		String text;
		ObjectMapper mapper = new ObjectMapper();
		Message message;
		MessageThread thread = (MessageThread)ch.objInStream.readObject();
		thread.print();
		
		//while(true) {
			// Assuming the communication to Half Duplex
			
			// infinte loop to send our messages
			while(true)
			{
				System.out.print("Me : ");
				text = sc.nextLine();
				if(text.equalsIgnoreCase("OVER_$$$"))
					break;
				message = new Message(ch.sender,text);
				ch.sendMessage(message);
			}
			// infinite loop to read all the receiving messages
			/*
			while(true)
			{
				System.out.print("Admin : ");
				message = (Message)ch.objInStream.readObject();
				System.out.print(message.getText());
				if(message.getText().equalsIgnoreCase("OVER_$$$"))
					break;
			}
			*/
		
		//}
	}
	
}
