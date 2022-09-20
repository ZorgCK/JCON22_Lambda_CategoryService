package one.microstream.controller;

import java.util.List;
import java.util.Optional;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import one.microstream.domain.Category;
import one.microstream.storage.DB;
import one.microstream.utils.MockupUtils;


@Controller("/categories")
public class CategoryController
{
	
	@Get()
	public List<Category> getCategories()
	{
		return DB.root.getCategories();
	}
	
	@Get("/search")
	public HttpResponse<Category> getHttpList(@Nullable @QueryValue Integer id, @Nullable @QueryValue String name)
	{
		if(id != null)
		{
			Optional<Category> optional =
				DB.root.getCategories().parallelStream().filter(c -> c.getId() == id).findFirst();
			return optional.isPresent() ? HttpResponse.ok(optional.get()) : HttpResponse.notFound();
		}
		else
		{
			Optional<Category> optional =
				DB.root.getCategories().parallelStream().filter(c -> c.getName().equals(name)).findFirst();
			return optional.isPresent() ? HttpResponse.ok(optional.get()) : HttpResponse.notFound();
		}
	}
	
	@Post("/update")
	public HttpResponse<Category> update(@Body Category category)
	{
		Optional<Category> categoryOptional =
			DB.root.getCategories().stream().filter(p -> p.getId() == category.getId()).findFirst();
		
		if(categoryOptional.isPresent())
		{
			categoryOptional.get().setName(category.getName());
			
			DB.storageManager.store(categoryOptional.get());
			
			return HttpResponse.ok(categoryOptional.get());
		}
		
		return HttpResponse.notFound();
	}
	
	@Post("/insert")
	public HttpResponse<Category> insert(@Body Category category)
	{
		Category newCategory = new Category();
		
		newCategory.setName(category.getName());
		newCategory.setId(category.getId());
		
		DB.root.getCategories().add(newCategory);
		DB.storageManager.store(DB.root.getCategories());
		
		return HttpResponse.ok(newCategory);
	}
	
	@Delete("/delete")
	public HttpResponse<Category> delete(@Nullable @QueryValue Integer id)
	{
		Optional<Category> categoryOptional =
			DB.root.getCategories().stream().filter(p -> p.getId() == id).findFirst();
		
		if(categoryOptional.isPresent())
		{
			Category deletedCategory = categoryOptional.get();
			
			DB.root.getCategories().remove(deletedCategory);
			DB.storageManager.store(DB.root.getCategories());
			
			HttpResponse.ok("Category has been successfully deleted");
		}
		
		return HttpResponse.notFound();
	}
	
	@Post("/init")
	public MutableHttpResponse<String> init()
	{
		List<Category> allCreatedcategries = MockupUtils.loadMockupData();
		
		DB.root.getCategories().addAll(allCreatedcategries);
		DB.storageManager.store(DB.root.getCategories());
		
		return HttpResponse.ok("Categories successfully created!");
	}
}
