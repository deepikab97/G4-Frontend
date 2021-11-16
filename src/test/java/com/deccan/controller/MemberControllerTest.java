/*package com.deccan.controller;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.deccan.entity.HealthInfo;
import com.deccan.services.UserService;
//import com.asbnotebook.dto.Student;
//import com.asbnotebook.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
 
@WebMvcTest
public class MemberControllerTest {
 
    
    @MockBean
    private MockMvc mockMvc;
 
    @Autowired
    private UserService userService;
 
    private static ObjectMapper mapper = new ObjectMapper();
 
    @Test
    public void testGetExample()  {
    	List<HealthInfo> healthInfos = new ArrayList<>();
        HealthInfo healthInfo = new HealthInfo();
        healthInfo.setId(105);
        healthInfo.setHeight(50.0);;
        healthInfo.setWeight(65.0);
        healthInfo.setBmi(0.0);
        healthInfo.setBloodGroup("O+");
        healthInfos.add(healthInfo);
        Mockito.when(userService.getHealthInfo(27)).thenReturn((HealthInfo) healthInfos);
        try {
			mockMvc.perform(get("/member/healthInfo/27")).andExpect(status().isFound()).andExpect(jsonPath("$", Matchers.hasSize(1)))
			        .andExpect(jsonPath("$[0].bloodGroup", Matchers.equalTo("O+")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
    @Test
    public void testPostExample() throws Exception {
        Student student = new Student();
        student.setId(1);
        student.setName("Arun");
        Mockito.when(studentService.saveStudent(ArgumentMatchers.any())).thenReturn(student);
        String json = mapper.writeValueAsString(student);
        mockMvc.perform(post("/postMapping").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("Arun")));
    }
 
    @Test
    public void testPutExample() throws Exception {
        Student student = new Student();
        student.setId(2);
        student.setName("John");
        Mockito.when(studentService.updateStudent(ArgumentMatchers.any())).thenReturn(student);
        String json = mapper.writeValueAsString(student);
        mockMvc.perform(put("/putMapping").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(2)))
                .andExpect(jsonPath("$.name", Matchers.equalTo("John")));
    }
 
    @Test
    public void testDeleteExample() throws Exception {
        Mockito.when(studentService.deleteStudent(ArgumentMatchers.anyString())).thenReturn("Student is deleted");
        MvcResult requestResult = mockMvc.perform(delete("/deleteMapping").param("student-id", "1"))
                .andExpect(status().isOk()).andExpect(status().isOk()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Student is deleted");
    }
}*/