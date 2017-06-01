package com.springboot.fastdfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.util.StringUtils;

import com.springboot.fastdfs.comm.utils.FastDFSUtils;
import com.springboot.fastdfs.comm.utils.UploadUtils;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
    
	public static int port=8080;
	
	public static void main(String[] args ) throws Exception{
		//本程序端口、文件服务器地址、文件服务器端口、文件大小	eg. 8080 192.168.1.100 22122 100 
		if(args.length>0){
			String port = args[0];
			String trackerAddress = args[1];
			String trackerPort = args[2];
			String maxSize = args[3];
			//
			FastDFSUtils.trackerAddress = new String[(args.length/2-1)];
			FastDFSUtils.trackerPort = new int[(args.length/2-1)];
			
			App.setPort(port);
			App.setTrackerServer(0,trackerAddress, trackerPort);
			App.setMaxSize(maxSize);
			
			if(args.length>=6){
				int j = 1;
				for(int i=4;i<args.length;i++){
					String tAddress = args[i];
					String tPort = args[i+=1];
					App.setTrackerServer(j,tAddress, tPort);
					j+=1;
				}
			}
		}
        SpringApplication.run(App.class, args);  
        System.out.println( "-----> start success..." );
    }

	/**
	 * 端口修改
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		container.setPort(App.port);
	}
	/**
	 * 
	 * @param port
	 * @throws Exception 
	 */
	public static void setPort(String port) throws Exception{
		if(StringUtils.isEmpty(port)){
			throw new Exception("应用的端口号必须填写");
		}
		App.port=Integer.parseInt(port);
	}
	/**
	 * 
	 * @param maxSize
	 * @throws Exception
	 */
	public static void setMaxSize(String maxSize) throws Exception{
		if(StringUtils.isEmpty(maxSize)){
			throw new Exception("文件大小必须填写");
		}
		int size = Integer.parseInt(maxSize);
		if(size<=0){
			throw new Exception("文件大小必须大于0");
		}
		UploadUtils.maxSize = (size*1024*1024)+"";
	}
	
	/**
	 * 
	 * @param trackerAddress
	 * @param trackerPort
	 * @throws Exception 
	 */
	public static void setTrackerServer(int i,String trackerAddress,String trackerPort) throws Exception{
		if(StringUtils.isEmpty(trackerAddress)||StringUtils.isEmpty(trackerPort)){
			throw new Exception("调度服务器的地址和端口必须填写");
		}
		FastDFSUtils.trackerAddress[i] = trackerAddress;
		FastDFSUtils.trackerPort[i] = Integer.parseInt(trackerPort);
	}
}
