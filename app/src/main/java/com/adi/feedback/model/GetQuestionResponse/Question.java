
package com.adi.feedback.model.GetQuestionResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("op_i")
    @Expose
    private String opI;
    @SerializedName("op_ii")
    @Expose
    private String opIi;
    @SerializedName("op_iii")
    @Expose
    private String opIii;
    @SerializedName("op_iv")
    @Expose
    private String opIv;
    @SerializedName("extraf")
    @Expose
    private String extraf;
    @SerializedName("extraff")
    @Expose
    private String extraff;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpI() {
        return opI;
    }

    public void setOpI(String opI) {
        this.opI = opI;
    }

    public String getOpIi() {
        return opIi;
    }

    public void setOpIi(String opIi) {
        this.opIi = opIi;
    }

    public String getOpIii() {
        return opIii;
    }

    public void setOpIii(String opIii) {
        this.opIii = opIii;
    }

    public String getOpIv() {
        return opIv;
    }

    public void setOpIv(String opIv) {
        this.opIv = opIv;
    }

    public String getExtraf() {
        return extraf;
    }

    public void setExtraf(String extraf) {
        this.extraf = extraf;
    }

    public String getExtraff() {
        return extraff;
    }

    public void setExtraff(String extraff) {
        this.extraff = extraff;
    }

}
