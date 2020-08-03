package com.anycommon.poi.word;

import com.anycommon.poi.annotation.NoFormat;

/**
 * @ClassName Question
 * @Author wangkai
 * @Date 2020/7/26 13:51
 * @Description Question
 * @Version 1.0
 */
public class Question {

    private String id;
    private String passageid;
    private String passage;
    private String question;
    private String information;

    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;
    private String option_e;
    private String option_z;

    @NoFormat
    private String answer1;
    private String answer2;

    private String parse;

    private String gradeid;
    private String subjectid;
    private String storage;
    private String think;
    private String qtype;
    private String diff;
    private String knowledges;
    private String knowledgeId;
    private String reference;

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", passageid='" + passageid + '\'' +
                ", passage='" + passage + '\'' +
                ", question='" + question + '\'' +
                ", information='" + information + '\'' +
                ", option_a='" + option_a + '\'' +
                ", option_b='" + option_b + '\'' +
                ", option_c='" + option_c + '\'' +
                ", option_d='" + option_d + '\'' +
                ", option_e='" + option_e + '\'' +
                ", option_z='" + option_z + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", parse='" + parse + '\'' +
                ", gradeid='" + gradeid + '\'' +
                ", subjectid='" + subjectid + '\'' +
                ", storage='" + storage + '\'' +
                ", think='" + think + '\'' +
                ", qtype='" + qtype + '\'' +
                ", diff='" + diff + '\'' +
                ", knowledges='" + knowledges + '\'' +
                ", knowledgeId='" + knowledgeId + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassageid() {
        return passageid;
    }

    public void setPassageid(String passageid) {
        this.passageid = passageid;
    }

    public String getPassage() {
        return passage;
    }

    public void setPassage(String passage) {
        this.passage = passage;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getOption_a() {
        return option_a;
    }

    public void setOption_a(String option_a) {
        this.option_a = option_a;
    }

    public String getOption_b() {
        return option_b;
    }

    public void setOption_b(String option_b) {
        this.option_b = option_b;
    }

    public String getOption_c() {
        return option_c;
    }

    public void setOption_c(String option_c) {
        this.option_c = option_c;
    }

    public String getOption_d() {
        return option_d;
    }

    public void setOption_d(String option_d) {
        this.option_d = option_d;
    }

    public String getOption_e() {
        return option_e;
    }

    public void setOption_e(String option_e) {
        this.option_e = option_e;
    }

    public String getOption_z() {
        return option_z;
    }

    public void setOption_z(String option_z) {
        this.option_z = option_z;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getParse() {
        return parse;
    }

    public void setParse(String parse) {
        this.parse = parse;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getThink() {
        return think;
    }

    public void setThink(String think) {
        this.think = think;
    }

    public String getQtype() {
        return qtype;
    }

    public void setQtype(String qtype) {
        this.qtype = qtype;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(String knowledges) {
        this.knowledges = knowledges;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
