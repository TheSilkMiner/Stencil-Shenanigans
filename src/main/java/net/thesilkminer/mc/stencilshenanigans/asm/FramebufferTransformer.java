package net.thesilkminer.mc.stencilshenanigans.asm;

import com.google.common.collect.ImmutableList;
import net.thesilkminer.mc.fermion.asm.api.LaunchPlugin;
import net.thesilkminer.mc.fermion.asm.api.MappingUtilities;
import net.thesilkminer.mc.fermion.asm.api.descriptor.ClassDescriptor;
import net.thesilkminer.mc.fermion.asm.api.descriptor.MethodDescriptor;
import net.thesilkminer.mc.fermion.asm.api.transformer.TransformerData;
import net.thesilkminer.mc.fermion.asm.prefab.transformer.SingleTargetMethodTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

final class FramebufferTransformer extends SingleTargetMethodTransformer {

    private static final String GLX = "com/mojang/blaze3d/platform/GLX";
    private static final String THIS = "net/minecraft/client/shader/Framebuffer";

    private static final Logger LOGGER = LogManager.getLogger("FramebufferTransformer");

    FramebufferTransformer(@Nonnull final LaunchPlugin owner) {
        super(
                TransformerData.Builder.create()
                        .setOwningPlugin(owner)
                        .setName("framebuffer")
                        .setDescription("Modifies the Framebuffer creation so that stencils are enabled. Disable this patch only if your graphic card crashes due to stencils")
                        .build(),
                ClassDescriptor.of(THIS),
                MethodDescriptor.of(
                        "func_216492_b",
                        ImmutableList.of(ClassDescriptor.of(int.class), ClassDescriptor.of(int.class), ClassDescriptor.of(boolean.class)),
                        ClassDescriptor.of(void.class)
                )
        );
    }

