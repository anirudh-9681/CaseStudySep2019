package com.beehyv.case_study.data_initialization;

import com.beehyv.case_study.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ContextListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        DataManager dataManager = new DataManager();
        dataManager.setResourceLoader(resourceLoader);
        dataManager.setProductRepo(productRepo);
        dataManager.run();
    }
}
