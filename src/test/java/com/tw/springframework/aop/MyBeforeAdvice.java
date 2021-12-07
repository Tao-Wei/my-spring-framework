package com.tw.springframework.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

public class MyBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(new Date());
        System.out.println(target + "对象的" + method.getName() + "被调用,参数为:" + Arrays.toString(args));
    }
}
