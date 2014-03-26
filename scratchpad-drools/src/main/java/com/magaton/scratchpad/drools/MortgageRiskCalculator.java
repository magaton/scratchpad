package com.magaton.scratchpad.drools;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.magaton.scratchpad.model.Mortgage;

@Component("mortgageRiskCalculator")
public class MortgageRiskCalculator {
	
	private static final Logger logger = LoggerFactory.getLogger(MortgageRiskCalculator.class);

	@Autowired
	RuleService ruleService;
	
	public void execute() throws Exception{
		  List<Mortgage> mortgages = Mortgage.findAllMortgages();		
		  logger.debug("mortgages size: "  + mortgages.size());
		  ruleService.init();
		  ruleService.runRules(mortgages);
		  for(Mortgage mortgage : mortgages){
			  logger.debug("risk="  + mortgage.getRisk());
		  }
	}
	
	public static void main(String[] args) throws Exception{
		
		 ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                 new String[]{
                         "classpath*:META-INF/spring/drools-applicationContext.xml",
                         "classpath*:META-INF/spring/applicationContext.xml"
                 });
         logger.info("context created");

         MortgageRiskCalculator mortgageRiskCalculator = context.getBean(MortgageRiskCalculator.class);
         mortgageRiskCalculator.execute();
	}
}	
