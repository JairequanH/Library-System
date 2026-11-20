package com.jairequan;
import java.time.*;
import java.time.temporal.ChronoUnit;

public class MutableClock extends Clock{
    private Instant current;
    private final ZoneId zone;

    public MutableClock(Instant start, ZoneId zone){
        this.current = start;
        this.zone = zone;
    }

    @Override public ZoneId getZone(){
        return this.zone;
    }

    @Override public Clock withZone(ZoneId zone){
        return new MutableClock(this.current ,zone);
    }

    @Override public Instant instant(){
        return this.current;
    }

    public void advanceDays(int days){
        this.current = this.current.plus(days, ChronoUnit.DAYS);
    }
}
