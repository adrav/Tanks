//: Tanks/Weapon.java

package tanks;

/**
 * Enum variable describing weapon chosen by player.
 * @author Michal Czop
 */

public enum Weapon {
	MISSLE (99), 
	BOMB (2), 
	BOUNCER (1) {
		@Override
		public Weapon next() { return MISSLE; }
	};
	
	private int amount;
	
	/** 
	 * Constructor.
	 * @param amount - initial amount of chosen weapon
	 */

	Weapon (int amount) {
		this.amount = amount;
	}

	/** 
	 * Changes weapon. After last one, starts from beginning.
	 */

	public Weapon next() {
		return values()[ordinal() +1];
	} 
	
	/** Getters and setters. */
	public void setAmount (int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
}
