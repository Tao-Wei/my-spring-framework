package com.tw.springframework.annotation;

import cn.hutool.core.util.StrUtil;
import com.tw.springframework.config.BeanDefinitionRegistry;
import com.tw.springframework.config.support.BeanDefinition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {
    private BeanDefinitionRegistry beanDefinitionRegistry;

    //这个方法就是将传入到包路径中，所有标记了@Component注解到类，封装到beanDefinition中，然后注册到BeanDefinitionRegistry中
    public void doScan(String... basePackages) {
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidateComponents = findCandidateComponents(basePackage);
            for (BeanDefinition candidateComponent : candidateComponents) {
                String scope = resolveBeanScope(candidateComponent);
                candidateComponent.setScope(scope);
                beanDefinitionRegistry.registerBeanDefinition(determineBeanName(candidateComponent), candidateComponent);
            }
        }
    }

    private String resolveBeanScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) return scope.value();
        return StrUtil.EMPTY;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String value = component.value();
        if (StrUtil.isEmpty(value)) {
            value = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return value;
    }
}
