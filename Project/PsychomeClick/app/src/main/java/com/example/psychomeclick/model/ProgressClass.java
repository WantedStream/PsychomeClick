package com.example.psychomeclick.model;

import java.util.List;

public class ProgressClass implements IProgressClass{
    List<ISubject> subjects;
    @Override
    public List<ISubject> getSubProgress() {
        return null;
    }

    @Override
    public void addSubject(ISubject sub) {

    }

    @Override
    public int getSubPercents() {
        int percent=1;
        for(ISubject subject:this.subjects){
            percent+=subject.getSubPercents();
        }
        return percent;
    }
}
