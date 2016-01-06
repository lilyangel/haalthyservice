package com.haalthy.service.sms;

public class Constants {
	
	public static class NumberConstant{
		public final static int 	ZERO = 0;
		public final static int 	ONE = 1;
		public final static int 	TWO = 2;
		
		public final static String  ZERO_STRING = "0";
		public final static String  ONE_STRING = "1";
		public final static String  TWO_STRING = "2";
	}
	
	public static class Regex{
		public final static String PHONE = "^((\\+{0,1}86){0,1})1[0-9]{10}$";
		public final static int PHONE_LENGTH = 20;
		public final static String PASSWORD = "^[\\w]{6,30}$";
		public final static String EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		
		public final static String ZHENGSHU = "^[1-9]\\d*$";
	}
	
	public static class MobileStatus{
		public final static int WORK=1; //工作
		public final static int AWAIT=2; //待机
		public final static int DORMANCY=3; //休眠
		public final static int CLOSE=4; //关闭
		public final static int EXIT=5; //退出
	}
	
	public static class UserStatus{
		public final static String OFFLINE = "Offline"; //离线
		public final static String TOKENMD5ERROR = "TokenMD5Error"; //个推id错误时返回的用户状态
	}
	
	public static class UserType{
		public static final String NORMAL = "0";  // User 普通用户
		public static final String ENTERPRISEUSER = "1"; // User 企业用户
	}
	
	public static class UserSex{
		public static final int WOMAN 	= 0;   // 女
		public static final int MAN 	= 1;   // 男 
		public static final int DEFAULT = 2;   // 保密 
	}
	
	public static class RoleType{
		public static final int TYPE1 = 1;  // 超级管理员
		public static final int TYPE2 = 2;  // 企业管理员
		public static final int TYPE3 = 3;  // 企业总经理
		public static final int TYPE4 = 4;  // 企业部门经理
		public static final int TYPE5 = 5;  // 企业员工
	} 
	
	public static class ConstantsType{
		public static final String EVENT_TYPE 		= "event_type";  // 活动类型
		public static final String TASK_COST_TYPE 	= "task_cost_type";  // 活动类型
	} 
	
	public static class EventConstants{
		public static final String EVENT_REGISTERED_YES = "1";  // 活动需要报名
		public static final String EVENT_REGISTERED_NO = "0";   // 活动不需要报名
		
		public static final String YIDAO = "已到";
		public static final String WEIDAO = "未到";
		
		public static final String USERNAME = "姓名";
		public static final String MOBILE = "手机";
		public static final String ISARRIVE = "已到/未到";
		public static final String EVENT_SIGNIN_TIME = "开始签到时间";
		public static final String EVENT_SIGNIN_END_TIME = "结束签到时间";
		public static final String EVENT_SIGNIN_ADDRESS = "签到地址";
		public static final String TIME_COUNT = "时长(小时)";
		public static final String DESCRIPTION = "描述";
		
		public static final String CONTENT_EMPTY="没有数据";
		
		public static final String[] title = { 
			EventConstants.USERNAME, EventConstants.MOBILE,
			EventConstants.EVENT_SIGNIN_TIME, EventConstants.EVENT_SIGNIN_END_TIME,
			EventConstants.EVENT_SIGNIN_ADDRESS, EventConstants.TIME_COUNT,
			EventConstants.DESCRIPTION};
		
		public static final String EXPORT_EXCEL_PATH = "export_excel_path"; //导出的excel的路径和文件名
		
	} 
	
	public static class OrderConstants{
		public static final String NEW_ORDER = "0";			//新订单
		public static final String ADD_ACCOUNT_NUMBER = "1";//添加账号数
		public static final String RENEWAL = "2";			//续期
		public static final String ALL = "3";				//添加账号数并续期
		
		public static final String PAY_TYPE_OFFLINE = "0";  //线下付款
		public static final String PAY_TYPE_ONLINE = "1";	//线上付款
		
