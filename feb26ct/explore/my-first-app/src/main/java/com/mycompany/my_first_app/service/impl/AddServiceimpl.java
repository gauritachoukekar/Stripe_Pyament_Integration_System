package com.mycompany.my_first_app.service.impl;

import org.springframework.stereotype.Service;
import com.mycompany.my_first_app.service.interfaces.AddService;
@Service
public class AddServiceimpl implements AddService {

	@Override
	public int add(int val1, int val2) {
		// TODO Auto-generated method stub
		return val1+val2;
	}

}
