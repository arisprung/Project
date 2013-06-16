package com.tailgate;

public class TeamBean
{
	
	private long id;
	private String teamname;
	
	public TeamBean ()
	{
		
	}
	
	
	public TeamBean(long id, String teamname)
	{
		super();
		this.id = id;
		this.teamname = teamname;
	}
	
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getTeamname()
	{
		return teamname;
	}
	public void setTeamname(String teamname)
	{
		this.teamname = teamname;
	}
	
	
	

}
