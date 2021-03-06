package mage.cards.f;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public class FallIntoOblivion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public FallIntoOblivion(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[] { CardType.INSTANT }, "{1}{B}");

        //destroy target non legendary creature
        spellAbility.addEffect(new DestroyTargetEffect());
        spellAbility.addTarget(new TargetCreaturePermanent(filter));
    }

    public FallIntoOblivion(final FallIntoOblivion fallIntoOblivion){
        super(fallIntoOblivion);
    }

    public FallIntoOblivion copy(){
        return new FallIntoOblivion(this);
    }
}
