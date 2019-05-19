package com.thoughtmechanix.assets.events.model;

public class CompanyChange {
    private String changeType;
    private String companyId;
    private String correlationId;
    private String action;

    public CompanyChange(String changeType, String companyId, String correlationId, String action) {
        this.changeType = changeType;
        this.companyId = companyId;
        this.correlationId = correlationId;
        this.action = action;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
