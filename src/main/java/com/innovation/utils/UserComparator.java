package com.innovation.utils;

import java.util.Comparator;

import com.innovation.persistence.data.User;

public class UserComparator implements Comparator<User> {

	@Override
	public int compare(User u1, User u2) {
		return u1.getName().compareTo(u2.getName());
	}
}
