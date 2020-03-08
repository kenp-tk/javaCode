package com.e3mall.sso.service;

import com.e3mall.conmon.utils.E3Result;


/**
 * 		根据Token查询用户信息
 * 
 * */
public interface TokenService {
	public E3Result getUserByToken(String token);
}
