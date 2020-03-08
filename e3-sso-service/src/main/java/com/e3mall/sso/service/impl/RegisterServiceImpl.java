package com.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.e3mall.conmon.utils.E3Result;
import com.e3mall.mapper.TbUserMapper;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.pojo.TbUserExample.Criteria;
import com.e3mall.sso.service.RegisterService;

/**
 * 		用户注册处理Service
 * 
 * */

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private TbUserMapper tbUserMapper;
	
	@Override
	public E3Result checkData(String param, int type) {
		// 根据传入的不同type  生成不同的查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		// 传入的type值对应的信息  1.用户名 2.手机号  3.邮箱
		if(type == 1) {
			criteria.andUsernameEqualTo(param);
		} else if(type == 2) {
			criteria.andPhoneEqualTo(param);
		} else if(type == 3) {
			criteria.andEmailEqualTo(param);
		} else {
			return E3Result.build(400, "数据类型错误");
		}
		//执行查询
		List<TbUser> list = tbUserMapper.selectByExample(example);
		//判断查询结果是否为空
		if(list != null && list.size() > 0) {
			//查询到了数据内容 
			return E3Result.ok(false);
		}
		//没有在数据库中查到数据  返回true
		return E3Result.ok(true);
	}
 
	@Override
	public E3Result register(TbUser user) {
		//进行数据有效性校验
			if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())) {
				return E3Result.build(400, "用户数据不完整,插入信息失败");
			}
			// 数据有效性校验 1.用户名 2.手机号  3.邮箱
			E3Result result = checkData(user.getUsername(), 1);
			if(!(boolean) result.getData()) {
				return E3Result.build(400, "用户名被占用");
			}
			result = checkData(user.getPhone(), 2);
			if(!(boolean) result.getData()) {
				return E3Result.build(400, "手机号已存在");
			}
		// 补全  pojo对象  的属性数据
		user.setCreated(new Date());
		user.setUpdated(new Date());
		// 对密码数据进行  MD5 加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		// 把用户数据插入到数据库中
		tbUserMapper.insert(user);
		// 返回注册成功
		return E3Result.ok();
	}

}
