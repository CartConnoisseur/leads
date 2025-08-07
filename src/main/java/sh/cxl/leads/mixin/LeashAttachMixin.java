package sh.cxl.leads.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.cxl.leads.access.LeashStateAccess;

import java.util.List;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class LeashAttachMixin {
    @Shadow private ClientWorld world;

    @Inject(method = "onEntityAttach", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;setHoldingEntityId(I)V"))
    private void onEntityAttach(EntityAttachS2CPacket packet, CallbackInfo ci, @Local Entity entity) {
        MobEntity target = (MobEntity) entity;

        if (packet.getHoldingEntityId() == 0) {
            if (target.getHoldingEntity() instanceof AbstractClientPlayerEntity player) {
                Box box = new Box(player.getX() - 16.0, player.getY() - 16.0, player.getZ() - 16.0, player.getX() + 16.0, player.getY() + 16.0, player.getZ() + 16.0);
                List<MobEntity> nearby = player.getWorld().getNonSpectatingEntities(MobEntity.class, box);

                for (MobEntity mob : nearby) {
                    if (mob != target && mob.getHoldingEntity() == player) {
                        return;
                    }
                }

                ((LeashStateAccess) player).leads$setLeashState(false);
            }
        } else {
            if (world.getEntityById(packet.getHoldingEntityId()) instanceof LeashStateAccess player) {
                player.leads$setLeashState(true);
            }
        }
    }
}
