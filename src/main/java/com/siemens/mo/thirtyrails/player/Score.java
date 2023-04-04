package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.svg.Svg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Score(Map<String, ScoreLine> lines, int mineScore) implements Svg {

    private static final Map<Integer, List<Integer>> scores = new HashMap<>();
    private static final Map<String, Integer> scoreLineIndex = new HashMap<>();
    static {
        scores.put(1, List.of(1, 2, 1));
        scores.put(2, List.of(1, 3, 2));
        scores.put(3, List.of(1, 4, 3));
        scores.put(4, List.of(2, 3, 3));
        scores.put(5, List.of(2, 4, 4));
        scores.put(6, List.of(3, 4, 5));

        scoreLineIndex.put("1-2", 0);
        scoreLineIndex.put("1-3", 1);
        scoreLineIndex.put("1-4", 2);
        scoreLineIndex.put("2-3", 3);
        scoreLineIndex.put("2-4", 4);
        scoreLineIndex.put("3-4", 5);
    }

    public int sum() {
        return mineScore + lines.values().stream().map(ScoreLine::sum).reduce(Integer::sum).orElse(0);
    }

    @Override
    public String toSvg(int x, int y) {
        StringBuffer svg = new StringBuffer();
        svg.append(scoreBoard(x, y));
        svg.append("<text x=\"").append(x + 528).append("\" y=\"").append(y + 980).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"blue\">").append(sum()).append("</text>\n");

        for (String line : lines.keySet()) {
            int row = scoreLineIndex.get(line);
            svg.append(lines.get(line).toSvg(x + 200, y + 100 + row * 100));
        }
        return svg.toString();
    }

    private String scoreBoard(int x, int y) {
        StringBuffer svg = new StringBuffer();
        for (int i = 0; i < 7; i++) {
            svg.append("<line x1=\"").append(x).append("\" y1=\"").append(y + 100 + i * 100).append("\" x2=\"").append(x + 600).append("\" y2=\"").append(y + 100 + i * 100).append("\" stroke=\"black\"/>\n");
        }
        for (int i = 0; i < 5; i++) {
            svg.append("<line x1=\"").append(x + 200 + i * 100).append("\" y1=\"").append(y).append("\" x2=\"").append(x + 200 + i * 100).append("\" y2=\"").append(y + 700).append("\" stroke=\"black\"/>\n");
        }
        svg.append("<circle cx=\"").append(x + 250).append("\" cy=\"").append(y + 50).append("\" r=\"43\" stroke=\"black\" fill=\"none\"/>\n");
        svg.append("<text x=\"").append(x + 228).append("\" y=\"").append(y + 80).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">?</text>\n");

        // 300 - 400
        svg.append("<line x1=\"").append(x + 305).append("\" y1=\"").append(y + 50).append("\" x2=\"").append(x + 395).append("\" y2=\"").append(y + 50).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 335).append("\" y1=\"").append(y + 20).append("\" x2=\"").append(x + 395).append("\" y2=\"").append(y + 20).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 305).append("\" y1=\"").append(y + 80).append("\" x2=\"").append(x + 365).append("\" y2=\"").append(y + 80).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 305).append("\" y1=\"").append(y + 50).append("\" x2=\"").append(x + 305).append("\" y2=\"").append(y + 50).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 335).append("\" y1=\"").append(y + 20).append("\" x2=\"").append(x + 335).append("\" y2=\"").append(y + 80).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 365).append("\" y1=\"").append(y + 20).append("\" x2=\"").append(x + 365).append("\" y2=\"").append(y + 80).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 305).append("\" y1=\"").append(y + 50).append("\" x2=\"").append(x + 305).append("\" y2=\"").append(y + 80).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 395).append("\" y1=\"").append(y + 20).append("\" x2=\"").append(x + 395).append("\" y2=\"").append(y + 50).append("\" stroke=\"black\"/>\n");

        svg.append("<text x=\"").append(x + 405).append("\" y=\"").append(y + 80).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">+2</text>\n");
        svg.append("<text x=\"").append(x + 528).append("\" y=\"").append(y + 80).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">E</text>\n");

        for (int i = 0; i < 6; i++) {
            svg.append("<circle cx=\"").append(x + 50).append("\" cy=\"").append(y + 150 + i * 100).append("\" r=\"43\" stroke=\"black\" fill=\"none\"/>\n");
            svg.append("<circle cx=\"").append(x + 150).append("\" cy=\"").append(y + 150 + i * 100).append("\" r=\"43\" stroke=\"black\" fill=\"none\"/>\n");
            svg.append("<line x1=\"").append(x + 93).append("\" y1=\"").append(y + 150 + i * 100).append("\" x2=\"").append(x + 107).append("\" y2=\"").append(y + 150 + i * 100).append("\" stroke=\"black\"/>\n");
            svg.append("<text x=\"").append(x + 28).append("\" y=\"").append(y + 180 + i * 100).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">").append(scores.get(i + 1).get(0)).append("</text>\n");
            svg.append("<text x=\"").append(x + 128).append("\" y=\"").append(y + 180 + i * 100).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">").append(scores.get(i + 1).get(1)).append("</text>\n");
            svg.append("<text x=\"").append(x + 228).append("\" y=\"").append(y + 180 + i * 100).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"#CCCCCC\">").append(scores.get(i + 1).get(2)).append("</text>\n");
        }

        for (int i = 0; i < 2; i++) {
            svg.append("<line x1=\"").append(x).append("\" y1=\"").append(y + 750 + i * 100).append("\" x2=\"").append(x + 600).append("\" y2=\"").append(y + 750 + i * 100).append("\" stroke=\"black\"/>\n");
        }
        for (int i = 0; i < 5; i++) {
            svg.append("<line x1=\"").append(x + 200 + i * 100).append("\" y1=\"").append(y + 750).append("\" x2=\"").append(x + 200 + i * 100).append("\" y2=\"").append(y + 850).append("\" stroke=\"black\"/>\n");
        }
        svg.append("<circle cx=\"").append(x + 50).append("\" cy=\"").append(y + 800).append("\" r=\"43\" stroke=\"black\" fill=\"none\"/>\n");
        svg.append("<circle cx=\"").append(x + 150).append("\" cy=\"").append(y + 800).append("\" r=\"43\" stroke=\"black\" fill=\"none\"/>\n");
        svg.append("<line x1=\"").append(x + 93).append("\" y1=\"").append(y + 800).append("\" x2=\"").append(x + 107).append("\" y2=\"").append(y + 800).append("\" stroke=\"black\"/>\n");
        svg.append("<text x=\"").append(x + 28).append("\" y=\"").append(y + 830).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">?</text>\n");
        svg.append("<text x=\"").append(x + 116).append("\" y=\"").append(y + 830).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">M</text>\n");
        svg.append("<text x=\"").append(x + 228).append("\" y=\"").append(y + 830).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"#CCCCCC\">2</text>\n");
        svg.append("<text x=\"").append(x + 328).append("\" y=\"").append(y + 830).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"#CCCCCC\">6</text>\n");
        svg.append("<text x=\"").append(x + 402).append("\" y=\"").append(y + 830).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"#CCCCCC\">12</text>\n");
        svg.append("<text x=\"").append(x + 507).append("\" y=\"").append(y + 830).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"#CCCCCC\">20</text>\n");

        svg.append("<line x1=\"").append(x + 400).append("\" y1=\"").append(y + 900).append("\" x2=\"").append(x + 600).append("\" y2=\"").append(y + 900).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 400).append("\" y1=\"").append(y + 1000).append("\" x2=\"").append(x + 600).append("\" y2=\"").append(y + 1000).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 400).append("\" y1=\"").append(y + 900).append("\" x2=\"").append(x + 400).append("\" y2=\"").append(y + 1000).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 500).append("\" y1=\"").append(y + 900).append("\" x2=\"").append(x + 500).append("\" y2=\"").append(y + 1000).append("\" stroke=\"black\"/>\n");
        svg.append("<line x1=\"").append(x + 600).append("\" y1=\"").append(y + 900).append("\" x2=\"").append(x + 600).append("\" y2=\"").append(y + 1000).append("\" stroke=\"black\"/>\n");
        svg.append("<text x=\"").append(x + 428).append("\" y=\"").append(y + 980).append("\" font-family=\"Arial\" font-size=\"80\" stroke=\"none\" fill=\"black\">E</text>\n");

        return svg.toString();
    }
}
