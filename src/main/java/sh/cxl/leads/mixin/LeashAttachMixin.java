package sh.cxl.leads.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
//? if >=1.21 {
import net.minecraft.entity.Leashable;
//?} else {
/*import net.minecraft.entity.mob.MobEntity;
*///?}
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.cxl.leads.access.LeashStateAccess;

import java.util.List;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class LeashAttachMixin {
    @Shadow private ClientWorld world;

    @Inject(
            method = "onEntityAttach",
            at = @At(
                    value = "INVOKE",
                    //? if >=1.21 {
                    target = "Lnet/minecraft/entity/Leashable;setUnresolvedLeashHolderId(I)V"
                    //?} else {
                    /*target = "Lnet/minecraft/entity/mob/MobEntity;setHoldingEntityId(I)V"
                     *///?}
            )
    )
    private void onEntityAttach(EntityAttachS2CPacket packet, CallbackInfo ci, @Local /*? >=1.21 {*/ Leashable target /*?} else {*//* Entity targetEntity *//*?}*/) {
        //? if <1.21
        /*MobEntity target = (MobEntity) targetEntity;*/

        if (packet.getHoldingEntityId() == 0) {
            if (getLeashHolder(target) instanceof AbstractClientPlayerEntity player) {
                Box box = new Box(player.getX() - 16.0, player.getY() - 16.0, player.getZ() - 16.0, player.getX() + 16.0, player.getY() + 16.0, player.getZ() + 16.0);
                List<Entity> nearby = player.getWorld().getNonSpectatingEntities(Entity.class, box);

                for (Entity entity : nearby) {
                    if (entity instanceof /*? >=1.21 {*/ Leashable /*?} else {*//* MobEntity *//*?}*/ leashable) {
                        if (leashable != target && getLeashHolder(leashable) == player) {
                            return;
                        }
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

    @Unique
    private Entity getLeashHolder(/*? >=1.21 {*/ Leashable /*?} else {*//* MobEntity *//*?}*/ leashable) {
        //? if >=1.21 {
        return leashable.getLeashHolder();
        //?} else {
        /*return leashable.getHoldingEntity();
         *///?}
    }
}
