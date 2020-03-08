package com.e3mall.order.controller.pay;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.e3mall.order.service.OrderService;
import com.e3mall.pojo.TbOrder;

@Controller
public class AlipayController {
	
	@Value("${APP_ID}")
	private String APP_ID;
	@Value("${APP_PRIVATE_KEY}")
	private String APP_PRIVATE_KEY;
	@Value("${CHARSET}")
	private String CHARSET;
	@Value("${ALIPAY_PUBLIC_KEY}")
	private String ALIPAY_PUBLIC_KEY;
	@Value("${GATEWAY_URL}")
	private String GATEWAY_URL;
	@Value("${FORMAT}")
	private String FORMAT;
	@Value("${SIGN_TYPE}")
	private String SIGN_TYPE;
	@Value("${NOTIFY_URL}")
	private String NOTIFY_URL;
	@Value("${RETURN_URL}")
	private String RETURN_URL;
	
	@Autowired
	private OrderService orderService;
	
	 
	@RequestMapping("/alipay")
	public void alipay(HttpServletResponse httpResponse) throws IOException {
		//实例化客户端,填入所需参数
		AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
		AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
		
		//在公共参数中设置回跳和通知地址
        request.setReturnUrl(RETURN_URL);
        request.setNotifyUrl(NOTIFY_URL);
        
        List<TbOrder> list = orderService.getListByUserId(5l);
        
        TbOrder order = list.get(0);
        //生成订单Id
        String out_trade_no = order.getOrderId();
        //付款金额
        String total_amount = order.getPayment();
        //订单名称，必填 
        String subject ="华为 HUAWEI Mate 30 Pro 麒麟990旗舰芯片OLED环幕屏双4000万徕卡电影四摄8GB+128GB亮黑色4G全网通手机";
        //商品描述，可空
        String body = "尊敬的用户欢迎购买华为 HUAWEI Mate 30 Pro";
        request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
	}
	
}
