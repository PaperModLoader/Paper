package xyz.papermodloader.paper;

import cuchaz.enigma.analysis.TranslationIndex;
import cuchaz.enigma.mapping.MappingParseException;
import cuchaz.enigma.mapping.Mappings;
import cuchaz.enigma.mapping.MappingsReader;
import xyz.papermodloader.paper.util.delayed.Delayed;
import xyz.papermodloader.paper.util.delayed.DelayedCache;

import java.io.*;

public class Constants {
    public static final Delayed<Mappings> MAPPINGS = new DelayedCache<>(() -> {
        try (Reader reader = new InputStreamReader(Constants.class.getResourceAsStream("/" + Paper.MINECRAFT_VERSION.getVersion() + ".mappings"))) {
            return new MappingsReader().read(reader);
        } catch (IOException | MappingParseException e) {
            throw new RuntimeException("Unable to read mappings", e);
        }
    });

    public static final Delayed<TranslationIndex> OBF_INDEX = new DelayedCache<>(() -> {
        try (InputStream stream = Constants.class.getResourceAsStream("/" + Paper.MINECRAFT_VERSION.getVersion() + "-obf.index")) {
            TranslationIndex index = new TranslationIndex();
            index.read(stream);
            return index;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read obfuscation index", e);
        }
    });

    public static final Delayed<TranslationIndex> DEOBF_INDEX = new DelayedCache<>(() -> {
        try (InputStream stream = Constants.class.getResourceAsStream("/" + Paper.MINECRAFT_VERSION.getVersion() + "-deobf.index")) {
            TranslationIndex index = new TranslationIndex();
            index.read(stream);
            return index;
        } catch (IOException e) {
            throw new RuntimeException("Unable to read deobfuscation index", e);
        }
    });
}
