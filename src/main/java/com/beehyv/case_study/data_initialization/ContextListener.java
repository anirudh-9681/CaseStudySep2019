package com.beehyv.case_study.data_initialization;

import com.beehyv.case_study.entities.MyUserCredentials;
import com.beehyv.case_study.repositories.MyUserCredentialsRepo;
import com.beehyv.case_study.repositories.MyUserRepo;
import com.beehyv.case_study.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ContextListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ProductRepo productRepo;

    @Autowired
    MyUserRepo myUserRepo;

    @Autowired
    MyUserCredentialsRepo myUserCredentialsRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${case_study.admin.username}")
    private String admin_username;
    @Value("${case_study.admin.password}")
    private String admin_password;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        UserDataManager userDataManager = new UserDataManager();
        userDataManager.setMyUserRepo(myUserRepo);
        userDataManager.setMyUserCredentialsRepo(myUserCredentialsRepo);
        userDataManager.run(admin_username,
                passwordEncoder.encode(
                        Base64.getEncoder().encodeToString(
                                admin_password.getBytes()
                        )
                )
        );
        ProductDataManager productDataManager = new ProductDataManager();
        productDataManager.setResourceLoader(resourceLoader);
        productDataManager.setProductRepo(productRepo);
        productDataManager.run();
    }
}
