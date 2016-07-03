package xyz.papermodloader.paper.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum ClassType {
    EXCLUDED(false, false,
            "java",
            "javax",
            "sun",
            "com.mojang",
            "org.apache",
            "com.google",
            "javassist",
            "org.reflections",
            "gnu.trove",
            "io.netty",
            "joptsimple",
            "paulscode.sound",
            "com.jcraft"
    ),
    MINECRAFT(true, false, "net.minecraft"),
    OTHER(true, true);

    private boolean shouldTranslate;
    private boolean shouldFilter;
    private Set<String> packages;

    ClassType(boolean shouldTranslate, boolean shouldFilter, String... packages) {
        this.shouldTranslate = shouldTranslate;
        this.shouldFilter = shouldFilter;

        this.packages = new HashSet<>();
        Collections.addAll(this.packages, packages);
    }

    public static ClassType get(String name) {
        for (ClassType type : values()) {
            if (type.includes(name)) {
                return type;
            }
        }
        return OTHER;
    }

    public String getPackage(String nameJava) {
        int index = nameJava.lastIndexOf('.');
        if (index == -1) {
            return null;
        }
        return nameJava.substring(0, index);
    }

    public boolean shouldTranslate() {
        return this.shouldTranslate;
    }

    public boolean shouldFilter() {
        return this.shouldFilter;
    }

    public boolean includes(String nameJava) {
        String thePackage = nameJava;
        while ((thePackage = getPackage(thePackage)) != null) {
            if (packages.contains(thePackage)) {
                return true;
            }
        }
        return false;
    }
}
