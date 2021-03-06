/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class LaquatussChampion extends CardImpl {

    public LaquatussChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // When Laquatus's Champion enters the battlefield, target player loses 6 life.
        Ability ability = new LaquatussChampionEntersBattlefieldTriggeredAbility();
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // When Laquatus's Champion leaves the battlefield, that player gains 6 life.
        this.addAbility(new LaquatussChampionLeavesBattlefieldTriggeredAbility());
        // {B}: Regenerate Laquatus's Champion.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{B}")));
    }

    public LaquatussChampion(final LaquatussChampion card) {
        super(card);
    }

    @Override
    public LaquatussChampion copy() {
        return new LaquatussChampion(this);
    }
}

class LaquatussChampionEntersBattlefieldTriggeredAbility extends EntersBattlefieldTriggeredAbility implements AdjustingSourceCosts {

    public LaquatussChampionEntersBattlefieldTriggeredAbility() {
        super(new LoseLifeTargetEffect(6), false);
    }

    public LaquatussChampionEntersBattlefieldTriggeredAbility(LaquatussChampionEntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LaquatussChampionEntersBattlefieldTriggeredAbility copy() {
        return new LaquatussChampionEntersBattlefieldTriggeredAbility(this);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(ability.getFirstTarget());
        if (player != null) {
            String key = CardUtil.getCardZoneString("targetPlayer", this.getSourceId(), game);
            game.getState().setValue(key, player.getId());
        }
    }
}

class LaquatussChampionLeavesBattlefieldTriggeredAbility extends LeavesBattlefieldTriggeredAbility {

    public LaquatussChampionLeavesBattlefieldTriggeredAbility() {
        super(new GainLifeTargetEffect(6), false);
    }

    public LaquatussChampionLeavesBattlefieldTriggeredAbility(LaquatussChampionLeavesBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            String key = CardUtil.getCardZoneString("targetPlayer", this.getSourceId(), game, true);
            Object object = game.getState().getValue(key);
            if (object instanceof UUID) {
                this.getEffects().setTargetPointer(new FixedTarget((UUID) object));
                return true;
            }
        }
        return false;
    }

    @Override
    public LaquatussChampionLeavesBattlefieldTriggeredAbility copy() {
        return new LaquatussChampionLeavesBattlefieldTriggeredAbility(this);
    }
}
