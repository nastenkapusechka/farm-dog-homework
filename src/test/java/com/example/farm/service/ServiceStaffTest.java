package com.example.farm.service;

import com.example.farm.service.models.Veterinarian;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ServiceStaffTest {

    @Test
    public void testInstance() {
        Veterinarian veterinarian = new Veterinarian();
        ServiceStaff staff = new ServiceStaff("Veterinarian");

        assertThat(veterinarian, is(not(equalTo(staff))));
    }

}