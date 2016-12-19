package com.example.devineni.foodtracker;

import java.util.Date;

/**
 * Created by devineni on 10/14/2016.
 */
public class DateComparator  implements Comparable<DateComparator>
{
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date date;
    @Override
    public int compareTo(DateComparator another) {
        return getDate().compareTo(another.getDate());
    }
}
