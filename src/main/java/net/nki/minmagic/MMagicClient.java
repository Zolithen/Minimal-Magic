package net.nki.minmagic;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.nki.minmagic.client.BindingRenderer;
import net.nki.minmagic.client.CustomRenderType;
import net.nki.minmagic.client.RenderHelper;

@Mod.EventBusSubscriber(modid = MMagic.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MMagicClient {

    public static BindingRenderer BINDING_RENDERER = new BindingRenderer();

    public static void init(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(MMagicClient::onRenderWorld);
        MinecraftForge.EVENT_BUS.addListener(MMagicClient::onRenderOverlay);
    }

    public static void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
        /*if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            int w = event.getWindow().getGuiScaledWidth();
            int h = event.getWindow().getGuiScaledHeight();
            int posX = w / 2;
            int posY = h / 2;
            Player entity = Minecraft.getInstance().player;

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            if (true) {
                RenderSystem.setShaderTexture(0, new ResourceLocation("minmagic:textures/gui/meter.png"));
                Minecraft.getInstance().gui.blit(event.getMatrixStack(), 0, 0, 0, 0, 192, 68, 192, 68);

                //RenderSystem.setShaderTexture(0, new ResourceLocation("minmagic:textures/gui/metro.png"));
                //Minecraft.getInstance().gui.blit(event.getMatrixStack(), posX + 1, posY + -108, 0, 0, 192, 64, 192, 64);

                Minecraft.getInstance().font.draw(event.getMatrixStack(), "Label text", posX + -116, posY + -103, -1);
            }
            RenderSystem.depthMask(true);
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1, 1, 1, 1);
        }*/
    }

    public static void onRenderWorld(RenderLevelLastEvent event) {
        /*Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        PoseStack ms = event.getPoseStack();
        ms.pushPose();

        Player player = Minecraft.getInstance().player;
        BlockPos playerPos = player.blockPosition();
        BlockPos invalid = new BlockPos(0, -1000, 0);
        int px = playerPos.getX();
        int py = playerPos.getY();
        int pz = playerPos.getZ();
        Level world = player.getCommandSenderWorld();

        ms.translate(-cameraPos.x(), -cameraPos.y(), -cameraPos.z());

        MultiBufferSource.BufferSource bs = Minecraft.getInstance().renderBuffers().bufferSource();
        //bs.getBuffer(RenderType.LINE_STRIP);
        VertexConsumer builder = bs.getBuffer(RenderType.LINES); // POSITION COLOR NORMAL

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int bx = -8; bx <=8 ; bx++) { // TODO : Optimization please
            for (int by = -8; by <=8 ; by++) {
                for (int bz = -8; bz <=8 ; bz++) {
                    pos.set(bx, by, bz);
                    BlockEntity be = world.getBlockEntity(pos);
                    if ((be instanceof IRunetTile) && ( ((IRunetTile) be).getBind() !=  invalid)) {

                    }
                }
            }
        }

        ms.popPose();
        RenderSystem.disableDepthTest();*/
        //bs.endBatch();

        /*Tesselator tes = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tes.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex();
        bufferBuilder.end();*/

        //Vector4f color = new Vector4f(0, 1, 0, 1);
        //renderLine(event, new Vec3(0, 0, 0), new Vec3(10, 10, 10), color, 4);

        //MMagic.LOGGER.info("HOLAAAAAAAAAAAAAAAAA");

        // Setup graphic stuff
        RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);


        Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MultiBufferSource.BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        VertexConsumer builder = buffer.getBuffer(CustomRenderType.WORLD_LINES);
        PoseStack ms = event.getPoseStack();

        ms.pushPose();

        ms.translate(-projectedView.x, -projectedView.y, -projectedView.z);
        Matrix4f matrix = ms.last().pose();
        // Actual drawing

        /*RenderHelper.renderLine(builder, matrix, start, end, RenderHelper.RED);
        RenderHelper.renderLine(builder, matrix, start, new Vec3(2f, 4f, -4f), RenderHelper.RED);*/
        MMagicClient.BINDING_RENDERER.renderBinding(builder, matrix);

        //End of actual drawing
        RenderSystem.disableDepthTest();
        buffer.endBatch();
        ms.popPose();

        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableTexture();
    }
}
