package service;

import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;

public class WeatherService {
    
    public enum WeatherCondition {
        SUNNY("Sunny ☀️"),
        PARTLY_CLOUDY("Partly Cloudy ⛅"),
        CLOUDY("Cloudy ☁️"),
        RAINY("Rainy 🌧️"),
        STORMY("Stormy ⛈️"),
        SNOWY("Snowy ❄️"),
        HOT("Hot 🌡️"),
        COLD("Cold 🥶");
        
        private final String displayName;
        
        WeatherCondition(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    public static class WeatherForecast {
        private final int temperature;
        private final WeatherCondition condition;
        private final String advice;
        private final int humidity;
        
        public WeatherForecast(int temperature, WeatherCondition condition, String advice, int humidity) {
            this.temperature = temperature;
            this.condition = condition;
            this.advice = advice;
            this.humidity = humidity;
        }
        
        public int getTemperature() { return temperature; }
        public WeatherCondition getCondition() { return condition; }
        public String getAdvice() { return advice; }
        public int getHumidity() { return humidity; }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("\n┌─────────────────────────────────┐\n");
            sb.append("│       WEATHER FORECAST       │\n");
            sb.append("├─────────────────────────────────┤\n");
            sb.append(String.format("│ Temperature: %3d°C                │\n", temperature));
            sb.append(String.format("│ Condition  : %-21s│\n", condition.getDisplayName()));
            sb.append(String.format("│ Humidity   : %%%2d                   │\n", humidity));
            sb.append("├─────────────────────────────────┤\n");
            sb.append("│ 💡 ").append(advice).append("\n");
            sb.append("└─────────────────────────────────┘");
            return sb.toString();
        }
    }
    
    public WeatherForecast getWeatherByCategory(String category) {
        int temperature;
        WeatherCondition condition;
        String advice;
        int humidity;
        
        switch (category.toUpperCase()) {
            case "ELIT", "LUXURY" -> {
                temperature = ThreadLocalRandom.current().nextInt(28, 38);
                condition = WeatherCondition.SUNNY;
                humidity = ThreadLocalRandom.current().nextInt(30, 50);
                advice = "Don't forget sunscreen and light clothes!";
            }
            case "KULTUR", "CULTURE" -> {
                temperature = ThreadLocalRandom.current().nextInt(15, 25);
                condition = WeatherCondition.PARTLY_CLOUDY;
                humidity = ThreadLocalRandom.current().nextInt(40, 60);
                advice = "Ideal for city tour, bring a light jacket.";
            }
            case "TROPIKAL", "TROPICAL" -> {
                temperature = ThreadLocalRandom.current().nextInt(26, 35);
                condition = WeatherCondition.RAINY;
                humidity = ThreadLocalRandom.current().nextInt(70, 90);
                advice = "Hot but may rain, bring a raincoat.";
            }
            case "EKONOMIK", "BUDGET" -> {
                temperature = ThreadLocalRandom.current().nextInt(-5, 15);
                condition = WeatherCondition.COLD;
                humidity = ThreadLocalRandom.current().nextInt(50, 70);
                advice = "Dress warm, beanie and scarf are a must!";
            }
            default -> {
                temperature = 22;
                condition = WeatherCondition.PARTLY_CLOUDY;
                humidity = 50;
                advice = "Have a great trip!";
            }
        }
        
        return new WeatherForecast(temperature, condition, advice, humidity);
    }
    
    public WeatherForecast getWeatherByMonth(Month month) {
        return switch (month) {
            case DECEMBER, JANUARY, FEBRUARY -> 
                new WeatherForecast(
                    ThreadLocalRandom.current().nextInt(-5, 10),
                    WeatherCondition.COLD,
                    "Winter season - warm clothes required!",
                    60
                );
            case MARCH, APRIL, MAY -> 
                new WeatherForecast(
                    ThreadLocalRandom.current().nextInt(10, 22),
                    WeatherCondition.PARTLY_CLOUDY,
                    "Spring - variable weather, dress in layers.",
                    55
                );
            case JUNE, JULY, AUGUST -> 
                new WeatherForecast(
                    ThreadLocalRandom.current().nextInt(25, 38),
                    WeatherCondition.SUNNY,
                    "Summer - drink plenty of water, protect from sun!",
                    40
                );
            case SEPTEMBER, OCTOBER, NOVEMBER -> 
                new WeatherForecast(
                    ThreadLocalRandom.current().nextInt(12, 24),
                    WeatherCondition.CLOUDY,
                    "Autumn - keep an umbrella handy.",
                    65
                );
        };
    }
    
    public String getWeatherRecommendation(String category) {
        WeatherForecast forecast = getWeatherByCategory(category);
        return forecast.toString();
    }
}
