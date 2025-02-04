/*
 * All Rights Reserved (c) 2022 MoriyaShiine
 */

package moriyashiine.bewitchment.mixin.client;

import com.mojang.authlib.GameProfile;
import moriyashiine.bewitchment.common.block.entity.WitchCauldronBlockEntity;
import moriyashiine.bewitchment.common.network.packet.CauldronTeleportPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
	public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile, @Nullable PlayerPublicKey publicKey) {
		super(world, profile, publicKey);
	}

	@Inject(method = "sendChatMessage(Ljava/lang/String;Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
	private void sendChatMessage(String message, Text preview, CallbackInfo ci) {
		if (!message.startsWith("/")) {
			for (int i = 0; i < 1; i++) {
				if (world.getBlockEntity(getBlockPos().down(i)) instanceof WitchCauldronBlockEntity witchCauldron && witchCauldron.mode == WitchCauldronBlockEntity.Mode.TELEPORTATION) {
					CauldronTeleportPacket.send(witchCauldron.getPos(), message);
					ci.cancel();
					return;
				}
			}
		}
	}
}
