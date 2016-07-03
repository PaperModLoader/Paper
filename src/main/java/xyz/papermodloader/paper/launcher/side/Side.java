package xyz.papermodloader.paper.launcher.side;

public enum Side {
    CLIENT,
    SERVER;

    public Side invert() {
        return this == CLIENT ? SERVER : CLIENT;
    }
}
