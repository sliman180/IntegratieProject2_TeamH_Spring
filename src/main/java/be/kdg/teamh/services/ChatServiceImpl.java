package be.kdg.teamh.services;

import be.kdg.teamh.entities.Bericht;
import be.kdg.teamh.entities.Chat;
import be.kdg.teamh.exceptions.ChatNotFound;
import be.kdg.teamh.repositories.ChatRepository;
import be.kdg.teamh.services.contracts.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository repository;

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

        repository.save(old);
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
    public void createMessage(int id, Bericht bericht) throws ChatNotFound {
        Chat chat = repository.findOne(id);


        if (chat == null) {
            throw new ChatNotFound();
        }

        chat.addBericht(bericht);
        repository.save(chat);
    }

}
