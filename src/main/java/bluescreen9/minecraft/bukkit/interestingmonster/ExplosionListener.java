package bluescreen9.minecraft.bukkit.interestingmonster;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import com.alibaba.fastjson.JSONObject;

public class ExplosionListener implements Listener{
								@EventHandler
								public void onExplode(ExplosionPrimeEvent event) {
									Entity entity = event.getEntity();
									if (entity.getType() == EntityType.CREEPER) {
											event.setCancelled(true);
											JSONObject explosionConfig = Configuration.Configuration.getJSONObject("monsters").getJSONObject("creeper").getJSONObject("explosion");
											float power = explosionConfig.getFloatValue("power");
											boolean fire = explosionConfig.getBooleanValue("fire");
											boolean breakBlocks = explosionConfig.getBooleanValue("break-blocks");
											
											Location loc = event.getEntity().getLocation();
											World world = loc.getWorld();
											world.createExplosion(loc, power, fire, breakBlocks, event.getEntity());
											if (!explosionConfig.getBooleanValue("damage-entities")) {
												ExplosionData.NoDamageList.add(entity.getUniqueId());
											}
											entity.remove();
									}
								}
								
								@EventHandler
								public void onFireBallHit(ProjectileHitEvent event) {
									Projectile projectile = event.getEntity();
												if (projectile instanceof DragonFireball) {//������
														if (projectile.getShooter() instanceof EnderDragon) {
															event.setCancelled(true);
															JSONObject fireballConfig = Configuration.Configuration.getJSONObject("bosses").getJSONObject("ender-dragon").getJSONObject("fireball");
															double power =fireballConfig.getDoubleValue("power");
															boolean breakBlocks = fireballConfig.getBooleanValue("break-blocks");
															boolean fire = fireballConfig.getBooleanValue("fire");
															boolean damageEntity = fireballConfig.getBooleanValue("damage-entities");
															Location loc = null;
															if (event.getEntity() != null) {
																loc =  event.getEntity().getLocation();
															}else {
																loc = event.getHitBlock().getLocation();
															}
															World world = loc.getWorld();
															world.createExplosion(loc, (float) power, fire, breakBlocks, projectile);
															if (!damageEntity) {
																ExplosionData.NoDamageList.add(projectile.getUniqueId());
															}
														}
												}
												if (projectile instanceof SmallFireball) {//�����˻���
													if (projectile.getShooter() instanceof Blaze) {
														event.setCancelled(true);
														JSONObject fireballConfig = Configuration.Configuration.getJSONObject("monsters").getJSONObject("blaze").getJSONObject("fireball");
														double power =fireballConfig.getDoubleValue("power");
														boolean breakBlocks = fireballConfig.getBooleanValue("break-blocks");
														boolean fire = fireballConfig.getBooleanValue("fire");
														boolean damageEntity = fireballConfig.getBooleanValue("damage-entities");
														Location loc = null;
														if (event.getEntity() != null) {
															loc =  event.getEntity().getLocation();
														}else {
															loc = event.getHitBlock().getLocation();
														}
														World world = loc.getWorld();
														world.createExplosion(loc, (float) power, fire, breakBlocks, projectile);
														if (!damageEntity) {
															ExplosionData.NoDamageList.add(projectile.getUniqueId());
														}
													}
												}
												if (projectile instanceof LargeFireball) {//������
													if (projectile.getShooter() instanceof Ghast) {
														event.setCancelled(true);
														JSONObject fireballConfig = Configuration.Configuration.getJSONObject("monsters").getJSONObject("ghast").getJSONObject("fireball");
														double power =fireballConfig.getDoubleValue("power");
														boolean breakBlocks = fireballConfig.getBooleanValue("break-blocks");
														boolean fire = fireballConfig.getBooleanValue("fire");
														boolean damageEntity = fireballConfig.getBooleanValue("damage-entities");
														Location loc = null;
														if (event.getEntity() != null) {
															loc =  event.getEntity().getLocation();
														}else {
															loc = event.getHitBlock().getLocation();
														}
														World world = loc.getWorld();
														world.createExplosion(loc, (float) power, fire, breakBlocks, projectile);
														if (!damageEntity) {
															ExplosionData.NoDamageList.add(projectile.getUniqueId());
														}
													}else {
														if (ExplosionData.OtherLauchers.contains(projectile)) {
															ExplosionData.OtherLauchers.remove(projectile);
															event.setCancelled(true);
															JSONObject config = Configuration.Configuration.getJSONObject("gamerules").getJSONObject("ai-fireball");
															projectile.getWorld().createExplosion(projectile, projectile.getLocation(), config.getFloatValue("power"), config.getBooleanValue("fire"), config.getBooleanValue("break-blocks"));
															if (!config.getBooleanValue("damage-entities")) {
																ExplosionData.NoDamageList.add(projectile.getUniqueId());
															}
														}
													}
												}
												if (projectile instanceof WitherSkull) {//����ͷ­
													if (projectile.getShooter() instanceof Wither) {
														event.setCancelled(true);
														JSONObject skullConfig = Configuration.Configuration.getJSONObject("bosses").getJSONObject("wither").getJSONObject("skull");
														double power =skullConfig.getDoubleValue("power");
														boolean breakBlocks = skullConfig.getBooleanValue("break-blocks");
														boolean fire = skullConfig.getBooleanValue("fire");
														boolean damageEntity = skullConfig.getBooleanValue("damage-entities");
														Location loc = null;
														if (event.getEntity() != null) {
															loc =  event.getEntity().getLocation();
														}else {
															loc = event.getHitBlock().getLocation();
														}
														World world = loc.getWorld();
														world.createExplosion(loc, (float) power, fire, breakBlocks, projectile);
														if (!damageEntity) {
															ExplosionData.NoDamageList.add(projectile.getUniqueId());
														}
													}
												}
								}
}
