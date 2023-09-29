package com.example.kafkastreamapp.Event1;

import com.example.kafkastreamapp.Models.Audit;
import com.example.kafkastreamapp.Models.Key;
import lombok.Getter;

public class Event1 {
    @Getter
    private Key key;
    private Event1Value event1Value;
    private Audit audit;

    public Event1() {
        this.key = new Key();
        this.event1Value = new Event1Value();
        this.audit = new Audit();
    }

    public Event1(String empl_id, Boolean is_fte, String dept, String dob, String country, String event_name, String source_system) {
        this.key = new Key(empl_id, dept);
        this.event1Value = new Event1Value(empl_id, is_fte, dept, dob, country);
        this.audit = new Audit(event_name, source_system);
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Event1Value getValue() {
        return this.event1Value;
    }

    public void setValue(Event1Value event1Value) {
        this.event1Value = event1Value;
    }

    public Audit getAudit() {
        return this.audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }
}
