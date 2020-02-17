package com.snakehunter.view;

import com.snakehunter.model.DataChangedListener;
import com.snakehunter.GameStage;
import com.snakehunter.model.piece.Player;

/**
 * @author WeiYi Yu
 * @date 2019-10-17
 */
public interface GameView
        extends DataChangedListener {
    void rollTheDice();

    void showSnakeBuilder();

    void showLadderBuilder();

    void showHumanBuilder();

    void showErrorDialog(String message);

    void showInfoDialog(String message);

    void hideDicePanel();

    void showDicePanel();

    void showTurnPanel();

    void showGuardPlacer();

    void hideTurnPanel();

    void updateTurnNo(int turnNo);

    void updateStage(GameStage s);

    void updateGuardNo();

    void updateParalyzedTurn();

    void hideSettingPanel();

    void showSettingPanel();

    void hideGameOverPanel();

    void showGameOverPanel(Player p);

    TurnPanel getTurnPanel();

    void setOnViewEventListener(ViewEventListener listener);

}
