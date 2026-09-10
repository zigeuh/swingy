package io.github.zigeuh.swingy.model;

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
			String request = "MERGE INTO SAVES (slot, name, hero_class, level, xp, attack, defense, hp)"
					+ "KEY(SLOT) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
			PreparedStatement stmt = conn.prepareStatement(request);

			stmt.setInt(0, slot);
			stmt.setString(1, hero.getName());
			stmt.setString(2, hero.getHeroClass());
			stmt.setInt(3, hero.getLevel());
			stmt.setInt(4, hero.getXp());
			stmt.setInt(5, hero.getAttack());
			stmt.setInt(6, hero.getDefense());
			stmt.setInt(7, hero.getHp());

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
				String heroClass = resultSet.getString("hero_class");
				int level = resultSet.getInt("level");
				int xp = resultSet.getInt("xp");
				int attack = resultSet.getInt("attack");
				int defense = resultSet.getInt("defense");
				int hp = resultSet.getInt("hp");

				saveSlots[slot] = new SaveSlot(slot + 1, true, new Hero(name, heroClass, level, xp, attack, defense, hp), 0);
				i++;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}

		for (int i = 0; i < 3; i++) {
			if (saveSlots[i] == null) {
				saveSlots[i] = new SaveSlot(i + 1, false, null, 0);
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
				+ "attack INT, "
				+ "defense INT, "
				+ "hp INT"
				+ ")";

		try (Connection conn = DriverManager.getConnection(DB_URL)) {
			Statement stmt = conn.createStatement();
			stmt.execute(sqlSaves);
		}
	}
}
