package com.iss.oauth.platform.system.performance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iss.common.anno.AccessAuthority;
import com.iss.common.utils.JsonMapper;
import com.iss.platform.system.performance.service.PerformanceService;

@Controller
public class PerformanceViewController {

	@Autowired
	private PerformanceService performanceService;

	@AccessAuthority(alias = "performance-list-page", name = "性能分页页面")
	@RequestMapping(value = "/platform/system/performance/list.do", method = RequestMethod.GET)
	public String list(String alias, Model model) {
		model.addAttribute("alias", alias);
		return "platform/system/performance/list";
	}
	
	@AccessAuthority(alias = "performance-list", name = "性能统计页面")
	@RequestMapping(value = "/platform/system/performance/performance-list.do", method = RequestMethod.GET)
	public String performanceList(Model model) throws ParseException {
		int days = getCurrentMonthLastDay();
		List<String> list = performanceService.queryPerformanceGroup();
		List<Map<String, Object>> maps = Lists.newArrayList();
		List<String> dayList = Lists.newArrayList();
		for (int day = 1; day <= days; day++) {
			dayList.add(getDay(day));
		}
		for (String alias : list) {
			Map<String, Object> map = Maps.newConcurrentMap();
			map.put("name", performanceService.queryPerformanceByalias(alias));
			map.put("alias", alias);
			Long[] avg = new Long[days];
			for (int day = 1; day <= days; day++) {
				Long executeTimeAvg = performanceService.queryAccessList(alias, getDay(day));
				if (executeTimeAvg != null) {
					System.out.println("executeTimeAvg:" + executeTimeAvg);
				}
				avg[day - 1] = executeTimeAvg == null ? 0 : executeTimeAvg;
			}
			map.put("data", avg);
			maps.add(map);
		}
		model.addAttribute("datas", new JsonMapper().toJson(maps));
		model.addAttribute("dayList", new JsonMapper().toJson(dayList));
		return "platform/system/performance/performance-list";
	}

	public static String getDay(int day) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(firstDay());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day - 1);
		date = calendar.getTime();
		return sdf.format(date);
	}

	public static String firstDay() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		System.out.println("-----1------firstDay:" + firstDay);
		return firstDay;
	}

	/**
	 * 取得当月天数
	 */
	public static int getCurrentMonthLastDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 得到指定月的天数
	 */
	public static int getMonthLastDay(int year, int month) {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.YEAR, year);
		a.set(Calendar.MONTH, month - 1);
		a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}
}
