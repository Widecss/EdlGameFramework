package com.edlplan.framework.ui.animation.interpolate;

import com.edlplan.framework.easing.Easing;

public interface ValueInterpolator<T> {
    public T applyInterplate(T startValue, T endValue, double time, Easing easing);
}
