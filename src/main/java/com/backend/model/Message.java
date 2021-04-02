package com.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;


public class Message implements Serializable
{
	
	@JsonProperty("Sender")
	String sender;
	
	@JsonProperty("MessageBody")
	String text;
	
	public Message() {
		super();
	}


	@JsonProperty("Time")
	Date dt;
	
	
	
	
	public Message(String sender, String text, Date dt) {
		super();
		this.sender = sender;
		this.text = text;
		this.dt = dt;
	}

	public Message(String sender)
	{
		this.sender = sender;
	}
	
	public Message(String sender, String text)
	{
		this.sender= sender;
		this.text = text;
		this.dt = new Date();
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Date getDt() {
		return dt;
	}


	public void setDt(Date dt) {
		this.dt = dt;
	}
	
	
	
}
