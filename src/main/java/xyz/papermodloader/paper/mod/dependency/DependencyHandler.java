package xyz.papermodloader.paper.mod.dependency;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A handler for dependencies of mods.
 */
public class DependencyHandler {
    private List<ModDependency> dependencies = new ArrayList<>();

    public DependencyHandler(JsonArray array) {
        if (array == null) {
            return;
        }

        for (JsonElement element : array) {
            String dependency = element.getAsString();
            String[] parts = dependency.split("@");
            this.dependencies.add(new ModDependency(parts[0], parts.length > 0 ? parts[1] : null));
        }
    }

    /**
     * @return all the dependencies of this mod.
     */
    public List<ModDependency> getDependencies() {
        return ImmutableList.copyOf(this.dependencies);
    }

    public List<ModDependency> getMissingDependencies() {
        return this.getDependencies().stream().filter(dependency -> !dependency.isInstalled()).collect(Collectors.toList());
    }
}
