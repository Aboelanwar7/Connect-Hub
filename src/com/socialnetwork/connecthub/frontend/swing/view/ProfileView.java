package com.socialnetwork.connecthub.frontend.swing.view;

import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.backend.interfaces.services.ProfileService;
import com.socialnetwork.connecthub.backend.interfaces.services.UserAccountService;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.RoundedImageLabel;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import test.ContentServiceTest;
import test.ProfileServiceTest;
import test.UserAccountServiceTest;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProfileView extends View {
    JPanel profilePanel;
    JPanel backgroundPanel;
    RoundedImageLabel profilePhoto;
    JLabel nameLabel;
    JLabel bioLabel;
    UserDTO user;
    ContentService contentService;
    ProfileService profileService;
    UserAccountService userAccountService;

    public ProfileView(UserDTO user) {
        contentService = new ContentServiceTest();
        userAccountService = new UserAccountServiceTest();
        this.user = user;
        profileService = new ProfileServiceTest();
        profilePanel = new JPanel(null);
        profilePanel.setBackground(new Color(215, 215, 215));
        profilePanel.setLayout(null); // Use null layout for precise positioning
        profilePanel.setBounds(0, 0, getWidth(), getHeight());

        if (user.getCoverPhotoPath() == null || user.getCoverPhotoPath().isEmpty()) {
            user.setCoverPhotoPath("src/test/Screenshot 2024-12-03 011157.png");
        }
        if (user.getProfilePhotoPath() == null || user.getProfilePhotoPath().isEmpty()) {
            user.setProfilePhotoPath("src/test/Screenshot 2024-12-03 011157.png");
        }

        // Set up background panel for the cover photo
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon(user.getCoverPhotoPath()).getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setBounds(0, 0, getWidth(), 200); // Fixed height for cover photo
        profilePanel.add(backgroundPanel); // Add background panel first

        // Set up left panel for profile photo, username, and bio
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);  // Using null layout for positioning
        leftPanel.setBackground(Color.WHITE);  // White background for the left side
        leftPanel.setBounds(0, 200, 300, getHeight());  // Fixed width of 300px and height as full frame

        // Set up profile photo on left panel
        profilePhoto = new RoundedImageLabel(user.getProfilePhotoPath(), 120, 120);
        profilePhoto.setBounds(90, 10, 120, 120); // Positioned within the left panel
        leftPanel.add(profilePhoto); // Add profile photo to left panel

        // Set up user name label under the profile photo
        nameLabel = new JLabel(user.getUsername(), 24, Color.BLACK, Font.BOLD);
        nameLabel.setBounds(90, 140, 200, 20);  // Positioned below profile photo
        leftPanel.add(nameLabel);

        // Set up bio label under the username
        bioLabel = new JLabel(user.getBio(), 18, Color.BLACK, Font.ITALIC);
        bioLabel.setBounds(50, 175, 200, 20);  // Positioned below username
        leftPanel.add(bioLabel);

        // Add buttons
        com.socialnetwork.connecthub.frontend.swing.components.JButton editProfileButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Edit Profile", 16, 12);
        com.socialnetwork.connecthub.frontend.swing.components.JButton friendManagerButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Friend Manager", 16, 12);
        com.socialnetwork.connecthub.frontend.swing.components.JButton homeButton = new com.socialnetwork.connecthub.frontend.swing.components.JButton("Home", 16, 12);


        homeButton.setBounds(75, 440, 150, 50);

        leftPanel.add(homeButton);
        editProfileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                        //open edit profile view
            }
        });

        friendManagerButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open friend manager view

            }
        });

        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Open news feed view

            }
        });

        profilePanel.add(leftPanel); // Add left panel
        leftPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 6));

        // Create a timeline panel and add it inside a JScrollPane
        JPanel timelinePanel = new JPanel(null);
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBackground(new Color(215, 215, 215));

        List<ContentDTO> contentList = contentService.getUserPosts(user.getUserId());
        for (ContentDTO content :  contentService.getUserPosts(user.getUserId())) {
            JPanel contentLabel = createContentLabel(content);
            timelinePanel.add(contentLabel);
            timelinePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
        }

        // Adjust preferred size based on content
        // Adjust content panel's preferred size dynamically
        int panelHeight = Math.max(1500, contentList.size() * 1210); // 1210px per content including spacing
        timelinePanel.setPreferredSize(new Dimension(800, panelHeight));

        // Create the scroll pane and set its bounds
        JScrollPane scrollPane = new JScrollPane(timelinePanel);
        scrollPane.setBounds(310, 200, 900, 600); // Set the size and position of the scroll pane
        scrollPane.setBackground(new Color(215, 215, 215));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizont

        profilePanel.add(scrollPane);

        // Add profile panel to main view
        add(profilePanel);
        repaint();
        revalidate();
        setVisible(true);
    }





    private JPanel createContentLabel(ContentDTO content) {
        // Create the content panel with a null layout for custom positioning
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(Color.WHITE);

        // Add rounded image for the author
        if (content.getImagePath() == null || content.getImagePath().isEmpty()) {
            RoundedImageLabel authorImageLabel = new RoundedImageLabel("src/com/socialnetwork/connecthub/resources/pics/friends.png", 50, 50);
            authorImageLabel.setBounds(10, 10, 50, 50); // Position the image
            contentPanel.add(authorImageLabel);

        }
        else {
            RoundedImageLabel authorImageLabel = new RoundedImageLabel(content.getImagePath(), 50, 50);
            authorImageLabel.setBounds(10, 10, 50, 50); // Position the image
            contentPanel.add(authorImageLabel);

        }


        // Add author name text
        javax.swing.JLabel authorNameLabel = new javax.swing.JLabel(userAccountService.getUserById(content.getAuthorId()).getUsername());
        authorNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        authorNameLabel.setForeground(Color.BLACK);

        authorNameLabel.setBounds(70, 20, 200, 30); // Adjusted position

        contentPanel.add(authorNameLabel);

        // Add timestamp text
        javax.swing.JLabel timestampLabel = new javax.swing.JLabel(content.getTimestamp().toString());
        timestampLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timestampLabel.setForeground(Color.GRAY);
        timestampLabel.setBounds(650, 20, 170, 30); // Adjusted position
        contentPanel.add(timestampLabel);

        // Add content text
        javax.swing.JLabel contentTextLabel = new javax.swing.JLabel("<html>" + content.getContent() + "</html>");
        contentTextLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        contentTextLabel.setForeground(Color.BLACK);
        contentTextLabel.setBounds(50, 80, 750, content.getContent().length() / 5); // Adjusted position and size
//        contentTextLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 3));
        contentPanel.add(contentTextLabel);

        JPanel contentImagePanel = null;
        if (content.getImagePath() != null && !content.getImagePath().isEmpty()) {
            contentImagePanel = new JPanel() {
                private Image image = new ImageIcon(content.getImagePath()).getImage();

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Draw the image scaled to fit the panel
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };

            contentImagePanel.setBounds(70, contentTextLabel.getHeight() + 100, 700, 400); // Position the image panel
            contentPanel.add(contentImagePanel);
        }

        // Add spacing at the bottom to maintain consistent height


        // Add a border for better visuals
        contentPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 5));
//        contentPanel.setPreferredSize(new Dimension(800, contentPanel.getY()+400)); // Set fixed size
        if( contentImagePanel != null){
            contentPanel.setSize(1900, contentImagePanel.getY() + 450);
            contentPanel.setMaximumSize(new Dimension(1900, contentImagePanel.getY() + 450));
        }
        else
        {
            contentPanel.setSize(1900, 450);
            contentPanel.setMaximumSize(new Dimension(1900, 450));
        }
        contentPanel.repaint();
        contentPanel.revalidate();

        return contentPanel;
    }
}
