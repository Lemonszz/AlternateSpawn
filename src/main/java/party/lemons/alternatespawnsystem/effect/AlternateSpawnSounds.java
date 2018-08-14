package party.lemons.alternatespawnsystem.effect;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import party.lemons.alternatespawnsystem.AlternateSpawn;

/**
 * Created by Sam on 14/08/2018.
 */
@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID)
@GameRegistry.ObjectHolder(AlternateSpawn.MODID)
public class AlternateSpawnSounds
{
	public static final SoundEvent FLAG_FAIL = SoundEvents.ENTITY_PIG_AMBIENT;
	public static final SoundEvent FLAG_SUCCEED = SoundEvents.ENTITY_PIG_AMBIENT;

	@SubscribeEvent
	public static void onRegisterSound(RegistryEvent.Register<SoundEvent> event)
	{
		event.getRegistry().registerAll(
				new SoundEvent(new ResourceLocation(AlternateSpawn.MODID, "flag_fail")).setRegistryName(AlternateSpawn.MODID, "flag_fail"),
				new SoundEvent(new ResourceLocation(AlternateSpawn.MODID, "flag_succeed")).setRegistryName(AlternateSpawn.MODID, "flag_succeed")
		);
	}
}
