package com.edlplan.framework.ui.drawable.sprite;

import com.edlplan.framework.MContext;
import com.edlplan.framework.graphics.opengl.BaseCanvas;
import com.edlplan.framework.graphics.opengl.BlendType;
import com.edlplan.framework.math.Color4;

public abstract class Sprite<S extends SpriteShader> extends AbstractSprite {

    protected float alpha = 1;

    protected Color4 accentColor = Color4.ONE.copyNew();

    protected BlendType blendType = BlendType.Normal;

    public Sprite(MContext c) {
        super(c);
    }

    public void setBlendType(BlendType blendType) {
        this.blendType = blendType;
    }

    public BlendType getBlendType() {
        return blendType;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAccentColor(Color4 accentColor) {
        this.accentColor.set(accentColor);
        this.accentColor.toPremultipledThis();
    }

    public Color4 getAccentColor() {
        return accentColor;
    }

    protected abstract S getShader();

    private boolean changeBlend = false;

    @Override
    protected void startDraw(BaseCanvas canvas) {
        getShader().useThis();
        changeBlend = (canvas.getBlendSetting().getBlendType() == blendType);
        if (changeBlend) {
            canvas.getBlendSetting().save();
            canvas.getBlendSetting().setBlendType(blendType);
        }
    }

    protected void prepareColorUniformBase(BaseCanvas canvas) {
        getShader().loadAccentColor(accentColor.copyNew().multipleAlpha(alpha * canvas.getCanvasAlpha()));
        getShader().loadAlpha(1);
    }

    @Override
    protected void prepareShader(BaseCanvas canvas) {
        prepareColorUniformBase(canvas);
        getShader().loadCamera(canvas.getCamera());
    }

    @Override
    protected void endDraw(BaseCanvas canvas) {

        if (changeBlend) {
            canvas.getBlendSetting().restore();
            changeBlend = false;
        }
    }
}
