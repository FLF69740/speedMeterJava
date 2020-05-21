package com.androidnative.speedjava.business;

public class CAverage {

   private double mAverage;
   private short mValue;
   private int mCounter;

    public CAverage() {
        mAverage = 0;
        mCounter = 0;
    }

    public double getAverage() {
        return mAverage;
    }

    public void setValue(short value) {
        mValue = value;
        mCounter++;
        defineAverage();
    }

    public int getCounter() {
        return mCounter;
    }

    private void defineAverage(){
        mAverage = ((mAverage * (mCounter - 1) + mValue) / mCounter);
    }

    public String getResult(){
        return mValue + " - " + mCounter + " - " + mAverage;
    }
}
