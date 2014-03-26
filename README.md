scratchpad
==========
PoC for drools, JPA (roo generated model) and spring integration. Works with mysql db.

Interesting concepts:
- drools rules (condition and consequence) are stored in DB
- drl is compiled in memory with result set and drools template file (drt)
- rules are executed against JPA objects and spring service class is imported as a global in drools template file.
- ideally drl should not be compiled in each call (init method in RuleService should be called only upon db change).

How to run:
  1. modify scratchpad/scratchpad-model/src/main/resources/META-INF/spring/database.properties
  2. execute sql (create db, tables and test data) scratchpad/scratchpad-drools/src/main/resources/mortgage.sql
  3. mvn clean install -DskipTests=true
  4. scratchpad/scratchpad-drools/target$ java -jar scratchpad-drools-0.0.1-SNAPSHOT-standalone.jar