package xyz.papermodloader.paper.launcher;

import cuchaz.enigma.analysis.TranslationIndex;
import cuchaz.enigma.mapping.ClassEntry;
import javassist.*;
import javassist.bytecode.Descriptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DeobfuscatedClasspath implements ClassPath {
    private TranslationIndex translationIndex;

    public DeobfuscatedClasspath(TranslationIndex translationIndex) {
        this.translationIndex = translationIndex;
    }

    @Override
    public InputStream openClassfile(String name) throws NotFoundException {
        ClassEntry classEntry = new ClassEntry(Descriptor.toJvmName(name));
        ClassEntry superclassEntry = this.translationIndex.getSuperclass(classEntry);
        if (superclassEntry == null) {
            throw new NotFoundException(name);
        }
        String superclassName = Descriptor.toJavaName(superclassEntry.toString());

        ClassPool classPool = new ClassPool();
        classPool.insertClassPath(new LoaderClassPath(getClass().getClassLoader()));
        CtClass ct = classPool.makeClass(name, this.isJRE(superclassEntry) ? classPool.get(superclassName) : classPool.makeClass(superclassName));

        try {
            return new ByteArrayInputStream(ct.toBytecode());
        } catch (IOException | CannotCompileException ex) {
            throw new NotFoundException(name);
        }
    }

    @Override
    public URL find(String name) {
        ClassEntry classEntry = new ClassEntry(Descriptor.toJvmName(name));
        ClassEntry superclassEntry = this.translationIndex.getSuperclass(classEntry);
        if (superclassEntry == null) {
            return null;
        }
        try {
            return new URL(String.format("file:///%s/%s", classEntry.toString(), superclassEntry.toString()));
        } catch (MalformedURLException e) {
            return null;
        }
    }

    @Override
    public void close() {

    }

    private boolean isJRE(ClassEntry classEntry) {
        String packageName = classEntry.getPackageName();
        return packageName != null && (packageName.startsWith("java") || packageName.startsWith("javax"));
    }
}
