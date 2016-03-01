package be.kdg.teamh.services;

import be.kdg.teamh.entities.Chat;
import be.kdg.teamh.entities.Message;
import be.kdg.teamh.exceptions.ChatNotFound;

import java.util.List;

public interface ChatService {

    List<Chat> all();

    void create(Chat chat);

    Chat find(int id) throws ChatNotFound;

    void update(int id, Chat chat) throws ChatNotFound;

    void delete(int id) throws ChatNotFound;

    void createMessage(int id, Message message) throws ChatNotFound;
}
