package com.tailgate;

public class LocationBean
{

	private long id;
	private String imei;
	private String username;
	private String latitude;
	private String longitude;

	public LocationBean()
	{

	}

	public LocationBean(String username,String imei, String latitude, String longitude)
	{
		super();
		this.imei = imei;
		this.username = username;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	

	public String getImei()
	{
		return imei;
	}

	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public long getId()
	{
		return id;
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

	public String getLatitude()
	{
		return latitude;
	}

	public void setLatitude(String latitude)
	{
		this.latitude = latitude;
	}

	public String getLongitude()
	{
		return longitude;
	}

	public void setLongitude(String longitude)
	{
		this.longitude = longitude;
	}

	@Override
	public String toString()
	{
		return "user: "+username+" "+"longnitude: "+longitude+"Latitude: "+latitude+"IMEI"+imei;
	}
}
