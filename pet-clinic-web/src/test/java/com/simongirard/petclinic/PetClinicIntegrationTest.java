package com.simongirard.petclinic;

import com.simongirard.petclinic.model.Vet;
import com.simongirard.petclinic.services.VetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.cache.Cache;
import javax.cache.CacheManager;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class PetClinicIntegrationTest {

    @Autowired
    private VetService vetService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testFindAllCacheable() {
        Cache<Object, Object> cache = cacheManager.getCache("vets");

        //Cache must be empty
        assertFalse(cache.iterator().hasNext());

        Set<Vet> vets = vetService.findAll(); //Served from Cache

        //Cache is not empty
        assertTrue(cache.iterator().hasNext());
        assertEquals("findAll", cache.iterator().next().getKey());
        assertEquals(vets, cache.iterator().next().getValue());
    }
}
