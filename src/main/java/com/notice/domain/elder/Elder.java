package com.notice.domain.elder;

import com.notice.domain.image.Image;
import com.notice.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "elders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Elder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(nullable = false)
    private LocalTime wakeTime;

    @Column(nullable = false)
    private LocalTime sleepTime;

    @Column(nullable = false)
    private boolean usesDiaper;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private BowelType bowelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MobilityLevel mobilityLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CognitiveStatus cognitiveStatus;

    @OneToMany(mappedBy = "elder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElderMedication> medications = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Elder(
            User user,
            Image image,
            String name,
            LocalDate birth,
            Gender gender,
            LocalTime wakeTime,
            LocalTime sleepTime,
            boolean usesDiaper,
            BowelType bowelType,
            MobilityLevel mobilityLevel,
            CognitiveStatus cognitiveStatus
    ) {
        this.user = user;
        this.image = image;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.wakeTime = wakeTime;
        this.sleepTime = sleepTime;
        this.usesDiaper = usesDiaper;
        this.bowelType = bowelType;
        this.mobilityLevel = mobilityLevel;
        this.cognitiveStatus = cognitiveStatus;
        this.createdAt = LocalDateTime.now();
    }

    public static Elder create(
            User user,
            Image image,
            String name,
            LocalDate birth,
            Gender gender,
            LocalTime wakeTime,
            LocalTime sleepTime,
            boolean usesDiaper,
            BowelType bowelType,
            MobilityLevel mobilityLevel,
            CognitiveStatus cognitiveStatus
    ) {
        return new Elder(
                user,
                image,
                name,
                birth,
                gender,
                wakeTime,
                sleepTime,
                usesDiaper,
                bowelType,
                mobilityLevel,
                cognitiveStatus
        );
    }

    public void addMedication(ElderMedication medication) {
        this.medications.add(medication);
        medication.assignElder(this);
    }

    public void updateImage(Image image) {
        this.image = image;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateName(String name) {
        this.name = name;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateWakeTime(LocalTime wakeTime) {
        this.wakeTime = wakeTime;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateSleepTime(LocalTime sleepTime) {
        this.sleepTime = sleepTime;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateUsesDiaper(boolean usesDiaper) {
        this.usesDiaper = usesDiaper;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateBowelType(BowelType bowelType) {
        this.bowelType = bowelType;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateMobilityLevel(MobilityLevel mobilityLevel) {
        this.mobilityLevel = mobilityLevel;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateCognitiveStatus(CognitiveStatus cognitiveStatus) {
        this.cognitiveStatus = cognitiveStatus;
        this.updatedAt = LocalDateTime.now();
    }

    public void removeMedication(ElderMedication medication) {
        this.medications.removeIf(
                elderMedication -> elderMedication.getId().equals(medication.getId())
        );

        this.updatedAt = LocalDateTime.now();
    }
}