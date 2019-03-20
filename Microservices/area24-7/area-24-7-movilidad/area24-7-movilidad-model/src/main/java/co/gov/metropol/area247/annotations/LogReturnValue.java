package co.gov.metropol.area247.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogReturnValue {
	
	@AliasFor("name")
	String value() default "";
	
	@AliasFor("value")
	String name() default "";
	
	boolean required() default true;

}
