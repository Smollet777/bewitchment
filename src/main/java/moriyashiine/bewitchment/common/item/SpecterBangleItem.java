package moriyashiine.bewitchment.common.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.client.network.packet.SpawnSpecterBangleParticlesPacket;
import moriyashiine.bewitchment.common.entity.interfaces.TrueInvisibleAccessor;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class SpecterBangleItem extends TrinketItem {
	public SpecterBangleItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
		if (entity instanceof PlayerEntity player && player.isSneaking() && BewitchmentAPI.drainMagic(player, 1, true)) {
			if (player.getRandom().nextFloat() < 1 / 40f) {
				BewitchmentAPI.drainMagic(player, 1, false);
			}
			if (!((TrueInvisibleAccessor) player).getTrueInvisible()) {
				((TrueInvisibleAccessor) player).setTrueInvisible(true);
				player.setInvisible(true);
			}
			if (player.getRandom().nextFloat() < 1 / 6f) {
				SpawnSpecterBangleParticlesPacket.send(player, player);
				PlayerLookup.tracking(player).forEach(playerEntity -> SpawnSpecterBangleParticlesPacket.send(playerEntity, player));
			}
		}
	}
}
