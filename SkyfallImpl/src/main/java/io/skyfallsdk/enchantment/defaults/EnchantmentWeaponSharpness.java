package io.skyfallsdk.enchantment.defaults;

import io.skyfallsdk.enchantment.DefaultEnchantment;
import io.skyfallsdk.entity.EntityLiving;
import io.skyfallsdk.event.entity.EntityDamageEntityEvent;
import io.skyfallsdk.item.Item;
import io.skyfallsdk.subscription.annotation.BlockingSubscriber;
import io.skyfallsdk.subscription.Subscription;
import io.skyfallsdk.subscription.SubscriptionPriority;

public class EnchantmentWeaponSharpness {

    @BlockingSubscriber(priority = SubscriptionPriority.FIRST)
    public void onEntityAttack(Subscription<EntityDamageEntityEvent> subscription) {
        EntityLiving damager = subscription.getSubscribable().getDamager();

        Item item = damager.getInventory().getItem(0);
        if (item == null) {
            return;
        }

        if (!item.hasEnchantment(DefaultEnchantment.SHARPNESS)) {
            return;
        }

        int level = item.getEnchantmentLevel(DefaultEnchantment.SHARPNESS);
        //
    }
}
