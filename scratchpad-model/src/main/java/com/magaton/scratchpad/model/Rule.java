package com.magaton.scratchpad.model;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "rule")
@Configurable
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "rule")
@RooDbManaged(automaticallyDelete = true)
public class Rule {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Rule().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countRules() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Rule o", Long.class).getSingleResult();
    }

	public static List<Rule> findAllRules() {
        return entityManager().createQuery("SELECT o FROM Rule o", Rule.class).getResultList();
    }

	public static Rule findRule(Integer id) {
        if (id == null) return null;
        return entityManager().find(Rule.class, id);
    }

	public static List<Rule> findRuleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Rule o", Rule.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Rule attached = Rule.findRule(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public Rule merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Rule merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Column(name = "rule_condition", length = 255)
    @NotNull
    private String ruleCondition;

	@Column(name = "rule_consequence", length = 255)
    @NotNull
    private String ruleConsequence;

	public String getRuleCondition() {
        return ruleCondition;
    }

	public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

	public String getRuleConsequence() {
        return ruleConsequence;
    }

	public void setRuleConsequence(String ruleConsequence) {
        this.ruleConsequence = ruleConsequence;
    }

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

	public Integer getId() {
        return this.id;
    }

	public void setId(Integer id) {
        this.id = id;
    }
}
