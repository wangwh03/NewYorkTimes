package com.weihua.newyorktimes.client;

public class NewYorkTimesClient {
    public static final String API_KEY = "5ca0fea6db644369a488d3b4067ee8bf";

    public enum TimePeriod {
        DAY(1),
        WEEK(7),
        MONTH(30);

        private final int daysValue;

        TimePeriod(int days) {
            this.daysValue = days;
        }

        public int getDaysValue() {
            return daysValue;
        }
    }
}
