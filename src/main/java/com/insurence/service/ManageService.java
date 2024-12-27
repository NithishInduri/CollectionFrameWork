package com.insurence.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.insurence.binding.ActivateAccount;
import com.insurence.binding.CaseUser;
import com.insurence.binding.Login;
import com.insurence.entity.CaseWorker;
import com.insurence.repository.CaseWorkerRepo;
import com.insurence.utils.EmailUtils;

@Service
public class ManageService implements CaseWorkerManagement {
	private Logger logger = LoggerFactory.getLogger(ManageService.class);
	private CaseWorkerRepo regRepo;
	private EmailUtils emailUtils;

	public ManageService(CaseWorkerRepo regRepo, EmailUtils emailUtils) {
		super();

		this.regRepo = regRepo;
		this.emailUtils = emailUtils;
	}

	private Random random = new Random();

	@Override
	public boolean saveUser(CaseUser user) {
		CaseWorker entity = new CaseWorker();
		BeanUtils.copyProperties(user, entity);
		String fullName = entity.getFullName();
		entity.setPassword(generateRandomPwd());
		entity.setAccountStatus("In-Active");
		System.out.println(entity.getAccountStatus());
		CaseWorker userrecord = regRepo.save(entity);
		System.out.println(userrecord.getAccountStatus());
		String subject = "Your Registration is Success";
		String fileName = "REG-EMAIL-BODY.txt";
		String body = readEmailBody(fullName, entity.getPassword(), fileName);
		emailUtils.sendMail(user.getEmail(), subject, body);

		return userrecord.getCwId() != null;
	}

	@Override
	public boolean activateUserAcc(ActivateAccount activateAcc) {
		CaseWorker entity = new CaseWorker();
		entity.setEmail(activateAcc.getEmail());
		entity.setPassword(activateAcc.getTempPwd());
		Example<CaseWorker> example = Example.of(entity);
		List<CaseWorker> findAll = regRepo.findAll(example);
		if (findAll.isEmpty()) {
			return false;
		} else {
			CaseWorker userMaster = findAll.get(0);
			userMaster.setPassword(activateAcc.getNewPwd());
			userMaster.setAccountStatus("Active");
			regRepo.save(userMaster);
			return true;
		}
	}

	@Override
	public List<CaseUser> getAllUsers() {
		List<CaseUser> users = new ArrayList<>();
		List<CaseWorker> findAll = regRepo.findAll();
		for (CaseWorker entity : findAll) {
			CaseUser user = new CaseUser();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		return users;
	}

	@Override
	public CaseUser getUserById(Integer userId) {
		Optional<CaseWorker> findById = regRepo.findById(userId);
		if (findById.isPresent()) {
			CaseUser user = new CaseUser();
			CaseWorker registration = findById.get();
			BeanUtils.copyProperties(registration, user);
			return user;
		}
		return null;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		try {
			regRepo.deleteById(userId);
		} catch (Exception e) {
			logger.error("Exception occured", e);
			;
		}
		return false;
	}

	@Override
	public boolean changeAccountStatus(Integer userId, String accStatus) {
		Optional<CaseWorker> findById = regRepo.findById(userId);
		if (findById.isPresent()) {
			CaseWorker userMaster = findById.get();
			userMaster.setAccountStatus(accStatus);
			regRepo.save(userMaster);
			return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {
		CaseWorker entity = new CaseWorker();
		entity.setEmail(login.getEmail());
		entity.setPassword(login.getPassword());
		Example<CaseWorker> example = Example.of(entity);
		List<CaseWorker> findAll = regRepo.findAll(example);
		if (findAll.isEmpty()) {
			return "Invalid Credentials";
		} else {
			CaseWorker userMaster = findAll.get(0);
			if (userMaster.getAccountStatus().equals("Active")) {
				return "Login Success !";
			} else {
				return "Account not activated. Please activate account";
			}
		}
	}

	@Override
	public String forgotPwd(String email) {
		CaseWorker entity = regRepo.findByEmail(email);
		if (entity == null) {
			return "Invalid Email ID";
		}
		String subject = "Forgot Password";
		String fileName = "RECOVER-PWD-BODY.txt";
		String body = readEmailBody(entity.getFullName(), entity.getPassword(), fileName);
		boolean sendMail = emailUtils.sendMail(entity.getEmail(), subject, body);

		if (sendMail) {
			return "Password sent to your registered mail";
		}
		return null;
	}

	private String generateRandomPwd() {

		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";
		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
		StringBuilder sb = new StringBuilder();
		// Random random = new Random();
		int length = 6;
		for (int i = 0; i < length; i++) {
			int index = this.random.nextInt(alphaNumeric.length());
			char randomChar = alphaNumeric.charAt(index);
			sb.append(randomChar);
		}
		String randomString = sb.toString();
		System.out.println("Random String is: " + randomString);
		return randomString;
	}

	private String readEmailBody(String fullname, String pwd, String filename) {
		String url = "";
		String mailBody = null;

		try (FileReader fr = new FileReader(filename); BufferedReader br = new BufferedReader(fr);) {
			StringBuilder builder = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				builder.append(line);
				line = br.readLine();
			}
			br.close();
			mailBody = builder.toString();
			mailBody = mailBody.replace("{FULL NAME}", fullname);
			mailBody = mailBody.replace("{TEMP-PWD}", pwd);
			mailBody = mailBody.replace("{URL}", url);
			mailBody = mailBody.replace("{PWD}", pwd);
		} catch (Exception e) {
			logger.error("Exception occured", e);
			;
		}
		return mailBody;
	}
}
