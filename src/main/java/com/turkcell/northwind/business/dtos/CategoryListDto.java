package com.turkcell.northwind.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryListDto {
	
	private int categoryId;
	
	private String categoryName;
}
