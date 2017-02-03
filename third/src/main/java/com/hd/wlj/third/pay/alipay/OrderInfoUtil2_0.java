package com.hd.wlj.third.pay.alipay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OrderInfoUtil2_0 {
	
	/**
	 * 构造授权参数列表
	 * 
	 * @param pid
	 * @param app_id
	 * @param target_id
	 * @return
	 */
	public static Map<String, String> buildAuthInfoMap(String pid, String app_id, String target_id, boolean rsa2) {
		Map<String, String> keyValues = new HashMap<String, String>();

		// 商户签约拿到的app_id，如：2013081700024223
		keyValues.put("app_id", app_id);

		// 商户签约拿到的pid，如：2088102123816631
		keyValues.put("pid", pid);

		// 服务接口名称， 固定值
		keyValues.put("apiname", "com.alipay.account.auth");

		// 商户类型标识， 固定值
		keyValues.put("app_name", "mc");

		// 业务类型， 固定值
		keyValues.put("biz_type", "openservice");

		// 产品码， 固定值
		keyValues.put("product_id", "APP_FAST_LOGIN");

		// 授权范围， 固定值
		keyValues.put("scope", "kuaijie");

		// 商户唯一标识，如：kkkkk091125
		keyValues.put("target_id", target_id);

		// 授权类型， 固定值
		keyValues.put("auth_type", "AUTHACCOUNT");

		// 签名类型
		keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");

		return keyValues;
	}

	/**
	 * 构造支付订单参数列表
	 * @param ab
	 * @return
	 */
	public static Map<String, String> buildOrderParamMap(AlipayBean ab)     {
		Map<String, String> keyValues = new HashMap<String, String>();
		//支付宝分配给开发者的应用ID
		keyValues.put("app_id", ab.getApp_id());
		//业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
		try { keyValues.put("biz_content", getOrderInfo(ab)); } catch (JSONException e) { e.printStackTrace(); }
		//商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
		keyValues.put("sign_type", ab.getSign_type());//rsa2 ? "RSA2" : "RSA"
		// 	发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"
		keyValues.put("timestamp",ab.getTimestamp());
		//支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https
		keyValues.put("notify_url", ab.getNotify_url());

		//请求使用的编码格式，如utf-8,gbk,gb2312等
		keyValues.put("charset", "utf-8");
		// 	接口名称
		keyValues.put("method", "alipay.trade.app.pay");
		//调用的接口版本，固定为：1.0
		keyValues.put("version", "1.0");

		return keyValues;
	}

	/**
	 *
	 * @return
	 * @throws JSONException
     */
	private static String getOrderInfo(AlipayBean ab) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。  128
		jsonObject.put("body",ab.getBody());
		//商品的标题/交易标题/订单标题/订单关键字等。 256
		jsonObject.put("subject",ab.getSubject());
		//商户网站唯一订单号 64
		jsonObject.put("out_trade_no",ab.getOut_trade_no());
		//订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
		jsonObject.put("total_amount",ab.getTotal_amount());
		/**
		 *------------------- 固定值
		 */
		//销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
		jsonObject.put("product_code","QUICK_MSECURITY_PAY");
		/**
		 *---------------- 非必填
		 */
		//该笔订单允许的最晚付款时间，非必填。默认30分钟，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
		jsonObject.put("timeout_express","30m");
		//收款支付宝用户ID,非必填。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
//		jsonObject.put("seller_id","");
		//商品主类型：0—虚拟类商品，1—实物类商品 注：虚拟类商品不支持使用花呗渠道
//		jsonObject.put("goods_type","0");
		//公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝  512
		jsonObject.put("passback_params",ab.getPassback_params());
		//优惠参数 注：仅与支付宝协商后可用  512
//		jsonObject.put("promo_params","0");
		//业务扩展参数，详见下面的“业务扩展参数说明”
//		jsonObject.put("extend_params","0");
		// 商户门店编号  32
		jsonObject.put("store_id",ab.getStore_id());

		/**
		 * 还有非必填参数没写入完
		 */
		return jsonObject.toString();
	}

	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 对支付参数信息进行签名
	 * 
	 * @param map
	 *            待签名授权信息
	 * 
	 * @return
	 */
	public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}
	
	/**
	 * 要求外部订单号必须唯一。
	 * @return
	 */
	private static String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

}
