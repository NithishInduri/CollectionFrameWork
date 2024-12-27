package com.insurence.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurence.binding.ActivateAccount;
import com.insurence.binding.CaseUser;
import com.insurence.binding.Login;
import com.insurence.service.ManageService;

@RestController
@RequestMapping("/CaseWorker")
public class CaseWorkerHandler {

	private ManageService service;

	public CaseWorkerHandler(ManageService service) {

		this.service = service;
	}

	@PostMapping("/registration")
	public ResponseEntity<String> saveUser(@RequestBody CaseUser user) {

		boolean saveUser = service.saveUser(user);
		if (saveUser) {
			return new ResponseEntity<>("User Registration is successful....", HttpStatus.CREATED);
		} else
			return new ResponseEntity<>("Registration is failed....", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@PostMapping("/activate")
	public ResponseEntity<String> activateAccount(@RequestBody ActivateAccount activateAccount) {
		boolean isActivated = service.activateUserAcc(activateAccount);
		if (isActivated) {
			return new ResponseEntity<>("Account activated", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid Temporary Password", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/CWUsers")
	public ResponseEntity<List<CaseUser>> getAllUsers() {
		List<CaseUser> allUsers = service.getAllUsers();
		return new ResponseEntity<>(allUsers, HttpStatus.OK);
	}

	@GetMapping("/CWUser/{userId}")
	public ResponseEntity<CaseUser> getUserById(@PathVariable Integer userId) {
		CaseUser user = service.getUserById(userId);
		return new ResponseEntity<CaseUser>(user, HttpStatus.OK);
	}

	@DeleteMapping("/CWUser/{userId}")
	public ResponseEntity<String> deleteUserById(@PathVariable Integer userId) {
		boolean isDeleted = service.deleteUserById(userId);

		if (isDeleted) {
			return new ResponseEntity<String>("User Deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("User Delete Failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/status/{userId}/{status}")
	public ResponseEntity<String> statusChange(@PathVariable Integer userId, @PathVariable String status) {
		boolean isChanged = service.changeAccountStatus(userId, status);
		if (isChanged) {
			return new ResponseEntity<>("Status Changed", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Failed to Change", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Login login) {
		String loginStatus = service.login(login);
		return new ResponseEntity<String>(loginStatus, HttpStatus.OK);
	}

	@GetMapping("/forgotpwd/{email}")
	public ResponseEntity<String> forgotPwd(@PathVariable String email) {
		String status = service.forgotPwd(email);
		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}
