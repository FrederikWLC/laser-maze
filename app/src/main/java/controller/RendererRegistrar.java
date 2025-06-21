package controller;

import view.GamePanel;
import view.TokenRenderer;
import view.ITurnableTokenRenderer;
import view.LaserTokenRenderer;
import view.TargetMirrorTokenRenderer;
import view.DoubleMirrorTokenRenderer;
import view.CellBlockerTokenRenderer;
import view.SplitterBeamTokenRenderer;
import view.CheckpointTokenRenderer;
import view.util.TokenImageLoader;
import view.PortalTokenRenderer;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class RendererRegistrar {
    public static void registerRenderers(GamePanel gamePanel) {
        TokenImageLoader loader = new TokenImageLoader();
        Map<String, BufferedImage> tokenImages = loader.loadTokenImages();

        Map<String, TokenRenderer> staticRenderers = new HashMap<>();
        Map<String, ITurnableTokenRenderer> turnableRenderers = new HashMap<>();

        turnableRenderers.put("LaserToken", new LaserTokenRenderer(tokenImages));
        turnableRenderers.put("TargetMirrorToken", new TargetMirrorTokenRenderer(tokenImages));
        turnableRenderers.put("DoubleMirrorToken", new DoubleMirrorTokenRenderer(tokenImages));
        staticRenderers.put("CellBlockerToken", new CellBlockerTokenRenderer(tokenImages));
        turnableRenderers.put("BeamSplitterToken", new SplitterBeamTokenRenderer(tokenImages));
        turnableRenderers.put("CheckpointToken", new CheckpointTokenRenderer(tokenImages));
        turnableRenderers.put("PortalToken", new PortalTokenRenderer(tokenImages));


        gamePanel.setStaticRenderers(staticRenderers);
        gamePanel.setTurnableRenderers(turnableRenderers);
        gamePanel.setTokenImages(tokenImages);
    }
}
