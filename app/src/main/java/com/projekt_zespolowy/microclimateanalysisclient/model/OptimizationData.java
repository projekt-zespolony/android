package com.projekt_zespolowy.microclimateanalysisclient.model;

public class OptimizationData {
    private final long timestamp;
    private final boolean peopleInTheRoom;
    private final boolean windowsOpened;

    public OptimizationData(long timestamp, boolean peopleInTheRoom, boolean windowsOpened)
    {
        this.timestamp=timestamp;
        this.peopleInTheRoom=peopleInTheRoom;
        this.windowsOpened=windowsOpened;
    }

    public long getTimestamp() { return timestamp; }

    public boolean isWindowsOpened() { return windowsOpened; }

    public boolean arePeopleInTheRoom() { return peopleInTheRoom; }
}
