package com.e3mall.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.e3mall.conmon.utils.FastDFSClient;


public class FastDFSTest {
	 
	@Test
	public void FastDFSUpload() throws Exception {
		//1. 在src/main/resources/conf  文件夹下创建 client.conf资源配置文件  名字任意  内容为tracker服务器的地址
		//2. 全局对象加载配置文件
		ClientGlobal.init("E:\\maven-workspace\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
		//3. 创建 TrackerClient 对象
		TrackerClient client = new TrackerClient();
		//4. 通过TrackerClient 对象 返回TrackerServer 对象
		TrackerServer trackerServer = client.getConnection();
		//5. 创建StorageServer 值为null
		StorageServer storageServer = null; 
		//6. 创建一个StorageClient，参数需要TrackerServer和StrorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer); 
		//7. 使用StorageClient上传文件
		String[] uploadfile = storageClient.upload_file("C:\\Users\\Administrator\\Pictures\\图片\\15120G12052-4.jpg", "jpg", null);
		for (String string : uploadfile) {
			System.out.println(string);
		}
		
	}
	
	@Test
	public void testFastDFSClient() throws Exception{
		//1. 使用工具类加载配置文件
		FastDFSClient fastDFSClient  = new FastDFSClient("E:/maven-workspace/e3-manager-web/src/main/resources/conf/client.conf");
		//2. 指定图片文件
		String string = fastDFSClient.uploadFile("C:\\Users\\Administrator\\Pictures\\图片\\demo-1-bg.jpg", "jpg");
		//3. 上传成功返回字符串
		System.out.println(string);
	}
	
}
