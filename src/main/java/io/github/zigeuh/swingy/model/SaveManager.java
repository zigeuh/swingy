package io.github.zigeuh.swingy.model;

import io.github.zigeuh.swingy.model.artifact.Artifact;
import io.github.zigeuh.swingy.model.artifact.ArtifactTemplate;
import io.github.zigeuh.swingy.model.hero.Hero;
import io.github.zigeuh.swingy.model.hero.HeroClass;

import java.sql.*;

public class SaveManager {

	private static final String DB_URL = "jdbc:h2:./saves/game;AUTO_SERVER=TRUE";

	public SaveManager() {
		try {
			initDatabase();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void save(int slot, Hero hero) {
		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			String request = "MERGE INTO SAVES (slot, name, hero_class, level, xp, current_hp, weapon, armor, helm, weapon_bonus, armor_bonus, helm_bonus)"
					+ " KEY(slot) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(request);

			stmt.setInt(1, slot);
			stmt.setString(2, hero.getName());
			stmt.setString(3, hero.getHeroClass().name());
			stmt.setInt(4, hero.getLevel());
			stmt.setInt(5, hero.getXp());
			stmt.setInt(6, hero.getHp());

			Artifact weapon = hero.getWeapon();
			Artifact armor = hero.getArmor();
			Artifact helm = hero.getHelm();
			stmt.setString(7, weapon != null ? weapon.getTemplate().name() : "");
			stmt.setString(8, armor != null ? armor.getTemplate().name() : "");
			stmt.setString(9, helm != null ? helm.getTemplate().name() : "");
			stmt.setInt(10,  weapon != null ? weapon.bonus(): 0);
			stmt.setInt(11,  armor != null ? armor.bonus(): 0);
			stmt.setInt(12,  helm != null ? helm.bonus(): 0);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(int slot) {
		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			String request = "DELETE FROM SAVES WHERE slot = ?";
			PreparedStatement stmt = conn.prepareStatement(request);

			stmt.setInt(1, slot);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public SaveSlot[] load() {

		SaveSlot[] saveSlots = new SaveSlot[3];

		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			Statement stmt = conn.createStatement();

			ResultSet resultSet =  stmt.executeQuery("SELECT * from SAVES");

			int i = 0;
			while (resultSet.next() && i < 3) {
				int slot = resultSet.getInt("slot");
				String name = resultSet.getString("name");
				HeroClass heroClass = HeroClass.valueOf(resultSet.getString("hero_class"));
				int level = resultSet.getInt("level");
				int xp = resultSet.getInt("xp");
				int currentHp = resultSet.getInt("current_hp");

				String weaponName = resultSet.getString("weapon");
				Artifact weapon = weaponName.isEmpty() ? null : new Artifact(ArtifactTemplate.valueOf(weaponName), resultSet.getInt("weapon_bonus"));

				String armorName = resultSet.getString("armor");
				Artifact armor = armorName.isEmpty() ? null : new Artifact(ArtifactTemplate.valueOf(armorName), resultSet.getInt("armor_bonus"));

				String helmName = resultSet.getString("helm");
				Artifact helm = helmName.isEmpty() ? null : new Artifact(ArtifactTemplate.valueOf(helmName), resultSet.getInt("helm_bonus"));

				Hero hero = new Hero(name, heroClass, level, xp, currentHp, weapon, armor, helm);
				saveSlots[slot] = new SaveSlot(slot, true, hero, 0);
				i++;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}

		for (int i = 0; i < 3; i++) {
			if (saveSlots[i] == null) {
				saveSlots[i] = new SaveSlot(i, false, null, 0);
			}
		}

		return saveSlots;
	}

	private void initDatabase() throws SQLException {
		String sqlSaves = "CREATE TABLE IF NOT EXISTS saves ("
				+ "slot INT PRIMARY KEY, "
				+ "name VARCHAR(50), "
				+ "hero_class VARCHAR(30), "
				+ "level INT, "
				+ "xp INT, "
				+ "current_hp INT,"
				+ "weapon VARCHAR(30),"
				+ "armor VARCHAR(30),"
				+ "helm VARCHAR(30),"
				+ "weapon_bonus INT,"
				+ "armor_bonus INT,"
				+ "helm_bonus INT"
				+ ")";

		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			Statement stmt = conn.createStatement();
			stmt.execute(sqlSaves);
		}
	}
}
