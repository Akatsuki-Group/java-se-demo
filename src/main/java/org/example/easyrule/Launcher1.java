package org.example.easyrule;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.mvel2.ParserContext;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuancetian
 * date 2022-11-14
 * @since v1.0
 */
public class Launcher1 {
    public static void main(String[] args) throws Exception {
        app3();
    }
    private static void app3() throws Exception {
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        ParserContext context =new ParserContext();
        context.addImport("UserService", UserService.class);
        Rules yamlRules = ruleFactory.createRules(new FileReader(Launcher1.class.getClassLoader().getResource("rule/rules.yml").getFile()),context);
        DefaultRulesEngine rulesEngine = new DefaultRulesEngine();
        Facts facts = new Facts();
        Map<String,Object> userinfo = new HashMap<>();
        userinfo.put("name2","dalong");
        userinfo.put("age2",27);
        facts.put("user",new User("dalong",27));
        facts.put("userinfo",userinfo);
        rulesEngine.registerRuleListener(new MyRulesListener());
        rulesEngine.registerRulesEngineListener(new MyRuleEngineListener());
        rulesEngine.fire(yamlRules, facts);
    }
}
