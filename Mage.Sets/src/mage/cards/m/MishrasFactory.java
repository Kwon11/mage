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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class MishrasFactory extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Assembly-Worker creature");

    static {
        filter.add(new SubtypePredicate(SubType.ASSEMBLY_WORKER));
    }

    public MishrasFactory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());
        // {1}: Mishra's Factory becomes a 2/2 Assembly-Worker artifact creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new AssemblyWorkerToken(), "land", Duration.EndOfTurn),
                new GenericManaCost(1)));
        // {tap}: Target Assembly-Worker creature gets +1/+1 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(1, 1, Duration.EndOfTurn),
                new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public MishrasFactory(final MishrasFactory card) {
        super(card);
    }

    @Override
    public MishrasFactory copy() {
        return new MishrasFactory(this);
    }
}

class AssemblyWorkerToken extends Token {

    public AssemblyWorkerToken() {
        super("Assembly-Worker", "2/2 Assembly-Worker artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ASSEMBLY_WORKER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
}
