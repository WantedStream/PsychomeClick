package com.example.psychomeclick;

import java.util.List;

public class Subject implements ISubject{
    List<ISubject> subjects;
    protected String subjectName;
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
            int currentpercent=1;
            currentpercent*=subject.getSubPercents();
            percent+=currentpercent;
        }
        return percent;
    }

    @Override
    public short getSubjectName() {
        return 0;
    }
}