    @Nonnull
    @Override
    @SuppressWarnings("SpellCheckingInspection")
    protected BiFunction<Integer, MethodVisitor, MethodVisitor> getMethodVisitorCreator() {
        return (v, mv) -> new MethodVisitor(v, mv) {
            //  // access flags 0x1
            //  public func_216492_b(IIZ)V
            //   L0
            //    LINENUMBER 78 L0
            //    ALOAD 0
            //    ILOAD 1
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferWidth : I
            //   L1
            //    LINENUMBER 79 L1
            //    ALOAD 0
            //    ILOAD 2
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferHeight : I
            //   L2
            //    LINENUMBER 80 L2
            //    ALOAD 0
            //    ILOAD 1
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureWidth : I
            //   L3
            //    LINENUMBER 81 L3
            //    ALOAD 0
            //    ILOAD 2
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureHeight : I
            //   L4
            //    LINENUMBER 82 L4
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.isUsingFBOs ()Z
            //    IFNE L5
            //   L6
            //    LINENUMBER 83 L6
            //    ALOAD 0
            //    ILOAD 3
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.framebufferClear (Z)V
            //    GOTO L7
            //   L5
            //    LINENUMBER 85 L5
            //   FRAME SAME
            //    ALOAD 0
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glGenFramebuffers ()I
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferObject : I
            //   L8
            //    LINENUMBER 86 L8
            //    ALOAD 0
            //    INVOKESTATIC com/mojang/blaze3d/platform/TextureUtil.generateTextureId ()I
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferTexture : I
            //   L9
            //    LINENUMBER 87 L9
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.useDepth : Z
            //    IFEQ L10
            //   L11
            //    LINENUMBER 88 L11
            //    ALOAD 0
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glGenRenderbuffers ()I
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.depthBuffer : I
            //   L10
            //    LINENUMBER 91 L10
            //   FRAME SAME
            //    ALOAD 0
            //    SIPUSH 9728
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.setFramebufferFilter (I)V
            //   L12
            //    LINENUMBER 92 L12
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTexture : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.bindTexture (I)V
            //   L13
            //    LINENUMBER 93 L13
            //    SIPUSH 3553
            //    ICONST_0
            //    LDC 32856
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureWidth : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureHeight : I
            //    ICONST_0
            //    SIPUSH 6408
            //    SIPUSH 5121
            //    ACONST_NULL
            //    CHECKCAST java/nio/IntBuffer
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.texImage2D (IIIIIIIILjava/nio/IntBuffer;)V
            //   L14
            //    LINENUMBER 94 L14
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_FRAMEBUFFER : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferObject : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glBindFramebuffer (II)V
            //   L15
            //    LINENUMBER 95 L15
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_FRAMEBUFFER : I
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_COLOR_ATTACHMENT0 : I
            //    SIPUSH 3553
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTexture : I
            //    ICONST_0
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glFramebufferTexture2D (IIIII)V
            //   L16
            //    LINENUMBER 96 L16
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.useDepth : Z
            // <<< INJECTION BEGIN
            //    <fermion-grab:label@L17>
            // >>> INJECTION END
            //    IFEQ L17
            //   L18
            //    LINENUMBER 97 L18
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_RENDERBUFFER : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.depthBuffer : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glBindRenderbuffer (II)V
            // <<< INJECTION BEGIN
            //   L800
            //    LINENUMBER 250 L800
            //    LDC 36161
            //    LDC 35056
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147622_a> : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147620_b> : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glRenderbufferStorage (IIII)V
            //   L801
            //    LINENUMBER 251 L801
            //    LDC 36160
            //    LDC 36096
            //    LDC 36161
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147624_h> : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glFramebufferRenderbuffer (IIII)V
            //   L802
            //    LINENUMBER 252 L802
            //    LDC 36160
            //    LDC 36128
            //    LDC 36161
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147624_h> : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glFramebufferRenderbuffer (IIII)V
            //   L803
            //    LINENUMBER 253 L803
            //    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
            //    LDC "[STENCIL SHENANIGANS] Re-initialized stencil support on the current framebuffer as per request"
            //    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
            //    GOTO L17
            // >>> INJECTION END
            //   L19
            //    LINENUMBER 98 L19
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_RENDERBUFFER : I
            //    LDC 33190
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureWidth : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureHeight : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glRenderbufferStorage (IIII)V
            //   L20
            //    LINENUMBER 99 L20
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_FRAMEBUFFER : I
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_DEPTH_ATTACHMENT : I
            //    GETSTATIC com/mojang/blaze3d/platform/GLX.GL_RENDERBUFFER : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.depthBuffer : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GLX.glFramebufferRenderbuffer (IIII)V
            //   L17
            //    LINENUMBER 102 L17
            //   FRAME SAME
            //    ALOAD 0
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.checkFramebufferComplete ()V
            //   L21
            //    LINENUMBER 103 L21
            //    ALOAD 0
            //    ILOAD 3
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.framebufferClear (Z)V
            //   L22
            //    LINENUMBER 104 L22
            //    ALOAD 0
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.unbindFramebufferTexture ()V
            //   L7
            //    LINENUMBER 106 L7
            //   FRAME SAME
            //    RETURN
            //   L23
            //    LOCALVARIABLE this Lnet/minecraft/client/shader/Framebuffer; L0 L23 0
            //    LOCALVARIABLE p_216492_1_ I L0 L23 1
            //    LOCALVARIABLE p_216492_2_ I L0 L23 2
            //    LOCALVARIABLE p_216492_3_ Z L0 L23 3
            //    MAXSTACK = 9
            //    MAXLOCALS = 4

            private boolean glFrameBufferTexture2dIdentified;
            private boolean useDepthIdentified;
            private Label jumpLabel;
            private boolean injectionComplete;

            @Override
            public void visitMethodInsn(final int opcode, @Nonnull final String owner, @Nonnull final String name, @Nonnull final String descriptor, final boolean isInterface) {
                super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

                if (!this.injectionComplete && this.jumpLabel != null
                        && Opcodes.INVOKESTATIC == opcode && GLX.equals(owner) && "glBindRenderbuffer".equals(name) && "(II)V".equals(descriptor)) {
                    LOGGER.info("Identified 'INVOKESTATIC {}.glBindRenderbuffer (II)V': performing injection", GLX);
                    this.inject();
                    this.injectionComplete = true;
                }

                if (!this.injectionComplete && this.jumpLabel == null
                        && Opcodes.INVOKESTATIC == opcode && GLX.equals(owner) && "glFramebufferTexture2D".equals(name) && "(IIIII)V".equals(descriptor)) {
                    LOGGER.info("Identified 'INVOKESTATIC {}.glFramebufferTexture2D (IIIII)V'", GLX);
                    this.glFrameBufferTexture2dIdentified = true;
                }
            }

            @Override
            public void visitFieldInsn(final int opcode, @Nonnull final String owner, @Nonnull final String name, @Nonnull final String descriptor) {
                super.visitFieldInsn(opcode, owner, name, descriptor);

                if (!this.injectionComplete && this.glFrameBufferTexture2dIdentified && this.jumpLabel == null
                        && opcode == Opcodes.GETFIELD && THIS.equals(owner) && "Z".equals(descriptor) && MappingUtilities.INSTANCE.mapField("field_147619_e").equals(name)) {
                    LOGGER.info("Identified 'GETFIELD {}.{} : Z'", THIS, MappingUtilities.INSTANCE.mapField("field_147619_e"));
                    this.useDepthIdentified = true;
                }
            }

            @Override
            public void visitJumpInsn(final int opcode, @Nonnull final Label label) {
                super.visitJumpInsn(opcode, label);

                if (!this.injectionComplete && this.jumpLabel == null && this.useDepthIdentified && opcode == Opcodes.IFEQ) {
                    LOGGER.info("Successfully identified 'IFEQ' jump instruction: label grabbed successfully");
                    this.jumpLabel = label;
                }
            }

            private void inject() {
                final Label l800 = new Label();
                super.visitLabel(l800);
                super.visitLineNumber(2 * 100 + 5 * 10, l800);
                super.visitLdcInsn(36161);
                super.visitLdcInsn(35056);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147622_a"), "I");
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147620_b"), "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, GLX, "glRenderbufferStorage", "(IIII)V", false);

                final Label l801 = new Label();
                super.visitLabel(l801);
                super.visitLineNumber(2 * 100 + 5 * 10 + 1, l801);
                super.visitLdcInsn(36160);
                super.visitLdcInsn(36096);
                super.visitLdcInsn(36161);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147624_h"), "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, GLX, "glFramebufferRenderbuffer", "(IIII)V", false);

                final Label l802 = new Label();
                super.visitLabel(l802);
                super.visitLineNumber(2 * 100 + 5 * 10 + 2, l802);
                super.visitLdcInsn(36160);
                super.visitLdcInsn(36128);
                super.visitLdcInsn(36161);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147624_h"), "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, GLX, "glFramebufferRenderbuffer", "(IIII)V", false);

                final Label l803 = new Label();
                super.visitLabel(l803);
                super.visitLineNumber(2 * 100 + 5 * 10 + 3, l803);
                super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                super.visitLdcInsn("[STENCIL SHENANIGANS] Re-initialized stencil support on the current framebuffer as per request");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                super.visitJumpInsn(Opcodes.GOTO, this.jumpLabel);
            }
        };
    }
}
