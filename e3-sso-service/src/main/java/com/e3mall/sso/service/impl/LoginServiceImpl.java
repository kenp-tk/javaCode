package com.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.e3mall.conmon.jedis.JedisClient;
import com.e3mall.conmon.utils.E3Result;
import com.e3mall.conmon.utils.JsonUtils;
import com.e3mall.mapper.TbUserMapper;
import com.e3mall.pojo.TbUser;
import com.e3mall.pojo.TbUserExample;
import com.e3mall.pojo.TbUserExample.Criteria;
import com.e3mall.sso.service.LoginService;
/**
 * 		用户登录处理
 * 
 * */
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private TbUserMapper tbUserMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	@Override
	public E3Result userLogin(String username, String password) {
		// 判断用户名和密码是否正确
			//根用户名查询用户信息
			TbUserExample example = new TbUserExample();
			Criteria criteria = example.createCriteria();
			criteria.andUsernameEqualTo(username);
			//执行查询
			List<TbUser> list = tbUserMapper.selectByExample(example);
			if(list == null && list.size() == 0) {
				//返回登录失败
				return E3Result.build(400, "用户名或密码错误");
			}
			//获得用户信息
			TbUser user = list.get(0);
			//判断密码是否正确
			if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
				//返回登录失败
				return E3Result.build(400, "用户名或密码错误");
			}
		//生成token
		String token = UUID.randomUUID().toString();
		//把用户信息写入 redis key:token value:用户信息
		user.setPassword(null);//不要将密码带到客户端
		jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(user));
		//设置  Session的过期时间
		jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
		//返回  token值
		return E3Result.ok(token);
	}

}
