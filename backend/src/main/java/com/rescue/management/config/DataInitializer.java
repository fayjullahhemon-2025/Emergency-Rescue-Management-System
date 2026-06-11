package com.rescue.management.config;

import com.rescue.management.model.Emergency;
import com.rescue.management.model.RescueTeam;
import com.rescue.management.model.User;
import com.rescue.management.repository.EmergencyRepository;
import com.rescue.management.repository.RescueTeamRepository;
import com.rescue.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EmergencyRepository emergencyRepository;
    private final RescueTeamRepository rescueTeamRepository;

    @Override
    public void run(String... args) {
        // Only seed if database is empty
        if (userRepository.count() > 0) {
            log.info("Database already contains data. Skipping seed.");
            return;
        }

        log.info("🌱 Seeding database with demo data...");

        // --- Seed Users ---
        User admin = new User(null, "Commander Sarah Chen", "admin@rescue.gov", "admin123",
                User.Role.ADMIN, "+1-555-0100", null);
        User citizen1 = new User(null, "John Rivera", "john@email.com", "user123",
                User.Role.CITIZEN, "+1-555-0201", null);
        User citizen2 = new User(null, "Aisha Patel", "aisha@email.com", "user123",
                User.Role.CITIZEN, "+1-555-0202", null);

        admin = userRepository.save(admin);
        citizen1 = userRepository.save(citizen1);
        citizen2 = userRepository.save(citizen2);
        log.info("✅ Seeded {} users", 3);

        // --- Seed Rescue Teams ---
        RescueTeam team1 = new RescueTeam(null, "Alpha Fire Response", "Fire & Hazmat",
                RescueTeam.TeamStatus.AVAILABLE,
                Arrays.asList("Captain James Cole", "Lt. Maria Santos", "FF. Derek Park", "FF. Yuki Tanaka"),
                "+1-555-0301", null);

        RescueTeam team2 = new RescueTeam(null, "Bravo Water Rescue", "Flood & Water",
                RescueTeam.TeamStatus.AVAILABLE,
                Arrays.asList("Captain Lena Okafor", "Diver Omar Hassan", "Diver Priya Nair", "Tech. Sam Wright"),
                "+1-555-0302", null);

        RescueTeam team3 = new RescueTeam(null, "Charlie Medical Unit", "Medical & Trauma",
                RescueTeam.TeamStatus.AVAILABLE,
                Arrays.asList("Dr. Emily Zhang", "Paramedic Rob Kline", "Paramedic Ana Cruz"),
                "+1-555-0303", null);

        RescueTeam team4 = new RescueTeam(null, "Delta Search & Rescue", "Earthquake & Collapse",
                RescueTeam.TeamStatus.AVAILABLE,
                Arrays.asList("Captain Leo Grant", "K9 Handler Maya Stone", "Tech. Ivan Petrov", "Eng. Fatima Al-Razi"),
                "+1-555-0304", null);

        RescueTeam team5 = new RescueTeam(null, "Echo Air Support", "Aerial & Mountain",
                RescueTeam.TeamStatus.AVAILABLE,
                Arrays.asList("Pilot Raj Mehta", "Co-Pilot Sarah Lin", "Medic Tom Reeves"),
                "+1-555-0305", null);

        team1 = rescueTeamRepository.save(team1);
        team2 = rescueTeamRepository.save(team2);
        team3 = rescueTeamRepository.save(team3);
        team4 = rescueTeamRepository.save(team4);
        team5 = rescueTeamRepository.save(team5);
        log.info("✅ Seeded {} rescue teams", 5);

        // --- Seed Rescue Team Users ---
        User teamUser1 = new User(null, "Captain James Cole", "alpha@rescue.gov", "team123",
                User.Role.RESCUE_TEAM, "+1-555-0301", team1.getId());
        User teamUser2 = new User(null, "Captain Lena Okafor", "bravo@rescue.gov", "team123",
                User.Role.RESCUE_TEAM, "+1-555-0302", team2.getId());
        User teamUser3 = new User(null, "Dr. Emily Zhang", "charlie@rescue.gov", "team123",
                User.Role.RESCUE_TEAM, "+1-555-0303", team3.getId());
        User teamUser4 = new User(null, "Captain Leo Grant", "delta@rescue.gov", "team123",
                User.Role.RESCUE_TEAM, "+1-555-0304", team4.getId());
        User teamUser5 = new User(null, "Pilot Raj Mehta", "echo@rescue.gov", "team123",
                User.Role.RESCUE_TEAM, "+1-555-0305", team5.getId());

        userRepository.saveAll(List.of(teamUser1, teamUser2, teamUser3, teamUser4, teamUser5));
        log.info("✅ Seeded {} team user accounts", 5);

        // --- Seed Demo Emergencies ---
        Emergency e1 = new Emergency(null,
                "John Rivera", "+1-555-0201",
                Emergency.DisasterType.FLOOD,
                new Emergency.Location("Riverside District, Sector 7", 23.8103, 90.4125),
                Emergency.Severity.CRITICAL,
                "Major flooding in Riverside District. Water level rising rapidly. Multiple families trapped on rooftops. Immediate evacuation needed.",
                null, // audioData
                Emergency.Status.PENDING, null, null,
                LocalDateTime.now().minusHours(2), LocalDateTime.now().minusHours(2));

        Emergency e2 = new Emergency(null,
                "Aisha Patel", "+1-555-0202",
                Emergency.DisasterType.FIRE,
                new Emergency.Location("Industrial Zone B, Building 14", 23.7545, 90.3907),
                Emergency.Severity.HIGH,
                "Large fire in industrial warehouse. Thick black smoke visible. Workers may still be inside. Fire spreading to adjacent buildings.",
                null, // audioData
                Emergency.Status.PENDING, null, null,
                LocalDateTime.now().minusHours(1), LocalDateTime.now().minusHours(1));

        Emergency e3 = new Emergency(null,
                "Maria Chen", "+1-555-0203",
                Emergency.DisasterType.EARTHQUAKE,
                new Emergency.Location("Downtown Core, Block 12", 23.7900, 90.4050),
                Emergency.Severity.HIGH,
                "Building partially collapsed after tremor. At least 10 people believed trapped. Structural damage in surrounding buildings.",
                null, // audioData
                Emergency.Status.PENDING, null, null,
                LocalDateTime.now().minusMinutes(45), LocalDateTime.now().minusMinutes(45));

        Emergency e4 = new Emergency(null,
                "Ahmed Khan", "+1-555-0204",
                Emergency.DisasterType.LANDSLIDE,
                new Emergency.Location("Hill Road, Northern Heights", 23.8250, 90.4300),
                Emergency.Severity.MEDIUM,
                "Landslide blocking main road. Two vehicles partially buried. No confirmed injuries yet but road is completely impassable.",
                null, // audioData
                Emergency.Status.PENDING, null, null,
                LocalDateTime.now().minusMinutes(30), LocalDateTime.now().minusMinutes(30));

        emergencyRepository.saveAll(List.of(e1, e2, e3, e4));
        log.info("✅ Seeded {} demo emergencies", 4);

        log.info("🎉 Database seeding complete!");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log.info("  Demo Accounts:");
        log.info("  Admin:        admin@rescue.gov / admin123");
        log.info("  Citizen:      john@email.com / user123");
        log.info("  Rescue Team:  alpha@rescue.gov / team123");
        log.info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
