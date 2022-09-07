package net.nki.minmagic.client;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.world.phys.Vec3;

public class RenderHelper {
    // Colors
    public static Vector4f RED = new Vector4f(1, 0, 0, 1);

    private static Vec3 offset1 = new Vec3(0.5f, 1.0f, 0.5f);
    private static Vec3 offset2 = new Vec3(0.5f, 0.0f, 0.5f);
    public static void renderLine(VertexConsumer builder, Matrix4f matrix, Vec3 p1, Vec3 p2, Vector4f color)
    {
        builder.vertex(matrix, (float)p1.x, (float)p1.y, (float)p1.z)
                .color(color.x(), color.y(), color.z(), color.w())
                .endVertex();
        builder.vertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z)
                .color(color.x(), color.y(), color.z(), color.w())
                .endVertex();
    }

    public static void renderLineBetweenBlocks(VertexConsumer builder, Matrix4f matrix, Vec3 b1, Vec3 b2, Vector4f color) {
        renderLine(builder, matrix, b1.add(offset1), b2.add(offset2), color);
    }
}
