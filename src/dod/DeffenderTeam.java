/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bcc
 */
public class DeffenderTeam extends Team {

    private List<Player> players;
    private List<UnitPosition> teamUnitsInitializationPositions;
    
    DeffenderTeam()
    {
        players=new ArrayList<>();
    }
    
    @Override
    void addPlayer(Player player) {
        players.add(player);
    }
    
}
