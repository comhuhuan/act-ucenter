/**   
 * @Title: CommonContant.java 
 * @Package com.act.web.constant 
 * @Description: (常量类) 
 * @author   fmj
 * @date 2017-2-6 下午12:00:49 
 * @version V1.0   
 */
package com.act.ucenter.common.constant;

public class CommonContant {

	/**
	 * 系统名称
	 */
	public static String SYSTEMNAME = "新框架";

	/**
	 * 是否显示验证码(应放在配置文件中)
	 */
	public static String LOGINISCODE = "N";

	/**
	 * session 中 用户属性
	 */
	public static final String SESSION_USERINFO = "userinfo";

	/**
	 * 非管理员nonAdmin
	 */
	public static final String NOT_ADMIN = "notAdmin";

	/**
	 * 登入用户user_Id
	 */
	public static final String USER_ID = "userId";

	/**
	 * 接收指令IP
	 */
	public static String SHAOBIN_CENTER_IP = "127.0.0.1";

	/**
	 * 首页显示趋势图的统计天数
	 */
	public static Integer DISPLAY_DATE_RANGE = 5;

	/**
	 * 本机IP
	 */
	public static final String LOCAL_IP = "local_ip";

	public static final String LOCAL_MAC = "local_mac";

	public static final String LOCAL_MACHINE = "local_machine";

	/**
	 * 实时流量刷新时间间隔
	 */
	public static Integer FLOW_REFRESH_TIME;

	/**
	 * 每页显示条数
	 */
	public static int ALARM_PAGE_SIZE = 10;

	/**
	 * 实时报警显示记录数
	 */
	public static int ALART_COUNT = 1;

	/**
	 * 实时报警刷新时间间隔
	 */
	public static int ALARM_REFRESH_TIME = 10;

	/**
	 * 通讯离线时间
	 */
	public static int LEAVE_TIME = 10;

	/**
	 * 显示查询区每行输出项数
	 */
	public static int LOG_SAVE_MONTH = 3;

	/**
	 * 显示查询区每行输出项数
	 */
	public static int TD_NUM = 4;

	/**
	 * 查询返回的数量
	 */
	public static long SECTION_NUMBER = 50000;
	
	/**
	 * 查询返回的数量,默认10w
	 */
	public static long RESULT_NUMBER = 100000;
	
	/**
	 * 是否开启coreseek搜索模式
	 */
	public static boolean CORESEEK_MODE = false;

	/**
	 * 索引返回数据是否启用排序
	 */
	public static boolean CORESEEK_IDS_SORT = false;

	/**
	 * 是否启用多库查询
	 */
	public static boolean CORESEEK_MULTI_DB = false;

	/**
	 * 警告所要显示的列
	 */
	public static String[] ALARM_CONTENT;
	
	/**
	 * 虚拟机房
	 */
	public static String VIRTUAL_HOUSE_CODE;

	/**
	 * 目前登入的用户
	 */
	public static String CURRENT_USER = "admin";
	
	/**
	 * 导出Excel
	 */
	public static final String CONTENT_TYPE = "application/force-download";
	public static final String CONTENT_TYPE_Octet = "application/octet-stream";
	
	public static final String CONTENT_DISPOSITION = "Content-Disposition";
	public static final String HEADER_VALUE = "attachment;filename=";
	/**
	 * 全国异常设备列表展示记录数量
	 */
	public static int DEVICE_NUM = 10;

	//EU状态类型
	/**
	 * EU到CU的通信状态
	 */
	public static final String EU_CU_COMM_TYPE = "1000";
	
	/**
	 * EU的CPU状态
	 */
	public static final String EU_CPURATE_TYPE = "1003";

	/**
	 * EU设备状态
	 */
	public static final String EU_DEVICE_TYPE = "1001";
	
	/**
	 * EU进程状态
	 */
	public static final String EU_PROCESS_TYPE = "1002";
	
	/**
	 * EU的CPU状态
	 */
	public static final String EU_CPURATE_TYPT = "1003";

	/**
	 * EU磁盘状态
	 */
	public static final String EU_DISK_TYPE = "1004";

	/**
	 * EU内存状态
	 */
	public static final String EU_MEMORY_TYPE = "1005";

	/**
	 * EU运行状态
	 */
	public static final String EU_RUNINFO_TYPE = "1006";

	/**
	 * EU到DU通信状态
	 */
	public static final String EU_DU_COMM_TYPE = "1007";

	/**
	 * EU链路状态
	 */
	public static final String EU_LINK_TYPE = "1008";

	/**
	 * EU网卡状态
	 */
	public static final String EU_NETWORK_TYPE = "1009";
	
	//CU状态类型
	/**
	 * CU设备状态
	 */
	public static final String CU_DEVICE_TYPE = "1010";
	
	/**
	 * CU进程状态
	 */
	public static final String CU_PROCESS_TYPE = "1011";

	/**
	 * CU的CPU状态
	 */
	public static final String CU_CPURATE_TYPE = "1012";

	/**
	 * CU磁盘状态
	 */
	public static final String CU_DISK_TYPE = "1013";

	/**
	 * CU内存状态
	 */
	public static final String CU_MEMORY_TYPE = "1014";

	/**
	 * CU运行状态
	 */
	public static final String CU_RUNINFO_TYPE = "1015";

	/**
	 * CU到管局通信状态
	 */
	public static final String CU_SMMS_COMM_TYPE = "1016";

	/**
	 * CU到DU通信状态
	 */
	public static final String CU_DU_COMM_TYPE = "1017";

	/**
	 * CU指令下发与接收状态
	 */
	public static final String CU_CMD_TYPE = "1018";
	
	/**
	 * EU设备状态统计表
	 */
	public static final String EU_STAT_TYPE = "1019";
	
	/**
	 * CU设备状态统计表
	 */
	public static final String CU_STAT_TYPE = "1020";
	
	/**
	 * cu设备状态
	 */
	public static String CU_DEVICE = "cuDevice";
	
	/**
	 * eu设备状态
	 */
	public static String EU_DEVICE = "euDevice";
	
	/**
	 * cu到管局状态
	 */
	public static String CU_TO_SMMS = "cuToSmms";
	
	/**
	 * cu到DU状态
	 */
	public static String CU_TO_DU = "cuToDu";
	 
	/**
	 * Eu到DU通信状态
	 */
	public static String EU_TO_DU = "euToDu";
	
	/**
	 * Eu到CU通信状态
	 */
	public static String EU_TO_CU = "euToCu";
	
	/**
	 * EU到DU访问日志
	 */
	public static String ACCESS_LOG = "euServiceStat";
	 
	/**
	 * EU链路状态
	 */
	public static String LINK_STATUS = "linkStatus";
	 
	/**
	 * 网卡状态
	 */                                   
	public static String NETWORK_STATUS = "networkStatus";
	
	/**
	 * 机器状态
	 */                                   
	public static String MECHINE_STATUS = "mechineState";
	
	/**
	 * 所有状态
	 */
	public static String DEVIC_ALL = "all";
	 
}
