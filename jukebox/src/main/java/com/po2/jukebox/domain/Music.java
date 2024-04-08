/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.po2.jukebox.domain;

/**
 *
 * @author knoba
 */
public class Music {

    private int id;
    private String name;
    private String link;
    private int durationInSeconds;
    private String bandName;

    public Music() {
    }

    public Music(int id, String name, String link, int durationInSeconds, String bandName) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.durationInSeconds = durationInSeconds;
        this.bandName = bandName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the durationInSeconds
     */
    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * @param durationInSeconds the durationInSeconds to set
     */
    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    /**
     * @return the bandName
     */
    public String getBandName() {
        return bandName;
    }

    /**
     * @param bandName the bandName to set
     */
    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

}
