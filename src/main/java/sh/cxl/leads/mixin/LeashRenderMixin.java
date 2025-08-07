package sh.cxl.leads.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.cxl.leads.access.LeashStateAccess;
import sh.cxl.leads.render.LeashRenderer;

@Mixin(PlayerEntityRenderer.class)
public abstract class LeashRenderMixin {
    @Inject(method = "renderRightArm", at = @At("TAIL"))
    public void renderRightArmLead(MatrixStack matrices, VertexConsumerProvider vertices, int light, AbstractClientPlayerEntity player, CallbackInfo ci) {
        if (((LeashStateAccess) player).leads$getLeashState() && MinecraftClient.getInstance().options.getMainArm().getValue() == Arm.RIGHT) {
            PlayerEntityModel<AbstractClientPlayerEntity> model = ((PlayerEntityRenderer) (Object) this).getModel();
            LeashRenderer.renderRightArmLeash(matrices, vertices, light, model);
        }
    }

    @Inject(method = "renderLeftArm", at = @At("TAIL"))
    public void renderLeftArmLead(MatrixStack matrices, VertexConsumerProvider vertices, int light, AbstractClientPlayerEntity player, CallbackInfo ci) {
        if (((LeashStateAccess) player).leads$getLeashState() && MinecraftClient.getInstance().options.getMainArm().getValue() == Arm.LEFT) {
            PlayerEntityModel<AbstractClientPlayerEntity> model = ((PlayerEntityRenderer) (Object) this).getModel();
            LeashRenderer.renderLeftArmLeash(matrices, vertices, light, model);
        }
    }
}
