package com.tw.springframework.config.support;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 某个bean实例，所有字段的名称和对应的值
 */
@Data
public class PropertyValues {
    private final List<PropertyValue> propertyValueList;

    public PropertyValues(List<PropertyValue> propertyValueList) {
        this.propertyValueList = propertyValueList == null ? new ArrayList<>() : propertyValueList;
    }

    public void addPropertyValues(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String PropertyName) {
        return propertyValueList.stream().filter(x -> x.getName().equals(PropertyName)).findFirst().orElse(null);
    }
}
