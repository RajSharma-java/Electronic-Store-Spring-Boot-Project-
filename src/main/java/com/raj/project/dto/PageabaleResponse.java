package com.raj.project.dto;

import java.util.List;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageabaleResponse<T>
{
	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPage;
	private boolean lastPage;

}
