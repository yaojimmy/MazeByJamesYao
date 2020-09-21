package generation;
// class for Order for testing purposes

public class StubOrder implements Order {
	
	int skillLevel;
	Builder builder;
	int seed;
	boolean perfect;
	Maze maze;
	int progress;
	
	public StubOrder() {
		
	}

	@Override
	public int getSkillLevel() {
		// TODO Auto-generated method stub
		return skillLevel;
	}
	
	public void setSkillLevel(int num) {
		skillLevel = num;
	}

	@Override
	public Builder getBuilder() {
		// TODO Auto-generated method stub
		return builder;
	}
	
	public void setBuilder(Builder b) {
		builder = b;
	}

	@Override
	public boolean isPerfect() {
		// TODO Auto-generated method stub
		return perfect;
	}
	
	public void setPerfect(boolean bool) {
		perfect = bool;
	}

	@Override
	public int getSeed() {
		// TODO Auto-generated method stub
		return seed;
	}
	
	public void setSeed(int num) {
		seed = num;
	}

	@Override
	public void deliver(Maze mazeConfig) {
		// TODO Auto-generated method stub
		maze = mazeConfig;
	}
	
	public Maze getMaze() {
		return maze;
	}

	@Override
	public void updateProgress(int percentage) {
		// TODO Auto-generated method stub
		progress = percentage;
	}
	
	public int getProgress() {
		return progress;
	}
}