package com.edlplan.edlosbsupport;

import com.edlplan.edlosbsupport.elements.IStoryboardElement;
import com.edlplan.edlosbsupport.elements.StoryboardSprite;

import java.io.Serializable;

public class OsuStoryboard implements Serializable {

    public OsuStoryboardLayer[] layers = new OsuStoryboardLayer[StoryboardSprite.Layer.values().length];

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
