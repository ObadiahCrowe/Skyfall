package io.skyfallsdk.enchantment.defaults;

import io.skyfallsdk.entity.EntityLiving;
import io.skyfallsdk.event.entity.EntityDamageEntityEvent;
import io.skyfallsdk.subscription.Subscription;
import io.skyfallsdk.subscription.SubscriptionInfo;
import io.skyfallsdk.subscription.SubscriptionPriority;

public class EnchantmentWeaponSharpness {

    @SubscriptionInfo(priority = SubscriptionPriority.FIRST)
    public void onEntityAttack(Subscription<EntityDamageEntityEvent> subscription) {
        EntityLiving damager = subscription.getSubscribable().getDamager();

    }
}
