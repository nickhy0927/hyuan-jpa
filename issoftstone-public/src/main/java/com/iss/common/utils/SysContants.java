package com.iss.common.utils;

public class SysContants {

	/**
	 * 新闻的发布状态
	 *
	 * @author nick
	 */
	public static class DeployStatus {

		/**
		 * 已发布
		 */
		public static final boolean DEPLOY = true;

		private static final String DEPLOY_NAME = "已发布";

		/**
		 * 未发布
		 */
		public static final boolean NODEPLOY = false;
		private static final String NODEPLOYNAME = "未发布";

		/**
		 * 获得发布状态的名称
		 *
		 * @param code
		 * @return
		 */
		public static String getName(boolean code) {
			String name = "";
			Integer flag = 0;
			if (code) {
				flag = 1;
			}
			switch (flag) {
			case 1:
				name = DEPLOY_NAME;
				break;
			case 0:
				name = NODEPLOYNAME;
				break;

			default:
				name = NODEPLOYNAME;
				break;
			}

			return name;
		}
	}

	/**
	 * 是否删除
	 *
	 * @author Curtain
	 */
	public static class IsDelete {
		public final static Boolean YES = true;
		public final static Boolean NO = false;

		public static String getIsDeleteName(Boolean code) {
			String name = "";
			if (NO.equals(code)) {
				name = "否";
			} else if (YES.equals(code)) {
				name = "是";
			}
			return name;
		}
	}
	public static class IsStart {
		public final static Integer YES = 1;
		public final static Integer NO = 0;
		
		public static String getIsDeleteName(Integer code) {
			String name = "";
			if (NO == code) {
				name = "停止";
			} else if (YES == code) {
				name = "运行中";
			}
			return name;
		}
	}

	/**
	 * 是否可用状态
	 *
	 * @author nick
	 */
	public static class IsDisable {

		public static final String DISABLE = "0";
		private static final String DISABLE_NAME = "不可用";
		public static final String NODISABLE = "1";
		private static final String NODISABLE_NAME = "可用";

		public static String getName(String code) {
			String name = "";
			switch (Integer.parseInt(code)) {
			case 1:
				name = NODISABLE_NAME;
				break;

			case 0:
				name = DISABLE_NAME;
				break;
			default:
				name = DISABLE_NAME;
			}
			return name;
		}
	}

	/**
	 * 是否置顶
	 *
	 * @author Curtain
	 */
	public static class IsTop {
		private final static Integer NO = 0;
		private final static Integer YES = 1;

		public static String getIsTopName(String code) {
			String name = "";
			if (NO == 0) {
				name = "否";
			} else if (YES == 1) {
				name = "是";
			}
			return name;
		}
	}

}
