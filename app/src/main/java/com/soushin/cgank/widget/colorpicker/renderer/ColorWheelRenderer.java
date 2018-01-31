package com.soushin.cgank.widget.colorpicker.renderer;


import com.soushin.cgank.widget.colorpicker.ColorCircle;

import java.util.List;

public interface ColorWheelRenderer {
    float GAP_PERCENTAGE = 0.025f;

    void draw();

    ColorWheelRenderOption getRenderOption();

    void initWith(ColorWheelRenderOption colorWheelRenderOption);

    List<ColorCircle> getColorCircleList();
}
