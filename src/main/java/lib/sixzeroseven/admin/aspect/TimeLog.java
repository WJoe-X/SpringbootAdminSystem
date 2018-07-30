package lib.sixzeroseven.admin.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:  记录该方法所用时间
 * 
 * @author WJoe
 * @time 2018年5月25日 下午2:17:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
@Documented
public @interface TimeLog {

	String description() default "";
}
