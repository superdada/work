package com.work.spark.xml.bean;

import java.io.Serializable;

/**
 * <p>@author lenovo
 * <p>@createAt 2016-12-26 19:42
 * <p>@version 1.0
 */
public class MreDetailBean implements Serializable,Cloneable{
    private String objectId;
    private String objectEventType;
    private String objectMmeCode;
    private String objectMmeGroupId;
    private String objectMmeUeS1apId;
    private String objectTimeStamp;
    private String vLteScRSRP;
    private String vLteScRSRQ;
    private String vLteScEarfcn;
    private String vLteScPci;
    private String vLteScCgi;
    private String vLteNcRSRP;
    private String vLteNcRSRQ;
    private String vLteNcEarfcn;
    private String vLteNcPci;
    private String vUtraCellParameterId;
    private String vUtraCpichRSCP;
    private String vUtraCpichEcNo;
    private String vGsmNcellNcc;
    private String vGsmNcellBcc;
    private String vGsmNcellBcch;
    private String vGsmNcellCarrierRSSI;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectEventType() {
        return objectEventType;
    }

    public void setObjectEventType(String objectEventType) {
        this.objectEventType = objectEventType;
    }

    public String getObjectMmeCode() {
        return objectMmeCode;
    }

    public void setObjectMmeCode(String objectMmeCode) {
        this.objectMmeCode = objectMmeCode;
    }

    public String getObjectMmeGroupId() {
        return objectMmeGroupId;
    }

    public void setObjectMmeGroupId(String objectMmeGroupId) {
        this.objectMmeGroupId = objectMmeGroupId;
    }

    public String getObjectMmeUeS1apId() {
        return objectMmeUeS1apId;
    }

    public void setObjectMmeUeS1apId(String objectMmeUeS1apId) {
        this.objectMmeUeS1apId = objectMmeUeS1apId;
    }

    public String getObjectTimeStamp() {
        return objectTimeStamp;
    }

    public void setObjectTimeStamp(String objectTimeStamp) {
        this.objectTimeStamp = objectTimeStamp;
    }

    public String getvLteScRSRP() {
        return vLteScRSRP;
    }

    public void setvLteScRSRP(String vLteScRSRP) {
        this.vLteScRSRP = vLteScRSRP;
    }

    public String getvLteScRSRQ() {
        return vLteScRSRQ;
    }

    public void setvLteScRSRQ(String vLteScRSRQ) {
        this.vLteScRSRQ = vLteScRSRQ;
    }

    public String getvLteScEarfcn() {
        return vLteScEarfcn;
    }

    public void setvLteScEarfcn(String vLteScEarfcn) {
        this.vLteScEarfcn = vLteScEarfcn;
    }

    public String getvLteScPci() {
        return vLteScPci;
    }

    public void setvLteScPci(String vLteScPci) {
        this.vLteScPci = vLteScPci;
    }

    public String getvLteScCgi() {
        return vLteScCgi;
    }

    public void setvLteScCgi(String vLteScCgi) {
        this.vLteScCgi = vLteScCgi;
    }

    public String getvLteNcRSRP() {
        return vLteNcRSRP;
    }

    public void setvLteNcRSRP(String vLteNcRSRP) {
        this.vLteNcRSRP = vLteNcRSRP;
    }

    public String getvLteNcRSRQ() {
        return vLteNcRSRQ;
    }

    public void setvLteNcRSRQ(String vLteNcRSRQ) {
        this.vLteNcRSRQ = vLteNcRSRQ;
    }

    public String getvLteNcEarfcn() {
        return vLteNcEarfcn;
    }

    public void setvLteNcEarfcn(String vLteNcEarfcn) {
        this.vLteNcEarfcn = vLteNcEarfcn;
    }

    public String getvLteNcPci() {
        return vLteNcPci;
    }

    public void setvLteNcPci(String vLteNcPci) {
        this.vLteNcPci = vLteNcPci;
    }

    public String getvUtraCellParameterId() {
        return vUtraCellParameterId;
    }

    public void setvUtraCellParameterId(String vUtraCellParameterId) {
        this.vUtraCellParameterId = vUtraCellParameterId;
    }

    public String getvUtraCpichRSCP() {
        return vUtraCpichRSCP;
    }

    public void setvUtraCpichRSCP(String vUtraCpichRSCP) {
        this.vUtraCpichRSCP = vUtraCpichRSCP;
    }

    public String getvUtraCpichEcNo() {
        return vUtraCpichEcNo;
    }

    public void setvUtraCpichEcNo(String vUtraCpichEcNo) {
        this.vUtraCpichEcNo = vUtraCpichEcNo;
    }

    public String getvGsmNcellNcc() {
        return vGsmNcellNcc;
    }

    public void setvGsmNcellNcc(String vGsmNcellNcc) {
        this.vGsmNcellNcc = vGsmNcellNcc;
    }

    public String getvGsmNcellBcc() {
        return vGsmNcellBcc;
    }

    public void setvGsmNcellBcc(String vGsmNcellBcc) {
        this.vGsmNcellBcc = vGsmNcellBcc;
    }

    public String getvGsmNcellBcch() {
        return vGsmNcellBcch;
    }

    public void setvGsmNcellBcch(String vGsmNcellBcch) {
        this.vGsmNcellBcch = vGsmNcellBcch;
    }

    public String getvGsmNcellCarrierRSSI() {
        return vGsmNcellCarrierRSSI;
    }

    public void setvGsmNcellCarrierRSSI(String vGsmNcellCarrierRSSI) {
        this.vGsmNcellCarrierRSSI = vGsmNcellCarrierRSSI;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "MreDetailBean{" +
                "objectId='" + objectId + '\'' +
                ", objectEventType='" + objectEventType + '\'' +
                ", objectMmeCode='" + objectMmeCode + '\'' +
                ", objectMmeGroupId='" + objectMmeGroupId + '\'' +
                ", objectMmeUeS1apId='" + objectMmeUeS1apId + '\'' +
                ", objectTimeStamp='" + objectTimeStamp + '\'' +
                ", vLteScRSRP='" + vLteScRSRP + '\'' +
                ", vLteScRSRQ='" + vLteScRSRQ + '\'' +
                ", vLteScEarfcn='" + vLteScEarfcn + '\'' +
                ", vLteScPci='" + vLteScPci + '\'' +
                ", vLteScCgi='" + vLteScCgi + '\'' +
                ", vLteNcRSRP='" + vLteNcRSRP + '\'' +
                ", vLteNcRSRQ='" + vLteNcRSRQ + '\'' +
                ", vLteNcEarfcn='" + vLteNcEarfcn + '\'' +
                ", vLteNcPci='" + vLteNcPci + '\'' +
                ", vUtraCellParameterId='" + vUtraCellParameterId + '\'' +
                ", vUtraCpichRSCP='" + vUtraCpichRSCP + '\'' +
                ", vUtraCpichEcNo='" + vUtraCpichEcNo + '\'' +
                ", vGsmNcellNcc='" + vGsmNcellNcc + '\'' +
                ", vGsmNcellBcc='" + vGsmNcellBcc + '\'' +
                ", vGsmNcellBcch='" + vGsmNcellBcch + '\'' +
                ", vGsmNcellCarrierRSSI='" + vGsmNcellCarrierRSSI + '\'' +
                '}';
    }
}
