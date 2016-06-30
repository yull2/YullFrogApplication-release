package com.frogoutofwell.yullfrogapplication.data;

import java.util.Arrays;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class ActivityDetail {
    public int seq;
    public String name;
    public String endDate;
    public float averageRate;
    public String actClass;
    public String indus;
    public String term;
    public String region;
    public int totalPostCount;
    public int[] totalPostCountStar;
    public int totalInterCount;
    public int totalInterLevel;
    public String guideImg;
    public String recruitImg;
    public String companyName;
    public String companyLogo;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public float getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(float averageRate) {
        this.averageRate = averageRate;
    }

    public String getActClass() {
        return actClass;
    }

    public void setActClass(String actClass) {
        this.actClass = actClass;
    }

    public String getIndus() {
        return indus;
    }

    public void setIndus(String indus) {
        this.indus = indus;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTotalPostCount() {
        return totalPostCount;
    }

    public void setTotalPostCount(int totalPostCount) {
        this.totalPostCount = totalPostCount;
    }

    public int[] getTotalPostCountStar() {
        return totalPostCountStar;
    }

    public void setTotalPostCountStar(int[] totalPostCountStar) {
        this.totalPostCountStar = totalPostCountStar;
    }

    public int getTotalInterCount() {
        return totalInterCount;
    }

    public void setTotalInterCount(int totalInterCount) {
        this.totalInterCount = totalInterCount;
    }

    public int getTotalInterLevel() {
        return totalInterLevel;
    }

    public void setTotalInterLevel(int totalInterLevel) {
        this.totalInterLevel = totalInterLevel;
    }

    public String getRecruitImg() {
        return recruitImg;
    }

    public void setRecruitImg(String recruitImg) {
        this.recruitImg = recruitImg;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getGuideImg() {
        return guideImg;
    }

    public void setGuideImg(String guideImg) {
        this.guideImg = guideImg;
    }

    @Override
    public String toString() {
        return "ActivityDetail{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", endDate='" + endDate + '\'' +
                ", averageRate=" + averageRate +
                ", actClass='" + actClass + '\'' +
                ", indus='" + indus + '\'' +
                ", term='" + term + '\'' +
                ", region='" + region + '\'' +
                ", totalPostCount=" + totalPostCount +
                ", totalPostCountStar=" + Arrays.toString(totalPostCountStar) +
                ", totalInterCount=" + totalInterCount +
                ", totalInterLevel=" + totalInterLevel +
                ", guideImg='" + guideImg + '\'' +
                ", recruitImg='" + recruitImg + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyLogo='" + companyLogo + '\'' +
                '}';
    }
}
