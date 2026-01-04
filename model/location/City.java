package model.location;

import java.util.ArrayList;
import java.util.List;

public class City {
    private final String name;
    private final Country country;
    private final List<Attraction> attractions;
    
    public City(String name, Country country) {
        this.name = name;
        this.country = country;
        this.attractions = new ArrayList<>();
    }
    
    public void addAttraction(Attraction attraction) {
        attractions.add(attraction);
    }
    
    public String getName() {
        return name;
    }
    
    public Country getCountry() {
        return country;
    }
    
    public List<Attraction> getAttractions() {
        return new ArrayList<>(attractions);
    }
    
    public String getFullName() {
        return name + ", " + country.getName();
    }
    
    @Override
    public String toString() {
        return getFullName();
    }
    
    public static City paris() {
        City paris = new City("Paris", Country.france());
        paris.addAttraction(new Attraction("Eiffel Tower", "City landmark", 28.0, 3));
        paris.addAttraction(new Attraction("Louvre Museum", "World-famous art museum", 17.0, 4));
        return paris;
    }
    
    public static City rome() {
        City rome = new City("Rome", Country.italy());
        rome.addAttraction(new Attraction("Colosseum", "Ancient Romen amphitheater", 16.0, 2));
        rome.addAttraction(new Attraction("Vatican", "Religious and cultural center", 20.0, 4));
        return rome;
    }
    
    public static City newYork() {
        City ny = new City("New York", Country.usa());
        ny.addAttraction(new Attraction("Statue of Liberty", "Symbol of America", 24.0, 3));
        ny.addAttraction(new Attraction("Central Park", "Giant city park", 0.0, 2));
        return ny;
    }
}
