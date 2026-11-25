// SPDX-License-Identifier: MIT
package dev.soulcatcherplus.addon.compat;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/** Ice breath stores its caster in different public members across Mowzie releases. */
public final class MowzieSpellOwner {
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();
    private static final MethodType CASTER_SIGNATURE = MethodType.methodType(LivingEntity.class, Entity.class);
    private static final ClassValue<MethodHandle> CASTER = new ClassValue<MethodHandle>() {
        @Override protected MethodHandle computeValue(Class<?> type) {
            try {
                return LOOKUP.unreflect(type.getMethod("getCaster")).asType(CASTER_SIGNATURE);
            } catch (NoSuchMethodException ignored) {
                try {
                    return LOOKUP.unreflectGetter(type.getField("caster")).asType(CASTER_SIGNATURE);
                } catch (ReflectiveOperationException missingLegacyField) {
                    return null;
                }
            } catch (IllegalAccessException inaccessibleMethod) {
                return null;
            }
        }
    };

    public static boolean protectsTerrain(Entity spell) {
        if (CompanionTerrain.protectsTerrain(spell)) return true;
        MethodHandle getter = CASTER.get(spell.getClass());
        if (getter == null) return false;
        try {
            LivingEntity caster = (LivingEntity)getter.invokeExact(spell);
            if (!CompanionTerrain.isProtectedSoul(caster)) return false;
            CompanionTerrain.protectAttack(spell, caster);
            return true;
        } catch (RuntimeException | Error exception) {
            throw exception;
        } catch (Throwable exception) {
            throw new IllegalStateException("Failed to resolve Mowzie spell caster", exception);
        }
    }

    private MowzieSpellOwner() {}
}
