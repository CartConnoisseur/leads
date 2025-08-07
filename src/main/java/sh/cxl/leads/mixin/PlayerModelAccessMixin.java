package sh.cxl.leads.mixin;

import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sh.cxl.leads.access.PlayerModelAccess;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerModelAccessMixin implements PlayerModelAccess {
    @Shadow @Final private boolean thinArms;

    @Override
    public boolean leads$isSlim() {
        return thinArms;
    }
}
