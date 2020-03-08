package com.e3mall.service;

import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;



public interface ItemParamService {
	// 获取 ItemParam相关表中的全部数据信息
	public EasyUIDataGridResult getItemParamList(int page, int rows);
	// 查询对应的商品类目
	public E3Result getItemParamByCatId(Long cid);
	// 向商品目录添加规格参数
	public E3Result addItemParam(String itemParam, Long cid);
	// 修改商品
	
}
