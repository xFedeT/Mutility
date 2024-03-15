package it.fedet.mutility.common.server.scoreboard.settings;


import it.fedet.mutility.common.server.scoreboard.provider.BoardProvider;

public class BoardSettings {
    private final BoardProvider boardProvider;
    private final ScoreDirection scoreDirection;

    BoardSettings(BoardProvider boardProvider, ScoreDirection scoreDirection) {
        this.boardProvider = boardProvider;
        this.scoreDirection = scoreDirection;
    }

    public static BoardSettingsBuilder builder() {
        return new BoardSettingsBuilder();
    }

    public BoardProvider getBoardProvider() {
        return this.boardProvider;
    }
    public ScoreDirection getScoreDirection() {
        return this.scoreDirection;
    }

    public static class BoardSettingsBuilder {
        private BoardProvider boardProvider;
        private ScoreDirection scoreDirection;

        BoardSettingsBuilder() {
        }

        public BoardSettingsBuilder boardProvider(BoardProvider boardProvider) {
            this.boardProvider = boardProvider;
            return this;
        }

        public BoardSettingsBuilder scoreDirection(ScoreDirection scoreDirection) {
            this.scoreDirection = scoreDirection;
            return this;
        }

        public BoardSettings build() {
            return new BoardSettings(this.boardProvider, this.scoreDirection);
        }
    }
}
