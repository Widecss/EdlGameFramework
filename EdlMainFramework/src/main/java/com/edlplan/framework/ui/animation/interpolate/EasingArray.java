package com.edlplan.framework.ui.animation.interpolate;

import com.edlplan.framework.easing.Easing;
import com.edlplan.framework.easing.EasingInterpolator;
import com.edlplan.framework.easing.EasingManager;

public class EasingArray {

    private static final EasingInterpolator[] easings = new EasingInterpolator[Easing.values().length];

    static {
        for (int i = 0; i < easings.length; i++) {
            Easing easing = Easing.getEasing(i);
            easings[i] = v -> (float) EasingManager.apply(easing, v);
        }
        easings[Easing.None.id] = v -> v;
        easings[Easing.In.id] = easings[Easing.InQuad.id] = v -> v * v;
    }

}
