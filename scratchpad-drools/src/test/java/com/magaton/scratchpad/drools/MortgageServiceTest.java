package com.magaton.scratchpad.drools;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.magaton.scratchpad.model.Mortgage;
import com.magaton.scratchpad.model.Task;
import com.magaton.scratchpad.model.User;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/spring/drools-applicationContext.xml",
        "classpath*:META-INF/spring/applicationContext.xml"})
@Configurable
public class MortgageServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(RuleService.class);

	static Integer taskId = 0;
	@Test
	public void testMortgageSelect(){
		  List<Mortgage> mortgages = Mortgage.findAllMortgages();		
		  logger.debug("mortgages size: "  + mortgages.size());
	}
	
	@Test
	public void testTaskCrud(){
		  Task task = new Task();
		  Mortgage m = Mortgage.findMortgage(1);
		  User u = User.findUser(1);
		  task.setMortgage(m);
		  task.setUser(u);
		  task.persist();
		  taskId = task.getId();
		  logger.debug("created new task id: "  + taskId);
		  assertTrue(taskId > 0);
		  task.setStartDate(new Date());
		  task.merge();
		  logger.debug("task.getStartDate() "  + task.getStartDate());
		  assertNotNull(task.getStartDate());
		  task.remove();
	}

}
