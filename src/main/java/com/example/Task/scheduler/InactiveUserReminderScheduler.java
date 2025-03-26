package com.example.Task.scheduler;


import com.example.Task.entity.SocialUser;
import com.example.Task.repository.SocialUserRepository;
import com.example.Task.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InactiveUserReminderScheduler {

    @Autowired
    private SocialUserRepository userRepository;


    @Autowired
    private EmailService emailService;

    @Value("${inactive.period.days}")
    private int inactivePeriodDays;

    @Scheduled(cron = "${scheduler.cron.expression}")
    public void checkInactiveUsers() {
        LocalDateTime inactiveThresholdDate = LocalDateTime.now().minusDays(inactivePeriodDays);

        List<SocialUser> inactiveUsers = userRepository.findUsersWithNoRecentPosts(inactiveThresholdDate);

        for (SocialUser user : inactiveUsers) {
            String message = String.format("Reminder: Hello %s, you have not posted anything for the last %d days!",
                    user.getName(), inactivePeriodDays);
            System.out.println(message);

            emailService.sendEmail(user.getEmail(),
                    "Oops! You haven't Posted Anything From Last 15 Days",
                    "Your Friends Are Waiting For Your Post. Come back and share something new!");
        }
    }

}