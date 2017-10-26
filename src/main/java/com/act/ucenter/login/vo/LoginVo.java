/**   
 * @Title: LoginVO.java 
 * @Package com.act.web.module.common.vo 
 * @Description: (登入模块vo) 
 * @author   fmj
 * @date 2017-2-6 下午5:10:14 
 * @version V1.0   
 */
package com.act.ucenter.login.vo;

public class LoginVo {
	public String forget;
	private String loginIsCode;
	/* 忘记密码模块 */
	private String uid;
	private String act;
	private String step;
	private String question;
	private String answer;
	private String newpwd;
	/* 登入方法 */
	private String userId;
	private String password;
	private String verifyId;

	/* delete id */
	private String deleteIds;

	public String getLoginIsCode() {
		return loginIsCode;
	}

	public void setLoginIsCode(String loginIsCode) {
		this.loginIsCode = loginIsCode;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public String getForget() {
		return forget;
	}

	public void setForget(String forget) {
		this.forget = forget;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

}
