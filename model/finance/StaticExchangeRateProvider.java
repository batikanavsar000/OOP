package model.finance;

public class StaticExchangeRateProvider implements ExchangeRateProvider {
    
    private static final double USD_TO_TRY = 34.50;
    private static final double EUR_TO_TRY = 37.00;
    
    @Override
    public double convert(double amount, Currency from, Currency to) {
        if (from == to) return amount;
        
        double amountInTry = toTry(amount, from);
        
        return fromTry(amountInTry, to);
    }
    
    @Override
    public double getRate(Currency from, Currency to) {
        return convert(1.0, from, to);
    }
    
    private double toTry(double amount, Currency from) {
        return switch (from) {
            case TRY -> amount;
            case USD -> amount * USD_TO_TRY;
            case EUR -> amount * EUR_TO_TRY;
        };
    }
    
    private double fromTry(double amountInTry, Currency to) {
        return switch (to) {
            case TRY -> amountInTry;
            case USD -> amountInTry / USD_TO_TRY;
            case EUR -> amountInTry / EUR_TO_TRY;
        };
    }
    
    public String formatWithConversion(double amountTry) {
        return String.format("%.2f TL (≈ $%.2f / €%.2f)", 
            amountTry, 
            convert(amountTry, Currency.TRY, Currency.USD),
            convert(amountTry, Currency.TRY, Currency.EUR));
    }
}
