package com.notice.domain.elder;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "elder_medications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ElderMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "elder_id", nullable = false)
    private Elder elder;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MedicationType medicationType;

    @Column(nullable = false)
    private boolean takenInMorning;

    @Column(nullable = false)
    private boolean takenAtLunch;

    @Column(nullable = false)
    private boolean takenInEvening;

    @Column(nullable = false)
    private boolean takenBeforeSleep;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private ElderMedication(
            MedicationType medicationType,
            boolean takenInMorning,
            boolean takenAtLunch,
            boolean takenInEvening,
            boolean takenBeforeSleep
    ) {
        this.medicationType = medicationType;
        this.takenInMorning = takenInMorning;
        this.takenAtLunch = takenAtLunch;
        this.takenInEvening = takenInEvening;
        this.takenBeforeSleep = takenBeforeSleep;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public static ElderMedication create(
            MedicationType medicationType,
            boolean takenInMorning,
            boolean takenAtLunch,
            boolean takenInEvening,
            boolean takenBeforeSleep
    ) {
        return new ElderMedication(
                medicationType,
                takenInMorning,
                takenAtLunch,
                takenInEvening,
                takenBeforeSleep
        );
    }

    public void assignElder(Elder elder) {
        this.elder = elder;
    }

    public void updateMedicationType(MedicationType medicationType) {
        this.medicationType = medicationType;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTakenInMorning(boolean takenInMorning) {
        this.takenInMorning = takenInMorning;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTakenAtLunch(boolean takenAtLunch) {
        this.takenAtLunch = takenAtLunch;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTakenInEvening(boolean takenInEvening) {
        this.takenInEvening = takenInEvening;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateTakenBeforeSleep(boolean takenBeforeSleep) {
        this.takenBeforeSleep = takenBeforeSleep;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateActive(boolean active) {
        this.active = active;
        this.updatedAt = LocalDateTime.now();
    }
}