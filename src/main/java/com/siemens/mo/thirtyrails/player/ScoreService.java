package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.map.Map;
import com.siemens.mo.thirtyrails.position.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ScoreService {

    private final java.util.Map<String, Integer> scoresByLine = new HashMap<>();
    private final List<Integer> mineScores = List.of(0, 2, 6, 12, 20);

    public ScoreService() {
        scoresByLine.put("1-2", 1);
        scoresByLine.put("1-3", 2);
        scoresByLine.put("1-4", 3);
        scoresByLine.put("2-3", 3);
        scoresByLine.put("2-4", 4);
        scoresByLine.put("3-4", 5);
    }

    public Score calculateScore(java.util.Map<Integer, List<String>> walkResults, Position bonusPosition) {

        String bonusIndex = bonusPosition == null ? "" : bonusPosition.index();
        int mineCount = 0;
        Set<String> processedLine = new HashSet<>();

        java.util.Map<String, ScoreLine> scoreLines = new HashMap<>();
        for (int i = 1; i <= 4; i++) {
            if (walkResults.containsKey(i)) {
                List<String> walkResult = walkResults.get(i);
                for (String resultLine : walkResult) {

                    if (!processedLine.contains(resultLine) && !processedLine.contains(reverseLine(resultLine))) {
                        processedLine.add(resultLine);
                        String[] lineParts = resultLine.split("-");
                        String scoreIndex = lineParts[0] + "-" + lineParts[lineParts.length - 1];
                        if (resultLine.endsWith("M")) {
                            mineCount++;
                        } else if (scoresByLine.containsKey(scoreIndex)) {
                            scoreLines.put(scoreIndex, new ScoreLine(scoresByLine.get(scoreIndex), lineParts.length - 2, resultLine.contains(bonusIndex) ? 2 : 0));
                        } else {
                            log.info("Invalid score index {}", scoreIndex);
                        }
                    }
                }
            }
        }

        return new Score(scoreLines, mineScores.get(mineCount));
    }

    private String reverseLine(String line) {
        String[] parts = line.split("-");
        StringBuilder result = new StringBuilder();
        for (int partIndex = parts.length - 1; partIndex >= 0; partIndex--) {
            result.append(parts[partIndex]);
            if (partIndex > 0) {
                result.append("-");
            }
        }
        return result.toString();
    }

}
