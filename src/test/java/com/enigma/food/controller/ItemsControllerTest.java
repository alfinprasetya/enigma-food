package com.enigma.food.controller;

import com.enigma.food.model.Items;
import com.enigma.food.service.ItemsService;
import com.enigma.food.utils.dto.ItemsCreateDto;
import com.enigma.food.utils.dto.ItemsUpdateDto;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ItemsControllerTest {
    @Mock
    private ItemsService itemsService;

    @InjectMocks
    private ItemsController itemsController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(itemsController).build();
    }

    @Test
    public void testCreateItems()throws Exception{
        ItemsCreateDto itemsCreateDto = new ItemsCreateDto("new Items", 57);

        Items items = new Items();
        items.setId(1);
        items.setName("New Items");
        items.setQty(57);

        when(itemsService.create(any(ItemsCreateDto.class))).thenReturn(items);

        mockMvc.perform(post("/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemsCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name", is("New Items")));

        verify(itemsService).create(any(ItemsCreateDto.class));
    }

    @Test
    public void testGetOneItems()throws Exception{
        Items items = new Items();
        items.setId(1);
        items.setName("Items1");
        items.setQty(12);

        when(itemsService.getOne(1)).thenReturn(items);

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Items1")));

        verify(itemsService).getOne(1);
    }

    @Test
    public void testGetOneItemsNotFound()throws Exception{
        when(itemsService.getOne(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Items Not Found"));

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateItems() throws Exception{
        ItemsUpdateDto itemsUpdateDto = new ItemsUpdateDto("Updated Items", 16);

        Items items = new Items();
        items.setId(1);
        items.setName("Updated Items");
        items.setQty(16);

        when(itemsService.update(eq(1), any(ItemsUpdateDto.class))).thenReturn(items);

        mockMvc.perform(put("/items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemsUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is("Updated Items")));

        verify(itemsService).update(eq(1), any(ItemsUpdateDto.class));
    }

    @Test
    public void testDeleteItems()throws Exception{
        doNothing().when(itemsService).delete(1);

        mockMvc.perform(delete("/items/1"))
                .andExpect(status().isOk());

        verify(itemsService, times(1)).delete(1);
    }
}
