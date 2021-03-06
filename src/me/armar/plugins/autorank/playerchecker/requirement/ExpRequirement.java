package me.armar.plugins.autorank.playerchecker.requirement;

import java.util.ArrayList;
import java.util.List;

import me.armar.plugins.autorank.AutorankTools;
import me.armar.plugins.autorank.language.Lang;
import me.armar.plugins.autorank.playerchecker.result.Result;

import org.bukkit.entity.Player;

public class ExpRequirement extends Requirement {

	private int minExp = 999999999;
	private boolean optional = false;
	private boolean autoComplete = false;
	private int reqId;
	List<Result> results = new ArrayList<Result>();

	@Override
	public boolean setOptions(String[] options, boolean optional,
			List<Result> results, boolean autoComplete, int reqId) {
		this.optional = optional;
		this.results = results;
		this.autoComplete = autoComplete;
		this.reqId = reqId;

		try {
			minExp = AutorankTools.stringtoInt(options[0]);
		} catch (Exception e) {
		}

		return minExp == 999999999;
	}

	@Override
	public boolean meetsRequirement(Player player) {
		if (isCompleted(getReqId(), player.getName())) {
			return true;
		}

		return player.getLevel() >= minExp;
	}

	@Override
	public String getDescription() {
		return Lang.EXP_REQUIREMENT.getConfigValue(new String[] {minExp + ""});
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
		progress = progress.concat(player.getLevel() + "/" + minExp);
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
