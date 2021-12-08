package com.tw.springframework.config.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.tw.springframework.annotation.ClassPathBeanDefinitionScanner;
import com.tw.springframework.config.BeanDefinitionRegistry;
import com.tw.springframework.core.ResourceLoader;
import com.tw.springframework.exception.BeansException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        super(beanDefinitionRegistry, resourceLoader);
    }


    /**
     * 读取xml配置文件中的内容，封装到BeanDefinition中，然后添加到BeanDefinitionRegistry中
     *
     * @param path
     * @throws IOException
     */
    @Override
    public void LoadBeanDefinition(String path) {
        try {

            Document document = XmlUtil.readXML(getResourceLoader().getResource(path).getInputStream());
            NodeList componentScanList = document.getElementsByTagName("context:component-scan");
            //如果xml文件中配置了包扫描，那么扫描的包，获取注解方式配置的beanDefinition
            for (int i = 0; i < componentScanList.getLength(); i++) {
                Node componentScan = componentScanList.item(i);
                if (componentScan instanceof Element) {
                    String scanPath = ((Element) componentScan).getAttribute("base-package");
                    if (StrUtil.isEmpty(scanPath)) {
                        throw new BeansException("component-scan 标签的base-package属性不能为空");
                    }
                    scanPackage(scanPath);
                }
            }


            NodeList beanLabels = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < beanLabels.getLength(); i++) {
                Node beanLabel = beanLabels.item(i);
                if (!(beanLabel instanceof Element)) {
                    continue;
                }
                if (!"bean".equals(beanLabel.getNodeName())){
                   continue;
                }
                Element beanElement = (Element) beanLabel;
                String id = beanElement.getAttribute("id");
                String name = beanElement.getAttribute("id");
                String classStr = beanElement.getAttribute("class");
                String initMethodName = beanElement.getAttribute("init-method");
                String destroyMethodName = beanElement.getAttribute("destroy-method");
                String scope = beanElement.getAttribute("scope");
                Class beanClass = Class.forName(classStr);
                //从标签中获取beanName
                String beanName = StrUtil.isBlank(id) ? name : id;
                if (StrUtil.isBlank(beanName)) {
                    String simpleName = beanClass.getSimpleName();
                    simpleName = ((char) (simpleName.charAt(0) | 0x20)) + simpleName.substring(1);
                    beanName = simpleName;
                }

                //创建beanDefinition注册到BeanDefinitionRegistry中
                PropertyValues propertyValues = new PropertyValues(null);
                BeanDefinition beanDefinition = new BeanDefinition(beanClass, propertyValues, initMethodName, destroyMethodName);
                beanDefinition.setScope(scope);
                getBeanDefinitionRegistry().registerBeanDefinition(beanName, beanDefinition);

                NodeList propertyLabels = beanLabel.getChildNodes();
                for (int i1 = 0; i1 < propertyLabels.getLength(); i1++) {
                    Node propertyLabel = propertyLabels.item(i1);
                    if (!(propertyLabel instanceof Element)) {
                        continue;
                    }
                    Element propertyElement = (Element) propertyLabel;
                    String attrValue = propertyElement.getAttribute("value");
                    String attrRef = propertyElement.getAttribute("ref");
                    String attrName = propertyElement.getAttribute("name");
                    PropertyValue propertyValue = new PropertyValue();
                    propertyValue.setName(attrName);
                    propertyValue.setValue(StrUtil.isBlank(attrValue) ? new BeanReference(attrRef) : attrValue);

                    propertyValues.addPropertyValue(propertyValue);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("加载beanDefinition失败");
        }
    }

    /**
     * 将包以及子包中配置的类，注册到beanDefinitionRegistry
     *
     * @param scanPath
     */
    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.split(scanPath, ",");
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner = new ClassPathBeanDefinitionScanner();
        classPathBeanDefinitionScanner.setBeanDefinitionRegistry(getBeanDefinitionRegistry());
        classPathBeanDefinitionScanner.doScan(scanPath);

    }

    public void LoadBeanDefinitions(String[] configLocations) {
        for (String configLocation : configLocations) {
            LoadBeanDefinition(configLocation);

        }
    }
}
