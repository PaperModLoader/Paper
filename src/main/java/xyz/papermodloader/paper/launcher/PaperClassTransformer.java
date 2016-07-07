package xyz.papermodloader.paper.launcher;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import xyz.papermodloader.book.mapping.Mappings;
import xyz.papermodloader.paper.launcher.side.Side;

public class PaperClassTransformer implements IClassTransformer {
    private Side side;
    private boolean dev;
    private Mappings mappings;

    public PaperClassTransformer(Side side, boolean dev) {
        this.side = side;
        this.dev = dev;
        this.mappings = Mappings.parseMappings(PaperClassTransformer.class.getResourceAsStream("/1.10.2.json"));
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        ClassNode classNode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept(classNode, 0);
        if (this.dev) {
            classNode.access = this.setPublic(classNode.access);
            for (MethodNode method : classNode.methods) {
                method.access = this.setPublic(method.access);
            }
            for (FieldNode field : classNode.fields) {
                field.access = this.setPublic(field.access);
            }
        } else {
            classNode.name = this.mappings.getClassMapping(classNode.name);
        }
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);
        return writer.toByteArray();
    }

    private int setPublic(int access) {
        return (access & ~(Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED)) | Opcodes.ACC_PUBLIC;
    }
}

