package com.soushin.cgank.widget.colorpicker.builder;


import com.soushin.cgank.widget.colorpicker.ColorPickerView;
import com.soushin.cgank.widget.colorpicker.renderer.ColorWheelRenderer;
import com.soushin.cgank.widget.colorpicker.renderer.FlowerColorWheelRenderer;
import com.soushin.cgank.widget.colorpicker.renderer.SimpleColorWheelRenderer;

public class ColorWheelRendererBuilder {
    public static ColorWheelRenderer getRenderer(ColorPickerView.WHEEL_TYPE wheelType) {
        switch (wheelType) {
            case CIRCLE:
                return new SimpleColorWheelRenderer();
            case FLOWER:
                return new FlowerColorWheelRenderer();
        }
        throw new IllegalArgumentException("wrong WHEEL_TYPE");
    }
}