package ui;

public interface Command {
    void execute();
    
    default String getDescription() {
        return "Command";
    }
}
