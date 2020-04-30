package com.simongirard.petclinic.services.map;

import com.simongirard.petclinic.model.Visit;
import com.simongirard.petclinic.services.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@Profile({"default", "map"})
public class VisitMapService extends AbstractMapService<Visit, Long> implements VisitService {

    @Override
    public Set<Visit> findAll() {
        log.info("Find all Visits");

        return super.findAll();
    }

    @Override
    public Visit findById(Long id) {
        log.info("Find visit with id {}", id);

        return super.findById(id);
    }

    @Override
    public Visit save(Visit visit) {
        if(visit.getPet() == null || visit.getPet().getOwner() == null || visit.getPet().getId() == null
        || visit.getPet().getOwner().getId() == null) {
            throw new RuntimeException("Invalid visit");
        }

        log.info("Visit saved");

        return super.save(visit);
    }

    @Override
    public void delete(Visit visit) {
        log.info("Delete visit");

        super.delete(visit);
    }

    @Override
    public void deleteById(Long id) {
        log.info("Delete visit with id {}", id);

        super.deleteById(id);
    }
}
