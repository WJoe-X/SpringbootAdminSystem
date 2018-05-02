package com.lianxiang.po;

import javax.validation.constraints.NotEmpty;

public class MemberKey {
	
    private String uid;

    
    @NotEmpty
    private String account;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}