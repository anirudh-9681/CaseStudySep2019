package com.beehyv.case_study.data_initialization;

import com.beehyv.case_study.entities.MyUser;
import com.beehyv.case_study.entities.MyUserCredentials;
import com.beehyv.case_study.repositories.MyUserCredentialsRepo;
import com.beehyv.case_study.repositories.MyUserRepo;

public class UserDataManager {

    private MyUserRepo myUserRepo;
    private MyUserCredentialsRepo myUserCredentialsRepo;

    public void setMyUserCredentialsRepo(MyUserCredentialsRepo myUserCredentialsRepo) {
        this.myUserCredentialsRepo = myUserCredentialsRepo;
    }

    public void setMyUserRepo(MyUserRepo myUserRepo) {
        this.myUserRepo = myUserRepo;
    }

    public void run(String admin_username, String admin_password) {
        if (myUserCredentialsRepo.count() != 0) {
            return;
        }
        MyUser tmp = new MyUser();
        myUserRepo.save(tmp);
        MyUserCredentials myUserCredentials = new MyUserCredentials();
        myUserCredentials.setMyUser(tmp);
        myUserCredentials.setEmail(admin_username);
        myUserCredentials.setPassword(admin_password);
        myUserCredentials.setAuthorities("ADMIN");
        myUserCredentialsRepo.save(myUserCredentials);
    }
}
