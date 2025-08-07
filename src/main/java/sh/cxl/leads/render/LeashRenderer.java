package sh.cxl.leads.render;

import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import sh.cxl.leads.access.PlayerModelAccess;

public class LeashRenderer {
    private static final Identifier TEXTURE = new Identifier("textures/entity/lead_knot.png");

    private static final ModelPart RIGHT_LEASH = buildModel(
            "leads$right_leash",
            ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, -3.0F, -3.0F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F))
    );

    private static final ModelPart RIGHT_LEASH_SLIM = buildModel(
            "leads$right_leash_slim",
            ModelPartBuilder.create().uv(0, 0).cuboid(2.0F, -3.0F, -3.0F, 5.0F, 8.0F, 6.0F, new Dilation(0.0F))
    );

    private static final ModelPart LEFT_LEASH = buildModel(
            "leads$left_leash",
            ModelPartBuilder.create().uv(0, 0).cuboid(3.0F, -3.0F, -3.0F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F))
    );

    private static final ModelPart LEFT_LEASH_SLIM = buildModel(
            "leads$left_leash",
            ModelPartBuilder.create().uv(0, 0).cuboid(3.0F, -3.0F, -3.0F, 5.0F, 8.0F, 6.0F, new Dilation(0.0F))
    );

    private static ModelPart buildModel(String name, ModelPartBuilder builder) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(name, builder, ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32).createModel();
    }

    public static void renderRightArmLeash(MatrixStack matrices, VertexConsumerProvider vertices, int light, PlayerEntityModel<AbstractClientPlayerEntity> model) {
        ModelPart leash = ((PlayerModelAccess) model).leads$isSlim() ? RIGHT_LEASH_SLIM : RIGHT_LEASH;
        renderArmLeash(matrices, vertices, light, model.rightArm, leash);
    }

    public static void renderLeftArmLeash(MatrixStack matrices, VertexConsumerProvider vertices, int light, PlayerEntityModel<AbstractClientPlayerEntity> model) {
        ModelPart leash = ((PlayerModelAccess) model).leads$isSlim() ? LEFT_LEASH_SLIM : LEFT_LEASH;
        renderArmLeash(matrices, vertices, light, model.leftArm, leash);
    }

    public static void renderArmLeash(MatrixStack matrices, VertexConsumerProvider vertices, int light, ModelPart arm, ModelPart leash) {
        leash.pivotX = arm.pivotX;
        leash.pivotY = arm.pivotY;
        leash.pivotZ = arm.pivotZ;
        leash.pitch = arm.pitch;
        leash.yaw = arm.yaw;
        leash.roll = arm.roll;
        leash.xScale = arm.xScale;
        leash.yScale = arm.yScale;
        leash.zScale = arm.zScale;

        leash.render(matrices, vertices.getBuffer(RenderLayer.getEntitySolid(TEXTURE)), light, OverlayTexture.DEFAULT_UV);
    }
}
