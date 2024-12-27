package com.insurence.service;

import java.util.List;

import com.insurence.binding.ActivateAccount;
import com.insurence.binding.CaseUser;
import com.insurence.binding.Login;

public interface CaseWorkerManagement {

	public boolean saveUser(CaseUser user);

	public boolean activateUserAcc(ActivateAccount activateAcc);

	public List<CaseUser> getAllUsers();

	public CaseUser getUserById(Integer userId);

	public boolean deleteUserById(Integer userId);

	public boolean changeAccountStatus(Integer userId, String accStatus);

	public String login(Login login);

	public String forgotPwd(String email);

}
