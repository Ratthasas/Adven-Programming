package se233.chapter2.model;

public class CurrencyEntity {
     private Double rate;
     private Double initialRate;
     private String date;

    public CurrencyEntity(Double rate, String date) {
                this.initialRate = rate;
                this.rate = rate;
                this.date = date;
            }

    public Double getInitialRate() {
        return initialRate;
    }

    public Double getRate() {
                return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getTimestamp() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s %.4f", date, rate);
    }
}
