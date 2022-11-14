package org.example.easyrule;

import jdk.nashorn.api.scripting.URLReader;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.jeasy.rules.mvel.MVELRule;
import org.jeasy.rules.mvel.MVELRuleFactory;
import org.jeasy.rules.support.YamlRuleDefinitionReader;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

@Slf4j
public class Launcher {

    public static void main(String[] args) throws Exception {

        // create facts
        Facts facts = new Facts();
        // create rules
        Rules rules = new Rules();
        RulesEngine rulesEngine = new DefaultRulesEngine();
        ////基于注解模式
        //rules.register(new HelloWorldRule());
        //
        //// create a rules engine and fire rules on known facts
        //rulesEngine.fire(rules, facts);
        ////基于注解模式
        //rules.register(new WeatherRule());
        //facts.put("rain", true);
        //rulesEngine.fire(rules, facts);
        //
        ////链式调用
        //Rule weatherRule = new RuleBuilder()
        //        .name("weather rule chain")
        //        .description("if it rains then take an umbrella")
        //        .when(facts1 -> facts1.get("rain").equals(true))
        //        .then(facts1 -> System.out.println("It chain rains, take an umbrella!" + facts1.get("rain")))
        //        .build();
        //rules.register(weatherRule);
        //facts.put("rain", true);
        //rulesEngine.fire(rules, facts);
        //基于表达式
        //Rule expressRule = new MVELRule()
        //        .name("weather express rule")
        //        .description("if it rains then take an umbrella")
        //        .when("rain.equals('obito')")
        //        .then("System.out.println(\"It express rains, take an umbrella!\");");
        //rules.register(expressRule);
        //facts.put("rain", "obito");
        //rulesEngine.fire(rules, facts);

        //基于文件
        MVELRuleFactory ruleFactory = new MVELRuleFactory(new YamlRuleDefinitionReader());
        Rule weatherRule = ruleFactory.createRule(new FileReader("D:\\project\\Akatsuki-Group\\java-se-demo\\target\\classes\\rule\\weather-rule.yml"));
        //Rule weatherRule = ruleFactory.createRule(new URLReader(new URL("https://raw.githubusercontent.com/j-easy/easy-rules/master/easy-rules-core/src/test/resources/rules/rules.yml")));
        rules.register(weatherRule);
        facts.put("rain", true);
        rulesEngine.fire(rules, facts);
    }
}