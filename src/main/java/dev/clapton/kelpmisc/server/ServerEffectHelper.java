package dev.clapton.kelpmisc.server;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

public class ServerEffectHelper {
    /**
     * plays smoke particles and a sound at player's location for all players.
     * levelIn has a sweet spot of 16-19 where fire particles are played.
     * @param world Server world
     * @param player Server player
     * @param levelIn Int representing the amount of smoke to generate. Expects a scale of 0-20 (or higher if you dare)
     */
    public static void usePipe(ServerWorld world, ServerPlayerEntity player, int levelIn) {
        playPipeEffect(world, player, levelIn);
        playPipeUseSound(world, player);

        /* Disabled for now.
        EffectInstance effect = player.getActivePotionEffect(Effects.RESISTANCE);
        if (effect == null) {
           effect = new EffectInstance(Effects.RESISTANCE, 0, 1);
        }

        effect.combine();
        */
        if (levelIn >= 5) {
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, (levelIn / 5) * 4 * 20, 0));
        }
    }

    public static void playPipeEffect(ServerWorld world, ServerPlayerEntity player, int levelIn) {
        double posX = player.getPosXRandom(0.2D);
        double posY = player.getPosYEye() - 0.02D;
        double posZ = player.getPosZRandom(0.2D);

        if (levelIn >= 16 && levelIn < 20) {
            world.spawnParticle(
                    ParticleTypes.FLAME,
                    posX,
                    posY,
                    posZ,
                    150,
                    0.5,
                    0.5,
                    0.5,
                    0.01
            );
        }

        if (levelIn >= 10) {
            world.spawnParticle(
                    ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                    posX,
                    posY,
                    posZ,
                    40 * levelIn,
                    0,
                    0,
                    0,
                    0.05
            );
        }

        world.spawnParticle(
                ParticleTypes.CAMPFIRE_COSY_SMOKE,
                posX,
                posY,
                posZ,
                20 * levelIn,
                0.3,
                0.3,
                0.3,
                0.02
        );
    }

    public static void playPipeUseSound(ServerWorld world, ServerPlayerEntity player) {
        world.playSound(
                null,
                player.getPosX(),
                player.getPosY(),
                player.getPosZ(),
                SoundEvents.ENTITY_BLAZE_SHOOT,
                SoundCategory.PLAYERS,
                0.5f,
                1.3f
        );
    }

    public static void playPipeRestockSound(ServerWorld world, ServerPlayerEntity player) {
        world.playSound(
                null,
                player.getPosX(),
                player.getPosY(),
                player.getPosZ(),
                SoundEvents.ENTITY_BAT_TAKEOFF,
                SoundCategory.PLAYERS,
                0.4f,
                1.2f
        );
    }
}
