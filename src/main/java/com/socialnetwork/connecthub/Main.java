package com.socialnetwork.connecthub;

import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;

import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        NavigationHandlerFactory.getNavigationHandler("final").goToLoginView();
        List<User> users = JsonUserRepository.getInstance().findAll();
        for (int i = 0; i < 3; i++) {
            NavigationHandlerFactory.getNavigationHandler("final").goToNewsFeedView(new UserDTO(users.get(i)));
        }
    }

}

