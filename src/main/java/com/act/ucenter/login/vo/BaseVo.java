package com.act.ucenter.login.vo;


public class BaseVo {
	private String simpleDataQuery;// 模糊查询字段
	// 页面导出必备字段 add 2017-04-12 by fmj
	private String exportType;// 导出方式[查询数据:1,全部数据:-1]
	private String fileName;// 文件名称 不需后缀名
	private String uploadInfo;// 导入信息


	public String getSimpleDataQuery() {
		return simpleDataQuery;
	}

	public void setSimpleDataQuery(String simpleDataQuery) {
		this.simpleDataQuery = simpleDataQuery;
	}


	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getUploadInfo() {
		return uploadInfo;
	}

	public void setUploadInfo(String uploadInfo) {
		this.uploadInfo = uploadInfo;
	}

}
