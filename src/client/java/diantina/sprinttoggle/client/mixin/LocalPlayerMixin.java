package diantina.sprinttoggle.client.mixin;

import diantina.sprinttoggle.client.SprintToggleClient;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    
    @Shadow
    protected int sprintTriggerTime;

    @Inject(method = "canStartSprinting", at = @At("HEAD"), cancellable = true)
    private void disableSprint(CallbackInfoReturnable<Boolean> cir) {
        if (!SprintToggleClient.sprintEnabled) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void forceStopSprint(CallbackInfo ci) {
        if (!SprintToggleClient.sprintEnabled) {
            LocalPlayer self = (LocalPlayer) (Object) this;
            self.setSprinting(false);
            this.sprintTriggerTime = 0;
        }
    }
}