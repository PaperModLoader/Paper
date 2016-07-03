package xyz.papermodloader.paper.launcher;

import javassist.*;
import xyz.papermodloader.paper.Paper;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.launcher.side.SideDependent;

public class ClassFilter {
    private Side side;

    public ClassFilter(Side side) {
        this.side = side;
    }

    public void filter(CtClass cls) {
        for (CtBehavior behavior : cls.getDeclaredBehaviors()) {
            try {
                if (behavior.hasAnnotation(SideDependent.class) && ((SideDependent) behavior.getAnnotation(SideDependent.class)).value() != this.side) {
                    if (behavior instanceof CtMethod) {
                        cls.removeMethod((CtMethod) behavior);
                    } else if (behavior instanceof CtConstructor) {
                        cls.removeConstructor((CtConstructor) behavior);
                    }
                }
            } catch (Exception e) {
                Paper.LOGGER.error("Unable to remove behavior " + behavior.getName(), e);
            }
        }

        for (CtField field : cls.getDeclaredFields()) {
            try {
                if (field.hasAnnotation(SideDependent.class) && ((SideDependent) field.getAnnotation(SideDependent.class)).value() != this.side) {
                    cls.removeField(field);
                }
            } catch (Exception e) {
                Paper.LOGGER.error("Unable to remove field " + field.getName(), e);
            }
        }
    }
}
