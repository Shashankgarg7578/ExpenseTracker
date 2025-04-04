package com.expense_tracker.model;

import com.expense_tracker.entities.UserInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@SuppressWarnings("deprecation")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class) // // it will convert user_name to userName
public class UserInfoDto extends UserInfo {

	private String userName; //user_name

	private String lastName;

	private Long phoneNumber;

	private String email;

}
