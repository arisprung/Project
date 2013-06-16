package com.tailgate;

public class MessageBean
{

	private long id;
	private String username;
	private String message;
	private String imei;
	private String time;

	public MessageBean()
	{

	}

	public MessageBean(String username, String imei, String message, String time)
	{
		super();
		this.imei = imei;
		this.username = username;
		this.message = message;
		this.time = time;
	}

	public long getId()
	{
		return id;
	}

	public String getImei()
	{
		return imei;
	}

	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "User: " + username + " Message: " + message;
	}

}
