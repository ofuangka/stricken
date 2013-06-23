package stricken.talent.loader;

public class TargetedActionDefinition {

	private String name;
	private TileCollectorDefinition targetingRange;
	private String actualRangeFilterType;
	private TileCollectorDefinition areaOfEffect;
	private EffectDefinition effect;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TileCollectorDefinition getTargetingRange() {
		return targetingRange;
	}

	public void setTargetingRange(TileCollectorDefinition targetedRange) {
		this.targetingRange = targetedRange;
	}

	public String getActualRangeFilterType() {
		return actualRangeFilterType;
	}

	public void setActualRangeFilterType(String actualRangeFilterType) {
		this.actualRangeFilterType = actualRangeFilterType;
	}

	public TileCollectorDefinition getAreaOfEffect() {
		return areaOfEffect;
	}

	public void setAreaOfEffect(TileCollectorDefinition areaOfEffect) {
		this.areaOfEffect = areaOfEffect;
	}

	public EffectDefinition getEffect() {
		return effect;
	}

	public void setEffect(EffectDefinition effect) {
		this.effect = effect;
	}
}
