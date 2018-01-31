package com.soushin.cgank.widget.colorpicker.builder;

import android.content.DialogInterface;

/**
 * Created by Vondear on 4/17/15.
 */
public interface ColorPickerClickListener {
    void onClick(DialogInterface d, int lastSelectedColor, Integer[] allColors);
}
