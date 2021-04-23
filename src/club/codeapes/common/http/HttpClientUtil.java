package club.codeapes.common.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 模拟http请求工具类
 * @author LinCH
 */
public class HttpClientUtil {
	
	/**
	 * GET 请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url) throws Exception{
		return doGet(url,"utf-8");
	}
	/**
	 * GET 请求
	 * @param url
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url,String encoding) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringBuffer sb = new StringBuffer();
		/**/
		if(url == null || url.length() == 0){
			System.out.println("missing 'url' attribute.");
		}
		try{
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
                HttpEntity entity = response.getEntity();
                // do something useful with the response body
                BufferedReader reader = null;
                InputStreamReader is = null;
                try{
                	is = new InputStreamReader(response.getEntity().getContent(),encoding);
                	reader = new BufferedReader(is);
                	String line = reader.readLine();
    				while (line != null) {
    					sb.append(new String(line.getBytes()));
    					line = reader.readLine();
    				}
                }finally{
                	if(is != null){
                		is.close();
                	}
                	if(reader!=null){
                		reader.close();
                	}
                }
                EntityUtils.consume(entity);
            } finally {
            	response.close();
            }
		}finally {
			httpClient.close();
        }
		return sb.toString();
	}
	
	/**
	 * POST 请求
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url) throws Exception{
		return doPost(url,null,null,"utf-8");
	}
	/**
	 * POST 请求
	 * @param url
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,String encoding) throws Exception{
		return doPost(url,null,null,encoding);
	}
	
	/**
	 * POST 请求
	 * @param url
	 * @param params
	 * @param files
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPost2(String url,Map<String,Object> params,Map<String,File> files,String encoding) throws Exception{
		List<NameValuePair> nvps = null;
		if(params!=null){
			nvps = new ArrayList<NameValuePair>();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(),entry.getValue()!=null?entry.getValue().toString():""));
			}
		}
		return doPost(url,nvps,files,encoding);
	}
	/**
	 * POST 请求
	 * @param url
	 * @param params
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static String doPost2(String url,Map<String,Object> params,Map<String,File> files)throws Exception{
		return doPost2(url,params,files,"utf-8");
	}
	/**
	 * POST 请求
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception 
	 */
	public static String doPost2(String url,Map<String,Object> params,String encoding) throws Exception{
		return doPost2(url,params,null,encoding);
	}
	/**
	 * POST 请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public static String doPost2(String url,Map<String,Object> params) throws Exception{
		return doPost2(url,params,"utf-8");
	}
	/***
	 * POST 请求
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,List<NameValuePair> params)throws Exception{
		return doPost(url,params,null,"utf-8");
	}
	
	/**
	 * POST 请求
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,List<NameValuePair> params,String encoding) throws Exception{
		return doPost(url,params,null,encoding);
	}
	/**
	 * POST 请求
	 * @param url
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,Map<String,File> files)throws Exception{
		return doPost(url,null,files,"utf-8");
	}
	/**
	 * POST 请求
	 * @param url
	 * @param files
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,Map<String,File> files,String encoding)throws Exception{
		return doPost(url,null,files,encoding);
	}
	/**
	 * POST 请求
	 * @param url
	 * @param params
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,List<NameValuePair> params,Map<String,File> files)throws Exception{
		return doPost(url,params,files,"utf-8");
	}
	/**
	 * POST 请求
	 * @param url
	 * @param params
	 * @param files
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url,List<NameValuePair> params,Map<String,File> files,String encoding)throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		StringBuffer sb = new StringBuffer();
		/**/
		if(url == null || url.length() == 0){
			System.out.println("missing 'url' attribute.");
		}
		try{
			HttpPost httpPost = new HttpPost(url);
			/* 设置参数 */
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			Charset chars = Charset.forName(encoding); // Setting up the encoding
			builder.setCharset(chars);
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			
			if(params!=null){
				for(NameValuePair nvp : params){
					builder.addTextBody(nvp.getName(), nvp.getValue(),ContentType.create("text/plain", encoding.toUpperCase()));
				}
				
			}
			if(files!=null && files.size()>0){
				for (Map.Entry<String, File> entry : files.entrySet()) {
					builder.addPart(entry.getKey(), new FileBody(entry.getValue(),ContentType.APPLICATION_OCTET_STREAM,entry.getValue().getName()));
				}
			}
			HttpEntity reqEntity = builder.build();// 生成 HTTP POST 实体        
			httpPost.setEntity(reqEntity);//设置请求参数  
			/**/
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				// 获取响应对象
	            HttpEntity entity = response.getEntity();
                // do something useful with the response body
                BufferedReader reader = null;
                InputStreamReader is = null;
                try{
                	is = new InputStreamReader(response.getEntity().getContent(),encoding);
                	reader = new BufferedReader(is);
                	String line = reader.readLine();
    				while (line != null) {
    					sb.append(new String(line.getBytes()));
    					line = reader.readLine();
    				}
                }finally{
                	if(is != null){
                		is.close();
                	}
                	if(reader!=null){
                		reader.close();
                	}
                }
                EntityUtils.consume(entity);
            } finally {
            	response.close();
            }
		}finally {
			httpClient.close();
        }
		return sb.toString();
	}
}
