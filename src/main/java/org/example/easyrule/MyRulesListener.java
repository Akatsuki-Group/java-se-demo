package org.example.easyrule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.RuleListener;

public class MyRulesListener implements RuleListener {
    Log log = LogFactory.getLog(MyRulesListener.class);

    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        return true;
    }

    @Override
    public void afterEvaluate(Rule rule, Facts facts, boolean b) {
        log.info("-----------------" + rule.getName() + " afterEvaluate-----------------");
        // log.info(rule.getName()+rule.getDescription()+facts.toString());
    }

    @Override
    public void beforeExecute(Rule rule, Facts facts) {
        log.info("-----------------" + rule.getName() + " beforeExecute-----------------");

        // log.info(rule.getName()+rule.getDescription()+facts.toString());

    }

    @Override
    public void onSuccess(Rule rule, Facts facts) {
        log.info("-----------------" + rule.getName() + " onSuccess-----------------");
        // log.info(rule.getName()+rule.getDescription()+facts.toString());

    }

    @Override
    public void onFailure(Rule rule, Facts facts, Exception e) {
        log.info("-----------------" + rule.getName() + " onFailure-----------------");
        log.info(rule.getName() + "----------" + rule.getDescription() + facts.toString() + e.toString());

    }
}