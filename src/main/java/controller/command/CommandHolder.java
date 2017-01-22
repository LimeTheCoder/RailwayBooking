package controller.command;


import java.util.HashMap;
import java.util.Map;

public class CommandHolder {
    public enum Method {
        GET, POST
    }

    public final static String DELIMITER = ":";

    public final static String HOME_PATH = "/home";

    private final Command DEFAULT_COMMAND = new DefaultCommand();

    private Map<String, Command> commands = new HashMap<>();

    public CommandHolder() {
        init();
    }

    private void init() {
        commands.put(buildKey(HOME_PATH, Method.GET), new HomeCommand());
    }

    public Command getCommand(String path, Method method) {
        return commands.getOrDefault(buildKey(path, method), DEFAULT_COMMAND);
    }

    private String buildKey(String path, Method method) {
        return method.name() + DELIMITER + path;
    }
}
