package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin{
	protected static Plugin MonsterEnhance;
	private static BukkitRunnable updateThread;
	
			@Override
			public void onEnable() {
					MonsterEnhance = Main.getPlugin(Main.class);
					Configuration.loadConfig();
					getServer().getPluginManager().registerEvents(new EntitySpawnListener(), MonsterEnhance);
					getServer().getPluginManager().registerEvents(new EntityDamageListener(), MonsterEnhance);
					getServer().getPluginManager().registerEvents(new EntityDeathListener(), MonsterEnhance);
					getServer().getPluginManager().registerEvents(new ExplosionListener(), MonsterEnhance);
					getServer().getPluginManager().registerEvents(new TargetingListener(), MonsterEnhance);
					getServer().getPluginManager().registerEvents(new SplitListener(), MonsterEnhance);
					getServer().getPluginManager().registerEvents(new SunDamageListener(), MonsterEnhance);
					getServer().getPluginManager().registerEvents(new EnderPearlListener(), MonsterEnhance);
					
					ExtraInventory.loadData();
					
					updateThread = new BukkitRunnable() {
						public void run() {
							ExtraInventory.updateData();
						}
					};
					updateThread.runTaskTimer(MonsterEnhance, 0, 60L);
			}
			
			@Override
			public void onDisable() {
					updateThread.cancel();
					updateThread = null;
					ExtraInventory.saveData();
			}
}
