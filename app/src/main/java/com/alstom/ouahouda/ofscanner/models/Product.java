package com.alstom.ouahouda.ofscanner.models;

import java.util.Date;

public class Product {
    private long id;
    private String designation;
    private long of;
    private String reference;
    private int week;
    private String currentService;
    private Date startTime;
    private Date startTimeKitting;
    private Date startTimeCutting;
    private Date startTimeAssembly;
    private Date startTimeTesting;
    private Date endTimeKitting;
    private Date endTimeCutting;
    private Date endTimeAssembly;
    private Date endTimeTesting;

    public Product() {

    }

    public Product(long id, String designation, long of, String reference, int week, String currentService, Date startTime, Date startTimeKitting, Date startTimeCutting, Date startTimeAssembly, Date startTimeTesting, Date endTimeKitting, Date endTimeCutting, Date endTimeAssembly, Date endTimeTesting) {
        this.id = id;
        this.designation = designation;
        this.of = of;
        this.reference = reference;
        this.week = week;
        this.currentService = currentService;
        this.startTime = startTime;
        this.startTimeKitting = startTimeKitting;
        this.startTimeCutting = startTimeCutting;
        this.startTimeAssembly = startTimeAssembly;
        this.startTimeTesting = startTimeTesting;
        this.endTimeKitting = endTimeKitting;
        this.endTimeCutting = endTimeCutting;
        this.endTimeAssembly = endTimeAssembly;
        this.endTimeTesting = endTimeTesting;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public long getOf() {
        return of;
    }

    public void setOf(long of) {
        this.of = of;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getCurrentService() {
        return currentService;
    }

    public void setCurrentService(String currentService) {
        this.currentService = currentService;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTimeKitting() {
        return startTimeKitting;
    }

    public void setStartTimeKitting(Date startTimeKitting) {
        this.startTimeKitting = startTimeKitting;
    }

    public Date getStartTimeCutting() {
        return startTimeCutting;
    }

    public void setStartTimeCutting(Date startTimeCutting) {
        this.startTimeCutting = startTimeCutting;
    }

    public Date getStartTimeAssembly() {
        return startTimeAssembly;
    }

    public void setStartTimeAssembly(Date startTimeAssembly) {
        this.startTimeAssembly = startTimeAssembly;
    }

    public Date getStartTimeTesting() {
        return startTimeTesting;
    }

    public void setStartTimeTesting(Date startTimeTesting) {
        this.startTimeTesting = startTimeTesting;
    }

    public Date getEndTimeKitting() {
        return endTimeKitting;
    }

    public void setEndTimeKitting(Date endTimeKitting) {
        this.endTimeKitting = endTimeKitting;
    }

    public Date getEndTimeCutting() {
        return endTimeCutting;
    }

    public void setEndTimeCutting(Date endTimeCutting) {
        this.endTimeCutting = endTimeCutting;
    }

    public Date getEndTimeAssembly() {
        return endTimeAssembly;
    }

    public void setEndTimeAssembly(Date endTimeAssembly) {
        this.endTimeAssembly = endTimeAssembly;
    }

    public Date getEndTimeTesting() {
        return endTimeTesting;
    }

    public void setEndTimeTesting(Date endTimeTesting) {
        this.endTimeTesting = endTimeTesting;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", of=" + of +
                ", reference='" + reference + '\'' +
                ", week=" + week +
                ", currentService='" + currentService + '\'' +
                ", startTime=" + startTime +
                ", startTimeKitting=" + startTimeKitting +
                ", startTimeCutting=" + startTimeCutting +
                ", startTimeAssembly=" + startTimeAssembly +
                ", startTimeTesting=" + startTimeTesting +
                ", endTimeKitting=" + endTimeKitting +
                ", endTimeCutting=" + endTimeCutting +
                ", endTimeAssembly=" + endTimeAssembly +
                ", endTimeTesting=" + endTimeTesting +
                '}';
    }
}