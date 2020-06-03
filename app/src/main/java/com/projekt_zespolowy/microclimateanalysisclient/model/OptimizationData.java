package com.projekt_zespolowy.microclimateanalysisclient.model;

public class OptimizationData {
    private final long timestamp;
    private final boolean people_in_the_room;
    private final boolean windows_are_opened;

    public OptimizationData(long timestamp, boolean people_in_the_room, boolean windows_are_opened)
    {
        this.timestamp=timestamp;
        this.people_in_the_room =people_in_the_room;
        this.windows_are_opened =windows_are_opened;
    }

    public long getTimestamp() { return timestamp; }

    public boolean isWindows_are_opened() { return windows_are_opened; }

    public boolean arePeopleInTheRoom() { return people_in_the_room; }

    public String getWindowsOpenedAsString()
    {
        if(windows_are_opened == true) return "Yes";
        else return "No";
    }

    public String getPeopleInTheRoomAsString()
    {
        if(people_in_the_room ==true) return "Yes";
        else return "No";
    }

}
