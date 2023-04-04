package com.siemens.mo.thirtyrails.player;

import com.siemens.mo.thirtyrails.position.Position;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreServiceTest {

    /**
     *      ?   []  +2  E
     * 1-2
     * 1-3
     * 1-4
     * 2-3
     * 2-4
     * 3-4
     *
     * ?-M  2
     *
     * SUM: 2
     */
    @Test
    void shouldCalculateScoreWithOneConnectedMine() {
        ScoreService scoreService = new ScoreService();
        Map<Integer, List<String>> lines = new HashMap<>();
        lines.put(2, List.of("2-11-21-22-23-33-43LR-53-54-44-43UD-42-52-M"));
        Score score = scoreService.calculateScore(lines, new Position(1, 1));

        MatcherAssert.assertThat(score.sum(), CoreMatchers.is(2));
    }

    /**
     *      ?   []  +2  E
     * 1-2  1   12  2   15
     * 1-3
     * 1-4
     * 2-3
     * 2-4
     * 3-4
     *
     * ?-M
     *
     * SUM: 15
     */
    @Test
    void shouldCalculateScoreWithConnectedStation1WithBonus() {
        ScoreService scoreService = new ScoreService();
        Map<Integer, List<String>> lines = new HashMap<>();
        lines.put(2, List.of("2-11-21-22-23-33-43LR-53-54-44-43UD-42-52-1"));
        lines.put(1, List.of("1-52-42-43UD-44-54-53-43LR-33-23-22-21-11-2"));
        Score score = scoreService.calculateScore(lines, new Position(1, 1));

        MatcherAssert.assertThat(score.sum(), CoreMatchers.is(15));
    }

    /**
     *      ?   []  +2  E
     * 1-2  1   12      13
     * 1-3
     * 1-4
     * 2-3
     * 2-4
     * 3-4
     *
     * ?-M
     *
     * SUM: 13
     */
    @Test
    void shouldCalculateScoreWithConnectedStation1WithoutBonus() {
        ScoreService scoreService = new ScoreService();
        Map<Integer, List<String>> lines = new HashMap<>();
        lines.put(2, List.of("2-11-21-22-23-33-43LR-53-54-44-43UD-42-52-1"));
        lines.put(1, List.of("1-52-42-43UD-44-54-53-43LR-33-23-22-21-11-2"));
        Score score = scoreService.calculateScore(lines, new Position(1, 6));

        MatcherAssert.assertThat(score.sum(), CoreMatchers.is(13));
    }

    /**
     *      ?   []  +2  E
     * 1-2  1   11  2   14
     * 1-3  2   9   2   13
     * 1-4  3   14  2   19
     * 2-3
     * 2-4  5  10   2   17
     * 3-4  6  8    2   16
     *
     * ?-M     6        6
     *
     * SUM:  85
     */
    @Test
    void shouldCalculateScoreWithConnectedStation1WithBonusWithMine() {
        ScoreService scoreService = new ScoreService();
        Map<Integer, List<String>> lines = new HashMap<>();

        lines.put(1, List.of(
                "1-26-36UL-35-45-44-43-42-32LR-22-21-11-2",
                "1-26-36UL-35-45-44-43-42-52-51-3",
                "1-26-36UL-35-45-44-43-42-52-M",
                "1-26-36UL-35-34-33-23-22-32LR-42-43-44-45-55-65LR-4"));

        lines.put(2, List.of(
                "2-11-21-22-32LR-42-43-44-45-35-36UL-26-1",
                "2-11-21-22-32LR-42-43-44-45-55-65LR-4"));

        lines.put(3, List.of(
                "3-51-52-42-43-44-45-35-36UL-26-1",
                "3-51-52-42-43-44-45-55-65LR-4"));

        lines.put(4, List.of(
                "4-65LR-55-45-44-43-42-32LR-22-21-11-2",
                "4-65LR-55-45-44-43-42-32LR-22-23-33-34-35-36UL-26-1",
                "4-65LR-55-45-44-43-42-52-51-3",
                "4-65LR-55-45-44-43-42-52-M"));


        Score score = scoreService.calculateScore(lines, new Position(4, 3));

        MatcherAssert.assertThat(score.sum(), CoreMatchers.is(83));
    }

}
