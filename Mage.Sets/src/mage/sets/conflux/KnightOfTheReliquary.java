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

package mage.sets.conflux;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.Filter.ComparisonScope;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;

import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class KnightOfTheReliquary extends CardImpl<KnightOfTheReliquary> {

	private static FilterControlledPermanent filter = new FilterControlledPermanent("Forest or Plains");

	static {
		filter.getCardType().add(CardType.LAND);
		filter.setScopeCardType(ComparisonScope.Any);
		filter.getSubtype().add("Forest");
		filter.getSubtype().add("Plains");
		filter.setScopeSubtype(ComparisonScope.Any);
	}

	public KnightOfTheReliquary(UUID ownerId) {
		super(ownerId, 113, "Knight of the Reliquary", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
		this.expansionSetCode = "CON";
		this.color.setWhite(true);
		this.color.setGreen(true);
		this.subtype.add("Human");
		this.subtype.add("Knight");
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);
		TargetCardInLibrary target = new TargetCardInLibrary(new FilterLandCard());
		Costs costs = new CostsImpl();
		costs.add(new TapSourceCost());
		costs.add(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KnightOfTheReliquaryEffect()));
		this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryPutInPlayEffect(target, false, Outcome.PutLandInPlay), costs));
	}

	public KnightOfTheReliquary(final KnightOfTheReliquary card) {
		super(card);
	}

	@Override
	public KnightOfTheReliquary copy() {
		return new KnightOfTheReliquary(this);
	}

	@Override
	public String getArt() {
		return "119798_typ_reg_sty_010.jpg";
	}

}

class KnightOfTheReliquaryEffect extends ContinuousEffectImpl<KnightOfTheReliquaryEffect> {

	private static FilterLandCard filter = new FilterLandCard();

	public KnightOfTheReliquaryEffect() {
		super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
	}

	public KnightOfTheReliquaryEffect(final KnightOfTheReliquaryEffect effect) {
		super(effect);
	}

	@Override
	public KnightOfTheReliquaryEffect copy() {
		return new KnightOfTheReliquaryEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int count = game.getPlayer(source.getControllerId()).getGraveyard().count(filter, game);
		if (count > 0) {
			Permanent target = (Permanent) game.getPermanent(source.getSourceId());
			if (target != null) {
				target.addPower(count);
				target.addToughness(count);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "{this} gets +1/+1 for each land card in your graveyard";
	}

}