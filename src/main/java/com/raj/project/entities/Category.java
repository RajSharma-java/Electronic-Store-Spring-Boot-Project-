package com.raj.project.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "category")
public class Category
{
	@Id
	@Column(name = "categoryId")
	private String id;
	
	private String title;
	private String description;
	private String coverImage;
	
	@OneToMany(mappedBy = "category",cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Product> products= new ArrayList<>();
}
