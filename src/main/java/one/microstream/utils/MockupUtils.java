package one.microstream.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.micronaut.core.io.ResourceResolver;
import io.micronaut.core.io.scan.ClassPathResourceLoader;
import one.microstream.domain.Category;


public class MockupUtils
{
	@SuppressWarnings("unchecked")
	public static List<Category> loadMockupData()
	{
		ClassPathResourceLoader loader = new ResourceResolver().getLoader(ClassPathResourceLoader.class).get();
		Optional<URL> categoryURL = loader.getResource("mockup/category.json");
		
		JSONParser categoryParser = new JSONParser();
		
		try
		{
			
			FileReader productsReader = new FileReader(new File(categoryURL.get().getFile()));
			
			// Read JSON file
			Object categoryJSON = categoryParser.parse(productsReader);
			JSONArray categoryList = (JSONArray)categoryJSON;
			// Iterate over employee array
			
			List<Category> categories = (List<Category>)categoryList.stream().map(c -> parseCategory((JSONObject)c)).collect(Collectors.toList());
			
			return categories;
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(ParseException e)
		{
			e.printStackTrace();
		}
		
		return new ArrayList<>();
	}
	
	private static Category parseCategory(JSONObject jsonCategory)
	{
		Category c = new Category();
		
		c.setId(Math.toIntExact((long)jsonCategory.get("id")));
		c.setName((String)jsonCategory.get("name"));
						
		return c;
	}
}
