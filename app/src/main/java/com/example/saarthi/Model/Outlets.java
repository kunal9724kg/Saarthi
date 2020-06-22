package com.example.saarthi.Model;

public class Outlets {
    private String outletname ;
    private String routecode;
    private String salesmanname;

    public Outlets() {

    }

    public Outlets(String outletname, String routecode, String salesmanname) {
        this.outletname = outletname;
        this.routecode = routecode;
        this.salesmanname = salesmanname;
    }

    public String getOutletname() {
        return outletname;
    }

    public String getRoutecode() {
        return routecode;
    }

    public String getSalesmanname() {
        return salesmanname;
    }
}