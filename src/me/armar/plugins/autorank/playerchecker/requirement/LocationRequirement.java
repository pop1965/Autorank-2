package me.armar.plugins.autorank.playerchecker.requirement;

import java.util.ArrayList;
import java.util.List;

import me.armar.plugins.autorank.language.Lang;
import me.armar.plugins.autorank.playerchecker.result.Result;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class LocationRequirement extends Requirement {

	private int xLocation = 0, yLocation = 0, zLocation = 0;
	private String world;
	private int radius = 1;
	private boolean optional = false;
	private boolean autoComplete = false;
	private int reqId;
	List<Result> results = new ArrayList<Result>();

	public LocationRequirement() {
		super();
	}

	@Override
	public boolean setOptions(String[] options, boolean optional,
			List<Result> results, boolean autoComplete, int reqId) {
		
		// Location = x;y;z;world;radius
		this.optional = optional;
		this.results = results;
		this.autoComplete = autoComplete;
		this.reqId = reqId;

		if (options.length != 5) {
			return false;
		}
		
		try {
			// Save x,y,z
			xLocation = Integer.parseInt(options[0]);
			yLocation = Integer.parseInt(options[1]);
			zLocation = Integer.parseInt(options[2]);
			
			// Save world
			world = options[3].trim();
			
			// Save radius
			radius = Integer.parseInt(options[4]);
			
			if (radius < 0) {
				radius = 0;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean meetsRequirement(Player player) {
		if (isCompleted(getReqId(), player.getName())) {
			return true;
		}
		
		Location pLocation = player.getLocation();
		
		// Positive and negative values
		int xRadiusP, yRadiusP, zRadiusP, xRadiusN, yRadiusN, zRadiusN;
		
		xRadiusN = xLocation - radius;
		yRadiusN = yLocation - radius;
		zRadiusN = zLocation - radius;
		
		xRadiusP = xLocation + radius;
		yRadiusP = yLocation + radius;
		zRadiusP = zLocation + radius;
		
		World realWorld = Bukkit.getWorld(world);
		
		if (realWorld == null) return false;
		
		// Player is not in the correct world
		if (!realWorld.getName().equals(pLocation.getWorld().getName())) return false; 

		// Check if a player is within the radius
		if (pLocation.getBlockX() >= xRadiusN && pLocation.getBlockX() <= xRadiusP) {
			if (pLocation.getBlockY() >= yRadiusN && pLocation.getBlockY() <= yRadiusP) {
				if (pLocation.getBlockZ() >= zRadiusN && pLocation.getBlockZ() <= zRadiusP) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String getDescription() {
		return Lang.LOCATION_REQUIREMENT.getConfigValue(new String[] {xLocation + ", " + yLocation + ", " + zLocation + " in " + world});
	}

	@Override
	public boolean isOptional() {
		return optional;
	}

	@Override
	public List<Result> getResults() {
		return results;
	}

	@Override
	public String getProgress(Player player) {
		String progress = "";
		progress = progress.concat("Cannot show progress.");
		return progress;
	}

	@Override
	public boolean useAutoCompletion() {
		return autoComplete;
	}

	@Override
	public int getReqId() {
		return reqId;
	}
}
