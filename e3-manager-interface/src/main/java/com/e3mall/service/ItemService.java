package com.e3mall.service;

import java.util.List;

import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.pojo.TbItem;
import com.e3mall.pojo.TbItemDesc;

public interface ItemService {
	public TbItem getItemById(long itemId);
	public EasyUIDataGridResult getItemList(int page, int rows);
	public E3Result addItem(TbItem item, String desc,String itemParams);
	public E3Result deleteItem(List<Long> ids);
	public E3Result updateItem(TbItem item, String desc);
	public TbItemDesc getItemDescById(long itemDescId);
}
