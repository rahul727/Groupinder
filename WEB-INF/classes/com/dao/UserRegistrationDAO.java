/* @authors Shaunak Sangdod, Nayanika Bhargava
 * Team 7  ||  Software Engineering 
 * Copyright 2017, all right reserved.
 * Date written: 
 * Last modified: 
 * version 7
 * Groupinder Web-application.
 * References:
 	
 */

/**
 * Reference:
 * 07-02-2017 : https://stackoverflow.com/questions/6592010/encrypt-and-decrypt-a-password-in-java
 */
package com.dao;

import java.sql.*;
import org.apache.catalina.valves.CometConnectionManagerValve;
import java.util.*;
import com.bean.User;
import com.dao.DbConnection;
import com.controller.AES_encryption;
import com.controller.MD5_encryption;
/**
 * @author shaun
 *
 */
public class UserRegistrationDAO {
	static Connection CurrentConnection = null;
	static int succsess = 0;

	public static User register(User new_user){
		Statement statement = null;
		String user_name = new_user.getUser_name();
		String email = new_user.getEmail();
		String password = new_user.getPassword(); 
		final String secretKey = "ssshhhhhhhhhhh!!!!";			
		password = AES_encryption.encrypt(password, secretKey);//Encrypting password.
		String phone_country_code = new_user.getPhone_number_countrycode();
		String phone_main = new_user.getPhone_number_main();
		
		String insertQuery = "INSERT INTO `user` VALUES (NULL, '"+user_name+"', '"+email+"', '"+password+"', '"+phone_country_code+"', '"+phone_main+"');";
		
		try{
			CurrentConnection = DbConnection.getConnection();
			statement = CurrentConnection.createStatement();
			succsess = statement.executeUpdate(insertQuery);
			new_user.setRegistered(true);

		}catch (Exception e) {
			new_user.setRegistered(false);
			System.out.println("User registration can not be done.");
			System.out.println(e.getMessage());
		}
		//closing connections
				finally{
					
					if(statement!= null){
						try {
							statement.close();
						} catch (Exception e2) {
							statement = null;
						}
					}
					
					if(CurrentConnection!= null){
						try {
							CurrentConnection.close();
						} catch (Exception e2) {
							CurrentConnection = null;
						}
					}
				}
		return new_user;
		
	}

}
