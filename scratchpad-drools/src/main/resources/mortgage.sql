DROP database if exists `mortgage_db`;

CREATE database `mortgage_db`
    character set 'utf8'
	collate 'utf8_general_ci';

use `mortgage_db`;

DROP TABLE IF EXISTS `rule`;

CREATE TABLE `rule` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `rule_condition` varchar(255) NOT NULL DEFAULT '',
  `rule_consequence` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `rule` (`id`, `rule_condition`, `rule_consequence`)
VALUES
	(1, 'interestRate < 7.94 && applicantIncome < 78223','mortgageService.setRisk($mortgage, 2.6)'),
	(2, 'interestRate < 7.19 && applicantIncome >= 78223','mortgageService.setRisk($mortgage, 3.4)'),
	(3, 'interestRate >= 7.19 && interestRate < 7.94 && applicantIncome >= 78223','mortgageService.setRisk($mortgage, 9.1)');



DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `email` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `user` (`id`, `name`, `email`)
VALUES
	(1, 'Milan Agatonovic','milan@magaton.com'),
	(2, 'Sanja Agatonovic','sanja@magaton.com'),
	(3, 'Lazar Agatonovic','lazar@magaton.com');



DROP TABLE IF EXISTS `mortgage`;

CREATE TABLE `mortgage` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mortgage_amount` double NOT NULL,
  `interest_rate` double NOT NULL,
  `applicant_income` double NOT NULL,
  `loan_to_value` int(11) NOT NULL,
  `condo` tinyint(4) NOT NULL,
  `risk` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `mortgage` (`id`, `mortgage_amount`, `interest_rate`, `applicant_income`, `loan_to_value`, `condo`, `risk`)
VALUES
	(1,120000,2.3,50000,76,1,-1),
	(2,360000,4.7,100000,12,0,-1),
	(3,400000,7.5,250000,20,0,-1);



DROP TABLE IF EXISTS `task`;

CREATE TABLE `task` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `mortgage_id` int(11) unsigned NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  constraint `fk_task_user` foreign key (`user_id`) references `user`(`id`),
  constraint `fk_mortgage_user` foreign key (`mortgage_id`) references `mortgage`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

