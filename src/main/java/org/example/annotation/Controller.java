package org.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // Target : 해당 어노테이션 타입이 적용될 수 있는 타입 리스트 & TYPE : interface, class, enum
@Retention(RetentionPolicy.RUNTIME) // 유지기간은 런타임 시간까지
public @interface Controller {
}
