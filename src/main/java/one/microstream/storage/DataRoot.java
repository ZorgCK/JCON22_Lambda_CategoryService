
package one.microstream.storage;

import java.util.ArrayList;
import java.util.List;

import one.microstream.domain.Category;


/**
 * MicroStream data root. Create your data model from here.
 * 
 * @see <a href="https://docs.microstream.one/manual/">Reference Manual</a>
 */
public class DataRoot
{
	private List<Category>	categories	= new ArrayList<Category>();
	private boolean			firstStart	= true;
	
	public List<Category> getCategories()
	{
		return categories;
	}
	
	public void setCategories(List<Category> categories)
	{
		this.categories = categories;
	}
	
	public boolean isFirstStart()
	{
		return firstStart;
	}
	
	public void setFirstStart(boolean firstStart)
	{
		this.firstStart = firstStart;
	}
	
}
