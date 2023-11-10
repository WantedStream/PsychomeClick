package com.example.psychomeclick;

import java.util.List;

public interface IProgressClass {
    public List<ISubject> getSubProgress();
    public void addSubject(ISubject sub);

    public int getSubPercents();
}
