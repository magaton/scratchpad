package com.magaton.scratchpad.drools;

import java.io.InputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.PackageBuilder;
import org.drools.template.jdbc.ResultSetGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.magaton.scratchpad.model.Mortgage;

@Component("ruleService")
public class RuleService {
	  // set up database connection
	private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

	  StatefulSession workingMemory;
	
	  @Autowired
	  MortgageService mortgageService;
	  
	  @Autowired
	  DataSource datasource;
		  
	  public void init() throws Exception {
		  InputStream is = null;
		  Connection conn = null;
		  PreparedStatement preparedStmt = null;
		  ResultSet resultSet = null;
		  try{
			  is = MortgageRiskCalculator.class.getResourceAsStream("/mortgage.drt");
			  conn = datasource.getConnection();
			  // build drl with data from database
			  preparedStmt = conn.prepareStatement("select rule_condition, rule_consequence from rule");
			  resultSet = preparedStmt.executeQuery();
			  ResultSetGenerator resultSetGenerator = new ResultSetGenerator();
			  String drl = resultSetGenerator.compile(resultSet, is);
			  logger.debug("------------------------------------------------");
			  logger.debug(drl);
			  logger.debug("------------------------------------------------");

			  // build rule base
			  PackageBuilder packageBuilder = new PackageBuilder();
			  packageBuilder.addPackageFromDrl(new StringReader(drl));
			  RuleBase ruleBase = RuleBaseFactory.newRuleBase();
			  ruleBase.addPackage(packageBuilder.getPackage());
			  workingMemory = ruleBase.newStatefulSession();
			  workingMemory.setGlobal("mortgageService", mortgageService);
		  } finally {
			  try {
				  resultSet.close();
				  preparedStmt.close();			  
                  conn.close();
                  is.close();
		      } catch (Exception e) {
		        e.printStackTrace();
		      }
		  }
	  }
	  
	  
	  public void runRules(List<Mortgage> facts) {
		 for(Mortgage fact : facts){
			 logger.debug("Adding fact " + fact.getMortgageAmount());
			 workingMemory.insert(fact);
		 }
	    workingMemory.fireAllRules();
	    workingMemory.dispose();
	  }
	}