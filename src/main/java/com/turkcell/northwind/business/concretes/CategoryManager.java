package com.turkcell.northwind.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.northwind.business.abstracts.CategoryService;
import com.turkcell.northwind.business.dtos.CategoryListDto;
import com.turkcell.northwind.business.requests.CreateCategoryRequest;
import com.turkcell.northwind.core.utilities.mapping.ModelMapperService;
import com.turkcell.northwind.dataAccess.abstracts.CategoryDao;
import com.turkcell.northwind.entities.concretes.Category;

@Service
public class CategoryManager implements CategoryService {

	private CategoryDao categoryDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CategoryManager(CategoryDao categoryDao, ModelMapperService modelMapperService) {
		this.categoryDao = categoryDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<CategoryListDto> getAll() {
		
		List<Category> result = categoryDao.findAll();
		
		List<CategoryListDto> response = result.stream()
				.map(category -> this.modelMapperService.forDto().map(category, CategoryListDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void add(CreateCategoryRequest createCategoryRequest) {
		
		Category category = modelMapperService.forRequest().map(createCategoryRequest, Category.class);

		this.categoryDao.save(category);

	}

}
