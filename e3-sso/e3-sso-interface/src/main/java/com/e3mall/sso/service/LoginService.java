package com.e3mall.sso.service;

import com.e3mall.conmon.utils.E3Result;

public interface LoginService {
	public E3Result userLogin(String username,String password);
}
