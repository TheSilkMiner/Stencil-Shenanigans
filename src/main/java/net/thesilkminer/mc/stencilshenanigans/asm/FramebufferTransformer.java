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

    private static final String GSM = "com/mojang/blaze3d/platform/GlStateManager";
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
            //  public createBuffers(IIZ)V
            //   L0
            //    LINENUMBER 85 L0
            //    INVOKEDYNAMIC get()Ljava/util/function/Supplier; [
            //      // handle kind 0x6 : INVOKESTATIC
            //      java/lang/invoke/LambdaMetafactory.metafactory(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;
            //      // arguments:
            //      ()Ljava/lang/Object;,
            //      // handle kind 0x6 : INVOKESTATIC
            //      com/mojang/blaze3d/systems/RenderSystem.isOnRenderThreadOrInit()Z,
            //      ()Ljava/lang/Boolean;
            //    ]
            //    INVOKESTATIC com/mojang/blaze3d/systems/RenderSystem.assertThread (Ljava/util/function/Supplier;)V
            //   L1
            //    LINENUMBER 86 L1
            //    ALOAD 0
            //    ILOAD 1
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferWidth : I
            //   L2
            //    LINENUMBER 87 L2
            //    ALOAD 0
            //    ILOAD 2
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferHeight : I
            //   L3
            //    LINENUMBER 88 L3
            //    ALOAD 0
            //    ILOAD 1
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureWidth : I
            //   L4
            //    LINENUMBER 89 L4
            //    ALOAD 0
            //    ILOAD 2
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureHeight : I
            //   L5
            //    LINENUMBER 90 L5
            //    ALOAD 0
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.genFramebuffers ()I
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferObject : I
            //   L6
            //    LINENUMBER 91 L6
            //    ALOAD 0
            //    INVOKESTATIC net/minecraft/client/renderer/texture/TextureUtil.generateTextureId ()I
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.framebufferTexture : I
            //   L7
            //    LINENUMBER 92 L7
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.useDepth : Z
            //    IFEQ L8
            //   L9
            //    LINENUMBER 93 L9
            //    ALOAD 0
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.genRenderbuffers ()I
            //    PUTFIELD net/minecraft/client/shader/Framebuffer.depthBuffer : I
            //   L8
            //    LINENUMBER 96 L8
            //   FRAME SAME
            //    ALOAD 0
            //    SIPUSH 9728
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.setFramebufferFilter (I)V
            //   L10
            //    LINENUMBER 97 L10
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTexture : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.bindTexture (I)V
            //   L11
            //    LINENUMBER 98 L11
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
            //   L12
            //    LINENUMBER 99 L12
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_FRAMEBUFFER : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferObject : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.bindFramebuffer (II)V
            //   L13
            //    LINENUMBER 100 L13
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_FRAMEBUFFER : I
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_COLOR_ATTACHMENT0 : I
            //    SIPUSH 3553
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTexture : I
            //    ICONST_0
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.framebufferTexture2D (IIIII)V
            //   L14
            //    LINENUMBER 101 L14
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.useDepth : Z
            // <<< INJECTION BEGIN
            //    <fermion-grab:label@L15>
            // >>> INJECTION END
            //    IFEQ L15
            //   L16
            //    LINENUMBER 102 L16
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_RENDERBUFFER : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.depthBuffer : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.bindRenderbuffer (II)V
            // <<< INJECTION BEGIN
            //   L800
            //    LINENUMBER 270 L800
            //    LDC 36161
            //    LDC 35056
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147622_a> : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147620_b> : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.<fermion-remap:func_227678_b_> (IIII)V
            //   L801
            //    LINENUMBER 271 L801
            //    LDC 36160
            //    LDC 36096
            //    LDC 36161
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147624_h> : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.<fermion-remap:func_227693_c_> (IIII)V
            //   L802
            //    LINENUMBER 272 L802
            //    LDC 36160
            //    LDC 36128
            //    LDC 36161
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.<fermion-remap:field_147624_h> : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.<fermion-remap:func_227693_c_> (IIII)V
            //   L803
            //    LINENUMBER 273 L803
            //    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
            //    LDC "[STENCIL SHENANIGANS] Re-initialized stencil support on the current framebuffer as per request"
            //    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
            //    GOTO L15
            // >>> INJECTION END
            //   L17
            //    LINENUMBER 103 L17
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_RENDERBUFFER : I
            //    LDC 33190
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureWidth : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.framebufferTextureHeight : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.renderbufferStorage (IIII)V
            //   L18
            //    LINENUMBER 104 L18
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_FRAMEBUFFER : I
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_DEPTH_ATTACHMENT : I
            //    GETSTATIC net/minecraft/client/shader/FramebufferConstants.GL_RENDERBUFFER : I
            //    ALOAD 0
            //    GETFIELD net/minecraft/client/shader/Framebuffer.depthBuffer : I
            //    INVOKESTATIC com/mojang/blaze3d/platform/GlStateManager.framebufferRenderbuffer (IIII)V
            //   L15
            //    LINENUMBER 107 L15
            //   FRAME SAME
            //    ALOAD 0
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.checkFramebufferComplete ()V
            //   L19
            //    LINENUMBER 108 L19
            //    ALOAD 0
            //    ILOAD 3
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.framebufferClear (Z)V
            //   L20
            //    LINENUMBER 109 L20
            //    ALOAD 0
            //    INVOKEVIRTUAL net/minecraft/client/shader/Framebuffer.unbindFramebufferTexture ()V
            //   L21
            //    LINENUMBER 110 L21
            //    RETURN
            //   L22
            //    LOCALVARIABLE this Lnet/minecraft/client/shader/Framebuffer; L0 L22 0
            //    LOCALVARIABLE p_216492_1_ I L0 L22 1
            //    LOCALVARIABLE p_216492_2_ I L0 L22 2
            //    LOCALVARIABLE p_216492_3_ Z L0 L22 3
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
                        && Opcodes.INVOKESTATIC == opcode && GSM.equals(owner) && MappingUtilities.INSTANCE.mapMethod("func_227730_i_").equals(name) && "(II)V".equals(descriptor)) {
                    LOGGER.info("Identified 'INVOKESTATIC {}.{} (II)V': performing injection", GSM, MappingUtilities.INSTANCE.mapMethod("func_227730_i_"));
                    this.inject();
                    this.injectionComplete = true;
                }

                if (!this.injectionComplete && this.jumpLabel == null
                        && Opcodes.INVOKESTATIC == opcode && GSM.equals(owner) && MappingUtilities.INSTANCE.mapMethod("func_227645_a_").equals(name) && "(IIIII)V".equals(descriptor)) {
                    LOGGER.info("Identified 'INVOKESTATIC {}.{} (IIIII)V'", GSM, MappingUtilities.INSTANCE.mapMethod("func_227645_a_"));
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
                super.visitLineNumber(2 * 100 + 7 * 10, l800);
                super.visitLdcInsn(36161);
                super.visitLdcInsn(35056);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147622_a"), "I");
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147620_b"), "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, GSM, MappingUtilities.INSTANCE.mapMethod("func_227678_b_"), "(IIII)V", false);

                final Label l801 = new Label();
                super.visitLabel(l801);
                super.visitLineNumber(2 * 100 + 7 * 10 + 1, l801);
                super.visitLdcInsn(36160);
                super.visitLdcInsn(36096);
                super.visitLdcInsn(36161);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147624_h"), "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, GSM, MappingUtilities.INSTANCE.mapMethod("func_227693_c_"), "(IIII)V", false);

                final Label l802 = new Label();
                super.visitLabel(l802);
                super.visitLineNumber(2 * 100 + 7 * 10 + 2, l802);
                super.visitLdcInsn(36160);
                super.visitLdcInsn(36128);
                super.visitLdcInsn(36161);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitFieldInsn(Opcodes.GETFIELD, THIS, MappingUtilities.INSTANCE.mapField("field_147624_h"), "I");
                super.visitMethodInsn(Opcodes.INVOKESTATIC, GSM, MappingUtilities.INSTANCE.mapMethod("func_227693_c_"), "(IIII)V", false);

                final Label l803 = new Label();
                super.visitLabel(l803);
                super.visitLineNumber(2 * 100 + 7 * 10 + 3, l803);
                super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                super.visitLdcInsn("[STENCIL SHENANIGANS] Re-initialized stencil support on the current framebuffer as per request");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                super.visitJumpInsn(Opcodes.GOTO, this.jumpLabel);
            }
        };
    }
}
