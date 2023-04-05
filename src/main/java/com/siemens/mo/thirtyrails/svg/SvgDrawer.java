package com.siemens.mo.thirtyrails.svg;

import org.springframework.stereotype.Component;

@Component
public class SvgDrawer {

    public <T extends Svg> String drawSvg(T svg, int width, int height) {
        return "<svg viewBox=\"0 0 " + width + " " + height + "\" xmlns=\"http://www.w3.org/2000/svg\" >\n" +
                svg.toSvg(0, 0) +
                "</svg>";
    }
}
