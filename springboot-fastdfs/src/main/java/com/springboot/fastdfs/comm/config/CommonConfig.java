package com.springboot.fastdfs.comm.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springboot.fastdfs.comm.utils.UploadUtils;

/**
 * 此处配置spring boot的文件限制大小
 * @author xiaocai
 *
 */
@Configuration
public class CommonConfig {
	@Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(1024L * 1024L * Integer.parseInt(UploadUtils.maxSize));
        return factory.createMultipartConfig();
    }
}
