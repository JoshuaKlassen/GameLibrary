package jgame.entities;

public abstract class Mob extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4577615793133065570L;

	private int maxHealth;
	private int health;
	
	@Override
	public boolean isAlive() { return health > 0; }

	public int getMaxHeatlh() { return maxHealth; }
	
	public void setMaxHealth(int health) { maxHealth = health; }
	
	public int getHealth() { return health; }
	
	public void damage(int damage) {
		int result = health - damage;
		if(result < 0){
			result = 0;
		}
		health = result;
	}
	
	public void healh(int heal){
		int result = health + heal;
		if(result > maxHealth){
			result = maxHealth;
		}
		health = heal;
	}
}