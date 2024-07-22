package com.enigma.food.service.impl;

import com.enigma.food.model.Ingridients;
import com.enigma.food.repository.IngridientsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngridientsServiceImplTest {

    @Mock
    private IngridientsRepository ingridientsRepository;

    @InjectMocks
    private IngridientsServiceImpl ingridientsService;

    @Test
    public void testGetAll() {
        Ingridients ing1 = new Ingridients();
        Ingridients ing2 = new Ingridients();
        List<Ingridients> ingList = Arrays.asList(ing1, ing2);

        when(ingridientsRepository.findAll()).thenReturn(ingList);

        List<Ingridients> result = ingridientsService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(ing1, result.get(0));
        assertEquals(ing2, result.get(1));
    }

    @Test
    public void testGetOne_Success() {
        Ingridients ing = new Ingridients();
        when(ingridientsRepository.findById(1)).thenReturn(Optional.of(ing));

        Ingridients result = ingridientsService.getOne(1);

        assertNotNull(result);
        assertEquals(ing, result);
    }

    @Test
    public void testGetOne_NotFound() {
        when(ingridientsRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> ingridientsService.getOne(1));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        assertEquals("Items With 1 Not Found", thrown.getReason());
    }

    @Test
    public void testCreate() {
        Ingridients ing = new Ingridients();
        when(ingridientsRepository.save(any(Ingridients.class))).thenReturn(ing);

        Ingridients result = ingridientsService.create(ing);

        assertNotNull(result);
        assertEquals(ing, result);
    }

    @Test
    public void testDelete_Success() {
        doNothing().when(ingridientsRepository).deleteById(1);

        ingridientsService.delete(1);

        verify(ingridientsRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDelete_NotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingridients Not Found")).when(ingridientsRepository).deleteById(1);

        assertThrows(ResponseStatusException.class, () -> ingridientsService.delete(1));
        verify(ingridientsRepository, times(1)).deleteById(1);
    }
}
