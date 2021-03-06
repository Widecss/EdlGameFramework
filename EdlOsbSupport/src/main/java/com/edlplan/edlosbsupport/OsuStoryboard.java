package com.edlplan.edlosbsupport;

import com.edlplan.edlosbsupport.elements.IStoryboardElement;
import com.edlplan.edlosbsupport.elements.StoryboardAnimationSprite;
import com.edlplan.edlosbsupport.elements.StoryboardSprite;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OsuStoryboard implements Serializable {

    public OsuStoryboardLayer[] layers = new OsuStoryboardLayer[StoryboardSprite.Layer.values().length];

    public String backgroundFile;

    public boolean needReplaceBackground() {
        if (backgroundFile == null || layers[0] == null) {
            return false;
        }
        for (IStoryboardElement sprite : layers[0].elements) {
            if (sprite.getClass() == StoryboardSprite.class) {
                if (backgroundFile.equals(((StoryboardSprite) sprite).spriteFilename)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void appendStoryboard(OsuStoryboard storyboard) {
        if (backgroundFile == null) {
            backgroundFile = storyboard.backgroundFile;
        }
        for (OsuStoryboardLayer layer : storyboard.layers) {
            if (layer != null) {
                for (IStoryboardElement element : layer.elements) {
                    if (element instanceof StoryboardSprite)addElement((StoryboardSprite) element);
                }
            }
        }
    }

    public void addElement(StoryboardSprite storyboardElement) {
        if (layers[storyboardElement.layer.ordinal()] == null) {
            layers[storyboardElement.layer.ordinal()] = new OsuStoryboardLayer(storyboardElement.layer);
        }
        layers[storyboardElement.layer.ordinal()].elements.add(storyboardElement);
    }

    public void clear() {
        for (int i = 0; i < layers.length; i++) {
            if (layers[i] != null) {
                layers[i].clear();
                layers[i] = null;
            }
        }
    }

    public Set<String> getAllNeededTextures() {
        HashSet<String> textures = new HashSet<>();
        for (OsuStoryboardLayer layer : layers) {
            if (layer != null) {
                for (IStoryboardElement element : layer.elements) {
                    if (element instanceof StoryboardSprite) {
                        ((StoryboardSprite) element).addAllTextures(textures);
                    }
                }
            }
        }
        return textures;
    }

    public HashMap<String, Integer> countTextureUsedTimes() {
        HashMap<String,Integer> textures = new HashMap<>();
        Integer tmp;
        String tmps;
        for (OsuStoryboardLayer layer : layers) {
            if (layer != null) {
                for (IStoryboardElement element : layer.elements) {
                    if (element instanceof StoryboardAnimationSprite) {
                        StoryboardAnimationSprite as = (StoryboardAnimationSprite) element;
                        for (int i = 0; i < as.frameCount; i++) {
                            if ((tmp = textures.get(tmps = as.buildPath(i))) == null) {
                                textures.put(tmps, 1);
                                continue;
                            }
                            textures.put(tmps, tmp + 1);
                        }
                    } else if (element instanceof StoryboardSprite) {
                        if ((tmp = textures.get(tmps = ((StoryboardSprite) element).spriteFilename)) == null) {
                            textures.put(tmps, 1);
                            continue;
                        }
                        textures.put(tmps, tmp + 1);
                    }
                }
            }
        }
        return textures;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (OsuStoryboardLayer layer : layers) {
            if (layer != null) {
                stringBuilder.append("\n\n").append(layer);
            }
        }
        return stringBuilder.toString();
    }
}
