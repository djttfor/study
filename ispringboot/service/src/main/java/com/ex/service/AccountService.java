package com.ex.service;

public class AccountService {
    private String name;

    public AccountService(String name) {
        this.name = name;
    }

    public AccountService() {
    }

    ;

    @Override
    public String toString() {
        return "AccountService{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
