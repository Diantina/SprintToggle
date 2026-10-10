package diantina.sprinttoggle.client.mixin;

import diantina.sprinttoggle.client.SprintToggleClient;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControlsScreen.class)
public abstract class ControlsScreenMixin extends OptionsSubScreen {

	protected ControlsScreenMixin(Screen lastScreen, Options options, Component title) {
		super(lastScreen, options, title);
	}

	@Inject(method = "addOptions", at = @At("TAIL"))
	private void addSprintButton(CallbackInfo ci) {
		if (this.list != null) {
			this.list.addSmall(
				CycleButton.booleanBuilder(
					Component.literal("ON"),
					Component.literal("OFF"),
					SprintToggleClient.sprintEnabled
				)
					.create(
						0,
						0,
						150,
						20,
						Component.literal("Sprinting"),
						(button, value) -> { SprintToggleClient.setSprintEnabled(value);
						 }
					),
				null
			);
		}
	}
}