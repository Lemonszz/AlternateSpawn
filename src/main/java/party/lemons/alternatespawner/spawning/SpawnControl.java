package party.lemons.alternatespawner.spawning;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import party.lemons.alternatespawner.AlternateSpawn;

/**
 * Created by Sam on 12/08/2018.
 */
@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID)
public class SpawnControl
{
	@SubscribeEvent
	public static void checkSpawn(LivingSpawnEvent.CheckSpawn event)
	{
		if(!event.getWorld().isRemote && event.getEntity() instanceof EntityMob)	//If the entity is an enemy
		{
			EntityMob e = (EntityMob) event.getEntity();

			if(canMobSpawn(event.getWorld(), e) && !isInFlagArea(event.getWorld(), e))	//Can the mob spawn here?
				event.setResult(Event.Result.ALLOW);
			else
				event.setResult(Event.Result.DENY);
		}
	}

	/***
	 * Checks if a given entity is within any flag area
	 * @param world
	 * @param e
	 * @return if mob is in flag area
	 */
	public static boolean isInFlagArea(World world, EntityMob e)
	{
		return WorldBaseData.get(world).isInArea(e.getPosition());
	}

	/***
	 * Vanilla checks minus light check
	 * @param world
	 * @param e
	 * @return can mob spawn here
	 */
	public static boolean canMobSpawn(World world, EntityMob e)
	{
		IBlockState iblockstate = world.getBlockState((new BlockPos(e)).down());

		boolean pathWeight = e.getBlockPathWeight(new BlockPos(e.posX, e.getEntityBoundingBox().minY, e.posZ)) >= 0.0F;
		boolean canSpawnBlock = iblockstate.canEntitySpawn(e);
		boolean difficulty = world.getDifficulty() != EnumDifficulty.PEACEFUL;
		boolean sunLight = world.getLightFor(EnumSkyBlock.SKY, e.getPosition()) - world.getSkylightSubtracted() <= 8;
		boolean colliding = e.isNotColliding();

		return pathWeight && canSpawnBlock && difficulty && sunLight && colliding;
	}
}
