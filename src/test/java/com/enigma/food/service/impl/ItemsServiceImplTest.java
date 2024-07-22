package com.enigma.food.service.impl;

import com.enigma.food.model.Items;
import com.enigma.food.repository.ItemsRepo;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.ItemsCreateDto;
import com.enigma.food.utils.dto.ItemsUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemsServiceImplTest {
    @Mock
    private ItemsRepo itemsRepo;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private ItemsServiceImpl itemsService;

    private List<Items> itemsList;

    @BeforeEach
    void setUp(){
        Items item1 = new Items();
        item1.setId(1);
        item1.setName("Buah Anggur");
        item1.setQty(23);

        Items item2 = new Items();
        item2.setId(2);
        item2.setName("Susu");
        item2.setQty(11);

        itemsList = Arrays.asList(item1);
    }

    @Test
    public void getOneSuccess(){
        Items item = new Items();
        item.setId(1);
        item.setName("Buah Anggur");
        when(itemsRepo.findById(1)).thenReturn(Optional.of(item));
        Items result = itemsService.getOne(1);
        assertEquals(item, result);
    }

    @Test
    public void getOneNotFound(){
        when(itemsRepo.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> itemsService.getOne(1));
    }

    @Test
    public void createSuccess() {
        ItemsCreateDto dto = new ItemsCreateDto("Buah Anggur", 22);

        Items expectedItem = new Items();
        expectedItem.setName(dto.getName());
        expectedItem.setQty(dto.getQty());

        when(itemsRepo.save(any())).thenReturn(expectedItem);

        Items result = itemsService.create(dto);

        assertNotNull(result);
        assertEquals("Buah Anggur", result.getName());
        assertEquals(22, result.getQty());
        verify(validationService, times(1)).validate(dto);
        verify(itemsRepo, times(1)).save(any(Items.class));
    }

    @Test
    public void updateSuccess(){
        Items item = new Items();
        ItemsUpdateDto dto = new ItemsUpdateDto("Buah Anggur", 44);
        when(itemsRepo.findById(1)).thenReturn(Optional.of(item));
        when(itemsRepo.save(any(Items.class))).thenReturn(item);

        Items result = itemsService.update(1, dto);

        assertNotNull(result);
        assertEquals("Buah Anggur", result.getName());
        assertEquals(44,result.getQty());
        verify(validationService, times(1)).validate(dto);
        verify(itemsRepo, times(1)).findById(1);
        verify(itemsRepo, times(1)).save(any(Items.class));
    }

    @Test
    public void updateNotFound(){
        ItemsUpdateDto dto = new ItemsUpdateDto("Buah Anggur", 44);
        when(itemsRepo.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> itemsService.update(1, dto));
        verify(itemsRepo, times(1)).findById(1);
        verify(itemsRepo, times(0)).save(any(Items.class));

    }

    @Test
    public void deleteSuccess(){
        itemsService.delete(2);

        verify(itemsRepo, times(1)).deleteById(2);
    }
}
