package bluescreen9.minecraft.bukkit.interestingmonster.ai;

import java.util.HashMap;
import java.util.UUID;

public class AIStates {
				protected static HashMap<UUID, AIStates> States = new HashMap<UUID,AIStates>();
				
				private boolean canThrowTnt = true;
				private boolean canPearl = true;
				private boolean canPotion = true;
				private boolean canFireBall = true;
				private boolean canMelee = true;
				private boolean canBow = true;
				
				public void setCanThrowTnt(boolean can) {
						canThrowTnt = can;
				}
				
				public void setCanPearl(boolean can) {
					canPearl = can;
				}
				
				public void setCanPotion(boolean can) {
					canPotion = can;
				}
				
				public void setCanFireBall(boolean can) {
					canFireBall = can;
				}
				
				public void setCanBow(boolean can) {
					canBow = can;
				}
				
				public void setMelee(boolean can) {
					canMelee = can;
				}
				
			public boolean getCanThrowTnt() {
					return canThrowTnt;
			}
			
			public boolean getCanPearl() {
				return canPearl;
			}
			
			public boolean getCanPotion() {
				return canPotion;
			}
			
			public boolean getCanFireBall() {
				return canFireBall;
			}
			
			public boolean getCanBow() {
				return canBow;
			}
			
			public boolean getCanMelee() {
				return canMelee;
			}
				
}
