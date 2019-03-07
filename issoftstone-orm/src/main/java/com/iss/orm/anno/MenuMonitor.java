package com.iss.orm.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuMonitor {

	/**
	  * 排序
	 * @return
	 */
	int orders();
	
	int level();
	
	/**
	 * 是否显示
	 * @return
	 */
	boolean enable() default true; 
	
	/**
	 * 上级菜单别名
	 * @return
	 */
	String paraentAlias() default "";
	
	String name();
	
	String url() default "";
}
