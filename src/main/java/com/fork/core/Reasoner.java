package com.fork.core;

import com.fork.domain.Rule;
import com.fork.persistance.sqlite.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 04/07/15.
 */
public class Reasoner {
    Connection connection = DatabaseConnector.getDatabaseConnection();

    /**
     * the function assume that the rules is unique
     * @param ruleSequence
     * Persist a set of fired rules in database
     */
    public void addToHistory(RuleSequence ruleSequence){
        if(ruleSequence.size() < 2){
            // there no pattern detected , one rule matched only
            return;
        }
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String firedRulesIds = "";
            if(ruleSequence.size() > 0){
                firedRulesIds += Integer.toString(ruleSequence.getRule(0).getID());
            }
            for(int i = 1 ; i < ruleSequence.size() ; i++){
                String id = Integer.toString(ruleSequence.getRule(i).getID());
                firedRulesIds += "#" + id ;
            }

            String query = "INSERT into RuleSequence (SEQUENCE) values ('"+firedRulesIds+"')";
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * perform the actual reasoning
     * @param rule
     * @return list of recommended rules
     */
    public List<Rule> performReasoning(Rule rule){
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            String query = "select * from RuleSequence";
            ResultSet resultSet = stmt.executeQuery(query);

            List<Rule>recomendedRules = new ArrayList<Rule>();
            while (resultSet.next()){
                String firedRules = resultSet.getString("SEQUENCE");
                String []seperatedFiredRulesIds = firedRules.split("#");

                int ruleIndex = checkRuleExistence(rule, seperatedFiredRulesIds);

                if(ruleIndex != -1) {
                    for (int i = 0; i < seperatedFiredRulesIds.length; i++) {
                        if(i != ruleIndex) {
                            query = "select * from Rule where ID=" + seperatedFiredRulesIds[i];
                            ResultSet resultSet1 = stmt.executeQuery(query);
                            while (resultSet1.next()) {
                                int id = resultSet.getInt("ID");
                                String name = resultSet.getString("NAME");
                                String rulee = resultSet.getString("RULE");
                                int state = resultSet.getInt("STATE");
                                Rule r = new Rule();
                                r.setID(id);
                                r.setRule(rulee);
                                r.setName(name);
                                r.setState(state);
                                recomendedRules.add(r);
                            }
                        }
                    }
                }
            }

            return recomendedRules;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * check if the rule id exist in a given set of rules ids
     * @param rule
     * @param seperatedFiredRules
     * @return the matched index of the rule
     */
    private int checkRuleExistence(Rule rule, String[] seperatedFiredRules) {
        for(int i = 0 ; i < seperatedFiredRules.length ; i ++) {
            if (rule.getID() == Integer.parseInt(seperatedFiredRules[i])) {
                return i;
            }
        }
        return -1;
    }
}
