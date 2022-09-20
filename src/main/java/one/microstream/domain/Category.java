package one.microstream.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public class Category
{
	private int		id;
	private String	name;
	
	public Category()
	{
		
	}
	
	public Category(int id, String name)
	{
		super();
		this.id = id;
		this.name = name;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
}
