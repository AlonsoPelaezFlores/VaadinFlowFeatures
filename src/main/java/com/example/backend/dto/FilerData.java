package com.example.backend.dto;


public class FilerData {
    String filerNumber;
    String filerName;
    String nrtNumber;
    String irpfNumber;
    Double contributionBalance;

    public FilerData() {
        this.filerNumber = "123456";
        this.filerName = "Josep Garcia Garcia";
        this.nrtNumber = "F-123456";
        this.irpfNumber = "Example IRPF";
        this.contributionBalance = 0.0;
    }


    public String getFilerNumber() {
        return filerNumber;
    }

    public String getFilerName() {
        return filerName;
    }

    public String getNrtNumber() {
        return nrtNumber;
    }

    public String getIrpfNumber() {
        return irpfNumber;
    }

    public Double getContributionBalance() {
        return contributionBalance;
    }
}
