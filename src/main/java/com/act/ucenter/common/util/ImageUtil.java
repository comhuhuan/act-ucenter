package com.act.ucenter.common.util;

import com.kpr.kui.storage.FileStorageService;

public class ImageUtil {
	
    /**
     * 获得图片访问路径
     * @return
     */
	public static String getImageFullUrl(String imgUrl){
		FileStorageService svc = ServiceFactory.getService(FileStorageService.class);
		return svc.getUrl(imgUrl);
	}
	
    /**
     * 获得图片服务器路径
     * @return
     */
	public static String getImageSiteUrl(){
		FileStorageService svc = ServiceFactory.getService(FileStorageService.class);
		return svc.getSiteUrl();
	}
}
