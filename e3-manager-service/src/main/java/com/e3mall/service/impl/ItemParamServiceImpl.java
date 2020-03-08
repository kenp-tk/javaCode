package com.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e3mall.conmon.pojo.EasyUIDataGridResult;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.mapper.TbItemParamExMapper;
import com.e3mall.mapper.TbItemParamMapper;
import com.e3mall.pojo.TbItemParam;
import com.e3mall.pojo.TbItemParamExample;
import com.e3mall.pojo.TbItemParamExample.Criteria;
import com.e3mall.service.ItemParamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 *   规格参数  服务
 * @author Administator
 *
 */


@Service
public class ItemParamServiceImpl implements ItemParamService {
	
	@Autowired
	private TbItemParamMapper tbItemParamMapper;
	@Autowired
	private TbItemParamExMapper tbItemParamExMapper;
	

	public EasyUIDataGridResult getItemParamList(int page, int rows) {

			//设置分页信息
			PageHelper.startPage(page,rows);
			
			//执行查询语句
			List<TbItemParam> list = tbItemParamExMapper.selectItemParamList();
			
			//设置返回结果的值
			EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
			easyUIDataGridResult.setRows(list);
			
			//获取分页结果
			PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(list);
			//获取总记录数
			long total = pageInfo.getTotal();
			easyUIDataGridResult.setTotal(total);
			
			return easyUIDataGridResult;
	
	}


	public E3Result getItemParamByCatId(Long cid) {
		// 设置条件
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		// 根据条件查询对应的商品类目是否存在
		List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(example);
		
//		System.out.println("Service" + list);
		
		// 判断查询的数据是否为空  有就返回数据
		if(list != null && !list.isEmpty()) {
			return E3Result.ok(list.get(0));
		}
		
		// 查询为空  正常返回
		return E3Result.ok();
	}
	
	

	public E3Result addItemParam(String itemParam, Long cid) {
		//新建一个  TbItemParam 类存储信息
		TbItemParam param = new TbItemParam();
		// 补全 ItemParam pojo类
		param.setParamData(itemParam);
		param.setItemCatId(cid);
		param.setCreated(new Date());
		param.setUpdated(new Date());
		// 将信息插入 数据库
		tbItemParamMapper.insert(param);
		//返回成功
		return E3Result.ok();
	}
	
}
