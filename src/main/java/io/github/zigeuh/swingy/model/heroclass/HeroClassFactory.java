package io.github.zigeuh.swingy.model.heroclass;

public class HeroClassFactory {

	public static AHeroClass createClass(String name) {
		AHeroClass heroClass;

		switch (name.toUpperCase()) {
			case "KEYBLADE_MASTER" -> heroClass = new KeybladeMaster();
			case "WIZARD" -> heroClass = new Wizard();
			case "DEFENDER" -> heroClass = new Defender();
			default -> throw new RuntimeException("Tried to create an invalid class: " + name);
		}

		return heroClass;
	}

	private HeroClassFactory() {}
}
