package com.greenfoxacademy.annie.p2pchatapp.Service;

import com.greenfoxacademy.annie.p2pchatapp.Model.Appuser;
import com.greenfoxacademy.annie.p2pchatapp.Repository.AppuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AppuserServiceDbImpl implements AppuserService {
    private final AppuserRepository appuserRepository;

    @Autowired
    public AppuserServiceDbImpl(AppuserRepository appuserRepository) {
        this.appuserRepository = appuserRepository;
    }

    @Override
    public Appuser findOne(long id) {
        return appuserRepository.findOne(id);
    }

    @Override
    public List<Appuser> findAll() {
        List<Appuser> appusers = new ArrayList<>();
        appuserRepository.findAll().forEach(appusers::add);
        Collections.sort(appusers, Comparator.comparing(Appuser::getId));
        return appusers;
    }

    @Override
    public void save(Appuser appuser) {
        appuserRepository.save(appuser);
    }

    @Override
    public Appuser findByName(String name) {
        return appuserRepository.findByUsername(name);
    }

    @Override
    public boolean exist(String name) {
        //return findAll().contains(appuserRepository.findByName(name));
        return appuserRepository.findByUsername(name) != null;
    }

}
