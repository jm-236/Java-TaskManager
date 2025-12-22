package edu.taskmanager.taskmanager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskmanagerApplicationTests {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @Test
//    void endpointDeCadastroDeveRetornarOk() throws Exception {
//        String content = "{\"name\": \"cacau\",\"password\": \"123\", \"email\": \"cacaugostoso@gmail.com\"}";
//        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(content))
//                .andExpect(status().isOk());
//    }

}