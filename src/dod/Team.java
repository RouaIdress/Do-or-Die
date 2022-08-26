/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.util.List;

/**
 *
 * @author Bcc
 */
public abstract class Team {

    private List<Player> players;
    private UnitPosition[] teamUnitsInitializationPositions;

    abstract void addPlayer(Player player);
}
