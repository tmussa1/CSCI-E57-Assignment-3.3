package com.thoughtmechanix.assets.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

@Entity
@Table(name = "assets_table")
public class Asset{

    @Id
    @Column(name = "asset_id", nullable = false)
    private String assetId;

    @Column(name = "company_id", nullable = false)
    private String companyId;

    @Column(name = "asset_name", nullable = false)
    private String assetName;

    @Column(name = "asset_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetType assetType;

    @Column(name="comment")
    private String comment;

    @Transient
    private String companyName = StringUtils.EMPTY;

    @Transient
    private String yearEstablished = StringUtils.EMPTY;

    @Transient
    private Integer numOfEmployees = 0;

    @Transient
    private double capital = 0.0;

    public Asset() {
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getYearEstablished() {
        return yearEstablished;
    }

    public void setYearEstablished(String yearEstablished) {
        this.yearEstablished = yearEstablished;
    }

    public Integer getNumOfEmployees() {
        return numOfEmployees;
    }

    public void setNumOfEmployees(Integer numOfEmployees) {
        this.numOfEmployees = numOfEmployees;
    }

    public double getCapital() {
        return capital;
    }

    public void setCapital(double capital) {
        this.capital = capital;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Asset withAssetId(String assetId){
        this.setAssetId(assetId);
        return this;
    }

    public Asset withAssetType(AssetType assetType){
        this.setAssetType(assetType);
        return this;
    }

    public Asset withAssetName(String assetName){
        this.setAssetName(assetName);
        return this;
    }

    public Asset withComment(String comment){
        this.setComment(comment);
        return this;
    }

    public Asset withCompanyId(String companyId){
        this.setCompanyId(companyId);
        return this;
    }

    public Asset withCompanyName(String companyName){
        this.setCompanyName(companyName);
        return this;
    }

    public Asset withYearEstablished(String year){
        this.setYearEstablished(year);
        return this;
    }

    public Asset withNumOfEmployees(int numOfEmployees){
        this.setNumOfEmployees(numOfEmployees);
        return this;
    }

    public Asset withCapital(double capital){
        this.setCapital(capital);
        return this;
    }

}
