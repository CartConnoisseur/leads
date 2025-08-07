package sh.cxl.leads.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import sh.cxl.leads.access.LeashStateAccess;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class PlayerLeashStateMixin implements LeashStateAccess {
    @Unique private boolean leashState = false;

    @Override
    public boolean leads$getLeashState() {
        return leashState;
    }

    @Override
    public void leads$setLeashState(boolean state) {
        leashState = state;
    }
}
