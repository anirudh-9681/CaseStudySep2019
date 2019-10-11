package com.beehyv.case_study.data_initialization;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ContextListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    DataManager dataManager;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        dataManager.run();
        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
        try {
            beanDefinitionRegistry.removeBeanDefinition("dataManager");
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
        }
    }
}
