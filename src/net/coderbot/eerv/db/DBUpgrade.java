package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBUpgrade 
{
	public static UpgradeEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		UpgradeEntry[] sr = new UpgradeEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new UpgradeEntry(data);
		}
		
		return sr;
	}
	
	/**
	 * Multipliers for each upgrade on units.
	 * @author coderbot
	 *
	 */
	public static class UpgradeEntry
	{
		byte[] name;
		float attack;
		float health;
		float speed;
		float range;
		float armorShock;
		float armorArrow;
		float armorPierce;
		float armorGun;
		float armorLaser;
		float armorMissle;
		float power;//flight time for planes
		float unused;
		float area;
		float unused2;
		
		float attackCost;
		float healthCost;
		float speedCost;
		float rangeCost;
		float armorShockCost;
		float armorArrowCost;
		float armorPierceCost;
		float armorGunCost;
		float armorLaserCost;
		float armorMissleCost;
		float powerCost;
		float unusedCost;
		float areaCost;
		float unused2Cost;
		
		int unusedv0;
		int maxUpgrades;
		int u0;//gameid?
		int index;
		int u1;
		int epoch;
		int unusedv1;
		
		UpgradeEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			attack = data.getFloat();
			health = data.getFloat();
			speed = data.getFloat();
			range = data.getFloat();
			armorShock = data.getFloat();
			armorArrow = data.getFloat();
			armorPierce = data.getFloat();
			armorGun = data.getFloat();
			armorLaser = data.getFloat();
			armorMissle = data.getFloat();
			power = data.getFloat();
			unused = data.getFloat();
			area = data.getFloat();
			unused2 = data.getFloat();
			
			attackCost = data.getFloat();
			healthCost = data.getFloat();
			speedCost = data.getFloat();
			rangeCost = data.getFloat();
			armorShockCost = data.getFloat();
			armorArrowCost = data.getFloat();
			armorPierceCost = data.getFloat();
			armorGunCost = data.getFloat();
			armorLaserCost = data.getFloat();
			armorMissleCost = data.getFloat();
			powerCost = data.getFloat();
			unusedCost = data.getFloat();
			areaCost = data.getFloat();
			unused2Cost = data.getFloat();
			
			unusedv0 = data.getInt();
			maxUpgrades = data.getInt();
			
			u0 = data.getInt();
			
			index = data.getInt();
			
			u1 = data.getInt();
			epoch = data.getInt();
			
			unusedv1 = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "UpgradeEntry {name: "+sname+", \tattack: "+attack+", health: "+health+", speed: "+speed+", range: "+range+", armorShock: "+armorShock+", armorArrow: "+
			armorArrow+", armorPierce: "+armorPierce+", armorGun: "+armorGun+", armorLaser: "+armorLaser+", armorMissle: "+armorMissle+", power: "+power+", unused: "+unused+
			", area: "+area+", attackCost: "+attackCost+", healthCost: "+healthCost+", speedCost: "+speedCost+", rangeCost: "+rangeCost+", armorShockCost: "+armorShockCost+
			", armorArrowCost: "+armorArrowCost+", armorPierceCost: "+armorPierceCost+", armorGunCost: "+armorGunCost+", armorLaserCost: "+armorLaserCost+", armorMissleCost: "+
			armorMissleCost+", powerCost: "+powerCost+", unusedCost: "+unusedCost+", areaCost: "+areaCost+", maxUpgrades: "+maxUpgrades+", index: "+index+", epoch: "+epoch+"}";
		}
	}
}
