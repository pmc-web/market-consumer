package com.pmc.market.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmc.market.ProductApplication;
import com.pmc.market.model.dto.QnARequestDto;
import com.pmc.market.model.vo.QnAResponseVo;
import com.pmc.market.service.QnAService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ProductApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class QnAControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    QnAService qnAService;

    @WithMockUser
    @DisplayName("큐엔에이 작성")
    @Test
    void writeQnA() throws Exception {

        QnARequestDto request = QnARequestDto.builder()
                .title("상품문의입니다.")
                .content("50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상")
                .productId(1L)
                .build();
        doNothing().when(qnAService).makeQnA(request);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/productQnA")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("큐엔에이 수정")
    @Test
    void updateQnA() throws Exception {
        QnARequestDto request = QnARequestDto.builder()
                .title("상품문의수정입니다.")
                .content("50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상50자이상")
                .productId(1L)
                .build();
        doNothing().when(qnAService).update(1L, request);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.put("/productQnA/{id}", 1)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("큐엔에이 단일조회")
    @Test
    void getQnA() throws Exception {

        when(qnAService.getQnA(1L)).thenReturn(QnAResponseVo.builder().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/productQnA/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @WithMockUser
    @DisplayName("상품 문의글보기")
    @Test
    void getProductQnAList() throws Exception {
        List<QnAResponseVo> list = new ArrayList<>();
        when(qnAService.getProductQnAList(1L)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/productQnA/product/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("마켓 문의글보기")
    @Test
    void getShopQnAList() throws Exception {
        List<QnAResponseVo> list = new ArrayList<>();
        when(qnAService.getShopQnAList(1L)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/productQnA/shop/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("내가쓴 문의글보기")
    @Test
    void getMyQnAList() throws Exception {
        List<QnAResponseVo> list = new ArrayList<>();
        when(qnAService.getUserQnAList()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/productQnA/my")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @WithMockUser
    @DisplayName("큐엔에이 삭제")
    @Test
    void deleteQnA() throws Exception {
        doNothing().when(qnAService).delete(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/productQnA/{reviewId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}