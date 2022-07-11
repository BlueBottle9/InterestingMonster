package bluescreen9.minecraft.bukkit.interestingmonster.ai;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import bluescreen9.minecraft.bukkit.interestingmonster.Configuration;

public class AILoader {
							public static void loadAI(Mob mob) {
									ArrayList<Ability> abilities = getAbilities(mob);
									if (!abilities.isEmpty()) {
										if (abilities.contains(Ability.ENDER_PEARL)) {
											Bukkit.getServer().getMobGoals().addGoal(mob, 1, new EnderPrearlAI(mob));
										}
										if (abilities.contains(Ability.FIREBALL)) {
											Bukkit.getServer().getMobGoals().addGoal(mob, 1, new FireBallAI(mob));
										}
										if (abilities.contains(Ability.TNT)) {
												Bukkit.getServer().getMobGoals().addGoal(mob, 1, new TntAI(mob));
										}
									}
									AIStates.States.put(mob.getUniqueId(), new AIStates());
							}
							
							public static ArrayList<Ability> getAbilities(Mob mob) {
								JSONObject config = Configuration.copyConfig().getJSONObject("enhance-ai").getJSONObject(Configuration.getConfigName(mob.getType()));
								JSONArray jsonArray = config.getJSONArray("extra-abilities");
								ArrayList<Ability> abilities = new ArrayList<Ability>();
								if (jsonArray != null) {
									ArrayList<String> data = (ArrayList<String>) jsonArray.toJavaList(String.class);
									for (String str:data) {
											abilities.add(Ability.valueOf(str.toUpperCase()));
									}
									return abilities;
								}
								return new ArrayList<Ability>();
							}
}
