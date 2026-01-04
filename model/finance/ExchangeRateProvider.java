package model.finance;

public interface ExchangeRateProvider {
    double convert(double amount, Currency from, Currency to);
    
    double getRate(Currency from, Currency to);
}
