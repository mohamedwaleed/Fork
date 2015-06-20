package com.fork.persistance.sqlite;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fork.domain.Rule;
import com.fork.domain.Script;

public class DatabaseLogic {

	public static void addScript(String name, String script) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "INSERT INTO Script (NAME,SCRIPT) " + "VALUES ('"
					+ name + "', '" + script + "' );";
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Script> getScripts() {
		List<Script> scripts = new ArrayList<Script>();
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Script;");
			System.out.println("asdasdasd");
			while (rs.next()) {
				Script sc = new Script();
				sc.setId(rs.getInt("id"));
				sc.setName(rs.getString("name"));
				sc.setScript(rs.getString("script"));
				scripts.add(sc);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return scripts;
	}

	public static void updateScript(int id, String name, String script) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "UPDATE Script set NAME = '" + name + "', script = '"
					+ script + "' where id = '" + id + "' ;";
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void deleteScripts(List<Integer> ids) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			for (int i = 0; i < ids.size(); i++) {
				stmt.addBatch("delete from Rule_Script where script_id = '"
						+ ids.get(i) + "';");
				stmt.addBatch("delete from Script where id = '" + ids.get(i)
						+ "';");
			}
			stmt.executeBatch();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int addRule(String name, String rule) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		int id = 0;
		try {
			stmt = con.createStatement();
			String sql = "INSERT INTO Rule (NAME,RULE,STATE) " + "VALUES ('"
					+ name + "', '" + rule + "' , '" + 1 + "');";
			stmt.executeUpdate(sql);

			String getId = "SELECT last_insert_rowid() AS id";
			ResultSet se = stmt.executeQuery(getId);
			if (se.next())
				id = se.getInt("id");
			se.close();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static List<Rule> getRules() {
		List<Rule> rules = new ArrayList<Rule>();
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Rule;");
			while (rs.next()) {
				Rule sc = new Rule();
				sc.setId(rs.getInt("id"));
				sc.setName(rs.getString("name"));
				sc.setRule(rs.getString("rule"));
				sc.setState(rs.getInt("state"));
				rules.add(sc);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return rules;
	}

	public static void deleteRules(List<Integer> ids) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			for (int i = 0; i < ids.size(); i++) {
				stmt.addBatch("delete from Rule_Script where rule_id = '"
						+ ids.get(i) + "';");
				stmt.addBatch("delete from Rule where id = '" + ids.get(i)
						+ "';");
			}
			stmt.executeBatch();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void changeRuleState(int id, int state) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "UPDATE Rule set state = '" + state + "' where id = '"
					+ id + "' ;";
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addRuleScripts(List<Script> pickedScripts, int newId) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			for (Script script : pickedScripts) {
				stmt.addBatch("INSERT INTO Rule_Script (SCRIPT_ID,RULE_ID) "
						+ "VALUES ('" + script.getId() + "', '" + newId + "');");
			}
			stmt.executeBatch();
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Script> getRuleScripts(int ruleId) {
		List<Script> scripts = new ArrayList<Script>();
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			con.setAutoCommit(false);
			stmt = con.createStatement();
			ResultSet rs = stmt
					.executeQuery("select script.id,script.name,script.script from  script,rule, rule_script where rule_script.rule_id= '"
							+ ruleId
							+ "' and rule.id = rule_script.rule_id and script.id = rule_script.script_id ;");
			while (rs.next()) {
				Script sc = new Script();
				sc.setId(rs.getInt("id"));
				sc.setName(rs.getString("name"));
				sc.setScript(rs.getString("script"));
				scripts.add(sc);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return scripts;
	}

	public static void deleteRuleScripts(int id) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "delete from Rule_Script where rule_id = '" + id
					+ "' ;";
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
