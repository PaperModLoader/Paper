package xyz.papermodloader.paper.launcher;

import cuchaz.enigma.bytecode.ClassRenamer;
import cuchaz.enigma.bytecode.ClassTranslator;
import cuchaz.enigma.mapping.ClassEntry;
import cuchaz.enigma.mapping.TranslationDirection;
import cuchaz.enigma.mapping.Translator;
import javassist.*;
import javassist.bytecode.Descriptor;
import javassist.bytecode.InnerClassesAttribute;
import net.minecraft.launchwrapper.IClassTransformer;
import xyz.papermodloader.paper.Constants;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.util.ClassType;

import java.io.IOException;

public class PaperClassTransformer implements IClassTransformer {
    private Side side;
    private boolean dev;
    private Translator obfuscator;
    private Translator deobfuscator;
    private ClassTranslator classObfuscator;
    private ClassTranslator classDeobfuscator;
    private ClassFilter filter;

    public PaperClassTransformer(Side side, boolean dev) {
        this.side = side;
        this.dev = dev;
        this.obfuscator = Constants.MAPPINGS.get().getTranslator(TranslationDirection.Obfuscating, Constants.OBF_INDEX.get());
        this.deobfuscator = Constants.MAPPINGS.get().getTranslator(TranslationDirection.Deobfuscating, Constants.DEOBF_INDEX.get());
        this.classObfuscator = new ClassTranslator(this.obfuscator);
        this.classDeobfuscator = new ClassTranslator(this.deobfuscator);
        this.filter = new ClassFilter(side);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        ClassEntry classEntry;
        if (this.dev) {
            classEntry = new ClassEntry(Descriptor.toJvmName(name));
        } else {
            ClassEntry obfClassEntry = new ClassEntry(Descriptor.toJvmName(name));
            if (obfClassEntry.isInDefaultPackage()) {
                obfClassEntry = new ClassEntry(cuchaz.enigma.Constants.NonePackage + "/" + obfClassEntry.getName());
            }
            classEntry = this.deobfuscator.translateEntry(obfClassEntry);
        }

        ClassType type = ClassType.get(Descriptor.toJavaName(classEntry.getClassName()));
        if (!type.shouldFilter() && !type.shouldTranslate()) {
            return bytes;
        }

        try {
            ClassPool classPool = new ClassPool();
            classPool.insertClassPath(new DeobfuscatedClasspath(this.obfuscator.getTranslationIndex()));
            classPool.insertClassPath(new LoaderClassPath(getClass().getClassLoader()));
            classPool.insertClassPath(new ByteArrayClassPath(name, bytes));
            CtClass cls = classPool.get(name);
            this.makePublic(cls);

            if (this.side == Side.CLIENT && type.shouldFilter()) {
                this.filter.filter(cls);
            }

            if (!this.dev) {
                ClassRenamer.moveAllClassesOutOfDefaultPackage(cls, cuchaz.enigma.Constants.NonePackage);
                if (type.shouldTranslate()) {
                    this.classDeobfuscator.translate(cls);
                }
            }

            this.deobfuscator.getTranslationIndex().indexClass(cls, false);

            if (!this.dev) {
                if (type.shouldTranslate()) {
                    this.classObfuscator.translate(cls);
                }
                ClassRenamer.moveAllClassesIntoDefaultPackage(cls, cuchaz.enigma.Constants.NonePackage);
            }

            bytes = cls.toBytecode();
        } catch (NotFoundException | CannotCompileException | IOException ex) {
            throw new RuntimeException(ex);
        }

        return bytes;
    }

    public void makePublic(CtClass cls) {
        cls.setModifiers(Modifier.setPublic(cls.getModifiers()));
        InnerClassesAttribute attribute = (InnerClassesAttribute) cls.getClassFile().getAttribute(InnerClassesAttribute.tag);
        if (attribute != null) {
            for (int i = 0; i < attribute.tableLength(); i++) {
                attribute.setAccessFlags(i, Modifier.setPublic(attribute.accessFlags(i)));
            }
        }
        for (CtField field : cls.getDeclaredFields()) {
            field.setModifiers(Modifier.setPublic(field.getModifiers()));
        }
        for (CtBehavior behavior : cls.getDeclaredBehaviors()) {
            behavior.setModifiers(Modifier.setPublic(behavior.getModifiers()));
        }
    }
}

