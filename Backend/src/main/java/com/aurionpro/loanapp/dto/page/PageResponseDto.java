package com.aurionpro.loanapp.dto.page;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PageResponseDto<T> {
	private List<T> content;
	private int number;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean isFirst;
	private boolean isLast;
	private boolean hasNext;
	private boolean hasPrevious;
}
