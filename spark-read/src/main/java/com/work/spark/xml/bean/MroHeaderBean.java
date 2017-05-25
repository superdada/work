package com.work.spark.xml.bean;

import java.io.Serializable;

/**
 * <p>@author lenovo
 * <p>@createAt 2016-12-26 22:45
 * <p>@version 1.0
 */
public class MroHeaderBean implements Serializable{
    private String fileFormatVersion;
    private String jobId;
    private String period;
    private String reportTime;
    private String startTime;
    private String endTime;
    private String enbId;

    public MroHeaderBean(String fileFormatVersion, String jobId, String period, String reportTime, String startTime, String endTime, String enbId) {
        this.fileFormatVersion = fileFormatVersion;
        this.jobId = jobId;
        this.period = period;
        this.reportTime = reportTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enbId = enbId;
    }

    public String getFileFormatVersion() {
        return fileFormatVersion;
    }

    public void setFileFormatVersion(String fileFormatVersion) {
        this.fileFormatVersion = fileFormatVersion;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEnbId() {
        return enbId;
    }

    public void setEnbId(String enbId) {
        this.enbId = enbId;
    }

    @Override
    public String toString() {
        return "MroHeaderBean{" +
                "fileFormatVersion='" + fileFormatVersion + '\'' +
                ", jobId='" + jobId + '\'' +
                ", period='" + period + '\'' +
                ", reportTime='" + reportTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", enbId='" + enbId + '\'' +
                '}';
    }
}
