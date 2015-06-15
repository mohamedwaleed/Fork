package com.fork.persistance;

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

	public static void updateScripts(int id, String name, String script) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "UPDATE Script set NAME = '" + name
					+ "' and script = '" + script + "' where id = '" + id
					+ "' ;";
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
				String sql = "delete from Script where id = '" + ids.get(i)
						+ "';";
				stmt.executeUpdate(sql);
			}
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addRule(String name, String rule) {
		Connection con = DatabaseConnector.getDatabaseConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "INSERT INTO Rule (NAME,RULE,STATE) " + "VALUES ('"
					+ name + "', '" + rule + "' , '" + 1 + "');";
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				String sql = "delete from Rule where id = '" + ids.get(i)
						+ "';";
				stmt.executeUpdate(sql);
			}
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
			String sql = "UPDATE Rule set state = '" + state
					+ "' where id = '" + id + "' ;";
			stmt.executeUpdate(sql);
			stmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
