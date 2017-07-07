package io.springboot.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.springboot.example.component.UserComponent;
import io.springboot.example.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test CRUD functionality for User.
 *
 * @author shekh akther
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Slf4j
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserComponent userComponent;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void testCreateUser() throws Exception {

        String fileLocation = getClass().getClassLoader().getResource("postUser.json").getFile();
        String requestBody = new String(Files.readAllBytes(Paths.get(fileLocation)));

        this.mockMvc.perform(post("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(requestBody))
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(jsonPath("$.name", is("John Doe")));
    }


    @Test
    public void testGetUsers() throws Exception {

        User user = storeUser();
        String name = user.getName();

        this.mockMvc.perform(get("/users")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(name)));

    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = storeUser();
        String newName = "Nikola Tesla";
        user.setName(newName);
        this.mockMvc.perform(put("/users/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful());

        assertNotNull(userComponent.getUsersByName(newName));
    }


    @Test
    public void testDeleteUser() throws Exception {
        storeUser();
        this.mockMvc.perform(delete("/users/1")
                                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    private User storeUser() {
        User user = new User();
        String name = "shekh akther";
        user.setName(name);
        user.setAge(33);

        return userComponent.storeUser(user);
    }
}
