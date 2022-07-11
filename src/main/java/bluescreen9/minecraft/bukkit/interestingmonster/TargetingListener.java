package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class TargetingListener implements Listener{
					@EventHandler
					public void onPlayerChangeGameMode(PlayerGameModeChangeEvent event) {
								if (event.getNewGameMode().equals(GameMode.CREATIVE) || event.getNewGameMode().equals(GameMode.SPECTATOR)) {
												Player p = event.getPlayer();
												for (World world:Bukkit.getServer().getWorlds()) {
													for (Entity entity:world.getEntities()) {
															if (entity instanceof Monster) {
																	Monster monster = (Monster)entity;
																	if (monster.getTarget() instanceof Player) {
																		Player player = (Player) monster.getTarget();
																		if (player.getUniqueId().equals(p.getUniqueId())) {
																			monster.setTarget(null);
																		}
																	}
															}
															
															if (entity instanceof Wither) {
																Wither wither = (Wither)entity;
																if (wither.getTarget() instanceof Player) {
																	Player player = (Player) wither.getTarget();
																	if (player.getUniqueId().equals(p.getUniqueId())) {
																		wither.setTarget(null);
																	}
																}
														}
													}
												}
								}
					}
}
