package be.kdg.teamh.services;

import be.kdg.teamh.entities.Subthema;
import be.kdg.teamh.repositories.SubthemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubthemaServiceImpl implements SubthemaService {

    @Autowired
    SubthemaRepository subthemaRepository;

    @Override
    public void createSubthema(Subthema subthema){
        this.subthemaRepository.save(subthema);
    }

    @Override
    public List<Subthema> findAll() {
        return this.subthemaRepository.findAll();
    }

}