		public static final String FEE_MONTHLY = "0";		//月费 
		public static final String FEE_ANNUAL = "1"; 		//年费
		public static final Float  FEE_DISCOUNT = (float) 0.9;	//折扣   
		
		public static final String APPENDIX_NAME = "appendix-";
		public static final String SUFFIX = ".jpg";			//图片后缀
	}
	
	public static class OrderStatus{
		public static final String UNDERWAY 		= "0"; 		//进行中
		public static final String ACCOUNT_PAID 	= "1"; 		//已付款(审核中)
		public static final String EXECUTED 		= "2"; 		//已生效(审核通过)
		public static final String LOSE_EFFICACY 	= "3"; 		//已过期
		public static final String NO_PASS 			= "4"; 		//核审不通过
	}
	
	public static class AccountPricing{
		public static final Integer BUY_ACCOUNT_NUM_10 = 10;			//少于等于10个账号，定价30/元/月
		public static final Float BUY_ACCOUNT_NUM_10_PRICE = 30f;
		
		public static final Float BUY_ACCOUNT_NUM_10_30_PRICE = 25f;	//第10-30个账号，定价25/元/月
		
		public static final Integer BUY_ACCOUNT_NUM_30 = 30;
		public static final Float BUY_ACCOUNT_NUM_30_PRICE = 20f;		//大于30以上的，定价20/元/月
		
	}
	
	public static class EventTerritoryConstants{
		public static final String TYPE_PROVINCE 	= "P";			//一级地域
		public static final String TYPE_CITY 		= "C";			//二级地域
		public static final String TYPE_SCENIC 		= "S";			//三级地域
		
		public static final String PROVINCE_ID 		= "provinceId";
		public static final String CITY_ID			= "cityId";
		public static final String SCENIC_ID 		= "scenicId";
		
		public static final String GUANGDONG = "广东省";
		public static final String SHENZHEN = "深圳市";
	}
	
	public static class TaskConstants{
		public static final String TASK_LOG = "task-log";
		public static final String TASK_COST = "task-cost";
	}
	
	public static class UserRoleConstants{
		public static final Integer SUPPER_ADMIN_ROLE_ID = 1; //超级管理员角色
	}
	
	public static class DirectionConstants{
		public static final Integer EVENT_NOT_END 	= 0; //未结束的活动
		public static final Integer EVENT_END 		= 1; //已结束的活动
	}
	
	public static class EzoutdoorEventConstants{
		public static final String APPLICATION_COUNT = "applicationCount";
		public static final String SIGNIN_COUNT = "signinCount";
		public static final String SEX = "sex";
	}
	
	public static class PagerConstants{
		public static final String OFFSET = "offset";
		public static final String ROWS = "rows";
	}
	
	public static class UrlConstants{
		public static final String LOGIN_URL = "http://115.29.169.236:8780/ezoutdoor/admin/login.html";
		public static final String EMAIL_URL = "http://osstest.ezoutwork.com/email";
		public static final String MERCHANT_AUDIT_TXT = "/merchant_audit.txt";
		public static final String MERCHANT_AUDIT_CSS = "/merchant_audit.css";
	}
	
	public static class EamilConstants{
		public static final String MERCHANT_SUBJECT = "商家信息审核";
		
		public static final String MERCHANT_MSG_SUCCESS = "恭喜您、您提交的核审信息通过了，" +
																							"点击<a target='_blank' href='http://115.29.169.236:8780/ezoutdoor/admin/login.html'>" +
																							"<b>&nbsp;<font size='5' color='#47972b'>发布赛事</font>&nbsp;</b></a>发布您的赛事吧。";
		
		public static final String MERCHANT_MSG_FAILURE = "很遗憾、您提交的核审信息没有通过，相关原因请查看核审消息。";
		
		public static final String EVENT_EAMIL_NAME = "		人员名单"; 
	}
	
}
