package com.bl.ai.web.controller;

import com.bl.ai.service.TenantUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TenantUserController.class)
public class TenantUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TenantUserService tenantUserService;

    @Test
    public void list_requiresTenant_butReturnsOk() throws Exception {
        mockMvc.perform(get("/api/tenant/users")).andExpect(status().isOk());
    }
}
