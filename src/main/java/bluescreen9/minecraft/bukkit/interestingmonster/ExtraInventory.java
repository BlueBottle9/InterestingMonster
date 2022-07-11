package bluescreen9.minecraft.bukkit.interestingmonster;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.minecraft.nbt.MojangsonParser;

public class ExtraInventory {
					private static String data;
					public static HashMap<UUID, Inventory> Data = new HashMap<UUID, Inventory>();
					
					public static void saveData() {
						try {
								File dataFolder = Main.MonsterEnhance.getDataFolder();
								if (!dataFolder.exists()) {
									dataFolder.mkdirs();
								}
								if (dataFolder.isDirectory()) {
									dataFolder.delete();
									dataFolder.mkdirs();
								}
								File dataFile = new File(dataFolder, "monster_inventory.json");
								if (dataFile.exists()) {
									dataFile.delete();
								}
								dataFile.createNewFile();
								
								BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dataFile));
								out.write(data.getBytes(Charset.forName("utf-8")));
								out.flush();
								out.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					public static void updateData() {
						JSONObject jsonObject = new JSONObject();
						for (UUID uuid:Data.keySet()) {
							JSONArray jsonArray = new JSONArray();
							for (ItemStack item:Data.get(uuid)) {
								if (item == null) {
									continue;
								}
								JSONObject jsonItem = new JSONObject();
								jsonItem.put("type", item.getType().name());
								jsonItem.put("nbt",ItemUtil.getNBTData(item).toString());
								jsonArray.add(jsonItem);
							}
							jsonObject.put(uuid.toString(), jsonArray);
						}
						data = jsonObject.toJSONString();
					}
					
					public static void loadData() {
							try {
								File sourceFile = new File(Main.MonsterEnhance.getDataFolder(), "monster_inventory.json");
								if (!sourceFile.exists()) {
									return;
								}
								BufferedInputStream in = new BufferedInputStream(new FileInputStream(sourceFile));
								byte[] originalData = in.readAllBytes();
								in.close();
								JSONObject jsonObject = JSONObject.parseObject(new String(originalData, Charset.forName("utf-8")));
								if (jsonObject != null) {
									for (String str:jsonObject.keySet()) {
											Inventory inventory = Bukkit.createInventory(null, 36);
											if (jsonObject.getJSONArray(str) != null) {
													ArrayList<JSONObject> items = (ArrayList<JSONObject>) jsonObject.getJSONArray(str).toJavaList(JSONObject.class);
													for (JSONObject config:items) {
														ItemStack item = new ItemStack(Material.valueOf(config.getString("type")));
														ItemStack nmsedItem = ItemUtil.setNBTData(item, MojangsonParser.a(config.getString("nbt")));
														inventory.addItem(nmsedItem);
													}
											}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
					}
}
