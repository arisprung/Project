package com.tailgate.json;

public class JSONBean
{
	private String Command = null;
	private String RequestType = null;
	private int RequestID;
	private String Message = null;

	public JSONBean(String command, String requestType, int requestID, String message)
	{
		super();
		Command = command;
		RequestType = requestType;
		RequestID = requestID;
		Message = message;
	}

	public String getCommand()
	{
		return Command;
	}

	public void setCommand(String command)
	{
		Command = command;
	}

	public String getRequestType()
	{
		return RequestType;
	}

	public void setRequestType(String requestType)
	{
		RequestType = requestType;
	}

	public int getRequestID()
	{
		return RequestID;
	}

	public void setRequestID(int requestID)
	{
		RequestID = requestID;
	}

	public String getMessage()
	{
		return Message;
	}

	public void setMessage(String message)
	{
		Message = message;
	}

}