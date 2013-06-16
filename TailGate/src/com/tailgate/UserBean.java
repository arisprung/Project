package com.tailgate;

public class UserBean
{
	private double latitude;
	private double longitude;
	private String name;
	private String status;
	private String imei;

	public UserBean(String name, String status)
	{
		super();

		this.name = name;

		this.status = status;
	}

	public UserBean(String name)
	{
		super();

		this.name = name;

	}

	public UserBean(double latitude, double longitude, String name)
	{
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
		this.imei = imei;

	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getImei()
	{
		return imei;
	}

	public void setImei(String imei)
	{
		this.imei = imei;
	}

	public double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(double latitude)
	{
		this.latitude = latitude;
	}

	public double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(double longitude)
	{
		this.longitude = longitude;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return "Name : " + name + " Status : " + status;
	}

}
