package com.e3mall.search.mapper;

import java.util.List;

import com.e3mall.conmon.pojo.SearchItem;

public interface ItemMapper {
	public List<SearchItem> getItemList(); 
	public SearchItem getItemById(long itemId);
}
