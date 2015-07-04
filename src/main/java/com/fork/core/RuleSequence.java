package com.fork.core;

import com.fork.domain.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed on 04/07/15.
 */
public class RuleSequence {

    List<Rule>firedRules = new ArrayList<Rule>();

    public void setFiredRules(List<Rule> firedRules) {
        this.firedRules = firedRules;
    }

    public List<Rule> getFiredRules() {
        return firedRules;
    }
    public int size(){
        return firedRules.size();
    }
    public Rule getRule(int index){
        return firedRules.get(index);
    }
    public void add(Rule rule){
        firedRules.add(rule);
    }
    public void clear(){
        firedRules.clear();
    }
    public boolean find(Rule rule){
        for(int i = 0 ; i < firedRules.size() ; i ++)
        {
            if(firedRules.get(i).getID() == rule.getID())
                return true;
        }
        return false;
    }
}
