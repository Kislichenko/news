package com.kislichenko.news.services;

import com.kislichenko.news.dao.ClientRepository;
import com.kislichenko.news.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl{

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public void create(Client client) {
        clientRepository.save(client);
    }

    public List<Client>  readAll() {
        return clientRepository.findAll();
    }

    public Client read(int id) {
        return clientRepository.getOne(id);
    }

    public boolean update(Client client, int id) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            clientRepository.save(client);
            return true;
        }

        return false;
    }

    public boolean delete(int id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
