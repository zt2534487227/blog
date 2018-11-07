package com.zt.blog.common.util;


import com.google.common.collect.Lists;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

	public static String httpPost(String url, Map<String, Object> paramMap) {
		if (url == null || url.trim().length() == 0)
			return null;
		CloseableHttpClient httpClient =HttpClientBuilder.create()
				.setDefaultConnectionConfig(ConnectionConfig.custom()
						.setCharset(Charset.forName("utf-8"))
						.build())
				.setDefaultRequestConfig(RequestConfig.custom()
						.setSocketTimeout(2000)
						.setConnectTimeout(6000)
						.build())
				.build();
		HttpPost post = new HttpPost(url);
		if (paramMap != null) {
			List<NameValuePair> nvps = Lists.newArrayList();
			paramMap.forEach((k,v)->{
				nvps.add(new BasicNameValuePair(k, (String) v));
			});
			post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		}
		BasicHttpContext context = new BasicHttpContext();
		try {
			HttpResponse res = httpClient.execute(post, context);
			String result = doWithRespAsString(res);
			EntityUtils.consume(res.getEntity());
			return result;
		} catch (IOException e) {
		    e.printStackTrace();
			return null;
		} finally {
			try {
				post.releaseConnection();
				httpClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	private static String doWithRespAsString(HttpResponse res) throws IOException {
		HttpEntity en = res.getEntity();
		InputStream is = en.getContent();
		byte[] bs = new byte[2048];
		StringBuffer sb = new StringBuffer();
		int cnt = 0;
		while (true) {
			cnt = is.read(bs);
			if (cnt == -1) {
				break;
			}
			sb.append(new String(bs, 0, cnt, Consts.UTF_8));
		}
		is.close();
		return sb.toString();
	}


}
