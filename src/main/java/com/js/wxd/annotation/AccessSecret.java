package com.js.wxd.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 授权校验注解
 * Created on 2017/9/17.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
//最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface AccessSecret {
}
