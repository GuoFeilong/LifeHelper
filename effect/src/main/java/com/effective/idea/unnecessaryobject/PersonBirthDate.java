package com.effective.idea.unnecessaryobject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jsion on 16/3/21.
 */
public class PersonBirthDate {
    private Date personDate;
    private static final Date START_DATA;
    private static final Date END_DATA;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1945, Calendar.JANUARY, 1, 0, 0, 0);
        START_DATA = calendar.getTime();
        calendar.set(2000, Calendar.JANUARY, 1, 0, 0, 0);
        END_DATA = calendar.getTime();

        System.out.print("\n>>>>>>static code block\n");
    }

    public PersonBirthDate(Date personDate) {
        this.personDate = personDate;
        System.out.print("\n>>>>>>class constructor\n");

    }

    public boolean isContains() {
        return personDate.compareTo(START_DATA) >= 0 && personDate.compareTo(END_DATA) < 0;
    }

}
