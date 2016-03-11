package be.kdg.teamh.services;

import be.kdg.teamh.entities.Bericht;
import be.kdg.teamh.entities.Chat;
import be.kdg.teamh.entities.Gebruiker;
import be.kdg.teamh.exceptions.ChatNotFound;
import be.kdg.teamh.exceptions.GebruikerNotFound;
import be.kdg.teamh.repositories.BerichtRepository;
import be.kdg.teamh.repositories.ChatRepository;
import be.kdg.teamh.repositories.GebruikerRepository;
import be.kdg.teamh.services.contracts.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository repository;

    @Autowired
    private GebruikerRepository gebruikerRepository;

    @Autowired
    private BerichtRepository berichtRepository;

    @Override
    public List<Chat> all() {
        return repository.findAll();
    }

    @Override
    public void create(Chat chat) {
        repository.save(chat);
    }

    @Override
    public Chat find(int id) throws ChatNotFound {
        Chat chat = repository.findOne(id);

        if (chat == null) {
            throw new ChatNotFound();
        }

        return chat;
    }

    @Override
    public void update(int id, Chat chat) throws ChatNotFound {
        Chat old = repository.findOne(id);

        if (old == null) {
            throw new ChatNotFound();
        }

        old.setNaam(chat.getNaam());
        old.setCirkelsessie(chat.getCirkelsessie());
        old.setBerichten(chat.getBerichten());

        repository.saveAndFlush(old);
    }

    @Override
    public void delete(int id) throws ChatNotFound {

        Chat old = repository.findOne(id);

        if (old == null) {
            throw new ChatNotFound();
        }

        repository.delete(id);
    }

    @Override
    public void createMessage(int id, int userId, Bericht bericht) throws ChatNotFound, GebruikerNotFound {
        Chat chat = repository.findOne(id);
        Gebruiker gebruiker = gebruikerRepository.findOne(userId);

        if (chat == null) {
            throw new ChatNotFound();
        }

        if (gebruiker == null) {
            throw new GebruikerNotFound();
        }
        //bericht
        bericht.setGebruiker(gebruiker);
        Bericht savedBericht = berichtRepository.save(bericht);

        //gebruiker
        gebruiker.addBericht(savedBericht);
        gebruikerRepository.save(gebruiker);

        //chat
        chat.addBericht(savedBericht);
        repository.save(chat);
    }
}
