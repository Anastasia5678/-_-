package com.example.meditationapp;

public class listview {
    int audioresrc;
    private String header, description, frequency;

    public listview(String header, String description, String frequency, int audioresrc) {
        this.header = header;
        this.description = description;
        this.frequency = frequency;
        this.audioresrc = audioresrc;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getAudioresrc() {
        return audioresrc;
    }

    public void setAudioresrc(int audioresrc) {
        this.audioresrc = audioresrc;
    }
}
