package com.example.psychomeclick.model;

import java.util.List;

public interface IProgressClass {
    public List<ISubject> getSubProgress();
    public void addSubject(ISubject sub);

    public int getSubPercents();
}
