package xyz.papermodloader.paper.mod.dependency;

/**
 * A SemVer version container.
 */
public class Version implements Comparable<Version> {
    /**
     * The major version.
     */
    private int major;

    /**
     * The minor version.
     */
    private int minor;

    /**
     * The patch.
     */
    private int patch;

    /**
     * The tag of this version, can be null.
     */
    private String tag;

    public Version(String version) throws NumberFormatException {
        String[] parts = version.split("\\-");
        version = parts[0];
        String[] values = version.split("\\.");
        this.major = Integer.parseInt(values[0]);
        this.minor = Integer.parseInt(values[1]);
        this.patch = Integer.parseInt(values[2]);
        this.tag = parts.length > 1 ? parts[1] : "";
    }

    /**
     * @return the full version string of this version object.
     */
    public String getVersion() {
        return this.major + "." + this.minor + "." + this.patch + (this.tag == null || this.tag.isEmpty() ? "" : "-" + this.tag);
    }

    @Override
    public String toString() {
        return this.getVersion();
    }

    /**
     * @param version the version to compare with.
     * @return true if this version is newer than the parameter.
     */
    public boolean isNewer(Version version) {
        return this.major > version.major || this.major == version.major && this.minor > version.minor || this.major == version.major && this.minor == version.minor && this.patch > version.patch;
    }

    /**
     *
     * @param version the version to compare with.
     * @return true if this version is older than the parameter.
     */
    public boolean isOlder(Version version) {
        return version.isNewer(this);
    }

    /**
     * @param version the version to compare with.
     * @return true if the major and minor version numbers of this version are the same as the parameter.
     */
    public boolean isCompatibleWith(Version version) {
        return this.major == version.major && this.minor == version.minor;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object instanceof Version) {
            Version version = (Version) object;
            return version.patch == this.patch && version.minor == this.minor && version.major == this.major;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Version version) {
        if (this.isNewer(version)) {
            return 1;
        } else if (this.isOlder(version)) {
            return -1;
        } else {
            return 0;
        }
    }
}
