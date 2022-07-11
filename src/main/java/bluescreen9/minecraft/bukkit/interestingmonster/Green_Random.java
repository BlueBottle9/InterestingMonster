package bluescreen9.minecraft.bukkit.interestingmonster;

import java.util.Random;
import java.util.UUID;

public class Green_Random {
		public static long randomLong() {
			return new Random().nextLong();
		}
		
		public static long randomLong(long min, long max) {
			max++;
			if (min >= max) {
				throw new ArithmeticException("min number cant greater than or equal to max number");
			}
			return min + ((long)(new Random().nextDouble() * (max - min)));
		}
		
		public static boolean chance(double chance) {
			if (chance < 0.0D || chance > 1.0D) {
				throw new ArithmeticException("chance cant less than 0 or greater than 1");
			}
			if (chance >= Math.random()) {
				return true;
			}
			return false;
		}
		
		public static String randomUUID() {
			return UUID.randomUUID().toString();
		}
}
