package com.work.spark.xml.bean;

import java.io.Serializable;

/**
 * <p>@author lenovo
 * <p>@createAt 2016-12-26 20:54
 * <p>@version 1.0
 */
public class MreHeaderBean implements Serializable{
    private String fileFormatVersion;
    private String jobId;
    private String reportTime;
    private String startTime;
    private String endTime;
    private String enbId;

    public MreHeaderBean(String fileFormatVersion, String jobId, String reportTime, String startTime, String endTime, String enbId) {

        this.fileFormatVersion = fileFormatVersion;
        this.jobId = jobId;
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
        return "MreHeaderBean{" +
                "fileFormatVersion='" + fileFormatVersion + '\'' +
                ", jobId='" + jobId + '\'' +
                ", reportTime='" + reportTime + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", enbId='" + enbId + '\'' +
                '}';
    }
}
