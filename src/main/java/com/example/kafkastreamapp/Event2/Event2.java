package com.example.kafkastreamapp.Event2;


import com.example.kafkastreamapp.Models.Audit;
import com.example.kafkastreamapp.Models.Key;
import lombok.Getter;

public class Event2 {
    @Getter
    private Key key;
    private Event2Value event2Value;
    @Getter
    private Audit audit;

    public Event2() {
        this.key = new Key();
        this.event2Value = new Event2Value();
        this.audit = new Audit();
    }

    public Event2(String empl_id, String dept, String country, String state, String event_name, String source_system) {
        this.key = new Key(empl_id, dept);
        this.event2Value = new Event2Value(empl_id, dept, state, country);
        this.audit = new Audit(event_name, source_system);
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Event2Value getValue() {
        return this.event2Value;
    }

    public void setValue(Event2Value event2Value) {
        this.event2Value = event2Value;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }
}
