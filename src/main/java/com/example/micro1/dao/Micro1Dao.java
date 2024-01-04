package com.example.micro1.dao;

import org.springframework.stereotype.Repository;

@Repository
public class Micro1Dao {

    public String getMicroserviceName()
    {
        return "micro1";
    }
}