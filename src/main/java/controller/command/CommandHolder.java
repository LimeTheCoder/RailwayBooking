package controller.command;


import controller.constants.PagesPaths;

import java.util.HashMap;
import java.util.Map;


public class CommandHolder {
    public enum Method {
        GET, POST
    }

    private final static String DELIMITER = ":";

    private final Command DEFAULT_COMMAND = new DefaultCommand();

    private Map<String, Command> commands = new HashMap<>();

    public CommandHolder() {
        init();
    }

    private void init() {
        commands.put(buildKey(PagesPaths.HOME_PATH, Method.GET),
                new GetHome());
        commands.put(buildKey(PagesPaths.LOGIN_PATH, Method.GET),
                new GetLogin());
        commands.put(buildKey(PagesPaths.LOGIN_PATH, Method.POST),
                new PostLogin());
        commands.put(buildKey(PagesPaths.SIGN_UP_PATH, Method.GET),
                new GetSignUp());
    }

    public Command getCommand(String path, Method method) {
        return commands.getOrDefault(buildKey(path, method), DEFAULT_COMMAND);
    }

    private String buildKey(String path, Method method) {
        return method.name() + DELIMITER + path;
    }
}
