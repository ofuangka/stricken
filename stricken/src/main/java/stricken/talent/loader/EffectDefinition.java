package stricken.talent.loader;

public class EffectDefinition {
	private int min;
	private int max;
	private String drivingStat;
	private String affectedStat;
	private boolean positive;

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getDrivingStat() {
		return drivingStat;
	}

	public void setDrivingStat(String drivingStat) {
		this.drivingStat = drivingStat;
	}

	public String getAffectedStat() {
		return affectedStat;
	}

	public void setAffectedStat(String affectedStat) {
		this.affectedStat = affectedStat;
	}

	public boolean isPositive() {
		return positive;
	}

	public void setPositive(boolean positive) {
		this.positive = positive;
	}
}
