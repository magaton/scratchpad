package com.magaton.scratchpad.drools;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.magaton.scratchpad.model.Mortgage;
import com.magaton.scratchpad.model.Task;
import com.magaton.scratchpad.model.User;

@Component("mortgageService")
public class MortgageService {

	private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

	public void setRisk(Mortgage m, Double r ) {
		logger.debug("set risk " + r + " to mortgage amount: " + m.getMortgageAmount());
	    m.setRisk(r);
	    m.merge();
	    Task task = new Task();
	    task.setMortgage(m);
	    List<User> candidates = User.findAllUsers();
	    // dummy assignment -> take 1st
	    User user = candidates.get(0);
	    task.setUser(user);
	    task.setStartDate(new Date());
	    task.persist();
	    logger.debug("create task " + task.getId());
	}
	
	
}
