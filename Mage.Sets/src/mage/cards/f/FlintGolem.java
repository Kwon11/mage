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
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes & L_J
 */
public class FlintGolem extends CardImpl {

    public FlintGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Flint Golem becomes blocked, defending player puts the top three cards of their library into their graveyard.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new FlintGolemEffect(), false));
    }

    public FlintGolem(final FlintGolem card) {
        super(card);
    }

    @Override
    public FlintGolem copy() {
        return new FlintGolem(this);
    }
}

class FlintGolemEffect extends OneShotEffect {

    public FlintGolemEffect() {
        super(Outcome.Detriment);
        this.staticText = "defending player puts the top three cards of their library into their graveyard";
    }

    public FlintGolemEffect(final FlintGolemEffect effect) {
        super(effect);
    }

    @Override
    public FlintGolemEffect copy() {
        return new FlintGolemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (blockingCreature != null) {
            Player opponent = game.getPlayer(blockingCreature.getControllerId());
            if (opponent != null) {
                opponent.moveCards(opponent.getLibrary().getTopCards(game, 3), Zone.GRAVEYARD, source, game);
                return true;
            }
        }
        return false;
    }
}
