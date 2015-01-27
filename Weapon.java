package tanks;

public enum Weapon {
	MISSLE (99), 
	BOMB (2), 
	BOUNCER (1) {
		@Override
		public Weapon next() { return MISSLE; }
	};
	
	private int amount;
	
	Weapon (int amount) {
		this.amount = amount;
	}

	public Weapon next() {
		return values()[ordinal() +1];
	} 
	
	public void setAmount (int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}
}
