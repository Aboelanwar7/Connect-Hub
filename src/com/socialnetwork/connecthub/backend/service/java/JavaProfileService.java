package com.socialnetwork.connecthub.backend.service.java;

import com.socialnetwork.connecthub.backend.interfaces.services.ProfileService;
import com.socialnetwork.connecthub.backend.model.User;
import com.socialnetwork.connecthub.backend.persistence.json.JsonUserRepository;
import com.socialnetwork.connecthub.shared.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class JavaProfileService implements ProfileService {

    @Override
    public void updateProfilePhoto(String userId, String photoPath) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        user.setProfilePhotoPath(photoPath);
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public void updateCoverPhoto(String userId, String photoPath) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        user.setCoverPhotoPath(photoPath);
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public void updateBio(String userId, String bio) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        user.setBio(bio);
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public void updatePassword(String userId, String newPassword) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        user.setHashedPassword(newPassword); // In a real application, hash the password before saving
        JsonUserRepository.getInstance().save(user);
    }

    @Override
    public List<UserDTO> getUserFriends(String userId) {
        User user = JsonUserRepository.getInstance().findById(userId).orElseThrow();
        List<UserDTO> friends = new ArrayList<>();

        for (String friendId : user.getFriends()) {
            JsonUserRepository.getInstance().findById(friendId).ifPresent(friend ->
                    friends.add(new UserDTO(
                            friend.getUserId(),
                            friend.getUsername(),
                            friend.getProfilePhotoPath(),
                            friend.getCoverPhotoPath(),
                            friend.getBio(),
                            friend.isOnlineStatus()
                    ))
            );
        }

        return friends;
    }
}
