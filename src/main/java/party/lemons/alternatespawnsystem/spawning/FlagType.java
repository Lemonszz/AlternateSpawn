package party.lemons.alternatespawnsystem.spawning;

import net.minecraft.block.Block;
import party.lemons.alternatespawnsystem.block.AlternateSpawnBlocks;
import party.lemons.alternatespawnsystem.config.AlternateSpawnConfig;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Created by Sam on 12/08/2018.
 */
public class FlagType
{
	public static final FlagType BASIC = new FlagType("basic", ()->AlternateSpawnBlocks.BASIC_FLAG, ()->AlternateSpawnBlocks.BASIC_FLAG_WALL, AlternateSpawnConfig.BASIC_FLAG_RANGE);
	public static final FlagType GOLD = new FlagType("gold", ()->AlternateSpawnBlocks.GOLDEN_FLAG, ()->AlternateSpawnBlocks.GOLDEN_FLAG_WALL, AlternateSpawnConfig.GOLDEN_FLAG_RANGE);
	public static final FlagType DIAMOND = new FlagType("diamond", ()->AlternateSpawnBlocks.DIAMOND_FLAG, ()->AlternateSpawnBlocks.DIAMOND_FLAG_WALL, AlternateSpawnConfig.DIAMOND_FLAG_RANGE);

	private final String name;
	private int size;
	private final Supplier<Block> wallBlockSupplier, standingBlockSupplier;

	public FlagType(String name, Supplier<Block> standingBlockSupplier, Supplier<Block> wallBlockSupplier, int size)
	{
		this.name = name;
		this.wallBlockSupplier = wallBlockSupplier;
		this.standingBlockSupplier = standingBlockSupplier;
		this.size = size;
	}

	public String getName()
	{
		return name;
	}

	public int getFlagSize()
	{
		return size;
	}

	public @Nonnull Block getWallBlock()
	{
		return wallBlockSupplier.get();
	}

	public @Nonnull Block getStandingBlock()
	{
		return standingBlockSupplier.get();
	}

	public void setFlagSize(int size)
	{
		this.size = size;
	}
}
