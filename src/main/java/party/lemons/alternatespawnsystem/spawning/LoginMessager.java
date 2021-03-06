package party.lemons.alternatespawnsystem.spawning;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.StatList;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import party.lemons.alternatespawnsystem.AlternateSpawn;
import party.lemons.alternatespawnsystem.config.AlternateSpawnConfig;

/**
 * Created by Sam on 12/08/2018.
 */

/****
 * Class messages new players to let them know about the spawning mechanics
 * Can be disabled in config
 */

@Mod.EventBusSubscriber(modid = AlternateSpawn.MODID)
public class LoginMessager
{
	@SubscribeEvent
	public static void onLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(!AlternateSpawnConfig.DO_LOGIN_MESSAGE || event.player.world.isRemote || !(event.player instanceof EntityPlayerMP))
			return;

		EntityPlayerMP playerMP = (EntityPlayerMP) event.player;
		int time = playerMP.getStatFile().readStat(StatList.PLAY_ONE_MINUTE);

		if(time < AlternateSpawnConfig.LOGIN_MESSAGE_MAX_TIME) 	//Check player play time
		{
			//Send message

			TextComponentTranslation txt = new TextComponentTranslation("alternatespawnsystem.message.login");
			Style style = new Style();
			style.setColor(TextFormatting.GRAY);
			style.setItalic(true);
			style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://minecraft.curseforge.com/projects/ass"));
			txt.setStyle(style);

			event.player.sendStatusMessage(txt, false);
		}
	}
}
