package xyz.papermodloader.paper.util;

import java.util.*;

public class Arguments {
    private Map<String, String> arguments = new HashMap<>();

    public Arguments(String[] args) {
        this(Arrays.asList(args));
    }

    public Arguments(List<String> args) {
        for (int i = 0; i < args.size(); i++) {
            String arg = args.get(i);
            if (arg.startsWith("--")) {
                this.arguments.put(arg.substring(2, arg.length()), args.get(i + 1));
            }
        }
    }

    public String get(String key) {
        if (this.has(key)) {
            return this.arguments.get(key);
        } else {
            return "";
        }
    }

    public void remove(String key) {
        this.arguments.remove(key);
    }

    public boolean has(String key) {
        return this.arguments.containsKey(key);
    }

    public void add(String key, String value) {
        this.arguments.put(key, value);
    }

    public String[] build() {
        String[] args = new String[this.arguments.size() * 2];
        List<Map.Entry<String, String>> entries = new ArrayList<>(this.arguments.entrySet());
        for (int i = 0; i < this.arguments.size(); i++) {
            Map.Entry<String, String> entry = entries.get(i);
            args[i * 2] = "--" + entry.getKey();
            args[i * 2 + 1] = entry.getValue();
        }
        return args;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Arguments[");
        List<Map.Entry<String, String>> entries = new ArrayList<>(this.arguments.entrySet());
        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<String, String> entry = entries.get(i);
            builder.append(entry.getKey()).append("=").append(entry.getValue());
            if (i - 1 < entries.size()) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
