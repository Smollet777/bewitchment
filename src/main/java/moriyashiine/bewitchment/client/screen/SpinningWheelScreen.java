package moriyashiine.bewitchment.client.screen;

import moriyashiine.bewitchment.common.Bewitchment;
import moriyashiine.bewitchment.common.screenhandler.SpinningWheelScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class SpinningWheelScreen extends HandledScreen<SpinningWheelScreenHandler> {
	private static final Identifier TEXTURE = new Identifier(Bewitchment.MODID, "textures/gui/distillery_and_spinning_wheel.png");
	
	public SpinningWheelScreen(SpinningWheelScreenHandler container, PlayerInventory inventory, Text title) {
		super(container, inventory, title);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		drawBackground(matrices, delta, mouseX, mouseY);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
	
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		Objects.requireNonNull(client).getTextureManager().bindTexture(TEXTURE);
		int posX = (width - backgroundWidth) / 2;
		int posY = (height - backgroundHeight) / 2;
		drawTexture(matrices, posX, posY, 0, 0, backgroundWidth, backgroundHeight);
		drawTexture(matrices, posX + 70, posY + 32, 176, 0, handler.time.get(0) * 39 / 200, 32);
	}
}