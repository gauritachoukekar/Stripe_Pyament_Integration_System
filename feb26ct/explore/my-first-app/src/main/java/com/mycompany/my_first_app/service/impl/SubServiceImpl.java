package com.mycompany.my_first_app.service.impl;

import com.mycompany.my_first_app.service.interfaces.SubService;
import org.springframework.stereotype.Service;

@Service
public class SubServiceImpl implements SubService {
    @Override
    public int sub(int val1, int val2) {
        return val1 - val2;
    }
}
