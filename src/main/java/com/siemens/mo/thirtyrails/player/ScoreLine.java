package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.svg.Svg;

public record ScoreLine(int connections, int squares, int bonus) implements Svg {

    public int sum() {
        return connections + squares + bonus;
    }

    @Override
    public String toSvg(int x, int y) {
        StringBuilder svg = new StringBuilder();
        svg.append("<text x=\"").append(x + 28).append("\" y=\"").append(y + 80).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"blue\">").append(connections).append("</text>\n");
        svg.append("<text x=\"").append(x + 128).append("\" y=\"").append(y + 80).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"blue\">").append(squares).append("</text>\n");
        svg.append("<text x=\"").append(x + 228).append("\" y=\"").append(y + 80).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"blue\">").append(bonus).append("</text>\n");
        svg.append("<text x=\"").append(x + 328).append("\" y=\"").append(y + 80).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"blue\">").append(sum()).append("</text>\n");
        return svg.toString();
    }
}
