package com.example.saarthi.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Routes {
    private String routename;
    private String routecode;

    public Routes(){

    }
    public Routes(String routename, String routecode){
        this.routename = routename;
        this.routecode = routecode;
    }

    public String getRoutename() {
        return routename;
    }

    public String getRoutecode() {
        return routecode;
    }
}